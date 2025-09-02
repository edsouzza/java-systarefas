package Dao;

import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.data;
import static biblioteca.VariaveisPublicas.hora;
import static biblioteca.VariaveisPublicas.dataDoDia;
import java.sql.PreparedStatement;
import modelo.Log;
import javax.swing.JOptionPane;

public class DAOLog {

    ConnConexao conexao  = new ConnConexao();
    
    public boolean salvarLogDAO(Log pLog) 
    {
        conexao.conectar();
        try 
        {
            String sql = "INSERT INTO tbllogs (ocorrencia, data, hora) VALUES (?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pLog.getOcorrencia());
            pst.setDate(2, new java.sql.Date(dataDoDia.getTime()));    //grava um Timestamp no banco com data e hora
            pst.setString(3, pLog.getHora());  //hora de abertura
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
            return false;
        } finally {
            conexao.desconectar();
        }
    }  
    public boolean salvarLogInicialDAO() 
    {
        conexao.conectar();
        try 
        {
            String sql = "INSERT INTO tbllogs (ocorrencia, data, hora) VALUES (?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "Sistema inicializado em "+data+" as "+hora);
            pst.setDate(2, new java.sql.Date(dataDoDia.getTime()));  //grava um Timestamp no banco com data e hora
            pst.setString(3, hora);  //hora de abertura
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
            return false;
        } finally {
            conexao.desconectar();
        }
    }  
    
}
