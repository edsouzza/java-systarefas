package visao;

import biblioteca.TudoMaiusculas;
import conexao.ConnConexao;
import java.awt.Color;
import java.awt.Font;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import static biblioteca.VariaveisPublicas.tabela_da_lista;
import static biblioteca.VariaveisPublicas.cadastrando;
import static biblioteca.VariaveisPublicas.totalRegs;
import static biblioteca.VariaveisPublicas.assuntoSelecionado;
import controle.CtrlPatriTransferido;
import java.awt.event.KeyEvent;
import modelo.PatriTransferido;


public class F_LISTAPADRAOUMCAMPO extends javax.swing.JDialog {
    
    ConnConexao          conexao               = new ConnConexao();
    MetodosPublicos      umMetodo              = new MetodosPublicos();     
    
    PatriTransferido     objPatriTransferido   = new PatriTransferido();
    CtrlPatriTransferido ctrlPatriTransferido  = new CtrlPatriTransferido();    
    
    int qdeRegs                                = umMetodo.getQdeRegistrosNaTabela(tabela_da_lista);
    int icodigo = 0;
    
    public F_LISTAPADRAOUMCAMPO(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setFocusableWindowState(true); //corrigindo erro quando clico fora da lista
        initComponents();   
        
        setTitle(tabela_da_lista.substring(3, tabela_da_lista.length()));
        setResizable(false);   //desabilitando o redimencionamento da tela    
        
        txtPESQUISA.setDocument(new TudoMaiusculas());
        txtPESQUISA.setFont(new Font("Tahoma", Font.BOLD, 12));
        txtPESQUISA.setForeground(Color.red);
        
        jTabela.setForeground(Color.blue);
        jTabela.setFont(new Font("Arial", Font.BOLD, 12));
        
        this.setTitle("Lista de Assuntos para Cadastro");
               
        if (cadastrando) {
            setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar            
        }
        
    }
     
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jBoxPesquisar = new javax.swing.JLayeredPane();
        txtPESQUISA = new javax.swing.JTextField();
        btnLimparPesquisa = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabela = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jBoxPesquisar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxPesquisar.setName("panelDados"); // NOI18N

        txtPESQUISA.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPESQUISA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPESQUISAFocusGained(evt);
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
                .addComponent(txtPESQUISA, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jBoxPesquisarLayout.setVerticalGroup(
            jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxPesquisarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBoxPesquisarLayout.createSequentialGroup()
                        .addGap(0, 2, Short.MAX_VALUE)
                        .addComponent(txtPESQUISA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBoxPesquisar)
            .addComponent(jScrollPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jBoxPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(736, 529));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
 

    private void btnLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparPesquisaActionPerformed
        limparCampos();
    }//GEN-LAST:event_btnLimparPesquisaActionPerformed
    
    public void limparCampos() {
        txtPESQUISA.setText("");
        txtPESQUISA.setEnabled(true);
        btnLimparPesquisa.setEnabled(false);
        txtPESQUISA.requestFocus(); 
    }
       
    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        //AO CLICAR EM UM REGISTRO DA TABELA PEGAR O NOME E CODIGO DO CLIENTE FECHAR E MOSTRAR O FORMULARIO DE CADASTRO DE TAREFAS COM OS DADOS SETADOS
        if (jTabela.getSelectedRow() != -1) { // Certifica-se de que uma linha foi realmente selecionada
            assuntoSelecionado = (String) jTabela.getValueAt(jTabela.getSelectedRow(), 0);
            dispose(); // Fecha a janela atual
        }
    }//GEN-LAST:event_jTabelaMouseClicked
    
    private void filtrarPorDigitacao(String pPesq) 
    {if(tabela_da_lista.equals("TBLMEMOSTRANSFERIDOS")){
            PreencherTabelaPadrao("SELECT DISTINCT assunto FROM TBLMEMOSTRANSFERIDOS WHERE (assunto like '%" + pPesq + "%') ORDER BY assunto");                      
        }
        this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);
    }

    private void txtPESQUISAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyReleased
        filtrarPorDigitacao(txtPESQUISA.getText());
        btnLimparPesquisa.setEnabled(true);
        
    }//GEN-LAST:event_txtPESQUISAKeyReleased

    private void txtPESQUISAFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPESQUISAFocusGained
        //PARA MOSTRAR A LISTA SOLICITADA LOGO QUE ABRIR O FORMULARIO
        if(tabela_da_lista.equals("TBLMEMOSTRANSFERIDOS")){
            PreencherTabelaPadrao("SELECT DISTINCT assunto FROM TBLMEMOSTRANSFERIDOS ORDER BY assunto");
        }else{  
         filtrarPorDigitacao("");
        }
    }//GEN-LAST:event_txtPESQUISAFocusGained

    private void txtPESQUISAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtPESQUISA.setText(null);
            txtPESQUISA.requestFocus();
        }
    }//GEN-LAST:event_txtPESQUISAKeyPressed
    
    public void PreencherTabelaPadrao(String sql) {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Assunto"};
        conexao.ExecutarPesquisaSQL(sql);
        try {
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                dados.add(new Object[]{
                    conexao.rs.getString("assunto")
                });
                totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
            };
            
            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabela.setModel(modelo);
            //define tamanho das colunas
            jTabela.getColumnModel().getColumn(0).setPreferredWidth(700);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(0).setResizable(false); 

            //define propriedades da tabela
            jTabela.getTableHeader().setReorderingAllowed(false);        //nao podera ser reorganizada
            jTabela.setAutoResizeMode(jTabela.AUTO_RESIZE_OFF);          //nao será possivel redimencionar a tabela
            jTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha 

        } catch (SQLException ex) {
            //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
            JOptionPane.showMessageDialog(null, "Erro ao preencher a lista padrão!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }        
    }          
    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimparPesquisa;
    private javax.swing.JLayeredPane jBoxPesquisar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabela;
    private javax.swing.JTextField txtPESQUISA;
    // End of variables declaration//GEN-END:variables
}