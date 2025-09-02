package visao;

import relatorios.GerarEXCEL;
import static biblioteca.VariaveisPublicas.tabela;
import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.sqlCamposSelecionados;
import java.util.ArrayList;
import biblioteca.MetodosPublicos;
import java.awt.*;
import javax.swing.JOptionPane;

public class F_SELECIONARCAMPOS extends javax.swing.JDialog {

    public F_SELECIONARCAMPOS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
    }

    MetodosPublicos umMetodo    = new MetodosPublicos();
    GerarEXCEL      objExcel    = new GerarEXCEL();
    Container       c           = getContentPane();
    String          campos_selecionados_sql = "";
    int             indice_campos_selecionados [];
    int             indiceSelecionado;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnExportar = new javax.swing.JButton();
        jListaCamposSelecionados = new java.awt.List();
        jListaTodosCampos = new java.awt.List();
        btnSelecionarVariosCampos = new javax.swing.JButton();
        btnRemoverTodosCampos = new javax.swing.JButton();
        btnRemoverCampoSelecionado = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Selecionar os campos do relatório Excel");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Excel.gif"))); // NOI18N
        btnExportar.setText("Exportar para Excel");
        btnExportar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportar.setEnabled(false);
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        jListaCamposSelecionados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jListaCamposSelecionados.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jListaCamposSelecionados.setMultipleMode(true);

        jListaTodosCampos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jListaTodosCampos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jListaTodosCampos.setMultipleMode(true);
        jListaTodosCampos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListaTodosCamposMouseClicked(evt);
            }
        });

        btnSelecionarVariosCampos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnSelecionarVariosCampos.setText(">");
        btnSelecionarVariosCampos.setToolTipText("Enviar todos os itens selecionados");
        btnSelecionarVariosCampos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSelecionarVariosCampos.setEnabled(false);
        btnSelecionarVariosCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelecionarVariosCamposActionPerformed(evt);
            }
        });

        btnRemoverTodosCampos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRemoverTodosCampos.setText("<<");
        btnRemoverTodosCampos.setToolTipText("Limpar seleção");
        btnRemoverTodosCampos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemoverTodosCampos.setEnabled(false);
        btnRemoverTodosCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverTodosCamposActionPerformed(evt);
            }
        });

        btnRemoverCampoSelecionado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRemoverCampoSelecionado.setText("<");
        btnRemoverCampoSelecionado.setToolTipText("Remover item selecionado");
        btnRemoverCampoSelecionado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemoverCampoSelecionado.setEnabled(false);
        btnRemoverCampoSelecionado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverCampoSelecionadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jListaTodosCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSelecionarVariosCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRemoverTodosCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRemoverCampoSelecionado, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jListaCamposSelecionados, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(236, 236, 236)
                        .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jListaTodosCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jListaCamposSelecionados, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(btnSelecionarVariosCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(btnRemoverTodosCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemoverCampoSelecionado, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        setSize(new java.awt.Dimension(654, 539));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void popularListaComTodosCampos() {
        //populando a lista1 da esquerda com todos os campos passados na sql
        sql = "SELECT * FROM " + tabela + " ORDER BY codigo";
        
//        JOptionPane.showMessageDialog(rootPane, "nome da tabela : "+ tabela);
//        JOptionPane.showMessageDialog(rootPane, sql);
        
        umMetodo.preencherListaComCampos(jListaTodosCampos, sql);
        
    }

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //abre o formulario populando a lista1 da esquerda com todos os campos da tabela
        popularListaComTodosCampos();
        
    }//GEN-LAST:event_formWindowOpened

    private String gerarSqlComItensSelecionados() {
        String sqlCampos = "SELECT " + campos_selecionados_sql + " FROM " + tabela + " ORDER BY CODIGO";
        
        //passando os valores da sqlCampos para o metodo getNomesColunasDaSql para gerar os dados posteriormente
        umMetodo.getNomesColunasDaSql(sqlCampos);
        
        //System.out.println(sqlCampos);
        return sqlCampos;
    }
    
    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        sqlCamposSelecionados = gerarSqlComItensSelecionados();
        //JOptionPane.showMessageDialog(null, gerarSqlComItensSelecionados());
            
        ArrayList<Object[]> dataList = objExcel.getListaDados(sqlCamposSelecionados);
        if (dataList != null && dataList.size() > 0) {
            
            for(Object m : dataList){
                System.out.println(m.toString());
            }            
            
            objExcel.doExportar(dataList);
            //JOptionPane.showMessageDialog(null,"O arquivo excel foi exportado com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Não há dados disponível na tabela para exportar,operação cancelada!", "Erro Fatal!", 2);
        }
    }//GEN-LAST:event_btnExportarActionPerformed

    private void jListaTodosCamposMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListaTodosCamposMouseClicked
       btnSelecionarVariosCampos.setEnabled(true);
       if(jListaCamposSelecionados.getItemCount() == 0){
           btnExportar.setEnabled(false);
       }
    }//GEN-LAST:event_jListaTodosCamposMouseClicked

    private void gerarSqlComCamposSelecionados(){
        campos_selecionados_sql = "";
        //crio um vetor pra receber todos os itens que foram selecionados na lista1
        String campos_selecionados [] = jListaTodosCampos.getSelectedItems();
        
        //só permite seguir se selecionar um item pelo menos
        if(campos_selecionados.length > 0)
        {
            for (int i = 0; i < campos_selecionados.length; i++)
            {
                //adicionando os selecionados na lista2
                jListaCamposSelecionados.add(campos_selecionados[i]);
                //gerando a sql para exportar os dados para o excel
                campos_selecionados_sql = campos_selecionados_sql+campos_selecionados[i]+", ";
            }
            //retirando a virgula do ultimo item da sql
            campos_selecionados_sql = campos_selecionados_sql.substring(0, campos_selecionados_sql.length()-2);       
        }        
    }    
        
    private void btnSelecionarVariosCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelecionarVariosCamposActionPerformed
        btnRemoverTodosCampos.setEnabled(true);
        btnRemoverCampoSelecionado.setEnabled(true);
        btnExportar.setEnabled(true);
        gerarSqlComCamposSelecionados();       
    }//GEN-LAST:event_btnSelecionarVariosCamposActionPerformed

    private void btnRemoverTodosCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverTodosCamposActionPerformed
        //remove todos os campos selecionados da lista não permitindo que o excel seja gerado sem sql       
        jListaCamposSelecionados.removeAll();
        btnExportar.setEnabled(false);       
    }//GEN-LAST:event_btnRemoverTodosCamposActionPerformed
    
    private void removerCampoDaLista(){
        indiceSelecionado = jListaCamposSelecionados.getSelectedIndex();  
        if(jListaCamposSelecionados.isIndexSelected(indiceSelecionado)){
            if(jListaCamposSelecionados.getItemCount() > 0){
                jListaTodosCampos.setMultipleMode(false);
                jListaCamposSelecionados.remove(jListaCamposSelecionados.getSelectedItem());
                btnExportar.setEnabled(true);
            }
        }
        
        //desabilitando o botão exportar se a lista de selecionados estiver vazia
        if(jListaCamposSelecionados.getItemCount()==0){
            btnExportar.setEnabled(false);
            btnRemoverTodosCampos.setEnabled(false);
            btnRemoverCampoSelecionado.setEnabled(false);                    
        }
        
        //gerar uma nova sql sem os campos removidos pois ate aqui todos os campos ainda estao na ( campos_selecionados_sql )
        campos_selecionados_sql = "";
        String campos_selecionados [] = jListaCamposSelecionados.getItems();
        
        //só permite seguir se selecionar um item pelo menos
        if(campos_selecionados.length > 0)
        {
            for (int i = 0; i < campos_selecionados.length; i++)
            {
                campos_selecionados_sql = campos_selecionados_sql+campos_selecionados[i]+", ";
            }
            campos_selecionados_sql = campos_selecionados_sql.substring(0, campos_selecionados_sql.length()-2);       
        }       
        
    }
    
    private void btnRemoverCampoSelecionadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverCampoSelecionadoActionPerformed
        removerCampoDaLista();        
    }//GEN-LAST:event_btnRemoverCampoSelecionadoActionPerformed

       
    /**
     * @param args the command line arguments
     */
   public static void main(String args[]) {
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                F_SELECIONARCAMPOS dialog = new F_SELECIONARCAMPOS(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnRemoverCampoSelecionado;
    private javax.swing.JButton btnRemoverTodosCampos;
    private javax.swing.JButton btnSelecionarVariosCampos;
    private java.awt.List jListaCamposSelecionados;
    private java.awt.List jListaTodosCampos;
    // End of variables declaration//GEN-END:variables

}