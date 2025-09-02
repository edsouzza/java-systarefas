package visao;

import biblioteca.Biblioteca;
import biblioteca.ModeloTabela;
import biblioteca.TudoMaiusculas;
import biblioteca.MetodosPublicos;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.selecionouCliente;
import static biblioteca.VariaveisPublicas.editandoRegistro;
import static biblioteca.VariaveisPublicas.cadastrandoEquipamento;
import static biblioteca.VariaveisPublicas.reativandoEquipamento;
import controle.CtrlCliente;
import static biblioteca.VariaveisPublicas.codigoCliente;
import static biblioteca.VariaveisPublicas.nomeCliente;
import static biblioteca.VariaveisPublicas.codigoSecao;
import static biblioteca.VariaveisPublicas.nomeSecao;
import static biblioteca.VariaveisPublicas.consultou;
import static biblioteca.VariaveisPublicas.nomeDepartamento;
import static biblioteca.VariaveisPublicas.rfCliente;
import static biblioteca.VariaveisPublicas.idDepto;
import static biblioteca.VariaveisPublicas.itemSelecionadoCadastro;
import static biblioteca.VariaveisPublicas.totalRegs;
import controle.CtrlSecoes;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.Cliente;
import modelo.Secao;

public class F_LISTACLIENTESPARACADASTRO extends javax.swing.JDialog {

    ConnConexao conexao         = new ConnConexao();
    Secao objModSecao           = new Secao();
    CtrlSecoes ctrlSecao        = new CtrlSecoes();
    Cliente objCliente          = new Cliente();
    CtrlCliente ctrCliente      = new CtrlCliente();
    Biblioteca umabiblio        = new Biblioteca();
    MetodosPublicos umMetodo    = new MetodosPublicos();

    int ind, codItemSelecionado = 0;
    String nome, nomeClienteFiltrado = null;  
      

    public F_LISTACLIENTESPARACADASTRO(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setResizable(false);   //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        jTabela.setForeground(Color.blue);
        txtPESQUISA.setDocument(new TudoMaiusculas());
        txtPESQUISA.setFont(new Font("TimesRoman", Font.BOLD, 12));
        txtPESQUISA.setForeground(Color.red);
        jTabela.setFont(new Font("Arial", Font.BOLD, 12));
        
        mostrarUsuarios();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBoxPesquisar = new javax.swing.JLayeredPane();
        txtPESQUISA = new javax.swing.JTextField();
        btnLimparPesquisa = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabela = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Selecione um usuário para cadastrar");

        jBoxPesquisar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxPesquisar.setName("panelDados"); // NOI18N

        txtPESQUISA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPESQUISAMouseClicked(evt);
            }
        });
        txtPESQUISA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPESQUISAKeyPressed(evt);
            }
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
                .addComponent(txtPESQUISA, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jBoxPesquisarLayout.setVerticalGroup(
            jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxPesquisarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPESQUISA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jBoxPesquisar.setLayer(txtPESQUISA, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxPesquisar.setLayer(btnLimparPesquisa, javax.swing.JLayeredPane.DEFAULT_LAYER);

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
            .addGap(0, 825, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 62, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jBoxPesquisar)
                        .addComponent(jScrollPane2))
                    .addGap(0, 68, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 586, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 48, Short.MAX_VALUE)
                    .addComponent(jBoxPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(6, 6, 6)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 48, Short.MAX_VALUE)))
        );

        setSize(new java.awt.Dimension(841, 625));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mostrarUsuarios() 
    {                       
        //JOptionPane.showMessageDialog(null, "ITEM SELECIONADO FOI "+itemSelecionadoCadastro);
        
        codItemSelecionado = umMetodo.getCodigoPassandoString("tbltipos", "tipo", itemSelecionadoCadastro);
        //JOptionPane.showMessageDialog(null, "CODIGO ITEM SELECIONADO "+codItemSelecionado);
        
        if(umMetodo.TipoTemClientesVirtuais(codItemSelecionado)){
            //JOptionPane.showMessageDialog(null, "ITEM SELECIONADO TEM CLIENTES VIRTUAIS");
            
            txtPESQUISA.setText(itemSelecionadoCadastro+" ");
            filtrarPorDigitacao(txtPESQUISA.getText());
            this.setTitle("Selecione um local para a "+itemSelecionadoCadastro);
            
        }else{
            //JOptionPane.showMessageDialog(null, "ITEM SELECIONADO NÃO TEM CLIENTES VIRTUAIS");
            filtrarPorDigitacao(txtPESQUISA.getText());
            this.setTitle("Selecione um local para a "+itemSelecionadoCadastro);
            
        }                    
                   
    }

    private void btnLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparPesquisaActionPerformed
        limparCampos();
    }//GEN-LAST:event_btnLimparPesquisaActionPerformed

    private void filtrarPorDigitacao(String pPesq) 
    {
        String sqlTodos     = ("Select c.codigo, c.nome, c.rf, c.deptoid, s.nome as secao, c.tipo from tblclientes c, tblsecoes s, tbldepartamentos d where c.secaoid=s.codigo and c.deptoid=d.codigo and c.status='ATIVO' and c.nome like '%" + pPesq + "%' order by c.nome");    
        String sqlVirtuais  = ("Select c.codigo, c.nome, c.rf, c.deptoid, s.nome as secao, c.tipo from tblclientes c, tblsecoes s, tbldepartamentos d where c.secaoid=s.codigo and c.deptoid=d.codigo and  c.tipovirtualid="+codItemSelecionado+"  and c.status='ATIVO' and c.nome like '%" + pPesq + "%' order by c.nome");    
        
        if (cadastrandoEquipamento || editandoRegistro) 
        {
           PreencherTabela(sqlTodos);        
           
        }else{
           PreencherTabela(sqlVirtuais);       
           
        }              
    }

    public void limparCampos() {
        
        txtPESQUISA.setText(null);
        txtPESQUISA.setEnabled(true);
        btnLimparPesquisa.setEnabled(false);
        txtPESQUISA.requestFocus();
        
        
        if(umMetodo.TipoTemClientesVirtuais(codItemSelecionado)){            
            
            txtPESQUISA.setText(itemSelecionadoCadastro+" ");
            filtrarPorDigitacao(txtPESQUISA.getText());
            this.setTitle("Selecione um local para a "+itemSelecionadoCadastro);
            
        }else{
           
            filtrarPorDigitacao(txtPESQUISA.getText());
            
        }            
       
    }

    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        //AO CLICAR EM UM REGISTRO DA TABELA PEGAR O NOME E CODIGO DO CLIENTE FECHAR E MOSTRAR O FORMULARIO DE CADASTRO DE TAREFAS COM OS DADOS SETADOS
        //JOptionPane.showMessageDialog(null, "NOME SELECIONADO...: "+jTabela.getValueAt(jTabela.getSelectedRow(), 0));
        objCliente.setNome((String) jTabela.getValueAt(jTabela.getSelectedRow(), 1)); //pegando o nome selecionado e setando no modelo
        ctrCliente.pesquisarCliente(objCliente);  //chamando a função e passando como parametro o nome selecinado acima

        //pegando o retorno da funcao e setando nas variaveis globais
        codigoCliente = objCliente.getCodigo();
        codigoSecao   = objCliente.getSecaoid();

        //pesquisando o nome da secao
        objModSecao.setCodigo(codigoSecao);
        ctrlSecao.pesquisarSecao(objModSecao);
        nomeSecao   = objModSecao.getNome();
        nomeCliente = objCliente.getNome();
        rfCliente   = objCliente.getRf();

        if (codigoCliente > 0) {
            consultou = true;
        }

        selecionouCliente      = true;
        editandoRegistro       = false;
        cadastrandoEquipamento = false;
        reativandoEquipamento  = false;
        nomeDepartamento       = umMetodo.retornaDepto(nomeSecao);
        idDepto                = umMetodo.getCodigoPassandoString("tbldepartamentos", "nome", nomeDepartamento);
                
        
        //JOptionPane.showMessageDialog(null, "CODIGO DO DEPARTAMENTO : "+idDepto+"\n"+"NOME DEPTO : "+nomeDepartamento);
        
        dispose();
                           
        //As linhas abaixo servem para mostrar o que esta sendo setado pelo cadastrante
        /*        JOptionPane.showMessageDialog(null,
                                                    "Seção :" +nomeSecao+"\n"+
                                                    "Código da Seção :" +codigoSecao+"\n"+
                                                    "Usuário :" +nomeCliente+"\n" +
                                                    "Código do Usuário :" +codigoCliente+"\n"+
                                                    "Nome Depto :" +nomeDepartamento+"\n"    
                );  
        */

    }//GEN-LAST:event_jTabelaMouseClicked

    private void txtPESQUISAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyReleased
        filtrarPorDigitacao(txtPESQUISA.getText());
    }//GEN-LAST:event_txtPESQUISAKeyReleased

    private void txtPESQUISAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyPressed
        //se teclar enter estando dentro da pesquisa limpar a pesquisa
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtPESQUISA.setText(null);
        }
        btnLimparPesquisa.setEnabled(true);
    }//GEN-LAST:event_txtPESQUISAKeyPressed

    private void txtPESQUISAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPESQUISAMouseClicked
        txtPESQUISA.selectAll();
    }//GEN-LAST:event_txtPESQUISAMouseClicked

    public void PreencherTabela(String sql) {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Usuário"};
        try {
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                dados.add(new Object[]{
                    conexao.rs.getString("codigo"),
                    conexao.rs.getString("nome")
                });
                 totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
            };
            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabela.setModel(modelo);
            //define tamanho das colunas
            jTabela.getColumnModel().getColumn(0).setPreferredWidth(50);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna        
            jTabela.getColumnModel().getColumn(1).setPreferredWidth(611);  //define o tamanho da coluna 480
            jTabela.getColumnModel().getColumn(1).setResizable(false);    //nao será possivel redimencionar a coluna    
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
            java.util.logging.Logger.getLogger(F_LISTACLIENTESPARACADASTRO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_LISTACLIENTESPARACADASTRO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_LISTACLIENTESPARACADASTRO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_LISTACLIENTESPARACADASTRO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                F_LISTACLIENTESPARACADASTRO dialog = new F_LISTACLIENTESPARACADASTRO(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnLimparPesquisa;
    private javax.swing.JLayeredPane jBoxPesquisar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabela;
    private javax.swing.JTextField txtPESQUISA;
    // End of variables declaration//GEN-END:variables
}