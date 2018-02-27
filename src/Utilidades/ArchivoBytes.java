/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utilidades;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 *
 * @author geovanni
 */
public class ArchivoBytes {
 private String ruta=null;
    public String getRuta() {
        return ruta;
    }
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

   /* public byte[] LeerArchivo(){
         byte[] buffer=null;
        try{
            FileInputStream fis = new FileInputStream(ruta);
            int numOfBytes = fis.available();
            buffer = new byte[numOfBytes];
            fis.read(buffer);
            fis.close();

        }catch(Exception ex){
            System.out.println("Error al Abrir el Archivo "+ruta);
        }
        return buffer;
    }*/
        public byte[] LeerArchivo(){
         byte[] buffer=null;
         String text="";
        try{
            FileInputStream fis = new FileInputStream(ruta);
            InputStreamReader istr= new InputStreamReader(fis,"ISO-8859-1");
            int a=istr.read();//primer char
            if(a==-1)//EOF inmediato
                return buffer;
             text+=Character.toString((char)a);//Prmer char
            int k=1;
            while(k==1){ //
                a=istr.read();//otro
                //System.out.print(a+"_");
                if(a==-1)//EOF
                    break;
                text+=Character.toString((char)a);//Agregas
                //System.out.println(txtOk);
            }
            int i=0;
            buffer=new byte[text.length()];
            for(i=0;i<text.length();i++)
                buffer[i]=(byte)text.charAt(i);

            //jTextArea2.setText(new String(utf,"ISO-8859-1"));

        }catch(Exception ex){
            System.out.println("Error al Abrir el Archivo "+ruta);
        }
        return buffer;
    }
    public void EscribirArchivo(byte[] bytes){
        try{
           FileOutputStream fos = new FileOutputStream(ruta);
           fos.write(bytes);
           fos.close();

        }catch(Exception ex){
            System.out.println("Error al Escribir el Archivo "+ruta);
        }
    }

}
