/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utilidades;

/**
 *
 * @author Mercurio
 */
public class Conversores {

        public static String hextToASSCII(String hexstr) {
        if (hexstr == null) {
            return null;
        } else {
            int len = hexstr.length();
            String str = "";
            for (int i = 0; i < len / 2; i++) {
                int in = (Integer.parseInt(hexstr.substring(i * 2, i * 2 + 2), 16));
                str += (char) in;

            }
            return str;
        }
        }
      public static String binToASSCII(String binstr) {
        if (binstr == null) {
            return null;
        } else {
            int len = binstr.length();
            String str = "";
            for (int i = 0; i < len / 8; i++) {
                int in = (Integer.parseInt(binstr.substring(i * 8, i * 8 + 8), 2));
                str += (char) in;

            }
            return str;
        }

    }
    public static String assciitoBin(String texto){
        if (texto == null) {
            return null;
        }

        String binstr = "";
        for (int ab = 0; ab < texto.length(); ab++) {

            String val = Integer.toBinaryString((int) texto.charAt(ab));
            
            int longd=val.length();
            if(longd<8){
                int f=8%(longd);
                String fal="";
                //System.out.println(f);
                for(int a=0;a<f;a++)
                    fal+="0";            
                   val=fal+val;
               }
            binstr += val;


        }
        return binstr;
    }
        public static String assciitoHEX(String texto){
          if(texto==null){
              return null;
          }

          String hexstr="";
            for(int ab=0;ab<texto.length();ab++){

               String val=Integer.toHexString((int)texto.charAt(ab));
               if(val.length()<2){
                   val="0"+val;
               }
               hexstr+=val;


             }
          return hexstr;
      }
    public static String assciitoOctal(String texto) {
        if (texto == null) {
            return null;
        }

        String octstr = "";
        for (int ab = 0; ab < texto.length(); ab++) {

            String val = Integer.toOctalString((int) texto.charAt(ab));
            int longd=val.length();
           
            if(longd<3){
                int f=3%longd;
                String fal="";
               
                for(int a=0;a<f;a++)
                    fal+="0";
                   val=fal+val;
               }
            
            octstr += val;
        }
        return octstr;
    }
     public static byte[] hexToBytes(String strhex){
        if(strhex==null){
            return null;
        }else if(strhex.length()<2){
            return null;
        }else{
            int len=strhex.length()/2;
            byte[] buffer=new byte[len];
            for(int i=0;i<len;i++){
                buffer[i]=(byte)Integer.parseInt(strhex.substring(i*2,i*2+2),16);
            }
            return buffer;
        }
    }
   public static String bytesToHex(byte[] data){
       if(data==null){
           return null;
       }else{
           int len=data.length;
           String str="";
           for(int i=0;i<len;i++){
               if((data[i]&0xFF)<16)str=str+"0"
                       +java.lang.Integer.toHexString(data[i]&0xFF);
               else str=str
                       +java.lang.Integer.toHexString(data[i]&0xFF);
           }
           return str.toUpperCase();
           }
       } 

}
