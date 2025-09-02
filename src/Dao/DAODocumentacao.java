package Dao;

import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.PreparedStatement;
import modelo.Documentacao;
import javax.swing.JOptionPane;

public class DAODocumentacao {

    ConnConexao conexao  = new ConnConexao();
    
    public boolean salvarDocumentacaoDAO(Documentacao pDoc) 
    {
        conexao.conectar();
        try 
        {
            String sql = "INSERT INTO tblDocumentacao (descricao, detalhes) VALUES (?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pDoc.getDescricao());
            pst.setString(2, pDoc.getDetalhes());            
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean atualizarDocumentacaoDAO(Documentacao pDoc) 
    {
        conexao.conectar();
        try
        {
            //optei por nao atualizar o RF pra nao correr o risco de duplicidades
            String sql = "UPDATE tblDocumentacao SET descricao=?, detalhes=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pDoc.getDescricao());
            pst.setString(2, pDoc.getDetalhes());
            pst.setInt   (3, pDoc.getCodigo());
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
    
    public Documentacao pesquisarDocumentacaoDAO(Documentacao pDoc) 
    {       
        try
        {
            conexao.conectar();
            sql = "SELECT * FROM tblDocumentacao WHERE codigo = '" + pDoc.getCodigo() + "'";           
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                pDoc.setCodigo(conexao.rs.getInt("codigo"));
                pDoc.setDescricao(conexao.rs.getString("descricao"));
                pDoc.setDetalhes(conexao.rs.getString("detalhes"));                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return pDoc;
    }
        
    public boolean excluirDocumentacaoDAO(int pCodigoMemo)
    {
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM tblDocumentacao WHERE codigo = '" + pCodigoMemo + "'";            
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