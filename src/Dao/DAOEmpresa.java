package Dao;

import static biblioteca.VariaveisPublicas.dataDoDia;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Empresa;
import javax.swing.JOptionPane;

public class DAOEmpresa {

    ConnConexao conexao  = new ConnConexao();    
    
    public boolean salvarEmpresaDAO(Empresa pEmpresa) 
    {
        conexao.conectar();
        try 
        {
            String sql = "INSERT INTO TBLEMPRESA (nome, datacad, status, obs ) VALUES (?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pEmpresa.getNome());           
            pst.setDate(2, new java.sql.Date(dataDoDia.getTime()));   
             pst.setString(3, "ATIVO");
            pst.setString(4, pEmpresa.getObs());           
           
            pst.executeUpdate();             
            JOptionPane.showMessageDialog(null, "Empresa do novo contrato de impressoras cadastrada com sucesso cadastre as impressoras!!","Cadastro da empresa do novo contrato de impressoras!",2);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean atualizarEmpresaDAO(Empresa pEmpresa)
    {
        conexao.conectar();
        try
        {
            //optei por nao atualizar o RF pra nao correr o risco de duplicidades
            sql = "UPDATE TBLEMPRESA SET nome=?, status=?, obs=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pEmpresa.getNome());
            pst.setString(2, pEmpresa.getStatus()); 
            pst.setString(3, pEmpresa.getObs()); 
            pst.setInt   (4, pEmpresa.getCodigo());
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
    
    public boolean atualizarStatusEmpresaDAO(int pCod, String pStatus)
    {                                  
        try 
        {   
            conexao.conectar();
            sql = "UPDATE TBLEMPRESA SET status = '"+pStatus+"' WHERE codigo = '"+pCod+"' ";
            conexao.ExecutarAtualizacaoSQL(sql);
            return true;           
       }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }          
    }    
    
    public boolean inativarEmpresaDoContratoImpressorasDAO()
    {                                  
        try 
        {   
            conexao.conectar();
            sql = "UPDATE tblempresa SET status='INATIVO' WHERE codigo=(select max(codigo) from tblempresa)";
            conexao.ExecutarAtualizacaoSQL(sql);
            return true;           
       }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }          
    }    
    
    public boolean atualizarNomeEmpresaDAO(int pCod, String pNome)
    {                                  
        try 
        {   
            conexao.conectar();
            sql = "UPDATE TBLEMPRESA SET nome = '"+pNome+"' WHERE codigo = '"+pCod+"' ";
            conexao.ExecutarAtualizacaoSQL(sql);
            return true;           
       }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }          
    }        
       
     public boolean RegistroDuplicado(Empresa pEmpresa)
    {
        conexao.conectar();                          
        try 
        {              
            sql = "SELECT * FROM TBLEMPRESA WHERE nome = '"+pEmpresa.getNome()+"'";
            conexao.ExecutarPesquisaSQL(sql);            
            if(conexao.rs.next())
            {
                //tipo de msg = 1 é a de informacao msg = 2 é a de exclamação  msg = 3 é a de interrogação
                JOptionPane.showMessageDialog(null,"Atenção o nome "+pEmpresa.getNome()+" digitado já esta cadastrado, verifique!","Operação não concluída por duplicidade no nome!",2);                
                return true;
                          
            }else{ return false; }            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa sobre duplicidade! "+ex);
             return false;
        }finally{
            conexao.desconectar();
        }
               
    }
        
    public boolean excluirEmpresaDAO(int pCodigoCliente)
    {
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM TBLEMPRESA WHERE codigo = '" + pCodigoCliente + "'";            
            conexao.ExecutarPesquisaSQL(sql);            
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    }    
    
    public void inativarEmpresasInativasDAO(int pCod)
    {                                  
        try 
        {   
            conexao.conectar();
            sql = "UPDATE TBLEMPRESA SET status = 'INATIVO' WHERE codigo = '"+pCod+"'";
            conexao.ExecutarAtualizacaoSQL(sql);                       
       }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n"+e+", o sql passado foi \n"+sql);           
       } finally {
            conexao.desconectar();
        }          
    }    
       
}