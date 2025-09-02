package visao;

import Dao.DAOMemorando;
import biblioteca.Biblioteca;
import biblioteca.CampoTxtLimitadoPorQdeCaracteresUpperCase;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import controle.ControleListaMemorandos;
import controle.CtrlMemorando;
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
import modelo.Memorando;

public class F_MEMORANDOS extends javax.swing.JFrame {
    ConnConexao             conexao  = new ConnConexao();
    Biblioteca              umabiblio                   = new Biblioteca();
    Memorando               umModMemorando              = new Memorando();
    CtrlMemorando           CtrMemorando                = new CtrlMemorando();
    ControleListaMemorandos umCtrLista                  = new ControleListaMemorandos();
    ControleGravarLog       umGravarLog                 = new ControleGravarLog();
    DAOMemorando            memoDAO                     = new DAOMemorando();  
    MetodosPublicos         umMetodo                    = new MetodosPublicos();
    
    
    Boolean clicouNaTabela,clicouConcluidas             = false;
    String memorando,assunto,nomeCli,assuntoSelecioado  = null;    
    int codigo,idClienteRegSel,ind,codigoAssunto        = 0; 
    boolean gravando,pesquisando;  //controla no botão gravar entre gravar novo registro e gravar alteração de um registro
    String sqlDefault = "select * from tblmemorandos order by codigo";  
    
    public F_MEMORANDOS() {
        initComponents();
        Leitura();
        PreencherTabela(sqlDefault);      //abre o formulario mostrando todos os registro cadastrados na tabela 
        setResizable(false);          //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        this.setTitle(this.mostrarTituloDoFormulario());
        
        // Colocando enter para pular de campo 
        HashSet conj = new HashSet(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS)); 
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0)); 
        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj); 
        
        //configuracoes dos edits 
        //txtMEMORANDO.setDocument(new CampoLimitadoParaDigitacao(2266)); //limitar a qde de caracteres digitados no memorando
        txtMEMORANDO.setFont(new Font("TimesRoman",Font.BOLD,16)); 
        umabiblio.configurarCamposTextos(txtCODIGO);
        umabiblio.configurarCamposTextos(txtASSUNTO);  
        txtASSUNTO.setFont(new Font("TimesRoman",Font.BOLD,12)); 
        txtASSUNTO.setForeground(Color.red);
        txtASSUNTO.setDocument(new CampoTxtLimitadoPorQdeCaracteresUpperCase(100));

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
        txtMEMORANDO = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        txtASSUNTO = new javax.swing.JTextField();
        txtCODIGO = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnExclir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1035, 756));

        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 733));

        jTabela.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTabelaMouseEntered(evt);
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

        txtMEMORANDO.setColumns(20);
        txtMEMORANDO.setLineWrap(true);
        txtMEMORANDO.setRows(5);
        txtMEMORANDO.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtMEMORANDO);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 255));
        jLabel5.setText("DETALHES");

        txtASSUNTO.setForeground(new java.awt.Color(51, 51, 255));
        txtASSUNTO.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtASSUNTOFocusGained(evt);
            }
        });
        txtASSUNTO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtASSUNTOKeyReleased(evt);
            }
        });

        txtCODIGO.setForeground(new java.awt.Color(51, 51, 255));
        txtCODIGO.setToolTipText("CODIGO DA TAREFA");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 255));
        jLabel3.setText("CÓDIGO");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 255));
        jLabel4.setText("ASSUNTO");

        btnExclir.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnExclir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_Cancelar.gif"))); // NOI18N
        btnExclir.setText("Excluir");
        btnExclir.setToolTipText("");
        btnExclir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExclir.setEnabled(false);
        btnExclir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExclirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtCODIGO, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtASSUNTO)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExclir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtASSUNTO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExclir, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1050, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1076, 815));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void gravarInclusaoRegistro() {
        //se digitou algo nos campos nome e rf        
        if (txtASSUNTO.getText().length() > 0 ) 
        {
            //setando os valores dos edits  
            assunto           = txtASSUNTO.getText(); 
            memorando         = txtMEMORANDO.getText();
            umModMemorando.setAssunto(assunto);
            umModMemorando.setMemorando(memorando); 
            
            CtrMemorando.salvarMemorando(umModMemorando);  
            
            umGravarLog.gravarLog("cadastro de novo memorando");
            PreencherTabela(sqlDefault);   
            gravando = false;
        }else{
           JOptionPane.showMessageDialog(this, "Operação inválida o campo assunto não aceita um valor nulo!","Digite um assunto!",2);
        }
        this.setTitle(this.mostrarTituloDoFormulario());
    }

    private void gravarEdicaoRegistro() 
    {
        //setando os valores dos edits  
        assunto         = txtASSUNTO.getText();
        memorando       = txtMEMORANDO.getText();
        codigo          = Integer.parseInt(txtCODIGO.getText());
                
        umModMemorando.setAssunto(assunto); 
        umModMemorando.setMemorando(memorando); 
        umModMemorando.setCodigo(codigo);          
                
        if(umabiblio.ConfirmouOperacao("Confirma o desejo de gravar a as alterações do assunto selecionado?","Confirmar edição!"))
        {
            CtrMemorando.atualizarMemorando(umModMemorando);  
            PreencherTabela(sqlDefault);  
            txtASSUNTO.setEditable(false);
            txtMEMORANDO.setEditable(false);
            gravando = false;
            umGravarLog.gravarLog("atualizacao do assunto "+umModMemorando.getCodigo());

        }
    }  
    
    private void Leitura() {
        umabiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 
        btnNovo.setEnabled(true);
        btnGravar.setEnabled(false);
        btnVoltar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnExclir.setEnabled(false);
        btnSair.setEnabled(true);  
        txtCODIGO.setEditable(false);
        txtASSUNTO.setEditable(false);
        txtMEMORANDO.setEditable(false);  
        pesquisando = false;
    }
    
    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        if (gravando) {
            gravarInclusaoRegistro();
        } else {
            gravarEdicaoRegistro();
        }
        txtMEMORANDO.setText(null);
        btnEditar.setEnabled(false);
        btnVoltar.setEnabled(false);
        btnNovo.setEnabled(true);
        btnSair.setEnabled(true);
        btnGravar.setEnabled(false);
        txtASSUNTO.setEditable(false);
        txtMEMORANDO.setEditable(false);
        txtASSUNTO.setText(null);
        txtCODIGO.setText(null);
        pesquisando = false;
        btnPesquisar.setEnabled(true);
        btnLimparPesquisa.setEnabled(false);
        
    }//GEN-LAST:event_btnGravarActionPerformed
    private void voltar(){
        pesquisando = false;
        clicouConcluidas = false;
        txtASSUNTO.setText(null);
        txtASSUNTO.setEditable(false);
        txtCODIGO.setEditable(false);
        txtCODIGO.setText(null);
        txtMEMORANDO.setText(null);
        btnEditar.setEnabled(false);
        btnExclir.setEnabled(false);
        btnNovo.setEnabled(true);
        btnVoltar.setEnabled(false);
        btnVoltar.setText("Voltar");
        btnGravar.setEnabled(false);
        btnSair.setEnabled(true);
        btnPesquisar.setEnabled(true);
        txtMEMORANDO.setText(null);
        this.setTitle(this.mostrarTituloDoFormulario());
        LimparPesquisa();
    }
    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        voltar();
    }//GEN-LAST:event_btnVoltarActionPerformed
        
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
     
        pesquisando = false;
        btnNovo.setEnabled(false);
        btnEditar.setEnabled(false);
        btnSair.setEnabled(false);
        btnPesquisar.setEnabled(false);
        btnVoltar.setEnabled(true);
        btnVoltar.setText("Cancelar");
        btnGravar.setEnabled(true);
        txtASSUNTO.setEditable(true);
        txtMEMORANDO.setEditable(true);
        txtASSUNTO.requestFocus();
        txtCODIGO.setText(null);
        gravando = true;
        txtCODIGO.setText(String.valueOf(umabiblio.mostrarProximoCodigo(tabela)));
     
               
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if(umabiblio.permissaoLiberada()){  
            pesquisando = false;
            btnGravar.setEnabled(true);
            btnEditar.setEnabled(false);
            txtASSUNTO.setEditable(true);
            txtMEMORANDO.setEditable(true);
            txtASSUNTO.requestFocus();
            txtASSUNTO.selectAll();//selecionando todo o texto pra edição
            btnVoltar.setText("Cancelar");
            btnPesquisar.setEnabled(false);
            btnLimparPesquisa.setEnabled(false);
        }

    }//GEN-LAST:event_btnEditarActionPerformed
    
    private void txtASSUNTOFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtASSUNTOFocusGained
        
        
    }//GEN-LAST:event_txtASSUNTOFocusGained
    private void mostrarDados(){
        //pesquisar o assunto pelo codigo do assunto retornado apos a escolha do assunto
        codigoAssunto = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0);
        assuntoSelecioado = (String) jTabela.getValueAt(jTabela.getSelectedRow(), 1);
        
        sql = "SELECT * FROM "+tabela+" WHERE codigo ="+codigoAssunto+" order by codigo";
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        try {
            conexao.rs.first();   
            txtCODIGO.setText(String.valueOf(conexao.rs.getInt("codigo"))); 
            txtASSUNTO.setText(conexao.rs.getString("assunto"));
            txtMEMORANDO.setText(conexao.rs.getString("memorando"));    
            
         } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar selecionar o assunto!\nERRO:"+ex.getMessage());
        }finally{
            conexao.desconectar();
        }
    }
    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        //AO CLICAR EM UM REGISTRO DA TABELA MOSTRAR OS DADOS NOS EDITS
        clicouNaTabela = true;
        mostrarDados();
        btnEditar      .setEnabled(true);
        btnGravar      .setEnabled(false);
        btnNovo        .setEnabled(false);
        btnVoltar      .setEnabled(true);
        btnExclir      .setEnabled(true);
        btnSair        .setEnabled(false);
        btnPesquisar   .setEnabled(false);
        btnLimparPesquisa.setEnabled(false);
        txtASSUNTO.setEditable(false);
        txtMEMORANDO.setEditable(false);
        txtMEMORANDO.setCaretPosition(0); //setando a primeira linha do JTextArea 
        txtMEMORANDO.requestFocus();

    }//GEN-LAST:event_jTabelaMouseClicked

    private void jTabelaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseEntered

    }//GEN-LAST:event_jTabelaMouseEntered

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        txtASSUNTO.setEditable(true);
        txtASSUNTO.requestFocus();
        pesquisando = true;
        btnLimparPesquisa.setEnabled(true);
        btnPesquisar.setEnabled(false);
        btnNovo.setEnabled(false);
    }//GEN-LAST:event_btnPesquisarActionPerformed
    private void filtrarPorDigitacao()   
    {
        String pPesq = txtASSUNTO.getText();
        PreencherTabela("SELECT codigo,assunto from tblmemorandos where assunto like '%"+pPesq+"%'");
                   
    }
    private void txtASSUNTOKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtASSUNTOKeyReleased
        if (pesquisando)
            filtrarPorDigitacao();
    }//GEN-LAST:event_txtASSUNTOKeyReleased
    private void LimparPesquisa(){
            PreencherTabela(sqlDefault);   
            pesquisando = false;
            txtASSUNTO.setText(null);
            txtMEMORANDO.setText(null);
            txtASSUNTO.setEditable(false);
            txtMEMORANDO.setEditable(false);
            btnPesquisar.setEnabled(true);
    }
    private void btnLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparPesquisaActionPerformed
        btnLimparPesquisa.setEnabled(false);
        LimparPesquisa();
        voltar();
    }//GEN-LAST:event_btnLimparPesquisaActionPerformed
        
    private void btnExclirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExclirActionPerformed
        String codigoExclusao = String.valueOf(codigoAssunto);
        
        if (umMetodo.ConfirmouOperacao("Confirma a exclusão do memorando  [ "+assuntoSelecioado+" ]?", "Exclusão do memorando de numero "+codigoExclusao)){
           umMetodo.deletarRegistrosConformeString("TBLMEMORANDOS", "codigo", codigoExclusao);
           JOptionPane.showMessageDialog(null, "O memorando de número "+codigoExclusao+" foi excluído com sucesso");
           PreencherTabela(sqlDefault);  
           Leitura();
        }
    }//GEN-LAST:event_btnExclirActionPerformed
    
    public void PreencherTabela(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Assunto"};
        try 
        {            
            conexao.ExecutarPesquisaSQL(sql);  
            while (conexao.rs.next())
            {   
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("assunto")
                    
                });
                
            };

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabela.setModel(modelo);
            //define tamanho das colunas
            jTabela.getColumnModel().getColumn(0).setPreferredWidth(50);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabela.getColumnModel().getColumn(1).setPreferredWidth(940);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(1).setResizable(false);    //nao será possivel redimencionar a coluna 

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
    private javax.swing.JButton btnExclir;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnLimparPesquisa;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabela;
    private javax.swing.JTextField txtASSUNTO;
    private javax.swing.JTextField txtCODIGO;
    private javax.swing.JTextArea txtMEMORANDO;
    // End of variables declaration//GEN-END:variables
}
