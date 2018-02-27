/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import javax.swing.JFileChooser;

/**
 *
 * @author Mercurio
 */
public class ArchivoUtilidades {

    public static byte[] abrirArchivoBytes() {
        byte[] bytes = null;
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogType(JFileChooser.OPEN_DIALOG);
        jfc.showOpenDialog(null);

        if (!jfc.getSelectedFile().getAbsolutePath().isEmpty()) {
            ArchivoBytes arch = new ArchivoBytes();
            arch.setRuta(jfc.getSelectedFile().getAbsolutePath());
            bytes = arch.LeerArchivo();
        }


        return bytes;
    }

    public static void guardarArchivoBytes(byte[] bytes) {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogType(JFileChooser.SAVE_DIALOG);
        jfc.showSaveDialog(null);
        ArchivoBytes arch = new ArchivoBytes();
        if (!jfc.getSelectedFile().getAbsolutePath().isEmpty()) {
            arch.setRuta(jfc.getSelectedFile().getAbsolutePath());
            arch.EscribirArchivo(bytes);
        }
    }
}
