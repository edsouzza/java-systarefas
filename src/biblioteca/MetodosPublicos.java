package biblioteca;

import Dao.DAOPatrimonio;
import static biblioteca.VariaveisPublicas.codigoDeptoSelecionado;
import static biblioteca.VariaveisPublicas.dataDoDia;
import static biblioteca.VariaveisPublicas.lstListaCampos;
import static biblioteca.VariaveisPublicas.nomeEstacao;
import static biblioteca.VariaveisPublicas.qdeColunas;
import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.temimpressorasAtivas;
import static biblioteca.VariaveisPublicas.temContratoImpressoraAtivo;
import static biblioteca.VariaveisPublicas.tabela;
import static biblioteca.VariaveisPublicas.totalRegs;
import static biblioteca.VariaveisPublicas.enviando;
import static biblioteca.VariaveisPublicas.rangeEstacao;
import static biblioteca.VariaveisPublicas.numeroEstacao;
import static biblioteca.VariaveisPublicas.idUsuarioInativado;
import static biblioteca.VariaveisPublicas.anoVigente;
import static biblioteca.VariaveisPublicas.contador;
import static biblioteca.VariaveisPublicas.lstListaStringsAuxiliar;
import static biblioteca.VariaveisPublicas.controlaNumemo;
import static biblioteca.VariaveisPublicas.isUsuario;
import static biblioteca.VariaveisPublicas.lstListaStrings;
import conexao.ConnConexao;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import controle.CtrlCliente;
import controle.CtrlNomeEstacao;
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import static jdk.nashorn.internal.objects.NativeString.toLowerCase;
import modelo.Cliente;
import modelo.NomeEstacao;
import modelo.Patrimonio;

public class MetodosPublicos {

    //para usar basta umabiblio.limparTodosCampos(jBoxDados) onde jBoxDados é o container em estao o JTextFields

    public static Object id;
    ConnConexao       conexao                   = new ConnConexao();    
    Connection        conn                      = null;  
    boolean           result                    = true;
    String            strRange                  = "";
    int               columnCount               = 0;
    String            nameColumn                = null;
    Date              dataFormatada             = null;
    Biblioteca        umaBiblio                 = new Biblioteca();
    DAOPatrimonio     umPatrimonioDAO           = new DAOPatrimonio();
    Cliente           umModCliente              = new Cliente();
    CtrlCliente       ctrCliente                = new CtrlCliente();
    NomeEstacao       umModeloNomeEstacao       = new NomeEstacao();
    CtrlNomeEstacao   umControleNomeEstacao     = new CtrlNomeEstacao();
    DateFormat        sdf                       = new SimpleDateFormat("dd/MM/yyyy");
    Date              dataDia                   = dataDoDia; 
    
    private Object    umMetodo;
    
    
    public void limparTodosCampos(Container container) {
        //LIMPA TODOS OS CAMPOS TEXTOS
        Component components[] = container.getComponents();
        for (Component component : components) {
            if (component instanceof JFormattedTextField) {
                JFormattedTextField field = (JFormattedTextField) component;
                field.setValue(null);
            } else if (component instanceof JTextField) {
                JTextField field = (JTextField) component;
                field.setText("");
            } else if (component instanceof JTextArea) {
                JTextArea field = (JTextArea) component;
                field.setText("");
            } else if (component instanceof JComboBox) {
                JComboBox field = (JComboBox) component;
                field.setSelectedIndex(-1);
                field.setEnabled(false);
            } else if (component instanceof JDateChooser) {
                JDateChooser field = (JDateChooser) component;
                field.setDate(null);
            } else if (component instanceof Container) {
                limparTodosCampos((Container) component);
            }
        }
    }      
            
public void configurarBotoes(JButton botao) {
    botao.setFont(new Font("TimesRoman", Font.BOLD, 12));
    botao.setCursor(new Cursor(12));

}

public boolean nomesSaoDiferentes(String nome1, String nome2) {
    if (nome1 == null && nome2 == null) return false; // os dois são nulos → iguais
    if (nome1 == null || nome2 == null) return true;  // um é nulo e o outro não → diferentes
    
    // compara ignorando espaços extras e maiúsculas/minúsculas
    return !nome1.trim().equalsIgnoreCase(nome2.trim());
}


public void desabilitarContainer(Container container, boolean enabled) {
    for (Component comp : container.getComponents()) {
        comp.setEnabled(enabled);
        if (comp instanceof Container) {
            desabilitarContainer((Container) comp, enabled);
        }
    }
}

public void desabilitarEdicaoJTextFields(Container container) {
    for (Component comp : container.getComponents()) {
        if (comp instanceof JTextField) {
            ((JTextField) comp).setEditable(false);
        } else if (comp instanceof Container) {
            desabilitarEdicaoJTextFields((Container) comp); // faz isso recursivamente
        }
    }
}

    
public int contarLinhasDoArquivoTXT(String caminho) 
{
    int linhas = 0;
    try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
        while (br.readLine() != null) {
            linhas++;
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return linhas;
}

    public void configurarCamposTextos(JTextField campoTexto) {
        campoTexto.setForeground(Color.blue);
        campoTexto.setFont(new Font("TimesRoman", Font.BOLD, 14));
        campoTexto.setDocument(new TudoMaiusculas());
    }
    
    public void configurarCamposTxtArea(JTextArea campoTexto) {
        campoTexto.setForeground(Color.blue);
        campoTexto.setFont(new Font("TimesRoman", Font.BOLD, 14));
        campoTexto.setDocument(new TudoMaiusculas());
    }
    
    public boolean Emicro(int pCodigo) {
        conexao.conectar();
        sql = "SELECT * FROM tblpatrimonios WHERE tipoid=1 OR tipoid=19 AND codigo = '"+ pCodigo +"'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar se o tipo do equipamento é micro pelo código! " + ex);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public void gravarPrimeiroNomeEstacao(String nomestacao, String nomeDepto){
        
        umModeloNomeEstacao.setNomestacao(nomestacao);
        umModeloNomeEstacao.setNumestacao(0);
        umModeloNomeEstacao.setDepto(nomeDepto);
        umModeloNomeEstacao.setStatus("DISPONIVEL");
        umControleNomeEstacao.salvarNomeEstacao(umModeloNomeEstacao);        
        
    }
    
    public String getMotivoPatrimonio(int pCodigo){
        conn = conexao.conectar();
        try {
            sql = "SELECT motivo FROM tblpatrimonios WHERE codigo = "+pCodigo+"";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return conexao.rs.getString("motivo");
            }else{
                return "";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tem motivos no registro da tabela, \n" + e + ", o sql passado foi \n" + sql);
            return "";
        } finally {
            conexao.desconectar();
        }
    }
    
    public ArrayList<String> getListaDeMotivosPatrimonios(){
        ArrayList<String> lista = new ArrayList();
        
        sql = "SELECT codigo, motivo from tblpatrimonios where tipoid = 3 and contrato='S' and empresaid > 0 and  status='ATIVO'";
 
        conn = conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);  
        
        try {          
              while (conexao.rs.next())
              {
                lista.add(conexao.rs.getString("codigo"));   
                lista.add(conexao.rs.getString("motivo"));  
              }            
              return lista;
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tem motivos no registro da tabela, \n" + e + ", o sql passado foi \n" + sql);
            return null;
        } finally {
            conexao.desconectar();
        }
    }       
    
    public boolean temDuplicidadeNaEdicao(String tabela, String campoTabela, String campoComparacao, Integer codigo) {
        conn = conexao.conectar();
        try {
            sql = "SELECT "+campoTabela+" FROM "+tabela+" WHERE "+campoTabela+" = "+campoComparacao+" and codigo <> "+codigo+"";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tem inativos na tabela, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean temDisponivel(String tabela) {
        conn = conexao.conectar();
        try {
            sql = "SELECT status FROM "+tabela+" WHERE status = 'DISPONIVEL'";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){               
                return true;
                
            }else{
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tem inativos na tabela, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
     public boolean temPraEnviar() {
        conn = conexao.conectar();
        try {
            sql = "SELECT status FROM TBLPATRIDEPTOS WHERE status = 'ENVIAR'";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tem equipamentos pra enviar na tabela, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
     
     public boolean temEnviados() {
        conn = conexao.conectar();
        try {
            sql = "SELECT status FROM TBLPATRIDEPTOS WHERE status = 'ENVIADO'";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tem enviados na tabela, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
     public boolean temEncerrados() {
        //Seleciona todos os regs com status ENCERRADO 
        conn = conexao.conectar();
        try {
            sql = "SELECT status FROM TBLPATRIDEPTOS WHERE status = 'ENCERRADO'";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tem encerrados na tabela, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
     
    public boolean soTemEncerrados() {
        //Seleciona todos os regs com status que não sejam = a ENCERRADO, se achar significa que a combo origem devera esta preenchida e ativa caso contrario deve estar desabilitada
        conn = conexao.conectar();
        try {
            sql = "SELECT status FROM TBLPATRIDEPTOS WHERE status != 'ENCERRADO' and status = 'ENVIAR'";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return false;
            }else{
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tem encerrados na tabela, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
     
   public boolean temMemorandoDeEnvio()
   {       
        //Seleciona as unidades que estao prontas para ENVIO a manutenção e retorna true se achar pelo menos uma unidade
        conn = conexao.conectar();
        sql = "SELECT codigo FROM tblpatrideptos where (DTENVIO is not null and memoenvio is not null)";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()){
                return true;
            }else{
                return false;
            }   
       } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tem encerrados na tabela, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
        
   public boolean temUnidadesParaEnvio()
   {       
        //Seleciona as unidades que estao prontas para ENVIO a manutenção e retorna true se achar pelo menos uma unidade
        conn = conexao.conectar();
        sql = "SELECT distinct destino, status FROM tblpatrideptos where (DTENVIO is not null and DTDEVOLUCAO is null) and memoenvio='N' and status='ENVIADO'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()){
                enviando=true;
                return true;
            }else{
                enviando=false;
                return false;
            }   
       } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tem encerrados na tabela, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }   
   
   public boolean temUnidadesParaDevolucao()
   {       
        //Seleciona as unidades que estao prontas para ENVIO a manutenção e retorna true se achar pelo menos uma unidade
        conn = conexao.conectar();
        sql = "SELECT distinct origem, status FROM tblpatrideptos where (DTENVIO is not null and DTDEVOLUCAO is not null) and memodevolucao='N' and status='ENCERRADO'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()){
                enviando=false;
                return true;
            }else{
                //enviando=true;
                return false;                
            }   
       } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tem encerrados na tabela, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
   
   public String retornaUnidadesParaDevolucao()
   {       
        //Seleciona as unidades que estao prontas para ENVIO a manutenção
        String unidadeEnvio = null;
        conn = conexao.conectar();
        sql = "SELECT distinct origem, status FROM tblpatrideptos where (DTENVIO is not null and DTDEVOLUCAO is null) and status='ENCERRADO' and memodevolucao='N'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                unidadeEnvio = conexao.rs.getString("origem");                    
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome da unidade de devolucao! " + ex);
            
        } finally {
            conexao.desconectar();
        }  
        return unidadeEnvio;
    }
   
   public ArrayList<String> listaUnidadesParaEnvio()
   {       
        //Seleciona as unidades que estao prontas para ENVIO a manutenção
        conn = conexao.conectar();
        sql = "SELECT distinct destino FROM tblpatrideptos where (DTENVIO is not null and DTDEVOLUCAO is null)";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            while (conexao.rs.next()) {
                lstListaStrings.add(conexao.rs.getString("origem"));                    
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome da seção! " + ex);
            
        } finally {
            conexao.desconectar();
        }  
        return lstListaStrings;
    }
   
   public ArrayList<String> listaUnidadesParaDevolucao()
   {       
        //Seleciona as unidades que estao prontas para ENVIO a manutenção
        conn = conexao.conectar();
        sql = "SELECT distinct origem FROM tblpatrideptos where DTENVIO is not null and DTDEVOLUCAO is null";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            while (conexao.rs.next()) {
                lstListaStrings.add(conexao.rs.getString("origem"));                    
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome da seção! " + ex);
            
        } finally {
            conexao.desconectar();
        }  
        return lstListaStrings;
    }
    
    public boolean TemRegsEnviadosOuEncerradosMemoNaoEmitidos() {
        //Seleciona todos os regs com status ENCERRADO mas que ainda não foram devolvidos atraves de memorando -> memodevolucao='N'
        conn = conexao.conectar();
        try {
            sql = "SELECT status FROM tblpatrideptos WHERE (status = 'ENVIADO' or status = 'ENCERRADO') and memodevolucao='N'";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){   
                return true;                
            }else{
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tem encerrados na tabela, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
     public boolean EstacaoAtivaEmPatrimonios(String sNome) {
        //Verifica se nome de estação esta ativa na TBLPATRIMONIOS
        conn = conexao.conectar();
        try {
            sql = "SELECT * FROM tblpatrimonios WHERE estacao = '"+sNome+"' and status = 'ATIVO'";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){   
                return true;                
            }else{
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tem encerrados na tabela, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean EInativoPorCodigo(String tabela, int pCod) {
        conn = conexao.conectar();
        try {
            sql = "SELECT codigo FROM "+tabela+" WHERE status = 'INATIVO' and codigo="+pCod+"";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se é inativo na tabela, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean temInativos(String tabela) {
        conn = conexao.conectar();
        try {
            sql = "SELECT status FROM "+tabela+" WHERE status = 'INATIVO'";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tem inativos na tabela, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }    
    
    public void indisponibilizarStatusNomeEstacao(String pEstacao) 
    {
        //Grava na tabela tblnomestacao diretamente sem criação de modelo etc. a alteração do status da estação para INDISPONIVEL
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblnomestacao SET status=? WHERE nomestacao=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "INDISPONIVEL");           
            pst.setString(2, pEstacao);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    }      
        
    public boolean verificarSePesquisaRetornouDados(String sql){

        conexao.conectar();
        boolean temDados = conexao.ExecutarPesquisaSQLComRetorno(sql);

        if (temDados) {
            //System.out.println("A pesquisa retornou resultados.");
            return true;
        } else {
            //System.out.println("Nenhum dado encontrado.");
            return false;
        }
    }       
              
    public void inativarImpressorasContrato(Patrimonio umPatrimonio) 
    {         
        conexao.conectar();
        try 
        { 
            sql =  "UPDATE tblpatrimonios "+
                    "SET motivo = COALESCE(motivo, '') || ASCII_CHAR(13) || ?, "+
                    "observacoes = COALESCE(observacoes, '') || ASCII_CHAR(13) || ?, "+
                    "status = 'INATIVO',  "+
                    "datainativacao = CURRENT_DATE  "+
                    "WHERE empresaid > 0 "+
                    "AND status = 'ATIVO' "+
                    "AND tipoid = 3";
            
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, umPatrimonio.getMotivo());       
            pst.setString(2, umPatrimonio.getObservacoes());
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de atualização inativando impressoras do contrato sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    }  
    
    public void indisponibilizarStatusNomeEstacaoTMP(String pEstacao) 
    {
        //Grava na tabela temporaria diretamente sem criação de modelo etc. a alteração do status da estação temporaria para INDISPONIVEL
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblnomestacaotmp SET status=? WHERE nomestacao=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "INDISPONIVEL");           
            pst.setString(2, pEstacao);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    }  
    
    public void DisponibilizarStatusNomeEstacaoTMP(String pEstacao) 
    {
        //Grava na tabela temporaria diretamente sem criação de modelo etc. a alteração do status da estação temporaria para INDISPONIVEL
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblnomestacaotmp SET status=? WHERE nomestacao=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "DISPONIVEL");           
            pst.setString(2, pEstacao);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    }  
    
    public void atualizarStatusParaBaixados(String numemo) 
    {
        conexao.conectar();
        try 
        {
            sql = "UPDATE TBLITENSMEMOTRANSFERIDOS SET status=? WHERE status=? AND numemo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "BAIXADO");           
            pst.setString(2, "PROCESSANDO");    
            pst.setString(3, numemo);    
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    }  
    
    public void atualizarStatusParaTransferidos(String numemo) 
    {
        conexao.conectar();
        try 
        {
            sql = "UPDATE TBLITENSMEMOTRANSFERIDOS SET status=? WHERE status=? AND numemo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "TRANSFERIDO");           
            pst.setString(2, "PROCESSANDO");    
            pst.setString(3, numemo);    
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    }  
    
    public void atualizarSerieChapaPatrimoniosEditadosETransferidos(String pNovaSeire, String pNovaChapa, String pSerieAtual) 
    {
        conexao.conectar();
        try 
        {
            sql = "UPDATE TBLITENSMEMOTRANSFERIDOS SET serie=?, chapa=? WHERE serie=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pNovaSeire);           
            pst.setString(2, pNovaChapa);    
            pst.setString(3, pSerieAtual);    
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    }  
    
    public void atualizarStatusDosMemosParaTransferidos(String numemo) 
    {
        conexao.conectar();
        try 
        {
            sql = "UPDATE TBLMEMOSTRANSFERIDOS SET status=? WHERE status=? AND numemo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "TRANSFERIDO");           
            pst.setString(2, "PROCESSANDO");  
            pst.setString(3, numemo);   
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    }  
    
    public String retornarObsDoRegistro(int iCodigo, String tabela){
        //retorna os dados do campo obs do registro indicado pelo codigo                
        conn = conexao.conectar();      
        try
        {  
            sql = "Select obs From "+tabela+" Where codigo = "+iCodigo+"";                       
            conexao.ExecutarPesquisaSQL(sql); 
            if(conexao.rs.next()){
               return conexao.rs.getString("obs"); 
            }else{
                return "";
            }                        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro a o pesquisar o campo obs na tabela, erro gerado : \n"+e+", o sql passado foi \n"+sql);
            return "";
       } finally {
            conexao.desconectar();
       }          
    }
    
    public String getDestinoMemorando(String sNumemo){               
        conn = conexao.conectar();      
        try
        {  
            sql = "SELECT distinct destino FROM TBLITENSMEMOTRANSFERIDOS Where numemo = '"+sNumemo+"'";                       
            conexao.ExecutarPesquisaSQL(sql); 
            if(conexao.rs.next()){
               return conexao.rs.getString("destino"); 
            }else{
                return "";
            }                        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro a o pesquisar o campo obs na tabela, erro gerado : \n"+e+", o sql passado foi \n"+sql);
            return "";
       } finally {
            conexao.desconectar();
       }          
    }
    
    public Integer getCodigoEmpresaAtualContratoImpressoras(){               
        //Metodo retorna o codigo da empresa atual do contrato de impressoras
        conn = conexao.conectar();      
        try
        {  
            String sqlEmpresaId = "SELECT codigo FROM tblempresa WHERE codigo=(select max(codigo) from tblempresa) and status = 'ATIVO'";                       
            conexao.ExecutarPesquisaSQL(sqlEmpresaId); 
            while(conexao.rs.next()){
               return conexao.rs.getInt("codigo"); 
            }                     
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro ao pesquisar o codigo da empresa de contrato de impressoras, erro gerado : \n"+e+", o sql passado foi \n"+sql);
            return 0;
       } finally {
            conexao.desconectar();
       }      
         return 0;
    }
    
    public void atualizarStatusParaEnviados(int iCodigo) 
    {
        //Atualiza apenas um campo de uma determinada tabela passada como parametro
        Date              dataDia   = dataDoDia; 
        SimpleDateFormat  sdf       = new SimpleDateFormat("dd.MM.yyyy"); 
        String            sObsAtual = retornarObsDoRegistro(iCodigo,"tblpatrideptos");
        
        conexao.conectar();
        try 
        {
            sql = "UPDATE TBLPATRIDEPTOS SET memoenvio=?, status=?, obs=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);      
            pst.setString(1, "S");  
            pst.setString(2, "DEVOLVER");  
            pst.setString(3, sObsAtual);   
            pst.setInt(4, iCodigo);    
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    } 
    
    public void atualizarStatusParaDevolvidos(int iCodigo) 
    {
        //Atualiza apenas um campo de uma determinada tabela passada como parametro
        Date              dataDia   = dataDoDia; 
        SimpleDateFormat  sdf       = new SimpleDateFormat("dd.MM.yyyy"); 
        String            sObsAtual = retornarObsDoRegistro(iCodigo,"tblpatrideptos");
        
        conexao.conectar();
        try 
        {
            sql = "UPDATE TBLPATRIDEPTOS SET dtdevolucao=?, memodevolucao=?, status=?, obs=? WHERE codigo=? and status='ENCERRADO'";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, sdf.format(dataDia));  
            pst.setString(2, "S");           
            pst.setString(3, "ENCERRADO");   
            pst.setString(4, sObsAtual);   
            pst.setInt(5, iCodigo);    
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    }  
    
    public void disponibilizarStatusNomeEstacao(String pEstacao) 
    {
        //Grava na tabela temporaria diretamente sem criação de modelo etc. a alteração do status da estação temporaria para INDISPONIVEL
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblnomestacao SET status=? WHERE nomestacao=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "DISPONIVEL");           
            pst.setString(2, pEstacao);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    }  
    
    public void disponibilizarStatusNomeEstacaoTMP(String pEstacao) 
    {
        //Grava na tabela temporaria diretamente sem criação de modelo etc. a alteração do status da estação temporaria para INDISPONIVEL
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblnomestacaotmp SET status=? WHERE nomestacao=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "DISPONIVEL");           
            pst.setString(2, pEstacao);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    }  
    
    public boolean EUsuario(String pNome){
        //retorna verdadeiro ou false se é um cliente usuario do sistem
        conn = conexao.conectar();
        sql = "SELECT nome FROM tblusuarios WHERE nome = '"+pNome+"'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                isUsuario = true;
                return true;               
            } else {
                 isUsuario = false;
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome da seção! " + ex);
            return false;
        } finally {
            conexao.desconectar();
        }                
    }
        
    public boolean EclienteUsuario(String pNome){
        //retorna verdadeiro ou false se é um cliente usuario do sistem
        conn = conexao.conectar();
        sql = "SELECT u.nome FROM tblusuarios u, tblclientes c WHERE u.nome = c.nome and u.nome = '" + pNome + "'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome da seção! " + ex);
            return false;
        } finally {
            conexao.desconectar();
        }                
    }
    
    public void atualizarSecaoDosPatrimoniosDoUsuarioNaNovaSecao(int idDaSecao, int idCliente)
    {
        //ATUALIZA TODOS OS PATRIMONIOS DE UM COLABORADOR COM A NOVA SEÇÃO 
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblpatrimonios SET secaoid=? WHERE clienteid=? AND status=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1, idDaSecao);    
            pst.setInt(2, idCliente);
            pst.setString(3, "ATIVO");
            pst.executeUpdate();             
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }               
    }   
    
    public void atualizarPatrimoniosParaSemUsuarioDaSecaoDoColaboradorTransferido(int idSemUsuarioDaSecao, int idUsuario)
    {
        //ATUALIZA CLIENTEID DE TODOS OS PATRIMONIOS DE UM COLABORADOR QUE FOI TRANSFERIDO PARA OUTRO SETOR SEM LEVAR OS EQUIPAMENTOS
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblpatrimonios SET clienteid=? WHERE clienteid=? AND status=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1, idSemUsuarioDaSecao);           
            pst.setInt(2, idUsuario);
            pst.setString(3, "ATIVO");
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }               
    }   
    
    public void atualizarPatrimoniosParaSemUsuarioDaSecaoDoColaboradorInativado(int idSemUsuarioDaSecao,int idUsuario)
    {
        //ATUALIZA TODOS OS PATRIMONIOS DE UM COLABORADOR INATIVADO PARA ( SEM USUARIO DA SUA ULTIMA SEÇÃO )
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblpatrimonios SET clienteid=? WHERE clienteid=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1, idSemUsuarioDaSecao);           
            pst.setInt(2, idUsuario);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
        //INATIVANDO USUÁRIO SE ESTE CLIENTE TB TIVER O ACESSO DESSE TIPO(COMO USUARIO DO SISTEMA)
        inativarUsuarioSeForClienteColaboradorInativado(idUsuarioInativado);
        
    }   
    
    public void alterarSecaoUsuarioSeForAlteradaSecaoDoClienteColaborador(int idSecao, int idUsuario)
    {
        //ATUALIZA O ID DA SECAO CASO SEJA ALTERADA A SEÇÃO DO CLIENTE/USUARIO DO SISTEMA
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblUsuarios SET secaoid=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);          
            pst.setInt(1, idSecao);           
            pst.setInt(2, idUsuario);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }        
    }   
    
    public void alterarNomeUsuarioSeForAlteradoNomeColaborador(String sNome, int idUsuario)
    {
        //ATUALIZA O NOME DO USUARIO CASO SEJA ALTERADO O NOME DO COLABORADOR
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblUsuarios SET nome=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, sNome);             
            pst.setInt(2, idUsuario);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de alteracao sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }        
    }   
    
    public void alterarRFUsuarioSeForAlteradoRFColaborador(String sRF, int idUsuario)
    {
        //ATUALIZA O RF DO USUARIO CASO SEJA ALTERADO O RF DO COLABORADOR
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblUsuarios SET rf=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);      
            pst.setString(1, sRF);           
            pst.setInt(2, idUsuario);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de alteracao sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }        
    }   
    
    public void alterarSecaoClienteSeForAlteradaSecaoDoUsuario(int idSecao, int idUsuario)
    {
        //ATUALIZA O ID DA SECAO CASO SEJA ALTERADA A SEÇÃO DO CLIENTE/USUARIO DO SISTEMA
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblClientes SET secaoid=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1, idSecao);           
            pst.setInt(2, idUsuario);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }        
    }   
    
    public void inativarUsuarioSeForClienteColaboradorInativado(int idUsuario)
    {
        //INATIVA UM USUARIO SE ELE FOR INATIVADO COMO CLIENTE COLABORADOR
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblUsuarios SET status=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "INATIVO");           
            pst.setInt(2, idUsuario);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }        
    }   
    
    public void inativarRegistrosPeloCodigo(int iCodigo)
    {        
        conexao.conectar();
        try 
        {
            sql = "UPDATE TBLPATRIMONIOS SET status=? WHERE codigo = ?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "INATIVO");    
            pst.setInt(2, iCodigo);    
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }        
    }   
    
    public void reativarClienteSeForUsuarioReativado(int idUsuario)
    {
        //INATIVA UM USUARIO SE ELE FOR INATIVADO COMO CLIENTE COLABORADOR
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblClientes SET status=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "ATIVO");           
            pst.setInt(2, idUsuario);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }        
    }   
    
    public void reativarUsuarioSeForClienteReativado(int idUsuario)
    {
        //INATIVA UM USUARIO SE ELE FOR INATIVADO COMO CLIENTE COLABORADOR
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblUsuarios SET status=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "ATIVO");           
            pst.setInt(2, idUsuario);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }        
    }   
    
    public boolean atualizarTabelasViaSql(String sqlUpdate)
    {         
        conn = conexao.conectar();
        try {            
            conexao.ExecutarAtualizacaoSQL(sqlUpdate);  
            return true;          
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar executar atualizações via SQL, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }          
    }   
    
    public void cancelarAtualizacaoStatusEstacaoNaoCad(String pEstacao) 
    {
        //Grava na tabela temporaria diretamente sem criação de modelo etc.
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblnomestacaotmp SET status=? WHERE nomestacao=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "DISPONIVEL");           
            pst.setString(2, pEstacao);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    }   
    
    private boolean salvarNomeEstacao(String pEstacao, int pNumestacao) 
    {
        //Grava na tabela temporaria diretamente sem criação de modelo etc.
        conexao.conectar();
        try 
        {
            sql = "INSERT INTO tblnomestacaotmp (depto, nomestacao, numestacao, status) VALUES (?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "CGGM");
            pst.setString(2, pEstacao);
            pst.setInt(3, pNumestacao);            
            pst.setString(4, "DISPONIVEL");
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
        return false;
    }   
    
    public void popularTabelaTMPNaoCadastrados()
    {
        //Esse procedimento serve apenas para popular a tabela TBLNOMESTACAOTMP que são nomes que não estão cadastrados na tabela de patrimonio e estão disponivel
        //quando acabarem esses nomes o sistema deverá cadastrar automaticamento um novo nome. Resumo : todos esses nomes estarao disponiveis e tambem os que forem
        //inativados
        
        String snomestacao = "";
        String sqlNumeros = "SELECT numestacao FROM tblnomestacao";   
        
        java.util.List<String> numerosestacoes = new ArrayList<>(); 
        conexao.conectar();

        try 
        {        
                conexao.ExecutarPesquisaSQL(sqlNumeros);  

                while (conexao.rs.next())
                {            
                    numerosestacoes.add(conexao.rs.getString("numestacao"));                           
                }

                //criando uma lista completa de numeros 1 a 140
                java.util.List<Integer> listaCompleta = new ArrayList<Integer>();
                for(int x=1; x < 139; x++){
                    listaCompleta.add(x);
                }

                //System.out.println("LISTA COMPLETA");
                for(Integer c : listaCompleta){

                     //System.out.println(c);
                }                    
                /*==============================================================================================*/

                // Convertendo lista para inteiros
                java.util.List<Integer> listaInteiros = new ArrayList<Integer>();
                for (int i = 0; i < numerosestacoes.size(); i++) {
                        listaInteiros.add(Integer.parseInt(numerosestacoes.get(i)));
                }   
                //Ordenando a lista de inteiros
                Set ordenaInteiros = new TreeSet(listaInteiros);
                //System.out.println("LISTA DOS CADASTRADOS");
                //System.out.println(ordenaInteiros);   

                /*==============================================================================================*/

                //Mostrar em uma terceira lista (destino) apenas os numeros que não se repetem entre elas
                java.util.List<Integer> destino = listaCompleta;

                destino.removeAll(listaInteiros);

                //System.out.println("LISTA SOMENTE DOS QUE NAO FORAM CADASTRADOS");
                for(Integer numeroEstacao : destino){                         

                    //System.out.println("PGMCGGMC"+numeroEstacao);
                    //System.out.println(numeroEstacao);

                    snomestacao = "PGMCGGMC"+numeroEstacao;
                    //so grava se o nome não constar na tabela
                    if(!temDuplicidadeDeCadastro("tblnomestacaotmp", "nomestacao", snomestacao))
                    {
                       salvarNomeEstacao(snomestacao, numeroEstacao);
                    }
                }
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar números de estações, \n" + e + ", o sql passado foi \n" + sqlNumeros);

        } finally {
            conexao.desconectar();
        }

    }    
        
    public boolean TipoTemClientesVirtuais(int pCod) {
        conn = conexao.conectar();
        try {
            sql = "SELECT c.*, t.* FROM tblclientes c, tbltipos t WHERE c.Tipovirtualid = t.codigo AND t.codigo =  '"+pCod+"'";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return true;                
            }else{
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tipo tem clientes virtuais cadastrados, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean temNomestacaoDisponivelTMP() {
        conn = conexao.conectar();
        try {
            sql = "SELECT status FROM TBLNOMESTACAOTMP WHERE status = 'DISPONIVEL'";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return true;                
            }else{
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tem estações disponiveis na tabela temporária, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean nomeEstacaoCadastrado(String estacao) {
        conn = conexao.conectar();
        try {
            sql = "SELECT nomestacao FROM TBLNOMESTACAO";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tem inativos na tabela, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean temMicros() {
        conn = conexao.conectar();
        try {
            sql = "SELECT codigo FROM tblpatrimonios WHERE tipoid=1";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se tem micros na tabela, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }

    public int retornarIdSecao(String pNomeCliente) {
        conn = conexao.conectar();
        sql = "SELECT secaoid FROM tblclientes WHERE nome = '"+pNomeCliente+"'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getInt("secaoid");
            } else {
                return 0;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar recuperar a quantidade de registros ativos da tabela "+tabela+"" + ex);
            return 0;
        } finally {
            conexao.desconectar();
        }
    }
    
    public int qdeRegistros(String tabela) {
        conn = conexao.conectar();
        sql = "select count(codigo) as total from " +tabela;
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getInt("total");
            } else {
                return 0;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar recuperar a quantidade de registros ativos da tabela "+tabela+"" + ex);
            return 0;
        } finally {
            conexao.desconectar();
        }
    }

    public int qdeRegistrosATIVOS(String tabela) {
        conn = conexao.conectar();
        sql = "select count(codigo) as total from "+tabela+" where status='ATIVO'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getInt("total");
            } else {
                return 0;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar recuperar a quantidade de registros ativos da tabela "+tabela+"" + ex);
            return 0;
        } finally {
            conexao.desconectar();
        }
    }

    public int qdeRegistrosINATIVOS(String tabela) {
        conn = conexao.conectar();
        sql = "select count(codigo) as total from "+tabela+" where status='INATIVO'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getInt("total");
            } else {
                return 0;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar recuperar a quantidade de registros inativos da tabela "+tabela+"" + ex);
            return 0;
        } finally {
            conexao.desconectar();
        }
    }

    public String mostrarTituloDoFormulario() {
        int qdeRegsATIVOS = qdeRegistrosATIVOS(tabela);
        int qdeRegs       = qdeRegistros(tabela);
        //substring retira o TBL da palavra

        String nomeTabela   = tabela.substring(3);
        nomeTabela          = nomeTabela.toLowerCase();

        if (qdeRegsATIVOS == 1) {
            return "Gerenciamento de " + primeiraLetraMaiuscula(nomeTabela) + " com " + qdeRegs + " registro(s) cadastrado(s) e " + qdeRegsATIVOS + " ativo";
        } else {
            return "Gerenciamento de " + primeiraLetraMaiuscula(nomeTabela) + " com " + qdeRegs + " registros cadastrados e " + qdeRegsATIVOS + " ativos";
        }

    }
                      
    public void novaLinhaComData(JTextArea txt) {
        String textoAtual = txt.getText().trim(); // remove espaços e quebras de linha do início e fim

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dataDia = dataDoDia; 

        if (textoAtual.isEmpty()) { 
            txt.setText(df.format(dataDia) + " : Cadastro inicial.");
        } else {
            txt.setText(txt.getText() + "\n" + df.format(dataDia) + " : ");
        }
    }
    
    public void novaLinhaComDataTexto(JTextArea txt, String destino){
        //se for a primeira digitação nao criar linha nova com data caso contrario criar
        if( txt.getText().equals("") ){
            DateFormat df     = new SimpleDateFormat("dd/MM/yyyy");
            Date dataDia      = dataDoDia; 
            String textoAtual = txt.getText();
            txt.setText(textoAtual+df.format(dataDia)+" : Cadastro inicial.");
        }else{
            DateFormat df     = new SimpleDateFormat("dd/MM/yyyy");
            Date dataDia      = dataDoDia; 
            String textoAtual = txt.getText();
            if(contador == 1){
               txt.setText(textoAtual+"\n"+df.format(dataDia)+" : Enviado para manutencao na "+destino+"."); 
            }else{
               txt.setText(textoAtual+"\n"+df.format(dataDia)+" : Devolvido para "+destino+" sua unidade de origem .");
            }
            contador=0;
        }
    }

    public String mostrarTituloDoFormularioParaInativos() {
        int qdeRegsINATIVOS = qdeRegistrosINATIVOS(tabela);
        int qdeRegs = qdeRegistros(tabela);
        //substring retira o TBL da palavra

        String nomeTabela = tabela.substring(3);
        nomeTabela = nomeTabela.toLowerCase();

        if (qdeRegsINATIVOS == 1) {
            return "Gerenciamento de " + primeiraLetraMaiuscula(nomeTabela) + " inativos com " + qdeRegsINATIVOS + " registro inativo";
        } else {
            return "Gerenciamento de " + primeiraLetraMaiuscula(nomeTabela) + " inativos com " + qdeRegsINATIVOS + " registros";
        }

    }
    
     //public String getValorCampoUltimoCodigo(String tabela, String campo, String campoAgrupado) {                  
     public String getValorCampoUltimoCodigo(String tabela, String campo) {                  
        conn = conexao.conectar();      
        try
        {  
            //sql = "Select "+campo+" From "+tabela+" Where codigo In (Select Max(codigo) From "+tabela+" Group By "+campoAgrupado+")";                       
            sql = "Select "+campo+" From "+tabela+" Where codigo In (Select Max(codigo) From "+tabela+")";                       
            conexao.ExecutarPesquisaSQL(sql); 
            if(conexao.rs.next()){
               return conexao.rs.getString(campo); 
            }else{
                return "";
            }                        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro a o pesquisar a tabela, erro gerado : \n"+e+", o sql passado foi \n"+sql);
            return "";
       } finally {
            conexao.desconectar();
       }        
    }  
     
     public String getNomeSecao(String pNomeCliente) {                  
        conn = conexao.conectar();      
        try
        {  
            sql = "SELECT c.secaoid, s.nome FROM tblclientes c, tblsecoes s WHERE c.secaoid=s.codigo AND c.nome = '"+pNomeCliente+"' ";                       
            conexao.ExecutarPesquisaSQL(sql); 
            if(conexao.rs.next()){
               return conexao.rs.getString("nome"); 
            }else{
                return "";
            }                        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro a o pesquisar a tabela, erro gerado : \n"+e+", o sql passado foi \n"+sql);
            return "";
       } finally {
            conexao.desconectar();
       }        
    }          
     
    public int mostrarUltimoCodigo(String tabela) {
        conn = conexao.conectar();
        sql = "select max(codigo) as ultimoCodigo from " + tabela;
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getInt("ultimoCodigo");
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao mostrar o último código! " + ex);
            return 0;
        } finally {
            conexao.desconectar();
        }
    }

    public int mostrarProximoCodigo(String tabela) {
        conn = conexao.conectar();
        sql = "select max(codigo) as proximoCodigo from " + tabela;
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getInt("proximoCodigo") + 1;
            } else {
                return 0;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar encontra o próximo código para exibição! " + ex);
            return 0;
        } finally {
            conexao.desconectar();
        }
    }
    
    public int gerarCodigoID(String tabela) {
        conn = conexao.conectar();
        sql = "select max(codigo) as proximoCodigo from " +tabela;
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getInt("proximoCodigo") + 1;
            } else {
                return 0;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar encontrar o próximo código para exibição! " + ex);
            return 0;
        } finally {
            conexao.desconectar();
        }
    }

    public boolean registroEncontrado(String tabela, String campoTabelaParaComparar, String paramDeComparacao) {
        //pesquisa se um registro existe na tabela passando como parametro uma string 
        conn = conexao.conectar();
        sql = "SELECT " + campoTabelaParaComparar + " FROM " + tabela + " WHERE " + campoTabelaParaComparar + " = '" + paramDeComparacao + "'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar se o registro esta cadastrado na tabela! " + ex);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
     public void setarStatusDosItensDoMemorando(String sStatus, String sNumemo) {
        conn = conexao.conectar();
        sql = "UPDATE TBLITENSMEMOTRANSFERIDOS SET status = '"+sStatus+"' WHERE numemo = '"+sNumemo+"' ";
        conexao.ExecutarAtualizacaoSQL(sql);
        try {
            //JOptionPane.showMessageDialog(null, "Status atualizados com sucesso!");            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível reativar o registro!\nErro:" + e + ", o sql passado foi \n" + sql);
        } finally {
            conexao.desconectar();
        }
    }
    
    public ArrayList<String> getDadosMemorando(String pNumemo)
    {
//        txtORIGEM.setText("CGGM/INFO");
//        txtDESTINO.setText("BAIXA");
//        txtASSUNTO.setText("BAIXA DE EQUIPAMENTOS INSERVIVEIS");
//        txtOBSERVACAO.setText("Todos os equipamentos foram dados como inserviveis."); 
        
        conn = conexao.conectar();
        sql = "SELECT i.numemo, i.origem, i.destino, m.assunto, m.observacao FROM TBLITENSMEMOTRANSFERIDOS i, TBLMEMOSTRANSFERIDOS m WHERE i.numemo=m.numemo AND m.numemo = '"+pNumemo+"' ";
        conexao.ExecutarPesquisaSQL(sql);
        
        ArrayList listaDados = new ArrayList();        
        
        try {
            if (conexao.rs.next()) {
                listaDados.add(conexao.rs.getString("numemo"));
                listaDados.add(conexao.rs.getString("origem"));
                listaDados.add(conexao.rs.getString("destino"));
                listaDados.add(conexao.rs.getString("assunto")); 
                listaDados.add(conexao.rs.getString("observacao"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar carregar os dados do memorando selecionado! " + ex);
        } finally {
            conexao.desconectar();
        }     
        return listaDados;
    }

    public void excluirMemorandoSemItens()
    {
        String numemo = getMemorandoSemItens();
        deletarMemorandoSemItens(numemo);    
        //JOptionPane.showMessageDialog(null, "O Memorando sem ítens cadastrados foi excluído com sucesso!"); 
    }       
    
    public String getMemorandoSemItens() 
    {
        //pesquisa se um registro existe na tabela passando como parametro uma string 
        conn = conexao.conectar();
        sql = "SELECT numemo FROM TBLMEMOSTRANSFERIDOS WHERE numemo not in (select numemo from TBLITENSMEMOTRANSFERIDOS)";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getString("numemo");
            } else {
                return "";
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar se o registro esta cadastrado na tabela! " + ex);
            return "";
        } finally {
            conexao.desconectar();
        }        
        
    }

    public void deletarMemorando(String sNumemo)
    {
        conn = conexao.conectar();
        sql = "DELETE FROM TBLMEMOSTRANSFERIDOS WHERE numemo = '"+sNumemo+"' ";
        conexao.ExecutarAtualizacaoSQL(sql);
        try {
            //JOptionPane.showMessageDialog(null, "Memorando excluído com sucesso!");            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir o memorando!\nErro:" + e + ", o sql passado foi \n" + sql);
        } finally {
            conexao.desconectar();
        }
    }
    
    public void deletarMemorandoProcessando()
    {
        conn = conexao.conectar();
        sql = "DELETE FROM TBLMEMOSTRANSFERIDOS WHERE status = 'PROCESSANDO' ";
        conexao.ExecutarAtualizacaoSQL(sql);
        try {                      
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir o memorando!\nErro:" + e + ", o sql passado foi \n" + sql);
        } finally {
            conexao.desconectar();
        }
    }
    
    public void deletarMemorandoSemItens(String sNumemo)
    {
        conn = conexao.conectar();
        sql = "DELETE FROM TBLMEMOSTRANSFERIDOS WHERE numemo = '"+sNumemo+"' ";
        conexao.ExecutarAtualizacaoSQL(sql);
        try {
            //JOptionPane.showMessageDialog(null, "Memorando excluído com sucesso!");            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir o memorando!\nErro:" + e + ", o sql passado foi \n" + sql);
        } finally {
            conexao.desconectar();
        }
    }
    
    public void deletarItensDoMemorando(String sNumemo)
    {
        conn = conexao.conectar();
        sql = "DELETE FROM TBLITENSMEMOTRANSFERIDOS WHERE numemo = '"+sNumemo+"' ";
        conexao.ExecutarAtualizacaoSQL(sql);
        try {
            //JOptionPane.showMessageDialog(null, "Memorando excluído com sucesso!");            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir o memorando!\nErro:" + e + ", o sql passado foi \n" + sql);
        } finally {
            conexao.desconectar();
        }
    }
    
    public void deletarItensDoMemorandoProcessando()
    {
        conn = conexao.conectar();
        sql = "DELETE FROM TBLITENSMEMOTRANSFERIDOS WHERE status = 'PROCESSANDO' ";
        conexao.ExecutarAtualizacaoSQL(sql);
        try {         
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir o memorando!\nErro:" + e + ", o sql passado foi \n" + sql);
        } finally {
            conexao.desconectar();
        }
    }
    
/**
 * Retorna todos os dados inseridos em uma lista de strings convertidos para arrays de String.
 * Pode preencher automaticamente campos faltantes ou descartar linhas inválidas, conforme configuração.
 * Se achar que os dados gerados no txt contem linhas diferentes uma das outras use o metodo assim : umMetodo.retornarTodosDadosInseridosNaListaDeStrings(lstListaStrings, false); 
 * Nesse caso o metodo vai igualar a qde de campos com um campo vazio caso contrario use umMetodo.retornarTodosDadosInseridosNaListaDeStrings(lstListaStrings, true); 
 * Nesse caso nada será feito
 * @param nomeLista A lista de strings a ser processada.
 * @param preencherCamposFaltantes Se true, preenche campos faltantes com ""; se false, descarta linhas inválidas.
 * @return Uma lista contendo arrays de String com os dados separados.
 */
public java.util.List<String[]> retornarTodosDadosInseridosNaListaDeStrings(java.util.List<String> nomeLista, boolean preencherCamposFaltantes) {
    java.util.List<String[]> listaDeDados = new ArrayList<>();
    int qdeCampos = -1; // ainda desconhecido
    int linhasCorrigidas = 0;
    int linhasDescartadas = 0;

    for (String linha : nomeLista) {
        if (linha != null && !linha.trim().isEmpty()) {
            linha = linha.trim();
            String[] dadosOriginais = linha.split(";");

            if (qdeCampos == -1) {
                // Descobre a quantidade de campos pela primeira linha válida
                qdeCampos = dadosOriginais.length;
            }

            if (dadosOriginais.length == qdeCampos) {
                // Linha perfeita
                listaDeDados.add(dadosOriginais);
            } else {
                if (preencherCamposFaltantes) {
                    String[] dadosCorrigidos = new String[qdeCampos];
                    for (int i = 0; i < qdeCampos; i++) {
                        if (i < dadosOriginais.length) {
                            dadosCorrigidos[i] = dadosOriginais[i];
                        } else {
                            dadosCorrigidos[i] = ""; // Preenche faltantes
                        }
                    }
                    listaDeDados.add(dadosCorrigidos);
                    linhasCorrigidas++;
                    System.out.println("Linha corrigida: " + linha);
                } else {
                    linhasDescartadas++;
                    System.err.println("Linha descartada (quantidade errada de campos): " + linha);
                }
            }
        }
    }

//    System.out.println("Processamento finalizado:");
//    System.out.println("Linhas válidas adicionadas: " + listaDeDados.size());
//    System.out.println("Linhas corrigidas: " + linhasCorrigidas);
//    System.out.println("Linhas descartadas: " + linhasDescartadas);

    return listaDeDados;
}     
    
    public boolean excluirItemDoMemoAtualPeloCodigo(int pCod)
    {
        //Excluir o ítem selecionado do memorando em curso
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM TBLITENSMEMOTRANSFERIDOS WHERE codigo = "+pCod+"";           
            conexao.ExecutarAtualizacaoSQL(sql);         
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
       }        
    } 
    
    public void deletarItensDoMemorandoPeloCodigo(int iCod)
    {
        conn = conexao.conectar();
        sql = "DELETE FROM TBLITENSMEMOTRANSFERIDOS WHERE codigo = '"+iCod+"' ";
        conexao.ExecutarAtualizacaoSQL(sql);
        try {
            //JOptionPane.showMessageDialog(null, "Memorando excluído com sucesso!");            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir o memorando!\nErro:" + e + ", o sql passado foi \n" + sql);
        } finally {
            conexao.desconectar();
        }
    }
    
    public void reativarRegistro(String sTabela, int iCodigo) {
        conn = conexao.conectar();
        sql = "UPDATE " + sTabela + " SET status='ATIVO' WHERE codigo = "+iCodigo+" ";
        conexao.ExecutarAtualizacaoSQL(sql);
        try {
            JOptionPane.showMessageDialog(null, "Registro reativado com sucesso!");            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível reativar o registro!\nErro:" + e + ", o sql passado foi \n" + sql);
        } finally {
            conexao.desconectar();
        }
    }
             
    public void atualizarChapasPelasSeriesTXT(String sChapa, String sObs, String sSerie) 
    {
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblPatrimonios SET chapa=?, observacoes=? WHERE serie=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, sChapa);           
            pst.setString(2, sObs);
            pst.setString(3, sSerie);
            pst.executeUpdate(); 
            pst.close();               
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de atualização das chapas, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    }  
    
    public void atualizarESTACOESCOMZERO() 
    {
        conexao.conectar();
        try 
        {
            sql = "UPDATE TBLNOMESTACAO SET status = 'DISPONIVEL' WHERE nomestacao LIKE '%000'";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);            
            pst.executeUpdate(); 
            pst.close();               
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de atualização das chapas, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    }  
         
    public int getCodigoPassandoString(String tabela, String nomeCampo, String sParam) {
        //pesquisar codigo de um campo passando seu nome
        int id = 0;
        conn = conexao.conectar();        
        sql = "SELECT codigo FROM "+tabela+" WHERE "+nomeCampo+" = '" +sParam+ "' ";
        conexao.ExecutarPesquisaSQL(sql);        
        try {
            if(conexao.rs.next()){
                id = conexao.rs.getInt("codigo");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa do código! " + ex);
        }
        return id;
    }
    
    public int getCodigoPassandoMaisDeUmParametroString(String tabela, String nomeCampo1, String sParam1, String nomeCampo2,  String sParam2) {
        //pesquisar codigo de um campo passando seu nome
        int id = 0;
        conn = conexao.conectar();        
        sql = "SELECT codigo FROM "+tabela+" WHERE "+nomeCampo1+" = '" +sParam1+ "' AND  "+nomeCampo2+" = '" +sParam2+ "' ";
        conexao.ExecutarPesquisaSQL(sql);        
        try {
            if(conexao.rs.next()){
                id = conexao.rs.getInt("codigo");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa do código! " + ex);
        }
        return id;
    }

    public String getStringPassandoCodigo(String tabela, String valorRetorno, int codigo) {
        //retorna uma String como(valorRetorno) do codigo passado como parametro da tabela entrada
        conn = conexao.conectar();
        sql = "select * FROM " + tabela + " WHERE codigo = '" + codigo + "'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()){
                return conexao.rs.getString(valorRetorno);
            }else{
                return null;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar encontrar uma string passando um valor de código! " + ex);
            return null;
        } finally {
            conexao.desconectar();
        }
    }
    
    public String getStringPassandoString(String tabela, String valorRetorno, String campoComparacao, String valorComparacao) {
        //retorna uma String como(valorRetorno) passando uma string como parametro
        conn = conexao.conectar();
        sql = "select " + valorRetorno + " FROM " + tabela + " WHERE " + campoComparacao + " = '" + valorComparacao + "'";
        //JOptionPane.showMessageDialog(null, sql);
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()){
                return conexao.rs.getString(valorRetorno);
            }else{
                return null;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar encontrar uma string passando um valor de código! " + ex);
            return null;
        } finally {
            conexao.desconectar();
        }
    }

    public int pesquisarInteiro(String tabela, String valorRetorno, String campoComparacao, String param) {
        //retorna o codigo da seção passada como parametro
        conn = conexao.conectar();
        //somente o valor de comparação deve estar entre aspas simples, nome de tabela e campos não precisa
        sql = "SELECT " + valorRetorno + " FROM " + tabela + " WHERE '" + campoComparacao + "'" + "=" + param;
        conexao.ExecutarPesquisaSQL(sql);
        try {
            conexao.rs.first();
            return conexao.rs.getInt(valorRetorno);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar encontrar um código passando um valor de string! " + ex);
            return 0;
        } finally {
            conexao.desconectar();
        }
    }
    
    public String pesquisarStringPassandoCodigo(String tabela, String valorRetorno, int codigo) {
        //retorna uma string passando codigo 
        conn = conexao.conectar();
        //somente o valor de comparação deve estar entre aspas simples, nome de tabela e campos não precisa
        sql = "SELECT " + valorRetorno + " FROM " + tabela + " WHERE codigo =" + codigo;
        conexao.ExecutarPesquisaSQL(sql);
        try {
            conexao.rs.first();
            return conexao.rs.getString(valorRetorno);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar encontrar um código passando um valor de string! " + ex);
            return null;
        } finally {
            conexao.desconectar();
        }
    }    
    
    public boolean tabelaEstaVazia(String tabela) {
        conn = conexao.conectar();
        try {
            sql = "SELECT * FROM "+tabela+"";
            //JOptionPane.showMessageDialog(null, sql);
            conexao.ExecutarPesquisaSQL(sql);

            if (conexao.rs.next()) {
                //JOptionPane.showMessageDialog(null, "A tabela: " + tabela + " esta vazia cadastre o primeiro registro!");
                return false;
            } else {
                //JOptionPane.showMessageDialog(null, "A tabela: " + tabela + " contem " + conexao.resultset.getRow() + " registro(s)");
                return true;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se a tabela " + tabela + " esta vazia!" + ex);
            return false;
        } finally {
            conexao.desconectar();
        }
    }

    public void limparTabela(String tabela) {
        conn = conexao.conectar();
        sql = "DELETE FROM " + tabela;
        conexao.ExecutarAtualizacaoSQL(sql);
        JOptionPane.showMessageDialog(null, "Todos os registros da tabela " + tabela + " foram excluídos com sucesso!");
    }
    
    public void deletarRegistrosConformeString(String tabela, String campoVerificacao, String valor) {
        conn = conexao.conectar();
        sql = "DELETE FROM " + tabela + " WHERE "+ campoVerificacao +" = '"+valor+"'";
        conexao.ExecutarAtualizacaoSQL(sql);
        //JOptionPane.showMessageDialog(null, "Registros excluídos conforme escopo "+tabela+" foram excluídos com sucesso!");
    }

    public boolean temCadastradoNaTabela(String tabela, String campoDeComparacao, String valorComparacao) {
        conn = conexao.conectar();
        try {
            //somente o valor de comparação deve esta entre aspas simples, nome de tabela e campos não precisa
            sql = "SELECT "+campoDeComparacao+" FROM " + tabela + " WHERE " + campoDeComparacao + " = '" + valorComparacao + "'";
            //JOptionPane.showMessageDialog(null, sql);
            conexao.ExecutarPesquisaSQL(sql);
            if ((conexao.rs.next())) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se existe duplicidade de cadastro na tabela "+tabela+"" + ex);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean tipoTemIP(String pTipo) {
        conn = conexao.conectar();
        try {
            //somente o valor de comparação deve estar entre aspas simples, nome de tabela e campos não precisa
            sql = "SELECT * FROM tbltipos WHERE tipo = '"+pTipo+"' and status='ATIVO' and temip='SIM'";
            //JOptionPane.showMessageDialog(null, sql);
            conexao.ExecutarPesquisaSQL(sql);
            if ((conexao.rs.next())) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar equipamento tem IP em "+tabela+"" + ex);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean tipoTemIPeloCodigo(int pTipoid) {
        conn = conexao.conectar();
        try {
            //somente o valor de comparação deve estar entre aspas simples, nome de tabela e campos não precisa
            sql = "SELECT codigo from tbltipos WHERE codigo='"+pTipoid+"' and temip='SIM'";
            //JOptionPane.showMessageDialog(null, sql);
            conexao.ExecutarPesquisaSQL(sql);
            if ((conexao.rs.next())) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar equipamento tem IP em "+tabela+"" + ex);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean tipoTemModeloCadastrado(int pCod) {
        conn = conexao.conectar();
        try {
            //somente o valor de comparação deve esta entre aspas simples, nome de tabela e campos não precisa
            sql = "SELECT * FROM tblmodelos WHERE tipoid = '" + pCod + "' and status='ATIVO'";
            //JOptionPane.showMessageDialog(null, sql);
            conexao.ExecutarPesquisaSQL(sql);
            if ((conexao.rs.next())) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se existe duplicidade de cadastro na tabela "+tabela+"" + ex);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean impressoraDeContrato(int pCod) {
        conn = conexao.conectar();
        try {
            //somente o valor de comparação deve esta entre aspas simples, nome de tabela e campos não precisa
            sql = "SELECT * FROM tblpatrimonios WHERE contrato = 'S' AND codigo='"+pCod+"'";
            //JOptionPane.showMessageDialog(null, sql);
            conexao.ExecutarPesquisaSQL(sql);
            if ((conexao.rs.next())) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se existe duplicidade de cadastro na tabela "+tabela+"" + ex);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean temDuplicidadeDeCadastro(String tabela, String campoDeComparacao, String valorComparacao) {
        conn = conexao.conectar();
        try {
            //somente o valor de comparação deve esta entre aspas simples, nome de tabela e campos não precisa
            sql = "SELECT * FROM " + tabela + " WHERE " + campoDeComparacao + " = '" + valorComparacao + "'";
            //JOptionPane.showMessageDialog(null, sql);
            conexao.ExecutarPesquisaSQL(sql);
            if ((conexao.rs.next())) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se existe duplicidade de cadastro na tabela "+tabela+"" + ex);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean temDuplicidadeDeCadastroNoMemorando(String tabela, String campoDeComparacao1, String valorComparacao1, String campoDeComparacao2, String valorComparacao2) {
        conn = conexao.conectar();
        try {
            //somente o valor de comparação deve esta entre aspas simples, nome de tabela e campos não precisa
            sql = "SELECT * FROM " + tabela + " WHERE " + campoDeComparacao1 + " = '" + valorComparacao1 + "' and "+ campoDeComparacao2 + " = '" + valorComparacao2 + "'";
            //JOptionPane.showMessageDialog(null, sql);
            conexao.ExecutarPesquisaSQL(sql);
            if ((conexao.rs.next())) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se existe duplicidade de cadastro na tabela "+tabela+"" + ex);
            return false;
        } finally {
            conexao.desconectar();
        }
    }

    //Os metodos de pular de um campo pra outro só deve ser utilizado quando você nao precisar abrir listas no cadastro
    public void passaCamposComEnterComJFrame(JFrame form) {
        //Colocando enter para pular de campo observando que este metodo inibe a ação de você clicar enter no TXTPESQUISA
        HashSet conj = new HashSet(form.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
        form.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);        

    }

    public void passaTodosCamposComEnterComJDialog(javax.swing.JDialog form) {
        //Colocando enter para pular de campo observando que este metodo inibe a ação de você clicar enter no TXTPESQUISA
        HashSet conj = new HashSet(form.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
        form.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);

    }
    
    public void passaCamposComEnterDentroPanel(JPanel painel){  
        // Colocando enter para pular de campo dentro de panel => ESTA É A MELHOR OPÇÃO pra não ter problemas com a pesquisa citada acima
        HashSet conj = new HashSet(painel.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));  
        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));  
        painel.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);  
    }  

    public void PreencherCombo(JComboBox cmb, String tabela, String campo) {
        //preenche a combobox ordenando pelo campo escolhido pra ser mostrado
        conn = conexao.conectar();
        sql = "select " + campo + " from " + tabela + " where status ='ATIVO' ORDER BY " + campo;
        conexao.ExecutarPesquisaSQL(sql);
        try {
            cmb.removeAllItems();
            while (conexao.rs.next()) {
                cmb.addItem(conexao.rs.getString(campo));
            };
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher o combo!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }
    }
    
    public void PreencherListaDestino(List lst, String sCampo) {
        //preenche a combobox ordenando pelo campo escolhido pra ser mostrado
        conn = conexao.conectar();
        sql = "select distinct " +sCampo+ ", status from tblpatrideptos where (status ='ENVIADO') and  memodevolucao ='N'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            lst.removeAll();
            while (conexao.rs.next()) {
                lst.add(conexao.rs.getString(sCampo));
            };
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher o combo!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }
    }
    
    public void PreencherComboComStatusVariados(JComboBox cmb, String tabela, String campo) {
        //preenche a combobox ordenando pelo campo escolhido pra ser mostrado, usado em F_PATRIDEPTOS < MOSTRARA TODAS AS ORIGENS CADASTRADAS > 
        conn = conexao.conectar();
        //sql = "select DISTINCT " + campo + " from " + tabela + " where status != '"+status+"' ORDER BY " + campo;
        sql = "select DISTINCT " + campo + " from " + tabela + " ORDER BY " + campo;
        conexao.ExecutarPesquisaSQL(sql);
        try {
            cmb.removeAllItems();
            while (conexao.rs.next()) {
                cmb.addItem(conexao.rs.getString(campo));
            };
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher o combo!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }
    }

    public void PreencherComboVariandoTipo(JComboBox cmb, String sql, String campoMostrar) {
        //preenche a combobox com opção de ordenação pela sql
        conn = conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        try {
            cmb.removeAllItems();
            while (conexao.rs.next()) {
                cmb.addItem(conexao.rs.getString(campoMostrar));
            };
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher o combo variando o tipo!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }
    }

    public void LimparCombo(JComboBox cmb, String tabela, String campo) {
        conn = conexao.conectar();
        sql = "select " + campo + " from " + tabela + " where codigo = 0";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            cmb.removeAllItems();
            while (conexao.rs.next()) {
                cmb.addItem(conexao.rs.getString(campo));
            };
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao limpar o combo!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }
    }

    public boolean ConfirmouOperacao(String sConfirmacao, String sTitulo) {
        /*se clicar no (x) para fechar ou escolher (Não) na caixa de dialogo não faz nada e fecha o dialogo e retorna false*/
        /* se clicar em Sim retorna true e executa o metodo solicitado
         A primeira string recebe a mensagem de pergunta tipo: Confirma o desejo de excluir o cliente?
         A segunda string recebe o título da caixa tipo: Excluindo cliente!
         */

        int sair = JOptionPane.showConfirmDialog(null, sConfirmacao, sTitulo, JOptionPane.YES_NO_OPTION,2);
        if (sair == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }

    public void preencherComboComListaCampos(String tabela, JComboBox cmb, int qdeColunas, int colunaOmitida) {
        //objetivo: mostrar todos os campos com exceção do codigo e email
        String nomeColuna = "";
        int numeroColuna = 0;
        //int qdeColunas    = 6;

        conn = conexao.conectar();
        sql = "select * from " + tabela + "";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            cmb.removeAllItems();
            ResultSetMetaData meta = conexao.rs.getMetaData();
            //os nomes dos campos seguem a sequencia de criacao no banco de dados ex:(codigo=1, nome=2...)
            //int omitir = 3;  //refere-se ao campo email
            for (int i = 2; i < colunaOmitida; i++) //omitindo o campo 1 que é o codigo
            {
                //Mostrando os números de 2 até um número antes do número a ser omitido que é o 3 entao mostra apenas o 2 que o campo nome
                nomeColuna = meta.getColumnName(i);  //retorna o nome da coluna conforme o numero passado 
                numeroColuna++;
                cmb.addItem(nomeColuna);
            }
            //mostro os numeros de 4 ate 6 omitindo o 3
            for (int i = (colunaOmitida + 1); (i <= qdeColunas); i++) {
                nomeColuna = meta.getColumnName(i);  //retorna o nome da coluna conforme o numero passado          
                numeroColuna++;
                cmb.addItem(nomeColuna);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar preencher o combo com lista de campos do banco!\nErro: " + ex.getMessage());
        } finally {
            conexao.desconectar();
        }

    }
    
    public boolean TemImpressorasDeContratoAtivas(){
       
        conn = conexao.conectar();
        try {            
            sql = "SELECT * from tblpatrimonios where tipoid = 3 and contrato='S' and status='ATIVO'";
            conexao.ExecutarPesquisaSQL(sql);
            if ((conexao.rs.next())) {
                temimpressorasAtivas = true;
                return true;
            } else {
                temimpressorasAtivas=false;
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se existem impressoras de contrato ativas "+tabela+"" + ex);
            return false;
        } finally {
            conexao.desconectar();
        }        
    }
    
     public boolean TemContratoImpressoraAtivo(){
        //uso -> umMetodo.TemContratoImpressoraAtivo();
        conn = conexao.conectar();
        try {            
            sql = "SELECT * from tblEmpresa where status='ATIVO'";
            conexao.ExecutarPesquisaSQL(sql);
            if ((conexao.rs.next())) {
                temContratoImpressoraAtivo = true;
                return true;
            } else {
                temContratoImpressoraAtivo = false;
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se existem impressoras de contrato ativas "+tabela+"" + ex);
            return false;
        } finally {
            conexao.desconectar();
        }        
    }
    
     public void ImpressorasDeContratoAtivas(){
       
        conn = conexao.conectar();
        try {            
            sql = "SELECT * from tblpatrimonios where tipoid = 3 and contrato='S' and status='ATIVO'";
            conexao.ExecutarPesquisaSQL(sql);
            if ((conexao.rs.next())) {
                temimpressorasAtivas = true;
            } else {
                temimpressorasAtivas=false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se existem impressoras de contrato ativas "+tabela+"" + ex);
            temimpressorasAtivas=false;
        } finally {
            conexao.desconectar();
        }        
    }

    public int somenteDigitos(String palavra) {
        //Retorna um numero que a palavra passada contenha
        String digitos = "";
        char[] letras = palavra.toCharArray();
        for (char letra : letras) {
            if (Character.isDigit(letra)) {
                digitos += letra;
            }
        }
        return Integer.parseInt(digitos);
    }
    
    public String getSomenteDigitosDaString(String palavra) {
        //Retorna uma string somente de numeros que a palavra passada contenha
        String digitos = "";
        char[] letras = palavra.toCharArray();
        for (char letra : letras) {
            if (Character.isDigit(letra)) {
                digitos += letra;
            }
        }
        return digitos;
    }

    public int buscarCodigoGenerico(String tabela, String campoTabelaParaComparar, String paramDeComparacao) {
        //retorna o codigo do registro tendo como parametro um campo string
        conn = conexao.conectar();
        sql = "SELECT codigo FROM " + tabela + " WHERE " + campoTabelaParaComparar + " = '" + paramDeComparacao + "'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if ((conexao.rs.next())) {
                conexao.rs.first();
                return conexao.rs.getInt("codigo");
            } else {
                return 0;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar código passando uma string! " + ex);
            return 0;
        } finally {
            conexao.desconectar();
        }
    }

    public boolean campoVazio(JTextField txt) {
        if (txt.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public String retornaRangePadrao(String txtParam) {
        /*
         setar txtINICIO.getText() ou txtFIM.getText()
         recebe os 7 primeiros numeros digitados => retorna SNJPGMC ou SNJCEJURC
         */

        int tamanhoString = txtParam.length();
        if (tamanhoString > 10) {         //SNJCEJURC25 => SNJCEJURC
            strRange = txtParam.substring(0, tamanhoString - 2);
        } else if (tamanhoString < 10) {  //SNJPGMC25 => SNJPGMC
            strRange = txtParam.substring(0, tamanhoString - 2);
        } else if (tamanhoString == 10) {  //SNJPGMC125 => SNJPGMC
            strRange = txtParam.substring(0, tamanhoString - 3);
        }
        //System.out.println("Padrão do range => "+strRange); 
        return strRange;
    }    

    public String retornaDeptoParaEdicaoPeloNomeDoColaborador(String nomeColaborador) {
        //retorna o departamento da seção
        conn = conexao.conectar();
        sql = "SELECT d.nome as departamento FROM tbldepartamentos d, tblclientes c WHERE c.deptoid=d.codigo and c.nome = '" + nomeColaborador + "'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getString("departamento");
            } else {
                return "";
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome do departamento! " + ex);
            return "";
        } finally {
            conexao.desconectar();
        }
    }
    
    public String retornaNomePassandoCodigo(int cod) {
        //retorna o departamento da seção
        conn = conexao.conectar();
        sql = "SELECT d.NOME,s.codigo FROM tbldepartamentos d, tblsecoes s WHERE s.deptoid=d.codigo  and s.codigo = '"+cod+"'" ;
        
        JOptionPane.showMessageDialog(null, sql);
        
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) 
            {
                return conexao.rs.getString("nome");                
            } else {
                return "";
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome do departamento! " + ex);
            return "";
        } finally {
            conexao.desconectar();
        }
    }
    
    public void atualizarNomestacaoSecaoInativada(int idSecao)
    {
        //SE INATIVAR A SEÇÃO TODAS AS ESTAÇÕES DEVERÃO FICAR COM NOME DE MICRO AO SEREM REATIVADAS GANHARAO NOVOS NOMES DE ACORDO COM A SECAO E DEPTO SELECIONADOS
        conexao.conectar();
        sql = "UPDATE TBLPATRIMONIOS SET ESTACAO='MICRO' WHERE secaoid = "+idSecao+" AND tipoid=1";        
        conexao.ExecutarAtualizacaoSQL(sql); 
                
    }
    
    public void atualizarStatusPatrimoniosReativados(int idSecao, int idDepto)
    {
        conexao.conectar();
        sql = "UPDATE tblpatrimonios SET status='ATIVO', deptoid = "+idDepto+" WHERE secaoid = "+idSecao+"";     
        conexao.ExecutarAtualizacaoSQL(sql); 
                
    }
    
    public String retornaDepto(String secao) {
        //retorna o departamento da seção
        conn = conexao.conectar();
        sql = "SELECT d.nome as departamento, s.nome FROM tbldepartamentos d, tblsecoes s WHERE s.deptoid=d.codigo AND s.nome = '" + secao + "'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getString("departamento");
            } else {
                return "";
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome do departamento! " + ex);
            return "";
        } finally {
            conexao.desconectar();
        }
    }
    
    public Integer retornaIdDepto(int pCod) {
        //retorna o departamento da seção
        conn = conexao.conectar();
        sql = "SELECT deptoid FROM tblsecoes WHERE codigo = '" +pCod+ "'";
        
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getInt("deptoid");
            } else {
                return 0;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome do departamento! " + ex);
            return 0;
        } finally {
            conexao.desconectar();
        }
    }
    
    public int buscarCodigoPeloNome(String tabela,String nome)
    {
        //retorna o codigo do nome passado como parametro
        conexao.conectar();      
        sql = "SELECT codigo FROM "+tabela+" WHERE nome = '"+nome+"'";  
        
        //JOptionPane.showMessageDialog(null, sql);
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if(conexao.rs.next()){
                return conexao.rs.getInt("codigo");
            }else{
                return 0;
            }            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível buscar o código pelo nome passado!"+ex);
            return 0;
        }finally{
            conexao.desconectar();
        }  
    }
    
    public Integer retornaCodigoDepto(String tabela, String sCampoComparacao, String pValor) {
        //retorna o departamento da seção
        conn = conexao.conectar();
        sql = "SELECT deptoid FROM "+tabela+" WHERE "+sCampoComparacao+" = '" +pValor+ "'";
        //JOptionPane.showMessageDialog(null, "CODIGO DO(A) " +sCampoComparacao);
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getInt("deptoid");
            } else {
                return 0;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o codigo do departamento! " + ex);
            return 0;
        } finally {
            conexao.desconectar();
        }
    }
    
    public Integer retornaCodigoPesq(String tabela, String sCampo1, String sCampo2, String pValor) {
        //retorna o codigo do patrimonio conforme o parametro escolhido(serie/chapa/estacao/ip)
        conn = conexao.conectar();
        sql = "SELECT codigo FROM "+tabela+" WHERE "+sCampo1+" = '" +pValor+ "' OR "+sCampo2+" = '" +pValor+ "'";
        //SELECT codigo FROM tblPatrimonios WHERE serie = valor OU chapa = valor 
        //JOptionPane.showMessageDialog(null, "CODIGO DO(A) " +sCampoComparacao);
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getInt("codigo");
            } else {
                return 0;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome do departamento! " + ex);
            return 0;
        } finally {
            conexao.desconectar();
        }
    }
    
    public Integer retornaCodigo(String tabela, String sCampoComparacao, String pValor) {
        //retorna o codigo do patrimonio conforme o parametro escolhido(serie/chapa/estacao/ip)
        conn = conexao.conectar();
        sql = "SELECT codigo FROM "+tabela+" WHERE "+sCampoComparacao+" = '" +pValor+ "'";
        //JOptionPane.showMessageDialog(null, "CODIGO DO(A) " +sCampoComparacao);
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getInt("codigo");
            } else {
                return 0;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome do departamento! " + ex);
            return 0;
        } finally {
            conexao.desconectar();
        }
    }
        
    public void getCodigosRegistrosParaUmaLista(int pCod) 
    {
        //Pega todos os nomes de clientes virtuais e coloca denta da lstListaStrings  
        conn = conexao.conectar();
        //sql = "SELECT nome FROM TBLCLIENTESVIRTUAIS";
        sql = "SELECT codigo FROM tblclientes WHERE tipovirtualid = '"+pCod+"'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            while (conexao.rs.next()) {
                lstListaStringsAuxiliar.add(conexao.rs.getString("codigo"));                    
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome da seção! " + ex);
            
        } finally {
            conexao.desconectar();
        }        
    }
    
    public void retornaValoresDeUmCampoStringDaTabelaParaUmaLista(String campo, String tabela) 
    {
        //Pega todos os nomes de clientes virtuais e coloca denta da lstListaStrings  
        conn = conexao.conectar();
        //sql = "SELECT nome FROM TBLCLIENTESVIRTUAIS";
        sql = "SELECT "+campo+" FROM "+tabela+"";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            while (conexao.rs.next()) {
                lstListaStrings.add(conexao.rs.getString("nome"));                    
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome da seção! " + ex);
            
        } finally {
            conexao.desconectar();
        }        
    }
    
    public void gravarNomeClienteVirtualNaTblClientes(String nomeSecao) 
    {
        //executando o metodo que retorna a lstListaStrings
        retornaValoresDeUmCampoStringDaTabelaParaUmaLista("nome","TBLCLIENTESVIRTUAIS");      
        
        for(int i=0; i<lstListaStrings.size();i++)
        {            
                  
            String secao                = nomeSecao;
            String nome                 = lstListaStrings.get(i)+" "+secao;
            Long n                      = new Long("0123456");                                       
            GerarNumerosAleatorios rf   = new GerarNumerosAleatorios(n);                      
            String rfCliente            = String.valueOf(rf.getNumeroAleatorioRF());
            String obs                  = "";
            int    deptoid              = codigoDeptoSelecionado;
            String status               = "ATIVO";
            String tipo                 = "N";
            int codSecao                = retornaCodigo("tblsecoes", "nome", nomeSecao); 
            int codClienteVirtual       = retornaCodigo("tbltipos", "tipo", lstListaStrings.get(i));

            umModCliente.setNome(nome);
            umModCliente.setRf(rfCliente);
            umModCliente.setObs(obs);
            umModCliente.setSecaoid(codSecao);
            umModCliente.setDeptoid(deptoid);
            umModCliente.setStatus(status);
            umModCliente.setTipo(tipo); 
            umModCliente.setTipovirtualid(codClienteVirtual);
                
            if (temDuplicidadeDeCadastro("TBLCLIENTES","RF",rfCliente)){
              JOptionPane.showMessageDialog(null, "O RF digitado já esta cadastrado, verifique!","Duplicidade no cadastro do RF!",2); 
            }else{                
                ctrCliente.salvarCliente(umModCliente);       
            }   
        }           
    }
    
    public void gravarNomeClienteVirtualSemUsuarioNaTblClientes(String nomeSecao) 
    {      
            //ex : SEM USUARIO FISC
        
            String secao                = nomeSecao;
            String nome                 = "SEM USUARIO "+nomeSecao;
            Long n                      = new Long("0123456");                                       
            GerarNumerosAleatorios rf   = new GerarNumerosAleatorios(n);                      
            String rfCliente            = String.valueOf(rf.getNumeroAleatorioRF());
            String obs                  = "";
            int    deptoid              = codigoDeptoSelecionado;
            String status               = "ATIVO";
            String tipo                 = "V"; //V de Virtual -> Pessoas = F de Fisica
            int codSecao                = retornaCodigo("tblsecoes", "nome", nomeSecao);   

            umModCliente.setNome(nome);
            umModCliente.setRf(rfCliente);
            umModCliente.setObs(obs);
            umModCliente.setSecaoid(codSecao);
            umModCliente.setDeptoid(deptoid);
            umModCliente.setStatus(status);
            umModCliente.setTipo(tipo);   
            umModCliente.setTipovirtualid(0);
                
            if (temDuplicidadeDeCadastro("TBLCLIENTES","RF",rfCliente)){
              JOptionPane.showMessageDialog(null, "O RF digitado já esta cadastrado, verifique!","Duplicidade no cadastro do RF!",2); 
            }else{                
                ctrCliente.salvarCliente(umModCliente);       
            }   
                  
    }
    
    public String retornarNome(String tabela, int codigo) {
        //retorna o nome atraves do codigo 
        conn = conexao.conectar();
        sql = "SELECT nome FROM "+tabela+" WHERE codigo = '" + codigo + "'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getString("nome");
            } else {
                return "";
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome da seção! " + ex);
            return "";
        } finally {
            conexao.desconectar();
        }
    }
    
    public String retornarNomeSecao(int codigo) {
        //retorna o nome da seção atraves do codigo 
        conn = conexao.conectar();
        sql = "SELECT nome FROM tblsecoes WHERE codigo = '" + codigo + "'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getString("nome");
            } else {
                return "";
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome da seção! " + ex);
            return "";
        } finally {
            conexao.desconectar();
        }
    }      
    
    public String retornaSecao(int codigo) {
        //retorna o nome da seção atraves do codigo do patrimonio
        conn = conexao.conectar();
        sql = "SELECT s.nome as secao FROM tblpatrimonios p, tblsecoes s WHERE p.secaoid=s.codigo AND p.codigo = '" + codigo + "'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getString("secao");
            } else {
                return "";
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome da seção! " + ex);
            return "";
        } finally {
            conexao.desconectar();
        }
    }   
        
    public String verificarvalordocampo(String tabela, String campo, int codigo) {
        //retorna o valor do campo citado como parametro
        conn = conexao.conectar();
        sql = "SELECT "+campo+" FROM "+tabela+" WHERE codigo = '" + codigo + "'"; //SELECT tipo FROM tblclientes WHERE codigo = 356
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return conexao.rs.getString(campo);
            } else {
                return "";
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao retornar o nome da seção! " + ex);
            return "";
        } finally {
            conexao.desconectar();
        }
    }

    public void getNomesColunasDaSql(String sql) {
        //conectando ao banco de dados
        conn = conexao.conectar();
        try {
            //faz o mapeamento do que dever ser atribuido ao relatorio
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs                = pst.executeQuery();
            ResultSetMetaData metaData  = rs.getMetaData();
            columnCount                 = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                //nomes das colunas da sql
                lstListaCampos.add(metaData.getColumnName(i));
            }
            //quantidade de campos da sql
            qdeColunas = columnCount;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao tentar retornar os nomes das colunas da tabela");
        }
    }
    
    public int getQdeColunasDaSql(String sql) {
        conn = conexao.conectar();
        try {
            //faz o mapeamento do que deve ser atribuido ao relatorio retornando a qde de colunas e os nomes de cada uma
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs                = pst.executeQuery();
            ResultSetMetaData metaData  = rs.getMetaData();
            columnCount                 = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                //nomes das colunas da sql
                lstListaCampos.add(metaData.getColumnName(i));
            }
            //quantidade de campos da sql
            qdeColunas = columnCount;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao tentar retornar a quantidade de colunas da tabela");
        }
        return qdeColunas;
    }

    public void preencherListaComCampos(List lstColunas, String sql) {
        /*Retorna todos os campos da tabela ideal quando não tiver chaves estrangeiras na tabela
        preenche uma lista com todos os campos da sql, importante salientar que não pode haver dados nulos nos registros*/
        conn = conexao.conectar();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs                = pst.executeQuery();
            ResultSetMetaData metaData  = rs.getMetaData();
            columnCount                 = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                lstColunas.add(metaData.getColumnLabel(i).toString());
            }
            //quantidade de campos da sql
            qdeColunas = columnCount;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Não é possível criar o PreparedStatement");
        }
    }
    
    public void preencherArrayListComCampos(ArrayList lstColunas, String sql) {
        //preenche e retorna um ArrayList com todos os campos da sql
        conn = conexao.conectar();
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs                = pst.executeQuery();
            ResultSetMetaData metaData  = rs.getMetaData();
            columnCount                 = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                lstColunas.add(metaData.getColumnLabel(i).toString());
                lstListaCampos = lstColunas;
            }
            //quantidade de campos da sql
            qdeColunas = columnCount;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao tentar preencher o arraylist com os campos da tabela");
        }
    }

    public boolean statusRegistroInativo(int pCodigo, String tabela) {
        //confirma se determinado registro esta inativo
        conn = conexao.conectar();
        try {
            sql = "SELECT * FROM " + tabela + " WHERE status='INATIVO' and codigo = '" + pCodigo + "'";
             conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se o status do registro é inativo, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean atualizaNomesDeEstacoesParaMonitor() 
    {
        //Verifica todos os equipamentos com tipoid = 2 e atualiza o nome de estação para MONITOR
        conn = conexao.conectar();       
        try {            
            sql = "update tblpatrimonios set estacao = 'MONITOR' WHERE tipoid = 2";
            conexao.ExecutarAtualizacaoSQL(sql);       
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar atualizar o nome da estação para MONITOR, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }          
    }   
    
    public boolean temNomesEstacoesDisponiveis(String depto) {
        //confirma se tem algum registro disponivel para determinado depto
        conn = conexao.conectar();
        try {
            sql = "SELECT * FROM tblnomestacao WHERE status='DISPONIVEL' AND depto='"+depto+"'";
             conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se o status do registro é inativo, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean tabelaTemInativos(String tabela) {
        conn = conexao.conectar();
        sql = "SELECT status FROM " + tabela + " WHERE status='INATIVO'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se na tabela tem inativos "+tabela+" tem registros inativos! " + ex);
            return false;
        } finally {
            conexao.desconectar();
        }
    }

    public String formatarDataParaBanco(String data) {
        //formata uma string de data para uma string aceitável para gravar no banco
        String dia = data.substring(0, 2);
        String mes = data.substring(3, 5);
        String ano = data.substring(6);

        data = dia + "." + mes + "." + ano;
        return data;
    }
            
    public Date converteStringParaData(String formato, String data) {
        //A partir de uma String data criar um formato para mesma
        SimpleDateFormat format = new SimpleDateFormat(formato);
        try {
            format.setLenient(false);
            return format.parse(data);
        } catch (ParseException e) {
            //JOptionPane.showMessageDialog(null, "Você entrou com uma data inválida, verifique!", "AVISO", JOptionPane.WARNING_MESSAGE);
        }
        return null;
    }

    public String converteDataParaString(String formato, Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        sdf.setLenient(false);
        return sdf.format(data);
    }
    
    public String converteHoraParaString(){
        //retorna uma string da hora minuto e segundo atual
        String horaMinutoAtual;
        int segundos;
        int minutos;
        int hora;
        Calendar data;
        
        try{           
            while(true){
                Thread.sleep(100);
                data    = Calendar.getInstance();
                hora    = data.get(Calendar.HOUR_OF_DAY);                
                minutos = data.get(Calendar.MINUTE);
                //segundos = data.get(Calendar.SECOND);                
                //String horaAtual = horas + ":" + minutos + ":" + segundos;
                
                if(minutos < 10 && hora < 10){
                       horaMinutoAtual = "0"+hora + ":0"+minutos; //OK
                       return horaMinutoAtual;
                }else if(minutos >= 10 && hora < 10){
                       horaMinutoAtual = "0"+hora + ":"+minutos; //OK
                       return horaMinutoAtual;
                }else if(minutos < 10 && hora >= 10){
                       horaMinutoAtual = hora + ":0"+minutos; //OK
                       return horaMinutoAtual;
                }else if(minutos >= 10 && hora >= 10){                       
                       horaMinutoAtual = hora + ":" + minutos;  //OK
                       return horaMinutoAtual;
                }                                
            }
            
        }
        catch(InterruptedException e){
            e.printStackTrace();
            return null;
        }       
    }
    
   public String formatarDataStringParaTabela(String dataFormatar){
       //formata uma data STRING vinda do banco de dados para um formato desejado tambem como STRING
       String dataFormatada=null;
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date data = formato.parse(dataFormatar);
            formato.applyPattern("dd.MM.yyyy");
            dataFormatada = formato.format(data);
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível converter uma data para string Erro : "+ex);
        }
        return dataFormatada;
    }
    
    public String getStringDataDoDia(){
        String dataString = converteDataParaString("dd.MM.yyy", dataDoDia);
        return dataString;
    }
    
    public String getStringHoraDoDia(){
        String horaString = converteHoraParaString();
        return horaString;
    }
    
    public String getStringComDataHora(){
        //juntando as duas strings data + hora ex. 13.03.2019  19:21:05       
        return getStringDataDoDia() +" "+getStringHoraDoDia()+" : ";
        
    }
    
     public int getQdeRegistrosNaTabela(String tabela){
        conn = conexao.conectar();
        sql = "SELECT count(codigo) as total FROM "+tabela;
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next())
               return conexao.rs.getInt("total");
            else
               return 0; 
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar recuperar a quantidade de registros na tabela "+tabela+" "+ex);
            return 0;
        }finally{
            conexao.desconectar();
        }  
    }     
         
    public boolean usuarioCadastrado(String nomeCliente){
        //VERIFICA SE O CLIENTE JÁ ESTA CADASTRADO COMO USUARIO
        conn = conexao.conectar();
        sql = "SELECT nome FROM tblusuarios WHERE nome = '"+nomeCliente+"'";
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next()){
                JOptionPane.showMessageDialog(null, "Atenção este usuário já esta cadastrado, verifique!","Usuário Cadastrado",2);
               return true;               
            }else{
               return false; 
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar recuperar a quantidade de registros na tabela "+tabela+" "+ex);
            return false;
        }finally{
            conexao.desconectar();
        }  
    }
     
    public String primeiraLetraMaiuscula(String texto) 
    {
        texto = texto.substring(0,1).toUpperCase().concat(toLowerCase(texto.substring(1)));
        return texto.trim();
    }     

    public boolean isDatasValidas(String datasRecebidas[]){
        //Este metodos recebe as strings de data e tenta converte-las em data do tipo date se conseguir converter a data esta digitada corretamento e é retornado true
        try {
            //SimpleDateFormat é usada para trabalhar com formatação de datas
            //neste caso meu formatador irá trabalhar com o formato "dd/MM/yyyy"
            //dd = dia, MM = mes, yyyy = ano
            //o "M" dessa String é maiusculo porque "m" minusculo se n me engano é minutos
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            //a mágica desse método acontece aqui, pois o setLenient() é usado para setar
            //sua escolha sobre datas estranhas, quando eu seto para "false" estou dizendo
            //que não aceito datas falsas como 31/02/2016
            sdf.setLenient(false);

            //aqui eu tento converter a String em um objeto do tipo date, se funcionar
            //sua data é valida
            for(int i=0;i<datasRecebidas.length;i++){
                sdf.parse(datasRecebidas[i]);
            }

            //se nada deu errado retorna true (verdadeiro)
            return true;
        } catch (ParseException ex) {
            //se algum passo dentro do "try" der errado quer dizer que sua data é falsa, então,
            //retorna falso
            return false;
        }
        
        /*
            segue o uso do metodo no botão salvar, mas a validação pode ser feita em qualquer local
            String dt1 = txtDTINICIO.getText();
            String dt2 = txtDTFIM.getText();
            String dt3 = txtDTDOC.getText();

            String sDatas[]={dt1,dt2,dt3};

            if (umMetodo.isDatasValidas(sDatas)) {
                if (cadastrando) {
                    gravarNovoRegistro();
                }else if (editando) {
                    gravarEdicaoRegistro();
                } else if (reativando) {
                    gravarReativacaoRegistro();
                }
                Leitura();
            } else {
                JOptionPane.showMessageDialog(null, "Você entrou com uma data inválida, corrija para continuar!", "AVISO", JOptionPane.WARNING_MESSAGE);
                txtDTINICIO.requestFocus();
            }        
        */
        
    }

    public boolean campoTemValores(String tabela, String campo, String valor) {
        //verifica se determinado campo tem valores na tabela
        conn = conexao.conectar();
        sql = "SELECT "+campo+" FROM " +tabela+ " WHERE "+campo+" = '"+valor+"'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa verificando se campo contem valores na tabela "+tabela+"! " + ex);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
     
    public boolean campoSelecionadoTemValores(String tabela, String campo, String pCampoChave, String pValorCampoChave) {
        //verifica se determinado campo tem valores na tabela
        conn = conexao.conectar();
        //sql = SELECT DISTINCT codigo, serie, chapa, observacoes FROM tblpatrimonios WHERE observacoes IS NULL OR observacoes = '' AND serie = 'BRJ502P7KD' 
        sql = "SELECT DISTINCT "+campo+" FROM "+tabela+" WHERE "+campo+" IS NULL OR "+campo+" = '' AND "+pCampoChave+" = '"+pValorCampoChave+"'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa verificando se campo contem valores na tabela "+tabela+"! " + ex);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
     
    public boolean sqlRetornouValores(String sql){
        conn = conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa verificando se campo contem valores na tabela "+tabela+"! " + ex);
            return false;
        } finally {
            conexao.desconectar();
        }
    }   
        
    public Integer retornaDiaVigente(){
        int dia;
        Calendar cal = Calendar.getInstance();
        dia = cal.get(Calendar.DAY_OF_MONTH );
        //System.out.println( "O dia corrente é: " + dia );
        
        return dia;
    }
    public Integer retornaMesVigente(){
        int mes;
        Calendar cal = Calendar.getInstance();
        mes = cal.get(Calendar.MONTH)+1;
        //System.out.println( "O mês corrente é: " + mes );
        
        return mes;
    }
    public Integer retornaAnoVigente(){
        int ano;
        Calendar cal = Calendar.getInstance();
        ano = cal.get(Calendar.YEAR);
        //System.out.println( "O ano corrente é: " + ano );
        
        return ano;
    }
    
    public String retornaMesAnoVigente(){
        String valorRetorno  = null;        
        
        //numDespacho recebe a string inteira ex 11/2018
        String mesVigente = String.valueOf(retornaMesVigente());
        String anoVigente = String.valueOf(retornaAnoVigente());
        
        
        String numDespacho = mesVigente+"/"+anoVigente;
        //System.out.println("String completa "+numDespacho);
        
        //sAno recebe a string somente do ano retirada da string numDespacho
        String[] sAno = numDespacho.split("/");
        String sAnoVigente = sAno[1];
        //System.out.println("Somente o ano "+sAno);
        
        //pegar apenas o numero antes da string
        int pos = numDespacho.indexOf("/");
        
        //variavel svalor recebe o numero do mes que vem antes da barra no exemplo acima 10
        int svalor = Integer.valueOf(numDespacho.substring(0, pos));
        //System.out.println("Numero do mes "+svalor);

        if(svalor == 1) {
             valorRetorno = "JAN/"+sAnoVigente;
        }else if(svalor == 2) {
             valorRetorno = "FEV/"+sAnoVigente;
        }else if(svalor == 3) {
             valorRetorno = "MAR/"+sAnoVigente;
        }else if(svalor == 4) {
             valorRetorno = "ABR/"+sAnoVigente;
        }else if(svalor == 5) {
             valorRetorno = "MAI/"+sAnoVigente;
        }else if(svalor == 6) {
             valorRetorno = "JUN/"+sAnoVigente;
        }else if(svalor == 7) {
             valorRetorno = "JUL/"+sAnoVigente;
        }else if(svalor == 8) {
             valorRetorno = "AGO/"+sAnoVigente;
        }else if(svalor == 9) {
             valorRetorno = "SET/"+sAnoVigente;
        }else if(svalor == 10) {
             valorRetorno = "OUT/"+sAnoVigente;
        }else if(svalor == 11) {
             valorRetorno = "NOV/"+sAnoVigente;
        }else if(svalor == 12) {
             valorRetorno = "DEZ/"+sAnoVigente;
        }
        return valorRetorno;
    }
    
    public String getNomeMes(int pNumes)
    {
        String valorRetorno  = null;        
        
        if(pNumes == 1) {
             valorRetorno = "Janeiro";
        }else if(pNumes == 2) {
             valorRetorno = "Fevereiro";
        }else if(pNumes == 3) {
             valorRetorno = "Marco";
        }else if(pNumes == 4) {
             valorRetorno = "Abril";
        }else if(pNumes == 5) {
             valorRetorno = "Maio";
        }else if(pNumes == 6) {
             valorRetorno = "Junho";
        }else if(pNumes == 7) {
             valorRetorno = "Julho";
        }else if(pNumes == 8) {
             valorRetorno = "Agosto";
        }else if(pNumes == 9) {
             valorRetorno = "Setembro";
        }else if(pNumes == 10) {
             valorRetorno = "Outubro";
        }else if(pNumes == 11) {
             valorRetorno = "Novembro";
        }else if(pNumes == 12) {
             valorRetorno = "Dezembro";
        }
       
        return valorRetorno;
    }
    
    public String retornaInicioDoNomeDaEstacao(String estacao)
    {
        String strRange = "";        
        int tamanhoString = estacao.length();
        strRange = estacao.substring(0, 3);
        return strRange;
    }

    public String retornaTresDigitosFinaisDaEstacao(String estacao)
    {
        String strRange = "";                  //  SNJPGMC000     
        int tamanhoString = estacao.length();

        if(tamanhoString == 10)
        {
           strRange = estacao.substring(estacao.length() - 3);
        }else if(tamanhoString < 10){
            strRange = estacao.substring(estacao.length() - 2);    
        }    
        return strRange;
    }
    
    public String retornaNomeTabela(String tabela){
        //retira do nome da tabela o TBL ficando apenas por exemplo de TBLSECOES -> SECOES
        String nomeTabela   = tabela.substring(3);
        nomeTabela          = nomeTabela.toLowerCase();
        
        return nomeTabela;
    }
    
    public String retornaRFparaGravarUsuario(String rf){
        //Pega o rf do cliente retira o ultimo numero tipo 5086981 e fica 508698 depois acrescenta o D ficando D508698
       int tamanho = rf.length();
       rf = rf.substring(0, tamanho-1);   
       return "D"+rf;
    }
                  
        
    public void gerarNumeroAleatorioParaCampoTexto(JTextField txt){
        //caso o equipamento não tenha número de chapa gere uma Chapa Ficticia 
        String numeroGerado = null;
        txt.setText(null);        
        Long n = new Long("0123456789");                                    
        GerarNumerosAleatorios num = new GerarNumerosAleatorios(n);          
        numeroGerado = String.valueOf(num.getNumeroAleatorioIP());
        
        while(umPatrimonioDAO.duplicidadePelaChapa(numeroGerado)){                    
            //se já estiver cadastrado apagar o campo e gerar novo numero ate que o numero gerado não esteja cadastrado
            //JOptionPane.showMessageDialog(null,"Atenção uma estação com Chapa "+numeroGerado+" já esta cadastrada, verifique se esta Ativa ou Inativa para reativação!","Duplicidade : Chapa "+numeroGerado+"",2); 
            numeroGerado = null;
            txt.setText(null);   
            numeroGerado = String.valueOf(num.getNumeroAleatorioIP());
        }
        txt.setText("0090"+numeroGerado);
    }
    
    public String gerarNumeroAleatorio(){
        //caso o equipamento não tenha número de chapa gere uma Chapa Ficticia 
        String numeroGerado = null;
               
        Long n = new Long("0123456789");                                    
        GerarNumerosAleatorios num = new GerarNumerosAleatorios(n);          
        numeroGerado = String.valueOf(num.getNumeroAleatorioIP());
        
//        while(umPatrimonioDAO.duplicidadePelaChapa(numeroGerado)){                    
//            //se já estiver cadastrado apagar o campo e gerar novo numero ate que o numero gerado não esteja cadastrado
//            //JOptionPane.showMessageDialog(null,"Atenção uma estação com Chapa "+numeroGerado+" já esta cadastrada, verifique se esta Ativa ou Inativa para reativação!","Duplicidade : Chapa "+numeroGerado+"",2); 
//            
//            numeroGerado = String.valueOf(num.getNumeroAleatorioIP());
//        }
        return numeroGerado;
    }
    
    public String retornarStatusDoRegistroPeloNome(String tabela, String pComparacao){
        
        String status = null;
        
        conexao.conectar();
        try
        {             
            sql = "SELECT status FROM "+tabela+" where nome = '"+pComparacao+"'";                
            conexao.ExecutarPesquisaSQL(sql);
            
            if (conexao.rs.next())
            {
                status = conexao.rs.getString("status");
                //JOptionPane.showMessageDialog(null, "RANGE : "+rangeEstacao);                    
            }       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }        
        
        return status;
        
    }
    
    public int retornarQdeRegsPeloStatus(String tabela, String pValor){
        conexao.conectar();
        try
        {             
            sql = "SELECT status FROM "+tabela+" where status = '"+pValor+"' and memodevolucao='N' and memoenvio='S' and dtdevolucao is not null"; 
            //JOptionPane.showMessageDialog(null, sql);
            conexao.ExecutarPesquisaSQL(sql);
            
            if (conexao.rs.next())
            {                
                totalRegs = conexao.rs.getRow();
            }       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }        
        
        return totalRegs;
        
    }
    
    public int getQdeRegsPeloStatus(String tabela, String pStatus){
        conexao.conectar();
        try
        {             
            sql = "SELECT COUNT(*) FROM "+tabela+" WHERE status = '"+pStatus+"' ";             
            conexao.ExecutarPesquisaSQL(sql);
            
            if (conexao.rs.next())
            {                
                totalRegs = conexao.rs.getInt(1);
            }       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }       
        return totalRegs;
    }
    
    public Boolean itemExisteNasTabelas(String tabela1, String tabela2, String pCampo, String pValComparar){
                        
        /* 
          select m.numemo from tblmemostransferidos m, tblitensmemotransferidos i where m.numemo = i.numemo and m.numemo = :pnumemo
        */ 
        conexao.conectar();
        
        try {
            sql = "SELECT a."+pCampo+" FROM "+tabela1+" a, "+tabela2+" b where a."+pCampo+" = b."+pCampo+" and b."+pCampo+" = '"+pValComparar+"'";               
            JOptionPane.showMessageDialog(null, sql);
            conexao.ExecutarPesquisaSQL(sql);
            if ((conexao.rs.next())) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se existe duplicidade de cadastro na tabela "+tabela+"" + ex);
            return false;
        } finally {
            conexao.desconectar();
        }      
    }
    
    public Boolean itemEnviadoAtravesDeOutroMemorando(String sSerie){        
        conexao.conectar();        
        try {
            sql = "SELECT serie, numemo FROM TBLITENSMEMOTRANSFERIDOS WHERE serie ='"+sSerie+"' AND status='PROCESSANDO'";               
            //JOptionPane.showMessageDialog(null, sql);
            conexao.ExecutarPesquisaSQL(sql);
            if ((conexao.rs.next())) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar verificar se existe duplicidade de cadastro na tabela "+tabela+"" + ex);
            return false;
        } finally {
            conexao.desconectar();
        }      
    }
    
    public String gerarRangeNomeEstacaoPeloDepto(String nomeDepto){
            
        conexao.conectar();
        try
        {             
            sql = "select range from tbldepartamentos where nome = '"+nomeDepto+"'";                
            conexao.ExecutarPesquisaSQL(sql);
            
            if (conexao.rs.next())
            {
                rangeEstacao = conexao.rs.getString("range");
                //JOptionPane.showMessageDialog(null, "RANGE : "+rangeEstacao);                    
            }       
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }        
        
        return rangeEstacao;
        
    }
    
     public String gerarProximoNomestacao(String depto)
     {
        String range        = "";
        String numestacao   = "";
        
        //JOptionPane.showMessageDialog(null, "DEPTO : "+depto);
        
        //Gerar o range de acordo com o depto identificado atraves do cliente selecionado ex: PGMCGGMC do depto CGGM
        if (depto.equals("BIBLIOTECA"))
        {   
            range =  "PGMCEJURC";
            
        }else{
            range = gerarRangeNomeEstacaoPeloDepto(depto);
        }
        
        //JOptionPane.showMessageDialog(null, "RANGE : "+range);
               
        //Gerar o número disponivel para completar o nome da estação
        if (depto.equals("BIBLIOTECA") || depto.equals("CEJUR") )
        { 
            numestacao = Integer.toString(verificarNomesDisponiveis("CEJUR")); 
        }else{   
            numestacao = Integer.toString(verificarNomesDisponiveis(depto)); 
        }
                 
        //JOptionPane.showMessageDialog(null, "NUM DA ESTACAO : "+numestacao);
        
        nomeEstacao = range+numestacao;
        //JOptionPane.showMessageDialog(null, "ESTACAO : "+nomeEstacao);
        
        //Nome completo da proxima estação
        return nomeEstacao;       
    }    
    
    public Integer verificarNomesDisponiveis(String depto){
        //Verifica se tem uma estação disponivel para o cadastro atual desse depto, se nao tiver gerar numero        
        conexao.conectar();
        try
        { 
            //se tiver uma estacao disponivel para o departamento ele vai usar (OK)            
            sql = "SELECT numestacao FROM tblnomestacao WHERE depto = '"+depto+"' AND status = 'DISPONIVEL' ORDER BY numestacao";            
            conexao.ExecutarPesquisaSQL(sql);
            
            if (conexao.rs.next())
            {
                numeroEstacao = (conexao.rs.getInt("numestacao"));
                //JOptionPane.showMessageDialog(null, "REGISTRO "+String.valueOf(numeroEstacao) + " COM STATUS DISPONIVEL");    
                
            }else{
                
                    sql = "SELECT depto FROM tblnomestacao WHERE depto = '"+depto+"'";            
                    conexao.ExecutarPesquisaSQL(sql);
                    //Se estiver cadastrando pela primeira vez o departamento seta a estacao 1(OK)
                    if (!conexao.rs.next())
                    {
                        numeroEstacao = 1;  
                    }else{
                        //gera o numero pegando o ultimo da sequencia da tblnomestacao
                        numeroEstacao = gerarProximoNumeroEstacao(depto);   
                    }                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }        
           
        return numeroEstacao;
    }
    
    public Integer gerarProximoNumeroEstacao(String depto)
    {
        int novoNumeroEstacao = 0;
        conexao.conectar();
        try
        {                         
            sql = "SELECT max(numestacao) as numax FROM tblnomestacao WHERE depto = '"+depto+"' GROUP BY numestacao";
            
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next())
            {
                novoNumeroEstacao = (conexao.rs.getInt("numax"));                 
                novoNumeroEstacao = novoNumeroEstacao+1;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }        

        return novoNumeroEstacao;        
    }   
            
    public Integer gerarProximoNumeroMemoTransferir()
    {  
        int proximoMemo;
        
        //identificar qual o numero do último memorando cadastrado pesquisando no BD gerando valor para variavel valorCampo abaixo ex: 10/2018
        //String valorCampo = getValorCampoUltimoCodigo("TBLITENSMEMOTRANSFERIDOS", "numemo"
        String valorCampo = getValorCampoUltimoCodigo("TBLMEMOSTRANSFERIDOS", "numemo");
        
        //agora temos que pegar apenas o numero 10 acima e convetê-lo para int ou seja precisamos de qualquer valor antes da barra        
        //Posição do caracter ( / ) barra na string
        int pos = valorCampo.indexOf("/");
        
        //variavel svalorUltimoRegistro recebe o numero que vem antes da barra no exemplo acima 10
        String svalorUltimoRegistro = valorCampo.substring(0, pos);
        
        //variavel int ultimoMemo recebe o numero 10 que vem antes da barra como inteiro para prosseguimento
        int ultimoMemo = Integer.valueOf(svalorUltimoRegistro); 
        
        //incrementa a variavel que controla os numeros de memorandos
        controlaNumemo = ultimoMemo;
        
        //Identificando o ano do ultimo memo cadastrado para comparar com o ano vigente - split gera um vetor a partir da separacao da / ou seja 10/2018 gera [10,2018] sendo indice 0 = 10 e indice 1 = 2018
        String[]anoDoUltimoMemo = valorCampo.split("/");         
        String strAnoUltimoMemo = anoDoUltimoMemo[1];     
        //System.out.println(Arrays.toString(anoDoUltimoMemo));
                
        if(!strAnoUltimoMemo.equals(anoVigente)){
           proximoMemo = 1;
        }else{
            proximoMemo = ultimoMemo + 1;
        }

        return proximoMemo;        
    }   
    
    public String gerarProximoNomeEstacaoTmp()
    {
        //ESTE METODO PEGA O PRIMEIRO NOME DISPONIVEL NA TBLNOMESTACAOTMP
        String estacao = "";
        conexao.conectar();
        try
        {                            
            sql = "SELECT first 1 nomestacao FROM tblnomestacaotmp WHERE status='DISPONIVEL'";
            
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next())
            {
                estacao = (conexao.rs.getString("nomestacao"));      
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }        

        return estacao;        
    }   
        
    public int retornarQdeRegistrosComCampoVazio(String tabela, String campo) 
    {    
        conexao.conectar();
        try
        { 
            sql = "SELECT COUNT(*) AS qde FROM "+tabela+" WHERE "+campo+" = ''";            
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next())
            {
                totalRegs = (conexao.rs.getInt("qde"));
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return totalRegs;
    }  
    
    public int retornarQdeRegistrosDoMemorando(String numemo) 
    {    
        conexao.conectar();
        try
        { 
            sql = "SELECT COUNT(*) AS qde FROM TBLITENSMEMOTRANSFERIDOS WHERE numemo = '"+numemo+"' ";            
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next())
            {
                totalRegs = (conexao.rs.getInt("qde"));
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return totalRegs;
    }  
            
    public ArrayList<String> ListarChapas() {
         int totalRegistros = retornarQdeRegistrosComCampoVazio("tblpatrimonios", "chapa");               
        
        //OBTENDO A LISTA COM OS NUMEROS DE CHAPAS GERADOS E RETORNANDO EM UM ARRAYLIST
        ArrayList listaChapas = new ArrayList();

        int i=0;
        while(i < totalRegistros){
            listaChapas.add(gerarNumeroAleatorio());                 
            //System.out.println(listaChapas.get(i));                
            i++;
        }
        //System.out.println("Total de registros : "+totalRegistros);
        return listaChapas;
    }
    
    public ArrayList<Integer> ListarCodigosDosItensDoMemorando(String pNumemo) {
        //OBTENDO A LISTA COM OS OS CODIGOS DOS REGS NA TABELA DE TBLPATRIMONIOS ATRAVES DO NUMERO DE SERIE ENTRE TBLPATRIMONIOS/TBLITENSMEMOTRANSFERIDOS
        conexao.conectar();  
        String sql1 = "select p.codigo from TBLPATRIMONIOS p, TBLITENSMEMOTRANSFERIDOS i where p.serie = i.serie AND i.numemo = '"+pNumemo+"'"; 
        conexao.ExecutarPesquisaSQL(sql1);

        ArrayList listarCodigos = new ArrayList();

        try {
        while(conexao.rs.next())
        {
             listarCodigos.add(conexao.rs.getInt("codigo"));                                
        }   
        } catch (SQLException ex) {
            Logger.getLogger(MetodosPublicos.class.getName()).log(Level.SEVERE, null, ex);
        }    

//        for (Object dado : listarCodigos) {
//            System.out.println(dado); 
//        }
        
        return listarCodigos;
    }       
    
    private ArrayList<String> ListarCodigos() {
        //OBTENDO A LISTA COM OS OS CODIGOS DOS REGS COM CHAPAS VAZIAS E RETORNANDO EM UM ARRAYLIST
        conexao.conectar();  
        String sql1 = "select codigo from tblpatrimonios where chapa is null or chapa='' order by codigo";
        conexao.ExecutarPesquisaSQL(sql1);

        ArrayList listarCodigos = new ArrayList();

        try {
        while(conexao.rs.next()){
             listarCodigos.add(conexao.rs.getString("codigo"));                                
          }   
        } catch (SQLException ex) {
            Logger.getLogger(MetodosPublicos.class.getName()).log(Level.SEVERE, null, ex);
        }    

//        for (Object dado : listarCodigos) {
//            System.out.println(dado); 
//        }
        return listarCodigos;
    }       
    
    private ArrayList<String> ListarCodigosPelaSerie(String sSerie) {
        //OBTENDO A LISTA COM OS OS CODIGOS DOS REGS COM CHAPAS VAZIAS E RETORNANDO EM UM ARRAYLIST
        conexao.conectar();  
        String sql1 = "select codigo from tblpatrimonios where serie = '"+sSerie+"' order by codigo";
        conexao.ExecutarPesquisaSQL(sql1);

        ArrayList listarCodigos = new ArrayList();

        try {
        while(conexao.rs.next()){
             listarCodigos.add(conexao.rs.getString("codigo"));                                
          }   
        } catch (SQLException ex) {
            Logger.getLogger(MetodosPublicos.class.getName()).log(Level.SEVERE, null, ex);
        }    

//        for (Object dado : listarCodigos) {
//            System.out.println(dado); 
//        }
        return listarCodigos;
    }       
        
    public ArrayList<String> ListarNumerosEstacoes(int numInicial, int numFinal) {
        
        //Retorna uma lista de numeros a partir de numero inicial e numero final entrados pelo usuário para exclusão de nomes de rede por intervalo solicitado
        ArrayList listaNumEstacoes = new ArrayList();

        int i = numInicial;
        while(i < (numFinal+1)){
            listaNumEstacoes.add(i);                         
            i++;
        }
//        for (Object nomes : listaNumEstacoes) {
//            System.out.println(nomes); 
//        }
        return listaNumEstacoes;
    }
    
    public Integer ListarCodigoNomeEstacao(String pNome) {
        //Retorna o codigo do nome de estação passado para exclusões por intervalo
        int codNome = 0;
        
        conexao.conectar(); 
              
            String sqlConsulta = "select codigo from TBLNOMESTACAO where nomestacao = '"+pNome+"'";
            conexao.ExecutarPesquisaSQL(sqlConsulta);
      
            try {
            while(conexao.rs.next()){
                 codNome = conexao.rs.getInt("codigo");                                
            }   
            } catch (SQLException ex) {
                Logger.getLogger(MetodosPublicos.class.getName()).log(Level.SEVERE, null, ex);
            }                     
        
            return codNome;
    }
    
    public Boolean nomeEstacaoExiste(String pNome) {
        //Retorna sim ou nao se nome existir ou nao na tabela para exclusões por intervalo
        conexao.conectar(); 
              
        String sqlConsulta = "select codigo from TBLNOMESTACAO where nomestacao = '"+pNome+"' AND status='DISPONIVEL'"; 
        conexao.ExecutarPesquisaSQL(sqlConsulta);

        try {
        while(conexao.rs.next()){
             return true;                                
        }   
        } catch (SQLException ex) {
            return false;
        }        
        return false;
    }
            
    public void atualizarChapasVazias(){
        //OBTENDO A QUANTIDADE DE REGISTROS A SEREM ATUALIZADOS
        int totalRegistros = retornarQdeRegistrosComCampoVazio("tblpatrimonios", "chapa");        
                
        //RECEBENDO A LISTA COM NUMEROS DE CHAPAS A SEREM UTILIZADOS NA ATUALIZAÇÃO E COLOCANDO DENTRO DE UM ARRAY PARA USO 
        lstListaStrings = ListarChapas();
//        for (Object dado : lstListaStrings) {
//            System.out.println(dado); 
//        }
        
        //RECEBENDO A LISTA COM CODIGOS UTILIZADOS NA ATUALIZAÇÃO E COLOCANDO DENTRO DE UM ARRAY PARA USO
        lstListaCampos = ListarCodigos();
//        for (Object dado1 : lstListaCampos) {
//            System.out.println(dado1); 
//        }
        
        //ATUALIZANDO TODOS OS REGISTROS COM CHAPAS VAZIAS ESTE METODO ESTA UTILIZANDO A CONEXAO DO METODO retornarQdeRegistrosComCampoVazio por isso nao precisei conectar
        for(int i=0;i < totalRegistros;i++){
            String sql2 = "UPDATE tblpatrimonios set chapa ='0090"+lstListaStrings.get(i)+"' where codigo = '"+lstListaCampos.get(i)+"' ";
            conexao.ExecutarAtualizacaoSQL(sql2);            
        }
        conexao.desconectar();
    }
    
    public int buscarCodigoEstacaoPeloNome(String tabela,String paramCampo,String paramValue)
    {
        //retorna o codigo do nome passado como parametro
        conexao.conectar();      
        sql = "SELECT codigo FROM "+tabela+" WHERE "+paramCampo+" = '"+paramValue+"'";  
        
        //JOptionPane.showMessageDialog(null, sql);
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if(conexao.rs.next()){
                return conexao.rs.getInt("codigo");
            }else{
                return 0;
            }            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível buscar o código pelo nome passado!"+ex);
            return 0;
        }finally{
            conexao.desconectar();
        }  
    }
                
}