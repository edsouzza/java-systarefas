package Dao;

import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.ClienteVirtual;
import javax.swing.JOptionPane;

public class DAOClienteVirtual {

    ConnConexao conexao  = new ConnConexao();    
    
    public boolean salvarClienteVirtualDAO(ClienteVirtual pCliente) 
    {
        conexao.conectar();
        try 
        {
            String sql = "INSERT INTO TBLCLIENTESVIRTUAIS (nome, status) VALUES (?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pCliente.getNome());           
            pst.setString(2, "ATIVO");
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean atualizarClienteVirtualDAO(ClienteVirtual pCliente)
    {
        conexao.conectar();
        try
        {
            //optei por nao atualizar o RF pra nao correr o risco de duplicidades
            sql = "UPDATE TBLCLIENTESVIRTUAIS SET nome=?, status=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pCliente.getNome());
            pst.setString(2, pCliente.getStatus()); 
            pst.setInt   (3, pCliente.getCodigo());
            pst.executeUpdate();
            pst.close();  
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        }finally{
            conexao.desconectar();
        }        
    }
    
    public boolean atualizarStatusClienteVirtualDAO(int pCod, String pStatus)
    {                                  
        try 
        {   
            conexao.conectar();
            sql = "UPDATE TBLCLIENTESVIRTUAIS SET status = '"+pStatus+"' WHERE codigo = '"+pCod+"' ";
            conexao.ExecutarAtualizacaoSQL(sql);
            return true;           
       }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }          
    }    
    
    public boolean atualizarNomeClienteVirtualDAO(int pCod, String pNome)
    {                                  
        try 
        {   
            conexao.conectar();
            sql = "UPDATE TBLCLIENTESVIRTUAIS SET nome = '"+pNome+"' WHERE codigo = '"+pCod+"' ";
            conexao.ExecutarAtualizacaoSQL(sql);
            return true;           
       }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }          
    }    
    
    public ClienteVirtual pesquisarClienteVirtualDAO(ClienteVirtual pCliente) 
    {       
        try
        {
            conexao.conectar();
            sql = "SELECT * FROM TBLCLIENTESVIRTUAIS WHERE codigo = '" + pCliente.getCodigo() + "' OR nome = '" + pCliente.getNome() + "'";           
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                pCliente.setCodigo(conexao.rs.getInt("codigo"));
                pCliente.setNome(conexao.rs.getString("nome"));            
                pCliente.setStatus(conexao.rs.getString("status"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return pCliente;
    }   
    
     public boolean RegistroDuplicado(ClienteVirtual pCliente)
    {
        conexao.conectar();                          
        try 
        {              
            sql = "SELECT * FROM TBLCLIENTESVIRTUAIS WHERE nome = '"+pCliente.getNome()+"'";
            conexao.ExecutarPesquisaSQL(sql);            
            if(conexao.rs.next())
            {
                //tipo de msg = 1 é a de informacao msg = 2 é a de exclamação  msg = 3 é a de interrogação
                JOptionPane.showMessageDialog(null,"Atenção o nome "+pCliente.getNome()+" digitado já esta cadastrado, verifique!","Operação não concluída por duplicidade no nome!",2);                
                return true;
                          
            }else{ return false; }            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa sobre duplicidade! "+ex);
             return false;
        }finally{
            conexao.desconectar();
        }
               
    }
        
    public boolean excluirClienteVirtualDAO(int pCodigoCliente)
    {
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM TBLCLIENTESVIRTUAIS WHERE codigo = '" + pCodigoCliente + "'";            
            conexao.ExecutarPesquisaSQL(sql);            
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    }        
       
}