/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import Dao.DAOUsuario;
import biblioteca.CampoLimitadoParaRF;
import biblioteca.MetodosPublicos;
import biblioteca.ValidarLogin;
import static biblioteca.VariaveisPublicas.confIni;
import static biblioteca.VariaveisPublicas.cadastrado;
import static biblioteca.VariaveisPublicas.codigoUsuario;
import static biblioteca.VariaveisPublicas.nivelAcessoUsuario;
import static biblioteca.VariaveisPublicas.nomeBancoSetado;
import static biblioteca.VariaveisPublicas.acessoInicial;
import static biblioteca.VariaveisPublicas.nomeUsuario;
import static biblioteca.VariaveisPublicas.nomeUsuarioLogado;
import static biblioteca.VariaveisPublicas.novaSenha;
import static biblioteca.VariaveisPublicas.rfUsuario;
import controle.Criptografia;
import controle.CtrlUsuario;
import controle.CtrlLog;
import modelo.Usuario;
import modelo.Log;
import controle.ControleGravarLog;
import controle.ControleConfiguracaoInicial;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

public class F_LOGIN extends javax.swing.JFrame 
{
    MetodosPublicos             umMetodo            = new MetodosPublicos();
    ControleGravarLog           umGravarLog         = new ControleGravarLog();
    ControleConfiguracaoInicial cci                 = new ControleConfiguracaoInicial();
    CtrlLog                     umControleLog       = new CtrlLog();
    CtrlUsuario                 umControleUsuario   = new CtrlUsuario();
    DAOUsuario                  umDaoUsuario        = new DAOUsuario();
    Usuario                     umModeloUsuario     = new Usuario();
    Log                         umModeloLog         = new Log();
    JPopupMenu                  popupMenu           = new JPopupMenu();
    JMenuItem                   jmEscolherBanco     = popupMenu.add("Escolher Banco");
    String                      senhadobanco        = "";
    String                      nomeBanco           = "";
    int                         Tentativas          = 1;    
    ValidarLogin                umaValidacao       = new ValidarLogin();

    public F_LOGIN() {
        
        /* SEMPRE QUE COMPILAR UM PROJETO DO TIPO SYSTAREFAS VOCE DEVE ALTERAR :
        1-NO FORMULARIO LOGIN ESCOLHA O BANCO DE DADOS QUE DEVERA SER ABERTO NA INICIALIZACAO SYSTAREFAS
        2-ABRA A CONEXAO E ACESSE A CLASSE GETIPSERVIDOR PARA SETAR O IP DO SERVIDOR */

        //DEFINE O BANCO DE DADOS A SER UTILIZADO COMO PADRÃO NO SERVIDOR
        nomeBancoSetado = "SYSTAREFAS";                
               
        initComponents();
        setResizable(false);   //desabilitando o redimencionamento da tela 
        
        lblUsuario.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblSenha.setFont(new Font("Times New Roman", Font.BOLD, 14)); 
        txtSenha.setHorizontalAlignment(txtSenha.CENTER); 
        txtLogin.setHorizontalAlignment(txtLogin.CENTER);        
        txtLogin.setDocument(new CampoLimitadoParaRF(7));
                
        // Colocando enter para pular de campo 
//        HashSet conj = new HashSet(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
//        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
//        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);

        //Impede que formulario seja arrastado na tela
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                setEnabled(false);
                setEnabled(true);
            }
        });//fim addComponentListener
        
         //implementando o poupup menu de escolha de banco ao clicar com o botao direito do mouse 
        jmEscolherBanco.addActionListener(new ActionListener() 
        {
        @Override
            public void actionPerformed(ActionEvent e) {
                
                /*Não pude usaro o F_SETARBANCO que é praticamente uma cópia mas o tipo de form para abrir como poupup deve ser JFrame e não JDialog*/
                F_SELECIONARBANCO frm = new F_SELECIONARBANCO();
                frm.setVisible(true);   
            }
        });//fim addComponentListener do poupup menu
                                      
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblSelecionarBanco = new javax.swing.JLabel();
        jpLogin = new javax.swing.JPanel();
        txtLogin = new javax.swing.JTextField();
        txtSenha = new javax.swing.JPasswordField();
        btnLogar = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();
        lblSenha = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login Patrimônios");
        setName("frmLogin"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lblSelecionarBanco.setFont(new java.awt.Font("Tahoma", 0, 3)); // NOI18N
        lblSelecionarBanco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/LOGOMARCA.jpg"))); // NOI18N
        lblSelecionarBanco.setOpaque(true);
        lblSelecionarBanco.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSelecionarBancoMouseClicked(evt);
            }
        });

        jpLogin.setBackground(new java.awt.Color(230, 228, 228));
        jpLogin.setEnabled(false);

        txtLogin.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtLogin.setToolTipText("D123456");
        txtLogin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLoginFocusGained(evt);
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

        txtSenha.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtSenha.setToolTipText("");
        txtSenha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSenhaFocusGained(evt);
            }
        });
        txtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSenhaKeyPressed(evt);
            }
        });

        btnLogar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLogar.setText("Entrar");
        btnLogar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogar.setEnabled(false);
        btnLogar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnLogarFocusGained(evt);
            }
        });
        btnLogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogarActionPerformed(evt);
            }
        });

        lblUsuario.setText("Usuário");

        lblSenha.setText("Senha");

        javax.swing.GroupLayout jpLoginLayout = new javax.swing.GroupLayout(jpLogin);
        jpLogin.setLayout(jpLoginLayout);
        jpLoginLayout.setHorizontalGroup(
            jpLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpLoginLayout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(jpLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblUsuario)
                    .addComponent(txtLogin)
                    .addComponent(txtSenha, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(lblSenha)
                    .addComponent(btnLogar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );
        jpLoginLayout.setVerticalGroup(
            jpLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpLoginLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(lblSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(btnLogar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(lblSelecionarBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSelecionarBanco, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(528, 225));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    public void autenticar() {
        
        //setando o valor da senha na variavel
        String senha = txtSenha.getText();
        
        try {
            //criptografando a senha para verificar se existe no banco a senha criptografada  
            senha = Criptografia.Criptografar(senha);
            //JOptionPane.showMessageDialog(null,senha);
        } catch (NoSuchAlgorithmException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível gerar uma senha cripotografado!", "Erro!", 2);
        }
        
        //setando o valor da senha criptografada no modelo   
        umModeloUsuario.setRf(rfUsuario);
        umModeloUsuario.setSenha(senha);        

        //verificando se a senha passada bate com a senha do banco
        if (umControleUsuario.logarUsuario(umModeloUsuario))
        {
            umGravarLog.gravarLog("acesso ao Sistema com senha pessoal");
            nivelAcessoUsuario = umModeloUsuario.getNivelacesso();
            nomeUsuarioLogado  = umModeloUsuario.getNome();
            codigoUsuario      = umModeloUsuario.getCodigo();
            
            F_PRINCIPAL frm = new F_PRINCIPAL();
            frm.setVisible(true);
            dispose();

        } else {
            txtSenha.setText(null);
            txtSenha.requestFocus();
            btnLogar.setEnabled(false);

            JOptionPane.showMessageDialog(null, "Senha inválida, tente novamente!", "Você errou a senha!", 2);
            if (Tentativas <= 2) {
                Tentativas++;
            } else {
                JOptionPane.showMessageDialog(null, "Desculpe mas você não tem permissão para acessar o Sistema!", "Você excedeu o limite de tentativas!", 3);
                System.exit(0);
            }

        }
    }
    private void btnLogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogarActionPerformed
       autenticar();
    }//GEN-LAST:event_btnLogarActionPerformed

    private boolean meuAcessoInicial(){
        if(umMetodo.tabelaEstaVazia("tblusuarios"))
        {
            acessoInicial = true;            
            if(acessoInicial)
            {               
                return true;
            }                      
        }   
        return false;
    }
    
    private void autenticarUsuario(){
        /*Verifica primeiramente se trata-se de um acesso inicial ou seja com banco de dados vazio, inicio de tudo, caso negativo verifica se o usuario esta cadastrado para usar o sistema*/
        meuAcessoInicial();
        
        if(acessoInicial)
        {
            JOptionPane.showMessageDialog(null, "Olá, seja  bem  vindo  ao  Systarefas  este  é seu  primeiro\n acesso ao Sistema, cadastre seu usuário para continuar!", "Primeiro acesso detectado!", 2);
            cci.gravarConfiguracoesInciais();
            
            F_ACESSOINICIAL frm = new F_ACESSOINICIAL(this,true);
            frm.setVisible(true); 
            acessoInicial=false;
            confIni=false;           
            
        }else{
            //JOptionPane.showMessageDialog(null, "Tudo certo!");
        }                
        
        txtSenha.selectAll();
        
        btnLogar.setEnabled(true);
        //pesquisa por rf ou por codigo, nesse caso verifica se existe pelo login(rf), o zero significa que estou descartando o codigo
        umModeloUsuario = umControleUsuario.pesquisarUsuario(txtLogin.getText(), 0); 
        
        if( cadastrado )
        {
            senhadobanco        = umModeloUsuario.getSenha();            
            codigoUsuario       = umModeloUsuario.getCodigo();
            nomeUsuario         = umModeloUsuario.getNome();
            rfUsuario           = umModeloUsuario.getRf();
            nivelAcessoUsuario  = umModeloUsuario.getNivelacesso();   
            
            //se o login existir verifica se a senha é a senha incial pro usuário entrar com a senha pessoal
            String senhainicial = "129c171d9ac81bfc46ccb98b149a94fb";  //senha pgminfo criptografada
            
            if (senhainicial.equals(senhadobanco)) 
            {
                //abre formulario para entrada de nova senha
                F_NOVASENHA frm = new F_NOVASENHA(this,true);
                frm.setVisible(true);
                
                umModeloUsuario.setCodigo(codigoUsuario);                
                umModeloUsuario.setSenha(novaSenha);      
                umControleUsuario.alterarSenhaUsuario(umModeloUsuario);                 
                
            }
            
        }else{
          if( Tentativas <= 2 )
          {
            //caso o usuário não seja cadastrado ou não esteja ativo isso verificado atraves do login(rf) digitado            
            txtLogin.setText(null);
            txtLogin.setEnabled(true);
            txtLogin.requestFocus();
            JOptionPane.showMessageDialog(null, "Ops, usuário não cadastrado ou inativo!", "Usuário não existe!", 2);
            Tentativas++;     
          }else{
              JOptionPane.showMessageDialog(null, "Desculpe mas você não tem permissão para acessar o Sistema!", "Você excedeu o limite de tentativas!", 3);
              System.exit(0);
          }
        }
    }
    
    private void validarLoginUsuario(){
        if (!umaValidacao.loginValido(txtLogin.getText())){    
            txtLogin.setText(null);
            txtLogin.requestFocus();   
            JOptionPane.showMessageDialog(null, "Digite seu RF DXXXXXX para acessar ao sistema.", "RF inválido!", JOptionPane.WARNING_MESSAGE);       
        } else {
            autenticarUsuario();            
        }      
    }
    
    private void txtSenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSenhaFocusGained
       
        if (!txtLogin.getText().equals("") || !txtLogin.getText().equals(null)){    
            txtSenha.setEditable(true);
            validarLoginUsuario();    
        } else {
             txtLogin.setText(null);
             txtLogin.requestFocus();   
             JOptionPane.showMessageDialog(null, "Digite seu RF DXXXXXX para acessar ao sistema.", "RF inválido!", JOptionPane.WARNING_MESSAGE);         
        }      
        
    }//GEN-LAST:event_txtSenhaFocusGained

    private void txtSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSenhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            validarLoginUsuario(); 
            autenticar();
            btnLogar.setEnabled(true);
        }      
    }//GEN-LAST:event_txtSenhaKeyPressed

    private void txtLoginFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLoginFocusGained
                        
        //txtLogin.setText("D631863");       
        //txtLogin.setText("D538076");   
        //txtLogin.setText("D741921");   
        
        txtLogin.selectAll();
        txtSenha.setEnabled(false);
    }//GEN-LAST:event_txtLoginFocusGained

    private void btnLogarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnLogarFocusGained
        autenticar();
    }//GEN-LAST:event_btnLogarFocusGained

    private void lblSelecionarBancoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSelecionarBancoMouseClicked
        lblSelecionarBanco.setComponentPopupMenu(popupMenu);
    }//GEN-LAST:event_lblSelecionarBancoMouseClicked

    private void txtLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLoginMouseClicked
        txtLogin.selectAll();
    }//GEN-LAST:event_txtLoginMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(confIni){          
            F_PRINCIPAL frm = new F_PRINCIPAL();
            frm.setVisible(true);
            dispose();
        } 
    }//GEN-LAST:event_formWindowOpened

    private void txtLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoginKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {            
            if ( !txtLogin.getText().isEmpty() ){    
                txtSenha.setEnabled(true);
                txtSenha.requestFocus();  
            } else {                 
                 txtLogin.requestFocus();   
                 JOptionPane.showMessageDialog(null, "Digite seu RF DXXXXXX para acessar ao sistema.", "RF inválido!", JOptionPane.WARNING_MESSAGE);         
            }      
            
        }
    }//GEN-LAST:event_txtLoginKeyPressed

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
            java.util.logging.Logger.getLogger(F_LOGIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_LOGIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_LOGIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_LOGIN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new F_LOGIN().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogar;
    private javax.swing.JPanel jpLogin;
    private javax.swing.JLabel lblSelecionarBanco;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JPasswordField txtSenha;
    // End of variables declaration//GEN-END:variables
}
