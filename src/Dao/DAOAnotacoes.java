package Dao;

import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import modelo.Anotacoes;
import javax.swing.JOptionPane;

public class DAOAnotacoes {

   ConnConexao conexao = new ConnConexao();   
   Connection  conn    = null; 
    
    public boolean salvarAnotacoesDAO(Anotacoes pAnotacoes) 
    {
        conn = conexao.conectar();
        try 
        {
            sql = "INSERT INTO tblanotacoes (codigo, ocorrencia) VALUES (?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, 1);
            pst.setString(2, pAnotacoes.getOcorrencia());
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível cadastrar a ocorrência \n"+e+", o sql passado foi \n"+sql); 
            return false;
        } finally {
            conexao.desconectar();
        }
    }  
    
    public boolean atualizarAnotacoesDAO(Anotacoes pAnotacoes) 
    {
        conn = conexao.conectar();
        try 
        {
            sql = "UPDATE tblanotacoes SET ocorrencia=? WHERE codigo=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, pAnotacoes.getOcorrencia());
            pst.setInt(2, 1);
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar a nova versao do arquivo \n"+e+", o sql passado foi \n"+sql); 
            return false;
        } finally {
            conexao.desconectar();
        }
    }  
    
    public Anotacoes pesquisarAnotacoesDAO(Anotacoes pAnotacoes) 
    {       
        conn = conexao.conectar();    
        sql = "SELECT codigo, ocorrencia FROM tblanotacoes WHERE codigo = 1";           
        conexao.ExecutarPesquisaSQL(sql);
        try
        {
            while (conexao.rs.next()) {
                pAnotacoes.setCodigo(conexao.rs.getInt("codigo"));
                pAnotacoes.setOcorrencia(conexao.rs.getString("ocorrencia"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível pesquisar a ocorrencia \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return pAnotacoes;
    }
    
}
