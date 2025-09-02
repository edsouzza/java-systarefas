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
import static biblioteca.VariaveisPublicas.contador;
import static biblioteca.VariaveisPublicas.nomeDepartamento;
import static biblioteca.VariaveisPublicas.totalRegs;
import controle.CtrlNomeEstacao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.NomeEstacao;


public class F_EDITARSTATUSNOMESTACOES extends javax.swing.JDialog  {

    ConnConexao        conexao                  = new ConnConexao();
    Biblioteca         umaBiblio                = new Biblioteca();
    MetodosPublicos    umMetodo                 = new MetodosPublicos();  
    NomeEstacao        umModeloNomeEstacao      = new NomeEstacao();
    CtrlNomeEstacao    umControleNomeEstacao    = new CtrlNomeEstacao();
    DAONomeEstacao     umEstacaoNomeEstacaoAO   = new DAONomeEstacao();
        
    String inicioRange, finalRange, estacaoInicial, estacaoFinal, strRange, sqlCmb, nomeSecao, sStatus, nomeDepto, titulo, caminhoTXT, linha, scodigo, snomestacao = "";    
    int codDepto, codigoEstacao;
    boolean selecionouDepto; 
    String sqlEstacoes                         = "SELECT * FROM tblnomestacao ORDER BY depto, RIGHT('000000000000' || nomestacao, 12)";
    
    String sqlEstacoesIndisponiveis            = "SELECT * FROM tblnomestacao WHERE status = 'INDISPONIVEL' ORDER BY depto, RIGHT('000000000000' || nomestacao, 12)";
    String sqlEstacoesDisponiveisPorDepto      = "SELECT * FROM tblnomestacao WHERE depto='"+nomeDepto+"' and status = 'DISPONIVEL' ORDER BY depto, RIGHT('000000000000' || nomestacao, 12)";
    String sqlEstacoesIndisponiveisPorDepto    = "SELECT * FROM tblnomestacao WHERE depto='"+nomeDepto+"' and status = 'INDISPONIVEL' ORDER BY depto, RIGHT('000000000000' || nomestacao, 12)";
    String sqlVazia                            = "SELECT * FROM tblnomestacao WHERE codigo= 0";
    String sqlEstacoesPorDepto, sqlEstacoesDisponiveis;
            

    public F_EDITARSTATUSNOMESTACOES() 
    {
        initComponents();
        setResizable(false);   //desabilitando o redimencionamento da tela     
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar      
        Leitura();       
           
        umaBiblio.configurarBotoes(btnDisponibilizar);
        umaBiblio.configurarBotoes(btnIndisponibilizar);   
        
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
                nomeDepto = (String) cmbDeptos.getSelectedItem();   
                
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
        contador  = 0; 
        btnDisponibilizar.setEnabled(false);
        btnIndisponibilizar.setEnabled(false);        
        //PreencherTabelaESTACOES(sqlEstacoes);   
        PreencherTabelaESTACOES(sqlVazia); 
        titulo = "GERENCIANDO NOMES DE REDE PARA ESTAÇÕES";       
        this.setTitle(titulo);       
    }    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        btnIndisponibilizar = new javax.swing.JButton();
        btnDisponibilizar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTabelaESTACOES = new javax.swing.JTable();
        btnDisponiveis = new javax.swing.JButton();
        btnIndisponiveis = new javax.swing.JButton();
        cmbDeptos = new javax.swing.JComboBox<String>();
        btnLimparFiltro = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelPrincipal.setPreferredSize(new java.awt.Dimension(1024, 733));
        panelPrincipal.setLayout(null);

        btnIndisponibilizar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnIndisponibilizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sairPrograma.gif"))); // NOI18N
        btnIndisponibilizar.setText("Indisponibilizar");
        btnIndisponibilizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIndisponibilizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIndisponibilizarActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnIndisponibilizar);
        btnIndisponibilizar.setBounds(410, 10, 140, 33);

        btnDisponibilizar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnDisponibilizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TICK.PNG"))); // NOI18N
        btnDisponibilizar.setText("Disponibilizar");
        btnDisponibilizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDisponibilizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisponibilizarActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnDisponibilizar);
        btnDisponibilizar.setBounds(270, 10, 140, 33);

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
        btnSair.setBounds(830, 10, 90, 33);

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
        jScrollPane3.setBounds(10, 60, 910, 510);

        btnDisponiveis.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnDisponiveis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/calculator_add.png"))); // NOI18N
        btnDisponiveis.setText("Disponiveis");
        btnDisponiveis.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDisponiveis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisponiveisActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnDisponiveis);
        btnDisponiveis.setBounds(550, 10, 140, 33);

        btnIndisponiveis.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnIndisponiveis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/building_delete.png"))); // NOI18N
        btnIndisponiveis.setText("Indisponiveis");
        btnIndisponiveis.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIndisponiveis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIndisponiveisActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnIndisponiveis);
        btnIndisponiveis.setBounds(690, 10, 140, 33);

        cmbDeptos.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        cmbDeptos.setForeground(new java.awt.Color(51, 51, 255));
        cmbDeptos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Filtrar por Departamento" }));
        cmbDeptos.setToolTipText("Escolha um departamento para filtrar");
        cmbDeptos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelPrincipal.add(cmbDeptos);
        cmbDeptos.setBounds(10, 10, 160, 33);

        btnLimparFiltro.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLimparFiltro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TICK.PNG"))); // NOI18N
        btnLimparFiltro.setText("Limpar");
        btnLimparFiltro.setToolTipText("Filtrar por Departamento");
        btnLimparFiltro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimparFiltro.setEnabled(false);
        btnLimparFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparFiltroActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnLimparFiltro);
        btnLimparFiltro.setBounds(171, 10, 100, 33);

        getContentPane().add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 920, 570));

        setSize(new java.awt.Dimension(939, 622));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
          
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        contador = 0;
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnDisponibilizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisponibilizarActionPerformed
        //Verificar se existe na TBLPATRIMONIOS e informar que este nome não pode ser disponibilizado
        if(umMetodo.EstacaoAtivaEmPatrimonios(snomestacao))
        {
            JOptionPane.showMessageDialog(null, "A estação "+snomestacao+" encontra em uso no momento e não pode ser disponibilizada! \nSe   necessário  faça   uma  transferência  para  CGGM  para  liberar  o  nome  para  uso!", "Estação em uso!",2); 
            //JOptionPane.showMessageDialog(null, "Escolha uma seção ou um tipo ao lado para filtrar!", "Seção ou tipo não selecionado", 2);
        }else{
            umMetodo.disponibilizarStatusNomeEstacao(snomestacao);
            JOptionPane.showMessageDialog(null, snomestacao+" foi disponibilizado com sucesso!"); 
            PreencherTabelaESTACOES(sqlEstacoes);  
        }
              
    }//GEN-LAST:event_btnDisponibilizarActionPerformed

    private void btnDisponiveisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisponiveisActionPerformed
            
       if(cmbDeptos.getSelectedIndex() >= 0){
                       
            if(nomeDepto.equals("BIBLIOTECA")){
                    titulo = "ESTAÇÕES DISPONIVEIS PARA "+nomeDepto+"/CEJUR";                   
                    nomeDepto = "CEJUR";                   
            }else
            if(nomeDepto.equals("CEJUR")){
                    titulo = "ESTAÇÕES DISPONIVEIS PARA "+nomeDepto+"/BIBLIOTECA";    
            }else{
                    titulo = "ESTAÇÕES DISPONIVEIS PARA "+nomeDepto;                   
            }
            
            sqlEstacoesDisponiveisPorDepto = "SELECT * FROM tblnomestacao WHERE depto='"+nomeDepto+"' and status = 'DISPONIVEL' ORDER BY depto, RIGHT('000000000000' || nomestacao, 12)";
            //sqlEstacoesDisponiveisPorDepto = "SELECT * FROM tblnomestacao WHERE depto='"+nomeDepto+"' and status = 'DISPONIVEL' ORDER BY numestacao";
            PreencherTabelaESTACOES(sqlEstacoesDisponiveisPorDepto);  
            this.setTitle(titulo);                  
       }else{    
            sqlEstacoesDisponiveis   = "SELECT * FROM tblnomestacao WHERE status = 'DISPONIVEL' ORDER BY depto, RIGHT('000000000000' || nomestacao, 12)";
            //sqlEstacoesDisponiveis   = "SELECT * FROM tblnomestacao WHERE status = 'DISPONIVEL' ORDER BY numestacao";
            PreencherTabelaESTACOES(sqlEstacoesDisponiveis);        
            titulo = "ESTAÇÕES DISPONIVEIS PARA TODOS OS DEPARTAMENTOS";        
            this.setTitle(titulo);      
       }
       btnIndisponiveis.setEnabled(false);
       btnDisponiveis.setEnabled(false); 
       cmbDeptos.setEnabled(false); 
       btnLimparFiltro.setEnabled(true);
    }//GEN-LAST:event_btnDisponiveisActionPerformed

    private void btnIndisponiveisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIndisponiveisActionPerformed
         
       if(cmbDeptos.getSelectedIndex() > 0){           
            //nomeDepto = cmbDeptos.getSelectedItem().toString();
            
            if(nomeDepto.equals("BIBLIOTECA")){
                nomeDepto = "CEJUR";
            }
            
            sqlEstacoesIndisponiveisPorDepto = "SELECT * FROM tblnomestacao WHERE depto='"+nomeDepto+"' and status = 'INDISPONIVEL' ORDER BY depto, RIGHT('000000000000' || nomestacao, 12)";
            //sqlEstacoesIndisponiveisPorDepto = "SELECT * FROM tblnomestacao WHERE depto='"+nomeDepto+"' and status = 'INDISPONIVEL' ORDER BY numestacao";
            PreencherTabelaESTACOES(sqlEstacoesIndisponiveisPorDepto);  
            titulo = "ESTAÇÕES INDISPONIVEIS PARA "+nomeDepto;        
            this.setTitle(titulo);                  
       }else{
            sqlEstacoesIndisponiveis   = "SELECT * FROM tblnomestacao WHERE status = 'INDISPONIVEL' ORDER BY depto, RIGHT('000000000000' || nomestacao, 12)";
            //sqlEstacoesIndisponiveis   = "SELECT * FROM tblnomestacao WHERE status = 'INDISPONIVEL' and status = 'INDISPONIVEL' ORDER BY numestacao";
            PreencherTabelaESTACOES(sqlEstacoesIndisponiveis);        
            titulo = "ESTAÇÕES INDISPONIVEIS PARA TODOS OS DEPARTAMENTOS";        
            this.setTitle(titulo);      
       }
       btnDisponiveis.setEnabled(false); 
       btnIndisponiveis.setEnabled(false);
       cmbDeptos.setEnabled(false); 
       btnLimparFiltro.setEnabled(true);
    }//GEN-LAST:event_btnIndisponiveisActionPerformed

    private void filtrarPorDepto()
    {       
        //nomeDepto = cmbDeptos.getSelectedItem().toString();
        sqlEstacoesPorDepto = "SELECT * FROM tblnomestacao WHERE depto='"+nomeDepto+"' and status = 'INDISPONIVEL' ORDER BY depto, RIGHT('000000000000' || nomestacao, 12)";
        PreencherTabelaESTACOES(sqlEstacoesPorDepto);       
    }       
    
    private void btnIndisponibilizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIndisponibilizarActionPerformed
        
        if (umMetodo.ConfirmouOperacao("Confirma o desejo indisponibilizar o nome da estação?", "Indisponibilizando o nome da estação")){
            umMetodo.indisponibilizarStatusNomeEstacao(snomestacao);
            JOptionPane.showMessageDialog(null, snomestacao+" foi indisponibilizado com sucesso!"); 
            PreencherTabelaESTACOES(sqlEstacoes);      
        }                 
        
    }//GEN-LAST:event_btnIndisponibilizarActionPerformed

    private void btnLimparFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparFiltroActionPerformed
        PreencherTabelaESTACOES(sqlVazia); 
        cmbDeptos.setEnabled(true); 
        cmbDeptos.setSelectedIndex(-1); 
        btnIndisponibilizar.setEnabled(false);
        btnDisponibilizar.setEnabled(false); 
        btnDisponiveis.setEnabled(true); 
        btnIndisponiveis.setEnabled(true);
        btnLimparFiltro.setEnabled(false);
        titulo = "GERENCIANDO NOMES DE REDE PARA ESTAÇÕES";  
        this.setTitle(titulo); 
    }//GEN-LAST:event_btnLimparFiltroActionPerformed

    private void jTabelaESTACOESMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaESTACOESMouseClicked
        snomestacao    = ((String) jTabelaESTACOES.getValueAt(jTabelaESTACOES.getSelectedRow(), 1)); 
        sStatus        = ((String) jTabelaESTACOES.getValueAt(jTabelaESTACOES.getSelectedRow(), 3)); 
         
        //JOptionPane.showMessageDialog(null, snomestacao);
        //JOptionPane.showMessageDialog(null, sStatus);
        codigoEstacao = umMetodo.buscarCodigoGenerico("tblnomestacao", "nomestacao", snomestacao);      //buscarCodigoEstacaoPeloNome("tblnomestacao", snomestacao);
        
        if(sStatus.equals("DISPONIVEL"))
        {            
            btnIndisponibilizar.setEnabled(true);
            btnDisponibilizar.setEnabled(false);
        }else{
            btnDisponibilizar.setEnabled(true);  
            btnIndisponibilizar.setEnabled(false);
        }
    }//GEN-LAST:event_jTabelaESTACOESMouseClicked
         
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
            jTabelaESTACOES.getColumnModel().getColumn(2).setPreferredWidth(300);  //define o tamanho da coluna
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
    public javax.swing.JButton btnDisponibilizar;
    public javax.swing.JButton btnDisponiveis;
    public javax.swing.JButton btnIndisponibilizar;
    public javax.swing.JButton btnIndisponiveis;
    public javax.swing.JButton btnLimparFiltro;
    public javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbDeptos;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTable jTabelaESTACOES;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables
}
