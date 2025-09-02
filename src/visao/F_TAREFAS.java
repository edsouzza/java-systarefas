package visao;

import Dao.DAOTarefa;
import biblioteca.Biblioteca;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import controle.ControleListaTarefas;
import controle.CtrlTarefa;
import static biblioteca.VariaveisPublicas.codigoUsuario;
import static biblioteca.VariaveisPublicas.codigoCliente;
import static biblioteca.VariaveisPublicas.nomeCliente;
import static biblioteca.VariaveisPublicas.tabela;
import static biblioteca.VariaveisPublicas.codigoSelecionado;
import static biblioteca.VariaveisPublicas.editando;
import static biblioteca.VariaveisPublicas.nomeRelatorio;
import static biblioteca.VariaveisPublicas.totalRegs;
import controle.ControleGravarLog;
import controle.CtrlCliente;
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.Cliente;
import modelo.Tarefa;

public class F_TAREFAS extends javax.swing.JFrame {
    ConnConexao                 conexao  = new ConnConexao();
    Biblioteca                  umabiblio                  = new Biblioteca();
    Tarefa                      umModTarefa                = new Tarefa();
    Cliente                     umMoCliente                = new Cliente();
    CtrlCliente                 umControleCliente          = new CtrlCliente();
    CtrlTarefa                  CtrTarefa                  = new CtrlTarefa();
    ControleListaTarefas        umCtrLista                 = new ControleListaTarefas();
    ControleGravarLog           umGravarLog                = new ControleGravarLog();
    DAOTarefa                   tarefaDAO                  = new DAOTarefa(); 
    MetodosPublicos             umMetodo                   = new MetodosPublicos();
    
    Boolean clicouNaTabela,clicouConcluidas, pesquisando, cadastrando = false;
    String tarefa,situacao,nomeCli,nomesecao  = null;    
    int codigo,idClienteRegSel,ind  = 0; 
    boolean gravando,filtrandoFechadas,novatarefa;  //controla no botão gravar entre gravar novo registro e gravar alteração de um registro
    
    String sqlVazia     = "select * from tbltarefas where codigo = 0";
    String sqlABERTA    = "select t.*, c.nome as cliente, c.codigo, c.secaoid, s.codigo, s.nome as secao from tblclientes c, tbltarefas t, tblsecoes s "
                        + "where c.codigo = t.clienteid and c.secaoid = s.codigo  and t.status = 'ABERTA' order by t.dtabertura desc"; 
                             
    String sqlCONCLUIDA = "select t.*, c.nome as cliente, c.codigo, c.secaoid, s.codigo, s.nome as secao from tblclientes c, tbltarefas t, tblsecoes s "
                        + "where c.codigo = t.clienteid and c.secaoid = s.codigo  and t.status = 'FECHADA' order by t.dtfechamento desc";           
    
    public F_TAREFAS() {
        initComponents();
        Leitura();        
        PreencherTabelaABERTA(sqlABERTA);         //abre o formulario mostrando todos os registro cadastrados na tabela         
        PreencherTabelaCONCLUIDA(sqlCONCLUIDA);   //abre o formulario mostrando todos os registro cadastrados na tabela    
        setResizable(false);          //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        this.setTitle(this.mostrarTituloDoFormulario());
        
        // Colocando enter para pular de campo 
        HashSet conj = new HashSet(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS)); 
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0)); 
        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj); 
        
        //configuracoes dos edits 
        umabiblio.configurarCamposTextos(txtCODIGO);
        umabiblio.configurarCamposTextos(txtNOMECLIENTE);    
        umabiblio.configurarCamposTextos(txtPESQUISA);    
        txtSITUACAO.setFont(new Font("TimesRoman",Font.BOLD,12)); 
        txtTAREFA.setFont(new Font("TimesRoman",Font.BOLD,12)); 
        txtNOMECLIENTE.setText(nomeCliente);
        txtNOMECLIENTE.setForeground(Color.red);
        cmbFILTRARPORSECAO.setForeground(Color.blue);

       //configuração dos botões
       umabiblio.configurarBotoes(btnNovo);
       umabiblio.configurarBotoes(btnEditar);
       umabiblio.configurarBotoes(btnGravar);
       umabiblio.configurarBotoes(btnImprimir);
       umabiblio.configurarBotoes(btnVoltar);
       umabiblio.configurarBotoes(btnSair);
       umabiblio.configurarBotoes(btnPesquisar);
               
       //cofigurações das tabelas
       jTabbedPane1.setFont(new Font("TimesRoman",Font.BOLD,12)); 
       jTabelaABERTA.setFont(new Font("TimesRoman",Font.BOLD,12));
       jTabelaABERTA.setForeground(Color.blue);
       jTabelaCONCLUIDA.setFont(new Font("TimesRoman",Font.BOLD,12)); 
       jTabelaCONCLUIDA.setForeground(Color.red);
       txtCODIGO.setForeground(Color.red);
        
        
        //Impede que formulario seja arrastado na tela
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                setEnabled(false);
                setEnabled(true);
            }
        });//fim addComponentListener       
        
    }
    
    private void PreencherComboFILTRARPORSECAO()
    {
        cmbFILTRARPORSECAO.removeAllItems();
        
        conexao.conectar();           
        sql="select distinct s.nome from tblclientes c, tblsecoes s, tbltarefas t where c.secaoid=s.codigo and c.codigo=t.clienteid and t.status ='ABERTA' order by s.nome";
                   
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
    private void PreencherComboComSecaoDaLinhaSelecionada(int codCliente)
    {
        cmbFILTRARPORSECAO.removeAllItems();
        btnFILTRAR.setEnabled(false);
        TituloSecao.setText("SEÇÃO");
        conexao.conectar();           
        sql="select DISTINCT s.nome as secao from tblclientes c, tbltarefas t, tblsecoes s where c.secaoid = s.codigo  and c.codigo = "+codCliente+" and t.status = 'ABERTA'";
                   
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
    
    private void PreencherComboFILTRARPORSECAOFECHADAS()
    {
        cmbFILTRARPORSECAO.removeAllItems();
        
        conexao.conectar();           
        sql="select distinct s.nome from tblclientes c, tblsecoes s, tbltarefas t where c.secaoid=s.codigo and c.codigo=t.clienteid and t.status ='FECHADA' order by s.nome";
                    
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
    
    public int qdeRegistrosABERTOS(String tabela)
    {        
        conexao.conectar();
        sql = "select count(codigo) as total from "+tabela+" where status='ABERTA'";
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
        int qdeRegs        = umabiblio.qdeRegistros(tabela);
        int qdeRegsABERTOS = this.qdeRegistrosABERTOS(tabela);
        //substring retira o TBL da palavra
        
        String nomeTabela = tabela.substring(3);
        nomeTabela        = nomeTabela.toLowerCase();  
        
         if(qdeRegs == 1){
           return "Gerenciamento de " + nomeTabela +" com "+qdeRegs+" registro cadastrado e "+qdeRegsABERTOS+" aberto"; 
        }else{
           return "Gerenciamento de " + nomeTabela +" com "+qdeRegs+" registros cadastrados e "+qdeRegsABERTOS+" abertos";    
        }                     
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabelaABERTA = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTabelaCONCLUIDA = new javax.swing.JTable();
        jBoxDados = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        txtTAREFA = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<String>();
        txtCODIGO = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNOMECLIENTE = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtSITUACAO = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        cmbFILTRARPORSECAO = new javax.swing.JComboBox<String>();
        TituloSecao = new javax.swing.JLabel();
        btnFILTRAR = new javax.swing.JButton();
        txtPESQUISA = new javax.swing.JTextField();
        jBoxBotoes = new javax.swing.JLayeredPane();
        btnGravar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnPesquisar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 733));

        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jTabelaABERTA.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabelaABERTA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaABERTAMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTabelaABERTA);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1029, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 20, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("ABERTAS", jPanel2);

        jTabelaCONCLUIDA.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabelaCONCLUIDA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaCONCLUIDAMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTabelaCONCLUIDA);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1021, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("CONCLUIDAS", jPanel3);

        jBoxDados.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxDados.setName("panelDados"); // NOI18N

        jLabel1.setText("TAREFA");

        txtTAREFA.setForeground(new java.awt.Color(51, 51, 255));
        txtTAREFA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtTAREFAMouseExited(evt);
            }
        });
        txtTAREFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTAREFAActionPerformed(evt);
            }
        });
        txtTAREFA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTAREFAFocusGained(evt);
            }
        });
        txtTAREFA.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtTAREFAPropertyChange(evt);
            }
        });
        txtTAREFA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTAREFAKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTAREFAKeyReleased(evt);
            }
        });

        jLabel3.setText("CÓDIGO");

        cmbStatus.setForeground(new java.awt.Color(51, 51, 255));
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ABERTA", "FECHAR" }));
        cmbStatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbStatus.setEnabled(false);

        txtCODIGO.setForeground(new java.awt.Color(51, 51, 255));
        txtCODIGO.setToolTipText("CODIGO DA TAREFA");

        jLabel4.setText("CLIENTE");

        jLabel5.setText("SITUAÇÃO");

        txtNOMECLIENTE.setForeground(new java.awt.Color(51, 51, 255));
        txtNOMECLIENTE.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNOMECLIENTEFocusGained(evt);
            }
        });

        txtSITUACAO.setColumns(20);
        txtSITUACAO.setRows(5);
        txtSITUACAO.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtSITUACAOFocusGained(evt);
            }
        });
        txtSITUACAO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSITUACAOMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(txtSITUACAO);

        jLabel6.setText("STATUS");

        cmbFILTRARPORSECAO.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cmbFILTRARPORSECAO.setForeground(new java.awt.Color(51, 51, 255));
        cmbFILTRARPORSECAO.setToolTipText("Escolha uma seção para filtrar");
        cmbFILTRARPORSECAO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbFILTRARPORSECAO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbFILTRARPORSECAOActionPerformed(evt);
            }
        });

        TituloSecao.setText("FILTRAR");

        btnFILTRAR.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        btnFILTRAR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_pesquisa.gif"))); // NOI18N
        btnFILTRAR.setText("FILTRAR");
        btnFILTRAR.setToolTipText("Filtrar por seção ou por tipo");
        btnFILTRAR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFILTRAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFILTRARActionPerformed(evt);
            }
        });

        txtPESQUISA.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        txtPESQUISA.setForeground(new java.awt.Color(51, 51, 255));
        txtPESQUISA.setToolTipText("barra de pesquisa");
        txtPESQUISA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPESQUISAKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPESQUISAKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jBoxDadosLayout = new javax.swing.GroupLayout(jBoxDados);
        jBoxDados.setLayout(jBoxDadosLayout);
        jBoxDadosLayout.setHorizontalGroup(
            jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPESQUISA)
                    .addComponent(jScrollPane1)
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtTAREFA)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jBoxDadosLayout.createSequentialGroup()
                                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtCODIGO))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(614, 614, 614))
                                    .addComponent(txtNOMECLIENTE))))
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBoxDadosLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(TituloSecao, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(99, 99, 99))
                            .addGroup(jBoxDadosLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(cmbStatus, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBoxDadosLayout.createSequentialGroup()
                                        .addComponent(cmbFILTRARPORSECAO, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnFILTRAR)))))))
                .addContainerGap())
        );
        jBoxDadosLayout.setVerticalGroup(
            jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBoxDadosLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(txtPESQUISA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(TituloSecao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNOMECLIENTE, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFILTRARPORSECAO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFILTRAR, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTAREFA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jBoxDados.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtTAREFA, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(cmbStatus, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtCODIGO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtNOMECLIENTE, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(cmbFILTRARPORSECAO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(TituloSecao, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(btnFILTRAR, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtPESQUISA, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jBoxBotoes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

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

        btnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_inicio.gif"))); // NOI18N
        btnVoltar.setText("Voltar");
        btnVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVoltar.setPreferredSize(new java.awt.Dimension(77, 25));
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
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

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/users16.jpg"))); // NOI18N
        btnNovo.setText("Nova");
        btnNovo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
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

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_imprimir.gif"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImprimir.setPreferredSize(new java.awt.Dimension(77, 25));
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_pesquisa.gif"))); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPesquisar.setPreferredSize(new java.awt.Dimension(77, 25));
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jBoxBotoesLayout = new javax.swing.GroupLayout(jBoxBotoes);
        jBoxBotoes.setLayout(jBoxBotoesLayout);
        jBoxBotoesLayout.setHorizontalGroup(
            jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jBoxBotoesLayout.setVerticalGroup(
            jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jBoxBotoes.setLayer(btnGravar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnVoltar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnSair, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnNovo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnEditar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnImprimir, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnPesquisar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBoxDados)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jBoxBotoes)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jBoxDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBoxBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1036, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 756, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1062, 799));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    public int BuscarIndice()
    {
        //essa funcão retorna um inteiro que é o indice de cada registro na tabela
        conexao.conectar();        
        String sql="select * from tbltarefas order by codigo";  
        conexao.ExecutarPesquisaSQL(sql);
        //Adicionando dados na lista
        try 
        {
            while(conexao.rs.next())
            {
                String tarefa = conexao.rs.getString("tarefa");  
                umCtrLista.addTarefas(tarefa);
            }
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao preencher o combo de seções!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        } 
           
        //mostrando os dados da lista
//        for(String nome: umCtrLista.lstClientes)
//        {
//            System.out.println(umCtrLista.retornaIndice(nome)+" - "+nome);
//        }
        
        return  umCtrLista.retornaIndice(tarefa); //a função retorna o indice de cada tarefa cadastrado
    }
         
        
    private void gravarInclusaoRegistro() {
        //se digitou algo nos campos nome e rf        
        if (txtTAREFA.getText().length() > 0 ) 
        {
            //setando os valores dos edits  
            tarefa              = txtTAREFA.getText(); 
            String status       = cmbStatus.getSelectedItem().toString();   
            situacao            = txtSITUACAO.getText();
            
            umModTarefa.setSituacao(situacao);
            umModTarefa.setTarefa(tarefa); 
            umModTarefa.setClienteid(codigoCliente); 
            umModTarefa.setUsuarioid(codigoUsuario);             
            umModTarefa.setStatus(status); 
            
            CtrTarefa.salvarTarefa(umModTarefa);  
            
            umGravarLog.gravarLog("cadastro de nova tarefa");
            PreencherTabelaABERTA(sqlABERTA);   
            gravando = false;
        }else{
           //JOptionPane.showMessageDialog(this, "Operação inválida o campo tarefa não aceita um valor nulo!","Digite uma tarefa!",2);
            JOptionPane.showMessageDialog(null, "Operação inválida o campo tarefa não aceita um valor nulo!");
            txtTAREFA.requestFocus();
        }
        nomeCliente = null;
        //Preenchendo a combo de filtro
        PreencherComboFILTRARPORSECAO();
        cmbFILTRARPORSECAO.setSelectedIndex(-1);    
    }

    private void gravarEdicaoRegistro() 
    {
        //setando os valores dos edits  
        String status   = (String) cmbStatus.getSelectedItem();
        tarefa          = txtTAREFA.getText();
        codigo          = Integer.parseInt(txtCODIGO.getText());
        situacao        = txtSITUACAO.getText(); 
                
        umModTarefa.setTarefa(tarefa); 
        umModTarefa.setCodigo(codigo);          
        umModTarefa.setStatus(status);  
        String textoAtual = txtSITUACAO.getText();
        
        if(status.equals("FECHAR")){
            umModTarefa.setSituacao(textoAtual+"\n"+umMetodo.getStringComDataHora()+"Tarefa atendida e fechada"); 
            umModTarefa.setStatus("FECHADA");  
            CtrTarefa.fecharTarefa(umModTarefa);
        }else{
             umModTarefa.setSituacao(situacao);       
        }
        if(umabiblio.ConfirmouOperacao("Confirma o desejo de gravar a as alterações e/ou fechar a tarefa selecionada?","Confirmar edição!"))
        {
            CtrTarefa.atualizarTarefa(umModTarefa);  
            PreencherTabelaABERTA(sqlABERTA);
            PreencherTabelaCONCLUIDA(sqlCONCLUIDA);
            txtTAREFA.setEditable(false);
            gravando = false;
            umGravarLog.gravarLog("atualizacao da tarefa "+umModTarefa.getCodigo());

        }
    }  
    
    private void Leitura() {
        if(umabiblio.tabelaVazia(tabela)){
            JOptionPane.showMessageDialog(null, "A tabela: " + tabela + " esta vazia cadastre o primeiro registro!");
            btnImprimir.setEnabled(false);
        }else{ 
            btnImprimir.setEnabled(true);
            
        }
        umabiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 
        clicouConcluidas=false;           
        btnNovo.setEnabled(true);
        btnGravar.setEnabled(false);
        btnVoltar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnSair.setEnabled(true);  
        txtCODIGO.setEditable(false);
        txtTAREFA.setEditable(false);
        txtNOMECLIENTE.setEditable(false);
        txtSITUACAO.setEditable(false);  
        txtPESQUISA.setEditable(false); 
        txtPESQUISA.setText(null);
        cmbStatus.setSelectedIndex(-1);
        btnVoltar.setToolTipText("");
        
        //Preenchendo a combo de filtro
        PreencherComboFILTRARPORSECAO();
        cmbFILTRARPORSECAO.setSelectedIndex(-1);        
        
    }
    
    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        
        if (gravando) {
            gravarInclusaoRegistro();
        } else {
            gravarEdicaoRegistro();
        }
        txtSITUACAO.setText(null);
        txtTAREFA.setText(null);
        btnEditar.setEnabled(false);
        btnVoltar.setEnabled(false);
        btnNovo.setEnabled(true);
        btnSair.setEnabled(true);
        btnGravar.setEnabled(false);
        cmbStatus.setSelectedIndex(-1); //volta para ATIVO depois de gravar
        cmbFILTRARPORSECAO.setSelectedIndex(-1);
        cmbStatus.setEnabled(false);
        txtTAREFA.setEditable(false);
        txtSITUACAO.setEditable(false);
        txtNOMECLIENTE.setText(null);
        txtCODIGO.setText(null);
        TituloSecao.setText("FILTRAR");
        editando = false;
        novatarefa=false;
        TituloSecao.setVisible(true);
        btnFILTRAR.setEnabled(true);
        
    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed

        PreencherTabelaABERTA(sqlABERTA);        
        PreencherTabelaCONCLUIDA(sqlCONCLUIDA);           
        jTabbedPane1.setSelectedIndex(0);
        codigoSelecionado = 0;        
        txtNOMECLIENTE.setText(null);
        txtTAREFA.setEditable(false);
        txtPESQUISA.setEditable(false);
        txtCODIGO.setEditable(false);
        txtCODIGO.setText(null);
        txtTAREFA.setText(null);
        btnEditar.setEnabled(false);
        btnNovo.setEnabled(true);
        btnVoltar.setEnabled(false);
        btnVoltar.setText("Voltar");
        btnVoltar.setToolTipText("");
        btnGravar.setEnabled(false);
        btnSair.setEnabled(true);
        cmbStatus.setSelectedIndex(-1);          
        txtSITUACAO.setText(null);
        txtPESQUISA.setText(null);
        nomeCliente = null;
        editando    = false;
        novatarefa=false;
        TituloSecao.setVisible(true);
        btnPesquisar.setEnabled(true);
        btnFILTRAR.setEnabled(true);
        cmbFILTRARPORSECAO.setEnabled(true);        
        clicouConcluidas = false;
        TituloSecao.setText("FILTRAR");
        PreencherComboFILTRARPORSECAO();
        cmbFILTRARPORSECAO.setSelectedIndex(-1);
        
        //verificando se a tabela ainda esta vazia para controle de botoes
        if(umabiblio.tabelaVazia(tabela))
        {
            btnImprimir.setEnabled(false);
        }else{
            btnImprimir.setEnabled(true);
        }
        this.setTitle(this.mostrarTituloDoFormulario());

    }//GEN-LAST:event_btnVoltarActionPerformed
        
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        nomeCliente = null;
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        
            btnGravar.setEnabled(false);
            btnNovo.setEnabled(false);
            btnEditar.setEnabled(false);
            btnImprimir.setEnabled(false);
            btnPesquisar.setEnabled(false);
            btnSair.setEnabled(false);
            btnVoltar.setEnabled(true);
            btnVoltar.setText("Cancelar");
            txtTAREFA.setEditable(true);
            txtTAREFA.requestFocus();        
            txtTAREFA.setText(null);   //apagando o edit
            txtTAREFA.requestFocus();
            txtCODIGO.setText(null);
            txtNOMECLIENTE.setEditable(false);
            gravando = true;
            cmbStatus.setSelectedIndex(0);
            txtCODIGO.setText(String.valueOf(umabiblio.mostrarProximoCodigo(tabela)));     
            PreencherTabelaABERTA(sqlVazia);
            PreencherTabelaCONCLUIDA(sqlVazia);
            
            novatarefa=true;            
            if(novatarefa){
                               
               cmbFILTRARPORSECAO.removeAllItems();
               btnFILTRAR.setEnabled(false);
               TituloSecao.setVisible(false);
                
            }
            
            //abre o formulario de escolha de clientes/colaboradores
            F_LISTACLIENTESATIVOS frm = new F_LISTACLIENTESATIVOS(this, true);
            frm.setVisible(true);
        

    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
    if(umabiblio.permissaoLiberada()){
        editando = true;
        btnGravar.setEnabled(true);
        btnEditar.setEnabled(false);
        txtTAREFA.setEditable(true);
        txtSITUACAO.setEditable(true);
        cmbStatus.setEnabled(true);        
        txtTAREFA.requestFocus();
        txtTAREFA.selectAll();//selecionando todo o texto pra edição        
        btnVoltar.setText("Cancelar");
        cadastrando=false;
        cmbFILTRARPORSECAO.setEnabled(false);     
    }

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        if(codigoSelecionado > 0)
        {
              nomeRelatorio = "reltarefasfechadas";
          }else{
              nomeRelatorio = "reltarefasabertas";
          } 
          btnVoltar.setEnabled(true);
          btnImprimir.setEnabled(false);  
          
          F_IMPRESSAO frm = new F_IMPRESSAO();
          frm.setVisible(true);          
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void jTabelaABERTAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaABERTAMouseClicked
        //AO CLICAR EM UM REGISTRO DA TABELA MOSTRAR OS DADOS NOS EDITS
        txtCODIGO.setForeground(Color.red);
        btnEditar.setEnabled(true);
        btnGravar.setEnabled(true);
        btnVoltar.setEnabled(true);
        btnNovo.setEnabled(false);
        btnImprimir.setEnabled(false);
        btnSair.setEnabled(false);
        clicouNaTabela=true;
        btnGravar.setEnabled(false);
        cmbStatus.setSelectedIndex(0);
        txtTAREFA.setEditable(false);
        txtTAREFA.requestFocus();

        //pesquisar o nome do cliente pelo codigo da tarefa retornado apos a escolha da tarefa
        int codigoTarefa = (int) jTabelaABERTA.getValueAt(jTabelaABERTA.getSelectedRow(), 0);
        sql = "SELECT t.*, c.nome as nomeCliente FROM "+tabela+" t, tblclientes c WHERE t.clienteid=c.codigo AND t.codigo="+codigoTarefa+"";
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        try {
            conexao.rs.first();   
            txtCODIGO.setText(String.valueOf(conexao.rs.getInt("codigo"))); 
            idClienteRegSel   = conexao.rs.getInt("clienteid");
            txtSITUACAO.setText(conexao.rs.getString("situacao"));
            txtTAREFA.setText(conexao.rs.getString("tarefa"));    
            txtNOMECLIENTE.setText(conexao.rs.getString("nomecliente"));    
            nomeCli = conexao.rs.getString("nomecliente");           
            
         } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar selecionar a seção!\nERRO:"+ex.getMessage());
        }finally{
            conexao.desconectar();
        }
        
        PreencherComboComSecaoDaLinhaSelecionada(idClienteRegSel);

    }//GEN-LAST:event_jTabelaABERTAMouseClicked

    private void jTabelaCONCLUIDAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaCONCLUIDAMouseClicked
        //AO CLICAR EM UM REGISTRO DA TABELA MOSTRAR OS DADOS NOS EDITS
        txtCODIGO.setForeground(Color.red);
        btnEditar.setEnabled(false);
        btnGravar.setEnabled(false);
        btnVoltar.setEnabled(true);
        btnNovo.setEnabled(false);
        btnImprimir.setEnabled(false);
        btnSair.setEnabled(false);
        clicouNaTabela=true;
        btnGravar.setEnabled(false);
        cmbStatus.setSelectedIndex(1);
        
        //pesquisar o nome do cliente pelo codigo da tarefa retornado apos a escolha da tarefa
        int codigoTarefa = (int) jTabelaCONCLUIDA.getValueAt(jTabelaCONCLUIDA.getSelectedRow(), 0);
        sql = "SELECT t.*, c.nome as nomeCliente FROM "+tabela+" t, tblclientes c WHERE t.clienteid=c.codigo AND t.codigo="+codigoTarefa+"";
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        try {
            conexao.rs.first();   
            txtCODIGO.setText(String.valueOf(conexao.rs.getInt("codigo"))); 
            idClienteRegSel   = conexao.rs.getInt("clienteid");
            txtSITUACAO.setText(conexao.rs.getString("situacao"));
            txtTAREFA.setText(conexao.rs.getString("tarefa"));    
            txtNOMECLIENTE.setText(conexao.rs.getString("nomecliente"));  
            nomeCli = conexao.rs.getString("nomecliente");
            
         } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar selecionar a seção!\nERRO:"+ex.getMessage());
        }finally{
            conexao.desconectar();
        }
        
        PreencherComboComSecaoDaLinhaSelecionada(idClienteRegSel);

    }//GEN-LAST:event_jTabelaCONCLUIDAMouseClicked

    private void txtTAREFAPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtTAREFAPropertyChange

    }//GEN-LAST:event_txtTAREFAPropertyChange

    private void txtTAREFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTAREFAActionPerformed
    }//GEN-LAST:event_txtTAREFAActionPerformed

    private void txtTAREFAFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTAREFAFocusGained
        if (gravando){ 
         txtNOMECLIENTE.setText(nomeCliente);           
       }
        txtTAREFA.selectAll();           //selecionando todo o texto pra edição
        txtSITUACAO.setCaretPosition(0); //setando a primeira linha do JTextArea 
    }//GEN-LAST:event_txtTAREFAFocusGained

    private void txtNOMECLIENTEFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNOMECLIENTEFocusGained
        
        
    }//GEN-LAST:event_txtNOMECLIENTEFocusGained

    private void txtTAREFAMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTAREFAMouseExited
        
    }//GEN-LAST:event_txtTAREFAMouseExited

    private void txtTAREFAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTAREFAKeyPressed
        //se teclar enter estando dentro da pesquisa limpar a caixa de texto
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            txtTAREFA.setText(null);     
        }            
    }//GEN-LAST:event_txtTAREFAKeyPressed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        //se clicou em tarefas fechadas
        if (jTabbedPane1.getSelectedIndex() == 1) {
            umabiblio.limparTodosCampos(rootPane);  
            btnGravar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnVoltar.setEnabled(true);
            btnNovo.setEnabled(false);
            btnVoltar.setText("Voltar");
            btnImprimir.setEnabled(true);
            txtTAREFA.setEditable(false);            
            gravando = false;  
            codigoSelecionado = 1;            
            btnPesquisar.setEnabled(true);
            clicouConcluidas = true;
            PreencherComboFILTRARPORSECAOFECHADAS();
            cmbFILTRARPORSECAO.setSelectedIndex(-1);
        }else{
            //se clicou em tarefas abertas
            umabiblio.limparTodosCampos(rootPane);  
            btnGravar.setEnabled(false);
            btnEditar.setEnabled(false);
            btnVoltar.setEnabled(false);
            btnNovo.setEnabled(true);   
            btnImprimir.setEnabled(true);
            txtTAREFA.setEditable(false);
            btnSair.setEnabled(true);            
            gravando = false;  
            codigoSelecionado = 0;
            clicouConcluidas = false;
            PreencherComboFILTRARPORSECAO();
            //cmbFILTRARPORSECAO.setSelectedIndex(-1);
            
        }
        
    }//GEN-LAST:event_jTabbedPane1MouseClicked
    
    private void filtrarPorDigitacao()   
    {
        String pPesq = txtPESQUISA.getText();
        
        if(clicouConcluidas){ 
            sql = "select t.*, c.nome as cliente, c.codigo, c.secaoid, s.codigo, s.nome as secao from tblclientes c, "
                      + "tbltarefas t, tblsecoes s where c.codigo = t.clienteid and c.secaoid = s.codigo  and "
                      + "t.status = 'FECHADA' and (c.nome like '%" + pPesq + "%'" + "OR tarefa like '%" + pPesq + "%'" + "OR s.nome like '%" + pPesq + "%'"+") order by t.dtabertura desc, s.nome";
                      this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);
                      PreencherTabelaCONCLUIDA(sql);                    
        }else{
            sql = "select t.*, c.nome as cliente, c.codigo, c.secaoid, s.codigo, s.nome as secao from tblclientes c, "
                      + "tbltarefas t, tblsecoes s where c.codigo = t.clienteid and c.secaoid = s.codigo  and "
                      + "t.status = 'ABERTA' and (c.nome like '%" + pPesq + "%'" + "OR tarefa like '%" + pPesq + "%'" + "OR s.nome like '%" + pPesq + "%'"+") order by t.dtabertura desc, s.nome";
                      this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);
                      PreencherTabelaABERTA(sql);    
        }
        btnVoltar.setText("Limpar");
        btnVoltar.setToolTipText("Limpar Pesquisa");
    }
    private void LimparCamposSetarPesquisa(){
            //limpando os campos e setando o campo txtTAREFA pra receber a digitação
            txtCODIGO.setText(null);
            txtNOMECLIENTE.setText(null);
            txtSITUACAO.setText(null);
            txtPESQUISA.setText(null);
            txtPESQUISA.setEditable(true);
            txtPESQUISA.requestFocus();
            btnPesquisar.setEnabled(false);
            btnNovo.setEnabled(false);
            btnImprimir.setEnabled(false);
            btnVoltar.setEnabled(true);
            btnEditar.setEnabled(false);
            cmbFILTRARPORSECAO.setEnabled(false);
            btnFILTRAR.setEnabled(false);
    }
    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        LimparCamposSetarPesquisa();        
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void txtTAREFAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTAREFAKeyReleased
        filtrarPorDigitacao();        
    }//GEN-LAST:event_txtTAREFAKeyReleased

    private void txtSITUACAOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSITUACAOMouseClicked
        if(editando){
            String textoAtual = txtSITUACAO.getText();
            if(txtSITUACAO.getText().equals("")){
                txtSITUACAO.setText(textoAtual+umMetodo.getStringComDataHora()+txtTAREFA.getText());
            }else{
                txtSITUACAO.setText(textoAtual+"\n"+umMetodo.getStringComDataHora());        
            }
        }
    }//GEN-LAST:event_txtSITUACAOMouseClicked

    private void txtSITUACAOFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSITUACAOFocusGained
        if(gravando){
            String situacao = umMetodo.getStringComDataHora()+"Tarefa "+txtTAREFA.getText();
            btnGravar.setEnabled(true);
            if(txtSITUACAO.getText().equals("")){
                txtSITUACAO.setText(situacao);
            }
        }
    }//GEN-LAST:event_txtSITUACAOFocusGained

    private void FiltrarPorSecao(){
        if(cmbFILTRARPORSECAO.getSelectedIndex() == -1){
           JOptionPane.showMessageDialog(null, "Selecione uma seção ao lado para filtrar!", "Seção não selecionada!", 2);
           PreencherTabelaABERTA(sqlABERTA);
        }else{            
            if(clicouConcluidas){            
                nomesecao = cmbFILTRARPORSECAO.getSelectedItem().toString(); 
                String sqlFILTRADA  = "select t.codigo, c.nome as cliente, s.nome as secao, t.tarefa, t.dtabertura, t.dtfechamento, t.status from tblclientes c, tbltarefas t, tblsecoes s "
                                    + "where c.codigo = t.clienteid and c.secaoid = s.codigo and t.status = 'FECHADA' and s.nome='"+nomesecao+"' order by t.dtabertura desc";
                PreencherTabelaCONCLUIDA(sqlFILTRADA);  
            }else{         
                nomesecao = cmbFILTRARPORSECAO.getSelectedItem().toString(); 
                String sqlFILTRADA  = "select t.codigo, c.nome as cliente, s.nome as secao, t.tarefa, t.dtabertura, t.status from tblclientes c, tbltarefas t, tblsecoes s "
                                    + "where c.codigo = t.clienteid and c.secaoid = s.codigo and t.status = 'ABERTA' and s.nome='"+nomesecao+"' order by t.dtabertura desc";
                PreencherTabelaABERTA(sqlFILTRADA);  
            }          
                        
            btnGravar.setEnabled(false);
            btnNovo.setEnabled(false);
            btnEditar.setEnabled(false);
            btnImprimir.setEnabled(false);
            btnPesquisar.setEnabled(false);
            btnSair.setEnabled(false);
            btnVoltar.setEnabled(true);
            btnFILTRAR.setEnabled(false);
            cmbFILTRARPORSECAO.setEnabled(false);
            
        }
    }
    
    private void btnFILTRARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFILTRARActionPerformed
         FiltrarPorSecao();        
    }//GEN-LAST:event_btnFILTRARActionPerformed
     
    
    private void txtPESQUISAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyPressed
        //se teclar enter estando dentro da pesquisa limpar a pesquisa
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtPESQUISA.setText(null);
            txtPESQUISA.requestFocus();
        }
    }//GEN-LAST:event_txtPESQUISAKeyPressed

    private void txtPESQUISAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyReleased
        //filtrar tarefas conforme texto digitado a procura se dará no campo tarefas
        filtrarPorDigitacao();
        
    }//GEN-LAST:event_txtPESQUISAKeyReleased

    private void cmbFILTRARPORSECAOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbFILTRARPORSECAOActionPerformed
        //ESSA COMBO SÓ MOSTRARA REGISTROS SE EM DETERMINADA SECAO TIVER TAREFAS EM ABERTO
    }//GEN-LAST:event_cmbFILTRARPORSECAOActionPerformed
    
    public void PreencherTabelaABERTA(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Cliente", "Seção", "Tarefa", "Abertura", "Status"};
        try 
        {            
            conexao.ExecutarPesquisaSQL(sql);  
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            while (conexao.rs.next())
            {   
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("cliente"),
                    conexao.rs.getString("secao"),
                    conexao.rs.getString("tarefa"),
                    df.format(conexao.rs.getDate("dtabertura")),
                    conexao.rs.getString("status")
                });
                    totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
            };

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabelaABERTA.setModel(modelo);
            //define tamanho das colunas
            jTabelaABERTA.getColumnModel().getColumn(0).setPreferredWidth(50);  //define o tamanho da coluna
            jTabelaABERTA.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaABERTA.getColumnModel().getColumn(1).setPreferredWidth(300);  //define o tamanho da coluna
            jTabelaABERTA.getColumnModel().getColumn(1).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaABERTA.getColumnModel().getColumn(2).setPreferredWidth(110);
            jTabelaABERTA.getColumnModel().getColumn(2).setResizable(false);  
            jTabelaABERTA.getColumnModel().getColumn(3).setPreferredWidth(410);  //define o tamanho da coluna
            jTabelaABERTA.getColumnModel().getColumn(3).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaABERTA.getColumnModel().getColumn(4).setPreferredWidth(80);
            jTabelaABERTA.getColumnModel().getColumn(4).setResizable(false);  
            jTabelaABERTA.getColumnModel().getColumn(5).setPreferredWidth(60);
            jTabelaABERTA.getColumnModel().getColumn(5).setResizable(false);  

            //define propriedades da tabela
            jTabelaABERTA.getTableHeader().setReorderingAllowed(false);        //nao podera ser reorganizada
            jTabelaABERTA.setAutoResizeMode(jTabelaABERTA.AUTO_RESIZE_OFF);          //nao será possivel redimencionar a tabela
            jTabelaABERTA.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
   
        } catch (SQLException ex) {
            //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
            JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        }
    }
    
    public void PreencherTabelaCONCLUIDA(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Cliente", "Seção", "Tarefa", "Abertura", "Fechamento", "Status"};
        try 
        {            
            conexao.ExecutarPesquisaSQL(sql);  
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            while (conexao.rs.next())
            {   
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("cliente"),
                    conexao.rs.getString("secao"),
                    conexao.rs.getString("tarefa"),
                    df.format(conexao.rs.getDate("dtabertura")),
                    df.format(conexao.rs.getDate("dtfechamento")),
                    conexao.rs.getString("status")
                });
                    totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
                };
                
                ModeloTabela modelo = new ModeloTabela(dados, Colunas);
                jTabelaCONCLUIDA.setModel(modelo);
                //define tamanho das colunas
                jTabelaCONCLUIDA.getColumnModel().getColumn(0).setPreferredWidth(50);  //define o tamanho da coluna
                jTabelaCONCLUIDA.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
                jTabelaCONCLUIDA.getColumnModel().getColumn(1).setPreferredWidth(290);
                jTabelaCONCLUIDA.getColumnModel().getColumn(1).setResizable(false);  
                jTabelaCONCLUIDA.getColumnModel().getColumn(2).setPreferredWidth(110);  //define o tamanho da coluna
                jTabelaCONCLUIDA.getColumnModel().getColumn(2).setResizable(false);    //nao será possivel redimencionar a coluna 
                jTabelaCONCLUIDA.getColumnModel().getColumn(3).setPreferredWidth(330);
                jTabelaCONCLUIDA.getColumnModel().getColumn(3).setResizable(false);  
                jTabelaCONCLUIDA.getColumnModel().getColumn(4).setPreferredWidth(75);
                jTabelaCONCLUIDA.getColumnModel().getColumn(4).setResizable(false);  
                jTabelaCONCLUIDA.getColumnModel().getColumn(5).setPreferredWidth(80);
                jTabelaCONCLUIDA.getColumnModel().getColumn(5).setResizable(false);  
                jTabelaCONCLUIDA.getColumnModel().getColumn(6).setPreferredWidth(70);
                jTabelaCONCLUIDA.getColumnModel().getColumn(6).setResizable(false);  
                                
                //define propriedades da tabela
                jTabelaCONCLUIDA.getTableHeader().setReorderingAllowed(false);        //nao podera ser reorganizada
                jTabelaCONCLUIDA.setAutoResizeMode(jTabelaCONCLUIDA.AUTO_RESIZE_OFF);          //nao será possivel redimencionar a tabela
                jTabelaCONCLUIDA.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha                  
           
        } catch (SQLException ex) {
            //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
            JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TituloSecao;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnFILTRAR;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<String> cmbFILTRARPORSECAO;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLayeredPane jBoxBotoes;
    private javax.swing.JLayeredPane jBoxDados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTabelaABERTA;
    private javax.swing.JTable jTabelaCONCLUIDA;
    private javax.swing.JTextField txtCODIGO;
    private javax.swing.JTextField txtNOMECLIENTE;
    private javax.swing.JTextField txtPESQUISA;
    private javax.swing.JTextArea txtSITUACAO;
    private javax.swing.JTextField txtTAREFA;
    // End of variables declaration//GEN-END:variables
}
