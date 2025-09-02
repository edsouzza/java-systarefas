package visao;

import Dao.DAOModelo;
import biblioteca.Biblioteca;
import biblioteca.CampoTxtLimitadoPorQdeCaracteresUpperCase;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import static biblioteca.VariaveisPublicas.isDeContrato;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import controle.CtrlModelo;
import static biblioteca.VariaveisPublicas.tabela;
import controle.ControleGravarLog;
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
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.Modelo;

public class F_MODELOS extends javax.swing.JFrame {
    ConnConexao         conexao               = new ConnConexao();
    Biblioteca          umabiblio             = new Biblioteca();
    Modelo              umModModelo           = new Modelo();
    CtrlModelo          CtrModelo             = new CtrlModelo();
    ControleGravarLog   umGravarLog           = new ControleGravarLog();
    DAOModelo           modeloDAO             = new DAOModelo();  
    MetodosPublicos     umMetodo              = new MetodosPublicos();
    
    Boolean clicouNaTabela,clicouConcluidas   = false;
    String modelo,nomeCli,descricao,deContrato,deStatus = null;    
    int codigo,idClienteRegSel,ind,tipoid,clicou,contador  = 0; 
    boolean gravando,pesquisando, alterouStatus,alterouContrato;  //controla no botão gravar entre gravar novo registro e gravar alteração de um registro
    String sqlDefault   = "select * from tblmodelos where status='ATIVO' order by modelo";
    String sqlVazia     = "select * from tblmodelos where codigo = 0";
    
    public F_MODELOS() {
        initComponents();
        Leitura();
        PreencherTabela(sqlDefault);      //abre o formulario mostrando todos os registro cadastrados na tabela         
        setResizable(false);              //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        this.setTitle(this.mostrarTituloDoFormulario());
        
        // Colocando enter para pular de campo 
        HashSet conj = new HashSet(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS)); 
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0)); 
        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj); 
        
        //configuracoes dos edits 
        txtDESCRICAO.setFont(new Font("TimesRoman", Font.BOLD, 16));
        umabiblio.configurarCamposTextos(txtCODIGO);
        umabiblio.configurarCamposTextos(txtMODELO);  
        txtMODELO.setForeground(Color.red);
        txtMODELO.setDocument(new CampoTxtLimitadoPorQdeCaracteresUpperCase(36));
        
       //configuração dos botões
       umabiblio.configurarBotoes(btnNovo);
       umabiblio.configurarBotoes(btnEditar);
       umabiblio.configurarBotoes(btnGravar);
       umabiblio.configurarBotoes(btnVoltar);
       umabiblio.configurarBotoes(btnSair);
       umabiblio.configurarBotoes(btnPesquisar);
               
       //cofigurações das tabelas
       jTabela.setFont(new Font("TimesRoman",Font.BOLD,12));
       jTabela.setForeground(Color.blue);
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
    public int qdeRegistrosCADASTRADOS(String tabela)
    {        
        conexao.conectar();
        sql = "select count(codigo) as total from "+tabela;
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
        int qdeRegistrosCADASTRADOS = this.qdeRegistrosCADASTRADOS(tabela);
        //substring retira o TBL da palavra
        
        String nomeTabela = tabela.substring(3);
        nomeTabela        = nomeTabela.toLowerCase();  
        
         if(qdeRegs == 1){
           return "Gerenciamento de " + nomeTabela +" com "+qdeRegs+" registro cadastrado"; 
        }else{
           return "Gerenciamento de " + nomeTabela +" com "+qdeRegs+" registros cadastrados";    
        }                     
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabela = new javax.swing.JTable();
        btnNovo = new javax.swing.JButton();
        btnGravar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        btnPesquisar = new javax.swing.JButton();
        btnLimparPesquisa = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDESCRICAO = new javax.swing.JTextArea();
        txtMODELO = new javax.swing.JTextField();
        txtCODIGO = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbSTATUS = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cmbTIPO = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cmbCONTRATO = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 733));

        jTabela.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTabela);

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

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_pesquisa.gif"))); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPesquisar.setPreferredSize(new java.awt.Dimension(77, 25));
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        btnLimparPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_limpar.gif"))); // NOI18N
        btnLimparPesquisa.setText("Limpar");
        btnLimparPesquisa.setToolTipText("Limpar Pesquisa");
        btnLimparPesquisa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimparPesquisa.setEnabled(false);
        btnLimparPesquisa.setPreferredSize(new java.awt.Dimension(77, 25));
        btnLimparPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparPesquisaActionPerformed(evt);
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

        txtDESCRICAO.setColumns(20);
        txtDESCRICAO.setRows(5);
        jScrollPane1.setViewportView(txtDESCRICAO);

        txtMODELO.setForeground(new java.awt.Color(51, 51, 255));
        txtMODELO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMODELOKeyReleased(evt);
            }
        });

        txtCODIGO.setForeground(new java.awt.Color(51, 51, 255));
        txtCODIGO.setToolTipText("CODIGO DA TAREFA");

        jLabel3.setText("CÓDIGO");

        jLabel4.setText("MODELO");

        jLabel2.setText("STATUS");

        cmbSTATUS.setForeground(new java.awt.Color(51, 51, 255));
        cmbSTATUS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "INATIVO" }));
        cmbSTATUS.setSelectedIndex(-1);
        cmbSTATUS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbSTATUS.setEnabled(false);
        cmbSTATUS.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbSTATUSItemStateChanged(evt);
            }
        });

        jLabel6.setText("TIPO");

        cmbTIPO.setForeground(new java.awt.Color(51, 51, 255));
        cmbTIPO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbTIPO.setEnabled(false);

        jLabel5.setText("DESCRIÇÃO DO MODELO");

        jLabel7.setText("CONTRATO");

        cmbCONTRATO.setForeground(new java.awt.Color(51, 51, 255));
        cmbCONTRATO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbCONTRATO.setEnabled(false);
        cmbCONTRATO.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCONTRATOItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtCODIGO, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtMODELO))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(cmbTIPO, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(cmbCONTRATO, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(cmbSTATUS, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane2))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addComponent(jLabel2))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cmbSTATUS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMODELO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmbTIPO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbCONTRATO, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1043, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 769, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1059, 804));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtMODELOKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMODELOKeyReleased
        if (pesquisando)
           filtrarPorDigitacao();
    }//GEN-LAST:event_txtMODELOKeyReleased

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparPesquisaActionPerformed
        Leitura();
    }//GEN-LAST:event_btnLimparPesquisaActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        txtMODELO.setEditable(true);
        txtMODELO.requestFocus();
        pesquisando = true;
        btnLimparPesquisa.setEnabled(true);
        btnPesquisar.setEnabled(false);
        btnNovo.setEnabled(false);       
        cmbTIPO.setEnabled(false);
        cmbCONTRATO.setEnabled(false);
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        Leitura();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if(umabiblio.permissaoLiberada()){
            
            pesquisando = false;
            gravando    = false;
            btnGravar.setEnabled(true);
            btnEditar.setEnabled(false);        
            txtMODELO.setEditable(true);
            txtDESCRICAO.setEditable(true);
            txtMODELO.requestFocus();
            txtMODELO.selectAll();//selecionando todo o texto pra edição
            btnVoltar.setText("Cancelar");
            btnPesquisar.setEnabled(false);
            btnLimparPesquisa.setEnabled(false);        
            cmbTIPO.setEnabled(true);             
            
            cmbCONTRATO.setEnabled(true);        
            cmbSTATUS.setEnabled(true);          

            //tratando o contrato
            if(deContrato.equals("S")){
                
                cmbCONTRATO.addItem("NÃO");
            }else{
                cmbCONTRATO.addItem("SIM");
            }
            //tratando o status
            
            cmbSTATUS.addItem("INATIVO");
            
           PreencherTabela(sql);
        }
                        
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        if (gravando) {
            if (txtMODELO.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Atenção digite o nome do modelo para continuar!","Descrição do nome do modelo inválida!",2);  
                    txtMODELO.requestFocus();  
            }else if(cmbTIPO.getSelectedIndex() == -1){
                JOptionPane.showMessageDialog(null,"Atenção escolha um tipo para gravar o novo modelo!","Escolha um tipo de modelo!",2);  
                cmbTIPO.requestFocus();
            }else if(txtDESCRICAO.getText().isEmpty() || txtMODELO.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Atenção digite a descrição do modelo para continuar!","Descrição do modelo inválida!",2);  
                    txtDESCRICAO.requestFocus();                
            }else{
                gravarInclusaoRegistro();                
            }            
        } else {
            gravarEdicaoRegistro();
        }
        //usado no controle de adição de cmbSTATUS.addItem("INATIVO");
        contador = 1;
    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        //abre form de questionamento sobre se é contrato ou não
        if(umabiblio.permissaoLiberada())
        {
            //Define se o equipamento é de contrato ou não
            F_LISTATIPOSCONTRATOS frmTiposContratos = new F_LISTATIPOSCONTRATOS(this, true);
            frmTiposContratos.setVisible(true); 
            
            cmbCONTRATO.removeAllItems();
            if(isDeContrato){                
                cmbCONTRATO.addItem("SIM"); 
            }else{
               cmbCONTRATO.addItem("NÃO");  
            }  

            pesquisando = false;
            gravando    = true;
            
            btnNovo.setEnabled(false);
            btnEditar.setEnabled(false);
            btnSair.setEnabled(false);
            btnGravar.setEnabled(true);
            btnPesquisar.setEnabled(false);
            btnVoltar.setEnabled(true);
            btnVoltar.setText("Cancelar");              
            
            txtDESCRICAO.setEditable(true);
            txtMODELO.setEditable(true);        
            txtMODELO.requestFocus();
            txtCODIGO.setText(null);            
            txtCODIGO.setText(String.valueOf(umabiblio.mostrarProximoCodigo(tabela)));
            
            umabiblio.PreencherCombo(cmbTIPO, "tbltipos", "tipo");
            cmbTIPO.setEnabled(true);
            cmbTIPO.setSelectedIndex(-1);
            
            cmbSTATUS.addItem("ATIVO");  
            cmbSTATUS.setSelectedIndex(0); //add ATIVO e mostrar na combobox
            
            PreencherTabela(sqlVazia);
        }
        
    }//GEN-LAST:event_btnNovoActionPerformed

    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        //AO CLICAR EM UM REGISTRO DA TABELA MOSTRAR OS DADOS NOS EDITS        
        umabiblio.PreencherCombo(cmbTIPO, "tbltipos", "tipo");
        
        btnEditar           .setEnabled(true);   
        btnGravar           .setEnabled(false);
        btnNovo             .setEnabled(false);
        btnVoltar           .setEnabled(true);
        btnSair             .setEnabled(false);
        btnPesquisar        .setEnabled(false);
        btnLimparPesquisa   .setEnabled(false);
        
        txtDESCRICAO.setEditable(false);
        txtDESCRICAO.setCaretPosition(0); //setando a primeira linha do JTextArea
        txtDESCRICAO.requestFocus();  
        txtMODELO.setEditable(false);
        
        mostrarDados();
        
    }//GEN-LAST:event_jTabelaMouseClicked

    private void cmbSTATUSItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbSTATUSItemStateChanged
        alterouStatus = true;
    }//GEN-LAST:event_cmbSTATUSItemStateChanged

    private void cmbCONTRATOItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCONTRATOItemStateChanged
        alterouContrato = true;
    }//GEN-LAST:event_cmbCONTRATOItemStateChanged
    
    private void gravarInclusaoRegistro() {
        //se digitou algo nos campos nome e rf        
        if (txtMODELO.getText().length()>0 ||txtDESCRICAO.getText().length()>0)  
        {
            if(isDeContrato){
               umModModelo.setContrato("S"); 
            }else{
               umModModelo.setContrato("N");  
            }

            //setando os valores dos edits  
            modelo               = txtMODELO.getText(); 
            descricao            = txtDESCRICAO.getText();
            tipoid               = umabiblio.buscarCodigoGenerico("tbltipos", "tipo", cmbTIPO.getSelectedItem().toString());
            umModModelo.setDescricao(descricao);
            umModModelo.setModelo(modelo); 
            umModModelo.setTipoid(tipoid); 
            
            if (umMetodo.temDuplicidadeDeCadastro("TBLMODELOS","MODELO",modelo))
            {
                JOptionPane.showMessageDialog(null, "O modelo "+modelo+" já esta cadastrado, verifique!","Duplicidade no cadastro!",2);  
            }else{
                CtrModelo.salvarModelo(umModModelo);              
                umGravarLog.gravarLog("cadastro de novo modelo");
            }
                      
            Leitura();
            
        }else{
           JOptionPane.showMessageDialog(this, "Operação inválida o campos modelo e descrição não aceitam um valor nulo!","Digite modelo e descrição!",2);
        }
        this.setTitle(this.mostrarTituloDoFormulario());
    }

    private void gravarEdicaoRegistro() 
    {
        //setando os valores dos edits  
        modelo          = txtMODELO.getText();
        descricao       = txtDESCRICAO.getText();
        tipoid          = umabiblio.buscarCodigoGenerico("tbltipos", "tipo", cmbTIPO.getSelectedItem().toString());
        codigo          = Integer.parseInt(txtCODIGO.getText());
        
        umModModelo.setCodigo(codigo); 
        umModModelo.setModelo(modelo); 
        umModModelo.setTipoid(tipoid); 
        umModModelo.setDescricao(descricao); 

        //tratando alteraçao de contrato para S/N
        if (alterouContrato) 
        {
            String contrato = cmbCONTRATO.getSelectedItem().toString();
            if(contrato.equals("NÃO")){            
               umModModelo.setContrato("N");
            }else{
               umModModelo.setContrato("S"); 
            }
        }else{
            umModModelo.setContrato(deContrato);
        }        
        
        //tratando alteraçao de status 
        umModModelo.setStatus(cmbSTATUS.getSelectedItem().toString());
                
        if(umabiblio.ConfirmouOperacao("Confirma o desejo de gravar a as alterações deste modelo?","Confirmar edição!"))
        {
            CtrModelo.atualizarModelo(umModModelo);  
            //LimparTela();
            Leitura();
            umGravarLog.gravarLog("atualizacao do modelo "+umModModelo.getCodigo());
        }
    }  
    
    private void Leitura() 
    {
        umabiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 
        
        txtCODIGO.setText(null);
        txtMODELO.setText(null);
        txtDESCRICAO.setText(null);
        
           
        cmbCONTRATO.removeAllItems();      
        cmbCONTRATO.setSelectedIndex(-1);
        cmbCONTRATO.setEnabled(false);
        cmbTIPO.removeAllItems();     
        cmbTIPO.setSelectedIndex(-1);
        cmbTIPO.setEnabled(false);  
        cmbSTATUS.removeAllItems();     
        cmbSTATUS.setSelectedIndex(-1);   
        cmbSTATUS.setEnabled(false);
                
        btnNovo.setEnabled(true);
        btnGravar.setEnabled(false);
        btnVoltar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnLimparPesquisa.setEnabled(false);
        btnSair.setEnabled(true);  
        btnPesquisar.setEnabled(true); 
        
        txtCODIGO.setEditable(false);
        txtMODELO.setEditable(false);
        txtDESCRICAO.setEditable(false);  
        
        pesquisando     = false;
        alterouContrato = false;
        gravando        = false;
        PreencherTabela(sqlDefault); 
        
    }
    
    private void mostrarDados()
    {
        //pesquisar o modelo pelo codigo do modelo retornado apos a escolha do modelo        
        int codigoModelo = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0);
        //JOptionPane.showMessageDialog(null, "CODIGO DO MODELO SELECIONADO : "+codigoModelo);
        sql = "SELECT m.*,t.tipo FROM tblmodelos m, tbltipos t WHERE t.codigo=m.tipoid and m.codigo ="+codigoModelo+"";
            conexao.conectar();
            conexao.ExecutarPesquisaSQL(sql);
            try {
                conexao.rs.first();   
                txtCODIGO.setText(String.valueOf(conexao.rs.getInt("codigo"))); 
                txtMODELO.setText(conexao.rs.getString("modelo"));
                txtDESCRICAO.setText(conexao.rs.getString("descricao"));             

                //setando na combo tipo o tipo do modelo selecionado
                String tipo = conexao.rs.getString("tipo");
                cmbTIPO.setSelectedItem(tipo);   
                
                cmbSTATUS.setEditable(true);  //se nao habilitar ele nao mostra o ítem selecionado
                deStatus = conexao.rs.getString("status");
                cmbSTATUS.setSelectedItem(deStatus);                 
               
                deContrato = conexao.rs.getString("contrato");
                cmbCONTRATO.setEditable(true);    //se nao habilitar ele nao mostra o ítem selecionado
                if(deContrato.equals("S"))
                {
                    cmbCONTRATO.setSelectedItem("SIM");
                }else if(deContrato.equals("N")){
                    cmbCONTRATO.setSelectedItem("NÃO");
                }       
             } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Erro ao tentar selecionar o modelo!\nERRO:"+ex.getMessage());
            }finally{
                conexao.desconectar();
        }
           
    }
   
    private void filtrarPorDigitacao()   
    {
        //umabiblio.PreencherCombo(cmbTIPO, "tbltipos", "tipo");
        String pPesq = txtMODELO.getText();
        sql = "select m.*,t.* FROM tblmodelos m, tbltipos t where t.codigo=m.tipoid and m.modelo like '%"+pPesq+"%' and m.status='ATIVO'";
        PreencherTabela(sql);
        
    }       
       
    public void PreencherTabela(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Modelo", "Contrato"};
        try 
        {            
            conexao.ExecutarPesquisaSQL(sql);  
            while (conexao.rs.next())
            {   
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("modelo"),
                    conexao.rs.getString("contrato")                    
                });
                
            };

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabela.setModel(modelo);
            //define tamanho das colunas
            jTabela.getColumnModel().getColumn(0).setPreferredWidth(50);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabela.getColumnModel().getColumn(1).setPreferredWidth(880);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(1).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabela.getColumnModel().getColumn(2).setPreferredWidth(70);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(2).setResizable(false);    //nao será possivel redimencionar a coluna 

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
    private javax.swing.JButton btnLimparPesquisa;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<String> cmbCONTRATO;
    private javax.swing.JComboBox<String> cmbSTATUS;
    private javax.swing.JComboBox<String> cmbTIPO;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabela;
    private javax.swing.JTextField txtCODIGO;
    private javax.swing.JTextArea txtDESCRICAO;
    private javax.swing.JTextField txtMODELO;
    // End of variables declaration//GEN-END:variables
}