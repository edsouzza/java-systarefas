package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import biblioteca.GetIPServidor;
import static biblioteca.VariaveisPublicas.ipServidor;
import static biblioteca.VariaveisPublicas.nomeDoBanco;
import static biblioteca.VariaveisPublicas.nomeBancoSetado;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnConexao {
    public static Connection conexao    = null;
    public static Statement stm         = null;
    public static ResultSet rs          = null;
        
    public static Connection conectar()
    { 
        
        /*pegando o ip da maquina local e comparando com IP do servidor SNJPGMC59(10.71.32.76)
        Se a maquina utilizada pelo sistema é a SNJPGMC59(10.71.32.157) o IP retornado será o mesmo senao retorna localhost*/
        GetIPServidor umIPServidor = new GetIPServidor();        
        umIPServidor.setNomeBanco(nomeBancoSetado);
        nomeDoBanco           = umIPServidor.getNomeBanco();        
        ipServidor            = umIPServidor.retornaIPServidor();
        
        String driver         = "org.firebirdsql.jdbc.FBDriver";
        String url            = "jdbc:firebirdsql:"+ipServidor+"/3050:C:/Meus Documentos/Bancos_de_Projetos/SYSTAREFAS/"+nomeDoBanco+".FDB";
        //String url            = "jdbc:firebirdsql:localhost/3050:C:/Meus Documentos/Bancos_de_Projetos/SYSTAREFAS/"+nomeDoBanco+".FDB";
        String usuario        = "SYSDBA";
        String senha          = "masterkey";  
        
        //JOptionPane.showMessageDialog(null,"IP DO SERVIDOR RETORNADO: "+umIPServidor.retornaIPServidor());
        
        try
        {
          Class.forName(driver);
          conexao = DriverManager.getConnection(url, usuario, senha); 
          //System.out.println("conexao ok");   
          return conexao;
        }
        catch(Exception erro){
            JOptionPane.showMessageDialog(null,"Ocorreu um erro de conexão. Verifique a Base de Dados indicada !"+"\n" +
            erro.getMessage(),"Conexão",3);
            erro.printStackTrace();
            return null;
        }
                
    }
    
    public Connection getConnection(){
        return this.conexao;
    }
    
     public void desconectar()
    {
       boolean result = true; 
       try 
        {
              conectar().close();             
              //JOptionPane.showMessageDialog(null, "Banco desconectado com sucesso!");
        }         
        catch (SQLException ErroFecharBanco)
        {
          JOptionPane.showMessageDialog(null, "Não foi possível fechar o banco de dados: "+ErroFecharBanco.getMessage());
          result = false;
        }          
    }
     
    public boolean ExecutarPesquisaSQLComRetorno(String sql) {        
        try {
            stm = conexao.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY);
            rs = stm.executeQuery(sql);   

            if (rs.next()) { 
                return true;  // Retornou dados
            } else {
                return false; // Não retornou dados
            }
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, 
                "Não foi possível executar o comando SQL de pesquisa, \n" + sqlEx + 
                ", o SQL passado foi \n" + sql);
            return false; // Em caso de erro, retorna falso
        }          
    } 
     
    public void ExecutarPesquisaSQL(String sql)
    {        
       //esse metodo executeQuery geralmente é utilizado para consultas select no banco
        try 
        {
           stm  = conexao.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY);
           rs   = stm.executeQuery(sql);             
        }         
        catch (SQLException sqlEx)
        {
          JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql de pesquisa, \n"+sqlEx+", o sql passado foi \n"+sql);          
        }          
    }    
         
    public void ExecutarAtualizacaoSQL(String sql)
    {        
       //esse metodo executeUpdate geralmente é utilizado para inserções insert / atualizacoes update / deleção delete no banco
        try 
        {
           stm     = conexao.createStatement(rs.TYPE_SCROLL_INSENSITIVE, rs.CONCUR_READ_ONLY);
           int rs  = stm.executeUpdate(sql);
        }         
        catch (SQLException sqlEx)
        {
          JOptionPane.showMessageDialog(null,"Não foi possível executar a atualização com o comando sql, \n"+sqlEx+", o sql passado foi \n"+sql);          
        }          
    }   
       
}