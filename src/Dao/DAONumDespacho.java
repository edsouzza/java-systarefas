package Dao;

import biblioteca.MetodosPublicos;
import static biblioteca.VariaveisPublicas.sql;
import conexao.ConnConexao;
import controle.CtrlNumDespachos;
import java.sql.PreparedStatement;
import modelo.NumsDespacho;
import javax.swing.JOptionPane;

public class DAONumDespacho {

    ConnConexao conexao  = new ConnConexao();  
    MetodosPublicos umMetodo = new MetodosPublicos();
    CtrlNumDespachos umCtrl  = new CtrlNumDespachos();
    

    public boolean salvarNumsDespachoInicialDAO() {
        conexao.conectar();
        String numDesp = umMetodo.retornaMesAnoVigente();
        try {
            sql = "INSERT INTO TBLNUMDESPACHOS (codigo, numdespacho, status) VALUES (?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1, 1);
            pst.setString(2, numDesp);
            pst.setString(3, "DISPONIVEL");
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível executar o comando sql, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean salvarNumDespachoInicialTabelaVaziaDAO() {
        conexao.conectar();
        String anoVigente = String.valueOf(umMetodo.retornaAnoVigente());
        String numDesp = "1/"+anoVigente;                
        
        try {
            sql = "INSERT INTO TBLNUMDESPACHOS (codigo, numdespacho, status) VALUES (?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1, 1);
            pst.setString(2, numDesp);
            pst.setString(3, "DISPONIVEL");
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível executar o comando sql, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean salvarNumsDespachoDAO(NumsDespacho pNumsDespacho) {
        int codigoID = umMetodo.gerarCodigoID("TBLNUMDESPACHOS");

        conexao.conectar();
        try {
            sql = "INSERT INTO TBLNUMDESPACHOS (codigo, numdespacho, status) VALUES (?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1, codigoID);
            pst.setString(2, pNumsDespacho.getNumdespacho());
            pst.setString(3, "DISPONIVEL");
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível executar o comando sql, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean indisponibilizarStatusNumDespachoDAO(int pCodigo) 
    {
        conexao.conectar();      
        try
        {  
            conexao.ExecutarAtualizacaoSQL("UPDATE TBLNUMDESPACHOS SET status = 'INDISPONIVEL' WHERE codigo = '"+pCodigo+"'"); 
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro a o tentar atualizar o registro : \n"+e+", o sql passado foi \n"+sql);  
            return false;
       } finally {
            conexao.desconectar();
       }   
    }
    
    public boolean disponibilizarStatusNumDespachoDAO(int pCodigo) 
    {
        conexao.conectar();      
        try
        {  
            conexao.ExecutarAtualizacaoSQL("UPDATE TBLNUMDESPACHOS SET status = 'DISPONIVEL' WHERE codigo = '"+pCodigo+"'");  
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro a o tentar atualizar o registro : \n"+e+", o sql passado foi \n"+sql);   
            return false;
       } finally {
            conexao.desconectar();
       }   
    }

    public boolean excluirNumsDespachoDAO(int pCodigo) {
        conexao.conectar();
        try {
            sql = "DELETE FROM TBLNUMDESPACHOS WHERE codigo = '" + pCodigo + "'";
            conexao.ExecutarPesquisaSQL(sql);
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir o registro, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }

    public String getNumDespachoDisponivelDAO() {
        conexao.conectar();
        try {
            sql = "SELECT numdespacho FROM TBLNUMDESPACHOS WHERE status = 'DISPONIVEL'";
            conexao.ExecutarPesquisaSQL(sql);
            if (conexao.rs.next()) {
                return conexao.rs.getString("numdespacho");
            } else {
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro a o pesquisar a tabela, erro gerado : \n" + e + ", o sql passado foi \n" + sql);
            return null;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean temDespachoDisponivelDAO() {
        conexao.conectar();
        try {
            sql = "SELECT numdespacho FROM TBLNUMDESPACHOS WHERE status = 'DISPONIVEL'";
            conexao.ExecutarPesquisaSQL(sql);
            if (conexao.rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro a o pesquisar a tabela, erro gerado : \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
        
}