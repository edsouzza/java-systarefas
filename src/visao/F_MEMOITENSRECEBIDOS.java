package visao;

import Dao.DAOPatriTensTransferido;
import Dao.DAOPatrimonio;
import biblioteca.Biblioteca;
import biblioteca.CampoTxtLimitadoPorQdeCaracteresUpperCase;
import biblioteca.CampoTxtLimitadoPorQdeCaracteres;
import biblioteca.MetodosPublicos;
import static biblioteca.VariaveisPublicas.numMemoTransferido;
import static biblioteca.VariaveisPublicas.origemTransferidos;
import static biblioteca.VariaveisPublicas.destinoTransferidos;
import static biblioteca.VariaveisPublicas.assuntoTransferido;
import static biblioteca.VariaveisPublicas.controlenaveg;
import static biblioteca.VariaveisPublicas.anoVigente;
import biblioteca.ModeloTabela;
import biblioteca.SomenteNumeros;
import static biblioteca.VariaveisPublicas.codigoUsuario;
import static biblioteca.VariaveisPublicas.lstListaInteiros;
import static biblioteca.VariaveisPublicas.nomeCliente;
import static biblioteca.VariaveisPublicas.patriDeptos;
import static biblioteca.VariaveisPublicas.valorItem;
import static biblioteca.VariaveisPublicas.patriDevolucao;
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
import java.awt.LayoutManager;
import javax.swing.JFrame;
import modelo.PatriTensTransferido;
import modelo.PatriTransferido;


public class F_MEMOITENSRECEBIDOS extends javax.swing.JFrame {
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
    
    
    String sqlPatriCGGM    = "SELECT i.*, m.* FROM TBLITENSMEMOTRANSFERIDOS i, TBLMODELOS m WHERE i.modeloid=m.codigo AND i.status <> 'TRANSFERIDO' AND i.status <> 'BAIXADO' ORDER BY i.item";        
    String sqlVazia        = "SELECT codigo FROM TBLITENSMEMOTRANSFERIDOS WHERE codigo < 1";  
    String observacao, numemoinicial;
    int icodigo, codExc, codItem, Item, TotalItens, codigoPatri = 0;
    boolean mostrouForm, selecionouUsuario;
    
    public F_MEMOITENSRECEBIDOS() {
        initComponents();
        Leitura();        
        setResizable(false);              //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        this.setTitle("Gerar e imprimir memorando de devolução de equipamentos");
        
        // Colocando enter para pular de campo 
        HashSet conj = new HashSet(this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS)); 
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0)); 
        this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);      
                       
        LayoutManager layout = this.getLayout();
        System.out.println("Layout do JFrame: " + layout.getClass().getName());
        
        txtORIGEMLOCAL.setFont(new Font("TimesRoman",Font.BOLD,16)); 
        txtORIGEMLOCAL.setForeground(Color.red);        
        txtORIGEMLOCAL.setDocument(new CampoTxtLimitadoPorQdeCaracteresUpperCase(30)); 
        
        txtORIGEMEXTERNA.setFont(new Font("TimesRoman",Font.BOLD,16)); 
        txtORIGEMEXTERNA.setForeground(Color.red);        
        txtORIGEMEXTERNA.setDocument(new CampoTxtLimitadoPorQdeCaracteresUpperCase(30)); 
        
        txtOBSERVACAO.setFont(new Font("TimesRoman",Font.BOLD,16)); 
        txtOBSERVACAO.setForeground(Color.red);
        txtOBSERVACAO.setDocument(new CampoTxtLimitadoPorQdeCaracteres(80));         
                
        //configuração dos botões
        umabiblio.configurarBotoes(btnAdicionar);
        umabiblio.configurarBotoes(btnCancelar);
        umabiblio.configurarBotoes(btnExcluirItem);
        umabiblio.configurarBotoes(btnImprimir);
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
        txtOBSERVACAO = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnExcluirItem = new javax.swing.JButton();
        txtORIGEMLOCAL = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btnLISTARCLIENTES = new javax.swing.JButton();
        txtORIGEMEXTERNA = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

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
        txtNUMEMO.setForeground(new java.awt.Color(255, 51, 51));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 255));
        jLabel4.setText("MEMO");

        txtOBSERVACAO.setForeground(new java.awt.Color(51, 51, 255));
        txtOBSERVACAO.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtOBSERVACAOFocusGained(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 255));
        jLabel6.setText("OBSERVAÇÃO");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sair.gif"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnExcluirItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sairPrograma.gif"))); // NOI18N
        btnExcluirItem.setText("Excluir Ítem");
        btnExcluirItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirItem.setEnabled(false);
        btnExcluirItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirItemActionPerformed(evt);
            }
        });

        txtORIGEMLOCAL.setForeground(new java.awt.Color(51, 51, 255));
        txtORIGEMLOCAL.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtORIGEMLOCALFocusGained(evt);
            }
        });
        txtORIGEMLOCAL.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtORIGEMLOCALMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 255));
        jLabel8.setText("ORIGEM EXTERNA");

        btnLISTARCLIENTES.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        btnLISTARCLIENTES.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/users16.jpg"))); // NOI18N
        btnLISTARCLIENTES.setText("SERVIDORES");
        btnLISTARCLIENTES.setToolTipText("Listar clientes ativos");
        btnLISTARCLIENTES.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLISTARCLIENTES.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLISTARCLIENTESMouseClicked(evt);
            }
        });
        btnLISTARCLIENTES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLISTARCLIENTESActionPerformed(evt);
            }
        });

        txtORIGEMEXTERNA.setForeground(new java.awt.Color(51, 51, 255));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 255));
        jLabel9.setText("ORIGEM LOCAL");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnExcluirItem, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtOBSERVACAO)
                            .addComponent(jScrollPane2)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNUMEMO, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtORIGEMEXTERNA, javax.swing.GroupLayout.PREFERRED_SIZE, 940, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtORIGEMLOCAL)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLISTARCLIENTES, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAdicionar, btnCancelar, btnExcluirItem, btnImprimir});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNUMEMO, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtORIGEMEXTERNA, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtORIGEMLOCAL, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLISTARCLIENTES, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOBSERVACAO, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnExcluirItem, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1029, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1045, 828));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
            
    private void Leitura() 
    {                         
        umMetodo.excluirMemorandoSemItens();        
        umCtrlPatrItemTranferido.excluirItensSProcessando();

        umabiblio.limparTodosCampos(rootPane);  //LIMPA TODOS OS EDITS 
        btnCancelar.setEnabled(false);
        btnAdicionar.setEnabled(false);
        btnImprimir.setEnabled(false);
        btnExcluirItem.setEnabled(false);
        btnLISTARCLIENTES.setEnabled(true);
        txtORIGEMLOCAL.setEditable(false);
        txtORIGEMEXTERNA.setEditable(true);
        txtORIGEMEXTERNA.requestFocus();
        txtOBSERVACAO.setEditable(false);        
        btnSair.setEnabled(true);               
        numMemoTransferido = "";
        mostrouForm = false;        
        valorItem = 0;
        controlenaveg = 0;
        lstListaInteiros.clear();
        selecionouUsuario = false;

        //JOptionPane.showMessageDialog(null, "Próximo numemo = "+String.valueOf(umMetodo.gerarProximoNumeroMemoTransferir()));
        if(!umabiblio.tabelaVazia("TBLMEMOSTRANSFERIDOS")){
            txtNUMEMO.setText(String.valueOf(umMetodo.gerarProximoNumeroMemoTransferir()));               
        }else{
            txtNUMEMO.setText("1");
        } 
        PreencherTabela(sqlVazia);                
        
    }
        
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        //Exclui os itens se nao gerou relatorio
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed
        
    private void gravarMemorando(){
        //GRAVA O CABECALHO DO MEMORANDO NA TABELA
        
        //GERANDO NUMERO DO MEMO COM O ANO VIGENTE
        numMemoTransferido     = txtNUMEMO.getText()+"/"+anoVigente;
        //origemTransferidos     = txtORIGEMLOCAL.getText();
        destinoTransferidos    = "CGGM/INFO";
        assuntoTransferido     = "DEVOLUCAO DE EQUIPAMENTOS";
        observacao             = txtOBSERVACAO.getText();

        /*salvando memorando em definitivo ( TBLMEMOSTRANSFERIDOS ) apos gerar o relatorio  
          Nao deixar salvar quando clicado mais de uma vez / So gravar a primeira vez que clicar*/            
        if(!umMetodo.temDuplicidadeDeCadastro("TBLMEMOSTRANSFERIDOS", "numemo", numMemoTransferido))
        {
            umModPatriTransferido.setNumemo(numMemoTransferido);

            if(!observacao.equals(null) && !observacao.equals(""))
            {
                umModPatriTransferido.setObservacao("Obs : "+umMetodo.primeiraLetraMaiuscula(observacao));
            }else{
                umModPatriTransferido.setObservacao(" ");
            }

            umModPatriTransferido.setStatus("TRANSFERIDO");
            umModPatriTransferido.setIdusuario(codigoUsuario);
            umModPatriTransferido.setAssunto(assuntoTransferido);
            umCtrlPatriTranferido.salvarPatriTransferido(umModPatriTransferido); 
            umGravarLog.gravarLog("cadastro do memo de devolucao de patrimonios "+numMemoTransferido);
        }        
        
    }
    
    private boolean comOrigemExterna(){
        if(!txtORIGEMEXTERNA.getText().equals("")){   
             return true;
        }else{
            return false;
        }             
    }
   
    private void AdicionarPatrimoniosAoMemorando(){                  
        
        /*QDO CLICA NO BOTAO ADICIONAR ABRE-SE A LISTA DE PATRIMONIOS E AO ESCOLHER UM ITEM O MESMO É INCLUIDO NA TBLITENSMEMOTRANSFERIDOS COM STATUS 
          PROCESSANDO MAS SÓ SERÁ GRAVADO SE GERAR O RELATORIO ATRAVES DO BOTAO IMPRIMIR, SE SAIR SEM GERAR O RELATORIO O MEMO E ÍTENS SERÃO EXCLUIDOS*/                             
        numMemoTransferido     = txtNUMEMO.getText()+"/"+anoVigente;
        //origemTransferidos     = txtORIGEMLOCAL.getText();
        assuntoTransferido     = "DEVOLUCAO DE EQUIPAMENTOS";
        destinoTransferidos    = "CGGM/INFO";
        patriDevolucao         = true;

        //ABRE ALISTA DE PATRIMONIOS INATIVADOS COM SEUS DEVIDOS MODELOS PARA SELEÇÃO DO PATRIMONIO DESEJADO
        F_LISTAPATRIMONIOS frmPatrimonios = new F_LISTAPATRIMONIOS(this, true);
        frmPatrimonios.setVisible(true);           
        
        PreencherTabela(sqlPatriCGGM); 
        
        btnImprimir.setEnabled(true);            
        btnSair.setEnabled(false);   
        btnExcluirItem.setEnabled(false);
        btnCancelar.setEnabled(true);            
        controlenaveg   = 0;
        patriDevolucao  = false;   
        btnLISTARCLIENTES.setEnabled(false);
        txtORIGEMLOCAL.setEditable(false);
    }
    
    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        if(comOrigemExterna()){           
            origemTransferidos = txtORIGEMEXTERNA.getText();
        }else{
            origemTransferidos = txtORIGEMLOCAL.getText();
        }              
        AdicionarPatrimoniosAoMemorando();           
        
    }//GEN-LAST:event_btnAdicionarActionPerformed
        
    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        //AO CLICAR EM UM REGISTRO DA TABELA MOSTRAR OS DADOS NOS EDITS
        codItem = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0); 
        Item    = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 1);
        //JOptionPane.showMessageDialog(null, "CODIGO DO ÍTEM SELECIONADO...: "+codItem); 
        
        btnImprimir      .setEnabled(false);
        btnAdicionar     .setEnabled(false);
        btnSair          .setEnabled(true);
        btnCancelar      .setEnabled(false);
        btnExcluirItem   .setEnabled(true);        
        txtNUMEMO        .setEditable(false);

    }//GEN-LAST:event_jTabelaMouseClicked
            
    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        /*IMPRIMINDO RELATORIO DOS PATRIMONIOS TRANSFERIDOS VERIFICANDO SE O ARQUIVO EXISTE RETORNA TRUE/FALSE
        System.out.println(new File("relatorio/relmemotransferidos.jasper").exists()); */
       //devolvendo o foco ao txtDESTINO logo apos a emissao do relatorio caso queira fazer outro memorando
     
        gravarMemorando();
        
        //atualizando tabela de ÍTENS ( TBLITENSMEMOTRANSFERIDOS ) do memorando de PROCESSANDO para TRANSFERIDO
        umMetodo.atualizarStatusParaTransferidos(numMemoTransferido);
                
        GerarRelatorios objRel = new GerarRelatorios();
        try {
            objRel.imprimirPatrimoniosTransferidos("relatorio/relmemodevolucaocomchapa.jasper", numMemoTransferido);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);                
        }    

        /*=============================================================================================================================================
                                                    REATIVAR PATRIMONIOS INSERIDOS NO MEMORANDO
        ===============================================================================================================================================*/
                    
        umPatrimonioDAO.ReativarPatrimonioPeloMemorandoDAO(numMemoTransferido);  
            
        Leitura();       
        
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        if(umabiblio.ConfirmouOperacao("Tem certeza que deseja sair e cancelar a operação,os dados não salvos serão perdidos?", "Saindo do Memorando "+numMemoTransferido)){
            umGravarLog.gravarLog("cancelando criacao do memorando "+numMemoTransferido);
            umMetodo.deletarMemorandoSemItens(numMemoTransferido);
            umMetodo.deletarItensDoMemorando(numMemoTransferido);        
            Leitura();                 
            dispose();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed
    
    
    private void btnExcluirItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirItemActionPerformed
        //EXCLUINDO ITEM DO MEMO ATUAL
        ArrayList<Integer> lstListaItens = new ArrayList<>();
        
        String message = "Confirma a exclusão do ítem com código "+codItem+" do memorando em curso?";
        String title   = "Confirmação de Exclusão";
        //Exibe caixa de dialogo (veja figura) solicitando confirmação ou não. 
        //Se o usuário clicar em "Sim" retorna 0 pra variavel reply, se informado não retorna 1
        int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) 
        {            
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
                    umCtrlPatrItemTranferido.atualizarValorDosItensAposExclusao(Item, numMemoTransferido);
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
        
    }//GEN-LAST:event_btnExcluirItemActionPerformed

    private void txtORIGEMLOCALMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtORIGEMLOCALMouseClicked
        txtOBSERVACAO.requestFocus();
    }//GEN-LAST:event_txtORIGEMLOCALMouseClicked

    private void btnLISTARCLIENTESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLISTARCLIENTESActionPerformed
             
      F_LISTACLIENTESATIVOS frm = new F_LISTACLIENTESATIVOS(null, true, "F");
      frm.setVisible(true);
             
       String texto = nomeCliente;
       if(texto.length() > 30){
            texto = nomeCliente.substring(0, 30);              
       }else{
            texto = nomeCliente;  
       }
       txtORIGEMLOCAL.setEditable(true);
       txtORIGEMLOCAL.setText(texto);
       txtOBSERVACAO.requestFocus();
       txtOBSERVACAO.setEditable(true);        
       selecionouUsuario = true;    
       btnCancelar.setEnabled(true);
       btnAdicionar.setEnabled(true);
       
    }//GEN-LAST:event_btnLISTARCLIENTESActionPerformed
        
    private void btnLISTARCLIENTESMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLISTARCLIENTESMouseClicked
         txtOBSERVACAO.requestFocus();          
    }//GEN-LAST:event_btnLISTARCLIENTESMouseClicked

    private void txtORIGEMLOCALFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtORIGEMLOCALFocusGained
        //Se preencheu a origem através de digitação
        if(!txtORIGEMEXTERNA.getText().equals("")){  
            txtOBSERVACAO.setEditable(true);
            txtOBSERVACAO.requestFocus();
            btnLISTARCLIENTES.setEnabled(false);
            btnCancelar.setEnabled(true);
            btnAdicionar.setEnabled(true);
        }
    }//GEN-LAST:event_txtORIGEMLOCALFocusGained

    private void txtOBSERVACAOFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtOBSERVACAOFocusGained
        if(!comOrigemExterna()){
            txtORIGEMEXTERNA.setEditable(false);
        }
    }//GEN-LAST:event_txtOBSERVACAOFocusGained
    
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
                };
            }
            
            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabela.setModel(modelo);
            //define tamanho das colunas   
            jTabela.getColumnModel().getColumn(0).setPreferredWidth(50);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna      
            jTabela.getColumnModel().getColumn(1).setPreferredWidth(50);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(1).setResizable(false);    //nao será possivel redimencionar a coluna      
            jTabela.getColumnModel().getColumn(2).setPreferredWidth(600);  //define o tamanho da coluna
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
    private javax.swing.JButton btnLISTARCLIENTES;
    private javax.swing.JButton btnSair;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabela;
    private javax.swing.JTextField txtNUMEMO;
    private javax.swing.JTextField txtOBSERVACAO;
    private javax.swing.JTextField txtORIGEMEXTERNA;
    private javax.swing.JTextField txtORIGEMLOCAL;
    // End of variables declaration//GEN-END:variables
}
