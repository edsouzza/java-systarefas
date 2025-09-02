package visao;

import biblioteca.Biblioteca;
import biblioteca.CampoLimitadoEmQdeLetrasNumeros;
import biblioteca.MetodosPublicos;
import static biblioteca.VariaveisPublicas.novaSenha;
import static biblioteca.VariaveisPublicas.rfUsuario;
import controle.Criptografia;
import controle.CtrlUsuario;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Usuario;

public class F_NOVASENHA extends javax.swing.JDialog {

    /**
     * FORMULARIO PARA ENTRADA DE NOVA SENHA PARA ACESSO AO SISTEMA
     */
    
    Biblioteca          umaBiblio           = new Biblioteca();
    MetodosPublicos     umMetodo            = new MetodosPublicos();
    Usuario             umModeloUsuario     = new Usuario();
    CtrlUsuario         umControleUsuario   = new CtrlUsuario();
    
    
    public F_NOVASENHA(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setIcon();
        initComponents();
        setResizable(false);   //desabilitando o redimencionamento da tela 
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar       
        
        txtSenha.setDocument(new CampoLimitadoEmQdeLetrasNumeros(10));        
       
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

        txtSenha = new javax.swing.JPasswordField();
        btnLogar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Entre com uma senha pessoal!");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        txtSenha.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtSenha.setToolTipText("máximo 10 caracteres");
        txtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaKeyPressed(evt);
            }
        });

        btnLogar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LOCK_ADD.PNG"))); // NOI18N
        btnLogar.setText("Gravar");
        btnLogar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogar.setEnabled(false);
        btnLogar.setPreferredSize(new java.awt.Dimension(95, 25));
        btnLogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_Cancelar.gif"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLogar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(298, 152));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            gravarNovaSenha();
        }
        btnLogar.setEnabled(true);
    }//GEN-LAST:event_txtSenhaKeyPressed

    private void gravarNovaSenha(){
        novaSenha = txtSenha.getText();
        
        if (novaSenha == null || novaSenha.equals(""))
        {    
              JOptionPane.showMessageDialog(null, "Senha inválida entre com a nova senha!", "Senha Inválida",2);   
              
              btnLogar.setEnabled(false);
              txtSenha.requestFocus();
              
        }else{
            try {
                //criptografando a senha para gravar no banco                
                novaSenha = Criptografia.Criptografar(novaSenha);
                
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(F_LOGIN.class.getName()).log(Level.SEVERE, null, ex);
            } 
            dispose();
        }         
       
    }
    
    private void btnLogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogarActionPerformed
        gravarNovaSenha();
    }//GEN-LAST:event_btnLogarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        JOptionPane.showMessageDialog(null, "Operação cancelada você ainda não tem acesso ao Sistema!", "Senha Inválida",2);
        CancelarReiniciarSenha();
        System.exit(0);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void CancelarReiniciarSenha(){
        
        umModeloUsuario = umControleUsuario.pesquisarUsuario(rfUsuario, 0); 
        umControleUsuario.reiniciarSenhaUsuario(umModeloUsuario);       
        
    }
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //JOptionPane.showMessageDialog(null, "RF DO USUARIO "+rfUsuario);
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
            java.util.logging.Logger.getLogger(F_NOVASENHA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_NOVASENHA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_NOVASENHA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_NOVASENHA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                F_NOVASENHA dialog = new F_NOVASENHA(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnLogar;
    private javax.swing.JPasswordField txtSenha;
    // End of variables declaration//GEN-END:variables
}
