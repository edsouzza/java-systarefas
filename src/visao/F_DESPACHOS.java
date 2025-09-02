package visao;

import Dao.DAOFlag;
import conexao.ConnConexao;
import biblioteca.CampoLimitadoEmQdeLetrasNumeros;
import biblioteca.GerarNumDespachos;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.consultandoInativos;
import static biblioteca.VariaveisPublicas.editando;
import static biblioteca.VariaveisPublicas.tabela;
import static biblioteca.VariaveisPublicas.tabela_da_lista;
import static biblioteca.VariaveisPublicas.TipoDocumento;
import static biblioteca.VariaveisPublicas.nomeUsuarioLogado;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import Dao.DAONumDespacho;
import biblioteca.Biblioteca;
import biblioteca.CampoLimitadoParaDigitacaoDeDocumentos;
import static biblioteca.VariaveisPublicas.lstListaCampos;
import static biblioteca.VariaveisPublicas.cadastrando;
import static biblioteca.VariaveisPublicas.codigoRegSelecionado;
import static biblioteca.VariaveisPublicas.codigoUsuario;
import static biblioteca.VariaveisPublicas.totalRegs;
import modelo.Despacho;
import controle.ControleGravarLog;
import controle.CtrlDespachos;
import controle.CtrlNumDespachos;
import java.awt.Cursor;
import relatorios.GerarExcelTable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;



public class F_DESPACHOS extends javax.swing.JDialog {

    ConnConexao          conexao            = new ConnConexao();
    MetodosPublicos      umMetodo           = new MetodosPublicos();
    GerarNumDespachos    objGerarNumeracao  = new GerarNumDespachos();
    DAONumDespacho       objNumDespacho     = new DAONumDespacho();
    Despacho             objModDespacho     = new Despacho();
    CtrlNumDespachos     ctrlNumDespacho    = new CtrlNumDespachos();
    CtrlDespachos        ctrlDespacho       = new CtrlDespachos();
    ControleGravarLog    umGravarLog        = new ControleGravarLog();
    DAOFlag              daoFLAG            = new DAOFlag();
    DateFormat           sdf                = new SimpleDateFormat("dd.MM.yyyy");
    Biblioteca           umabiblio          = new Biblioteca();
    
    int mes, numsecaoid, numdespachoid, idcadastrante, tipodocumentoid = 0;
    String mesano, numDesp, numExpediente, destino, digitalizado, assunto, obs, cadastrante, dData, status, oldRequerente, oldTipoDespacho,oldTipoExpediente,oldNumDespacho, statusDigitalizado;
    
    String sqlDefault = "SELECT d.codigo,d.numdespachoid,d.mesano,d.assunto,d.destino,d.data,d.obs,d.status,u.nome,n.numdespacho FROM tbldespachos d, tblnumdespachos n, tblusuarios u WHERE d.numdespachoid=n.codigo AND d.cadastranteid=u.codigo ORDER BY d.codigo";
    String sqlVazia   = "select * from TBLDESPACHOS where codigo = 0";
        

    public F_DESPACHOS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();        
        Leitura();
        //this.setTitle(this.mostrarTituloDoFormulario());
        setResizable(false);   //desabilitando o redimencionamento da tela        
        //setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar

        //Impede que formulario seja arrastado na tela
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                setEnabled(false);
                setEnabled(true);
            }
        });//fim addComponentListener

        //configuracoes dos edits         
        umMetodo.configurarCamposTextos(txtOBS);               

        //configuração dos botões
        umMetodo.configurarBotoes(btnNovo);
        umMetodo.configurarBotoes(btnEditar);
        umMetodo.configurarBotoes(btnGravar);
        umMetodo.configurarBotoes(btnVoltar);
        umMetodo.configurarBotoes(btnSair);
        

        jTabelaDESPACHOS            .setFont(new Font("Arial", Font.BOLD, 12));
        txtTIPODOCUMENTO             .setFont(new Font("TimesRoman", Font.BOLD, 14));          
        txtOBS                      .setDocument(new CampoLimitadoEmQdeLetrasNumeros(200));
        txtOBS                      .setFont(new Font("TimesRoman", Font.BOLD, 14)); 
        txtCODIGO                   .setFont(new Font("TimesRoman", Font.BOLD, 16));
        txtCODIGO                   .setForeground(Color.blue);
        txtNUMDOCUMENTO              .setFont(new Font("TimesRoman", Font.BOLD, 24));
        txtNUMDOCUMENTO              .setForeground(Color.red);
        txtPESQUISA                 .setDocument(new CampoLimitadoParaDigitacaoDeDocumentos());
        txtPESQUISA                 .setFont(new Font("TimesRoman", Font.BOLD, 14));
        txtDESTINO                  .setDocument(new CampoLimitadoParaDigitacaoDeDocumentos());
        txtDESTINO                  .setFont(new Font("TimesRoman", Font.BOLD, 14));
        txtASSUNTO                  .setFont(new Font("TimesRoman", Font.BOLD, 14));
        txtASSUNTO                  .setForeground(Color.blue);
        txtCADASTRANTE              .setFont(new Font("TimesRoman", Font.BOLD, 14));        

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBoxPesquisar = new javax.swing.JLayeredPane();
        txtPESQUISA = new javax.swing.JTextField();
        jBoxDados = new javax.swing.JLayeredPane();
        jpBoxDados = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNUMDOCUMENTO = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCODIGO = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDESTINO = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtOBS = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtASSUNTO = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        txtCADASTRANTE = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtTIPODOCUMENTO = new javax.swing.JTextField();
        jBoxBotoes = new javax.swing.JLayeredPane();
        btnGravar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnGerarExcel = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTabelaDESPACHOS = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Manutenção de Servidores com Cargo");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jBoxPesquisar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxPesquisar.setName("panelDados"); // NOI18N

        txtPESQUISA.setEditable(false);
        txtPESQUISA.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtPESQUISA.setToolTipText("Digite algo para pesquisar pelo nome");
        txtPESQUISA.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        javax.swing.GroupLayout jBoxPesquisarLayout = new javax.swing.GroupLayout(jBoxPesquisar);
        jBoxPesquisar.setLayout(jBoxPesquisarLayout);
        jBoxPesquisarLayout.setHorizontalGroup(
            jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxPesquisarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtPESQUISA)
                .addContainerGap())
        );
        jBoxPesquisarLayout.setVerticalGroup(
            jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBoxPesquisarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtPESQUISA, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addContainerGap())
        );
        jBoxPesquisar.setLayer(txtPESQUISA, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jBoxDados.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxDados.setName("panelDados"); // NOI18N

        jLabel1.setText("NUMERO DO DOCUMENTO");

        txtNUMDOCUMENTO.setEditable(false);
        txtNUMDOCUMENTO.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        txtNUMDOCUMENTO.setForeground(new java.awt.Color(255, 0, 0));
        txtNUMDOCUMENTO.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNUMDOCUMENTO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtNUMDOCUMENTO.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNUMDOCUMENTOFocusGained(evt);
            }
        });
        txtNUMDOCUMENTO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNUMDOCUMENTOKeyPressed(evt);
            }
        });

        jLabel3.setText("CÓDIGO");

        txtCODIGO.setEditable(false);
        txtCODIGO.setForeground(new java.awt.Color(51, 51, 255));

        jLabel10.setText("DESTINO");

        txtDESTINO.setEditable(false);
        txtDESTINO.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtDESTINO.setForeground(new java.awt.Color(51, 51, 255));
        txtDESTINO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtDESTINO.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDESTINOFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDESTINOFocusLost(evt);
            }
        });
        txtDESTINO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDESTINOMouseClicked(evt);
            }
        });
        txtDESTINO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDESTINOActionPerformed(evt);
            }
        });
        txtDESTINO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDESTINOKeyPressed(evt);
            }
        });

        jLabel4.setText("OBSERVACOES");

        txtOBS.setEditable(false);
        txtOBS.setForeground(new java.awt.Color(51, 51, 255));
        txtOBS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtOBS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtOBSFocusGained(evt);
            }
        });
        txtOBS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtOBSKeyPressed(evt);
            }
        });

        txtASSUNTO.setEditable(false);
        txtASSUNTO.setColumns(20);
        txtASSUNTO.setLineWrap(true);
        txtASSUNTO.setRows(5);
        txtASSUNTO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtASSUNTO.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtASSUNTOFocusLost(evt);
            }
        });
        txtASSUNTO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtASSUNTOKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(txtASSUNTO);

        jLabel6.setText("ASSUNTO");

        txtCADASTRANTE.setEditable(false);
        txtCADASTRANTE.setForeground(new java.awt.Color(51, 51, 255));
        txtCADASTRANTE.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtCADASTRANTE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCADASTRANTEKeyPressed(evt);
            }
        });

        jLabel18.setText("CADASTRANTE");

        jLabel13.setText("TIPO DE DOCUMENTO");

        txtTIPODOCUMENTO.setEditable(false);
        txtTIPODOCUMENTO.setForeground(new java.awt.Color(51, 51, 255));
        txtTIPODOCUMENTO.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtTIPODOCUMENTO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTIPODOCUMENTOMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtTIPODOCUMENTOMouseEntered(evt);
            }
        });
        txtTIPODOCUMENTO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTIPODOCUMENTOKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jpBoxDadosLayout = new javax.swing.GroupLayout(jpBoxDados);
        jpBoxDados.setLayout(jpBoxDadosLayout);
        jpBoxDadosLayout.setHorizontalGroup(
            jpBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBoxDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpBoxDadosLayout.createSequentialGroup()
                        .addGroup(jpBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDESTINO, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jpBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOBS, javax.swing.GroupLayout.PREFERRED_SIZE, 737, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addGroup(jpBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jpBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jpBoxDadosLayout.createSequentialGroup()
                                .addGroup(jpBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(29, 29, 29)
                                .addGroup(jpBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNUMDOCUMENTO)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
                                .addGap(29, 29, 29)
                                .addGroup(jpBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCADASTRANTE, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jpBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTIPODOCUMENTO, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpBoxDadosLayout.setVerticalGroup(
            jpBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBoxDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpBoxDadosLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(8, 8, 8)
                        .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpBoxDadosLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNUMDOCUMENTO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpBoxDadosLayout.createSequentialGroup()
                        .addGroup(jpBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCADASTRANTE, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTIPODOCUMENTO, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOBS, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDESTINO, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jBoxDadosLayout = new javax.swing.GroupLayout(jBoxDados);
        jBoxDados.setLayout(jBoxDadosLayout);
        jBoxDadosLayout.setHorizontalGroup(
            jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBoxDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jBoxDadosLayout.setVerticalGroup(
            jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBoxDados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jBoxDados.setLayer(jpBoxDados, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jBoxBotoes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_gravar.jpg"))); // NOI18N
        btnGravar.setText("Gravar");
        btnGravar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });

        btnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_reverter.gif"))); // NOI18N
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

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_blocoNotas.gif"))); // NOI18N
        btnNovo.setText("Novo Documento");
        btnNovo.setToolTipText("");
        btnNovo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/calculator.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnGerarExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Excel.gif"))); // NOI18N
        btnGerarExcel.setText("Excel");
        btnGerarExcel.setToolTipText("Gerar relatório Excel");
        btnGerarExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGerarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jBoxBotoesLayout = new javax.swing.GroupLayout(jBoxBotoes);
        jBoxBotoes.setLayout(jBoxBotoesLayout);
        jBoxBotoesLayout.setHorizontalGroup(
            jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxBotoesLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnGerarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jBoxBotoesLayout.setVerticalGroup(
            jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGerarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jBoxBotoes.setLayer(btnGravar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnVoltar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnSair, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnNovo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnEditar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnGerarExcel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jTabbedPane3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabbedPane3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane3MouseClicked(evt);
            }
        });

        jTabelaDESPACHOS.setGridColor(new java.awt.Color(255, 255, 255));
        jTabelaDESPACHOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaDESPACHOSMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTabelaDESPACHOS);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 285, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("DOCUMENTOS", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jBoxBotoes, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jBoxDados, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jBoxPesquisar, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBoxPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBoxDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBoxBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.getAccessibleContext().setAccessibleName("DESPACHOS");

        setSize(new java.awt.Dimension(1180, 859));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPESQUISAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyPressed
        //se teclar enter estando dentro da pesquisa limpar a pesquisa
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtPESQUISA.setText(null);
            Leitura();        
        }

    }//GEN-LAST:event_txtPESQUISAKeyPressed

    private void filtrarPorDigitacao(String pPesq) {
        //filtrando por digitação por varios campos
        PreencherTabela("SELECT d.*,n.numdespacho,u.nome,t.tipo "
                      + "FROM tbldespachos d, tblnumdespachos n, tblusuarios u, tbltipodocumentos t "
                      + "WHERE (u.nome like '%" + pPesq + "%'" + " OR n.numdespacho like '%" + pPesq + "%'" + "OR d.destino like '%" + pPesq + "%'"  
                      + "OR t.tipo like '%" + pPesq + "%'" + "OR d.mesano like '%" + pPesq + "%'" + " OR d.assunto like '%" + pPesq + "%'" +") "
                      + "AND d.numdespachoid=n.codigo AND d.cadastranteid=u.codigo AND d.tipodocumentoid=t.codigo"); 
        this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);

        btnNovo.setEnabled(false);
        btnSair.setEnabled(false);
    }

    private void txtPESQUISAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyReleased
        //filtrar o nome conforme o usuario for digitando
        filtrarPorDigitacao(txtPESQUISA.getText());
        btnGerarExcel.setEnabled(false);
        btnVoltar.setEnabled(true);
    
    }//GEN-LAST:event_txtPESQUISAKeyReleased

     public void getValoresDosEdits() {
        
        destino                             = txtDESTINO.getText();
        assunto                             = txtASSUNTO.getText();
        obs                                 = txtOBS.getText();
        mesano                              = umMetodo.retornaMesAnoVigente();
        cadastrante                         = txtCADASTRANTE.getText();
        
    }
    
    private void gravarNovoRegistro() 
    {
        getValoresDosEdits();
        
        objModDespacho.setNumdespachoid(umMetodo.getCodigoPassandoString("tblnumdespachos", "numdespacho", txtNUMDOCUMENTO.getText()));
        objModDespacho.setTipodocumentoid(umMetodo.getCodigoPassandoString("tbltipodocumentos", "tipo", txtTIPODOCUMENTO.getText()));
        objModDespacho.setMesano(mesano);
        objModDespacho.setAssunto(assunto);
        objModDespacho.setDestino(destino);  
        objModDespacho.setCadastranteid(codigoUsuario);
        objModDespacho.setObs(obs);

        if (cadastrando) {           
            if(ctrlDespacho.salvarDespacho(objModDespacho)) 
            { 
                JOptionPane.showMessageDialog(null,"Documento cadastrado com sucesso!"); 
                umGravarLog.gravarLog("cadastro do documento numero : " + txtNUMDOCUMENTO.getText());
            }  
        }
        
        PreencherTabela(sqlDefault);
        cadastrando = false;
        
        if(!ctrlNumDespacho.temDespachoDisponivel()){
           //depois de gravar o registro verifica se não tiver mais documentos disponiveis gerar mais 
           gerarDisponiveis();
        }
    }

    private void gravarEdicaoRegistro() {
        
        getValoresDosEdits();
        //setando os valores dos edits no objeto mod
        objModDespacho   .setCodigo              (codigoRegSelecionado);
        objModDespacho   .setTipodocumentoid     (umMetodo.getCodigoPassandoString("tbltipodocumentos", "tipo", txtTIPODOCUMENTO.getText()));
        objModDespacho   .setDestino(txtDESTINO.getText());
        objModDespacho   .setAssunto(txtASSUNTO.getText());
        objModDespacho   .setObs(txtOBS.getText());

        if (editando) {           
            if(ctrlDespacho.atualizarDespacho(objModDespacho)) 
            { 
                umGravarLog.gravarLog("edicao do despacho numero : " + txtNUMDOCUMENTO.getText());
            }  
        }
        
        PreencherTabela(sqlDefault);
        editando = false;       
        
    }

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed

        if (cadastrando) {
            gravarNovoRegistro();
        } else if (editando) {
            gravarEdicaoRegistro();
        }

        Leitura();
    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        //se cancelar DISPINIBILIZA o registro novamente
        if(cadastrando){
           ctrlNumDespacho.disponibilizarStatusNumDespacho(umMetodo.getCodigoPassandoString("TBLNUMDESPACHOS", "numdespacho", numDesp)); 
        }
        Leitura();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        if(umabiblio.permissaoLiberada()){
            umMetodo.limparTodosCampos(jpBoxDados);
            txtCODIGO.setEnabled(true);
            txtCODIGO.setText(String.valueOf(umMetodo.mostrarProximoCodigo(tabela))); 
            //Ao clicar em novo despacho já esta indisponibilizando o registro
            txtNUMDOCUMENTO.setEnabled(true);
            txtNUMDOCUMENTO.setText(ctrlNumDespacho.getNumDespachoDisponivel());        
            ctrlNumDespacho.indisponibilizarStatusNumDespacho(umMetodo.getCodigoPassandoString("TBLNUMDESPACHOS", "numdespacho", txtNUMDOCUMENTO.getText()));
            txtTIPODOCUMENTO.requestFocus();
            txtTIPODOCUMENTO.setEnabled(true);
            txtCADASTRANTE.setEnabled(true);    
            txtCADASTRANTE.setEditable(false);   
            txtCADASTRANTE.setText(nomeUsuarioLogado);        
            txtDESTINO.setEnabled(true);        
            txtDESTINO.setEditable(true);        
            txtOBS.setEnabled(true);        
            txtOBS.setEditable(true);        
            txtASSUNTO.setEnabled(true);        
            txtASSUNTO.setEditable(true); 
            txtPESQUISA.setText(null);
            btnGerarExcel.setEnabled(false);
            btnVoltar.setEnabled(true);
            btnVoltar.setText("Cancelar");
            btnNovo.setEnabled(false);        
            btnSair.setEnabled(false);
            numDesp = txtNUMDOCUMENTO.getText();
            cadastrando=true;       
            //SELECIONANDO O CURSO NA HORA DA EDICAO        
            txtCADASTRANTE.setCursor(null);
            txtDESTINO.setCursor(null);
            txtASSUNTO.setCursor(null);        
            txtOBS.setCursor(null);
            txtNUMDOCUMENTO.setCursor(null);
            abrirListaTipoDocumentos();
        }
        
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if(umabiblio.permissaoLiberada()){
            Edicao();
            if(editando){
               txtTIPODOCUMENTO.setForeground(Color.RED); 
               txtASSUNTO.setForeground(Color.RED); 
               txtDESTINO.setForeground(Color.RED); 
               txtOBS.setForeground(Color.RED); 
            }
        }
    }//GEN-LAST:event_btnEditarActionPerformed

       private void mostrarCamposComID() {
        //mostrando os dados do registro selecionado nos edits       
        txtNUMDOCUMENTO    .setText(umMetodo.getStringPassandoCodigo("tblnumdespachos", "numdespacho",  numdespachoid));
        txtTIPODOCUMENTO   .setText(umMetodo.getStringPassandoCodigo("tbltipodocumentos", "tipo",  tipodocumentoid));
        txtCADASTRANTE     .setText(umMetodo.getStringPassandoCodigo("tblusuarios", "nome",  idcadastrante));
        oldNumDespacho     = txtNUMDOCUMENTO.getText();  
    }
    
    private void mostrarDadosRegSelecionado(int iCodigo) {
        sql = "SELECT d.codigo,d.numdespachoid,d.tipodocumentoid,d.cadastranteid,d.mesano,d.assunto,d.destino,d.data,d.obs,d.status,u.nome,n.numdespacho " 
              + "FROM tbldespachos d, tblnumdespachos n, tblusuarios u WHERE d.numdespachoid=n.codigo AND d.cadastranteid=u.codigo "
              + "AND d.codigo="+iCodigo+" ORDER BY d.codigo ";
              

        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        try {

            if (conexao.rs.next()) 
            {
                //RECEBE OS VALORES DOS IDs DA TABELA E APRESENTA OS NOMES NOS CAMPOS ATRAVES DO METODO mostrarCamposComID

                numdespachoid         = conexao.rs.getInt("numdespachoid");                
                tipodocumentoid       = conexao.rs.getInt("tipodocumentoid");                
                idcadastrante         = conexao.rs.getInt("cadastranteid"); 
                
                txtCODIGO             .setText(String.valueOf(conexao.rs.getInt("codigo")));                
                txtDESTINO            .setText(conexao.rs.getString("destino"));
                txtASSUNTO            .setText(conexao.rs.getString("assunto")); 
                txtOBS                .setText(conexao.rs.getString("obs")); 
                mesano                =       (conexao.rs.getString("mesano"));                               
                dData                 =        umMetodo.converteDataParaString("dd.MM.yyyy", conexao.rs.getDate("data"));                
                                
            }            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar selecionar o cargo!\nERRO:" + ex.getMessage());
        } finally {
            conexao.desconectar();
        }

        mostrarCamposComID();
        
        //controla apresentacao dos edits nao permitindo ediçao
        txtPESQUISA.setEditable(false);
        txtPESQUISA.setText(null);
        txtCODIGO.setEnabled(true);
        txtCODIGO.setEditable(false);
        txtCADASTRANTE.setEnabled(true);
        txtCADASTRANTE.setEditable(false);
        txtTIPODOCUMENTO.setEnabled(true);
        txtTIPODOCUMENTO.setEditable(false);
        txtNUMDOCUMENTO.setEnabled(true);
        txtNUMDOCUMENTO.setEditable(false);
        txtDESTINO.setEnabled(true);
        txtDESTINO.setEditable(false);
        txtASSUNTO.setEnabled(true);
        txtASSUNTO.setEditable(false);
        txtOBS.setEnabled(true);
        txtOBS.setEditable(false);

    }

    private void btnGerarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarExcelActionPerformed
        /*retorna todos os campos da tabela ideal quando tiver chaves estrangeiras na tabela você passa a SQL completa dos dados
         importante salientar que não podem haver dados nulos nos registros */

        GerarExcelTable excel = new GerarExcelTable();     
        
        sql =   "SELECT d.codigo,n.numdespacho as numdocumento, t.tipo as tipodocumento, d.mesano,d.assunto,d.destino,d.data,d.obs,d.status, " 
              + "u.nome as cadastrante " 
              + "FROM tbldespachos d, tblnumdespachos n, tblusuarios u, tbltipodocumentos t WHERE " 
              + "d.numdespachoid=n.codigo AND d.cadastranteid=u.codigo AND t.codigo=d.tipodocumentoid ORDER BY d.codigo";  
                
        lstListaCampos.clear();   
        umMetodo.preencherArrayListComCampos(lstListaCampos, sql);
        
        ArrayList<Object[]> dataList = excel.getListaDados(sql);        
        if (dataList != null && dataList.size() > 0) {
            excel.doExportar(dataList);
        } else {
            JOptionPane.showMessageDialog(null, "Não há dados disponíveis na tabela para exportacao, operação cancelada!", "Erro Fatal, verifique a SQL!", 2);
        }
        umGravarLog.gravarLog("impressao de relatorio : "+umMetodo.retornaNomeTabela(tabela));
        
    }//GEN-LAST:event_btnGerarExcelActionPerformed

    private void txtPESQUISAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPESQUISAMouseClicked
        txtPESQUISA.selectAll();
    }//GEN-LAST:event_txtPESQUISAMouseClicked

    private void jTabbedPane3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane3MouseClicked
        editando = false;
        cadastrando = false;
        btnEditar.setEnabled(false);
        btnGravar.setEnabled(false);
        btnGravar.setText("REATIVAR");
        umMetodo.limparTodosCampos(jBoxDados);
        txtPESQUISA.setEditable(true);
        txtPESQUISA.requestFocus();

    }//GEN-LAST:event_jTabbedPane3MouseClicked

    private void jTabelaDESPACHOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaDESPACHOSMouseClicked
        /*passando o codigo do patrimonio para o metodo => (int) jTabelaATIVOS1.getValueAt(jTabelaATIVOS1.getSelectedRow(), 0)
         onde o zero é a coluna que detem o valor que se deseja nesse caso o codigo (coluna 0)*/
        codigoRegSelecionado = (int) jTabelaDESPACHOS.getValueAt(jTabelaDESPACHOS.getSelectedRow(), 0);               
        mostrarDadosRegSelecionado(codigoRegSelecionado);
        cadastrando = false;
        btnEditar.setEnabled(true);
        btnGravar.setEnabled(false);
        btnNovo.setEnabled(false);
        btnVoltar.setEnabled(true);
        btnSair.setEnabled(false);
        btnGerarExcel.setEnabled(false);
        
        //SELECIONANDO O CURSO NA HORA DA EDICAO
        txtDESTINO.setCursor(null);
        txtASSUNTO.setCursor(null);        
        txtOBS.setCursor(null);
        txtNUMDOCUMENTO.setCursor(null);

    }//GEN-LAST:event_jTabelaDESPACHOSMouseClicked

    private void txtDESTINOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDESTINOKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtOBS.requestFocus();
            btnGravar.setEnabled(true);
        }
        if(editando){
            txtDESTINO.setForeground(Color.BLACK); 
        }
    }//GEN-LAST:event_txtDESTINOKeyPressed

    private void txtDESTINOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDESTINOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDESTINOActionPerformed

    private void txtDESTINOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDESTINOMouseClicked
        
    }//GEN-LAST:event_txtDESTINOMouseClicked

    private void txtDESTINOFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDESTINOFocusGained
        txtDESTINO.selectAll();
    }//GEN-LAST:event_txtDESTINOFocusGained

    private void txtNUMDOCUMENTOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNUMDOCUMENTOKeyPressed
       
    }//GEN-LAST:event_txtNUMDOCUMENTOKeyPressed

    private void txtNUMDOCUMENTOFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNUMDOCUMENTOFocusGained
        txtNUMDOCUMENTO.selectAll();
    }//GEN-LAST:event_txtNUMDOCUMENTOFocusGained

    private void gerarDisponiveis(){
        
        //identifica em que ano o sistema foi acessado
        mes = umMetodo.retornaMesVigente();    
        
        //verifico se o flag esta False pois o retorno do metodo é False
        boolean statusFlagFalse = daoFLAG.consFlagFalseDAO();
        
        if(mes == 1 && statusFlagFalse){
            //se for (JAN e flag=FALSE) gera a numeracao anual e atualiza o flag para TRUE
            objGerarNumeracao.gerarNumsDespachosAnual();
            daoFLAG.atualizarFlagTrueDAO();
        }
        else if(mes == 1 && !statusFlagFalse){
            //se for (JAN e flag=TRUE) se faltar numero vai gerar mais DISPONIVEIS
            if (!objGerarNumeracao.temNumeroDisponivel()) {
                //JOptionPane.showMessageDialog(rootPane, "NÃO TEM NUMERO DISPONIVEL");
                objGerarNumeracao.gerarNumsDespachosAdicionais();                
            }         
        }else if(mes > 1)
        {
                //De (FEV a DEZ) atualiza o FLAG para FALSE 
                daoFLAG.atualizarFlagFalseDAO();
                
                //De FEV a DEZ se o flag=TRUE se faltar numero vai gerar mais DISPONIVEIS
                if(mes > 1 && statusFlagFalse){                    
                    if (!objGerarNumeracao.temNumeroDisponivel()) {
                        //JOptionPane.showMessageDialog(rootPane, "NÃO TEM NUMERO DISPONIVEL");
                        objGerarNumeracao.gerarNumsDespachosAdicionais();                
                    }         
                }            
        }                
    }
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(umMetodo.tabelaEstaVazia("TBLNUMDESPACHOS")){
            ctrlNumDespacho.salvarNumDespachoInicialTabelaVazia();
        }
         
        gerarDisponiveis();
        
        if(umMetodo.tabelaEstaVazia(tabela)){
            btnGerarExcel.setEnabled(false);
        }
        
    }//GEN-LAST:event_formWindowOpened

    private void abrirListaTipoDocumentos(){
        
        if (cadastrando || editando) {
            tabela_da_lista = "TBLTIPODOCUMENTOS";
            F_LISTAPADRAO frm = new F_LISTAPADRAO(new javax.swing.JFrame(), true);
            frm.setVisible(true);
            txtASSUNTO.requestFocus();
            txtTIPODOCUMENTO.setText(TipoDocumento);
        }   
        
    }    
        
    private void txtOBSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOBSKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnGravar.requestFocus();
        }
        if(editando){
            txtOBS.setForeground(Color.BLACK); 
        }
    }//GEN-LAST:event_txtOBSKeyPressed

    private void txtOBSFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOBSFocusGained
       
    }//GEN-LAST:event_txtOBSFocusGained

    private void txtASSUNTOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtASSUNTOKeyPressed
       if(editando){
            txtASSUNTO.setForeground(Color.BLACK); 
       }
       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtDESTINO.requestFocus();
        }
    }//GEN-LAST:event_txtASSUNTOKeyPressed

    private void txtCADASTRANTEKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCADASTRANTEKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCADASTRANTEKeyPressed

    private void txtTIPODOCUMENTOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTIPODOCUMENTOMouseClicked
        if(editando)abrirListaTipoDocumentos();
    }//GEN-LAST:event_txtTIPODOCUMENTOMouseClicked

    private void txtTIPODOCUMENTOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTIPODOCUMENTOKeyPressed
        if(editando)abrirListaTipoDocumentos();
    }//GEN-LAST:event_txtTIPODOCUMENTOKeyPressed

    private void txtDESTINOFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDESTINOFocusLost
        if(cadastrando){
            if(txtDESTINO.getText().length() > 0){
                btnGravar.setEnabled(true);
                txtOBS.requestFocus();
            }else{
                JOptionPane.showMessageDialog(null,"Campo obrigatório, preencha o campo destino para continuar!","Campo vazio!",2);
                txtDESTINO.requestFocus();
            }
        }
    }//GEN-LAST:event_txtDESTINOFocusLost

    private void txtASSUNTOFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtASSUNTOFocusLost
        if(cadastrando){
            if(txtASSUNTO.getText().length() > 0){
                txtDESTINO.requestFocus();
            }else{
                JOptionPane.showMessageDialog(null,"Campo obrigatório, preencha o campo assunto para continuar!","Campo vazio!",2);
                txtASSUNTO.requestFocus();
            }       
        }
    }//GEN-LAST:event_txtASSUNTOFocusLost

    private void txtTIPODOCUMENTOMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTIPODOCUMENTOMouseEntered
        if(editando){            
            txtTIPODOCUMENTO.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }//GEN-LAST:event_txtTIPODOCUMENTOMouseEntered

    private void Edicao() {
        //metodo para quando usuario clicar em Editar 
        cadastrando = false;
        editando    = true;        
        boolean Habilitar = true;
        Component[] c     = null;
        btnNovo.setEnabled(!Habilitar);
        btnSair.setEnabled(!Habilitar);
        btnGravar.setEnabled(Habilitar);
        btnEditar.setEnabled(!Habilitar);
        btnGerarExcel.setEnabled(!Habilitar);
        btnVoltar.setEnabled(Habilitar);
        btnVoltar.setText("Cancelar");

        //mostra o titulo do formulario de acordo com o status
        umMetodo.mostrarTituloDoFormulario();
        
        PreencherTabela(sqlVazia);      
                
        //desabilitando os edits para edicao
        c = jpBoxDados.getComponents();
        for (int i = 0; i < c.length; i++) {
            c[i].setEnabled(Habilitar);
        }

        //habilitando a pesquisa  e preenchendo a tabela se tiver registros
        c = jBoxPesquisar.getComponents();
        for (int i = 0; i < c.length; i++) {
            c[i].setEnabled(!Habilitar);
        }
        //HABILITANDO OS EDITS PARA EDIÇÃO
        txtDESTINO.setEditable(Habilitar);
        txtDESTINO.setCursor(new Cursor(Cursor.HAND_CURSOR));        
        txtASSUNTO.setEditable(Habilitar);
        txtASSUNTO.setCursor(new Cursor(Cursor.HAND_CURSOR));     
        txtOBS.setEditable(Habilitar);
        txtOBS.setCursor(new Cursor(Cursor.HAND_CURSOR));
        txtNUMDOCUMENTO.setCursor(null);
        
        
    }

    public void Leitura() {
        //formatacao inicial dos botoes ao abrir o formulario
        //mostra o titulo do formulario de acordo com o status
        boolean Habilitar                   = true;
        Component[] c                       = null;
        cadastrando                         = false;
        editando                            = false;
        consultandoInativos                 = false;
        
        btnNovo.setEnabled(Habilitar);
        btnSair.setEnabled(Habilitar);
        btnGravar.setEnabled(!Habilitar);
        btnGravar.setText("Gravar");
        btnEditar.setEnabled(!Habilitar);
        btnVoltar.setEnabled(!Habilitar);        
        btnVoltar.setText("Voltar");

        txtDESTINO.setForeground(Color.blue);        
        txtTIPODOCUMENTO.setForeground(Color.blue);        
        txtASSUNTO.setForeground(Color.blue);
        txtOBS.setForeground(Color.blue);  
        
        //preenchendo a tabela com os registros
        PreencherTabela(sqlDefault); 
        
        //mostra o titulo do formulario de acordo com o status
        this.setTitle(umMetodo.mostrarTituloDoFormulario());         
               
        //desabilitando os edits para edicao
        c = jpBoxDados.getComponents();
        for (int i = 0; i < c.length; i++) {
            c[i].setEnabled(!Habilitar);
            umMetodo.limparTodosCampos(this);
            txtPESQUISA.requestFocus();
        }
        if(!umMetodo.tabelaEstaVazia(tabela)){
            //se a tabela não estiver vazia
            txtPESQUISA.setEnabled(true);
            txtPESQUISA.setEditable(true);
            txtPESQUISA.requestFocus();
            btnGerarExcel.setEnabled(Habilitar);
        }
             
    }

    public void HabilitarDesabilitarBotoes(boolean Habilitar) {
        //ações para quando clicar em cada botão
        Component[] c = null;

        btnNovo.setEnabled(Habilitar);
        btnGravar.setEnabled(!Habilitar);
        btnEditar.setEnabled(Habilitar);
        btnVoltar.setEnabled(!Habilitar);
        btnSair.setEnabled(Habilitar);

        //habilitando os edits para edicao
        c = jBoxDados.getComponents();
        for (int i = 0; i < c.length; i++) {
            c[i].setEnabled(!Habilitar);
        }

        //desabilitando a pesquisa enquanto cadastra
        c = jBoxPesquisar.getComponents();
        for (int i = 0; i < c.length; i++) {
            c[i].setEnabled(Habilitar);
        }

    }

    public void PreencherTabela(String sql) {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Documento", "Mes/Ano", "Destino", "Data"};
        conexao.ExecutarPesquisaSQL(sql);
        try {
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                dados.add(new Object[]{
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("numdespacho"),
                    conexao.rs.getString("mesano"),
                    conexao.rs.getString("destino"),
                    sdf.format(conexao.rs.getDate("data"))
                });
                totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
            };

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabelaDESPACHOS.setModel(modelo);
            //define tamanho das colunas
            jTabelaDESPACHOS.getColumnModel().getColumn(0).setPreferredWidth(80);  //define o tamanho da coluna
            jTabelaDESPACHOS.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaDESPACHOS.getColumnModel().getColumn(1).setPreferredWidth(200);
            jTabelaDESPACHOS.getColumnModel().getColumn(1).setResizable(false);
            jTabelaDESPACHOS.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTabelaDESPACHOS.getColumnModel().getColumn(2).setResizable(false);
            jTabelaDESPACHOS.getColumnModel().getColumn(3).setPreferredWidth(600);
            jTabelaDESPACHOS.getColumnModel().getColumn(3).setResizable(false);
            jTabelaDESPACHOS.getColumnModel().getColumn(4).setPreferredWidth(120);
            jTabelaDESPACHOS.getColumnModel().getColumn(4).setResizable(false);
            //define propriedades da tabela
            jTabelaDESPACHOS.getTableHeader().setReorderingAllowed(false);        //nao podera ser reorganizada
            jTabelaDESPACHOS.setAutoResizeMode(jTabelaDESPACHOS.AUTO_RESIZE_OFF);          //nao será possivel redimencionar a tabela
            jTabelaDESPACHOS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha 

        } catch (SQLException ex) {
            //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
            JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }

    }

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
            java.util.logging.Logger.getLogger(F_DESPACHOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_DESPACHOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_DESPACHOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_DESPACHOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                F_DESPACHOS dialog = new F_DESPACHOS(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGerarExcel;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLayeredPane jBoxBotoes;
    private javax.swing.JLayeredPane jBoxDados;
    private javax.swing.JLayeredPane jBoxPesquisar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTabelaDESPACHOS;
    private javax.swing.JPanel jpBoxDados;
    private javax.swing.JTextArea txtASSUNTO;
    private javax.swing.JTextField txtCADASTRANTE;
    private javax.swing.JTextField txtCODIGO;
    private javax.swing.JTextField txtDESTINO;
    private javax.swing.JTextField txtNUMDOCUMENTO;
    private javax.swing.JTextField txtOBS;
    private javax.swing.JTextField txtPESQUISA;
    private javax.swing.JTextField txtTIPODOCUMENTO;
    // End of variables declaration//GEN-END:variables

    

}
