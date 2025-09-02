package visao;

import static biblioteca.VariaveisPublicas.editandoMemorando;
import static biblioteca.VariaveisPublicas.numemoParaImprimir;
import javax.swing.JOptionPane;
import relatorios.GerarRelatorios;


public class F_ESCOLHAIMPRESSAO extends javax.swing.JFrame {
    
    public F_ESCOLHAIMPRESSAO() {
        initComponents();
        setResizable(false);  //desabilitando o redimencionamento da tela
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar      
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnImprimirSemChapa1 = new javax.swing.JButton();
        btnImprimirComChapa1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Formulário de Impressão");
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        btnImprimirSemChapa1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnImprimirSemChapa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_Ok1.gif"))); // NOI18N
        btnImprimirSemChapa1.setText("SEM CHAPA");
        btnImprimirSemChapa1.setToolTipText("Imprimir");
        btnImprimirSemChapa1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImprimirSemChapa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirSemChapa1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnImprimirSemChapa1);
        btnImprimirSemChapa1.setBounds(210, 50, 130, 40);

        btnImprimirComChapa1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnImprimirComChapa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TICK.PNG"))); // NOI18N
        btnImprimirComChapa1.setText("COM CHAPA");
        btnImprimirComChapa1.setToolTipText("Gerar PDF");
        btnImprimirComChapa1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImprimirComChapa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirComChapa1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnImprimirComChapa1);
        btnImprimirComChapa1.setBounds(40, 50, 140, 40);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 140));

        setSize(new java.awt.Dimension(379, 141));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnImprimirSemChapa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirSemChapa1ActionPerformed

        GerarRelatorios objRel = new GerarRelatorios();
        try {
            if(!editandoMemorando){
                objRel.imprimirRelatorioPatrimoniosTransferidos("relatorio/relmemotransferidosemchapa.jasper", numemoParaImprimir);
            }else{
                objRel.imprimirRelatorioPatrimoniosTransferidos("relatorio/relmemotransferidosemchapa.jasper", numemoParaImprimir);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);
        }
        dispose();

    }//GEN-LAST:event_btnImprimirSemChapa1ActionPerformed

    private void btnImprimirComChapa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirComChapa1ActionPerformed

        GerarRelatorios objRel = new GerarRelatorios();
        try {
            if(!editandoMemorando){
                objRel.imprimirRelatorioPatrimoniosTransferidos("relatorio/relmemotransferidocomchapa.jasper", numemoParaImprimir);
            }else{
                objRel.imprimirRelatorioPatrimoniosTransferidos("relatorio/relmemotransferidocomchapa.jasper", numemoParaImprimir);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);
        }
        dispose();

    }//GEN-LAST:event_btnImprimirComChapa1ActionPerformed

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
    private javax.swing.JButton btnImprimirComChapa1;
    private javax.swing.JButton btnImprimirSemChapa1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
