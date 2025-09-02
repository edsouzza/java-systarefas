package visao;

import conexao.ConnConexao;
import biblioteca.Biblioteca;
import biblioteca.MetodosPublicos;
import biblioteca.GerarNumerosAleatorios;
import biblioteca.ModeloTabela;
import static biblioteca.VariaveisPublicas.TipoModelo;
import controle.CtrlPatriDepto;
import static biblioteca.VariaveisPublicas.totalRegs;
import static biblioteca.VariaveisPublicas.codigoRegSelecionado;
import static biblioteca.VariaveisPublicas.indiceItemSelecionado;
import static biblioteca.VariaveisPublicas.controlenaveg;
import static biblioteca.VariaveisPublicas.contador;
import static biblioteca.VariaveisPublicas.dataDoDia;
import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.tabela;
import static biblioteca.VariaveisPublicas.tabela_da_lista;
import static biblioteca.VariaveisPublicas.cadPatriDeptos;
import static biblioteca.VariaveisPublicas.codigoTipoModelo;
import controle.ControleGravarLog;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.PatriDepto;
import relatorios.GerarRelatorios;


public class F_PATRIDEPTOS extends javax.swing.JFrame {
   
      
    ConnConexao         conexao                  = new ConnConexao();    
    Biblioteca          umaBiblio                = new Biblioteca();
    MetodosPublicos     umMetodo                 = new MetodosPublicos();
    
    //modelos
    PatriDepto         umModPatriDeptos         = new PatriDepto();
    
    //controles
    CtrlPatriDepto     ctrDepartamento          = new CtrlPatriDepto();    
    ControleGravarLog   umGravarLog              = new ControleGravarLog();
    
    //DAO
    //DAOPatriDeptos       umPatrimonioDAO         = new DAOPatriDeptos();   
    Date                dataDia                    = dataDoDia; 
    SimpleDateFormat    sdf                        = new SimpleDateFormat("dd.MM.yyyy");   
       
    String tipo,serie,chapa,origem,destino,dtentrada,dtenvio,dtdevolucao,status,obs,sOrigemSelecionada,sStatusSelecionado,dataDevolucao,
           sqlPorOrigem,dataEnvio,dataDev,memoDev,memoEnv,statusAtual,modelo  = null; 
    int controle, codigo, tipoid, modeloid = 0;
    boolean editando, cadastrando, clicouEnviar, clicouEncerrados, filtrou, enviado, alterouCampo, clicouEnviados, clicouDevolver, clicouInativos;
    
    String sqlEnviar            = "SELECT m.modelo, p.* FROM tblmodelos m, tblpatrideptos p WHERE p.modeloid = m.codigo AND (p.status = 'ENVIAR') ORDER BY p.codigo";
    String sqlEnviados          = "SELECT m.modelo, p.* FROM tblmodelos m, tblpatrideptos p WHERE p.modeloid = m.codigo AND (p.status = 'ENVIADO') ORDER BY p.codigo";
    String sqlDevolver          = "SELECT m.modelo, p.* FROM tblmodelos m, tblpatrideptos p WHERE p.modeloid = m.codigo AND (p.status = 'DEVOLVER') ORDER BY p.codigo";    
    String sqlEncerrados        = "SELECT m.modelo, p.* FROM tblmodelos m, tblpatrideptos p WHERE p.modeloid = m.codigo AND (p.status = 'ENCERRADO') ORDER BY p.codigo";    
    String sqlDefault           = "select * from tblPatriDeptos";    
    String sqlVazia             = "select * from tblPatriDeptos where codigo = 0";
    
    
    
    public F_PATRIDEPTOS() {
        initComponents();  
        setResizable(false);   //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        Leitura();
        
        //configuracoes dos edits           
        umaBiblio.configurarCamposTextos(txtCODIGO);
        umaBiblio.configurarCamposTextos(txtTIPO);
        umaBiblio.configurarCamposTextos(txtMODELO);
        umaBiblio.configurarCamposTextos(txtSERIE);
        umaBiblio.configurarCamposTextos(txtCHAPA);
        umaBiblio.configurarCamposTextos(txtORIGEM);
        umaBiblio.configurarCamposTextos(txtDESTINO);
        umaBiblio.configurarCamposTextos(txtPESQUISA);
        txtCODIGO.setForeground(Color.red);        
        txtOBS.setForeground(Color.blue);        
        txtOBS.setFont(new Font("TimesRoman", Font.BOLD, 12));
        cmbSTATUS.setFont(new Font("TimesRoman", Font.BOLD, 14));
        cmbSTATUS.setForeground(Color.BLUE);
        
        //Impede que formulario seja arrastado na tela
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                setEnabled(false);
                setEnabled(true);
            }
        });//fim addComponentListener       
                
      //configuração dos botões
      umaBiblio.configurarBotoes(btnNovo);
      umaBiblio.configurarBotoes(btnEditar);
      umaBiblio.configurarBotoes(btnGravar);
      umaBiblio.configurarBotoes(btnImprimir);
      umaBiblio.configurarBotoes(btnCancelar);
      umaBiblio.configurarBotoes(btnSair); 
      JTableEnviar.setFont(new Font("TimesRoman", Font.BOLD, 12));
      JTableEnviar.setForeground(Color.blue);
      JTableEnviados.setFont(new Font("TimesRoman", Font.BOLD, 12));
      JTableEnviados.setForeground(Color.blue);            
      JTableDevolver.setFont(new Font("TimesRoman", Font.BOLD, 12));
      JTableDevolver.setForeground(Color.blue);            
      JTableEncerrados.setFont(new Font("TimesRoman", Font.BOLD, 12));
      JTableEncerrados.setForeground(Color.red);            
      JTablePorOrigem.setFont(new Font("TimesRoman", Font.BOLD, 12));
      JTablePorOrigem.setForeground(Color.blue);            
      
      
      cmbFILTRARPORORIGEM.setFont(new Font("TimesRoman", Font.BOLD, 12));
      cmbFILTRARPORORIGEM.setForeground(Color.blue);       
      
    
        //Impede que formulario seja arrastado na tela
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                setEnabled(false);
                setEnabled(true);
            }
        });//fim addComponentListener      
        
        if (umaBiblio.tabelaVazia(tabela)) {
            btnImprimir.setEnabled(false);  
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        JTableStatus = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JTableEnviar = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        JTableEnviados = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        JTableDevolver = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        JTableEncerrados = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        JTablePorOrigem = new javax.swing.JTable();
        btnNovo = new javax.swing.JButton();
        btnGravar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtSERIE = new javax.swing.JTextField();
        cmbSTATUS = new javax.swing.JComboBox<String>();
        jLabel3 = new javax.swing.JLabel();
        txtCODIGO = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtCHAPA = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtORIGEM = new javax.swing.JTextField();
        btnEncaminhar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtOBS = new javax.swing.JTextArea();
        txtTIPO = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDESTINO = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtPESQUISA = new javax.swing.JTextField();
        cmbFILTRARPORORIGEM = new javax.swing.JComboBox<String>();
        jLabel14 = new javax.swing.JLabel();
        txtMODELO = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelPrincipal.setPreferredSize(new java.awt.Dimension(1024, 733));

        JTableStatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JTableStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTableStatusMouseClicked(evt);
            }
        });

        JTableEnviar.setGridColor(new java.awt.Color(255, 255, 255));
        JTableEnviar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTableEnviarMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(JTableEnviar);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 915, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(128, Short.MAX_VALUE))
        );

        JTableStatus.addTab("ENVIAR PARA MANUTENCAO", jPanel5);

        JTableEnviados.setGridColor(new java.awt.Color(255, 255, 255));
        JTableEnviados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTableEnviadosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(JTableEnviados);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 935, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 126, Short.MAX_VALUE))
        );

        JTableStatus.addTab("ENVIADOS A MANUTENCAO", jPanel2);

        JTableDevolver.setGridColor(new java.awt.Color(255, 255, 255));
        JTableDevolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTableDevolverMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(JTableDevolver);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 935, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 126, Short.MAX_VALUE))
        );

        JTableStatus.addTab("DEVOLVER A UNIDADE DE ORIGEM", jPanel3);

        JTableEncerrados.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane6.setViewportView(JTableEncerrados);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 935, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 126, Short.MAX_VALUE))
        );

        JTableStatus.addTab("ENCERRADOS", jPanel4);

        JTablePorOrigem.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane7.setViewportView(JTablePorOrigem);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 935, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 126, Short.MAX_VALUE))
        );

        JTableStatus.addTab("FILTRAR POR ORIGEM", jPanel6);

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

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_inicio.gif"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setPreferredSize(new java.awt.Dimension(77, 25));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
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

        jLabel6.setText("EQUIPAMENTO");

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
        txtSERIE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSERIEMouseClicked(evt);
            }
        });
        txtSERIE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSERIEKeyPressed(evt);
            }
        });

        cmbSTATUS.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cmbSTATUS.setForeground(new java.awt.Color(51, 51, 255));
        cmbSTATUS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbSTATUS.setEnabled(false);
        cmbSTATUS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbSTATUSMouseClicked(evt);
            }
        });
        cmbSTATUS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSTATUSActionPerformed(evt);
            }
        });

        jLabel3.setText("CÓDIGO");

        txtCODIGO.setForeground(new java.awt.Color(51, 51, 255));

        jLabel1.setText("CHAPA");

        txtCHAPA.setEditable(false);
        txtCHAPA.setForeground(new java.awt.Color(51, 51, 255));
        txtCHAPA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCHAPAFocusLost(evt);
            }
        });
        txtCHAPA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCHAPAMouseClicked(evt);
            }
        });
        txtCHAPA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCHAPAKeyPressed(evt);
            }
        });

        jLabel4.setText("SERIE");

        jLabel7.setText("ORIGEM");

        txtORIGEM.setEditable(false);
        txtORIGEM.setForeground(new java.awt.Color(51, 51, 255));
        txtORIGEM.setToolTipText("");
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
        txtORIGEM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtORIGEMKeyPressed(evt);
            }
        });

        btnEncaminhar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_pesquisa.gif"))); // NOI18N
        btnEncaminhar.setText("Encaminhar");
        btnEncaminhar.setToolTipText("Perquisar por chapa, serie, estação, ip ou nome do colaborador");
        btnEncaminhar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEncaminhar.setPreferredSize(new java.awt.Dimension(77, 25));
        btnEncaminhar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEncaminharActionPerformed(evt);
            }
        });

        txtOBS.setEditable(false);
        txtOBS.setColumns(20);
        txtOBS.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        txtOBS.setRows(5);
        txtOBS.setToolTipText("Histórico e observações");
        txtOBS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtOBSFocusGained(evt);
            }
        });
        txtOBS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtOBSMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(txtOBS);

        txtTIPO.setEditable(false);
        txtTIPO.setForeground(new java.awt.Color(51, 51, 255));

        jLabel8.setText("STATUS");

        txtDESTINO.setEditable(false);
        txtDESTINO.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDESTINO.setForeground(new java.awt.Color(51, 51, 255));
        txtDESTINO.setToolTipText("");
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

        jLabel10.setText("DESTINO");

        jLabel12.setText("PESQUISAR POR DIGITAÇÃO");
        jLabel12.setAlignmentX(0.5F);

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

        cmbFILTRARPORORIGEM.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        cmbFILTRARPORORIGEM.setForeground(new java.awt.Color(51, 51, 255));
        cmbFILTRARPORORIGEM.setToolTipText("Escolha um setor de origem para filtrar");
        cmbFILTRARPORORIGEM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbFILTRARPORORIGEM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbFILTRARPORORIGEMMouseClicked(evt);
            }
        });
        cmbFILTRARPORORIGEM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFILTRARPORORIGEMActionPerformed(evt);
            }
        });

        jLabel14.setText("ORIGENS");
        jLabel14.setAlignmentX(0.5F);

        txtMODELO.setEditable(false);
        txtMODELO.setForeground(new java.awt.Color(51, 51, 255));
        txtMODELO.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMODELOFocusGained(evt);
            }
        });
        txtMODELO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtMODELOKeyPressed(evt);
            }
        });

        jLabel2.setText("MODELO");

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(JTableStatus)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtTIPO, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMODELO)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSERIE, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(10, 10, 10)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtCHAPA, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(btnNovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEncaminhar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtPESQUISA))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbFILTRARPORORIGEM, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtORIGEM, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDESTINO, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(229, 229, 229))
                            .addComponent(cmbSTATUS, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTIPO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMODELO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSERIE, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCHAPA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbSTATUS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtORIGEM, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDESTINO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEncaminhar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cmbFILTRARPORORIGEM, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(txtPESQUISA))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JTableStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 860));

        setSize(new java.awt.Dimension(977, 876));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
      
    private void popularComboFiltroPorOrigem(){                
        //Popula a combo com todas as origens cadastradas
        umMetodo.PreencherComboComStatusVariados(cmbFILTRARPORORIGEM, "TBLPATRIDEPTOS", "ORIGEM");
        cmbFILTRARPORORIGEM.setSelectedIndex(-1);            
    }
    
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
        sql = "select count(codigo) as total from "+tabela+" where status = 'ENVIAR'";
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
        sql = "select count(codigo) as total from "+tabela+" where status = 'ENCERRADO'";
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
        int qdePatrimonios  =  qdePatrimoniosCadastrados("tblPatrideptos");
        int qdeAtivos       =  qdePatrimoniosATIVOS("tblPatrideptos");
        int qdeInativos     =  qdePatrimoniosINATIVOS("tblPatrideptos");
       
        if(qdePatrimonios == 1){
           return "Equipamentos : "+qdePatrimonios+" patrimônio cadastrado"; 
        }else{
           return "Equipamentos : "+qdePatrimonios+" patrimônios cadastrados -> "+qdeAtivos+" a ENVIAR e "+qdeInativos+" ENCERRADOS";    
        } 
        
    }           
       
    private void gravarInclusaoRegistro()
    {        
        SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");   
        Date dataAtual = new Date();
        
        if (txtSERIE.getText().length() > 0 || txtORIGEM.getText().length() > 0 || txtOBS.getText().length() > 0) 
        {
            //setando os valores dos edits   
            tipo            = txtTIPO.getText(); 
            tipoid          = umMetodo.getCodigoPassandoString("tbltipos", "tipo", tipo);
            modelo          = txtMODELO.getText();              
            serie           = txtSERIE.getText();              
            chapa           = txtCHAPA.getText();              
            origem          = txtORIGEM.getText();   
            status          = cmbSTATUS.getSelectedItem().toString();
            obs             = txtOBS.getText();
            
            umModPatriDeptos.setTipoid(tipoid);
            umModPatriDeptos.setModeloid(codigoTipoModelo);
            umModPatriDeptos.setSerie(serie);
            umModPatriDeptos.setChapa(chapa);
            umModPatriDeptos.setOrigem(origem);
            umModPatriDeptos.setDtentrada(sd.format(dataAtual)); 
            umModPatriDeptos.setDtenvio(sd.format(dataAtual)); 
            umModPatriDeptos.setStatus(status); 
            umModPatriDeptos.setObs(obs); 
            
            ctrDepartamento.salvarPatrimonio(umModPatriDeptos);
            umGravarLog.gravarLog("cadastro de "+tipo);
            
        } else {
            JOptionPane.showMessageDialog(null, "Os campos [Série,Origem e Observações] são de preenchimento obrigatórios!", "Campos obrigatórios vazios!", 2);
            txtSERIE.requestFocus(); //foco no campo secao
        } 
        Leitura();
        cadastrando = false;
                 
    }
    
    private void gravarEdicaoRegistro(){
    
        //tratando os codigos da seção e do cliente(usuario) se fizer alteração no cliente
        codigo              = Integer.valueOf(txtCODIGO.getText());    
        
        SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");   
        Date dataAtual      = new Date();
        dataEnvio           = sd.format(dataAtual);
        dataDev             = sd.format(dataAtual);        
        memoDev             = "N";
        
        /*Criando as variaveis dataEnvio e dataDevolucao para serem convertidas em sql.Date já que o JDateChooser tem uma data sql.util*/  
        tipo                = txtTIPO.getText(); 
        tipoid              = umMetodo.getCodigoPassandoString("tbltipos", "tipo", tipo);
        modeloid            = umMetodo.getCodigoPassandoString("tblmodelos", "modelo", txtMODELO.getText());
        modelo              = txtMODELO.getText(); 
        serie               = txtSERIE.getText();              
        chapa               = txtCHAPA.getText();              
        origem              = txtORIGEM.getText();   
        destino             = txtDESTINO.getText();    
        obs                 = txtOBS.getText();                   

        umModPatriDeptos.setCodigo(codigo);
        umModPatriDeptos.setTipoid(tipoid);
        umModPatriDeptos.setModeloid(modeloid);
        umModPatriDeptos.setSerie(serie);
        umModPatriDeptos.setChapa(chapa);
        umModPatriDeptos.setOrigem(origem);    
        umModPatriDeptos.setDestino(destino);       
        umModPatriDeptos.setDtenvio(dataEnvio);   
        umModPatriDeptos.setMemoenvio(memoEnv);
        umModPatriDeptos.setMemodevolucao(memoDev);  
        
        if(cmbSTATUS.getSelectedItem().toString().equals("ENCERRADO")){
            umModPatriDeptos.setDtdevolucao(dataDev);  
        }
                
        if(cmbSTATUS.getSelectedIndex() == -1){
            status = statusAtual; 
        }else{
            status = cmbSTATUS.getSelectedItem().toString();
        }        
                
        umModPatriDeptos.setStatus(status); 
        
        umModPatriDeptos.setObs(obs);          

        //verificando valores passados para atualização
        //System.out.println("\n codigo :"+codigo+"\n tipoid : "+tipoid+"\n serie : "+serie+"\n chapa : "+chapa+"\n origem : "+origem+"\n destino : "+origem+"\n data ENVIO : "+dataEnvio+"\n data devol : "+dataDev+"\n memodevolucao : "+memoDev+"\n status : "+status+"\n obs : "+obs);
              
        //verificando se a data de envio esta vazia caso nao digite coloca a data do dia automaticamente  
        ctrDepartamento.atualizarPatrimonio(umModPatriDeptos);
        umGravarLog.gravarLog("Atualização de "+tipo);   

        Leitura();
        
        editando = false;      
         
 }
 
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void ImprimindoRelatorios(){
                
        if(indiceItemSelecionado == 2 && !clicouInativos){
            if(umMetodo.temEncerrados()){
                GerarRelatorios objRel = new GerarRelatorios();
                try {
                    objRel.imprimirPatrimoniosDepartamentos("relatorio/relPatriDeptosTodosEncerrados.jasper");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);                
                }     
            }else{
                JOptionPane.showMessageDialog(null, "Ainda não temos patrimônios com processos encerrados no momento!", "Sem patrimônios encerrados!", 2);
            }
        }if(indiceItemSelecionado == 0 && !clicouInativos){
            if(umMetodo.temPraEnviar()){
                GerarRelatorios objRel = new GerarRelatorios();
                try {
                    objRel.imprimirPatrimoniosDepartamentos("relatorio/relPatriDeptosTodosEnviar.jasper");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);                
                }   
            }else{
                JOptionPane.showMessageDialog(null, "Ainda não temos patrimônios para enviar cadastrados no momento!", "Sem patrimônios para enviar!", 2);
            }
        }if(indiceItemSelecionado == 1 && !clicouInativos){
           if(umMetodo.temEnviados()){
                GerarRelatorios objRel = new GerarRelatorios();
                try {
                    objRel.imprimirPatrimoniosDepartamentos("relatorio/relPatriDeptosTodosEnviados.jasper");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);                
                }   
           }else{
               JOptionPane.showMessageDialog(null, "Ainda não temos patrimônios enviados no momento!", "Sem patrimônios enviados!", 2);
           }
        }if(indiceItemSelecionado == 3 && !clicouInativos){
            
            GerarRelatorios objRel = new GerarRelatorios();
            try {
                objRel.imprimirPatrimoniosDepartamentos("relatorio/relPatriDeptosTodosCadastrados.jasper");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);                
            }        
        } 
                
        btnCancelar.setEnabled(true);       
        btnImprimir.setEnabled(false); 
        btnEncaminhar.setEnabled(false); 
        btnNovo.setEnabled(false); 
        btnSair.setEnabled(false); 
        txtPESQUISA.setEnabled(false);
        PreencherTabelaEnviar(sqlVazia);
    }
    
    private void AbrirListaDeOpcoesDeRelatorios(){
        
       F_LISTARELATORIOS frm = new F_LISTARELATORIOS(this,true);
       frm.setVisible(true);  
       
       ImprimindoRelatorios();
       
    }
    
    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed

        if(filtrou && !clicouInativos){    
                                      
            GerarRelatorios objRel = new GerarRelatorios();
            try {
                objRel.imprimirPatrimoniosDepartamentosPorOrigem("relatorio/relPatriDeptosTodosPorOrigem.jasper",sOrigemSelecionada);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);                
            }       
                          
        }else{        
            AbrirListaDeOpcoesDeRelatorios();    
        }
        cmbFILTRARPORORIGEM.setEnabled(false);
        cmbFILTRARPORORIGEM.setSelectedIndex(-1);
        btnImprimir.setEnabled(false);
        btnCancelar.setText("Voltar");
        PreencherTabelaEnviar(sqlVazia);
        filtrou=false;
        clicouInativos=false;
        JTableStatus.setEnabled(false);
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        
        if (!umaBiblio.tabelaVazia(tabela)) {
            Leitura();
            btnImprimir.setEnabled(true);
                     
        }else{
            btnImprimir.setEnabled(false);           
        }
          
        txtSERIE.setText(null);
        txtSERIE.setEditable(false);
        txtOBS.setEditable(false);        
        txtMODELO.setEditable(false);
        txtMODELO.setEnabled(true);
        txtCHAPA.setEditable(false);
        txtORIGEM.setEditable(false);
        txtORIGEM.setEnabled(true);
        txtDESTINO.setEditable(false);
        txtDESTINO.setEnabled(true);
        txtCODIGO.setEditable(false);
        btnEditar.setEnabled(false);
        btnSair.setEnabled(true);
        btnCancelar.setEnabled(false);
        btnCancelar.setText("Cancelar");
        btnGravar.setEnabled(false);
        btnNovo.setEnabled(true);
        controle         = 0;   
        cadastrando      = false;
        editando         = false;
        clicouEnviar     = false;
        clicouDevolver   = false;
        clicouEncerrados = false;
        clicouEnviados   = false;
        filtrou          = false;
        clicouInativos   = false;
        umaBiblio.limparTodosCampos(rootPane);
        cmbSTATUS.setEnabled(false);
        cmbSTATUS.setSelectedIndex(-1);
        JTableStatus.setSelectedIndex(0);
        JTableStatus.setEnabled(true);
        mostrarCursorDefault();
                 
    }//GEN-LAST:event_btnCancelarActionPerformed
           
    private void bloquearFocus(){
        txtCODIGO.setFocusable(false);
        txtTIPO.setFocusable(false);
        txtMODELO.setFocusable(false);
        txtSERIE.setFocusable(false);
        txtCHAPA.setFocusable(false);
        txtORIGEM.setFocusable(false);
        txtDESTINO.setFocusable(false);
        txtOBS.setFocusable(false);
        btnNovo.setFocusable(false);
        btnSair.setFocusable(false);
    }
    
    private void desbloquearFocus(){
        txtCODIGO.setFocusable(true);
        txtTIPO.setFocusable(true);
        txtMODELO.setFocusable(true);
        txtSERIE.setFocusable(true);
        txtCHAPA.setFocusable(true);
        txtORIGEM.setFocusable(true);
        txtDESTINO.setFocusable(true);
        txtOBS.setFocusable(true);
        btnNovo.setFocusable(true);
        btnSair.setFocusable(true);
    }
          
    private void Leitura()
    {        
        if (umaBiblio.tabelaVazia(tabela)) {
            btnImprimir.setEnabled(false); 
            cmbFILTRARPORORIGEM.setEnabled(false);
        } else {
            btnImprimir.setEnabled(true);
            txtPESQUISA.setEnabled(true);
            cmbFILTRARPORORIGEM.setEnabled(true);
            popularComboFiltroPorOrigem();
            PreencherTabelaEnviar(sqlEnviar);
            PreencherTabelaEnviados(sqlEnviados);
            PreencherTabelaDevolver(sqlDevolver);
            PreencherTabelaEncerrados(sqlEncerrados);
        }          
        
        umaBiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS        
        btnNovo.setEnabled(true);
        btnGravar.setEnabled(false);
        btnCancelar.setEnabled(false);        
        btnEditar.setEnabled(false);
        btnSair.setEnabled(true);
        
        txtCODIGO.setEditable(false);
        txtCODIGO.setEnabled(true);
        
        txtTIPO.setEnabled(true);
        txtTIPO.setEditable(false);          
        
        txtMODELO.setEditable(false);
        txtMODELO.setEnabled(true);
        
        txtSERIE.setEditable(false);
        txtSERIE.setEnabled(true);
        
        txtCHAPA.setEditable(false);
        txtCHAPA.setEnabled(true);
        
        txtORIGEM.setEditable(false);
        txtORIGEM.setEnabled(true);
        
        txtDESTINO.setEditable(false);
        txtDESTINO.setEnabled(true);
        
        txtOBS.setEditable(false);       
        txtOBS.setEnabled(true);                    
        
        //formatacao inicial dos botoes ao abrir o formulario
        boolean Habilitar = true;
        Component[]c      = null;
        cadastrando       = false;       
                
        btnNovo          .setEnabled(Habilitar);
        btnSair          .setEnabled(Habilitar);
        btnGravar        .setEnabled(!Habilitar);
        btnEditar        .setEnabled(!Habilitar);
        btnImprimir      .setEnabled(Habilitar);      
        btnCancelar      .setEnabled(!Habilitar);               
        btnEncaminhar    .setEnabled(!Habilitar);               
                                      
        //mostrando o titulo com qde de registros cadastrados
        this.setTitle(umaBiblio.mostrarTituloDoFormulario());
        bloquearFocus();
        
        txtPESQUISA.requestFocus();
        mostrarCursorDefault();

    }
      
    private void retornaStatusEnviado(){
        //se estiver com valor,retornar true se estiver vazio, retornar falso
        if(txtDESTINO.getText().equals("")){
            enviado = false;
        }else{
            enviado = true;
        }
    }
    
    private void mostrarCursorEdicao(){
        txtSERIE.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        txtMODELO.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        txtCHAPA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        txtORIGEM.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        txtDESTINO.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        txtOBS.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    
    private void mostrarCursorDefault(){
        txtSERIE.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        txtMODELO.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        txtCHAPA.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        txtORIGEM.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        txtDESTINO.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        txtOBS.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }     
    
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
       if(umaBiblio.permissaoLiberada()){
           
        retornaStatusEnviado();
        desbloquearFocus();
        editando = true;
        
        txtSERIE       .setEditable(true);
        txtMODELO      .setEditable(true);
        txtCHAPA       .setEditable(true);
        txtORIGEM      .setEditable(true);
        txtDESTINO     .setEditable(true);
        txtOBS         .setEditable(true);
        
        cmbFILTRARPORORIGEM.setEnabled(false);  
        txtPESQUISA.setEnabled(false);
        btnEditar.setEnabled(false); 
        btnCancelar.setText("Cancelar");    
        btnGravar.setEnabled(true);
        
        PreencherTabelaEnviar(sqlVazia);
        
        //Se destino estiver preenchido <ENVIADO>
        if(txtDESTINO.getText().length() > 0) {
            txtDESTINO.setText(txtORIGEM.getText());
            cmbSTATUS.setEnabled(true);
            cmbSTATUS.removeAllItems();
            cmbSTATUS.addItem("ENCERRADO");
            cmbSTATUS.setSelectedIndex(-1);
        }
        txtORIGEM.setEditable(false);
        txtMODELO.selectAll();
        txtMODELO.requestFocus();              
        mostrarCursorEdicao();
        controle = 0;
          
      }       
                  
    }//GEN-LAST:event_btnEditarActionPerformed
           
    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
                   
        if (cadastrando){
            gravarInclusaoRegistro();
        }else if(editando){             
            gravarEdicaoRegistro();       
        }
  
        btnEditar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnNovo.setEnabled(true);
        btnEncaminhar.setEnabled(true);
        btnImprimir.setEnabled(true);
        btnSair.setEnabled(true);
        btnGravar.setEnabled(false);
        txtMODELO.setEditable(false);        
        txtCHAPA.setEditable(false);        
        txtOBS.setEditable(false);
        txtORIGEM.setEnabled(true);
        txtORIGEM.setEditable(false);
        txtSERIE.setEditable(false);   
        txtTIPO.setEnabled(true);
        txtTIPO.setEditable(false);
        txtPESQUISA.requestFocus();
        umaBiblio.limparTodosCampos(this);
        contador       = 0;
        cadastrando    = false;     
        editando       = false;  
        filtrou        = false;
        cadPatriDeptos = false;
        tipo           = "";
        serie          = "";      
        cmbSTATUS.setEnabled(false);
        cmbSTATUS.setSelectedIndex(-1);
        mostrarCursorDefault();        
        
    }//GEN-LAST:event_btnGravarActionPerformed

    public void HabilitarDesabilitarBotoes(boolean Habilitar)
    {
        //ações para quando clicar em cada botão
        Component[]c = null;
        
        btnNovo          .setEnabled(Habilitar);
        btnGravar        .setEnabled(!Habilitar);
        btnEditar        .setEnabled(Habilitar);
        btnCancelar      .setEnabled(Habilitar);
        btnEncaminhar    .setEnabled(!Habilitar);
        btnImprimir      .setEnabled(Habilitar);
        btnSair          .setEnabled(Habilitar);               
               
    }
    
    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
     if(umaBiblio.permissaoLiberada()){
            
            //limpando os campos        
            txtCODIGO.setEditable(false);
            txtCODIGO.setEnabled(true);
            txtCODIGO.setText(String.valueOf(umaBiblio.mostrarProximoCodigo(tabela)));   
            
            F_LISTATIPOS frmTipos = new F_LISTATIPOS(this, true);
            frmTipos.setVisible(true);            
            
            txtTIPO.setEditable(false);
            txtTIPO.setEnabled(true);

            //se este tipo tiver clientes virturais dos setores quero abrir a lista com eles e não a lista com os servidores
            tipo = frmTipos.getItemSelecionado();                     
            txtTIPO.setText(tipo); 
            desbloquearFocus();            
                    
            HabilitarDesabilitarBotoes(false);            

            txtMODELO.setText(null);
            txtMODELO.setEditable(false);
            txtMODELO.setEnabled(true);
            
            txtSERIE.setText(null);
            txtSERIE.setEditable(true);
            txtSERIE.setEnabled(true);
            
            txtCHAPA.setText(null);
            txtCHAPA.setEditable(true);
            txtCHAPA.setEnabled(true);

            txtORIGEM.setText(null);
            txtORIGEM.setEditable(true);
            txtORIGEM.setEnabled(true);
            
            txtOBS.setText(null);
            txtOBS.setEditable(true);
            txtOBS.setEnabled(true);
            
            btnNovo.setEnabled(false);
            btnCancelar.setEnabled(true);
            btnSair.setEnabled(false);
            btnGravar.setEnabled(false);
            btnEncaminhar.setEnabled(false);
                        
            cmbSTATUS.removeAllItems();
            cmbSTATUS.addItem("ENVIAR");
            cmbSTATUS.setEnabled(true);
            
            PreencherTabelaEnviar(sqlVazia);            
            cadPatriDeptos = true;
            cadastrando    = true;   
            
            
            if(cadastrando){
                tabela_da_lista = "TBLMODELOS";        
                F_LISTAPADRAO frm = new F_LISTAPADRAO(this,true);
                frm.setVisible(true); 

                txtMODELO.setText(TipoModelo);
                modeloid = codigoTipoModelo;
                
            }     
            txtSERIE.requestFocus();
            
        }

    }//GEN-LAST:event_btnNovoActionPerformed
                
    private void selecionarPrimeiroRegistro(){
        //seleciona o primeiro registro da tabeala com status ENVIAR
        conexao.conectar();
        String sql = "select * from tblpatrideptos where status='ENVIAR'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {   //selecionando a primeira linha somente se tiver registros
                JTableEnviar.addRowSelectionInterval(0, 0);                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela com registro ATIVOS\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }        
    }                       
                         
    private void JTableStatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTableStatusMouseClicked

        if (JTableStatus.getSelectedIndex() == 0) { 
            txtPESQUISA.setEnabled(true);
            btnEncaminhar.setEnabled(false);
            clicouEnviar = true;    
                        
        }else if (JTableStatus.getSelectedIndex() == 1) { 
           txtPESQUISA.setEnabled(true);
           cmbFILTRARPORORIGEM.setSelectedIndex(-1);
           btnEncaminhar.setEnabled(true);
           btnCancelar.setEnabled(true);
           clicouEnviados = true;  
                      
        }else if (JTableStatus.getSelectedIndex() == 2) { 
           txtPESQUISA.setEnabled(true);
           cmbFILTRARPORORIGEM.setSelectedIndex(-1);
           btnEncaminhar.setEnabled(true);
           btnCancelar.setEnabled(true);
           clicouDevolver = true;    
                      
        }else if (JTableStatus.getSelectedIndex() == 3) {    
           txtPESQUISA.setEnabled(true);
           cmbFILTRARPORORIGEM.setSelectedIndex(-1);
           btnEncaminhar.setEnabled(true);
           btnCancelar.setEnabled(true);
           clicouEncerrados = true;                           
        }else if (JTableStatus.getSelectedIndex() == 4) {    
           txtPESQUISA.setEnabled(true);
           cmbFILTRARPORORIGEM.setSelectedIndex(-1);
           btnEncaminhar.setEnabled(true);
           btnCancelar.setEnabled(true);
           clicouEncerrados = true;   
           PreencherTabelaPorOrigem(sqlVazia);
        }
        
    }//GEN-LAST:event_JTableStatusMouseClicked
        
    private void btnEncaminharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEncaminharActionPerformed
        if(umMetodo.temUnidadesParaEnvio() || umMetodo.temUnidadesParaDevolucao()){
            tabela = "TBLITENSMEMOTRANSFERIDOS";   
            F_MEMOITENSTRANSFERIDOS frm = new F_MEMOITENSTRANSFERIDOS();
            controlenaveg = 1;
            frm.setVisible(true); 
            dispose();
        }else{
            JOptionPane.showMessageDialog(null, "Ops, não temos patrimônios disponíveis para emissão de memorando no momento!", "Sem patrimônios para encaminhamento!", 2);
        }
    }//GEN-LAST:event_btnEncaminharActionPerformed
                 
            
    private void txtOBSFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOBSFocusGained
        
        if( txtOBS.getText().equals("") ){
            if(cadastrando){
              umMetodo.novaLinhaComData(txtOBS);
              btnGravar.setEnabled(true);
            }
        }
         if(editando){            
            //alterando o ponteiro do mouse indicando que o campo esta editável
            txtOBS.setCursor(new Cursor(Cursor.HAND_CURSOR));
         }
    }//GEN-LAST:event_txtOBSFocusGained
             
    private void gerarChapaFicticia(){
        //caso o equipamento não tenha número de chapa gere uma Chapa Ficticia 
        txtCHAPA.setText(null);
        Long n = new Long("0123456789");                                    //novo long com tamanho 9
        GerarNumerosAleatorios rf = new GerarNumerosAleatorios(n);          //passo o long
        txtCHAPA.setText(String.valueOf(rf.getNumeroAleatorioIP()));
        txtCHAPA.selectAll();
        txtSERIE.requestFocus();    
    }           
    
    private void txtORIGEMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtORIGEMMouseClicked
       txtORIGEM.selectAll();
    }//GEN-LAST:event_txtORIGEMMouseClicked

    private void txtOBSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtOBSMouseClicked
       if(editando){
           contador=1;
           umMetodo.novaLinhaComData(txtOBS);
       }else{
           contador=0;
       }       
    }//GEN-LAST:event_txtOBSMouseClicked

    private void mostrarDadosRegSelecionado()
    {
        //AO CLICAR EM UM REGISTRO DA TABELA MOSTRAR OS DADOS NOS EDITS  
        sql = "SELECT t.tipo, p.*, m.modelo FROM tbltipos t, tblpatrideptos p, tblmodelos m WHERE m.codigo=p.modeloid and p.tipoid = t.codigo and p.codigo="+codigoRegSelecionado+"";        
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);          
        try 
        {          
            conexao.rs.first(); 
            
            //mostrando os dados do registro selecionado nos edits
            txtCODIGO.setText(String.valueOf(conexao.rs.getInt("codigo")));
            txtTIPO.setText(conexao.rs.getString("tipo")); 
            txtMODELO.setText(conexao.rs.getString("modelo")); 
            txtSERIE.setText(conexao.rs.getString("serie")); 
            txtCHAPA.setText(conexao.rs.getString("chapa")); 
            txtORIGEM.setText(conexao.rs.getString("origem")); 
            txtDESTINO.setText(conexao.rs.getString("destino"));          
            txtOBS.setText(conexao.rs.getString("obs"));   
            memoEnv = conexao.rs.getString("memoenvio");
            
            //mostro o status
            cmbSTATUS.removeAllItems();
            cmbSTATUS.addItem(conexao.rs.getString("status"));
            statusAtual = conexao.rs.getString("status");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar selecionar o equipamento!\nERRO:"+ex.getMessage());
        }finally{
             conexao.desconectar();
        }

        //controla apresentacao dos edits sem permitir edição
        txtCODIGO.setEnabled(true);
        txtCODIGO.setEditable(false);
        txtTIPO.setEnabled(true);
        txtTIPO.setEditable(false);
        txtMODELO.setEnabled(true);
        txtMODELO.setEditable(false);
        txtSERIE.setEnabled(true);
        txtSERIE.setEditable(false);
        txtCHAPA.setEnabled(true);
        txtCHAPA.setEditable(false);
        txtORIGEM.setEnabled(true);
        txtORIGEM.setEditable(false);
        txtDESTINO.setEnabled(true);
        txtDESTINO.setEditable(false);          
        txtOBS.setEnabled(true);
        txtOBS.setEditable(false);      
       
    }
    
    private void JTableEnviarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTableEnviarMouseClicked
        /*passando o codigo do patrimonio para o metodo => (int) jTabelaATIVOS.getValueAt(jTabelaATIVOS.getSelectedRow(), 0)
        onde o zero é a coluna que detem o valor que se deseja nesse caso o codigo (coluna 0)*/        
        txtCODIGO.setForeground(Color.red);
        txtCODIGO.requestFocus();
        btnCancelar.setEnabled(true);
        btnCancelar.setText("Voltar");
        btnNovo.setEnabled(false);        
        btnImprimir.setEnabled(false);
        btnEncaminhar.setEnabled(false);
        btnGravar.setEnabled(false);
        btnSair.setEnabled(false);
        btnEditar.setEnabled(true);       
        txtPESQUISA.setEnabled(false);
        clicouEnviar=true;
        editando=true;
        codigoRegSelecionado = (int) JTableEnviar.getValueAt(JTableEnviar.getSelectedRow(), 0);          ;
        mostrarDadosRegSelecionado();
        
    }//GEN-LAST:event_JTableEnviarMouseClicked

    private void JTableEnviadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTableEnviadosMouseClicked
        //passando o codigo do patrimonio para o metodo => (int) jTabelaATIVOS.getValueAt(jTabelaATIVOS.getSelectedRow(), 0) é igual ao codigo do patrimonio selecionado
        codigoRegSelecionado = (int) JTableEnviados.getValueAt(JTableEnviados.getSelectedRow(), 0);
        mostrarDadosRegSelecionado();
        clicouEnviados=true;
        btnCancelarActionPerformed(null);
        btnEncaminhar.setEnabled(true);
        JOptionPane.showMessageDialog(null, "Equipamentos encaminhados para manutenção aguardando emissão do memorando de envio!", "Emitir o memorando de envio!", 2); 

    }//GEN-LAST:event_JTableEnviadosMouseClicked

    private void txtSERIEFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSERIEFocusLost
         //VERIFICAR PELO NUMERO DE SERIE SE ESTE MICRO ESTA INATIVADO
        if(cadastrando){ 
            if(umaBiblio.registroEncontrado("tblpatrideptos", "serie", txtSERIE.getText()))
            {
                JOptionPane.showMessageDialog(null, "Atenção este patrimônio já esta cadastrado, verifique!", "Duplicidade no Cadastro!", 2); 
                cadastrando = false;
                txtSERIE.requestFocus();
                btnCancelarActionPerformed(null);                
            }            
        }
    }//GEN-LAST:event_txtSERIEFocusLost
               
    private void txtCHAPAFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCHAPAFocusLost
       
        if(cadastrando){
            if(txtCHAPA.getText().equals(""))
            {
               txtCHAPA.setText("009"+umMetodo.gerarNumeroAleatorio());
            }
        }
    }//GEN-LAST:event_txtCHAPAFocusLost

    private void filtrarPorDigitacao(String pPesq) {
        
        if(!clicouInativos){     
            
              PreencherTabelaEnviar("SELECT p.*, m.* FROM tblmodelos m, tblpatrideptos p "
              + "WHERE (m.modelo like '%" + pPesq + "%'" + "OR p.origem like '%" + pPesq + "%'" + " OR p.serie like '%" + pPesq + "%'" + " OR p.chapa like '%" + pPesq + "%'"+") "
              + "AND p.modeloid=m.codigo AND (p.status = 'ENVIAR' OR p.status = 'ENVIADO' OR p.status = 'DEVOLVER') ORDER BY p.codigo");           
            
            this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);                        
                     
        }else if(clicouInativos){
            PreencherTabelaEnviados("SELECT p.*, m.* FROM tblmodelos m, tblpatrideptos p "
              + "WHERE (m.modelo like '%" + pPesq + "%'" + "OR p.origem like '%" + pPesq + "%'" + " OR p.serie like '%" + pPesq + "%'" + " OR p.chapa like '%" + pPesq + "%'"+") "
              + "AND p.modeloid=m.codigo AND (p.status = 'ENCERRADO') ORDER BY p.codigo");
           
            this.setTitle("Total de registros inativos retornados pela pesquisa = "+totalRegs);
        }       
        
        if( totalRegs > 0 )btnImprimir.setEnabled(true);        
        btnImprimir.setEnabled(true);  
        
    }    
        
    private void txtPESQUISAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyReleased
        filtrarPorDigitacao(txtPESQUISA.getText());    
    }//GEN-LAST:event_txtPESQUISAKeyReleased

    private void txtSERIEKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSERIEKeyPressed
        //SE TECLAR ENTER DENTRO DO TXT IR PARA TXTDESTINO
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtCHAPA.requestFocus();
            txtCHAPA.selectAll();
        }
        alterouCampo=true;        
    }//GEN-LAST:event_txtSERIEKeyPressed

    private void txtCHAPAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCHAPAKeyPressed
        //SE TECLAR ENTER DENTRO DO TXT IR PARA TXTDESTINO
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtORIGEM.requestFocus();
            txtORIGEM.selectAll();
        }
        alterouCampo=true;        

    }//GEN-LAST:event_txtCHAPAKeyPressed

    private void txtORIGEMFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtORIGEMFocusGained
      if(cadastrando)  {
        if(txtSERIE.getText().equals(""))
         {
            txtSERIE.setText("008"+umMetodo.gerarNumeroAleatorio());
         }    
      }
    }//GEN-LAST:event_txtORIGEMFocusGained

    private void txtORIGEMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtORIGEMKeyPressed
        if(cadastrando){
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                txtOBS.requestFocus();
            }
        }
       
        if(editando){
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {                
                txtDESTINO.requestFocus();
                txtDESTINO.selectAll();
            }
        }
        alterouCampo=true;        

    }//GEN-LAST:event_txtORIGEMKeyPressed
           
    private void txtSERIEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSERIEMouseClicked
        txtSERIE.selectAll();
    }//GEN-LAST:event_txtSERIEMouseClicked

    private void txtCHAPAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCHAPAMouseClicked
        txtCHAPA.selectAll();
    }//GEN-LAST:event_txtCHAPAMouseClicked

    private void txtDESTINOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDESTINOMouseClicked
        txtDESTINO.selectAll();
    }//GEN-LAST:event_txtDESTINOMouseClicked

    private void txtPESQUISAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
          txtPESQUISA.setText(null);                
        }
    }//GEN-LAST:event_txtPESQUISAKeyPressed

    private void txtPESQUISAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPESQUISAMouseClicked
        filtrarPorDigitacao("");   
        txtPESQUISA.setText(null);
        btnCancelarActionPerformed(null);
    }//GEN-LAST:event_txtPESQUISAMouseClicked
        
    private void itemSelecionadoNaComboStatus(){
        if(controle == 1){
            sStatusSelecionado = cmbSTATUS.getSelectedItem().toString();   
            //JOptionPane.showMessageDialog(null,"A origem selecionada foi : "+sOrigemSelecionada);
            
            //DEFININDO AÇÃO APOS A ESCOLHA DA ORIGEM
            if(sStatusSelecionado.equals("ENVIADO")){
                if(editando){  
                   contador=1; 
                   umMetodo.novaLinhaComDataTexto(txtOBS, txtDESTINO.getText());    
                   btnGravar.setEnabled(true);
                }
            }else            
            if(sStatusSelecionado.equals("ENCERRADO")){
                if(editando){  
                   contador=0; 
                   umMetodo.novaLinhaComDataTexto(txtOBS, txtDESTINO.getText());    
                   btnGravar.setEnabled(true);
                }
            }            
            controle = 0;            
        }
    }    
    
    private void itemSelecionadoNaComboFiltro(){
        
        if(controle == 1){
            sOrigemSelecionada = cmbFILTRARPORORIGEM.getSelectedItem().toString();   
            //JOptionPane.showMessageDialog(null,"A origem selecionada foi : "+sOrigemSelecionada);
            
            //DEFININDO AÇÃO APOS A ESCOLHA DA ORIGEM              
            sqlPorOrigem = "SELECT m.modelo, p.* FROM tblmodelos m, tblpatrideptos p WHERE p.modeloid = m.codigo AND (p.origem = '"+sOrigemSelecionada+"') ORDER BY p.codigo";
            PreencherTabelaPorOrigem(sqlPorOrigem);
            JTableStatus.setSelectedIndex(4);
           
            btnCancelar.setEnabled(true);
            btnCancelar.setText("Voltar");
            btnEncaminhar.setEnabled(false); 
            btnSair.setEnabled(false);
            btnImprimir.setEnabled(true);
            btnNovo.setEnabled(false);
            txtPESQUISA.setEnabled(false);
            
            controle = 0;
            filtrou  = true;
        }
    }
        
    private void cmbFILTRARPORORIGEMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbFILTRARPORORIGEMMouseClicked
        controle = 1;
    }//GEN-LAST:event_cmbFILTRARPORORIGEMMouseClicked

    private void cmbFILTRARPORORIGEMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFILTRARPORORIGEMActionPerformed
        itemSelecionadoNaComboFiltro();
    }//GEN-LAST:event_cmbFILTRARPORORIGEMActionPerformed

    private void txtDESTINOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDESTINOKeyPressed
        alterouCampo=true;  
        cmbSTATUS.setEnabled(true);                
        btnGravar.setEnabled(false);
        cmbSTATUS.removeAllItems();
        cmbSTATUS.addItem("ENVIADO");
        cmbSTATUS.setSelectedIndex(-1);    
    }//GEN-LAST:event_txtDESTINOKeyPressed

    private void cmbSTATUSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSTATUSActionPerformed
        itemSelecionadoNaComboStatus();
    }//GEN-LAST:event_cmbSTATUSActionPerformed

    private void txtSERIEFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSERIEFocusGained
        retornaStatusEnviado();
        if(editando){  
            if(!enviado){
                alterouCampo=true;
                //cmbSTATUS.removeAllItems();
                cmbSTATUS.addItem("ENVIADO");               
            }else{
                alterouCampo=true;    
            } 
            
            txtORIGEM.setEditable(false);
            txtORIGEM.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_txtSERIEFocusGained

    private void JTableDevolverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTableDevolverMouseClicked
         /*passando o codigo do patrimonio para o metodo => (int) jTabelaATIVOS.getValueAt(jTabelaATIVOS.getSelectedRow(), 0)
        onde o zero é a coluna que detem o valor que se deseja nesse caso o codigo (coluna 0)*/
        codigoRegSelecionado = (int) JTableDevolver.getValueAt(JTableDevolver.getSelectedRow(), 0);  
        mostrarDadosRegSelecionado();
        txtCODIGO.setForeground(Color.red);
        txtCODIGO.requestFocus();
        btnCancelar.setEnabled(true);
        btnCancelar.setText("Voltar");
        btnNovo.setEnabled(false);        
        btnImprimir.setEnabled(false);
        btnEncaminhar.setEnabled(false);
        btnGravar.setEnabled(false);
        btnSair.setEnabled(false);
        btnEditar.setEnabled(true);       
        txtPESQUISA.setEnabled(false);
        
    }//GEN-LAST:event_JTableDevolverMouseClicked

    private void cmbSTATUSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbSTATUSMouseClicked
        controle=1;
    }//GEN-LAST:event_cmbSTATUSMouseClicked

    private void txtMODELOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMODELOKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtSERIE.requestFocus();
            txtSERIE.selectAll();
        }
    }//GEN-LAST:event_txtMODELOKeyPressed

    private void txtMODELOFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMODELOFocusGained
        
    }//GEN-LAST:event_txtMODELOFocusGained
    
    public void PreencherTabelaEnviar(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Modelo", "Série", "Chapa", "Origem", "Destino", "Entrada", "Status"};
        try 
        {  
            conexao.ExecutarPesquisaSQL(sql);
            
            while (conexao.rs.next())
            {               
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("modelo"),
                    conexao.rs.getString("serie"),
                    conexao.rs.getString("chapa"),
                    conexao.rs.getString("origem"),
                    conexao.rs.getString("destino"),                    
                    conexao.rs.getString("dtentrada"), 
                    conexao.rs.getString("status")
                });
                totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
                
            };    
            
            if(totalRegs == 0){btnImprimir.setEnabled(false);}

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            JTableEnviar.setModel(modelo);
            //define tamanho das colunas
            JTableEnviar.getColumnModel().getColumn(0).setPreferredWidth(50);  //define o tamanho da coluna
            JTableEnviar.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            JTableEnviar.getColumnModel().getColumn(1).setPreferredWidth(280);
            JTableEnviar.getColumnModel().getColumn(1).setResizable(false);
            JTableEnviar.getColumnModel().getColumn(2).setPreferredWidth(120);
            JTableEnviar.getColumnModel().getColumn(2).setResizable(false);
            JTableEnviar.getColumnModel().getColumn(3).setPreferredWidth(120);  //define o tamanho da coluna
            JTableEnviar.getColumnModel().getColumn(3).setResizable(false);    //nao será possivel redimencionar a coluna 
            JTableEnviar.getColumnModel().getColumn(4).setPreferredWidth(95);
            JTableEnviar.getColumnModel().getColumn(4).setResizable(false);
            JTableEnviar.getColumnModel().getColumn(5).setPreferredWidth(95);
            JTableEnviar.getColumnModel().getColumn(5).setResizable(false);
            JTableEnviar.getColumnModel().getColumn(6).setPreferredWidth(80);
            JTableEnviar.getColumnModel().getColumn(6).setResizable(false);
            JTableEnviar.getColumnModel().getColumn(7).setPreferredWidth(80);
            JTableEnviar.getColumnModel().getColumn(7).setResizable(false);

            //define propriedades da tabela
            JTableEnviar.getTableHeader().setReorderingAllowed(false);          //nao podera ser reorganizada
            JTableEnviar.setAutoResizeMode(JTableEnviar.AUTO_RESIZE_OFF);      //nao será possivel redimencionar a tabela
            JTableEnviar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
        
        } catch (SQLException ex) {
                //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
                JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList da tabela ATIVOS!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }        
    }

    public void PreencherTabelaEnviados(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Modelo", "Série", "Chapa", "Origem", "Destino", "Entrada", "Status"};
        try 
        {  
            conexao.ExecutarPesquisaSQL(sql);
            
            while (conexao.rs.next())
            {               
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("modelo"),
                    conexao.rs.getString("serie"),
                    conexao.rs.getString("chapa"),
                    conexao.rs.getString("origem"),
                    conexao.rs.getString("destino"),
                    conexao.rs.getString("dtentrada"), 
                    conexao.rs.getString("status")
                });
                totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
                
            };    
            
            if(totalRegs == 0){btnImprimir.setEnabled(false);}

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            JTableEnviados.setModel(modelo);
            //define tamanho das colunas
            JTableEnviados.getColumnModel().getColumn(0).setPreferredWidth(50);  //define o tamanho da coluna
            JTableEnviados.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            JTableEnviados.getColumnModel().getColumn(1).setPreferredWidth(280);
            JTableEnviados.getColumnModel().getColumn(1).setResizable(false);
            JTableEnviados.getColumnModel().getColumn(2).setPreferredWidth(120);
            JTableEnviados.getColumnModel().getColumn(2).setResizable(false);
            JTableEnviados.getColumnModel().getColumn(3).setPreferredWidth(120);  //define o tamanho da coluna
            JTableEnviados.getColumnModel().getColumn(3).setResizable(false);    //nao será possivel redimencionar a coluna 
            JTableEnviados.getColumnModel().getColumn(4).setPreferredWidth(95);
            JTableEnviados.getColumnModel().getColumn(4).setResizable(false);
            JTableEnviados.getColumnModel().getColumn(5).setPreferredWidth(95);
            JTableEnviados.getColumnModel().getColumn(5).setResizable(false);
            JTableEnviados.getColumnModel().getColumn(6).setPreferredWidth(80);
            JTableEnviados.getColumnModel().getColumn(6).setResizable(false);
            JTableEnviados.getColumnModel().getColumn(7).setPreferredWidth(80);
            JTableEnviados.getColumnModel().getColumn(7).setResizable(false);

            //define propriedades da tabela
            JTableEnviados.getTableHeader().setReorderingAllowed(false);          //nao podera ser reorganizada
            JTableEnviados.setAutoResizeMode(JTableEnviados.AUTO_RESIZE_OFF);      //nao será possivel redimencionar a tabela
            JTableEnviados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
        
        } catch (SQLException ex) {
                //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
                JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList da tabela ATIVOS!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }        
    }
    
    public void PreencherTabelaDevolver(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Modelo", "Série", "Chapa", "Origem", "Destino", "Entrada", "Status"};
        try 
        {  
            conexao.ExecutarPesquisaSQL(sql);
            
            while (conexao.rs.next())
            {               
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("modelo"),
                    conexao.rs.getString("serie"),
                    conexao.rs.getString("chapa"),
                    conexao.rs.getString("origem"),
                    conexao.rs.getString("destino"),
                    conexao.rs.getString("dtentrada"), 
                    conexao.rs.getString("status")
                });
                totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
                
            };    
            
            if(totalRegs == 0){btnImprimir.setEnabled(false);}

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            JTableDevolver.setModel(modelo);
            //define tamanho das colunas
            JTableDevolver.getColumnModel().getColumn(0).setPreferredWidth(50);  //define o tamanho da coluna
            JTableDevolver.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            JTableDevolver.getColumnModel().getColumn(1).setPreferredWidth(280);
            JTableDevolver.getColumnModel().getColumn(1).setResizable(false);
            JTableDevolver.getColumnModel().getColumn(2).setPreferredWidth(120);
            JTableDevolver.getColumnModel().getColumn(2).setResizable(false);
            JTableDevolver.getColumnModel().getColumn(3).setPreferredWidth(120);  //define o tamanho da coluna
            JTableDevolver.getColumnModel().getColumn(3).setResizable(false);    //nao será possivel redimencionar a coluna 
            JTableDevolver.getColumnModel().getColumn(4).setPreferredWidth(95);
            JTableDevolver.getColumnModel().getColumn(4).setResizable(false);
            JTableDevolver.getColumnModel().getColumn(5).setPreferredWidth(95);
            JTableDevolver.getColumnModel().getColumn(5).setResizable(false);
            JTableDevolver.getColumnModel().getColumn(6).setPreferredWidth(80);
            JTableDevolver.getColumnModel().getColumn(6).setResizable(false);
            JTableDevolver.getColumnModel().getColumn(7).setPreferredWidth(80);
            JTableDevolver.getColumnModel().getColumn(7).setResizable(false);

            //define propriedades da tabela
            JTableDevolver.getTableHeader().setReorderingAllowed(false);          //nao podera ser reorganizada
            JTableDevolver.setAutoResizeMode(JTableDevolver.AUTO_RESIZE_OFF);      //nao será possivel redimencionar a tabela
            JTableDevolver.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
        
        } catch (SQLException ex) {
                //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
                JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList da tabela ATIVOS!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }        
    }
    
    public void PreencherTabelaEncerrados(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Modelo", "Série", "Chapa", "Origem", "Destino", "Entrada","Devolução", "Status"};
        try 
        {  
            conexao.ExecutarPesquisaSQL(sql);
            
            while (conexao.rs.next())
            {               
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("modelo"),
                    conexao.rs.getString("serie"),
                    conexao.rs.getString("chapa"),
                    conexao.rs.getString("origem"),
                    conexao.rs.getString("destino"),
                    conexao.rs.getString("dtentrada"), 
                    conexao.rs.getString("dtdevolucao"), 
                    conexao.rs.getString("status")
                });
                totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
                
            };    
            
            if(totalRegs == 0){btnImprimir.setEnabled(false);}

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            JTableEncerrados.setModel(modelo);
            //define tamanho das colunas
            JTableEncerrados.getColumnModel().getColumn(0).setPreferredWidth(50);  //define o tamanho da coluna
            JTableEncerrados.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            JTableEncerrados.getColumnModel().getColumn(1).setPreferredWidth(200);
            JTableEncerrados.getColumnModel().getColumn(1).setResizable(false);
            JTableEncerrados.getColumnModel().getColumn(2).setPreferredWidth(120);
            JTableEncerrados.getColumnModel().getColumn(2).setResizable(false);
            JTableEncerrados.getColumnModel().getColumn(3).setPreferredWidth(120);  //define o tamanho da coluna
            JTableEncerrados.getColumnModel().getColumn(3).setResizable(false);    //nao será possivel redimencionar a coluna 
            JTableEncerrados.getColumnModel().getColumn(4).setPreferredWidth(100);
            JTableEncerrados.getColumnModel().getColumn(4).setResizable(false);
            JTableEncerrados.getColumnModel().getColumn(5).setPreferredWidth(100);
            JTableEncerrados.getColumnModel().getColumn(5).setResizable(false);
            JTableEncerrados.getColumnModel().getColumn(6).setPreferredWidth(80);
            JTableEncerrados.getColumnModel().getColumn(6).setResizable(false);
            JTableEncerrados.getColumnModel().getColumn(7).setPreferredWidth(80);
            JTableEncerrados.getColumnModel().getColumn(7).setResizable(false);
            JTableEncerrados.getColumnModel().getColumn(8).setPreferredWidth(85);
            JTableEncerrados.getColumnModel().getColumn(8).setResizable(false);

            //define propriedades da tabela
            JTableEncerrados.getTableHeader().setReorderingAllowed(false);          //nao podera ser reorganizada
            JTableEncerrados.setAutoResizeMode(JTableEncerrados.AUTO_RESIZE_OFF);      //nao será possivel redimencionar a tabela
            JTableEncerrados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
        
        } catch (SQLException ex) {
                //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
                JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList da tabela ATIVOS!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }        
    }
    
    public void PreencherTabelaPorOrigem(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Modelo", "Série", "Chapa", "Origem", "Destino", "Entrada","Devolução", "Status"};
        try 
        {  
            conexao.ExecutarPesquisaSQL(sql);
            
            while (conexao.rs.next())
            {               
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("modelo"),
                    conexao.rs.getString("serie"),
                    conexao.rs.getString("chapa"),
                    conexao.rs.getString("origem"),
                    conexao.rs.getString("destino"),
                    conexao.rs.getString("dtentrada"), 
                    conexao.rs.getString("dtdevolucao"), 
                    conexao.rs.getString("status")
                });
                totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
                
            };    
            
            if(totalRegs == 0){btnImprimir.setEnabled(false);}

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            JTablePorOrigem.setModel(modelo);
            //define tamanho das colunas
            JTablePorOrigem.getColumnModel().getColumn(0).setPreferredWidth(50);  //define o tamanho da coluna
            JTablePorOrigem.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            JTablePorOrigem.getColumnModel().getColumn(1).setPreferredWidth(200);
            JTablePorOrigem.getColumnModel().getColumn(1).setResizable(false);
            JTablePorOrigem.getColumnModel().getColumn(2).setPreferredWidth(120);
            JTablePorOrigem.getColumnModel().getColumn(2).setResizable(false);
            JTablePorOrigem.getColumnModel().getColumn(3).setPreferredWidth(120);  //define o tamanho da coluna
            JTablePorOrigem.getColumnModel().getColumn(3).setResizable(false);    //nao será possivel redimencionar a coluna 
            JTablePorOrigem.getColumnModel().getColumn(4).setPreferredWidth(100);
            JTablePorOrigem.getColumnModel().getColumn(4).setResizable(false);
            JTablePorOrigem.getColumnModel().getColumn(5).setPreferredWidth(100);
            JTablePorOrigem.getColumnModel().getColumn(5).setResizable(false);
            JTablePorOrigem.getColumnModel().getColumn(6).setPreferredWidth(80);
            JTablePorOrigem.getColumnModel().getColumn(6).setResizable(false);
            JTablePorOrigem.getColumnModel().getColumn(7).setPreferredWidth(80);
            JTablePorOrigem.getColumnModel().getColumn(7).setResizable(false);
            JTablePorOrigem.getColumnModel().getColumn(8).setPreferredWidth(85);
            JTablePorOrigem.getColumnModel().getColumn(8).setResizable(false);

            //define propriedades da tabela
            JTablePorOrigem.getTableHeader().setReorderingAllowed(false);          //nao podera ser reorganizada
            JTablePorOrigem.setAutoResizeMode(JTablePorOrigem.AUTO_RESIZE_OFF);      //nao será possivel redimencionar a tabela
            JTablePorOrigem.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
        
        } catch (SQLException ex) {
                //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
                JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList da tabela ATIVOS!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTableDevolver;
    private javax.swing.JTable JTableEncerrados;
    private javax.swing.JTable JTableEnviados;
    private javax.swing.JTable JTableEnviar;
    private javax.swing.JTable JTablePorOrigem;
    private javax.swing.JTabbedPane JTableStatus;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEncaminhar;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbFILTRARPORORIGEM;
    private javax.swing.JComboBox<String> cmbSTATUS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTextField txtCHAPA;
    private javax.swing.JTextField txtCODIGO;
    private javax.swing.JTextField txtDESTINO;
    private javax.swing.JTextField txtMODELO;
    public javax.swing.JTextArea txtOBS;
    private javax.swing.JTextField txtORIGEM;
    private javax.swing.JTextField txtPESQUISA;
    private javax.swing.JTextField txtSERIE;
    private javax.swing.JTextField txtTIPO;
    // End of variables declaration//GEN-END:variables
}