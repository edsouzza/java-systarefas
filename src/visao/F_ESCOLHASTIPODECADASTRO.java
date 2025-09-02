package visao;

import static biblioteca.VariaveisPublicas.tipoCadastroEntrada;


public class F_ESCOLHASTIPODECADASTRO extends javax.swing.JDialog {
    
    public F_ESCOLHASTIPODECADASTRO(java.awt.Frame parent, boolean modal) {
        
        super(parent, modal); // <- isso é essencial para funcionar como modal
        initComponents();
        setResizable(false);  //desabilitando o redimencionamento da tela
                        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnEntrarDigitandoSeries = new javax.swing.JButton();
        btnEntrarLendoTXT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Formulário de Impressão");
        setBackground(new java.awt.Color(0, 51, 255));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        btnEntrarDigitandoSeries.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnEntrarDigitandoSeries.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_Ok1.gif"))); // NOI18N
        btnEntrarDigitandoSeries.setText("POR DIGITAÇÃO MANUAL");
        btnEntrarDigitandoSeries.setToolTipText("");
        btnEntrarDigitandoSeries.setActionCommand("POR DIGITAÇÃO MANUAL");
        btnEntrarDigitandoSeries.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEntrarDigitandoSeries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarDigitandoSeriesActionPerformed(evt);
            }
        });
        jPanel1.add(btnEntrarDigitandoSeries);
        btnEntrarDigitandoSeries.setBounds(20, 50, 230, 50);

        btnEntrarLendoTXT.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnEntrarLendoTXT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TICK.PNG"))); // NOI18N
        btnEntrarLendoTXT.setText("POR LEITURA ARQUIVO TXT ");
        btnEntrarLendoTXT.setToolTipText("");
        btnEntrarLendoTXT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEntrarLendoTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarLendoTXTActionPerformed(evt);
            }
        });
        jPanel1.add(btnEntrarLendoTXT);
        btnEntrarLendoTXT.setBounds(270, 50, 230, 50);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 140));

        setSize(new java.awt.Dimension(514, 141));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEntrarDigitandoSeriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarDigitandoSeriesActionPerformed
        //ENTRADA MANUAL
        if(tipoCadastroEntrada){
            dispose();
            F_GERARTXTENTRADAMANUAL frm = new F_GERARTXTENTRADAMANUAL(new javax.swing.JFrame(), true);
            frm.setVisible(true);  
        }else{            
        //SAÍDA MANUAL    
            dispose();
            F_GERARTXTENVIOMANUAL frm = new F_GERARTXTENVIOMANUAL(null,true);
            frm.setVisible(true);  
        }
        tipoCadastroEntrada = false;
    }//GEN-LAST:event_btnEntrarDigitandoSeriesActionPerformed

    private void btnEntrarLendoTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarLendoTXTActionPerformed
        //ENTRADA AUTOMATICA VIA TXT
        if(tipoCadastroEntrada){
            dispose(); 
            F_GERARTXTENTRADAAUTO frm = new F_GERARTXTENTRADAAUTO(new javax.swing.JFrame(), true);
            frm.setVisible(true);
        }else{
        //SAÍDA AUTOMATICA  
            dispose(); 
            F_GERARTXTENVIOAUTO frm = new F_GERARTXTENVIOAUTO(null,true);
            frm.setVisible(true); 
        }
        tipoCadastroEntrada = false;
    }//GEN-LAST:event_btnEntrarLendoTXTActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(F_IMPRESSAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(F_IMPRESSAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(F_IMPRESSAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(F_IMPRESSAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new F_IMPRESSAO().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEntrarDigitandoSeries;
    private javax.swing.JButton btnEntrarLendoTXT;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
