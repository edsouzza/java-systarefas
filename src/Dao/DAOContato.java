package Dao;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.PreparedStatement;
import modelo.Contato;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DAOContato {

    ConnConexao conexao  = new ConnConexao();   
    
    public boolean salvarContatoDAO(Contato pContato) 
    {
        conexao.conectar();
        try 
        {
            String sql = "INSERT INTO tblcontatos (nome,telefone,departamento,celular,obs) VALUES (?,?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pContato.getNome());
            pst.setString(2, pContato.getTelefone());            
            pst.setString(3, pContato.getDepartamento());
            pst.setString(4, pContato.getCelular());
            pst.setString(5, pContato.getObs());
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean atualizarContatoDAO(Contato pContato)
    {
        conexao.conectar();
        try
        {
            String sql = "UPDATE tblcontatos SET nome=?, telefone=?, departamento=?, celular=?, obs=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pContato.getNome());
            pst.setString(2, pContato.getTelefone());            
            pst.setString(3, pContato.getDepartamento());
            pst.setString(4, pContato.getCelular());
            pst.setString(5, pContato.getObs());
            pst.setInt(6, pContato.getCodigo());
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
    
    public Contato pesquisarContatoDAO(int pCodigo) 
    {  
        Contato contato = new Contato();      
        try
        {
            conexao.conectar();
            sql = "SELECT * FROM tblcontatos WHERE codigo = '" + pCodigo + "'";            
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                contato.setCodigo(conexao.rs.getInt("codigo"));
                contato.setNome(conexao.rs.getString("nome"));
                contato.setTelefone(conexao.rs.getString("telefone"));                
                contato.setDepartamento(conexao.rs.getString("departamento"));
                contato.setCelular(conexao.rs.getString("celular"));
                contato.setObs(conexao.rs.getString("obs"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return contato;
    }
    
    public boolean excluirContatoDAO(int pCodigo)
    {
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM tblcontatos WHERE codigo = '" + pCodigo + "';";            
            conexao.ExecutarPesquisaSQL(sql);            
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    }    
           
    public boolean RegistroDuplicado(Contato pContato)
    {
        conexao.conectar();                          
        try 
        {              
            sql = "SELECT * FROM tblcontatos WHERE nome = '"+pContato.getNome()+"'";
            conexao.ExecutarPesquisaSQL(sql);            
            if(conexao.rs.next())
            {
                //tipo de msg = 1 é a de informacao msg = 2 é a de exclamação  msg = 3 é a de interrogação
                JOptionPane.showMessageDialog(null,"Atenção este contato já esta cadastrado, verifique!","Duplicidade no nome!",2);                
                return true;
                          
            }else{ return false; }            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa sobre duplicidade! "+ex);
             return false;
        }finally{
            conexao.desconectar();
        }
               
    }
    
    public int buscarCodigo(String nome)
    {
        //retorna o codigo da seção selecionada
        conexao.conectar();      
        sql = "SELECT codigo FROM tblcontatos WHERE nome = '"+nome+"'";           
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
    
    public ArrayList<Contato> RecuperaObjetoSQL(String sql) 
    {      
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        
        ArrayList<Contato> lstContato = new ArrayList<>();
        try {
                        
            while (conexao.rs.next()) 
            {
                Integer vCodigo       = conexao.rs.getInt("codigo");
                String vNome          = conexao.rs.getString("nome");
                String vTel           = conexao.rs.getString("telefone");                
                String vDepto         = conexao.rs.getString("departamento");
                String vCel           = conexao.rs.getString("celular");
                String vObs           = conexao.rs.getString("obs");
                
                Contato objContatos = new Contato();  
                
                objContatos.setCodigo(vCodigo);
                objContatos.setNome(vNome);
                objContatos.setTelefone(vTel);
                objContatos.setCelular(vCel);
                objContatos.setDepartamento(vDepto);
                objContatos.setObs(vObs);
                
                lstContato.add(objContatos);
            }
            conexao.desconectar();
        } catch (NumberFormatException | SQLException erro) {
            String erroMsg = "Erro ao recuperar objetos : " + erro.getMessage();
            JOptionPane.showMessageDialog(null, erroMsg, "Mensagem", JOptionPane.ERROR_MESSAGE);
        }

        return lstContato;
    }
    
    
    public ArrayList<Contato> PesquisaObjeto(String sCampo, String sValor, boolean bTodaParte){
        String sql = "select * from tblcontatos where " + sCampo + " like '";
        //se nao definir em toda parte considerará apenas palavras que começam com...
        if(bTodaParte)
            sql = sql + "%";
            sql = sql + sValor + "%'";
            sql = sql + "Order by nome";
        
        return RecuperaObjetoSQL(sql);
    }
   
}
