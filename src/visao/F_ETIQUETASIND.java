package visao;

import biblioteca.TudoMaiusculas;
import static biblioteca.VariaveisPublicas.nomeRelatorio;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import relatorios.GerarRelatorios;

public class F_ETIQUETASIND extends javax.swing.JFrame {
    TudoMaiusculas  tdm = new TudoMaiusculas();
    public F_ETIQUETASIND() {
        initComponents();
        setResizable(false);   //desabilitando o redimencionamento da tela      
        //configurando o txtPARAM
        txtPARAM.setFont(new Font("Arial",Font.BOLD,14));
        txtPARAM.setForeground(Color.RED); 
        txtPARAM.setDocument(new TudoMaiusculas());         //letras maiusculas no jTextField 
        txtPARAM.setHorizontalAlignment(JTextField.CENTER); //centralizando o texto no JTextField
        
        //Impede que formulario seja arrastado na tela
        this.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentMoved(ComponentEvent e) {
            setEnabled(false);
            setEnabled(true);
        }
        });//fim addComponentListener  
    }
    
    private void consultarImprimirEtiqueta()
    {
       if(txtPARAM.equals("") || txtPARAM.getText().length() == 0)   
       { 
          JOptionPane.showMessageDialog(null, "Digite algo para pesquisar!!");
          txtPARAM.requestFocus();
       }else{
            String param = txtPARAM.getText();
            nomeRelatorio = "relEtiquetaIndividual";
            GerarRelatorios objRel = new GerarRelatorios();
            try {
                objRel.imprimirEtiquetaSelecionada("relatorio/"+nomeRelatorio+".jasper", param);  
                //JOptionPane.showMessageDialog(rootPane, "codigo: "+codigoSelecionado+" relatorio: "+nomeRelatorio);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!"+e);                
            }           
       }
       txtPARAM.setText(null);
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtPARAM = new javax.swing.JTextField();
        btnImprimirEtiqueta = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Imprime etiqueta");

        jPanel1.setBackground(new java.awt.Color(238, 237, 237));

        txtPARAM.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPARAM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPARAMKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPARAMKeyReleased(evt);
            }
        });

        btnImprimirEtiqueta.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnImprimirEtiqueta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/impressora.png"))); // NOI18N
        btnImprimirEtiqueta.setText("Imprimir");
        btnImprimirEtiqueta.setToolTipText("Imprimir etiqueta da estação digitada");
        btnImprimirEtiqueta.setBorder(new javax.swing.border.MatteBorder(null));
        btnImprimirEtiqueta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImprimirEtiqueta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirEtiquetaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("Entre com o nome da estação");
        jLabel1.setBorder(javax.swing.BorderFactory.createCompoundBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel1)
                .addGap(0, 55, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(btnImprimirEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(txtPARAM, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPARAM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImprimirEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnImprimirEtiquetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirEtiquetaActionPerformed
       consultarImprimirEtiqueta();
    }//GEN-LAST:event_btnImprimirEtiquetaActionPerformed

    private void txtPARAMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPARAMKeyPressed
        //se teclar enter estando dentro do edit efetuar a pesquisa
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            consultarImprimirEtiqueta();
        }
       
    }//GEN-LAST:event_txtPARAMKeyPressed

    private void txtPARAMKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPARAMKeyReleased
        
    }//GEN-LAST:event_txtPARAMKeyReleased
    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(F_ETIQUETASIND.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_ETIQUETASIND.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_ETIQUETASIND.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_ETIQUETASIND.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new F_ETIQUETASIND().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimirEtiqueta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField txtPARAM;
    // End of variables declaration//GEN-END:variables
}
