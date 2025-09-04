package visao;

import conexao.ConnConexao;
import Dao.DAOPatrimonio;
import Dao.DAOIpsDisponiveis;
import Dao.DAONomeEstacao;
import Dao.DAOPatriMovel;
import biblioteca.Biblioteca;
import biblioteca.MetodosPublicos;
import biblioteca.CampoTxtLimitadoPorQdeCaracteresUpperCase;
import biblioteca.CampoLimitadoParaIP;
import biblioteca.GerarNumerosAleatorios;
import biblioteca.ModeloTabela;
import controle.ControleListaPatrimonios;
import controle.CtrlPatrimonio;
import controle.CtrlCliente;
import controle.CtrlIPsDisponiveis;
import static biblioteca.VariaveisPublicas.deptobiblio;
import static biblioteca.VariaveisPublicas.totalRegs;
import static biblioteca.VariaveisPublicas.codigoCliente;
import static biblioteca.VariaveisPublicas.codigoRegSelecionado;
import static biblioteca.VariaveisPublicas.codParaHistorico;
import static biblioteca.VariaveisPublicas.serieEquipamento;
import static biblioteca.VariaveisPublicas.novonomestacao;
import static biblioteca.VariaveisPublicas.alterouNomeRede;
import static biblioteca.VariaveisPublicas.codigoSecao;
import static biblioteca.VariaveisPublicas.alterandonomestacao;
import static biblioteca.VariaveisPublicas.cadastrandoEquipamento;
import static biblioteca.VariaveisPublicas.userInformatica;
import static biblioteca.VariaveisPublicas.dataDoDia;
import static biblioteca.VariaveisPublicas.cadastrandoEstacaoForaRede;
import static biblioteca.VariaveisPublicas.editandoNomeEstacaoReativada;
import static biblioteca.VariaveisPublicas.editandoDisponiveis;
import static biblioteca.VariaveisPublicas.cadastrandonomestacao;
import static biblioteca.VariaveisPublicas.codTipoVirtual;
import static biblioteca.VariaveisPublicas.motivoInicial;
import static biblioteca.VariaveisPublicas.nomestacaosubstituida; 
import static biblioteca.VariaveisPublicas.contador;
import static biblioteca.VariaveisPublicas.editandoNomeRede;
import static biblioteca.VariaveisPublicas.editandoRegistro;
import static biblioteca.VariaveisPublicas.idDepto;
import static biblioteca.VariaveisPublicas.isDeContrato;
import static biblioteca.VariaveisPublicas.isGbit;
import static biblioteca.VariaveisPublicas.isImpressora;
import static biblioteca.VariaveisPublicas.isMicro;
import static biblioteca.VariaveisPublicas.isMonitor;
import static biblioteca.VariaveisPublicas.isNotebook;
import static biblioteca.VariaveisPublicas.isScanner;
import static biblioteca.VariaveisPublicas.isSwitch;
import static biblioteca.VariaveisPublicas.itemSelecionadoCadastro;
import static biblioteca.VariaveisPublicas.lstListaCampos;
import static biblioteca.VariaveisPublicas.naoTemModelo;
import static biblioteca.VariaveisPublicas.nomeCliente;
import static biblioteca.VariaveisPublicas.nomeDepartamento;
import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.tipoEquipamento;
import static biblioteca.VariaveisPublicas.reativou;
import static biblioteca.VariaveisPublicas.rfCliente;
import static biblioteca.VariaveisPublicas.salvandoreativado;
import static biblioteca.VariaveisPublicas.selecionouCliente;
import static biblioteca.VariaveisPublicas.tabela;
import static biblioteca.VariaveisPublicas.temContratoImpressoraAtivo;
import controle.ControleGravarLog;
import controle.CtrlNomeEstacao;
import controle.CtrlSecoes;
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
import modelo.NomeEstacao;
import relatorios.GerarExcelPatrimonios;
import relatorios.GerarRelatorios;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class F_PATRIMONIOS extends javax.swing.JFrame {
    
    public final static Date DATA_VAZIA = new Date(0);
    
    ConnConexao         conexao                  = new ConnConexao();    
    Biblioteca          umaBiblio                = new Biblioteca();
    MetodosPublicos     umMetodo                 = new MetodosPublicos();
    
    DAOPatriMovel       umPatriMovelDAO          = new DAOPatriMovel();
    
    //modelos
    Patrimonio          umModPatrimonio          = new Patrimonio();
    Cliente             umModeloCliente          = new Cliente();
    IPDisponivel        umModeloIPDisponivel     = new IPDisponivel();
    Secao               umModeloSecao            = new Secao();
    NomeEstacao         umModeloNomeEstacao      = new NomeEstacao();
    
    //controles
    CtrlCliente         umControleCliente        = new CtrlCliente();
    CtrlPatrimonio      umControlePatrimonio     = new CtrlPatrimonio();
    CtrlIPsDisponiveis  umControleIPDisponivel   = new CtrlIPsDisponiveis();
    CtrlSecoes          umControleSecao          = new CtrlSecoes();
    CtrlNomeEstacao     umControleNomeEstacao    = new CtrlNomeEstacao();
    ControleGravarLog   umGravarLog              = new ControleGravarLog();
    
    //DAO
    DAOPatrimonio       umPatrimonioDAO          = new DAOPatrimonio();
    DAONomeEstacao      umEstacaoNomeEstacaoAO   = new DAONomeEstacao();
    DAOIpsDisponiveis   umIPDisponivelDAO        = new DAOIpsDisponiveis();
    Date                dataDia                  = dataDoDia; 
    DateFormat          df                       = new SimpleDateFormat("dd/MM/yyyy"); 
    
    
    //Controle de Listas
    ControleListaPatrimonios umCtrLista          = new ControleListaPatrimonios();
    
    String descricaoDeReativacao,motivoReativacao,patrimonio, situacao, nomeCli, nomeTipo, nomeESTACAOOLD, numIPOLD, STATUSIPOLD, nomeClienteOLD, numSerieOLD, motivoInativacao, estacao, obs, serie, chapa, paramPesquisa, paramPesqCod, paramPesqIP, nomeColuna, nomeEstacao, nomeEstacaoAtual, ip, observacaoDeInativacao, tipo, contrato, nomestacaoCad, nomeInicial, nomeFinal, motivo, nomeSecao,novaObservacao,deContrato  = null; 
    int    empresaid,cont,ipesqPorCod,ipesqPorIP,codEstacaoParaEditar,codigo, idClienteRegSel, ind, idSecao, numeroColuna, idModelo,deptoid, tipoid, controleMostraDescricao, idCodigoIPDisponivel, idCodigoEstacaoDisponivel, codPatrimonio,codTipoid, contClick = 0;
    boolean entrouNovaObs, selecionouTipo,escolheuModelo,reativando,gravando,transferindo,semIP, temIP, alterouIP, alterouEstacao, editando, cadastrando, flagImprimiu, listouClientesParaEdicao, alterouStatus, bEncontrou, clicouNaTabela, filtrou, naoMicro, clicouInativos, filtrouClicou, selecionouItem,estacaoRecebeuDisponivel;      
    String sqlDefaultATIVOS   = "select p.*, c.nome as cliente, s.nome as secao, t.*, m.* from tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t where p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.status = 'ATIVO' ORDER BY p.codigo desc";
    String sqlDefaultINATIVOS = "select p.*, c.nome as cliente, s.nome as secao, t.*, m.* from tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t where p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.status = 'INATIVO' ORDER BY p.datainativacao desc";
    String sqlVazia           = "select * from tblpatrimonios where codigo = 0";
    String sqlComboTipo       = "select tipo from tbltipos";
        
    public F_PATRIMONIOS() {
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
        umaBiblio.configurarCamposTextos(txtRF);        
        umaBiblio.configurarCamposTextos(txtCHAPA);        
        umaBiblio.configurarCamposTextos(txtESTACAO);
        umaBiblio.configurarCamposTextos(txtCLIENTE);
        umaBiblio.configurarCamposTextos(txtTIPO);
        umaBiblio.configurarCamposTextos(txtCONTRATO);
        umaBiblio.configurarCamposTextos(txtPESQUISA);
        txtDESCRICAO.setFont(new Font("TimesRoman", Font.BOLD, 12));
        txtOBSERVACOES.setFont(new Font("TimesRoman", Font.BOLD, 12));
        txtOBSERVACOES.setForeground(Color.blue);        
        txtIP.setDocument(new CampoLimitadoParaIP());
        txtCHAPA.setDocument(new CampoTxtLimitadoPorQdeCaracteresUpperCase(20));
        txtSERIE.setDocument(new CampoTxtLimitadoPorQdeCaracteresUpperCase(20));
        txtCODIGO.setForeground(Color.red);        
        cmbMODELOS.setFont(new Font("TimesRoman", Font.BOLD, 12));        
        
        cmbFILTRAPORTIPO.setFont(new Font("TimesRoman", Font.BOLD, 12));
        cmbFILTRARPORSECAO.setFont(new Font("TimesRoman", Font.BOLD, 12));
        
        //configuração dos botões
        umaBiblio.configurarBotoes(btnNovo);
        umaBiblio.configurarBotoes(btnEditar);
        umaBiblio.configurarBotoes(btnGravar);
        umaBiblio.configurarBotoes(btnImprimir);
        umaBiblio.configurarBotoes(btnVoltar);
        umaBiblio.configurarBotoes(btnSair);
        umaBiblio.configurarBotoes(btnPesquisar);
        umaBiblio.configurarBotoes(btnExcel);
        umaBiblio.configurarBotoes(btnHistorico);
   
        jTabelaATIVOS.setFont(new Font("TimesRoman", Font.BOLD, 12));
        jTabelaATIVOS.setForeground(Color.blue);
        jTabelaINATIVOS.setFont(new Font("TimesRoman", Font.BOLD, 12));
        jTabelaINATIVOS.setForeground(Color.red);
        txtCODIGO.setForeground(Color.red);
        txtCLIENTE.setForeground(Color.red);
        txtRF.setForeground(Color.red);
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTabelaINATIVOS = new javax.swing.JTable();
        btnNovo = new javax.swing.JButton();
        btnGravar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        lblMODELO = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDESCRICAO = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        txtSERIE = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cmbSTATUS = new javax.swing.JComboBox<String>();
        btnFILTRAR = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtCODIGO = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cmbFILTRAPORTIPO = new javax.swing.JComboBox<String>();
        txtCHAPA = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbFILTRARPORSECAO = new javax.swing.JComboBox<String>();
        txtCLIENTE = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtESTACAO = new javax.swing.JTextField();
        btnLISTARCLIENTESPARAEDITAR = new javax.swing.JButton();
        btnPesquisar = new javax.swing.JButton();
        txtIP = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cmbMODELOS = new javax.swing.JComboBox<String>();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtOBSERVACOES = new javax.swing.JTextArea();
        txtTIPO = new javax.swing.JTextField();
        btnHistorico = new javax.swing.JButton();
        btnGerarObsAdicional = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtCONTRATO = new javax.swing.JTextField();
        txtPESQUISA = new javax.swing.JTextField();
        txtRF = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNUMESTACAO = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnCadSemRede = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        LblPesquisaPorCod = new javax.swing.JLabel();

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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 935, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("ATIVOS", jPanel5);

        jTabelaINATIVOS.setGridColor(new java.awt.Color(255, 255, 255));
        jTabelaINATIVOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaINATIVOSMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTabelaINATIVOS);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 935, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("INATIVOS", jPanel2);

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/users16.jpg"))); // NOI18N
        btnNovo.setText("Novo");
        btnNovo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

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
        btnEditar.setText("Editar");
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setPreferredSize(new java.awt.Dimension(77, 25));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_inicio.gif"))); // NOI18N
        btnVoltar.setText("Voltar");
        btnVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVoltar.setPreferredSize(new java.awt.Dimension(77, 25));
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_imprimir.gif"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImprimir.setPreferredSize(new java.awt.Dimension(77, 25));
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
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

        lblMODELO.setText("MODELO");

        txtDESCRICAO.setEditable(false);
        txtDESCRICAO.setColumns(20);
        txtDESCRICAO.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        txtDESCRICAO.setRows(5);
        txtDESCRICAO.setToolTipText("Detalhes da descrição");
        jScrollPane1.setViewportView(txtDESCRICAO);

        jLabel6.setText("TIPO");

        txtSERIE.setEditable(false);
        txtSERIE.setForeground(new java.awt.Color(51, 51, 255));
        txtSERIE.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSERIEFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSERIEFocusLost(evt);
            }
        });

        jLabel2.setText("CONTRATO");

        cmbSTATUS.setForeground(new java.awt.Color(51, 51, 255));
        cmbSTATUS.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ATIVO", "INATIVO" }));
        cmbSTATUS.setSelectedIndex(-1);
        cmbSTATUS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbSTATUS.setEnabled(false);
        cmbSTATUS.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSTATUSItemStateChanged(evt);
            }
        });

        btnFILTRAR.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnFILTRAR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_pesquisa.gif"))); // NOI18N
        btnFILTRAR.setText("Filtrar");
        btnFILTRAR.setToolTipText("Filtrar por seção ou por tipo");
        btnFILTRAR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFILTRAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFILTRARActionPerformed(evt);
            }
        });

        jLabel3.setText("CÓDIGO");

        txtCODIGO.setForeground(new java.awt.Color(51, 51, 255));
        txtCODIGO.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCODIGOFocusGained(evt);
            }
        });

        jLabel1.setText("CHAPA");

        cmbFILTRAPORTIPO.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        cmbFILTRAPORTIPO.setForeground(new java.awt.Color(51, 51, 255));
        cmbFILTRAPORTIPO.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<ESCOLHA  TIPO>", "MICRO", "MONITOR", "IMPRESSORA" }));
        cmbFILTRAPORTIPO.setToolTipText("Escolha um tipo para filtrar");
        cmbFILTRAPORTIPO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbFILTRAPORTIPO.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbFILTRAPORTIPOItemStateChanged(evt);
            }
        });

        txtCHAPA.setEditable(false);
        txtCHAPA.setForeground(new java.awt.Color(51, 51, 255));
        txtCHAPA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCHAPAFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCHAPAFocusLost(evt);
            }
        });

        jLabel4.setText("SERIE");

        cmbFILTRARPORSECAO.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        cmbFILTRARPORSECAO.setForeground(new java.awt.Color(51, 51, 255));
        cmbFILTRARPORSECAO.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<ESCOLHA A SEÇÃO>" }));
        cmbFILTRARPORSECAO.setToolTipText("Escolha uma seção para filtrar");
        cmbFILTRARPORSECAO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbFILTRARPORSECAO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbFILTRARPORSECAOMouseClicked(evt);
            }
        });
        cmbFILTRARPORSECAO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFILTRARPORSECAOActionPerformed(evt);
            }
        });

        txtCLIENTE.setEditable(false);
        txtCLIENTE.setForeground(new java.awt.Color(51, 51, 255));
        txtCLIENTE.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCLIENTEFocusGained(evt);
            }
        });

        jLabel7.setText("ESTAÇÃO");

        txtESTACAO.setEditable(false);
        txtESTACAO.setForeground(new java.awt.Color(51, 51, 255));
        txtESTACAO.setToolTipText("Campo gerado automaticamente através da escolha de um usuário");
        txtESTACAO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtESTACAOMouseClicked(evt);
            }
        });

        btnLISTARCLIENTESPARAEDITAR.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        btnLISTARCLIENTESPARAEDITAR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/users16.jpg"))); // NOI18N
        btnLISTARCLIENTESPARAEDITAR.setText("ALTERAR USUÁRIO");
        btnLISTARCLIENTESPARAEDITAR.setToolTipText("Listar clientes para alteração");
        btnLISTARCLIENTESPARAEDITAR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLISTARCLIENTESPARAEDITAR.setEnabled(false);
        btnLISTARCLIENTESPARAEDITAR.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnLISTARCLIENTESPARAEDITARFocusGained(evt);
            }
        });
        btnLISTARCLIENTESPARAEDITAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLISTARCLIENTESPARAEDITARActionPerformed(evt);
            }
        });

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_pesquisa.gif"))); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.setToolTipText("Perquisar por chapa, serie, estação, ip ou nome do colaborador");
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
        txtIP.setToolTipText("Se for Impressoras de contrato inative-a para liberar o Ip");
        txtIP.setEnabled(false);
        txtIP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtIPMouseClicked(evt);
            }
        });
        txtIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIPKeyReleased(evt);
            }
        });

        jLabel5.setText("IP");

        jLabel11.setText("USUÁRIO");

        cmbMODELOS.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        cmbMODELOS.setForeground(new java.awt.Color(51, 51, 255));
        cmbMODELOS.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<ESCOLHA O MODELO>", " " }));
        cmbMODELOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbMODELOS.setEnabled(false);
        cmbMODELOS.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMODELOSItemStateChanged(evt);
            }
        });

        txtOBSERVACOES.setEditable(false);
        txtOBSERVACOES.setColumns(20);
        txtOBSERVACOES.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        txtOBSERVACOES.setRows(5);
        txtOBSERVACOES.setToolTipText("Histórico e observações");
        txtOBSERVACOES.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtOBSERVACOESFocusGained(evt);
            }
        });
        txtOBSERVACOES.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtOBSERVACOESMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(txtOBSERVACOES);

        txtTIPO.setEditable(false);
        txtTIPO.setForeground(new java.awt.Color(51, 51, 255));

        btnHistorico.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnHistorico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_blocoNotas.gif"))); // NOI18N
        btnHistorico.setText("Histórico");
        btnHistorico.setToolTipText("Histórico completo do patrimônio");
        btnHistorico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHistorico.setEnabled(false);
        btnHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistoricoActionPerformed(evt);
            }
        });

        btnGerarObsAdicional.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnGerarObsAdicional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/building_add.png"))); // NOI18N
        btnGerarObsAdicional.setText("Add Observacao");
        btnGerarObsAdicional.setToolTipText("Gerar Obs adicional no cadastro");
        btnGerarObsAdicional.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGerarObsAdicional.setEnabled(false);
        btnGerarObsAdicional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarObsAdicionalActionPerformed(evt);
            }
        });

        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Excel.gif"))); // NOI18N
        btnExcel.setText("Excel");
        btnExcel.setToolTipText("");
        btnExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });

        jLabel8.setText("STATUS");

        txtCONTRATO.setEditable(false);
        txtCONTRATO.setForeground(new java.awt.Color(51, 51, 255));

        txtPESQUISA.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPESQUISA.setForeground(new java.awt.Color(51, 51, 255));
        txtPESQUISA.setToolTipText("Barra de Pesquisa Rápida");
        txtPESQUISA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPESQUISAMouseClicked(evt);
            }
        });
        txtPESQUISA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPESQUISAKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPESQUISAKeyReleased(evt);
            }
        });

        txtRF.setEditable(false);
        txtRF.setForeground(new java.awt.Color(51, 51, 255));

        jLabel9.setText("RF");

        txtNUMESTACAO.setEditable(false);
        txtNUMESTACAO.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNUMESTACAO.setForeground(new java.awt.Color(51, 51, 255));
        txtNUMESTACAO.setToolTipText("Campo gerado automaticamente no cadastro");

        jLabel10.setText("NUMESTACAO");

        btnCadSemRede.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnCadSemRede.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/computador.png"))); // NOI18N
        btnCadSemRede.setText("Sem Rede");
        btnCadSemRede.setToolTipText("Cadastre estações sem rede");
        btnCadSemRede.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadSemRede.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadSemRedeActionPerformed(evt);
            }
        });

        jLabel12.setText("PESQUISAR POR DIGITAÇÃO");
        jLabel12.setAlignmentX(0.5F);

        LblPesquisaPorCod.setText("PESQUISAR POR CÓDIGO");
        LblPesquisaPorCod.setAlignmentX(0.5F);
        LblPesquisaPorCod.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                LblPesquisaPorCodMouseMoved(evt);
            }
        });
        LblPesquisaPorCod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LblPesquisaPorCodMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(txtPESQUISA, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtCHAPA, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(txtSERIE, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCONTRATO, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbSTATUS, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addComponent(cmbMODELOS, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(cmbFILTRARPORSECAO, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbFILTRAPORTIPO, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFILTRAR, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCadSemRede, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGerarObsAdicional, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(panelPrincipalLayout.createSequentialGroup()
                                    .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(txtTIPO, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(txtCLIENTE))
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                                    .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtESTACAO)
                                        .addGroup(panelPrincipalLayout.createSequentialGroup()
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtNUMESTACAO, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                                    .addComponent(txtRF, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnLISTARCLIENTESPARAEDITAR)))
                            .addComponent(jLabel4)))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(lblMODELO)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LblPesquisaPorCod)))
                .addContainerGap())
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTIPO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtESTACAO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNUMESTACAO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCLIENTE, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLISTARCLIENTESPARAEDITAR, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtRF, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCONTRATO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSERIE, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbSTATUS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCHAPA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1))
                        .addGap(36, 36, 36)))
                .addGap(4, 4, 4)
                .addComponent(lblMODELO)
                .addGap(3, 3, 3)
                .addComponent(cmbMODELOS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbFILTRARPORSECAO, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFILTRAPORTIPO, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFILTRAR, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHistorico, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGerarObsAdicional, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadSemRede, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LblPesquisaPorCod, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPESQUISA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(182, 182, 182))
        );

        getContentPane().add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 860));

        setSize(new java.awt.Dimension(974, 883));
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
    
    private void limparObservacaoAdicional()
    {
        if(entrouNovaObs)
        {
            String message = "Você adicionou uma observação deseja mantê-la para os próximos cadastros?";
            String title = "Confirmação";
            //Exibe caixa de dialogo (veja figura) solicitando confirmação ou não. 
            //Se o usuário clicar em "Sim" retorna 0 pra variavel reply, se informado não retorna 1
            int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                entrouNovaObs = true;
            }else{
                entrouNovaObs = false;
            }
        }        
    }
    
    private void gravarEstacaoForaDaRede()
    {                
        codigoSecao   = 30;
        codigoCliente = 202;
        idDepto       = 6;
        serie         = txtSERIE.getText();        
        motivo        = df.format(dataDia)+" : Cadastro inicial";        
        chapa         = txtCHAPA.getText();         
        idModelo      = umaBiblio.buscarCodigoGenerico("tblmodelos", "modelo", cmbMODELOS.getSelectedItem().toString());
                
        umModPatrimonio.setTipoid(1);
        umModPatrimonio.setSerie(serie);
        umModPatrimonio.setChapa(chapa);
        umModPatrimonio.setIp(ip);
        umModPatrimonio.setSecaoid(codigoSecao);
        umModPatrimonio.setClienteid(codigoCliente);
        umModPatrimonio.setModeloid(idModelo);
        umModPatrimonio.setDeptoid(idDepto);
        umModPatrimonio.setEstacao("PGMCGGMC000");
        umModPatrimonio.setStatus("ATIVO");
        umModPatrimonio.setDatacad(dataDoDia);            
        umModPatrimonio.setContrato("N");
        umModPatrimonio.setMotivo(motivo);
        
        obs = df.format(dataDia)+" : Cadastro inicial.";
        
        if(entrouNovaObs)
        {
            umModPatrimonio.setObservacoes(novaObservacao);       
        }else{
             umModPatrimonio.setObservacoes(obs);      
        }                 
               
        umControlePatrimonio.salvarPatrimonio(umModPatrimonio);          
        //limparObservacaoAdicional();   //Pergunta se deseja manter a variavel de adição de observação com o texto digitado     
        PreencherTabelaATIVOS(sqlDefaultATIVOS);
               
    }
    
    private void gravarInclusaoRegistro()
    {        
        ip                  = txtIP.getText();
        serie               = txtSERIE.getText();        
        chapa               = txtCHAPA.getText();    
        codPatrimonio       = Integer.valueOf(txtCODIGO.getText());  
        obs                 = txtOBSERVACOES.getText(); 
        idModelo            = umaBiblio.buscarCodigoGenerico("tblmodelos", "modelo", cmbMODELOS.getSelectedItem().toString());
        tipoid              = umaBiblio.buscarCodigoGenerico("tbltipos", "tipo", txtTIPO.getText());
        tipo                = txtTIPO.getText();           
        contrato            = txtCONTRATO.getText();   
        motivo              = df.format(dataDia)+" : Cadastro inicial";
               
        if(contrato.equals("NAO")){
            umModPatrimonio.setContrato("N");
        }else{
             umModPatrimonio.setContrato("S");
        }
        
        if(txtNUMESTACAO.getText().equals("")) 
         {             
             nomeEstacao   = txtESTACAO.getText();  
         }else{
             nomeEstacao   = txtESTACAO.getText()+txtNUMESTACAO.getText();  
        }     
        
        if(entrouNovaObs)
        {
            umModPatrimonio.setObservacoes(novaObservacao);       
        }else{
             umModPatrimonio.setObservacoes(obs);      
        }     
                     
        umModPatrimonio.setChapa(chapa);
        umModPatrimonio.setSerie(serie);
        umModPatrimonio.setTipoid(tipoid);
        umModPatrimonio.setIp(ip);
        umModPatrimonio.setSecaoid(codigoSecao);
        umModPatrimonio.setClienteid(codigoCliente);
        umModPatrimonio.setModeloid(idModelo);
        umModPatrimonio.setDeptoid(idDepto);        
        umModPatrimonio.setEmpresaid(empresaid);        
        umModPatrimonio.setEstacao(nomeEstacao);
        umModPatrimonio.setStatus("ATIVO");
        umModPatrimonio.setMotivo(motivo);
                
        //Valores ques serão passados para cadastrar um novo patrimonio
//        JOptionPane.showMessageDialog(null, 
//        "CHAPA              : "+chapa+"\n" 
//        +"SERIE             : "+serie+"\n" 
//        +"TIPO              : "+tipoid+"\n" 
//        +"IP                : "+ip+"\n" 
//        +"COD SEÇÃO         : "+codigoSecao+"\n" 
//        +"COD CLIENTE       : "+codigoCliente+"\n" 
//        +"COD MODELO        : "+idModelo+"\n" 
//        +"COD DEPTO         : "+idDepto+"\n" 
//        +"COD EMPRESA       : "+empresaid+"\n" 
//        +"OBS               : "+obs+"\n" 
//        +"NOME ESTAÇÃO      : "+nomeEstacao+"\n" 
//        +"STATUS            : ATIVO"+"\n"                   
//        +"CONTRATO          : "+contrato                    
//        );
       
        //se estiver cadastrando um micro deverá verificar se os campos ip/serie/estacao estao preenchidos
        if(txtSERIE.getText().length() > 0 && txtCHAPA.getText().length() > 0)
        {
                if(tipo.equals("MICRO")){                

                    /*verificar se o nome já existe em TBLNOMESTACAO se positivo apenas alterar o status para INDISPONIVEL
                    Caso contrário gravar novo registro também como INDISPONIVEL*/
                    if(umMetodo.temDuplicidadeDeCadastro("TBLNOMESTACAO", "nomestacao", nomeEstacao))
                    {
                        /*SE NOME EXISTIR NA TABELA TBLNOMESTACAO FAZER ALTERACAO DO STATUS PARA INDISPONIVEL  
                        JOptionPane.showMessageDialog(rootPane,  nomeEstacao +" JA EXISTE NA TABELA DE NOMES DE ESTACOES [TBLNOMESTACAO]");*/
                        umEstacaoNomeEstacaoAO.indisponibilizarNomeEstacao(nomeEstacao);                            
                    }else{          
                        //SE NOME NÃO EXISTIR NA TABELA TBLNOMESTACAO CADASTRAR NOVO COM STATUS INDISPONIVEL 
                        //JOptionPane.showMessageDialog(rootPane, "ESTE AQUI E O NOME DO DEPTO "+ nomeDepartamento); 
                        
                        if(nomeDepartamento.equals("BIBLIOTECA"))
                        {
                            deptobiblio = "CEJUR";
                            umEstacaoNomeEstacaoAO.cadastrarNomeComStatusIndisponivel(nomeEstacao, deptobiblio);   
                        }else{
                            umEstacaoNomeEstacaoAO.cadastrarNomeComStatusIndisponivel(nomeEstacao, nomeDepartamento);   
                        }
                    }  
                    umControlePatrimonio.salvarPatrimonio(umModPatrimonio); 
                    
                    umGravarLog.gravarLog("cadastro de um(a) "+tipo+" serie "+umModPatrimonio.getSerie());     

                }else if(tipo.equals("NOTEBOOK")){

                    /*verificar se o nome já existe em TBLNOMESTACAO se positivo apenas alterar o status para INDISPONIVEL
                   Caso contrário gravar novo registro também como INDISPONIVEL*/                 
                   if(umMetodo.temDuplicidadeDeCadastro("TBLNOMESTACAO", "nomestacao", nomeEstacao))
                   {
                       //SE NOME EXISTIR NA TAB ELA FAZER ALTERACAO
                       umEstacaoNomeEstacaoAO.indisponibilizarNomeEstacao(nomeEstacao);  
                       //JOptionPane.showMessageDialog(rootPane,  nomeEstacao +" JA EXISTE NA TABELA");
                   }else{          
                       //SE O NOME NAO EXISTIR CADASTRAR NOVO
                       umEstacaoNomeEstacaoAO.cadastrarNomeComStatusIndisponivel(nomeEstacao, nomeDepartamento);    
                       umGravarLog.gravarLog("cadastro de um(a) "+tipo+" serie "+umModPatrimonio.getSerie());                       
                   }  
                   umControlePatrimonio.salvarPatrimonio(umModPatrimonio);   
                   
                   umGravarLog.gravarLog("cadastro de um(a) "+tipo+" serie "+umModPatrimonio.getSerie());

               }else if(tipo.equals("IMPRESSORA")){                
                        //se estiver cadastrando uma impressora deverá verificar se os campos serie estao preenchidos                

                        /*Primeiro verifica se o IP esta cadastrado na lista, depois atualiza o status do IP que foi reutilizado para INDISPONIVEL*/
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
                        umGravarLog.gravarLog("cadastro de um(a) "+tipo+" serie "+umModPatrimonio.getSerie());                             

                }else {
                        umControlePatrimonio.salvarPatrimonio(umModPatrimonio);
                        umGravarLog.gravarLog("cadastro de um(a) "+tipo+" serie "+umModPatrimonio.getSerie());
                }            

                PreencherTabelaATIVOS(sqlDefaultATIVOS);     
        }else{
                JOptionPane.showMessageDialog(null, "Os campos [Chapa e Série] são de preenchimento obrigatórios!", "Campos obrigatórios vazios!", 2);
                btnVoltarActionPerformed(null);
        }              
                 
    }
    
public void validandoMotivoInativacao()
{      
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        motivoInativacao  = JOptionPane.showInputDialog(null, "Entre com o motivo da inativação!", "Inativação de Patrimônio", 2); 

        //solicita o motivo até que o mesmo seja digitado corretamente
        while (motivoInativacao == null || motivoInativacao.equals("")) 
        {
            JOptionPane.showMessageDialog(this, "Digite um motivo válido e confirme!");  
            motivoInativacao = JOptionPane.showInputDialog(null, "Entre com o motivo da inativação!", "Inativação de Patrimônio", 2);
        }
        //quando gravar string motivoInativacao em observacoes colocar o motivo na linha abaixo da data com \n
        motivoInativacao = "\n"+df.format(dataDia)+" : Inativado na CGGM. "+umMetodo.primeiraLetraMaiuscula(motivoInativacao);
        observacaoDeInativacao = txtOBSERVACOES.getText()+motivoInativacao;  
                
        cmbSTATUS.setSelectedIndex(-1); 
}

private void disponibilizarNomeEstacaoPeloCodigo(int pCod)
{    
    umModeloNomeEstacao.setCodigo(pCod);
    umModeloNomeEstacao.setStatus("DISPONIVEL");
    umControleNomeEstacao.atualizarStatusNomeEstacao(umModeloNomeEstacao);      
     
}

private void disponibilizarNomeEstacaoPeloNome()
{   
    umModeloNomeEstacao.setStatus("DISPONIVEL");
    umModeloNomeEstacao.setNomestacao(nomeESTACAOOLD);
    umControleNomeEstacao.atualizarStatusPeloNomeDaEstacao(umModeloNomeEstacao);      
     
}

private void disponibilizarIPImpressoraTransferida(){
    //disponibiliza o ip da impressora caso seja inativada ou transferida
    
    if(isImpressora) //(txtTIPO.getText().equals("IMPRESSORA")) 
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
            umModeloIPDisponivel.setStatus("DISPONIVEL");   
            umControleIPDisponivel.atualizarIPDisponivel(umModeloIPDisponivel);
            
        }else{
            umModeloIPDisponivel.setIp(numIPOLD);           
            umModeloIPDisponivel.setStatus("DISPONIVEL");   
            umControleIPDisponivel.salvarIPDisponivel(umModeloIPDisponivel);            
        }
    }    
}

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void ImprimindoRelatorios(){
        int indiceTipo  = cmbFILTRAPORTIPO.getSelectedIndex();  
        int indiceSecao = cmbFILTRARPORSECAO.getSelectedIndex();
        
        if( indiceTipo == -1  && indiceSecao == -1 && !clicouInativos)
        {
                //imprime todos os patrimonios cadastrados pois não filtrou seção ou tipo
                if (umaBiblio.ConfirmouOperacao("Confirma a impressão de todos os patrimônios cadastrados e ativos?", "Impressão de Ativos")){
                        GerarRelatorios objRel = new GerarRelatorios();
                        try {
                            objRel.imprimirTodosPatrimonios("relatorio/relpatrimonios.jasper",tabela);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);                
                        }       
                }
        }if(clicouInativos && indiceTipo == -1  && indiceSecao == -1){
            //imprime todos os patrimonios INATIVOS pois não filtrou seção ou tipo
            if (umaBiblio.ConfirmouOperacao("Confirma a impressão de todos os patrimônios cadastrados e inativos?", "Impressão de Inativos")){
                GerarRelatorios objRel = new GerarRelatorios();
                try {
                    objRel.imprimirPatrimoniosInativos("relatorio/relpatrimoniosinativos.jasper",tabela);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);                
                }   
            }
        }if(clicouNaTabela && !filtrou){
            //imprimir o registro selecionado na tabela sem filtrar
            GerarRelatorios objRel = new GerarRelatorios();
            try {
                objRel.imprimirPatrimonioSelecionado("relatorio/relpatrimonioselecionado.jasper", Integer.parseInt(txtCODIGO.getText()),tabela);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);                
            }
        }if(filtrouClicou){
            //imprimir o registro selecionado na tabela após filtro
            GerarRelatorios objRel = new GerarRelatorios();
            try {
                objRel.imprimirPatrimonioSelecionado("relatorio/relpatrimonioselecionado.jasper", Integer.parseInt(txtCODIGO.getText()),tabela);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);                
            }
        }if(indiceSecao >= 0 && indiceTipo == -1){
            //imprimir todos os patrimonios da SECAO selecionada     
            GerarRelatorios objRel = new GerarRelatorios();
            try {                
                objRel.imprimirPatrimoniosFiltrados("relatorio/relpatrimoniosporsecao.jasper", cmbFILTRARPORSECAO.getSelectedItem().toString(),tabela);                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);                
            }      
       }if(indiceSecao >= 0 && indiceTipo >= 0){
           /*imprime todos os equipamentos do TIPO e SEÇÃO selecionados            
             AQUI DEVE TER UMA CONSULTA VERIFICANDO SE A SECAO SELECIONADA TEM UM TIPO SELECIONADO SE RETORNAR ZERO REGS MSG DE NADA ENCONTRADO CASO CONTRARIO IMPRIME*/
           String secao = cmbFILTRARPORSECAO.getSelectedItem().toString();
           String tipo  = cmbFILTRAPORTIPO.getSelectedItem().toString();
           
          if (!umMetodo.sqlRetornouValores("select t.tipo, p.chapa, p.serie, s.nome AS secao from tblpatrimonios p, tbltipos t, tblsecoes s WHERE\n" +
                                           "p.tipoid=t.codigo and p.secaoid=s.codigo and p.status='ATIVO' and t.tipo='"+tipo+"' and s.nome='"+secao+"'"))
          {           
                JOptionPane.showMessageDialog(null, "Não foram encontrados equipamentos desse tipo para esta seção!", "Registros encontrados (0)!", 2);                  
                btnVoltarActionPerformed(null);
                
          }else{           
                GerarRelatorios objRel = new GerarRelatorios();
                try {
                    objRel.imprimirPatrimoniosFiltradosPorTipoSecao("relatorio/relPatrimoniosPorTipoSecao.jasper", cmbFILTRARPORSECAO.getSelectedItem().toString(),cmbFILTRAPORTIPO.getSelectedItem().toString(),tabela);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);                
                } 
                btnPesquisar.setEnabled(false);
                btnImprimir.setEnabled(false);               
                
          }                  
       }if( indiceTipo >= 0 && indiceSecao == -1 ){
            //imprime todos os equipamentos do TIPO selecionado somente
            GerarRelatorios objRel = new GerarRelatorios();            
            try {
                int codigoTipo = umMetodo.getCodigoPassandoString("tbltipos","tipo",cmbFILTRAPORTIPO.getSelectedItem().toString());
                objRel.imprimirSomenteTipoSelecionado("relatorio/relSomenteTipoSelecionado.jasper",codigoTipo, tabela);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar relatório do tipo selecionado!\n"+e);                
            }               
        } 

        btnImprimir         .setEnabled(true);  
        btnVoltar           .setEnabled(true);
        btnNovo             .setEnabled(false);
        btnCadSemRede       .setEnabled(false);        
        txtPESQUISA         .setEnabled(false);
        btnExcel            .setEnabled(false);
        btnPesquisar        .setEnabled(false);
        btnGravar           .setEnabled(false);
        txtNUMESTACAO       .setEnabled(false);
        btnHistorico        .setEnabled(false);
        LblPesquisaPorCod   .setEnabled(false);
        DesabilitarConsulta();    
    }
    
    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
       
        ImprimindoRelatorios();
    
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        umaBiblio.limparTodosCampos(rootPane);
        
        jTabbedPane2.setSelectedIndex(0);  
        Leitura();
        txtPESQUISA.setEnabled(true);
        txtPESQUISA.requestFocus();
        cmbMODELOS.setSelectedIndex(-1);
        
    
        if (!umaBiblio.tabelaVazia(tabela)) {
            PreencherTabelaATIVOS(sqlDefaultATIVOS);  
        }else{
            btnPesquisar.setEnabled(false);
        }
        
        if(jTabbedPane2.getSelectedIndex() == 1){
            jTabbedPane2.setSelectedIndex(0);
            clicouInativos=false;
            txtPESQUISA.setEnabled(true);
            btnVoltar.setEnabled(false);
            btnVoltar.setText("Voltar");
        }      
       
        cadastrando                 = false;
        listouClientesParaEdicao    = false;
        userInformatica             = false;    
        editando                    = false;
        clicouInativos              = false;    
        clicouNaTabela              = false;
        selecionouTipo              = false;
        filtrouClicou               = false;
        filtrou                     = false;
        reativando                  = false;        
        temIP                       = false;
        codTipoid                   = 0;
        controleMostraDescricao     = 0;
        selecionouCliente           = false;
        totalRegs                   = 0;
        contador                    = 0;
        nomeSecao                   = null;
        nomeCliente                 = null;
              
        this.setTitle(this.mostrarTituloDoFormulario());
        umaBiblio.LimparCombo(cmbMODELOS, "tblmodelos", "modelo");
        PreencherTabelaINATIVOS(sqlDefaultINATIVOS);
        //System.out.println("VALOR DO CONTADOR APOS CANCELAMENTO/VOLTAR...: "+contador);
        
        if(alterandonomestacao)
        {
            atualizarStatusDasEstacoesRenomeadas();   
        }       
                 
    }//GEN-LAST:event_btnVoltarActionPerformed
   
    private void atualizarStatusDasEstacoesRenomeadas() {
           
        /*QUANDO VOCE FIZER ALTERAÇÃO DO NOME DA ESTAÇÃO PARA UM NOME DISPONIVEL POR CONTA DE INATIVAÇÃO
          ATUALIZA O STATUS DA NOVA ESTAÇÃO ESCOLHIDA PARA INDISPONIVEL*/
        
        //JOptionPane.showMessageDialog(rootPane, "NOME A FICAR DISPONIVEL "+novonomestacao);
        int codigoEst = umMetodo.getCodigoPassandoString("TBLNOMESTACAO", "nomestacao", novonomestacao);
        umModeloNomeEstacao.setCodigo(codigoEst);
        umModeloNomeEstacao.setNomestacao(novonomestacao);
        umModeloNomeEstacao.setStatus("DISPONIVEL");
        umControleNomeEstacao.atualizarStatusNomeEstacao(umModeloNomeEstacao);   
        
        //ATUALIZAR O STATUS DA ESTACAO SUBSTITUIDA PARA DISPONIVEL
        //JOptionPane.showMessageDialog(rootPane, "NOME A FICAR INDISPONIVEL "+nomestacaosubstituida);
        int codigoEst1 = umMetodo.getCodigoPassandoString("TBLNOMESTACAO", "nomestacao", nomestacaosubstituida);
        umModeloNomeEstacao.setCodigo(codigoEst1);
        umModeloNomeEstacao.setNomestacao(nomestacaosubstituida);
        umModeloNomeEstacao.setStatus("INDISPONIVEL");
        umControleNomeEstacao.atualizarStatusNomeEstacao(umModeloNomeEstacao);  
        
        alterandonomestacao = false;
        
        JOptionPane.showMessageDialog(null, "Os nome da estação não foi alterado!", "Operação cancelada pelo usuário!", 2);
        txtPESQUISA.setEnabled(true);
    }    
    
    private void PreencherComboFILTRARPORSECAO()
    {
        conexao.conectar();  
        sql="select * from tblsecoes where status ='ATIVO' ORDER BY nome";
        conexao.ExecutarPesquisaSQL(sql);              
        try 
        {                       
            cmbFILTRARPORSECAO.removeAllItems();
            while (conexao.rs.next()) {
                 cmbFILTRARPORSECAO.addItem(conexao.rs.getString("nome"));
            };    
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao preencher o combo de seções!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        } 
    }
    
    private void PreencherComboFILTRARPORTIPO()
    {
        conexao.conectar();           
        sql="select * from tbltipos where status ='ATIVO' and tipopatrimonio='PATRIMONIO' order by tipo ";
        conexao.ExecutarPesquisaSQL(sql);              
        try 
        {                       
            cmbFILTRAPORTIPO.removeAllItems();
            while (conexao.rs.next()) {
                 cmbFILTRAPORTIPO.addItem(conexao.rs.getString("tipo"));
            };    
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao preencher o combo de tipos!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        } 
    }
    
    private void Leitura()
    {
        if (umaBiblio.tabelaVazia(tabela)) {
            btnImprimir.setEnabled(false);            
            DesabilitarConsulta();
        } else {
            btnImprimir.setEnabled(true);
            HabilitarConsulta();
            PreencherTabelaATIVOS(sqlDefaultATIVOS);
            PreencherTabelaINATIVOS(sqlDefaultINATIVOS);
        }
        umaBiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 
        PreencherComboFILTRARPORSECAO();
        PreencherComboFILTRARPORTIPO();
        btnCadSemRede.setEnabled(true);
        btnNovo.setEnabled(true);
        btnGravar.setEnabled(false);
        btnVoltar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnSair.setEnabled(true);
        entrouNovaObs = false;
        txtCLIENTE.setEditable(false);
        txtCODIGO.setEditable(false);
        txtCHAPA.setEditable(false);
        txtESTACAO.setEditable(false);
        txtESTACAO.setEnabled(true);
        txtCODIGO.setEditable(false);
        txtSERIE.setEditable(false);
        txtOBSERVACOES.setEditable(false);
        txtPESQUISA.setEnabled(true);
        cmbFILTRARPORSECAO.setSelectedIndex(-1);
        cmbFILTRAPORTIPO.setSelectedIndex(-1);
        txtIP.setEnabled(true);
        txtIP.setEditable(false);
        txtTIPO.setEnabled(true);
        txtTIPO.setEditable(false);
        LblPesquisaPorCod.setEnabled(true);
        cont=0;
        umMetodo.TemImpressorasDeContratoAtivas();
    }
    
    public void HabilitarConsulta() {
        cmbFILTRARPORSECAO.setEnabled(true);
        cmbFILTRAPORTIPO.setEnabled(true);
        txtTIPO.setEnabled(true);
        btnFILTRAR.setEnabled(true);
        btnPesquisar.setEnabled(true);
    }

    public void DesabilitarConsulta() {
        cmbFILTRAPORTIPO.setEnabled(false);
        btnFILTRAR.setEnabled(false);
        cmbFILTRARPORSECAO.setEnabled(false);
        btnPesquisar.setEnabled(false);
        btnCadSemRede.setEnabled(false);
        cmbMODELOS.setEnabled(false);
    }
    
private void gravarRegistroReativado() 
{
    //GRAVANDO O NOVO NOME PARA ESTACAO QUE FOI REATIVADA
    codigo            = Integer.valueOf(txtCODIGO.getText());  
    estacao           = txtESTACAO.getText();
    obs               = txtOBSERVACOES.getText(); 
    ip                = txtIP.getText();
    serie             = txtSERIE.getText();
    chapa             = txtCHAPA.getText();
    tipoid            = umaBiblio.buscarCodigoGenerico("tbltipos","tipo",txtTIPO.getText());
    tipo              = txtTIPO.getText();
    contrato          = txtCONTRATO.getText();     
    
    //listouClientesParaEdicao significa que escolheu um novo cliente para o equipamento   
    umModPatrimonio.setCodigo(codigo);
    umModPatrimonio.setSecaoid(30);    
    umModPatrimonio.setClienteid(202);   
    umModPatrimonio.setStatus("ATIVO");
    umModPatrimonio.setIp(ip);    
    umModPatrimonio.setObservacoes(obs);        
    motivoInativacao = "";        
        
    if(txtCONTRATO.getText().equals("NAO")){
            umModPatrimonio.setContrato("N");
        }else{
             umModPatrimonio.setContrato("S");
    }
    
    //setando os valores dos edits
    umModPatrimonio.setTipoid(tipoid);   
    umModPatrimonio.setDeptoid(idDepto);   
    umModPatrimonio.setSerie(serie);
    umModPatrimonio.setChapa(chapa);           
    umModPatrimonio.setEstacao("PGMCGGMC000");          

    //setando codigo do modelo
    idModelo  = umaBiblio.buscarCodigoGenerico("tblmodelos","modelo",cmbMODELOS.getSelectedItem().toString());
    umModPatrimonio.setModeloid(idModelo);
    //JOptionPane.showMessageDialog(null, "CODIGO DO MODELO...: "+ idModelo);
            
    //passando as variaveis que serão alteradas    
    /*JOptionPane.showMessageDialog(null, "LINAO EDITANDO REGISTRO => IP...:"+ip+"\n SÉRIE...:"+serie+"\n CHAPA...:"+chapa+"\n ESTACAO...:"+estacao+"\n OBS...:"+obs+
                "\n TIPOID...:"+tipoid+"\n COD.SECAO..:"+codigoSecao+"\n COD.CLIENTE..:"+codigoCliente+"\n ID.MODELO..:"+idModelo);
    */  
            
    umControlePatrimonio.atualizarPatrimonio(umModPatrimonio);        
    umGravarLog.gravarLog("atualizacao no cadastro de um(a) "+txtTIPO.getText()+" serie "+umModPatrimonio.getSerie());            
                                 
    PreencherTabelaATIVOS(sqlDefaultATIVOS);        
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
    contrato          = txtCONTRATO.getText();    
    nomeFinal         = txtESTACAO.getText();
    //JOptionPane.showMessageDialog(rootPane, "NOME FINAL : "+nomeFinal);
    
    if(!nomeInicial.equals(nomeFinal)){
        if(!estacao.equals("PGMCGGMC000")){
            umMetodo.indisponibilizarStatusNomeEstacao(nomeFinal);
        }
        umMetodo.indisponibilizarStatusNomeEstacaoTMP(nomeFinal); 
    }
    
    //JOptionPane.showMessageDialog(null, "selecionou novo cliente? : "+ listouClientesParaEdicao);
    //identifica qual registro será atualizado
    umModPatrimonio.setCodigo(codigo);    
        
    //listouClientesParaEdicao significa que escolheu um novo cliente para o equipamento
    if (listouClientesParaEdicao) 
    {
        umModPatrimonio.setSecaoid(codigoSecao);
        umModPatrimonio.setClienteid(codigoCliente);
        
    } else {            
        //pesquisando o codigo da seção pelo nome        
        codigoSecao   = umaBiblio.buscarCodigoGenerico("tblsecoes","nome",cmbFILTRARPORSECAO.getSelectedItem().toString());
        umModPatrimonio.setSecaoid(codigoSecao);
        
        //pesquisar codigo do cliente pelo nome 
        codigoCliente     = umaBiblio.buscarCodigoGenerico("tblclientes","nome",(txtCLIENTE.getText()));        
    }    
     
    if (alterouStatus) //Ao clicar no botão de status foi identificado que foi alterado o mesmo
    {
        validandoMotivoInativacao();
        
        //Disponibiliza o nome da estação para uso com status DISPONIVEL
        String nomeEst = txtESTACAO.getText().toString();
        int codEstacao = umMetodo.getCodigoPassandoString("TBLNOMESTACAO", "nomestacao", nomeEst);      
        disponibilizarNomeEstacaoPeloCodigo(codEstacao);
        
        umModPatrimonio.setStatus("INATIVO");
        umModPatrimonio.setIp("0"); //se inativou a impressora devera gravar zero para o numero do IP 
        umModPatrimonio.setMotivo(motivoInativacao); 
        umModPatrimonio.setDatainativacao(dataDia);
        umModPatrimonio.setObservacoes(observacaoDeInativacao); 
        umModPatrimonio.setClienteid(202); 
        umModPatrimonio.setSecaoid(30); 
        umControlePatrimonio.atualizarDataRegistroInativado(umModPatrimonio);
        
    }else{
        umModPatrimonio.setStatus("ATIVO");
        umModPatrimonio.setIp(ip);    
        umModPatrimonio.setObservacoes(obs);        
        umModPatrimonio.setMotivo(motivoInicial); 
        umModPatrimonio.setClienteid(codigoCliente); 
    }   
    
    if(txtCONTRATO.getText().equals("NAO")){
            umModPatrimonio.setContrato("N");
        }else{
             umModPatrimonio.setContrato("S");
    }
    
    deptoid = umMetodo.retornaIdDepto(codigoSecao);
    
    //setando os valores dos edits
    umModPatrimonio.setTipoid(tipoid);   
    umModPatrimonio.setDeptoid(deptoid);   
    umModPatrimonio.setSerie(serie);
    umModPatrimonio.setChapa(chapa);      
    
    estacao = txtESTACAO.getText();
    umModPatrimonio.setEstacao(estacao);    
    
    //CASO O PATRIMONIO TENHA SIDO TRANSFERERIDO PARA OUTRO LUGAR A SERIE E CHAPA SERAO ATUALIZADOS CONFORME ESTE PROCEDIMENTO   
    umMetodo.atualizarSerieChapaPatrimoniosEditadosETransferidos(serie, chapa, numSerieOLD);
    
    //setando codigo do modelo
    idModelo  = umaBiblio.buscarCodigoGenerico("tblmodelos","modelo",cmbMODELOS.getSelectedItem().toString());
    umModPatrimonio.setModeloid(idModelo);
    //JOptionPane.showMessageDialog(null, "CODIGO DO MODELO...: "+ idModelo);
            
    //passando as variaveis que serão alteradas    
    /*JOptionPane.showMessageDialog(null, "EDITANDO REGISTRO => IP...:"+ip+"\n SÉRIE...:"+serie+"\n CHAPA...:"+chapa+"\n ESTACAO...:"+estacao+"\n OBS...:"+obs+
                "\n TIPOID...:"+tipoid+"\n COD.SECAO..:"+codigoSecao+"\n COD.CLIENTE..:"+codigoCliente+"\n ID.MODELO..:"+idModelo);
    */
     
    if(tipo.equals("MICRO")){
        
        if(estacaoRecebeuDisponivel){
        
            String nomeEstacaoAlterada = txtESTACAO.getText(); 
            //JOptionPane.showMessageDialog(null, "ESTACAO ALTERADA : "+nomeEstacaoAlterada);
            
            if(umMetodo.temCadastradoNaTabela("tblnomestacao", "nomestacao", nomeEstacaoAlterada)){
                umMetodo.indisponibilizarStatusNomeEstacao(nomeEstacaoAlterada);  
            }else{
                int numeroEstacao =  umMetodo.somenteDigitos(nomeEstacaoAlterada);  //pegar o nome da estação e retirar o numero final
                if(numeroEstacao > 0){
                    umModeloNomeEstacao.setNomestacao(nomeEstacaoAlterada);
                    umModeloNomeEstacao.setNumestacao(numeroEstacao);
                    umModeloNomeEstacao.setDepto(nomeDepartamento);
                    umModeloNomeEstacao.setStatus("INDISPONIVEL");
                    umControleNomeEstacao.salvarNomeEstacao(umModeloNomeEstacao);
                }
            }
            
        }
         
        umControlePatrimonio.atualizarPatrimonio(umModPatrimonio);        
        umGravarLog.gravarLog("atualizacao no cadastro de um(a) "+txtTIPO.getText()+" serie "+umModPatrimonio.getSerie());
    }
    
    if(!tipo.equals("MICRO") && !tipo.equals("IMPRESSORA")){
        
        umControlePatrimonio.atualizarPatrimonio(umModPatrimonio);        
        umGravarLog.gravarLog("atualizacao no cadastro de um(a) "+txtTIPO.getText()+" serie "+umModPatrimonio.getSerie());
       
    }
        
    if(tipo.equals("IMPRESSORA"))
    {
        //executar alteração e disponibilizar na tabela de IPs o IP do registro que foi alterado aqui no formulario de patrimonios alterando o status para DISPONIVEL
        int codIpAntigoNoBanco = umaBiblio.buscarCodigoGenerico("tblipsdisponiveis", "ip", numIPOLD); 
        //JOptionPane.showMessageDialog(null, "codigo do IP atual que será alterado...: "+" "+codIpAntigoNoBanco+"\n IP...atual...:"+numIPOLD);
        umModeloIPDisponivel.setCodigo(codIpAntigoNoBanco); 
        umModeloIPDisponivel.setIp(numIPOLD);           
        umModeloIPDisponivel.setStatus("DISPONIVEL");   
        umControleIPDisponivel.atualizarIPDisponivel(umModeloIPDisponivel);


        int codIpAlteradoNoBanco = umaBiblio.buscarCodigoGenerico("tblipsdisponiveis", "ip", ip);  
        //JOptionPane.showMessageDialog(null, "codigo do novo IP...: "+" "+codIpAlteradoNoBanco+"\n novo IP...:"+ip);
        umModeloIPDisponivel.setCodigo(codIpAlteradoNoBanco); 
        umModeloIPDisponivel.setIp(ip);           
        umModeloIPDisponivel.setStatus("INDISPONIVEL"); 
        umControleIPDisponivel.atualizarIPDisponivel(umModeloIPDisponivel);

  
        if(!umPatrimonioDAO.duplicidadeImpressoraDAO(codigo, serie , ip)){
            umControlePatrimonio.atualizarPatrimonio(umModPatrimonio);        
            umGravarLog.gravarLog("atualizacao no cadastro de um(a) "+txtTIPO.getText()+" serie "+umModPatrimonio.getSerie());
        }
        disponibilizarIPImpressoraTransferida();                      
    }

    //Atualiza os grids apos a alterações
    PreencherTabelaATIVOS(sqlDefaultATIVOS);
    PreencherTabelaINATIVOS(sqlDefaultINATIVOS);
    PreencherComboFILTRARPORSECAO();
    PreencherComboFILTRARPORTIPO();
    txtCHAPA.setEditable(false);
    cadastrando                  = false;
    editandoNomeRede             = false;
    editando                     = false;
    listouClientesParaEdicao     = false;
    alterouStatus                = false;
    editandoNomeEstacaoReativada = false;
    cmbSTATUS.setSelectedIndex(-1);
    cmbFILTRARPORSECAO.setSelectedIndex(-1);
    cmbFILTRAPORTIPO.setSelectedIndex(-1);
    umPatrimonioDAO.atualizarNomeEstacoesInativadasDAO(); //Coloca o nome da estação como INATIVA enquanto estiver nessa condição
    Leitura();
    
 }
    
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
      if(umaBiblio.permissaoLiberada()){
        
        listouClientesParaEdicao = false;
        cadastrandonomestacao    = false;  
        tipo                     = txtTIPO.getText();
        numSerieOLD              = txtSERIE.getText();
        contrato                 = txtCONTRATO.getText();
        nomeSecao                = umPatrimonioDAO.getSecaoEquipamento(Integer.valueOf(txtCODIGO.getText()));
        
        btnExcel.setEnabled(false);
        btnCadSemRede.setEnabled(false);
        cmbSTATUS.addItem("INATIVO");
        
        //se for impressora
        numIPOLD              = txtIP.getText(); //guarda o numero do IP antes da alteração           
        idCodigoIPDisponivel  = umaBiblio.buscarCodigoGenerico("tblipsdisponiveis","ip",numIPOLD); //retorna o codigo do IP que foi alterado na tblIPsDisponiveis
        
        //se estiver editando estação reativada, e o cmbFILTRARPORSECAO estiver vazio buscar o codigo da seção e setar no cmbFILTRARPORSECAO
        if(cmbFILTRARPORSECAO.getSelectedIndex() == -1){
           //atraves do codigo do patrimonio setar o nome da seção no cmbFILTRARPORSECAO 
           cmbFILTRARPORSECAO.setSelectedItem(umaBiblio.retornaSecao(Integer.valueOf(txtCODIGO.getText())));
        }
                       
        /*Controla a liberação da combo cmbstatus apenas para equipamentos que estejam na CGGM a inativação dos que estão 
        fora será realizada através do memo de transferência*/
        if(umMetodo.retornaDepto(nomeSecao).equals("CGGM")){
            cmbSTATUS.setEnabled(true); 
        }else{
            cmbSTATUS.setEnabled(false);
        }        
        
        btnLISTARCLIENTESPARAEDITAR.setEnabled(true); 
         
        if(!temIP){
            //JOptionPane.showMessageDialog(null,"EQUIPAMENTO NÃO TEM IP");
            txtIP.setEditable(false);
            txtIP.setEnabled(true);
            txtCHAPA.requestFocus();
            txtCHAPA.selectAll();           
        }else{
            //JOptionPane.showMessageDialog(null,"EQUIPAMENTO TEM IP");
            txtIP.setEditable(true);
            txtIP.setEnabled(true);            
            txtIP.selectAll(); 
            txtIP.requestFocus();
        }              
        
        PreencherTabelaATIVOS(sqlVazia);
        this.setTitle("Editando "+txtTIPO.getText()+" "+txtSERIE.getText());
        nomeESTACAOOLD = txtESTACAO.getText();

        editando            = true;
        cadastrando         = false;
        alterouStatus       = false;
        btnGravar.setEnabled(true);
        btnGravar.setText("Gravar");
        btnEditar.setEnabled(false);    
        btnVoltar.setText("Cancelar");
        btnImprimir.setEnabled(false);
        btnHistorico.setEnabled(false);
        txtESTACAO.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));     
        txtCHAPA.setEditable(true);
        txtCHAPA.setEnabled(true);        
        txtSERIE.setEditable(true);
        txtSERIE.setEnabled(true);
        txtOBSERVACOES.setEditable(true);
        txtOBSERVACOES.setEnabled(true); 
        txtOBSERVACOES.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        LblPesquisaPorCod.setEnabled(false);
               
        if(tipo.equals("IMPRESSORA"))
        {
            //se for IMPRESSORA
            numIPOLD              = txtIP.getText(); //guarda o numero do IP antes da alteração           
            idCodigoIPDisponivel  = umaBiblio.buscarCodigoGenerico("tblipsdisponiveis","ip",numIPOLD); //retorna o codigo do IP que foi alterado na tblIPsDisponiveis
            PreencherTabelaATIVOS(sqlVazia);
            this.setTitle("Editando "+txtTIPO.getText()+" "+txtSERIE.getText());
            nomeESTACAOOLD = txtESTACAO.getText();
            
            editando                    = true;
            cadastrando                 = false;
            alterouStatus               = false;            
            codigoCliente               = 0;
            
            btnGravar.setEnabled(true);
            btnGravar.setText("Gravar");
            btnEditar.setEnabled(false);    
            btnVoltar.setText("Cancelar");
            btnImprimir.setEnabled(false);
                       
            txtIP.setEditable(true);
            txtIP.setEnabled(true);            
            txtIP.selectAll(); 
            txtIP.requestFocus();
            
            txtCHAPA.setEditable(true);
            txtCHAPA.setEnabled(true);            
            txtSERIE.setEditable(true);
            txtSERIE.setEnabled(true);            
            txtOBSERVACOES.setEditable(true);
            txtOBSERVACOES.setEnabled(true);       
            
        }               
      }        
    }//GEN-LAST:event_btnEditarActionPerformed

    private void gravarReativacaoRegistro()
    {
        //REATIVA O EQUIPAMENTO DEIXANDO-ATIVO, SE FOR ESTAÇÃO FICARA COM STATUS FORA DA REDE E NOME PGMCGGMC000 AGUARDANDO NOVO NOME E USUARIO
        reativou=true; 
        
        if(reativou){        
            //tratando os codigos da seção e do cliente(usuario) se fizer alteração no cliente       
            String codigo     = txtCODIGO.getText();  
            tipo              = txtTIPO.getText();

            //gravando reativacao do registro
            DateFormat df     = new SimpleDateFormat("dd/MM/yyyy");
            Date dataDia      = dataDoDia; 
            motivoReativacao  = JOptionPane.showInputDialog(null, "Entre com o motivo da reativação!", "Reativação de Patrimônio", 2); 

            //solicita o motivo até que o mesmo seja digitado corretamente
            while (motivoReativacao == null || motivoReativacao.equals("")) 
            {
                JOptionPane.showMessageDialog(this, "Digite um motivo válido e confirme!");  
                motivoReativacao = JOptionPane.showInputDialog(null, "Entre com o motivo da inativação!", "Inativação de Patrimônio", 2);
            }

            //quando gravar string motivoReativacao em observacao colocar o motivo na linha abaixo da data com \n
            motivoReativacao = "\n"+df.format(dataDia)+" : Recadastrado na CGGM. "+umMetodo.primeiraLetraMaiuscula(motivoReativacao);

            if(txtOBSERVACOES.getText().equals("")){
                descricaoDeReativacao = txtOBSERVACOES.getText()+motivoReativacao;  //para acrescentar o motivo na observacao do patrimonio 
            }else{
                descricaoDeReativacao = txtOBSERVACOES.getText()+motivoReativacao;  
            }

            umModPatrimonio.setStatus("ATIVO");  
            //sempre que reativar um patrimonio atribui-lo a seção INFORMATICA (30) / cliente SEM USUARIO INFORMATICA(202)
            umModPatrimonio.setMotivo(motivoReativacao);       
            umModPatrimonio.setDeptoid(6);   
            umModPatrimonio.setSecaoid(30);
            umModPatrimonio.setClienteid(202);         
            umModPatrimonio.setObservacoes(descricaoDeReativacao); 
            
            if(tipo.equals("MICRO")){
                umModPatrimonio.setEstacao("PGMCGGMC000"); 
            }
             else if(tipo.equals("IMPRESSORA"))
            {                
                //JOptionPane.showMessageDialog(rootPane, "É IMPRESSORA");
                
                if(!umMetodo.impressoraDeContrato(Integer.parseInt(codigo)))
                {
                    //JOptionPane.showMessageDialog(rootPane, "NÃO É DE CONTRATO");
                    gerarIPFicticio();
                    
                }else{      
                    //JOptionPane.showMessageDialog(rootPane, "É DE CONTRATO");

                    txtIP.setEnabled(true);
                    txtIP.setEditable(false);

                    F_LISTAIPSPARAIMPRESSORAS frm = new F_LISTAIPSPARAIMPRESSORAS(new JFrame(),true);
                    frm.setVisible(true);   
                    txtIP.setText(frm.getItemSelecionado());    
                }
                umModPatrimonio.setEstacao("IMPRESSORA"); 
                umModPatrimonio.setIp(txtIP.getText());
            }else{
                umModPatrimonio.setEstacao(tipo); 
            }

            //identifica qual registro será atualizado
            umModPatrimonio.setCodigo(Integer.parseInt(txtCODIGO.getText()));
            umModPatrimonio.setDatainativacao(null); //gravando a data vazia

            //gravando as alteraçoes do registro 
            umControlePatrimonio.reativarPatrimonio(umModPatrimonio);                    
            umGravarLog.gravarLog("reativação no cadastro de um(a) "+txtTIPO.getText()+" serie "+txtSERIE.getText());
            
            //Atualiza os grids apos a alterações
            PreencherTabelaATIVOS(sqlDefaultATIVOS);
            PreencherTabelaINATIVOS(sqlDefaultINATIVOS);
            PreencherComboFILTRARPORSECAO();  
            PreencherComboFILTRARPORTIPO();
            jTabbedPane2.setSelectedIndex(0);
            clicouInativos = false;
            reativou       = false;
            
            //APOS A REATIVAÇÃO FECHAR O FORMULARIO E VOLTAR
            dispose();
            tabela = "TBLPATRIMONIOS";
            F_PATRIMONIOS frm1 = new F_PATRIMONIOS();
            frm1.setVisible(true);            
            
        }else{
            //SE CANCELOU A REATIVAÇÃO
            btnVoltarActionPerformed(null);            
        }
    }
      
    
    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        
        if (cadastrando){
            gravarInclusaoRegistro();
        }else if(reativando){
            gravarReativacaoRegistro();     
        }else if(cadastrandoEstacaoForaRede){
            gravarEstacaoForaDaRede();
        }else if(editando){
            gravarEdicaoRegistro();            
        }
        umMetodo.atualizarESTACOESCOMZERO();                
        btnEditar.setEnabled(false);
        btnVoltar.setEnabled(false);
        btnNovo.setEnabled(true);
        btnSair.setEnabled(true);
        btnGravar.setEnabled(false);
        btnCadSemRede.setEnabled(true);
        btnVoltar.setText("Voltar");
        btnGerarObsAdicional.setEnabled(false);
        btnLISTARCLIENTESPARAEDITAR.setEnabled(false);
        cmbFILTRARPORSECAO.setEnabled(true);
        cmbFILTRAPORTIPO.setEnabled(true);
        cmbMODELOS.setEnabled(false);
        cmbMODELOS.removeAllItems();
        btnFILTRAR.setEnabled(true);
        txtCHAPA.setEditable(false);        
        txtOBSERVACOES.setEditable(false);
        txtESTACAO.setEnabled(true);
        txtESTACAO.setEditable(false);
        txtSERIE.setEditable(false);    
        txtIP.setEnabled(true);
        txtIP.setEditable(false);
        txtTIPO.setEnabled(true);
        txtTIPO.setEditable(false);
        txtCODIGO.requestFocus();
        umaBiblio.limparTodosCampos(this);
        cmbSTATUS.setEnabled(false);
        LblPesquisaPorCod.setEnabled(true);
        btnGerarObsAdicional.setEnabled(false);   
           
        nomeSecao      = null;
        nomeCliente    = null;
        semIP          = false;
        alterouIP      = false;
        isMicro        = false;   
        isMonitor      = false;   
        isSwitch       = false;   
        isScanner      = false;   
        isImpressora   = false; 
        isDeContrato   = false;
        editando       = false;
        cadastrando    = false;
        reativando     = false;
        alterouEstacao = false;
        alterouNomeRede= false;
        alterouStatus  = false;
        contador       = 0;        
        codigoCliente  = 0;
        cadastrando    = false;        
        editando       = false;        
        userInformatica = false;        
        tipo           = "";
        nomeEstacao    = "";
        serie          = "";
        listouClientesParaEdicao   = false; 
        alterandonomestacao        = false;
        cadastrandonomestacao      = false;
        editandoDisponiveis        = false;
        cadastrandoEstacaoForaRede = false;
        estacaoRecebeuDisponivel   = false;
        controleMostraDescricao    = 0;     
        txtOBSERVACOES.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        this.setTitle(this.mostrarTituloDoFormulario());
        
    }//GEN-LAST:event_btnGravarActionPerformed
        
    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
    if(umaBiblio.permissaoLiberada())
    {
        editando              = false;
        cadastrando           = true;  
        selecionouCliente     = false;  
        isMicro               = false;   
        isMonitor             = false;   
        isSwitch              = false;   
        isScanner             = false;   
        isImpressora          = false; 
        isNotebook            = false;
        isGbit                = false;
        cadastrandonomestacao = true;
        editandoDisponiveis   = false;
                
        //habilita o combo de modelos 
        cmbMODELOS.setEnabled(true);  
        
        //CONTROLANDO OS BOTOES
        btnNovo.setEnabled(false);
        btnEditar.setEnabled(false);
        btnImprimir.setEnabled(false);
        btnExcel.setEnabled(false);
        btnSair.setEnabled(false);
        btnVoltar.setEnabled(true);
        btnVoltar.setText("Cancelar");
        btnPesquisar.setEnabled(false);
        btnCadSemRede.setEnabled(false);   
        btnGerarObsAdicional.setEnabled(true);

        //LIBERANDO OS TXT PARA EDIÇÃO
        umaBiblio.limparTodosCampos(rootPane);
        txtCODIGO.setEditable(false);       
        txtSERIE.setEditable(true);        
        txtCHAPA.setEditable(true);
        txtESTACAO.setEnabled(true);
        txtESTACAO.setEditable(false);
        txtOBSERVACOES.setEditable(true);
        txtCLIENTE.setEnabled(true);
        cmbFILTRARPORSECAO.setEnabled(false);
        cmbFILTRAPORTIPO.setEnabled(false);
        btnFILTRAR.setEnabled(false);
        txtCODIGO.setText(String.valueOf(umaBiblio.mostrarProximoCodigo(tabela))); 
        PreencherTabelaATIVOS(sqlVazia);
        
        //abrir lista de tipos de equipamentos para cadastrar
        F_LISTATIPOS frmTipos = new F_LISTATIPOS(this, true);
        frmTipos.setVisible(true);
        
        //se este tipo tiver clientes virturais dos setores quero abrir a lista com eles e não a lista com os servidores
        tipo = frmTipos.getItemSelecionado();  
        
        txtTIPO.setText(tipo);                
        txtESTACAO.setText(tipo);
        txtIP.setText("0");
        txtCONTRATO.setText("NAO");
        
        int codItemSelec= umMetodo.getCodigoPassandoString("tbltipos", "tipo", tipo);       
        
        if(umMetodo.TipoTemClientesVirtuais(codItemSelec)){
            cadastrandoEquipamento = false;
        }else{
            cadastrandoEquipamento = true;
        }
        
        if(umMetodo.tipoTemIP(tipo))
        {
            txtIP.setEditable(true);
            txtIP.setEnabled(true);
            txtIP.requestFocus();
            txtIP.selectAll();
        }else{
            txtCHAPA.requestFocus(); 
            txtIP.setEditable(false);
            txtIP.setEnabled(true);
        }                
                  
        if(tipo.equals("IMPRESSORA")){
            if(!temContratoImpressoraAtivo){
                //System.out.println("nao temos impressoras ativas");
                        
                if(umMetodo.ConfirmouOperacao("O contrato de locacao de impressoras da empresa anterior foi finalizado, é necessário\n" +
                    "cadastrar  a  empresa   do  novo  contrato  para   então  cadastrar  seus  equipamentos \n" +
                    "Deseja cadastrar a nova empresa agora?", "Contrato de impressoras finalizado")){              
                    
                    dispose(); 
                    
                    tabela = "TBLEMPRESA";     
                    F_EMPRESA frm = new F_EMPRESA(this,true);
                    frm.setVisible(true); 
                    return;
                }    
            }         
        }
           
        if(tipo.equals("IMPRESSORA")){
            
            isImpressora=true;
            cadastrandoEquipamento = true;

            //Tela de verificacao se é de contrato ou nao, por enquanto somente as impressoras são de contrato entao essa tela só aparece pra impressoras até o momento
            F_LISTATIPOSCONTRATOS frmTiposContratos = new F_LISTATIPOSCONTRATOS(this, true);
            frmTiposContratos.setVisible(true);     

            if(!isDeContrato){
               gerarIPFicticio();
               txtCONTRATO.setText("NAO");

            }else{
               txtCONTRATO.setText("SIM"); 
               int codigoEmpresaAtualContratoImpressoras     = Integer.parseInt(umMetodo.getValorCampoUltimoCodigo("tblempresa", "codigo"));                    

               if(isDeContrato && itemSelecionadoCadastro.equals("IMPRESSORA")){
                   empresaid = codigoEmpresaAtualContratoImpressoras;           
               }   
               //lista os ips disponiveis para impressoras de contrato
               listarIPImpressorasContrato();      
            }
            txtIP.requestFocus();   
            txtIP.selectAll();       
            
            
        }             
              
        //POPULANDO O COMBO COM OS TIPO DE MODELOS
        popularComboBoxModelos();
        
        txtTIPO.setEditable(false);
        txtTIPO.setEnabled(true);
        
        if(naoTemModelo)
        {
            btnVoltarActionPerformed(null);
            
        }else{
            
            if( (!tipo.equals("MICRO")) && !tipo.equals("NOTEBOOK") && !tipo.equals("MONITOR")){
                F_LISTACLIENTESATIVOS frm = new F_LISTACLIENTESATIVOS(null, true, "V");
                frm.setVisible(true);                                    
            }else{
                F_LISTACLIENTESATIVOS frm = new F_LISTACLIENTESATIVOS(null, true, "");
                frm.setVisible(true);  
            }                       

            txtCLIENTE.setText(nomeCliente);
            txtRF.setText(rfCliente);       

            umMetodo.gerarNumeroAleatorioParaCampoTexto(txtCHAPA);          

            //Abre lista de nomes de estações disponiveis
            if(tipo.equals("MICRO") || tipo.equals("NOTEBOOK")){   
               //Sempre mostrar o nome PGMCGGMC000 para opção de cadastro
               umPatrimonioDAO.atualizarNomesEstacoesPadrao("PGMCGGMC000");    
               definirNomestacao();
               //abrirListaEstacoesDisponiveis();
            }          
            
        }      
        
    }
       
    }//GEN-LAST:event_btnNovoActionPerformed

    private void txtSERIEFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSERIEFocusGained
        ip    = txtIP.getText();
        tipo  = txtTIPO.getText(); 
        txtSERIE.selectAll();               
        
         if(editando){            
           //alterando o ponteiro do mouse indicando que o campo esta editável
           txtSERIE.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }                
       
    }//GEN-LAST:event_txtSERIEFocusGained
 
    private void definirNomestacao()
    {                    
        disponibilizarNomeEstacaoPeloNome();
        if(cadastrando || editando)
        {  
              mostrarNomeClienteSelecionado(); 
              F_LISTAESTACOESDISPONIVEIS frm = new F_LISTAESTACOESDISPONIVEIS(new JFrame(),true);
              frm.setVisible(true);
              txtESTACAO.setText(frm.getNomeEstacaoSelecionada()); 
              estacaoRecebeuDisponivel = true;
              txtCHAPA.requestFocus();              
        }        
    }
        
    private void txtCHAPAFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCHAPAFocusGained
          
    tipo  = txtTIPO.getText();
    ip    = txtIP.getText();   
    txtCHAPA.selectAll();   
     
    if(cadastrando){
        serie = txtSERIE.getText();    
    }     
     
    if(editando){            
         //alterando o ponteiro do mouse indicando que o campo esta editável
         txtCHAPA.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }    
        
    }//GEN-LAST:event_txtCHAPAFocusGained

    private void txtCODIGOFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCODIGOFocusGained
        txtCODIGO.setEditable(false);
        cmbMODELOS.setEnabled(false);
    }//GEN-LAST:event_txtCODIGOFocusGained

    private void cmbSTATUSItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSTATUSItemStateChanged
        alterouStatus = true;        
    }//GEN-LAST:event_cmbSTATUSItemStateChanged
    
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
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela com registro ATIVOS\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }
        
    }    
    
    private String pesquisarDeptoRegistroReativado(int codigo){
        conexao.conectar();
        String sql = "SELECT p.deptoid, p.estacao, d.nome as depto FROM tblpatrimonios p, tbldepartamentos d WHERE d.codigo= p.deptoid and p.codigo="+ codigo;
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {   
                return conexao.rs.getString("depto");
            }else{
                return "";
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar departamento!\nErro: " + ex.getMessage());
            return "";
        } finally {
            conexao.desconectar();
        }
    }
            
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
               
        sql = "SELECT p.*, c.nome as cliente, c.rf, s.nome as secao, s.codigo as codigosecao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
            + "p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.codigo="+codPatrimonio+"";                      
        
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        
        try{
                conexao.rs.first();            
                txtCODIGO.setText(String.valueOf(conexao.rs.getInt("codigo")));
                txtSERIE.setText(conexao.rs.getString("serie"));
                txtIP.setText(conexao.rs.getString("ip"));            
                txtRF.setText(conexao.rs.getString("rf"));
                txtCHAPA.setText(conexao.rs.getString("chapa"));
                txtDESCRICAO.setText(conexao.rs.getString("descricao"));
                txtESTACAO.setText(conexao.rs.getString("estacao"));
                txtOBSERVACOES.setText(conexao.rs.getString("observacoes")); 
                txtCLIENTE.setText(conexao.rs.getString("cliente"));
                
                String deContrato = conexao.rs.getString("contrato");
                if(deContrato.equals("N")){
                     txtCONTRATO.setText("NAO");
                }else{
                     txtCONTRATO.setText("SIM");
                }
                
                //guarda o nome da estacão atual do patrimonio
                nomeEstacaoAtual = txtESTACAO.getText();
                
                //setando a seção do patrimonio na combo filtrar por secao
                idSecao = conexao.rs.getInt("codigosecao");
                
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
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Problemas ao tentar recuperar os dados dos registro selecionado!\n Erro : " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }
        //VERIFICA SE O PATRIMONIO É DE CONTRATO OU NAO
        umControlePatrimonio.ImpressoraDeContrato(Integer.parseInt(txtCODIGO.getText()));
    }
       
    public void filtrarPorSecao()
    {
        int indiceSecao = cmbFILTRARPORSECAO.getSelectedIndex();
        int indiceTipo  = cmbFILTRAPORTIPO.getSelectedIndex();
        
        //so escolheu secao / e não escolheu um tipo imprimirá todos os patrimonios da seção selecionada   
        idSecao  = umaBiblio.buscarCodigoGenerico("tblsecoes", "nome", cmbFILTRARPORSECAO.getSelectedItem().toString());
        if(indiceTipo == -1 && indiceSecao >= 0){
            PreencherTabelaATIVOS("select p.*, t.*, c.nome as cliente, s.nome as secao from tblpatrimonios p, tbltipos t, "
                    + "tblclientes c, tblsecoes s where s.codigo=p.secaoid and p.tipoid=t.codigo and p.clienteid=c.codigo "
                    + "and p.status = 'ATIVO' and s.codigo='" + idSecao + "'");                   

            btnNovo.setEnabled(false);
            btnVoltar.setText("Limpar");
            btnVoltar.setToolTipText("Limpar Pesquisa");
            this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
            flagImprimiu=true;
        
        }else       
        //so escolheu tipo / e nao escolheu seção imprimirá todos os patrimonios da seção selecionada por tipo 
        selecionouTipo  = true;
        idSecao  = umaBiblio.buscarCodigoGenerico("tblsecoes", "nome", cmbFILTRARPORSECAO.getSelectedItem().toString());
        if(indiceTipo >= 0 && indiceSecao == -1){
            //Se você escolheu um Tipo mostrará todos os patrimonios da seção selecionada filtrados pelo tipo escolhido
            nomeTipo = cmbFILTRAPORTIPO.getSelectedItem().toString();                
            //JOptionPane.showMessageDialog(this, nomeTipo);
            PreencherTabelaATIVOS("select p.*, t.*, c.nome as cliente, s.nome as secao from tblpatrimonios p, "
                    + "tbltipos t, tblclientes c, tblsecoes s where s.codigo=p.secaoid and p.tipoid=t.codigo "
                    + "and p.clienteid=c.codigo and p.status = 'ATIVO' and t.tipo= '" + nomeTipo + "'");

            btnNovo.setEnabled(false);
            btnVoltar.setText("Limpar");
            btnVoltar.setToolTipText("Limpar Pesquisa");
            this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
            flagImprimiu=true;           
         
        }else       
        //escolheu um tipo e uma seção imprimirá todos os patrimonios da seção selecionada por tipo 
        selecionouTipo  = true;
        idSecao  = umaBiblio.buscarCodigoGenerico("tblsecoes", "nome", cmbFILTRARPORSECAO.getSelectedItem().toString());
        
        if(indiceTipo >= 0 && indiceSecao >= 0){
            //Se você escolheu um Tipo mostrará todos os patrimonios da seção selecionada filtrados pelo tipo escolhido
            nomeTipo = cmbFILTRAPORTIPO.getSelectedItem().toString();               
                     
            PreencherTabelaATIVOS("select p.*, t.*, c.nome as cliente, s.nome as secao from tblpatrimonios p, "
                    + "tbltipos t, tblclientes c, tblsecoes s where s.codigo=p.secaoid and p.tipoid=t.codigo "
                    + "and p.clienteid=c.codigo and p.status = 'ATIVO' and t.tipo= '" + nomeTipo + "' and s.codigo='" + idSecao + "'");

            btnNovo.setEnabled(false);
            btnVoltar.setText("Limpar");
            btnVoltar.setToolTipText("Limpar Pesquisa");
            this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
            flagImprimiu=true;   
        
        } 

    }
    
    private void btnFILTRARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFILTRARActionPerformed
        int indiceSecao = cmbFILTRARPORSECAO.getSelectedIndex();
        int indiceTipo  = cmbFILTRAPORTIPO.getSelectedIndex();       
                    
        //se não escolher uma seção ou um tipo menssagem de erro
        if (!(indiceSecao == -1 && indiceTipo == -1)){
            totalRegs=0;
            
            //se escolheu um ( TIPO - SECAO ) mostrará todos os patrimonios filtrados pelo tipo escolhido
            if( indiceTipo >= 0 && indiceSecao == -1)
            {                
                nomeTipo = cmbFILTRAPORTIPO.getSelectedItem().toString();                
                //JOptionPane.showMessageDialog(this, nomeTipo);
                PreencherTabelaATIVOS("select p.*, t.*, c.nome as cliente, s.nome as secao from tblpatrimonios p, tbltipos t, tblclientes c, tblsecoes s where s.codigo=p.secaoid "
                        + "and p.tipoid=t.codigo and p.clienteid=c.codigo and p.status = 'ATIVO' and t.tipo= '" + nomeTipo + "'");

                btnNovo.setEnabled(false);
                btnExcel.setEnabled(false);                
                this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
                selecionouTipo  = true;
                flagImprimiu    = true;                

            }else{ 
                //se escolher ( SECAO + TIPO ) ou  se escolher ( SECAO - TIPO )
                if((indiceSecao >=0 && indiceTipo >= 0)||(indiceSecao >=0 && indiceTipo == -1)) {
                    filtrarPorSecao();
                } 
                //se escolher ( SECAO - TIPO )
                if(indiceSecao >=0 && indiceTipo == -1) {
                    btnExcel.setEnabled(true);
                } else{
                    btnExcel.setEnabled(false);
                }                      
            }

            //libera o botao de impressao somente se selecionar o tipo e a seção
            if( totalRegs > 0 ){
                btnImprimir.setEnabled(true);
                btnFILTRAR.setEnabled(false);
            }else{
                btnImprimir.setEnabled(false);
                btnExcel.setEnabled(false);
            }
            
        }else if (indiceSecao == -1 && indiceTipo == -1){            
            JOptionPane.showMessageDialog(null, "Escolha uma seção ou um tipo ao lado para filtrar!", "Seção ou tipo não selecionado", 2);
            
            totalRegs=0;
            if(totalRegs==0){
                btnVoltarActionPerformed(null);
                btnFILTRAR.setEnabled(true);
                
            }
        }
        filtrou  = true;      
        cmbFILTRARPORSECAO  .setEnabled(false);
        cmbFILTRAPORTIPO    .setEnabled(false);
        btnFILTRAR          .setEnabled(false);
        btnVoltar           .setEnabled(true);
        btnVoltar           .setText("Voltar");
        btnPesquisar        .setEnabled(false);        
        btnCadSemRede       .setEnabled(false);        
        
    }//GEN-LAST:event_btnFILTRARActionPerformed

    private void jTabbedPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane2MouseClicked
        //se clicou na aba INATIVOS
        if (jTabbedPane2.getSelectedIndex() == 1) {
            //desabilitar as consultas            
            DesabilitarConsulta();   
            umaBiblio.limparTodosCampos(rootPane);
            btnEditar.setEnabled(false);
            btnVoltar.setEnabled(true);
            btnImprimir.setEnabled(true);
            btnNovo.setEnabled(false);            
            btnExcel.setEnabled(false);
            txtPESQUISA.setEnabled(false);
            txtPESQUISA.setText(null);
            btnHistorico.setEnabled(false);
            btnCadSemRede.setEnabled(false);
            cmbFILTRARPORSECAO.setSelectedIndex(-1);            
            clicouInativos=true;            
            
            if(filtrou){
                btnImprimir.setEnabled(false);
                btnVoltar.setText("Voltar");
                btnVoltar.setEnabled(true);
                LblPesquisaPorCod.setEnabled(false);
            }
            
        } else if (jTabbedPane2.getSelectedIndex() == 0) {
            btnVoltarActionPerformed(null);
            clicouInativos=false;
            btnGravar.setText("Gravar");
            btnCadSemRede.setEnabled(true);
            reativando = false;
            LblPesquisaPorCod.setEnabled(true);
        }
        
    }//GEN-LAST:event_jTabbedPane2MouseClicked

    private void btnLISTARCLIENTESPARAEDITARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLISTARCLIENTESPARAEDITARActionPerformed
        //Sinalizando que esta editando, será utilizado na gravacao
        //JOptionPane.showMessageDialog(rootPane, "TIPO SELECIONADO : "+tipo);
        codTipoVirtual           = umMetodo.getCodigoPassandoString("TBLTIPOS", "tipo", tipo);
        //JOptionPane.showMessageDialog(rootPane, "CODIGO TIPO VIRTUAL SELECIONADO : "+codTipoVirtual);
        itemSelecionadoCadastro  = tipo;
        listouClientesParaEdicao = true;  
        editandoNomeRede         = true;  
        editandoRegistro         = true;        
        txtESTACAO.setEditable(false);
       
        if(tipo.equals("IMPRESSORA")){
            nomeDepartamento = umaBiblio.retornaDepto(cmbFILTRARPORSECAO.getSelectedItem().toString());
            isImpressora     = true;
        }   
        
        nomeDepartamento = umMetodo.retornaDeptoParaEdicaoPeloNomeDoColaborador(txtCLIENTE.getText());      

        F_LISTACLIENTESATIVOS frm = new F_LISTACLIENTESATIVOS(null, true, "");
        frm.setVisible(true);  

        txtCLIENTE.setText(nomeCliente);
        txtRF.setText(rfCliente);        
        cmbFILTRARPORSECAO.setEnabled(false);    
        
        if(userInformatica){
            disponibilizarNomeEstacaoPeloNome();
            txtIP.setText("0");
            txtESTACAO.setText("PGMCGGMC000");
        }else{
            if(editando && tipo.equals("MICRO")){
                if(umMetodo.ConfirmouOperacao("Deseja alterar também o nome da estação", "Alterar nome estação!")){
                    definirNomestacao();
                }
            }
        }               
        
        txtCLIENTE.setText(nomeCliente);
        txtCHAPA.requestFocus();           


    }//GEN-LAST:event_btnLISTARCLIENTESPARAEDITARActionPerformed
        
    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        Pesquisar();
    }//GEN-LAST:event_btnPesquisarActionPerformed
   
   public void Pesquisar()
   {    
        //pesquisa não deverá aceitar valores vazios, nulos ou zero como parâmetro
        while(paramPesquisa == null || paramPesquisa.equals("") || paramPesquisa.equals("0"))   //enquanto nao digitar um valor valido pra pesquisa não sair
        {
            
            paramPesquisa = JOptionPane.showInputDialog(null, "Entre com a chapa, serie, estação, ip ou nome do colaborador!", "Pesquisando Patrimônio", 2);
           
            if (paramPesquisa == null || paramPesquisa.equals("") || paramPesquisa.equals("0"))
            {
                JOptionPane.showMessageDialog(null, "Valor inválido","Entre com um parâmetro válido",2);
            }else{    
                bEncontrou = false;
                paramPesquisa = paramPesquisa.toUpperCase();  //Esta variavel receberá um valor em letras maiusculas  

                //Encontrar o codigo do patrimonio pela serie ou chapa pois a pesquisa de inativados permite pesquisar apenas por esses dois campos
                int ipesqPorCod = umMetodo.retornaCodigoPesq(tabela, "serie", "chapa", paramPesquisa);                

               //Se encontrar o registro e este estiver inativado msg, caso contrario mostrar o registro simplesmente
                if(umMetodo.EInativoPorCodigo(tabela,ipesqPorCod)){
                   jTabbedPane2.setSelectedIndex(1);
                   JOptionPane.showMessageDialog(null, "Este Patrimônio encontra-se inativado no momento!","Patrimônio inativo",2);                  
                }

                verificarRetornoDeRegistrosNaSql(paramPesquisa); 

                btnPesquisar.setEnabled(false);                
                btnVoltar.setEnabled(true);
                btnNovo.setEnabled(false);
                btnExcel.setEnabled(false);
                btnImprimir.setEnabled(false);
                btnCadSemRede.setEnabled(false);
                btnVoltar.setToolTipText("Limpar pesquisa");
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
                      +"p.tipoid=t.codigo and p.clienteid=c.codigo and p.secaoid=s.codigo and p.modeloid=m.codigo and p.ip='"+pParam+"'";         
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
                        +"p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.serie='"+pParam+"'";         
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
                        + "p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.chapa='"+pParam+"'";         
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
                           +"p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.estacao like '%"+pParam+"%'";       
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
                               +"p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.tipoid=1 and c.nome like '%"+pParam+"%'";         
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
            JOptionPane.showMessageDialog(null, "Patrimônio não encontrado ou inativado!"); 
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
                        + "p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.ip='"+pParam+"'";
                    break;
                    
                case 4:   //refere-se ao numero da coluna SERIE no bd e não ao indice
                    sql = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
                        + "p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.serie='"+pParam+"'";
                    break;
                    
                case 5:   //refere-se ao numero da coluna CHAPA no bd e não ao indice
                    sql = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
                        + "p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.chapa='"+pParam+"'";
                    break;                                    
                
                case 6:   //refere-se ao numero da coluna ESTACAO no bd e não ao indice
                    sql = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
                        + "p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.status = 'ATIVO' and p.estacao like '%"+pParam+"%'";
                    break; 
                    
                case 8:   //refere-se ao numero da coluna NOME DO CLIENTE no bd e não ao indice OBS: esta pesquisa retorna somente os micros do usuario
                    sql = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
                        + "p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.tipoid=1 and c.nome like '%"+pParam+"%'";  
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
    
    private void popularComboBoxModelos(){
        //preenche a combo dos modelos de acordo com o tipo de equipamento que estiver sendo cadastrado e filtrar qdo de contrato somente as do contrato vigente
        if(cadastrando || cadastrandoEstacaoForaRede)
        {             
            int tipoIdEquipto = umMetodo.getCodigoPassandoString("TBLTIPOS", "tipo", tipo); 
            int empresaId     = umMetodo.getCodigoEmpresaAtualContratoImpressoras();
            
            if(isDeContrato){
                 sql="SELECT DISTINCT m.codigo, m.modelo, p.modeloid, p.empresaid from tblmodelos m, tblpatrimonios p where m.codigo=p.modeloid and m.tipoid="+tipoIdEquipto+" and p.empresaid = '"+empresaId+"'";
            }else{
                sql="Select modelo From tblModelos WHERE tipoid="+tipoIdEquipto+" Order by modelo";                
            }
            
            umaBiblio.PreencherComboVariandoTipo(cmbMODELOS, sql, "modelo");  
            
            cmbMODELOS.setSelectedIndex(-1); //primeiro item vazio obrigando a escolher um item
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
                JOptionPane.showMessageDialog(null, "Erro ao pesquisar IP disponiveis!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
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
            
    private void txtOBSERVACOESFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOBSERVACOESFocusGained
        txtDESCRICAO.setCaretPosition(0); //setando a primeira linha do JTextArea 
        controleMostraDescricao=0;   
        if( txtOBSERVACOES.getText().equals("") ){
            if(cadastrando){
              umMetodo.novaLinhaComData(txtOBSERVACOES);
            }
        }
         if(editando){            
            //alterando o ponteiro do mouse indicando que o campo esta editável
            txtOBSERVACOES.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }
    }//GEN-LAST:event_txtOBSERVACOESFocusGained

    private void cmbMODELOSItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMODELOSItemStateChanged
        //System.out.println("VALOR DO CONTADOR ALTERANDOO ITEM...: "+contador);        
        if((contador != 0)&&(contador !=2)){
                int codigo = umaBiblio.buscarCodigoGenerico("tblmodelos", "modelo", cmbMODELOS.getSelectedItem().toString());
                mostrarDescricao(codigo);            
        }        
    }//GEN-LAST:event_cmbMODELOSItemStateChanged

    private void txtCLIENTEFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCLIENTEFocusGained
        //se estiver cadastrando MONITOR ou IMPRESSORA
        if((cadastrando) && (controleMostraDescricao==0) && (!tipo.equals("MICRO"))){
            abrirListaClientes();             
        }  
        if(editando){
            txtCHAPA.requestFocus();   
        }        
    }//GEN-LAST:event_txtCLIENTEFocusGained

    private void btnLISTARCLIENTESPARAEDITARFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnLISTARCLIENTESPARAEDITARFocusGained
        // TODO add your handling code here:
        if(listouClientesParaEdicao){
            txtCLIENTE.requestFocus();
        }
    }//GEN-LAST:event_btnLISTARCLIENTESPARAEDITARFocusGained

    private void txtIPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIPKeyReleased
        alterouIP = true;
    }//GEN-LAST:event_txtIPKeyReleased
  
    private void abrirListaEstacoesDisponiveis(){        
    //Abre lista de estações disponiveis para edição do nome de rede  
    //JOptionPane.showMessageDialog(rootPane, nomeDepartamento);
    //JOptionPane.showMessageDialog(rootPane, tipo);
    umMetodo.atualizarESTACOESCOMZERO();
    
    nomeDepartamento = umMetodo.retornaDepto(cmbFILTRARPORSECAO.getSelectedItem().toString());
        
    if(nomeDepartamento.equals("BIBLIOTECA")){
          nomeDepartamento = "CEJUR";
    }
    //System.out.println(nomeDepartamento);
    if(umMetodo.temNomesEstacoesDisponiveis(nomeDepartamento)){                
       
        if( editando && (tipo.equals("MICRO") || tipo.equals("NOTEBOOK")) )
        {    
            /*Abre o formulario com a lista de nomes de estações disponiveis para uso que devera ser do tipo JDialog e Modal
            (ou seja o programa para ao abrir o form e segue qdo fechado)*/
            editandoDisponiveis   = true;
            nomestacaosubstituida = txtESTACAO.getText(); 
            
            F_LISTAESTACOESDISPONIVEIS frm = new F_LISTAESTACOESDISPONIVEIS(new JFrame(),true);
            frm.setVisible(true); 

            //JOptionPane.showMessageDialog(null, nomeEstacaoAtual);

            //recebe o valor da estação selecionada e seta no txtESTACAO
            txtESTACAO.setText(frm.getNomeEstacaoSelecionada());                
            alterouEstacao      = true;
            alterandonomestacao = true;            
            txtCHAPA.requestFocus();               
        }           
              
    }
}
    
    private void listarIPImpressorasContrato(){
        if(tipo.equals("IMPRESSORA")){
            /*Abre o formulario com a lista de IPS disponiveis para uso que devera ser do tipo JDialog e Modal
            (ou seja o programa para ao abrir o form e segue qdo fechado)*/
            txtIP.setEnabled(true);
            txtIP.setEditable(false);
            
            F_LISTAIPSPARAIMPRESSORAS frm = new F_LISTAIPSPARAIMPRESSORAS(new JFrame(),true);
            frm.setVisible(true); 
            
            //recebe o valor da estação selecionada e seta no txtESTACAO
            
            txtIP.setText(frm.getItemSelecionado());
            txtCHAPA.requestFocus();
        }
    }
    
    private void btnHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistoricoActionPerformed
        serieEquipamento = txtSERIE.getText();
        tipoEquipamento  = txtTIPO.getText();
        
        F_CONSHISTORICO frm = new F_CONSHISTORICO(new JFrame(),true);
        frm.setVisible(true); 
        
        //btnHistorico.setEnabled(false);
        btnVoltar.setEnabled(true);
    }//GEN-LAST:event_btnHistoricoActionPerformed
       
    private void gerarIPFicticio(){
        //caso a impressora for uma impressora que não estará na rede, cadastre-a normalmente e gere um IP Ficticio 
        //para que a mesma não fique sem ip cadastrado o ip cadastrado nela será devolvido e disponibilizado automaticamente
        txtIP.setText(null);
        Long n = new Long("0123456789");                                    //novo long com tamanho 9
        GerarNumerosAleatorios rf = new GerarNumerosAleatorios(n);          //passo o long
        txtIP.setText(String.valueOf(rf.getNumeroAleatorioIP()));
        txtIP.selectAll();
        txtCHAPA.requestFocus();       
        
    }
    
    private void gerarChapaFicticia(){
        //caso o equipamento não tenha número de chapa gere uma Chapa Ficticia 
        txtCHAPA.setText(null);
        Long n = new Long("0123456789");                                    //novo long com tamanho 9
        GerarNumerosAleatorios rf = new GerarNumerosAleatorios(n);          //passo o long
        txtCHAPA.setText(String.valueOf(rf.getNumeroAleatorioIP()));
        txtCHAPA.selectAll();
        txtSERIE.requestFocus();    
    }
        
    private void btnGerarObsAdicionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarObsAdicionalActionPerformed
        //JOptionPane.showMessageDialog(this, "VALOR DO CONT : "+cont);
        if(cont <=1)
        {
            novaObservacao = JOptionPane.showInputDialog(null, "Entre com sua observação adicional!", "Observação Adicional", 2); 
            while (novaObservacao == null || novaObservacao.equals("")) 
            {
                JOptionPane.showMessageDialog(this, "Digite um texto válido!");  
                novaObservacao = JOptionPane.showInputDialog(null, "Entre com sua observação adicional!", "Observação Adicional", 2); 
            }               
            novaObservacao = df.format(dataDia)+" : Cadastro inicial. \n"+df.format(dataDia)+" : "+umMetodo.primeiraLetraMaiuscula(novaObservacao); 
            entrouNovaObs = true;
            btnGerarObsAdicional.setText("Del Observação");
            JOptionPane.showMessageDialog(this, "Observação adicional inserida com sucesso para os demais cadastros,\n delete  no  mesmo  botão  caso  não  deseje  mais  o  texto  adicionado!", "Observação Adicional", 2); 
            cont++;
            cont++;
        }else{
            entrouNovaObs = false;
            btnGerarObsAdicional.setText("Add Observação");
            cont = 0;
        }
    }//GEN-LAST:event_btnGerarObsAdicionalActionPerformed

    private void cmbFILTRAPORTIPOItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbFILTRAPORTIPOItemStateChanged
        selecionouTipo=true;
    }//GEN-LAST:event_cmbFILTRAPORTIPOItemStateChanged

    private void GerarExceldePatrimonios(){
       /*retorna todos os campos da tabela ideal quando tiver chaves estrangeiras na tabela você passa a SQL completa dos dados
         importante salientar que não podem haver dados nulos nos registros 
        
        Trata-se apenas dos patrimonios da PMSP e não das impressoras do contrato portanto não aparecerão nesta listagem*/
       
        int idSecao = umaBiblio.buscarCodigoSecao(cmbFILTRARPORSECAO.getSelectedItem().toString());
        nomeSecao   = cmbFILTRARPORSECAO.getSelectedItem().toString();
        
        GerarExcelPatrimonios excel = new GerarExcelPatrimonios();    
        
        sql =  "select s.nome as secao, t.tipo, m.modelo, p.serie, p.chapa, p.estacao, c.nome as usuario from " +
               "tblpatrimonios p, tblsecoes s, tblclientes c, tblmodelos m, tbltipos t where " +
               "p.clienteid=c.codigo and p.secaoid=s.codigo and p.modeloid=m.codigo and t.codigo= p.tipoid and " +
               "p.contrato='N' and p.status='ATIVO' and s.codigo= "+idSecao+" " +
               "order by t.tipo, s.nome";
                
        lstListaCampos.clear();   
        umMetodo.preencherArrayListComCampos(lstListaCampos, sql);
        
        ArrayList<Object[]> dataList = excel.getListaDados(sql);        
        if (dataList != null && dataList.size() > 0) {
            excel.doExportar(dataList);
        } else {
            JOptionPane.showMessageDialog(null, "Não há dados disponíveis na tabela para exportacao, operação cancelada!", "Erro Fatal, verifique a SQL!", 2);
        }
        umGravarLog.gravarLog("impressao de relatorio : "+umMetodo.retornaNomeTabela(tabela)); 
    }
    
    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        totalRegs=0;
        int indiceSecaoSelecionada = cmbFILTRARPORSECAO.getSelectedIndex();      
        if(indiceSecaoSelecionada == -1)
        {
             JOptionPane.showMessageDialog(null, "Escolha uma seção acima para gerar o relatório!", "Seção não selecionada", 2);
             cmbFILTRAPORTIPO.setSelectedIndex(-1);
            
        }else{
            GerarExceldePatrimonios();
        }   
        btnExcel.setEnabled(false);
        btnVoltar.setEnabled(true);
        btnVoltar.setText("Voltar");
        
    }//GEN-LAST:event_btnExcelActionPerformed

    private void filtrarPorDigitacao(String pPesq) {
        
        if(!clicouInativos){        
           
            PreencherTabelaATIVOS("select p.*, t.*, c.nome as cliente, s.nome as secao from tblpatrimonios p, tbltipos t, tblclientes c, tblsecoes s "
            + "where (c.nome like '%" + pPesq + "%'" + "OR s.nome like '%" + pPesq + "%'" + " OR p.serie like '%" + pPesq + "%'" + " OR p.ip like '%" + pPesq + "%'"+ " OR p.chapa like '%" + pPesq + "%'" + " OR p.estacao like '%" + pPesq + "%'"+") and s.codigo=p.secaoid "
            + "and p.tipoid=t.codigo and p.clienteid=c.codigo and p.status = 'ATIVO' ");
            this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);
            LblPesquisaPorCod.setEnabled(false);
            
                /**TODO O PROCEDIMENTO ABAIXO SERVE PARA QUANDO A PESQUISAR RETORNAR UM PATRIMONIO QUE ESTEJA INATIVADO AVISANDO O USUÁRIO**/
                //Encontrar o codigo do patrimonio pela serie ou chapa pois a pesquisa de inativados permite pesquisar apenas por esses dois campos
                int ipesqPorCod = umMetodo.retornaCodigoPesq(tabela, "serie", "chapa", pPesq);
                
                String sqlCod = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
                      +"p.tipoid=t.codigo and p.clienteid=c.codigo and p.secaoid=s.codigo and p.modeloid=m.codigo and p.codigo="+ipesqPorCod;  
                
               conexao.conectar();
               conexao.ExecutarPesquisaSQL(sqlCod); 
                try {
                    if (conexao.rs.next())
                    {
                        numeroColuna = 1;                   //refere-se ao numero da coluna CODIGO no bd e não ao indice  
                        bEncontrou   = true;
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
                
               //Se encontrar o registro e este estiver inativado msg, caso contrario mostrar o registro simplesmente
                if(umMetodo.EInativoPorCodigo(tabela,ipesqPorCod)){
                   jTabbedPane2.setSelectedIndex(1);
                   PreencherTabelaINATIVOS(sqlCod);
                    mostrarDadosRegSelecionado(ipesqPorCod);
                   JOptionPane.showMessageDialog(null, "Este Patrimônio encontra-se inativado no momento!","Patrimônio inativo",2);   
                }                
            
        }else if(clicouInativos){
            PreencherTabelaINATIVOS("select p.*, t.*, c.nome as cliente, s.nome as secao from tblpatrimonios p, tbltipos t, tblclientes c, tblsecoes s "
            + "where (c.nome like '%" + pPesq + "%'" + "OR s.nome like '%" + pPesq + "%'" + " OR p.serie like '%" + pPesq + "%'" + " OR p.chapa like '%" + pPesq + "%'" + " OR p.estacao like '%" + pPesq + "%'"+") and s.codigo=p.secaoid "
            + "and p.tipoid=t.codigo and p.clienteid=c.codigo and p.status = 'INATIVO' ");
            this.setTitle("Total de registros inativos retornados pela pesquisa = "+totalRegs);
        }       
        
        if( totalRegs > 0 )btnImprimir.setEnabled(true);
        filtrou=true;
        cmbFILTRARPORSECAO.setSelectedItem(nomeSecao);
        btnImprimir.setEnabled(true);  
        btnVoltar.setEnabled(true);
        btnNovo.setEnabled(false);
        btnCadSemRede.setEnabled(false);
        btnImprimir.setEnabled(false);
        btnExcel.setEnabled(false);
        btnPesquisar.setEnabled(false);
        btnGravar.setEnabled(false);
        txtNUMESTACAO.setEnabled(false);
        btnHistorico.setEnabled(true);
        LblPesquisaPorCod.setEnabled(false);
        DesabilitarConsulta();    
        
    }
    
    private void txtPESQUISAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyReleased
        filtrarPorDigitacao(txtPESQUISA.getText());        
    }//GEN-LAST:event_txtPESQUISAKeyReleased

    private void txtPESQUISAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyPressed
        //se teclar enter estando dentro da pesquisa limpar a pesquisa
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtPESQUISA.setText(null);             
        }
    }//GEN-LAST:event_txtPESQUISAKeyPressed

    private void txtPESQUISAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPESQUISAMouseClicked
        //apagar campo de pesquisa mostrar todos os registros 
        if (!clicouInativos) {
            txtPESQUISA.setText(null);        
            btnVoltarActionPerformed(null);
            txtPESQUISA.requestFocus();
            btnCadSemRede.setEnabled(false);                
        }
        LblPesquisaPorCod.setEnabled(false);
        
    }//GEN-LAST:event_txtPESQUISAMouseClicked

    private void txtESTACAOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtESTACAOMouseClicked
        //ao clicar no TXTESTACAO abrir a tela de estações disponiveis para edição do nome de rede        
        nomestacaosubstituida = txtESTACAO.getText(); 
        if(editando)
        {
            if(umMetodo.ConfirmouOperacao("Deseja alterar o nome de rede desta estação?", "Alteração do nome de rede"))
            {     
                abrirListaEstacoesDisponiveis();
                
            }else{
                btnVoltarActionPerformed(null);
            }
        }
    }//GEN-LAST:event_txtESTACAOMouseClicked

    private void txtOBSERVACOESMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtOBSERVACOESMouseClicked
       if(editando || cadastrando){
           umMetodo.novaLinhaComData(txtOBSERVACOES);
       }
    }//GEN-LAST:event_txtOBSERVACOESMouseClicked

    private void jTabelaATIVOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaATIVOSMouseClicked
        /*passando o codigo do patrimonio para o metodo => (int) jTabelaATIVOS.getValueAt(jTabelaATIVOS.getSelectedRow(), 0)
        onde o zero é a coluna que detem o valor que se deseja nesse caso o codigo (coluna 0)*/

        codigoRegSelecionado = (int) jTabelaATIVOS.getValueAt(jTabelaATIVOS.getSelectedRow(), 0);
        codParaHistorico     = codigoRegSelecionado;                
        
        mostrarDadosRegSelecionado(codigoRegSelecionado);
        cmbFILTRARPORSECAO.setSelectedItem(umMetodo.getStringPassandoCodigo("tblsecoes", "nome", idSecao));
   
        codTipoid = 0;
        temIP     = false;        
        codTipoid = umMetodo.buscarCodigoGenerico("tbltipos", "tipo", txtTIPO.getText());
        if(umMetodo.tipoTemIPeloCodigo(codTipoid)){
            temIP=true;            
        }

        txtCODIGO.setForeground(Color.red);
        txtCODIGO.requestFocus();
        nomeClienteOLD = txtCLIENTE.getText();
        nomeESTACAOOLD = txtESTACAO.getText();
        numIPOLD = txtIP.getText();
        if(cmbSTATUS.getSelectedItem().toString().equals("ATIVO")) btnEditar.setEnabled(true);
        btnVoltar.setEnabled(true);
        btnNovo.setEnabled(false);
        btnCadSemRede.setEnabled(false);
        btnImprimir.setEnabled(false);
        txtPESQUISA.setEnabled(false);
        btnExcel.setEnabled(false);
        btnPesquisar.setEnabled(false);
        btnGravar.setEnabled(false);
        txtNUMESTACAO.setEnabled(false);
        btnHistorico.setEnabled(true);
        LblPesquisaPorCod.setEnabled(false);
        DesabilitarConsulta();        
        txtTIPO.setEnabled(true);
        txtTIPO.setEditable(false);
        clicouNaTabela           = true;
        cadastrando              = false;
        contador                 = 0;
        controleMostraDescricao  = 0;
        nomeInicial              = txtESTACAO.getText();
        motivoInicial            = umMetodo.getMotivoPatrimonio(codigoRegSelecionado);
        //JOptionPane.showMessageDialog(rootPane, "NOME INICIAL : "+nomeInicial);
        txtDESCRICAO.setCaretPosition(0); //setando a primeira linha do JTextArea        
        
        if(clicouNaTabela && filtrou){
            filtrouClicou = true;
        }
        nomeDepartamento = umaBiblio.retornaDepto(cmbFILTRARPORSECAO.getSelectedItem().toString());
        //JOptionPane.showMessageDialog(null, "DEPARTAMENTO: "+nomeDepartamento);        
         
    }//GEN-LAST:event_jTabelaATIVOSMouseClicked

    private void jTabelaINATIVOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaINATIVOSMouseClicked
        //passando o codigo do patrimonio para o metodo => (int) jTabelaATIVOS.getValueAt(jTabelaATIVOS.getSelectedRow(), 0) é igual ao codigo do patrimonio selecionado
        codParaHistorico = (int) jTabelaINATIVOS.getValueAt(jTabelaINATIVOS.getSelectedRow(), 0);
        mostrarDadosRegSelecionado(codParaHistorico);
        txtCODIGO.setForeground(Color.red);
        DesabilitarConsulta();
        btnGravar.setEnabled(true);
        btnHistorico.setEnabled(true);
        btnVoltar.setEnabled(true);
        txtNUMESTACAO.setEnabled(false);
        txtPESQUISA.setEnabled(false);
        btnExcel.setEnabled(false);
        btnImprimir.setEnabled(false);
        txtIP.setEnabled(true);
        txtIP.setEditable(false);
        txtTIPO.setEnabled(true);
        txtTIPO.setEditable(false);
        contador  = 0;
        reativando = true;
        btnGravar.setText("Reativar");    
        LblPesquisaPorCod.setEnabled(false);

    }//GEN-LAST:event_jTabelaINATIVOSMouseClicked

    private void txtSERIEFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSERIEFocusLost
         //VERIFICAR PELO NUMERO DE SERIE SE ESTE MICRO ESTA INATIVADO
        if(cadastrando || cadastrandoEstacaoForaRede){ 
            if(umaBiblio.registroInativado(txtSERIE.getText(), txtCHAPA.getText()))
            {
                JOptionPane.showMessageDialog(null, "Atenção patrimônio cadastrado ou inativado, pesquise reative ou designe-o para outro usuário!", "Registros Inativados ou Cadastrados!", 2); 
                btnVoltarActionPerformed(null);
            };
            if(txtSERIE.getText().equals(""))
            {
               txtSERIE.setText("008"+umMetodo.gerarNumeroAleatorio());
            }
        }
    }//GEN-LAST:event_txtSERIEFocusLost

    private void txtIPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtIPMouseClicked
        txtIP.selectAll();
    }//GEN-LAST:event_txtIPMouseClicked

    private void btnCadSemRedeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadSemRedeActionPerformed
        if(umaBiblio.permissaoLiberada())
        {
            cadastrandoEstacaoForaRede  = true;  
            editando                    = false;            
            selecionouCliente           = false;       
            cadastrando                 = false; 
            salvandoreativado           = false; 
            reativando                  = false; 
            editando                    = false; 
            cadastrandoEquipamento      = false;

            //CONTROLANDO OS BOTOES
            btnNovo.setEnabled(false);
            btnEditar.setEnabled(false);
            btnImprimir.setEnabled(false);
            btnExcel.setEnabled(false);
            btnSair.setEnabled(false);
            btnVoltar.setEnabled(true);
            btnVoltar.setText("Cancelar");
            btnPesquisar.setEnabled(false);
            btnCadSemRede.setEnabled(false);
            btnGerarObsAdicional.setEnabled(true); 

            //LIBERANDO OS TXT PARA EDIÇÃO
            umaBiblio.limparTodosCampos(rootPane);
            txtCODIGO.setEditable(false);   
            txtTIPO.setEditable(false);  
            txtSERIE.setEditable(true);        
            txtCHAPA.setEditable(true);
            txtESTACAO.setEnabled(true);
            txtESTACAO.setEditable(false);
            txtOBSERVACOES.setEditable(true);
            txtCLIENTE.setEnabled(true);
            cmbFILTRARPORSECAO.setEnabled(false);
            cmbFILTRAPORTIPO.setEnabled(false);
            btnFILTRAR.setEnabled(false);
            txtCODIGO.setText(String.valueOf(umaBiblio.mostrarProximoCodigo(tabela)));    
            PreencherTabelaATIVOS(sqlVazia);   
            
            //PREENCHENDO OS VALORES PADRÕES PARA GRAVAÇÃO
            txtSERIE.setText(txtSERIE.getText()); 
            txtCHAPA.setText(txtCHAPA.getText()); 
            txtTIPO.setText("MICRO");                
            txtESTACAO.setText("PGMCGGMC000");            
            txtIP.setText("0");        
            txtCONTRATO.setText("NAO");            
            txtCLIENTE.setText("SEM USUARIO INFORMATICA");
            txtRF.setText("1333333");   
            txtCHAPA.requestFocus();  
            
            //Se nao gerar obs add cadastra este
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
            txtOBSERVACOES.setText(df.format(dataDia)+" : Cadastro inicial.");
            
            //Gera um numero aleatório para CHAPA caso não tenha pra digitar, se tiver digite por cima
            umMetodo.gerarNumeroAleatorioParaCampoTexto(txtCHAPA); 
           
            //habilita o combo de modelos 
            cmbMODELOS.setEnabled(true);   
            
            isMicro           = true;   
            tipo              = "MICRO";
            
            //POPULANDO O COMBO COM OS TIPO DE MODELOS
            popularComboBoxModelos();            
            
        }
        
    }//GEN-LAST:event_btnCadSemRedeActionPerformed

    public boolean mostrarDadosPesquisaPorCod(int pParam)
    {        
        //pesquisar um patrimonio pelo CODIGO setando os edits, nao usei o next porque se clicou na tabela o unico registro com certeza existe
        try
        {           
            //Recupera o numero e nome da coluna
            ResultSetMetaData meta = conexao.rs.getMetaData();    
            
            switch(numeroColuna) //Escolher a sql conforme o numero e consequentemente o nome da coluna 
            {
                                    
                case 1:   //refere-se ao numero da coluna CODIGO no bd e não ao indice
                    sql = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
                        + "p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.codigo="+pParam;
                    break;
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
    
    public boolean mostrarDadosPesquisaPorIP(int pParam)
    {        
        //pesquisar um patrimonio pelo IP setando os edits, nao usei o next porque se clicou na tabela o unico registro com certeza existe
        try
        {           
            //Recupera o numero e nome da coluna
            ResultSetMetaData meta = conexao.rs.getMetaData();    
            
            switch(numeroColuna) //Escolher a sql conforme o numero e consequentemente o nome da coluna 
            {
                                    
                case 1:   //refere-se ao numero da coluna CODIGO no bd e não ao indice
                    sql = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
                        + "p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.ip="+pParam;
                    break;
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
    
    public void verificarRetornoDeRegistrosNaSqlPorCod(int pParam)
    {
       /*neste metodo eu verifico qual das colunas me retorna dados e seto um numero de coluna para aquela que tiver registros
         o numero da coluna corresponde a ordem em que ela se encontra na tabela do banco de dados, exemplo: iniciando em 1 para o codigo*/
        
       String sqlCod = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
                      +"p.tipoid=t.codigo and p.clienteid=c.codigo and p.secaoid=s.codigo and p.modeloid=m.codigo and p.codigo="+pParam;         
       conexao.conectar();
       conexao.ExecutarPesquisaSQL(sqlCod); 
        try {
            if (conexao.rs.next())
            {
                numeroColuna = 1;                   //refere-se ao numero da coluna CODIGO no bd e não ao indice  
                bEncontrou   = true;
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
        
        if(bEncontrou){
            
            ipesqPorCod = Integer.parseInt(paramPesqCod);
            mostrarDadosPesquisaPorCod(ipesqPorCod);  
            
            if(umMetodo.EInativoPorCodigo(tabela,ipesqPorCod)){
               JOptionPane.showMessageDialog(null, "Este Patrimônio encontra-se inativado no momento!","Patrimônio inativo",2);
               jTabbedPane2.setSelectedIndex(1); //setando a tabela de INATIVOS
               PreencherTabelaINATIVOS(sqlCod);  //mostrando somente o registro encontrado na tabela INATIVOS
            }
        }else{           
            JOptionPane.showMessageDialog(null, "Patrimônio não encontrado!");             
            PreencherTabelaATIVOS(sqlVazia);
        }
        LblPesquisaPorCod.setEnabled(false);
    }
    
    public void verificarRetornoDeRegistrosNaSqlPorIP(int pParam)
    {
       /*neste metodo eu verifico qual das colunas me retorna dados e seto um numero de coluna para aquela que tiver registros
         o numero da coluna corresponde a ordem em que ela se encontra na tabela do banco de dados, exemplo: iniciando em 1 para o codigo*/
        
       String sqlIP = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
                      +"p.tipoid=t.codigo and p.clienteid=c.codigo and p.secaoid=s.codigo and p.modeloid=m.codigo and p.ip="+pParam;         
       conexao.conectar();
       conexao.ExecutarPesquisaSQL(sqlIP); 
        try {
            if (conexao.rs.next())
            {
                numeroColuna = 1;                   //refere-se ao numero da coluna CODIGO no bd e não ao indice  
                bEncontrou   = true;
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
        
        if(bEncontrou){
            
            ipesqPorIP = Integer.parseInt(paramPesqIP);
            mostrarDadosPesquisaPorIP(ipesqPorIP);  
            
            if(umMetodo.EInativoPorCodigo(tabela,ipesqPorIP)){
               JOptionPane.showMessageDialog(null, "Este Patrimônio encontra-se inativado no momento!","Patrimônio inativo",2);
               jTabbedPane2.setSelectedIndex(1); //setando a tabela de INATIVOS
               PreencherTabelaINATIVOS(sqlIP);  //mostrando somente o registro encontrado na tabela INATIVOS
            }
        }else{           
            JOptionPane.showMessageDialog(null, "Patrimônio não encontrado!"); 
            PreencherTabelaATIVOS(sqlVazia);
        }         
    }
    
    public void PesquisarPorCod()
    {                  
        while(paramPesqCod == null || paramPesqCod.equals("") || paramPesqCod.equals("0") || paramPesqCod.length() > 9) 
        {            
            paramPesqCod = JOptionPane.showInputDialog(null, "Entre com o código do equipamento até 9 digitos!", "Pesquisa por Código", 2);
            
            if( (paramPesqCod == null ) || (paramPesqCod.equals("")) || (paramPesqCod.equals("0")) || (paramPesqCod.length() > 9) ) 
            {
                JOptionPane.showMessageDialog(null, "Entre com o código do equipamento!","O valor digitado não é válido!",2);
            }else{

                if (!paramPesqCod.trim().matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Digite apenas o código do equipamento!", "Somente números!",JOptionPane.ERROR_MESSAGE);
                    PreencherTabelaATIVOS(sqlVazia);
                    DesabilitarConsulta();
                    paramPesqCod = null;
                }else{
                    bEncontrou = false;
                    ipesqPorCod = Integer.parseInt(paramPesqCod);
                    verificarRetornoDeRegistrosNaSqlPorCod(ipesqPorCod);                    
                    btnPesquisar.setEnabled(false);                
                    btnVoltar.setEnabled(true);
                    btnNovo.setEnabled(false);
                    btnExcel.setEnabled(false);
                    btnImprimir.setEnabled(false);
                    btnCadSemRede.setEnabled(false);
                    btnVoltar.setToolTipText("Limpar pesquisa");
                    DesabilitarConsulta();
                } 
            }            
       }
       paramPesqCod = null;
    }           
            
    private void LblPesquisaPorCodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LblPesquisaPorCodMouseClicked
        PesquisarPorCod();
    }//GEN-LAST:event_LblPesquisaPorCodMouseClicked

    private void LblPesquisaPorCodMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LblPesquisaPorCodMouseMoved
        //ao passar o mouse sobre o label mudar o cursor para forma de mao
        LblPesquisaPorCod.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));        
    }//GEN-LAST:event_LblPesquisaPorCodMouseMoved

    private void txtCHAPAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCHAPAFocusLost
        if(txtCHAPA.getText().equals(""))
        {
           txtCHAPA.setText("009"+umMetodo.gerarNumeroAleatorio());
        }
    }//GEN-LAST:event_txtCHAPAFocusLost

    private void cmbFILTRARPORSECAOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFILTRARPORSECAOActionPerformed
        itemSelecionadoNaComboPorSecao();          
    }//GEN-LAST:event_cmbFILTRARPORSECAOActionPerformed
     
    private void itemSelecionadoNaComboPorSecao(){
        if(cont == 1){            
            btnCadSemRede.setEnabled(false);
            btnNovo.setEnabled(false);
            btnPesquisar.setEnabled(false);
            btnHistorico.setEnabled(false);
            btnExcel.setEnabled(false);
            btnImprimir.setEnabled(false);
            cont = 0;
        }
    }
    
    private void cmbFILTRARPORSECAOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbFILTRARPORSECAOMouseClicked
        cont = 1;
    }//GEN-LAST:event_cmbFILTRARPORSECAOMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //verificando se tem contrato de impressora ativo
        umMetodo.TemContratoImpressoraAtivo();
    }//GEN-LAST:event_formWindowOpened
        
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
        btnGravar.setEnabled(true);
        contador++;
        
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
                nomeSecao = conexao.rs.getString("secao");
            };    
            
            if(totalRegs == 0){btnImprimir.setEnabled(false);}

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
            jTabelaATIVOS.getColumnModel().getColumn(4).setPreferredWidth(130);
            jTabelaATIVOS.getColumnModel().getColumn(4).setResizable(false);
            jTabelaATIVOS.getColumnModel().getColumn(5).setPreferredWidth(110);
            jTabelaATIVOS.getColumnModel().getColumn(5).setResizable(false);
            jTabelaATIVOS.getColumnModel().getColumn(6).setPreferredWidth(300);
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

    public void PreencherTabelaINATIVOS(String sql) 
    {
        DateFormat df     = new SimpleDateFormat("dd/MM/yyyy");
                
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        //String[] Colunas = new String[]{"Código", "Tipo", "Série", "Chapa", "Motivo da inativação", "Inativado em:"};
        String[] Colunas = new String[]{"Código", "Tipo", "Série", "Chapa", "Motivo da inativação"};
        try 
        {  
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next())
            {               
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("tipo"),
                    conexao.rs.getString("serie"),
                    conexao.rs.getString("chapa"),
                    conexao.rs.getString("motivo")
                    //df.format(conexao.rs.getDate("datainativacao"))
                });
                totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
                nomeEstacao = conexao.rs.getString("estacao");
             };                
             
                ModeloTabela modelo = new ModeloTabela(dados, Colunas);
                jTabelaINATIVOS.setModel(modelo);
                //define tamanho das colunas
                jTabelaINATIVOS.getColumnModel().getColumn(0).setPreferredWidth(80);  //define o tamanho da coluna
                jTabelaINATIVOS.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
                jTabelaINATIVOS.getColumnModel().getColumn(1).setPreferredWidth(130);
                jTabelaINATIVOS.getColumnModel().getColumn(1).setResizable(false);
                jTabelaINATIVOS.getColumnModel().getColumn(2).setPreferredWidth(130);  //define o tamanho da coluna
                jTabelaINATIVOS.getColumnModel().getColumn(2).setResizable(false);    //nao será possivel redimencionar a coluna 
                jTabelaINATIVOS.getColumnModel().getColumn(3).setPreferredWidth(130);
                jTabelaINATIVOS.getColumnModel().getColumn(3).setResizable(false);
                jTabelaINATIVOS.getColumnModel().getColumn(4).setPreferredWidth(430);
                jTabelaINATIVOS.getColumnModel().getColumn(4).setResizable(false);                

                //define propriedades da tabela
                jTabelaINATIVOS.getTableHeader().setReorderingAllowed(false);        //nao podera ser reorganizada
                jTabelaINATIVOS.setAutoResizeMode(jTabelaINATIVOS.AUTO_RESIZE_OFF);          //nao será possivel redimencionar a tabela
                jTabelaINATIVOS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  

        } catch (SQLException ex) {
                //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
                JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList da tabela INATIVOS!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LblPesquisaPorCod;
    private javax.swing.JButton btnCadSemRede;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnFILTRAR;
    private javax.swing.JButton btnGerarObsAdicional;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnHistorico;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnLISTARCLIENTESPARAEDITAR;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<String> cmbFILTRAPORTIPO;
    private javax.swing.JComboBox<String> cmbFILTRARPORSECAO;
    private javax.swing.JComboBox<String> cmbMODELOS;
    private javax.swing.JComboBox<String> cmbSTATUS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTabelaATIVOS;
    private javax.swing.JTable jTabelaINATIVOS;
    private javax.swing.JLabel lblMODELO;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTextField txtCHAPA;
    private javax.swing.JTextField txtCLIENTE;
    private javax.swing.JTextField txtCODIGO;
    private javax.swing.JTextField txtCONTRATO;
    private javax.swing.JTextArea txtDESCRICAO;
    private javax.swing.JTextField txtESTACAO;
    public javax.swing.JTextField txtIP;
    private javax.swing.JTextField txtNUMESTACAO;
    private javax.swing.JTextArea txtOBSERVACOES;
    private javax.swing.JTextField txtPESQUISA;
    private javax.swing.JTextField txtRF;
    private javax.swing.JTextField txtSERIE;
    private javax.swing.JTextField txtTIPO;
    // End of variables declaration//GEN-END:variables
}