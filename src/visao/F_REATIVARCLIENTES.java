package visao;

import biblioteca.Biblioteca;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import conexao.ConnConexao;
import biblioteca.ModeloTabela;
import controle.CtrlCliente;
import controle.ControleListaClientes;
import Dao.DAOCliente;
import biblioteca.CampoLimitadoParaRFSomenteNumeros;
import biblioteca.MetodosPublicos;
import static biblioteca.VariaveisPublicas.codigoDepartamento;
import static biblioteca.VariaveisPublicas.codigoSecao;
import static biblioteca.VariaveisPublicas.codigoSelecionado;
import static biblioteca.VariaveisPublicas.contador;
import static biblioteca.VariaveisPublicas.entidadeInativa;
import static biblioteca.VariaveisPublicas.idUsuarioReativado;
import static biblioteca.VariaveisPublicas.nomeRelatorio;
import static biblioteca.VariaveisPublicas.nomeSecao;
import static biblioteca.VariaveisPublicas.oldRF;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import javax.swing.ListSelectionModel;
import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.tabela;
import static biblioteca.VariaveisPublicas.tabela_da_lista;
import controle.ControleGravarLog;
import controle.CtrlUsuario;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import modelo.Cliente;
import modelo.Usuario;

public class F_REATIVARCLIENTES extends javax.swing.JFrame 
{
    ConnConexao conexao  = new ConnConexao();
    Biblioteca    umabiblio          = new Biblioteca();
    MetodosPublicos  umMetodo        = new MetodosPublicos();
    Cliente    umModCliente          = new Cliente();
    Usuario umModUsuario             = new Usuario();
    CtrlUsuario CtrUsuario           = new CtrlUsuario();
    CtrlCliente   ctrCliente         = new CtrlCliente();
    ControleListaClientes umCtrLista = new ControleListaClientes();
    ControleGravarLog umGravarLog    = new ControleGravarLog();
    DAOCliente    clienteDAO         = new DAOCliente();    
    Boolean clicouNaTabela           = false;
    int codigo,idSecaoRegSel,ind     = 0; 
    String secao,rf,nome,sNomeSecao,obs  = "";
    boolean gravando;  //controla no botão gravar entre gravar novo registro e gravar alteração de um registro
    String sqlDefault      = "select c.*, s.nome as secao, s.ramal from tblclientes c, tblsecoes s where s.codigo = c.secaoid and c.status='INATIVO' and c.tipo='F' order by c.nome";    
    String sqlVazia        = "select * from tblclientes where codigo = 0";

    public F_REATIVARCLIENTES() {
        initComponents();
        PreencherTabela(sqlDefault);   
        Leitura();
        setResizable(false);   //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        this.setTitle(umabiblio.mostrarTituloDoFormularioParaInativos());
        //configuracoes dos edits   
        umabiblio.configurarCamposTextos(txtPESQUISA);
        umabiblio.configurarCamposTextos(txtCODIGO);
        umabiblio.configurarCamposTextos(txtNOME);
        umabiblio.configurarCamposTextos(txtRF);
        
        //Impede que formulario seja arrastado na tela
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                setEnabled(false);
                setEnabled(true);
            }
        });//fim addComponentListener       
                
      //configuração dos botões
      umabiblio.configurarBotoes(btnNovo);
      umabiblio.configurarBotoes(btnEditar);
      umabiblio.configurarBotoes(btnGravar);
      umabiblio.configurarBotoes(btnImprimir);
      umabiblio.configurarBotoes(btnVoltar);
      umabiblio.configurarBotoes(btnSair);
      jTabela.setFont(new Font("Arial", Font.BOLD, 12));  
      txtOBS.setFont(new Font("TimesRoman", Font.BOLD, 12));  
      txtCODIGO.setForeground(Color.red);
      txtRF.setDocument(new CampoLimitadoParaRFSomenteNumeros(7));
      btnNovo.setEnabled(false);
      cmbSecao.addItem("REATIVAR");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jBoxDados = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        txtNOME = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtCODIGO = new javax.swing.JTextField();
        cmbSecao = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtRF = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtOBS = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabela = new javax.swing.JTable();
        jBoxPesquisar = new javax.swing.JLayeredPane();
        txtPESQUISA = new javax.swing.JTextField();
        jBoxBotoes = new javax.swing.JLayeredPane();
        btnGravar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

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
        txtNOME.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNOMEFocusGained(evt);
            }
        });
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
        txtCODIGO.setName("txtCODIGO"); // NOI18N

        cmbSecao.setForeground(new java.awt.Color(51, 51, 255));
        cmbSecao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel4.setText("SEÇÃO");

        jLabel5.setText("RF XXXXXXX");

        txtRF.setForeground(new java.awt.Color(51, 51, 255));
        txtRF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtRFMouseClicked(evt);
            }
        });
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

        jBoxDados.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtNOME, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(cmbStatus, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtCODIGO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(cmbSecao, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtRF, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtOBS, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jBoxDadosLayout = new javax.swing.GroupLayout(jBoxDados);
        jBoxDados.setLayout(jBoxDadosLayout);
        jBoxDadosLayout.setHorizontalGroup(
            jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOBS, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jBoxDadosLayout.createSequentialGroup()
                                .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNOME, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jBoxDadosLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(8, 8, 8)
                                .addComponent(jLabel1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRF, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jBoxDadosLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbSecao, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jBoxDadosLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 66, Short.MAX_VALUE))
                            .addComponent(cmbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jBoxDadosLayout.setVerticalGroup(
            jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jBoxDadosLayout.createSequentialGroup()
                                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNOME, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jBoxDadosLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtRF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jBoxDadosLayout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cmbSecao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOBS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jBoxDados);
        jBoxDados.setBounds(10, 80, 730, 130);

        jTabela.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTabela);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(10, 290, 730, 250);

        jBoxPesquisar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxPesquisar.setName("panelDados"); // NOI18N

        txtPESQUISA.setToolTipText("Filtrar por digitação");
        txtPESQUISA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPESQUISAActionPerformed(evt);
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

        jBoxPesquisar.setLayer(txtPESQUISA, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jBoxPesquisarLayout = new javax.swing.GroupLayout(jBoxPesquisar);
        jBoxPesquisar.setLayout(jBoxPesquisarLayout);
        jBoxPesquisarLayout.setHorizontalGroup(
            jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxPesquisarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtPESQUISA, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
                .addContainerGap())
        );
        jBoxPesquisarLayout.setVerticalGroup(
            jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxPesquisarLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(txtPESQUISA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        getContentPane().add(jBoxPesquisar);
        jBoxPesquisar.setBounds(10, 10, 730, 60);

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
        btnEditar.setText("Reativar");
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

        jBoxBotoes.setLayer(btnGravar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnVoltar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnSair, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnNovo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnEditar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnImprimir, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jBoxBotoesLayout = new javax.swing.GroupLayout(jBoxBotoes);
        jBoxBotoes.setLayout(jBoxBotoesLayout);
        jBoxBotoesLayout.setHorizontalGroup(
            jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jBoxBotoesLayout.setVerticalGroup(
            jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnNovo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jBoxBotoes);
        jBoxBotoes.setBounds(10, 220, 730, 67);

        setSize(new java.awt.Dimension(760, 587));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
     private void popularComboStatus(){
        cmbStatus.removeAllItems();
        cmbStatus.addItem("ATIVO");
    }
    private void txtPESQUISAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPESQUISAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPESQUISAActionPerformed
    
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
                cmbSecao.removeAllItems();      //primeiro apaga os ítens que foram selecionados anteriormente
                cmbSecao.addItem(sNomeSecao);   //seta a seçao do usuario selecionado                
                //JOptionPane.showMessageDialog(null,"SECAO SELECIONADA..: " +conexao.resultset.getString("nome"));
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
     
    private void mostrarDados()
    {
        //AO CLICAR EM UM REGISTRO DA TABELA MOSTRAR OS DADOS NOS EDITS        
        int codigo = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0);
        codigoSelecionado = codigo;
        
        //seta o nivel de acesso do usuario ao clicar na tabela e mostra a seção do mesmo
         sql = "SELECT * FROM tblclientes WHERE codigo="+codigo+" and tipo='F'";        
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);          
        try 
        {          
            conexao.rs.first(); 
            //mostrando os dados do registro selecionado nos edits
            txtCODIGO.setText(String.valueOf(conexao.rs.getInt("codigo")));
            txtNOME.setText(conexao.rs.getString("nome")); 
            txtRF.setText(conexao.rs.getString("rf"));
            txtOBS.setText(conexao.rs.getString("obs"));
            idSecaoRegSel = conexao.rs.getInt("secaoid");   
            oldRF = conexao.rs.getString("rf");
            
            //mostro o status
            cmbStatus.removeAllItems();
            cmbStatus.addItem(conexao.rs.getString("status"));
            
            //MostrarSecaoSelecionada(idSecaoRegSel);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar selecionar a seção!\nERRO:"+ex.getMessage());
        }finally{
             conexao.desconectar();
        }

        //controla apresentacao dos edits
        txtPESQUISA.setEditable(false);
        txtCODIGO  .setEnabled(true);
        txtCODIGO  .setEditable(false);
        txtNOME    .setEnabled(true);
        txtNOME    .setEditable(false);
        txtOBS     .setEnabled(true);
        txtOBS     .setEditable(false);
        txtRF      .setEnabled(true);
        txtRF      .setEditable(false);        
        
    }
    
    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        //AO CLICAR EM UM REGISTRO DA TABELA MOSTRAR OS DADOS NOS EDITS
        clicouNaTabela = true;
        mostrarDados();
        btnEditar      .setEnabled(true);
        btnGravar      .setEnabled(false);
        btnNovo        .setEnabled(false);
        btnImprimir    .setEnabled(false); 
        btnVoltar      .setEnabled(true);
        btnSair        .setEnabled(false);  
        cmbSecao       .setEnabled(true);
        cmbStatus      .setEnabled(true);        
        
    }//GEN-LAST:event_jTabelaMouseClicked

    private void txtNOMEFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNOMEFocusGained
        txtNOME.selectAll();//selecionando todo o texto pra edição
    }//GEN-LAST:event_txtNOMEFocusGained

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        conexao.conectar();        
        String sql="select * from tblclientes where status='INATIVO' and tipo='F' order by nome";  
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if( conexao.rs.next())
            {   //selecionando a primeira linha somente se tiver registros
                jTabela.addRowSelectionInterval(0,0);
                
            }
        }catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao preencher o combo de seções!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        }         
    }//GEN-LAST:event_formWindowOpened
    
    private void txtPESQUISAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyPressed
        //se teclar enter estando dentro da pesquisa limpar a pesquisa
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
          txtPESQUISA.setText(null);       
        }
    }//GEN-LAST:event_txtPESQUISAKeyPressed
    private void filtrarPorDigitacao(String pPesq)   
    {
        //filtrando por digitação pelos trez campos nome rf ou secao
        PreencherTabela("select c.*, s.ramal, s.nome as secao from tblclientes c, tblsecoes s where (c.nome like '%"+pPesq+"%'"+" "
                      + "OR c.rf like '%"+pPesq+"%'"+" OR s.nome like '%"+pPesq+"%'"+") and s.codigo = c.secaoid "
                      + "and c.status='INATIVO' AND c.tipo='F' order by c.nome");
    }
    private void txtPESQUISAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyReleased
        //filtrar o nome conforme o cliente for digitando
        filtrarPorDigitacao(txtPESQUISA.getText());
    }//GEN-LAST:event_txtPESQUISAKeyReleased

    private void txtRFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRFFocusGained
        txtRF.selectAll();
    }//GEN-LAST:event_txtRFFocusGained

    private void txtRFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtRFMouseClicked
        txtRF.selectAll();
    }//GEN-LAST:event_txtRFMouseClicked

    private void txtNOMEKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNOMEKeyPressed
        //se teclar enter estando dentro do txtNOME vá para o campo do rf
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
          txtRF.requestFocus();          
        }
    }//GEN-LAST:event_txtNOMEKeyPressed

    private void txtRFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRFKeyPressed
        //se teclar enter estando dentro do txtNOME vá para o campo do rf
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
          txtOBS.requestFocus();          
        }
    }//GEN-LAST:event_txtRFKeyPressed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        ReativarRegistro();        
        Leitura();
    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        Leitura();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        //populando os combobox
        popularComboStatus();
        umabiblio.PreencherCombo(cmbSecao, "tblsecoes", "nome");
        //controlando os botoes
        HabilitarDesabilitarBotoes(false);
        btnVoltar.setText("Cancelar");
        umabiblio.limparTodosCampos(jBoxDados);
        txtPESQUISA.setText(null);
        txtNOME  .requestFocus();
        txtNOME  .setEditable(true);
        txtOBS   .setEditable(true);
        txtRF    .setEditable(true);
        gravando = true;
        txtCODIGO.setText(String.valueOf(umabiblio.mostrarProximoCodigo(tabela)));
        PreencherTabela(sqlVazia);

    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        //abre lista de setores para escolher um para o colaborador ora reativado
        tabela_da_lista = "TBLSECOES";        
        F_LISTAPADRAO frm = new F_LISTAPADRAO(this,true);
        frm.setVisible(true);       
        
        Edicao();
        //controla apresentacao dos edits
        
        //limpa a como secao
        cmbSecao.removeAllItems();
        cmbSecao.addItem(nomeSecao);
        
        //popular a combo status com opcoes ATIVO pra manter inalterado e INATIVO caso deseje inativar
        cmbStatus.removeAllItems();
        cmbStatus.addItem("ATIVO");     
        
        PreencherTabela(sqlVazia);

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        txtPESQUISA.setText("INATIVOS");           
        contador++;

        //IMPRESSAO DO RELATORIO PARA IMPRESSORA
        nomeRelatorio = "relclientesInativos";       
        
        //IMPRESSAO DO PDF
        entidadeInativa = txtPESQUISA.getText(); //DEFINE SE IMPRIMIRA ATIVOS OU INATIVOS-> DIGITE INATIVOS NA PESQUISA
        F_IMPRESSAO frm = new F_IMPRESSAO();
        frm.setVisible(true);            
        
        btnVoltar.setEnabled(true);
        btnImprimir.setEnabled(false);
        btnNovo.setEnabled(false);      
        btnVoltarActionPerformed(null);
        
    }//GEN-LAST:event_btnImprimirActionPerformed

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
            java.util.logging.Logger.getLogger(F_REATIVARCLIENTES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_REATIVARCLIENTES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_REATIVARCLIENTES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_REATIVARCLIENTES.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new F_REATIVARCLIENTES().setVisible(true);

            }
        });
    }        
   
    private void ReativarRegistro() 
    {
        //setando os valores dos edits  
        String status = (String) cmbStatus.getSelectedItem();
        nome          = txtNOME.getText();
        obs           = txtOBS.getText(); 
        codigo        = Integer.parseInt(txtCODIGO.getText());
        
        umModCliente.setCodigo(codigo);
        umModCliente.setNome(nome);
        umModCliente.setRf(oldRF);        
        umModCliente.setSecaoid(codigoSecao); 
        umModCliente.setStatus(status);          
        umModCliente.setDeptoid(codigoDepartamento);
        umModCliente.setTipo("F");
        umModCliente.setObs(obs);                         
        
        //atualizar tb na tblclientes
        idUsuarioReativado = umMetodo.getCodigoPassandoString("tblclientes", "nome", nome);
        umMetodo.alterarSecaoClienteSeForAlteradaSecaoDoUsuario(codigoSecao, idUsuarioReativado);        
       
        
        //VERIFICAR SE O CLIENTE REATIVADO É UM USUÁRIO INATIVADO E PERGUNTAR SE DESEJA MANTER A PERMISSÃO DE USUARIO
        if(umMetodo.EclienteUsuario(nome)){
            if(umMetodo.ConfirmouOperacao("Este é um cliente usuário do sistema deseja manter esta permissão?", "Cliente Usuário"))
            {
                 //SE SIM EXECUTAR A REATIVAÇÃO DO USUÁRIO CASO CONTRARIO DEIXA COMO ESTA INATIVADO
                idUsuarioReativado = umMetodo.getCodigoPassandoString("tblusuarios", "nome", nome);
                umMetodo.reativarUsuarioSeForClienteReativado(idUsuarioReativado);                
                
            }else{
                //SE RESPONDER NAO
                int idUsuarioReativado = umMetodo.getCodigoPassandoString("tblusuarios", "nome", nome);
                umMetodo.alterarSecaoUsuarioSeForAlteradaSecaoDoClienteColaborador(codigoSecao, idUsuarioReativado);                
            }
        }
        umMetodo.alterarSecaoUsuarioSeForAlteradaSecaoDoClienteColaborador(codigoSecao, idUsuarioReativado);
        ctrCliente.atualizarCliente(umModCliente);
        umGravarLog.gravarLog("atualizacao no cadastro de "+umModCliente.getNome());
        
        
        
        Leitura();
        PreencherTabela(sqlDefault);
        txtNOME.setEditable(false);
        gravando = false;
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
                
        //preenchendo a tabela com os registros
        PreencherTabela(sqlDefault); 
        
        //desabilitando os edits para edicao
        c = jBoxDados.getComponents();
        for(int i=0; i<c.length; i++)
        {
            c[i].setEnabled(Habilitar);  
        }      
                
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
        
        btnNovo          .setEnabled(!Habilitar);
        btnImprimir      .setEnabled(Habilitar);
        btnSair          .setEnabled(Habilitar);
        btnGravar        .setEnabled(!Habilitar);
        btnEditar        .setEnabled(!Habilitar);
        btnVoltar        .setEnabled(!Habilitar);      
                
        //preenchendo a tabela com os registros
        PreencherTabela(sqlDefault); 
        
        //desabilitando os edits para edicao
        c = jBoxDados.getComponents();
        for(int i=0; i<c.length; i++)
        {
            c[i].setEnabled(!Habilitar);  
            umabiblio.limparTodosCampos(this);
            txtPESQUISA.requestFocus();
        }
                       
        //mostrando o titulo com qde de registros cadastrados
        this.setTitle(umabiblio.mostrarTituloDoFormularioParaInativos());
        
        //pulando de campo com enter, nao usei porque esse procedimento desabilita o metodo de enter pra apagar o campo de pesquisa
        //umabiblio.passaCamposComEnter(this);
        
        //limpando os combos 
        umabiblio.LimparCombo(cmbSecao, "tblsecoes", "nome");
        cmbStatus       .setSelectedIndex(-1);
                                  
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
                
        //habilitando os edits para edicao
        c = jBoxDados.getComponents();
        for(int i=0; i<c.length; i++)
            c[i].setEnabled(!Habilitar);
        
        //desabilitando a pesquisa enquanto cadastra
        c = jBoxPesquisar.getComponents();
        for(int i=0; i<c.length; i++)
            c[i].setEnabled(Habilitar);
        
    }

    
    public void PreencherTabela(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Nome", "RF", "Seção", "Ramal"};
        try 
        {  
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next())
            {
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("nome"),
                    conexao.rs.getString("rf"),
                    conexao.rs.getString("secao"),
                    conexao.rs.getString("ramal")
                });
            }; 
               
                ModeloTabela modelo = new ModeloTabela(dados, Colunas);
                jTabela.setModel(modelo);
                //define tamanho das colunas
                jTabela.getColumnModel().getColumn(0).setPreferredWidth(60);  //define o tamanho da coluna
                jTabela.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
                jTabela.getColumnModel().getColumn(1).setPreferredWidth(330);
                jTabela.getColumnModel().getColumn(1).setResizable(false);  
                jTabela.getColumnModel().getColumn(2).setPreferredWidth(80);  //define o tamanho da coluna
                jTabela.getColumnModel().getColumn(2).setResizable(false);    //nao será possivel redimencionar a coluna 
                jTabela.getColumnModel().getColumn(3).setPreferredWidth(150);
                jTabela.getColumnModel().getColumn(3).setResizable(false);  
                jTabela.getColumnModel().getColumn(4).setPreferredWidth(80);
                jTabela.getColumnModel().getColumn(4).setResizable(false);  
                //define propriedades da tabela
                jTabela.getTableHeader().setReorderingAllowed(false);        //nao podera ser reorganizada
                jTabela.setAutoResizeMode(jTabela.AUTO_RESIZE_OFF);          //nao será possivel redimencionar a tabela
                jTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
                
        } catch (SQLException ex) {
            //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
            JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        }        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<String> cmbSecao;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLayeredPane jBoxBotoes;
    private javax.swing.JLayeredPane jBoxDados;
    private javax.swing.JLayeredPane jBoxPesquisar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabela;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtCODIGO;
    private javax.swing.JTextField txtNOME;
    private javax.swing.JTextField txtOBS;
    private javax.swing.JTextField txtPESQUISA;
    private javax.swing.JTextField txtRF;
    // End of variables declaration//GEN-END:variables

}
