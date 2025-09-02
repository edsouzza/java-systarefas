/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import Dao.DAOPatrimonio;
import biblioteca.Biblioteca;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import biblioteca.TudoMaiusculas;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.codigoPatrimonio;
import static biblioteca.VariaveisPublicas.numMemoTransferido;
import static biblioteca.VariaveisPublicas.origemTransferidos;
import static biblioteca.VariaveisPublicas.destinoTransferidos;
import static biblioteca.VariaveisPublicas.lstListaGenerica;
import static biblioteca.VariaveisPublicas.lstListaStrings;
import static biblioteca.VariaveisPublicas.valorItem;
import static biblioteca.VariaveisPublicas.nomeEstacaoTransferida;
import static biblioteca.VariaveisPublicas.editandoMemorando;
import static biblioteca.VariaveisPublicas.chapaRetornada;
import static biblioteca.VariaveisPublicas.serieRetornada;
import static biblioteca.VariaveisPublicas.codModeloRetornado;
import static biblioteca.VariaveisPublicas.patriDeptos;
import static biblioteca.VariaveisPublicas.patriDevolucao;
import controle.ControleGravarLog;
import controle.CtrlPatriDepto;
import controle.CtrlPatrimonio;
import controle.CtrlPatriItenstransferido;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import modelo.PatriDepto;
import modelo.Patrimonio;
import modelo.PatriTensTransferido;

public class F_LISTAPATRIMONIOS extends javax.swing.JDialog {

    ConnConexao                 conexao                     = new ConnConexao();
    ControleGravarLog           umGravarLog                 = new ControleGravarLog();
    Biblioteca                  umabiblio                   = new Biblioteca();
    Patrimonio                  objModPatrimonio            = new Patrimonio();
    CtrlPatrimonio              ctrlPatrimonio              = new CtrlPatrimonio();  
    CtrlPatriDepto              ctrlPatriDepto              = new CtrlPatriDepto();
    PatriTensTransferido        objModPatriTemTransferido   = new PatriTensTransferido();
    CtrlPatriItenstransferido   ctrlPatriTenstransferido    = new CtrlPatriItenstransferido();
    DAOPatrimonio               umPatrimonioTransferidoDAO  = new DAOPatrimonio();
    MetodosPublicos             umMetodo                    = new MetodosPublicos();
    PatriDepto                  umModPatrDepto              = new PatriDepto();
        
    int icodigo, codModelo, tipoid = 0;
    String serieClicada;
    String sqlPatriCGGM   = "SELECT p.*, t.*, m.* FROM tblpatrimonios p, tbltipos t, tblmodelos m WHERE p.tipoid = t.codigo AND p.modeloid = m.codigo AND p.status = 'ATIVO' ORDER BY m.modelo, p.codigo";    
    String sqlDevolucao   = "SELECT p.*, t.*, m.* FROM tblpatrimonios p, tbltipos t, tblmodelos m WHERE m.codigo=p.modeloid AND p.tipoid = t.codigo AND (p.status = 'INATIVO') ORDER BY t.tipo,p.codigo";
    
    String codigo, modelo, serie, chapa, numemo, origem, destino, status, tipo;
    String[] Colunas;
    
    public F_LISTAPATRIMONIOS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setResizable(false);   //desabilitando o redimencionamento da tela    
                
        
        //setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        jTabela.setForeground(Color.blue);
        txtPESQUISA.setDocument(new TudoMaiusculas());
        txtPESQUISA.setFont(new Font("TimesRoman", Font.BOLD, 12));
        txtPESQUISA.setForeground(Color.red);
        jTabela.setFont(new Font("Arial", Font.BOLD, 12));
        
        this.setTitle("Selecione um Patrimônio CGGM!");              

        mostrarPatrimonios();

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

        jBoxPesquisar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxPesquisar.setName("panelDados"); // NOI18N

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
            .addGap(0, 711, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 5, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jBoxPesquisar)
                        .addComponent(jScrollPane2))
                    .addGap(0, 11, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jBoxPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(6, 6, 6)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setSize(new java.awt.Dimension(727, 550));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
            
    private void mostrarPatrimonios() 
    {      
        //MOSTRA TODOS OS PATRIMONIOS CADASTRADOS 
        //JOptionPane.showMessageDialog(rootPane, patriDeptos);
        if(!patriDevolucao){
           PreencherTabela(sqlPatriCGGM);  
        }else{         
           PreencherTabela(sqlDevolucao); 
        }    
        
    }

    private void btnLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparPesquisaActionPerformed
        limparCampos();
    }//GEN-LAST:event_btnLimparPesquisaActionPerformed

    private void filtrarPorDigitacao(String pPesq) {
        //filtro pela serie, chapa ou modelo
        if(!patriDevolucao){
            
            String filtrarPatriCGGM     = "SELECT p.*, m.*, t.* FROM tblpatrimonios p, tblmodelos m, tbltipos t "
                                        + "WHERE p.tipoid = t.codigo AND p.modeloid = m.codigo AND (p.serie like '%" + pPesq + "%' OR p.chapa like '%" + pPesq + "%' OR m.modelo "
                                        + "like '%" + pPesq + "%') AND p.status = 'ATIVO'";   
        
            PreencherTabela(filtrarPatriCGGM);  
        }else{
                                    
            String filtrarPatriDEVOLUCAO     = "SELECT p.*, m.*, t.* FROM tblpatrimonios p, tblmodelos m, tbltipos t "
                                             + "WHERE p.tipoid = t.codigo AND p.modeloid = m.codigo AND (p.serie like '%" + pPesq + "%' OR p.chapa like '%" + pPesq + "%' OR m.modelo "
                                             + "like '%" + pPesq + "%') AND p.status = 'INATIVO'";  
                    
            PreencherTabela(filtrarPatriDEVOLUCAO);  
        }
      
    }

    public void limparCampos() {
        
        txtPESQUISA.setText("");
        txtPESQUISA.setEnabled(true);
        btnLimparPesquisa.setEnabled(false);
        txtPESQUISA.requestFocus();    
        if(!patriDevolucao){
           PreencherTabela(sqlPatriCGGM);  
        }else{           
           PreencherTabela(sqlDevolucao);    
        }
        mostrarPatrimonios();                        
                 
    }
    
    private void gravarRegistro() 
    {        
        //GRAVA O REGISTRO SELECIONADO NA TABELA TBLITENSMEMOTRANSFERIDOS PARA POSTERIOR IMPRESSAO       
        numemo          = numMemoTransferido;
        origem          = origemTransferidos;
        destino         = destinoTransferidos;
        status          = "PROCESSANDO";                  
                       
        //SETANDO OS VALORES NO MODELO PARA GRAVAR
        objModPatriTemTransferido.setItem(valorItem);
        objModPatriTemTransferido.setNumemo(numemo);
        objModPatriTemTransferido.setModeloid(codModelo);               
        objModPatriTemTransferido.setSerie(serieRetornada);
        objModPatriTemTransferido.setChapa(chapa);      
        objModPatriTemTransferido.setOrigem(origem);     
        objModPatriTemTransferido.setDestino(destino);     
        objModPatriTemTransferido.setStatus(status);
        
        ctrlPatriTenstransferido.salvarPatriItensTransferido(objModPatriTemTransferido);
        umGravarLog.gravarLog("Adição de itens ao memorando de transferencia "+numemo);
        
        codModelo = 0;
    }

    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        //AO CLICAR EM UM REGISTRO DA TABELA PEGAR O CODIGO DO REGISTRO CLICADO
        //JOptionPane.showMessageDialog(null, "SERIE DO PATRIMÔNIO SELECIONADO...: "+jTabela.getValueAt(jTabela.getSelectedRow(), 2));   
        //JOptionPane.showMessageDialog(null, "PATRIDEPTOS : "+patriDeptos);   
        valorItem++;
        
        serieClicada = (String) jTabela.getValueAt(jTabela.getSelectedRow(), 2);    
            
        //setando a serie do equipamento para pesquisa de NAO PATRIDEPTOS
        objModPatrimonio.setSerie(serieClicada);      

        //obtendo o patrimonio com todos os dados
        ctrlPatrimonio.pesquisarPatrimonio(objModPatrimonio); 

        //pegando o retorno da funcao/pesquisa e setando nas variaveis globais  
        codigoPatrimonio            = objModPatrimonio.getCodigo();     
        serieRetornada              = objModPatrimonio.getSerie();
        tipoid                      = objModPatrimonio.getTipoid();
        chapa                       = objModPatrimonio.getChapa();
        chapaRetornada              = objModPatrimonio.getChapa();
        nomeEstacaoTransferida      = objModPatrimonio.getEstacao();
        codModelo                   = objModPatrimonio.getModeloid();         
        codModeloRetornado          = objModPatrimonio.getModeloid();         

        //inserindo os dados para atualização dos registros em uma lista           
        lstListaGenerica.add(String.valueOf(codigoPatrimonio));
        lstListaStrings.add(String.valueOf(nomeEstacaoTransferida));      
            
//            System.out.println("A serie clicada foi : "+serieClicada);
//            System.out.println(codigoPatrimonio);
//            System.out.println(serieRetornada);
//            System.out.println(tipoid);
//            System.out.println(chapa);            
            
        //O metodo de gravação será startado depois de gerar o relatório se cancelar nada acontece       
        //JOptionPane.showMessageDialog(rootPane, "codigo :"+codigoPatrimonio+"\n"+"serie :"+serie+"\n"+"chapa :"+chapa+"\n" );       
               
        if(umabiblio.duplicidadeDeCadastroMemo("TBLITENSMEMOTRANSFERIDOS", "serie", serieRetornada)){
            //Verificando duplicidade na inserção de patrimônios
            JOptionPane.showMessageDialog(null, "Este patrimônio já esta inserido neste memorando!","Atenção duplicidade de patrimônio!",2);
            valorItem--;            
        }else{
            //gravar registro na tabela TBLITENSMEMOTRANSFERIDOS 
            if(!editandoMemorando){
                gravarRegistro();
            }            
        }  
        
        dispose();

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

    public void PreencherTabela(String sql) {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
         if(!patriDeptos){
            Colunas = new String[]{"Código", "Descrição", "Série", "Chapa"};
         }else{
            Colunas = new String[]{"Código", "Origem", "Descrição", "Série", "Chapa"}; 
         }
        try {
            conexao.ExecutarPesquisaSQL(sql);
            //JOptionPane.showMessageDialog(null, patriDeptos);
            if(!patriDeptos){
                
                while (conexao.rs.next()) {
                    dados.add(new Object[]{
                        conexao.rs.getString("codigo"),
                        conexao.rs.getString("modelo"),
                        conexao.rs.getString("serie"),
                        conexao.rs.getString("chapa")
                    });
                };
            }else{               
                //< PATRIDEPTOS >
                while (conexao.rs.next()) {
                    dados.add(new Object[]{
                        conexao.rs.getString("codigo"),
                        conexao.rs.getString("origem"),
                        conexao.rs.getString("modelo"),
                        conexao.rs.getString("serie"),
                        conexao.rs.getString("chapa")
                    });
                };
             }         
            
            if(!patriDeptos){                        
                ModeloTabela modelo = new ModeloTabela(dados, Colunas);
                jTabela.setModel(modelo);
                //define tamanho das colunas
                jTabela.getColumnModel().getColumn(0).setPreferredWidth(50);   //define o tamanho da coluna
                jTabela.getColumnModel().getColumn(0).setResizable(false);     //nao será possivel redimencionar a coluna        
                jTabela.getColumnModel().getColumn(1).setPreferredWidth(350);  //define o tamanho da coluna
                jTabela.getColumnModel().getColumn(1).setResizable(false);     //nao será possivel redimencionar a coluna        
                jTabela.getColumnModel().getColumn(2).setPreferredWidth(130);  //define o tamanho da coluna
                jTabela.getColumnModel().getColumn(2).setResizable(false);     //nao será possivel redimencionar a coluna    
                jTabela.getColumnModel().getColumn(3).setPreferredWidth(130);  //define o tamanho da coluna
                jTabela.getColumnModel().getColumn(3).setResizable(false);     //nao será possivel redimencionar a coluna        
                //define propriedades da tabela
                jTabela.getTableHeader().setReorderingAllowed(false);          //nao podera ser reorganizada
                jTabela.setAutoResizeMode(jTabela.AUTO_RESIZE_OFF);            //nao será possivel redimencionar a tabela
                jTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
                
            }else{
                
                ModeloTabela modelo = new ModeloTabela(dados, Colunas);
                jTabela.setModel(modelo);
                //define tamanho das colunas
                jTabela.getColumnModel().getColumn(0).setPreferredWidth(50);   //define o tamanho da coluna
                jTabela.getColumnModel().getColumn(0).setResizable(false);     //nao será possivel redimencionar a coluna        
                jTabela.getColumnModel().getColumn(1).setPreferredWidth(100);  //define o tamanho da coluna
                jTabela.getColumnModel().getColumn(1).setResizable(false);     //nao será possivel redimencionar a coluna        
                jTabela.getColumnModel().getColumn(2).setPreferredWidth(320);  //define o tamanho da coluna
                jTabela.getColumnModel().getColumn(2).setResizable(false);     //nao será possivel redimencionar a coluna    
                jTabela.getColumnModel().getColumn(3).setPreferredWidth(100);  //define o tamanho da coluna
                jTabela.getColumnModel().getColumn(3).setResizable(false);     //nao será possivel redimencionar a coluna        
                jTabela.getColumnModel().getColumn(4).setPreferredWidth(100);  //define o tamanho da coluna
                jTabela.getColumnModel().getColumn(4).setResizable(false);     //nao será possivel redimencionar a coluna      
                //define propriedades da tabela
                jTabela.getTableHeader().setReorderingAllowed(false);          //nao podera ser reorganizada
                jTabela.setAutoResizeMode(jTabela.AUTO_RESIZE_OFF);            //nao será possivel redimencionar a tabela
                jTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
                
                
            }

        } catch (SQLException ex) {
            //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
            JOptionPane.showMessageDialog(null, "Erro ao preencher a Tabela de Patrimônios!\nErro: " + ex.getMessage());
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
            java.util.logging.Logger.getLogger(F_LISTAPATRIMONIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_LISTAPATRIMONIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_LISTAPATRIMONIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_LISTAPATRIMONIOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                F_LISTAPATRIMONIOS dialog = new F_LISTAPATRIMONIOS(new javax.swing.JFrame(), true);
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
