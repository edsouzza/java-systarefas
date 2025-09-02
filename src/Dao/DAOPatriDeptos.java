package Dao;

import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import modelo.PatriDepto;


public class DAOPatriDeptos {

    ConnConexao       conexao  = new ConnConexao(); 
    SimpleDateFormat  sdf      = new SimpleDateFormat("dd/MM/yyyy");
    
    public boolean salvarPatriDeptosDAO(PatriDepto umPatriDepto) 
    {
        conexao.conectar();
        try 
        {
            sql = "INSERT INTO TBLPATRIDEPTOS (tipoid, modeloid, serie, chapa, origem, dtentrada, memoenvio, memodevolucao, status, obs) VALUES (?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1, umPatriDepto.getTipoid());
            pst.setInt(2, umPatriDepto.getModeloid());
            pst.setString(3, umPatriDepto.getSerie());
            pst.setString(4, umPatriDepto.getChapa());
            pst.setString(5, umPatriDepto.getOrigem());
            pst.setString(6, umPatriDepto.getDtentrada());
            pst.setString(7, "N");
            pst.setString(8, "N");
            pst.setString(9, umPatriDepto.getStatus());
            pst.setString(10, umPatriDepto.getObs());            
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
    
    public boolean AtualizarPatriDeptosDAO(PatriDepto umPatriDeptos) 
    {
        conexao.conectar();
        try
        {
            sql = "UPDATE TBLPATRIDEPTOS SET tipoid=?, modeloid=?, serie=?, chapa=?, origem=?, destino=?, dtenvio=?, memoenvio=?, dtdevolucao=?, memodevolucao=?, status=?, obs=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1, umPatriDeptos.getTipoid());
            pst.setInt(2, umPatriDeptos.getModeloid());
            pst.setString(3, umPatriDeptos.getSerie());
            pst.setString(4, umPatriDeptos.getChapa());
            pst.setString(5, umPatriDeptos.getOrigem());
            pst.setString(6, umPatriDeptos.getDestino());
            pst.setString(7, umPatriDeptos.getDtenvio());
            pst.setString(8, umPatriDeptos.getMemoenvio());
            pst.setString(9, umPatriDeptos.getDtdevolucao()); 
            pst.setString(10, umPatriDeptos.getMemodevolucao()); 
            pst.setString(11, umPatriDeptos.getStatus());
            pst.setString(12, umPatriDeptos.getObs());
            pst.setInt(13, umPatriDeptos.getCodigo());
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
    
    public PatriDepto pesquisarPatriDeptoDAO(PatriDepto umPatridepto) 
    {    
        conexao.conectar();
        try
        {       
            sql =     "SELECT p.*, m.modelo FROM tblpatrideptos p, tblmodelos m WHERE "
                    + "(p.serie = '"+umPatridepto.getSerie()+"' OR p.chapa = '"+umPatridepto.getChapa()+"' "
                    + "OR p.origem = '"+umPatridepto.getOrigem()+"' OR p.destino = '"+umPatridepto.getDestino()+"' "
                    + "OR p.codigo = '"+umPatridepto.getCodigo()+"') "
                    + "AND p.modeloid = m.codigo ORDER BY p.codigo";      
            
            conexao.ExecutarPesquisaSQL(sql);
            
            while (conexao.rs.next())
            {
                umPatridepto.setCodigo(conexao.rs.getInt("codigo"));
                umPatridepto.setTipoid(conexao.rs.getInt("tipoid"));
                umPatridepto.setSerie(conexao.rs.getString("modelo"));
                umPatridepto.setModeloid(conexao.rs.getInt("modeloid"));
                umPatridepto.setSerie(conexao.rs.getString("serie"));
                umPatridepto.setChapa(conexao.rs.getString("chapa"));
                umPatridepto.setOrigem(conexao.rs.getString("origem"));
                umPatridepto.setDestino(conexao.rs.getString("destino"));  
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return umPatridepto;
    } 
                    
}