package visao;


public class F_ESCOLHATIPODEENTRADA extends javax.swing.JFrame {
    
    public F_ESCOLHATIPODEENTRADA() {
        initComponents();
        setResizable(false);  //desabilitando o redimencionamento da tela
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar      
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnEntrarDigitandoSeries = new javax.swing.JButton();
        btnEntrarLendoTXT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Formulário de Impressão");
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEntrarDigitandoSeries.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnEntrarDigitandoSeries.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_Ok1.gif"))); // NOI18N
        btnEntrarDigitandoSeries.setText("DIGITAÇÃO MANUAL");
        btnEntrarDigitandoSeries.setToolTipText("");
        btnEntrarDigitandoSeries.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEntrarDigitandoSeries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarDigitandoSeriesActionPerformed(evt);
            }
        });
        getContentPane().add(btnEntrarDigitandoSeries, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 180, 43));

        btnEntrarLendoTXT.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnEntrarLendoTXT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TICK.PNG"))); // NOI18N
        btnEntrarLendoTXT.setText("LER ARQUIVO TXT");
        btnEntrarLendoTXT.setToolTipText("");
        btnEntrarLendoTXT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEntrarLendoTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarLendoTXTActionPerformed(evt);
            }
        });
        getContentPane().add(btnEntrarLendoTXT, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 180, 43));

        setSize(new java.awt.Dimension(446, 141));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEntrarDigitandoSeriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarDigitandoSeriesActionPerformed
        
        dispose();
        F_GERARTXTENTRADAMANUAL frm = new F_GERARTXTENTRADAMANUAL(new javax.swing.JFrame(), true);
        frm.setVisible(true);  
        
    }//GEN-LAST:event_btnEntrarDigitandoSeriesActionPerformed

    private void btnEntrarLendoTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarLendoTXTActionPerformed
  
        dispose(); 
        F_GERARTXTENTRADAAUTO frm = new F_GERARTXTENTRADAAUTO(new javax.swing.JFrame(), true);
        frm.setVisible(true);
        
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
    // End of variables declaration//GEN-END:variables
}
