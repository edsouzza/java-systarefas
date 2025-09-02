package visao;

import biblioteca.Biblioteca;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import static biblioteca.VariaveisPublicas.codigoDepartamento;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.data;
import static biblioteca.VariaveisPublicas.tabela_da_lista;
import controle.CtrlSecoes;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.Secao;


public class F_REATIVARSECAO extends javax.swing.JFrame {
    String nomeSecao, motivoReativacao,descricaoReativacao,obs  = "";
    Integer codigo;
    ConnConexao conexao                  = new ConnConexao();
    CtrlSecoes umControleSecao           = new CtrlSecoes();
    Secao umModSecao                     = new Secao();
    Biblioteca umabiblio                 = new Biblioteca();
    MetodosPublicos umMetodo             = new MetodosPublicos();
    String sqlInativos                   = "select * from tblsecoes where status='INATIVO' order by nome";
    String dataDia                       = data;
    int codigoDoClienteSemUsuario;
    
    public F_REATIVARSECAO() {
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
        btnReativarSecao.setText("Reativar Seção");
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
        });
        jScrollPane2.setViewportView(jTabela);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(338, Short.MAX_VALUE)
                .addComponent(btnReativarSecao, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(334, 334, 334))
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

    public void reativarSecaoComPatrimonios()
    {        
        String dataDia = data;
        motivoReativacao  = JOptionPane.showInputDialog(null, "Entre com o motivo da Reativação!", "Reativa Seção e Patrimônios relacionados", 2);        
        
        while (motivoReativacao == null || motivoReativacao.equals("")) 
        {
            JOptionPane.showMessageDialog(this, "Digite um motivo válido e confirme!");  
            motivoReativacao  = JOptionPane.showInputDialog(null, "Entre com o motivo da reativação!", "Reativa Seção e Patrimônios relacionados", 2);   
        }
        //passando o motivo
        motivoReativacao  = "Reativado em "+dataDia+" \nMotivo "+nomeSecao+" : "+motivoReativacao.toLowerCase();

        //executando as reativaçoes no banco
        if(umControleSecao.reativarSecaoComPatrimonios(codigo, motivoReativacao)){
            
            JOptionPane.showMessageDialog(null, "A seção "+nomeSecao+" juntamente com seus patrimônios foram reativados com sucesso!");
        }    
        mostrarTitulo();
        
    }
    
    public void reativarSecaoComClientes()
    {        
        String dataDia = data;
        motivoReativacao  = JOptionPane.showInputDialog(null, "Entre com o motivo da Reativação!", "Reativa Seção e Patrimônios relacionados", 2);        
        
        while (motivoReativacao == null || motivoReativacao.equals("")) 
        {
            JOptionPane.showMessageDialog(this, "Digite um motivo válido e confirme!");  
            motivoReativacao  = JOptionPane.showInputDialog(null, "Entre com o motivo da reativação!", "Reativa Seção e Patrimônios relacionados", 2);   
        }
        //passando o motivo
        motivoReativacao  = "Reativado em "+dataDia+" \nMotivo "+nomeSecao+" : "+motivoReativacao.toLowerCase();

        //executando as reativaçoes no banco
        if(umControleSecao.reativarSecaoComClientes(codigo, motivoReativacao)){
            
            JOptionPane.showMessageDialog(null, "A seção "+nomeSecao+" juntamente com seus usuários foram reativados com sucesso!");
        }    
        mostrarTitulo();
        
    }
     
    public void reativarSecaoComPatrimoniosEClientes(){
        String dataDia = data;
        motivoReativacao  = JOptionPane.showInputDialog(null, "Entre com o motivo da Reativação!", "Reativa Seção e Patrimônios relacionados", 2);        
        
        while (motivoReativacao == null || motivoReativacao.equals("")) 
        {
            JOptionPane.showMessageDialog(this, "Digite um motivo válido e confirme!");  
            motivoReativacao  = JOptionPane.showInputDialog(null, "Entre com o motivo da reativação!", "Reativa Seção e Patrimônios relacionados", 2);   
        }
        //passando o motivo
        motivoReativacao  = "Reativado em "+dataDia+" \nMotivo "+nomeSecao+" : "+motivoReativacao.toLowerCase();

        //executando as reativaçoes no banco
        if(umControleSecao.reativarSecaoComPatrimonioEClientes(codigo, motivoReativacao)){
            JOptionPane.showMessageDialog(null, "A seção "+nomeSecao+" juntamente com seus patrimônios e usuários foram reativados com sucesso!");
        }    
        mostrarTitulo();
    }
    
    public void reativarSecao()
    {
       
    }
            
 
    
    private void btnReativarSecaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReativarSecaoActionPerformed
//        if (umabiblio.ConfirmouOperacao("Confirma o desejo de reativar esta seção juntamente com os patrimônios e seus usuários?", "Reativando seção com patrimônios e usuários!")){
//            reativarSecaoComPatrimoniosEClientes();
//        }else if (umabiblio.ConfirmouOperacao("Confirma o desejo de reativar esta seção com seus patrimônios?", "Reativando seção com patrimônios!")){
//            reativarSecaoComPatrimonios();
//        }else if (umabiblio.ConfirmouOperacao("Confirma o desejo de reativar esta seção com seus usuários?", "Reativando seção com usuários!")){
//            reativarSecaoComClientes();            
//        }
        
        if (umabiblio.ConfirmouOperacao("Confirma o desejo de reativar esta seção?", "Reativando seção!")){            
                                
            motivoReativacao  = JOptionPane.showInputDialog(null, "Entre com o motivo da Reativação!", "Reativa Seção", 2);        

            while (motivoReativacao == null || motivoReativacao.equals("")) 
            {
                JOptionPane.showMessageDialog(this, "Digite um motivo válido e confirme!");  
                motivoReativacao  = JOptionPane.showInputDialog(null, "Entre com o motivo da reativação!", "Reativa Seção", 2);   
            }
            //passando o motivo atual no campo OBS com o motivo da nova reativacao -> motivoReativacao
            String obsAtual = umMetodo.getStringPassandoCodigo("tblsecoes", "obs", codigo);
            motivoReativacao  = obsAtual+"\n"+dataDia+" "+": Reativado pelo seguinte motivo : "+motivoReativacao.toLowerCase();

            //executando as reativaçoes no banco
            int codigoDoClienteSemUsuario = umMetodo.getCodigoPassandoString("tblclientes", "nome", "SEM USUARIO "+nomeSecao);
            //System.out.println("codigo da seção : "+codigoDoClienteSemUsuario);
            
            if(umControleSecao.reativarSecao(codigo, motivoReativacao, codigoDepartamento)){
                umControleSecao.reativarClienteSemUsuario(codigoDoClienteSemUsuario);                
                //reativar o cliente denominado "SEM USUARIO DA SECAO"
                   
                JOptionPane.showMessageDialog(null, "A seção "+nomeSecao+" foi reativa com sucesso!");
            }    
                                    
            mostrarTitulo();
        }
        
    }//GEN-LAST:event_btnReativarSecaoActionPerformed

    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        
        //AO CLICAR EM UM REGISTRO PASSAR O VALOR DO CODIGO
        codigo = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0);
        btnReativarSecao.setEnabled(true);
        
        nomeSecao = umMetodo.retornarNomeSecao(codigo);
        
        //ABRIR LISTA DE DEPARTAMENTOS PARA ESCOLHER O DEPARTAMENTO A SER REATIVADO PARA A SEÇÃO ESCOLHIDA      
        tabela_da_lista = "TBLDEPARTAMENTOS";        
        F_LISTAPADRAO frm = new F_LISTAPADRAO(new javax.swing.JFrame(), true);
        frm.setVisible(true);          
        
    }//GEN-LAST:event_jTabelaMouseClicked

    private void mostrarTitulo()
    {
        PreencherTabela(sqlInativos);  ////abre o formulario mostrando todos os registro cadastrados na tabela INATIVOS 
        this.setTitle(umabiblio.mostrarTituloDoFormularioParaInativos());
        btnReativarSecao.setEnabled(false);
        
    }
    
    public void PreencherTabela(String sql)
   {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        String[] Colunas = new String[]
        {
            "Código", "Nome", "Email", "Pr", "Simproc", "Ramal"
        };
            try 
            {  
                 conexao.ExecutarPesquisaSQL(sql); 
                 while (conexao.rs.next())
                 {   
                    dados.add(new Object[]
                    {                        
                        conexao.rs.getInt("codigo"),
                        conexao.rs.getString("nome"),
                        conexao.rs.getString("email"),
                        conexao.rs.getString("pr"),
                        conexao.rs.getString("simproc"),
                        conexao.rs.getString("ramal")
                    });
                    };                        
                    ModeloTabela modelo = new ModeloTabela(dados, Colunas);
                    jTabela.setModel(modelo);
                    //define tamanho das colunas
                    jTabela.getColumnModel().getColumn(0).setPreferredWidth(50);  //define o tamanho da coluna
                    jTabela.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
                    jTabela.getColumnModel().getColumn(1).setPreferredWidth(200);
                    jTabela.getColumnModel().getColumn(1).setResizable(false);  
                    jTabela.getColumnModel().getColumn(2).setPreferredWidth(300);
                    jTabela.getColumnModel().getColumn(2).setResizable(false);  
                    jTabela.getColumnModel().getColumn(3).setPreferredWidth(80);
                    jTabela.getColumnModel().getColumn(3).setResizable(false);  
                    jTabela.getColumnModel().getColumn(4).setPreferredWidth(90);
                    jTabela.getColumnModel().getColumn(4).setResizable(false);  
                    jTabela.getColumnModel().getColumn(5).setPreferredWidth(90);
                    jTabela.getColumnModel().getColumn(5).setResizable(false);  
                    
                    //define propriedades da tabela
                    jTabela.getTableHeader().setReorderingAllowed(false);        //nao podera ser reorganizada
                    jTabela.setAutoResizeMode(jTabela.AUTO_RESIZE_OFF);          //nao será possivel redimencionar a tabela
                    jTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
                                    
            } catch (SQLException ex) {            
                   JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList!\nErro: " + ex.getMessage());
        }finally{
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
