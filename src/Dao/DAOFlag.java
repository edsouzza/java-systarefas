package Dao;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class DAOFlag {
    
    ConnConexao conexao  = new ConnConexao();
        
    public boolean salvarFlagInicialDAO() 
    {
        conexao.conectar();
        try 
        {
            String sql = "INSERT INTO tblflags (flag) VALUES (?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "FALSE");           
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
            return false;
        } finally {
            conexao.desconectar();
        }
    }  
   
    public void atualizarFlagTrueDAO() 
    {
        conexao.conectar();      
        try
        {  
            conexao.ExecutarAtualizacaoSQL("UPDATE TBLFLAGS SET FLAG = 'TRUE' WHERE CODIGO = 1");  
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro a o tentar atualizar o registro : \n"+e+", o sql passado foi \n"+sql);           
       } finally {
            conexao.desconectar();
       }   
    }
    
    public void atualizarFlagFalseDAO() 
    {
        conexao.conectar();      
        try
        {  
            conexao.ExecutarAtualizacaoSQL("UPDATE TBLFLAGS SET FLAG = 'FALSE' WHERE CODIGO = 1");  
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro a o tentar atualizar o registro : \n"+e+", o sql passado foi \n"+sql);           
       } finally {
            conexao.desconectar();
       }   
    }
    
    public boolean consFlagFalseDAO()
    {       
        conexao.conectar();      
        try
        {  
            sql = "SELECT flag FROM TBLFLAGS WHERE codigo = 1 AND flag = 'FALSE'";                       
            conexao.ExecutarPesquisaSQL(sql); 
            if(conexao.rs.next()){
               return true; 
            }else{
                return false;
            }                        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro a o pesquisar a tabela, erro gerado : \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
       }         
    }    
    
}