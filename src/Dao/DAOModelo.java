package Dao;

import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.PreparedStatement;
import modelo.Modelo;
import javax.swing.JOptionPane;

public class DAOModelo {

    ConnConexao conexao  = new ConnConexao();     
    
    public boolean salvarModeloDAO(Modelo pModelo) 
    {
        conexao.conectar();
        try 
        {
            String sql = "INSERT INTO tblModelos (tipoid, modelo, descricao, contrato, status) VALUES (?,?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1, pModelo.getTipoid());
            pst.setString(2, pModelo.getModelo());
            pst.setString(3, pModelo.getDescricao());    
            pst.setString(4, pModelo.getContrato());    
            pst.setString(5, "ATIVO");  
            pst.execute(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean atualizarModeloDAO(Modelo pModelo) 
    {
        conexao.conectar();
        try
        {
            String sql = "UPDATE tblModelos SET tipoid=?, modelo=?, descricao=?, contrato=?, status=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1, pModelo.getTipoid());
            pst.setString(2, pModelo.getModelo());
            pst.setString(3, pModelo.getDescricao());
            pst.setString(4, pModelo.getContrato());   
            pst.setString(5, pModelo.getStatus());  
            pst.setInt   (6, pModelo.getCodigo());
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
    
    public Modelo pesquisarModeloDAO(Modelo pModelo) 
    {       
        try
        {
            conexao.conectar();
            sql = "SELECT * FROM tblModelos WHERE codigo = '" + pModelo.getCodigo() + "'";           
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                pModelo.setCodigo(conexao.rs.getInt("codigo"));
                pModelo.setTipoid(conexao.rs.getInt("tipoid"));
                pModelo.setModelo(conexao.rs.getString("modelo"));
                pModelo.setDescricao(conexao.rs.getString("descricao"));  
                pModelo.setDescricao(conexao.rs.getString("contrato"));  
                pModelo.setStatus(conexao.rs.getString("status"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return pModelo;
    }
        
    public boolean excluirModeloDAO(int pCodigo)
    {
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM tblModelos WHERE codigo = '" + pCodigo + "'";            
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
