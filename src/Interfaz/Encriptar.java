/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Encriptar.java
 *
 * Created on Sep 8, 2009, 2:53:08 PM
 */
package Interfaz;

import AES.AES;
import Utilidades.ArchivoUtilidades;
import Utilidades.Cadena;
import Utilidades.Conversores;
import java.awt.Toolkit;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author geovanni
 */
public class Encriptar extends javax.swing.JFrame {

    private String plaint_text = null;
    private String cypher_text = null;
    private byte[] bytes_plaintText=null;
    private Object[] llavestam={"ASCII","HEX"};
    private String codCypher;
    private boolean bande_file=false;
    private String codplaint;
    private DefaultComboBoxModel comb=new DefaultComboBoxModel(llavestam);
    private DefaultComboBoxModel comb1=new DefaultComboBoxModel(llavestam);

    public byte[] getBytes_cipherText() {
        return bytes_cipherText;
    }

    public void setBytes_cipherText(byte[] bytes_cipherText) {
        this.bytes_cipherText = bytes_cipherText;
    }

    public byte[] getBytes_plaintText() {
        return bytes_plaintText;
    }

    public void setBytes_plaintText(byte[] bytes_plaintText) {
        this.bytes_plaintText = bytes_plaintText;
    }
    private byte[] bytes_cipherText=null;
    //Atributo para definir la accion a relizar
    private boolean cifrar = true;//Cifrar por defecto 
    //Tipo de algoritmo
    private int tipo_algoritmo = ALGORITMOS.CIFRADO_AES;
    private String llave;

    public void setTipo_algoritmo(int tipo_algoritmo) {
        this.tipo_algoritmo = tipo_algoritmo;
    }

    public int getTipo_algoritmo() {
        return tipo_algoritmo;
    }

    /** Creates new form Encriptar */
    public Encriptar() {
        initComponents();
        inicializaElementos();
    }

    public void setPlaint_text(String plainttext) {
        this.plaint_text = plainttext;
    }

    public String getPlaint_Text() {
        return plaint_text;
    }
    public void changeCodificacionCypher(String codant,String codselected){
      if(this.salida_txt!=null){
      if(codCypher.equals(codselected)){
        //this.input_txt.setText(Conversores.hextToASSCII(this.input_txt.getText()));
        }else{
          if(codselected.equals("HEX"))
            this.salida_txt.setText(Conversores.assciitoHEX(this.salida_txt.getText()));
          else
              this.salida_txt.setText(Conversores.hextToASSCII(this.salida_txt.getText()));
        }
        }
   codCypher=codselected;
    }
   public void changeCodificacionPlaint(String codant,String codselected){
   if(this.input_txt!=null){
      if(codplaint.equals(codselected)){
        //this.input_txt.setText(Conversores.hextToASSCII(this.input_txt.getText()));
        }else{
          if(codselected.equals("HEX"))
            this.input_txt.setText(Conversores.assciitoHEX(this.input_txt.getText()));
          else
              this.input_txt.setText(Conversores.hextToASSCII(this.input_txt.getText()));
        }
        }
   codplaint=codselected;

    }
    public void setCypher_Text(String cypher_Text) {
        this.cypher_text = cypher_Text;
    }

    public String getCypherText() {
        return this.cypher_text;
    }

    public void setFile_namePlaint(String filename) {
       this.file_name_lb.setText(filename);
    }
    public void setFile_nameCipher(String filename) {
       this.file_name_cp.setText(filename);
    }

    public void setAccion(boolean value) {
        this.cifrar = value;
    }

    public boolean getAccion() {
        return this.cifrar;
    }

    public String getLlave() {
        return this.llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    private void inicializaElementos() {
        this.input_txt.setText(null);
        this.salida_txt.setText(null);
        this.file_name_lb.setText("");
        this.file_name_cp.setText("");

        this.cifrar_rb.setSelected(true);

        //Limpiar los atributos
        this.plaint_text = null;
        this.cypher_text = null;
        this.codplaint= "ASCII";
        this.codCypher="ASCII";
        //Limpiar la llave
        this.llave = null;
        //Actividad Cifrar
        this.setAccion(true);
        this.cod_cypher_cb.setModel(comb);
        this.cod_cypher_cb.setSelectedIndex(0);
        this.codificacion_pla_cb.setModel(comb1);
        this.codificacion_pla_cb.setSelectedIndex(0);
        this.bande_file=false;
    }

    
    private void cifradoAES() {
        this.setPlaint_text(this.input_txt.getText());
        if (this.getPlaint_Text().length() > 0) {

            LlaveAES llavet=new LlaveAES(this, true);
            llavet.setLocation(Toolkit.getDefaultToolkit().getScreenSize().height/4,Toolkit.getDefaultToolkit().getScreenSize().width/4);
            llavet.setVisible(true);
            String hexkey =  llavet.getHexkey();
            llavet.dispose();
            if (hexkey!=null) {

                try {
                    if(codplaint.equals("HEX")){
                        try{
                            this.setPlaint_text(Conversores.hextToASSCII(this.getPlaint_Text()));
                            System.out.println("Entro...");
                        }catch(Exception e){
                            JOptionPane.showMessageDialog(null, "Error de Conversion...");
                            return;
                        }                        
                    }
                    String cad_val_pT = Cadena.validarCadena(this.getPlaint_Text());
                    String cypher = "";
                    byte[] key = Conversores.hexToBytes(hexkey);
                    AES aes =new AES(key);
                    for (int a = 0; a < cad_val_pT.length() / 16; a++) {
                        System.out.println("PT"+cad_val_pT.substring(a * 16, (a * 16) + 16));
                        String hextPlaintText = Conversores.assciitoHEX(cad_val_pT.substring(a * 16, (a * 16) + 16));
                        byte[] text = Conversores.hexToBytes(hextPlaintText);
                        byte[] cyphertext = aes.cifrar(text);
                        cypher += Conversores.hextToASSCII(Conversores.bytesToHex(cyphertext));
                    }                    
                    this.setCypher_Text(cypher);
                    this.salida_txt.setText(this.getCypherText());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
               

            } else {
                    JOptionPane.showConfirmDialog(this, "No hay Texto que procesar");
                return;
            }
         
    }

     private void descifradoAES() {
        if(!bande_file){
            this.setPlaint_text(this.input_txt.getText());
            System.out.println("No file");
        }
        if (this.getPlaint_Text().length() > 0) {

            LlaveAES llavet=new LlaveAES(this, true);
            llavet.setLocation(Toolkit.getDefaultToolkit().getScreenSize().height/4,Toolkit.getDefaultToolkit().getScreenSize().width/4);
            llavet.setVisible(true);
            String hexkey =  llavet.getHexkey();
            ///
                //hexkey=Conversores.assciitoHEX((Conversores.hextToASSCII(hexkey)));

            ///
            llavet.dispose();
            if (hexkey!=null) {

                try {
                    if(codplaint.equals("HEX")&&(!bande_file)){
                        try{
                            this.setPlaint_text(Conversores.hextToASSCII(this.getPlaint_Text()));
                        }catch(Exception e){
                            JOptionPane.showMessageDialog(null, "Error de Conversion...");
                            return;
                        }
                    }
                    String cad_val_pT = Cadena.validarCadena(this.getPlaint_Text());


                    String plaint = "";
                    byte[] key = Conversores.hexToBytes(hexkey);
                    AES aes =new AES(key);
                    for (int a = 0; a < cad_val_pT.length() / 16; a++) {
                        String hextCypherText = Conversores.assciitoHEX((cad_val_pT.substring(a * 16, (a * 16) + 16)));
                        byte[] text = Conversores.hexToBytes(hextCypherText);
                        byte[] plainttext = aes.descifrar(text);
                        plaint += Conversores.hextToASSCII(Conversores.bytesToHex(plainttext));
                    }
                    this.setCypher_Text(plaint);
                    this.salida_txt.setText(new String(Conversores.hexToBytes(Conversores.assciitoHEX(plaint)), "ISO-8859-1"));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }


            } else {
                    JOptionPane.showConfirmDialog(this, "No hay Texto que procesar");
                return;
            }

        }


    
   

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jTextField1 = new javax.swing.JTextField();
        jLayeredPane7 = new javax.swing.JLayeredPane();
        ejecutar_bt = new javax.swing.JButton();
        jLayeredPane5 = new javax.swing.JLayeredPane();
        cifrar_rb = new javax.swing.JRadioButton();
        descifrar_rb = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        input_txt = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        salida_txt = new javax.swing.JTextPane();
        jLayeredPane11 = new javax.swing.JLayeredPane();
        abrir_plaint_bt = new javax.swing.JButton();
        guardar_plaint_bt = new javax.swing.JButton();
        borrar_plaint_bt = new javax.swing.JButton();
        file_name_lb = new javax.swing.JLabel();
        entrada_lb = new javax.swing.JLabel();
        entrada_lb1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        codificacion_pla_cb = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cod_cypher_cb = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        file_name_cp = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        viewAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cifrador GVV");

        jTextField1.setText("jTextField1");

        jLayeredPane7.setBorder(javax.swing.BorderFactory.createTitledBorder("Cifrado AES"));

        ejecutar_bt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Refresh (copy).png"))); // NOI18N
        ejecutar_bt.setLabel("Ejecutar");
        ejecutar_bt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ejecutar_btMouseClicked(evt);
            }
        });
        ejecutar_bt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ejecutar_btActionPerformed(evt);
            }
        });
        ejecutar_bt.setBounds(470, 230, 130, 50);
        jLayeredPane7.add(ejecutar_bt, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane5.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));

        buttonGroup2.add(cifrar_rb);
        cifrar_rb.setSelected(true);
        cifrar_rb.setText("Cifrar");
        cifrar_rb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cifrar_rbMouseClicked(evt);
            }
        });
        cifrar_rb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cifrar_rbActionPerformed(evt);
            }
        });
        cifrar_rb.setBounds(10, 20, 100, 23);
        jLayeredPane5.add(cifrar_rb, javax.swing.JLayeredPane.DEFAULT_LAYER);

        buttonGroup2.add(descifrar_rb);
        descifrar_rb.setText("DesCifrar");
        descifrar_rb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                descifrar_rbMouseClicked(evt);
            }
        });
        descifrar_rb.setBounds(10, 50, 120, 23);
        jLayeredPane5.add(descifrar_rb, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Padlock (copy).png"))); // NOI18N
        jLabel3.setBounds(130, 20, 50, 50);
        jLayeredPane5.add(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane5.setBounds(430, 130, 190, 90);
        jLayeredPane7.add(jLayeredPane5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        input_txt.setToolTipText("");
        jScrollPane3.setViewportView(input_txt);

        jScrollPane3.setBounds(20, 40, 400, 320);
        jLayeredPane7.add(jScrollPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        salida_txt.setEditable(false);
        salida_txt.setToolTipText("");
        jScrollPane2.setViewportView(salida_txt);

        jScrollPane2.setBounds(700, 40, 400, 320);
        jLayeredPane7.add(jScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane11.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));

        abrir_plaint_bt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/My-Documents.png"))); // NOI18N
        abrir_plaint_bt.setText("...");
        abrir_plaint_bt.setToolTipText("Abrir Archivo...");
        abrir_plaint_bt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                abrir_plaint_btMouseClicked(evt);
            }
        });
        abrir_plaint_bt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrir_plaint_btActionPerformed(evt);
            }
        });
        abrir_plaint_bt.setBounds(10, 20, 80, 50);
        jLayeredPane11.add(abrir_plaint_bt, javax.swing.JLayeredPane.DEFAULT_LAYER);

        guardar_plaint_bt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N
        guardar_plaint_bt.setText("...");
        guardar_plaint_bt.setToolTipText("Guardar a Archivo");
        guardar_plaint_bt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guardar_plaint_btMouseClicked(evt);
            }
        });
        guardar_plaint_bt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardar_plaint_btActionPerformed(evt);
            }
        });
        guardar_plaint_bt.setBounds(90, 20, 80, 50);
        jLayeredPane11.add(guardar_plaint_bt, javax.swing.JLayeredPane.DEFAULT_LAYER);

        borrar_plaint_bt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lapiz.png"))); // NOI18N
        borrar_plaint_bt.setText("...");
        borrar_plaint_bt.setToolTipText("Limpiar");
        borrar_plaint_bt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                borrar_plaint_btMouseClicked(evt);
            }
        });
        borrar_plaint_bt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrar_plaint_btActionPerformed(evt);
            }
        });
        borrar_plaint_bt.setBounds(170, 20, 80, 50);
        jLayeredPane11.add(borrar_plaint_bt, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLayeredPane11.setBounds(430, 30, 260, 90);
        jLayeredPane7.add(jLayeredPane11, javax.swing.JLayeredPane.DEFAULT_LAYER);
        file_name_lb.setBounds(20, 370, 400, -1);
        jLayeredPane7.add(file_name_lb, javax.swing.JLayeredPane.DEFAULT_LAYER);

        entrada_lb.setText("Texto de Salida PlainText/CypherText");
        entrada_lb.setBounds(700, 20, 270, -1);
        jLayeredPane7.add(entrada_lb, javax.swing.JLayeredPane.DEFAULT_LAYER);

        entrada_lb1.setText("Texto de Entrada PlainText/CypherText");
        entrada_lb1.setBounds(20, 20, 270, -1);
        jLayeredPane7.add(entrada_lb1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Locker-blue.png"))); // NOI18N
        jLabel2.setBounds(450, 330, 150, 150);
        jLayeredPane7.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Codificacion")));

        codificacion_pla_cb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        codificacion_pla_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codificacion_pla_cbActionPerformed(evt);
            }
        });

        jLabel1.setText("Codificacion:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(codificacion_pla_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codificacion_pla_cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel1.setBounds(20, 390, 270, 70);
        jLayeredPane7.add(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder("Codificacion")));

        cod_cypher_cb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cod_cypher_cb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cod_cypher_cbActionPerformed(evt);
            }
        });

        jLabel4.setText("Codificacion:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 156, Short.MAX_VALUE)
                .addComponent(cod_cypher_cb, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cod_cypher_cb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2.setBounds(700, 390, 400, 70);
        jLayeredPane7.add(jPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        file_name_cp.setBounds(700, 370, 400, -1);
        jLayeredPane7.add(file_name_cp, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jMenu1.setText("Acerca de...");

        viewAbout.setText("Acerca de...");
        viewAbout.setToolTipText("Ver Frecuencia de Caracteres ESP");
        viewAbout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewAboutMouseClicked(evt);
            }
        });
        viewAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewAboutActionPerformed(evt);
            }
        });
        jMenu1.add(viewAbout);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1112, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void borrar_plaint_btActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrar_plaint_btActionPerformed
        // TODO add your handling code here:
         if (JOptionPane.showConfirmDialog(this, "¿En realidad desea limpiar el área de Trabajo?", "Cifrador GVV", JOptionPane.YES_NO_OPTION) == 0) {
            this.inicializaElementos();
        }
    }//GEN-LAST:event_borrar_plaint_btActionPerformed

    private void borrar_plaint_btMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_borrar_plaint_btMouseClicked
        // TODO add your handling code here:
       
}//GEN-LAST:event_borrar_plaint_btMouseClicked

    private void guardar_plaint_btActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardar_plaint_btActionPerformed
        // TODO add your handling code here:
        if((input_txt.getText().length()>0)||(salida_txt.getText().length()>0)){
        try{
            if(getPlaint_Text()==null){
                this.setPlaint_text(input_txt.getText());
            }


            Guardar gua=new Guardar(this, true);
            gua.setVisible(true);
            int estado=gua.getAccion();
            gua.dispose();
            if(estado==Guardar.GUARDAR_PLAINT_TEXT){
                bytes_plaintText=Conversores.hexToBytes((Conversores.assciitoHEX(Cadena.validarCadena(this.getPlaint_Text()))));
                if(bytes_plaintText==null){
                    JOptionPane.showMessageDialog(null, "No hay Texto que guardar");
                    return;
                }else{
                    try{
                        ArchivoUtilidades.guardarArchivoBytes(bytes_plaintText);
                }catch(Exception ex){
                        JOptionPane.showMessageDialog(null, "Error al Guardar el Archivo");
                }
                }
            }else{

            bytes_cipherText=Conversores.hexToBytes((Conversores.assciitoHEX(Cadena.validarCadena(this.getCypherText()))));
                if(bytes_cipherText==null){
                    JOptionPane.showMessageDialog(null, "No hay Texto que guardar");
                    return;
                }else{
                    try{
                        ArchivoUtilidades.guardarArchivoBytes(bytes_cipherText);
                }catch(Exception ex){
                        JOptionPane.showMessageDialog(null, "Error al Guardar el Archivo");
                }
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        }else{
            JOptionPane.showMessageDialog(null, "No hay Texto que guardar");
        }
    }//GEN-LAST:event_guardar_plaint_btActionPerformed

    private void guardar_plaint_btMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardar_plaint_btMouseClicked
        // TODO add your handling code here:
        
}//GEN-LAST:event_guardar_plaint_btMouseClicked

    private void abrir_plaint_btActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrir_plaint_btActionPerformed
        // TODO add your handling code here:
        inicializaElementos();
        try{
             bytes_plaintText=ArchivoUtilidades.abrirArchivoBytes();
             input_txt.setText(new String(bytes_plaintText,"ISO-8859-1"));
             this.setPlaint_text(Conversores.hextToASSCII(Conversores.bytesToHex(bytes_plaintText)));
             //input_txt.setText(this.getPlaint_Text());
             bande_file=true;
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error al Abrir el Archivo");
        }
    }//GEN-LAST:event_abrir_plaint_btActionPerformed

    private void abrir_plaint_btMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_abrir_plaint_btMouseClicked
        // TODO add your handling code here:         
                      
        
}//GEN-LAST:event_abrir_plaint_btMouseClicked

    private void descifrar_rbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_descifrar_rbMouseClicked
        // TODO add your handling code here:
        this.setAccion(false);//Descifrar
       

    }//GEN-LAST:event_descifrar_rbMouseClicked

    private void cifrar_rbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cifrar_rbMouseClicked
        // TODO add your handling code here:
        this.setAccion(true);//Cifrar
       

}//GEN-LAST:event_cifrar_rbMouseClicked

    private void ejecutar_btActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ejecutar_btActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ejecutar_btActionPerformed

    private void ejecutar_btMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ejecutar_btMouseClicked
        // TODO add your handling code here:
        if (input_txt.getText().isEmpty()) {
            JOptionPane.showConfirmDialog(null, "No hay texto que procesar...", "Cifrador GVV", JOptionPane.OK_OPTION);
        } else {

            if (this.getAccion()) {//Cifrar         
                        cifradoAES();
                       
            } else {//Descifrar              
                        descifradoAES();
            }
        }
}//GEN-LAST:event_ejecutar_btMouseClicked

    private void viewAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewAboutActionPerformed
        // TODO add your handling code here:
        AboutBox ab=new AboutBox(this, true);
        ab.setLocation(getWidth()/4, getHeight()/2);
        ab.setVisible(true);
   
    }//GEN-LAST:event_viewAboutActionPerformed

    private void viewAboutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewAboutMouseClicked
        // TODO add your handling code here:
}//GEN-LAST:event_viewAboutMouseClicked

    private void cifrar_rbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cifrar_rbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cifrar_rbActionPerformed

    private void codificacion_pla_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codificacion_pla_cbActionPerformed
        // TODO add your handling code here:
        try{
          
        changeCodificacionPlaint(codplaint,codificacion_pla_cb.getSelectedItem().toString());
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error en la Conversion...");
        }

    }//GEN-LAST:event_codificacion_pla_cbActionPerformed

    private void cod_cypher_cbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cod_cypher_cbActionPerformed
        // TODO add your handling code here:
        try{
              
                changeCodificacionCypher(codCypher,cod_cypher_cb.getSelectedItem().toString());
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Error en la Conversion...");
        }
    }//GEN-LAST:event_cod_cypher_cbActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                Encriptar encriptar = new Encriptar();
                encriptar.setVisible(true);
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                }

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton abrir_plaint_bt;
    private javax.swing.JButton borrar_plaint_bt;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JRadioButton cifrar_rb;
    private javax.swing.JComboBox cod_cypher_cb;
    private javax.swing.JComboBox codificacion_pla_cb;
    private javax.swing.JRadioButton descifrar_rb;
    private javax.swing.JButton ejecutar_bt;
    private javax.swing.JLabel entrada_lb;
    private javax.swing.JLabel entrada_lb1;
    private javax.swing.JLabel file_name_cp;
    private javax.swing.JLabel file_name_lb;
    private javax.swing.JButton guardar_plaint_bt;
    private javax.swing.JTextPane input_txt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLayeredPane jLayeredPane11;
    private javax.swing.JLayeredPane jLayeredPane5;
    private javax.swing.JLayeredPane jLayeredPane7;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextPane salida_txt;
    private javax.swing.JMenuItem viewAbout;
    // End of variables declaration//GEN-END:variables
}
