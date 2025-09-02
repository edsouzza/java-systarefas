package visao;

import Dao.DAONomeEstacao;
import conexao.ConnConexao;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import biblioteca.Biblioteca;
import biblioteca.ModeloTabela;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ListSelectionModel;
import biblioteca.MetodosPublicos;
import static biblioteca.VariaveisPublicas.nomeDepartamento;
import static biblioteca.VariaveisPublicas.totalRegs;
import controle.CtrlNomeEstacao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.NomeEstacao;


public class F_EXCLUSAONOMESTACOESINDIVIDUAL extends javax.swing.JDialog  {

    ConnConexao        conexao                  = new ConnConexao();
    Biblioteca         umaBiblio                = new Biblioteca();
    MetodosPublicos    umMetodo                 = new MetodosPublicos();  
    NomeEstacao        umModeloNomeEstacao      = new NomeEstacao();
    CtrlNomeEstacao    umControleNomeEstacao    = new CtrlNomeEstacao();
    DAONomeEstacao     umEstacaoNomeEstacaoAO   = new DAONomeEstacao();
    
    String sqlEstacoesPorDepto, sqlEstacoesDisponiveis, nomeDepto;
    String sqlVazia   = "SELECT * FROM TBLNOMESTACAO WHERE CODIGO=0";
    boolean selecionouItem;
    int numInicial, codigoNomeEstacao;
            

    public F_EXCLUSAONOMESTACOESINDIVIDUAL() 
    {
        initComponents();
        setResizable(false);   //desabilitando o redimencionamento da tela     
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar     
        this.setTitle("Exclusão de nomes de rede disponíveis");
        Leitura();       
        
        //cofigurações 
        jTabelaESTACOES.setFont(new Font("TimesRoman", Font.BOLD, 12));
        jTabelaESTACOES.setForeground(Color.blue); 
                             
        //Impede que formulario seja arrastado na tela
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                setEnabled(false);
                setEnabled(true);
            }
        });//fim addComponentListener     
        
        // Controla o item selecionado no jcombobox
        cmbDeptos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtém o item selecionado
                
                nomeDepartamento = (String) cmbDeptos.getSelectedItem();  
                if(cmbDeptos.getSelectedIndex() >= 0){
                    
                    if(nomeDepartamento.equals("BIBLIOTECA")){
                        nomeDepartamento = "CEJUR";
                    }               
                }
                btnFiltrarPorDepto.setEnabled(true);
                btnExcluirPorIntervalo.setEnabled(false);           
            }
        });
        
    }  
   
    private void popularComboDeptos()
    {
        //Populando o combo com os nomes dos departamentos
        umMetodo.PreencherCombo(cmbDeptos, "tbldepartamentos", "nome");
        cmbDeptos.setSelectedIndex(-1);            
    }    
            
    private void Leitura()
    {   
        popularComboDeptos();       
       
    }    
    
    private void mostrarTodosNomesEstacoesDisponiveisPorDepto(String pDepto){
        String sqlTodosNomesPorDepto  = "SELECT * FROM TBLNOMESTACAO WHERE  depto = '"+pDepto+"' AND status = 'DISPONIVEL' ORDER BY NUMESTACAO";        
        PreencherTabelaESTACOES(sqlTodosNomesPorDepto);
        cmbDeptos.setEnabled(false); 
    }
       
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        btnSair = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTabelaESTACOES = new javax.swing.JTable();
        cmbDeptos = new javax.swing.JComboBox<String>();
        btnLimpar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        btnExcluirPorIntervalo = new javax.swing.JButton();
        btnFiltrarPorDepto = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelPrincipal.setPreferredSize(new java.awt.Dimension(1024, 733));
        panelPrincipal.setLayout(null);

        btnSair.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sair.gif"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnSair);
        btnSair.setBounds(710, 80, 200, 40);

        jTabelaESTACOES.setAutoCreateRowSorter(true);
        jTabelaESTACOES.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabelaESTACOES.setToolTipText("");
        jTabelaESTACOES.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabelaESTACOES.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaESTACOESMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTabelaESTACOES);

        panelPrincipal.add(jScrollPane3);
        jScrollPane3.setBounds(10, 140, 900, 520);

        cmbDeptos.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmbDeptos.setForeground(new java.awt.Color(51, 51, 255));
        cmbDeptos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Departamento" }));
        cmbDeptos.setToolTipText("");
        cmbDeptos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelPrincipal.add(cmbDeptos);
        cmbDeptos.setBounds(10, 80, 270, 40);

        btnLimpar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TICK.PNG"))); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.setToolTipText("");
        btnLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpar.setEnabled(false);
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnLimpar);
        btnLimpar.setBounds(590, 80, 110, 40);

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(153, 0, 255));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("ESCOLHA O DEPARTAMENTO E UM NOME DE REDE PARA EXCLUSÃO INDIVIDUAL");
        panelPrincipal.add(lblTitulo);
        lblTitulo.setBounds(10, 20, 900, 14);
        lblTitulo.getAccessibleContext().setAccessibleName("ESCOLHA O DEPARTAMENTO PARA VISUALIZAR AS ESTACOES DISPONIVEIS PARA EXCLUSÃO");

        jScrollPane1.setToolTipText("");

        jTextPane1.setEnabled(false);
        jScrollPane1.setViewportView(jTextPane1);

        panelPrincipal.add(jScrollPane1);
        jScrollPane1.setBounds(10, 0, 900, 60);

        btnExcluirPorIntervalo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnExcluirPorIntervalo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sairPrograma.gif"))); // NOI18N
        btnExcluirPorIntervalo.setText("Excluir Intervalo");
        btnExcluirPorIntervalo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirPorIntervalo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirPorIntervaloActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnExcluirPorIntervalo);
        btnExcluirPorIntervalo.setBounds(430, 80, 150, 40);

        btnFiltrarPorDepto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnFiltrarPorDepto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/calculator_add.png"))); // NOI18N
        btnFiltrarPorDepto.setText("Filtrar Depto");
        btnFiltrarPorDepto.setToolTipText("");
        btnFiltrarPorDepto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFiltrarPorDepto.setEnabled(false);
        btnFiltrarPorDepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarPorDeptoActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnFiltrarPorDepto);
        btnFiltrarPorDepto.setBounds(290, 80, 130, 40);

        getContentPane().add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 920, 660));

        setSize(new java.awt.Dimension(928, 708));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
          
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
       
        dispose();
        
    }//GEN-LAST:event_btnSairActionPerformed
       
    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
                   
        btnLimpar.setEnabled(false);
        btnSair.setEnabled(true);   
        btnFiltrarPorDepto.setEnabled(false);
        cmbDeptos.setEnabled(true);        
        cmbDeptos.setSelectedIndex(-1);
        btnExcluirPorIntervalo.setEnabled(true);
        PreencherTabelaESTACOES(sqlVazia);
        
    }//GEN-LAST:event_btnLimparActionPerformed

    private void jTabelaESTACOESMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaESTACOESMouseClicked
        umModeloNomeEstacao.setNomestacao((String) jTabelaESTACOES.getValueAt(jTabelaESTACOES.getSelectedRow(), 1)); //pegando o nome selecionado e setando no modelo
        umControleNomeEstacao.pesquisarNomeEstacao(umModeloNomeEstacao);  //chamando a função e passando como parametro o nome selecinado acima

        //pegando o retorno da funcao onde meu objeto ja contem todos os dados do nome da estacao, ai posso pegar o codigo ao clicar no item da tabela e posteriormente excluir
        String nomeEstacao = umModeloNomeEstacao.getNomestacao();        
        codigoNomeEstacao = umMetodo.buscarCodigoEstacaoPeloNome("tblnomestacao", "nomestacao",nomeEstacao);
        
        //JOptionPane.showMessageDialog(rootPane, "codiogo do NomeEstacao : " +codigoNomeEstacao);
        
        if(umMetodo.ConfirmouOperacao("Confirma o desejo de excluir o nome "+nomeEstacao+"", "Exclusão do Nome de Rede")){            
           if(umControleNomeEstacao.excluirNomeEstacaoIndividual(codigoNomeEstacao)){
               JOptionPane.showMessageDialog(null, nomeEstacao+" foi excluido com sucesso!","Exclusao de nome de rede", 2);                
           }
           mostrarTodosNomesEstacoesDisponiveisPorDepto(nomeDepartamento);             
        }
        btnLimparActionPerformed(null);
    }//GEN-LAST:event_jTabelaESTACOESMouseClicked

    private void btnFiltrarPorDeptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarPorDeptoActionPerformed
        if(cmbDeptos.getSelectedIndex() < 0){
            JOptionPane.showMessageDialog(null, "Escolha um Departamento para visualizar os nomes disponíveis!", "Departamento inválido!", 2); 
            btnExcluirPorIntervalo.setEnabled(true);
            cmbDeptos.setEnabled(true);
        }else{
            if(nomeDepartamento.equals("BIBLIOTECA")){
                nomeDepartamento = "CEJUR";
            }
            mostrarTodosNomesEstacoesDisponiveisPorDepto(nomeDepartamento);
            btnFiltrarPorDepto.setEnabled(false);
            cmbDeptos.setEnabled(true);
            btnLimpar.setEnabled(true);
            btnExcluirPorIntervalo.setEnabled(false);
        }
        cmbDeptos.setEnabled(true);
    }//GEN-LAST:event_btnFiltrarPorDeptoActionPerformed

    private void btnExcluirPorIntervaloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirPorIntervaloActionPerformed
        
        F_EXCLUSAONOMESPORINTERVALO frm = new F_EXCLUSAONOMESPORINTERVALO();
        frm.setVisible(true); 
       
    }//GEN-LAST:event_btnExcluirPorIntervaloActionPerformed
         
    public void PreencherTabelaESTACOES(String sql) {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Estação", "Departamento", "Status"};
        try {
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                dados.add(new Object[]{
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("nomestacao"),
                    conexao.rs.getString("depto"),
                    conexao.rs.getString("status")

                });
                totalRegs = conexao.rs.getRow();
            };
                        
            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabelaESTACOES.setModel(modelo);
            
            //define tamanho das colunas
            jTabelaESTACOES.getColumnModel().getColumn(0).setPreferredWidth(150);  //define o tamanho da coluna
            jTabelaESTACOES.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaESTACOES.getColumnModel().getColumn(1).setPreferredWidth(300);  //define o tamanho da coluna
            jTabelaESTACOES.getColumnModel().getColumn(1).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaESTACOES.getColumnModel().getColumn(2).setPreferredWidth(280);  //define o tamanho da coluna
            jTabelaESTACOES.getColumnModel().getColumn(2).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaESTACOES.getColumnModel().getColumn(3).setPreferredWidth(130);  //define o tamanho da coluna
            jTabelaESTACOES.getColumnModel().getColumn(3).setResizable(false);    //nao será possivel redimencionar a coluna 

            //define propriedades da tabela
            jTabelaESTACOES.getTableHeader().setReorderingAllowed(false);        //nao podera ser reorganizada
            jTabelaESTACOES.setAutoResizeMode(jTabelaESTACOES.AUTO_RESIZE_OFF);          //nao será possivel redimencionar a tabela
            jTabelaESTACOES.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  

        } catch (SQLException ex) {
            //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
            JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnExcluirPorIntervalo;
    public javax.swing.JButton btnFiltrarPorDepto;
    public javax.swing.JButton btnLimpar;
    public javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbDeptos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTable jTabelaESTACOES;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables
}
