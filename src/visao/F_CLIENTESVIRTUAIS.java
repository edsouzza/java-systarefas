package visao;

import Dao.DAOCliente;
import Dao.DAOClienteVirtual;
import biblioteca.Biblioteca;
import biblioteca.GerarNumerosAleatorios;
import conexao.ConnConexao;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import static biblioteca.VariaveisPublicas.codigoSelecionado;
import static biblioteca.VariaveisPublicas.editando;
import static biblioteca.VariaveisPublicas.lstListaStrings;
import static biblioteca.VariaveisPublicas.tabela;
import controle.ControleGravarLog;
import controle.CtrlCliente;
import controle.CtrlClienteVirtual;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.Cliente;
import modelo.ClienteVirtual;

/**
 *
 * @author d631863
 * Este formulario serve para cadastrar todos os usuarios que voce desejar ex: SCANNER para todas as seções ficaria SCANNER DIVISAO / SCANNER BENS etc...
 * Ao cadastrar aqui serão gerados os clientes como no exemplo acima automaticamente na tabela TBLCLIENTES
 */
public class F_CLIENTESVIRTUAIS extends javax.swing.JFrame {
    ConnConexao                 conexao                 = new ConnConexao();
    Biblioteca                  umabiblio               = new Biblioteca();
    MetodosPublicos             umMetodo                = new MetodosPublicos();
    ClienteVirtual              umModClienteVirtual     = new ClienteVirtual();
    CtrlClienteVirtual          ctrClienteVirtual       = new CtrlClienteVirtual();    
    ControleGravarLog           umGravarLog             = new ControleGravarLog();
    DAOClienteVirtual           clienteVirtualDAO       = new DAOClienteVirtual();
    
    Cliente                     umModCliente            = new Cliente();
    CtrlCliente                 ctrCliente              = new CtrlCliente();
    DAOCliente                  clienteDAO              = new DAOCliente();    
   
    
    String nome, sql, status = "";   
    int codigo = 0;
    boolean gravando, clicouNaTabela;  //controla no botão gravar entre gravar novo registro e gravar alteração de um registro
    
    String sqlDefault = "select * from TBLCLIENTESVIRTUAIS order by nome";
    String sqlVazia   = "select * from TBLCLIENTESVIRTUAIS where codigo = 0";

    
    public F_CLIENTESVIRTUAIS() {
        initComponents();
        PreencherTabela(sqlDefault);         
        setResizable(false);   //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        Leitura();
                
        //configuracoes dos edits   
       //configuracoes dos edits 
        umMetodo.configurarCamposTextos(txtNOME);
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
        txtNOME = new javax.swing.JTextField();
        txtCODIGO = new javax.swing.JTextField();
        cmbStatus = new javax.swing.JComboBox<String>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblDESCRICAO = new javax.swing.JLabel();

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
            .addGroup(jBoxBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGravar, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jBoxBotoesLayout.setVerticalGroup(
            jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxBotoesLayout.createSequentialGroup()
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jBoxBotoes.setLayer(btnGravar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnVoltar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnSair, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnNovo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnEditar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jBoxDados.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxDados.setName("panelDados"); // NOI18N

        txtNOME.setEditable(false);
        txtNOME.setToolTipText("");

        txtCODIGO.setEditable(false);
        txtCODIGO.setForeground(new java.awt.Color(51, 51, 255));
        txtCODIGO.setName("txtCODIGO"); // NOI18N

        cmbStatus.setForeground(new java.awt.Color(51, 51, 255));
        cmbStatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbStatus.setEnabled(false);

        jLabel2.setText("STATUS");

        jLabel3.setText("CÓDIGO");

        lblDESCRICAO.setText("NOME");

        javax.swing.GroupLayout jBoxDadosLayout = new javax.swing.GroupLayout(jBoxDados);
        jBoxDados.setLayout(jBoxDadosLayout);
        jBoxDadosLayout.setHorizontalGroup(
            jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBoxDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addComponent(lblDESCRICAO)
                        .addGap(0, 308, Short.MAX_VALUE))
                    .addComponent(txtNOME))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap())
        );
        jBoxDadosLayout.setVerticalGroup(
            jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lblDESCRICAO))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNOME, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );
        jBoxDados.setLayer(txtNOME, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtCODIGO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(cmbStatus, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(lblDESCRICAO, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBoxDados)
                    .addComponent(jBoxBotoes)
                    .addComponent(jScrollPane2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBoxDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBoxBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(636, 526));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

     private void mostrarDados()
    {
        //AO CLICAR EM UM REGISTRO DA TABELA MOSTRAR OS DADOS NOS EDITS        
        int codigo = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0);
        codigoSelecionado = codigo;
        
        //seta o nivel de acesso do usuario ao clicar na tabela e mostra a seção do mesmo
         sql = "SELECT * FROM TBLCLIENTESVIRTUAIS WHERE codigo="+codigo+"";        
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);          
        try 
        {          
            conexao.rs.first(); 
            
            //mostrando os dados do registro selecionado nos edits
            txtCODIGO.setText(String.valueOf(conexao.rs.getInt("codigo")));
            txtNOME.setText(conexao.rs.getString("Nome")); 
            
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
            txtNOME.setEnabled(true);
            txtNOME.requestFocus();
            gravando = true;
            txtCODIGO.setText(String.valueOf(umMetodo.mostrarProximoCodigo(tabela)));
            PreencherTabela(sqlVazia);
            //popular a combo status com opcoes ATIVO pra manter inalterado e INATIVO caso deseje inativar
            cmbStatus.removeAllItems();
            cmbStatus.addItem("ATIVO");
        }
    }//GEN-LAST:event_btnNovoActionPerformed
    
    private void gravarRegistro() {
        //se digitou algo nos campos nome e rf        
        if (txtNOME.getText().length() > 0) 
        {
            //setando os valores dos edits 
            String nomeCadastro = "";
            String statusComp   = "";
            nome    = txtNOME.getText();              
            status  = "ATIVO";     
            
            umModClienteVirtual.setNome(nome);
            umModClienteVirtual.setStatus(status); 
            
            if (gravando)
            {
                if(!clienteVirtualDAO.RegistroDuplicado(umModClienteVirtual)) 
                { 
                    ctrClienteVirtual.salvarClienteVirtual(umModClienteVirtual);
                    
                    //1-GRAVEI O NOVO CLIENTE VIRTUAL
                    //2- CADASTRAR O NOVO CLIENTE VIRTUAL NA TABELA DE CLIENTES PARA TODAS AS SECOES  
                    
                    //pegar a lista de todas as secoes cadastradas e montar o nome do novo cliente
                    umMetodo.retornaValoresDeUmCampoStringDaTabelaParaUmaLista("nome", "tblsecoes");                    
                            
                    for(int i=0; i < lstListaStrings.size(); i++)
                    {
                        
                        nomeCadastro = nome+" "+lstListaStrings.get(i);
                        
                        Long n                      = new Long("0123456");                                       
                        GerarNumerosAleatorios rf   = new GerarNumerosAleatorios(n);                      
                        String rfCliente            = String.valueOf(rf.getNumeroAleatorioRF());      
                                                           
                       if(!umMetodo.temDuplicidadeDeCadastro("tblclientes", "nome", nomeCadastro))
                       {                        
                            umModCliente.setNome(nomeCadastro);
                            umModCliente.setRf(rfCliente);

                            //verificar o status da secao se ativo ou inativo
                            statusComp = umMetodo.retornarStatusDoRegistroPeloNome("tblsecoes",lstListaStrings.get(i));
                            //JOptionPane.showMessageDialog(null, "nome da secao : "+lstListaStrings.get(i)+ " - STATUS : "+statusComp);
                            
                            switch (statusComp) {
                                case "ATIVO":
                                    umModCliente.setStatus("ATIVO");
                                    break;
                                case "INATIVO":    
                                    umModCliente.setStatus("INATIVO");
                                    break;
                            }                                                       

                            int codSecao     = umMetodo.retornaCodigo("tblsecoes", "nome", lstListaStrings.get(i)); 
                            umModCliente.setSecaoid(codSecao);

                            int deptoid      = umMetodo.retornaCodigoDepto("tblsecoes", "nome", lstListaStrings.get(i));
                            umModCliente.setDeptoid(deptoid); 
                            
                            String tipo  = "N";
                            umModCliente.setTipo(tipo); 
                            
                            String obs  = "";  
                            umModCliente.setObs(obs);                        

                            //GRAVA UM NOVO CLIENTE NA TBLCLIENTES
                            ctrCliente.salvarClienteVirtual(umModCliente);      
                       }
                        
                    }                    
                    umGravarLog.gravarLog("cadastro do cliente virtual "+nome);
                }  
            }
        } else {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos para continuar!"); //se nao digitou nada no campo nome da seção
            txtNOME.requestFocus(); //foco no campo secao
        } 
        PreencherTabela(sqlDefault);   
        gravando = false;
    }

    private void editarRegistro() 
    {
        //setando os valores dos edits  
        codigo  = Integer.parseInt(txtCODIGO.getText());
        nome    = txtNOME.getText();              
        status  = cmbStatus.getSelectedItem().toString(); 
        
        umModClienteVirtual.setCodigo(codigo);
        umModClienteVirtual.setNome(nome); 
        umModClienteVirtual.setStatus(status); 
              
       if (!gravando){               
            clienteVirtualDAO.atualizarClienteVirtualDAO(umModClienteVirtual);
            JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
            umGravarLog.gravarLog("atualizacao no cadastro do cliente virtual "+umModClienteVirtual.getNome());                 
        } else {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos para continuar!"); //se nao digitou nada no campo nome da seção
            txtNOME.requestFocus(); //foco no campo secao
        } 
        Leitura();
        PreencherTabela(sqlDefault);
        //txtTIPO.setEditable(false);
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
        
        txtNOME.setEditable(true);
        
        btnNovo          .setEnabled(Habilitar);
        btnSair          .setEnabled(Habilitar);
        btnGravar        .setEnabled(!Habilitar);
        btnEditar        .setEnabled(!Habilitar);
        btnVoltar        .setEnabled(!Habilitar);      
                
        //preenchendo a tabela com os registros
        PreencherTabela(sqlDefault); 
        
        //mostrando o titulo com qde de registros cadastrados
        this.setTitle(umMetodo.mostrarTituloDoFormulario());
        
        txtNOME.setText("");
        txtCODIGO.setText("");
        txtNOME.setEditable(false);
        
        //desabilitando os edits para edicao
//        c = jBoxDados.getComponents();
//        for(int i=0; i<c.length; i++)
//        {
//            c[i].setEnabled(!Habilitar);  
//            umMetodo.limparTodosCampos(this);
//            txtNOME.requestFocus();
//        }                
        btnVoltar.setText("Voltar");
        btnNovo.setVisible(false);
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
        String[] Colunas = new String[]{"Código", "Nome Documento", "Status"};
        try 
        {  
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next())
            {
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("Nome"),
                    conexao.rs.getString("status")
                });
            }; 
               
                ModeloTabela modelo = new ModeloTabela(dados, Colunas);
                jTabela.setModel(modelo);
                //define tamanho das colunas
                jTabela.getColumnModel().getColumn(0).setPreferredWidth(80);  //define o tamanho da coluna
                jTabela.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
                jTabela.getColumnModel().getColumn(1).setPreferredWidth(320);
                jTabela.getColumnModel().getColumn(1).setResizable(false);  
                jTabela.getColumnModel().getColumn(2).setPreferredWidth(160);
                jTabela.getColumnModel().getColumn(2).setResizable(false);  
               
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
            java.util.logging.Logger.getLogger(F_CLIENTESVIRTUAIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_CLIENTESVIRTUAIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_CLIENTESVIRTUAIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_CLIENTESVIRTUAIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new F_CLIENTESVIRTUAIS().setVisible(true);
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabela;
    private javax.swing.JLabel lblDESCRICAO;
    private javax.swing.JTextField txtCODIGO;
    private javax.swing.JTextField txtNOME;
    // End of variables declaration//GEN-END:variables
}
