package Dao;

import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.PreparedStatement;
import modelo.Memorando;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DAOMemorando {

    ConnConexao conexao  = new ConnConexao();
    
    public boolean salvarMemorandoDAO(Memorando pMemo) 
    {
        conexao.conectar();
        try 
        {
            String sql = "INSERT INTO tblMemorandos (assunto, memorando) VALUES (?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pMemo.getAssunto());
            pst.setString(2, pMemo.getMemorando());            
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean atualizarMemorandoDAO(Memorando pMemo) 
    {
        conexao.conectar();
        try
        {
            //optei por nao atualizar o RF pra nao correr o risco de duplicidades
            String sql = "UPDATE tblMemorandos SET assunto=?, memorando=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pMemo.getAssunto());
            pst.setString(2, pMemo.getMemorando());
            pst.setInt   (3, pMemo.getCodigo());
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
    
    public Memorando pesquisarMemorandoDAO(Memorando pMemo) 
    {       
        try
        {
            conexao.conectar();
            sql = "SELECT * FROM tblMemorandos WHERE codigo = '" + pMemo.getCodigo() + "'";           
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                pMemo.setCodigo(conexao.rs.getInt("codigo"));
                pMemo.setAssunto(conexao.rs.getString("assunto"));
                pMemo.setMemorando(conexao.rs.getString("memorando"));                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return pMemo;
    }
        
    public boolean excluirMemorandoDAO(int pCodigoMemo)
    {
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM tblMemorandos WHERE codigo = '" + pCodigoMemo + "'";            
            conexao.ExecutarPesquisaSQL(sql);            
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    }    
                  
    public ArrayList<Memorando> RecuperaObjetoSQL(String sql) 
    {      
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        
        ArrayList<Memorando> lstMemo = new ArrayList<>();
        try {
                        
            while (conexao.rs.next()) 
            {
                Integer vCodigo  = conexao.rs.getInt("codigo");
                String vAssunto  = conexao.rs.getString("assunto");
                String vMemo     = conexao.rs.getString("memorando");                
                
                Memorando objCliente = new Memorando();   
                
                objCliente.setCodigo(vCodigo);
                objCliente.setAssunto(vAssunto);
                objCliente.setMemorando(vMemo);
                
                lstMemo.add(objCliente);
            }
            conexao.desconectar();
        } catch (NumberFormatException | SQLException erro) {
            String erroMsg = "Erro ao recuperar objetos : " + erro.getMessage();
            JOptionPane.showMessageDialog(null, erroMsg, "Mensagem", JOptionPane.ERROR_MESSAGE);
        }

        return lstMemo;
    }
                  
}
