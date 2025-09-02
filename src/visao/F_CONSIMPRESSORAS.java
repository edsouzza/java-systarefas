package visao;

import Dao.DAOImpressora;
import Dao.DAOPatrimonio;
import biblioteca.AbrirURL;
import biblioteca.Biblioteca;
import biblioteca.CampoLimitadoParaCHAPA;
import biblioteca.CampoLimitadoParaIP;
import biblioteca.CopiarParaClipboard;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import conexao.ConnConexao;
import controle.CtrlPatrimonio;
import biblioteca.TudoMaiusculas;
import static biblioteca.VariaveisPublicas.anoVigente;
import static biblioteca.VariaveisPublicas.dataDoDia;
import controle.CtrlCliente;
import static biblioteca.VariaveisPublicas.totalRegs;
import static biblioteca.VariaveisPublicas.nomeCliente;
import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.tabela;
import static biblioteca.VariaveisPublicas.codigoUsuario;
import controle.ControleGravarLog;
import controle.CtrlEmpresa;
import controle.CtrlPatriItenstransferido;
import controle.CtrlPatriTransferido;
import controle.CtrlSecoes;
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.Cliente;
import modelo.Impressora;
import modelo.PatriTensTransferido;
import modelo.PatriTransferido;
import modelo.Patrimonio;
import modelo.Secao;
import relatorios.GerarRelatorios;

public class F_CONSIMPRESSORAS extends javax.swing.JFrame {

    ConnConexao                 conexao                         = new ConnConexao();
    Biblioteca                  umabiblio                       = new Biblioteca();
    MetodosPublicos             umMetodo                        = new MetodosPublicos();
    Patrimonio                  umModPatrimonio                 = new Patrimonio();
    Cliente                     umModeloCliente                 = new Cliente();
    CtrlCliente                 umControleCliente               = new CtrlCliente();
    CtrlPatrimonio              umControlePatrimonio            = new CtrlPatrimonio();
    ControleGravarLog           umGravarLog                     = new ControleGravarLog();
    DAOPatrimonio               patrimonioDAO                   = new DAOPatrimonio();
    DAOImpressora               impressoraDAO                   = new DAOImpressora();
    Secao                       umModeloSecao                   = new Secao();
    CtrlSecoes                  umControleSecao                 = new CtrlSecoes();
    AbrirURL                    umaURL                          = new AbrirURL();
    CopiarParaClipboard         umTexto                         = new CopiarParaClipboard();
    PatriTensTransferido        objModPatriTemTransferido       = new PatriTensTransferido();
    CtrlPatriItenstransferido   ctrlPatriTenstransferido        = new CtrlPatriItenstransferido();
    PatriTransferido            umModPatriTransferido           = new PatriTransferido();
    CtrlPatriTransferido        umCtrlPatriTranferido           = new CtrlPatriTransferido();
    CtrlEmpresa                 umCtrlEmpresa                   = new CtrlEmpresa();
    
    String modelo, numemo, status, chapa, origem, destino, empresa, assunto, observacao, descricaoDeReativacao ,motivoReativacao, patrimonio, situacao, nomeCli, nomeTipo, nomeClienteOLD, motivoInativacao, serie, paramPesquisa, nomeColuna, nomeEstacao, ip, descricaoDeInativacao, tipo, paramPesqIP  = null; 
    int codigo, codModelo, codEmpresa, valorItem, idClienteRegSel, ind, idSecao, numeroColuna, idModelo, codigoSecao, codigoModelo = 0;
    boolean filtrouPorModelo, filtrouPorSecao, reativando,gravando, editando, flag, alterouStatus, bEncontrou, clicouNaTabela, escolheuModelo,filtrou, naoMicro, clicouInativos, filtrouClicou;  //controla no botão gravar entre gravar novo registro e gravar alteração de um registro    
    
    String sqlDefaultATIVOS     = "select p.*, c.nome as cliente, s.nome as secao, m.*,t.* from tblpatrimonios p, tblclientes c, tblsecoes s, tbltipos t, "
                                + "tblmodelos m where p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.status='ATIVO' and t.tipo='IMPRESSORA' and p.empresaid > 0";
    
    String sqlSemContratoATIVOS = "select p.*, c.nome as cliente, s.nome as secao, m.*,t.* from tblpatrimonios p, tblclientes c, tblsecoes s, tbltipos t, "
                                + "tblmodelos m where p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.status='ATIVO' and t.tipo='IMPRESSORA' and p.empresaid = 0";
    
    
    String sqlDefaultINATIVOS   = "select p.*, t.*, c.nome as cliente, s.nome as secao, m.* from tblpatrimonios p, tblclientes c, tblsecoes s, tbltipos t,"
                                + "tblmodelos m where p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.status='INATIVO' and t.tipo='IMPRESSORA' and p.empresaid > 0";
    
    String sqlVazia             = "select * from tblpatrimonios where codigo = 0";
    
    public F_CONSIMPRESSORAS() {
        initComponents();
        setResizable(false);   //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        this.setTitle(this.mostrarTituloDoFormulario());
        Leitura();

        // Colocando enter para pular de campo 
        HashSet conj = new HashSet(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);       
        
        //configuracoes dos edits 
        umabiblio.configurarCamposTextos(txtCODIGO);
        umabiblio.configurarCamposTextos(txtSERIE);
        umabiblio.configurarCamposTextos(txtIP);
        umabiblio.configurarCamposTextos(txtCHAPA);        
        umabiblio.configurarCamposTextos(txtEMPRESA);        
        umabiblio.configurarCamposTextos(txtCLIENTE);
        txtDESCRICAO.setFont(new Font("TimesRoman", Font.BOLD, 12));
        txtDESCRICAO.setForeground(Color.blue);
        txtDESCRICAO.setEditable(false);
        txtIP.setDocument(new CampoLimitadoParaIP());
        txtCHAPA.setDocument(new CampoLimitadoParaCHAPA());
        txtCODIGO.setForeground(Color.red);
        txtOBSERVACOES.setForeground(Color.red);
        txtEMPRESA.setForeground(Color.red);
        txtOBSERVACOES.setDocument(new TudoMaiusculas());
        cmbMODELOS.setFont(new Font("TimesRoman", Font.BOLD, 12));
        cmbSECOES.setFont(new Font("TimesRoman", Font.BOLD, 12));
        txtOBSERVACOES.setFont(new Font("TimesRoman", Font.BOLD, 12));
        PreencherTabelaATIVOS(sqlDefaultATIVOS);
        PreencherTabelaINATIVOS(sqlDefaultINATIVOS);
        PreencherTabelaSemContrato(sqlSemContratoATIVOS);
        
        //configuração dos botões        
        umabiblio.configurarBotoes(btnSair);
        
        //preenchendo a combo dos modelos somente com impressoras e registros do bd
        PreencherComboModeloImpressoras(cmbMODELOS, "modelo");
        PreencherComboSecoes(cmbSECOES, "secao");

        jTabelaATIVOS2.setFont(new Font("TimesRoman", Font.BOLD, 12));
        jTabelaATIVOS2.setForeground(Color.blue);
        jTabelaINATIVOS.setFont(new Font("TimesRoman", Font.BOLD, 12));
        jTabelaINATIVOS.setForeground(Color.red);
        jTabelaSEMCONTRATO.setFont(new Font("TimesRoman", Font.BOLD, 12));
        jTabelaSEMCONTRATO.setForeground(Color.blue);
        txtCODIGO.setForeground(Color.red);
        txtCLIENTE.setForeground(Color.red);

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
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDESCRICAO = new javax.swing.JTextArea();
        txtSERIE = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCODIGO = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtCHAPA = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCLIENTE = new javax.swing.JTextField();
        txtIP = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cmbMODELOS = new javax.swing.JComboBox<>();
        txtOBSERVACOES = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnImprimirTudo = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTabelaATIVOS2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTabelaINATIVOS = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTabelaSEMCONTRATO = new javax.swing.JTable();
        btnLimparPesquisa = new javax.swing.JButton();
        cmbSECOES = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        btnFiltroModeloSecao = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        btnPorModelo = new javax.swing.JButton();
        btnPorSecao = new javax.swing.JButton();
        btnConsultarPorSerie = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtEMPRESA = new javax.swing.JTextField();
        btnImprimirAtivas = new javax.swing.JButton();
        btnInativarContrato = new javax.swing.JButton();
        btnConsPorIP = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consultando Impressoras");
        setPreferredSize(new java.awt.Dimension(870, 670));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelPrincipal.setPreferredSize(new java.awt.Dimension(1024, 733));

        jLabel8.setText("MODELO");

        txtDESCRICAO.setEditable(false);
        txtDESCRICAO.setColumns(20);
        txtDESCRICAO.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        txtDESCRICAO.setRows(5);
        txtDESCRICAO.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDESCRICAOFocusGained(evt);
            }
        });
        jScrollPane1.setViewportView(txtDESCRICAO);

        txtSERIE.setEditable(false);
        txtSERIE.setForeground(new java.awt.Color(51, 51, 255));
        txtSERIE.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSERIEFocusGained(evt);
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

        txtCHAPA.setEditable(false);
        txtCHAPA.setForeground(new java.awt.Color(51, 51, 255));

        jLabel4.setText("SERIE");

        txtCLIENTE.setForeground(new java.awt.Color(51, 51, 255));

        txtIP.setEditable(false);
        txtIP.setForeground(new java.awt.Color(51, 51, 255));

        jLabel5.setText("IP");

        jLabel11.setText("SEÇÃO");

        cmbMODELOS.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        cmbMODELOS.setForeground(new java.awt.Color(51, 51, 255));
        cmbMODELOS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<ESCOLHA O MODELO>", " " }));
        cmbMODELOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbMODELOS.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMODELOSItemStateChanged(evt);
            }
        });

        txtOBSERVACOES.setForeground(new java.awt.Color(51, 51, 255));

        jLabel12.setText("OBSERVAÇÕES");

        jLabel13.setText("DESCRIÇAO");

        btnImprimirTudo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_imprimir.gif"))); // NOI18N
        btnImprimirTudo.setText("Imprimir Cadastradas");
        btnImprimirTudo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImprimirTudo.setPreferredSize(new java.awt.Dimension(77, 25));
        btnImprimirTudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirTudoActionPerformed(evt);
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

        jTabbedPane4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabbedPane4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane4MouseClicked(evt);
            }
        });

        jTabelaATIVOS2.setGridColor(new java.awt.Color(255, 255, 255));
        jTabelaATIVOS2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaATIVOS2MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTabelaATIVOS2);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 887, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("ATIVAS", jPanel7);

        jTabelaINATIVOS.setGridColor(new java.awt.Color(255, 255, 255));
        jTabelaINATIVOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaINATIVOSMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTabelaINATIVOS);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 887, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("INATIVAS", jPanel2);

        jTabelaSEMCONTRATO.setGridColor(new java.awt.Color(255, 255, 255));
        jTabelaSEMCONTRATO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaSEMCONTRATOMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTabelaSEMCONTRATO);

        jTabbedPane4.addTab("SEM CONTRATO", jScrollPane6);

        btnLimparPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_limpar.gif"))); // NOI18N
        btnLimparPesquisa.setText("Limpar Pesquisa");
        btnLimparPesquisa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimparPesquisa.setEnabled(false);
        btnLimparPesquisa.setPreferredSize(new java.awt.Dimension(77, 25));
        btnLimparPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparPesquisaActionPerformed(evt);
            }
        });

        cmbSECOES.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        cmbSECOES.setForeground(new java.awt.Color(51, 51, 255));
        cmbSECOES.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "<ESCOLHA A SEÇÃO>", "" }));
        cmbSECOES.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbSECOES.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSECOESItemStateChanged(evt);
            }
        });

        jLabel9.setText("SEÇOES");

        btnFiltroModeloSecao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_pesquisa.gif"))); // NOI18N
        btnFiltroModeloSecao.setText("Modelo+Seção");
        btnFiltroModeloSecao.setToolTipText("Pesquisa o Modelo e a Seção do equipamento");
        btnFiltroModeloSecao.setActionCommand("");
        btnFiltroModeloSecao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFiltroModeloSecao.setEnabled(false);
        btnFiltroModeloSecao.setPreferredSize(new java.awt.Dimension(77, 25));
        btnFiltroModeloSecao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltroModeloSecaoActionPerformed(evt);
            }
        });

        jLabel10.setText("FILTRAR");

        btnPorModelo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_imprimir.gif"))); // NOI18N
        btnPorModelo.setText("Modelo");
        btnPorModelo.setToolTipText("Por Modelo");
        btnPorModelo.setActionCommand("");
        btnPorModelo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPorModelo.setPreferredSize(new java.awt.Dimension(77, 25));
        btnPorModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPorModeloActionPerformed(evt);
            }
        });

        btnPorSecao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_blocoNotas.gif"))); // NOI18N
        btnPorSecao.setText("Seção");
        btnPorSecao.setToolTipText("Por Seção");
        btnPorSecao.setActionCommand("");
        btnPorSecao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPorSecao.setPreferredSize(new java.awt.Dimension(77, 25));
        btnPorSecao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPorSecaoActionPerformed(evt);
            }
        });

        btnConsultarPorSerie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_pesquisa.gif"))); // NOI18N
        btnConsultarPorSerie.setText("Consultar Pela Série");
        btnConsultarPorSerie.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConsultarPorSerie.setPreferredSize(new java.awt.Dimension(77, 25));
        btnConsultarPorSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarPorSerieActionPerformed(evt);
            }
        });

        jLabel2.setText("EMPRESA DO CONTRATO");

        txtEMPRESA.setEditable(false);
        txtEMPRESA.setForeground(new java.awt.Color(51, 51, 255));

        btnImprimirAtivas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_imprimir.gif"))); // NOI18N
        btnImprimirAtivas.setText("Imprimir Ativas");
        btnImprimirAtivas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImprimirAtivas.setPreferredSize(new java.awt.Dimension(77, 25));
        btnImprimirAtivas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirAtivasActionPerformed(evt);
            }
        });

        btnInativarContrato.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sairPrograma.gif"))); // NOI18N
        btnInativarContrato.setText("Inativar Contrato");
        btnInativarContrato.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInativarContrato.setPreferredSize(new java.awt.Dimension(77, 25));
        btnInativarContrato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInativarContratoActionPerformed(evt);
            }
        });

        btnConsPorIP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/computador.png"))); // NOI18N
        btnConsPorIP.setText("Por IP");
        btnConsPorIP.setToolTipText("Por IP");
        btnConsPorIP.setActionCommand("");
        btnConsPorIP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConsPorIP.setPreferredSize(new java.awt.Dimension(77, 25));
        btnConsPorIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsPorIPActionPerformed(evt);
            }
        });

        jLabel14.setText("CONSULTAR IP");

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelPrincipalLayout.createSequentialGroup()
                                .addComponent(btnImprimirTudo, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnImprimirAtivas, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnConsultarPorSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnInativarContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelPrincipalLayout.createSequentialGroup()
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(txtOBSERVACOES, javax.swing.GroupLayout.PREFERRED_SIZE, 593, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtEMPRESA, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnConsPorIP, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jTabbedPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelPrincipalLayout.createSequentialGroup()
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(cmbMODELOS, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPorModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(108, 108, 108))
                                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                                        .addComponent(cmbSECOES, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnPorSecao, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addGap(113, 113, 113))
                                    .addComponent(btnFiltroModeloSecao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelPrincipalLayout.createSequentialGroup()
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(txtCLIENTE, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                                        .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtSERIE, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(123, 123, 123)
                                        .addComponent(jLabel4)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(txtCHAPA, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(84, 84, 84))))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbSECOES, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnPorModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnFiltroModeloSecao, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnPorSecao, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(7, 7, 7)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSERIE, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCHAPA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(panelPrincipalLayout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cmbMODELOS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(37, 37, 37))
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCLIENTE, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))))
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtOBSERVACOES, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelPrincipalLayout.createSequentialGroup()
                                    .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel14))
                                    .addGap(37, 37, 37))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                                    .addGap(19, 19, 19)
                                    .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtEMPRESA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnConsPorIP, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnImprimirTudo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConsultarPorSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnImprimirAtivas, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnInativarContrato, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        getContentPane().add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, 680));

        setSize(new java.awt.Dimension(934, 725));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void PreencherComboSecoes(JComboBox cmb,String campo)
    {
        conexao.conectar();   
        sql="select distinct s.nome as secao from tblsecoes s, tblpatrimonios p, tbltipos t where s.status='ATIVO' and p.tipoid=t.codigo "
          + "and p.tipoid=3 and p.secaoid=s.codigo order by s.nome";
        conexao.ExecutarPesquisaSQL(sql);               
        try 
        {                       
            cmb.removeAllItems();
            while (conexao.rs.next()) {
                 cmb.addItem(conexao.rs.getString(campo));
                 cmb.setSelectedIndex(-1);
            };    
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao preencher o combo de seções!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        } 
    }
    private void PreencherComboModeloImpressoras(JComboBox cmb, String campo)
    {
        conexao.conectar();   
        //sql="Select modelo From tblModelos WHERE tipoid=3 and status='ATIVO' Order by modelo";
        sql="Select distinct m.modelo From tblModelos m, tblpatrimonios p WHERE p.tipoid=3 and p.modeloid= m.codigo order by m.modelo";
        conexao.ExecutarPesquisaSQL(sql);              
        try 
        {                       
            cmb.removeAllItems();
            while (conexao.rs.next()) {
                 cmb.addItem(conexao.rs.getString(campo));
                 cmb.setSelectedIndex(-1);
            };    
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao preencher o combo de seções!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        } 
    }
    
    public int qdeImpressorasCadastradas()
    {        
       conexao.conectar();
        sql = "select count(codigo) as total from "+tabela+" where tipoid=3";
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
    public int qdeImpressorasCadastradasAtivas()
    {        
         conexao.conectar();
        sql = "select count(codigo) as total from "+tabela+" where tipoid=3 and status='ATIVO'";
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
    public int qdeImpressorasCadastradasInativas()
    {        
        conexao.conectar();
        sql = "select count(codigo) as total from "+tabela+" where tipoid=3 and status='INATIVO'";
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
        int qdeImpressoras  =  qdeImpressorasCadastradas();
        int qdeAtivos       =  qdeImpressorasCadastradasAtivas();
        int qdeInativos     =  qdeImpressorasCadastradasInativas();
       
        if(qdeImpressoras == 1){
           return "Equipamentos de Informática : "+qdeImpressoras+" impressora cadastrada"; 
        }else{
           return "Equipamentos de Informática : "+qdeImpressoras+" impressoras cadastradas -> "+qdeAtivos+" ativas e "+qdeInativos+" inativas";    
        } 
        
    }

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnImprimirTudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirTudoActionPerformed
        if(clicouInativos){
            //se clicou na aba inativos imprime todas as impressoras inativadas
            GerarRelatorios objRel = new GerarRelatorios();
            try {
                objRel.imprimirImpressorasInativas("relatorio/relimpressorasinativas.jasper");
                btnLimparPesquisaActionPerformed(null);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!"+e);                
            }         
        }else{if(filtrou){
              if(filtrouPorSecao){
                //se filtrou por seção imprime relatorio da secao selecionada
                GerarRelatorios objRel = new GerarRelatorios();
                try {
                    objRel.imprimirImpressorasSecaoSelecionada("relatorio/relimpressorasdasecaoselecionada.jasper",codigoSecao);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!"+e);                
                }      
              }else if(filtrouPorModelo){
                //se filtrou por modelo imprime relatorio do modelo selecionado
                GerarRelatorios objRel = new GerarRelatorios();
                try {
                    objRel.imprimirImpressorasModeloSelecionado("relatorio/relimpressorasdomodeloselecionado.jasper",codigoModelo);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!"+e);                
                }      
              } 
        }else{if(!filtrou && !clicouInativos){
            //se nao filtrou nada imprime relatorio de todas as secoes cadastradas
            GerarRelatorios objRel = new GerarRelatorios();
            try {
                objRel.imprimirImpressorasCadastradas("relatorio/relimpressorascadastradas.jasper");
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!"+e);                
            }
        }}}
        filtrou=false;        
    }//GEN-LAST:event_btnImprimirTudoActionPerformed
       
    private void Leitura()
    {

        if (umabiblio.tabelaVazia(tabela)) {
            btnImprimirTudo.setEnabled(false);            
        } else {
            btnImprimirTudo.setEnabled(true);
            PreencherTabelaATIVOS(sqlDefaultATIVOS);
        }
        umabiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 

        btnSair.setEnabled(true);
        txtCLIENTE.setEditable(false);
        txtCODIGO.setEditable(false);
        txtCHAPA.setEditable(false);        
        txtCODIGO.setEditable(false);
        txtSERIE.setEditable(false);
        txtOBSERVACOES.setEditable(false);
        txtIP.setEditable(false);  
        cmbMODELOS.setSelectedIndex(-1);
        cmbSECOES.setSelectedIndex(-1);
        btnFiltroModeloSecao.setEnabled(false);   
        
    }
              
    private void txtSERIEFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSERIEFocusGained
        txtSERIE.selectAll();
    }//GEN-LAST:event_txtSERIEFocusGained

    private void txtCODIGOFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCODIGOFocusGained
        txtCODIGO.setEditable(false);
    }//GEN-LAST:event_txtCODIGOFocusGained
  
    private void mostrarNomeClienteParaCadastro(){
        if ((gravando) || (flag)) {
            txtCLIENTE.setText(nomeCliente);
        } else {
            txtCLIENTE.setText(nomeClienteOLD);
        }
    }
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
                
        if(!umMetodo.TemImpressorasDeContratoAtivas()){
            
            if(umMetodo.ConfirmouOperacao("O contrato  da  empresa anterior foi finalizado, deseja cadastrar a nova empresa\n agora? Se já o fez clique em não, saia e cadastre os equipamentos da mesma!", "Contrato de impressoras finalizado")){              
                  dispose(); 
                  tabela = "TBLEMPRESA";     
                  F_EMPRESA frm = new F_EMPRESA(this,true);
                  frm.setVisible(true);                     
             }            
        }else{      
            conexao.conectar();
            conexao.ExecutarPesquisaSQL(sqlDefaultATIVOS);
            try {
                if (conexao.rs.next()) {   //selecionando a primeira linha somente se tiver registros
                    jTabelaATIVOS2.addRowSelectionInterval(0, 0);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela ATIVOS!\nErro: " + ex.getMessage());
            } finally {
                conexao.desconectar();
            }      
        }
        
         
        
    }//GEN-LAST:event_formWindowOpened
    public void mostrarDadosRegSelecionado(int codPatrimonio)
    {
        //pesquisar um patrimonio pelo numero do codigo setando os edits,nao use o next porque se clicou na tabela o registro com certeza existe
        //Obs: no caso dos inativos não precisa mostrar a seção pois não estão mais na PGM em nenhuma seçao

        sql = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.*, e.*  FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t, tblempresa e WHERE "
                     +"p.tipoid=t.codigo and e.codigo = p.empresaid and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.codigo='" + codPatrimonio + "'";
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        try
        {
            if( conexao.rs.next())
            {
                conexao.rs.first();
                txtCODIGO.setText(String.valueOf(conexao.rs.getInt("codigo")));
                txtSERIE.setText(conexao.rs.getString("serie"));
                txtIP.setText(conexao.rs.getString("ip"));            
                txtCHAPA.setText(conexao.rs.getString("chapa"));
                txtEMPRESA.setText(conexao.rs.getString("nome"));
                txtDESCRICAO.setText(conexao.rs.getString("descricao"));
                txtCLIENTE.setText(conexao.rs.getString("cliente"));  
                txtOBSERVACOES.setText(conexao.rs.getString("observacoes"));   
                                             
                //setando o modelo na combobox do patrimonio escolhido
                String modelo = conexao.rs.getString("modelo");    
                cmbMODELOS.setSelectedItem(modelo);
                
                //setando a seção na combobox do patrimonio escolhido
                String secao = conexao.rs.getString("secao");    
                cmbSECOES.setSelectedItem(secao);
                
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar selecionar os dados dos registro!\nERRO:" + ex.getMessage());
        } finally {
            conexao.desconectar();
        }
    }    
    
     public void mostrarDadosRegSelecionadoSemContrato(int codPatrimonio)
    {
        //pesquisar um patrimonio pelo numero do codigo setando os edits,nao use o next porque se clicou na tabela o registro com certeza existe
        //Obs: no caso dos inativos não precisa mostrar a seção pois não estão mais na PGM em nenhuma seçao

//        sql = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.*, e.nome  FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t, tblempresa e WHERE "
//                     +"p.tipoid=t.codigo and e.codigo=p.empresaid and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.contrato='N' and  p.empresaid = 0 and p.codigo='" + codPatrimonio + "'";
        sql ="select p.*, c.nome as cliente, s.nome as secao, m.*,t.* from tblpatrimonios p, tblclientes c, tblsecoes s, tbltipos t, "
             +"tblmodelos m where p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo " 
             +"and p.secaoid=s.codigo and p.status='ATIVO' and t.tipo='IMPRESSORA' and p.empresaid = 0 and p.codigo='"+codPatrimonio+"'";
        
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        try
        {
            if( conexao.rs.next())
            {
                conexao.rs.first();
                txtCODIGO.setText(String.valueOf(conexao.rs.getInt("codigo")));
                txtSERIE.setText(conexao.rs.getString("serie"));
                txtIP.setText(conexao.rs.getString("ip"));            
                txtCHAPA.setText(conexao.rs.getString("chapa"));
                txtEMPRESA.setText(conexao.rs.getString("nome"));
                txtDESCRICAO.setText(conexao.rs.getString("descricao"));
                txtCLIENTE.setText(conexao.rs.getString("cliente"));  
                txtOBSERVACOES.setText(conexao.rs.getString("observacoes"));   
                                             
                //setando o modelo na combobox do patrimonio escolhido
                String modelo = conexao.rs.getString("modelo");    
                cmbMODELOS.setSelectedItem(modelo);
                
                //setando a seção na combobox do patrimonio escolhido
                String secao = conexao.rs.getString("secao");    
                cmbSECOES.setSelectedItem(secao);
                
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar selecionar os dados dos registro!\nERRO:" + ex.getMessage());
        } finally {
            conexao.desconectar();
        }
    }    
     
    private void cmbMODELOSItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMODELOSItemStateChanged
        txtDESCRICAO.requestFocus();
        editando=true;
    }//GEN-LAST:event_cmbMODELOSItemStateChanged
        
    public void filtrarPorIP(String pIp)
    {
        nomeTipo        = "IMPRESSORA";
        escolheuModelo  = false;
        filtrou         = false;
        filtrouPorSecao = false;
        
        PreencherTabelaATIVOS("select p.*, c.nome as cliente, s.nome as secao, m.*, t.*, e.nome from tbltipos t, tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tblempresa e "
                            + "where e.codigo=p.empresaid and p.tipoid=t.codigo and s.codigo=p.secaoid and p.modeloid=m.codigo and p.status='ATIVO' and  p.empresaid > 0 and p.clienteid=c.codigo "
                            + "and t.tipo= '" + nomeTipo + "' and p.ip = "+pIp);
        
        if(escolheuModelo)        
            this.setTitle("Total de impressoras "+modelo+" retornadas pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        else
             this.setTitle("Total de impressoras retornadas pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        umabiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 
        cmbMODELOS.setEnabled(false);
        cmbSECOES.setEnabled(false);
    }
    
    public void filtrarPorSecaoAtiva(int idSecao)
    {
        nomeTipo = "IMPRESSORA";
        escolheuModelo = false;
        filtrou=true;
        filtrouPorSecao=true;
        
        PreencherTabelaATIVOS("select p.*, c.nome as cliente, s.nome as secao, m.*, t.*, e.nome from tbltipos t, tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tblempresa e "
                            + "where e.codigo=p.empresaid and p.tipoid=t.codigo and s.codigo=p.secaoid and p.modeloid=m.codigo and p.status='ATIVO' and  p.empresaid > 0 and p.clienteid=c.codigo "
                            + "and t.tipo= '" + nomeTipo + "' and s.codigo= "+idSecao);
        
        if(escolheuModelo)        
            this.setTitle("Total de impressoras "+modelo+" retornadas pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        else
             this.setTitle("Total de impressoras retornadas pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        umabiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 
        cmbMODELOS.setEnabled(false);
        cmbSECOES.setEnabled(false);
    }
    
    public void filtrarPorSerieAtiva(String pSerie)
    {
        nomeTipo = "IMPRESSORA";
        escolheuModelo   =false;
        filtrou          =true;
        filtrouPorSecao  =true;
        
        PreencherTabelaATIVOS("select p.*, c.nome as cliente, s.nome as secao, m.*, t.*, e.nome from tbltipos t, tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tblempresa e "
                            + "where e.codigo=p.empresaid and p.tipoid=t.codigo and s.codigo=p.secaoid and p.modeloid=m.codigo and p.status='ATIVO' and p.empresaid > 0 and p.clienteid=c.codigo "
                            + "and t.tipo= '" + nomeTipo + "' and p.serie = '" + pSerie + "'");
        
        if(escolheuModelo)        
            this.setTitle("Total de impressoras "+modelo+" retornadas pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        else
             this.setTitle("Total de impressoras retornadas pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        umabiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 
        cmbMODELOS.setEnabled(false);
        cmbSECOES.setEnabled(false);
    }
    
    public void filtrarPorSecaoInativa(int idSecao)
    {
        nomeTipo = "IMPRESSORA";
        escolheuModelo = false;
        filtrou=true;
        filtrouPorSecao=true;
        
        PreencherTabelaINATIVOS("select p.*, c.nome as cliente, s.nome as secao, m.*, t.*, e.nome from tbltipos t, tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tblempresa e "
                            + "where e.codigo=p.empresaid and p.tipoid=t.codigo and s.codigo=p.secaoid and p.modeloid=m.codigo and p.status='INATIVO' and  p.empresaid > 0 and p.clienteid=c.codigo "
                            + "and t.tipo= '" + nomeTipo + "' and s.codigo= "+idSecao);
        
        if(escolheuModelo)        
            this.setTitle("Total de impressoras "+modelo+" retornadas pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        else
             this.setTitle("Total de impressoras retornadas pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        umabiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 
        cmbMODELOS.setEnabled(false);
        cmbSECOES.setEnabled(false);
    }
    
    public void filtrarPorModeloAtivo(int idModelo)
    {
        nomeTipo = "IMPRESSORA";
        escolheuModelo = true;
        filtrou=true;
        filtrouPorModelo=true;
        
        PreencherTabelaATIVOS("select p.*, c.nome as cliente, s.nome as secao, m.*, t.*, e.nome from tbltipos t, tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tblempresa e "
                            + "where e.codigo=p.empresaid and p.tipoid=t.codigo and s.codigo=p.secaoid and p.modeloid=m.codigo and p.status='ATIVO' and p.empresaid > 0 and p.clienteid=c.codigo and "
                            + "t.tipo= '" + nomeTipo + "' and m.codigo= "+idModelo);
        
        if(escolheuModelo)        
            this.setTitle("Total de impressoras "+modelo+" retornadas pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        else
             this.setTitle("Total de impressoras retornadas pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        umabiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 
        cmbMODELOS.setEnabled(false);
        cmbSECOES.setEnabled(false);
    }
    
    public void filtrarPorModeloInativo(int idModelo)
    {
        nomeTipo = "IMPRESSORA";
        escolheuModelo = true;
        filtrou=true;
        filtrouPorModelo=true;
        
        PreencherTabelaINATIVOS("select p.*, c.nome as cliente, s.nome as secao, m.*, t.*, e.nome from tbltipos t, tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tblempresa e "
                            + "where e.codigo=p.empresaid and p.tipoid=t.codigo and s.codigo=p.secaoid and p.modeloid=m.codigo and p.status='INATIVO' and p.empresaid > 0 and p.clienteid=c.codigo and "
                            + "t.tipo= '" + nomeTipo + "' and m.codigo= "+idModelo);
        
        if(escolheuModelo)        
            this.setTitle("Total de impressoras "+modelo+" retornadas pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        else
             this.setTitle("Total de impressoras retornadas pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        umabiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 
        cmbMODELOS.setEnabled(false);
        cmbSECOES.setEnabled(false);
    }
    
    public void filtrarPorModeloSecaoAtivo(int idModelo, int idSecao)
    {
        nomeTipo = "IMPRESSORA";
        escolheuModelo = true;
        filtrou=true;
        filtrouPorModelo=true;
        
        PreencherTabelaATIVOS("select p.*, c.nome as cliente, s.nome as secao, m.*, t.*, e.nome from tbltipos t, tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tblempresa e "
                            + "where e.codigo=p.empresaid and p.tipoid=t.codigo and s.codigo=p.secaoid and p.modeloid=m.codigo and p.status='ATIVO' and p.empresaid > 0 and p.clienteid=c.codigo and "
                            + "t.tipo= '" + nomeTipo + "' and m.codigo= "+idModelo+" and s.codigo = "+idSecao);
        
        if(escolheuModelo)        
            this.setTitle("Total de impressoras "+modelo+" retornadas pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        else
             this.setTitle("Total de impressoras retornadas pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        umabiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 
        cmbMODELOS.setEnabled(false);
        cmbSECOES.setEnabled(false);
    }
    
    public void filtrarPorModeloSecaoInativo(int idModelo, int idSecao)
    {
        nomeTipo = "IMPRESSORA";
        escolheuModelo = true;
        filtrou=true;
        filtrouPorModelo=true;
        
        PreencherTabelaATIVOS("select p.*, c.nome as cliente, s.nome as secao, m.*, t.*, e.nome from tbltipos t, tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tblempresa e "
                            + "where e.codigo=p.empresaid and p.tipoid=t.codigo and s.codigo=p.secaoid and p.modeloid=m.codigo and p.status='INATIVO' and p.empresaid > 0 and p.clienteid=c.codigo and "
                            + "t.tipo= '" + nomeTipo + "' and m.codigo= "+idModelo+" and s.codigo = "+idSecao);
        
        if(escolheuModelo)        
            this.setTitle("Total de impressoras "+modelo+" retornadas pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        else
             this.setTitle("Total de impressoras retornadas pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        umabiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 
        cmbMODELOS.setEnabled(false);
        cmbSECOES.setEnabled(false);
    }
    
    private void txtDESCRICAOFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDESCRICAOFocusGained
        txtOBSERVACOES.requestFocus();
    }//GEN-LAST:event_txtDESCRICAOFocusGained

    private void jTabelaATIVOS2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaATIVOS2MouseClicked
        //passando o codigo do patrimonio para o metodo => (int) jTabelaATIVOS2.getValueAt(jTabelaATIVOS2.getSelectedRow(), 0) é igual ao codigo do patrimonio selecionado
        int codigoRegSelecionado = (int) jTabelaATIVOS2.getValueAt(jTabelaATIVOS2.getSelectedRow(), 0);
        mostrarDadosRegSelecionado(codigoRegSelecionado);

        txtCODIGO.setForeground(Color.red);        
        txtCODIGO.requestFocus();
        nomeClienteOLD = txtCLIENTE.getText();
        btnImprimirAtivas.setEnabled(false);
        btnConsultarPorSerie.setEnabled(false);
        btnImprimirTudo.setEnabled(false);
        btnInativarContrato.setEnabled(false);
        btnLimparPesquisa.setEnabled(true);
        btnFiltroModeloSecao.setEnabled(false);
        cmbMODELOS.setEnabled(false);
        cmbSECOES.setEnabled(false);
        btnPorModelo.setEnabled(false);
        btnPorSecao.setEnabled(false);
        txtIP.setEnabled(true);
        clicouNaTabela = true;
        if(clicouNaTabela && filtrou){
            filtrouClicou = true;
        }

    }//GEN-LAST:event_jTabelaATIVOS2MouseClicked

    private void jTabelaINATIVOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaINATIVOSMouseClicked
        //passando o codigo do patrimonio para o metodo => (int) jTabelaINATIVOS.getValueAt(jTabelaINATIVOS.getSelectedRow(), 0) é igual ao codigo do patrimonio selecionado
        int codigoRegSelecionado = (int) jTabelaINATIVOS.getValueAt(jTabelaINATIVOS.getSelectedRow(), 0);
        mostrarDadosRegSelecionado(codigoRegSelecionado);

        txtCODIGO.setForeground(Color.red);
        txtCODIGO.requestFocus();
        txtCODIGO.setEnabled(false);
        nomeClienteOLD = txtCLIENTE.getText();
        btnImprimirTudo.setEnabled(true);
        txtIP.setEnabled(true);
        btnLimparPesquisa.setEnabled(true);
        btnPorModelo.setEnabled(false);
        btnPorSecao.setEnabled(false);
        txtCODIGO.setEnabled(false);
        clicouNaTabela = true;
        if(clicouNaTabela && filtrou){
            filtrouClicou = true;
        }
    }//GEN-LAST:event_jTabelaINATIVOSMouseClicked

    private void jTabbedPane4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane4MouseClicked
        //se clicou na aba INATIVOS
        if (jTabbedPane4.getSelectedIndex() == 1) {
            umabiblio.limparTodosCampos(rootPane);
            clicouInativos=true;
            reativando = true;
            txtCODIGO.setEnabled(false);
            umabiblio.configurarBotoes(btnSair);
            btnImprimirTudo.setText("Imprimir Inativas");
            btnImprimirAtivas.setEnabled(false);
            btnConsultarPorSerie.setEnabled(false);
            btnLimparPesquisa.setText("Voltar");
            btnLimparPesquisa.setEnabled(true);
        }else {
            clicouInativos=false;
            umabiblio.limparTodosCampos(rootPane);            
            txtCODIGO.setEnabled(false);
            btnLimparPesquisa.setEnabled(true);
        }
        
         if (jTabbedPane4.getSelectedIndex() == 2) {
            btnImprimirTudo.setEnabled(false);
            btnImprimirAtivas.setEnabled(false);
            btnConsultarPorSerie.setEnabled(false);
            btnLimparPesquisa.setEnabled(true);
        }
        btnInativarContrato.setEnabled(false);
    }//GEN-LAST:event_jTabbedPane4MouseClicked
    private void limparPesquisa(){
        umabiblio.limparTodosCampos(rootPane);
        PreencherTabelaATIVOS(sqlDefaultATIVOS);
        escolheuModelo = false;
        filtrou=false;
        clicouInativos=false;
        this.setTitle(this.mostrarTituloDoFormulario());
        cmbMODELOS.setEnabled(true);
        cmbSECOES.setEnabled(true);
        btnLimparPesquisa.setEnabled(false);         
        jTabbedPane4.setSelectedIndex(0);
        btnLimparPesquisa.setText("Limpar Pesquisa"); 
        btnImprimirTudo.setText("Imprimir Cadastradas");
        filtrouPorModelo=false;
        filtrouPorSecao=false;  
        btnPorModelo.setEnabled(true);
        btnPorSecao.setEnabled(true);
        btnFiltroModeloSecao.setEnabled(false);
        txtCODIGO.setEnabled(true);
        cmbMODELOS.setSelectedIndex(-1);
        cmbSECOES.setSelectedIndex(-1);
        btnImprimirTudo.setEnabled(true);
        btnConsultarPorSerie.setEnabled(true);
        btnImprimirAtivas.setEnabled(true);    
        btnInativarContrato.setEnabled(true);
        btnConsPorIP.setEnabled(true);
    }
    private void btnLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparPesquisaActionPerformed
       limparPesquisa();        
    }//GEN-LAST:event_btnLimparPesquisaActionPerformed

    private void cmbSECOESItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSECOESItemStateChanged
        txtDESCRICAO.requestFocus();
        if(cmbMODELOS.getSelectedIndex() >=0 ){
            btnFiltroModeloSecao.setEnabled(true);
            btnPorModelo.setEnabled(false);
            btnPorSecao.setEnabled(false);
        }
    }//GEN-LAST:event_cmbSECOESItemStateChanged

    private void btnFiltroModeloSecaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltroModeloSecaoActionPerformed
       //filtrando por modelo + secao
       codigoModelo  = umabiblio.buscarCodigoGenerico("tblmodelos","modelo",cmbMODELOS.getSelectedItem().toString());
       codigoSecao  = umabiblio.buscarCodigoGenerico("tblsecoes","nome",cmbSECOES.getSelectedItem().toString());
       if(clicouInativos){
           filtrarPorModeloSecaoInativo(codigoModelo, codigoSecao);
       }else{
           filtrarPorModeloSecaoAtivo(codigoModelo, codigoSecao);
       }
       
       btnLimparPesquisa.setEnabled(true);
       btnPorModelo.setEnabled(false);
       btnPorSecao.setEnabled(false);
       btnFiltroModeloSecao.setEnabled(false);
       txtCODIGO.setEnabled(false);
    }//GEN-LAST:event_btnFiltroModeloSecaoActionPerformed

    private void btnPorModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPorModeloActionPerformed
        //quando perder o foco mostrar descricao atraves do id do patrimonio
       codigoModelo  = umabiblio.buscarCodigoGenerico("tblmodelos","modelo",cmbMODELOS.getSelectedItem().toString());
       if(clicouInativos){
           filtrarPorModeloInativo(codigoModelo);
       }else{
           filtrarPorModeloAtivo(codigoModelo);
       }
       
       btnLimparPesquisa.setEnabled(true);
       btnFiltroModeloSecao.setEnabled(false);
       btnPorModelo.setEnabled(false);
       btnPorSecao.setEnabled(false);
       txtCODIGO.setEnabled(false);
       
    }//GEN-LAST:event_btnPorModeloActionPerformed

    private void btnPorSecaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPorSecaoActionPerformed
        //quando perder o foco mostrar descricao atraves do id do patrimonio
       codigoSecao  = umabiblio.buscarCodigoGenerico("tblsecoes","nome",cmbSECOES.getSelectedItem().toString());
       if(clicouInativos){
           filtrarPorSecaoInativa(codigoSecao);
       }else{
           filtrarPorSecaoAtiva(codigoSecao);
       }
       
       btnLimparPesquisa.setEnabled(true);
       btnImprimirTudo.setEnabled(true);
       btnPorSecao.setEnabled(false);
       btnPorModelo.setEnabled(false);
       txtCODIGO.setEnabled(false);
       
    }//GEN-LAST:event_btnPorSecaoActionPerformed
        
    public void Pesquisar()
    {    
        //pesquisa não deverá aceitar valores vazios, nulos ou zero como parâmetro
        while(paramPesquisa == null || paramPesquisa.equals("") || paramPesquisa.equals("0"))   //enquanto nao digitar um valor valido pra pesquisa não sair
        {
            
            paramPesquisa = JOptionPane.showInputDialog(null, "Entre com serie da Impressora!", "Pesquisando Patrimônio", 2);
           
            if (paramPesquisa == null || paramPesquisa.equals("") || paramPesquisa.equals("0"))
            {
                JOptionPane.showMessageDialog(null, "Valor inválido","Entre com um parâmetro válido",2);
            }else{    
                bEncontrou = false;
                paramPesquisa = paramPesquisa.toUpperCase();  //Esta variavel receberá um valor em letras maiusculas  

                //Encontrar o codigo do patrimonio pela serie ou ip pois a pesquisa de inativados permite pesquisar apenas por esses dois campos
                int ipesqPorCod = umMetodo.retornaCodigoPesq(tabela, "serie", "ip", paramPesquisa);                

               //Se encontrar o registro e este estiver inativado msg, caso contrario mostrar o registro simplesmente
                if(umMetodo.EInativoPorCodigo(tabela,ipesqPorCod)){
                   jTabbedPane4.setSelectedIndex(1);
                   JOptionPane.showMessageDialog(null, "Este Patrimônio encontra-se inativado no momento!","Patrimônio inativo",2);                   
                }else{
                   jTabbedPane4.setSelectedIndex(0); 
                   filtrarPorSerieAtiva(paramPesquisa);
                }
                btnPorModelo.setEnabled(false);
                btnPorSecao.setEnabled(false);
                btnLimparPesquisa.setEnabled(true);
                btnImprimirTudo.setEnabled(false);
                btnConsultarPorSerie.setEnabled(false);
                btnImprimirAtivas.setEnabled(false);
                btnConsPorIP.setEnabled(false);
            }            
        }  
       paramPesquisa = null;
       btnInativarContrato.setEnabled(false);
    }
    
    private void btnConsultarPorSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarPorSerieActionPerformed
        Pesquisar();
    }//GEN-LAST:event_btnConsultarPorSerieActionPerformed

    private void jTabelaSEMCONTRATOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaSEMCONTRATOMouseClicked
        int codigoRegSelecionado = (int) jTabelaSEMCONTRATO.getValueAt(jTabelaSEMCONTRATO.getSelectedRow(), 0);
        mostrarDadosRegSelecionadoSemContrato(codigoRegSelecionado);

        txtCODIGO.setForeground(Color.red);
        txtCODIGO.requestFocus();
        txtCODIGO.setEnabled(false);
        nomeClienteOLD = txtCLIENTE.getText();
        btnImprimirTudo.setEnabled(true);
        btnInativarContrato.setEnabled(false);
        txtIP.setEnabled(true);
        btnLimparPesquisa.setEnabled(true);
        btnPorModelo.setEnabled(false);
        btnPorSecao.setEnabled(false);
        txtCODIGO.setEnabled(false);
        clicouNaTabela = true;
        if(clicouNaTabela && filtrou){
            filtrouClicou = true;
        }
    }//GEN-LAST:event_jTabelaSEMCONTRATOMouseClicked

    private void btnImprimirAtivasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirAtivasActionPerformed
        GerarRelatorios objRel = new GerarRelatorios();
        try {
            objRel.imprimirImpressorasCadastradas("relatorio/relImpressorasativas.jasper");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!"+e);                
        }
    }//GEN-LAST:event_btnImprimirAtivasActionPerformed

    private void disponibilizarIpContratoFinalizado(){
             
        //Preciso da lista de ips da empresa
        int empresaId = umMetodo.getCodigoEmpresaAtualContratoImpressoras();
        //System.out.println("Codigo da empresa : "+empresaId);
        
        ArrayList<String> lstIpsDisponibilizar = new ArrayList<>();
        lstIpsDisponibilizar = patrimonioDAO.listaDeIpsAdisponibilizar(empresaId);
                
        int totalRegs = lstIpsDisponibilizar.size();
        //System.out.println("Total de regs encontrados : "+totalRegs);                
        
        for(int i=0;i<totalRegs;i++){        
            patrimonioDAO.disponibilizarIpsDoContratoFinalizado(lstIpsDisponibilizar.get(i));
        }
        
    }
    
    private void gravarMemorando(){
        //GRAVA O CABECALHO DO MEMORANDO NA TABELA
        
        /*salvando memorando em definitivo ( TBLMEMOSTRANSFERIDOS ) apos gerar o relatorio  
          Nao deixar salvar quando clicado mais de uma vez / So gravar a primeira vez que clicar*/            
        if(!umMetodo.temDuplicidadeDeCadastro("TBLMEMOSTRANSFERIDOS", "numemo", numemo))
        {
            umModPatriTransferido.setNumemo(numemo);

            if(!observacao.equals(null) && !observacao.equals(""))
            {
                umModPatriTransferido.setObservacao("Obs : "+umMetodo.primeiraLetraMaiuscula(observacao));
            }else{
                umModPatriTransferido.setObservacao(" ");
            }

            umModPatriTransferido.setStatus("TRANSFERIDO");
            umModPatriTransferido.setIdusuario(codigoUsuario);
            umModPatriTransferido.setAssunto((assunto).toUpperCase());
            umCtrlPatriTranferido.salvarPatriTransferido(umModPatriTransferido); 
            umGravarLog.gravarLog("cadastro do memo de transferencia de patrimonios "+numemo);
        }        
        
    }
    
    private void inativarImpressorasDoContratoAtual(){
        
        //Inativa todas as impressoras de contrato para cadastro de nova Empresa 
        //Preciso manter os dados que constam em motivo e obs e quebrar linha somente se tiver dados       
        
        Date              dataDia   = dataDoDia; 
        SimpleDateFormat  sdf       = new SimpleDateFormat("dd.MM.yyyy");              
        String motivo               = sdf.format(dataDia)+" : Inativado por conta da troca de empresa e do novo contrato de alocacao.";
        String obs                  = sdf.format(dataDia)+" : Inativado por conta da troca de empresa e do novo contrato de alocacao.";
        
        if(umabiblio.permissaoLiberada()){
            if (umabiblio.ConfirmouOperacao("Confirma Inativação de todas as Impressoras do Contrato Atual?", "Inativação de Impressoras de Contrato Atual")){
                
                umModPatrimonio.setMotivo(motivo);
                umModPatrimonio.setObservacoes(obs); 
                
                //Disponibiliza os ips para uso pos inativação da empresa e seus equiptos
                disponibilizarIpContratoFinalizado();
                
                //GERA MEMORANDO DE ENCAMINHAMENTO AUTOMATICAMENTE
                int empresaId = umMetodo.getCodigoEmpresaAtualContratoImpressoras();   
                
                //Inativar a empresa do ultimo contrato
                umCtrlEmpresa.inativarEmpresaDoContratoImpressoras();
               
                
                numemo     = umMetodo.gerarProximoNumeroMemoTransferir()+"/"+anoVigente;
                empresa    = umMetodo.retornarNome("tblEmpresa", empresaId);
                origem     = "CGGM/INFO";
                destino    = empresa;        
                assunto    = "Devolucao de equipamentos por motivo de encerramento de contrato.";
                observacao = "Devolucao de equipamentos por motivo de encerramento de contrato.";
                status     = "TRANSFERIDO";     
                
                valorItem = 1;
                
                ArrayList<Impressora> lista = new ArrayList<>();
                
                lista = impressoraDAO.listaDeImpressorasContratoFinalizado(empresaId);

                for (Impressora impressora : lista) 
                {
                    
                    //Adicionando os patrimonios ao memorando                    
                    codModelo      = impressora.getModeloId();
                    codEmpresa     = impressora.getEmpresaId();
                    serie          = impressora.getSerie();
                    chapa          = impressora.getChapa();       
                    
                    objModPatriTemTransferido.setItem(valorItem);           
                    objModPatriTemTransferido.setNumemo(numemo);            
                    objModPatriTemTransferido.setModeloid(codModelo);	              
                    objModPatriTemTransferido.setSerie(serie);
                    objModPatriTemTransferido.setChapa(chapa);      	
                    objModPatriTemTransferido.setOrigem(origem);     	
                    objModPatriTemTransferido.setDestino(destino);     	
                    objModPatriTemTransferido.setStatus(status);		

                    ctrlPatriTenstransferido.salvarPatriItensTransferido(objModPatriTemTransferido);
                    
                    umGravarLog.gravarLog("Adicao de itens ao memorando de transferencia "+numemo);    
                    valorItem++;
                    
                }
                                
                //Inativa impressoras da empresa
                umMetodo.inativarImpressorasContrato(umModPatrimonio);
                
                //Grava memorando na tabela  
                gravarMemorando();    

                //Gerando o relatorio mas não abrira na tela
                GerarRelatorios objRel = new GerarRelatorios();
                try {
                    objRel.imprimirPatrimoniosDevolucaoEmpresa("relatorio/relmemotransferidos.jasper", numemo);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao gerar relatório de Patrimônios devolvidos para empresa por motivo do fim de contrato!\n"+e);                
                }                                   
                
                JOptionPane.showMessageDialog(null, "A Empresa do contrato atual de Impressoras foi inativada com sucesso, juntamente com seus\nequipamentos,  observando que foi gerado o memorando de devolução  das  impressoras, se\npossível  cadastre  a  nova  Empresa  para  então  iniciar  o  cadastro de seus equipamentos!!!","Inativação da Empresa e seus equipamentos!",2);
                dispose();                
                
                umGravarLog.gravarLog("Inativacao da empresa atual de contrato de impressoras e seus equipamentos"); 
                                
                PreencherTabelaATIVOS(sqlDefaultATIVOS);  
                
                tabela = "TBLEMPRESA";     
                F_EMPRESA frm1 = new F_EMPRESA(this,true);
                frm1.setVisible(true); 
                
            }   
        }       
    }
    
    private void btnInativarContratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInativarContratoActionPerformed
        
        inativarImpressorasDoContratoAtual();    
        
    }//GEN-LAST:event_btnInativarContratoActionPerformed
    
    public void PesquisarPorIP()
    {                  
        while(paramPesqIP == null || paramPesqIP.equals("") || paramPesqIP.equals("0") || paramPesqIP.length() > 9) 
        {            
            paramPesqIP = JOptionPane.showInputDialog(null, "Entre com o ip do equipamento!", "Pesquisa por IP", 2);
            
            if( (paramPesqIP == null ) || (paramPesqIP.equals("")) || (paramPesqIP.equals("0")) || (paramPesqIP.length() > 9) ) 
            {
                JOptionPane.showMessageDialog(null, "Entre com o ip do equipamento!","O valor digitado não é válido!",2);
            }else{

                if (!paramPesqIP.trim().matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Digite apenas o ip do equipamento!", "Somente números!",JOptionPane.ERROR_MESSAGE);
                    PreencherTabelaATIVOS(sqlVazia);
                    paramPesqIP = null;
                }else{
                    bEncontrou = false;
                    filtrarPorIP(paramPesqIP);
                    
                    btnFiltroModeloSecao.setEnabled(false);
                    btnConsPorIP.setEnabled(false);
                    txtCODIGO.setEnabled(false);                    
                    btnImprimirAtivas.setEnabled(false);
                    btnConsultarPorSerie.setEnabled(false);
                    btnImprimirTudo.setEnabled(false);
                    btnInativarContrato.setEnabled(false);
                    btnLimparPesquisa.setEnabled(true);
                    cmbMODELOS.setEnabled(false);
                    cmbSECOES.setEnabled(false);
                    btnPorModelo.setEnabled(false);
                    btnPorSecao.setEnabled(false);
                } 
            }            
       }
       paramPesqIP = null;
       
    } 
    
    private void btnConsPorIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsPorIPActionPerformed
        PesquisarPorIP();
    }//GEN-LAST:event_btnConsPorIPActionPerformed
   
    private void mostrarDescricao(int codModelo){
        //Mostrando a descricao vinda da tabela de modelos
        sql="select descricao from tblmodelos where codigo="+codModelo;
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        try {
            conexao.rs.first();
            txtDESCRICAO.setText(conexao.rs.getString("descricao"));   
            
        } catch (SQLException ex) {
                //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
                JOptionPane.showMessageDialog(null, "Erro ao tentar pesquisar!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }       
    }

    public void PreencherTabelaATIVOS(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Seção", "Ip", "Série", "Chapa", "Status", "Contrato"};
        try 
        {  
            conexao.ExecutarPesquisaSQL(sql);
            
            while (conexao.rs.next())
            {               
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("secao"),
                    conexao.rs.getString("ip"),
                    conexao.rs.getString("serie"),
                    conexao.rs.getString("chapa"),
                    conexao.rs.getString("status"),
                    conexao.rs.getString("contrato")
                });
                totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
                modelo    = conexao.rs.getString("modelo");
                
             };                

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabelaATIVOS2.setModel(modelo);
            //define tamanho das colunas
            jTabelaATIVOS2.getColumnModel().getColumn(0).setPreferredWidth(75);  //define o tamanho da coluna
            jTabelaATIVOS2.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaATIVOS2.getColumnModel().getColumn(1).setPreferredWidth(170);
            jTabelaATIVOS2.getColumnModel().getColumn(1).setResizable(false);
            jTabelaATIVOS2.getColumnModel().getColumn(2).setPreferredWidth(135);
            jTabelaATIVOS2.getColumnModel().getColumn(2).setResizable(false);
            jTabelaATIVOS2.getColumnModel().getColumn(3).setPreferredWidth(150);
            jTabelaATIVOS2.getColumnModel().getColumn(3).setResizable(false);
            jTabelaATIVOS2.getColumnModel().getColumn(4).setPreferredWidth(150);  //define o tamanho da coluna
            jTabelaATIVOS2.getColumnModel().getColumn(4).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaATIVOS2.getColumnModel().getColumn(5).setPreferredWidth(100);  //define o tamanho da coluna
            jTabelaATIVOS2.getColumnModel().getColumn(5).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaATIVOS2.getColumnModel().getColumn(6).setPreferredWidth(90);  //define o tamanho da coluna
            jTabelaATIVOS2.getColumnModel().getColumn(6).setResizable(false);    //nao será possivel redimencionar a coluna 

            //define propriedades da tabela
            jTabelaATIVOS2.getTableHeader().setReorderingAllowed(false);          //nao podera ser reorganizada
            jTabelaATIVOS2.setAutoResizeMode(jTabelaATIVOS2.AUTO_RESIZE_OFF);      //nao será possivel redimencionar a tabela
            jTabelaATIVOS2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
        
        } catch (SQLException ex) {
                //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
                JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList da tabela ATIVOS!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }  
    }
        
        public void PreencherTabelaINATIVOS(String sql) 
        {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Seção", "Ip", "Série", "Chapa", "Status", "Contrato"};
        try 
        {  
            conexao.ExecutarPesquisaSQL(sql);
            
            while (conexao.rs.next())
            {               
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("secao"),
                    conexao.rs.getString("ip"),
                    conexao.rs.getString("serie"),
                    conexao.rs.getString("chapa"),
                    conexao.rs.getString("status"),
                    conexao.rs.getString("contrato")
                });
                totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
             };                

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabelaINATIVOS.setModel(modelo);
            //define tamanho das colunas
            jTabelaINATIVOS.getColumnModel().getColumn(0).setPreferredWidth(75);  //define o tamanho da coluna
            jTabelaINATIVOS.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaINATIVOS.getColumnModel().getColumn(1).setPreferredWidth(170);
            jTabelaINATIVOS.getColumnModel().getColumn(1).setResizable(false);
            jTabelaINATIVOS.getColumnModel().getColumn(2).setPreferredWidth(135);
            jTabelaINATIVOS.getColumnModel().getColumn(2).setResizable(false);
            jTabelaINATIVOS.getColumnModel().getColumn(3).setPreferredWidth(150);
            jTabelaINATIVOS.getColumnModel().getColumn(3).setResizable(false);
            jTabelaINATIVOS.getColumnModel().getColumn(4).setPreferredWidth(150);  //define o tamanho da coluna
            jTabelaINATIVOS.getColumnModel().getColumn(4).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaINATIVOS.getColumnModel().getColumn(5).setPreferredWidth(90);  //define o tamanho da coluna
            jTabelaINATIVOS.getColumnModel().getColumn(5).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaINATIVOS.getColumnModel().getColumn(6).setPreferredWidth(85);  //define o tamanho da coluna
            jTabelaINATIVOS.getColumnModel().getColumn(6).setResizable(false);    //nao será possivel redimencionar a coluna 

            //define propriedades da tabela
            jTabelaINATIVOS.getTableHeader().setReorderingAllowed(false);          //nao podera ser reorganizada
            jTabelaINATIVOS.setAutoResizeMode(jTabelaINATIVOS.AUTO_RESIZE_OFF);      //nao será possivel redimencionar a tabela
            jTabelaINATIVOS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
        
        } catch (SQLException ex) {
                //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
                JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList da tabela ATIVOS!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }  
       
    }
        
        public void PreencherTabelaSemContrato(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Seção", "Ip", "Série", "Chapa", "Status", "Contrato"};
        try 
        {  
            conexao.ExecutarPesquisaSQL(sql);
            
            while (conexao.rs.next())
            {               
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("secao"),
                    conexao.rs.getString("ip"),
                    conexao.rs.getString("serie"),
                    conexao.rs.getString("chapa"),
                    conexao.rs.getString("status"),
                    conexao.rs.getString("contrato")
                });
                totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
                modelo    = conexao.rs.getString("modelo");
                
             };                

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabelaSEMCONTRATO.setModel(modelo);
            //define tamanho das colunas
            jTabelaSEMCONTRATO.getColumnModel().getColumn(0).setPreferredWidth(75);  //define o tamanho da coluna
            jTabelaSEMCONTRATO.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaSEMCONTRATO.getColumnModel().getColumn(1).setPreferredWidth(170);
            jTabelaSEMCONTRATO.getColumnModel().getColumn(1).setResizable(false);
            jTabelaSEMCONTRATO.getColumnModel().getColumn(2).setPreferredWidth(135);
            jTabelaSEMCONTRATO.getColumnModel().getColumn(2).setResizable(false);
            jTabelaSEMCONTRATO.getColumnModel().getColumn(3).setPreferredWidth(150);
            jTabelaSEMCONTRATO.getColumnModel().getColumn(3).setResizable(false);
            jTabelaSEMCONTRATO.getColumnModel().getColumn(4).setPreferredWidth(150);  //define o tamanho da coluna
            jTabelaSEMCONTRATO.getColumnModel().getColumn(4).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaSEMCONTRATO.getColumnModel().getColumn(5).setPreferredWidth(100);  //define o tamanho da coluna
            jTabelaSEMCONTRATO.getColumnModel().getColumn(5).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaSEMCONTRATO.getColumnModel().getColumn(6).setPreferredWidth(90);  //define o tamanho da coluna
            jTabelaSEMCONTRATO.getColumnModel().getColumn(6).setResizable(false);    //nao será possivel redimencionar a coluna 

            //define propriedades da tabela
            jTabelaSEMCONTRATO.getTableHeader().setReorderingAllowed(false);          //nao podera ser reorganizada
            jTabelaSEMCONTRATO.setAutoResizeMode(jTabelaSEMCONTRATO.AUTO_RESIZE_OFF);      //nao será possivel redimencionar a tabela
            jTabelaSEMCONTRATO.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
        
        } catch (SQLException ex) {
                //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
                JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList da tabela ATIVOS!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }  
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsPorIP;
    private javax.swing.JButton btnConsultarPorSerie;
    private javax.swing.JButton btnFiltroModeloSecao;
    private javax.swing.JButton btnImprimirAtivas;
    private javax.swing.JButton btnImprimirTudo;
    private javax.swing.JButton btnInativarContrato;
    private javax.swing.JButton btnLimparPesquisa;
    private javax.swing.JButton btnPorModelo;
    private javax.swing.JButton btnPorSecao;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbMODELOS;
    private javax.swing.JComboBox<String> cmbSECOES;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable jTabelaATIVOS2;
    private javax.swing.JTable jTabelaINATIVOS;
    private javax.swing.JTable jTabelaSEMCONTRATO;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTextField txtCHAPA;
    private javax.swing.JTextField txtCLIENTE;
    private javax.swing.JTextField txtCODIGO;
    private javax.swing.JTextArea txtDESCRICAO;
    private javax.swing.JTextField txtEMPRESA;
    private javax.swing.JTextField txtIP;
    private javax.swing.JTextField txtOBSERVACOES;
    private javax.swing.JTextField txtSERIE;
    // End of variables declaration//GEN-END:variables
}
