package visao;

import Dao.DAOCliente;
import Dao.DAOClienteVirtual;
import Dao.DAOTipo;
import biblioteca.Biblioteca;
import biblioteca.GerarNumerosAleatorios;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import biblioteca.TudoMaiusculas;
import biblioteca.VariaveisPublicas;
import static biblioteca.VariaveisPublicas.cadastrando;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.codigoSelecionado;
import static biblioteca.VariaveisPublicas.lstListaCampos;
import static biblioteca.VariaveisPublicas.lstListaStrings;
import static biblioteca.VariaveisPublicas.lstListaStringsAuxiliar;
import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.tabela;
import controle.ControleGravarLog;
import controle.CtrlCliente;
import controle.CtrlClienteVirtual;
import controle.CtrlTipo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.Cliente;
import modelo.ClienteVirtual;
import modelo.Tipo;
import relatorios.GerarExcelTable;

/**
 *
 * @author d631863
 */
public class F_TIPOS extends javax.swing.JFrame {

    ConnConexao conexao = new ConnConexao();
    Biblioteca umabiblio = new Biblioteca();
    MetodosPublicos umMetodo = new MetodosPublicos();
    Tipo umModTipo = new Tipo();
    CtrlTipo ctrTipo = new CtrlTipo();
    ControleGravarLog umGravarLog = new ControleGravarLog();
    DAOTipo tipoDAO = new DAOTipo();

    Cliente umModCliente = new Cliente();
    CtrlCliente ctrCliente = new CtrlCliente();
    DAOCliente clienteDAO = new DAOCliente();

    ClienteVirtual umModClienteVirtual = new ClienteVirtual();
    CtrlClienteVirtual ctrClienteVirtual = new CtrlClienteVirtual();
    DAOClienteVirtual clienteVirtualDAO = new DAOClienteVirtual();

    String tipo, status, tipopatrimonio, oldTipoInicial, oldStatusInicial, temip;
    int codigo, codigoClienteVirtualParaAlteracao;
    boolean gravando, pesquisando, editando, alterouNome, alterouStatus;
    String sqlDefault = "select * from tbltipos order by tipo";
    String sqlVazia   = "select * from tbltipos where codigo = 0";

    public F_TIPOS() {
        initComponents();
        PreencherTabela(sqlDefault);
        setResizable(false);   //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        Leitura();
        //configuracoes dos edits          
        umabiblio.configurarCamposTextos(txtCODIGO);
        txtCODIGO.setForeground(Color.red);
        txtTIPO.setForeground(Color.blue);
        txtTIPO.setFont(new Font("Arial", Font.BOLD, 12));
        txtTIPO.setDocument(new TudoMaiusculas());

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
        umabiblio.configurarBotoes(btnGerarExcel);
        jTabela.setFont(new Font("Arial", Font.BOLD, 12));

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
        btnGerarExcel = new javax.swing.JButton();
        jBoxDados = new javax.swing.JLayeredPane();
        txtTIPO = new javax.swing.JTextField();
        txtCODIGO = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lblDESCRICAO = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cmbTipoPatrimonio = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        cmbTemIP = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();

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

        btnGerarExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_imprimir.gif"))); // NOI18N
        btnGerarExcel.setText("Gerar Excel");
        btnGerarExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGerarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarExcelActionPerformed(evt);
            }
        });

        jBoxBotoes.setLayer(btnGravar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnVoltar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnSair, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnNovo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnEditar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnGerarExcel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jBoxBotoesLayout = new javax.swing.GroupLayout(jBoxBotoes);
        jBoxBotoes.setLayout(jBoxBotoesLayout);
        jBoxBotoesLayout.setHorizontalGroup(
            jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGerarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jBoxBotoesLayout.setVerticalGroup(
            jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGerarExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBoxDados.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxDados.setName("panelDados"); // NOI18N

        txtTIPO.setEditable(false);
        txtTIPO.setToolTipText("");
        txtTIPO.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTIPOFocusGained(evt);
            }
        });
        txtTIPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTIPOKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTIPOKeyReleased(evt);
            }
        });

        txtCODIGO.setEditable(false);
        txtCODIGO.setForeground(new java.awt.Color(51, 51, 255));
        txtCODIGO.setName("txtCODIGO"); // NOI18N

        jLabel3.setText("CÓDIGO");

        lblDESCRICAO.setText("TIPO");

        jLabel4.setText("TIPO DE PATRIMONIO");

        cmbTipoPatrimonio.setForeground(new java.awt.Color(51, 51, 255));
        cmbTipoPatrimonio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbTipoPatrimonio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTipoPatrimonioActionPerformed(evt);
            }
        });

        jLabel2.setText("STATUS");

        cmbStatus.setForeground(new java.awt.Color(51, 51, 255));
        cmbStatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        cmbTemIP.setForeground(new java.awt.Color(51, 51, 255));
        cmbTemIP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel6.setText("TEM IP");

        jBoxDados.setLayer(txtTIPO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(txtCODIGO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(lblDESCRICAO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(cmbTipoPatrimonio, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(cmbStatus, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(cmbTemIP, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxDados.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jBoxDadosLayout = new javax.swing.GroupLayout(jBoxDados);
        jBoxDados.setLayout(jBoxDadosLayout);
        jBoxDadosLayout.setHorizontalGroup(
            jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBoxDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDESCRICAO)
                    .addComponent(txtTIPO, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(cmbTipoPatrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbTemIP, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap())
        );
        jBoxDadosLayout.setVerticalGroup(
            jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBoxDadosLayout.createSequentialGroup()
                .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbTipoPatrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbTemIP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jBoxDadosLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jBoxDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jBoxDadosLayout.createSequentialGroup()
                                .addComponent(lblDESCRICAO)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTIPO, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jBoxDadosLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCODIGO, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBoxBotoes)
                    .addComponent(jBoxDados)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBoxDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBoxBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(864, 640));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mostrarDados() {
        //AO CLICAR EM UM REGISTRO DA TABELA MOSTRAR OS DADOS NOS EDITS        
        int codigo = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0);
        codigoSelecionado = codigo;

        //seta o nivel de acesso do usuario ao clicar na tabela e mostra a seção do mesmo
        sql = "SELECT * FROM tbltipos WHERE codigo=" + codigo + "";
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        try {
            conexao.rs.first();

            //mostrando os dados do registro selecionado nos edits
            txtCODIGO.setText(String.valueOf(conexao.rs.getInt("codigo")));
            txtTIPO.setText(conexao.rs.getString("tipo"));

            //mostro o status
            cmbStatus.removeAllItems();
            cmbStatus.addItem(conexao.rs.getString("status"));

            //mostro o tipo de patrimonio
            cmbTipoPatrimonio.removeAllItems();
            cmbTipoPatrimonio.addItem(conexao.rs.getString("tipopatrimonio"));
            
             //mostro se o tipo tem ip
            cmbTemIP.removeAllItems();
            cmbTemIP.addItem(conexao.rs.getString("temip"));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar selecionar a seção!\nERRO:" + ex.getMessage());
        } finally {
            conexao.desconectar();
        }

        //controla apresentacao dos edits sem permitir edição
        txtCODIGO.setEnabled(true);
        txtCODIGO.setEditable(false);
        txtTIPO.setEnabled(true);
        txtTIPO.setEditable(false);
        txtTIPO.requestFocus();
        cmbStatus.setEnabled(true);
        cmbTipoPatrimonio.setEnabled(true);
        btnGerarExcel.setEnabled(false);

        //setando os valores iniciais
        oldTipoInicial = txtTIPO.getText();
        oldStatusInicial = cmbStatus.getSelectedItem().toString();

        codigoClienteVirtualParaAlteracao = umMetodo.getCodigoPassandoString("TBLCLIENTESVIRTUAIS", "nome", txtTIPO.getText());
     

    }

    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        //AO CLICAR EM UM REGISTRO DA TABELA MOSTRAR OS DADOS NOS EDITS
        mostrarDados();
        btnEditar.setEnabled(true);
        btnGravar.setEnabled(false);
        btnNovo.setEnabled(false);
        btnVoltar.setEnabled(true);
        btnSair.setEnabled(false);
        cmbTipoPatrimonio.setEnabled(false);
        cmbStatus.setEnabled(false);

    }//GEN-LAST:event_jTabelaMouseClicked

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        if (gravando) {   
            gravarRegistro();                           
        } else if(editando){
            gravarEdicaoRegistro();
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

        if (umabiblio.permissaoLiberada()) {
            //popular a combo com opcoes 
            txtTIPO.setText(null);
            txtTIPO.setEditable(true);
            txtTIPO.setEnabled(true);
            txtTIPO.requestFocus();
            HabilitarDesabilitarBotoes(false);
            btnVoltar.setText("Cancelar");
            umabiblio.limparTodosCampos(jBoxDados);
            gravando = true;
            txtCODIGO.setText(String.valueOf(umabiblio.mostrarProximoCodigo(tabela)));
            PreencherTabela(sqlVazia);
            
        }

    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (umabiblio.permissaoLiberada()) {
            Edicao();
            gravando = false;
            editando = true;
            
            //controla apresentacao dos edits
            txtTIPO.requestFocus();
            txtTIPO.setEditable(true);
            txtTIPO.setEnabled(true);
            btnGravar.setEnabled(true);
            btnGerarExcel.setEnabled(false);

            //popular a combo status com opcoes ATIVO pra manter inalterado e INATIVO caso deseje inativar
            cmbStatus.setEnabled(true);
            cmbStatus.removeAllItems();
            cmbStatus.addItem("ATIVO");
            cmbStatus.addItem("INATIVO");
            
            //popular a combo temip com opcoes 
            cmbTemIP.setEnabled(true);
            cmbTemIP.removeAllItems();
            cmbTemIP.addItem("NAO");
            cmbTemIP.addItem("SIM");

            PreencherTabela(sqlVazia);
            cmbTipoPatrimonio.setEnabled(false);
        }

    }//GEN-LAST:event_btnEditarActionPerformed

    private void filtrarPorDigitacao() {
        //umabiblio.PreencherCombo(cmbTIPO, "tbltipos", "tipo");
        String pPesq = txtTIPO.getText();
        sql = "SELECT * FROM tbltipos WHERE tipo like '%" + pPesq + "%' and status='ATIVO'";
        PreencherTabela(sql);

    }

    private void txtTIPOKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTIPOKeyReleased

        if (!gravando || !editando) {
            filtrarPorDigitacao();
            btnNovo.setEnabled(false);
            btnEditar.setEnabled(false);
            btnGerarExcel.setEnabled(false);
            btnVoltar.setEnabled(true);
        } else {
            btnVoltar.setText("Cancelar");
            btnEditar.setEnabled(false);
        }

    }//GEN-LAST:event_txtTIPOKeyReleased

    private void btnGerarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarExcelActionPerformed
        /*retorna todos os campos da tabela ideal quando tiver chaves estrangeiras na tabela você passa a SQL completa dos dados
         importante salientar que não podem haver dados nulos nos registros */

        if (umabiblio.tabelaVazia("tbltipos")) {
            JOptionPane.showMessageDialog(null, "Por enquanto a tabela de tipos esta vazia!", "Cadastre o primeiro registro!", 2);
        } else {

            GerarExcelTable excel = new GerarExcelTable();

            sql = "SELECT * from tbltipos";

            lstListaCampos.clear();
            umMetodo.preencherArrayListComCampos(lstListaCampos, sql);

            ArrayList<Object[]> dataList = excel.getListaDados(sql);
            if (dataList != null && dataList.size() > 0) {
                excel.doExportar(dataList);
            } else {
                JOptionPane.showMessageDialog(null, "Não há dados disponíveis na tabela para exportacao, operação cancelada!", "Erro Fatal, verifique a SQL!", 2);
            }
            umGravarLog.gravarLog("impressao de relatorio : " + umMetodo.retornaNomeTabela(tabela));
        }

    }//GEN-LAST:event_btnGerarExcelActionPerformed

    private void verificaTabelaVazia() {
        if (umabiblio.tabelaVazia("tbltipos")) {
            btnGerarExcel.setEnabled(false);
        }
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        verificaTabelaVazia();
    }//GEN-LAST:event_formWindowOpened

    private void txtTIPOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTIPOKeyPressed
        //se teclar enter estando dentro da pesquisa limpar a pesquisa
        if (pesquisando) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                //txtTIPO.setText(null); 
                JOptionPane.showMessageDialog(null, "Preencha os campos e selecione as opções para continuar!", "Ação incorreta!", 2);
                btnVoltarActionPerformed(null);
            }
        }
        if(gravando)
        {
            cmbTipoPatrimonio.setEnabled(true);
            cmbTemIP.setEnabled(true);
            cmbTemIP.setSelectedIndex(0);
            cmbStatus.addItem("ATIVO");
           
        }
    }//GEN-LAST:event_txtTIPOKeyPressed


    private void cmbTipoPatrimonioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTipoPatrimonioActionPerformed
       
    }//GEN-LAST:event_cmbTipoPatrimonioActionPerformed

    private void txtTIPOFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTIPOFocusGained

    }//GEN-LAST:event_txtTIPOFocusGained

    private void gravarClientesVirtualIndividual() {
        //se digitou algo nos campos nome e rf        
        if (txtTIPO.getText().length() > 0) {
            //setando os valores dos edits 
            String nomeCadastro = "";
            String statusComp   = "";
            tipo                = txtTIPO.getText();
            status              = "ATIVO";

            umModClienteVirtual.setNome(tipo);
            umModClienteVirtual.setStatus(status);

            if (gravando) {
                if (!clienteVirtualDAO.RegistroDuplicado(umModClienteVirtual)) {
                    ctrClienteVirtual.salvarClienteVirtual(umModClienteVirtual);
                    
//                    if (cmbClientesVirtuais.getSelectedItem().toString().equals("SIM")) {
//                        gravarClientesVirtuaisColetivo();
//                    }
                    
                    umGravarLog.gravarLog("cadastro do cliente virtual " + tipo);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos para continuar!"); //se nao digitou nada no campo nome da seção
            txtTIPO.requestFocus(); //foco no campo secao
        }
    }

    private void gravarClientesVirtuaisColetivo() {
        //se digitou algo nos campos nome e rf        
        if (txtTIPO.getText().length() > 0) {
            //setando os valores dos edits 
            String nomeCadastro = "";
            String statusComp   = "";
            int codigo          = Integer.parseInt(txtCODIGO.getText());
            tipo                = txtTIPO.getText();
            status              = "ATIVO";

            umModClienteVirtual.setNome(tipo);
            umModClienteVirtual.setStatus(status);

            if (gravando) {
                    //CADASTRAR O NOVO CLIENTE VIRTUAL NA TABELA DE CLIENTES PARA TODAS AS SECOES  

                //pegar a lista de todas as secoes cadastradas e montar o nome do novo cliente
                umMetodo.retornaValoresDeUmCampoStringDaTabelaParaUmaLista("nome", "tblsecoes");

                for (int i = 0; i < lstListaStrings.size(); i++) {

                    nomeCadastro = tipo + " " + lstListaStrings.get(i);

                    Long n = new Long("0123456");
                    GerarNumerosAleatorios rf = new GerarNumerosAleatorios(n);
                    String rfCliente = String.valueOf(rf.getNumeroAleatorioRF());

                    if (!umMetodo.temDuplicidadeDeCadastro("tblclientes", "nome", nomeCadastro)) {
                        umModCliente.setNome(nomeCadastro);
                        umModCliente.setRf(rfCliente);

                        //verificar o status da secao se ativo ou inativo
                        statusComp = umMetodo.retornarStatusDoRegistroPeloNome("tblsecoes", lstListaStrings.get(i));
                            //JOptionPane.showMessageDialog(null, "nome da secao : "+lstListaStrings.get(i)+ " - STATUS : "+statusComp);

                        switch (statusComp) {
                            case "ATIVO":
                                umModCliente.setStatus("ATIVO");
                                break;
                            case "INATIVO":
                                umModCliente.setStatus("INATIVO");
                                break;
                        }

                        int codSecao = umMetodo.retornaCodigo("tblsecoes", "nome", lstListaStrings.get(i));
                        umModCliente.setSecaoid(codSecao);

                        int deptoid = umMetodo.retornaCodigoDepto("tblsecoes", "nome", lstListaStrings.get(i));
                        umModCliente.setDeptoid(deptoid);

                        umModCliente.setTipovirtualid(codigo);

                        String tipo = "N";
                        umModCliente.setTipo(tipo);

                        String obs = "";
                        umModCliente.setObs(obs);

                        //GRAVA UM NOVO CLIENTE NA TBLCLIENTES
                        ctrCliente.salvarClienteVirtual(umModCliente);
                    }

                    umGravarLog.gravarLog("cadastro do cliente virtual " + tipo);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos para continuar!"); //se nao digitou nada no campo nome da seção
            txtTIPO.requestFocus(); //foco no campo secao
        }
        gravando = false;
    }

    private void gravarRegistro() {
        //se digitou algo nos campos   
        if (txtTIPO.getText().length() > 0) {
            //setando os valores dos edits   
            tipo           = txtTIPO.getText();
            status         = "ATIVO";
            temip          = cmbTemIP.getSelectedItem().toString();
            tipopatrimonio = cmbTipoPatrimonio.getSelectedItem().toString();
            
            umModTipo.setTipo(tipo);
            umModTipo.setStatus(status);
            umModTipo.setTemIp(temip);
            umModTipo.setTipopatrimonio(tipopatrimonio);

            if (gravando) {
                if (!tipoDAO.RegistroDuplicado(umModTipo)) //se nao estiver duplicado libera a gravaçao do registro
                {
                    ctrTipo.salvarTipo(umModTipo);

                    //verificando :  se for PATRIMOVEL não cadastra cliente virtual
                    //if (cmbTipoPatrimonio.getSelectedItem().toString().equals("PATRIMONIO")) {
                        gravarClientesVirtualIndividual();
                    //}

                    //se for cadastrar PATRIMONIO com CLIENTE VIRTUAL, ele já cadastrou o individual acima e agora vai cadastrar os coletivos de cada setor abaixo
//                    if (cmbTipoPatrimonio.getSelectedItem().toString().equals("PATRIMONIO") && cmbClientesVirtuais.getSelectedItem().toString().equals("SIM")) {
//                        gravarClientesVirtuaisColetivo();
//                    }

                    umGravarLog.gravarLog("cadastro de " + umModTipo.getTipo());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos para continuar!"); //se nao digitou nada no campo nome da seção
            txtTIPO.requestFocus(); //foco no campo secao
        }
        PreencherTabela(sqlDefault);
        gravando = false;

    }

    private void AlterarStatusClienteVirtualIndividual(int pCod, String pStatus) {
        //agora é so gravar a alteracao na tblclientes
        ctrClienteVirtual.atualizarStatusClienteVirtual(pCod, pStatus);    
    }
    
    private void AlterarNomeClienteVirtualIndividual(int pCod, String pNome) {
        //quando mudar o nome do tipo gravar a alteracao tambem na tblclientesvirtuais
        if (!umMetodo.temDuplicidadeDeCadastro("tblclientes", "nome", pNome)) {
            ctrClienteVirtual.atualizarNomeClienteVirtual(pCod, pNome);
        }
    }

    private void AlterarTodosNomesClientesVirtuais(int pCod) {
        //se o tipo tiver clientes virtuais dos setores todos eles terão alteração no seu nome passando como parametro o CODIGO DO TIPO
        
        //este metodo retorna todos os nomes de secoes cadastrados em uma lista(lstListaStrings) para gerar o novo nome (novoNomeTipo = tipo + " " + lstListaStrings.get(i))
        umMetodo.retornaValoresDeUmCampoStringDaTabelaParaUmaLista("nome", "tblsecoes");
        
       //este metodo retorna todos os CODIGOS dos registros que serão alterados em uma lista(lstListaStringsAuxiliar) e que contem cliente virtual dos setores
        umMetodo.getCodigosRegistrosParaUmaLista(pCod);

//        verificando os nomes colocados dentro da lista lstListaStrings
//        for(int i=0; i < lstListaStrings.size(); i++)
//        {
//            System.out.println(lstListaStrings);
//        }
//        verificando os codigos colocados dentro da lista lstListaStringsAuxiliar
//        
//        for(int j=0; j < lstListaStringsAuxiliar.size(); j++)
//        {
//            System.out.println(lstListaStringsAuxiliar);
//        }                
        
        for (int i = 0; i < lstListaStringsAuxiliar.size(); i++) {
            //Pego o codigo
            int idReg = Integer.parseInt(lstListaStringsAuxiliar.get(i));

            for (int j = 0; j < lstListaStrings.size(); j++) {
                //Pego o nome
                String novoNomeTipo = tipo + " " + lstListaStrings.get(i);

                //Verifico se o nome não esta cadastrado para não dar duplicidade
                if (!umMetodo.temDuplicidadeDeCadastro("tblclientes", "nome", novoNomeTipo)) {
                    //atualizando o nome do cliente na tblclientes | Isso será feito para todos os registros
                    ctrCliente.atualizarNomesClientesVirtuais(idReg, novoNomeTipo);
                }
            }
        }
    }
    
    private void AlterarStatusClientesVirtuaisColetivo(int pCod) {
        //se o tipo tiver clientes virtuais dos setores todos eles terão alteração no seu status passando como parametro o CODIGO DO TIPO
                       
        //JOptionPane.showMessageDialog(null,"CODIGO DO TIPO = "+ pCod);
        
        //este metodo retorna todos os CODIGOS dos registros que serão alterados em uma lista(lstListaStringsAuxiliar) e que contem cliente virtual dos setores
        umMetodo.getCodigosRegistrosParaUmaLista(pCod);
        
        //verificando os codigos colocados dentro da lista lstListaStringsAuxiliar        
//        for(int j=0; j < lstListaStringsAuxiliar.size(); j++)
//        {
//            System.out.println(lstListaStringsAuxiliar);
//        }                   

        //atualizar todos os status dos registros relacionados ao tipo cadastrados na tblclientes
        for (int i = 0; i < lstListaStringsAuxiliar.size(); i++) {
            //Pego o codigo
            int idReg = Integer.parseInt(lstListaStringsAuxiliar.get(i));
            ctrCliente.atualizarStatusClientesVirtuaisColetivo(idReg,status);
        }
    }

    private void gravarEdicaoRegistro() {
        //setando os valores dos edits e gravando as alterações usei este metodo para gravar as edições
        codigo           = Integer.parseInt(txtCODIGO.getText());
        tipo             = txtTIPO.getText();
        status           = (String) cmbStatus.getSelectedItem();
        tipopatrimonio   = (String) cmbTipoPatrimonio.getSelectedItem();
        temip            = cmbTemIP.getSelectedItem().toString();

        //setando modelo
        umModTipo.setTipo(tipo);
        umModTipo.setTipopatrimonio(tipopatrimonio);       
        umModTipo.setTemIp(temip);
        umModTipo.setStatus(status);        
        umModTipo.setCodigo(codigo);

        //SE ALTERAR O TIPO
        if (!tipo.equals(oldTipoInicial)) {
            //JOptionPane.showMessageDialog(null, "Houve alteração do nome do tipo");

            if (umMetodo.TipoTemClientesVirtuais(codigo)) {
                //gravar a alteração do nome do tipo em tbltipos
                ctrTipo.atualizarTipo(umModTipo); 

                //Alterar o nome do cliente virtual individual  em tblclientesvirtuais 
                AlterarNomeClienteVirtualIndividual(codigoClienteVirtualParaAlteracao,tipo);

                //Alterar todos os nomes dos clientes virtuais na tblclientes
                AlterarTodosNomesClientesVirtuais(codigo);

            } else {
               //gravar a alteração do nome do tipo em tbltipos
                ctrTipo.atualizarTipo(umModTipo); 

                //Alterar o nome do cliente virtual individual  em tblclientesvirtuais 
                AlterarNomeClienteVirtualIndividual(codigoClienteVirtualParaAlteracao,tipo);
            }
        }else{
            //gravar a alteração do nome do tipo em tbltipos
            ctrTipo.atualizarTipo(umModTipo); 
        }

        //SE ALTERAR O STATUS
        if (!status.equals(oldStatusInicial)) {
            //JOptionPane.showMessageDialog(null, "Houve alteração do status");

            //gravar alteração do status em tbltipos
            ctrTipo.atualizarTipo(umModTipo); 

            //gravar alteração de status em tblclientesvirtuais 
            AlterarStatusClienteVirtualIndividual(codigoClienteVirtualParaAlteracao,status);

            //verificar se o tipo tem clientes virtuais dos setores em tblclientes, alterar seus status também
            if (umMetodo.TipoTemClientesVirtuais(codigo)) {
                AlterarStatusClientesVirtuaisColetivo(codigo);
            }
        }

        umGravarLog.gravarLog("atualizacao no cadastro de " + umModTipo.getTipo());
       
        Leitura();
        PreencherTabela(sqlDefault);
        
    }

    private void Edicao() {
        //metodo para quando usuario clicar em Editar
        boolean Habilitar = true;
        Component[] c = null;
        gravando = false;

        btnNovo.setEnabled(!Habilitar);
        btnSair.setEnabled(!Habilitar);
        btnGravar.setEnabled(Habilitar);
        btnEditar.setEnabled(!Habilitar);
        btnVoltar.setEnabled(Habilitar);
        btnGerarExcel.setEnabled(Habilitar);

        btnVoltar.setText("Cancelar");

        txtCODIGO.setEditable(false);
        txtTIPO.setEditable(true);
        txtTIPO.setEnabled(true);
        txtTIPO.requestFocus();

        //habilitando os dados
        c = jBoxDados.getComponents();
        for (int i = 0; i < c.length; i++) {
            c[i].setEnabled(!Habilitar);
        }

        //mostrando o titulo com qde de registros cadastrados
        this.setTitle(umabiblio.mostrarTituloDoFormulario());

        //preenchendo a tabela com os registros
        PreencherTabela(sqlDefault);

    }

    public void Leitura() {
        //formatacao inicial dos botoes ao abrir o formulario
        boolean Habilitar = true;
        Component[] c     = null;
        gravando          = false;

        txtCODIGO.setFocusable(false); //retirando o foco do codigo
        txtTIPO.setEditable(true);
        txtTIPO.setText(null);
        txtTIPO.requestFocus();
        
        btnNovo.setEnabled(Habilitar);
        btnSair.setEnabled(Habilitar);
        btnGravar.setEnabled(!Habilitar);
        btnEditar.setEnabled(!Habilitar);
        btnVoltar.setEnabled(!Habilitar);
        btnGerarExcel.setEnabled(Habilitar);

        //preenchendo a tabela com os registros
        PreencherTabela(sqlDefault);

        //limpando o txtTIPO      
        umabiblio.limparTodosCampos(this);

        //mostrando o titulo com qde de registros cadastrados
        this.setTitle(umabiblio.mostrarTituloDoFormulario());

        //popular a combo status com opcoes ATIVO pra manter inalterado e INATIVO caso deseje inativar
        cmbStatus.removeAllItems();       
        cmbStatus.setEnabled(false);
        
        //popular a combo temip com opcoes NAO/SIM Com a combo vazia selecionando o NAO qdo clicar em NOVO
        cmbTemIP.removeAllItems();
        cmbTemIP.addItem("NAO");
        cmbTemIP.addItem("SIM");
        cmbTemIP.setEnabled(false);
        cmbTemIP.setSelectedIndex(-1);

        //popular a combo tipos de patrimonio para cadastro PATRIMONIO/PATRIMOVEL
        cmbTipoPatrimonio.removeAllItems();
        cmbTipoPatrimonio.addItem("PATRIMONIO");
        cmbTipoPatrimonio.addItem("PATRIMOVEL");
        cmbTipoPatrimonio.setEnabled(false);
        cmbTipoPatrimonio.setSelectedIndex(-1);
        
        btnVoltar.setText("Voltar");
        gravando = false;
        editando = false;
        pesquisando = true;

    }

    public void HabilitarDesabilitarBotoes(boolean Habilitar) {
        //ações para quando clicar em cada botão
        Component[] c = null;

        btnNovo.setEnabled(Habilitar);
        btnGravar.setEnabled(!Habilitar);
        btnEditar.setEnabled(Habilitar);
        btnVoltar.setEnabled(!Habilitar);
        btnSair.setEnabled(Habilitar);
        btnGerarExcel.setEnabled(Habilitar);

    }

    public void PreencherTabela(String sql) {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Tipo", "Status", "Patrimonio", "Tem IP"};
        try {
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                dados.add(new Object[]{
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("tipo"),
                    conexao.rs.getString("status"),
                    conexao.rs.getString("tipopatrimonio"),
                    conexao.rs.getString("temip")
                });
            };

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabela.setModel(modelo);
            //define tamanho das colunas
            jTabela.getColumnModel().getColumn(0).setPreferredWidth(80);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabela.getColumnModel().getColumn(1).setPreferredWidth(360);
            jTabela.getColumnModel().getColumn(1).setResizable(false);
            jTabela.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTabela.getColumnModel().getColumn(2).setResizable(false);
            jTabela.getColumnModel().getColumn(3).setPreferredWidth(150);
            jTabela.getColumnModel().getColumn(3).setResizable(false);
            jTabela.getColumnModel().getColumn(4).setPreferredWidth(50);
            jTabela.getColumnModel().getColumn(4).setResizable(false);

            //define propriedades da tabela
            jTabela.getTableHeader().setReorderingAllowed(false);        //nao podera ser reorganizada
            jTabela.setAutoResizeMode(jTabela.AUTO_RESIZE_OFF);          //nao será possivel redimencionar a tabela
            jTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  

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
            java.util.logging.Logger.getLogger(F_TIPOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_TIPOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_TIPOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_TIPOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new F_TIPOS().setVisible(true);
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
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JComboBox<String> cmbTemIP;
    private javax.swing.JComboBox<String> cmbTipoPatrimonio;
    private javax.swing.JLayeredPane jBoxBotoes;
    private javax.swing.JLayeredPane jBoxDados;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabela;
    private javax.swing.JLabel lblDESCRICAO;
    private javax.swing.JTextField txtCODIGO;
    private javax.swing.JTextField txtTIPO;
    // End of variables declaration//GEN-END:variables
}
