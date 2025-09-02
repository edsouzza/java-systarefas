package visao;

import Dao.DAOLog;
import biblioteca.Biblioteca;
import biblioteca.ModeloTabela;
import com.toedter.calendar.JTextFieldDateEditor;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.tabela;
import controle.ControleGravarLog;
import controle.CtrlLog;
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
import java.util.Date;
import java.util.HashSet;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.Log;
import relatorios.GerarRelatorios;

public class F_LOGS extends javax.swing.JFrame {
    ConnConexao conexao  = new ConnConexao();
    ControleGravarLog umGravarLog         = new ControleGravarLog();
    Biblioteca    umabiblio               = new Biblioteca();
    Log           umModLog                = new Log();
    CtrlLog       umCtrLog                = new CtrlLog();
    DAOLog        logDAO                  = new DAOLog();
    Boolean clicouNaTabela                = false;
    String log,dtInicio,dtFim             = null;
    int codigo                            = 0; 
    boolean gravando;  //controla no botão gravar entre gravar novo registro e gravar alteração de um registro
    
    
    public F_LOGS() {
        initComponents();   
        PreencherTabela("SELECT * FROM tblLogs ORDER BY codigo desc");
        setResizable(false);   //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        this.setTitle(this.mostrarTituloDoFormulario());
        
        // Colocando enter para pular de campo 
        HashSet conj = new HashSet(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS)); 
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0)); 
        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj); 
                                
        //configuração dos botões
        umabiblio.configurarBotoes(btnImprimir);
        umabiblio.configurarBotoes(btnLimparPesquisa);
        umabiblio.configurarBotoes(btnSair);
        umabiblio.configurarBotoes(btnPesquisar);
        
        jTabelaLOGS.setFont(new Font("Arial",Font.BOLD,12));
        jTabelaLOGS.setForeground(Color.blue); 
        
        //desabilitando a edição das datas
        ((JTextFieldDateEditor) jDataInicial.getDateEditor ()). setEditable (false);
        ((JTextFieldDateEditor) jDataFinal.getDateEditor ()). setEditable (false);
                
        //Impede que formulario seja arrastado na tela
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                setEnabled(false);
                setEnabled(true);
            }
        });//fim addComponentListener       
        
    }
    
    public final String mostrarTituloDoFormulario()
    {
        int qdeRegs       = umabiblio.qdeRegistros(tabela);
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
        jTabelaLOGS = new javax.swing.JTable();
        jBoxBotoes = new javax.swing.JLayeredPane();
        btnSair = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnPesquisar = new javax.swing.JButton();
        btnLimparPesquisa = new javax.swing.JButton();
        jDataFinal = new com.toedter.calendar.JDateChooser();
        jDataInicial = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 733));

        jTabelaLOGS.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabelaLOGS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabelaLOGS.setGridColor(new java.awt.Color(204, 204, 204));
        jScrollPane2.setViewportView(jTabelaLOGS);

        jBoxBotoes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sair.gif"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSair.setPreferredSize(new java.awt.Dimension(77, 25));
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

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
        btnLimparPesquisa.setToolTipText("Limpar pesquisa");
        btnLimparPesquisa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimparPesquisa.setEnabled(false);
        btnLimparPesquisa.setPreferredSize(new java.awt.Dimension(77, 25));
        btnLimparPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparPesquisaActionPerformed(evt);
            }
        });

        jDataFinal.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N

        jDataInicial.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N

        javax.swing.GroupLayout jBoxBotoesLayout = new javax.swing.GroupLayout(jBoxBotoes);
        jBoxBotoes.setLayout(jBoxBotoesLayout);
        jBoxBotoesLayout.setHorizontalGroup(
            jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBoxBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jBoxBotoesLayout.setVerticalGroup(
            jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jBoxBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDataInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDataFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jBoxBotoes.setLayer(btnSair, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnImprimir, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnPesquisar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(btnLimparPesquisa, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(jDataFinal, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxBotoes.setLayer(jDataInicial, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBoxBotoes)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBoxBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 844, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(861, 710));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        //conexao.desconectar();
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        //As data passadas tem que ser do tipo java.util.date mesmo tipo dos parametros dentro do relatorio
        Date dtinicio =  jDataInicial.getDate();
        Date dtfim    =  jDataFinal.getDate();
        
        GerarRelatorios objRel = new GerarRelatorios();
        try {
            objRel.imprimirPeriodoSelecionado("relatorio/relLogsPorPeriodo.jasper", dtinicio, dtfim, tabela);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!"+e);                
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        jTabelaLOGS.addRowSelectionInterval(0,0);
    }//GEN-LAST:event_formWindowOpened
    public void pesquisar(){
        //configurando a data a ser passada na sql
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setLenient(true);
        String dataIniFormatada = sdf.format(jDataInicial.getDate());
        String dataFimFormatada = sdf.format(jDataFinal.getDate());
        //passando a sql para o metodo de preenchimento da tabela
        PreencherTabela("SELECT * FROM tblLogs WHERE data BETWEEN '"+dataIniFormatada+"'"+" AND '"+dataFimFormatada+"'"+" ORDER BY codigo desc");  
        jTabelaLOGS.addRowSelectionInterval(0,0);
        btnImprimir.setEnabled(true);
        btnLimparPesquisa.setEnabled(true);
        btnPesquisar.setEnabled(false);
        jDataInicial.setEnabled(false);
        jDataFinal.setEnabled(false);
    }
    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        //verificando se as datas foram digitadas antes de pesquisar
        if (!(jDataInicial.getDate()!=null))
        {
            JOptionPane.showMessageDialog(null,"Escolha as datas do período desejado!","Data inicial ou data final inválida",2);
        } else 
        if (!(jDataFinal.getDate()!=null)) 
        {
            JOptionPane.showMessageDialog(null,"Escolha as datas do período desejado!","Data inicial ou data final inválida",2);
        } else {
             pesquisar();
        }
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparPesquisaActionPerformed
        PreencherTabela("SELECT * FROM tbllogs ORDER BY codigo desc");
        jTabelaLOGS.addRowSelectionInterval(0,0);
        jDataFinal.setDate(null);
        jDataInicial.setDate(null);
        btnImprimir.setEnabled(false);
        btnLimparPesquisa.setEnabled(false);
        btnPesquisar.setEnabled(true);
        jDataInicial.setEnabled(true);
        jDataFinal.setEnabled(true);
        
    }//GEN-LAST:event_btnLimparPesquisaActionPerformed
   
    public void PreencherTabela(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        String[] Colunas = new String[]{"Cód.","Ocorrência","Data","Hora"};
        try 
        {                 
            conexao.ExecutarPesquisaSQL(sql);
            if(!conexao.rs.next())
            {
               JOptionPane.showMessageDialog(null,"O período selecionado não retornou registros!","Nada encontrado no período!",2);
               jDataFinal.setDate(null);
               jDataInicial.setDate(null);
               btnImprimir.setEnabled(false);
            }else{
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                while (conexao.rs.next())
                {  
                    dados.add(new Object[]
                    {
                        conexao.rs.getString("codigo"),
                        conexao.rs.getString("ocorrencia"),
                        df.format(conexao.rs.getDate("data")),
                        conexao.rs.getString("hora")
                        
                    });
                    };
                    ModeloTabela modelo = new ModeloTabela(dados, Colunas);
                    jTabelaLOGS.setModel(modelo);
                    //define tamanho das colunas
                    jTabelaLOGS.getColumnModel().getColumn(0).setPreferredWidth(60);
                    jTabelaLOGS.getColumnModel().getColumn(0).setResizable(false); 
                    jTabelaLOGS.getColumnModel().getColumn(1).setPreferredWidth(600);
                    jTabelaLOGS.getColumnModel().getColumn(1).setResizable(false);  
                    jTabelaLOGS.getColumnModel().getColumn(2).setPreferredWidth(75);
                    jTabelaLOGS.getColumnModel().getColumn(2).setResizable(false); 
                    jTabelaLOGS.getColumnModel().getColumn(3).setPreferredWidth(60);
                    jTabelaLOGS.getColumnModel().getColumn(3).setResizable(false); 
                    //define propriedades da tabela
                    jTabelaLOGS.getTableHeader().setReorderingAllowed(false);        //nao podera ser reorganizada
                    jTabelaLOGS.setAutoResizeMode(jTabelaLOGS.AUTO_RESIZE_OFF);          //nao será possivel redimencionar a tabela
                    jTabelaLOGS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
            }                
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
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnSair;
    private javax.swing.JLayeredPane jBoxBotoes;
    private com.toedter.calendar.JDateChooser jDataFinal;
    private com.toedter.calendar.JDateChooser jDataInicial;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabelaLOGS;
    // End of variables declaration//GEN-END:variables
}
