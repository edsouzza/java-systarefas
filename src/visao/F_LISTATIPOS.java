/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import biblioteca.Biblioteca;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import conexao.ConnConexao;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.Patrimonio;
import static biblioteca.VariaveisPublicas.itemSelecionadoCadastro;
import static biblioteca.VariaveisPublicas.codigoTipoModelo;
import static biblioteca.VariaveisPublicas.naoTemModelo;

/**  
 *
 * @author EDI
 */
public class F_LISTATIPOS extends javax.swing.JDialog {

    ConnConexao conexao         = new ConnConexao();
    Biblioteca  umaBiblio       = new Biblioteca();
    Patrimonio umPatrimonio     = new Patrimonio();
    MetodosPublicos umMetodo    = new MetodosPublicos();
    String campo                = "tipo";
    String tabela               = "tblTIPOS";
    String status               = "ATIVO";
    String sqlDefault           = "SELECT tipo FROM tbltipos WHERE status = 'ATIVO' and tipopatrimonio='PATRIMONIO' ORDER BY tipo";
    
    private String itemSelecionado;
    
    public F_LISTATIPOS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Selecione o tipo de equipamento para cadastrar");
        initComponents();        
        PreencherTabela(sqlDefault);        
        
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
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(546, 327));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        //AO CLICAR EM UM REGISTRO DA TABELA PEGAR O NOME E CODIGO DO CLIENTE FECHAR E MOSTRAR O FORMULARIO DE CADASTRO DE TAREFAS COM OS DADOS SETADOS
        //JOptionPane.showMessageDialog(null, "NOME SELECIONADO...: "+jTabela.getValueAt(jTabela.getSelectedRow(), 0));
        setItemSelecionado((String) jTabela.getValueAt(jTabela.getSelectedRow(), 0));        
        //JOptionPane.showMessageDialog(null, "TIPO SELECIONADO: "+jTabela.getValueAt(jTabela.getSelectedRow(), 0)); 
        itemSelecionadoCadastro = (String) jTabela.getValueAt(jTabela.getSelectedRow(), 0); 
        
        //Verificando se equipamento tem um modelo obrigatoriamente cadastrado
        int codItemSelecionado = umMetodo.getCodigoPassandoString("tbltipos", "tipo", itemSelecionadoCadastro);   
        codigoTipoModelo       = codItemSelecionado;
        if(!umMetodo.tipoTemModeloCadastrado(codItemSelecionado))
        {
            JOptionPane.showMessageDialog(null,itemSelecionadoCadastro+" não tem um modelo cadastrado, cancele o cadastro em andamento, saia e cadastre um modelo para este equipamento!","Tipo sem modelo cadastrado!",2);                      
            //essa variavel informa que equipto nao tem modelo cadastrado para abortar o cadastro em andamento e nao abrir a lista de usuarios, 
            //fechando assim o frmPatrimonios e abrindo de Modelos para cadastro. A lista de usuarios só abrirá se naoTemModelo estiver como false.
            naoTemModelo = true; 
            
        }else{
            naoTemModelo = false; 
        }
       
        dispose(); 
       
    }//GEN-LAST:event_jTabelaMouseClicked

    
    public void PreencherTabela(String sql)
    {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{campo.toUpperCase()+"S DISPONÍVEIS"};
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
                jTabela.getColumnModel().getColumn(0).setPreferredWidth(510);  //define o tamanho da coluna
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
            java.util.logging.Logger.getLogger(F_LISTATIPOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_LISTATIPOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_LISTATIPOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_LISTATIPOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                F_LISTATIPOS dialog = new F_LISTATIPOS(new javax.swing.JFrame(), true);
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