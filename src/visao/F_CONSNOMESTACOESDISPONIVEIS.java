package visao;

import Dao.DAONomeEstacao;
import conexao.ConnConexao;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import biblioteca.Biblioteca;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import static biblioteca.VariaveisPublicas.contador;
import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.totalRegs;
import controle.CtrlNomeEstacao;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.NomeEstacao;


public class F_CONSNOMESTACOESDISPONIVEIS extends javax.swing.JDialog  {

    ConnConexao        conexao                  = new ConnConexao();
    Biblioteca         umaBiblio                = new Biblioteca();
    MetodosPublicos    umMetodo                 = new MetodosPublicos();  
    NomeEstacao        umModeloNomeEstacao      = new NomeEstacao();
    CtrlNomeEstacao    umControleNomeEstacao    = new CtrlNomeEstacao();
    DAONomeEstacao     umEstacaoNomeEstacaoAO   = new DAONomeEstacao();
        
    String inicioRange, finalRange, estacaoInicial, estacaoFinal, strRange, sqlCmb, nomeSecao, nomeDepto, titulo, caminhoTXT, linha, scodigo, snomestacao,sqlPesquisaNomesUtilizados = "";  
    String sqlDisponiveis        = "SELECT * FROM TBLNOMESTACAO WHERE STATUS='DISPONIVEL' ORDER BY NUMESTACAO";
    String sqlVazia              = "SELECT * FROM TBLNOMESTACAO WHERE CODIGO=0";
    int codDepto, codigoEstacao;
    boolean selecionouDepto, disponivel;    
            

    public F_CONSNOMESTACOESDISPONIVEIS() 
    {
        initComponents();
        setResizable(false);   //desabilitando o redimencionamento da tela     
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar        
                
        Leitura();       
           
        umaBiblio.configurarBotoes(btnDisponiveis);
        umaBiblio.configurarBotoes(btnLimparPesquisa);   
        umaBiblio.configurarBotoes(btnSair);   
        
        //cofigurações 
        jTabelaESTACOES.setFont(new Font("TimesRoman", Font.BOLD, 12));
        lblTITULO.setFont(new Font("TimesRoman", Font.BOLD, 12));
        jTabelaESTACOES.setForeground(Color.blue);  
        lblTITULO.setForeground(Color.blue);  
        
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
                nomeDepto = (String) cmbDeptos.getSelectedItem(); 
                btnDisponiveis.setEnabled(true);
                btnUtilizados.setEnabled(true);
                btnLimparPesquisa.setEnabled(true); 
                
                // Exibe o item selecionado no console
                //System.out.println("Departamento : " + nomeDepartamento);               
            }
        });
        
    }  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        btnDisponiveis = new javax.swing.JButton();
        cmbDeptos = new javax.swing.JComboBox<String>();
        btnLimparPesquisa = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        lblTITULO = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTabelaESTACOES = new javax.swing.JTable();
        btnUtilizados = new javax.swing.JButton();
        txtNOMESTACAO = new javax.swing.JTextField();

        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelPrincipal.setPreferredSize(new java.awt.Dimension(1024, 733));
        panelPrincipal.setLayout(null);

        btnDisponiveis.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnDisponiveis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_pesquisa.gif"))); // NOI18N
        btnDisponiveis.setText("Disponíveis");
        btnDisponiveis.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDisponiveis.setEnabled(false);
        btnDisponiveis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisponiveisActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnDisponiveis);
        btnDisponiveis.setBounds(320, 10, 120, 33);

        cmbDeptos.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cmbDeptos.setForeground(new java.awt.Color(51, 51, 255));
        cmbDeptos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<ESCOLHA A SEÇÃO>" }));
        cmbDeptos.setToolTipText("Escolha um departamento para filtrar");
        cmbDeptos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbDeptos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDeptosItemStateChanged(evt);
            }
        });
        panelPrincipal.add(cmbDeptos);
        cmbDeptos.setBounds(10, 10, 300, 30);

        btnLimparPesquisa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLimparPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_limpar.gif"))); // NOI18N
        btnLimparPesquisa.setText("Limpar");
        btnLimparPesquisa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimparPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparPesquisaActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnLimparPesquisa);
        btnLimparPesquisa.setBounds(580, 10, 120, 33);

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
        btnSair.setBounds(710, 10, 120, 33);

        lblTITULO.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTITULO.setText("PRÓXIMO NOME DE ESTAÇÃO DE");
        panelPrincipal.add(lblTITULO);
        lblTITULO.setBounds(10, 60, 820, 14);

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
        jScrollPane3.setBounds(10, 120, 820, 450);

        btnUtilizados.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnUtilizados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sairPrograma.gif"))); // NOI18N
        btnUtilizados.setText("Utilizados");
        btnUtilizados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUtilizados.setEnabled(false);
        btnUtilizados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUtilizadosActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnUtilizados);
        btnUtilizados.setBounds(450, 10, 120, 33);

        txtNOMESTACAO.setEditable(false);
        txtNOMESTACAO.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNOMESTACAO.setForeground(new java.awt.Color(255, 0, 0));
        panelPrincipal.add(txtNOMESTACAO);
        txtNOMESTACAO.setBounds(10, 80, 820, 30);

        getContentPane().add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 830, 570));

        setSize(new java.awt.Dimension(852, 622));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void popularComboSecoes()
    {
        //Populando o combo com os nomes dos departamentos
        sqlCmb = "select nome from tbldepartamentos where status='ATIVO' order by nome";
        umMetodo.PreencherComboVariandoTipo(cmbDeptos, sqlCmb, "NOME");        
        cmbDeptos.setSelectedIndex(-1); 
    }
    
    private void Leitura()
    {       
        
        popularComboSecoes();               
        contador  = 0; 
        btnDisponiveis.setEnabled(false);
        btnUtilizados.setEnabled(false);  
        btnLimparPesquisa.setEnabled(false);  
        lblTITULO.setText("SELECIONE UM DEPARTAMENTO ACIMA E DEFINA UMA CONSULTA ATRAVÉS DOS BOTÕES DE PESQUISA");        
        titulo = "SELECIONE UM DEPARTAMENTO PARA CONSULTAR NOMES DE ESTAÇÕES DISPONÍVEIS PARA CADASTRO";        
        this.setTitle(titulo);  
        PreencherTabelaESTACOES(sqlVazia);
        
    }    
    
    private void LimparPesquisa()
    {
       lblTITULO.setText("NOMES DE ESTAÇÕES DISPONÍVEIS PARA CADASTRO");
       
       btnLimparPesquisa.setEnabled(false);
       cmbDeptos.setEnabled(true);
       cmbDeptos.setSelectedIndex(-1); 
       txtNOMESTACAO.setText(null);
       txtNOMESTACAO.setEditable(false);          
       lblTITULO.setForeground(Color.blue);  
       Leitura();        
    }
    
     public void PesquisarUtilizados(String pDepto) 
    {                     
        int umDeptoid = umMetodo.getCodigoPassandoString("tbldepartamentos", "nome", pDepto);        
                
        if(pDepto.equals("CGGM"))
        {
             sqlPesquisaNomesUtilizados = "SELECT p.codigo, p.estacao, p.serie, t.tipo, s.nome as secao FROM tblpatrimonios p, tbltipos t, tblsecoes s WHERE "
             + "s.codigo=p.secaoid and t.codigo=p.tipoid and p.deptoid = '"+umDeptoid+"' and p.estacao like '%PGMCGGMC%' and p.estacao<>'PGMCGGMC000' "
             + "and p.status='ATIVO' ORDER BY RIGHT('000000000000' || p.estacao, 12)";  
        }else if(pDepto.equals("CEJUR")){
             sqlPesquisaNomesUtilizados = "SELECT p.codigo, p.estacao, p.serie, t.tipo, s.nome as secao FROM tblpatrimonios p, tbltipos t, tblsecoes s WHERE "
             + "s.codigo=p.secaoid and t.codigo=p.tipoid and p.deptoid = '"+umDeptoid+"' and p.estacao like '%PGMCEJURC%' and p.estacao<>'PGMCEJURC00' "
             + "and p.status='ATIVO' ORDER BY RIGHT('000000000000' || p.estacao, 12)";  
        }else if(pDepto.equals("CEJUSC")){
             sqlPesquisaNomesUtilizados = "SELECT p.codigo, p.estacao, p.serie, t.tipo, s.nome as secao FROM tblpatrimonios p, tbltipos t, tblsecoes s WHERE "
             + "s.codigo=p.secaoid and t.codigo=p.tipoid and p.deptoid = '"+umDeptoid+"' and p.estacao like '%PGMCEJUSCC%' and p.estacao<>'PGMCEJUSCC0' "
             + "and p.status='ATIVO' ORDER BY RIGHT('000000000000' || p.estacao, 12)";  
        }else if(pDepto.equals("PFM")){
             sqlPesquisaNomesUtilizados = "SELECT p.codigo, p.estacao, p.serie, t.tipo, s.nome as secao FROM tblpatrimonios p, tbltipos t, tblsecoes s WHERE "
             + "s.codigo=p.secaoid and t.codigo=p.tipoid and p.deptoid = '"+umDeptoid+"' and p.estacao like '%PGMPFMC%' and p.estacao<>'PGMPFMC00' "
             + "and p.status='ATIVO' ORDER BY RIGHT('000000000000' || p.estacao, 12)"; 
        }else if(pDepto.equals("BIBLIOTECA")){
             sqlPesquisaNomesUtilizados = "SELECT p.codigo, p.estacao, p.serie, t.tipo, s.nome as secao FROM tblpatrimonios p, tbltipos t, tblsecoes s WHERE "
             + "s.codigo=p.secaoid and t.codigo=p.tipoid and p.deptoid = '"+umDeptoid+"' and p.estacao like '%PGMCEJURC%' and p.estacao<>'PGMCEJURC00' "
             + "and p.status='ATIVO' ORDER BY RIGHT('000000000000' || p.estacao, 12)"; 
        }
                                          
        PreencherTabelaESTACOESUTILIZADAS(sqlPesquisaNomesUtilizados);             
         
        btnDisponiveis.setEnabled(false);
        btnLimparPesquisa.setEnabled(true);   
        cmbDeptos.setEnabled(false);
        
        //JOptionPane.showMessageDialog(rootPane,"TOTAL DE REGISTROS ENCONTRADOS : "+ String.valueOf(totalRegs));        
        titulo = pDepto+" - TOTAL DE ESTAÇÕES/MICROS : "+totalRegs+"";          
        this.setTitle(titulo);       
        
        if(totalRegs == 0){
          
            JOptionPane.showMessageDialog(null, "Não foram encontrados registros para "+pDepto+"!","Nenhum registro encontrado!",2);
            btnLimparPesquisaActionPerformed(null);
        }
    }     
    
    public void Pesquisar(String pDepto) 
    {                            
        String sqlDisponiveisPorInativacao = "SELECT * FROM TBLNOMESTACAO WHERE status='DISPONIVEL' AND Depto = '"+pDepto+"' ORDER BY numestacao";   
        PreencherTabelaESTACOES(sqlDisponiveisPorInativacao);
        txtNOMESTACAO.setText(umMetodo.gerarProximoNomestacao(nomeDepto)); 
        lblTITULO.setText("NOMES DE ESTAÇÕES DISPONÍVEIS PARA CADASTRO : "+nomeDepto);

        btnLimparPesquisa.setEnabled(true);
        btnDisponiveis.setEnabled(false);
        txtNOMESTACAO.setEditable(false);
        cmbDeptos.setEnabled(false);
    }        
      
    private void btnDisponiveisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisponiveisActionPerformed

        disponivel = true;        
        lblTITULO.setForeground(Color.BLUE);  
        btnDisponiveis.setEnabled(false);
        btnUtilizados.setEnabled(false);   
        
        if(nomeDepto.equals("BIBLIOTECA")){
            nomeDepto = "CEJUR";
        }
             
        codDepto  = umaBiblio.buscarCodigoGenerico("tbldepartamentos", "nome", nomeDepto);
        
        if(contador == 1)
        {
            Pesquisar(nomeDepto);            
        }          
                
    }//GEN-LAST:event_btnDisponiveisActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        contador = 0;
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparPesquisaActionPerformed
       LimparPesquisa();
    }//GEN-LAST:event_btnLimparPesquisaActionPerformed

    private void cmbDeptosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDeptosItemStateChanged
         contador++;            
         //JOptionPane.showMessageDialog(rootPane,"VL CONTADOR : "+ String.valueOf(contador)); 
         
         if( contador == 1){
             btnDisponiveis.setEnabled(true); 
             btnUtilizados.setEnabled(true);
         }else{
            btnDisponiveis.setEnabled(false); 
            btnUtilizados.setEnabled(false);
         }
         
    }//GEN-LAST:event_cmbDeptosItemStateChanged

    private void btnUtilizadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUtilizadosActionPerformed
       
        disponivel = false;
        lblTITULO.setForeground(Color.red);  
        btnUtilizados.setEnabled(false);
        btnDisponiveis.setEnabled(false); 
        
        if(nomeDepto.equals("BIBLIOTECA")){
            nomeDepto = "CEJUR";
        }
        
        codDepto  = umaBiblio.buscarCodigoGenerico("tbldepartamentos", "nome", nomeDepto);
        //JOptionPane.showMessageDialog(rootPane, "codigo do depto"+nomeDepto+"="+codDepto);
        if(contador == 1)
        {
            PesquisarUtilizados(nomeDepto);   
            lblTITULO.setText("NOMES DE ESTAÇÕES UTILIZADOS NO MOMENTO EM "+nomeDepto);
        }             
        
    }//GEN-LAST:event_btnUtilizadosActionPerformed
      
    public void indisponibilizarStatusNomeEstacaoPeloCodigo(int pCodigo) 
    {
        //Grava na tabela tblnomestacao diretamente sem criação de modelo etc. a alteração do status da estação para INDISPONIVEL
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblnomestacao SET status=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "INDISPONIVEL");           
            pst.setInt(2, pCodigo);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    }  
    
    public void DisponibilizarStatusNomeEstacaoPeloCodigo(int pCodigo) 
    {
        //Grava na tabela tblnomestacao diretamente sem criação de modelo etc. a alteração do status da estação para INDISPONIVEL
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblnomestacao SET status=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "DISPONIVEL");           
            pst.setInt(2, pCodigo);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    }  
        
     
    private void jTabelaESTACOESMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaESTACOESMouseClicked
        snomestacao =  ((String) jTabelaESTACOES.getValueAt(jTabelaESTACOES.getSelectedRow(), 1)); 
        codigoEstacao = umMetodo.buscarCodigoEstacaoPeloNome("tblnomestacao", "nomestacao",snomestacao);
        //JOptionPane.showMessageDialog(null, "O codigo é : "+ codigoEstacao);       
        
    }//GEN-LAST:event_jTabelaESTACOESMouseClicked
         
    public void PreencherTabelaESTACOES(String sql) {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Estação"};
        try {
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                dados.add(new Object[]{
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("nomestacao")

                });
                totalRegs = conexao.rs.getRow();
            };
                        
            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabelaESTACOES.setModel(modelo);
            
            //define tamanho das colunas
            jTabelaESTACOES.getColumnModel().getColumn(0).setPreferredWidth(70);  //define o tamanho da coluna
            jTabelaESTACOES.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaESTACOES.getColumnModel().getColumn(1).setPreferredWidth(720);  //define o tamanho da coluna
            jTabelaESTACOES.getColumnModel().getColumn(1).setResizable(false);    //nao será possivel redimencionar a coluna            

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
    
    public void PreencherTabelaESTACOESUTILIZADAS(String sql) {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Tipo", "Seção", "Estação", "Série"};
        try {
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                dados.add(new Object[]{
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("tipo"),
                    conexao.rs.getString("secao"),
                    conexao.rs.getString("estacao"),
                    conexao.rs.getString("serie")

                });
                totalRegs = conexao.rs.getRow();
            };
                        
            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabelaESTACOES.setModel(modelo);
            
            //define tamanho das colunas
            jTabelaESTACOES.getColumnModel().getColumn(0).setPreferredWidth(80);  //define o tamanho da coluna
            jTabelaESTACOES.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaESTACOES.getColumnModel().getColumn(1).setPreferredWidth(170);  //define o tamanho da coluna
            jTabelaESTACOES.getColumnModel().getColumn(1).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaESTACOES.getColumnModel().getColumn(2).setPreferredWidth(170);  //define o tamanho da coluna
            jTabelaESTACOES.getColumnModel().getColumn(2).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaESTACOES.getColumnModel().getColumn(3).setPreferredWidth(170);  //define o tamanho da coluna
            jTabelaESTACOES.getColumnModel().getColumn(3).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaESTACOES.getColumnModel().getColumn(4).setPreferredWidth(200);  //define o tamanho da coluna
            jTabelaESTACOES.getColumnModel().getColumn(4).setResizable(false);    //nao será possivel redimencionar a coluna 
            
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
    public javax.swing.JButton btnDisponiveis;
    public javax.swing.JButton btnLimparPesquisa;
    public javax.swing.JButton btnSair;
    public javax.swing.JButton btnUtilizados;
    private javax.swing.JComboBox<String> cmbDeptos;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTable jTabelaESTACOES;
    private javax.swing.JLabel lblTITULO;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTextField txtNOMESTACAO;
    // End of variables declaration//GEN-END:variables
}
