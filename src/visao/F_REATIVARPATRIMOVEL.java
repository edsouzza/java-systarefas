package visao;

import biblioteca.Biblioteca;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.data;
import static biblioteca.VariaveisPublicas.sql;
import controle.CtrlPatrimovel;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.Patrimovel;


public class F_REATIVARPATRIMOVEL extends javax.swing.JFrame {
    String nomeSecao, motivoReativacao,descricaoReativacao,obs  = "";
    Integer codigo;
    ConnConexao conexao                 = new ConnConexao();
    Patrimovel umModPatrimovel          = new Patrimovel();
    CtrlPatrimovel ctrPatrimovel        = new CtrlPatrimovel();
    Biblioteca umabiblio                = new Biblioteca();
    MetodosPublicos umMetodo            = new MetodosPublicos();
    String sqlInativos                  = "select p.*, s.nome as secao, m.* from tblpatrimovel p, tblsecoes s, tblmodelos m where p.modeloid = m.codigo and p.secaoid=s.codigo and p.status='INATIVO' ORDER BY m.modelo";
    
    public F_REATIVARPATRIMOVEL() {
        initComponents();
        setResizable(false);   //desabilitando o redimencionamento da tela   
        jTabela.setFont(new Font("Arial", Font.BOLD, 12));  
        mostrarTitulo();
        
        
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
        btnReativarSecao = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabela = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reativar Seção juntamente com seus Clientes e Micros");

        btnReativarSecao.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnReativarSecao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_Ok.gif"))); // NOI18N
        btnReativarSecao.setText("Reativar Patrimônio");
        btnReativarSecao.setToolTipText("");
        btnReativarSecao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReativarSecao.setEnabled(false);
        btnReativarSecao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReativarSecaoActionPerformed(evt);
            }
        });

        jTabela.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTabelaMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(jTabela);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 810, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReativarSecao, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(323, 323, 323))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReativarSecao, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void reativarPatrimovel()
    {        
        String dataDia = data;
        motivoReativacao  = JOptionPane.showInputDialog(null, "Entre com o motivo da Reativação!", "Reativa Patrimônios", 2);        
        
        while (motivoReativacao == null || motivoReativacao.equals("")) 
        {
            JOptionPane.showMessageDialog(this, "Digite um motivo válido e confirme!");  
            motivoReativacao  = JOptionPane.showInputDialog(null, "Entre com o motivo da reativação!", "Reativa Patrimônios", 2);   
        }
        //passando o motivo
        motivoReativacao  = "Reativado em "+dataDia+" \nMotivo : "+motivoReativacao.toLowerCase();

        //executando as reativaçoes no banco
        if(ctrPatrimovel.reativarPatrimovel(codigo, motivoReativacao)){
            JOptionPane.showMessageDialog(null, "O patrimovel foi reativado com sucesso!");
        }    
        mostrarTitulo();
        
    }
                
    private void mostrarTitulo()
    {
        PreencherTabela(sqlInativos);  ////abre o formulario mostrando todos os registro cadastrados na tabela INATIVOS 
        this.setTitle(umabiblio.mostrarTituloDoFormularioParaInativos());
        btnReativarSecao.setEnabled(false);
        
    }
    
    private void btnReativarSecaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReativarSecaoActionPerformed
        if (umabiblio.ConfirmouOperacao("Confirma o desejo de reativar este patrimônio?", "Reativando patrimônio!")){
            reativarPatrimovel();
        }
        
    }//GEN-LAST:event_btnReativarSecaoActionPerformed

    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        
        //AO CLICAR EM UM REGISTRO PASSAR O VALOR DO CODIGO
        codigo = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0);
        btnReativarSecao.setEnabled(true);
        int secaoid = 0;        
        
        sql = "SELECT secaoid FROM tblpatrimovel WHERE codigo="+codigo+"";        
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);          
        try {          
            conexao.rs.first(); 
            //mostrando os dados do registro selecionado nos edits
            secaoid = conexao.rs.getInt("secaoid");
            nomeSecao = (umMetodo.getStringPassandoCodigo("tblsecoes","nome",secaoid)); 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro ao tentar selecionar a seção AGORA !\nERRO:"+ex.getMessage());
        }finally{
             conexao.desconectar();
        }

        
    }//GEN-LAST:event_jTabelaMouseClicked

    private void jTabelaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabelaMouseEntered

   public void PreencherTabela(String sql) {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Cód.", "Serial", "Chapa", "Descrição", "Status"};
        try {
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                dados.add(new Object[]{
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("serial"),
                    conexao.rs.getString("chapa"),
                    conexao.rs.getString("descricao"),
                    conexao.rs.getString("status")
                });
            };

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabela.setModel(modelo);
            //define tamanho das colunas
            jTabela.getColumnModel().getColumn(0).setPreferredWidth(40);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabela.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTabela.getColumnModel().getColumn(1).setResizable(false);
            jTabela.getColumnModel().getColumn(2).setPreferredWidth(110);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(2).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabela.getColumnModel().getColumn(3).setPreferredWidth(500);
            jTabela.getColumnModel().getColumn(3).setResizable(false);
            jTabela.getColumnModel().getColumn(4).setPreferredWidth(60);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReativarSecao;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabela;
    // End of variables declaration//GEN-END:variables
}
