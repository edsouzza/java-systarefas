package Dao;

import biblioteca.MetodosPublicos;
import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.diaVigente;
import static biblioteca.VariaveisPublicas.mesVigente;
import static biblioteca.VariaveisPublicas.anoVigente;
import conexao.ConnConexao;
import java.sql.PreparedStatement;
import modelo.PatriTransferido;
import javax.swing.JOptionPane;

public class DAOPatriTransferido {

    ConnConexao conexao         = new ConnConexao(); 
    MetodosPublicos umMetodo    = new MetodosPublicos();
    String diaCadastro          = diaVigente;
    String mesCadastro          = mesVigente;
    String anoCadastro          = anoVigente;
    String dataCadastro         = diaVigente+" "+umMetodo.getNomeMes(Integer.valueOf(mesVigente))+" de "+anoVigente;    
        
    public boolean salvarPatriTransferidoDAO(PatriTransferido pPatritransferido) 
    {
        conexao.conectar();
        try 
        {
            String sql = "INSERT INTO TBLMEMOSTRANSFERIDOS (idusuario, numemo, datacad, status, assunto, observacao) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1, pPatritransferido.getIdusuario());             
            pst.setString(2, pPatritransferido.getNumemo());             
            pst.setString(3, dataCadastro);
            pst.setString(4, pPatritransferido.getStatus());
            pst.setString(5, pPatritransferido.getAssunto());
            pst.setString(6, pPatritransferido.getObservacao());
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }     
    
    public boolean editarPatriTransferidoDAO(PatriTransferido pPatritransferido) 
    {
        conexao.conectar();
        try 
        {
            String sql = "UPDATE TBLMEMOSTRANSFERIDOS SET observacao=? WHERE numemo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);    
            pst.setString(1, pPatritransferido.getObservacao());
            pst.setString(2, pPatritransferido.getNumemo());   
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql de atualização do registro, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }                     
    
    public PatriTransferido pesquisarPatriTransferidoDAO(PatriTransferido pPatriTransferido)
    {       
        try
        {
            conexao.conectar();
            sql = "SELECT * FROM TBLMEMOSTRANSFERIDOS  WHERE (codigo = '"+pPatriTransferido.getCodigo()+"') OR (numemo = '"+pPatriTransferido.getNumemo()+"')";            
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                pPatriTransferido.setCodigo(conexao.rs.getInt("codigo"));
                pPatriTransferido.setNumemo(conexao.rs.getString("numemo"));
                pPatriTransferido.setStatus(conexao.rs.getString("status"));
                pPatriTransferido.setAssunto(conexao.rs.getString("assunto"));
                pPatriTransferido.setObservacao(conexao.rs.getString("observacao"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return pPatriTransferido;
    }    
    
    public boolean excluirMemoTransferidoDAO(String pNumemo)
    {
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM TBLMEMOSTRANSFERIDOS WHERE numemo = '"+pNumemo+"'";            
            conexao.ExecutarAtualizacaoSQL(sql);         
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    }  
    
    public String getMemoComStatusProcessandoDAO()
    {
        try
        {            
            conexao.conectar();            
            sql = "SELECT m.numemo, i.numemo FROM TBLMEMOSTRANSFERIDOS m, TBLITENSMEMOTRANSFERIDOS i WHERE m.numemo = i.numemo";            
            conexao.ExecutarPesquisaSQL(sql);  
            while (conexao.rs.next()) {
               return conexao.rs.getString("numemo");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);            
        } finally {
            conexao.desconectar();
        }  
        return "";
    }  
    
    
    
}