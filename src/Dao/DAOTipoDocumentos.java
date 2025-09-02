package Dao;

import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.PreparedStatement;
import modelo.TipoDocumento;
import javax.swing.JOptionPane;

/**
 *
 * @author d631863
 */
public class DAOTipoDocumentos {

    ConnConexao conexao  = new ConnConexao();  
    
    public boolean salvarTipoDocumentosInicialDAO() 
    {
           
        String [] tipos = {"DESPACHO", "OFICIO", "MEMORANDO", "INFORMACAO", "PORTARIA", "PORTARIA CONJUNTA", "SUMULA", "INSTRUCAO NORMATIVA", "ORDEM INTERNA", "PROCESSO"};
        conexao.conectar(); 
        try
        {
            for(int i=0; i < tipos.length; i++)
            {
              sql = "INSERT INTO TBLTIPODOCUMENTOS (tipo, status) VALUES ('"+tipos[i]+"' ,'ATIVO')";     
              conexao.ExecutarAtualizacaoSQL(sql);   
            }
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }                  
   }
    
    
    public boolean salvarTipoDocumentoDAO(TipoDocumento pTipoDocumento) 
    {
        conexao.conectar();
        try 
        {
            sql = "INSERT INTO TBLTIPODOCUMENTOS (tipo, status) VALUES (?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pTipoDocumento.getTipo().toUpperCase());//caso importe dados com textos em letras minusculas gravar em caixa alta
            pst.setString(2, "ATIVO");
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
    
    public boolean AtualizarTipoDocumentoDAO(TipoDocumento pTipoDocumento)
    {
        conexao.conectar();
        try
        {
            //rf não será alterado por conta de duplicidade de rf
            sql = "UPDATE TBLTIPODOCUMENTOS SET tipo=?, status=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pTipoDocumento.getTipo());
            pst.setString(2, pTipoDocumento.getStatus());
            pst.setInt(3, pTipoDocumento.getCodigo());
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
      
    public boolean ExcluirTipoDocumentoDAO(int pCodigo)
    {
        conexao.conectar();    
        try
        {  
            sql = "DELETE FROM TBLTIPODOCUMENTOS WHERE codigo = '" + pCodigo + "'";            
            conexao.ExecutarPesquisaSQL(sql);            
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    }    
           
    public boolean RegistroDuplicado(TipoDocumento pTipoDocumento)
    {
        conexao.conectar();                        
        try 
        {              
            sql = "SELECT * FROM TBLTIPODOCUMENTOS WHERE tipo = '"+pTipoDocumento.getTipo()+"'";
            conexao.ExecutarPesquisaSQL(sql);            
            if(conexao.rs.next())
            {
                //tipo de msg = 1 é a de informacao msg = 2 é a de exclamação  msg = 3 é a de interrogação
                JOptionPane.showMessageDialog(null,"Atenção este tipo de despacho já esta cadastrado, verifique!","Duplicidade de cadastro!",2);                
                return true;
            }else{ return false; }            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa sobre duplicidade! "+ex);
             return false;
        }finally{
            conexao.desconectar();
        }
               
    }    
    
    public TipoDocumento pesquisarTipoDocumentoDAO(TipoDocumento pTipoDocumento) 
    {       
        try
        {
            conexao.conectar();
            sql = "SELECT * FROM TBLTIPODOCUMENTOS WHERE codigo = '" + pTipoDocumento.getCodigo() + "' OR tipo = '" + pTipoDocumento.getTipo() + "'";           
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                pTipoDocumento.setCodigo(conexao.rs.getInt("codigo"));
                pTipoDocumento.setTipo(conexao.rs.getString("tipo"));
                pTipoDocumento.setStatus(conexao.rs.getString("status"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return pTipoDocumento;
    }
   
       
}