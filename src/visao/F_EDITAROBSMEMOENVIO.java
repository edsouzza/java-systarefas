package visao;

import biblioteca.Biblioteca;
import biblioteca.CampoTxtLimitadoPorQdeCaracteresLowerCase;
import biblioteca.MetodosPublicos;
import static biblioteca.VariaveisPublicas.numemoParaEditarObs;
import controle.ControleGravarLog;
import controle.CtrlPatriTransferido;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JOptionPane;
import modelo.PatriTransferido;

public class F_EDITAROBSMEMOENVIO extends javax.swing.JDialog {

    /**
     * FORMULARIO PARA ENTRADA DE USUARIO QUE FARA SEU PRIMEIRO ACESSO AO SISTEMA. OBS ELE SERA CADASTRADO NA SEÇÃO DA INFORMATICA INICIALMENTE
     */
    
    String novaObs="";
    
    Biblioteca              umaBiblio              = new Biblioteca();
    MetodosPublicos         umMetodo               = new MetodosPublicos();
    PatriTransferido        umModeloPatriTrans     = new PatriTransferido();
    CtrlPatriTransferido    umControlePatriTrans   = new CtrlPatriTransferido();
    ControleGravarLog       umGravarLog            = new ControleGravarLog();
    
    
    public F_EDITAROBSMEMOENVIO(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setIcon();
        initComponents();
        setResizable(false);   //desabilitando o redimencionamento da tela 
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar   
        
        //umaBiblio.configurarCamposTextos(txtObs);          
        txtObs.setDocument(new CampoTxtLimitadoPorQdeCaracteresLowerCase(80)); 
       
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

        btnGravar = new javax.swing.JButton();
        lblNome = new javax.swing.JLabel();
        txtObs = new javax.swing.JTextField();
        btnSair = new javax.swing.JButton();
        lblNumemo = new javax.swing.JLabel();
        lblNumemo1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Faça a alteração pertinente a observação e grave");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_gravar.jpg"))); // NOI18N
        btnGravar.setText("Gravar");
        btnGravar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGravar.setEnabled(false);
        btnGravar.setPreferredSize(new java.awt.Dimension(95, 25));
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });

        lblNome.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNome.setText("EDITE A OBSERVAÇÃO E GRAVE");

        txtObs.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtObs.setToolTipText("");
        txtObs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtObsKeyPressed(evt);
            }
        });

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sair.gif"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSair.setPreferredSize(new java.awt.Dimension(95, 25));
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        lblNumemo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNumemo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumemo.setText("0000");

        lblNumemo1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNumemo1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNumemo1.setText("Memorando");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblNumemo1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNumemo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtObs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 522, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumemo)
                    .addComponent(lblNome)
                    .addComponent(lblNumemo1))
                .addGap(28, 28, 28)
                .addComponent(txtObs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        setSize(new java.awt.Dimension(590, 289));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void mostrarDados(){
        
        umModeloPatriTrans.setNumemo(numemoParaEditarObs);
        umControlePatriTrans.pesquisarPatriTransferido(umModeloPatriTrans);                
        lblNumemo.setText(umModeloPatriTrans.getNumemo());
        txtObs.setText(umModeloPatriTrans.getObservacao());               
        
    }
    
    
    private void gravarAlteracao(){
        novaObs = txtObs.getText();      
        
        umModeloPatriTrans.setNumemo(numemoParaEditarObs); 
        
        if(!txtObs.getText().isEmpty()){
            umModeloPatriTrans.setObservacao(umMetodo.primeiraLetraMaiuscula(novaObs));                    
        }else{
            umModeloPatriTrans.setObservacao("Obs : "+novaObs); 
        }
        
        if(umControlePatriTrans.editarPatriTransferido(umModeloPatriTrans))
        {            
            umGravarLog.gravarLog("alteracao da observaçao do memorando "+numemoParaEditarObs);
            JOptionPane.showMessageDialog(rootPane, "Alteração procedida com sucesso!" , "Registro de alteracao da observação",2);            
            dispose();
        }        
               
    }
    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        gravarAlteracao();           
    }//GEN-LAST:event_btnGravarActionPerformed
       
    private void txtObsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObsKeyPressed
         btnGravar.setEnabled(true);        
    }//GEN-LAST:event_txtObsKeyPressed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        mostrarDados();
    }//GEN-LAST:event_formWindowOpened
           
   public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img_icones_forms/LogonDaPMSP.png")));
   }
   
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
            java.util.logging.Logger.getLogger(F_EDITAROBSMEMOENVIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_EDITAROBSMEMOENVIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_EDITAROBSMEMOENVIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_EDITAROBSMEMOENVIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                F_EDITAROBSMEMOENVIO dialog = new F_EDITAROBSMEMOENVIO(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnSair;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNumemo;
    private javax.swing.JLabel lblNumemo1;
    private javax.swing.JTextField txtObs;
    // End of variables declaration//GEN-END:variables
}
