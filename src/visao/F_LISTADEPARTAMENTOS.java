package visao;

import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.codigoDeptoSelecionado;
import static biblioteca.VariaveisPublicas.reativou;
import javax.swing.JOptionPane;
import modelo.Patrimonio;

public class F_LISTADEPARTAMENTOS extends javax.swing.JDialog {

    ConnConexao conexao        = new ConnConexao();
    Patrimonio  umPatrimonio   = new Patrimonio();
    
    
    public F_LISTADEPARTAMENTOS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();      
        this.setTitle("Selecione um departamento!");
        setResizable(false);   //desabilitando o redimencionamento da tela        
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar
        
    }
   
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbDeptos = new javax.swing.JComboBox();
        btnEscolhaDepto = new javax.swing.JButton();
        btnCancelarReativacao = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        cmbDeptos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cmbDeptos.setForeground(new java.awt.Color(51, 153, 255));
        cmbDeptos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PGM", "CEJUR" }));
        cmbDeptos.setSelectedIndex(-1);
        cmbDeptos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbDeptos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDeptosItemStateChanged(evt);
            }
        });

        btnEscolhaDepto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEscolhaDepto.setText("OK");
        btnEscolhaDepto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEscolhaDepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEscolhaDeptoActionPerformed(evt);
            }
        });

        btnCancelarReativacao.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnCancelarReativacao.setText("CANCELAR");
        btnCancelarReativacao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelarReativacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarReativacaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(cmbDeptos, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(btnEscolhaDepto, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelarReativacao, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(cmbDeptos, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEscolhaDepto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelarReativacao, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );

        setSize(new java.awt.Dimension(316, 182));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmbDeptosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDeptosItemStateChanged
          codigoDeptoSelecionado = cmbDeptos.getSelectedIndex();         
       
        if(codigoDeptoSelecionado == 0){
            codigoDeptoSelecionado = 1; //PGM
        }else if(codigoDeptoSelecionado == 1){
            codigoDeptoSelecionado = 2; //CEJUR
        }
    }//GEN-LAST:event_cmbDeptosItemStateChanged

    private void btnEscolhaDeptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEscolhaDeptoActionPerformed
      
        if(cmbDeptos.getSelectedIndex() == -1){
           JOptionPane.showMessageDialog(null, "Escolha um Departamento para continuar a reativação!", "Opção inválida", 2);
        }else{
            //JOptionPane.showMessageDialog(rootPane,"Código selecionado : "+codigoDeptoSelecionado);
            reativou=true;
            dispose();
        }                   
    }//GEN-LAST:event_btnEscolhaDeptoActionPerformed

    private void btnCancelarReativacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarReativacaoActionPerformed
        JOptionPane.showMessageDialog(null, "A reativação do registro não foi efetivada!", "Cancelada pelo usuário", 1);
        reativou=false;
        dispose();
    }//GEN-LAST:event_btnCancelarReativacaoActionPerformed

    
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
            java.util.logging.Logger.getLogger(F_LISTADEPARTAMENTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(F_LISTADEPARTAMENTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(F_LISTADEPARTAMENTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(F_LISTADEPARTAMENTOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                F_LISTADEPARTAMENTOS dialog = new F_LISTADEPARTAMENTOS(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelarReativacao;
    private javax.swing.JButton btnEscolhaDepto;
    private javax.swing.JComboBox cmbDeptos;
    // End of variables declaration//GEN-END:variables
  
}