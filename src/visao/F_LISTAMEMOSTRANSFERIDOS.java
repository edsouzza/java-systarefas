package visao;

import Dao.DAOPatrimonio;
import biblioteca.Biblioteca;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import biblioteca.SomenteNumeros;
import biblioteca.TudoMaiusculas;
import static biblioteca.VariaveisPublicas.numMemoTransferido;
import static biblioteca.VariaveisPublicas.totalRegs;
import static biblioteca.VariaveisPublicas.numemoParaEditarObs;
import static biblioteca.VariaveisPublicas.numemoParaImprimir;
import static biblioteca.VariaveisPublicas.relAssuntoMemo;
import static biblioteca.VariaveisPublicas.relDestinoMemo;
import static biblioteca.VariaveisPublicas.imprimindoDaLista;
import static biblioteca.VariaveisPublicas.tabela;
import conexao.ConnConexao;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import controle.ControleGravarLog;
import controle.CtrlPatriItenstransferido;
import controle.CtrlPatriTransferido;
import modelo.PatriTransferido;


public class F_LISTAMEMOSTRANSFERIDOS extends javax.swing.JFrame {
    ConnConexao                    conexao                      = new ConnConexao();
    Biblioteca                     umabiblio                    = new Biblioteca();
    SomenteNumeros                 soNumeros                    = new SomenteNumeros();
    ControleGravarLog              umGravarLog                  = new ControleGravarLog();  
    
    MetodosPublicos                umMetodo                     = new MetodosPublicos();
    CtrlPatriItenstransferido      umCtrlPatrItemTranferido     = new CtrlPatriItenstransferido();
    CtrlPatriTransferido           umCtrlPatriTranferido        = new CtrlPatriTransferido();
    PatriTransferido               umModPatriTransferido        = new PatriTransferido();
    DAOPatrimonio                  umPatrimonioDAO              = new DAOPatrimonio();
        
    String sqlDinamica  = "SELECT DISTINCT m.codigo, m.numemo, i.origem, i.destino, m.assunto FROM TBLMEMOSTRANSFERIDOS m, TBLITENSMEMOTRANSFERIDOS i WHERE m.numemo=i.numemo AND m.status = 'TRANSFERIDO' ORDER BY m.codigo";  
    String numemo, sDestino;    
    int icodigo, codExc = 0;
    ArrayList<Integer>listaCodigosParaReativacao  = new ArrayList();
    
    public F_LISTAMEMOSTRANSFERIDOS() {
        initComponents();
        Leitura();        
        setResizable(false);              //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        this.setTitle("Lista Memorandos de Patrimônios Transferidos");        
                  
        //configuração dos botões        
        umabiblio.configurarBotoes(btnImprimir);
        umabiblio.configurarBotoes(btnExluir);
        umabiblio.configurarBotoes(btnSair);
        umabiblio.configurarBotoes(btnEditarObservacao);
        umabiblio.configurarBotoes(btnEditarMemorando);
               
        //cofigurações das tabelas
        jTabela.setFont(new Font("TimesRoman",Font.BOLD,12));
        jTabela.setForeground(Color.blue);
        
        //configs do txtPesquisa
        txtPESQUISA.setDocument(new TudoMaiusculas());
        txtPESQUISA.setFont(new Font("Tahoma", Font.BOLD, 12));
        txtPESQUISA.setForeground(Color.red);              
        
        //Impede que formulario seja arrastado na tela
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                setEnabled(false);
                setEnabled(true);
            }
        });//fim addComponentListener       
        
    }    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabela = new javax.swing.JTable();
        jBoxPesquisar = new javax.swing.JLayeredPane();
        txtPESQUISA = new javax.swing.JTextField();
        btnLimparPesquisa = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnExluir = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnEditarObservacao = new javax.swing.JButton();
        btnEditarMemorando = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();

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

        jBoxPesquisar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxPesquisar.setName("panelDados"); // NOI18N

        txtPESQUISA.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPESQUISA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPESQUISAMouseClicked(evt);
            }
        });
        txtPESQUISA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPESQUISAKeyReleased(evt);
            }
        });

        btnLimparPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_limpar.gif"))); // NOI18N
        btnLimparPesquisa.setText("Limpar");
        btnLimparPesquisa.setToolTipText("Limpar a pesquisa corrente");
        btnLimparPesquisa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimparPesquisa.setEnabled(false);
        btnLimparPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparPesquisaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jBoxPesquisarLayout = new javax.swing.GroupLayout(jBoxPesquisar);
        jBoxPesquisar.setLayout(jBoxPesquisarLayout);
        jBoxPesquisarLayout.setHorizontalGroup(
            jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxPesquisarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtPESQUISA)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jBoxPesquisarLayout.setVerticalGroup(
            jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBoxPesquisarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jBoxPesquisarLayout.createSequentialGroup()
                        .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3))
                    .addGroup(jBoxPesquisarLayout.createSequentialGroup()
                        .addGap(0, 2, Short.MAX_VALUE)
                        .addComponent(txtPESQUISA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jBoxPesquisar.setLayer(txtPESQUISA, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxPesquisar.setLayer(btnLimparPesquisa, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBoxPesquisar)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 839, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBoxPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE))
        );

        btnExluir.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnExluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sairPrograma.gif"))); // NOI18N
        btnExluir.setText("Excluir Memo");
        btnExluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExluir.setEnabled(false);
        btnExluir.setMaximumSize(new java.awt.Dimension(77, 25));
        btnExluir.setMinimumSize(new java.awt.Dimension(77, 25));
        btnExluir.setPreferredSize(new java.awt.Dimension(77, 25));
        btnExluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExluirActionPerformed(evt);
            }
        });

        btnImprimir.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_imprimir.gif"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImprimir.setEnabled(false);
        btnImprimir.setPreferredSize(new java.awt.Dimension(77, 25));
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnEditarObservacao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEditarObservacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_gravar.gif"))); // NOI18N
        btnEditarObservacao.setText("Editar Observação");
        btnEditarObservacao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarObservacao.setEnabled(false);
        btnEditarObservacao.setPreferredSize(new java.awt.Dimension(77, 25));
        btnEditarObservacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarObservacaoActionPerformed(evt);
            }
        });

        btnEditarMemorando.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEditarMemorando.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_pesquisa.gif"))); // NOI18N
        btnEditarMemorando.setText("Editar Memorando");
        btnEditarMemorando.setToolTipText("");
        btnEditarMemorando.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarMemorando.setEnabled(false);
        btnEditarMemorando.setPreferredSize(new java.awt.Dimension(77, 25));
        btnEditarMemorando.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarMemorandoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnExluir, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditarObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEditarMemorando, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExluir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarMemorando, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnSair.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sair.gif"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSair.setPreferredSize(new java.awt.Dimension(77, 25));
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        setSize(new java.awt.Dimension(875, 703));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
                       
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed
       
    
    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        //AO CLICAR EM UM REGISTRO DA TABELA MOSTRAR OS DADOS NOS EDITS
        icodigo              = (int)jTabela.getValueAt(jTabela.getSelectedRow(), 0);
        numemoParaImprimir   = (String)jTabela.getValueAt(jTabela.getSelectedRow(), 1);
        numemoParaEditarObs  = (String)jTabela.getValueAt(jTabela.getSelectedRow(), 1);
        relDestinoMemo       = (String)jTabela.getValueAt(jTabela.getSelectedRow(), 3);        
        relAssuntoMemo       = (String)jTabela.getValueAt(jTabela.getSelectedRow(), 4);        
        
        btnImprimir          .setEnabled(true);
        btnEditarObservacao  .setEnabled(true);
        btnExluir            .setEnabled(true);  
        btnEditarMemorando   .setEnabled(true);   
        
        //Incrementar a listaCodigosParaReativacao -> Ler todos os registros que constam do memorando e guardar os codigos        
        listaCodigosParaReativacao = umMetodo.ListarCodigosDosItensDoMemorando(numemoParaImprimir);    
        
                        
    }//GEN-LAST:event_jTabelaMouseClicked
    
    private void excluirMemorandoSelecionado(){
         if(umabiblio.permissaoLiberada())
        {  
            if (umabiblio.ConfirmouOperacao("Confirma a exclusão do memorando "+numemoParaImprimir+"?", "Exclusão de memorando "+numemoParaImprimir))
            {    
                if (umCtrlPatriTranferido.excluirMemoTransferido(numemoParaImprimir))
                {                
                    //esta exclusao devera afetar as duas tabelas TBLMEMOSTRANSFERIDOS | TBLITENSMEMOTRANSFERIDOS             
                    umCtrlPatrItemTranferido.excluirItensDoMemoSelecionado(numemoParaImprimir);
                    
                    JOptionPane.showMessageDialog(null, "O memorando "+numemoParaImprimir+" foi excluído com sucesso!","Exclusão de memorando!",1);
                    
                    PreencherTabela(sqlDinamica);                 
                }            
            }

            Leitura();
        }
    }
    
    private void reativarPatrimoniosDoMemorandoPosExclusao()
    {
        //Caso o memorando seja excluido todos os patrimonios constantes do mesmo serão reativados        
        int totalRegs = listaCodigosParaReativacao.size();        
                
        for(int i=0; i < totalRegs; i++){
            int pCodigo = listaCodigosParaReativacao.get(i);   
            umPatrimonioDAO.updateStatusPosExclusaoItemDeMemoEnviado(pCodigo, numemoParaImprimir);
        }        
   }
    
    private void btnExluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExluirActionPerformed
        //exclusão do ítem da tabela de transferencia
        excluirMemorandoSelecionado();
        reativarPatrimoniosDoMemorandoPosExclusao();
       
    }//GEN-LAST:event_btnExluirActionPerformed
       
    private void filtrarPorDigitacao(String pPesq) 
    {
       PreencherTabela("SELECT DISTINCT m.codigo, m.numemo, i.origem, i.destino, m.assunto FROM TBLMEMOSTRANSFERIDOS m, TBLITENSMEMOTRANSFERIDOS i "
                        + "WHERE m.numemo=i.numemo AND m.status = 'TRANSFERIDO' AND "
                        + "( (m.numemo like '%" + pPesq + "%') OR (i.destino like '%" + pPesq + "%') OR (m.assunto like '%" + pPesq + "%')) "
                        + "ORDER BY i.destino"
                       );
       
       this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);
    }
    
    private void Leitura() {
        
        umabiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 
        btnExluir.setEnabled(false);
        btnImprimir.setEnabled(false);
        btnEditarObservacao.setEnabled(false);
        btnEditarMemorando.setEnabled(false);
        btnSair.setEnabled(true);         
        txtPESQUISA.requestFocus();
        PreencherTabela(sqlDinamica);        
        
    }    

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
       imprimindoDaLista = true;
       if(relAssuntoMemo.equals("DEVOLUCAO DE EQUIPAMENTOS")){
            F_ESCOLHAIMPRESSAODEVOLUCAO frm = new F_ESCOLHAIMPRESSAODEVOLUCAO();
            frm.setVisible(true);         
       }else if(relAssuntoMemo.equals("BAIXA DE EQUIPAMENTOS INSERVIVEIS")){
            numMemoTransferido = numemoParaImprimir;
            F_ESCOLHAIMPRESSAOINSERVIVEIS frm = new F_ESCOLHAIMPRESSAOINSERVIVEIS();
            frm.setVisible(true);          
       }else{
           F_ESCOLHAIMPRESSAO frm = new F_ESCOLHAIMPRESSAO();
            frm.setVisible(true);
       }             
       btnImprimir.setEnabled(false);
       btnExluir.setEnabled(false);
       btnEditarObservacao.setEnabled(false);
       btnEditarMemorando.setEnabled(false);
       umGravarLog.gravarLog("impressao do memo de transferencia de patrimonios "+numemoParaImprimir); 
       imprimindoDaLista = false;
    }//GEN-LAST:event_btnImprimirActionPerformed
    
    public void limparCampos() {
        Leitura();        
    }
    
    private void txtPESQUISAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyReleased
        filtrarPorDigitacao(txtPESQUISA.getText());
        btnLimparPesquisa.setEnabled(true);
    }//GEN-LAST:event_txtPESQUISAKeyReleased

    private void btnLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparPesquisaActionPerformed
        limparCampos();
    }//GEN-LAST:event_btnLimparPesquisaActionPerformed

    private void btnEditarObservacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarObservacaoActionPerformed
        F_EDITAROBSMEMOENVIO frm = new F_EDITAROBSMEMOENVIO(new javax.swing.JFrame(), true);
        frm.setVisible(true);  
        btnImprimir.setEnabled(false);
        btnEditarObservacao.setEnabled(false);
        btnExluir.setEnabled(false);
    }//GEN-LAST:event_btnEditarObservacaoActionPerformed

    private void btnEditarMemorandoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarMemorandoActionPerformed
        //Converter status de todos os itens do memorando selecionado para 'PROCESSANDO'        
        String numemoParaEditar = numemoParaImprimir;        
        umMetodo.setarStatusDosItensDoMemorando("PROCESSANDO", numemoParaEditar);
        
        if(umabiblio.permissaoLiberada()){       
            tabela = "TBLITENSMEMOTRANSFERIDOS";   
            F_EDITARMEMOITENSTRANSFERIDOS frm = new F_EDITARMEMOITENSTRANSFERIDOS();
            frm.setVisible(true);             
        }
        dispose();
    }//GEN-LAST:event_btnEditarMemorandoActionPerformed

    private void txtPESQUISAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPESQUISAMouseClicked
        btnLimparPesquisaActionPerformed(null);
    }//GEN-LAST:event_txtPESQUISAMouseClicked
    
    public void PreencherTabela(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Memorando", "Origem", "Destino","Assunto"};
        try {
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                dados.add(new Object[]{
                    
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("numemo"),
                    conexao.rs.getString("origem"),
                    conexao.rs.getString("destino"),
                    conexao.rs.getString("assunto")

                });
                totalRegs = conexao.rs.getRow();
            };
            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabela.setModel(modelo);
            //define tamanho das colunas   
            jTabela.getColumnModel().getColumn(0).setPreferredWidth(60);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna      
            jTabela.getColumnModel().getColumn(1).setPreferredWidth(80); //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(1).setResizable(false);    //nao será possivel redimencionar a coluna        
            jTabela.getColumnModel().getColumn(2).setPreferredWidth(150); //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(2).setResizable(false);    //nao será possivel redimencionar a coluna         
            jTabela.getColumnModel().getColumn(3).setPreferredWidth(260); //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(3).setResizable(false);    //nao será possivel redimencionar a coluna     
            jTabela.getColumnModel().getColumn(4).setPreferredWidth(260);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(4).setResizable(false);    //nao será possivel redimencionar a coluna      
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
    private javax.swing.JButton btnEditarMemorando;
    private javax.swing.JButton btnEditarObservacao;
    private javax.swing.JButton btnExluir;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnLimparPesquisa;
    private javax.swing.JButton btnSair;
    private javax.swing.JLayeredPane jBoxPesquisar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabela;
    private javax.swing.JTextField txtPESQUISA;
    // End of variables declaration//GEN-END:variables
}
