/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import biblioteca.MetodosPublicos;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static java.util.EnumSet.range;
import javax.swing.JOptionPane;
import modelo.Departamento;

/**
 *
 * @author d631863
 */
public class DAODepartamento {

    ConnConexao         conexao    = new ConnConexao();  
    MetodosPublicos     umMetodo   = new MetodosPublicos();
    
    public boolean salvarDepartamentoDAO(Departamento pDepartamento) 
    {
        conexao.conectar();
        try 
        {
            String sql = "INSERT INTO tblDepartamentos (nome,range,status) VALUES (?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pDepartamento.getNome());
            pst.setString(2, pDepartamento.getRange());
            pst.setString(3, pDepartamento.getStatus());
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    public boolean salvarDepartamentoInicialDAO() 
    {
        conexao.conectar();
        try 
        {
            String sql = "INSERT INTO tblDepartamentos (nome,range,status) VALUES (?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "CGGM");
            pst.setString(2, "PGMCGGMC");
            pst.setString(3, "ATIVO");
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
        
    }
    
    public boolean atualizarDepartamentoDAO(Departamento pDepartamento) 
    {
        conexao.conectar();
        try
        {
            String sql = "UPDATE tblDepartamentos SET nome=?, range=?, status=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pDepartamento.getNome());
            pst.setString(2, pDepartamento.getRange());
            pst.setString(3, pDepartamento.getStatus());
            pst.setInt   (4, pDepartamento.getCodigo());
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
    
    public Departamento pesquisarDepartamentoDAO(Departamento pDepartamento) 
    {       
        try
        {
            conexao.conectar();
            sql = "SELECT * FROM tblDepartamentos WHERE codigo = '" + pDepartamento.getCodigo() + "'";           
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                pDepartamento.setCodigo(conexao.rs.getInt("codigo"));
                pDepartamento.setNome(conexao.rs.getString("nome"));
                pDepartamento.setNome(conexao.rs.getString("range"));
                pDepartamento.setStatus(conexao.rs.getString("status"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return pDepartamento;
    }
        
    public boolean excluirDepartamentoDAO(int pCodigo)
    {
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM tblDepartamentos WHERE codigo = '" + pCodigo + "'";            
            conexao.ExecutarPesquisaSQL(sql);            
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    }  
    
     public boolean RegistroDuplicado(Departamento pDepartamento)
    {
        conexao.conectar();                          
        try 
        {              
            sql = "SELECT * FROM tblDepartamentos WHERE nome = '"+pDepartamento.getNome()+"'";
            conexao.ExecutarPesquisaSQL(sql);            
            if(conexao.rs.next())
            {
                //tipo de msg = 1 é a de informacao msg = 2 é a de exclamação  msg = 3 é a de interrogação
                JOptionPane.showMessageDialog(null,"Atenção o departamento digitado já esta cadastrado, verifique!","Operação não concluída por duplicidade no nome!",2);                
                return true;
                          
            }else{ return false; }            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa sobre duplicidade! "+ex);
             return false;
        }finally{
            conexao.desconectar();
        }
               
    }
                  
}
