package visao;

import Dao.DAONomeEstacao;
import conexao.ConnConexao;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import biblioteca.Biblioteca;
import biblioteca.MetodosPublicos;
import static biblioteca.VariaveisPublicas.nomeDepartamento;
import controle.CtrlNomeEstacao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.NomeEstacao;


public class F_EXCLUSAONOMESPORINTERVALO extends javax.swing.JDialog  {

    ConnConexao        conexao                  = new ConnConexao();
    Biblioteca         umaBiblio                = new Biblioteca();
    MetodosPublicos    umMetodo                 = new MetodosPublicos();  
    NomeEstacao        umModeloNomeEstacao      = new NomeEstacao();
    CtrlNomeEstacao    umControleNomeEstacao    = new CtrlNomeEstacao();
    DAONomeEstacao     umEstacaoNomeEstacaoAO   = new DAONomeEstacao();
    
    String sqlEstacoesPorDepto, sqlEstacoesDisponiveis, nomeDepto;
    String sqlVazia   = "SELECT * FROM TBLNOMESTACAO WHERE CODIGO=0";
    boolean selecionouItem;
    int numInicial, numFinal, codigoNomeEstacao;
            

    public F_EXCLUSAONOMESPORINTERVALO() 
    {
        initComponents();
        setResizable(false);   //desabilitando o redimencionamento da tela     
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar     
        this.setTitle("Exclusão de nomes de rede disponíveis por intervalo");
        Leitura();      
               
                             
        //Impede que formulario seja arrastado na tela
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                setEnabled(false);
                setEnabled(true);
            }
        });//fim addComponentListener     
        
        // Controla o item selecionado no jcombobox
        cmbDeptos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtém o item selecionado
                
                nomeDepartamento = (String) cmbDeptos.getSelectedItem();  
                if(cmbDeptos.getSelectedIndex() >= 0){       
                    preencherNomeDepto();
                }                
                //btnExcluirPorIntervalo.setEnabled(true);
                txtNumInicial.setEnabled(true);       
                txtNumInicial.selectAll();
                txtNumInicial.requestFocus();
                txtNumFinal.setEnabled(true);  
                selecionouItem = true;
            }
        });        
    }     
    
    private void LimparCampos(){        
        btnExcluirPorIntervalo.setEnabled(false);
        btnCancelar.setEnabled(false);
        txtNumInicial.setText("0");
        txtNumFinal.setText("0");       
        txtNumInicial.setEnabled(false);       
        txtNumFinal.setEnabled(false);    
        cmbDeptos.setSelectedIndex(-1);
        cmbDeptos.requestFocus();
        selecionouItem = false;
        lblSiglaInicial.setText("DEPTO");
        lblSiglaFinal.setText("DEPTO");        
    }
      
    private void preencherNomeDepto()
    {        
      switch (nomeDepartamento) {
          case "CGGM":  
              lblSiglaInicial.setText("PGMCGGMC");
              lblSiglaFinal.setText("PGMCGGMC");
              break;
          case "BIBLIOTECA":
              lblSiglaInicial.setText("PGMCEJURC");
              lblSiglaFinal.setText("PGMCEJURC");
              break;
          case "CEJUR":
              lblSiglaInicial.setText("PGMCEJURC");
              lblSiglaFinal.setText("PGMCEJURC");
              break;
          case "CEJUSC":
              lblSiglaInicial.setText("PGMCEJUSCC");
              lblSiglaFinal.setText("PGMCEJUSCC");
              break;
          case "PFM":
              lblSiglaInicial.setText("PGMPFMC");
              lblSiglaFinal.setText("PGMPFMC");
              break;      
          default: 
              JOptionPane.showMessageDialog(null, "Nome inválido!", "Valor inválido!", 2);  
              break;
       }
                
    }    
    private void popularComboDeptos()
    {
        //Populando o combo com os nomes dos departamentos
        umMetodo.PreencherCombo(cmbDeptos, "tbldepartamentos", "nome");
        cmbDeptos.setSelectedIndex(-1);                  
    }    
      
    private void TamanhoTxt(){
        txtNumInicial.setBounds(190,10,70,40);
        txtNumFinal.setBounds(190,10,70,40);
    }
    private void Leitura()
    {   
        popularComboDeptos();       
        TamanhoTxt();
    }    
             
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jLayeredPane3 = new javax.swing.JLayeredPane();
        lblSiglaFinal = new javax.swing.JLabel();
        txtNumFinal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLayeredPane4 = new javax.swing.JLayeredPane();
        lblSiglaInicial = new javax.swing.JLabel();
        txtNumInicial = new javax.swing.JTextField();
        lblTitulo = new javax.swing.JLabel();
        jLayeredPane5 = new javax.swing.JLayeredPane();
        cmbDeptos = new javax.swing.JComboBox<String>();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        btnExcluirPorIntervalo = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelPrincipal.setPreferredSize(new java.awt.Dimension(1024, 733));
        panelPrincipal.setLayout(null);

        jLayeredPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLayeredPane3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblSiglaFinal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblSiglaFinal.setForeground(new java.awt.Color(0, 51, 51));
        lblSiglaFinal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSiglaFinal.setText("DEPTO");
        lblSiglaFinal.setToolTipText("");
        jLayeredPane3.add(lblSiglaFinal);
        lblSiglaFinal.setBounds(3, 10, 180, 40);

        txtNumFinal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtNumFinal.setForeground(new java.awt.Color(51, 51, 255));
        txtNumFinal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumFinal.setText("0");
        txtNumFinal.setEnabled(false);
        txtNumFinal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumFinalFocusGained(evt);
            }
        });
        txtNumFinal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNumFinalMouseClicked(evt);
            }
        });
        jLayeredPane3.add(txtNumFinal);
        txtNumFinal.setBounds(190, 10, 21, 35);

        jLayeredPane2.add(jLayeredPane3);
        jLayeredPane3.setBounds(340, 30, 270, 60);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("A");
        jLayeredPane2.add(jLabel5);
        jLabel5.setBounds(310, 40, 20, 40);

        jLayeredPane4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblSiglaInicial.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblSiglaInicial.setForeground(new java.awt.Color(0, 51, 51));
        lblSiglaInicial.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSiglaInicial.setText("DEPTO");
        lblSiglaInicial.setToolTipText("");
        jLayeredPane4.add(lblSiglaInicial);
        lblSiglaInicial.setBounds(3, 10, 180, 40);

        txtNumInicial.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtNumInicial.setForeground(new java.awt.Color(51, 51, 255));
        txtNumInicial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumInicial.setText("0");
        txtNumInicial.setEnabled(false);
        txtNumInicial.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumInicialFocusGained(evt);
            }
        });
        txtNumInicial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNumInicialMouseClicked(evt);
            }
        });
        txtNumInicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumInicialKeyPressed(evt);
            }
        });
        jLayeredPane4.add(txtNumInicial);
        txtNumInicial.setBounds(190, 10, 21, 35);

        jLayeredPane2.add(jLayeredPane4);
        jLayeredPane4.setBounds(30, 30, 270, 60);

        panelPrincipal.add(jLayeredPane2);
        jLayeredPane2.setBounds(10, 180, 630, 110);

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(153, 0, 255));
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("EXCLUSÃO DE NOMES DE REDE POR INTERVALO SELECIONE O DEPARTAMENTO");
        panelPrincipal.add(lblTitulo);
        lblTitulo.setBounds(14, 20, 620, 17);
        lblTitulo.getAccessibleContext().setAccessibleName("ESCOLHA O DEPARTAMENTO PARA VISUALIZAR AS ESTACOES DISPONIVEIS PARA EXCLUSÃO");

        jLayeredPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        cmbDeptos.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        cmbDeptos.setForeground(new java.awt.Color(51, 51, 255));
        cmbDeptos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Departamento" }));
        cmbDeptos.setToolTipText("");
        cmbDeptos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLayeredPane5.add(cmbDeptos);
        cmbDeptos.setBounds(20, 30, 590, 40);

        panelPrincipal.add(jLayeredPane5);
        jLayeredPane5.setBounds(10, 60, 630, 100);

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnExcluirPorIntervalo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnExcluirPorIntervalo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sairPrograma.gif"))); // NOI18N
        btnExcluirPorIntervalo.setText("Excluir Intervalo");
        btnExcluirPorIntervalo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluirPorIntervalo.setEnabled(false);
        btnExcluirPorIntervalo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirPorIntervaloActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnExcluirPorIntervalo);
        btnExcluirPorIntervalo.setBounds(30, 30, 180, 50);

        btnSair.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_sair.gif"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnSair);
        btnSair.setBounds(450, 30, 160, 50);

        btnCancelar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_inicio.gif"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jLayeredPane1.add(btnCancelar);
        btnCancelar.setBounds(250, 30, 160, 50);

        panelPrincipal.add(jLayeredPane1);
        jLayeredPane1.setBounds(10, 310, 630, 100);

        getContentPane().add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 640, 440));

        setSize(new java.awt.Dimension(666, 490));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
          
    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed

        dispose();
        
    }//GEN-LAST:event_btnSairActionPerformed

    private void txtNumInicialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNumInicialMouseClicked
        txtNumInicial.selectAll();
    }//GEN-LAST:event_txtNumInicialMouseClicked

    private void txtNumFinalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNumFinalMouseClicked
       txtNumFinal.selectAll();
    }//GEN-LAST:event_txtNumFinalMouseClicked

    private void txtNumInicialKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumInicialKeyPressed
         if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txtNumFinal.requestFocus();            
        }
    }//GEN-LAST:event_txtNumInicialKeyPressed

    private void txtNumFinalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumFinalFocusGained
        txtNumFinal.selectAll();
    }//GEN-LAST:event_txtNumFinalFocusGained

    private void ExcluirIntevalo(){
        numInicial = Integer.parseInt(txtNumInicial.getText());
        numFinal   = Integer.parseInt(txtNumFinal.getText());
        if(numFinal < numInicial){
            JOptionPane.showMessageDialog(null, "O número final não pode ser menor que o inicial!", "Valor inválido!", 2);                  
            txtNumFinal.setText("0");
            txtNumFinal.requestFocus();
        }else{
            //Pegando a lista de nomes a ser excluida
            ArrayList listaDigitosEstacoes          = new ArrayList();
            ArrayList listaNomesEstacoes            = new ArrayList();
            ArrayList<Integer> listaCodigosDosNomes = new ArrayList<Integer>();
            
            listaDigitosEstacoes = umMetodo.ListarNumerosEstacoes(numInicial, numFinal);
            
            //imprimindo a lista de digitos
//            for (Object cods : listaDigitosEstacoes) {
//                System.out.println(cods); 
//            }   
                
            for (Object numero : listaDigitosEstacoes) {
                //System.out.println(lblSiglaInicial.getText()+numero); 
                listaNomesEstacoes.add(lblSiglaInicial.getText()+numero);
            }
            
            //imprimindo os nomes das estações selecionadas
            for (Object nomes : listaNomesEstacoes) {
                //System.out.println(nomes); 
                //System.out.println(umMetodo.ListarCodigosNomesEstacoes(nomes.toString()));    
                listaCodigosDosNomes.add(umMetodo.ListarCodigoNomeEstacao(nomes.toString()));
            }        
                        
            //Com a lista de codigos na mao excluir da tabela um por um todos que estejam dentro do intevalo        
            for (Object codigo : listaCodigosDosNomes) {
                //System.out.println(codigo); 
                umControleNomeEstacao.excluirNomeEstacaoPorIntervalo((int) codigo);               
            }   
            //Finalizando com mensagem de sucesso
            JOptionPane.showMessageDialog(null, "O intervalo de estações foi excluido com sucesso faça uma consulta e confirme!","Exclusao de nome de rede por intervalo", 2); 
            LimparCampos();      
        }           
    }
    
    private boolean intervaloExiste(){
        numInicial = Integer.parseInt(txtNumInicial.getText());
        numFinal   = Integer.parseInt(txtNumFinal.getText());
        if(numFinal < numInicial){
            JOptionPane.showMessageDialog(null, "O número final não pode ser menor que o inicial!", "Valor inválido!", 2);                  
            txtNumFinal.setText("0");
            txtNumFinal.requestFocus();
        }else{
            //Pegando a lista de nomes a ser excluida
            ArrayList listaDigitosEstacoes          = new ArrayList();
            ArrayList listaNomesEstacoes            = new ArrayList();            
            
            listaDigitosEstacoes = umMetodo.ListarNumerosEstacoes(numInicial, numFinal);
            
            //imprimindo a lista de digitos
//            for (Object cods : listaDigitosEstacoes) {
//                System.out.println(cods); 
//            }   
                
            for (Object numero : listaDigitosEstacoes) {
                //System.out.println(lblSiglaInicial.getText()+numero); 
                listaNomesEstacoes.add(lblSiglaInicial.getText()+numero);
            }   
            
            //imprimindo os nomes das estações selecionadas
            for (Object nomes : listaNomesEstacoes) {
                //System.out.println(nomes); 
                //System.out.println(umMetodo.ListarCodigosNomesEstacoes(nomes.toString()));    
                if(umMetodo.nomeEstacaoExiste(nomes.toString())){
                    return true;
                }
            }               
        }
        return false;
    }
    
    private void btnExcluirPorIntervaloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirPorIntervaloActionPerformed
        if(intervaloExiste()){
            ExcluirIntevalo();                       
        }else{
            JOptionPane.showMessageDialog(null, "O intervalo de estações não existe, verifique!","Intervalo inválido", 2); 
            LimparCampos();
        }
    }//GEN-LAST:event_btnExcluirPorIntervaloActionPerformed

    private void txtNumInicialFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumInicialFocusGained
        if(selecionouItem){
            btnExcluirPorIntervalo.setEnabled(true);
            btnCancelar.setEnabled(true);
        }else{
            btnExcluirPorIntervalo.setEnabled(false);   
            btnCancelar.setEnabled(false);
        }
    }//GEN-LAST:event_txtNumInicialFocusGained

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        JOptionPane.showMessageDialog(null, "Operação cancelada conforme solicitado!","Cancelado!", 2); 
        LimparCampos();        
    }//GEN-LAST:event_btnCancelarActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCancelar;
    public javax.swing.JButton btnExcluirPorIntervalo;
    public javax.swing.JButton btnSair;
    private javax.swing.JComboBox<String> cmbDeptos;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JLayeredPane jLayeredPane3;
    private javax.swing.JLayeredPane jLayeredPane4;
    private javax.swing.JLayeredPane jLayeredPane5;
    private javax.swing.JLabel lblSiglaFinal;
    private javax.swing.JLabel lblSiglaInicial;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTextField txtNumFinal;
    private javax.swing.JTextField txtNumInicial;
    // End of variables declaration//GEN-END:variables
}
