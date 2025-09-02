package Dao;

import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.PreparedStatement;
import modelo.Cliente;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DAOCliente {

    ConnConexao conexao  = new ConnConexao();    
    
    public boolean salvarClienteDAO(Cliente pCliente) 
    {
        conexao.conectar();
        try 
        {
            sql = "INSERT INTO tblClientes (nome, rf, status, secaoid, deptoid, tipovirtualid, tipo, obs) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pCliente.getNome());
            pst.setString(2, pCliente.getRf());            
            pst.setString(3, "ATIVO");
            pst.setInt(4, pCliente.getSecaoid());
            pst.setInt(5, pCliente.getDeptoid());
            pst.setInt(6, pCliente.getTipovirtualid());
            pst.setString(7, pCliente.getTipo());
            pst.setString(8, pCliente.getObs());
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean salvarClienteVirtualDAO(Cliente pCliente) 
    {
        conexao.conectar();
        try 
        {
            sql = "INSERT INTO tblClientes (nome, rf, status, secaoid, deptoid, tipovirtualid, tipo, obs) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pCliente.getNome());
            pst.setString(2, pCliente.getRf());            
            pst.setString(3, pCliente.getStatus());
            pst.setInt(4, pCliente.getSecaoid());
            pst.setInt(5, pCliente.getDeptoid());
            pst.setInt(6, pCliente.getTipovirtualid());
            pst.setString(7, pCliente.getTipo());
            pst.setString(8, pCliente.getObs());
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean atualizarClienteDAO(Cliente pCliente)
    {
        conexao.conectar();
        try
        {
            //optei por nao atualizar o RF pra nao correr o risco de duplicidades
            sql = "UPDATE tblClientes SET nome=?, rf=?, status=?, secaoid=?, deptoid=?, tipovirtualid=?, tipo=?, obs=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pCliente.getNome());
            pst.setString(2, pCliente.getRf());
            pst.setString(3, pCliente.getStatus());
            pst.setInt   (4, pCliente.getSecaoid());  
            pst.setInt   (5, pCliente.getDeptoid());  
            pst.setInt   (6, 0);  
            pst.setString(7, pCliente.getTipo());
            pst.setString(8, pCliente.getObs());
            pst.setInt   (9, pCliente.getCodigo());
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
    
    public Cliente pesquisarClienteDAO(Cliente pCliente) 
    {       
        try
        {
            conexao.conectar();
            sql = "SELECT * FROM tblClientes WHERE codigo = '" + pCliente.getCodigo() + "' OR nome = '" + pCliente.getNome() + "'";           
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                pCliente.setCodigo(conexao.rs.getInt("codigo"));
                pCliente.setNome(conexao.rs.getString("nome"));
                pCliente.setRf(conexao.rs.getString("rf"));                
                pCliente.setStatus(conexao.rs.getString("status"));
                pCliente.setObs(conexao.rs.getString("obs"));
                pCliente.setSecaoid(conexao.rs.getInt("secaoid"));
                pCliente.setDeptoid(conexao.rs.getInt("deptoid"));
                pCliente.setTipovirtualid(conexao.rs.getInt("tipovirtualid"));
                pCliente.setTipo(conexao.rs.getString("tipo"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return pCliente;
    }
    
    public int pesquisarCodigoClientePeloNome(String nome)
    {
        int vCodigo = 0;
        try
        {
            conexao.conectar();
            sql = "SELECT codigo FROM tblClientes WHERE nome = '" + nome + "'";  
            JOptionPane.showMessageDialog(null, sql);
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next())
            {
                vCodigo  = (conexao.rs.getInt("codigo"));
                
            }            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }    
        return vCodigo;
    }
    
    public boolean excluirClienteDAO(int pCodigoCliente)
    {
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM tblClientes WHERE codigo = '" + pCodigoCliente + "'";            
            conexao.ExecutarPesquisaSQL(sql);            
            return true;            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    }    
           
    public boolean atualizarNomesClientesVirtuaisDAO(int pCod, String pNome )
    {                                  
        try 
        {   
            conexao.conectar();
            sql = "UPDATE tblclientes SET nome = '"+pNome+"' WHERE codigo = "+pCod+" ";
            conexao.ExecutarAtualizacaoSQL(sql);
            return true;           
       }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }          
    }    
    
    public boolean atualizarNomeSemUsuarioDAO(int pCod, String pNome )
    {                                  
        try 
        {   
            conexao.conectar();
            sql = "UPDATE tblclientes SET nome = '"+pNome+"' WHERE codigo = "+pCod+" ";
            conexao.ExecutarAtualizacaoSQL(sql);
            return true;           
       }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }          
    }    
    
    public boolean atualizarStatusClientesVirtuaisColetivoDAO(int pCod, String pStatus )
    {                                  
        try 
        {   
            conexao.conectar();
            sql = "UPDATE tblclientes SET status = '"+pStatus+"' WHERE codigo = "+pCod+" ";
            conexao.ExecutarAtualizacaoSQL(sql);
            return true;           
       }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }          
    }    
    
    public boolean RegistroDuplicado(Cliente pCliente)
    {
        conexao.conectar();                          
        try 
        {              
            sql = "SELECT * FROM tblClientes WHERE rf = '"+pCliente.getRf()+"'";
            conexao.ExecutarPesquisaSQL(sql);            
            if(conexao.rs.next())
            {
                //tipo de msg = 1 é a de informacao msg = 2 é a de exclamação  msg = 3 é a de interrogação
                JOptionPane.showMessageDialog(null,"Atenção o RF digitado já esta cadastrado, verifique!","Operação não concluída por duplicidade no RF!",2);                
                return true;
                          
            }else{ return false; }            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa sobre duplicidade! "+ex);
             return false;
        }finally{
            conexao.desconectar();
        }               
    }    

    public ArrayList<Cliente> RecuperaObjetoSQL(String sql) 
    {      
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        
        ArrayList<Cliente> lstCliente = new ArrayList<>();
        try {
                        
            while (conexao.rs.next()) 
            {
                Integer vCodigo  = conexao.rs.getInt("codigo");
                String vNome     = conexao.rs.getString("nome");
                String vRF       = conexao.rs.getString("rf");                
                String vStatus   = conexao.rs.getString("status");
                String vObs      = conexao.rs.getString("obs");
                Integer vSecaoid = conexao.rs.getInt("secaoid");
                Integer vDeptoid = conexao.rs.getInt("deptoid");
                String vTipo     = conexao.rs.getString("tipo");
                
                Cliente objCliente = new Cliente();   
                
                objCliente.setCodigo(vCodigo);
                objCliente.setNome(vNome);
                objCliente.setRf(vRF);
                objCliente.setSecaoid(vSecaoid);
                objCliente.setDeptoid(vDeptoid);
                objCliente.setObs(vObs);
                objCliente.setStatus(vStatus);
                objCliente.setTipo(vTipo);
                
                lstCliente.add(objCliente);
            }
            conexao.desconectar();
        } catch (NumberFormatException | SQLException erro) {
            String erroMsg = "Erro ao recuperar objetos : " + erro.getMessage();
            JOptionPane.showMessageDialog(null, erroMsg, "Mensagem", JOptionPane.ERROR_MESSAGE);
        }

        return lstCliente;
    }
    
    
    public ArrayList<Cliente> PesquisaObjeto(String sCampo, String sValor, boolean bTodaParte){
        String sql = "select * from tblclientes where " + sCampo + " like '";
        //se nao definir em toda parte considerará apenas palavras que começam com...
        if(bTodaParte)
            sql = sql + "%";
            sql = sql + sValor + "%'";
            sql = sql + "Order by nome";
        
        return RecuperaObjetoSQL(sql);
    }
       
}