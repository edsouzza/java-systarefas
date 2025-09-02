package visao;

import Dao.DAOCliente;
import Dao.DAONomeEstacao;
import biblioteca.Biblioteca;
import biblioteca.GerarNumerosAleatorios;
import biblioteca.MetodosPublicos;
import biblioteca.RetornarQdeLinhasDoTxt;
import biblioteca.SelecionarArquivoTexto;
import static biblioteca.VariaveisPublicas.dataDoDia;
import static biblioteca.VariaveisPublicas.lstListaStrings;
import static biblioteca.VariaveisPublicas.lstListaStringsAuxiliar;
import static biblioteca.VariaveisPublicas.sql;
import conexao.ConnConexao;
import controle.CtrlCliente;
import controle.CtrlNomeEstacao;
import controle.CtrlPatrimonio;
import java.awt.AWTKeyStroke;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import javax.swing.JOptionPane;
import modelo.Cliente;
import modelo.NomeEstacao;


public class F_APOIO extends javax.swing.JDialog
{
    ConnConexao        conexao                  = new ConnConexao();
    Biblioteca         umabiblio                = new Biblioteca();
    MetodosPublicos    umMetodo                 = new MetodosPublicos();
    NomeEstacao        umModeloNomeEstacao      = new NomeEstacao();
    CtrlNomeEstacao    umControleNomeEstacao    = new CtrlNomeEstacao();
    DAONomeEstacao     umEstacaoNomeEstacaoAO   = new DAONomeEstacao();
    CtrlPatrimonio     umControlePatrimonio     = new CtrlPatrimonio();
    
    Cliente            umModCliente             = new Cliente();
    CtrlCliente        ctrCliente               = new CtrlCliente();
    DAOCliente         clienteDAO               = new DAOCliente();  
    DateFormat          sdf                     = new SimpleDateFormat("dd/MM/yyyy");
    Date dataDia                                = dataDoDia; 
    
    String caminhoTXT, linha, snomestacao, paramPesqCod, sChapa, sObs, sSerie, novaObservacao;
    Boolean bEncontrou;
    int ipesqPorCod;
   
    public F_APOIO(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
         initComponents();               
       
        setResizable(false);          //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
                
        // Colocando enter para pular de campo 
        HashSet conj = new HashSet(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS)); 
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0)); 
        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj); 
        
       //configuração dos botões    
       umabiblio.configurarBotoes(btnPopularTblNomestacaoTMP);
               
       //cofigurações das tabelas
       jTabbedPane1.setFont(new Font("TimesRoman",Font.BOLD,12)); 
             
        
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        btnPopularTblNomestacaoTMP = new javax.swing.JButton();
        btnImportarTXT = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnCadastroClientesVirtuais = new javax.swing.JButton();
        btnExecutarAtualizacoesViaSQL = new javax.swing.JButton();
        btnExclusaoDeRegistros = new javax.swing.JButton();
        btnTransferirParaCGGM = new javax.swing.JButton();
        btnCadastroClientesSemUsuario = new javax.swing.JButton();
        btnAtualizaChapasGenericasPorVerdadeiras = new javax.swing.JButton();
        btnAtualizaNomeEstacaoParaMonitor = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTITULO = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 733));

        jTabbedPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnPopularTblNomestacaoTMP.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnPopularTblNomestacaoTMP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TICK.PNG"))); // NOI18N
        btnPopularTblNomestacaoTMP.setText("POPULAR TBLNOMESTACAOTMP NAO CADASTRADOS");
        btnPopularTblNomestacaoTMP.setToolTipText("");
        btnPopularTblNomestacaoTMP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPopularTblNomestacaoTMP.setPreferredSize(new java.awt.Dimension(77, 25));
        btnPopularTblNomestacaoTMP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPopularTblNomestacaoTMPActionPerformed(evt);
            }
        });

        btnImportarTXT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnImportarTXT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/calculator.png"))); // NOI18N
        btnImportarTXT.setText("IMPORTAR DADOS DO TXT PARA TBLNOMESTACAO");
        btnImportarTXT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImportarTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarTXTActionPerformed(evt);
            }
        });

        btnSair.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sair.gif"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSair.setPreferredSize(new java.awt.Dimension(77, 25));
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnCadastroClientesVirtuais.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCadastroClientesVirtuais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/monitor.png"))); // NOI18N
        btnCadastroClientesVirtuais.setText("ACERTO  DE CADASTRO  DOS  CLIENTES VIRTUAIS");
        btnCadastroClientesVirtuais.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadastroClientesVirtuais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastroClientesVirtuaisActionPerformed(evt);
            }
        });

        btnExecutarAtualizacoesViaSQL.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnExecutarAtualizacoesViaSQL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_blocoNotas.gif"))); // NOI18N
        btnExecutarAtualizacoesViaSQL.setText("  ACERTO DE TABELAS VIA SQL");
        btnExecutarAtualizacoesViaSQL.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExecutarAtualizacoesViaSQL.setEnabled(false);
        btnExecutarAtualizacoesViaSQL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExecutarAtualizacoesViaSQLActionPerformed(evt);
            }
        });

        btnExclusaoDeRegistros.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnExclusaoDeRegistros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sairPrograma.gif"))); // NOI18N
        btnExclusaoDeRegistros.setText("EXCLUSÃO DEFINITIVA DE PATRIMONIOS PELO CODIGO");
        btnExclusaoDeRegistros.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExclusaoDeRegistros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExclusaoDeRegistrosActionPerformed(evt);
            }
        });

        btnTransferirParaCGGM.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTransferirParaCGGM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/patrimonios.png"))); // NOI18N
        btnTransferirParaCGGM.setText("TRANSFERIR PATRIMONIO PARA CGGM PELO CODIGO");
        btnTransferirParaCGGM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTransferirParaCGGM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransferirParaCGGMActionPerformed(evt);
            }
        });

        btnCadastroClientesSemUsuario.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCadastroClientesSemUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/users16.jpg"))); // NOI18N
        btnCadastroClientesSemUsuario.setText("ACERTO  DE CADASTRO  DOS  CLIENTES SEM USUARIO");
        btnCadastroClientesSemUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCadastroClientesSemUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastroClientesSemUsuarioActionPerformed(evt);
            }
        });

        btnAtualizaChapasGenericasPorVerdadeiras.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAtualizaChapasGenericasPorVerdadeiras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/building_add.png"))); // NOI18N
        btnAtualizaChapasGenericasPorVerdadeiras.setText("ATUALIZAR CHAPAS AUTO LENDO TXT [COM SERIES E NOVAS CHAPAS]");
        btnAtualizaChapasGenericasPorVerdadeiras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualizaChapasGenericasPorVerdadeiras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizaChapasGenericasPorVerdadeirasActionPerformed(evt);
            }
        });

        btnAtualizaNomeEstacaoParaMonitor.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnAtualizaNomeEstacaoParaMonitor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TICK.PNG"))); // NOI18N
        btnAtualizaNomeEstacaoParaMonitor.setText("ACERTO  DE CADASTRO  ATUALIZA ESTACAO PARA MONITOR");
        btnAtualizaNomeEstacaoParaMonitor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualizaNomeEstacaoParaMonitor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizaNomeEstacaoParaMonitorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnAtualizaNomeEstacaoParaMonitor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAtualizaChapasGenericasPorVerdadeiras, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCadastroClientesSemUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTransferirParaCGGM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExclusaoDeRegistros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCadastroClientesVirtuais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPopularTblNomestacaoTMP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImportarTXT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExecutarAtualizacoesViaSQL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(464, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnExecutarAtualizacoesViaSQL, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnImportarTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPopularTblNomestacaoTMP, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCadastroClientesVirtuais, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnExclusaoDeRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTransferirParaCGGM, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAtualizaChapasGenericasPorVerdadeiras, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCadastroClientesSemUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAtualizaNomeEstacaoParaMonitor, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("ABA1", jPanel2);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 51));
        jLabel1.setText("  ATUALIZADO EM 30/05/25  08:45 EM HOME");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 951, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 608, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("ABA2", jPanel3);

        lblTITULO.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTITULO.setText("FORMULÁRIO CRIADO PARA GERENCIAR PROCEDIMENTOS DE APOIO PARA ACERTOS EM BANCO DE DADOS E TUDO MAIS");
        lblTITULO.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(lblTITULO)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTITULO)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 679, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 976, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(992, 815));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnPopularTblNomestacaoTMPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPopularTblNomestacaoTMPActionPerformed
        /*O OBJETIVO DESSE PROCEDIMENTO É DE POPULAR A TABELA TBLNOMESTACAOTMP COM TODAS AS ESTACOES DA CGGM QUE JÁ NÃO ESTÃO CADASTRADAS, OU SEJA FICARÃO DISPONIVEL
        PARA USO ANTES DO SISTEMA COMEÇAR A GERAR NOVOS NOMES*/
        if (umMetodo.ConfirmouOperacao("Confirma o desejo de popular a tabela TBLNOMESTACAOTMP com registros não cadastrados agora?", "Populando a TBLNOMESTACAOTMP")){
            if(umMetodo.tabelaEstaVazia("TBLNOMESTACAOTMP"))
            {
                umMetodo.popularTabelaTMPNaoCadastrados();
                JOptionPane.showMessageDialog(null, "Os dados foram salvos com sucesso na tabela!");
            }
        }

    }//GEN-LAST:event_btnPopularTblNomestacaoTMPActionPerformed

    private void ImportarTXT(){
        //inicializando as variaveis dos campos a serem gravados
        int totalLinhas,numEst               = 0;
        RetornarQdeLinhasDoTxt qdeLinhas     = new RetornarQdeLinhasDoTxt();

        //setando o caminho do arquivo TXT no edit do formulario apenas para mostrar o arquivo que esta sendo importado
        SelecionarArquivoTexto select = new SelecionarArquivoTexto();  
        caminhoTXT = select.ImportarTXT();   
        
        if(caminhoTXT != null)
        {
             //setando o caminho do arquivo TXT na variavel caminhoTXT para pegar os valores                   
             totalLinhas  = qdeLinhas.retornaNumLinhasDoTxt(caminhoTXT);
             //JOptionPane.showMessageDialog(null, "Qde de linhas do arquivo...: "+String.valueOf(totalLinhas));
                        
        
                //criando uma variavel arquivo do tipo File e setando o caminho do arquivo TXT nela
                File arquivo = new File(caminhoTXT);        
                try {
                    FileReader ler          = new FileReader(arquivo);
                    BufferedReader lerBuf   = new BufferedReader(ler);
                    linha                   = lerBuf.readLine();

                    while(linha != null)
                    {                                               
                        snomestacao       = linha.split(";")[0];
                        
                        //mostrando os valores no console
                        //System.out.println("NOME.....:"+nome+"\nRF.......:"+rf+"\nLOTAÇÃO..:"+lotacao+"\n");     

                        //setando os valores no objeto do modelo
                        numEst    = umMetodo.somenteDigitos(snomestacao);                        
                        umModeloNomeEstacao.setNomestacao(snomestacao);
                        umModeloNomeEstacao.setNumestacao(numEst);
                        umModeloNomeEstacao.setDepto("CGGM");
                        umModeloNomeEstacao.setStatus("INDISPONIVEL");
                        umControleNomeEstacao.salvarNomeEstacao(umModeloNomeEstacao);   

                            //JOptionPane.showMessageDialog(null,"O SecretariasComSecretaria "+nome+" já esta cadastrado!");
                        
                        //lendo a proxima linha
                        linha = lerBuf.readLine();                       
                     }
                    JOptionPane.showMessageDialog(null,"Os dados foram importados com sucesso!");  
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Erro ao tentar ler o arquivo!");
                }
        }
    }
    
    private void btnImportarTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarTXTActionPerformed
        /*O OBJETIVO DESSE PROCEDIMENTO É DE POPULAR A TABELA TBLNOMESTACAO COM TODAS AS ESTACOES DA CGGM QUE JÁ ESTÃO CADASTRADAS MAS QUE ESTEJAM COM A NOVA NOMENCLATURA
        PGMCGGMC EM TODAS AS ESTAÇÕES, OU SEJA PRIMEIRO FAÇA OS PROCEDIMENTOS DE RENOMEAR TODAS AS ESTAÇÕES PARA PGMCGGMC DEPOIS COLOQUE-AS EM UM ARQUIVO TXT ASSIM:
        PGMCGGMC1;
        PGMCGGMC2;
        E ASSIM POR DIANTE*/
        if (umMetodo.ConfirmouOperacao("Deseja Importar os dados do Arquivo TXT e popular a tabela TBLNOMESTACAO agora?", "Populando a TBLNOMESTACAO")){
            ImportarTXT();
        }
    }//GEN-LAST:event_btnImportarTXTActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    public void retornaNomesTblClientesVirtuais() 
    {
        //Pega todos os nomes de clientes virtuais e coloca dentro da lstListaStrings  
        conexao.conectar();        
        sql = "SELECT nome FROM TBLCLIENTESVIRTUAIS";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            while (conexao.rs.next()) {
                lstListaStringsAuxiliar.add(conexao.rs.getString("nome"));                    
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome da seção! " + ex);
            
        } finally {
            conexao.desconectar();
        }        
    }       
    
//    public boolean atualizaNomesDeEstacoesParaMonitor() 
//    {
//        //Verifica todos os equipamentos com tipoid = 2 e atualiza o nome de estação para MONITOR
//        conexao.conectar();        
//        sql = "update tblpatrimonios set estacao = 'MONITOR' WHERE tipoid = 2";
//        try {            
//            conexao.ExecutarAtualizacaoSQL(sql);       
//            return true;
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Erro ao tentar atualizar o nome da estação para MONITOR, \n" + e + ", o sql passado foi \n" + sql);
//        } finally {
//            conexao.desconectar();
//            return false;
//        }          
//    }   
    
    private void cadastrarClientesVirtuaisAindaNaoIncluidos()
    {
        String nomeParaCadastro,statusReg,nome = null;
        
        //pegar a lista de todas as secoes cadastradas e montar o nome do novo cliente
        umMetodo.retornaValoresDeUmCampoStringDaTabelaParaUmaLista("nome", "TBLSECOES");    
        
        //pegar a lista de todas os clientes virtuais cadastradas e montar o nome do novo cliente
        retornaNomesTblClientesVirtuais(); // metodo local retornado na lstListaStringsAuxiliar   
        
        //Fazer a gravação de cada Cliente Virtual para cada seção encontrada

        for(int i=0; i < lstListaStrings.size(); i++)
        {

            for(int j=0; j < lstListaStringsAuxiliar.size(); j++)
            {
                    nomeParaCadastro = lstListaStringsAuxiliar.get(j)+" "+lstListaStrings.get(i);
                    //JOptionPane.showMessageDialog(rootPane, nomeParaCadastro);

                    Long n                      = new Long("0123456");                                       
                    GerarNumerosAleatorios rf   = new GerarNumerosAleatorios(n);                      
                    String rfCliente            = String.valueOf(rf.getNumeroAleatorioRF());      

                   if(!umMetodo.temDuplicidadeDeCadastro("tblclientes", "nome", nomeParaCadastro))
                   {                        
                        umModCliente.setNome(nomeParaCadastro);
                        umModCliente.setRf(rfCliente);

                        //verificar o status da secao se ativo ou inativo
                        statusReg = umMetodo.retornarStatusDoRegistroPeloNome("tblsecoes",lstListaStrings.get(i));
                        //JOptionPane.showMessageDialog(null, "nome da secao : "+lstListaStrings.get(i)+ " - STATUS : "+statusComp);

                        switch (statusReg) {
                            case "ATIVO":
                                umModCliente.setStatus("ATIVO");
                                break;
                            case "INATIVO":    
                                umModCliente.setStatus("INATIVO");
                                break;
                        }                                                       

                        int codSecao     = umMetodo.retornaCodigo("tblsecoes", "nome", lstListaStrings.get(i)); 
                        umModCliente.setSecaoid(codSecao);

                        int deptoid      = umMetodo.retornaCodigoDepto("tblsecoes", "nome", lstListaStrings.get(i));
                        umModCliente.setDeptoid(deptoid); 

                        String tipo  = "N";
                        umModCliente.setTipo(tipo); 

                        String obs  = "";  
                        umModCliente.setObs(obs);                        

                        //GRAVA UM NOVO CLIENTE NA TBLCLIENTES
                        ctrCliente.salvarClienteVirtual(umModCliente);    
                        
                   }
                   
            }
        }  
        JOptionPane.showMessageDialog(null, "Os Clientes Virtuais faltantes foram cadastrados com sucesso!");
    }
    
    private void btnCadastroClientesVirtuaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastroClientesVirtuaisActionPerformed
        if (umMetodo.ConfirmouOperacao("Confirma o desejo de popular a TBLCLIENTES com os Clientes Virtuais faltantes?", "Cadastrando os clientes virtuais faltantes!")){
            cadastrarClientesVirtuaisAindaNaoIncluidos();
        }
    }//GEN-LAST:event_btnCadastroClientesVirtuaisActionPerformed

    private void btnExecutarAtualizacoesViaSQLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExecutarAtualizacoesViaSQLActionPerformed
        
        String sql1="UPDATE tblsecoes SET nome='CGGM/GAB' WHERE codigo=8";    
        lstListaStrings.add(sql1);        
        String sql2="UPDATE tblsecoes SET deptoid=6  WHERE codigo in(1,4,5,6,7,8,9,10,11,12,13,16,23,24,27,29,30,31,32,33,34)";  
        lstListaStrings.add(sql2);
        String sql3="UPDATE tblsecoes SET deptoid = 1  WHERE codigo in(1,2,14,15,19,25,28)";  
        lstListaStrings.add(sql3);
        String sql4="UPDATE tblsecoes SET deptoid = 7  WHERE codigo =19";  
        lstListaStrings.add(sql4);
        String sql5="UPDATE tblsecoes SET nome='PGM/GAB' WHERE codigo=28";  
        lstListaStrings.add(sql5);
        String sql6="UPDATE tblsecoes SET deptoid = 2  WHERE codigo =3";  
        lstListaStrings.add(sql6);
        String sql7="UPDATE tblsecoes SET deptoid = 5  WHERE codigo =17";  
        lstListaStrings.add(sql7);
        String sql8="UPDATE tblsecoes SET deptoid = 13  WHERE codigo =18";  
        lstListaStrings.add(sql8);
        String sql9="UPDATE tblsecoes SET deptoid = 3  WHERE codigo =21";  
        lstListaStrings.add(sql9);
        String sql10="UPDATE tblsecoes SET deptoid = 11  WHERE codigo =22";  
        lstListaStrings.add(sql10);
        String sql11="UPDATE tblsecoes SET deptoid = 12  WHERE codigo =25";  
        lstListaStrings.add(sql11);
        String sql12="update tblpatrimonios set deptoid = 11 where secaoid = 22";  
        lstListaStrings.add(sql12);
        String sql13="UPDATE tblpatrimonios SET secaoid=30 WHERE codigo in(211,231,264,265,345,350,353,354,355,356,364,365,367,369)";  
        lstListaStrings.add(sql13);
        String sql14="UPDATE tblclientes set tipo='S' WHERE tipo<>'N'";  
        lstListaStrings.add(sql14);
        String sql15="UPDATE tblclientes set DEPTOID=2 where SECAOID=3";  
        lstListaStrings.add(sql15);
        String sql16="UPDATE tblclientes set DEPTOID=11 where SECAOID=22";  
        lstListaStrings.add(sql16);
        String sql17="UPDATE tblclientes set DEPTOID=5 where SECAOID=17";  
        lstListaStrings.add(sql17);
        String sql18="UPDATE tblclientes set DEPTOID=1 where SECAOID=2";  
        lstListaStrings.add(sql18);
        String sql19="UPDATE tblclientes set DEPTOID=1 where SECAOID=14";  
        lstListaStrings.add(sql19);
        String sql20="UPDATE tblclientes set DEPTOID=13 where SECAOID=18";  
        lstListaStrings.add(sql20);
        String sql21="UPDATE tblclientes set DEPTOID=1 where SECAOID=19";  
        lstListaStrings.add(sql21);
        String sql22="UPDATE tblclientes set DEPTOID=3 where SECAOID=21";  
        lstListaStrings.add(sql22);
        String sql23="UPDATE tblclientes set DEPTOID=1 where SECAOID=28";  
        lstListaStrings.add(sql23);
        String sql24="UPDATE tblclientes set DEPTOID=6 where SECAOID=4";  
        lstListaStrings.add(sql24);
        String sql25="UPDATE tblclientes set DEPTOID=6 where SECAOID=5";  
        lstListaStrings.add(sql25);
        String sql26="UPDATE tblclientes set DEPTOID=6 where SECAOID=11";  
        lstListaStrings.add(sql26);
        String sql27="UPDATE tblclientes set DEPTOID=6 where SECAOID=23";  
        lstListaStrings.add(sql27);
        String sql28="UPDATE tblclientes set DEPTOID=6 where SECAOID=24";  
        lstListaStrings.add(sql28);
        String sql29="UPDATE tblclientes set DEPTOID=6 where SECAOID=1";  
        lstListaStrings.add(sql29);
        String sql30="UPDATE tblclientes set DEPTOID=6 where SECAOID=30";  
        lstListaStrings.add(sql30);
        String sql31="UPDATE tblclientes set DEPTOID=6 where SECAOID=12";  
        lstListaStrings.add(sql31);
        String sql32="UPDATE tblclientes set DEPTOID=6 where SECAOID=6";  
        lstListaStrings.add(sql32);
        String sql33="UPDATE tblclientes set DEPTOID=6 where SECAOID=29";  
        lstListaStrings.add(sql33);
        String sql34="UPDATE tblclientes set DEPTOID=6 where SECAOID=7";  
        lstListaStrings.add(sql34);
        String sql35="UPDATE tblclientes set DEPTOID=6 where SECAOID=8";  
        lstListaStrings.add(sql35);
        String sql36="UPDATE tblclientes set DEPTOID=6 where SECAOID=9";  
        lstListaStrings.add(sql36);
        String sql37="UPDATE tblclientes set DEPTOID=6 where SECAOID=10";  
        lstListaStrings.add(sql37);
        String sql38="UPDATE tblclientes set DEPTOID=6 where SECAOID=31";  
        lstListaStrings.add(sql38);
        String sql39="UPDATE tblclientes set DEPTOID=6 where SECAOID=13";  
        lstListaStrings.add(sql39);
        String sql40="UPDATE tblclientes set DEPTOID=6 where SECAOID=16";  
        lstListaStrings.add(sql40);
        String sql41="UPDATE tblclientes set DEPTOID=6 where SECAOID=15";  
        lstListaStrings.add(sql41);
        String sql42="UPDATE tblclientes set DEPTOID=6 where SECAOID=33";  
        lstListaStrings.add(sql42);
        String sql43="UPDATE tblclientes set DEPTOID=6 where SECAOID=32";  
        lstListaStrings.add(sql43);
        String sql44="UPDATE tblclientes set DEPTOID=6 where SECAOID=34";  
        lstListaStrings.add(sql44);
        String sql45="update tblpatrimonios set estacao = replace(ESTACAO,'SNJPGMC0','SNJPGMC')";  
        lstListaStrings.add(sql45);
        String sql46="update tblpatrimonios set estacao = replace(ESTACAO,'SNJPGMC','PGMCGGMC')";  
        lstListaStrings.add(sql46);
        String sql47="update tblpatrimonios set estacao = REPLACE(ESTACAO, 'SNJCEJURC0', 'SNJCEJURC')";  
        lstListaStrings.add(sql47);
        String sql48="update tblpatrimonios set estacao = REPLACE(ESTACAO, 'SNJCEJURC', 'PGMCEJURC')";  
        lstListaStrings.add(sql48);
        String sql49="update tblpatrimonios set deptoid = 2 where secaoid = 3";  
        lstListaStrings.add(sql49);
        String sql50="UPDATE tblpatrimonios set DEPTOID=5 where SECAOID=17";  
        lstListaStrings.add(sql50);
        String sql51="UPDATE tblpatrimonios set DEPTOID=6 where DEPTOID=1";  
        lstListaStrings.add(sql51);
        
        //Mostrando os dados inseridos na Lista
//        for(String m: lstListaStrings)
//        {
//            System.out.println(m);           
//           
//        }

        for(int i=0; i<lstListaStrings.size(); i++)
        {
            umMetodo.atualizarTabelasViaSql(lstListaStrings.get(i));           
        }
        JOptionPane.showMessageDialog(null,"As atualizações foram executadas com sucesso!");
        
    }//GEN-LAST:event_btnExecutarAtualizacoesViaSQLActionPerformed

   public void PesquisarExcluirPorCod()
    {   
               
        while(paramPesqCod == null || paramPesqCod.equals("") || paramPesqCod.equals("0") || paramPesqCod.length() > 9) 
        {            
            paramPesqCod = JOptionPane.showInputDialog(null, "Entre com o código do equipamento até 9 digitos!", "Pesquisa por Código", 2);
            
            if( (paramPesqCod == null ) || (paramPesqCod.equals("")) || (paramPesqCod.equals("0")) || (paramPesqCod.length() > 9) ) 
            {
                JOptionPane.showMessageDialog(null, "Entre com o código do equipamento a ser excluído!","O valor digitado não é válido!",2);
            }else{

                if (!paramPesqCod.trim().matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Digite apenas o código do equipamento!", "Somente números!",JOptionPane.ERROR_MESSAGE);                    
                    paramPesqCod = null;
                }else{
                    bEncontrou = false;
                    ipesqPorCod = Integer.parseInt(paramPesqCod);
                    
                    if(umControlePatrimonio.getPatrimonioPeloCodico(ipesqPorCod)){
                    
                        if (umMetodo.ConfirmouOperacao("Confirma o desejo de excluir em definitivo da base de dados o registro com código nº "+paramPesqCod+"?", "Exclusão de Patrimônio")){
                                umControlePatrimonio.ExcluirPatrimonio(ipesqPorCod);
                                //JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n "+e+" , o sql passado foi \n"+sql);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"O Patrimônio com código "+paramPesqCod+" não foi encontrado na base de dados","Código não cadastrado na tabela!",2);                     
                    }
                }
               
            }
            
       }
       paramPesqCod = null;
    }          
   
   public void PesquisarTransferirPorCod()
    {   
               
        while(paramPesqCod == null || paramPesqCod.equals("") || paramPesqCod.equals("0") || paramPesqCod.length() > 9) 
        {            
            paramPesqCod = JOptionPane.showInputDialog(null, "Entre com o código do equipamento até 9 digitos!", "Pesquisa por Código", 2);
            
            if( (paramPesqCod == null ) || (paramPesqCod.equals("")) || (paramPesqCod.equals("0")) || (paramPesqCod.length() > 9) ) 
            {
                JOptionPane.showMessageDialog(null, "Entre com o código do equipamento a ser transferido!","O valor digitado não é válido!",2);
            }else{

                if (!paramPesqCod.trim().matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "Digite apenas o código do equipamento!", "Somente números!",JOptionPane.ERROR_MESSAGE);                    
                    paramPesqCod = null;
                }else{
                    bEncontrou = false;
                    ipesqPorCod = Integer.parseInt(paramPesqCod);
                    
                    if(umControlePatrimonio.getPatrimonioPeloCodico(ipesqPorCod)){
                    
                        if (umMetodo.ConfirmouOperacao("Confirma o desejo de transferir o equipamento com códico nº "+paramPesqCod+" para CGGM?", "Transferência de Patrimônio para CGGM")){
                                umControlePatrimonio.TransferirPatrimonio(ipesqPorCod);
                                //JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n "+e+" , o sql passado foi \n"+sql);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"O Patrimônio com código "+paramPesqCod+" não foi encontrado na base de dados","Código não cadastrado na tabela!",2);                     
                    }
                }
               
            }
            
       }
       paramPesqCod = null;
    }      
     
    private void btnExclusaoDeRegistrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExclusaoDeRegistrosActionPerformed
        PesquisarExcluirPorCod();
    }//GEN-LAST:event_btnExclusaoDeRegistrosActionPerformed

    private void btnTransferirParaCGGMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTransferirParaCGGMActionPerformed
        PesquisarTransferirPorCod();
    }//GEN-LAST:event_btnTransferirParaCGGMActionPerformed

    public void retornaListaDeTodasSecoesCadastradas() 
    {
        //Pega todos os nomes de clientes virtuais e coloca dentro da lstListaStrings  
        conexao.conectar();        
        sql = "SELECT nome FROM TBLSECOES";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            while (conexao.rs.next()) {
                lstListaStringsAuxiliar.add(conexao.rs.getString("nome"));                    
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome da seção! " + ex);
            
        } finally {
            conexao.desconectar();
        }        
    }
    
   public void gravarNomeClienteVirtualSemUsuarioNaTblClientes(String nomeSecao) 
   {      
            //ex : SEM USUARIO FISC
        
            String secao                = nomeSecao;
            String nome                 = "SEM USUARIO "+nomeSecao;
            Long n                      = new Long("0123456");                                       
            GerarNumerosAleatorios rf   = new GerarNumerosAleatorios(n);                      
            String rfCliente            = String.valueOf(rf.getNumeroAleatorioRF());
            String obs                  = "";
            int    deptoid              = 6;
            String status               = "ATIVO";
            String tipo                 = "F";
            int codSecao                = umMetodo.retornaCodigo("tblsecoes", "nome", nomeSecao);   

            umModCliente.setNome(nome);
            umModCliente.setRf(rfCliente);
            umModCliente.setObs(obs);
            umModCliente.setSecaoid(codSecao);
            umModCliente.setDeptoid(deptoid);
            umModCliente.setStatus(status);
            umModCliente.setTipo(tipo);   
            umModCliente.setTipovirtualid(0);
                
            if (!umMetodo.temDuplicidadeDeCadastro("TBLCLIENTES","nome",nome)){               
                ctrCliente.salvarCliente(umModCliente);       
            }                     
    }
        
    private void btnCadastroClientesSemUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastroClientesSemUsuarioActionPerformed
        String nomeParaCadastro = null;
        
        retornaListaDeTodasSecoesCadastradas();
        
        for(int i=0; i < lstListaStringsAuxiliar.size(); i++)
        {
            nomeParaCadastro = lstListaStringsAuxiliar.get(i);
            gravarNomeClienteVirtualSemUsuarioNaTblClientes(nomeParaCadastro);
        }     
        
        JOptionPane.showMessageDialog(null, "Os Clientes Sem Usuários faltantes foram cadastrados com sucesso!");
        
//        for(String m: lstListaStringsAuxiliar)
//        {
//            System.out.println(m);     
//        }
    }//GEN-LAST:event_btnCadastroClientesSemUsuarioActionPerformed

    private void LexTXT()
    {
        //inicializando as variaveis dos campos a serem gravados
        int totalLinhas = 0;
        RetornarQdeLinhasDoTxt qdeLinhas = new RetornarQdeLinhasDoTxt();

        //setando o caminho do arquivo TXT no edit do formulario apenas para mostrar o arquivo que esta sendo importado
        SelecionarArquivoTexto select = new SelecionarArquivoTexto();
        caminhoTXT = select.ImportarTXT();

        if (caminhoTXT != null) {
            totalLinhas = qdeLinhas.retornaNumLinhasDoTxt(caminhoTXT);
             
                //criando uma variavel arquivo do tipo File e setando o caminho do arquivo TXT nela
                File arquivo = new File(caminhoTXT);
                 try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                    String linha;
                    while ((linha = br.readLine()) != null) {
                        
                        String[] colunas = linha.split("\\s+"); // Divide por espaços em branco
                        if (colunas.length == 2) {              // Certifica-se de que há duas colunas
                            sSerie =  colunas[0];               // Primeira coluna como inteiro
                            sChapa  = colunas[1];               // Segunda coluna como string                            
                        }
                        atualizarChapasCorrespondentesASerie();                  // Grava os dados de atualização no banco
                    }
                    JOptionPane.showMessageDialog(null, "As chapas com séries correspondentes foram atualizadas com sucesso.", "Atualização de chapas!", 2);
                    
                } catch (IOException e) {
                    System.err.println("Erro ao ler o arquivo: " + e.getMessage());
                }
        }
    }
    
    private String addObsAdicionalAuto(){       
        novaObservacao = "A chapa de incorporacao foi atualizada atraves da serie correspondente por meio de leitura de Arquivo TXT.";
        
        if(umMetodo.campoSelecionadoTemValores("tblpatrimonios", "observacoes", "serie", sSerie)){
            novaObservacao = sdf.format(dataDia)+" : Cadastro inicial. \n"+sdf.format(dataDia)+" : "+umMetodo.primeiraLetraMaiuscula(novaObservacao);     
        }else{
            //inserir o que já tinha no campo aqui
            String valorAtualObs = umMetodo.getStringPassandoString("tblpatrimonios", "observacoes", "serie", sSerie);
            sObs =  valorAtualObs+"\n"+sdf.format(dataDia)+" : "+umMetodo.primeiraLetraMaiuscula(novaObservacao);                     
        }
        return sObs;
    }
           
    private void atualizarChapasCorrespondentesASerie()
    {        
        sObs = addObsAdicionalAuto();    
        umMetodo.atualizarChapasPelasSeriesTXT(sChapa, sObs, sSerie);          
    }        
    
    private void btnAtualizaChapasGenericasPorVerdadeirasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizaChapasGenericasPorVerdadeirasActionPerformed
         LexTXT();          
    }//GEN-LAST:event_btnAtualizaChapasGenericasPorVerdadeirasActionPerformed

    private void btnAtualizaNomeEstacaoParaMonitorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizaNomeEstacaoParaMonitorActionPerformed
        if(umMetodo.atualizaNomesDeEstacoesParaMonitor()){
            JOptionPane.showMessageDialog(null, "Todos os registros com TipoId = 2 tiveram seus campos ESTAÇÃO atualizados para MONITOR com sucesso!");
        }else{
            JOptionPane.showMessageDialog(null, "Erro ao tentar atualizar os registros com TipoId = 2 e não tiveram seus campos ESTAÇÃO atualizados para MONITOR","Erro ao tentar atualizar",2);
        }
    }//GEN-LAST:event_btnAtualizaNomeEstacaoParaMonitorActionPerformed
          
    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("img_icones_forms/LogonDaPMSP.png")));
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
            java.util.logging.Logger.getLogger(F_APOIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_APOIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_APOIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_APOIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                F_APOIO dialog = new F_APOIO(new javax.swing.JFrame(), true);
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
    public javax.swing.JButton btnAtualizaChapasGenericasPorVerdadeiras;
    public javax.swing.JButton btnAtualizaNomeEstacaoParaMonitor;
    public javax.swing.JButton btnCadastroClientesSemUsuario;
    public javax.swing.JButton btnCadastroClientesVirtuais;
    public javax.swing.JButton btnExclusaoDeRegistros;
    public javax.swing.JButton btnExecutarAtualizacoesViaSQL;
    public javax.swing.JButton btnImportarTXT;
    private javax.swing.JButton btnPopularTblNomestacaoTMP;
    private javax.swing.JButton btnSair;
    public javax.swing.JButton btnTransferirParaCGGM;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTITULO;
    // End of variables declaration//GEN-END:variables

}
