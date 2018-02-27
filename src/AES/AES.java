/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AES;

import Utilidades.Conversores;

/**
 *
 * @author Mercurio
 */
public class AES {

    /**
     * Admite llaves de 128,192 o 256 bits
     * @param entrada
     */
    protected int actual;//indice del estado actual del algoritmo AES
    /**
     * El número de palabras (32 bits)
     */
    protected static int Nb = 4;
    /**
     *
     */
    protected int Nk;
    /**
     * Nùmero de rondas para este algoritmo AES.
     */
    protected int Nr;
    /**
     * Las matrices de estado del Algoritmo AES.
     */
    protected int[][][] estado;
    /**
     * Key Schedule en AES.
     */
    protected int[] w;
    /**
     * Llave
     */
    protected int[] llave;
/***
 * Constructora
 * @param entrada
 * Es la llave en representación binaria
 * Parametros de AES.
 * tamaño de la llave (palabras/bytes/bits)  (4/16/128)  (6/24/192) (8/32/256)
 * bloque texto plano (palabras/bytes/bits)  (4/16/128)  (4/16/128) (4/16/128)
 * Numero de rondas                          (10)        (12)       (14)
 * Tamaño de la llave por ronda  (p/by/b)    (4/16/128)  (4/16/128) (4/16/128)
 * Tamaño de la llave expandida  (p/by/b)    (44/176)    (52/208)   (60/240)
 */
    public AES(byte[] entrada) {
        llave = new int[entrada.length];
        for (int i = 0; i < entrada.length; i++) {
            llave[i] = entrada[i];
        }
        Nb = 4;//Estandar para AES (4*32)=128 bits
        switch (entrada.length) {
            //Llave de 128 bits
            case 16:
                Nr = 10;
                Nk = 4;
                break;
            //Llave de 192 bits.
            case 24:
                Nr = 12;
                Nk = 6;
                break;

            // Llave de 256 bits.
            case 32:
                Nr = 14;
                Nk = 8;
                break;
            default:
                throw new IllegalArgumentException("Solamente soporta llaves de 128,192 y 256 bits");
        }
        /**
         * Crear la matriz de almancenamiento para los estados.
         * Solamente se requieren 2 estados con  4 filas y Nb columnas.
         */
        estado = new int[2][4][Nb];
        /**
         * Crear el vector de almacenamiento para la expansiòn de la llave
         */
        w = new int[Nb * (Nr + 1)];
        /**
         * Expander la llave dentro del arreglo w
         */
        expandirLlave();
       
    }
   

    /**
     * Los 128 bits de un estado son se les aplica un desplazamiento XOR
     * con los 128 bits de la llave expendida.
     * s:matriz de estado que tiene Nb columnas y 4 filas.
     * ronda: Una ronda de la llave w a ser adicionada.
     * s:devuelve la adicion de la llave por ronda
     */
    protected int[][] addRoundKey(int[][] s, int ronda) {
        for (int c = 0; c < Nb; c++) {
            for (int r = 0; r < 4; r++) {
                s[r][c] = s[r][c] ^ ((w[ronda * Nb + c] << (r * 8)) >>> 24);
            }
        }
        return s;
    }

    /**
     * Encripta el texto dado
     **/
    protected int[][] cifrado(int[][] entrada, int[][] salida) {
        for (int i = 0; i < entrada.length; i++) {
            for (int j = 0; j < entrada[0].length; j++) {
                salida[i][j] = entrada[i][j];
            }
        }
        actual = 0;//Ronda 0
        addRoundKey(salida, actual);

        for (actual = 1; actual < Nr; actual++) {
            subBytes(salida);
            shiftRows(salida);
            mixColumns(salida);
            addRoundKey(salida, actual);
        }
        subBytes(salida);
        shiftRows(salida);
        addRoundKey(salida, actual);
        return salida;
    }

    /***
     * Decifra el cipher text
     * entrada:
     *     Arreglo de el texto a decifrar.
     * salida:
     *     Arreglo para almacenar el texto decifrado.
     * */
    protected int[][] inversoCifrar(int[][] entrada, int[][] salida) {
        for (int i = 0; i < entrada.length; i++) {
            for (int j = 0; j < entrada.length; j++) {
                salida[i][j] = entrada[i][j];
            }
        }
        actual = Nr;
        addRoundKey(salida, actual);

        for (actual = Nr - 1; actual > 0; actual--) {
            invShiftRows(salida);
            invSubBytes(salida);
            addRoundKey(salida, actual);
            invMixColumnas(salida);
        }
        invShiftRows(salida);
        invSubBytes(salida);
        addRoundKey(salida, actual);
        return salida;

    }

    /**
     *
     * @param bloque
     * El bloque de 128-bit (16-byte) texto plano usando cifrado AES.
     * @return
     * 128 bits (16-byte) de ciphertext producidos porel algoritmo de cifrado.
     */
    public byte[] cifrar(byte[] bloque) {
        if (bloque.length != 16) {
            throw new IllegalArgumentException("Solamente se pueden cifrar bloques de 16byte");
        }
        byte[] almacena = new byte[bloque.length];

        for (int i = 0; i < Nb; i++) {//columnas
            for (int j = 0; j < 4; j++) {//filas
                estado[0][j][i] = bloque[i * Nb + j] & 0xff;
            }
        }
        //cifra dentro de s[2];
        cifrado(estado[0], estado[1]);
        for (int i = 0; i < Nb; i++) {
            for (int j = 0; j < 4; j++) {
                almacena[i * Nb + j] = (byte) (estado[1][j][i] & 0xff);
            }
        }
        return almacena;
    }

    /**
     *
     * @param bloque
     * bloque de 128-bits (16-byte) de ciphertext a ser decifrado.
     * @return
     * bloque de 128 bits (16 byte) de plainttext producido por el algoritmo de decifrado.
     */
    public byte[] descifrar(byte[] bloque) {
        if (bloque.length != 16) {
            throw new IllegalArgumentException("Solamente se pueden decifrar bloques de 16byte");
        }
        byte[] almacena = new byte[bloque.length];

        for (int i = 0; i < Nb; i++) {//Columnas
            for (int j = 0; j < 4; j++) {//Filas
                estado[0][j][i] = bloque[i * Nb + j] & 0xff;
            }
        }
        //decifrar en s[2]
        inversoCifrar(estado[0], estado[1]);
        for (int i = 0; i < Nb; i++) {
            for (int j = 0; j < 4; j++) {
                almacena[i * Nb + j] = (byte) (estado[1][j][i] & 0xff);
            }
        }
        return almacena;

    }

    /**
     *
     * InvMixes cada columna de la matriz de estado. Multiplica cada columna --a polinomial
     * en GF(GF(2^8)^4)-- veces {0b}x^3+{0d}^2+{09}x+{0e} modulo x^4+1.
     * est:
     * matriz de estado que tiene Nb columnas y 4 filas.
     * devuelve s: despues de unmixing cada columna.
     */
    protected int[][] invMixColumnas(int[][] est) {
        int temp0, temp1, temp2, temp3;
        for (int c = 0; c < Nb; c++) {
            temp0 = mult(0x0e, est[0][c]) ^ mult(0x0b, est[1][c]) ^ mult(0x0d, est[2][c]) ^ mult(0x09, est[3][c]);
            temp1 = mult(0x09, est[0][c]) ^ mult(0x0e, est[1][c]) ^ mult(0x0b, est[2][c]) ^ mult(0x0d, est[3][c]);
            temp2 = mult(0x0d, est[0][c]) ^ mult(0x09, est[1][c]) ^ mult(0x0e, est[2][c]) ^ mult(0x0b, est[3][c]);
            temp3 = mult(0x0b, est[0][c]) ^ mult(0x0d, est[1][c]) ^ mult(0x09, est[2][c]) ^ mult(0x0e, est[3][c]);

            est[0][c] = temp0;
            est[1][c] = temp1;
            est[2][c] = temp2;
            est[3][c] = temp3;
        }
        return est;
    }

    /**
     * Aplica un desplazamiento inverso a las ultimas 3 filas de la matriz de estado.
     * est: Una matriz de estado que tiene Nb columnas y 4 filas.
     * est: devuelve est despues de un desplazamiento ciclico inverso aplicado a cada fila.
     *
     */
    protected int[][] invShiftRows(int[][] est) {
        int temp1, temp2, temp3, i; //Temporales usados para los desplazamientos
        //fila 1;
        temp1 = est[1][Nb - 1];
        for (i = Nb - 1; i > 0; i--) {
            est[1][i] = est[1][(i - 1) % Nb];
        }
        est[1][0] = temp1;
        //fila 2
        temp1 = est[2][Nb - 1];
        temp2 = est[2][Nb - 2];
        for (i = Nb - 1; i > 1; i--) {
            est[2][i] = est[2][(i - 2) % Nb];
        }
        est[2][1] = temp1;
        est[2][0] = temp2;
        //fila 3
        temp1 = est[3][Nb - 3];
        temp2 = est[3][Nb - 2];
        temp3 = est[3][Nb - 1];
        for (i = Nb - 1; i > 2; i--) {
            est[3][i] = est[3][(i - 3) % Nb];
        }
        est[3][0] = temp1;
        est[3][1] = temp2;
        est[3][2] = temp3;
        return est;
    }

    /***
     *
     * Aplica la sustituciòn inversa de S-Box a cada byte de la matriz de estado.
     * est:
     * Una matriz de estado que tiene Nb columnas y 4 filas.
     * est:
     * La matriz de estado despues de haber aplicado la substituciòn inversa.
     */
    protected int[][] invSubBytes(int[][] est) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Nb; j++) {
                est[i][j] = invSubWord(est[i][j]) & 0xFF;
            }
        }
        return est;
    }

    /**
     * Aplica la sustituciòn inversa S-Box a cada byte de una palabre de 4-byte.
     * palabra:
     * Una palabra de 4-byte.
     * devuelve:palabra:
     * Despues que una substituciòn inversa es aplicada a cada byte.
     */
    protected static int invSubWord(int palabra) {
        int subPalabra = 0;
        for (int i = 24; i >= 0; i -= 8) {
            int in = palabra << i >>> 24;
            subPalabra |= Cajas.invSBox[in] << (24 - i);
        }
        return subPalabra;
    }
/***
 * Key Expansion toma una entrada de 4 palabras (16 byte) llave, y produce un arreglo lineal de 44 palabras (176 bytes)
 * la llave es copiada dentro de las primeras cuatro palabras de la llave expandida. El resto de la llave expandida es
 * rellenado con 4 palabras cada vez.
 * Esto es suficiente para para proveer una llave de 4 palabras por ronda para el estado inicial
 * AddRoundKey y cada una de las 10 rondas de el algoritmo de cifrado. *
 * Cada palabra agregada w[i] depende de su precedente w[i-1], y las cuatro posiciones atras, w[i-4]. *
 * 1.-rotWord:
 * Realiza un desplazamiento circular hacia la izquierda de un byte sobre una palabra.
 * 2.-subWord:
 * Realiza una sustituciòn de bytes sobre cada byte de su palabra de entrada, usando la S-box.
 * al resultado de los pasos anteriores (1 y 2) se le aplica un XOR con una ronda constante, Rcon[i]
 * @return
 */
    protected int[] expandirLlave() {
        int temp, i = 0;
        while (i < Nk) {
            w[i] = 0x00000000;
            w[i] |= llave[4 * i] << 24;
            w[i] |= llave[4 * i + 1] << 16;
            w[i] |= llave[4 * i + 2] << 8;
            w[i] |= llave[4 * i + 3];
            i++;
        }
        i = Nk;
        while (i < Nb * (Nr + 1)) {
            temp = w[i - 1];
            if (i % Nk == 0) {
                //Aplica un XOR con una ronda constante rCon.
                temp = subWord(rotWord(temp)) ^ (Cajas.rCon[i / Nk] << 24);
            } else if (Nk > 6 && (i % Nk == 4)) {
                temp = subWord(temp);
            } else {
            }
            w[i] = w[i - Nk] ^ temp;
            i++;
        }
        return w;
    }


    /**
     * Mixes cada columna de una matriz de estado.
     * Cada byte de una columna es mapeado dentro de un nuevo valor que es una
     * funciòn de todos los cuatro bytes en aquella columna.
     *Cada elemento en la matriz producto es la suma de productos de elementos
     * de una fila y una columna. En este caso las adiciones y multipliciaciones
     * individuales  son realizados con un polinomio en GF(GF(2^8)^4)--times {03}x^3+{01}x^2+{01}x+{02} modulo x^4+1.
     * est:
     *  Una matriz de estado que tiene Nb columnas y 4 filas.
     * est:
     *  Matriz de estado despues de Mix cada columna.
     */
    protected int[][] mixColumns(int[][] est) {
        int temp0, temp1, temp2, temp3;
        for (int c = 0; c < Nb; c++) {

            temp0 = mult(0x02, est[0][c]) ^ mult(0x03, est[1][c]) ^ est[2][c] ^ est[3][c];
            temp1 = est[0][c] ^ mult(0x02, est[1][c]) ^ mult(0x03, est[2][c]) ^ est[3][c];
            temp2 = est[0][c] ^ est[1][c] ^ mult(0x02, est[2][c]) ^ mult(0x03, est[3][c]);
            temp3 = mult(0x03, est[0][c]) ^ est[1][c] ^ est[2][c] ^ mult(0x02, est[3][c]);

            est[0][c] = temp0;
            est[1][c] = temp1;
            est[2][c] = temp2;
            est[3][c] = temp3;
        }

        return est;
    }

    /**
     * Multiplica dos polinomios a(x),b(x) en GF(2^8) modulo elpolinomio irreducible
     * m(x) = x^8+x^4+x^3+x+1. (i.e. m(x) = 0x11b).
     * part_a:
     *      Un polinomio a(x)=a7x^7+a6x^6+a5x^5+a4x^4+a3x^3+a2x^2+a1x+a0 en
     *          GF(2^8).
     * part_b:
     *      Un polinomio b(x)=b7x^7+b6x^6+b5x^5+b4x^4+b3x^3+b2x^2+b1x+b0 en
     *      GF(2^8).
     * return a(x)b(x) modulo x^8+x^4+x^3+x+1. *
     */
    protected static int mult(int part_a, int part_b) {
        int sum = 0;

        while (part_a != 0) { // Mientras no sea 0

            if ((part_a & 1) != 0) // Checar si el primer bit es 1
            {
                sum = sum ^ part_b; // Sumar b desde el bit mas pequeño
            }
            part_b = xtime(part_b); // bit shift left mod 0x11b si es necesario;

            part_a = part_a >>> 1; // lowest bit of a was used so shift right
        }
        return sum;

    }

    /**
     * Aplica un desplazamiento circular hacia la izquierda sobre una palabra.
     * Esto significa que una palabra [b0,b1,b2,b3] es transformada en
     * [b0,b1,b2,b3,b4]
     * word:
     * Una palabra de 4-byte.
     * return word:
     * Despues de que una permutaciòn cìclica es aplicada.
     */
    protected static int rotWord(int word) {
        return (word << 8) | ((word & 0xFF000000) >>> 24);
    }

    /***
     * Aplica un desplazamiento cìclico a las ùltimas 3 filas de la matriz de estado.
     * La primera fila de estado no es alterada.
     * est:
     *   Una matriz de estado que tiene Nb columnas y 4 filas.
     * return est:
     *   Despues de un desplazamiento ciclico es aplicado a cada fila.
     */
    protected int[][] shiftRows(int[][] est) {
        int temp1, temp2, temp3, i;
        //fila 1
        temp1 = est[1][0];
        for (i = 0; i < Nb - 1; i++) {
            est[1][i] = est[1][(i + 1) % Nb];
        }
        est[1][Nb - 1] = temp1;

        // fila 2, se desplaza 1-byte
        temp1 = est[2][0];
        temp2 = est[2][1];
        for (i = 0; i < Nb - 2; i++) {
            est[2][i] = est[2][(i + 2) % Nb];
        }
        est[2][Nb - 2] = temp1;
        est[2][Nb - 1] = temp2;

        // fila 3, se desplaza 2-bytes
        temp1 = est[3][0];
        temp2 = est[3][1];
        temp3 = est[3][2];
        for (i = 0; i < Nb - 3; i++) {
            est[3][i] = est[3][(i + 3) % Nb];
        }
        est[3][Nb - 3] = temp1;
        est[3][Nb - 2] = temp2;
        est[3][Nb - 1] = temp3;

        return est;
    }

    /**
     * Aplica substituciòn S-Box a cada byte de una matriz de estado.
     * est:
     *   Una matriz de estado teniendo Nb columnas y 4 filas.
     * est:
     *   Despues de que la subtituciòn S-box es aplicada a cada byte.
     */
    protected int[][] subBytes(int[][] est) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Nb; j++) {
                est[i][j] = subWord(est[i][j]) & 0xFF;
            }
        }
        return est;

    }

    /**
     * Realiza una sustituciòn de byte sobre cada byte de entrada usando la
     * S-Box
     * word:
     *   Una palabra de 4-bye.
     * word:
     *   Despues de una sustituciòn S-box es aplicada a cada byte.
     */
    protected static int subWord(int word) {
        int subWord = 0;
        for (int i = 24; i >= 0; i -= 8) {
            int in = word << i >>> 24;
            subWord |= Cajas.sBox[in] << (24 - i);
        }
        return subWord;
    }

    /**
     * Multiplica x veces un polinomio b(x) en GF(2^8) modulo el polinomio reducible
     * m(x)=x^8+x^4+x^3+x+1. (ejemplo. m(x) = 0x11b).
     * b:
     *   Un polinomio b(x)=b7x^7+b6x^6+b5x^5+b4x^4+b3x^3+b2x^2+b1x+b0 en
     *    GF(2^8).
     * return: xb(x) mod x8+x4+x3+x+1.
     */
    protected static int xtime(int b) {
        if ((b & 0x80) == 0) {
            return b << 1;
        }
        return (b << 1) ^ 0x11b;
    }
}
