package Dao;

import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.dataDoDia;
import static biblioteca.VariaveisPublicas.cadastrado;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.Tarefa;

public class DAOTarefa 
{
    ConnConexao conexao  = new ConnConexao();   
         
    public boolean salvarTarefaDAO(Tarefa pTarefa) 
    {
        conexao.conectar();
        try 
        {
            sql = "INSERT INTO tbltarefas (clienteid, usuarioid, tarefa, situacao, status, dtabertura) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1, pTarefa.getClienteid());
            pst.setInt(2, pTarefa.getUsuarioid());
            pst.setString(3, pTarefa.getTarefa());
            pst.setString(4, pTarefa.getSituacao());
            pst.setString(5, "ABERTA");
            pst.setDate(6, new java.sql.Date(dataDoDia.getTime()));    //grava um Timestamp no banco com data e hora
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
    
    public boolean AtualizarTarefaDAO(Tarefa pTarefa) 
    {
        conexao.conectar();
        try
        {
            sql = "UPDATE tbltarefas SET tarefa=?, situacao=?, status=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pTarefa.getTarefa());
            pst.setString(2, pTarefa.getSituacao());
            pst.setString(3, pTarefa.getStatus());
            pst.setInt(4, pTarefa.getCodigo());
            pst.executeUpdate();
            pst.close();  
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n "+e+" , o sql passado foi \n"+sql);  
            return false;
        }finally{
            conexao.desconectar();
        }
        
    }
    
    public boolean FecharTarefaDAO(Tarefa pTarefa) 
    {
        //fechando a tarefa com a data vigente
        conexao.conectar();
        try
        {
            sql = "UPDATE tbltarefas SET dtfechamento=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setDate(1, new java.sql.Date(dataDoDia.getTime()));    //grava um Timestamp no banco com data e hora
            pst.setInt(2, pTarefa.getCodigo());
            pst.executeUpdate();
            pst.close();  
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n "+e+" , o sql passado foi \n"+sql);   
            return false;
        }finally{
            conexao.desconectar();
        }
    }
   
    public Tarefa pesquisarTarefaDAO(String pTarefa) 
    {  
        Tarefa objTarefa = new Tarefa();    
        conexao.conectar();
        try
        { 
            sql = "SELECT * FROM tbltarefas like tarefa '%" + pTarefa + "%'";          
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next())
            {
                objTarefa.setCodigo(conexao.rs.getInt("codigo"));
                objTarefa.setClienteid(conexao.rs.getInt("clienteid"));
                objTarefa.setUsuarioid(conexao.rs.getInt("usuarioid"));
                objTarefa.setTarefa(conexao.rs.getString("tarefa"));
                objTarefa.setSituacao(conexao.rs.getString("situacao"));
                objTarefa.setStatus(conexao.rs.getString("status"));
                cadastrado = true;
            }
        } catch (Exception e) {
            cadastrado = false;
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return objTarefa;
    }
    
    public boolean excluirTarefaDAO(int pCodigo)
    {
        conexao.conectar();      
        try
        {  
            sql = "DELETE FROM tbltarefas WHERE codigo = '" + pCodigo + "'";            
            conexao.ExecutarPesquisaSQL(sql);            
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    }   
    
     public int buscarCodigo(String nome)
    {
        //retorna o codigo da seção selecionada
        conexao.conectar();      
        sql = "SELECT codigo FROM tblclientes WHERE nome = '"+nome+"'";           
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            conexao.rs.first();
            return conexao.rs.getInt("codigo");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return 0;
        }finally{
            conexao.desconectar();
        }  
    }    
    
}
