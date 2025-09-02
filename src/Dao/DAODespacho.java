package Dao;

import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.dataDoDia;
import conexao.ConnConexao;
import java.sql.PreparedStatement;
import modelo.Despacho;
import javax.swing.JOptionPane;


public class DAODespacho {

    ConnConexao conexao  = new ConnConexao();
   
    public boolean salvarDespachoDAO(Despacho pDespacho) 
    {
        conexao.conectar();
        try 
        {
            sql = "INSERT INTO tblDespachos (numdespachoid, tipodocumentoid, mesano, assunto, destino, cadastranteid, data, obs, status) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1, pDespacho.getNumdespachoid());
            pst.setInt(2, pDespacho.getTipodocumentoid());
            pst.setString(3, pDespacho.getMesano());  
            pst.setString(4, pDespacho.getAssunto());  
            pst.setString(5, pDespacho.getDestino());  
            pst.setInt(6, pDespacho.getCadastranteid()); 
            pst.setDate(7, new java.sql.Date(dataDoDia.getTime()));    //grava um Timestamp no banco com data e hora
            pst.setString(8, pDespacho.getObs());
            pst.setString(9, "ATIVO");
            pst.executeUpdate(); 
            pst.close(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean AtualizarDespachoDAO(Despacho pDespacho)
    {
        conexao.conectar();
        try
        {
            sql = "UPDATE tblDespachos SET assunto=?, tipodocumentoid=?, destino=?, obs=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);   
            pst.setString(1, pDespacho.getAssunto());  
            pst.setInt(2, pDespacho.getTipodocumentoid());
            pst.setString(3, pDespacho.getDestino());  
            pst.setString(4, pDespacho.getObs());
            pst.setInt(5, pDespacho.getCodigo());
            pst.executeUpdate(); 
            pst.close(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
        
    }
           
    public boolean excluirDespachoDAO(int pCodigo)
    {
        conexao.conectar();      
        try
        {  
            sql = "DELETE FROM tblDespachos WHERE codigo = '" + pCodigo + "'";            
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
    
