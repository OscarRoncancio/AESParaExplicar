/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

/**
 *
 * @author Mercurio
 */
public class Cadena {

    public static String validarCadena(String text) {
        int longitud = text.length();

        int itera = longitud / 16;
        if ((longitud % 16) > 0) {
            itera = itera + 1;
            int resto = 16 - (longitud % 16);
            for (int a = 0; a < resto; a++) {
                text += ' ';
            }
        }

        return text;

    }

    public static String ordenarBloqueAES(String bloque) {
        String tmp = "";
        
            tmp += bloque.charAt(0);
            tmp += bloque.charAt(4);
            tmp += bloque.charAt(8);
            tmp += bloque.charAt(12);
            tmp += bloque.charAt(1);
            tmp += bloque.charAt(5);
            tmp += bloque.charAt(9);
            tmp += bloque.charAt(13);
            tmp += bloque.charAt(2);
            tmp += bloque.charAt(6);
            tmp += bloque.charAt(10);
            tmp += bloque.charAt(14);
            tmp += bloque.charAt(3);
            tmp += bloque.charAt(7);
            tmp += bloque.charAt(11);
            tmp += bloque.charAt(15);
        
        System.out.println(tmp);
        return tmp;
}
}