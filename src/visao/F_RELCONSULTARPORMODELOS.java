package visao;

import biblioteca.Biblioteca;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import biblioteca.SomenteNumeros;
import static biblioteca.VariaveisPublicas.TipoModelo;
import static biblioteca.VariaveisPublicas.codigoTipoModelo;
import static biblioteca.VariaveisPublicas.totalRegs;
import static biblioteca.VariaveisPublicas.imprimirPorModelo;
import static biblioteca.VariaveisPublicas.tabela_da_lista;
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
import relatorios.GerarRelatorios;


public class F_RELCONSULTARPORMODELOS extends javax.swing.JFrame {
    ConnConexao                    conexao                      = new ConnConexao();
    Biblioteca                     umabiblio                    = new Biblioteca();
    SomenteNumeros                 soNumeros                    = new SomenteNumeros();
    ControleGravarLog              umGravarLog                  = new ControleGravarLog();  
    
    MetodosPublicos                umMetodo                     = new MetodosPublicos();
    CtrlPatriItenstransferido      umCtrlPatrItemTranferido     = new CtrlPatriItenstransferido();
    CtrlPatriTransferido           umCtrlPatriTranferido        = new CtrlPatriTransferido();
    PatriTransferido               umModPatriTransferido        = new PatriTransferido();
          
    String sqlVazia      = "SELECT * FROM tblpatrimonios WHERE codigo = 0";        
    String sqlDinamica, modelo, numemo, sStatus = "";
    int icodigo, codExc = 0;
    
    public F_RELCONSULTARPORMODELOS() {
        initComponents();
        Leitura();        
        setResizable(false);              //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        this.setTitle("Listar Patrimônios Por Modelo Selecionado e Status");        
                  
        //configuração dos botões        
        umabiblio.configurarBotoes(btnImprimir);
        umabiblio.configurarBotoes(btnSair);
               
        //cofigurações das tabelas
        jTabela.setFont(new Font("TimesRoman",Font.BOLD,12));
        jTabela.setForeground(Color.blue);
               
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
        btnLimparPesquisa = new javax.swing.JButton();
        btnListarModelos = new javax.swing.JButton();
        cmbEscolhaStatus = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        btnImprimir = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 733));

        jTabela.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane2.setViewportView(jTabela);

        jBoxPesquisar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxPesquisar.setName("panelDados"); // NOI18N

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

        btnListarModelos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnListarModelos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_pesquisa.gif"))); // NOI18N
        btnListarModelos.setText("Listar Modelos para Seleção");
        btnListarModelos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnListarModelos.setPreferredSize(new java.awt.Dimension(77, 25));
        btnListarModelos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarModelosActionPerformed(evt);
            }
        });

        cmbEscolhaStatus.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbEscolhaStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ATIVO", "INATIVO", "TODOS" }));
        cmbEscolhaStatus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jBoxPesquisar.setLayer(btnLimparPesquisa, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxPesquisar.setLayer(btnListarModelos, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxPesquisar.setLayer(cmbEscolhaStatus, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jBoxPesquisarLayout = new javax.swing.GroupLayout(jBoxPesquisar);
        jBoxPesquisar.setLayout(jBoxPesquisarLayout);
        jBoxPesquisarLayout.setHorizontalGroup(
            jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxPesquisarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbEscolhaStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnListarModelos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jBoxPesquisarLayout.setVerticalGroup(
            jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxPesquisarLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListarModelos, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbEscolhaStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBoxPesquisar)
                    .addComponent(jScrollPane2)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBoxPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                .addGap(2, 2, 2))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 893, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setSize(new java.awt.Dimension(919, 703));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
                       
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed
                    
    private void Leitura() {
        
        umabiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 
        btnImprimir.setEnabled(false);
        btnLimparPesquisa.setEnabled(false);
        imprimirPorModelo = false;
        btnSair.setEnabled(true);                          
        btnListarModelos.setEnabled(true);   
        btnImprimir.setText("Imprimir");
        this.setTitle("Listar Patrimônios Por Modelo Selecionado e Status");         
        cmbEscolhaStatus.setEnabled(true);
        cmbEscolhaStatus.setSelectedIndex(0);
        
    }
    
    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
                    
        PreencherTabela(sqlVazia);
        
        if(!sStatus.equals("TODOS")) {
        
            GerarRelatorios objRel = new GerarRelatorios();
            try {            
                   objRel.imprimirPatrimoniosDoModeloSelecionadoAtivoOuInativo("relatorio/relPatrimoniosModeloSelecionadoAtivoOuInativo.jasper", sStatus,codigoTipoModelo);           
            } catch (Exception e) {
                e.printStackTrace(); 
                JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);
            } 
        }else{
            
            GerarRelatorios objRel = new GerarRelatorios();
            try {            
                   objRel.imprimirTodosPatrimoniosDoModeloSelecionado("relatorio/relTodosPatrimoniosModeloSelecionado.jasper", codigoTipoModelo);           
            } catch (Exception e) {
                e.printStackTrace(); 
                JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);
            } 
            
        }            
              
       umGravarLog.gravarLog("impressao de patrimonios ativos por modelo selecionado "+modelo);
        btnLimparPesquisaActionPerformed(null);
        
    }//GEN-LAST:event_btnImprimirActionPerformed
    
    public void limparCampos() {
        Leitura();    
        PreencherTabela(sqlVazia);
    }
    
    private void btnLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparPesquisaActionPerformed
        limparCampos();
    }//GEN-LAST:event_btnLimparPesquisaActionPerformed

    private void btnListarModelosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarModelosActionPerformed
        totalRegs         = 0;
        imprimirPorModelo = true;
        tabela_da_lista   = "TBLMODELOS";
        sStatus           = cmbEscolhaStatus.getSelectedItem().toString();
        
        F_LISTAMODELOSGERARTXT frm = new F_LISTAMODELOSGERARTXT(new javax.swing.JFrame(), true);
        frm.setVisible(true);  
            
        if(!sStatus.equals("TODOS")) {
            
            sqlDinamica =   ("SELECT p.codigo,p.serie,p.chapa,p.modeloid,p.deptoid,p.secaoid,p.status,m.codigo,m.modelo,d.codigo,d.nome as DEPARTAMENTO,s.codigo,s.nome as SECAO \n" +
                            "from tblpatrimonios p, tblmodelos m, tbldepartamentos d, tblsecoes s \n" +
                            "WHERE \n" +                        
                            "p.modeloid = m.codigo AND \n" +
                            "p.modeloid = "+codigoTipoModelo+" AND \n" +
                            "d.codigo = p.deptoid AND \n" +
                            "s.codigo = p.secaoid AND \n" +
                            "p.status = '"+sStatus+"' \n" +
                            "ORDER BY p.codigo");
        }else{
            sqlDinamica =   ("SELECT p.codigo,p.serie,p.chapa,p.modeloid,p.deptoid,p.secaoid,m.codigo,m.modelo,d.codigo,d.nome as DEPARTAMENTO,s.codigo,s.nome as SECAO \n" +
                            "from tblpatrimonios p, tblmodelos m, tbldepartamentos d, tblsecoes s \n" +
                            "WHERE \n" +                        
                            "p.modeloid = m.codigo AND \n" +
                            "p.modeloid = "+codigoTipoModelo+" AND \n" +
                            "d.codigo = p.deptoid AND \n" +
                            "s.codigo = p.secaoid \n" +
                            "ORDER BY p.codigo");
        }       
        
        PreencherTabela(sqlDinamica); 
        
        String sTitle = "Encontrados "+totalRegs+" patrimônios do modelo "+TipoModelo+" - "+sStatus+"";
        this.setTitle(sTitle);
        btnListarModelos.setEnabled(false);
        btnLimparPesquisa.setEnabled(true);
        cmbEscolhaStatus.setEnabled(false);            
        
        if(totalRegs > 0){
            btnImprimir.setEnabled(true);
        }
        
    }//GEN-LAST:event_btnListarModelosActionPerformed
    
    public void PreencherTabela(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Modelo", "Série", "Chapa","Depto", "Seção" };
        try {
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                dados.add(new Object[]{
                    
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("modelo"),
                    conexao.rs.getString("serie"),
                    conexao.rs.getString("chapa"),
                    conexao.rs.getString("departamento"),
                    conexao.rs.getString("secao")

                });
                totalRegs = conexao.rs.getRow();
                modelo    = conexao.rs.getString("modelo");
            };
            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabela.setModel(modelo);
            //define tamanho das colunas   
            jTabela.getColumnModel().getColumn(0).setPreferredWidth(60);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna      
            jTabela.getColumnModel().getColumn(1).setPreferredWidth(230); //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(1).setResizable(false);    //nao será possivel redimencionar a coluna   
            jTabela.getColumnModel().getColumn(2).setPreferredWidth(150); //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(2).setResizable(false);    //nao será possivel redimencionar a coluna               
            jTabela.getColumnModel().getColumn(3).setPreferredWidth(150); //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(3).setResizable(false);    //nao será possivel redimencionar a coluna         
            jTabela.getColumnModel().getColumn(4).setPreferredWidth(100); //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(4).setResizable(false);    //nao será possivel redimencionar a coluna         
            jTabela.getColumnModel().getColumn(5).setPreferredWidth(150); //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(5).setResizable(false);    //nao será possivel redimencionar a coluna         
               
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
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnLimparPesquisa;
    private javax.swing.JButton btnListarModelos;
    private javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbEscolhaStatus;
    private javax.swing.JLayeredPane jBoxPesquisar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabela;
    // End of variables declaration//GEN-END:variables
}
