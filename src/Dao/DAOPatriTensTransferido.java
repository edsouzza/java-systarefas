package Dao;

import static biblioteca.VariaveisPublicas.sql;
import conexao.ConnConexao;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.PatriTensTransferido;
import javax.swing.JOptionPane;

public class DAOPatriTensTransferido {

    ConnConexao conexao  = new ConnConexao(); 
    
    public boolean salvarPatriItensTransferidoDAO(PatriTensTransferido pPatritransferido) 
    {
        conexao.conectar();
        try 
        {
            String sql = "INSERT INTO TBLITENSMEMOTRANSFERIDOS (item, numemo, modeloid, serie, chapa, origem, destino, status) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1, pPatritransferido.getItem());
            pst.setString(2, pPatritransferido.getNumemo());
            pst.setInt(3, pPatritransferido.getModeloid());
            pst.setString(4, pPatritransferido.getSerie());  
            pst.setString(5, pPatritransferido.getChapa());  
            pst.setString(6, pPatritransferido.getOrigem());                 
            pst.setString(7, pPatritransferido.getDestino());                 
            pst.setString(8, pPatritransferido.getStatus());
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }      
    
    public boolean excluirItensProcessandoDAO()
    {
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM TBLITENSMEMOTRANSFERIDOS WHERE status = 'PROCESSANDO'";            
            conexao.ExecutarAtualizacaoSQL(sql);         
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    } 
    
    public ArrayList<Integer> ListaItemsAposExclusao() 
    {    
        ArrayList listarItens = new ArrayList();
        
        conexao.conectar();  
        sql = "SELECT item FROM TBLITENSMEMOTRANSFERIDOS WHERE status = 'PROCESSANDO'";
        conexao.ExecutarPesquisaSQL(sql);
        
        try {
        while(conexao.rs.next()){
             listarItens.add(conexao.rs.getInt("item"));                                
          }   
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar a pesquisa, \n"+ex+", o sql passado foi \n"+sql);
        }    

//        for (Object dado : listarItens) {
//            System.out.println(dado); 
//        }
        return listarItens;
    }  
    
    
    public boolean memoAtualTemItensDAO()
    {
        //Verificando se o memorando em curso tem ítens cadastrado retornando sim ou nao        
        try
        {            
            conexao.conectar();       
            sql = "SELECT status FROM TBLITENSMEMOTRANSFERIDOS WHERE status = 'PROCESSANDO'";           
            conexao.ExecutarPesquisaSQL(sql);       
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível executar a pesquisa, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    }
    
    public boolean excluirItemDoMemoAtualDAO(int pCodItem)
    {
        //Excluir o ítem selecionado do memorando em curso
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM TBLITENSMEMOTRANSFERIDOS WHERE codigo = "+pCodItem;           
            conexao.ExecutarAtualizacaoSQL(sql);         
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
       }        
    } 
    
    public boolean excluirItemDoMemoAtualPeloNumemoDAO(int pCodItem, String pNuMemo)
    {
        //Excluir o ítem selecionado do memorando em curso
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM TBLITENSMEMOTRANSFERIDOS WHERE codigo = "+pCodItem+" AND numemo = "+pNuMemo;           
            conexao.ExecutarAtualizacaoSQL(sql);         
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
       }        
    } 
            
    public boolean excluirItensDoMemoSelecionadoDAO(String pNumemo)
    {
        //Exluir todos os ítem do memorando selecionado
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM TBLITENSMEMOTRANSFERIDOS WHERE numemo = '"+pNumemo+"'";            
            conexao.ExecutarAtualizacaoSQL(sql);         
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    }       
    
    public boolean atualizarValorDosItensAposExclusaoDAO(int pItemExcluido, String pNumemo)
    {
        /*Esta atualização refere-se aos ítens subsequentes ao ítem excluido, entao eu pego o valor do ítem excluido e somo+1 -> isso informa qual 
          será o primeiro ítem a ser atualizado Ex: se informa em pItemExcluido = 4 então o primeiro a ser alterado será o 5 com valor 5+1=6  e a
          parte da SQL que diz (item >= "+pItemUpdate+) informa que todos que forem > que o ítem 4 serão atualizados com + 1*/
        int pItemForUpdate = pItemExcluido + 1;
        try
        {            
            conexao.conectar();            
            sql = "UPDATE TBLITENSMEMOTRANSFERIDOS SET item = item-1 WHERE item >="+pItemForUpdate+" AND numemo = '"+pNumemo+"'";            
            conexao.ExecutarAtualizacaoSQL(sql);         
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    }         
             
}