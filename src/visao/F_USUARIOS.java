package visao;

import biblioteca.Biblioteca;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import conexao.ConnConexao;
import biblioteca.ModeloTabela;
import controle.CtrlUsuario;
import controle.ControleListaUsuarios;
import Dao.DAOUsuario;
import biblioteca.CampoLimitadoParaRF;
import biblioteca.MetodosPublicos;
import static biblioteca.VariaveisPublicas.codigoSecao;
import static biblioteca.VariaveisPublicas.codigoSelecionado;
import static biblioteca.VariaveisPublicas.nivelAcessoUsuario;
import static biblioteca.VariaveisPublicas.nomeRelatorio;
import static biblioteca.VariaveisPublicas.nomeCliente;
import static biblioteca.VariaveisPublicas.rfCliente;
import static biblioteca.VariaveisPublicas.nomeSecao;
import static biblioteca.VariaveisPublicas.cadastrando;
import static biblioteca.VariaveisPublicas.contador;
import static biblioteca.VariaveisPublicas.entidadeInativa;
import static biblioteca.VariaveisPublicas.idUsuarioReativado;
import static biblioteca.VariaveisPublicas.visualizandoInativos;
import controle.Criptografia;
import java.awt.event.KeyEvent;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;
import modelo.Usuario;
import static biblioteca.VariaveisPublicas.tabela;
import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.status;
import static biblioteca.VariaveisPublicas.tabela_da_lista;
import static biblioteca.VariaveisPublicas.totalRegs;
import controle.ControleGravarLog;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

public class F_USUARIOS extends javax.swing.JFrame {

    ConnConexao conexao                   = new ConnConexao();
    Biblioteca umabiblio                  = new Biblioteca();
    Usuario umModUsuario                  = new Usuario();
    CtrlUsuario CtrUsuario                = new CtrlUsuario();
    ControleListaUsuarios umCtrLista      = new ControleListaUsuarios();
    DAOUsuario usuarioDAO                 = new DAOUsuario();
    Criptografia umaCriptografia          = new Criptografia();
    ControleGravarLog umGravarLog         = new ControleGravarLog();
    MetodosPublicos umMetodo              = new MetodosPublicos();
    
    Boolean clicouNaTabela,reiniciouSenha = false;
    String secao, rf, nome, sNomeSecao, strNivelAcesso, sNomeAcesso, nAcesso, obs  = "";    
    int codigo, idSecaoRegSel, ind, nivelAcesso, qdeRegs, codigoRegSelecionado = 0;
    boolean gravando;  //controla no botão gravar entre gravar novo registro e gravar alteração de um registro
    
    String sqlDefault = "select u.*, s.nome as secao from tblusuarios u, tblsecoes s where s.codigo = u.secaoid "
                      + "and u.status='ATIVO' order by u.nome";
    String sqlInativos = "select u.*, s.nome as secao from tblusuarios u, tblsecoes s where s.codigo = u.secaoid "
                       + "and u.status='INATIVO' order by u.nome";
    String sqlVazia = "select * from tblusuarios where codigo = 0";

    public F_USUARIOS() {
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
        
        //configuracoes dos edits 
        umabiblio.configurarCamposTextos(txtPESQUISA);
        umabiblio.configurarCamposTextos(txtCODIGO);
        umabiblio.configurarCamposTextos(txtSECAO); 
        umabiblio.configurarCamposTextos(txtNOME); 
        txtRF.setDocument(new CampoLimitadoParaRF(7));
        txtRF.setFont(new Font("TimesRoman", Font.BOLD, 14));   
                
       //configuração dos botões
       umabiblio.configurarBotoes(btnNovo);
       umabiblio.configurarBotoes(btnEditar);
       umabiblio.configurarBotoes(btnGravar);
       umabiblio.configurarBotoes(btnImprimir);
       umabiblio.configurarBotoes(btnVoltar);
       umabiblio.configurarBotoes(btnSair);
       umabiblio.configurarBotoes(btnReiniciarSenha);
       jTabela.setFont(new Font("Arial", Font.BOLD, 12)); 
       txtOBS.setFont(new Font("TimesRoman", Font.BOLD, 12));  
       txtCODIGO.setForeground(Color.red);
       txtCODIGO.setEditable(false);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBoxDados = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        txtNOME = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<String>();
        jLabel2 = new javax.swing.JLabel();
        txtCODIGO = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmbNivelAcesso = new javax.swing.JComboBox<String>();
        jLabel6 = new javax.swing.JLabel();
        txtRF = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtOBS = new javax.swing.JTextField();
        txtSECAO = new javax.swing.JTextField();
        jBoxBotoes = new javax.swing.JLayeredPane();
        btnGravar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnReiniciarSenha = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabela = new javax.swing.JTable();
        jBoxPesquisar = new javax.swing.JLayeredPane();
        txtPESQUISA = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jBoxDados.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxDados.setName("panelDados"); // NOI18N

        jLabel1.setText("NOME");

        txtNOME.setForeground(new java.awt.Color(51, 51, 255));
        txtNOME.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNOMEKeyPressed(evt);
            }
        });

        jLabel3.setText("CÓDIGO");

        cmbStatus.setForeground(new java.awt.Color(51, 51, 255));
        cmbStatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbStatus.setEnabled(false);

        jLabel2.setText("STATUS");

        txtCODIGO.setForeground(new java.awt.Color(51, 51, 255));

        jLabel4.setText("SEÇÃO");

        jLabel5.setText("RF");

        cmbNivelAcesso.setForeground(new java.awt.Color(51, 51, 255));
        cmbNivelAcesso.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbNivelAcesso.setEnabled(false);

        jLabel6.setText("NIVEL ACESSO");

        txtRF.setForeground(new java.awt.Color(51, 51, 255));
        txtRF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRFFocusGained(evt);
            }
        });
        txtRF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRFKeyPressed(evt);
            }
        });

        jLabel8.setText("OBSERVAÇÕES");

        txtOBS.setForeground(new java.awt.Color(51, 51, 255));

        txtSECAO.setForeground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout jBoxDadosLayout = new javax.swing.GroupLayout(jBoxDados);
        jBoxDados.setLayout(jBoxDadosLayout);
        jBoxDadosLayout.setHorizontalGroup(
            jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8)
                            .addComponent(txtOBS, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jBoxDadosLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cmbNivelAcesso, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCODIGO))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jBoxDadosLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 365, Short.MAX_VALUE))
                            .addComponent(txtNOME))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtRF, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtSECAO, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jBoxDadosLayout.setVerticalGroup(
            jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSECAO, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbNivelAcesso, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jBoxDadosLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNOME, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jBoxDadosLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtOBS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jBoxDados.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtNOME, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(cmbStatus, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtCODIGO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(cmbNivelAcesso, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtRF, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtOBS, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtSECAO, javax.swing.JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(jBoxDados);
        jBoxDados.setBounds(10, 80, 830, 140);

        jBoxBotoes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_gravar.jpg"))); // NOI18N
        btnGravar.setText("Gravar");
        btnGravar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });

        btnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_inicio.gif"))); // NOI18N
        btnVoltar.setText("Voltar");
        btnVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
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

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/users16.jpg"))); // NOI18N
        btnNovo.setText("Novo");
        btnNovo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_blocoNotas.gif"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_imprimir.gif"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnReiniciarSenha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_limpar.gif"))); // NOI18N
        btnReiniciarSenha.setText("Reiniciar Senha");
        btnReiniciarSenha.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReiniciarSenha.setEnabled(false);
        btnReiniciarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReiniciarSenhaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jBoxBotoesLayout = new javax.swing.GroupLayout(jBoxBotoes);
        jBoxBotoes.setLayout(jBoxBotoesLayout);
        jBoxBotoesLayout.setHorizontalGroup(
            jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReiniciarSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jBoxBotoesLayout.setVerticalGroup(
            jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnReiniciarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNovo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jBoxBotoes.setLayer(btnGravar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnVoltar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnSair, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnNovo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnEditar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnImprimir, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnReiniciarSenha, javax.swing.JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(jBoxBotoes);
        jBoxBotoes.setBounds(10, 230, 830, 67);

        jTabela.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTabela);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(10, 300, 830, 270);

        jBoxPesquisar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxPesquisar.setName("panelDados"); // NOI18N

        txtPESQUISA.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtPESQUISA.setToolTipText("Digite INATIVOS para imprimir todos os registos com status inativatdos");
        txtPESQUISA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPESQUISAKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPESQUISAKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jBoxPesquisarLayout = new javax.swing.GroupLayout(jBoxPesquisar);
        jBoxPesquisar.setLayout(jBoxPesquisarLayout);
        jBoxPesquisarLayout.setHorizontalGroup(
            jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxPesquisarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtPESQUISA, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
                .addContainerGap())
        );
        jBoxPesquisarLayout.setVerticalGroup(
            jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxPesquisarLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(txtPESQUISA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jBoxPesquisar.setLayer(txtPESQUISA, javax.swing.JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(jBoxPesquisar);
        jBoxPesquisar.setBounds(10, 10, 830, 60);

        setSize(new java.awt.Dimension(859, 612));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void mostrarTitulo()
    {
        if(status.equals("ATIVO"))
        {
            PreencherTabela(sqlDefault);   //abre o formulario mostrando todos os registro cadastrados na tabela ATIVOS 
            this.setTitle(umabiblio.mostrarTituloDoFormulario());
        }else if(status.equals("INATIVO")){
            PreencherTabela(sqlInativos);  ////abre o formulario mostrando todos os registro cadastrados na tabela INATIVOS 
            this.setTitle(umabiblio.mostrarTituloDoFormularioParaInativos());
        }
    }               
    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        //populando os combobox
        if(umabiblio.permissaoLiberada()){
            if(!umabiblio.tabelaVazia("tblclientes"))
            {
                popularComboNivelAcesso();
                popularComboStatus();
                //umabiblio.PreencherCombo(cmbSecao, "tblsecoes", "nome");
                //controlando os botoes
                HabilitarDesabilitarBotoes(false);
                btnReiniciarSenha.setEnabled(false);
                btnVoltar.setText("Cancelar");
                umabiblio.limparTodosCampos(jBoxDados);
                txtPESQUISA.setText(null);
                txtOBS  .requestFocus();            
                //txtRF    .setEditable(true);
                txtOBS   .setEditable(true);
                gravando    = true;       
                cadastrando = true;       
                txtCODIGO.setText(String.valueOf(umabiblio.mostrarProximoCodigo(tabela)));
                PreencherTabela(sqlVazia); 

                //abre lista de servidores
                tabela_da_lista = "TBLCLIENTES";
                F_LISTAPADRAO frm = new F_LISTAPADRAO(new javax.swing.JFrame(), true);
                frm.setVisible(true);

                 //passando os dados das variaveis para os edits somente se o usuário não for cadastrado
                if (!umMetodo.usuarioCadastrado(nomeCliente)){             
                    txtNOME.setText(nomeCliente);
                    txtRF.setText(umMetodo.retornaRFparaGravarUsuario(rfCliente));
                    txtSECAO.setText(nomeSecao);                
                }else{
                    Leitura();                 
                }            
            }else{
                JOptionPane.showMessageDialog(null, "Esta ação exige que a tabela de Clientes não esteja vazia, saia e cadastre clientes primeiro!","Tabela Clientes vazia",2);
                btnSair.requestFocus();
            }
        }
                
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        if (gravando) {
            gravarNovoRegistro();
        } else {
            gravarEdicaoRegistro();
        }
          Leitura();

    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        Leitura();
    }//GEN-LAST:event_btnVoltarActionPerformed
    
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if(umabiblio.permissaoLiberada()){
            Edicao(); 
            //controla apresentacao dos edits   
            txtOBS .setEditable(true);
            txtOBS.requestFocus();
            
            //limpa a como nivel de acesso
            cmbNivelAcesso.removeAllItems();
            //popular a combo nivel de acesso com todos os acessos possiveis
            cmbNivelAcesso.addItem("USUÁRIO");
            cmbNivelAcesso.addItem("ADMINISTRADOR");
            
            if(nivelAcessoUsuario == 1){
                cmbNivelAcesso.addItem("SISTEMA");
            }
            
            //seta o nivel de acesso atual do usuario
            cmbNivelAcesso.getModel().setSelectedItem(sNomeAcesso);

            //popular a combo status com opcoes ATIVO pra manter inalterado e INATIVO caso deseje inativar
            cmbStatus.removeAllItems();
            cmbStatus.addItem("ATIVO");
            cmbStatus.addItem("INATIVO");
            if(codigoRegSelecionado == 1){
                cmbStatus.setEnabled(false);
                cmbNivelAcesso.setEnabled(false);
            }
            PreencherTabela(sqlVazia); 
        }
        btnReiniciarSenha.setEnabled(false);
    }//GEN-LAST:event_btnEditarActionPerformed
    
     public String MostrarSecaoSelecionada(int idSecao)
    {
        //utilizada quando o usuario clicar em um registro da tabela
        conexao.conectar();
        sql="select * from tblsecoes where codigo ="+idSecao;    
        conexao.ExecutarPesquisaSQL(sql);              
        try 
        {
            if (conexao.rs.next())
            {
                conexao.rs.first();
                sNomeSecao = conexao.rs.getString("nome");
                
            };               
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar seção!\nErro: " + ex.getMessage());
            return null;
        }finally{
             conexao.desconectar();
        }
        return sNomeSecao;
    }
    private String verificarNivelAcesso(int nivel)
    {
        switch (nivelAcesso) 
        {
            case 1:
                cmbNivelAcesso.removeAllItems();
                cmbNivelAcesso.addItem("SISTEMA");
                return "SISTEMA";
            case 2:
                cmbNivelAcesso.removeAllItems();
                cmbNivelAcesso.addItem("ADMINISTRADOR");
                return "ADMINISTRADOR";
            case 3:
                cmbNivelAcesso.removeAllItems();
                cmbNivelAcesso.addItem("USUÁRIO");
                return "USUÁRIO";
            default:
                break;
        }
        
         return null;
    }
    
    private void mostrarDados()
    {
        //AO CLICAR EM UM REGISTRO DA TABELA MOSTRAR OS DADOS NOS EDITS        
        if (nivelAcessoUsuario < 3)
        {
            btnReiniciarSenha.setEnabled(true);
        }       

        codigoRegSelecionado = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0);
        codigoSelecionado = codigoRegSelecionado;
        
        //seta o nivel de acesso do usuario ao clicar na tabela e mostra a seção do mesmo
        sql = "SELECT * FROM tblusuarios WHERE codigo=" + codigoSelecionado + "";
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        try
        {
            conexao.rs.first();
            txtCODIGO.setText(String.valueOf(conexao.rs.getInt("codigo")));
            txtNOME.setText(conexao.rs.getString("nome"));
            txtRF.setText(conexao.rs.getString("rf"));            
            txtOBS.setText(conexao.rs.getString("obs"));
            nivelAcesso   = conexao.rs.getInt("nivelacesso");
            
            //mostro o status
            cmbStatus.removeAllItems();
            cmbStatus.addItem(conexao.rs.getString("status"));
            
            idSecaoRegSel = conexao.rs.getInt("secaoid");
            txtSECAO.setText(umMetodo.getStringPassandoCodigo("tblsecoes","nome",idSecaoRegSel));

            //mostrar a seção do usuario selecionado, pesquisando atraves do id da seção
            MostrarSecaoSelecionada(idSecaoRegSel);

            //mostra o nivel de acesso do usuario selecionado
            sNomeAcesso = verificarNivelAcesso(nivelAcesso);
            
            status = cmbStatus.getSelectedItem().toString();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar selecionar o usuário!\nERRO:" + ex.getMessage());
        } finally {
            conexao.desconectar();
        }     

        //controla apresentacao dos edits
        if(cmbStatus.getSelectedItem().toString().equals("ATIVO"))
        { 
            btnReiniciarSenha.setEnabled(true);
        }else{ 
            btnReiniciarSenha.setEnabled(false);
        }
        
        txtPESQUISA.setEditable(false);
        txtCODIGO  .setEnabled(true);
        txtCODIGO  .setEditable(false);
        txtNOME    .setEnabled(true);
        txtNOME    .setEditable(false);
        txtRF      .setEnabled(true);
        txtRF      .setEditable(false);    
        txtOBS     .setEnabled(true);
        txtOBS     .setEditable(false);
        txtSECAO   .setEnabled(true);
        txtSECAO   .setEditable(false);
        
    }
    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        mostrarDados();
        btnEditar      .setEnabled(true);
        btnGravar      .setEnabled(false);
        btnNovo        .setEnabled(false);
        btnImprimir    .setEnabled(true); //assim poderá imprimir o registro selecionado
        btnVoltar      .setEnabled(true);
        btnSair        .setEnabled(false);  
        cmbStatus      .setEnabled(true);
        cmbNivelAcesso .setEnabled(true);
        clicouNaTabela = true;
                        
    }//GEN-LAST:event_jTabelaMouseClicked

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        if(status.equals("INATIVO")) 
        {  
           
            contador++;
            if(clicouNaTabela)
            {
                  nomeRelatorio = "relusuarioselecionado";
            }else{
                if(status.equals("ATIVO"))
                  {
                    nomeRelatorio = "relusuarios";
                  }else{
                     nomeRelatorio = "relusuariosInativos";
                  }  

            } 
              entidadeInativa = ("INATIVOS"); //DEFINE SE IMPRIMIRA ATIVOS OU INATIVOS-> DIGITE INATIVOS NA PESQUISA

              F_IMPRESSAO frm = new F_IMPRESSAO();
              frm.setVisible(true);  
             
        }
        else{
             contador++;
                if(clicouNaTabela)
                {
                      nomeRelatorio = "relusuarioselecionado";
                }else{
                    if(status.equals("ATIVO"))
                      {
                        nomeRelatorio = "relusuarios";
                      }else{
                         nomeRelatorio = "relusuariosInativos";
                      }  

                } 
                  entidadeInativa = txtPESQUISA.getText(); //DEFINE SE IMPRIMIRA ATIVOS OU INATIVOS-> DIGITE INATIVOS NA PESQUISA

                  F_IMPRESSAO frm = new F_IMPRESSAO();
                  frm.setVisible(true); 
        }
        
        btnVoltar.setEnabled(true);
        btnNovo.setEnabled(false);
        btnReiniciarSenha.setEnabled(false);
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
       //preenche a tabela e seta o primeiro registro se tiver registros cadastrados
        
        conexao.conectar();
        String sql = "select * from tblusuarios where status='ATIVO' order by nome";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {   
                //selecionando a primeira linha somente se tiver registros
                jTabela.addRowSelectionInterval(0, 0);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher o combo de seções!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }
       
    }//GEN-LAST:event_formWindowOpened

    public void ReiniciarSenha() 
    {
        int zerarsenha = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja reiniciar a senha do usuário?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (zerarsenha == JOptionPane.YES_OPTION) {
            int codigo = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0);
            umModUsuario.setSenha("129c171d9ac81bfc46ccb98b149a94fb");  //pgminfo criptografada com MD5
            umModUsuario.setCodigo(codigo);      
            CtrUsuario.reiniciarSenhaUsuario(umModUsuario);
            
            btnReiniciarSenha.setEnabled(false);
            btnVoltar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnSair.setEnabled(true);
            btnNovo.setEnabled(true);
            btnImprimir.setEnabled(true);
            reiniciouSenha = true;
           
        } else {
            btnReiniciarSenha.setEnabled(false);
            btnVoltar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnSair.setEnabled(true);
            btnNovo.setEnabled(true);
            btnImprimir.setEnabled(true);

        }
    }

    private void btnReiniciarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReiniciarSenhaActionPerformed
        //JOptionPane.showMessageDialog(null, nivelAcessoUsuario);
        if (nivelAcessoUsuario > 2) {
            JOptionPane.showMessageDialog(this, "Você não tem permissão para este procedimento!", "Sem permissão!", 2);
            btnReiniciarSenha.setEnabled(false);
        } else {
            if(txtRF.getText().equals("D631863") && nivelAcessoUsuario > 1){
                JOptionPane.showMessageDialog(this, "Você não tem permissão para este procedimento!", "Sem permissão!", 2);
                btnReiniciarSenha.setEnabled(false);
            }else{            
               ReiniciarSenha();
               if(reiniciouSenha)
                  JOptionPane.showMessageDialog(null, "A senha do usuário foi reiniciada com sucessos!", "Reiniciando Senha do Usuário",2);
               
            }
        }
        reiniciouSenha = false;
    }//GEN-LAST:event_btnReiniciarSenhaActionPerformed

    private void txtPESQUISAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyPressed
        //se teclar enter estando dentro da pesquisa limpar a pesquisa
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
          txtPESQUISA.setText(null);          
      }
        
    }//GEN-LAST:event_txtPESQUISAKeyPressed
    private void filtrarPorDigitacao(String pPesq)   
    {        
        //filtrando por digitação pelos trez campos nome rf ou secao
        if(status.equals("INATIVO"))
            PreencherTabela("select u.*, s.nome as secao from tblusuarios u, tblsecoes s where (u.nome like '%"+pPesq+"%'"+" "
                      + "OR u.rf like '%"+pPesq+"%'"+" OR s.nome like '%"+pPesq+"%'"+") and s.codigo = u.secaoid "
                      + "and u.status='INATIVO' order by u.nome");
        else
        {
            PreencherTabela("select u.*, s.nome as secao from tblusuarios u, tblsecoes s where (u.nome like '%"+pPesq+"%'"+" "
                      + "OR u.rf like '%"+pPesq+"%'"+" OR s.nome like '%"+pPesq+"%'"+") and s.codigo = u.secaoid "
                      + "and u.status='ATIVO' order by u.nome");
        }
        this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);
        btnNovo.setEnabled(false);
        btnVoltar.setEnabled(true);
    }
    private void txtPESQUISAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyReleased
        //filtrar o nome conforme o usuario for digitando        
        filtrarPorDigitacao(txtPESQUISA.getText());                                 
    }//GEN-LAST:event_txtPESQUISAKeyReleased

    private void txtNOMEKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNOMEKeyPressed
        //se teclar enter estando dentro do txtNOME vá para o campo do rf
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
          txtRF.requestFocus();          
        }
    }//GEN-LAST:event_txtNOMEKeyPressed

    private void txtRFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRFFocusGained
        txtRF.selectAll();
    }//GEN-LAST:event_txtRFFocusGained

    private void txtRFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRFKeyPressed
         //se teclar enter estando dentro do txtNOME vá para o campo do rf
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
          txtOBS.requestFocus();          
        }
    }//GEN-LAST:event_txtRFKeyPressed

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
            java.util.logging.Logger.getLogger(F_USUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_USUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_USUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_USUARIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new F_USUARIOS().setVisible(true);

            }
        });
    }
   
    private void popularComboNivelAcesso(){
        cmbNivelAcesso.removeAllItems();
        cmbNivelAcesso.addItem("USUÁRIO");
        cmbNivelAcesso.addItem("ADMINISTRADOR");
    }
    private void popularComboStatus(){
        cmbStatus.removeAllItems();
        cmbStatus.addItem("ATIVO");
    }

    private void gravarNovoRegistro() 
    {
        //se digitou algo nos campos nome e rf        
        if (txtNOME.getText().length() > 0 && txtRF.getText().length() > 0) {
            try {
                //setando os valores dos edits

                String senha = umaCriptografia.Criptografar("pgminfo");
                //JOptionPane.showMessageDialog(rootPane, senha);
                String nomeSecao = txtSECAO.getText();
                nome = txtNOME.getText();
                rf   = txtRF.getText();
                obs  = txtOBS.getText(); 
                
                //passando o nivel de acesso
                int nivelacesso = cmbNivelAcesso.getSelectedIndex();
                if (nivelacesso == 0) {
                    nivelacesso = 3;
                } else {
                    nivelacesso = 2;
                }                

                umModUsuario.setNome(nome);
                umModUsuario.setRf(rf);
                umModUsuario.setSenha(senha);
                umModUsuario.setSecaoid(codigoSecao);
                umModUsuario.setNivelacesso(nivelacesso);
                umModUsuario.setStatus("ATIVO");
                umModUsuario.setObs(obs);

                if (gravando) {
                    if (!usuarioDAO.RegistroDuplicado(umModUsuario)) //se nao estiver duplicado libera a gravaçao do registro
                    {
                        CtrUsuario.salvarUsuario(umModUsuario);
                        umGravarLog.gravarLog("cadastro de "+umModUsuario.getNome());
                    }
                }
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(F_USUARIOS.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos para continuar!","Atenção um dos campos esta vazio",2); //se nao digitou nada no campo nome da seção
            txtNOME.requestFocus(); //foco no campo secao
        }
        btnVoltar.setText("Voltar");        
        
    }

    private void gravarEdicaoRegistro()
    {
        //setando o que estiver selecionado no combobox status(ATIVO OU INATIVO)
        String status = cmbStatus.getSelectedItem().toString();  //passando a string ATIVO ou INATIVO
        nome          = txtNOME.getText();
        codigo        = Integer.parseInt(txtCODIGO.getText());
        rf            = txtRF.getText();
        obs           = txtOBS.getText(); 
        
        //recebe o codigo da seção selecionada atraves do retorno da função buscarCodigo onde passamos o nome da seçao
        int idSecao = usuarioDAO.buscarCodigo(txtSECAO.getText());

        if (cmbNivelAcesso.getSelectedItem().equals("USUÁRIO")) {
            nivelAcesso = 3;
        } else if (cmbNivelAcesso.getSelectedItem().equals("ADMINISTRADOR")) {
            nivelAcesso = 2;
        } else if (cmbNivelAcesso.getSelectedItem().equals("SISTEMA")) {
            nivelAcesso = 1;
        }
        
        umModUsuario.setNome(nome);
        umModUsuario.setRf(rf);        
        umModUsuario.setNivelacesso(nivelAcesso);
        umModUsuario.setStatus(status);
        umModUsuario.setObs(obs);
        umModUsuario.setCodigo(codigo);
        
        if(visualizandoInativos){        
            tabela_da_lista   = "TBLSECOES";
            JOptionPane.showMessageDialog(null, "Atenção selecione uma seção para o usuário ora reativado!", "Reativando usuário!", 2);
            F_LISTAPADRAO frm = new F_LISTAPADRAO(this,true);
            frm.setVisible(true);
            umModUsuario.setSecaoid(codigoSecao);
            //atualizar tb na tblclientes
            idUsuarioReativado = umMetodo.getCodigoPassandoString("tblclientes", "nome", nome);
            umMetodo.alterarSecaoClienteSeForAlteradaSecaoDoUsuario(codigoSecao, idUsuarioReativado);
        }else{
           umModUsuario.setSecaoid(idSecao); 
        }                
        
        CtrUsuario.atualizarUsuario(umModUsuario);
        umGravarLog.gravarLog("atualizacao no cadastro de "+umModUsuario.getNome());
        
        //REATIVAR ESTE USUARIO TB NA TABELA DE CLIENTE/COLABORADOR
        idUsuarioReativado = umMetodo.getCodigoPassandoString("tblclientes", "nome", nome);
        umMetodo.reativarClienteSeForUsuarioReativado(idUsuarioReativado);
        
        //SE INATIVAR O USUARIO E FOR O UNICO NA TELA A MESMA DEVE SER FECHADA CASO CONTRARIO NAO FECHAR
        if(visualizandoInativos && !umMetodo.tabelaTemInativos(tabela)){
            visualizandoInativos = false;
            dispose();
        }else{
            Leitura(); 
        }      
    }

    private void Edicao()
    {
        //metodo para quando usuario clicar em Editar
        boolean Habilitar = true;
        Component[]c      = null;
        gravando          = false; 
        
        btnNovo          .setEnabled(!Habilitar);
        btnImprimir      .setEnabled(!Habilitar);
        btnSair          .setEnabled(!Habilitar);
        btnGravar        .setEnabled(Habilitar);
        btnEditar        .setEnabled(!Habilitar);
        btnVoltar        .setEnabled(Habilitar);    
        btnVoltar.setText("Cancelar");
                
        //mostra o titulo do formulario de acordo com o status
        mostrarTitulo();
        
        //desabilitando os edits para edicao
        c = jBoxDados.getComponents();
        for(int i=0; i<c.length; i++)
        {
            c[i].setEnabled(Habilitar);  
        }
        txtCODIGO.setEditable(false);
        txtNOME.requestFocus();
        //cmbStatus.setSelectedIndex(0);
                
        //mostrando o titulo com qde de registros cadastrados
        this.setTitle(umabiblio.mostrarTituloDoFormulario());
                          
        //habilitando a pesquisa  e preenchendo a tabela se tiver registros
         c = jBoxPesquisar.getComponents();
         for(int i=0; i<c.length; i++)
         {
            c[i].setEnabled(!Habilitar);
         }      
    }
    
    public void Leitura()
    {
        //formatacao inicial dos botoes ao abrir o formulario
        boolean Habilitar = true;
        Component[]c      = null;
        gravando          = false;
        clicouNaTabela    = false;
        
        txtPESQUISA.setEditable(true);
               
        btnNovo          .setEnabled(Habilitar);
        btnImprimir      .setEnabled(Habilitar);
        btnSair          .setEnabled(Habilitar);
        btnGravar        .setEnabled(!Habilitar);
        btnEditar        .setEnabled(!Habilitar);
        btnVoltar        .setEnabled(!Habilitar);      
                
        //mostra o titulo do formulario de acordo com o status        
        mostrarTitulo();
        
        //desabilitando os edits para edicao
        c = jBoxDados.getComponents();
        for(int i=0; i<c.length; i++)
        {
            c[i].setEnabled(!Habilitar);  
            umabiblio.limparTodosCampos(this);
            txtPESQUISA.requestFocus();
        }
        
        //pulando de campo com enter, nao usei porque esse procedimento desabilita o metodo de enter pra apagar o campo de pesquisa
        //umabiblio.passaCamposComEnter(this);
        
        //limpando os combos         
        cmbStatus       .setSelectedIndex(-1);
        cmbNivelAcesso  .setSelectedIndex(-1);                        
                                
        //habilitando a pesquisa  e preenchendo a tabela se tiver registros
         c = jBoxPesquisar.getComponents();
         for(int i=0; i<c.length; i++)
         {
            c[i].setEnabled(Habilitar);
         }      
    }
    
     public void HabilitarDesabilitarBotoes(boolean Habilitar)
    {
        //ações para quando clicar em cada botão
        Component[]c = null;
        
        btnNovo          .setEnabled(Habilitar);
        btnGravar        .setEnabled(!Habilitar);
        btnEditar        .setEnabled(Habilitar);
        btnVoltar        .setEnabled(!Habilitar);
        btnImprimir      .setEnabled(Habilitar);
        btnSair          .setEnabled(Habilitar);
        btnReiniciarSenha.setEnabled(!Habilitar);
                
        //habilitando os edits para edicao
        c = jBoxDados.getComponents();
        for(int i=0; i<c.length; i++)
            c[i].setEnabled(!Habilitar);
        
        //desabilitando a pesquisa enquanto cadastra
        c = jBoxPesquisar.getComponents();
        for(int i=0; i<c.length; i++)
            c[i].setEnabled(Habilitar);
        
    }

    public void PreencherTabela(String sql) {
        conexao.conectar();

        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Nome", "RF", "Seção"};
        conexao.ExecutarPesquisaSQL(sql);
         try 
            {     
                 conexao.ExecutarPesquisaSQL(sql); 
                 while (conexao.rs.next())
                 {   
                    dados.add(new Object[]{
                        conexao.rs.getInt("codigo"),
                        conexao.rs.getString("nome"),
                        conexao.rs.getString("rf"),
                        conexao.rs.getString("secao")
                    });
                    totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
                };

                ModeloTabela modelo = new ModeloTabela(dados, Colunas);
                jTabela.setModel(modelo);
                //define tamanho das colunas
                jTabela.getColumnModel().getColumn(0).setPreferredWidth(80);  //define o tamanho da coluna
                jTabela.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
                jTabela.getColumnModel().getColumn(1).setPreferredWidth(490);
                jTabela.getColumnModel().getColumn(1).setResizable(false);
                jTabela.getColumnModel().getColumn(2).setPreferredWidth(80);  //define o tamanho da coluna
                jTabela.getColumnModel().getColumn(2).setResizable(false);    //nao será possivel redimencionar a coluna 
                jTabela.getColumnModel().getColumn(3).setPreferredWidth(168);
                jTabela.getColumnModel().getColumn(3).setResizable(false);
                //define propriedades da tabela
                jTabela.getTableHeader().setReorderingAllowed(false);        //nao podera ser reorganizada
                jTabela.setAutoResizeMode(jTabela.AUTO_RESIZE_OFF);          //nao será possivel redimencionar a tabela
                jTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha 
                
        } catch (SQLException ex) {
            //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
            JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnReiniciarSenha;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<String> cmbNivelAcesso;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLayeredPane jBoxBotoes;
    private javax.swing.JLayeredPane jBoxDados;
    private javax.swing.JLayeredPane jBoxPesquisar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabela;
    private javax.swing.JTextField txtCODIGO;
    private javax.swing.JTextField txtNOME;
    private javax.swing.JTextField txtOBS;
    private javax.swing.JTextField txtPESQUISA;
    private javax.swing.JTextField txtRF;
    private javax.swing.JTextField txtSECAO;
    // End of variables declaration//GEN-END:variables

}
