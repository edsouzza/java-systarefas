package Dao;

import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.PreparedStatement;
import modelo.Patrimovel;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DAOPatriMovel {

    ConnConexao conexao  = new ConnConexao(); 
    
    public boolean salvarPatrimovelDAO(Patrimovel pPatrimovel) 
    {
        conexao.conectar();
        try 
        {
            sql = "INSERT INTO tblPatrimovel (serial, chapa, modeloid, secaoid, status, obs) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pPatrimovel.getSerial());
            pst.setString(2, pPatrimovel.getChapa());                   
            pst.setInt(3, pPatrimovel.getModeloid());            
            pst.setInt(4, pPatrimovel.getSecaoid());            
            pst.setString(5, "ATIVO");
            pst.setString(6, pPatrimovel.getObs());
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean atualizarPatrimovelDAO(Patrimovel pPatrimovel)
    {
        conexao.conectar();
        try
        {
            sql = "UPDATE tblPatrimovel SET serial=?, chapa=?, modeloid=?, secaoid=?, status=?, obs=?  WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1,pPatrimovel.getSerial());
            pst.setString(2,pPatrimovel.getChapa()); 
            pst.setInt(3, pPatrimovel.getModeloid()); 
            pst.setInt(4, pPatrimovel.getSecaoid());        
            pst.setString(5, pPatrimovel.getStatus());    
            pst.setString(6, pPatrimovel.getObs());
            pst.setInt(7, pPatrimovel.getCodigo());
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
            
    public boolean excluirPatrimovelDAO(int pCodigoPatrimovel)
    {
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM tblPatrimovel WHERE codigo = '" + pCodigoPatrimovel + "'";            
            conexao.ExecutarPesquisaSQL(sql);            
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    }  
    
    public boolean duplicidadeSerial(String serial)
    {
        //pesquisa se um registro existe na tabela passando como parametro uma string : metodo refeito somente porque voce nao colocou o campo como serie e sim serial
        conexao.conectar();              
        sql = "SELECT serial FROM tblpatrimovel WHERE  serial = '"+serial+"' and serial <> '0'";           
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next()) {
                //JOptionPane.showMessageDialog(null, "Registro encontrado");
                return true;
                
            } else {
                //JOptionPane.showMessageDialog(null, "Registro nao encontrado");
                return false;
            }
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return false;
        }finally{
            conexao.desconectar();
        }  
    } 
    
    public boolean duplicidadeChapa(String chapa)
    {
        //pesquisa se um registro existe na tabela passando como parametro uma string : metodo refeito somente porque voce nao colocou o campo como serie e sim serial
        conexao.conectar();      
        sql = "SELECT chapa FROM tblpatrimovel WHERE  chapa = '"+chapa+"' and chapa <> '0'";           
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            if (conexao.rs.next()) {
                //JOptionPane.showMessageDialog(null, "Registro encontrado");
                return true;
                
            } else {
                //JOptionPane.showMessageDialog(null, "Registro nao encontrado");
                return false;
            }
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return false;
        }finally{
            conexao.desconectar();
        }  
    } 
                   
    public boolean RegistroDuplicado(String paramSERIAL, String paramCHAPA)
    {
        //nesta verificação de duplicidade verificará sempre se existe um numero em SERIAL, CHAPA ignorando os registros com 0
        conexao.conectar(); 
        try 
        {              
            sql = "SELECT p.serial, p.chapa, s.nome FROM tblPatrimovel p, tblsecoes s WHERE (p.serial = '"+paramSERIAL+"' "
                + "and p.serial <> '0' or p.chapa = '"+paramCHAPA+"' and p.chapa <> '0') and p.secaoid=s.codigo and p.status='ATIVO' ";
                       
            conexao.ExecutarPesquisaSQL(sql);            
            if(conexao.rs.next())
            {
                //tipo de msg = 1 é a de informacao msg = 2 é a de exclamação  msg = 3 é a de interrogação
                JOptionPane.showMessageDialog(null,"Atenção um patrimônio com este serial,chapa ou nome de estação já esta cadastrado, verifique!","Operação não concluída por duplicidade de patrimônio!",2);                
                return true;
                          
            }else{ return false; }            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa sobre duplicidade! "+ex);
             return false;
        }finally{
            conexao.desconectar();
        }
               
    }
    
    public int buscarCodigoSecao(String nome)
    {
        //retorna o codigo da seção selecionada
        conexao.conectar();      
        sql = "SELECT codigo FROM tblsecoes WHERE nome = '"+nome+"' ";           
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
    
    public int buscarCodigoPatrimovel(String pSerieChapa)
    {
        //retorna o codigo do patrimonio pelo serial ou chapa
        conexao.conectar();      
        sql = "SELECT codigo FROM tblpatrimovel WHERE serial = '"+pSerieChapa+"' OR chapa = '"+pSerieChapa+"' ";           
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
    
    public boolean reativarPatrimovelDAO(int codigoPatrimonio, String motivo)
    {
        conexao.conectar();
        sql = "UPDATE tblpatrimovel SET status='ATIVO' WHERE codigo = "+codigoPatrimonio;      
        conexao.ExecutarAtualizacaoSQL(sql); 
        return true;        
    }
       
}