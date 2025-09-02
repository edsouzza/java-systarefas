package Dao;

import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.IPDisponivel;

/**
 *
 * @author d631863
 */
public class DAOIpsDisponiveis {

    ConnConexao  conexao  = new ConnConexao();
    
    
    public boolean salvarIPDisponivelDAO(IPDisponivel pIP) 
    {
        conexao.conectar();
        try 
        {
            String sql = "INSERT INTO tblIPSDisponiveis (ip,status) VALUES (?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pIP.getIp());
            pst.setString(2, pIP.getStatus());
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean atualizarIPDisponivelDAO(IPDisponivel pIP) 
    {
        conexao.conectar();
        try
        {
            String sql = "UPDATE tblIPSDisponiveis SET ip=?, status=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pIP.getIp());
            pst.setString(2, pIP.getStatus());
            pst.setInt   (3, pIP.getCodigo());
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
    
    public IPDisponivel pesquisarIPDisponivelDAO(IPDisponivel pIP) 
    {       
        try
        {
            conexao.conectar();
            sql = "SELECT * FROM tblIPSDisponiveis WHERE codigo = '" + pIP.getCodigo() + "'";           
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                pIP.setCodigo(conexao.rs.getInt("codigo"));
                pIP.setIp(conexao.rs.getString("ip"));
                pIP.setStatus(conexao.rs.getString("status"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return pIP;
    }
        
    public boolean excluirIPDisponivelDAO(IPDisponivel pIP) 
    {
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM tblIPSDisponiveis WHERE codigo = '" + pIP.getCodigo() + "'";     
            conexao.ExecutarAtualizacaoSQL(sql);      
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    }  
    
    public boolean RegistroDuplicado(IPDisponivel pIP)
    {
        conexao.conectar();                          
        try 
        {              
            sql = "SELECT * FROM tblIPSDisponiveis WHERE ip = '"+pIP.getIp()+"' AND status='DISPONIVEL'";
            conexao.ExecutarPesquisaSQL(sql);            
            if(conexao.rs.next())
            {
                return true;
                          
            }else{ return false; }            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa sobre duplicidade! "+ex);
             return false;
        }finally{
            conexao.desconectar();
        }               
    }
    public boolean RegistroUtilizado(IPDisponivel pIP)
    {
        conexao.conectar();                          
        try 
        {              
            sql = "SELECT ip FROM tblPatrimonios WHERE ip = '"+pIP.getIp()+"' AND tipoid=3";
            conexao.ExecutarPesquisaSQL(sql);            
            if(conexao.rs.next())
            {
                return true;
                          
            }else{ return false; }            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa sobre duplicidade! "+ex);
             return false;
        }finally{
            conexao.desconectar();
        }               
    }
      
}