package Dao;

import biblioteca.MetodosPublicos;
import static biblioteca.VariaveisPublicas.nomeDepartamento;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import controle.CtrlNomeEstacao;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import modelo.NomeEstacao;

/**
 *
 * @author d631863
 */
public class DAONomeEstacao {

    ConnConexao         conexao                  = new ConnConexao();    
    CtrlNomeEstacao     umControleNomeEstacao    = new CtrlNomeEstacao();
    NomeEstacao         umModeloNomeEstacao      = new NomeEstacao();
    MetodosPublicos     umMetodo                 = new MetodosPublicos();
    
    public boolean salvarNomeEstacaoDAO(NomeEstacao pEstacao) 
    {
        conexao.conectar();
        try 
        {
            String sql = "INSERT INTO tblnomestacao (depto, nomestacao, numestacao, status) VALUES (?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pEstacao.getDepto());
            pst.setString(2, pEstacao.getNomestacao());
            pst.setInt(3, pEstacao.getNumestacao());            
            pst.setString(4, pEstacao.getStatus());
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean salvarNomeEstacaoInicialDAO() 
    {
        conexao.conectar();
        try 
        {
            String sql = "INSERT INTO tblnomestacao (depto, nomestacao, numestacao, status) VALUES (?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "CGGM");
            pst.setString(2, "PGMCGGMC000");
            pst.setInt(3, 0);            
            pst.setString(4, "DISPONIVEL");
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean atualizarStatusDoNomeEstacaoDAO(NomeEstacao pEstacao) 
    {
        conexao.conectar();
        try
        {
            String sql = "UPDATE tblnomestacao SET status=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pEstacao.getStatus());
            pst.setInt(2, pEstacao.getCodigo());
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
    
    public boolean atualizarStatusPeloNomeDaEstacaoDAO(NomeEstacao pEstacao) 
    {
        conexao.conectar();
        try
        {
            
            String sql = "UPDATE tblnomestacao SET status=? WHERE nomestacao=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pEstacao.getStatus());
            pst.setString(2, pEstacao.getNomestacao());
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
       
    public void indisponibilizarNomeEstacao(String pEstacao)
    {        
        int codigoEst = umMetodo.getCodigoPassandoString("TBLNOMESTACAO", "nomestacao", pEstacao);
        umModeloNomeEstacao.setCodigo(codigoEst);
        umModeloNomeEstacao.setStatus("INDISPONIVEL");
        umControleNomeEstacao.atualizarStatusNomeEstacao(umModeloNomeEstacao);      
    }
    
    public void disponibilizarNomeEstacao(String pEstacao)
    {        
        int codigoEst = umMetodo.getCodigoPassandoString("TBLNOMESTACAO", "nomestacao", pEstacao);
        umModeloNomeEstacao.setCodigo(codigoEst);
        umModeloNomeEstacao.setStatus("DISPONIVEL");
        umControleNomeEstacao.atualizarStatusNomeEstacao(umModeloNomeEstacao);      
    }
    
    public void cadastrarNomeComStatusIndisponivel(String pEstacao, String pDepto)
    {        
        umModeloNomeEstacao.setDepto(pDepto);
        umModeloNomeEstacao.setNomestacao(pEstacao); 
        umModeloNomeEstacao.setNumestacao(umMetodo.somenteDigitos(pEstacao));                            
        umModeloNomeEstacao.setStatus("INDISPONIVEL");
        umControleNomeEstacao.salvarNomeEstacao(umModeloNomeEstacao); 
        umMetodo.indisponibilizarStatusNomeEstacaoTMP(pEstacao); //tblnomestacaotmp
    }
    
    public NomeEstacao pesquisarNomeEstacaoDAO(NomeEstacao pNomeEstacao) 
    {       
        try
        {
            conexao.conectar();
            sql = "SELECT * FROM tblnomestacao WHERE codigo = '" + pNomeEstacao.getCodigo() + "'";           
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                pNomeEstacao.setCodigo(conexao.rs.getInt("codigo"));
                pNomeEstacao.setNomestacao(conexao.rs.getString("nomeestacao"));
                pNomeEstacao.setNumestacao(conexao.rs.getInt("numestacao"));
                pNomeEstacao.setDepto(conexao.rs.getString("depto"));
                pNomeEstacao.setStatus(conexao.rs.getString("status"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return pNomeEstacao;
    }      
    
    public boolean excluirNomeEstacaoDao(int pCodigo) 
    {
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM tblnomestacao WHERE codigo = "+pCodigo;   
            conexao.ExecutarAtualizacaoSQL(sql);      
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    } 
    
    public boolean excluirNomeEstacaoIndividualDao(int pCodigo) 
    {
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM tblnomestacao WHERE codigo = "+pCodigo+" AND status='DISPONIVEL'";   
            conexao.ExecutarAtualizacaoSQL(sql);      
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    } 
    
    public boolean excluirNomeEstacaoPorIntervaloDao(int pCodigo) 
    {
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM tblnomestacao WHERE codigo = "+pCodigo+" AND status='DISPONIVEL'";     
            conexao.ExecutarAtualizacaoSQL(sql);      
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    } 
                     
}