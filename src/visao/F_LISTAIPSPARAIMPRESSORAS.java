/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import Dao.DAOIpsDisponiveis;
import biblioteca.Biblioteca;
import biblioteca.ModeloTabela;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import controle.CtrlIPsDisponiveis;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.IPDisponivel;
import modelo.Patrimonio;

public class F_LISTAIPSPARAIMPRESSORAS extends javax.swing.JDialog {

    ConnConexao conexao  = new ConnConexao();
    Patrimonio umPatrimonio                      = new Patrimonio();
    Biblioteca umaBiblio                         = new Biblioteca();
    IPDisponivel umModeloIPDisponivel            = new IPDisponivel();
    CtrlIPsDisponiveis  umControleIPDisponivel   = new CtrlIPsDisponiveis();
    DAOIpsDisponiveis   umIPDisponivelDAO        = new DAOIpsDisponiveis();
    String campo                                 = "ip";
    String tabela                                = "tblIpsDisponiveis";                                                             
    String status                                = "DISPONIVEL";
    String sqlDefault = "SELECT "+campo+" FROM "+tabela+" WHERE status = "+"'"+status+"' ORDER BY RIGHT('0000000' || ip, 7)";
    private String itemSelecionado;
    
    public F_LISTAIPSPARAIMPRESSORAS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        PreencherTabela(sqlDefault);
        this.setTitle("Selecione um "+campo+" para impressora");
        
        setResizable(false);   //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        jTabela.setForeground(Color.blue);
        jTabela.setFont(new Font("Arial", Font.BOLD, 14));  
    }   
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTabela = new javax.swing.JTable();

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

    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        //AO CLICAR EM UM REGISTRO DA TABELA PEGAR O NOME E CODIGO DO CLIENTE FECHAR E MOSTRAR O FORMULARIO DE CADASTRO DE TAREFAS COM OS DADOS SETADOS
        //JOptionPane.showMessageDialog(null, "NOME SELECIONADO...: "+jTabela.getValueAt(jTabela.getSelectedRow(), 0));
        setItemSelecionado((String) jTabela.getValueAt(jTabela.getSelectedRow(), 0));
        dispose();

    }//GEN-LAST:event_jTabelaMouseClicked
       
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        atualizarListaDeIPs();
    }//GEN-LAST:event_formWindowOpened

     private void atualizarListaDeIPs() {        
        sql = "select ip from tblPatrimonios where tipoid=3 and status='ATIVO' and ip IN(select ip from tblipsdisponiveis where status='DISPONIVEL')";
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        try {
            while (conexao.rs.next()) {
                String ipEncontrado = conexao.rs.getString("ip");
                //System.out.println("Ip encontrado...: "+ipEncontrado+"\n");
                int idIP = umaBiblio.buscarCodigoGenerico("tblipsdisponiveis", "ip", ipEncontrado);
                umModeloIPDisponivel.setCodigo(idIP);
                umModeloIPDisponivel.setIp(ipEncontrado);
                umModeloIPDisponivel.setStatus("INDISPONIVEL");
                umControleIPDisponivel.atualizarIPDisponivel(umModeloIPDisponivel);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela ips disponiveis para cadastro de impressoras!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }
        PreencherTabela(sqlDefault);        
    }
        
    public void PreencherTabela(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{campo+"s Disponíveis"};
        try 
            {     
                 conexao.ExecutarPesquisaSQL(sql); 
                 while (conexao.rs.next())
                 {   
                    dados.add(new Object[]
                    {
                        conexao.rs.getString(campo)

                    });
                 };
                ModeloTabela modelo = new ModeloTabela(dados, Colunas);
                jTabela.setModel(modelo);
                //define tamanho das colunas
                jTabela.getColumnModel().getColumn(0).setPreferredWidth(340);  //define o tamanho da coluna
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
            java.util.logging.Logger.getLogger(F_LISTAIPSPARAIMPRESSORAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_LISTAIPSPARAIMPRESSORAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_LISTAIPSPARAIMPRESSORAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_LISTAIPSPARAIMPRESSORAS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                F_LISTAIPSPARAIMPRESSORAS dialog = new F_LISTAIPSPARAIMPRESSORAS(new javax.swing.JFrame(), true);
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
     * @return the itemSelecionado
     */
    public String getItemSelecionado() {
        return itemSelecionado;
    }

    /**
     * @param itemSelecionado the itemSelecionado to set
     */
    public void setItemSelecionado(String itemSelecionado) {
        this.itemSelecionado = itemSelecionado;
    }
}