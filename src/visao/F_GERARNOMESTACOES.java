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


public class F_GERARNOMESTACOES extends javax.swing.JDialog  {

    ConnConexao        conexao                  = new ConnConexao();
    Biblioteca         umaBiblio                = new Biblioteca();
    MetodosPublicos    umMetodo                 = new MetodosPublicos();  
    NomeEstacao        umModeloNomeEstacao      = new NomeEstacao();
    CtrlNomeEstacao    umControleNomeEstacao    = new CtrlNomeEstacao();
    DAONomeEstacao     umEstacaoNomeEstacaoAO   = new DAONomeEstacao();
    
    String sqlEstacoesPorDepto, sqlEstacoesDisponiveis, nomeDepto;
    String sqlVazia   = "SELECT * FROM TBLNOMESTACAO WHERE CODIGO=0";
    boolean selecionouItem;
    int numInicial, numFinal, codigoNomeEstacao;
            

    public F_GERARNOMESTACOES() 
    {
        initComponents();
        setResizable(false);   //desabilitando o redimencionamento da tela     
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar     
        this.setTitle("Disponibilização de nomes de rede para cadastrar estações");
        Leitura();       
           
        umaBiblio.configurarBotoes(btnGerarNomes);         
        
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
                txtQDE.setEnabled(true);
                txtQDE.requestFocus();
                // Exibe o item selecionado no console
                //System.out.println("Departamento : " + nomeDepartamento);               
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
    
    private int getProximoNumeroEstacao(){        
        int ultimoNome = 0;    
        
        if(nomeDepartamento.equals("BIBLIOTECA")){
          nomeDepartamento = "CEJUR";
        }
         
        ultimoNome = umMetodo.gerarProximoNumeroEstacao(nomeDepartamento);        
        return ultimoNome;
    }
    
    public static ArrayList<Integer> gerarArrayList(int inicio, int fim) {
        //Retorna uma lista de numeros inteiros que estejam entre o inicio e o fim passados
        ArrayList<Integer> lista = new ArrayList<>();

        for (int i = inicio; i <= fim; i++) {
            lista.add(i);
        }
        return lista;
    }
    
    public ArrayList<String> gerarNomesEstacoes() {       
        numInicial   = getProximoNumeroEstacao();
        numFinal = (numInicial + Integer.parseInt(txtQDE.getText())-1);    
         
        ArrayList listaNomes = new ArrayList();

        ArrayList<Integer> numeros = gerarArrayList(numInicial, numFinal);

        // Imprimir a ArrayList
        for (Integer numero : numeros) {
            //System.out.print("PGM"+nomeDepartamento+"C"+numero + "\n");
            listaNomes.add("PGM"+nomeDepartamento+"C"+numero);
        }
        return listaNomes;
    }
    
     private void gravarNomesNaTabela(){
         ArrayList<String> nomesEstacoes = gerarNomesEstacoes();

        // Imprime os nomes gerados
        for (String nome : nomesEstacoes) 
        {        
            umModeloNomeEstacao.setNomestacao(nome);
            umModeloNomeEstacao.setNumestacao(umMetodo.somenteDigitos(nome));
            umModeloNomeEstacao.setDepto(nomeDepartamento);
            umModeloNomeEstacao.setStatus("DISPONIVEL");
            umControleNomeEstacao.salvarNomeEstacao(umModeloNomeEstacao);              
            //System.out.print(nomesEstacoes);
        }
        
    }       
    
    private void mostrarEstacoesAdicionadasNaTabela(int pNumeInicial, String pDepto){
        String sqlNomesAdicionados  = "SELECT * FROM TBLNOMESTACAO WHERE numestacao >= "+pNumeInicial+" AND depto = '"+pDepto+"' AND status = 'DISPONIVEL' ORDER BY NUMESTACAO";        
        PreencherTabelaESTACOES(sqlNomesAdicionados);
    }
    
    private void mostrarTodosNomesEstacoesDisponiveisPorDepto(String pDepto){
        String sqlTodosNomesPorDepto  = "SELECT * FROM TBLNOMESTACAO WHERE  depto = '"+pDepto+"' AND status = 'DISPONIVEL' ORDER BY NUMESTACAO";        
        PreencherTabelaESTACOES(sqlTodosNomesPorDepto);
    }
       
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        btnGerarNomes = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTabelaESTACOES = new javax.swing.JTable();
        cmbDeptos = new javax.swing.JComboBox<String>();
        btnLimpar = new javax.swing.JButton();
        txtQDE = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnTodosDisponiveisDepto = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelPrincipal.setPreferredSize(new java.awt.Dimension(1024, 733));
        panelPrincipal.setLayout(null);

        btnGerarNomes.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnGerarNomes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TICK.PNG"))); // NOI18N
        btnGerarNomes.setText("GERAR");
        btnGerarNomes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGerarNomes.setEnabled(false);
        btnGerarNomes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarNomesActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnGerarNomes);
        btnGerarNomes.setBounds(290, 80, 160, 40);

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
        btnSair.setBounds(800, 80, 110, 40);

        jTabelaESTACOES.setAutoCreateRowSorter(true);
        jTabelaESTACOES.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabelaESTACOES.setToolTipText("");
        jTabelaESTACOES.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane3.setViewportView(jTabelaESTACOES);

        panelPrincipal.add(jScrollPane3);
        jScrollPane3.setBounds(10, 140, 900, 520);

        cmbDeptos.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmbDeptos.setForeground(new java.awt.Color(51, 51, 255));
        cmbDeptos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Departamento" }));
        cmbDeptos.setToolTipText("Escolha um departamento para filtrar");
        cmbDeptos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelPrincipal.add(cmbDeptos);
        cmbDeptos.setBounds(10, 80, 180, 40);

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
        btnLimpar.setBounds(630, 80, 160, 40);

        txtQDE.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtQDE.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQDE.setText("0");
        txtQDE.setEnabled(false);
        txtQDE.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtQDEFocusGained(evt);
            }
        });
        txtQDE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtQDEMouseClicked(evt);
            }
        });
        txtQDE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtQDEKeyPressed(evt);
            }
        });
        panelPrincipal.add(txtQDE);
        txtQDE.setBounds(200, 80, 80, 40);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ESCOLHA O DEPARTAMENTO E DIGITE A QUANTIDADE DE NOMES DESEJADA");
        panelPrincipal.add(jLabel1);
        jLabel1.setBounds(10, 20, 900, 14);

        btnTodosDisponiveisDepto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnTodosDisponiveisDepto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/calculator_add.png"))); // NOI18N
        btnTodosDisponiveisDepto.setText("Todos Disponiveis");
        btnTodosDisponiveisDepto.setToolTipText("");
        btnTodosDisponiveisDepto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTodosDisponiveisDepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTodosDisponiveisDeptoActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnTodosDisponiveisDepto);
        btnTodosDisponiveisDepto.setBounds(460, 80, 160, 40);

        jScrollPane1.setToolTipText("");

        jTextPane1.setEnabled(false);
        jScrollPane1.setViewportView(jTextPane1);

        panelPrincipal.add(jScrollPane1);
        jScrollPane1.setBounds(10, 0, 900, 60);

        getContentPane().add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 920, 660));

        setSize(new java.awt.Dimension(928, 708));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
          
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
       
        dispose();
        
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnGerarNomesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarNomesActionPerformed
        
       gerarNomesEstacoes(); 
       gravarNomesNaTabela();       
                 
       mostrarEstacoesAdicionadasNaTabela(numInicial, nomeDepartamento);
       
       JOptionPane.showMessageDialog(null, "Atenção os nomes foram gerados com sucesso, e se encontram a disposição para uso!","Cadastrado com sucesso!",2);
       
       btnGerarNomes.setEnabled(false);
       btnTodosDisponiveisDepto.setEnabled(false);
       cmbDeptos.setEnabled(false);
       txtQDE.setEnabled(false);
       btnLimpar.setEnabled(true);
       btnSair.setEnabled(false);
       
    }//GEN-LAST:event_btnGerarNomesActionPerformed
       
    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
                
        btnGerarNomes.setEnabled(false);         
        btnLimpar.setEnabled(false);
        btnSair.setEnabled(true);
        cmbDeptos.setSelectedIndex(-1);
        cmbDeptos.setEnabled(true);
        btnTodosDisponiveisDepto.setEnabled(true);
        txtQDE.setEnabled(false);
        txtQDE.setText("0");
        
        PreencherTabelaESTACOES(sqlVazia);
        
    }//GEN-LAST:event_btnLimparActionPerformed

    private void txtQDEFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQDEFocusGained
        txtQDE.selectAll();
    }//GEN-LAST:event_txtQDEFocusGained

    private void txtQDEKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQDEKeyPressed
        btnGerarNomes.setEnabled(true);
        btnTodosDisponiveisDepto.setEnabled(false);
    }//GEN-LAST:event_txtQDEKeyPressed

    private void txtQDEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtQDEMouseClicked
        txtQDE.selectAll();
    }//GEN-LAST:event_txtQDEMouseClicked

    private void btnTodosDisponiveisDeptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTodosDisponiveisDeptoActionPerformed
        if(cmbDeptos.getSelectedIndex() < 0){
            JOptionPane.showMessageDialog(null, "Escolha um Departamento para visualizar os nomes disponíveis!", "Departamento inválido!", 2);
        }else{
             if(nomeDepartamento.equals("BIBLIOTECA")){
                nomeDepartamento = "CEJUR";
             }
             mostrarTodosNomesEstacoesDisponiveisPorDepto(nomeDepartamento);   
             btnTodosDisponiveisDepto.setEnabled(false);
             cmbDeptos.setEnabled(false);
             txtQDE.setEnabled(false);
              btnLimpar.setEnabled(true);
        }
       
    }//GEN-LAST:event_btnTodosDisponiveisDeptoActionPerformed
         
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
    public javax.swing.JButton btnGerarNomes;
    public javax.swing.JButton btnLimpar;
    public javax.swing.JButton btnSair;
    public javax.swing.JButton btnTodosDisponiveisDepto;
    private javax.swing.JComboBox<String> cmbDeptos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTable jTabelaESTACOES;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTextField txtQDE;
    // End of variables declaration//GEN-END:variables
}
