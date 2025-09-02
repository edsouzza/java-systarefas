package Dao;

import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.PreparedStatement;
import modelo.Informacao;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DAOInformacao {

    ConnConexao conexao  = new ConnConexao();
    
    public boolean salvarInformacaoDAO(Informacao pInfo) 
    {
        conexao.conectar();
        try 
        {
            String sql = "INSERT INTO tblInformacoes (instituicao, senha, status, obs) VALUES (?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pInfo.getInstituicao());
            pst.setString(2, pInfo.getSenha());  
            pst.setString(3, "ATIVO");
            pst.setString(4, pInfo.getObs());  
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean atualizarInformacaoDAO(Informacao pInfo) 
    {
        conexao.conectar();
        try
        {            
            String sql = "UPDATE tblInformacoes SET instituicao=?, senha=?, obs=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pInfo.getInstituicao());
            pst.setString(2, pInfo.getSenha());   
            pst.setString(3, pInfo.getObs());
            pst.setInt   (4, pInfo.getCodigo());
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
    
    public Informacao pesquisarInformacaoDAO(Informacao pInfo) 
    {       
        try
        {
            conexao.conectar();
            sql = "SELECT * FROM tblInformacoes WHERE codigo = '" + pInfo.getCodigo() + "'";           
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                pInfo.setCodigo(conexao.rs.getInt("codigo"));
                pInfo.setInstituicao(conexao.rs.getString("instituicao"));
                pInfo.setSenha(conexao.rs.getString("senha"));                
                pInfo.setStatus(conexao.rs.getString("status"));                
                pInfo.setObs(conexao.rs.getString("obs"));                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return pInfo;
    }
                            
    public ArrayList<Informacao> RecuperaObjetoSQL(String sql) 
    {      
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        
        ArrayList<Informacao> lstMemo = new ArrayList<>();
        try {
                        
            while (conexao.rs.next()) 
            {
                Integer vCodigo       = conexao.rs.getInt("codigo");
                String  vInstituicao  = conexao.rs.getString("instituicao");
                String  vSenha        = conexao.rs.getString("senha");                
                String  vStatus       = conexao.rs.getString("status");                
                String  vObs          = conexao.rs.getString("obs");                
                
                Informacao objInfo = new Informacao();   
                
                objInfo.setCodigo(vCodigo);
                objInfo.setInstituicao(vInstituicao);
                objInfo.setSenha(vSenha);
                objInfo.setStatus(vStatus);
                objInfo.setObs(vObs);
                
                lstMemo.add(objInfo);
            }
            conexao.desconectar();
        } catch (NumberFormatException | SQLException erro) {
            String erroMsg = "Erro ao recuperar objetos : " + erro.getMessage();
            JOptionPane.showMessageDialog(null, erroMsg, "Mensagem", JOptionPane.ERROR_MESSAGE);
        }

        return lstMemo;
    }
                  
}
