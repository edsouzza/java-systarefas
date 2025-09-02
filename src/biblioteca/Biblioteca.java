package biblioteca;
import conexao.ConnConexao;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.tabela;
import static biblioteca.VariaveisPublicas.nivelAcessoUsuario;
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


public class Biblioteca 
{  
    //para usar basta umabiblio.limparTodosCampos(jBoxDados) onde jBoxDados é o container em estao o JTextFields
    public static Object id;
    ConnConexao conexao  = new ConnConexao();
    boolean result             = true;
    String strRange            = "";
    public static ArrayList<String> lstListaDeStrings = new ArrayList<>();
    
    public void limparTodosCampos(Container container) {  
        Component components[] = container.getComponents();  
        for (Component component : components) {  
            if (component instanceof JFormattedTextField) {  
                JFormattedTextField field = (JFormattedTextField) component;  
                field.setValue(null);  
            } else if (component instanceof JTextField) {  
                JTextField field = (JTextField) component;  
                field.setText("");  
            }else if (component instanceof JTextArea ){
                JTextArea field = (JTextArea) component;  
                field.setText("");
            } else if (component instanceof Container) {  
                limparTodosCampos((Container) component);  
            }
        }
        
    } 
   
    public String mostrarNivelAcesso()
    {                
        if( nivelAcessoUsuario > 1 && nivelAcessoUsuario < 3 )     //se for igual a 2
        {
            return "ADMINISTRADOR";
        }
        else if(nivelAcessoUsuario > 2)                            //se for maior que 2 
        {
            return "USUÁRIO";
        }
        else if(nivelAcessoUsuario > 0 && nivelAcessoUsuario < 2 )  //se for igual a 1
        {
            return "SISTEMA";
        }
        return null;
    }
    public void configurarBotoes(JButton botao)
    {
        botao.setFont(new Font("TimesRoman", Font.BOLD, 12));        
        botao.setCursor(new Cursor(12));
        
    }
    public void configurarCamposTextos(JTextField campoTexto)
    {
        campoTexto.setForeground(Color.blue);
        campoTexto.setFont(new Font("TimesRoman", Font.BOLD, 14));  
        campoTexto.setDocument(new TudoMaiusculas());
        
    }
    public int qdeRegistros(String tabela)
    {        
        conexao.conectar();
        sql = "select count(codigo) as total from "+tabela;
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next())
               return conexao.rs.getInt("total");
            else
                return 0; 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return 0;
        }finally{
            conexao.desconectar();
        }                 
    }
    public int qdeRegistrosATIVOS(String tabela)
    {        
        conexao.conectar();
        sql = "select count(codigo) as total from "+tabela+" where status='ENVIAR'";
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next())
               return conexao.rs.getInt("total");
            else
                return 0; 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return 0;
        }finally{
            conexao.desconectar();
        }                 
    }
    public int qdeRegistrosINATIVOS(String tabela)
    {        
        conexao.conectar();
        sql = "select count(codigo) as total from "+tabela+" where status='ENCERRADO'";
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next())
               return conexao.rs.getInt("total");
            else
                return 0; 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return 0;
        }finally{
            conexao.desconectar();
        }                 
    }
    public int qdePatrimovelRegistrosINATIVOS(String tabela)
    {        
        conexao.conectar();
        sql = "select count(codigo) as total from "+tabela+" where status='INATIVO'";
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next())
               return conexao.rs.getInt("total");
            else
                return 0; 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return 0;
        }finally{
            conexao.desconectar();
        }                 
    }
    public String mostrarTituloDoFormulario()
    {
        int qdeRegsATIVOS   = qdeRegistrosATIVOS(tabela);
        int qdeRegsINATIVOS = qdeRegistrosINATIVOS(tabela);
        int qdeRegs         = qdeRegistros(tabela);   
        //substring retira o TBL da palavra
        
        String nomeTabela = tabela.substring(3);
        nomeTabela        = nomeTabela.toLowerCase();  
        String sStatus;
        
        if(qdeRegsINATIVOS == 1){
            sStatus = "encerrado";
        }else{
            sStatus = "encerrados";
        }
        
        if(qdeRegsATIVOS == 1){
           return "Gerenciamento de " + nomeTabela +" com "+qdeRegs+" registro(s) cadastrado(s) e "+qdeRegsATIVOS+" "+sStatus+""; 
        }else{
           return "Gerenciamento de " + nomeTabela +" com "+qdeRegs+" registros cadastrados e "+qdeRegsINATIVOS+" "+sStatus+""; 
        }        
    }
    public String mostrarTituloDoFormularioPatrimoveis()
    {
        int qdeRegsATIVOS   = qdeRegistrosATIVOS(tabela);
        int qdeRegsINATIVOS = qdePatrimovelRegistrosINATIVOS(tabela);
        int qdeRegs         = qdeRegistros(tabela);   
        //substring retira o TBL da palavra
        
        String nomeTabela = tabela.substring(3);
        nomeTabela        = nomeTabela.toLowerCase();  
        String sStatus;
        
        if(qdeRegsINATIVOS == 1){
            sStatus = "inativo";
        }else{
            sStatus = "inativos";
        }
        
        if(qdeRegsATIVOS == 1){
           return "Gerenciamento de " + nomeTabela +" com "+qdeRegs+" registro(s) cadastrado(s) e "+qdeRegsATIVOS+" "+sStatus+""; 
        }else{
           return "Gerenciamento de " + nomeTabela +" com "+qdeRegs+" registros cadastrados e "+qdeRegsINATIVOS+" "+sStatus+""; 
        }        
    }
    public String mostrarTituloDoFormularioParaInativos()
    {
        int qdeRegsINATIVOS = qdeRegistrosINATIVOS(tabela);
        int qdeRegs         = qdeRegistros(tabela);   
        //substring retira o TBL da palavra
        
        String nomeTabela = tabela.substring(3);
        nomeTabela        = nomeTabela.toLowerCase();    
        
        if(qdeRegsINATIVOS == 1){
           return "Gerenciamento de " + nomeTabela +" inativos com "+qdeRegsINATIVOS+" registro inativo"; 
        }else{
           return "Gerenciamento de " + nomeTabela +" inativos com "+qdeRegsINATIVOS+" registros";  
        }
        
    }
    public int mostrarUltimoCodigo(String tabela){
        conexao.conectar();
        sql = "select max(codigo) as ultimoCodigo from "+tabela;
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next())
               return conexao.rs.getInt("ultimoCodigo");
            else
                return 0; 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return 0;
        }finally{
            conexao.desconectar();
        }  
    }
    public int mostrarProximoCodigo(String tabela){
        conexao.conectar();
        sql = "select max(codigo) as proximoCodigo from "+tabela;
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next())
               return conexao.rs.getInt("proximoCodigo")+1;
            else
                return 0; 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return 0;
        }finally{
            conexao.desconectar();
        }  
    }
    
    public boolean registroEncontrado(String tabela, String campoTabelaParaComparar, String paramDeComparacao)
    {
        //pesquisa se um registro existe na tabela passando como parametro uma string 
        conexao.conectar();      
        sql = "SELECT "+campoTabelaParaComparar+" FROM "+tabela+" WHERE "+campoTabelaParaComparar+" = '"+paramDeComparacao+"'";           
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next()) {
                //JOptionPane.showMessageDialog(null, "Registro encontrado");
                return true;
                
            } else {
                //JOptionPane.showMessageDialog(null, "Registro nao encontrado");
                return false;
            }
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return false;
        }finally{
            conexao.desconectar();
        }  
    } 
    
    public boolean registroInativado(String serie, String chapa)
    {
        //pesquisa se um registro existe na tabela passando como parametro uma string 
        conexao.conectar();      
        sql = "SELECT serie,chapa FROM tblpatrimonios WHERE serie = '"+serie+"' OR chapa = '"+chapa+"' and Status = 'INATIVO'";           
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next()) {
                //JOptionPane.showMessageDialog(null, "Registro inativado encontrado");
                return true;
                
            } else {
                //JOptionPane.showMessageDialog(null, "Registro inativado nao encontrado");
                return false;
            }
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return false;
        }finally{
            conexao.desconectar();
        }  
    } 
    
    public boolean patrimovel_serie_chapa_nulos(String serial, String chapa)
    {
       if( serial.equals("0") && chapa.equals("0") ){
         return true;
       }else{
         return false;        
       }      
    }         
           
    public String pesquisarString(String tabela, String valorRetorno, int codigo){
        //retorna uma String (valorRetorno) do codigo passado como parametro
        conexao.conectar();
        sql = "select "+valorRetorno+" FROM "+tabela+" WHERE codigo = '"+codigo+"'";        
        //JOptionPane.showMessageDialog(null, sql);
        
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next())
               return conexao.rs.getString(valorRetorno);
            else
                return ""; 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return "";
        }finally{
            conexao.desconectar();
        }  
    }
    
    public int pesquisarInteiro(String tabela, String valorRetorno, String campoComparacao, String param)
    {
        //retorna o codigo da seção passada como parametro
        conexao.conectar(); 
        //somente o valor de comparação deve esta entre aspas simples, nome de tabela e campos não precisa
        sql = "SELECT "+valorRetorno+" FROM "+tabela+" WHERE '"+campoComparacao+"'" +"="+ param;        
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            conexao.rs.first();
            return conexao.rs.getInt(valorRetorno);
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return 0;
        }finally{
            conexao.desconectar();
        }  
    } 
    
    public boolean tabelaVazia(String tabela) 
     {
        conexao.conectar();      
        try {
            sql = "SELECT * FROM "+tabela+"";
            //JOptionPane.showMessageDialog(null, sql);
            conexao.ExecutarPesquisaSQL(sql);     

            if (!(conexao.rs.next())) {
                //JOptionPane.showMessageDialog(null, "A tabela: " + tabela + " esta vazia cadastre o primeiro registro!");
                return true; 
            } else {
                //JOptionPane.showMessageDialog(null, "A tabela: " + tabela + " contem " + conexao.resultset.getRow() + " registro(s)");
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "A pesquisa na tabela :" + tabela + " falhou!" + ex);
           return false;
        }finally{
            conexao.desconectar();
        }        
    }  
    
     public void apagarTabela(String tabela)
    {
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM "+tabela;            
            conexao.ExecutarAtualizacaoSQL(sql);   
       } finally {
            conexao.desconectar();
        }        
    }  
       
    public boolean duplicidadeDeCadastro(String tabela, String campoDeComparacao, String valorComparacao)
     {
        conexao.conectar();      
        try {
            //somente o valor de comparação deve esta entre aspas simples, nome de tabela e campos não precisa
            sql = "SELECT * FROM "+tabela+" WHERE "+campoDeComparacao+" = '"+valorComparacao+"'";  
            //JOptionPane.showMessageDialog(null, sql);
            conexao.ExecutarPesquisaSQL(sql);   
            if ((conexao.rs.next())) {
                return true; 
            } else {
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
           return false;
        }finally{
            conexao.desconectar();
        }        
    } 
    
     public boolean duplicidadeDeCadastroMemo(String tabela, String campoDeComparacao, String valorComparacao)
     {
        conexao.conectar();      
        try {
            //somente o valor de comparação deve esta entre aspas simples, nome de tabela e campos não precisa
            sql = "SELECT * FROM "+tabela+" WHERE "+campoDeComparacao+" = '"+valorComparacao+"' AND status = 'PROCESSANDO'";  
            //JOptionPane.showMessageDialog(null, sql);
            conexao.ExecutarPesquisaSQL(sql);   
            if ((conexao.rs.next())) {
                return true; 
            } else {
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
           return false;
        }finally{
            conexao.desconectar();
        }        
    } 
    
    public void passaCamposComEnter(JFrame form){
         //Colocando enter para pular de campo
         HashSet conj = new HashSet(form.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
         conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
         form.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj); 

    }    
    
    public void PreencherCombo(JComboBox cmb, String tabela, String campo)
    {
        //preenche a combobox ordenando pelo campo escolhido pra ser mostrado
        conexao.conectar();   
        sql="select "+campo+" from "+tabela+" where status ='ATIVO' ORDER BY "+campo;  
        conexao.ExecutarPesquisaSQL(sql);              
        try 
        {                       
            cmb.removeAllItems();
            while (conexao.rs.next()) {
                 cmb.addItem(conexao.rs.getString(campo));
            };    
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao preencher o combo de seções!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        } 
    }
    public void PreencherComboVariandoTipo(JComboBox cmb, String sql, String campoMostrar)
    {
        //preenche a combobox com opção de ordenação pela sql
        conexao.conectar();   
        conexao.ExecutarPesquisaSQL(sql);              
        try 
        {                       
            cmb.removeAllItems();
            while (conexao.rs.next()) {
                 cmb.addItem(conexao.rs.getString(campoMostrar));
            };    
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao preencher o combo variando o tipo!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        } 
    }
    
    public void LimparCombo(JComboBox cmb, String tabela, String campo)
    {
        conexao.conectar();  
        sql="select "+campo+" from "+tabela+" where codigo = 0";  
        conexao.ExecutarPesquisaSQL(sql);              
        try 
        {                       
            cmb.removeAllItems();
            while (conexao.rs.next()) {
                 cmb.addItem(conexao.rs.getString(campo));
            };    
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao preencher o combo de seções!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        } 
    }
    public boolean ConfirmouOperacao(String sConfirmacao, String sTitulo){
        /*se clicar no (x) para fechar ou escolher (Não) na caixa de dialogo não faz nada e fecha o dialogo e retorna false*/ 
        /* se clicar em Sim retorna true e executa o metodo solicitado
        A primeira string recebe a mensagem de pergunta tipo: Confirma o desejo de excluir o cliente?
        A segunda string recebe o título da caixa tipo: Excluindo cliente!
        */
   
        int sair = JOptionPane.showConfirmDialog(null, sConfirmacao,sTitulo,JOptionPane.YES_NO_OPTION);
        if(sair == JOptionPane.YES_OPTION){
            return true ;          
        }else{
            return false;
        } 
    }
    public void preencherComboComListaCampos(String tabela, JComboBox cmb, int qdeColunas, int colunaOmitida)
    {
        //objetivo: mostrar todos os campos com exceção do codigo e email
        String nomeColuna = "";
        int numeroColuna  = 0;
        //int qdeColunas    = 6;
        
        conexao.conectar(); 
        sql="select * from "+tabela+"";  
        conexao.ExecutarPesquisaSQL(sql);                             
        try 
        {     
            cmb.removeAllItems();
            ResultSetMetaData meta = conexao.rs.getMetaData(); 
            //os nomes dos campos seguem a sequencia de criacao no banco de dados ex:(codigo=1, nome=2, email=3, pr=4, simproc=5, sei=6, deptoid=7, status=8, ramail=9, obs=10)
            //int omitir = 3;  //refere-se ao campo email
            for(int i=2; i<colunaOmitida; i++)     //omitindo o campo 1 que é o codigo
            {
                //Mostrando os números de 2 até um número antes do número a ser omitido que é o 3 entao mostra apenas o 2 que o campo nome
                nomeColuna   =  meta.getColumnName(i);  //retorna o nome da coluna conforme o numero passado 
                numeroColuna++;                 
                cmb.addItem(nomeColuna);              
            }    
            //mostro os numeros de 4 ate 6 omitindo o 3
            for (int i = (colunaOmitida + 1); (i <= qdeColunas); i++){  
                nomeColuna   =  meta.getColumnName(i);  //retorna o nome da coluna conforme o numero passado          
                numeroColuna++;                 
                cmb.addItem(nomeColuna);
                }
        } catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Erro ao preencher o combo de seções!\nErro: " + ex.getMessage());
        }finally{
             conexao.desconectar();
        }        
       
    }
    
    public int somenteDigitos(String palavra) 
    {
        //Gerando uma lista de numeros utilizados a partir do nome da estação (SNJPGMC03 = 3) cadastrada
        String digitos = "";
        char[] letras  = palavra.toCharArray();
        for (char letra : letras) {
            if(Character.isDigit(letra)) {
                digitos += letra;
            }
        }
        return Integer.parseInt(digitos);
    } 

    public int buscarCodigoGenerico(String tabela, String campoTabelaParaComparar, String paramDeComparacao)
    {
        //retorna o codigo do registro tendo como parametro um campo string
        conexao.conectar();      
        sql = "SELECT codigo FROM "+tabela+" WHERE "+campoTabelaParaComparar+" = '"+paramDeComparacao+"'";           
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if ((conexao.rs.next())) {
                conexao.rs.first();
                return conexao.rs.getInt("codigo");
            } else {
                return 0;
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return 0;
        }finally{
            conexao.desconectar();
        }  
    }  
    
    public int buscarCodigoSecao(String nomeSecao)
    {
        //retorna o codigo da seção selecionada
        conexao.conectar();      
        sql = "SELECT codigo FROM tblsecoes WHERE nome = '"+nomeSecao+"'";           
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            conexao.rs.first();
            return conexao.rs.getInt("codigo");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return 0;
        }finally{
            conexao.desconectar();
        }  
    }
    
    public boolean campoVazio(JTextField txt){
        if( txt.getText().isEmpty()){
          return true;
        }else{
          return false;
        }
    }
     
    public String retornaDepto(String secao){
        //retorna o departamento da seção
        conexao.conectar();
        sql = "SELECT d.nome as departamento, s.nome FROM tbldepartamentos d, tblsecoes s WHERE s.deptoid=d.codigo AND s.nome = '"+secao+"'";
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next())
               return conexao.rs.getString("departamento");
            else
                return ""; 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return "";
        }finally{
            conexao.desconectar();
        }  
    } 
    
    public String retornaSecao(int codigo){
        //retorna o nome da seção atraves do codigo do patrimonio
        conexao.conectar();
        sql = "SELECT s.nome as secao FROM tblpatrimonios p, tblsecoes s WHERE p.secaoid=s.codigo AND p.codigo = '"+codigo+"'";
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next())
               return conexao.rs.getString("secao");
            else
                return ""; 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return "";
        }finally{
            conexao.desconectar();
        }  
    } 
    
    public String retornaStringPassandoInteiro(int pCodigo, String pCampo, String pTabela){
        //retorna o nome da seção atraves do codigo do patrimonio
        conexao.conectar();
        sql = "SELECT "+pCampo+" FROM "+pTabela+" WHERE codigo = '"+pCodigo+"'";
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next())
               return conexao.rs.getString(pCampo);
            else
                return ""; 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return "";
        }finally{
            conexao.desconectar();
        }  
    } 
    
     public int retornaQdeColunasDaTabela(String tabela){
        //RETORNA A QUANTIDADE DE COLUNAS DE UMA TABELA
        conexao.conectar();
        conexao.ExecutarPesquisaSQL("Select * from "+tabela); 
        if(conexao.rs != null){
            ResultSetMetaData meta=null;
            try{
                meta = conexao.rs.getMetaData();
                int numberOfColumns = meta.getColumnCount();
                return numberOfColumns;
            } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            }
        }
        return 0;
    }
    
    public ArrayList<String> recuperarNomesColunas(String tabela) {
        //RETORNA OS NOMES DAS COLUNAS DA TABELA

        conexao.conectar();
        conexao.ExecutarPesquisaSQL("Select * from " + tabela);
        
        //recuperando a qde de colunas da tabela
        int qdeCol = 0;
        qdeCol = retornaQdeColunasDaTabela(tabela);
        
        //criando o array que receberá a lista de nomes de colunas
        ArrayList<String> lstListaColunas = new ArrayList<>();
        
        if (conexao.rs != null) {
            ResultSetMetaData meta = null;
            try {
                meta = conexao.rs.getMetaData();               
                for(int i = 1; i <= qdeCol; i++){
                    //recuperando os nomes das colunas e add na lista
                    lstListaColunas.add(meta.getColumnName(i));
                }       
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            }
        }
        return lstListaColunas;
    }
    
    public void gerarExcel(JTable jTabela, String nomeDaPlanilha){
        //ESTE METODO FOI GERADO UTILIZANDO A BIBLIOTECA JXL COM ELE FICOU MAIS PRATICO SE ESTIVER COM UMA TABELA NO FORMULARIO    
        //selecionar o local para salvar o arquivo
        JFileChooser chooser            = new JFileChooser();
        FileNameExtensionFilter filtro  = new FileNameExtensionFilter("Arquivos do excel","xls");
        chooser.setFileFilter(filtro);
        chooser.setDialogTitle("Salvar arquivo");
        chooser.setMultiSelectionEnabled(false);
        chooser.setAcceptAllFileFilterUsed(false);
                        
        //gerando o cabeçalho
        gerarCabecalho();
        
        //se escolheu um local aceitavel continua
        if(chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
            //criando as listas
            List<JTable> lstNomeJTable     = new ArrayList<>();
            List<String> lstNomePlanilha   = new ArrayList<>();
            
            //adicionando os valores nas listas que serão utilizados no metodo de criação do xls
            lstNomeJTable.add(jTabela);               //nome da jTable do formulario
            lstNomePlanilha.add(nomeDaPlanilha);      //nome que vai na planilha
            
            //passando o caminho completo do nome do arquivo a ser gerado
            String nomeArquivo = chooser.getSelectedFile().toString().concat(".xls");
            try 
            {  
                //exportando para o excel
                ExportarExcel exc = new ExportarExcel(new File(nomeArquivo), lstNomeJTable, lstNomePlanilha);
                if(exc.exportar()){
                    JOptionPane.showMessageDialog(null,"Arquivo Excel gerado com sucesso!","Gerando arquivo excel", JOptionPane.INFORMATION_MESSAGE);
                }                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,"Erro ao gerar arquivo! "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }   
    
    public void gerarCabecalho(){
        try {
            
            String nomePlanilha ="plan";
            DataOutputStream out = new DataOutputStream(new FileOutputStream("arquivo"));
            WritableWorkbook   w = Workbook.createWorkbook(out);            
            WritableSheet      s = w.createSheet(nomePlanilha, 0);    

            // Cabeçalho
            String cabecalho[] = new String[3];
            cabecalho[0] = "CODIGO";
            cabecalho[1] = "TIPO";
            cabecalho[2] = "STATUS";

            // Write the Header to the excel file
            for (int k = 0; k < cabecalho.length; k++) {
                    Label label = new Label(0, 0, cabecalho[k]);
                    s.addCell(label);
                    WritableCell cell = s.getWritableCell(k, 0);

            }
        } 
          catch (IOException | WriteException e) {
        }
    }
    
    public boolean permissaoLiberada(){
        //Restringe o acesso ao USUARIO comum e libera para ADMINISTRADOR e SISTEMA que tem acesso a tudo
        if(nivelAcessoUsuario == 3){
             JOptionPane.showMessageDialog(null, "Atenção você não tem permissão para executar esta operação!","Contate o administrador!",2);
            return false;            
        }        
       return true; 
    }  
 
}   