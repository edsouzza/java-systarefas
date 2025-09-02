
package visao;

import biblioteca.MetodosPublicos;
import static biblioteca.VariaveisPublicas.indiceItemSelecionado;
import static biblioteca.VariaveisPublicas.destinoMemorando;
import static biblioteca.VariaveisPublicas.patriDeptos;
import static biblioteca.VariaveisPublicas.enviando;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;

/**  
 * LISTA DE RELATORIOS A SEREM IMPRESSOS
 */

public class F_LISTADEPTOSMEMORANDO extends javax.swing.JDialog {
    
    MetodosPublicos  umMetodo  = new MetodosPublicos();
    int qdeRegsEnviados, qdeRegsEncerrados, qdeRegsDevolver = 0;
  
    public F_LISTADEPTOSMEMORANDO(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        
        initComponents();     
        Leitura();
        if(patriDeptos){
            popularlistaDeptos();
            this.setTitle("Selecione um destino para o memorando");
        }else{
            popularlistaCGGM();
            this.setTitle("Selecione um tipo de relatório para impressão");
        }
               
        setResizable(false);   //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
    }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JListaRelatorios = new java.awt.List();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        JListaRelatorios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JListaRelatorios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JListaRelatoriosMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JListaRelatorios, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JListaRelatorios, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(546, 327));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Leitura(){
        enviando=false;
    }
    
    private void JListaRelatoriosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JListaRelatoriosMouseClicked
        indiceItemSelecionado = JListaRelatorios.getSelectedIndex(); 

        if(!patriDeptos){
            if(indiceItemSelecionado == 0){
                 patriDeptos = false;                
            }else if(indiceItemSelecionado == 1){            
                 patriDeptos = true;             
            }        
        }else{
            destinoMemorando = JListaRelatorios.getSelectedItem().toString();
        }                
        
        this.dispose();
    }//GEN-LAST:event_JListaRelatoriosMouseClicked

    private void popularlistaCGGM(){
       //Populando e configurando a Lista de Opções
       JListaRelatorios.setFont(new Font("TimesRoman",Font.BOLD,14));
       JListaRelatorios.setForeground(Color.blue);
       JListaRelatorios.add("MEMO   DE   TRANSFERÊNCIA  DE  PATRIMÔNIOS   DA  CGGM");
       JListaRelatorios.add("MEMO DE TRANSFERENCIA PATRIMONIOS DEPARTAMENTOS");
       JListaRelatorios.select(0); //selecionando o primeiro item da lista
    }
    
    private void popularlistaDeptos(){
       //Populando e configurando a Lista de Opções para patrideptos
       JListaRelatorios.setFont(new Font("TimesRoman",Font.BOLD,14));
       JListaRelatorios.setForeground(Color.blue);
       
       qdeRegsEnviados   = umMetodo.retornarQdeRegsPeloStatus("tblpatrideptos", "ENVIADO");
       qdeRegsDevolver   = umMetodo.retornarQdeRegsPeloStatus("tblpatrideptos", "DEVOLVER");
       qdeRegsEncerrados = umMetodo.retornarQdeRegsPeloStatus("tblpatrideptos", "ENCERRADO");
       
//       System.out.println("ENVIADOS "+qdeRegsEnviados);
//       System.out.println("DEVOLVER "+qdeRegsDevolver);
//       System.out.println("ENCERRADO "+qdeRegsEncerrados);
       
       if(qdeRegsEnviados > 0){
         umMetodo.PreencherListaDestino(JListaRelatorios, "DESTINO");
         enviando=true;
       }else if(qdeRegsDevolver > 0){
         umMetodo.PreencherListaDestino(JListaRelatorios, "ORIGEM");  
         enviando=false;
       }else if(qdeRegsEncerrados > 0){
         umMetodo.PreencherListaDestino(JListaRelatorios, "ORIGEM");  
         enviando=false;
       }
       
       JListaRelatorios.select(0); //selecionando o primeiro item da lista
    }
        
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
            java.util.logging.Logger.getLogger(F_LISTADEPTOSMEMORANDO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_LISTADEPTOSMEMORANDO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_LISTADEPTOSMEMORANDO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_LISTADEPTOSMEMORANDO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                F_LISTADEPTOSMEMORANDO dialog = new F_LISTADEPTOSMEMORANDO(new javax.swing.JFrame(), true);
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
    private java.awt.List JListaRelatorios;
    // End of variables declaration//GEN-END:variables
   
}