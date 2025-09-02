package visao;

import Dao.DAOPatrimonio;
import conexao.ConnConexao;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import biblioteca.Biblioteca;
import biblioteca.MetodosPublicos;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.JOptionPane;
import biblioteca.RetornarQdeLinhasDoTxt;
import biblioteca.SelecionarArquivoTexto;
import static biblioteca.VariaveisPublicas.caminhoArqTXT;
import static biblioteca.VariaveisPublicas.salvandoLote;
import static biblioteca.VariaveisPublicas.dataDoDia;
import static biblioteca.VariaveisPublicas.reativando;
import static biblioteca.VariaveisPublicas.tipoCadastroEntrada;
import controle.ControleGravarLog;
import controle.CtrlPatrimonio;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.SwingWorker;
import modelo.Patrimonio;


public class F_CADPATRIMONIOSVIATXT extends javax.swing.JDialog  {

    ConnConexao         conexao                  = new ConnConexao();
    Biblioteca          umaBiblio                = new Biblioteca();
    MetodosPublicos     umMetodo                 = new MetodosPublicos();  
    Patrimonio          umModPatrimonio          = new Patrimonio();
    CtrlPatrimonio      umControlePatrimonio     = new CtrlPatrimonio();
    ControleGravarLog   umGravarLog              = new ControleGravarLog();
    DAOPatrimonio       umPatrimonioDAO          = new DAOPatrimonio();
    DateFormat          sdf                      = new SimpleDateFormat("dd/MM/yyyy");
    Date dataDia                                 = dataDoDia; 
        
    String sChapa, sSerie, sTipoid, sSecaoid, sClienteid, sModeloid, sDeptoid, sNomeEquipamento, sContrato, sObs, caminhoTXT, linha, observacaoCadLote, novaObservacao = "";  
    boolean reativandoTXT;
    int contador,cont,iCodigo,totalLinhas =0;    

    public F_CADPATRIMONIOSVIATXT() 
    {
        initComponents();
        setResizable(false);   //desabilitando o redimencionamento da tela     
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar  
        Leitura();
        umaBiblio.configurarBotoes(btnLerTXT);
        umaBiblio.configurarBotoes(btnLimpar); 
        umaBiblio.configurarBotoes(btnSair);           
        
        txtDESCRICAO.setFont(new Font("TimesRoman", Font.BOLD, 12));
        
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

        panelPrincipal = new javax.swing.JPanel();
        btnSair = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDESCRICAO = new javax.swing.JTextArea();
        btnLimpar = new javax.swing.JButton();
        btnGerarObsAdicional = new javax.swing.JButton();
        btnGerarArquivoTXT = new javax.swing.JButton();
        btnLerTXT = new javax.swing.JButton();
        btnReativar = new javax.swing.JButton();

        setTitle("Cadastro de Patrimônios em Lote através da leitura de um arquivo TXT");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelPrincipal.setPreferredSize(new java.awt.Dimension(1024, 733));
        panelPrincipal.setLayout(null);

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
        btnSair.setBounds(910, 590, 110, 45);

        txtDESCRICAO.setColumns(20);
        txtDESCRICAO.setRows(5);
        jScrollPane1.setViewportView(txtDESCRICAO);

        panelPrincipal.add(jScrollPane1);
        jScrollPane1.setBounds(10, 10, 1010, 560);

        btnLimpar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_limpar.gif"))); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpar.setEnabled(false);
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnLimpar);
        btnLimpar.setBounds(230, 590, 170, 45);

        btnGerarObsAdicional.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnGerarObsAdicional.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/calculator_add.png"))); // NOI18N
        btnGerarObsAdicional.setText("Add Observação");
        btnGerarObsAdicional.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGerarObsAdicional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarObsAdicionalActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnGerarObsAdicional);
        btnGerarObsAdicional.setBounds(400, 590, 170, 45);

        btnGerarArquivoTXT.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnGerarArquivoTXT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TICK.PNG"))); // NOI18N
        btnGerarArquivoTXT.setText("Gerar TXT dos Equipamentos");
        btnGerarArquivoTXT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGerarArquivoTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarArquivoTXTActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnGerarArquivoTXT);
        btnGerarArquivoTXT.setBounds(10, 590, 220, 45);

        btnLerTXT.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLerTXT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_blocoNotas.gif"))); // NOI18N
        btnLerTXT.setText("Ler TXT e Cadastrar");
        btnLerTXT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLerTXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLerTXTActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnLerTXT);
        btnLerTXT.setBounds(740, 590, 170, 45);

        btnReativar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnReativar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_reverter.gif"))); // NOI18N
        btnReativar.setText("Ler TXT e Reativar");
        btnReativar.setToolTipText("Utilize o mesmo TXT de entrada se existir");
        btnReativar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReativar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReativarActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnReativar);
        btnReativar.setBounds(570, 590, 170, 45);

        getContentPane().add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1030, 650));

        setSize(new java.awt.Dimension(1045, 693));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
      
    private void Leitura(){
        txtDESCRICAO.setEditable(false);
        cont=0;
    }
    
   private void LerTXT() 
   {
        SelecionarArquivoTexto select = new SelecionarArquivoTexto();
        caminhoArqTXT = select.ImportarTXT();

        totalLinhas = 0;
        RetornarQdeLinhasDoTxt qdeLinhas = new RetornarQdeLinhasDoTxt();

        if (caminhoArqTXT == null) {
            JOptionPane.showMessageDialog(null, "Nenhum arquivo selecionado.");
            btnGerarArquivoTXT.setEnabled(true);
            btnReativar.setEnabled(true);
            return;
        }

        F_BARRAPROGRESSOLENDOTXT frm = new F_BARRAPROGRESSOLENDOTXT(null);
        frm.setVisible(true);

        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {

            @Override
            protected Void doInBackground() throws Exception 
            {
                totalLinhas = qdeLinhas.retornaNumLinhasDoTxt(caminhoArqTXT);
                File arquivo    = new File(caminhoArqTXT);
                BufferedReader lerBuf = new BufferedReader(new FileReader(arquivo));

                linha = lerBuf.readLine();
                int linhaAtual = 0;

                while (linha != null) {
                    try {
                        // Processar dados da linha
                        String[] dados = linha.split(";");
                        sChapa           = dados[0];
                        sSerie           = dados[1];
                        sTipoid          = dados[2];
                        sSecaoid         = dados[3];
                        sClienteid       = dados[4];
                        sModeloid        = dados[5];
                        sDeptoid         = dados[6];
                        sNomeEquipamento = dados[7];
                        sContrato        = dados[8];

                        if (!reativandoTXT) {
                            gravarDados();
                        } else {
                            gravarReativacao();
                        }

                        // Atualiza JTextArea (opcional)
                        String sObs = txtDESCRICAO.getText();
                        txtDESCRICAO.setText(sObs +
                            "CHAPA.........: " + sChapa + "\n" +
                            "SERIE.........: " + sSerie + "\n" +
                            "TIPOID........: " + sTipoid + "\n" +
                            "SECAOID.......: " + sSecaoid + "\n" +
                            "CLIENTEID.....: " + sClienteid + "\n" +
                            "MODELOID......: " + sModeloid + "\n" +
                            "DEPTOID.......: " + sDeptoid + "\n" +
                            "EQUIPAMENTO...: " + sNomeEquipamento + "\n" +
                            "CONTRATO......: " + sContrato + "\n" +
                            "=================================================================================================================\n");

                    } catch (Exception ex) {
                        System.err.println("Erro ao processar linha: " + linha);
                        ex.printStackTrace();
                    }

                    linhaAtual++;
                    publish(linhaAtual);
                    linha = lerBuf.readLine();
                }

                lerBuf.close();
                return null;
            }

            @Override
            //Escolha por aqui entre duas opções o tipo de informação que sua barra de progresso deverá ter:
            
            protected void process(java.util.List<Integer> chunks) {
                int ultimaLinha = chunks.get(chunks.size() - 1);
                frm.atualizarProgressoPelaQdeRegs(ultimaLinha, totalLinhas);                
            }
            
//            protected void process(List<Integer> chunks) {
//                int atual = chunks.get(chunks.size() - 1);
//                frm.atualizarProgressoPorPorcentagem(atual);
//            }

            @Override
            protected void done() {
                frm.label.setText("Importação concluída! Fechando em 3s...");

                // Fechar a janela depois de 10 segundos
                new javax.swing.Timer(3000, e -> frm.dispose()).start();

                if (!reativandoTXT) {
                    if (contador > 0) {
                        JOptionPane.showMessageDialog(null, "Todos os "+totalLinhas+" patrimônios válidos foram cadastrados com sucesso!", "Cadastrado com Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                        btnLimpar.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro no cadastro de alguns registros...", "ERRO no cadastro!", JOptionPane.WARNING_MESSAGE);
                        btnLimpar.setEnabled(true);
                        btnLerTXT.setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Todos os "+totalLinhas+" patrimônios foram reativados com sucesso!", "Reativados com Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                    btnReativar.setEnabled(false);
                    btnGerarArquivoTXT.setEnabled(true);
                    txtDESCRICAO.setText("");
                }
            }
        };
        worker.execute();
}

           
    private void gravarDados()
    {
        //setando os valores no objeto do modelo
        salvandoLote = true;
        umModPatrimonio.setChapa(sChapa);
        umModPatrimonio.setSerie(sSerie);
        umModPatrimonio.setTipoid(Integer.parseInt(sTipoid));
        umModPatrimonio.setIp("0");
        umModPatrimonio.setSecaoid(Integer.parseInt(sSecaoid));
        umModPatrimonio.setClienteid(Integer.parseInt(sClienteid));
        umModPatrimonio.setModeloid(Integer.parseInt(sModeloid));
        umModPatrimonio.setDeptoid(Integer.parseInt(sDeptoid));
        umModPatrimonio.setEstacao(sNomeEquipamento);
        umModPatrimonio.setContrato(sContrato);
        umModPatrimonio.setMotivo(sdf.format(dataDia)+" : Cadastro inicial"); 
        
        if(cont >= 1){
            umModPatrimonio.setObservacoes(novaObservacao);
        }else{
            umModPatrimonio.setObservacoes(sdf.format(dataDia)+" : Cadastro inicial");
        };
        
        iCodigo = umMetodo.getCodigoPassandoString("tblpatrimonios", "serie", sSerie);

        //gravando no banco de dados, antes verifica se o rf já esta cadastrado e não grava se isso acontecer
        if (umMetodo.temDuplicidadeDeCadastro("tblpatrimonios", "serie", sSerie)) {
            JOptionPane.showMessageDialog(null,"Erro : Patrimônio Serie "+sSerie+" já esta cadastrado e seu código é : "+iCodigo+"","Duplicidade : Série "+sSerie+"",2);            
            contador = 0;
        } else {
            if (umControlePatrimonio.salvarPatrimonio(umModPatrimonio)) {
                contador = 1;
                String sTipo = umMetodo.getStringPassandoCodigo("tbltipos", "tipo", Integer.parseInt(sTipoid));
                //PEGAR O NOME DO TIPO PELO ID E ACRESCENTAR AQUI
                umGravarLog.gravarLog("cadastro de um(a) "+sTipo+" serie "+umModPatrimonio.getSerie());
            }
        }
        btnLerTXT.setEnabled(false);   
        btnGerarObsAdicional.setEnabled(false);
    }
    private void gravarReativacao()
    {
        umModPatrimonio.setSerie(sSerie);
        iCodigo = umMetodo.getCodigoPassandoString("tblpatrimonios", "serie", sSerie);
        
        String motivoAtual = umMetodo.getStringPassandoCodigo("tblPatrimonios", "motivo", iCodigo);
        String obsAtual    = umMetodo.getStringPassandoCodigo("tblPatrimonios", "observacoes", iCodigo);
        String mensagem    = " : Equipamentos recebidos na CGGM/INFO e reativados atraves de TXT.";
        
        String novoMOtivo = motivoAtual+("\n"+sdf.format(dataDia)+mensagem);
        String novaObs    = obsAtual+("\n"+sdf.format(dataDia)+mensagem);
        
        umModPatrimonio.setMotivo(novoMOtivo);         
        umModPatrimonio.setObservacoes(novaObs);         
        umModPatrimonio.setStatus("ATIVO");                          
               
        if (umControlePatrimonio.reativarInativadoPelaSerie(umModPatrimonio)) {            

            String sTipo = umMetodo.getStringPassandoCodigo("tbltipos", "tipo", Integer.parseInt(sTipoid));
            //PEGAR O NOME DO TIPO PELO ID E ACRESCENTAR AQUI
            umGravarLog.gravarLog("reativacao de um(a) "+sTipo+" serie "+umModPatrimonio.getSerie());
        }
       
        btnLerTXT.setEnabled(false);   
        btnGerarObsAdicional.setEnabled(false);
        
    }
                      
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        dispose();
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnLerTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLerTXTActionPerformed
        //faz a leitura do arquivo TXT e cadastra os equipamentos registrados nele
        LerTXT();  
        btnGerarArquivoTXT.setEnabled(false);
        btnReativar.setEnabled(false);
        btnLimpar.setText("Voltar");
    }//GEN-LAST:event_btnLerTXTActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        txtDESCRICAO.setText("");
        btnLerTXT.setEnabled(true);
        btnGerarObsAdicional.setEnabled(true);
        btnGerarArquivoTXT.setEnabled(true);
        btnLimpar.setEnabled(false);
        btnLimpar.setText("Limpar");
        btnSair.setEnabled(true);
        salvandoLote = false;
        novaObservacao = sdf.format(dataDia)+" : Cadastro inicial";
    }//GEN-LAST:event_btnLimparActionPerformed

    private void btnGerarObsAdicionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarObsAdicionalActionPerformed
         //JOptionPane.showMessageDialog(this, "VALOR DO CONT : "+cont);
        if(cont <1)
        {
            novaObservacao = JOptionPane.showInputDialog(null, "Entre com sua observação adicional!", "Observação Adicional", 2); 
            while (novaObservacao == null || novaObservacao.equals("")) 
            {
                JOptionPane.showMessageDialog(this, "Digite um texto válido!");  
                novaObservacao = JOptionPane.showInputDialog(null, "Entre com sua observação adicional!", "Observação Adicional", 2); 
            }               
            novaObservacao = sdf.format(dataDia)+" : Cadastro inicial. \n"+sdf.format(dataDia)+" : "+umMetodo.primeiraLetraMaiuscula(novaObservacao);     
            JOptionPane.showMessageDialog(this, "Observação adicional inserida com sucesso para todos os registros!", "Observação Adicional", 2); 
            btnGerarObsAdicional.setEnabled(false);
            cont++;
        }else{           
            cont = 0;
        }
        btnGerarArquivoTXT.setEnabled(false);
        btnLimpar.setEnabled(true);
        btnLimpar.setText("Cancelar");
        
    }//GEN-LAST:event_btnGerarObsAdicionalActionPerformed

    private void btnGerarArquivoTXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarArquivoTXTActionPerformed
        
        //F_ESCOLHASTIPODECADASTRO -> ENTRADA
        tipoCadastroEntrada = true;
        F_ESCOLHASTIPODECADASTRO frm = new F_ESCOLHASTIPODECADASTRO(null, true);
        frm.setVisible(true);      
        
    }//GEN-LAST:event_btnGerarArquivoTXTActionPerformed

    private void btnReativarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReativarActionPerformed
        //faz a leitura do arquivo TXT e reativa os equipamentos registrados nele
        reativandoTXT = true;
        reativando = true;
        LerTXT();  
        btnGerarArquivoTXT.setEnabled(false);
        btnLimpar.setText("Voltar");
    }//GEN-LAST:event_btnReativarActionPerformed
          
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnGerarArquivoTXT;
    public javax.swing.JButton btnGerarObsAdicional;
    public javax.swing.JButton btnLerTXT;
    public javax.swing.JButton btnLimpar;
    public javax.swing.JButton btnReativar;
    public javax.swing.JButton btnSair;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTextArea txtDESCRICAO;
    // End of variables declaration//GEN-END:variables
}
