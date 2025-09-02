package visao;

import biblioteca.Biblioteca;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import static biblioteca.VariaveisPublicas.editandoDisponiveis;
import static biblioteca.VariaveisPublicas.nomeDepartamento;
import static biblioteca.VariaveisPublicas.nomestacaosubstituida;
import static biblioteca.VariaveisPublicas.novonomestacao;
import conexao.ConnConexao;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.Patrimonio;
import controle.CtrlNomeEstacao;
import modelo.NomeEstacao;

/**
 *
 * @author EDI
 */
public class F_LISTAESTACOESDISPONIVEIS extends javax.swing.JDialog {

    ConnConexao     conexao                = new ConnConexao();
    Patrimonio      umPatrimonio           = new Patrimonio();
    Biblioteca      umaBiblio              = new Biblioteca();
    MetodosPublicos umMetodo               = new MetodosPublicos();
    NomeEstacao     umModeloNomeEstacao    = new NomeEstacao();
    CtrlNomeEstacao umControleNomeEstacao  = new CtrlNomeEstacao();
    String          sqlTrocaNomeRede       = "select nomestacao from tblnomestacao where depto='"+nomeDepartamento+"' AND status='DISPONIVEL' ORDER BY NUMESTACAO";
    String          sqlBiblioteca          = "select nomestacao from tblnomestacao where depto='CEJUR' AND status='DISPONIVEL' ORDER BY NUMESTACAO";
    String          estacaoEncontrada      = "";
    
    
    private String nomeEstacaoSelecionada;
    
    public F_LISTAESTACOESDISPONIVEIS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        PopularTabelaComDadosDaSql();  
        
        setResizable(false);   //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        jTabela.setFont(new Font("TimesRoman",Font.BOLD,12));
        jTabela.setForeground(Color.blue);
        jTabela.setFont(new Font("Arial", Font.BOLD, 12));  
    }
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTabela = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTabela.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTabela);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 371, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 185, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setSize(new java.awt.Dimension(389, 230));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void PopularTabelaComDadosDaSql()
    {             
        if(nomeDepartamento.equals("BIBLIOTECA")){          
            PreencherTabela(sqlBiblioteca);          
        }else{            
            PreencherTabela(sqlTrocaNomeRede);         
        }
        this.setTitle("Lista de nomes disponíveis "+nomeDepartamento);                  
    }
        
    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        //AO CLICAR EM UM REGISTRO DA TABELA PEGAR O NOME E CODIGO DO CLIENTE FECHAR E MOSTRAR O FORMULARIO DE CADASTRO DE TAREFAS COM OS DADOS SETADOS
        //JOptionPane.showMessageDialog(null, "NOME SELECIONADO...: "+jTabela.getValueAt(jTabela.getSelectedRow(), 0));
        setNomeEstacaoSelecionada((String) jTabela.getValueAt(jTabela.getSelectedRow(), 0));
        //estacaoEncontrada = (String) jTabela.getValueAt(jTabela.getSelectedRow(), 0);  
        nomeEstacaoSelecionada = getNomeEstacaoSelecionada();        
        atualizarStatusDasEstacaoEditada(nomeEstacaoSelecionada);        
        dispose();

    }//GEN-LAST:event_jTabelaMouseClicked
      
    
    private void atualizarStatusDasEstacaoEditada(String nomestacao) {
        if(editandoDisponiveis)
        {      
            //ATUALIZA O STATUS DA NOVA ESTAÇÃO ESCOLHIDA PARA INDISPONIVEL     
            int codigoEst = umMetodo.getCodigoPassandoString("TBLNOMESTACAO", "nomestacao", nomestacao);
            umModeloNomeEstacao.setCodigo(codigoEst);
            umModeloNomeEstacao.setNomestacao(nomestacao);
            umModeloNomeEstacao.setStatus("INDISPONIVEL");
            umControleNomeEstacao.atualizarStatusNomeEstacao(umModeloNomeEstacao);   
            novonomestacao = nomestacao;

            //ATUALIZAR O STATUS DE QUALQUER ESTACAO SUBSTITUIDA PARA DISPONIVEL    
            int codigoEst1 = umMetodo.getCodigoPassandoString("TBLNOMESTACAO", "nomestacao", nomestacaosubstituida);
            umModeloNomeEstacao.setCodigo(codigoEst1);
            umModeloNomeEstacao.setNomestacao(nomestacaosubstituida);
            umModeloNomeEstacao.setStatus("DISPONIVEL");
            umControleNomeEstacao.atualizarStatusNomeEstacao(umModeloNomeEstacao);                                  
        }                
    }   
    
    public void PreencherTabela(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Estações Disponíveis"};
        try 
            {     
                 conexao.ExecutarPesquisaSQL(sql); 
                 while (conexao.rs.next())
                 {   
                    dados.add(new Object[]
                    {
                        conexao.rs.getString("nomestacao")

                    });
                 };
                ModeloTabela modelo = new ModeloTabela(dados, Colunas);
                jTabela.setModel(modelo);
                //define tamanho das colunas
                jTabela.getColumnModel().getColumn(0).setPreferredWidth(345);  //define o tamanho da coluna
                jTabela.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna        
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
            java.util.logging.Logger.getLogger(F_LISTAESTACOESDISPONIVEIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_LISTAESTACOESDISPONIVEIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_LISTAESTACOESDISPONIVEIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_LISTAESTACOESDISPONIVEIS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                F_LISTAESTACOESDISPONIVEIS dialog = new F_LISTAESTACOESDISPONIVEIS(new javax.swing.JFrame(), true);
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabela;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the nomeEstacaoSelecionada
     */
    public String getNomeEstacaoSelecionada() {
        return nomeEstacaoSelecionada;
    }

    /**
     * @param nomeEstacaoSelecionada the nomeEstacaoSelecionada to set
     */
    public void setNomeEstacaoSelecionada(String nomeEstacaoSelecionada) {
        this.nomeEstacaoSelecionada = nomeEstacaoSelecionada;
    }
}