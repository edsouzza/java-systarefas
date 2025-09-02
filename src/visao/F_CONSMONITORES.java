package visao;

import Dao.DAOPatrimonio;
import biblioteca.Biblioteca;
import biblioteca.CampoLimitadoParaCHAPA;
import biblioteca.CampoLimitadoParaIP;
import biblioteca.ModeloTabela;
import conexao.ConnConexao;
import controle.CtrlPatrimonio;
import biblioteca.TudoMaiusculas;
import controle.CtrlCliente;
import static biblioteca.VariaveisPublicas.totalRegs;
import static biblioteca.VariaveisPublicas.nomeCliente;
import static biblioteca.VariaveisPublicas.sql;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.Cliente;
import modelo.Patrimonio;
import modelo.Secao;
import relatorios.GerarRelatorios;

public class F_CONSMONITORES extends javax.swing.JFrame {

    ConnConexao conexao  = new ConnConexao();
    Biblioteca umabiblio                = new Biblioteca();
    Patrimonio umModPatrimonio          = new Patrimonio();
    Cliente umModeloCliente                = new Cliente();
    CtrlCliente umControleCliente       = new CtrlCliente();
    CtrlPatrimonio umControlePatrimonio = new CtrlPatrimonio();
    ControleGravarLog umGravarLog       = new ControleGravarLog();
    DAOPatrimonio patrimonioDAO         = new DAOPatrimonio();
    Secao umModeloSecao                 = new Secao();
    CtrlSecoes umControleSecao          = new CtrlSecoes();
    String modelo,descricaoDeReativacao,motivoReativacao,patrimonio, situacao, nomeCli, nomeTipo, nomeClienteOLD, motivoInativacao, serie, paramPesquisa, nomeColuna, nomeEstacao, ip, descricaoDeInativacao, tipo  = null; 
    int codigo, idClienteRegSel, ind, idSecao, numeroColuna, idModelo, codigoSecao, codigoModelo = 0;
    boolean filtrouPorModelo, filtrouPorSecao, reativando,gravando, editando, flag, alterouStatus, bEncontrou, clicouNaTabela, escolheuModelo,filtrou, naoMicro, clicouInativos, filtrouClicou;  //controla no botão gravar entre gravar novo registro e gravar alteração de um registro    
    
    String sqlDefaultATIVOS   = "select p.*, c.nome as cliente, s.nome as secao, m.*,t.* from tblpatrimonios p, tblclientes c, tblsecoes s, tbltipos t, "
                              + "tblmodelos m where p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.status='ATIVO' and t.tipo='MONITOR'";
    String sqlDefaultINATIVOS = "select p.*, t.*, c.nome as cliente, s.nome as secao, m.* from tblpatrimonios p, tblclientes c, tblsecoes s, tbltipos t,"
                              + "tblmodelos m where p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.status='INATIVO' and t.tipo='MONITOR'";
    
    public F_CONSMONITORES() {
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
        umabiblio.configurarCamposTextos(txtCODIGO);
        umabiblio.configurarCamposTextos(txtSERIE);
        umabiblio.configurarCamposTextos(txtIP);
        umabiblio.configurarCamposTextos(txtCHAPA);        
        umabiblio.configurarCamposTextos(txtCLIENTE);
        txtDESCRICAO.setFont(new Font("TimesRoman", Font.BOLD, 12));
        txtDESCRICAO.setForeground(Color.blue);
        txtDESCRICAO.setEditable(false);
        txtIP.setDocument(new CampoLimitadoParaIP());
        txtCHAPA.setDocument(new CampoLimitadoParaCHAPA());
        txtCODIGO.setForeground(Color.red);
        txtOBSERVACOES.setForeground(Color.red);
        txtOBSERVACOES.setDocument(new TudoMaiusculas());
        cmbMODELOS.setFont(new Font("TimesRoman", Font.BOLD, 12));
        cmbSECOES.setFont(new Font("TimesRoman", Font.BOLD, 12));
        txtOBSERVACOES.setFont(new Font("TimesRoman", Font.BOLD, 12));
        PreencherTabelaATIVOS(sqlDefaultATIVOS);
        PreencherTabelaINATIVOS(sqlDefaultINATIVOS);
        
        //configuração dos botões
        umabiblio.configurarBotoes(btnImprimir);
        umabiblio.configurarBotoes(btnSair);
        
         //preenchendo a combo dos modelos e secoes com registros do bd
        PreencherComboModelosMonitores(cmbMODELOS, "modelo");
        cmbMODELOS.setSelectedIndex(-1); //mostrando primeiro item vazio para forçar a escolha de um ítem
        PreencherComboSecoes(cmbSECOES,"secao");
        cmbSECOES.setSelectedIndex(-1); //mostrando primeiro item vazio para forçar a escolha de um ítem

        jTabelaATIVOS2.setFont(new Font("TimesRoman", Font.BOLD, 12));
        jTabelaATIVOS2.setForeground(Color.blue);
        jTabelaINATIVOS.setFont(new Font("TimesRoman", Font.BOLD, 12));
        jTabelaINATIVOS.setForeground(Color.red);
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
        btnImprimir = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTabelaATIVOS2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTabelaINATIVOS = new javax.swing.JTable();
        btnLimparPesquisa = new javax.swing.JButton();
        cmbSECOES = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consultando Impressoras");
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
        txtCHAPA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCHAPAFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCHAPAFocusLost(evt);
            }
        });
        txtCHAPA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCHAPAActionPerformed(evt);
            }
        });
        txtCHAPA.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtCHAPAPropertyChange(evt);
            }
        });

        jLabel4.setText("SERIE");

        txtCLIENTE.setForeground(new java.awt.Color(51, 51, 255));
        txtCLIENTE.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCLIENTEFocusGained(evt);
            }
        });

        txtIP.setEditable(false);
        txtIP.setForeground(new java.awt.Color(51, 51, 255));
        txtIP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIPFocusGained(evt);
            }
        });

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
        cmbMODELOS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmbMODELOSFocusLost(evt);
            }
        });

        txtOBSERVACOES.setForeground(new java.awt.Color(51, 51, 255));
        txtOBSERVACOES.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtOBSERVACOESFocusGained(evt);
            }
        });

        jLabel12.setText("OBSERVAÇÕES");

        jLabel13.setText("DESCRIÇAO");

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
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 861, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("ATIVOS", jPanel7);

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
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 857, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("INATIVOS", jPanel2);

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
        cmbSECOES.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmbSECOESFocusLost(evt);
            }
        });

        jLabel9.setText("SEÇOES");

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(txtCLIENTE, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(123, 123, 123)
                                .addComponent(jLabel4))
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtSERIE, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtCHAPA)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(cmbMODELOS, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(cmbSECOES, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOBSERVACOES))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(panelPrincipalLayout.createSequentialGroup()
                            .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(jLabel9))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbMODELOS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbSECOES, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCLIENTE, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelPrincipalLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtIP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSERIE, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCHAPA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(37, 37, 37)))
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOBSERVACOES, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 690));

        setSize(new java.awt.Dimension(918, 734));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void PreencherComboSecoes(JComboBox cmb,String campo)
    {
        conexao.conectar();   
        sql="select distinct s.nome as secao from tblsecoes s, tblpatrimonios p, tbltipos t where s.status='ATIVO' and p.tipoid=t.codigo "
          + "and p.tipoid=2 and p.secaoid=s.codigo order by s.nome";
        conexao.ExecutarPesquisaSQL(sql);              
        try 
        {                       
            cmb.removeAllItems();
            while (conexao.rs.next()) {
                 cmb.addItem(conexao.rs.getString(campo));
            };    
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao preencher o combo de seções!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        } 
    }
    private void PreencherComboModelosMonitores(JComboBox cmb, String campo)
    {
        conexao.conectar();   
        sql="Select modelo From tblModelos WHERE tipoid=2 Order by modelo";
        conexao.ExecutarPesquisaSQL(sql);              
        try 
        {                       
            cmb.removeAllItems();
            while (conexao.rs.next()) {
                 cmb.addItem(conexao.rs.getString(campo));
            };    
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao preencher o combo de seções!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        } 
    }
    
    public int qdeMonitoresCadastrados()
    {        
        conexao.conectar();
        sql = "select count(codigo) as total from "+tabela+" where tipoid=2";
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
    public int qdeMonitoresCadastradosAtivos()
    {        
        conexao.conectar();
        sql = "select count(codigo) as total from "+tabela+" where tipoid=2 and status='ATIVO'";
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
    public int qdeMonitoresCadastradosInativos()
    {        
       conexao.conectar();
        sql = "select count(codigo) as total from "+tabela+" where tipoid=2 and status='INATIVO'";
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
        int qdeMonitores    =  qdeMonitoresCadastrados();
        int qdeAtivos       =  qdeMonitoresCadastradosAtivos();
        int qdeInativos     =  qdeMonitoresCadastradosInativos();
       
        if(qdeMonitores == 1){
           return "Equipamentos de Informática : "+qdeMonitores+" monitor cadastrado"; 
        }else{
           return "Equipamentos de Informática : "+qdeMonitores+" monitores cadastrados -> "+qdeAtivos+" ativos e "+qdeInativos+" inativos";    
        } 
        
    }

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        if(clicouInativos){
            //se clicou na aba inativos imprime todas as impressoras inativadas
            GerarRelatorios objRel = new GerarRelatorios();
            try {
                objRel.imprimirMonitoresCadastrados("relatorio/relmonitoresinativos.jasper");
                btnLimparPesquisaActionPerformed(null);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!"+e);                
            }         
        }else{if(filtrou){
              if(filtrouPorSecao){
                //se filtrou por seção imprime relatorio da secao selecionada
                GerarRelatorios objRel = new GerarRelatorios();
                try {
                    objRel.imprimirMonitoresSecaoSelecionada("relatorio/relmonitoresdasecaoselecionada.jasper",codigoSecao);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!"+e);                
                }      
              }else if(filtrouPorModelo){
                //se filtrou por modelo imprime relatorio do modelo selecionado
                GerarRelatorios objRel = new GerarRelatorios();
                try {
                    //JOptionPane.showMessageDialog(null, codigoModelo);
                    objRel.imprimirMonitoresModeloSelecionado("relatorio/relmonitoresdomodeloselecionado.jasper",codigoModelo);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!"+e);                
                }      
              } 
        }else{if(!filtrou && !clicouInativos){
            //se nao filtrou nada imprime relatorio de todas as secoes cadastradas
            GerarRelatorios objRel = new GerarRelatorios();
            try {
                objRel.imprimirMicrosCadastrados("relatorio/relmonitores.jasper");
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!"+e);                
            }
        }}}
        filtrou=false;
        btnImprimir.setEnabled(false);
        btnLimparPesquisa.setEnabled(true);
    }//GEN-LAST:event_btnImprimirActionPerformed
       
    private void Leitura()
    {
        if (umabiblio.tabelaVazia(tabela)) {
            //JOptionPane.showMessageDialog(null, "A tabela: " + tabela + " esta vazia cadastre o primeiro registro!");
            btnImprimir.setEnabled(false);            
        } else {
            btnImprimir.setEnabled(true);
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

    }
              
    private void txtSERIEFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSERIEFocusGained
        txtSERIE.selectAll();
    }//GEN-LAST:event_txtSERIEFocusGained

    private void txtCHAPAPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtCHAPAPropertyChange

    }//GEN-LAST:event_txtCHAPAPropertyChange

    private void txtCHAPAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCHAPAActionPerformed

    }//GEN-LAST:event_txtCHAPAActionPerformed

    private void txtCHAPAFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCHAPAFocusGained
       
    }//GEN-LAST:event_txtCHAPAFocusGained

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
    }//GEN-LAST:event_formWindowOpened
    public void mostrarDadosRegSelecionado(int codPatrimonio)
    {
        //pesquisar um patrimonio pelo numero do codigo setando os edits,nao use o next porque se clicou na tabela o registro com certeza existe
        //Obs: no caso dos inativos não precisa mostrar a seção pois não estão mais na PGM em nenhuma seçao

        sql = "SELECT p.*, c.nome as cliente, s.nome as secao, m.*, t.* FROM tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m, tbltipos t WHERE "
                     +"p.tipoid=t.codigo and p.clienteid=c.codigo and p.modeloid=m.codigo and p.secaoid=s.codigo and p.codigo='" + codPatrimonio + "'";
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
    private void txtCHAPAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCHAPAFocusLost
        
    }//GEN-LAST:event_txtCHAPAFocusLost

    private void txtCLIENTEFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCLIENTEFocusGained
       
    }//GEN-LAST:event_txtCLIENTEFocusGained
    
    private void txtIPFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIPFocusGained
      
        
    }//GEN-LAST:event_txtIPFocusGained

    private void cmbMODELOSItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMODELOSItemStateChanged
        txtDESCRICAO.requestFocus();
        editando=true;
    }//GEN-LAST:event_cmbMODELOSItemStateChanged

    public void filtrarPorSecao(int idSecao)
    {
        nomeTipo = "MONITOR";
        escolheuModelo = false;
        filtrou=true;
        filtrouPorSecao=true;
        
        PreencherTabelaATIVOS("select p.*, c.nome as cliente, s.nome as secao, m.*, t.* from tbltipos t, tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m "
                            + "where p.tipoid=t.codigo and s.codigo=p.secaoid and p.modeloid=m.codigo and p.status='ATIVO' and  p.clienteid=c.codigo "
                            + "and t.tipo= '" + nomeTipo + "' and s.codigo= "+idSecao);
        
        if(escolheuModelo)        
            this.setTitle("Total de monitores "+modelo+" retornados pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        else
             this.setTitle("Total de monitores retornados pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        umabiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 
        cmbMODELOS.setEnabled(false);
        cmbSECOES.setEnabled(false);
        jTabbedPane4.setSelectedIndex(0);
    }
    public void filtrarPorModelo(int idModelo)
    {
        nomeTipo = "MONITOR";
        escolheuModelo = true;
        filtrou=true;
        filtrouPorModelo=true;
        
        PreencherTabelaATIVOS("select p.*, c.nome as cliente, s.nome as secao, m.*, t.* from tbltipos t, tblpatrimonios p, tblclientes c, tblsecoes s, tblmodelos m "
                            + "where p.tipoid=t.codigo and s.codigo=p.secaoid and p.modeloid=m.codigo and p.status='ATIVO' and p.clienteid=c.codigo and "
                            + "t.tipo= '" + nomeTipo + "' and m.codigo= "+idModelo);
        
        if(escolheuModelo)        
            this.setTitle("Total de monitores "+modelo+" retornados pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        else
             this.setTitle("Total de monitores retornados pela pesquisa = "+totalRegs);  //passando o total de registros para o titulo
        umabiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 
        cmbMODELOS.setEnabled(false);
        cmbSECOES.setEnabled(false);
        jTabbedPane4.setSelectedIndex(0);
    }
    
    private void cmbMODELOSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbMODELOSFocusLost
       //quando perder o foco mostrar descricao atraves do id do patrimonio
       codigoModelo  = umabiblio.buscarCodigoGenerico("tblmodelos","modelo",cmbMODELOS.getSelectedItem().toString());
       filtrarPorModelo(codigoModelo);
       btnLimparPesquisa.setEnabled(true);
    }//GEN-LAST:event_cmbMODELOSFocusLost

    private void txtOBSERVACOESFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOBSERVACOESFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOBSERVACOESFocusGained

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
        btnImprimir.setEnabled(false);
        btnLimparPesquisa.setEnabled(true);
        cmbMODELOS.setEnabled(false);
        cmbSECOES.setEnabled(false);
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
        nomeClienteOLD = txtCLIENTE.getText();
        btnImprimir.setEnabled(true);
        txtIP.setEnabled(true);
        clicouNaTabela = true;
        if(clicouNaTabela && filtrou){
            filtrouClicou = true;
        }
    }//GEN-LAST:event_jTabelaINATIVOSMouseClicked

    private void jTabbedPane4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane4MouseClicked
        //se clicou na aba INATIVOS
        if (jTabbedPane4.getSelectedIndex() == 1) {
            umabiblio.limparTodosCampos(rootPane);
            btnImprimir.setEnabled(true);
            btnLimparPesquisa.setText("Voltar");
            clicouInativos=true;
            reativando = true;
            cmbMODELOS.setEnabled(true);
            cmbSECOES.setEnabled(true);
            btnLimparPesquisa.setEnabled(true);
        } else {
            clicouInativos=false;
            umabiblio.limparTodosCampos(rootPane);
            btnLimparPesquisa.setEnabled(false);
        }
        
    }//GEN-LAST:event_jTabbedPane4MouseClicked
    private void limparPesquisa(){
        umabiblio.limparTodosCampos(rootPane);
        PreencherTabelaATIVOS(sqlDefaultATIVOS);
        escolheuModelo = false;
        filtrou=false;
        clicouInativos=false;
        this.setTitle(this.mostrarTituloDoFormulario());
        cmbMODELOS.setEnabled(true);
        cmbMODELOS.setSelectedIndex(-1); //mostrando primeiro item vazio para forçar a escolha de um ítem
        cmbSECOES.setEnabled(true);
        cmbSECOES.setSelectedIndex(-1); //mostrando primeiro item vazio para forçar a escolha de um ítem
        
        btnLimparPesquisa.setEnabled(false);         
        jTabbedPane4.setSelectedIndex(0);
        btnLimparPesquisa.setText("Limpar Pesquisa"); 
        btnImprimir.setEnabled(true);
        filtrouPorModelo=false;
        filtrouPorSecao=false;       
    }
    private void btnLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparPesquisaActionPerformed
       limparPesquisa();        
    }//GEN-LAST:event_btnLimparPesquisaActionPerformed

    private void cmbSECOESItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSECOESItemStateChanged
        txtDESCRICAO.requestFocus();
    }//GEN-LAST:event_cmbSECOESItemStateChanged

    private void cmbSECOESFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbSECOESFocusLost
       //quando perder o foco mostrar descricao atraves do id do patrimonio
       codigoSecao  = umabiblio.buscarCodigoGenerico("tblsecoes","nome",cmbSECOES.getSelectedItem().toString());
       filtrarPorSecao(codigoSecao);
       //JOptionPane.showMessageDialog(null, codigoSecao);
       btnLimparPesquisa.setEnabled(true);
       btnImprimir.setEnabled(true);
    }//GEN-LAST:event_cmbSECOESFocusLost
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
        String[] Colunas = new String[]{"Código", "Seção", "Série", "Chapa", "Status"};
        try 
        {  
            conexao.ExecutarPesquisaSQL(sql);
            
            while (conexao.rs.next())
            {               
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("secao"),
                    conexao.rs.getString("serie"),
                    conexao.rs.getString("chapa"),
                    conexao.rs.getString("status")
                });
                totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
                modelo    = conexao.rs.getString("modelo");
                
             };                

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabelaATIVOS2.setModel(modelo);
            //define tamanho das colunas
            jTabelaATIVOS2.getColumnModel().getColumn(0).setPreferredWidth(60);  //define o tamanho da coluna
            jTabelaATIVOS2.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaATIVOS2.getColumnModel().getColumn(1).setPreferredWidth(190);
            jTabelaATIVOS2.getColumnModel().getColumn(1).setResizable(false);
            jTabelaATIVOS2.getColumnModel().getColumn(2).setPreferredWidth(190);
            jTabelaATIVOS2.getColumnModel().getColumn(2).setResizable(false);
            jTabelaATIVOS2.getColumnModel().getColumn(3).setPreferredWidth(190);
            jTabelaATIVOS2.getColumnModel().getColumn(3).setResizable(false);
            jTabelaATIVOS2.getColumnModel().getColumn(4).setPreferredWidth(190);  //define o tamanho da coluna
            jTabelaATIVOS2.getColumnModel().getColumn(4).setResizable(false);    //nao será possivel redimencionar a coluna 

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
        String[] Colunas = new String[]{"Código", "Seção", "Série", "Chapa", "Status"};
        try 
        {  
            conexao.ExecutarPesquisaSQL(sql);
            
            while (conexao.rs.next())
            {               
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("secao"),
                    conexao.rs.getString("serie"),
                    conexao.rs.getString("chapa"),
                    conexao.rs.getString("status")
                        
                });
                totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
             };                

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabelaINATIVOS.setModel(modelo);
            //define tamanho das colunas
            jTabelaINATIVOS.getColumnModel().getColumn(0).setPreferredWidth(60);  //define o tamanho da coluna
            jTabelaINATIVOS.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaINATIVOS.getColumnModel().getColumn(1).setPreferredWidth(190);
            jTabelaINATIVOS.getColumnModel().getColumn(1).setResizable(false);
            jTabelaINATIVOS.getColumnModel().getColumn(2).setPreferredWidth(190);
            jTabelaINATIVOS.getColumnModel().getColumn(2).setResizable(false);
            jTabelaINATIVOS.getColumnModel().getColumn(3).setPreferredWidth(190);
            jTabelaINATIVOS.getColumnModel().getColumn(3).setResizable(false);
            jTabelaINATIVOS.getColumnModel().getColumn(4).setPreferredWidth(190);  //define o tamanho da coluna
            jTabelaINATIVOS.getColumnModel().getColumn(4).setResizable(false);    //nao será possivel redimencionar a coluna 

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnLimparPesquisa;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbMODELOS;
    private javax.swing.JComboBox<String> cmbSECOES;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable jTabelaATIVOS2;
    private javax.swing.JTable jTabelaINATIVOS;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTextField txtCHAPA;
    private javax.swing.JTextField txtCLIENTE;
    private javax.swing.JTextField txtCODIGO;
    private javax.swing.JTextArea txtDESCRICAO;
    private javax.swing.JTextField txtIP;
    private javax.swing.JTextField txtOBSERVACOES;
    private javax.swing.JTextField txtSERIE;
    // End of variables declaration//GEN-END:variables
}
