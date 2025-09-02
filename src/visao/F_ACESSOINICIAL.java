package visao;

import Dao.DAOUsuario;
import biblioteca.Biblioteca;
import biblioteca.CampoLimitadoParaRF;
import biblioteca.MetodosPublicos;
import biblioteca.ValidarLogin;
import static biblioteca.VariaveisPublicas.novaSenha;
import static biblioteca.VariaveisPublicas.rfUsuario;
import controle.ControleGravarLog;
import controle.Criptografia;
import controle.CtrlUsuario;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;
import modelo.Usuario;

public class F_ACESSOINICIAL extends javax.swing.JDialog {

    /**
     * FORMULARIO PARA ENTRADA DE USUARIO QUE FARA SEU PRIMEIRO ACESSO AO SISTEMA. OBS ELE SERA CADASTRADO NA SEÇÃO DA INFORMATICA INICIALMENTE
     */
    
    String novoUser, loginUser, senhaUser ="";
    
    Biblioteca              umaBiblio           = new Biblioteca();
    MetodosPublicos         umMetodo            = new MetodosPublicos();
    Usuario                 umModeloUsuario     = new Usuario();
    CtrlUsuario             umControleUsuario   = new CtrlUsuario();
    DAOUsuario              usuarioDAO          = new DAOUsuario();
    ControleGravarLog       umGravarLog         = new ControleGravarLog();
    ValidarLogin            umaValidacao        = new ValidarLogin();
    
    
    public F_ACESSOINICIAL(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setIcon();
        initComponents();
        setResizable(false);   //desabilitando o redimencionamento da tela 
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar   
        
        umaBiblio.configurarCamposTextos(txtNome);          
        txtSenha.setHorizontalAlignment(txtSenha.CENTER); 
        txtLogin.setHorizontalAlignment(txtLogin.CENTER);        
        txtLogin.setDocument(new CampoLimitadoParaRF(7));
       
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
        lblUsuario = new javax.swing.JLabel();
        txtLogin = new javax.swing.JTextField();
        lblSenha = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastre seu usuário para ter o acesso ao sistema");

        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LOCK_ADD.PNG"))); // NOI18N
        btnGravar.setText("Gravar");
        btnGravar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGravar.setEnabled(false);
        btnGravar.setPreferredSize(new java.awt.Dimension(95, 25));
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblUsuario.setText("RF");

        txtLogin.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtLogin.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtLogin.setToolTipText("D123456");
        txtLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLoginFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLoginFocusLost(evt);
            }
        });
        txtLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtLoginMouseClicked(evt);
            }
        });
        txtLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtLoginKeyPressed(evt);
            }
        });

        lblSenha.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSenha.setText("Senha");

        txtSenha.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtSenha.setToolTipText("");
        txtSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSenhaMouseClicked(evt);
            }
        });
        txtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaKeyPressed(evt);
            }
        });

        lblNome.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNome.setText("Nome");

        txtNome.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtNome.setToolTipText("");
        txtNome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomeFocusGained(evt);
            }
        });
        txtNome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNomeMouseClicked(evt);
            }
        });
        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(81, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblNome)
                    .addComponent(txtNome)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuario)
                            .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSenha)
                            .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnGravar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuario, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSenha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        setSize(new java.awt.Dimension(544, 289));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void gravarNovoUser(){
        novoUser    = txtNome.getText();
        loginUser   = txtLogin.getText();
        senhaUser   = txtSenha.getText();
        
        if (novoUser == null || loginUser.equals("")|| senhaUser.equals(""))
        {    
              JOptionPane.showMessageDialog(null, "Usuário ou Senha inválida entre com seu usuário e sua senha!", "Senha ou Usuário Inválidos",2);  
              btnGravar.setEnabled(false);
              txtNome.requestFocus();
              
        }else{
            try {
                //criptografando a senha para gravar no banco                
                novaSenha = Criptografia.Criptografar(senhaUser);
                
                umModeloUsuario.setNome(novoUser);
                umModeloUsuario.setRf(loginUser);
                umModeloUsuario.setSenha(novaSenha);                
                umModeloUsuario.setSecaoid(1);
                if(loginUser.equals("D631863"))
                {
                   umModeloUsuario.setNivelacesso(1); 
                }else{
                   umModeloUsuario.setNivelacesso(2); 
                }                
                umModeloUsuario.setStatus("ATIVO");
                umModeloUsuario.setObs("Cadastro inicial para primeiro acesso ao Sistema!");
               
                if (!usuarioDAO.RegistroDuplicado(umModeloUsuario)) //se nao estiver duplicado libera a gravaçao do registro
                {
                    if(umControleUsuario.salvarUsuario(umModeloUsuario))
                    {
                        //JOptionPane.showMessageDialog(null, "Usuário foi cadastrado com sucesso acesse com suas credenciais!", "Usuário Cadastrado!",2); 
                        umGravarLog.gravarLog("cadastro de "+umModeloUsuario.getNome());
                        //System.exit(0);  
                        dispose();    
                    }            
                }      
                
            } catch (NoSuchAlgorithmException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível gravar o novo usuário!", "Erro ao tentar gravar usuário",2); 
            }        
        }         
    }
    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        gravarNovoUser();           
    }//GEN-LAST:event_btnGravarActionPerformed

    private void CancelarReiniciarSenha(){
        
        umModeloUsuario = umControleUsuario.pesquisarUsuario(rfUsuario, 0); 
        umControleUsuario.reiniciarSenhaUsuario(umModeloUsuario);       
        
    }
    
    private void txtLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLoginMouseClicked
        txtLogin.selectAll();
    }//GEN-LAST:event_txtLoginMouseClicked

    private void txtSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnGravarActionPerformed(null);
        }
        btnGravar.setEnabled(true);
    }//GEN-LAST:event_txtSenhaKeyPressed

    private void txtLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoginKeyPressed
      if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtSenha.requestFocus();
        }
    }//GEN-LAST:event_txtLoginKeyPressed

    private void txtNomeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomeFocusGained
        txtNome.selectAll();
        txtSenha.setEditable(false);
    }//GEN-LAST:event_txtNomeFocusGained

    private void txtNomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNomeMouseClicked
        txtNome.selectAll();
    }//GEN-LAST:event_txtNomeMouseClicked

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtLogin.requestFocus();
        }
    }//GEN-LAST:event_txtNomeKeyPressed

    private void txtSenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSenhaMouseClicked
        txtSenha.selectAll();
    }//GEN-LAST:event_txtSenhaMouseClicked

    private void txtLoginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLoginFocusGained
        txtLogin.selectAll();
    }//GEN-LAST:event_txtLoginFocusGained
    
    private void validarLogin()
    {
        //Validado o login no que se refere a ter uma letra e seis numeros      
        if (umaValidacao.loginValido(txtLogin.getText())){    
            txtSenha.setEditable(true);
            txtSenha.requestFocus();
        }else{
            JOptionPane.showMessageDialog(null, "Atenção digite o RF com 7 digitos sendo o primeiro uma letra, Ex: D999999", "Número de RF inválido!", 2);
            txtLogin.requestFocus();
        }         
    }
    
    private void txtLoginFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLoginFocusLost
        if(txtLogin.getText().length() > 0)
        {
           validarLogin(); 
        }else{
          JOptionPane.showMessageDialog(null, "Atenção digite o RF com 7 digitos sendo o primeiro uma letra, Ex: D999999", "Número do RF inválido!", 2);
          txtLogin.requestFocus();
        }    
       
    }//GEN-LAST:event_txtLoginFocusLost

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
            java.util.logging.Logger.getLogger(F_ACESSOINICIAL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_ACESSOINICIAL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_ACESSOINICIAL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_ACESSOINICIAL.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                F_ACESSOINICIAL dialog = new F_ACESSOINICIAL(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtNome;
    private javax.swing.JPasswordField txtSenha;
    // End of variables declaration//GEN-END:variables
}
