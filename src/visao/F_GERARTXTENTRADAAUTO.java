package visao;

import biblioteca.Biblioteca;
import biblioteca.GerarTXT;
import biblioteca.MetodosPublicos;
import biblioteca.SelecionarArquivoTexto;
import static biblioteca.VariaveisPublicas.tabela_da_lista;
import static biblioteca.VariaveisPublicas.TipoModelo;
import static biblioteca.VariaveisPublicas.codTipoSelecionado;
import static biblioteca.VariaveisPublicas.codigoTipoModelo;
import static biblioteca.VariaveisPublicas.imprimirPorModelo;
import static biblioteca.VariaveisPublicas.dataDoDia;
import static biblioteca.VariaveisPublicas.lstAuxiliar;
import static biblioteca.VariaveisPublicas.lstListaCampos;
import static biblioteca.VariaveisPublicas.reativando;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class F_GERARTXTENTRADAAUTO extends javax.swing.JDialog {
    MetodosPublicos     umMetodo    = new MetodosPublicos();
    Biblioteca          umaBiblio   = new Biblioteca();
    GerarTXT            objGerarTXT = new GerarTXT();
    DefaultListModel    model       = new DefaultListModel();
    DateFormat          sdf         = new SimpleDateFormat("dd/MM/yyyy");
    Date dataDia                    = dataDoDia; 
   
    
    String sTipo, sChapa, sSerie, sEstacao, sObs,caminhoTXT, linha  = "";
    int iTipoid, codItem, totalLinhas = 0;
    Boolean metodoPADRAOINIFIM,inserindo = false;    
    
    public F_GERARTXTENTRADAAUTO(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Leitura();
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar

        //Impede que formulario seja arrastado na tela
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                setEnabled(false);
                setEnabled(true);
            }
        });//fim addComponentListener

        //configuracoes dos edits      
        umMetodo.configurarBotoes(btnGerarTXT);
        umMetodo.configurarBotoes(btnLerTxtAdicionarItensNaLista);
        umMetodo.configurarBotoes(btnLimpar);
        umMetodo.configurarBotoes(btnSair);
        umMetodo.configurarBotoes(btnNovo);
        umMetodo.configurarBotoes(btnRemoverItem);
        
        lstITENS.setForeground(Color.blue);        
        lstITENS.setFont(new Font("TimesRoman", Font.BOLD, 14));            
        
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPANELTOTAL = new javax.swing.JPanel();
        jBoxPesquisar1 = new javax.swing.JLayeredPane();
        txtTIPO = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtSECAO = new javax.swing.JTextField();
        txtMODELO = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbSTATUS = new javax.swing.JComboBox<>();
        btnVERCONFIGTXT = new javax.swing.JButton();
        btnGerarTXT = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnLerTxtAdicionarItensNaLista = new javax.swing.JButton();
        btnRemoverItem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstITENS = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        jBoxPesquisar1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxPesquisar1.setName("panelDados"); // NOI18N

        txtTIPO.setEditable(false);
        txtTIPO.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTIPO.setForeground(new java.awt.Color(51, 51, 255));

        jLabel6.setText("TIPO");

        jLabel2.setText("SEÇÃO");

        txtSECAO.setEditable(false);
        txtSECAO.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSECAO.setForeground(new java.awt.Color(51, 51, 255));

        txtMODELO.setEditable(false);
        txtMODELO.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtMODELO.setForeground(new java.awt.Color(51, 51, 255));

        jLabel7.setText("MODELO");

        jLabel8.setText("CONTRATO?");

        cmbSTATUS.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        cmbSTATUS.setForeground(new java.awt.Color(51, 51, 255));
        cmbSTATUS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NAO", "SIM" }));
        cmbSTATUS.setSelectedIndex(-1);
        cmbSTATUS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbSTATUS.setEnabled(false);

        btnVERCONFIGTXT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnVERCONFIGTXT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/configs.png"))); // NOI18N
        btnVERCONFIGTXT.setText("CONFIGURACAO TXT");
        btnVERCONFIGTXT.setToolTipText("Informações sobre as configurações do TXT");
        btnVERCONFIGTXT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVERCONFIGTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVERCONFIGTXTActionPerformed(evt);
            }
        });

        jBoxPesquisar1.setLayer(txtTIPO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxPesquisar1.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxPesquisar1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxPesquisar1.setLayer(txtSECAO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxPesquisar1.setLayer(txtMODELO, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxPesquisar1.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxPesquisar1.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxPesquisar1.setLayer(cmbSTATUS, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jBoxPesquisar1.setLayer(btnVERCONFIGTXT, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jBoxPesquisar1Layout = new javax.swing.GroupLayout(jBoxPesquisar1);
        jBoxPesquisar1.setLayout(jBoxPesquisar1Layout);
        jBoxPesquisar1Layout.setHorizontalGroup(
            jBoxPesquisar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxPesquisar1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxPesquisar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBoxPesquisar1Layout.createSequentialGroup()
                        .addGroup(jBoxPesquisar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSECAO, javax.swing.GroupLayout.PREFERRED_SIZE, 697, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jBoxPesquisar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jBoxPesquisar1Layout.createSequentialGroup()
                                .addComponent(cmbSTATUS, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnVERCONFIGTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jBoxPesquisar1Layout.createSequentialGroup()
                        .addGroup(jBoxPesquisar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTIPO, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jBoxPesquisar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jBoxPesquisar1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtMODELO))))
                .addContainerGap())
        );
        jBoxPesquisar1Layout.setVerticalGroup(
            jBoxPesquisar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxPesquisar1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jBoxPesquisar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jBoxPesquisar1Layout.createSequentialGroup()
                        .addGroup(jBoxPesquisar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMODELO, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTIPO, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jBoxPesquisar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jBoxPesquisar1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jBoxPesquisar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnVERCONFIGTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jBoxPesquisar1Layout.createSequentialGroup()
                                .addGroup(jBoxPesquisar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSECAO, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jBoxPesquisar1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(cmbSTATUS, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        btnGerarTXT.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnGerarTXT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TICK.PNG"))); // NOI18N
        btnGerarTXT.setText("Gerar TXT");
        btnGerarTXT.setToolTipText("");
        btnGerarTXT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGerarTXT.setEnabled(false);
        btnGerarTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarTXTActionPerformed(evt);
            }
        });

        btnLimpar.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_limpar.gif"))); // NOI18N
        btnLimpar.setText("Cancelar");
        btnLimpar.setToolTipText("");
        btnLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpar.setEnabled(false);
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnSair.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sair.gif"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.setToolTipText("");
        btnSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        btnNovo.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_blocoNotas.gif"))); // NOI18N
        btnNovo.setText("Novo");
        btnNovo.setToolTipText("");
        btnNovo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnLerTxtAdicionarItensNaLista.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnLerTxtAdicionarItensNaLista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_Ok1.gif"))); // NOI18N
        btnLerTxtAdicionarItensNaLista.setText("Ler TXT");
        btnLerTxtAdicionarItensNaLista.setToolTipText("");
        btnLerTxtAdicionarItensNaLista.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLerTxtAdicionarItensNaLista.setEnabled(false);
        btnLerTxtAdicionarItensNaLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLerTxtAdicionarItensNaListaActionPerformed(evt);
            }
        });

        btnRemoverItem.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        btnRemoverItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_Cancelar.gif"))); // NOI18N
        btnRemoverItem.setText("Remover Item");
        btnRemoverItem.setToolTipText("");
        btnRemoverItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemoverItem.setEnabled(false);
        btnRemoverItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverItemActionPerformed(evt);
            }
        });

        lstITENS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lstITENS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstITENSMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lstITENS);

        javax.swing.GroupLayout jPANELTOTALLayout = new javax.swing.GroupLayout(jPANELTOTAL);
        jPANELTOTAL.setLayout(jPANELTOTALLayout);
        jPANELTOTALLayout.setHorizontalGroup(
            jPANELTOTALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPANELTOTALLayout.createSequentialGroup()
                .addGroup(jPANELTOTALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBoxPesquisar1)
                    .addGroup(jPANELTOTALLayout.createSequentialGroup()
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGerarTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLerTxtAdicionarItensNaLista, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnRemoverItem, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPANELTOTALLayout.setVerticalGroup(
            jPANELTOTALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPANELTOTALLayout.createSequentialGroup()
                .addComponent(jBoxPesquisar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPANELTOTALLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGerarTXT, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLerTxtAdicionarItensNaLista, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemoverItem, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPANELTOTAL);
        jPANELTOTAL.setBounds(14, 11, 1020, 650);

        setSize(new java.awt.Dimension(1055, 700));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Leitura() 
    {        
        txtSECAO.setText("INFORMATICA");
        sTipo  = txtTIPO.getText();
        limpar();      
    }    
    
    private String gerarNumeroChapa(){
        
        sChapa = "008"+umMetodo.gerarNumeroAleatorio();
        
        while(umMetodo.temCadastradoNaTabela("tblPatrimonios", "chapa", sChapa))
        {  
            sChapa = "008"+umMetodo.gerarNumeroAleatorio();
        }
        
        return sChapa;
    }        
      
    private void btnGerarTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarTXTActionPerformed
       //Metodo MANUAL inserido a série inteira
       lstAuxiliar.clear(); //limpando as series adicionadas para verificação de duplicidade e inserindo a string completa com todos os dados
       
       for(int i = 0; i < model.size(); i++)
       {
            lstAuxiliar.add(model.get(i).toString());
       }
       
       objGerarTXT.gerarTXTDELISTA(lstAuxiliar);     
       
       btnLimparActionPerformed(null);           
       cmbSTATUS.setEnabled(false);
       inserindo=false;
       imprimirPorModelo = false;
       this.setTitle("Gerar arquivo TXT para cadastro automático a partir da leitura de outro TXT contendo os números de séries");
      
    }//GEN-LAST:event_btnGerarTXTActionPerformed

    private void limpar(){
        btnNovo.setEnabled(true);
        btnSair.setEnabled(true);
        btnVERCONFIGTXT.setEnabled(true);
        btnGerarTXT.setEnabled(false);
        btnLerTxtAdicionarItensNaLista.setEnabled(false);
        btnLimpar.setEnabled(false);
        btnRemoverItem.setEnabled(false);
        cmbSTATUS.setEnabled(false);
        txtTIPO.setText("");
        txtMODELO.setText("");     
        txtSECAO.setText("");      
        cmbSTATUS.setSelectedIndex(-1);
        lstListaCampos.clear();
        model.clear();
        inserindo=false; 
        imprimirPorModelo = false;
        lstAuxiliar.clear();
        this.setTitle("Gerar arquivo TXT para cadastro automático a partir da leitura de outro TXT contendo os números de séries");
        
    }
    
    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limpar();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        //abrir lista de tipos de equipamentos para cadastrar
        inserindo=true;
        F_LISTATIPOS frmTipos = new F_LISTATIPOS(new javax.swing.JFrame(), true);
        frmTipos.setVisible(true);
        //se este tipo tiver clientes virturais dos setores quero abrir a lista com eles e não a lista com os servidores
        sTipo = frmTipos.getItemSelecionado();       
        txtTIPO.setText(sTipo);  
        codTipoSelecionado = umMetodo.getCodigoPassandoString("tbltipos", "tipo", sTipo);      
        
        //abre a lista de modelos
        imprimirPorModelo = false;
        tabela_da_lista   = "TBLMODELOS";
        F_LISTAMODELOSGERARTXT frm = new F_LISTAMODELOSGERARTXT(new javax.swing.JFrame(), true);
        frm.setVisible(true);                    
        txtMODELO.setText(TipoModelo);  
                        
        //habilitando edição do txtSerie     
        txtSECAO.setText("INFORMATICA");      
        cmbSTATUS.setSelectedIndex(0);    
        btnNovo.setEnabled(false); 
        btnVERCONFIGTXT.setEnabled(false); 
        cmbSTATUS.setEnabled(true);
        btnLerTxtAdicionarItensNaLista.setEnabled(true);
        lstAuxiliar.clear();
        model.clear();
        
    }//GEN-LAST:event_btnNovoActionPerformed
    
private void LerSeriesDoTXT() 
{
    SelecionarArquivoTexto select = new SelecionarArquivoTexto();
    caminhoTXT = select.ImportarTXT();
    
    int totalLinhasTXT = umMetodo.contarLinhasDoArquivoTXT(caminhoTXT);

    if (caminhoTXT == null || caminhoTXT.isEmpty()) return;
    
    //Desabilita os botões
    cmbSTATUS.setEnabled(false);
    lstITENS.setEnabled(false);
    btnSair.setEnabled(false);
    btnLimpar.setEnabled(false);
    btnLerTxtAdicionarItensNaLista.setEnabled(false);
    
    setTitle("Processando a leitura de "+ totalLinhasTXT +" registros ao final do processamento os botões serão habilitados para prosseguimento favor aguardar..." );
    
    // Cria e mostra a barra de progresso
    F_BARRAPROGRESSOLENDOTXT barra = new F_BARRAPROGRESSOLENDOTXT((Frame) SwingUtilities.getWindowAncestor(this));
    barra.setAlwaysOnTop(true);
    barra.setVisible(true);

    // Calcula total de linhas antes do SwingWorker para não bloquear a UI
    totalLinhas = 0;
    try (BufferedReader contador = new BufferedReader(new FileReader(caminhoTXT))) {
        while (contador.readLine() != null) {
            totalLinhas++;
        }
    } catch (IOException e) {
        e.printStackTrace();
        barra.dispose();
        return;
    }

    // Inicia a leitura real no SwingWorker
    SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
        @Override
        protected Void doInBackground() throws Exception {
            lstAuxiliar.clear();

            try (BufferedReader lerArq = new BufferedReader(new FileReader(caminhoTXT))) {
                String linha;
                int linhaAtual = 0;

                while ((linha = lerArq.readLine()) != null) {
                    lstAuxiliar.add(linha);
                    linhaAtual++;
                    publish(linhaAtual); // atualiza a barra
                    Thread.sleep(30); // força a UI a respirar, opcional
                }
            }

            return null;
        }

        @Override
        protected void process(java.util.List<Integer> chunks) {
            int linhaAtual = chunks.get(chunks.size() - 1);
            barra.atualizarProgressoPelaQdeRegs(linhaAtual, totalLinhas);
        }

       @Override
        protected void done() {
            barra.dispose(); // Fecha a barra assim que a leitura termina

            // Agora, cria um novo SwingWorker só para adicionar os itens ao modelo
            SwingWorker<Void, Void> adicionaItensWorker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    addItensAoTXT();  // Adiciona todos os itens à lista visual
                    return null;
                }

                @Override
                protected void done() {
                    lstITENS.repaint();  // Força atualização da UI  
                    
                    // Reabilita os botões em caso de erro
                    btnGerarTXT.setEnabled(true);
                    btnLimpar.setEnabled(true);
                    lstITENS.setEnabled(true);
                    
                    setTitle("Total de ítens inseridos : " + lstAuxiliar.size());
                }
            };

            adicionaItensWorker.execute(); // Executa o segundo processo
        }
    };      
    worker.execute();
}

    
    private void btnLerTxtAdicionarItensNaListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLerTxtAdicionarItensNaListaActionPerformed
        
        //Fazer a leitura do arquivo TXT
        LerSeriesDoTXT();   
             
    }//GEN-LAST:event_btnLerTxtAdicionarItensNaListaActionPerformed

private void addItensAoTXT() {
    int qtdItens = lstAuxiliar.size();

    int i = 0;
    for (i = 0; i < qtdItens; i++) {

        sChapa = gerarNumeroChapa();
        sSerie = lstAuxiliar.get(i);

        // VERIFICAR SE NÃO FOR MICRO OU NOTEBOOK ENTRAR COM O TIPO 1/19    
        iTipoid = umMetodo.getCodigoPassandoString("tbltipos", "tipo", sTipo);

        if ((iTipoid == 1) || (iTipoid == 19)) {
            sEstacao = "PGMCGGMC000";
        } else {
            sEstacao = sTipo;
        }

        // adicionando item na lista    
        int iCodigo = umMetodo.getCodigoPassandoString("tblpatrimonios", "serie", sSerie);

        String obsAtual = umMetodo.getStringPassandoCodigo("tblPatrimonios", "observacoes", iCodigo);
        String novaObs = obsAtual + ("\n" + sdf.format(dataDia) + " " + sObs);

        if (reativando) {
            String dados = sChapa + ";" + sSerie + ";" + iTipoid + ";" + "30;" + "202;" + codigoTipoModelo + ";" + "6;" + sEstacao + ";" + "N;" + novaObs;
            String item = dados;
            SwingUtilities.invokeLater(() -> {
                model.addElement(item);
                lstITENS.setModel(model);
            });
        } else {
            String dados = sChapa + ";" + sSerie + ";" + iTipoid + ";" + "30;" + "202;" + codigoTipoModelo + ";" + "6;" + sEstacao + ";" + "N;";
            String item = dados;
            SwingUtilities.invokeLater(() -> {
                model.addElement(item);
                lstITENS.setModel(model);
            });
        }
    }
}    
    
    private void removerItem(){
        model.remove(codItem);       
        btnRemoverItem.setEnabled(false);
        btnLerTxtAdicionarItensNaLista.setEnabled(false);
        this.setTitle("TOTAL DE ÍTENS INSERIDOS : "+String.valueOf(lstAuxiliar.size()-1));
    }
    
    private void btnRemoverItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverItemActionPerformed
        removerItem();
    }//GEN-LAST:event_btnRemoverItemActionPerformed

    private void lstITENSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstITENSMouseClicked
        codItem = lstITENS.getSelectedIndex();  
        btnRemoverItem.setEnabled(true);
    }//GEN-LAST:event_lstITENSMouseClicked

    private void btnVERCONFIGTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVERCONFIGTXTActionPerformed
        String texto = (""
               + "=================== INFORMAÇÕES SOBRE O CONTEÚDO DO ARQUIVO TXT ==================\n\n" 
               + "Um  arquivo  TXT  deverá  conter  as  SÉRIES dos equipamentos onde após  sua  leitura, o Sistema\n"
               + "irá  gerar  um   novo  TXT  com   as  configurações  iniciais   apontando  para  CGGM-INFORMATICA\n"
               + "esse  novo  arquivo  deverá  ser  lido  para  cadastrar os equipamentos listados  automaicamente.\n" 
               + "Segue  um  exemplo  das  linhas  do  arquivo TXT    -   Sem  a  linha   do  título  somente  os  dados.\n\n" 
               + "005E5WDD\n"
               + "002E5WDD\n"
               + "002EESDD\n" 
               + "002E5WSD\n"
               + "002E5WEE\n"
               + "002E5EWD\n"
               + "002E5WGD\n"
               + "002SSWDD\n"
               + "002SE5GD\n"
               + "002SSDBD\n"
               + "");             
        
        F_CONFIGURACAOTXT frm = new F_CONFIGURACAOTXT(null, true, texto);
        frm.setVisible(true); 
    }//GEN-LAST:event_btnVERCONFIGTXTActionPerformed

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
            java.util.logging.Logger.getLogger(F_GERARTXTENTRADAAUTO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_GERARTXTENTRADAAUTO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_GERARTXTENTRADAAUTO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_GERARTXTENTRADAAUTO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                F_GERARTXTENTRADAAUTO dialog = new F_GERARTXTENTRADAAUTO(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGerarTXT;
    private javax.swing.JButton btnLerTxtAdicionarItensNaLista;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnRemoverItem;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnVERCONFIGTXT;
    private javax.swing.JComboBox<String> cmbSTATUS;
    private javax.swing.JLayeredPane jBoxPesquisar1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPANELTOTAL;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstITENS;
    private javax.swing.JTextField txtMODELO;
    private javax.swing.JTextField txtSECAO;
    private javax.swing.JTextField txtTIPO;
    // End of variables declaration//GEN-END:variables
}
