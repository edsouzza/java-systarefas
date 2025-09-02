package visao;

//import Dao.DAOCliente;
import Dao.DAOEmpresa;
import biblioteca.Biblioteca;
import conexao.ConnConexao;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import static biblioteca.VariaveisPublicas.codigoSelecionado;
import static biblioteca.VariaveisPublicas.editando;
import static biblioteca.VariaveisPublicas.tabela;
import controle.ControleGravarLog;
import controle.CtrlEmpresa;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.Empresa;

/**
 *
 * @author d631863
 * Este formulario serve para cadastrar todos os usuarios que voce desejar ex: SCANNER para todas as seções ficaria SCANNER DIVISAO / SCANNER BENS etc...
 * Ao cadastrar aqui serão gerados os clientes como no exemplo acima automaticamente na tabela TBLCLIENTES
 */
public class F_EMPRESA extends javax.swing.JDialog  {
    ConnConexao                 conexao                 = new ConnConexao();
    Biblioteca                  umabiblio               = new Biblioteca();
    MetodosPublicos             umMetodo                = new MetodosPublicos();
    Empresa                     umModEmpresa            = new Empresa();
    CtrlEmpresa                 ctrEmpresa              = new CtrlEmpresa();    
    ControleGravarLog           umGravarLog             = new ControleGravarLog();
    DAOEmpresa                  umaEmpresaDAO           = new DAOEmpresa();
       
    
    String nome, sql, status, obs = "";   
    int codigo = 0;
    boolean gravando, clicouNaTabela;  //controla no botão gravar entre gravar novo registro e gravar alteração de um registro
    
    String sqlDefault = "select * from TBLEMPRESA order by codigo";
    String sqlVazia   = "select * from TBLEMPRESA where codigo = 0";

    
    public F_EMPRESA(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        PreencherTabela(sqlDefault);         
        setResizable(false);   //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        Leitura();                
   
       //configuracoes dos edits 
        umMetodo.configurarCamposTextos(txtNOME);
//        umMetodo.configurarCamposTextos(txtOBS);
        umMetodo.configurarCamposTextos(txtCODIGO);
        
        //Impede que formulario seja arrastado na tela
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                setEnabled(false);
                setEnabled(true);
            }
        });//fim addComponentListener       
                
      //configuração dos botões
      //configuração dos botões
       umMetodo.configurarBotoes(btnNovo);
       umMetodo.configurarBotoes(btnEditar);
       umMetodo.configurarBotoes(btnGravar);
       umMetodo.configurarBotoes(btnVoltar);
       umMetodo.configurarBotoes(btnSair);
       
       jTabela.setFont(new Font("Arial", Font.BOLD, 12)); 
       txtOBS.setFont(new Font("Arial", Font.BOLD, 12)); 
       
       txtOBS.setForeground(Color.blue);
       txtCODIGO.setForeground(Color.red);
      
    }
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTabela = new javax.swing.JTable();
        jBoxBotoes = new javax.swing.JLayeredPane();
        btnGravar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jBoxDados = new javax.swing.JLayeredPane();
        txtOBS = new javax.swing.JTextField();
        txtCODIGO = new javax.swing.JTextField();
        cmbStatus = new javax.swing.JComboBox<String>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblDESCRICAO = new javax.swing.JLabel();
        txtNOME = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("TIPOS DE EXPEDIENTES");

        jTabela.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTabela);

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
        btnEditar.setText("Editar");
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jBoxBotoesLayout = new javax.swing.GroupLayout(jBoxBotoes);
        jBoxBotoes.setLayout(jBoxBotoesLayout);
        jBoxBotoesLayout.setHorizontalGroup(
            jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBoxBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnGravar, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addGap(27, 27, 27)
                .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addContainerGap())
        );
        jBoxBotoesLayout.setVerticalGroup(
            jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jBoxBotoes.setLayer(btnGravar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnVoltar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnSair, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnNovo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnEditar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jBoxDados.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxDados.setName("panelDados"); // NOI18N

        txtOBS.setEditable(false);
        txtOBS.setToolTipText("");

        txtCODIGO.setEditable(false);
        txtCODIGO.setForeground(new java.awt.Color(51, 51, 255));
        txtCODIGO.setName("txtCODIGO"); // NOI18N

        cmbStatus.setForeground(new java.awt.Color(51, 51, 255));
        cmbStatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbStatus.setEnabled(false);

        jLabel2.setText("STATUS");

        jLabel3.setText("CÓDIGO");

        lblDESCRICAO.setText("NOME");

        txtNOME.setEditable(false);
        txtNOME.setToolTipText("");
        txtNOME.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNOMEKeyPressed(evt);
            }
        });

        jLabel4.setText("OBSERVACAO");

        javax.swing.GroupLayout jBoxDadosLayout = new javax.swing.GroupLayout(jBoxDados);
        jBoxDados.setLayout(jBoxDadosLayout);
        jBoxDadosLayout.setHorizontalGroup(
            jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxDadosLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtOBS)
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jBoxDadosLayout.createSequentialGroup()
                                .addComponent(lblDESCRICAO)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtNOME))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addContainerGap())
        );
        jBoxDadosLayout.setVerticalGroup(
            jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lblDESCRICAO))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNOME, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOBS, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jBoxDados.setLayer(txtOBS, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtCODIGO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(cmbStatus, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(lblDESCRICAO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtNOME, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jBoxBotoes)
                        .addComponent(jBoxDados)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBoxDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(jBoxBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(682, 526));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

     private void mostrarDados()
    {
        //AO CLICAR EM UM REGISTRO DA TABELA MOSTRAR OS DADOS NOS EDITS        
        int codigo = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0);
        codigoSelecionado = codigo;
        
        //seta o nivel de acesso do usuario ao clicar na tabela e mostra a seção do mesmo
         sql = "SELECT * FROM TBLEMPRESA WHERE codigo="+codigo+"";        
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);          
        try 
        {          
            conexao.rs.first(); 
            
            //mostrando os dados do registro selecionado nos edits
            txtCODIGO.setText(String.valueOf(conexao.rs.getInt("codigo")));
            txtNOME.setText(conexao.rs.getString("nome"));
            txtOBS.setText(conexao.rs.getString("obs")); 
            
            //mostro o status
            cmbStatus.removeAllItems();
            cmbStatus.addItem(conexao.rs.getString("status"));
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar selecionar o cliente virtual!\nERRO:"+ex.getMessage());
        }finally{
             conexao.desconectar();
        }

        //controla apresentacao dos edits sem permitir edição
        txtCODIGO.setEnabled(true);
        txtCODIGO.setEditable(false);
        txtOBS.setEnabled(true);
        txtOBS.setEditable(false);
        txtNOME.setEnabled(true);
        txtNOME.setEditable(false);
        txtNOME.requestFocus();
        txtNOME.selectAll();        
        cmbStatus.setEnabled(true);
        
    }
    
    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        //AO CLICAR EM UM REGISTRO DA TABELA MOSTRAR OS DADOS NOS EDITS
        mostrarDados();
        btnEditar      .setEnabled(true);
        btnGravar      .setEnabled(false);
        btnNovo        .setEnabled(false);
        btnVoltar      .setEnabled(true);
        btnSair        .setEnabled(false);

    }//GEN-LAST:event_jTabelaMouseClicked

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        if (gravando) {   
            gravarRegistro();
        } else {
            editarRegistro();
        }
        Leitura();

    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        Leitura();
    }//GEN-LAST:event_btnVoltarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if(umabiblio.permissaoLiberada()){
            Edicao();
            gravando=false;
            //controla apresentacao dos edits
            txtNOME.requestFocus();
            txtNOME.setEditable(true);
            txtNOME.setEnabled(true);
            txtOBS.setEditable(true);
            txtOBS.setEnabled(true);
            btnGravar.setEnabled(true);        

            //popular a combo status com opcoes ATIVO pra manter inalterado e INATIVO caso deseje inativar
            cmbStatus.setEnabled(true);
            cmbStatus.removeAllItems();
            cmbStatus.addItem("ATIVO");
            cmbStatus.addItem("INATIVO");

            PreencherTabela(sqlVazia);
        }

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        //populando os combobox
        if(umabiblio.permissaoLiberada()){
            HabilitarDesabilitarBotoes(false);
            btnVoltar.setText("Cancelar");
            umMetodo.limparTodosCampos(jBoxDados);
            txtNOME.setText(null);
            txtNOME.setEditable(true);
            txtNOME.requestFocus();
            gravando = true;
            txtCODIGO.setText(String.valueOf(umMetodo.mostrarProximoCodigo(tabela)));
            PreencherTabela(sqlVazia);
            //popular a combo status com opcoes ATIVO pra manter inalterado e INATIVO caso deseje inativar
            cmbStatus.removeAllItems();
            cmbStatus.addItem("ATIVO");
            txtOBS.setText("Empresa do novo contrato de alocacao de impressoras.");
            btnGravar.setEnabled(false);
        }
    }//GEN-LAST:event_btnNovoActionPerformed

    private void txtNOMEKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNOMEKeyPressed
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtOBS.requestFocus();
        }   
        btnGravar.setEnabled(true);
    }//GEN-LAST:event_txtNOMEKeyPressed
    
    private void gravarRegistro() {
        //se digitou algo nos campos nome      
        if (txtNOME.getText().length() > 0) 
        {
            //setando os valores dos edits 
            nome    = txtNOME.getText();              
            obs     = txtOBS.getText();     
            status  = "ATIVO";     
            
            umModEmpresa.setNome(nome);
            umModEmpresa.setObs(obs); 
            umModEmpresa.setStatus(status); 
            
            if (gravando)
            {
                if(!umaEmpresaDAO.RegistroDuplicado(umModEmpresa)) 
                { 
                    int qdeRegs = (umMetodo.getQdeRegistrosNaTabela("TblEmpresa"));
                    
                    //Ao cadastrar a nova empresa será inativada a empresa anterior automaticamente
                    if(qdeRegs >= 1){
                       String codigoAtual = umMetodo.getValorCampoUltimoCodigo("tblempresa", "codigo");
                       umaEmpresaDAO.inativarEmpresasInativasDAO(Integer.parseInt(codigoAtual));        
                    }    
                       ctrEmpresa.salvarEmpresa(umModEmpresa);
                }else{                    
                    umGravarLog.gravarLog("cadastro de empresa do novo contrato de impressoras "+nome);
                }  
            }
        } else {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos para continuar!!!","Cadastro da empresa do novo contrato de impressoras!",2);
            txtNOME.requestFocus(); 
        } 
        PreencherTabela(sqlDefault);   
        Leitura();        
        gravando = false;        
                    
    }

    private void editarRegistro() 
    {
        //setando os valores dos edits  
        codigo  = Integer.parseInt(txtCODIGO.getText());
        nome    = txtNOME.getText();              
        obs     = txtOBS.getText();              
        status  = cmbStatus.getSelectedItem().toString(); 
        
        umModEmpresa.setCodigo(codigo);
        umModEmpresa.setNome(nome); 
        umModEmpresa.setObs(obs); 
        umModEmpresa.setStatus(status); 
              
       if (!gravando){               
            umaEmpresaDAO.atualizarEmpresaDAO(umModEmpresa);
            JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
            umGravarLog.gravarLog("atualizacao no cadastro de empresa do contrato de impressoras "+umModEmpresa.getNome());                 
        } else {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos para continuar!"); //se nao digitou nada no campo nome da seção
            txtNOME.requestFocus(); //foco no campo secao
        } 
        Leitura();
        PreencherTabela(sqlDefault);
        gravando = false;
    }    
    
    private void Edicao()
    {
        //metodo para quando usuario clicar em Editar
        boolean Habilitar = true;
        Component[]c      = null;
        gravando          = false; 
        editando          = true;
        btnNovo          .setEnabled(!Habilitar);
        btnSair          .setEnabled(!Habilitar);
        btnGravar        .setEnabled(Habilitar);
        btnEditar        .setEnabled(!Habilitar);
        btnVoltar        .setEnabled(Habilitar);   

        btnVoltar.setText("Cancelar");                
      
        txtCODIGO.setEditable(false);
        txtNOME.setEditable(true);
        txtNOME.setEnabled(true);
        txtOBS.setEditable(true);
        txtOBS.setEnabled(true);
        txtNOME.requestFocus();     
        
        //mostrando o titulo com qde de registros cadastrados
        this.setTitle(umMetodo.mostrarTituloDoFormulario());
                                          
        //habilitando os dados
         c = jBoxDados.getComponents();
         for(int i=0; i<c.length; i++)
         {
            c[i].setEnabled(!Habilitar);
         }   
                         
        //preenchendo a tabela com os registros
        PreencherTabela(sqlDefault);          
        
    }
   
    public void Leitura()
    {
        //formatacao inicial dos botoes ao abrir o formulario
        boolean Habilitar = true;
        Component[]c      = null;
        gravando          = false;
        
        txtOBS.setEditable(true);
        txtNOME.setEditable(false);
        
        btnNovo          .setEnabled(Habilitar);
        btnSair          .setEnabled(Habilitar);
        btnGravar        .setEnabled(!Habilitar);
        btnEditar        .setEnabled(!Habilitar);
        btnVoltar        .setEnabled(!Habilitar);      
                
        //preenchendo a tabela com os registros
        PreencherTabela(sqlDefault); 
        
        //mostrando o titulo com qde de registros cadastrados
        this.setTitle(umMetodo.mostrarTituloDoFormulario());
        
        txtCODIGO.setText("");
        txtNOME.setText("");
        txtOBS.setText("");
        txtNOME.setEditable(false);              
        txtOBS.setEditable(false);                
        btnVoltar.setText("Voltar");
        cmbStatus.setEnabled(false);
        cmbStatus.setSelectedIndex(-1);
    }
    
     public void HabilitarDesabilitarBotoes(boolean Habilitar)
    {
        //ações para quando clicar em cada botão
        Component[]c = null;
        
        btnNovo          .setEnabled(Habilitar);
        btnGravar        .setEnabled(!Habilitar);
        btnEditar        .setEnabled(Habilitar);
        btnVoltar        .setEnabled(!Habilitar);
        btnSair          .setEnabled(Habilitar);    
               
    }
    
    public void PreencherTabela(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Nome", "Data Cadastro", "Status"};
        try 
        {  
            conexao.ExecutarPesquisaSQL(sql);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            while (conexao.rs.next())
            {
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("Nome"),
                    df.format(conexao.rs.getDate("datacad")),
                    conexao.rs.getString("status")
                });
            }; 
               
                ModeloTabela modelo = new ModeloTabela(dados, Colunas);
                jTabela.setModel(modelo);
                //define tamanho das colunas
                jTabela.getColumnModel().getColumn(0).setPreferredWidth(80);  //define o tamanho da coluna
                jTabela.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
                jTabela.getColumnModel().getColumn(1).setPreferredWidth(340);
                jTabela.getColumnModel().getColumn(1).setResizable(false);  
                jTabela.getColumnModel().getColumn(2).setPreferredWidth(100);
                jTabela.getColumnModel().getColumn(2).setResizable(false);  
                jTabela.getColumnModel().getColumn(3).setPreferredWidth(100);
                jTabela.getColumnModel().getColumn(3).setResizable(false);  
               
                //define propriedades da tabela
                jTabela.getTableHeader().setReorderingAllowed(false);        //nao podera ser reorganizada
                jTabela.setAutoResizeMode(jTabela.AUTO_RESIZE_OFF);          //nao será possivel redimencionar a tabela
                jTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
                
        } catch (Exception ex) {
            //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
            JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList!\nErro: " + ex.getMessage());
        }finally{
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
            java.util.logging.Logger.getLogger(F_EMPRESA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_EMPRESA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_EMPRESA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_EMPRESA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                F_EMPRESA dialog = new F_EMPRESA(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLayeredPane jBoxBotoes;
    private javax.swing.JLayeredPane jBoxDados;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabela;
    private javax.swing.JLabel lblDESCRICAO;
    private javax.swing.JTextField txtCODIGO;
    private javax.swing.JTextField txtNOME;
    private javax.swing.JTextField txtOBS;
    // End of variables declaration//GEN-END:variables
}
