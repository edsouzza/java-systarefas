package visao;

import biblioteca.Biblioteca;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import conexao.ConnConexao;
import biblioteca.ModeloTabela;
import controle.CtrlInformacao;
import Dao.DAOInformacao;
import biblioteca.CampoTxtLimitadoPorQdeCaracteresUpperCase;
import static biblioteca.VariaveisPublicas.codigoSelecionado;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import javax.swing.ListSelectionModel;
import static biblioteca.VariaveisPublicas.tabela;
import controle.ControleGravarLog;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import modelo.Informacao;

/**
 *
 * FORMULARIO CRIADO PARA CADASTRO E PESQUISAS DE SENHAS DE ACESSOS NOS APLICATIVOS DA PMSP
 */
public class F_ACESSOS extends javax.swing.JFrame {
    ConnConexao conexao  = new ConnConexao();    
    Biblioteca              umabiblio           = new Biblioteca();
    Informacao              umModInformacao     = new Informacao();
    CtrlInformacao          umCtrInformacao     = new CtrlInformacao();
    ControleGravarLog       umGravarLog         = new ControleGravarLog();
    DAOInformacao           umDAOInfo           = new DAOInformacao();
    
    Boolean clicouNaTabela = false;
    int codigo, idSecaoRegSel, ind = 0;    
    boolean gravando,editando;  //controla no botão gravar entre gravar novo registro e gravar alteração de um registro
    String nome, rf, vinculo, obs, senha, instituicao, senhaescolhido, caminhoTXT, sql, status, linha, referencia, provimento = "";      
    String sqlDefault = "select * from TBLINFORMACOES order by instituicao";
    String sqlVazia   = "select * from TBLINFORMACOES where codigo = 0";

    
    public F_ACESSOS() {
        initComponents();        
        PreencherTabela(sqlDefault);         
        setResizable(false);   //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        Leitura();
                
        //configuracoes dos edits   
        umabiblio.configurarCamposTextos(txtPESQUISA);
        umabiblio.configurarCamposTextos(txtINSTITUICAO);
        umabiblio.configurarCamposTextos(txtCODIGO);
        txtOBS.setDocument(new CampoTxtLimitadoPorQdeCaracteresUpperCase(77));
       
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
       umabiblio.configurarBotoes(btnVoltar);
       umabiblio.configurarBotoes(btnSair);
       
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
        txtSENHA = new javax.swing.JTextField();
        txtCODIGO = new javax.swing.JTextField();
        cmbStatus = new javax.swing.JComboBox<String>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblDESCRICAO = new javax.swing.JLabel();
        txtINSTITUICAO = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtOBS = new javax.swing.JTextField();
        txtPESQUISA = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jTabela.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTabela);

        jBoxBotoes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_gravar.gif"))); // NOI18N
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
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jBoxBotoesLayout.setVerticalGroup(
            jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnNovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jBoxBotoes.setLayer(btnGravar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnVoltar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnSair, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnNovo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnEditar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jBoxDados.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxDados.setName("panelDados"); // NOI18N

        txtSENHA.setEditable(false);
        txtSENHA.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSENHA.setForeground(new java.awt.Color(255, 51, 51));
        txtSENHA.setToolTipText("");
        txtSENHA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSENHAKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSENHAKeyReleased(evt);
            }
        });

        txtCODIGO.setEditable(false);
        txtCODIGO.setForeground(new java.awt.Color(51, 51, 255));
        txtCODIGO.setName("txtCODIGO"); // NOI18N

        cmbStatus.setForeground(new java.awt.Color(51, 51, 255));
        cmbStatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbStatus.setEnabled(false);

        jLabel2.setText("STATUS");

        jLabel3.setText("CÓDIGO");

        lblDESCRICAO.setText("INSTITUICAO");

        txtINSTITUICAO.setEditable(false);
        txtINSTITUICAO.setToolTipText("");
        txtINSTITUICAO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtINSTITUICAOKeyPressed(evt);
            }
        });

        jLabel4.setText("SENHA");

        jLabel5.setText("OBSERVACOES");

        txtOBS.setEditable(false);
        txtOBS.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtOBS.setForeground(new java.awt.Color(0, 51, 255));
        txtOBS.setToolTipText("");

        txtPESQUISA.setEditable(false);
        txtPESQUISA.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPESQUISA.setForeground(new java.awt.Color(0, 51, 255));
        txtPESQUISA.setToolTipText("");
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
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jBoxDadosLayout.createSequentialGroup()
                                .addComponent(txtINSTITUICAO)
                                .addGap(9, 9, 9))
                            .addGroup(jBoxDadosLayout.createSequentialGroup()
                                .addComponent(lblDESCRICAO)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 169, Short.MAX_VALUE)))
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSENHA, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtOBS)
                    .addComponent(txtPESQUISA, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jBoxDadosLayout.setVerticalGroup(
            jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBoxDadosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtPESQUISA, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4))
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lblDESCRICAO))
                        .addGap(1, 1, 1)))
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtINSTITUICAO, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSENHA, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOBS, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jBoxDados.setLayer(txtSENHA, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtCODIGO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(cmbStatus, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(lblDESCRICAO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtINSTITUICAO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtOBS, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtPESQUISA, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jBoxBotoes)
                    .addComponent(jBoxDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jBoxDados, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBoxBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(681, 590));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

     private void mostrarDados()
    {
        //AO CLICAR EM UM REGISTRO DA TABELA MOSTRAR OS DADOS NOS EDITS        
        int codigo = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0);
        codigoSelecionado = codigo;
        
        //seta o nivel de acesso do usuario ao clicar na tabela e mostra a seção do mesmo
        sql = "SELECT * FROM TBLINFORMACOES WHERE codigo="+codigo+"";        
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);          
        try 
        {          
            conexao.rs.first(); 
            
            //mostrando os dados do registro selecionado nos edits
            txtCODIGO.setText(String.valueOf(conexao.rs.getInt("codigo")));
            txtINSTITUICAO.setText(conexao.rs.getString("instituicao")); 
            txtSENHA.setText(conexao.rs.getString("senha")); 
            txtOBS.setText(conexao.rs.getString("obs")); 
            
            //mostro o status
            cmbStatus.removeAllItems();
            cmbStatus.addItem(conexao.rs.getString("status"));
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar selecionar a seção!\nERRO:"+ex.getMessage());
        }finally{
             conexao.desconectar();
        }

        //controla apresentacao dos edits sem permitir edição
        txtCODIGO.setEnabled(true);
        txtCODIGO.setEditable(false);
        txtSENHA.setEnabled(true);
        txtSENHA.setEditable(false);
        txtINSTITUICAO.setEnabled(true);
        txtINSTITUICAO.setEditable(false);
        txtOBS.setEnabled(true);
        txtOBS.setEditable(false);
        txtINSTITUICAO.requestFocus();        
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
        txtPESQUISA    .setEnabled(false);
        txtPESQUISA    .setEditable(false);

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

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        //populando os combobox
        gravando = true;
        HabilitarDesabilitarBotoes(false);
        btnVoltar.setText("Cancelar");
        umabiblio.limparTodosCampos(jBoxDados);
        txtSENHA.setText(null);
        txtSENHA.setEditable(true);
        txtSENHA.setEnabled(true);
        txtOBS.setText(null);
        txtOBS.setEditable(true);
        txtOBS.setEnabled(true);
        txtINSTITUICAO.setText(null);
        txtINSTITUICAO.setEditable(true);
        txtINSTITUICAO.setEnabled(true);
        txtINSTITUICAO.requestFocus();
        if(gravando){cmbStatus.addItem("ATIVO");}
        txtCODIGO.setText(String.valueOf(umabiblio.mostrarProximoCodigo(tabela)));
        PreencherTabela(sqlVazia);        

    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        Edicao();
        gravando=false;
        //controla apresentacao dos edits
        txtSENHA.requestFocus();
        txtSENHA.setEditable(true);
        txtSENHA.setEnabled(true);
        txtOBS.requestFocus();
        txtOBS.setEditable(true);
        txtOBS.setEnabled(true);
        txtINSTITUICAO.requestFocus();
        txtINSTITUICAO.setEditable(true);
        txtINSTITUICAO.setEnabled(true);    
        btnGravar.setEnabled(true);        
        
        //popular a combo status com opcoes ATIVO pra manter inalterado e INATIVO caso deseje inativar
        cmbStatus.setEnabled(true);
        cmbStatus.removeAllItems();
        cmbStatus.addItem("ATIVO");
        cmbStatus.addItem("INATIVO");
        
        PreencherTabela(sqlVazia);

    }//GEN-LAST:event_btnEditarActionPerformed

    private void txtSENHAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSENHAKeyPressed
        //se teclar enter estando dentro do txtNOME vá para o campo do rf
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtOBS.requestFocus();
        }     
    }//GEN-LAST:event_txtSENHAKeyPressed

    private void txtSENHAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSENHAKeyReleased
       btnVoltar.setText("Cancelar");
       btnEditar.setEnabled(false);
    }//GEN-LAST:event_txtSENHAKeyReleased
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        umabiblio.tabelaVazia(tabela);
    }//GEN-LAST:event_formWindowOpened

    private void txtINSTITUICAOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtINSTITUICAOKeyPressed
        //se teclar enter estando dentro do txtNOME vá para o campo do rf
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtSENHA.requestFocus();
        }
    }//GEN-LAST:event_txtINSTITUICAOKeyPressed

    private void txtPESQUISAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyPressed
        //se teclar enter estando dentro da pesquisa limpar a pesquisa
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtPESQUISA.setText(null);
        }
    }//GEN-LAST:event_txtPESQUISAKeyPressed
    
     private void filtrarPorDigitacao(String pPesq) {
        //filtrando por digitação pelos trez campos nome rf ou secao
        PreencherTabela("select * from tblinformacoes where instituicao like '%" + pPesq + "%' order by instituicao");
    }
    
    private void txtPESQUISAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyReleased
        filtrarPorDigitacao(txtPESQUISA.getText());
    }//GEN-LAST:event_txtPESQUISAKeyReleased

    private void gravarRegistro() {
        //se digitou algo nos campos nome e rf        
        if ((txtINSTITUICAO.getText().length() > 0) || (txtSENHA.getText().length() > 0))
        {
            //setando os valores dos edits   
            senha          = txtSENHA.getText();              
            instituicao    = txtINSTITUICAO.getText();              
            obs            = txtOBS.getText();              
            status         = "ATIVO";      
            
            umModInformacao.setInstituicao(instituicao);
            umModInformacao.setSenha(senha); 
            umModInformacao.setStatus(status); 
            umModInformacao.setObs(obs); 
            
            if (gravando){
               
                umCtrInformacao.salvarInformacao(umModInformacao);                
                umGravarLog.gravarLog("cadastro da senha de "+instituicao);
               
            }
        } else {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos para continuar!"); //se nao digitou nada no campo nome da seção
            txtSENHA.requestFocus(); //foco no campo secao
        } 
        PreencherTabela(sqlDefault);   
        gravando = false;
    }

    private void editarRegistro() 
    {
        //setando os valores dos edits  
        codigo         = Integer.parseInt(txtCODIGO.getText());
        senha          = txtSENHA.getText();              
        instituicao    = txtINSTITUICAO.getText(); 
        obs            = txtOBS.getText();  
        status         = cmbStatus.getSelectedItem().toString();         
        
        umModInformacao.setCodigo(codigo);
        umModInformacao.setInstituicao(instituicao);
        umModInformacao.setSenha(senha); 
        umModInformacao.setStatus(status);
        umModInformacao.setObs(obs); 
              
       if (!gravando){               
            umCtrInformacao.atualizarInformacao(umModInformacao);            
            umGravarLog.gravarLog("atualizacao no cadastro de "+umModInformacao.getInstituicao());                 
        } else {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos para continuar!"); //se nao digitou nada no campo nome da seção
            txtSENHA.requestFocus(); //foco no campo secao
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
        
        //popular a combo status com opcoes ATIVO pra manter inalterado e INATIVO caso deseje inativar
        cmbStatus.setEnabled(true);
        cmbStatus.removeAllItems();
        cmbStatus.addItem("ATIVO");
        cmbStatus.addItem("INATIVO");
      
        txtCODIGO.setEditable(false);
        txtSENHA.setEditable(true);
        txtSENHA.setEnabled(true);
        txtINSTITUICAO.setEditable(true);
        txtINSTITUICAO.setEnabled(true);
        txtOBS.setEditable(true);
        txtOBS.setEnabled(true);
        txtSENHA.requestFocus();               
                                  
        //habilitando os dados
         c = jBoxDados.getComponents();
         for(int i=0; i<c.length; i++)
         {
            c[i].setEnabled(!Habilitar);
         }   
         
        //mostrando o titulo com qde de registros cadastrados
        this.setTitle(umabiblio.mostrarTituloDoFormulario()); 
        
        //preenchendo a tabela com os registros
        PreencherTabela(sqlDefault);          
        
    }
   
    public void Leitura()
    {
        //formatacao inicial dos botoes ao abrir o formulario
        boolean Habilitar = true;
        Component[]c      = null;
        gravando          = false;
        
        txtSENHA.setEditable(true);
        
        btnNovo          .setEnabled(Habilitar);
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
            txtPESQUISA.setEnabled(true);
            txtPESQUISA.setEditable(true);            
            txtPESQUISA.requestFocus();
        }
                       
        //mostrando o titulo com qde de registros cadastrados
        this.setTitle(umabiblio.mostrarTituloDoFormulario());
        
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
        String[] Colunas = new String[]{"Código", "Instituição", "Senha", "Obs"};
        try 
        {  
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next())
            {
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("instituicao"),
                    conexao.rs.getString("senha"),                    
                    conexao.rs.getString("obs")
                });
            }; 
               
                ModeloTabela modelo = new ModeloTabela(dados, Colunas);
                jTabela.setModel(modelo);
                //define tamanho das colunas
                jTabela.getColumnModel().getColumn(0).setPreferredWidth(50);  //define o tamanho da coluna
                jTabela.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
                jTabela.getColumnModel().getColumn(1).setPreferredWidth(160);
                jTabela.getColumnModel().getColumn(1).setResizable(false);  
                jTabela.getColumnModel().getColumn(2).setPreferredWidth(100);
                jTabela.getColumnModel().getColumn(2).setResizable(false);  
                jTabela.getColumnModel().getColumn(3).setPreferredWidth(265);
                jTabela.getColumnModel().getColumn(3).setResizable(false);  
                
               
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
            java.util.logging.Logger.getLogger(F_ACESSOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_ACESSOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_ACESSOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_ACESSOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new F_ACESSOS().setVisible(true);
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabela;
    private javax.swing.JLabel lblDESCRICAO;
    private javax.swing.JTextField txtCODIGO;
    private javax.swing.JTextField txtINSTITUICAO;
    private javax.swing.JTextField txtOBS;
    private javax.swing.JTextField txtPESQUISA;
    private javax.swing.JTextField txtSENHA;
    // End of variables declaration//GEN-END:variables
}
