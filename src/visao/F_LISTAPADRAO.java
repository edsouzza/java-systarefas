package visao;

import biblioteca.TudoMaiusculas;
import conexao.ConnConexao;
import java.awt.Color;
import java.awt.Font;
import biblioteca.MetodosPublicos;
import biblioteca.ModeloTabela;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import static biblioteca.VariaveisPublicas.codigoTipoDocumento;
import static biblioteca.VariaveisPublicas.codigoDepartamento;
import static biblioteca.VariaveisPublicas.TipoDocumento;
import static biblioteca.VariaveisPublicas.nomeDepartamento;
import static biblioteca.VariaveisPublicas.codigoTipoModelo;
import static biblioteca.VariaveisPublicas.TipoModelo;
import static biblioteca.VariaveisPublicas.tabela_da_lista;
import static biblioteca.VariaveisPublicas.cadastrando;
import static biblioteca.VariaveisPublicas.cadPatrimovel;
import static biblioteca.VariaveisPublicas.cadPatriDeptos;
import static biblioteca.VariaveisPublicas.nomeSecao;
import static biblioteca.VariaveisPublicas.codigoSecao;
import static biblioteca.VariaveisPublicas.nomeCliente;
import static biblioteca.VariaveisPublicas.rfCliente;
import static biblioteca.VariaveisPublicas.totalRegs;
import static biblioteca.VariaveisPublicas.codigoNumemo;
import static biblioteca.VariaveisPublicas.numemoParaImprimir;
import controle.CtrlCliente;
import controle.CtrlModelo;
import controle.CtrlTipoDocumentos;
import controle.CtrlDepartamento;
import controle.CtrlPatriTransferido;
import controle.CtrlSecoes;
import modelo.Cliente;
import modelo.TipoDocumento;
import modelo.Modelo;
import modelo.Departamento;
import modelo.PatriTransferido;
import modelo.Secao;
import relatorios.GerarRelatorios;


public class F_LISTAPADRAO extends javax.swing.JDialog {
    
    ConnConexao          conexao               = new ConnConexao();
    MetodosPublicos      umMetodo              = new MetodosPublicos(); 
    
    TipoDocumento        objTipoDocumento      = new TipoDocumento();
    CtrlTipoDocumentos   ctrlTipoDocumento     = new CtrlTipoDocumentos();   
    
    Modelo               objTipoModelo         = new Modelo();
    CtrlModelo           ctrlTipoModelo        = new CtrlModelo();   
    
    Departamento         objTipoDepartamento   = new Departamento();
    CtrlDepartamento     ctrlTipoDepartamento  = new CtrlDepartamento();  
    
    Cliente              objCliente            = new Cliente();
    CtrlCliente          ctrlCliente           = new CtrlCliente(); 
    
    Secao                objSecao              = new Secao();
    CtrlSecoes           ctrlSecoes            = new CtrlSecoes(); 
    
    PatriTransferido     objPatriTransferido   = new PatriTransferido();
    CtrlPatriTransferido ctrlPatriTransferido  = new CtrlPatriTransferido();    
    
            
    String               nomeDoCampo           = null;
    int qdeRegs                                = umMetodo.getQdeRegistrosNaTabela(tabela_da_lista);
    int icodigo = 0;
    
    public F_LISTAPADRAO(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.setFocusableWindowState(true); //corrigindo erro quando clico fora da lista
        initComponents();   
        setTitle(tabela_da_lista.substring(3, tabela_da_lista.length()));
        setResizable(false);   //desabilitando o redimencionamento da tela        
        jTabela.setForeground(Color.blue);
        txtPESQUISA.setDocument(new TudoMaiusculas());
        txtPESQUISA.setFont(new Font("Tahoma", Font.BOLD, 12));
        txtPESQUISA.setForeground(Color.red);
        jTabela.setFont(new Font("Arial", Font.BOLD, 12));
        
        PreencherLista();
        
        if (cadastrando) {
            setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE); //desabilitando o botao fechar            
        }
        
    }
     
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jBoxPesquisar = new javax.swing.JLayeredPane();
        txtPESQUISA = new javax.swing.JTextField();
        btnLimparPesquisa = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTabela = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jBoxPesquisar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jBoxPesquisar.setName("panelDados"); // NOI18N

        txtPESQUISA.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPESQUISA.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPESQUISAFocusGained(evt);
            }
        });
        txtPESQUISA.addKeyListener(new java.awt.event.KeyAdapter() {
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
                .addComponent(txtPESQUISA, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jBoxPesquisarLayout.setVerticalGroup(
            jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBoxPesquisarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jBoxPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jBoxPesquisarLayout.createSequentialGroup()
                        .addGap(0, 2, Short.MAX_VALUE)
                        .addComponent(txtPESQUISA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnLimparPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jBoxPesquisar)
            .addComponent(jScrollPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jBoxPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(736, 529));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void PreencherLista()
    {                
        //DETERMINA QUAL SERÁ O CAMPO A SER EXIBIDO NA LISTA DA TABELA(JTABELA)
        //JOptionPane.showMessageDialog(null, tabela_da_lista);
        
        switch (tabela_da_lista) {
            case "TBLTIPODOCUMENTOS":
                nomeDoCampo = "tipo";
                break;                                 
            case "TBLMODELOS":
                nomeDoCampo = "modelo";                
                break;                                 
            case "TBLDEPARTAMENTOS":
                nomeDoCampo = "nome";
                break;  
            case "TBLCLIENTES":
                nomeDoCampo = "nome";                
                break;                 
            case "TBLSECOES":
                nomeDoCampo = "nome";                
                break;                 
            case "TBLMEMOSTRANSFERIDOS":
                nomeDoCampo = "numemo";                
                break;                 
        }  
        
    }

    private void btnLimparPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparPesquisaActionPerformed
        limparCampos();
    }//GEN-LAST:event_btnLimparPesquisaActionPerformed
    
    public void limparCampos() {
        txtPESQUISA.setText("");
        txtPESQUISA.setEnabled(true);
        btnLimparPesquisa.setEnabled(false);
        txtPESQUISA.requestFocus(); 
    }
    
    public void ImprimirRelatorioSelecionado(String numemo){
        GerarRelatorios objRel = new GerarRelatorios();
        try {
            objRel.imprimirRelatorioPatrimoniosTransferidos("relatorio/relimprimirmemodetransferidos.jasper", numemo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao gerar relatório!\n"+e);                
        }    
    }

    private void jTabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabelaMouseClicked
        //AO CLICAR EM UM REGISTRO DA TABELA PEGAR O NOME E CODIGO DO CLIENTE FECHAR E MOSTRAR O FORMULARIO DE CADASTRO DE TAREFAS COM OS DADOS SETADOS
        //JOptionPane.showMessageDialog(null, "NOME SELECIONADO...: "+jTabela.getValueAt(jTabela.getSelectedRow(), 0));        

        switch (tabela_da_lista) { 
            
            case "TBLTIPODOCUMENTOS":
                //pegando o nome selecionado e setando no modelo neste caso o nome do tipo de documento e codigo
                icodigo = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0);
                objTipoDocumento.setCodigo(icodigo); 
                ctrlTipoDocumento.pesquisarTipoDocumento(objTipoDocumento);                
                codigoTipoDocumento     = objTipoDocumento.getCodigo();
                TipoDocumento           = objTipoDocumento.getTipo();                
                break;   
                
            case "TBLMODELOS":
                //pegando o nome selecionado e setando no modelo neste caso o nome do modelo e codigo               
                icodigo = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0);
                objTipoModelo.setCodigo(icodigo); 
                ctrlTipoModelo.pesquisarModelo(objTipoModelo);                
                codigoTipoModelo        = objTipoModelo.getCodigo();
                TipoModelo              = objTipoModelo.getModelo();                                 
                break;    
                
            case "TBLDEPARTAMENTOS":
                //pegando o nome selecionado e setando no modelo neste caso o nome do departamento e codigo
                icodigo = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0);
                objTipoDepartamento.setCodigo(icodigo); 
                ctrlTipoDepartamento.pesquisarDepartamento(objTipoDepartamento);                
                codigoDepartamento      = objTipoDepartamento.getCodigo();
                nomeDepartamento        = objTipoDepartamento.getNome();                                 
                break;        
                
            case "TBLSECOES":
                //pegando o nome selecionado e setando no modelo neste caso o nome da secao e codigo
                icodigo = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0);
                objSecao.setCodigo(icodigo); 
                ctrlSecoes.pesquisarSecao(objSecao);                
                codigoSecao      = objSecao.getCodigo();
                nomeSecao        = objSecao.getNome();      
                codigoDepartamento = umMetodo.retornaIdDepto(codigoSecao);
                break;        
                
            case "TBLCLIENTES":
                //pegando o nome selecionado e setando no modelo neste caso o nome do cliente e codigo
                icodigo = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0);
                objCliente.setCodigo(icodigo); 
                ctrlCliente.pesquisarCliente(objCliente);                
                nomeCliente      = objCliente.getNome();
                rfCliente        = objCliente.getRf(); 
                codigoSecao      = objCliente.getSecaoid();
                nomeSecao        = umMetodo.getStringPassandoCodigo("tblsecoes","nome",codigoSecao);
                break;      
                
            case "TBLMEMOSTRANSFERIDOS":
                //pegando o nome selecionado e setando no modelo neste caso o nome do cliente e codigo
                icodigo = (int) jTabela.getValueAt(jTabela.getSelectedRow(), 0);                
                objPatriTransferido.setCodigo(icodigo); 
                ctrlPatriTransferido.pesquisarPatriTransferido(objPatriTransferido);  
                codigoNumemo       = objPatriTransferido.getCodigo();
                numemoParaImprimir = objPatriTransferido.getNumemo();    
                ImprimirRelatorioSelecionado(numemoParaImprimir);                
                break;        
            
            default:
                JOptionPane.showMessageDialog(null, "Nenhuma alternativa válida foi selecionada!");
        }
        cadPatrimovel  =false;
        cadPatriDeptos =false;
        cadastrando    =false;
        dispose();              
        
    }//GEN-LAST:event_jTabelaMouseClicked
    
    private void filtrarPorDigitacao(String pPesq) 
    {
        if(tabela_da_lista.equals("TBLTIPODOCUMENTOS")){
            PreencherTabelaPadrao("select * from TBLTIPODOCUMENTOS where (tipo like '%" + pPesq + "%') ORDER BY tipo");                      
        }else if(tabela_da_lista.equals("TBLMODELOS")&&cadPatrimovel){
            PreencherTabelaPadrao("select * from TBLMODELOS where (modelo like '%" + pPesq + "%') and tipoid=1 and tipoid=2 order by tipoid");         //Cadastro PatriMoveis          
        }else if(tabela_da_lista.equals("TBLMODELOS")&&cadPatriDeptos){
            PreencherTabelaPadrao("select * from TBLMODELOS where (modelo like '%" + pPesq + "%') and tipoid= "+codigoTipoModelo+" order by tipoid");  //Cadastro PatriDeptos
        }else if(tabela_da_lista.equals("TBLMODELOS")){
            PreencherTabelaPadrao("select * from TBLMODELOS where (modelo like '%" + pPesq + "%') ORDER BY modelo");                      
        }else if(tabela_da_lista.equals("TBLDEPARTAMENTOS")){
            PreencherTabelaPadrao("select * from TBLDEPARTAMENTOS where (nome like '%" + pPesq + "%') ORDER BY nome");                      
        }else if(tabela_da_lista.equals("TBLCLIENTES")){
            PreencherTabelaPadrao("select * from TBLCLIENTES where tipo='F' and status = 'ATIVO' and (nome like '%" + pPesq + "%') OR (rf like '%" + pPesq + "%') ORDER BY nome,rf");                            
        }else if(tabela_da_lista.equals("TBLSECOES")){
            PreencherTabelaPadrao("select * from TBLSECOES where status = 'ATIVO' and (nome like '%" + pPesq + "%') ORDER BY nome");                      
        }else if(tabela_da_lista.equals("TBLMEMOSTRANSFERIDOS")){
            PreencherTabelaPadrao("select * from TBLMEMOSTRANSFERIDOS  where status = 'TRANSFERIDO' and (numemo like '%" + pPesq + "%') ORDER BY codigo");   
        }
        this.setTitle("Total de registros retornados pela pesquisa = "+totalRegs);
    }

    private void txtPESQUISAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPESQUISAKeyReleased
        filtrarPorDigitacao(txtPESQUISA.getText());
        btnLimparPesquisa.setEnabled(true);
        
    }//GEN-LAST:event_txtPESQUISAKeyReleased

    private void txtPESQUISAFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPESQUISAFocusGained
        //PARA MOSTRAR A LISTA SOLICITADA LOGO QUE ABRIR O FORMULARIO
        if(tabela_da_lista.equals("TBLCLIENTES")){
            PreencherTabelaPadrao("select * from TBLCLIENTES where tipo='F' and status = 'ATIVO' ORDER BY nome");
        }else{  
         filtrarPorDigitacao("");
        }
    }//GEN-LAST:event_txtPESQUISAFocusGained
    
    public void PreencherTabelaPadrao(String sql) {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Nome"};
        conexao.ExecutarPesquisaSQL(sql);
        try {
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                dados.add(new Object[]{
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString(nomeDoCampo)
                });
                totalRegs = conexao.rs.getRow(); //passando o total de registros para o titulo
            };
            
            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabela.setModel(modelo);
            //define tamanho das colunas
            jTabela.getColumnModel().getColumn(0).setPreferredWidth(80);  //define o tamanho da coluna
            jTabela.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabela.getColumnModel().getColumn(1).setPreferredWidth(615);
            jTabela.getColumnModel().getColumn(1).setResizable(false);

            //define propriedades da tabela
            jTabela.getTableHeader().setReorderingAllowed(false);        //nao podera ser reorganizada
            jTabela.setAutoResizeMode(jTabela.AUTO_RESIZE_OFF);          //nao será possivel redimencionar a tabela
            jTabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha 

        } catch (SQLException ex) {
            //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
            JOptionPane.showMessageDialog(null, "Erro ao preencher a lista padrão!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }        
    }          
    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimparPesquisa;
    private javax.swing.JLayeredPane jBoxPesquisar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTabela;
    private javax.swing.JTextField txtPESQUISA;
    // End of variables declaration//GEN-END:variables
}