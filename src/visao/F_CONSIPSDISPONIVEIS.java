package visao;

import conexao.ConnConexao;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import biblioteca.Biblioteca;
import biblioteca.ModeloTabela;
import controle.CtrlIPsDisponiveis;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import javax.swing.ListSelectionModel;
import modelo.IPDisponivel;
import Dao.DAOIpsDisponiveis;
import biblioteca.CampoLimitadoParaIP;
import static biblioteca.VariaveisPublicas.sql;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class F_CONSIPSDISPONIVEIS extends javax.swing.JFrame {

    ConnConexao conexao  = new ConnConexao();
    Biblioteca    umabiblio              = new Biblioteca();
    IPDisponivel  umModIP                = new IPDisponivel();
    DAOIpsDisponiveis umDAOIPDisponivel  = new DAOIpsDisponiveis();
    CtrlIPsDisponiveis umCtrlIP          = new CtrlIPsDisponiveis();
    JPopupMenu popupMenu                 = new JPopupMenu();
    JMenuItem jmExclusao                 = popupMenu.add("Excluir Registro");
    
    String sqlDefault      =  "select p.codigo, p.ip, s.nome as secao, t.* from tblpatrimonios p, tblsecoes s, tbltipos t "
                           +  "where p.tipoid=t.codigo and p.secaoid=s.codigo and t.tipo='IMPRESSORA' and p.status='ATIVO' and p.ip<>'0' ORDER BY p.ip";
    String sqlDisponiveis  =  "select * from tblipsdisponiveis where status = 'DISPONIVEL'";
        
    public F_CONSIPSDISPONIVEIS() {
        initComponents();
        setResizable(false);   //desabilitando o redimencionamento da tela      
        PreencherTabelaUTILIZADOS(sqlDefault);
        PreencherTabelaDisponiveis(sqlDisponiveis);
       
        umabiblio.configurarBotoes(btnAdd);
        umabiblio.configurarCamposTextos(txtIP);
        umabiblio.configurarCamposTextos(txtINICIO);
        umabiblio.configurarCamposTextos(txtFIM);
        txtIP.setForeground(Color.BLACK);
        txtINICIO.setDocument(new CampoLimitadoParaIP());
        txtINICIO.setForeground(Color.RED);
        txtFIM.setDocument(new CampoLimitadoParaIP());
        txtFIM.setForeground(Color.RED);
        
        //cofigurações 
        jTabelaUTILIZADOS.setFont(new Font("TimesRoman",Font.BOLD,12));
        jTabelaUTILIZADOS.setForeground(Color.blue);
        jTabelaDISPONIVEIS.setFont(new Font("TimesRoman",Font.BOLD,12));
        jTabelaDISPONIVEIS.setForeground(Color.blue);
        
               
        //Impede que formulario seja arrastado na tela
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                setEnabled(false);
                setEnabled(true);
            }
        });//fim addComponentListener      
        
        //implementando o poupup menu de exclusão ao clicar com o botao direito do mouse na tabela de DISPONIVEIS
        jmExclusao.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println("Ah, a ghost!");
            if(umabiblio.permissaoLiberada()){
                ExcluirLinhaDaTabela();
            }
        }

        });//fim addComponentListener do poupup menu

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTabelaUTILIZADOS = new javax.swing.JTable();
        txtIP = new javax.swing.JTextField();
        lblTITULO2 = new javax.swing.JLabel();
        txtFIM = new javax.swing.JTextField();
        txtINICIO = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTabelaDISPONIVEIS = new javax.swing.JTable();
        lblTITULO1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnLimparTudo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lista de IPs de impressoras cadastradas no sistema");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelPrincipal.setPreferredSize(new java.awt.Dimension(1024, 733));
        panelPrincipal.setLayout(null);

        jTabelaUTILIZADOS.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabelaUTILIZADOS.setToolTipText("UTILIZADOS");
        jTabelaUTILIZADOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane3.setViewportView(jTabelaUTILIZADOS);

        panelPrincipal.add(jScrollPane3);
        jScrollPane3.setBounds(10, 60, 382, 490);

        txtIP.setForeground(new java.awt.Color(51, 51, 255));
        txtIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIPKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIPKeyReleased(evt);
            }
        });
        panelPrincipal.add(txtIP);
        txtIP.setBounds(10, 20, 240, 30);

        lblTITULO2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTITULO2.setForeground(new java.awt.Color(0, 102, 102));
        lblTITULO2.setText("INICIO                       FIM                         Add IP's Disponíveis");
        panelPrincipal.add(lblTITULO2);
        lblTITULO2.setBounds(400, 0, 380, 15);

        txtFIM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFIMKeyPressed(evt);
            }
        });
        panelPrincipal.add(txtFIM);
        txtFIM.setBounds(530, 20, 110, 30);

        txtINICIO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtINICIOKeyPressed(evt);
            }
        });
        panelPrincipal.add(txtINICIO);
        txtINICIO.setBounds(400, 20, 110, 30);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/calculator_add.png"))); // NOI18N
        btnAdd.setText("Add IP");
        btnAdd.setEnabled(false);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnAdd);
        btnAdd.setBounds(650, 20, 130, 33);

        jTabelaDISPONIVEIS.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTabelaDISPONIVEIS.setToolTipText("DISPONÍVEIS");
        jTabelaDISPONIVEIS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane4.setViewportView(jTabelaDISPONIVEIS);

        panelPrincipal.add(jScrollPane4);
        jScrollPane4.setBounds(400, 60, 382, 490);

        lblTITULO1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTITULO1.setForeground(new java.awt.Color(0, 102, 102));
        lblTITULO1.setText("Consultar IPs utilizados por impressoras cadastradas");
        panelPrincipal.add(lblTITULO1);
        lblTITULO1.setBounds(10, 0, 380, 15);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 51, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("A");
        panelPrincipal.add(jLabel1);
        jLabel1.setBounds(510, 30, 20, 15);

        btnLimparTudo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_limpar.gif"))); // NOI18N
        btnLimparTudo.setText("Limpar");
        btnLimparTudo.setEnabled(false);
        btnLimparTudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparTudoActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnLimparTudo);
        btnLimparTudo.setBounds(260, 20, 130, 33);

        getContentPane().add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 800, 570));

        setSize(new java.awt.Dimension(799, 618));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private void atualizarStatusDosIPs(){
        //sql vai encontrar todos os ips de impressoras ATIVAS que constem como DISPONIVEL na tabela de IPS disponiveis e vai alterar seus status para INDISPONIVEL
        sql = "select ip from tblPatrimonios where tipoid=3 and ip<>'0' and status='ATIVO' and ip IN(select ip from tblipsdisponiveis where status='DISPONIVEL')";
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        try {
            while(conexao.rs.next()) {   
                String ipEncontrado = conexao.rs.getString("ip");
                //System.out.println("Ip encontrado...: "+ipEncontrado+"\n");
                int idIP = umabiblio.buscarCodigoGenerico("tblipsdisponiveis", "ip", ipEncontrado);
                umModIP.setCodigo(idIP); 
                umModIP.setIp(ipEncontrado);           
                umModIP.setStatus("INDISPONIVEL"); 
                umCtrlIP.atualizarIPDisponivel(umModIP);                
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela ATIVOS!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }
        PreencherTabelaDisponiveis(sqlDisponiveis);
        //metodo vai verificar todos os ips cadastrados nas impressoras e se nao estiver na lista vai adicionar 
        verificarDuplicidadeNaListaDeIPsInserirApenasOsQueNaoConstarem();
    }
    
    private void inserirIPUtilizadoNaListaComoIndisponivel(String ip){
        //metodo de inserção de ip na tabela TBLIPSDISPONIVEIS, entrando como INDISPONIVEL pois já estao sendo utilizados na TBLPATRIMONIOS   
        umModIP.setIp(ip);
        umModIP.setStatus("INDISPONIVEL");
        umCtrlIP.salvarIPDisponivel(umModIP);
    }
    
    private void verificarDuplicidadeNaListaDeIPsInserirApenasOsQueNaoConstarem(){
       //metodo vai verificar todos os ips cadastrados nas impressoras e se nao estiver na lista vai adicionar 
       sql = "select ip from tblPatrimonios where tipoid=3 and ip<>'0' and status='ATIVO' and ip NOT IN(select ip from tblipsdisponiveis)";
       conexao.conectar();
       conexao.ExecutarPesquisaSQL(sql); 
       try {
            while (conexao.rs.next()) {
                inserirIPUtilizadoNaListaComoIndisponivel(conexao.rs.getString("ip"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar registros!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }         
    }        
    
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        atualizarStatusDosIPs();
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sqlDefault);
        try {
            if (conexao.rs.next()) {   //selecionando a primeira linha somente se tiver registros
                jTabelaUTILIZADOS.addRowSelectionInterval(0, 0);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela ATIVOS!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }
    }//GEN-LAST:event_formWindowOpened
    
    public void filtrarPorDigitacao(String pPesq)   
    {
        //filtrando por digitação 
        PreencherTabelaUTILIZADOS("select p.codigo, p.ip, s.nome as secao from tblpatrimonios p, tblsecoes s, tbltipos t where p.ip like '%"+pPesq+"%'"+" and "
                      + "p.secaoid=s.codigo and p.tipoid=t.codigo and t.tipo='IMPRESSORA' and p.status='ATIVO' and p.ip<>0 ORDER BY p.ip");
    }
    
    private void txtIPKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIPKeyReleased
        //só filtra se tiver algo digitado
        if(txtIP.getText().length()>0)
           filtrarPorDigitacao(txtIP.getText());
        
    }//GEN-LAST:event_txtIPKeyReleased

    private void txtIPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIPKeyPressed
         //se teclar enter estando dentro da pesquisa limpar a pesquisa
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            PreencherTabelaUTILIZADOS(sqlDefault);
            txtIP.setText(null);                
        }
         btnLimparTudo.setEnabled(true);
         txtINICIO.setEditable(false);
         txtFIM.setEditable(false);
    }//GEN-LAST:event_txtIPKeyPressed
    
    public void addUmIPDisponivel(){
        String ipParaCadastrar = txtINICIO.getText();
        umModIP.setIp(ipParaCadastrar);
        umModIP.setStatus("DISPONIVEL");
            
            if(!umDAOIPDisponivel.RegistroDuplicado(umModIP))     //tblIPsDisponiveis
            {            
                if(!umDAOIPDisponivel.RegistroUtilizado(umModIP)) //tblPatrimonios
                {
                    umCtrlIP.salvarIPDisponivel(umModIP);  
                    JOptionPane.showMessageDialog(null,"IP "+ipParaCadastrar+" foi disponibilizado com sucesso!");    
                    PreencherTabelaDisponiveis(sqlDisponiveis);                        
                }else{
                    JOptionPane.showMessageDialog(null,"IP "+ipParaCadastrar+" já esta sendo utilizado, consulte o quadro a esquerda!","Operação não concluída!",2);                
                }
            }else{
                JOptionPane.showMessageDialog(null,"IP "+ipParaCadastrar+" já esta disponível, consulte o quadro a direita!","Operação não concluída!",2);                  
            }
            txtINICIO.setText(null);
            txtINICIO.requestFocus();        
                
    }
    
    public void addUmRangeIPDisponivel(){
        String ipInicial, ipFinal, ipCadastrar, qdIPCadastrar, inicioRange, numeroInicial  = "";
        int numInicial;
        int numFinal;
        int qdeIPsCadastrar;        
        
        ipInicial = txtINICIO.getText();
        ipFinal   = txtFIM.getText();
        
        //receb os seis primeiros numeros digitados => mostra 107133
        int tamanhoString = ipInicial.length();
        inicioRange = ipInicial.substring(0, tamanhoString-3);
                
        //mostra o final do primeiro ip digitado ex: 107133100 - mostra 100
        int tamanhoIni = ipInicial.length();
        ipInicial = ipInicial.substring(6, tamanhoIni);
        numInicial = Integer.valueOf(ipInicial);
        //System.out.println("Numero inicial...: "+numInicial);
        
        //mostra o final do segundo ip digitado ex: 107133105 - mostra 105
        int tamanhoFim = ipFinal.length();
        ipFinal  = ipFinal.substring(6, tamanhoFim);
        numFinal = Integer.valueOf(ipFinal);
        //System.out.println("Numero final.....: "+numFinal);
        
        //obtem a quantidade de ips que serão cadastrados ex: (105-100)
        qdeIPsCadastrar = (numFinal-numInicial+1);
        
//        qdIPCadastrar = String.valueOf("Qde de IPs para cadastrar...: "+qdeIPsCadastrar);
//        ipCadastrar    = ("IPs para cadastrar...: "+inicioRange+ipInicial);        
//        System.out.println(ipCadastrar);
        
        //AGORA COM O IP PARA CADASTRAR NA MAO CADASTRE ADICIONANDO A QDE DE IPS ANO NUMERO TIPO 107133100...101...102
         for(int i=0; i < qdeIPsCadastrar; i++)
         {
            ipCadastrar    = (inicioRange+(numInicial+i));
            umModIP.setIp(ipCadastrar);
            umModIP.setStatus("DISPONIVEL");
            
            if(!umDAOIPDisponivel.RegistroDuplicado(umModIP))       //tblIPsDisponiveis
            {            
                if(!umDAOIPDisponivel.RegistroUtilizado(umModIP))   //tblPatrimonios
                {
                    umCtrlIP.salvarIPDisponivel(umModIP);  
                    PreencherTabelaDisponiveis(sqlDisponiveis);                        
                }else{
                    JOptionPane.showMessageDialog(null,"IP "+ipCadastrar+" já esta sendo utilizado, consulte o quadro a esquerda!","Operação não concluída!",2);                
                }
            }else{
                JOptionPane.showMessageDialog(null,"IP "+ipCadastrar+" já esta disponível, consulte o quadro a direita!","Operação não concluída!",2);                  
            }
            txtINICIO.setText(null);
            txtFIM.setText(null);
            txtINICIO.requestFocus();
         }
         JOptionPane.showMessageDialog(null,"O range de Ips foi disponibilizado com sucesso!");        
    }
        
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if(umabiblio.permissaoLiberada()){
            if(txtINICIO.getText().length()>0 && txtFIM.getText().length()==0){
               addUmIPDisponivel();
           }else{
               if(!txtINICIO.getText().equals(txtFIM.getText())){
                  addUmRangeIPDisponivel();
               }else{
                   JOptionPane.showMessageDialog(null,"Os ips do range devem ser diferentes um do outro!","Operação não concluída!",2);    
                   txtINICIO.setText(null);
                   txtFIM.setText(null);
                   txtINICIO.requestFocus();
               }
           }
        }
        btnAdd.setEnabled(false);
        btnLimparTudo.setEnabled(false);          
        
    }//GEN-LAST:event_btnAddActionPerformed

    private void LimparCampos(){
        txtINICIO.setText("");
        txtFIM.setText("");
        txtIP.setText("");
        txtIP.requestFocus();
        txtINICIO.setEditable(true);
        txtFIM.setEditable(true);
        txtIP.setEditable(true);
    }
    
    private void txtINICIOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtINICIOKeyPressed
         //se teclar enter estando dentro da pesquisa limpar a pesquisa
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
          txtFIM.requestFocus();       
        }
        txtIP.setEditable(false);
        btnLimparTudo.setEnabled(true);
        btnAdd.setEnabled(true);
    }//GEN-LAST:event_txtINICIOKeyPressed

    private void txtFIMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFIMKeyPressed
        if(!txtINICIO.getText().equals("")){
            btnAdd.setEnabled(true);
            btnLimparTudo.setEnabled(true);
        }else{
            JOptionPane.showMessageDialog(null,"Se deseja cadastrar um range de ips comece digitando o inicio do range no campo inicial e o fim neste campo!","Cadastro de range de ips!",2);
            JOptionPane.showMessageDialog(null,"Se deseja cadastrar apenas um ip digite o mesmo no campo inicial somente!","Cadastro de um ip!",2);
            txtINICIO.requestFocus();
            txtFIM.setText("");
        }
    }//GEN-LAST:event_txtFIMKeyPressed

    private void btnLimparTudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparTudoActionPerformed
        LimparCampos();
        btnAdd.setEnabled(false);
        btnLimparTudo.setEnabled(false);        
    }//GEN-LAST:event_btnLimparTudoActionPerformed
    
    private void ExcluirLinhaDaTabela(){
        //verificando primeiro se o usuário selecionou uma linha na tabela
        int selecionada = jTabelaDISPONIVEIS.getSelectedRow();
        if (selecionada == -1) {
            JOptionPane.showMessageDialog(null,"Operação não concluída, você não selecionou uma linha!","Selecione uma linha para excluir!",2); //Não tem nada selecionado
        }else{
            //passando o codigo do registro selecionado para exclusão
            int codigoRegSelecionado = (int) jTabelaDISPONIVEIS.getValueAt(jTabelaDISPONIVEIS.getSelectedRow(), 0);
            umModIP.setCodigo(codigoRegSelecionado);
            
                if(umabiblio.ConfirmouOperacao("Confirma a exclusão do registro selecionado?", "Excluindo registro!")){
                    umCtrlIP.excluirIPDisponivel(umModIP);
                    JOptionPane.showMessageDialog(null,"Registro excluído com sucesso!");
                    PreencherTabelaDisponiveis(sqlDisponiveis);    
                }
        }
    }
    
   public void PreencherTabelaUTILIZADOS(String sql)
   {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Ips Utilizados", "Seção"};
        try 
        {            
            conexao.ExecutarPesquisaSQL(sql);  
            while (conexao.rs.next())
            {   
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("ip"),
                    conexao.rs.getString("secao")
                    
                });
            };

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabelaUTILIZADOS.setModel(modelo);
            //define tamanho das colunas
            jTabelaUTILIZADOS.getColumnModel().getColumn(0).setPreferredWidth(50);  //define o tamanho da coluna
            jTabelaUTILIZADOS.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaUTILIZADOS.getColumnModel().getColumn(1).setPreferredWidth(150);  //define o tamanho da coluna
            jTabelaUTILIZADOS.getColumnModel().getColumn(1).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaUTILIZADOS.getColumnModel().getColumn(2).setPreferredWidth(160);  //define o tamanho da coluna
            jTabelaUTILIZADOS.getColumnModel().getColumn(2).setResizable(false);    //nao será possivel redimencionar a coluna 

            //define propriedades da tabela
            jTabelaUTILIZADOS.getTableHeader().setReorderingAllowed(false);        //nao podera ser reorganizada
            jTabelaUTILIZADOS.setAutoResizeMode(jTabelaUTILIZADOS.AUTO_RESIZE_OFF);          //nao será possivel redimencionar a tabela
            jTabelaUTILIZADOS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
   
        } catch (SQLException ex) {
            //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
            JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        }
       
    }
   public void PreencherTabelaDisponiveis(String sql)
   {
        conexao.conectar();
        ArrayList dados = new ArrayList();
        //para receber os dados das colunas(exibe os titulos das colunas)
        String[] Colunas = new String[]{"Código", "Ips Disponíveis"};
        try 
        {            
            conexao.ExecutarPesquisaSQL(sql);  
            while (conexao.rs.next())
            {   
                dados.add(new Object[]
                {
                    conexao.rs.getInt("codigo"),
                    conexao.rs.getString("ip")
                    
                });
            };

            ModeloTabela modelo = new ModeloTabela(dados, Colunas);
            jTabelaDISPONIVEIS.setModel(modelo);
            //define tamanho das colunas
            jTabelaDISPONIVEIS.getColumnModel().getColumn(0).setPreferredWidth(50);  //define o tamanho da coluna
            jTabelaDISPONIVEIS.getColumnModel().getColumn(0).setResizable(false);    //nao será possivel redimencionar a coluna 
            jTabelaDISPONIVEIS.getColumnModel().getColumn(1).setPreferredWidth(300);  //define o tamanho da coluna
            jTabelaDISPONIVEIS.getColumnModel().getColumn(1).setResizable(false);    //nao será possivel redimencionar a coluna 

            //define propriedades da tabela
            jTabelaDISPONIVEIS.getTableHeader().setReorderingAllowed(false);        //nao podera ser reorganizada
            jTabelaDISPONIVEIS.setAutoResizeMode(jTabelaDISPONIVEIS.AUTO_RESIZE_OFF);          //nao será possivel redimencionar a tabela
            jTabelaDISPONIVEIS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //so podera selecionar apena uma linha  
            jTabelaDISPONIVEIS.setComponentPopupMenu(popupMenu); //adicionando o poupup menu de exclusão na tabela ao clicar o botao direito
              
        } catch (SQLException ex) {
            //apos a consulta acima abrir o formulario mesmo que a tabela esteja vazia  
            JOptionPane.showMessageDialog(null, "Erro ao preencher o ArrayList!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        }
       
    }
   
    public static void main(String args[]){
       F_CONSIPSDISPONIVEIS frm = new F_CONSIPSDISPONIVEIS();
       frm.setVisible(true);
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAdd;
    public javax.swing.JButton btnLimparTudo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JTable jTabelaDISPONIVEIS;
    public javax.swing.JTable jTabelaUTILIZADOS;
    public javax.swing.JLabel lblTITULO1;
    public javax.swing.JLabel lblTITULO2;
    private javax.swing.JPanel panelPrincipal;
    public javax.swing.JTextField txtFIM;
    public javax.swing.JTextField txtINICIO;
    public javax.swing.JTextField txtIP;
    // End of variables declaration//GEN-END:variables
}
