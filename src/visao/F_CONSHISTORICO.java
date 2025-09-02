package visao;

import biblioteca.Biblioteca;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.codigoRegSelecionado;
import static biblioteca.VariaveisPublicas.codParaHistorico;
import static biblioteca.VariaveisPublicas.serieEquipamento;
import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.tipoEquipamento;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class F_CONSHISTORICO extends javax.swing.JDialog {
    ConnConexao conexao     = new ConnConexao();
    Biblioteca  umaBiblio   = new Biblioteca();
    
    public F_CONSHISTORICO(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setResizable(false);   //desabilitando o redimencionamento da tela        
        this.setTitle("HISTÓRICO "+tipoEquipamento+" - SÉRIE : "+serieEquipamento);
        txtHISTORICO.setFont(new Font("TimesRoman", Font.BOLD, 12));
        txtHISTORICO.setForeground(Color.blue);

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

        jScrollPane4 = new javax.swing.JScrollPane();
        txtHISTORICO = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        txtHISTORICO.setEditable(false);
        txtHISTORICO.setColumns(20);
        txtHISTORICO.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        txtHISTORICO.setRows(5);
        txtHISTORICO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane4.setViewportView(txtHISTORICO);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(724, 549));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //txtHISTORICO.setText(null);
        mostrarHistoricoRegSelecionado(codParaHistorico);        
    }//GEN-LAST:event_formWindowOpened

    public void mostrarHistoricoRegSelecionado(int codPatrimonio)
    {
        sql = "SELECT observacoes FROM tblpatrimonios WHERE codigo='" + codPatrimonio + "' ";
        
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        try
        {
            if( conexao.rs.next())
            {
                txtHISTORICO.setText(conexao.rs.getString("observacoes"));
                txtHISTORICO.setCaretPosition(0); //setando a primeira linha do JTextArea
                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar selecionar os dados dos registro!\nERRO:" + ex.getMessage());
        } finally {
            conexao.desconectar();
        }
        txtHISTORICO.setCaretPosition(0); //setando a primeira linha do JTextArea
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
            java.util.logging.Logger.getLogger(F_CONSHISTORICO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_CONSHISTORICO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_CONSHISTORICO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_CONSHISTORICO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                F_CONSHISTORICO dialog = new F_CONSHISTORICO(new javax.swing.JFrame(), true);
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
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea txtHISTORICO;
    // End of variables declaration//GEN-END:variables
}
