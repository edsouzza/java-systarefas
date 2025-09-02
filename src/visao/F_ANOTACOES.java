package visao;

import biblioteca.Biblioteca;
import biblioteca.MetodosPublicos;
import conexao.ConnConexao;
import controle.CtrlAnotacoes;
import Dao.DAOAnotacoes;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JOptionPane;
import modelo.Anotacoes;

    
public class F_ANOTACOES extends javax.swing.JDialog {
    ConnConexao             umaConexao                  = new ConnConexao();
    MetodosPublicos         umMetodo                    = new MetodosPublicos();
    Biblioteca              umaBiblio                   = new Biblioteca();
    Anotacoes               modAnotacoes                = new Anotacoes();
    CtrlAnotacoes           CtrlAnotacoes               = new CtrlAnotacoes();
    DAOAnotacoes            daoAnotacoes                = new DAOAnotacoes();
    
    public F_ANOTACOES(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Leitura();
        setResizable(false);   //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar

        //Impede que formulario seja arrastado na tela
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                setEnabled(false);
                setEnabled(true);
            }
        });//fim addComponentListener
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane4 = new javax.swing.JScrollPane();
        txtOCORRENCIASFUTURAS = new javax.swing.JTextArea();
        btnGravarAtualizacao = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CONTROLE DE VERSÕES DO SISTEMA");

        txtOCORRENCIASFUTURAS.setColumns(20);
        txtOCORRENCIASFUTURAS.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtOCORRENCIASFUTURAS.setLineWrap(true);
        txtOCORRENCIASFUTURAS.setRows(5);
        txtOCORRENCIASFUTURAS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane4.setViewportView(txtOCORRENCIASFUTURAS);

        btnGravarAtualizacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_gravar.gif"))); // NOI18N
        btnGravarAtualizacao.setText("Gravar");
        btnGravarAtualizacao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGravarAtualizacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarAtualizacaoActionPerformed(evt);
            }
        });

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sair.gif"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1026, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(337, 337, 337)
                .addComponent(btnGravarAtualizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGravarAtualizacao, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9))
        );

        setSize(new java.awt.Dimension(1062, 720));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Leitura() {
        modAnotacoes.setCodigo(1);
        daoAnotacoes.pesquisarAnotacoesDAO(modAnotacoes);
        String ocorrencia = modAnotacoes.getOcorrencia();
        
        if(!umMetodo.tabelaEstaVazia("tblanotacoes")){
            txtOCORRENCIASFUTURAS.setText(ocorrencia);
            txtOCORRENCIASFUTURAS.setCaretPosition(0);
        }
    }
    
    
    private void btnGravarAtualizacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarAtualizacaoActionPerformed
        if(umMetodo.tabelaEstaVazia("tblanotacoes")){
            modAnotacoes.setOcorrencia(txtOCORRENCIASFUTURAS.getText());
            if (CtrlAnotacoes.salvarAnotacoes(modAnotacoes)){
                JOptionPane.showMessageDialog(null, "Ocorrência gravada com sucesso!");
                txtOCORRENCIASFUTURAS.setCaretPosition(0);
            } else {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao tentar cadastrar o registro!");
            }
        }else{
            modAnotacoes.setOcorrencia(txtOCORRENCIASFUTURAS.getText());
            if (CtrlAnotacoes.atualizarAnotacoes(modAnotacoes)){
                JOptionPane.showMessageDialog(null, "Ocorrência atualizada com sucesso!");
                txtOCORRENCIASFUTURAS.setCaretPosition(0);
            } else {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao tentar atualizar o registro!");
            }
        }
    }//GEN-LAST:event_btnGravarAtualizacaoActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(F_ANOTACOES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_ANOTACOES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_ANOTACOES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_ANOTACOES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                F_ANOTACOES dialog = new F_ANOTACOES(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGravarAtualizacao;
    private javax.swing.JButton btnSair;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea txtOCORRENCIASFUTURAS;
    // End of variables declaration//GEN-END:variables
}
