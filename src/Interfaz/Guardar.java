/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Guardar.java
 *
 * Created on 3 déc. 2009, 07:35:05
 */

package Interfaz;

/**
 *
 * @author Mercurio
 */
public class Guardar extends javax.swing.JDialog {
public static final int GUARDAR_PLAINT_TEXT=1;
public static final int GUARDAR_CYPHER_TEXT=2;
private int accion=GUARDAR_PLAINT_TEXT;

    public int getAccion() {
        return accion;
    }
    /** Creates new form Guardar */
    public Guardar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        iniciar();
    }
    private void iniciar(){
        guarda_plaint_bt.setSelected(true);
        accion=GUARDAR_PLAINT_TEXT;
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
        jPanel1 = new javax.swing.JPanel();
        guarda_plaint_bt = new javax.swing.JRadioButton();
        guardar_cp_rb = new javax.swing.JRadioButton();
        guardar_bt = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Guardar...");

        buttonGroup1.add(guarda_plaint_bt);
        guarda_plaint_bt.setText("Guardar A archivo Texto de Entrada");
        guarda_plaint_bt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guarda_plaint_btActionPerformed(evt);
            }
        });

        buttonGroup1.add(guardar_cp_rb);
        guardar_cp_rb.setText("Guardar a archivo Texto Procesado");
        guardar_cp_rb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardar_cp_rbActionPerformed(evt);
            }
        });

        guardar_bt.setText("Guardar");
        guardar_bt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardar_btActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Save.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(guardar_cp_rb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                        .addComponent(guardar_bt))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(guarda_plaint_bt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(27, 27, 27))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                        .addComponent(guardar_bt))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(guarda_plaint_bt)
                        .addGap(18, 18, 18)
                        .addComponent(guardar_cp_rb)
                        .addContainerGap(36, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void guarda_plaint_btActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guarda_plaint_btActionPerformed
        // TODO add your handling code here:
        accion=GUARDAR_PLAINT_TEXT;      
    }//GEN-LAST:event_guarda_plaint_btActionPerformed

    private void guardar_cp_rbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardar_cp_rbActionPerformed
        // TODO add your handling code here:
        accion=GUARDAR_CYPHER_TEXT;        
    }//GEN-LAST:event_guardar_cp_rbActionPerformed

    private void guardar_btActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardar_btActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_guardar_btActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Guardar dialog = new Guardar(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton guarda_plaint_bt;
    private javax.swing.JButton guardar_bt;
    private javax.swing.JRadioButton guardar_cp_rb;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

}
