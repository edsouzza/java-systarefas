package visao;

import Dao.DAOPatrimonio;
import Dao.DAOIpsDisponiveis;
import biblioteca.Biblioteca;
import biblioteca.CampoLimitadoParaCHAPA;
import biblioteca.CampoLimitadoParaIP;
import biblioteca.ModeloTabela;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.cejur_ou_pgm;
import controle.ControleListaPatrimonios;
import controle.CtrlPatrimonio;
import static biblioteca.VariaveisPublicas.sql;
import controle.CtrlCliente;
import controle.CtrlIPsDisponiveis;
import static biblioteca.VariaveisPublicas.totalRegs;
import static biblioteca.VariaveisPublicas.codigoCliente;
import static biblioteca.VariaveisPublicas.codigoRegSelecionado;
import static biblioteca.VariaveisPublicas.nomeCliente;
import static biblioteca.VariaveisPublicas.codigoSecao;
import static biblioteca.VariaveisPublicas.dataDoDia;
import static biblioteca.VariaveisPublicas.isImpressora;
import static biblioteca.VariaveisPublicas.isMicro;
import static biblioteca.VariaveisPublicas.isMonitor;
import static biblioteca.VariaveisPublicas.nomeDepartamento;
import static biblioteca.VariaveisPublicas.contador;
import static biblioteca.VariaveisPublicas.nomeSecao;
import static biblioteca.VariaveisPublicas.selecionouCliente;
import static biblioteca.VariaveisPublicas.tabela;
import controle.ControleGravarLog;
import controle.CtrlSecoes;
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.Cliente;
import modelo.Patrimonio;
import modelo.Secao;
import modelo.IPDisponivel;

public class F_EDITARMODELOS extends javax.swing.JFrame {
    
    ConnConexao conexao  = new ConnConexao();
    Biblioteca          umaBiblio                = new Biblioteca();    
    //modelos
    Patrimonio          umModPatrimonio          = new Patrimonio();
    Cliente             umModeloCliente          = new Cliente();
    IPDisponivel        umModeloIPDisponivel     = new IPDisponivel();
    Secao               umModeloSecao            = new Secao();
    
    //controles
    CtrlCliente         umControleCliente        = new CtrlCliente();
    CtrlPatrimonio      umControlePatrimonio     = new CtrlPatrimonio();
    CtrlIPsDisponiveis  umControleIPDisponivel   = new CtrlIPsDisponiveis();
    CtrlSecoes          umControleSecao          = new CtrlSecoes();
    
    ControleGravarLog   umGravarLog              = new ControleGravarLog();
    
    //DAO
    DAOPatrimonio       umPatrimonioDAO          = new DAOPatrimonio();
    DAOIpsDisponiveis   umIPDisponivelDAO        = new DAOIpsDisponiveis();
    
    //Controle de Listas
    ControleListaPatrimonios umCtrLista          = new ControleListaPatrimonios();
    
    String descricaoDeReativacao,motivoReativacao,patrimonio, situacao, nomeCli, nomeTipo, nomeESTACAOOLD, numIPOLD, STATUSIPOLD, nomeClienteOLD, motivoInativacao, estacao, obs, serie, chapa, paramPesquisa, nomeColuna, nomeEstacao, ip, observacaoDeInativacao, tipo  = null; 
    int codEstacaoParaEditar,codigo, idClienteRegSel, ind, idSecao, numeroColuna, idModelo,tipoid, controleMostraDescricao, idCodigoIPDisponivel, idCodigoEstacaoDisponivel = 0;
    boolean escolheuModelo,reativando,gravando,transferindo,semIP, alterouIP, alterouEstacao, editando, cadastrando, flagImprimiu, listouClientesParaEdicao, alterouStatus, bEncontrou, clicouNaTabela, filtrou, naoMicro, clicouInativos, filtrouClicou, selecionouItem;  //controla no botão gravar entre gravar novo registro e gravar alteração de um registro    
    String sqlDefaultATIVOS   = "select p.*, c.nome as cliente, s.nome as secao, t.*, m.* from tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t where p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.status = 'ATIVO' ORDER BY p.codigo desc";
    String sqlDefaultINATIVOS = "select p.*, c.nome as cliente, s.nome as secao, t.*, m.* from tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t where p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.status = 'INATIVO' ORDER BY p.codigo desc";
    String sqlVazia           = "select * from tblpatrimonios where codigo = 0";
    String sqlComboTipo       = "select tipo from tbltipos";
    
    public F_EDITARMODELOS() {
        initComponents();
        Leitura();
        setResizable(false);   //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        this.setTitle(this.mostrarTituloDoFormulario());

        // Colocando enter para pular de campo 
        HashSet conj = new HashSet(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);      
               
        //configuracoes dos edits 
        umaBiblio.configurarCamposTextos(txtCODIGO);
        umaBiblio.configurarCamposTextos(txtSERIE);
        umaBiblio.configurarCamposTextos(txtIP);
        umaBiblio.configurarCamposTextos(txtCHAPA);        
        umaBiblio.configurarCamposTextos(txtESTACAO);
        umaBiblio.configurarCamposTextos(txtCLIENTE);
        umaBiblio.configurarCamposTextos(txtTIPO);
        txtDESCRICAO.setFont(new Font("TimesRoman", Font.BOLD, 12));
        txtOBSERVACOES.setFont(new Font("TimesRoman", Font.BOLD, 12));
        txtOBSERVACOES.setForeground(Color.blue);        
        txtIP.setDocument(new CampoLimitadoParaIP());
        txtCHAPA.setDocument(new CampoLimitadoParaCHAPA());
        txtCODIGO.setForeground(Color.red);        
        cmbMODELOS.setFont(new Font("TimesRoman", Font.BOLD, 12));
        
        //configuração dos botões
        umaBiblio.configurarBotoes(btnEditar);
        umaBiblio.configurarBotoes(btnGravar);
        umaBiblio.configurarBotoes(btnSair);
        umaBiblio.configurarBotoes(btnPesquisar);
        
        //preenchendo a combo dos tipos
        //umaBiblio.PreencherComboVariandoTipo(cmbTIPO, sqlComboTipo, "tipo");

        jTabelaATIVOS.setFont(new Font("TimesRoman", Font.BOLD, 12));
        jTabelaATIVOS.setForeground(Color.blue);
        txtCODIGO.setForeground(Color.red);
        txtCLIENTE.setForeground(Color.red);
        txtESTACAO.setForeground(Color.red);

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

        panelPrincipal = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabelaATIVOS = new javax.swing.JTable();
        btnGravar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        lblMODELO = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDESCRICAO = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        txtSERIE = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cmbSTATUS = new javax.swing.JComboBox<String>();
        jLabel3 = new javax.swing.JLabel();
        txtCODIGO = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtCHAPA = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCLIENTE = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtESTACAO = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();
        txtIP = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cmbMODELOS = new javax.swing.JComboBox<String>();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtOBSERVACOES = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        txtTIPO = new javax.swing.JTextField();
        lblMODELO1 = new javax.swing.JLabel();
        cmbNOVOMODELO = new javax.swing.JComboBox<String>();
        btnLimpar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelPrincipal.setPreferredSize(new java.awt.Dimension(1024, 733));

        jTabbedPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabbedPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane2MouseClicked(evt);
            }
        });

        jTabelaATIVOS.setGridColor(new java.awt.Color(255, 255, 255));
        jTabelaATIVOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaATIVOSMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTabelaATIVOS);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 855, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("ATIVOS", jPanel5);

        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_gravar.jpg"))); // NOI18N
        btnGravar.setText("Gravar");
        btnGravar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGravar.setMaximumSize(new java.awt.Dimension(77, 25));
        btnGravar.setMinimumSize(new java.awt.Dimension(77, 25));
        btnGravar.setPreferredSize(new java.awt.Dimension(77, 25));
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_blocoNotas.gif"))); // NOI18N
        btnEditar.setText("Editar Modelo");
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setPreferredSize(new java.awt.Dimension(77, 25));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sair.gif"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSair.setPreferredSize(new java.awt.Dimension(77, 25));
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        lblMODELO.setText("MODELO ATUAL");

        txtDESCRICAO.setEditable(false);
        txtDESCRICAO.setColumns(20);
        txtDESCRICAO.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        txtDESCRICAO.setRows(5);
        jScrollPane1.setViewportView(txtDESCRICAO);

        jLabel6.setText("TIPO");

        txtSERIE.setEditable(false);
        txtSERIE.setForeground(new java.awt.Color(51, 51, 255));

        jLabel2.setText("EDITAR STATUS");

        cmbSTATUS.setForeground(new java.awt.Color(51, 51, 255));
        cmbSTATUS.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ATIVO" }));
        cmbSTATUS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbSTATUS.setEnabled(false);

        jLabel3.setText("CÓDIGO");

        txtCODIGO.setForeground(new java.awt.Color(51, 51, 255));

        jLabel1.setText("CHAPA");

        txtCHAPA.setEditable(false);
        txtCHAPA.setForeground(new java.awt.Color(51, 51, 255));

        jLabel4.setText("SERIE");

        txtCLIENTE.setEditable(false);
        txtCLIENTE.setForeground(new java.awt.Color(51, 51, 255));

        jLabel7.setText("ESTAÇÃO");

        txtESTACAO.setEditable(false);
        txtESTACAO.setForeground(new java.awt.Color(51, 51, 255));
        txtESTACAO.setToolTipText("Campo gerado automaticamente no cadastro");

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_pesquisa.gif"))); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.setToolTipText("Perquisar por série, chapa, estação, ip obs: somente patrimonios ativos");
        btnPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPesquisar.setPreferredSize(new java.awt.Dimension(77, 25));
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        txtIP.setEditable(false);
        txtIP.setForeground(new java.awt.Color(51, 51, 255));
        txtIP.setText("0");
        txtIP.setEnabled(false);

        jLabel5.setText("IP");

        jLabel11.setText("USUÁRIO");

        cmbMODELOS.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        cmbMODELOS.setForeground(new java.awt.Color(51, 51, 255));
        cmbMODELOS.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<ESCOLHA O MODELO>", " " }));
        cmbMODELOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbMODELOS.setEnabled(false);

        jLabel12.setText("OBSERVAÇÕES");

        txtOBSERVACOES.setEditable(false);
        txtOBSERVACOES.setColumns(20);
        txtOBSERVACOES.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        txtOBSERVACOES.setRows(5);
        jScrollPane4.setViewportView(txtOBSERVACOES);

        jLabel13.setText("DESCRIÇÃO");

        txtTIPO.setEditable(false);
        txtTIPO.setForeground(new java.awt.Color(51, 51, 255));

        lblMODELO1.setText("ESCOLHA O NOVO MODELO");

        cmbNOVOMODELO.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        cmbNOVOMODELO.setForeground(new java.awt.Color(51, 51, 255));
        cmbNOVOMODELO.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<ESCOLHA O MODELO>", " " }));
        cmbNOVOMODELO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbNOVOMODELO.setEnabled(false);

        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_limpar.gif"))); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.setToolTipText("Limpar pesquisa");
        btnLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpar.setEnabled(false);
        btnLimpar.setPreferredSize(new java.awt.Dimension(77, 25));
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane4)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtESTACAO))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCHAPA, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtSERIE, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbSTATUS, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2)))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtTIPO, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(txtCLIENTE)))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMODELO)
                                    .addComponent(cmbMODELOS, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMODELO1)
                                    .addComponent(cmbNOVOMODELO, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                                        .addGap(27, 27, 27)
                                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtCLIENTE, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTIPO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel3)
                                    .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel11)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtESTACAO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtSERIE, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtCHAPA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbSTATUS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMODELO)
                        .addGap(2, 2, 2)
                        .addComponent(cmbMODELOS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblMODELO1)
                        .addGap(2, 2, 2)
                        .addComponent(cmbNOVOMODELO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(208, 208, 208))
        );

        getContentPane().add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 880, 740));

        setSize(new java.awt.Dimension(899, 777));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
   
    public int qdePatrimoniosCadastrados(String tabela)
    {        
        //total de patrimonios cadastrados e ativos (micros,monitores,impressoras)
        conexao.conectar();
        sql = "select count(codigo) as total from "+tabela+"";
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next())
               return conexao.rs.getInt("total");
            else
                return 0; 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return 0;
        }finally{
            conexao.desconectar();
        }                 
    }
    public int qdePatrimoniosATIVOS(String tabela)
    {        
        //total de patrimonios cadastrados e ativos (micros,monitores,impressoras)
        conexao.conectar();
        sql = "select count(codigo) as total from "+tabela+" where status = 'ATIVO'";
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next())
               return conexao.rs.getInt("total");
            else
                return 0; 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return 0;
        }finally{
            conexao.desconectar();
        }                 
    }
    public int qdePatrimoniosINATIVOS(String tabela)
    {        
        //total de patrimonios cadastrados inativos (micros,monitores,impressoras)
        conexao.conectar();
        sql = "select count(codigo) as total from "+tabela+" where status = 'INATIVO'";
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next())
               return conexao.rs.getInt("total");
            else
                return 0; 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return 0;
        }finally{
            conexao.desconectar();
        }                 
    }
    
    public final String mostrarTituloDoFormulario()
    {
        int qdePatrimonios  =  qdePatrimoniosCadastrados("tblPatrimonios");
        int qdeAtivos       =  qdePatrimoniosATIVOS("tblPatrimonios");
        int qdeInativos     =  qdePatrimoniosINATIVOS("tblPatrimonios");
       
        if(qdePatrimonios == 1){
           return "Equipamentos de Informática : "+qdePatrimonios+" patrimônio cadastrado"; 
        }else{
           return "Equipamentos de Informática : "+qdePatrimonios+" patrimônios cadastrados -> "+qdeAtivos+" ativos e "+qdeInativos+" inativos";    
        } 
        
    }
    
    private void gravarInclusaoRegistro()
    {
        ip                  = txtIP.getText();
        serie               = txtSERIE.getText();
        nomeEstacao         = txtESTACAO.getText();    
        chapa               = txtCHAPA.getText();    
        int codPatrimonio   = Integer.valueOf(txtCODIGO.getText());  
        obs                 = txtOBSERVACOES.getText(); 
        idModelo            = umaBiblio.buscarCodigoGenerico("tblmodelos", "modelo", cmbMODELOS.getSelectedItem().toString());
        tipoid              = umaBiblio.buscarCodigoGenerico("tbltipos", "tipo", txtTIPO.getText());
        tipo                = txtTIPO.getText();    
        
        //visualizando os dados passados nas variaveis
//        JOptionPane.showMessageDialog(null, "IP...:"+ip+"\n SÉRIE...:"+serie+"\n ESTACAO...:"+nomeEstacao+"\n OBS...:"+obs+"\n CHAPA...:"+chapa+
//                "\n TIPOID...:"+tipoid+"\n COD.SECAO..:"+codigoSecao+"\n COD.CLIENTE..:"+codigoCliente+"\n ID.MODELO..:"+idModelo);
        
        umModPatrimonio.setChapa(chapa);
        umModPatrimonio.setSerie(serie);
        umModPatrimonio.setTipoid(tipoid);
        umModPatrimonio.setIp(ip);
        umModPatrimonio.setSecaoid(codigoSecao);
        umModPatrimonio.setClienteid(codigoCliente);
        umModPatrimonio.setModeloid(idModelo);
        umModPatrimonio.setObservacoes(obs);
        umModPatrimonio.setEstacao(nomeEstacao);
        umModPatrimonio.setStatus("ATIVO");
        umModPatrimonio.setDatacad(dataDoDia);
        
        //se estiver cadastrando um micro deverá verificar se os campos ip/serie/estacao estao preenchidos
        if(tipo.equals("MICRO")){
                if(txtIP.getText().length() > 0 && txtSERIE.getText().length() > 0 && txtESTACAO.getText().length() > 0){
                    //verificando duplicidade para gravar o registro 
                    if(!umControlePatrimonio.patrimonioDuplicado(chapa,serie,nomeEstacao))
                    {                        
                        umControlePatrimonio.salvarPatrimonio(umModPatrimonio);
                        umGravarLog.gravarLog("cadastro de um(a) "+txtTIPO.getText()+" serie "+umModPatrimonio.getSerie());
                    }            
                }else{
                    if(txtIP.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "O campo ip é de preenchimento obrigatório para cadastro de micros!","Atenção operação cancelada, campo ip vazio!",2);
                    }else  if(txtSERIE.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "O campo série é de preenchimento obrigatório cadastro de micros!","Atenção operação cancelada, campo série vazio!",2);
                    }else  if(txtESTACAO.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "O campo estação é de preenchimento obrigatório cadastro de micros!","Atenção operação cancelada, campo estação vazio!",2);
                    }                    
                }
        }else if(tipo.equals("MONITOR")){
                //se estiver cadastrando um monitor deverá verificar se os campos serie estao preenchidos
                if(txtSERIE.getText().length() > 0 ){
                    //verificando duplicidade para gravar o registro 
                    if(!umControlePatrimonio.patrimonioDuplicadoMonitor(codPatrimonio,serie))
                    {
                        umControlePatrimonio.salvarPatrimonio(umModPatrimonio);
                        umGravarLog.gravarLog("cadastro de um(a) "+txtTIPO.getText()+" serie "+umModPatrimonio.getSerie());
                    }            
                }else{
                    if(txtSERIE.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "O campo série é de preenchimento obrigatório para cadastro de monitores!","Atenção operação cancelada, campo série vazio!",2);
                    }
                }
        }else if(tipo.equals("IMPRESSORA")){
                //se estiver cadastrando uma impressora deverá verificar se os campos serie estao preenchidos
                if(txtIP.getText().length() > 0 && txtSERIE.getText().length() > 0 ){
                    //verificando duplicidade para gravar o registro 
                    if(!umControlePatrimonio.patrimonioDuplicadoImpressora(codPatrimonio,serie,ip))
                    {
                        /*atualizando o status do IP que foi reutilizado para INDISPONIVEL*/
                        boolean ipEncontrado = umaBiblio.registroEncontrado("tblipsdisponiveis", "ip", ip);   
                        //JOptionPane.showMessageDialog(null, "IP ...:"+ip+"\nSTATUS DO REGISTRO ...:"+ipEncontrado);
                        
                        if(ipEncontrado){
                            int idCodigoIPEncontrado = umaBiblio.buscarCodigoGenerico("tblipsdisponiveis", "ip", ip);
                            //JOptionPane.showMessageDialog(null, "CODIGO DO IP ...: "+idCodigoIPEncontrado+ "\n IP CADASTRADO...: "+ip);
                            umModeloIPDisponivel.setCodigo(idCodigoIPEncontrado);
                            umModeloIPDisponivel.setIp(ip);                   //mantendo o mesmo IP
                            umModeloIPDisponivel.setStatus("INDISPONIVEL");   //setando o novo status do IP para INDISPONIVEL
                            umControleIPDisponivel.atualizarIPDisponivel(umModeloIPDisponivel);
                        }
                        
                        //gravando um patrimonio como impressora
                        umControlePatrimonio.salvarPatrimonio(umModPatrimonio);     
                                               
                        //gravando o LOG
                        umGravarLog.gravarLog("cadastro de um(a) "+txtTIPO.getText()+" serie "+umModPatrimonio.getSerie());
                    }            
                }else{
                    if(txtIP.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "O campo ip é de preenchimento obrigatório para cadastro de impressoras!","Atenção operação cancelada, campo ip vazio!",2);
                    }else  if(txtSERIE.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "O campo série é de preenchimento obrigatório cadastro de impressoras!","Atenção operação cancelada, campo série vazio!",2);
                    }
                }
               
        }
        
        PreencherTabelaATIVOS(sqlDefaultATIVOS);
        codigoCliente            = 0;
        cadastrando              = false;
        listouClientesParaEdicao = false;
        editando                 = false;
        tipo                     = "";
        nomeEstacao              = "";
        serie                    = "";
       
    }
    
public void verificarStatus()
{        
        if(transferindo){
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date dataDia      = dataDoDia; 
            motivoInativacao  = JOptionPane.showInputDialog(null, "Entre com o motivo da transferência!", "Transferência de Patrimônio", 2); 

            //solicita o motivo até que o mesmo seja digitado corretamente
            while (motivoInativacao == null || motivoInativacao.equals("")) 
            {
                JOptionPane.showMessageDialog(this, "Digite um motivo válido e confirme!");  
                motivoInativacao = JOptionPane.showInputDialog(null, "Entre com o motivo da transferência!", "Transferência de Patrimônio", 2);
            }
            //quando gravar string motivoInativacao em observacoes colocar o motivo na linha abaixo da data com \n
            motivoInativacao       = "Trasnferido em "+df.format(dataDia)+"\nMotivo : "+motivoInativacao;
            if(txtOBSERVACOES.getText().length() == 0){
                //se nao tiver nenhum texto de observacao coloque apenas o motivo
                observacaoDeInativacao = motivoInativacao;  
            }else{
                //se já tiver algum texto de observacao quebrar linha e acrescentar o motivo nas observações do patrimonio 
                observacaoDeInativacao = txtOBSERVACOES.getText()+"\n"+motivoInativacao;  
            }
        }else{
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date dataDia      = dataDoDia; 
            motivoInativacao  = JOptionPane.showInputDialog(null, "Entre com o motivo da inativação!", "Inativação de Patrimônio", 2); 

            //solicita o motivo até que o mesmo seja digitado corretamente
            while (motivoInativacao == null || motivoInativacao.equals("")) 
            {
                JOptionPane.showMessageDialog(this, "Digite um motivo válido e confirme!");  
                motivoInativacao = JOptionPane.showInputDialog(null, "Entre com o motivo da inativação!", "Inativação de Patrimônio", 2);
            }
            //quando gravar string motivoInativacao em observacoes colocar o motivo na linha abaixo da data com \n
            motivoInativacao       = "Inativado em "+df.format(dataDia)+"\nMotivo : "+motivoInativacao;
            if(txtOBSERVACOES.getText().length() == 0){
                //se nao tiver nenhum texto de observacao coloque apenas o motivo
                observacaoDeInativacao = motivoInativacao;  
            }else{
                //se já tiver algum texto de observacao quebrar linha e acrescentar o motivo nas observações do patrimonio 
                observacaoDeInativacao = txtOBSERVACOES.getText()+"\n"+motivoInativacao;  
            }
        }

    cmbSTATUS.setSelectedIndex(-1); 

}

private void disponibilizarIPImpressoraTransferida(){
    //disponibiliza o ip da impressora caso seja inativada ou transferida
    
    if(isImpressora)                                            //(txtTIPO.getText().equals("IMPRESSORA")) 
    {
        //se na tabela de IPSDISPONIVEIS já existir o IP que esta sendo inativado altere o status para DISPONIVEL caso contrario crie 
        //e grave um novo registro com o IP em questão com o mesmo status
        if(umaBiblio.registroEncontrado("tblipsdisponiveis", "ip", numIPOLD))
        {
            //executar alteração e disponibilizar na tabela de IPs o IP que foi alterado aqui no formulario de patrimonios alterando o status para DISPONIVEL
            int codIpAntigoNoBanco = umaBiblio.buscarCodigoGenerico("tblipsdisponiveis", "ip", numIPOLD); 
            //JOptionPane.showMessageDialog(null, "codigo do IP atual que será alterado...: "+" "+codIpAntigoNoBanco+"\n IP...atual...:"+numIPOLD);
            umModeloIPDisponivel.setCodigo(codIpAntigoNoBanco); 
            umModeloIPDisponivel.setIp(numIPOLD);           
            umModeloIPDisponivel.setStatus("DISPONIVEL");   
            umControleIPDisponivel.atualizarIPDisponivel(umModeloIPDisponivel);
            
        }else{
            umModeloIPDisponivel.setIp(numIPOLD);           
            umModeloIPDisponivel.setStatus("DISPONIVEL");   
            umControleIPDisponivel.salvarIPDisponivel(umModeloIPDisponivel);
            
        }
    }
    
}

private void gravarEdicaoRegistro() 
{
    //tratando os codigos da seção e do cliente(usuario) se fizer alteração no cliente
    codigo            = Integer.valueOf(txtCODIGO.getText());  
    estacao           = txtESTACAO.getText();
    obs               = txtOBSERVACOES.getText(); 
    ip                = txtIP.getText();
    serie             = txtSERIE.getText();
    chapa             = txtCHAPA.getText();
    tipoid            = umaBiblio.buscarCodigoGenerico("tbltipos","tipo",txtTIPO.getText());
    tipo              = txtTIPO.getText();
    
    //setando codigo do modelo
    idModelo  = umaBiblio.buscarCodigoGenerico("tblmodelos","modelo",cmbNOVOMODELO.getSelectedItem().toString());
    umModPatrimonio.setModeloid(idModelo);
    //JOptionPane.showMessageDialog(null, "CODIGO DO MODELO...: "+ idModelo);
    
    //identifica qual registro será atualizado
    umModPatrimonio.setCodigo(codigo);
    
    //gravando as alteraçoes do registro se nao tiver duplicidade de ip com outro registro 
    if(tipo.equals("MICRO")){        
            umControlePatrimonio.atualizarModeloPatrimonio(umModPatrimonio);        
            umGravarLog.gravarLog("atualizacao no cadastro de um(a) "+txtTIPO.getText()+" serie "+umModPatrimonio.getSerie());       
    }
    
    //gravando as alteraçoes do registro se nao tiver duplicidade de serie ou chapa com outro registro  
    if(tipo.equals("MONITOR")){        
            umControlePatrimonio.atualizarModeloPatrimonio(umModPatrimonio);        
            umGravarLog.gravarLog("atualizacao no cadastro de um(a) "+txtTIPO.getText()+" serie "+umModPatrimonio.getSerie());       
    }
    
    if(tipo.equals("IMPRESSORA")){       
            umControlePatrimonio.atualizarModeloPatrimonio(umModPatrimonio);        
            umGravarLog.gravarLog("atualizacao no cadastro de um(a) "+txtTIPO.getText()+" serie "+umModPatrimonio.getSerie());       
    }

    //Atualiza os grids apos a alterações
    txtCHAPA.setEditable(false);
    cadastrando               = false;
    editando                  = false;
    listouClientesParaEdicao  = false;
    alterouStatus             = false;
    cmbSTATUS.setSelectedIndex(-1);
    cmbNOVOMODELO.setSelectedIndex(-1);
    cmbNOVOMODELO.setEnabled(false);
      
 }

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed
       
    private void Leitura()
    {
        if (umaBiblio.tabelaVazia(tabela)) {
            DesabilitarConsulta();
        } else {
            HabilitarConsulta();
            PreencherTabelaATIVOS(sqlDefaultATIVOS);
        }
        umaBiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS         
        btnGravar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnSair.setEnabled(true);

        txtCLIENTE.setEditable(false);
        txtCODIGO.setEditable(false);
        txtCHAPA.setEditable(false);
        txtESTACAO.setEditable(false);
        txtESTACAO.setEnabled(true);
        txtCODIGO.setEditable(false);
        txtSERIE.setEditable(false);
        txtOBSERVACOES.setEditable(false);
        txtIP.setEditable(false);
        txtTIPO.setEnabled(true);
        txtTIPO.setEditable(false);

    }
    public void HabilitarConsulta() {
        txtTIPO.setEnabled(true);
        btnPesquisar.setEnabled(true);

    }

    public void DesabilitarConsulta() {
        btnPesquisar.setEnabled(false);
        cmbMODELOS.setEnabled(false);

    }
    
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if(umaBiblio.permissaoLiberada()){
            editando  = true;   
            btnLimpar.setEnabled(true);
            tipo = txtTIPO.getText();

            PreencherTabelaATIVOS(sqlVazia);
            this.setTitle("Editando "+txtTIPO.getText()+" "+txtSERIE.getText());
            nomeESTACAOOLD = txtESTACAO.getText();
            cadastrando                 = false;
            alterouStatus               = false;
            listouClientesParaEdicao    = false;
            codigoCliente               = 0;

            btnGravar.setEnabled(true);
            btnGravar.setText("Gravar");
            btnEditar.setEnabled(false);    

            cmbNOVOMODELO.setEnabled(true); 
            popularComboBoxNovosModelos();
        }
        
    }//GEN-LAST:event_btnEditarActionPerformed
        
    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
    if(umaBiblio.permissaoLiberada()){
        gravarEdicaoRegistro();            
       
        btnEditar.setEnabled(false);
        btnSair.setEnabled(true);
        btnGravar.setEnabled(false);
        cmbMODELOS.setEnabled(false);
        cmbMODELOS.removeAllItems();
        txtCHAPA.setEditable(false);        
        txtOBSERVACOES.setEditable(false);
        txtESTACAO.setEnabled(true);
        txtESTACAO.setEditable(false);
        txtSERIE.setEditable(false);
        txtIP.setEnabled(true);
        txtIP.setEditable(false);
        txtIP.setEnabled(true);
        txtTIPO.setEnabled(false);
        txtCODIGO.requestFocus();
        umaBiblio.limparTodosCampos(this);
        cmbSTATUS.setEnabled(false);
        controleMostraDescricao = 0;
        nomeSecao      = null;
        nomeCliente    = null;
        semIP          = false;
        alterouIP      = false;
        isMicro        = false;
        isMonitor      = false;
        isImpressora   = false;
        editando       = false;
        cadastrando    = false;
        reativando     = false;
        alterouEstacao = false;
        alterouStatus  = false;
        contador       = 0;
    }
        
    }//GEN-LAST:event_btnGravarActionPerformed
    
    private void selecionarPrimeiroRegistro(){
        //seleciona o primeiro registro da tabeala de ATIVOS
        conexao.conectar();
        String sql = "select * from tblpatrimonios where status='ATIVO'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {   //selecionando a primeira linha somente se tiver registros
                jTabelaATIVOS.addRowSelectionInterval(0, 0);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela ATIVOS!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }
    }
    
    private boolean temRegistrosTransferidosSemNomeDeRede(){
        //verifica se tem registros que foram transferidos e não renomeados para seu devido departamento
        conexao.conectar();
        String sql = "select codigo,estacao from tblpatrimonios where estacao='SNJPGMC00' or estacao='SNJCEJURC00'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {   
                return true;
            }else{
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela ATIVOS!\nErro: " + ex.getMessage());
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    private void selecionarRegistroParaEdicao(){
        //abre a lista de registro que foram transferidos e aguardam um novo nome de rede
        F_LISTATRANSFERIDOS frm = new F_LISTATRANSFERIDOS(this, true);
        frm.setVisible(true);
        mostrarDadosRegSelecionado(codigoRegSelecionado);
        btnEditarActionPerformed(null);
        DesabilitarConsulta();
        
    }
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //na abertura do formulario verifica se tem pendencias a resolver em relacao a equipamentos transferidos e nao renomeados
        if(temRegistrosTransferidosSemNomeDeRede()){
            JOptionPane.showMessageDialog(null,"Atenção registros transferidos requerem sua atenção no que diz respeito a renomeá-los!","Registros transferidos sem nome de rede!",2); 
            selecionarRegistroParaEdicao();
        }else{
            selecionarPrimeiroRegistro();
        }
    }//GEN-LAST:event_formWindowOpened
    
    private String obterSTATUSOLD(String paramIP){
        
        conexao.conectar();
        sql = "SELECT * FROM tblipsdisponiveis WHERE ip ='"+ paramIP + "'";
        conexao.ExecutarPesquisaSQL(sql);       
        try {
            if (conexao.rs.next())
               return conexao.rs.getString("STATUS");
            else
                return ""; 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return "";
        }finally{
            conexao.desconectar();
        }  
    }
    
    
    public void mostrarDadosRegSelecionado(int codPatrimonio)
    {
        //pesquisar um patrimonio pelo numero do codigo setando os edits,nao use o next porque se clicou na tabela o registro com certeza existe
        //Obs: no caso dos inativos não precisa mostrar a seção pois não estão mais na PGM e nem atralados a uma seçao
               
        sql = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
             +"p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.codigo='" + codPatrimonio + "'";
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        try
        {
            if( conexao.rs.next())
            {
                txtCODIGO.setText(String.valueOf(conexao.rs.getInt("codigo")));
                txtSERIE.setText(conexao.rs.getString("serie"));
                txtIP.setText(conexao.rs.getString("ip"));            
                txtCHAPA.setText(conexao.rs.getString("chapa"));
                txtDESCRICAO.setText(conexao.rs.getString("descricao"));
                txtESTACAO.setText(conexao.rs.getString("estacao"));
                txtOBSERVACOES.setText(conexao.rs.getString("observacoes")); 
                txtCLIENTE.setText(conexao.rs.getString("cliente"));
                                
                //passando o tipo do registro selecionado
                txtTIPO.setText(conexao.rs.getString("tipo"));
                
                //limpando a combomodelos e inserindo a string do modelo selecionado
                cmbMODELOS.removeAllItems();
                cmbMODELOS.addItem(conexao.rs.getString("modelo"));
                
                //limpando a combostatus e inserindo a string do status do reg selecionado
                cmbSTATUS.removeAllItems();
                cmbSTATUS.addItem(conexao.rs.getString("status"));
                
                //Obtendo o status do IP para futura atualização do registro
                STATUSIPOLD = obterSTATUSOLD(conexao.rs.getString("ip"));
                
                txtDESCRICAO.setCaretPosition(0); //setando a primeira linha do JTextArea
                txtOBSERVACOES.setCaretPosition(0); //setando a primeira linha do JTextArea
                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar selecionar os dados dos registro selecionado!\nERRO:" + ex.getMessage());
        } finally {
            conexao.desconectar();
        }
        
    }
               
    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        btnLimpar.setEnabled(true);
        Pesquisar();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
         umaBiblio.limparTodosCampos(rootPane);
        if (!umaBiblio.tabelaVazia(tabela)) {
            PreencherTabelaATIVOS(sqlDefaultATIVOS);
            btnPesquisar.setEnabled(true);
        }else{
            btnPesquisar.setEnabled(false);
        }
        if(jTabbedPane2.getSelectedIndex() == 1){
            jTabbedPane2.setSelectedIndex(0);
            clicouInativos=false;
        }
        HabilitarConsulta();  
        txtSERIE.setEditable(false);
        txtOBSERVACOES.setEditable(false);
        txtIP.setEditable(false);
        txtIP.setEnabled(true);
        txtCHAPA.setEditable(false);
        txtESTACAO.setEditable(false);
        txtESTACAO.setEnabled(true);
        txtCODIGO.setEditable(false);
        btnEditar.setEnabled(false);
        btnGravar.setEnabled(false);
        btnPesquisar.setEnabled(true);
        btnSair.setEnabled(true);
        cmbMODELOS.setEnabled(false);
        cmbSTATUS.removeAllItems();
        cmbSTATUS.setEnabled(false);
        txtTIPO.setEnabled(true);
        txtTIPO.setEditable(false);
        txtCODIGO.requestFocus();
        cadastrando                 = false;
        listouClientesParaEdicao    = false;
        editando                    = false;
        clicouNaTabela              = false;
        filtrouClicou               = false;
        filtrou                     = false;
        controleMostraDescricao     = 0;
        selecionouCliente           = false;
        totalRegs                   = 0;
        contador                    = 0;
        nomeSecao                   = null;
        nomeCliente                 = null;
        this.setTitle(this.mostrarTituloDoFormulario());
        umaBiblio.LimparCombo(cmbMODELOS, "tblmodelos", "modelo");
        umaBiblio.LimparCombo(cmbNOVOMODELO, "tblmodelos", "modelo");
        btnLimpar.setEnabled(false);
        cmbNOVOMODELO.setEnabled(false);
    }//GEN-LAST:event_btnLimparActionPerformed

    private void jTabbedPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane2MouseClicked
        //se clicou na aba INATIVOS
        if (jTabbedPane2.getSelectedIndex() == 1) {
            //desabilitar as consultas
            DesabilitarConsulta();
            umaBiblio.limparTodosCampos(rootPane);
            btnEditar.setEnabled(false);
            clicouInativos=true;
            btnGravar.setText("REATIVAR");
            reativando = true;

        } else if (jTabbedPane2.getSelectedIndex() == 0) {
            clicouInativos=false;
            btnGravar.setText("GRAVAR");
            reativando = false;
        }

    }//GEN-LAST:event_jTabbedPane2MouseClicked

    private void jTabelaATIVOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaATIVOSMouseClicked
        /*passando o codigo do patrimonio para o metodo => (int) jTabelaATIVOS.getValueAt(jTabelaATIVOS.getSelectedRow(), 0)
        onde o zero é a coluna que detem o valor que se deseja nesse caso o codigo (coluna 0)*/

        codigoRegSelecionado = (int) jTabelaATIVOS.getValueAt(jTabelaATIVOS.getSelectedRow(), 0);
        mostrarDadosRegSelecionado(codigoRegSelecionado);

        txtCODIGO.setForeground(Color.red);
        txtCODIGO.requestFocus();
        nomeClienteOLD = txtCLIENTE.getText();
        nomeESTACAOOLD = txtESTACAO.getText();
        numIPOLD = txtIP.getText();
        if(cmbSTATUS.getSelectedItem().toString().equals("ATIVO")) btnEditar.setEnabled(true);
        btnPesquisar.setEnabled(false);
        btnGravar.setEnabled(false);
        DesabilitarConsulta();
        txtIP.setEnabled(true);
        txtIP.setEditable(false);
        txtTIPO.setEnabled(true);
        txtTIPO.setEditable(false);
        clicouNaTabela           = true;
        cadastrando              = false;
        contador                 = 0;
        controleMostraDescricao  = 0;
        txtDESCRICAO.setCaretPosition(0); //setando a primeira linha do JTextArea

        if(clicouNaTabela && filtrou){
            filtrouClicou = true;
        }

        if(txtTIPO.getText().equals("MONITOR")){
            isMonitor = true;
            //JOptionPane.showMessageDialog(null, "TIPO: "+txtTIPO.getText());
        }else if(txtTIPO.getText().equals("IMPRESSORA")){
            isImpressora=true;
            //JOptionPane.showMessageDialog(null, "TIPO: "+txtTIPO.getText());
        }else if(txtTIPO.getText().equals("MICRO")){
            isMicro=true;
            //JOptionPane.showMessageDialog(null, "TIPO: "+txtTIPO.getText());
        }

    }//GEN-LAST:event_jTabelaATIVOSMouseClicked
   
    public void Pesquisar()
    {    
        //pesquisa não deverá aceitar valores vazios, nulos ou zero como parâmetro
        while(paramPesquisa == null || paramPesquisa.equals("") || paramPesquisa.equals("0"))   //enquanto nao digitar um valor valido pra pesquisa não sair
        {
            paramPesquisa = JOptionPane.showInputDialog(null, "Entre com a chapa, serie, estação, ou do ip se for impressora!", "Pesquisando Patrimônio", 2);
            if (paramPesquisa == null || paramPesquisa.equals("") || paramPesquisa.equals("0"))
            {
                JOptionPane.showMessageDialog(null, "Valor inválido","Entre com um parâmetro válido",2);
            }else{    
                bEncontrou = false;
                paramPesquisa = paramPesquisa.toUpperCase();  //Esta variavel receberá um valor em letras maiusculas   
                verificarRetornoDeRegistrosNaSql(paramPesquisa);
                
                btnPesquisar.setEnabled(false);               
                DesabilitarConsulta();
            }
        }  
       paramPesquisa = null;
    }
    
    public void verificarRetornoDeRegistrosNaSql(String pParam)
    {
       /*neste metodo eu verifico qual das colunas me retorna dados e seto um numero de coluna para aquela que tiver registros
         o numero da coluna corresponde a ordem em que ela se encontra na tabela do banco de dados, exemplo: iniciando em 1 para o codigo*/
        
       String sqlIP = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
                      +"p.tipoid=t.codigo and p.clienteid=c.codigo and p.secaoid=s.codigo and p.modeloid=m.codigo and (p.ip='"+pParam+"')";         
       conexao.conectar();
       conexao.ExecutarPesquisaSQL(sqlIP); 
        try {
            if (conexao.rs.next())
            {
                numeroColuna = 3;   //refere-se ao numero da coluna IP no bd e não ao indice  
                bEncontrou = true;
                umCtrLista.lstPatrimonios.clear();  //limpa a lista logo que setar o numero da coluna
                totalRegs = conexao.rs.getRow();
                this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);
             }
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro na execução da sql!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        } 
        
       String sqlSerie = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
                        +"p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and (p.serie='"+pParam+"')";         
       conexao.conectar();
       conexao.ExecutarPesquisaSQL(sqlSerie); 
        try {
            if (conexao.rs.next())
            {
                numeroColuna = 4;   //refere-se ao numero da coluna SERIE no bd e não ao indice
                bEncontrou = true;
                umCtrLista.lstPatrimonios.clear();  //limpa a lista logo que setar o numero da coluna
                totalRegs = conexao.rs.getRow();
                this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);
            }
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro na execução da sql que verifica o retorno de registros!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        } 
        
       String sqlChapa = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s,tblmodelos m, tbltipos t WHERE "
                        + "p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and (p.chapa='"+pParam+"')";         
       conexao.conectar();
       conexao.ExecutarPesquisaSQL(sqlChapa); 
        try {
            if (conexao.rs.next())
            {
                numeroColuna = 5;   //refere-se ao numero da coluna CHAPA no bd e não ao indice
                bEncontrou = true;
                umCtrLista.lstPatrimonios.clear();  //limpa a lista logo que setar o numero da coluna
                totalRegs = conexao.rs.getRow();
                this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);
                
             }
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro na execução da sql que verifica o retorno de registros!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        } 
                      
       String sqlEstacao = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s,tblmodelos m, tbltipos t WHERE "
                           +"p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and (p.estacao='"+pParam+"')";         
       conexao.conectar();
       conexao.ExecutarPesquisaSQL(sqlEstacao); 
        try {
            if (conexao.rs.next())
            {
                numeroColuna = 6;   //refere-se ao numero da coluna ESTACAO no bd e não ao indice
                bEncontrou = true;
                umCtrLista.lstPatrimonios.clear();  //limpa a lista logo que setar o numero da coluna
                totalRegs = conexao.rs.getRow();
                this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);
             }
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro na execução da sql que verifica o retorno de registros!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        } 
        //somente para MICROS
        String sqlNomeCliente = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s,tblmodelos m, tbltipos t WHERE "
                               +"p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.tipoid=1 and (c.nome like '%"+pParam+"%')";         
       conexao.conectar();
       conexao.ExecutarPesquisaSQL(sqlNomeCliente); 
        try {
            if (conexao.rs.next())
            {
                numeroColuna = 8;   //refere-se ao numero da coluna NOME DO CLIENTE no bd e não ao indice OBS: esta pesquisa retorna somente os micros do usuario
                bEncontrou = true;
                umCtrLista.lstPatrimonios.clear();  //limpa a lista logo que setar o numero da coluna
                totalRegs = conexao.rs.getRow();
                this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);
             }
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro na execução da sql que verifica o retorno de registros!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        } 
        
        if(bEncontrou){
            mostrarDadosPesquisa(paramPesquisa);  
        }else{           
            JOptionPane.showMessageDialog(null, "Patrimônio não encontrado!"); 
            PreencherTabelaATIVOS(sqlVazia);
        }
    }
        
    public boolean mostrarDadosPesquisa(String pParam)
    {        
        //pesquisar um patrimonio pelo ip, serie, chapa ou estação setando os edits, nao usei o next porque se clicou na tabela o unico registro com certeza existe
        try
        {           
            //Recupera o numero e nome da coluna
            ResultSetMetaData meta = conexao.rs.getMetaData();    
            
            switch(numeroColuna) //Escolher a sql conforme o numero e consequentemente o nome da coluna 
            {
                case 3:   //refere-se ao numero da coluna IP no bd e não ao indice
                    sql = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
                        + "p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and (p.ip='"+pParam+"')";
                    break;
                    
                case 4:   //refere-se ao numero da coluna SERIE no bd e não ao indice
                    sql = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
                        + "p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and (p.serie='"+pParam+"')";
                    break;
                    
                case 5:   //refere-se ao numero da coluna CHAPA no bd e não ao indice
                    sql = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
                        + "p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and (p.chapa='"+pParam+"')";
                    break;                                    
                
                case 6:   //refere-se ao numero da coluna ESTACAO no bd e não ao indice
                    sql = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
                        + "p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.status = 'ATIVO' and (p.estacao='"+pParam+"')";
                    break; 
                    
                case 8:   //refere-se ao numero da coluna NOME DO CLIENTE no bd e não ao indice OBS: esta pesquisa retorna somente os micros do usuario
                    sql = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
                        + "p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.tipoid=1 and (c.nome like '%"+pParam+"%')";  
                    break; 
                default:                        
                    numeroColuna = 0;
                    
            }
               if(numeroColuna > 0)
               {
                    //preenche a tabela somente com o registro filtrado 
                    PreencherTabelaATIVOS(sql);
                    
                    //mostra os dados do registro filtrado nos edits
                    conexao.conectar();
                    conexao.ExecutarPesquisaSQL(sql); 
                    if (conexao.rs.next())
                    {  
                        //pegando o codigo pra mostrar os dados do registro filtrado pela sql
                        int codigoPatrimonio = conexao.rs.getInt("codigo");
                        mostrarDadosRegSelecionado(codigoPatrimonio);
                    }
                }
            return true;            
        } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao pesquisar o registro, para mostrar os dados no edits! " + ex.getMessage());
                return false;
        }finally{
             conexao.desconectar();
             
        }      
        
    }
    
    private void popularComboBoxNovosModelos(){
        //preenche a combo dos modelos de acordo com o tipo de equipamento que estiver sendo cadastrado
        if(editando)
        { 
            if(tipo.equals("MICRO")){  //se estiver cadastrando MICRO setar somente micros no combobox dos modelos            
                sql="Select modelo From tblModelos WHERE tipoid=1 Order by modelo";
                umaBiblio.PreencherComboVariandoTipo(cmbNOVOMODELO, sql, "modelo");
            }else if(tipo.equals("MONITOR")){  //se estiver cadastrando MONITOR setar somente monitores no combobox dos modelos
                sql="Select modelo From tblModelos WHERE tipoid=2 Order by modelo";
                umaBiblio.PreencherComboVariandoTipo(cmbNOVOMODELO, sql, "modelo");            
            }else if(tipo.equals("IMPRESSORA")){  //se estiver cadastrando IMPRESSORA setar somente impressoras no combobox dos modelos     
                sql="Select modelo From tblModelos WHERE tipoid=3 Order by modelo";
                umaBiblio.PreencherComboVariandoTipo(cmbNOVOMODELO, sql, "modelo");
            }else if(tipo.equals("SWITCH")){  //se estiver cadastrando SWITCH setar somente impressoras no combobox dos modelos     
                sql="Select modelo From tblModelos WHERE tipoid=4 Order by modelo";
                umaBiblio.PreencherComboVariandoTipo(cmbNOVOMODELO, sql, "modelo");
            }else if(tipo.equals("NOTEBOOK")){  //se estiver cadastrando NOTEBOOK setar somente impressoras no combobox dos modelos     
                sql="Select modelo From tblModelos WHERE tipoid=19 Order by modelo";
                umaBiblio.PreencherComboVariandoTipo(cmbNOVOMODELO, sql, "modelo");
            }else if(tipo.equals("SCANNER")){  //se estiver cadastrando SCANNER setar somente impressoras no combobox dos modelos     
                sql="Select modelo From tblModelos WHERE tipoid=5 Order by modelo";
                umaBiblio.PreencherComboVariandoTipo(cmbNOVOMODELO, sql, "modelo");
            }
            cmbNOVOMODELO.setSelectedIndex(-1); //primeiro item vazio obrigando a escolher um item
            contador++;
       }
       
       //System.out.println("VALOR DO CONTADOR DEPOIS DE POPULAR O COMBO MODELOS...: "+contador);
    } 
    
    private void setarNovoIPImpressora(){
        //seta um novo IP para impressora de acordo com o que estiver DISPONIVEL na tabela de IPs disponiveis
        conexao.conectar();
        conexao.ExecutarPesquisaSQL("select * from tblipsdisponiveis WHERE status='DISPONIVEL'");
        
        try {
            if (conexao.rs.next())
            {               
                conexao.rs.first();
                conexao.rs.getInt("codigo");
                conexao.rs.getString("ip");
                conexao.rs.getString("status");           
                txtIP.setText(conexao.rs.getString("ip"));
            }else{
                //caso não existam ips disponiveis quando for cadastrar uma impressora, abrir o formulario para providenciar ips
                JOptionPane.showMessageDialog(null,"Atenção no momento não existem ips disponíveis verifique!","Sem ips disponíveis!",2); 
                dispose();
                tabela = "TBLIPSDISPONIVEIS";
                F_CONSIPSDISPONIVEIS frm = new F_CONSIPSDISPONIVEIS();
                frm.setVisible(true);                
            }         

        } catch (SQLException ex) {
                //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
                JOptionPane.showMessageDialog(null, "Erro ao pesquisar IP!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }       
    }
    
    
    private void definirTipoDeCadastro(){
        txtCHAPA.selectAll();  
             
        tipo  = txtTIPO.getText();
        ip    = txtIP.getText();       
        
        
        if(cadastrando)
        {  
            mostrarNomeClienteSelecionado(); 
            

            if(txtTIPO.equals("MICRO")){        //MICRO = 0  
                txtIP.setEnabled(true);
                txtIP.setEditable(true); 
                txtESTACAO.setEnabled(true);
                txtESTACAO.setEditable(false);
                txtIP.requestFocus(); 
                isMicro=true;
            }else if(txtTIPO.equals("MONITOR")){  //MONITOR = 1 
                txtIP.setText("0");
                txtIP.setEnabled(false);
                txtCHAPA.requestFocus(); 
                txtESTACAO.setEnabled(true);
                txtESTACAO.setEditable(false);
                txtESTACAO.setText(txtTIPO.getText());
                isMonitor=true;
            }else if(txtTIPO.equals("IMPRESSORA")){  //IMPRESSORA = 2
                    setarNovoIPImpressora();
                    txtCHAPA.requestFocus();
                    txtIP.setEditable(false);            
                    numIPOLD              = txtIP.getText(); //guarda o numero do IP antes da alteração
                    idCodigoIPDisponivel  = umaBiblio.buscarCodigoGenerico("tblipsdisponiveis","ip",numIPOLD); //retorna o codigo do IP que foi alterado na tblIPsDisponiveis
                    txtESTACAO.setEnabled(true);
                    txtESTACAO.setEditable(false);
                    txtESTACAO.setText(txtTIPO.getText());
                    isImpressora=true;
            }
        }else if(editando){
            if(tipo.equals("MICRO")){             //MICRO = 0  
                isMicro=true;
            }else if(tipo.equals("MONITOR")){     //MONITOR = 1 
                isMonitor=true;
            }else if(tipo.equals("IMPRESSORA")){  //IMPRESSORA = 2
                isImpressora=true;
            }
        }
    }
    
        
    private void mostrarNomeClienteSelecionado(){
       //JOptionPane.showMessageDialog(null, "CLIENTE...: "+nomeCliente);
       if(cadastrando){
           txtCLIENTE.setText(nomeCliente);
           
       }else{
           txtCLIENTE.setText(nomeClienteOLD);
       }
       
    }
    
    private void abrirListaClientes(){
        //mostra o nome do usuario selecionado para cadastro do patrimonio, logo que sair da lista de nomes
        //tipoEquipamento = tipo;
        if (!selecionouCliente){
            
            //abre o formulario com lista de CLIENTES     
            F_LISTAPATRIMONIOS frm = new F_LISTAPATRIMONIOS(new JFrame(),true);
            frm.setVisible(true); 
            
            controleMostraDescricao=1;
            
        }
        //seta o nome do usuario escolhido no campo txtCLIENTE       
        cmbMODELOS.setEnabled(true);  
        txtTIPO.requestFocus();
        mostrarNomeClienteSelecionado();
        
    }
            
    private void transferirPatrimonio(){
        //passando os valores para o modelo de patrimonio
        //JOptionPane.showMessageDialog(null, "TIPO: "+tipo+"\nTRANSFERINDO DO DEPARTAMENTO: "+nomeDepartamento);   
       
         if(txtTIPO.getText().equals("MONITOR")){
            if(nomeDepartamento.equals("PGM")){  //da PGM -> CEJUR
                //JOptionPane.showMessageDialog(null,"TRANSFERINDO MONITOR DE "+nomeDepartamento+" PARA CEJUR"); 
                umModPatrimonio.setCodigo(Integer.parseInt(txtCODIGO.getText()));
                umModPatrimonio.setIp("0");
                umModPatrimonio.setEstacao("MONITOR"); 
                umModPatrimonio.setSerie(txtSERIE.getText());
                umModPatrimonio.setChapa(txtCHAPA.getText());
                umModPatrimonio.setClienteid(196);           //SEM USUARIO CEJUR
                umModPatrimonio.setTipoid(2);
                umModPatrimonio.setSecaoid(3);               //CEJUR
                umModPatrimonio.setStatus("ATIVO");
                idModelo  = umaBiblio.buscarCodigoGenerico("tblmodelos","modelo",cmbMODELOS.getSelectedItem().toString());
                umModPatrimonio.setModeloid(idModelo);
            }else if(nomeDepartamento.equals("CEJUR")){   //de CEJUR -> PGM
                umModPatrimonio.setCodigo(Integer.parseInt(txtCODIGO.getText()));
                umModPatrimonio.setIp("0");
                umModPatrimonio.setEstacao("MONITOR");
                umModPatrimonio.setSerie(txtSERIE.getText());
                umModPatrimonio.setChapa(txtCHAPA.getText());
                umModPatrimonio.setClienteid(245);             //SEM USUARIO PGM
                umModPatrimonio.setTipoid(2);
                umModPatrimonio.setSecaoid(28);                //PGM
                umModPatrimonio.setStatus("ATIVO");
                idModelo  = umaBiblio.buscarCodigoGenerico("tblmodelos","modelo",cmbMODELOS.getSelectedItem().toString());
                umModPatrimonio.setModeloid(idModelo);
            }
        }else if(txtTIPO.getText().equals("IMPRESSORA")){
            if(nomeDepartamento.equals("PGM")){  //da PGM -> CEJUR
                //JOptionPane.showMessageDialog(null,"TRANSFERINDO IMPRESSORA DE "+nomeDepartamento+" PARA CEJUR"); 
                umModPatrimonio.setCodigo(Integer.parseInt(txtCODIGO.getText()));
                umModPatrimonio.setIp("0");
                umModPatrimonio.setEstacao("IMPRESSORA"); 
                umModPatrimonio.setSerie(txtSERIE.getText());
                umModPatrimonio.setChapa(txtCHAPA.getText());
                umModPatrimonio.setClienteid(213);           //IMPRESSORA CEJUR
                umModPatrimonio.setTipoid(3);
                umModPatrimonio.setSecaoid(3);               //CEJUR
                umModPatrimonio.setStatus("INATIVO");
                idModelo  = umaBiblio.buscarCodigoGenerico("tblmodelos","modelo",cmbMODELOS.getSelectedItem().toString());
                umModPatrimonio.setModeloid(idModelo);
                disponibilizarIPImpressoraTransferida();
            }else if(nomeDepartamento.equals("CEJUR")){   //de CEJUR -> PGM
                umModPatrimonio.setCodigo(Integer.parseInt(txtCODIGO.getText()));
                umModPatrimonio.setIp("0");
                umModPatrimonio.setEstacao("IMPRESSORA");
                umModPatrimonio.setSerie(txtSERIE.getText());
                umModPatrimonio.setChapa(txtCHAPA.getText());
                umModPatrimonio.setClienteid(246);             //IMPRESSORA PGM
                umModPatrimonio.setTipoid(3);
                umModPatrimonio.setSecaoid(28);                //PGM
                umModPatrimonio.setStatus("INATIVO");
                idModelo  = umaBiblio.buscarCodigoGenerico("tblmodelos","modelo",cmbMODELOS.getSelectedItem().toString());
                umModPatrimonio.setModeloid(idModelo);
                disponibilizarIPImpressoraTransferida();
            }
        }
        
        verificarStatus();
        umModPatrimonio.setMotivo(motivoInativacao); 
        umModPatrimonio.setObservacoes(observacaoDeInativacao);
        
        //gravando as alterações no modelo do patrimonio
        if(umControlePatrimonio.atualizarPatrimonio(umModPatrimonio)){
            if(nomeDepartamento.equals("PGM")){
                JOptionPane.showMessageDialog(null,"O equipamento de série "+txtSERIE.getText()+" foi transferido com sucesso!"); 
            }else if(nomeDepartamento.equals("CEJUR")){
                JOptionPane.showMessageDialog(null,"O equipamento de série "+txtSERIE.getText()+" foi transferido com sucesso!"); 
            }
        }
        isMicro      = false;
        isMonitor    = false;
        isImpressora = false;
        
        
    }
            
    private void mostrarDescricao(int idMod){
        //Mostrando a descricao vinda da tabela de modelos
        sql="select descricao from tblmodelos where codigo="+idMod;
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        try {
            conexao.rs.first();
            txtDESCRICAO.setText(conexao.rs.getString("descricao"));   
            
        } catch (SQLException ex) {
                //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
                JOptionPane.showMessageDialog(null, "Erro ao tentar pesquisar descricao atraves do codigo do modelo!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        } 
        txtOBSERVACOES.requestFocus();
        txtDESCRICAO.setCaretPosition(0); //setando a primeira linha do JTextArea
        cmbMODELOS.setEnabled(false);
        txtTIPO.setEnabled(false);
        btnGravar.setEnabled(true);
        contador++;
        
    }
    
    public void filtrarPorSecao()
    {
        

    }

    public void PreencherTabelaATIVOS(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Seção", "Tipo", "Série", "Chapa", "Estação", "Usuário"};
        try 
        {  
            conexao.ExecutarPesquisaSQL(sql);
            
            while (conexao.rs.next())
            {               
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("secao"),
                    conexao.rs.getString("tipo"),
                    conexao.rs.getString("serie"),
                    conexao.rs.getString("chapa"),
                    conexao.rs.getString("estacao"),
                    conexao.rs.getString("cliente")
                });
                totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
             };                

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabelaATIVOS.setModel(modelo);
            //define tamanho das colunas
            jTabelaATIVOS.getColumnModel().getColumn(0).setPreferredWidth(50);  //define o tamanho da coluna
            jTabelaATIVOS.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaATIVOS.getColumnModel().getColumn(1).setPreferredWidth(120);
            jTabelaATIVOS.getColumnModel().getColumn(1).setResizable(false);
            jTabelaATIVOS.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTabelaATIVOS.getColumnModel().getColumn(2).setResizable(false);
            jTabelaATIVOS.getColumnModel().getColumn(3).setPreferredWidth(110);  //define o tamanho da coluna
            jTabelaATIVOS.getColumnModel().getColumn(3).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaATIVOS.getColumnModel().getColumn(4).setPreferredWidth(110);
            jTabelaATIVOS.getColumnModel().getColumn(4).setResizable(false);
            jTabelaATIVOS.getColumnModel().getColumn(5).setPreferredWidth(100);
            jTabelaATIVOS.getColumnModel().getColumn(5).setResizable(false);
            jTabelaATIVOS.getColumnModel().getColumn(6).setPreferredWidth(230);
            jTabelaATIVOS.getColumnModel().getColumn(6).setResizable(false);

            //define propriedades da tabela
            jTabelaATIVOS.getTableHeader().setReorderingAllowed(false);          //nao podera ser reorganizada
            jTabelaATIVOS.setAutoResizeMode(jTabelaATIVOS.AUTO_RESIZE_OFF);      //nao será possivel redimencionar a tabela
            jTabelaATIVOS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
        
        } catch (SQLException ex) {
                //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
                JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList da tabela ATIVOS!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbMODELOS;
    private javax.swing.JComboBox<String> cmbNOVOMODELO;
    private javax.swing.JComboBox<String> cmbSTATUS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTabelaATIVOS;
    private javax.swing.JLabel lblMODELO;
    private javax.swing.JLabel lblMODELO1;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTextField txtCHAPA;
    private javax.swing.JTextField txtCLIENTE;
    private javax.swing.JTextField txtCODIGO;
    private javax.swing.JTextArea txtDESCRICAO;
    private javax.swing.JTextField txtESTACAO;
    public javax.swing.JTextField txtIP;
    private javax.swing.JTextArea txtOBSERVACOES;
    private javax.swing.JTextField txtSERIE;
    private javax.swing.JTextField txtTIPO;
    // End of variables declaration//GEN-END:variables
}
