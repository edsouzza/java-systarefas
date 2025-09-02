package visao;

import Dao.DAOPatriTensTransferido;
import Dao.DAOPatrimonio;
import biblioteca.Biblioteca;
import biblioteca.MetodosPublicos;
import static biblioteca.VariaveisPublicas.controlenaveg;
import static biblioteca.VariaveisPublicas.patriDeptos;
import biblioteca.ModeloTabela;
import biblioteca.SomenteNumeros;
import static biblioteca.VariaveisPublicas.lstListaGenerica;
import static biblioteca.VariaveisPublicas.lstListaInteiros;
import static biblioteca.VariaveisPublicas.serieRetornada;
import static biblioteca.VariaveisPublicas.numemoParaImprimir;
import static biblioteca.VariaveisPublicas.destinoMemorando;
import static biblioteca.VariaveisPublicas.origemMemorando;
import static biblioteca.VariaveisPublicas.chapaRetornada;
import static biblioteca.VariaveisPublicas.codModeloRetornado;
import static biblioteca.VariaveisPublicas.editandoMemorando;
import static biblioteca.VariaveisPublicas.numemoParaEditar;
import static biblioteca.VariaveisPublicas.relAssuntoMemo;
import static biblioteca.VariaveisPublicas.relPorAssunto;
import static biblioteca.VariaveisPublicas.totalRegs;
import static biblioteca.VariaveisPublicas.valorItem;
import conexao.ConnConexao;
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import relatorios.GerarRelatorios;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import controle.ControleGravarLog;
import controle.CtrlPatriItenstransferido;
import controle.CtrlPatriTransferido;
import java.util.Iterator;
import modelo.PatriTensTransferido;
import modelo.PatriTransferido;


public class F_EDITARMEMOITENSTRANSFERIDOS extends javax.swing.JFrame {
    ConnConexao                    conexao                      = new ConnConexao();
    Biblioteca                     umabiblio                    = new Biblioteca();
    SomenteNumeros                 soNumeros                    = new SomenteNumeros();
    ControleGravarLog              umGravarLog                  = new ControleGravarLog();      
    MetodosPublicos                umMetodo                     = new MetodosPublicos();
    CtrlPatriItenstransferido      umCtrlPatrItemTranferido     = new CtrlPatriItenstransferido();
    CtrlPatriTransferido           umCtrlPatriTranferido        = new CtrlPatriTransferido();
    PatriTransferido               umModPatriTransferido        = new PatriTransferido();
    PatriTensTransferido           umModPatrItensTransferido    = new PatriTensTransferido();
    DAOPatrimonio                  umPatrimonioDAO              = new DAOPatrimonio();
    DAOPatriTensTransferido        umDAOPatriItens              = new DAOPatriTensTransferido();
    
    
    String sqlPatriCGGM    = "SELECT i.*, m.* FROM TBLITENSMEMOTRANSFERIDOS i, TBLMODELOS m WHERE i.modeloid=m.codigo AND i.status = 'PROCESSANDO' ORDER BY i.item";  
    String observacao, numemoinicial, destinoMemo, sNumemo, sStatus, sSerie, sqlFiltro;
    int icodigo, codExc, codItem, Item, TotalItens, codigoPatri = 0;
    boolean mostrouForm, adicionouItem;
    ArrayList listaDados                        = new ArrayList();
    ArrayList<Integer>listaCodigos              = new ArrayList();
    
    
    public F_EDITARMEMOITENSTRANSFERIDOS() {
        initComponents();
        Leitura();        
        setResizable(false);              //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        this.setTitle("Editar e Gerenciar Memorando de Transferência ou Baixa de Equipamentos");
        
        // Colocando enter para pular de campo 
        HashSet conj = new HashSet(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS)); 
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0)); 
        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);         
        
        //umabiblio.configurarCamposTextos(txtDESTINO);
        txtORIGEM.setFont(new Font("TimesRoman",Font.BOLD,16)); 
        txtORIGEM.setForeground(Color.red);               
        
        txtDESTINO.setFont(new Font("TimesRoman",Font.BOLD,16)); 
        txtDESTINO.setForeground(Color.red);        
        
        txtASSUNTO.setFont(new Font("TimesRoman",Font.BOLD,16)); 
        txtASSUNTO.setForeground(Color.red);       
        
        txtOBSERVACAO.setFont(new Font("TimesRoman",Font.BOLD,16)); 
        txtOBSERVACAO.setForeground(Color.red);    
        
        umabiblio.configurarCamposTextos(txtPESQUISA);
        txtPESQUISA.setFont(new Font("TimesRoman",Font.BOLD,16));
        txtPESQUISA.setForeground(Color.red);
        
                
        //configuração dos botões
        umabiblio.configurarBotoes(btnAdicionar);
        umabiblio.configurarBotoes(btnExcluirItem);
        umabiblio.configurarBotoes(btnImprimir);
        umabiblio.configurarBotoes(btnCancelar);
        umabiblio.configurarBotoes(btnSair);
               
        //cofigurações das tabelas
        jTabela.setFont(new Font("TimesRoman",Font.BOLD,12));
        jTabela.setForeground(Color.blue);
        jTabela.setFocusable(false);   //RETIRANDO O FOCO DA TABELA PARA ABRIR FOCO DIRETO NO TXTNUMERO        
        txtNUMEMO.setFocusable(false); //RETIRANDO O FOCO DO NUMERO PARA ABRIR FOCO DIRETO NO TXTNUMERO        
        
        
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabela = new javax.swing.JTable();
        btnAdicionar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        txtNUMEMO = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDESTINO = new javax.swing.JTextField();
        txtOBSERVACAO = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnExcluirItem = new javax.swing.JButton();
        txtORIGEM = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtASSUNTO = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        txtPESQUISA = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 733));

        jTabela.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabelaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTabela);

        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/users16.jpg"))); // NOI18N
        btnAdicionar.setText("Add Equipamentos");
        btnAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_imprimir.gif"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImprimir.setEnabled(false);
        btnImprimir.setPreferredSize(new java.awt.Dimension(77, 25));
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sair.gif"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSair.setPreferredSize(new java.awt.Dimension(77, 25));
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        txtNUMEMO.setEditable(false);
        txtNUMEMO.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtNUMEMO.setForeground(new java.awt.Color(255, 0, 0));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 255));
        jLabel4.setText("MEMO");

        txtDESTINO.setEditable(false);
        txtDESTINO.setForeground(new java.awt.Color(51, 51, 255));
        txtDESTINO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDESTINOMouseClicked(evt);
            }
        });

        txtOBSERVACAO.setEditable(false);
        txtOBSERVACAO.setForeground(new java.awt.Color(51, 51, 255));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 255));
        jLabel6.setText("OBSERVAÇÃO");

        btnExcluirItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sairPrograma.gif"))); // NOI18N
        btnExcluirItem.setText("Excluir Ítem");
        btnExcluirItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirItem.setEnabled(false);
        btnExcluirItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirItemActionPerformed(evt);
            }
        });

        txtORIGEM.setEditable(false);
        txtORIGEM.setForeground(new java.awt.Color(51, 51, 255));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 255));
        jLabel7.setText("DESTINO");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 255));
        jLabel8.setText("ORIGEM");

        txtASSUNTO.setEditable(false);
        txtASSUNTO.setForeground(new java.awt.Color(51, 51, 255));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 255));
        jLabel9.setText("ASSUNTO");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sairPrograma.gif"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txtPESQUISA.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtPESQUISA.setForeground(new java.awt.Color(51, 51, 255));
        txtPESQUISA.setToolTipText("Barra de Pesquisa Rápida");
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtNUMEMO, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtORIGEM, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDESTINO, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtASSUNTO)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(txtOBSERVACAO)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnExcluirItem, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1012, Short.MAX_VALUE)
                            .addComponent(txtPESQUISA))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtPESQUISA, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNUMEMO, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtORIGEM, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtASSUNTO, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDESTINO, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOBSERVACAO, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluirItem, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1040, 824));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
        
    private void PreencherDadosMemo()
    {        
        listaDados = umMetodo.getDadosMemorando(numemoParaImprimir);       
        
            Iterator<String> iterator = listaDados.iterator();
            while (iterator.hasNext()) {        
                txtNUMEMO.setText((String)     iterator.next());
                txtORIGEM.setText((String)     iterator.next());
                txtDESTINO.setText((String)    iterator.next());
                txtASSUNTO.setText((String)    iterator.next());
                txtOBSERVACAO.setText((String) iterator.next());        
            }                     
    }
           
    private void Leitura() 
    {                      
        PreencherTabela(sqlPatriCGGM);
        PreencherDadosMemo(); 
        
        btnImprimir.setEnabled(false);   
        
        txtNUMEMO.setEditable(false);
        txtORIGEM.setEditable(false);
        txtDESTINO.setEditable(false);
        txtASSUNTO.setEditable(false);
        txtOBSERVACAO.setEditable(false);        
         
        lstListaInteiros.clear();  
        mostrouForm       = false;    
        editandoMemorando = true;
        controlenaveg     = 0;
        valorItem         = umMetodo.getQdeRegsPeloStatus("TBLITENSMEMOTRANSFERIDOS", "PROCESSANDO"); 
        //JOptionPane.showMessageDialog(rootPane, "Valor do Item = "+valorItem);
    }
    
    private void atualizarStatusDosItens(){
        numemoParaEditar = txtNUMEMO.getText();
        destinoMemo      = umMetodo.getDestinoMemorando(numemoParaEditar);
        if(!destinoMemo.equals("BAIXA"))
        {
            umMetodo.setarStatusDosItensDoMemorando("TRANSFERIDO", numemoParaEditar);  
        }else{
            umMetodo.setarStatusDosItensDoMemorando("BAIXADO", numemoParaEditar);  
        }          
    }
        
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        
        umGravarLog.gravarLog("cancelando edição do memorando "+txtNUMEMO.getText());            
        if(adicionouItem){
           deletarItensAdicionadosAoMemorando();  
        }else{
           atualizarStatusDosItens();               
        }
        dispose();     
       
    }//GEN-LAST:event_btnSairActionPerformed

    private void gravarItemNaTabeala() 
    {        
        //GRAVA O REGISTRO SELECIONADO NA TABELA TBLITENSMEMOTRANSFERIDOS PARA POSTERIOR IMPRESSAO  
        sNumemo             = txtNUMEMO.getText();
        destinoMemorando    = txtDESTINO.getText();
        origemMemorando     = txtORIGEM.getText();
        sStatus             = "PROCESSANDO";        
        
        //SETANDO OS VALORES NO MODELO PARA GRAVAR
        umModPatrItensTransferido.setItem(valorItem);
        umModPatrItensTransferido.setNumemo(sNumemo);
        umModPatrItensTransferido.setModeloid(codModeloRetornado);        
        umModPatrItensTransferido.setSerie(serieRetornada);
        umModPatrItensTransferido.setChapa(chapaRetornada);      
        umModPatrItensTransferido.setOrigem(origemMemorando);     
        umModPatrItensTransferido.setDestino(destinoMemorando);     
        umModPatrItensTransferido.setStatus(sStatus);
        
        umCtrlPatrItemTranferido.salvarPatriItensTransferido(umModPatrItensTransferido);
        umGravarLog.gravarLog("Adição de itens ao memorando de transferencia "+sNumemo);
        adicionouItem = true;     
        
    }
        
    
    private void AdicionarPatrimoniosAoMemorando(){                  
    
        F_LISTAPATRIMONIOS frmPatrimonios = new F_LISTAPATRIMONIOS(this, true);
        frmPatrimonios.setVisible(true);
        btnImprimir.setEnabled(true);   
        
        //VerificarDuplicidade
        if(umabiblio.duplicidadeDeCadastroMemo("TBLITENSMEMOTRANSFERIDOS", "serie", serieRetornada)){    
        //Mostra msg de duplicidade    
        }else{
            //grava registro do item na tabela TBLITENSMEMOTRANSFERIDOS   
            gravarItemNaTabeala();
            
            int ultimoCod = umMetodo.mostrarUltimoCodigo("TBLITENSMEMOTRANSFERIDOS");
            ArrayList<Integer>listaCodigosPosExclusao   = new ArrayList();
            
//            for (Object dado : listaCodigos) {
//                System.out.println(dado); 
//            }

        }                  
        
        PreencherTabela(sqlPatriCGGM);         
                
        btnSair.setEnabled(false);   
        btnExcluirItem.setEnabled(false);       
        controlenaveg=0;                
    }
    
    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
               
        AdicionarPatrimoniosAoMemorando();           
        
    }//GEN-LAST:event_btnAdicionarActionPerformed
        
    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        //AO CLICAR EM UM REGISTRO DA TABELA MOSTRAR OS DADOS NOS EDITS
        codItem = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0); 
        Item    = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 1); 
        sSerie  = (String) jTabela.getValueAt(jTabela.getSelectedRow(), 3); 
        //JOptionPane.showMessageDialog(null, "CODIGO DO ÍTEM SELECIONADO...: "+codItem); 
        
        btnImprimir      .setEnabled(false);
        btnAdicionar     .setEnabled(false);
        btnExcluirItem   .setEnabled(true);
        btnSair          .setEnabled(true);                 

    }//GEN-LAST:event_jTabelaMouseClicked
            
    private void selecionarTipoDeRelatorio(){
        //Se for normal ou de baixa
        numemoParaEditar = txtNUMEMO.getText();
        destinoMemo      = umMetodo.getDestinoMemorando(numemoParaEditar);
        if(!destinoMemo.equals("BAIXA"))
        {
            umMetodo.setarStatusDosItensDoMemorando("TRANSFERIDO", numemoParaEditar);  
        }else{
            umMetodo.setarStatusDosItensDoMemorando("BAIXADO", numemoParaEditar);  
        }  
       
    }
       
    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        /*IMPRIMINDO RELATORIO DOS PATRIMONIOS TRANSFERIDOS VERIFICANDO SE O ARQUIVO EXISTE RETORNA TRUE/FALSE
        System.out.println(new File("relatorio/relmemotransferidos.jasper").exists()); */      
        relAssuntoMemo    = txtASSUNTO.getText();
        if(relAssuntoMemo.equals("DEVOLUCAO DE EQUIPAMENTOS")){
            relPorAssunto = true;    
        }
        atualizarStatusDosItens(); 
        
        GerarRelatorios objRel = new GerarRelatorios();
        try {
            
            //Se for normal ou de baixa            
            numemoParaEditar  = txtNUMEMO.getText();
            destinoMemo       = umMetodo.getDestinoMemorando(numemoParaEditar);
            
            if(!destinoMemo.equals("BAIXA") && (!relPorAssunto))
            {
                objRel.imprimirPatrimoniosTransferidos("relatorio/relmemotransferidos.jasper", numemoParaEditar); 
            }else if(destinoMemo.equals("BAIXA")){                
                F_ESCOLHAIMPRESSAOINSERVIVEIS frm = new F_ESCOLHAIMPRESSAOINSERVIVEIS();
                frm.setVisible(true); 
            }else if(relAssuntoMemo.equals("DEVOLUCAO DE EQUIPAMENTOS")){
                F_ESCOLHAIMPRESSAODEVOLUCAO frm = new F_ESCOLHAIMPRESSAODEVOLUCAO();
                frm.setVisible(true);
                relPorAssunto = false;   
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);                
        }    
                 
        /*=============================================================================================================================================
                                                    INATIVAR PATRIMONIOS INSERIDOS NA EDIÇÃO DO MEMORANDO
        ===============================================================================================================================================*/
        
        int totalRegs = lstListaGenerica.size();
        
        for(int i=0; i < totalRegs; i++){
            String pCod = lstListaGenerica.get(i);
            int iCod = Integer.parseInt(pCod);
            umMetodo.inativarRegistrosPeloCodigo(iCod);
        } 
        
        //Sair do formulario ao termino da impressao
        dispose();             
        
    }//GEN-LAST:event_btnImprimirActionPerformed
    
    private void ExcluirItemPeloCodigo()
    {
        //EXCLUINDO ITEM DO MEMO ATUAL PELO CODIGO
        ArrayList<Integer> lstListaItens = new ArrayList<>();
        
        numemoParaEditar = txtNUMEMO.getText();
        String message = "Confirma a exclusão do ítem com código "+codItem+" do memorando em curso?";
        String title   = "Confirmação de Exclusão";
        //Exibe caixa de dialogo (veja figura) solicitando confirmação ou não. 
        //Se o usuário clicar em "Sim" retorna 0 pra variavel reply, se informado não retorna 1
        
        //Codigo do patrimonios na tabela TBLPATRIMONIOS
        int codPatri = umMetodo.getCodigoPassandoString("tblpatrimonios", "serie", sSerie);
        
        int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) 
        {             
            //Reativando patrimonio pos exclusao de item em ediçao de memorando de encaminhamento            
            umPatrimonioDAO.updateStatusPosExclusaoItemDeMemoEnviado(codPatri, numemoParaEditar);
            
            if(umCtrlPatrItemTranferido.excluirItemDoMemoAtual(codItem))
            {
                JOptionPane.showMessageDialog(null, "Ítem com código "+codItem+" foi excluído com sucesso do memorando atual!");
                
                //Reorganizar os numeros dos ítens e depois mostrar com PreencherTabela(sqlDinamica)
                valorItem--;
                //reorganizarListaDeItensDoMemorandoAposExclusao(codItem);
                
                //Verificando se o memo atual ainda tem ítens apos a exclusao
                lstListaItens = umDAOPatriItens.ListaItemsAposExclusao();
                
                //Se ainda restarem ítens no memo em curso
                if(lstListaItens.size()>0)
                {
//                    JOptionPane.showMessageDialog(null,"Sim este memorando ainda tem ítens cadastrados após a exclusão");
//                    JOptionPane.showMessageDialog(null, "O total atual de itens após a exclusão é : "+lstListaGenerica.size());
                                       
                    //atualizar o valor do item
                    umCtrlPatrItemTranferido.atualizarValorDosItensAposExclusao(Item, numemoParaEditar);
                    btnAdicionar.setEnabled(true);
                    btnImprimir.setEnabled(true);
                                        
                }else{
                    //JOptionPane.showMessageDialog(null,"Não existem ítens cadastrados neste memorando após a exclusão");
                    //Tudo Ok por aqui...
                    btnAdicionar.setEnabled(true);
                }
                
                PreencherTabela(sqlPatriCGGM);       
                
            }
        }else{
            btnAdicionar.setEnabled(true);
        }            
            
        btnExcluirItem.setEnabled(false);   
        txtPESQUISA.setText(null);
        btnSair.setEnabled(false);
    }
    
    private void btnExcluirItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirItemActionPerformed
        //Se o memorando tiver apenas um ítem o mesmo não pode permitir a exclusao do ultimo ítem
        numemoParaEditar = txtNUMEMO.getText();
        int qdeItensNoMemo = umMetodo.retornarQdeRegistrosDoMemorando(numemoParaEditar);
        
        if( qdeItensNoMemo > 1){
            ExcluirItemPeloCodigo();            
        }else{
            JOptionPane.showMessageDialog(null, "Este ítem não pode ser excluído por ser único neste memorando, se deseja continuar pesquise o memorando e faça exclusão do mesmo!", "Exclusão de ítens do memorando", 2);
            btnExcluirItem.setEnabled(false);
            btnAdicionar.setEnabled(true);
            btnImprimir.setEnabled(true);
            btnSair.setEnabled(true);
        }
        btnSair.setEnabled(true);
        //mudar icone do botao     
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sairPrograma.gif")));
    }//GEN-LAST:event_btnExcluirItemActionPerformed

    private void txtDESTINOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDESTINOMouseClicked
        txtDESTINO.selectAll();
    }//GEN-LAST:event_txtDESTINOMouseClicked

    private void deletarItensAdicionadosAoMemorando()
    {
        int totalRegs = listaCodigos.size();
        
        for(int i=0; i < totalRegs; i++){
            int pCod = listaCodigos.get(i);
            umMetodo.deletarItensDoMemorandoPeloCodigo(pCod);
        } 
        atualizarStatusDosItens();
    }    
        
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        //Mensagem de confirmação se deseja sair e cancelar a operação
        if(btnCancelar.getText().equals("Cancelar")){
            if(umabiblio.ConfirmouOperacao("Tem certeza que deseja sair e cancelar a operação,os dados não salvos serão perdidos?", "Saindo da Edição do Memorando "+txtNUMEMO.getText())){
                umGravarLog.gravarLog("cancelando edicao do memorando "+txtNUMEMO.getText());
                //Se adicionou itens devera exclui-los da tabela TBLITENSMEMOTRANSFERIDOS
                deletarItensAdicionadosAoMemorando();            
                dispose();
            }            
        }else{
            txtPESQUISA.setText(null);
            txtPESQUISA.requestFocus();
            txtPESQUISAMouseClicked(null);
            btnCancelar.setText("Cancelar");
        }
        //mudar icone do botao     
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sairPrograma.gif")));
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtPESQUISAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPESQUISAMouseClicked
        //apagar campo de pesquisa mostrar todos os registros
        txtPESQUISA.setText(null);   
        PreencherTabela(sqlPatriCGGM);
    }//GEN-LAST:event_txtPESQUISAMouseClicked

    private void filtrarPorDigitacao(String pPesq, String snuMemo) {
        
        sqlFiltro = ("SELECT DISTINCT i.*, m.* FROM TBLITENSMEMOTRANSFERIDOS i, TBLMODELOS m WHERE "
                     + "(i.serie like '%" + pPesq + "%' OR i.chapa like '%" + pPesq + "%') AND i.modeloid=m.codigo AND i.status = 'PROCESSANDO' ORDER BY i.item");
        PreencherTabela(sqlFiltro);
        this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);      
        
    }
    
    
    private void txtPESQUISAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyReleased
        filtrarPorDigitacao(txtPESQUISA.getText(), sNumemo);
        btnCancelar.setText("Limpar");
        //mudar icone do botao     
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_limpar.gif")));
    }//GEN-LAST:event_txtPESQUISAKeyReleased

    private void txtPESQUISAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {            
            PreencherTabela(sqlFiltro);
        }
    }//GEN-LAST:event_txtPESQUISAKeyPressed
    
    public void PreencherTabela(String sql)
    {
        String[] Colunas;
        
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        Colunas = new String[]{"Cod.", "Ítem", "Descrição", "Série", "Chapa"};
        
        try {
            conexao.ExecutarPesquisaSQL(sql);
            
            if(!patriDeptos){
                while (conexao.rs.next()) {
                        dados.add(new Object[]{
                        conexao.rs.getInt("codigo"),
                        conexao.rs.getInt("item"),
                        conexao.rs.getString("modelo"),
                        conexao.rs.getString("serie"),
                        conexao.rs.getString("chapa")
                    });
                         totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
                         
                };
            }
            
            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabela.setModel(modelo);
            //define tamanho das colunas   
            jTabela.getColumnModel().getColumn(0).setPreferredWidth(50);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna      
            jTabela.getColumnModel().getColumn(1).setPreferredWidth(50);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(1).setResizable(false);    //nao será possivel redimencionar a coluna      
            jTabela.getColumnModel().getColumn(2).setPreferredWidth(550);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(2).setResizable(false);    //nao será possivel redimencionar a coluna        
            jTabela.getColumnModel().getColumn(3).setPreferredWidth(150);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(3).setResizable(false);    //nao será possivel redimencionar a coluna    
            jTabela.getColumnModel().getColumn(4).setPreferredWidth(150);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(4).setResizable(false);    //nao será possivel redimencionar a coluna        
              
            //define propriedades da tabela
            jTabela.getTableHeader().setReorderingAllowed(false);        //nao podera ser reorganizada
            jTabela.setAutoResizeMode(jTabela.AUTO_RESIZE_OFF);          //nao será possivel redimencionar a tabela
            jTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
        } catch (SQLException ex) {
            //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
            JOptionPane.showMessageDialog(null, "Erro ao preencher a Tabela de Patrimônios!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        }
    }
 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluirItem;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnSair;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabela;
    private javax.swing.JTextField txtASSUNTO;
    private javax.swing.JTextField txtDESTINO;
    private javax.swing.JTextField txtNUMEMO;
    private javax.swing.JTextField txtOBSERVACAO;
    private javax.swing.JTextField txtORIGEM;
    private javax.swing.JTextField txtPESQUISA;
    // End of variables declaration//GEN-END:variables
}
