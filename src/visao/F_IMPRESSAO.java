/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import static biblioteca.VariaveisPublicas.clicouNaTabela;
import static biblioteca.VariaveisPublicas.codigoSelecionado;
import static biblioteca.VariaveisPublicas.contador;
import static biblioteca.VariaveisPublicas.nomeRelatorio;
import static biblioteca.VariaveisPublicas.entidadeInativa;
import static biblioteca.VariaveisPublicas.tabela;
import javax.swing.JOptionPane;
import relatorios.GerarRelatorios;
import relatorios.GerarPDF;


public class F_IMPRESSAO extends javax.swing.JFrame {
    public F_IMPRESSAO() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGerarParaImpressora = new javax.swing.JButton();
        btnGerarPDF = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Formulário de Impressão");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGerarParaImpressora.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnGerarParaImpressora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/impressora.png"))); // NOI18N
        btnGerarParaImpressora.setText("Imprimir");
        btnGerarParaImpressora.setToolTipText("Imprimir");
        btnGerarParaImpressora.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGerarParaImpressora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarParaImpressoraActionPerformed(evt);
            }
        });
        getContentPane().add(btnGerarParaImpressora, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 50, 130, 43));

        btnGerarPDF.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnGerarPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/PDF.png"))); // NOI18N
        btnGerarPDF.setText("Gerar PDF");
        btnGerarPDF.setToolTipText("Gerar PDF");
        btnGerarPDF.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGerarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarPDFActionPerformed(evt);
            }
        });
        getContentPane().add(btnGerarPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 130, 43));

        setSize(new java.awt.Dimension(395, 171));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGerarParaImpressoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarParaImpressoraActionPerformed
        //impressoes para SECOES CLIENTES CONTATOS USUARIOS PATRIMOVEIS
        if(codigoSelecionado > 0)
        {
            //imprimir apenas o registro selecionado
            GerarRelatorios objRel = new GerarRelatorios();
            try {
                objRel.imprimirSelecionado("relatorio/"+nomeRelatorio+".jasper", codigoSelecionado, tabela);  
                //JOptionPane.showMessageDialog(rootPane, "codigo: "+codigoSelecionado+" relatorio: "+nomeRelatorio);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!"+e);                
            }
        }else{
            //se nao selecionou um registro imprime todos
            GerarRelatorios objRel = new GerarRelatorios();
            try {
                objRel.imprimirTodos("relatorio/"+nomeRelatorio+".jasper", tabela);    
                //JOptionPane.showMessageDialog(rootPane, "codigo: "+codigoSelecionado+" relatorio: "+nomeRelatorio);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!"+e);                
            }
        } 
        
        clicouNaTabela    = false;
        codigoSelecionado = 0;
        nomeRelatorio     = "";
        dispose();
    }//GEN-LAST:event_btnGerarParaImpressoraActionPerformed

    private void btnGerarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarPDFActionPerformed
        //fechar o formulario e gerar o PDF
        dispose();  
        GerarPDF pdf  = new GerarPDF();
        if (tabela.equals("TBLSECOES"))
        {            
            //RELATORIO DE SEÇÕES
            if(entidadeInativa.equals("INATIVOS") && contador > 0){
               pdf.GerarPDFSECOESINATIVADAS();  
               contador=0;  
               entidadeInativa = "";
            }else{
                pdf.GerarPDFSECOES();   
                contador=0;                
            }         
        }else 
        if (tabela.equals("TBLCLIENTES"))
        {
            //RELATORIO DE CLIENTES/COLABORADORES            
            if(entidadeInativa.equals("INATIVOS") && contador > 0){
               pdf.GerarPDFCLIENTESINATIVADOS();  
               contador=0;  
               entidadeInativa = "";
            }else{
                pdf.GerarPDFCLIENTES();  
                contador=0;                
            }                        
        }else 
        if (tabela.equals("TBLUSUARIOS"))
        {            
            //RELATORIO DE USUÁRIOS
            if(entidadeInativa.equals("INATIVOS") && contador > 0){
               pdf.GerarPDFUSUARIOSINATIVOS();    
               contador=0;  
               entidadeInativa = "";
            }else{
                pdf.GerarPDFUSUARIOS();   
                contador=0;                
            }         
        }else 
        if (tabela.equals("TBLPATRIMOVEL"))
        {
                pdf.GerarPDFPATRIMOVEIS();   
        }else 
        if (tabela.equals("TBLCONTATOS"))
        {
                pdf.GerarPDFCONTATOS();   
        }
        
    }//GEN-LAST:event_btnGerarPDFActionPerformed

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
    private javax.swing.JButton btnGerarPDF;
    private javax.swing.JButton btnGerarParaImpressora;
    // End of variables declaration//GEN-END:variables
}
