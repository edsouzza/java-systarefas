package visao;

import Dao.DAOPatriTensTransferido;
import Dao.DAOPatriTransferido;
import Dao.DAOPatrimonio;
import biblioteca.Biblioteca;
import biblioteca.CampoTxtLimitadoPorQdeCaracteres;
import biblioteca.CampoTxtLimitadoPorQdeCaracteresUpperCase;
import biblioteca.GerarTXT;
import biblioteca.LeitorArquivoTXTEnviarDadosParaUmaLista;
import biblioteca.MetodosPublicos;
import biblioteca.SelecionarArquivoTexto;
import static biblioteca.VariaveisPublicas.anoVigente;
import static biblioteca.VariaveisPublicas.codigoUsuario;
import static biblioteca.VariaveisPublicas.lstListaCampos;
import static biblioteca.VariaveisPublicas.lstListaStrings;
import static biblioteca.VariaveisPublicas.dataDoDia;
import static biblioteca.VariaveisPublicas.destinoTransferidos;
import static biblioteca.VariaveisPublicas.numMemoTransferido;
import static biblioteca.VariaveisPublicas.origemTransferidos;
import static biblioteca.VariaveisPublicas.valorItem;
import static biblioteca.VariaveisPublicas.gerouNumo;
import static biblioteca.VariaveisPublicas.lstAuxiliar;
import controle.ControleGravarLog;
import controle.CtrlPatriItenstransferido;
import controle.CtrlPatriTransferido;
import controle.CtrlPatrimonio;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import modelo.PatriTensTransferido;
import modelo.PatriTransferido;
import modelo.Patrimonio;
import relatorios.GerarRelatorios;

public class F_GERARTXTENVIOAUTO extends javax.swing.JFrame {
    MetodosPublicos                umMetodo                     = new MetodosPublicos();
    Biblioteca                     umaBiblio                    = new Biblioteca();
    GerarTXT                       objGerarTXT                  = new GerarTXT();
    DefaultListModel               model                        = new DefaultListModel();
    Patrimonio                     umModPatrimonio              = new Patrimonio();
    CtrlPatrimonio                 umControlePatrimonio         = new CtrlPatrimonio();
    ControleGravarLog              umGravarLog                  = new ControleGravarLog();
    CtrlPatriItenstransferido      umCtrlPatrItemTranferido     = new CtrlPatriItenstransferido();
    CtrlPatriTransferido           umCtrlPatriTranferido        = new CtrlPatriTransferido();
    PatriTransferido               umModPatriTransferido        = new PatriTransferido();
    PatriTensTransferido           umModPatrItensTransferido    = new PatriTensTransferido();
    PatriTensTransferido           objModPatriTemTransferido    = new PatriTensTransferido();
    CtrlPatriItenstransferido      ctrlPatriTenstransferido     = new CtrlPatriItenstransferido();
    DAOPatriTensTransferido        umDAOPatriItens              = new DAOPatriTensTransferido();
    DAOPatriTransferido            umDAOPatriTransferido        = new DAOPatriTransferido();
    DAOPatrimonio                  umDaoPatri                   = new DAOPatrimonio();
    DateFormat                     sdf                          = new SimpleDateFormat("dd/MM/yyyy");
    Date dataDia                                                = dataDoDia; 
    
    String sTipo, sChapa, sSerie, sEstacao, sCodigo, sStatus, sMotivo, sObs, sOrigem, sDestino, sMemorando, sObservacoes, sObsMemo, sAssunto, sMemoobservacao, sSecaoid, sClienteid, caminhoTXT, linha, sstatusItem, destinoMemo, proxMemo  = "";
    int iTipoid, codItem, codMOdelo, codPatr, contador, codSecao, codCliente, cont, qdeItens, contReg, codigoDoItem, totalLinhas, totalLinhasTXT, contvez, numeroMemo = 0;
    Boolean metodoPADRAOINIFIM,inserindo,inseriuItem, gerouTXT = false;   
    String[] getDados;
    List<String[]> dadosLidos = null;
    
    public F_GERARTXTENVIOAUTO(java.awt.Frame parent, boolean modal) {       
        initComponents();
        Leitura();
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
        umMetodo.configurarBotoes(btnGerarTXTDeEnvio);
        umMetodo.configurarBotoes(btnLerTXTAddItensNaLista);
        umMetodo.configurarBotoes(btnLimpar);
        umMetodo.configurarBotoes(btnSair);
        umMetodo.configurarBotoes(btnNovo);
        
        lstITENS.setForeground(Color.blue);        
        lstITENS.setFont(new Font("TimesRoman", Font.BOLD, 14));  
        umaBiblio.configurarCamposTextos(txtDESTINO);
        txtOBSERVACAO.setDocument(new CampoTxtLimitadoPorQdeCaracteres(80));  
        txtOBSERVACAO.setFont(new Font("TimesRoman", Font.BOLD, 14));
        txtASSUNTO.setDocument(new CampoTxtLimitadoPorQdeCaracteresUpperCase(80));  
        txtASSUNTO.setFont(new Font("TimesRoman", Font.BOLD, 14));
        txtMENSAGEM.setFont(new Font("TimesRoman", Font.BOLD, 14));
        txtMENSAGEM.setForeground(Color.red);
        txtMEMORANDO.setForeground(Color.red);
        
        //Se o usuario apagar o texto N/C vai desabilitar o botao btnLerTXTAddItensNaLista automaticamente
        txtOBSERVACAO.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                verificarTexto();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                verificarTexto();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                verificarTexto();
            }

            private void verificarTexto() {
                // Usa SwingUtilities.invokeLater para evitar conflito de eventos durante a digitação
                SwingUtilities.invokeLater(() -> {
                    String texto = txtOBSERVACAO.getText().trim();

                    if (texto.isEmpty()) {
                        txtOBSERVACAO.setText("N/C");
                        txtOBSERVACAO.setCaretPosition(txtOBSERVACAO.getText().length()); // coloca o cursor no final
                    }
                });
            }
        });        
                
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPANELTOTAL = new javax.swing.JPanel();
        jBoxCabecalho = new javax.swing.JLayeredPane();
        txtMEMORANDO = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtORIGEM = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtDESTINO = new javax.swing.JTextField();
        txtOBSERVACAO = new javax.swing.JTextField();
        txtASSUNTO = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnGerarTXTDeEnvio = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnLerTXTAddItensNaLista = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstITENS = new javax.swing.JList();
        btnLerEnviarDados = new javax.swing.JButton();
        txtMENSAGEM = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GERAR ARQUIVO TXT AUTOMATICAMENTE PARA ENVIO DE PATRIMONIOS PARA OUTRA UNIDADE");
        getContentPane().setLayout(null);

        jBoxCabecalho.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxCabecalho.setName("panelDados"); // NOI18N

        txtMEMORANDO.setEditable(false);
        txtMEMORANDO.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMEMORANDO.setForeground(new java.awt.Color(51, 51, 255));

        jLabel6.setText("MEMORANDO");

        jLabel4.setText("OBSERVAÇÃO");

        txtORIGEM.setEditable(false);
        txtORIGEM.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtORIGEM.setForeground(new java.awt.Color(51, 51, 255));
        txtORIGEM.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtORIGEMFocusGained(evt);
            }
        });
        txtORIGEM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtORIGEMMouseClicked(evt);
            }
        });

        jLabel7.setText("ORIGEM");

        jLabel9.setText("DESTINO");

        txtDESTINO.setEditable(false);
        txtDESTINO.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDESTINO.setForeground(new java.awt.Color(51, 51, 255));
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
        txtDESTINO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDESTINOKeyPressed(evt);
            }
        });

        txtOBSERVACAO.setEditable(false);
        txtOBSERVACAO.setForeground(new java.awt.Color(51, 51, 255));
        txtOBSERVACAO.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtOBSERVACAOFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtOBSERVACAOFocusLost(evt);
            }
        });
        txtOBSERVACAO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtOBSERVACAOKeyPressed(evt);
            }
        });

        txtASSUNTO.setEditable(false);
        txtASSUNTO.setForeground(new java.awt.Color(51, 51, 255));
        txtASSUNTO.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtASSUNTOFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtASSUNTOFocusLost(evt);
            }
        });
        txtASSUNTO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtASSUNTOMouseClicked(evt);
            }
        });
        txtASSUNTO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtASSUNTOKeyPressed(evt);
            }
        });

        jLabel8.setText("ASSUNTO");

        javax.swing.GroupLayout jBoxCabecalhoLayout = new javax.swing.GroupLayout(jBoxCabecalho);
        jBoxCabecalho.setLayout(jBoxCabecalhoLayout);
        jBoxCabecalhoLayout.setHorizontalGroup(
            jBoxCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxCabecalhoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBoxCabecalhoLayout.createSequentialGroup()
                        .addGap(904, 904, 904)
                        .addComponent(jLabel2)
                        .addGap(0, 84, Short.MAX_VALUE))
                    .addGroup(jBoxCabecalhoLayout.createSequentialGroup()
                        .addGroup(jBoxCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtASSUNTO, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jBoxCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOBSERVACAO)
                            .addGroup(jBoxCabecalhoLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jBoxCabecalhoLayout.createSequentialGroup()
                        .addGroup(jBoxCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtMEMORANDO, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jBoxCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jBoxCabecalhoLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7))
                            .addGroup(jBoxCabecalhoLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(txtORIGEM, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10)
                        .addGroup(jBoxCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDESTINO)
                            .addGroup(jBoxCabecalhoLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jBoxCabecalhoLayout.setVerticalGroup(
            jBoxCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxCabecalhoLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jBoxCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jBoxCabecalhoLayout.createSequentialGroup()
                        .addGroup(jBoxCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDESTINO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtMEMORANDO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jBoxCabecalhoLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtORIGEM, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBoxCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jBoxCabecalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOBSERVACAO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtASSUNTO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(127, 127, 127))
        );
        jBoxCabecalho.setLayer(txtMEMORANDO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxCabecalho.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxCabecalho.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxCabecalho.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxCabecalho.setLayer(txtORIGEM, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxCabecalho.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxCabecalho.setLayer(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxCabecalho.setLayer(txtDESTINO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxCabecalho.setLayer(txtOBSERVACAO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxCabecalho.setLayer(txtASSUNTO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxCabecalho.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);

        btnGerarTXTDeEnvio.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnGerarTXTDeEnvio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TICK.PNG"))); // NOI18N
        btnGerarTXTDeEnvio.setText("Gerar TXT Envio");
        btnGerarTXTDeEnvio.setToolTipText("");
        btnGerarTXTDeEnvio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGerarTXTDeEnvio.setEnabled(false);
        btnGerarTXTDeEnvio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarTXTDeEnvioActionPerformed(evt);
            }
        });

        btnLimpar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_limpar.gif"))); // NOI18N
        btnLimpar.setText("Cancelar");
        btnLimpar.setToolTipText("");
        btnLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpar.setEnabled(false);
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnSair.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sair.gif"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.setToolTipText("");
        btnSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnNovo.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_blocoNotas.gif"))); // NOI18N
        btnNovo.setText("Novo");
        btnNovo.setToolTipText("");
        btnNovo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnLerTXTAddItensNaLista.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnLerTXTAddItensNaLista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_Ok1.gif"))); // NOI18N
        btnLerTXTAddItensNaLista.setText("Ler TXT Series");
        btnLerTXTAddItensNaLista.setToolTipText("");
        btnLerTXTAddItensNaLista.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLerTXTAddItensNaLista.setEnabled(false);
        btnLerTXTAddItensNaLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLerTXTAddItensNaListaActionPerformed(evt);
            }
        });

        lstITENS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lstITENS.setEnabled(false);
        lstITENS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstITENSMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lstITENS);

        btnLerEnviarDados.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnLerEnviarDados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_Ok.gif"))); // NOI18N
        btnLerEnviarDados.setText("Ler TXT Enviar");
        btnLerEnviarDados.setToolTipText("");
        btnLerEnviarDados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLerEnviarDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLerEnviarDadosActionPerformed(evt);
            }
        });

        txtMENSAGEM.setEditable(false);
        txtMENSAGEM.setForeground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout jPANELTOTALLayout = new javax.swing.GroupLayout(jPANELTOTAL);
        jPANELTOTAL.setLayout(jPANELTOTALLayout);
        jPANELTOTALLayout.setHorizontalGroup(
            jPANELTOTALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPANELTOTALLayout.createSequentialGroup()
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGerarTXTDeEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnLerTXTAddItensNaLista, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLerEnviarDados, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPANELTOTALLayout.createSequentialGroup()
                .addGroup(jPANELTOTALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBoxCabecalho)
                    .addComponent(jScrollPane1)
                    .addComponent(txtMENSAGEM))
                .addGap(10, 10, 10))
        );
        jPANELTOTALLayout.setVerticalGroup(
            jPANELTOTALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPANELTOTALLayout.createSequentialGroup()
                .addComponent(jBoxCabecalho, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMENSAGEM, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPANELTOTALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGerarTXTDeEnvio, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLerTXTAddItensNaLista, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLerEnviarDados, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPANELTOTAL);
        jPANELTOTAL.setBounds(14, 11, 1020, 660);

        setSize(new java.awt.Dimension(1055, 723));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Leitura() 
    {  
        limpar();      
    }        
        
    private void addItensNaTela()
    {          
        for(int i=0; i<lstAuxiliar.size(); i++)
        {
           sSerie      = (String) lstAuxiliar.get(i);            
           int codPatr = umMetodo.retornaCodigo("tblpatrimonios", "serie", sSerie);
      
            sDestino   = txtDESTINO.getText();
            sOrigem    = txtORIGEM.getText();            
            sMemorando = txtMEMORANDO.getText();
            sAssunto   = txtASSUNTO.getText();        
            sObsMemo   = txtOBSERVACAO.getText();        
            iTipoid    = umMetodo.getCodigoPassandoString("tbltipos", "tipo", sTipo);
            sCodigo    = String.valueOf(codPatr);

            if(umMetodo.Emicro(codPatr)){            

                switch (sDestino) {
                    case "CEJUR":                    
                        sStatus     = "ATIVO";
                        sSecaoid    = "3";
                        sClienteid  = "196";  
                        codSecao    = 3;
                        codCliente  = 196;   
                        sEstacao    = "PGMCEJURC00";
                        break;
                    case "CEJUSC":                    
                        sStatus     = "ATIVO";
                        sSecaoid    = "21";
                        sClienteid  = "197";  
                        codSecao    = 21;
                        codCliente  = 197;   
                        sEstacao    = "PGMCEJUSCC0";
                        break;
                    case "PFM":                       
                        sStatus     = "ATIVO";
                        sSecaoid    = "17";
                        sClienteid  = "227";  
                        codSecao    = 17;
                        codCliente  = 227;   
                        sEstacao    = "PGMPFMC000";
                        break;
                    case "BIBLIOTECA":                    
                        sStatus     = "ATIVO";
                        sSecaoid    = "3";
                        sClienteid  = "196";  
                        codSecao    = 22;
                        codCliente  = 195;   
                        sEstacao    = "PGMCEJURC00";
                        break;
                    default:                    
                        sStatus     = "INATIVO";
                        sSecaoid    = "30";
                        sClienteid  = "202";
                        codSecao    = 30;
                        codCliente  = 202;
                        sEstacao    = "PGMCGGMC000";
                }             
            }else{
                sEstacao = umMetodo.getStringPassandoCodigo("tblpatrimonios", "estacao", codPatr);
                sStatus     = "INATIVO";
                sSecaoid    = "30";
                sClienteid  = "202";
                codSecao    = 30;
                codCliente  = 202;
            }

            sMotivo      = dataDoDia+" : Transferido de "+sOrigem+" para "+sDestino+" através do Memorando "+sMemorando+"";
            sObservacoes = dataDoDia+" : Transferido de "+sOrigem+" para "+sDestino+" através do Memorando "+sMemorando+"";

            //adicionando item na lista   sMemorando  sObsMemo
            String dados = sCodigo+";"+sEstacao+";"+sSecaoid+";"+sClienteid+";"+sMemorando+";"+sOrigem+";"+sDestino+";"+sSerie+";"+sStatus+";"+sAssunto+";"+sObsMemo; 
            String tela  = sCodigo+";"+sEstacao+";"+sSecaoid+";"+sClienteid+";"+sMemorando+";"+sOrigem+";"+sDestino+";"+sSerie+";"+sStatus; 
            String item  = tela;    
            
            lstListaStrings.add(dados);
            model.addElement(tela);
            lstITENS.setModel(model); 
            
        }
       
    }            
    
    private void btnGerarTXTDeEnvioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarTXTDeEnvioActionPerformed
       
       objGerarTXT.gerarTXTDELISTA(lstListaStrings);        
       umMetodo.retornarTodosDadosInseridosNaListaDeStrings(lstListaStrings, false);    
       contvez++;
       limpar();      
       btnLerEnviarDados.setEnabled(true);
       btnGerarTXTDeEnvio.setEnabled(false);
       lstITENS.setEnabled(false);
       btnSair.setEnabled(true);
       inserindo=false;                   
       gerouNumo=true; 
       gerouTXT=true;
       contReg = 0;
       
       this.setTitle("GERAR ARQUIVO TXT AUTOMATICAMENTE PARA ENVIO DE PATRIMONIOS PARA OUTRA UNIDADE");
                                       
    }//GEN-LAST:event_btnGerarTXTDeEnvioActionPerformed

    private void exclusaoDeItensDoMemoCancelado()
    {
        sMemorando = txtMEMORANDO.getText();        
        umMetodo.deletarItensDoMemorando(sMemorando);
        umMetodo.excluirMemorandoSemItens();        
        limpar();
        btnLimpar.setEnabled(false);
    }
    
    private void limpar(){
        btnNovo.setEnabled(true);
        btnSair.setEnabled(true);
        btnGerarTXTDeEnvio.setEnabled(false);
        btnLerTXTAddItensNaLista.setEnabled(false);
        txtORIGEM.setEditable(false);
        txtASSUNTO.setEditable(false);
        txtOBSERVACAO.setEditable(false);
        txtDESTINO.setEditable(false);
        btnLimpar.setEnabled(false);
        txtORIGEM.setText("");
        txtMEMORANDO.setText("");
        txtMENSAGEM.setText("");
        txtDESTINO.setText("");     
        txtOBSERVACAO.setText("");      
        txtASSUNTO.setText("");      
        lstListaCampos.clear();
        model.clear();
        inserindo=false;    
        qdeItens=0;   
        contReg = 0;
    }
    
    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        //Este é o botão de cancelamento da operação
        limpar();        
        JOptionPane.showMessageDialog(null, "Processo cancelado pelo usuário!","Cancelado",2);      
        this.setTitle("GERAR ARQUIVO TXT AUTOMATICAMENTE PARA ENVIO DE PATRIMONIOS PARA OUTRA UNIDADE");
        txtMENSAGEM.setText("");
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        umDAOPatriItens.excluirItensProcessandoDAO();
        String numeroMemorandoExcluir =  umDAOPatriTransferido.getMemoComStatusProcessandoDAO();
        umMetodo.deletarMemorando(numeroMemorandoExcluir);
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed
    
    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        //abrir lista de tipos de equipamentos para cadastrar
        inserindo=true;                
        numeroMemo = umMetodo.gerarProximoNumeroMemoTransferir();               
        
        if (contvez == 0){
            proxMemo = String.valueOf(numeroMemo+"/"+anoVigente);          
            txtMEMORANDO.setText(proxMemo);            
        }
        if (contvez > 0){
            proxMemo = String.valueOf(numeroMemo+contvez+"/"+anoVigente);
            txtMEMORANDO.setText(proxMemo);   
        }
        
        txtMENSAGEM.setText("Digite o destino dos equipamentos...");
        txtORIGEM.setText("CGGM/INFO");
        txtDESTINO.requestFocus();        
               
        //habilitando edição do txtSerie         
        txtOBSERVACAO.setEditable(true);        
        txtASSUNTO.setEditable(true);        
        txtORIGEM.setEditable(true);        
        txtDESTINO.setEditable(true);     
        btnNovo.setEnabled(false);        
        btnSair.setEnabled(false);        
        btnLimpar.setEnabled(true);
        btnGerarTXTDeEnvio.setEnabled(false);
        btnLerEnviarDados.setEnabled(false);
        valorItem=0;
        lstAuxiliar.clear();
        lstListaStrings.clear();
        model.clear();        
                
    }//GEN-LAST:event_btnNovoActionPerformed
    
private void LerSeriesDoTXT() 
{
    SelecionarArquivoTexto select = new SelecionarArquivoTexto();
    caminhoTXT = select.ImportarTXT();
    
    // Verifica se o usuário cancelou a seleção
    if (caminhoTXT == null || caminhoTXT.isEmpty()) {        
        return;
    }

    totalLinhasTXT = umMetodo.contarLinhasDoArquivoTXT(caminhoTXT);
    
    // Desabilita os botões
    lstITENS.setEnabled(false);
    btnSair.setEnabled(false);
    btnLimpar.setEnabled(false);
    umMetodo.desabilitarEdicaoJTextFields(jBoxCabecalho); // Desabilita a edição de todos os JTextFields dentro do container(JBoxCabecalho)
    btnLerTXTAddItensNaLista.setEnabled(false);
    
    setTitle("Processando a leitura de "+ totalLinhasTXT +" registros ao final do processamento os botões serão habilitados para prosseguimento, favor aguardar...");
    
    // Cria e mostra a barra de progresso
    F_BARRAPROGRESSOLENDOTXT barra = new F_BARRAPROGRESSOLENDOTXT((Frame) SwingUtilities.getWindowAncestor(this));
    barra.setAlwaysOnTop(true);
    barra.setVisible(true);

    // Calcula total de linhas antes do SwingWorker para não bloquear a UI
    totalLinhas = 0;
    try (BufferedReader contador = new BufferedReader(new FileReader(caminhoTXT))) {
        while (contador.readLine() != null) {
            totalLinhas++;
        }
    } catch (IOException e) {
        e.printStackTrace();
        barra.dispose();
        return;
    }

    // Inicia a leitura real no SwingWorker
    SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
        @Override
        protected Void doInBackground() throws Exception {
            lstAuxiliar.clear();

            try (BufferedReader lerArq = new BufferedReader(new FileReader(caminhoTXT))) {
                String linha;
                int linhaAtual = 0;

                while ((linha = lerArq.readLine()) != null) {
                    // Ignora as linhas em branco do txt
                    if (!linha.trim().isEmpty()) {
                        lstAuxiliar.add(linha);
                    }
                    linhaAtual++;
                    publish(linhaAtual); // Atualiza a barra
                    Thread.sleep(30);    // Força a UI a respirar, opcional
                }
            }

            return null;
        }

        @Override
        protected void process(java.util.List<Integer> chunks) {
            int linhaAtual = chunks.get(chunks.size() - 1);
            barra.atualizarProgressoPelaQdeRegs(linhaAtual, totalLinhas);
        }

        @Override
        protected void done() {
            barra.dispose(); // Fecha a barra assim que a leitura termina

            // Agora, cria um novo SwingWorker só para adicionar os itens ao modelo
            SwingWorker<Void, Void> adicionaItensWorker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // Mostra os itens na tela
                    addItensNaTela();  
                    return null;
                }

                @Override
                protected void done() {
                    lstITENS.repaint();  // Força atualização da UI  
                    setTitle("Total de ítens inseridos: " + lstAuxiliar.size());

                    // Chama o método finalizarLeitura após a inserção dos itens
                    finalizarLeitura();
                }
            };

            adicionaItensWorker.execute(); // Executa o segundo processo     
        }
    };      
    worker.execute();
}

private void finalizarLeitura() {
    // Reabilita os botões em caso de erro
    btnGerarTXTDeEnvio.setEnabled(true);
    btnLimpar.setEnabled(true);
    lstITENS.setEnabled(true);
    txtMENSAGEM.setText("Clique em Gerar TXT para prosseguir...");  
}
    
    private void btnLerTXTAddItensNaListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLerTXTAddItensNaListaActionPerformed
         if(todosCamposPreenchidos()){
            LerSeriesDoTXT();
            txtMENSAGEM.setText("Aguarde o final do processamento, o botão Gerar TXT Envio será liberado em breve...");        

            if(umMetodo.itemEnviadoAtravesDeOutroMemorando(sSerie)){
                //identificar o numero do memorando atraves da serie
                String numeroDoMemorando = umMetodo.getStringPassandoString("TBLITENSMEMOTRANSFERIDOS", "numemo", "serie", sSerie);            
                JOptionPane.showMessageDialog(null, "A série "+sSerie+" esta inserida no memorando "+numeroDoMemorando+" e aguarda seu envio através do mesmo!", "Série utilizada no Memorando "+numeroDoMemorando, 2);                      
                btnSair.setEnabled(false);
            }        
        }else{
            
            Object[][] camposComRotulo = {
                {txtDESTINO, "Destino"},
                {txtASSUNTO, "Assunto"},
                {txtOBSERVACAO, "Observação"}
            };

            for (Object[] campoERotulo : camposComRotulo) {
                JTextField campo = (JTextField) campoERotulo[0];
                String nome      = (String) campoERotulo[1];

                if (campo.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O campo " + nome + " está vazio!", "Aviso", JOptionPane.WARNING_MESSAGE);
                    campo.requestFocus(); 
                    btnGerarTXTDeEnvio.setEnabled(false);
                    break;
                }else{
                    btnGerarTXTDeEnvio.setEnabled(true);
                }
            }            
        }         
           
    }//GEN-LAST:event_btnLerTXTAddItensNaListaActionPerformed
   
    
    private void lstITENSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstITENSMouseClicked
        // Se a lista estiver desabilitada, ignora o clique
        if (!lstITENS.isEnabled()) {
            return;
        }
        
        codItem = lstITENS.getSelectedIndex();  
        
        if (lstITENS.getSelectedValue() != null) {
                        
            //Dados do item separados por ";" preciso transforma-los em um vetor usando como separador o ";"
            String itemClicado = lstITENS.getSelectedValue().toString();
            //System.out.println(itemClicado);
            
            //Criando um vetor chapa para receber o vetor convertido
            String[] chapa;              
            //transformando os dados em um vetor usando como separador o ";"      
            chapa = itemClicado.split(";");
            //Visualizando o vetor
            //System.out.println(Arrays.toString(chapa));
            //Pegando o valor desejado que é a chapa que é o indice 7 do vetor
            //System.out.println(chapa[7]);
            String chapaSelecionada = chapa[7];
            //Agora com a chapa em maos pesquise o codigo do item na tabela TBLITENSMEMOTRANSFERIDOS para exclusao
            codigoDoItem = umMetodo.getCodigoPassandoMaisDeUmParametroString("TBLITENSMEMOTRANSFERIDOS", "serie", chapaSelecionada, "numemo", sMemorando);            
        }             
            
    }//GEN-LAST:event_lstITENSMouseClicked

    private void atualizarStatusEquipamentos()
    {        
        //setando os valores no objeto do modelo
        
        codPatr = Integer.valueOf(sCodigo);
        String Motivobd = umMetodo.getStringPassandoCodigo("tblpatrimonios", "motivo", codPatr);
        String Obsbd    = umMetodo.getStringPassandoCodigo("tblpatrimonios", "observacoes", codPatr);
        
        umModPatrimonio.setCodigo(Integer.valueOf(sCodigo));
        umModPatrimonio.setEstacao(sEstacao);
        umModPatrimonio.setIp("0");
        umModPatrimonio.setSecaoid(Integer.valueOf(sSecaoid));
        umModPatrimonio.setClienteid(Integer.valueOf(sClienteid));
        umModPatrimonio.setStatus(sStatus);    
        umModPatrimonio.setDatainativacao(dataDia);
        
        umModPatrimonio.setMotivo(Motivobd+"\n"+sdf.format(dataDia)+" : Transferido de "+sOrigem+" para "+sDestino+" atraves do Memorando n. "+sMemorando+"."); 
        umModPatrimonio.setObservacoes(Obsbd+"\n"+sdf.format(dataDia)+" : Transferido de "+sOrigem+" para "+sDestino+" atraves do Memorando n. "+sMemorando+".");

        if (umControlePatrimonio.AtualizarPatrimoniosEncaminhados(umModPatrimonio)) {
            contador = 1;            
            umGravarLog.gravarLog("envio de equipamentos para "+sDestino+" atraves do Memorando n. "+sMemorando);
        }
        btnLerEnviarDados.setEnabled(false);
        
    }
    
    private void LerEncaminharTXT()
    {  
        if (caminhoTXT != null) {            
             //JOptionPane.showMessageDialog(null, "Qde de linhas do arquivo...: "+String.valueOf(totalLinhas));

            //criando uma variavel arquivo do tipo File e setando o caminho do arquivo TXT nela
            File arquivo = new File(caminhoTXT);
            try {
                FileReader ler        = new FileReader(arquivo);
                BufferedReader lerBuf = new BufferedReader(ler);
                linha                 = lerBuf.readLine();
                
                //Ler o destino para msg de confirmação
                while (linha != null) 
                {
                    sDestino            = linha.split(";")[6];
                    break;
                }
                
                if(umMetodo.ConfirmouOperacao("Confirma o envio dos equipamentos para "+sDestino+"?", "Envio de Equipamentos para "+sDestino))
                {                                             
                        while (linha != null) 
                        {
                            sCodigo             = linha.split(";")[0]; 
                            sEstacao            = linha.split(";")[1];
                            sSecaoid            = linha.split(";")[2];
                            sClienteid          = linha.split(";")[3];
                            sMemorando          = linha.split(";")[4];
                            sOrigem             = linha.split(";")[5];
                            sDestino            = linha.split(";")[6];
                            sSerie              = linha.split(";")[7];
                            sStatus             = linha.split(";")[8];
                            sAssunto            = linha.split(";")[9];
                            sObsMemo            = linha.split(";")[10];
                            
                            atualizarStatusEquipamentos();                       
                            
                            //lendo a proxima linha
                            linha = lerBuf.readLine();
                        }
                }else{
                    //Excluir os ítens deste sMemorando pois nessa etapa já estarão excluídos
                    umMetodo.deletarItensDoMemorando(sMemorando);
                    umMetodo.deletarMemorando(sMemorando);
                    return;
                }
                                                   
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar ler o arquivo!");
            }
            if (contador > 0) {
                JOptionPane.showMessageDialog(null, "Todos os patrimônios foram encaminhados com sucesso para "+sDestino+"!", "Encaminhados com sucesso para "+sDestino+"!", 2);                
            } else if (contador == 0) {                
                JOptionPane.showMessageDialog(null, "ERRO  no  cadastro  de  alguns registros, possíveis  causas :  Problemas  na  leitura do arquivo TXT\nou  duplicidade  em   algum   número  de   série   inserido,  confira  os   dados  do  TXT  selecionado!", "ERRO no cadastro!", 2);                                
            }
            
            imprimirRelatorio();        
            
            //atualizando tabela de ÍTENS ( TBLITENSMEMOTRANSFERIDOS ) do memorando de PROSSESANDO para TRANSFERIDO            
            umMetodo.atualizarStatusParaTransferidos(sMemorando); 
            umMetodo.atualizarStatusDosMemosParaTransferidos(sMemorando); 
            
        }else{             
            btnNovo.setEnabled(true);
            btnSair.setEnabled(true); 
            gerouNumo = false;
        }   
        btnLerEnviarDados.setEnabled(true);  
    }  
    
 private boolean todosCamposPreenchidos(){
    if ( 
            (!txtDESTINO.getText().trim().isEmpty()) && 
            (!txtASSUNTO.getText().trim().isEmpty()) && 
            (!txtOBSERVACAO.getText().trim().isEmpty()) 
        )
    {
        btnGerarTXTDeEnvio.setEnabled(true);
        return true;
    }
    return false;
}
    
private void gravarItensNoBanco() 
{     
    dadosLidos = umMetodo.retornarTodosDadosInseridosNaListaDeStrings(lstListaStrings, false);    

    valorItem=0;

    for (String[] dados : dadosLidos) 
    {
        codPatr           = Integer.parseInt(dados[0]);
        String estacao    = dados[1];
        String secaoid    = dados[2];
        String clienteid  = dados[3];
        sMemorando        = dados[4];
        sOrigem           = dados[5];
        sDestino          = dados[6];
        sSerie            = dados[7];
        sstatusItem       = dados[8];
        sAssunto          = dados[9];
        sObsMemo          = dados[10];
        

        //GRAVA O REGISTRO SELECIONADO NA TABELA TBLITENSMEMOTRANSFERIDOS PARA POSTERIOR IMPRESSAO  
        sstatusItem            = "PROCESSANDO";        

        //SETANDO OS VALORES NO MODELO PARA GRAVAR  
        valorItem++;
        objModPatriTemTransferido.setItem(valorItem);
        objModPatriTemTransferido.setNumemo(sMemorando);

        //Preciso entrar com o codigo do modelo         
        getDados  = umDaoPatri.getChapaSerieModeloPeloCodigoDAO(codPatr);   
        codMOdelo = Integer.valueOf(getDados[2]);  

        sChapa = umMetodo.getStringPassandoCodigo("tblpatrimonios", "chapa", codPatr);

        objModPatriTemTransferido.setModeloid(codMOdelo);            
        objModPatriTemTransferido.setSerie(sSerie);
        objModPatriTemTransferido.setChapa(sChapa);      
        objModPatriTemTransferido.setOrigem(sOrigem);     
        objModPatriTemTransferido.setDestino(sDestino);     
        objModPatriTemTransferido.setStatus(sstatusItem);

        ctrlPatriTenstransferido.salvarPatriItensTransferido(objModPatriTemTransferido);     
    }

}    
    
    private void gerarMemorandoDeEnvioComLeituraDiretaDoTXT(){                  
        
            /*QDO O ENVIO DOS EQUIPAMENTOS SE DA APENAS COM A LEITURA DO ARQUIVO TXT JA CRIADO E NÃO PASSA PELA SUA CRIAÇÃO*/                
            dadosLidos = umMetodo.retornarTodosDadosInseridosNaListaDeStrings(lstListaStrings, false); 

            for (String[] dados : dadosLidos) {
                codPatr           = Integer.parseInt(dados[0]);
                String estacao    = dados[1];
                String secaoid    = dados[2];
                String clienteid  = dados[3];
                sMemorando        = dados[4];
                sOrigem           = dados[5];
                sDestino          = dados[6];
                sSerie            = dados[7];
                sstatusItem       = dados[8];
                sAssunto          = dados[9];
                sObsMemo          = dados[10];
            }       

            //GERANDO NUMERO DO MEMO COM O ANO VIGENTE
            numMemoTransferido     = sMemorando;            
            origemTransferidos     = sOrigem;
            destinoTransferidos    = sDestino;
            sMemoobservacao        = sObsMemo;
            
            if(sMemoobservacao.equals("n/c")){
                sMemoobservacao = " ";
            }
            
            /*salvando memorando em definitivo ( TBLMEMOSTRANSFERIDOS ) apos gerar o relatorio  
              Nao deixar salvar quando clicado mais de uma vez / So gravar a primeira vez que clicar*/            
            if(!umMetodo.temDuplicidadeDeCadastro("TBLMEMOSTRANSFERIDOS", "numemo", numMemoTransferido))
            {
                umModPatriTransferido.setNumemo(numMemoTransferido);
                
                if (sMemoobservacao != null && !sMemoobservacao.isEmpty())
                {
                    umModPatriTransferido.setObservacao("Obs : "+umMetodo.primeiraLetraMaiuscula(sMemoobservacao));
                }else{
                    //se já tiver Cadastro inicial
                    umModPatriTransferido.setObservacao(" ");
                }                
                    umModPatriTransferido.setAssunto(sAssunto);
                    umModPatriTransferido.setStatus("PROCESSANDO");
                    umModPatriTransferido.setIdusuario(codigoUsuario);
                    umCtrlPatriTranferido.salvarPatriTransferido(umModPatriTransferido); 
                    umGravarLog.gravarLog("cadastro do memo de transferencia de patrimonios "+numMemoTransferido);
               
            }
       
    }
    
    private void imprimirRelatorio(){
        
        if (umMetodo.ConfirmouOperacao("Confirma a impressão do Memorando "+sMemorando+"?", "Impressão do Relatório"))
        {
            GerarRelatorios objRel = new GerarRelatorios();
           
            try {              
                //Se for normal ou de baixa            
                //JOptionPane.showMessageDialog(rootPane, "DESTINO : " + destinoMemo);
                        
                if(!sDestino.equals("BAIXA"))
                {
                    objRel.imprimirPatrimoniosTransferidos("relatorio/relmemotransferidos.jasper", sMemorando); 
                }else if(sDestino.equals("BAIXA")){
                    F_ESCOLHAIMPRESSAOINSERVIVEIS frm = new F_ESCOLHAIMPRESSAOINSERVIVEIS();
                    frm.setVisible(true);   
                }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);                
                }         

                umGravarLog.gravarLog("Impressao do Memo de Transferencia "+sMemorando);     
        }          

    }
            
    private boolean memorandoDuplicadoNoBanco(){
        
        //Limpa a lista lstListaStrings
        lstListaStrings.clear();
                
        //Faz a leitura do arquivoTXT
        SelecionarArquivoTexto select = new SelecionarArquivoTexto();
        caminhoTXT = select.ImportarTXT();

        // Verifica se o usuário cancelou a seleção
        if (caminhoTXT == null || caminhoTXT.isEmpty()) {        
            //System.err.println("Nenhum arquivo selecionado.");
            return true;
        }
        
        totalLinhasTXT = umMetodo.contarLinhasDoArquivoTXT(caminhoTXT);                       

        //Envia os dados do arquivoTXT para uma lista denominada lstListaStrings
        LeitorArquivoTXTEnviarDadosParaUmaLista.LeitorArquivoTXTEnviarDadosParaUmaLista(caminhoTXT, lstListaStrings);
        
        dadosLidos = umMetodo.retornarTodosDadosInseridosNaListaDeStrings(lstListaStrings, false);    
           
        for (String[] dados : dadosLidos) 
        {
            codPatr           = Integer.parseInt(dados[0]);
            String estacao    = dados[1];
            String secaoid    = dados[2];
            String clienteid  = dados[3];
            sMemorando        = dados[4];
            sOrigem           = dados[5];
            sDestino          = dados[6];
            sSerie            = dados[7];
            sstatusItem       = dados[8];
            sAssunto          = dados[9];
            sObsMemo          = dados[10];
        }
        
//        for (String item : lstListaStrings) {
//            System.out.println(item);
//        }

        if(umMetodo.verificarSePesquisaRetornouDados("select numemo from TBLMEMOSTRANSFERIDOS where numemo = '"+sMemorando+"'"))
        { 
           return true;
        }else{
           return false; 
        }        
    }
    
    private void LerEnviarDados()
    {    
        gravarItensNoBanco();  
        
        gerarMemorandoDeEnvioComLeituraDiretaDoTXT();                
        
        LerEncaminharTXT();    
    }
    
    private void btnLerEnviarDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLerEnviarDadosActionPerformed
        //antes de ler e enviar dados verificar se o memorando já foi processado pra evitar duplicidade
        if(!memorandoDuplicadoNoBanco()){            
            LerEnviarDados();
        }else{
            JOptionPane.showMessageDialog(null, "Atenção o memorando "+sMemorando+" já foi processado, verifique e selecione um memorando válido!", "Duplicidade memorando processado..!",2);
            return;
        }                
    }//GEN-LAST:event_btnLerEnviarDadosActionPerformed

    
    private void txtORIGEMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtORIGEMMouseClicked
        txtORIGEM.selectAll();
    }//GEN-LAST:event_txtORIGEMMouseClicked

    private void txtORIGEMFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtORIGEMFocusGained
        txtORIGEM.selectAll();
    }//GEN-LAST:event_txtORIGEMFocusGained

    private void txtDESTINOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDESTINOKeyPressed
        //se teclar enter estando dentro do txtNOME vá para o campo do rf
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtASSUNTO.requestFocus();
        }   
    }//GEN-LAST:event_txtDESTINOKeyPressed

    private void txtDESTINOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDESTINOMouseClicked
        txtDESTINO.selectAll();
    }//GEN-LAST:event_txtDESTINOMouseClicked

    private void txtDESTINOFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDESTINOFocusGained
        txtDESTINO.selectAll();
        txtMENSAGEM.setText("Digite o destino dos equipamentos e tecle <Enter> todos os campos são obrigatórios...");
    }//GEN-LAST:event_txtDESTINOFocusGained

    private void txtOBSERVACAOFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOBSERVACAOFocusGained
       txtMENSAGEM.setText("Digite uma observação que constará no memorando...");
       txtOBSERVACAO.selectAll();
    }//GEN-LAST:event_txtOBSERVACAOFocusGained

    private void txtOBSERVACAOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOBSERVACAOKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtMENSAGEM.requestFocus();
        }         
    }//GEN-LAST:event_txtOBSERVACAOKeyPressed

    private void txtASSUNTOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtASSUNTOKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtOBSERVACAO.requestFocus();            
        }    
        txtOBSERVACAO.setText("n/c");
    }//GEN-LAST:event_txtASSUNTOKeyPressed

    private void txtDESTINOFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDESTINOFocusLost
        if(txtDESTINO.getText().equals("BAIXA")){
            txtASSUNTO.setText("BAIXA DE EQUIPAMENTOS INSERVIVEIS");
            txtOBSERVACAO.setText("Todos os equipamentos foram dados como inserviveis.");
        }
    }//GEN-LAST:event_txtDESTINOFocusLost

    private void txtASSUNTOFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtASSUNTOFocusLost
        
       btnLerTXTAddItensNaLista.setEnabled(true);     

    }//GEN-LAST:event_txtASSUNTOFocusLost

    private void txtASSUNTOFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtASSUNTOFocusGained
        txtMENSAGEM.setText("Digite o assunto a que se refere o envio e tecle <Enter> todos os campos são obrigatórios...");
        //txtOBSERVACAO.setText("n/c");
    }//GEN-LAST:event_txtASSUNTOFocusGained

    private void txtASSUNTOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtASSUNTOMouseClicked
        txtASSUNTO.selectAll();
    }//GEN-LAST:event_txtASSUNTOMouseClicked

    private void txtOBSERVACAOFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOBSERVACAOFocusLost
        
        btnLerTXTAddItensNaLista.setEnabled(true);
        
    }//GEN-LAST:event_txtOBSERVACAOFocusLost

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
            java.util.logging.Logger.getLogger(F_GERARTXTENVIOAUTO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_GERARTXTENVIOAUTO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_GERARTXTENVIOAUTO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_GERARTXTENVIOAUTO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                F_GERARTXTENVIOAUTO dialog = new F_GERARTXTENVIOAUTO(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGerarTXTDeEnvio;
    private javax.swing.JButton btnLerEnviarDados;
    private javax.swing.JButton btnLerTXTAddItensNaLista;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSair;
    private javax.swing.JLayeredPane jBoxCabecalho;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPANELTOTAL;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstITENS;
    private javax.swing.JTextField txtASSUNTO;
    private javax.swing.JTextField txtDESTINO;
    private javax.swing.JTextField txtMEMORANDO;
    private javax.swing.JTextField txtMENSAGEM;
    private javax.swing.JTextField txtOBSERVACAO;
    private javax.swing.JTextField txtORIGEM;
    // End of variables declaration//GEN-END:variables
}
