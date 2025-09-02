package Dao;

import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.cadastrado;
import java.sql.PreparedStatement;
import modelo.Usuario;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import controle.ControleGravarLog;
import java.util.ArrayList;
import java.util.List;


public class DAOUsuario {

    ConnConexao conexao  = new ConnConexao();  
    
    public boolean salvarUsuarioDAO(Usuario pUsuario) 
    {
        conexao.conectar();
        try 
        {
            sql = "INSERT INTO tblusuarios (nome, rf, senha, secaoid, nivelacesso, status, obs) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pUsuario.getNome());
            pst.setString(2, pUsuario.getRf());
            pst.setString(3, pUsuario.getSenha());
            pst.setInt(4, pUsuario.getSecaoid());
            pst.setInt(5, pUsuario.getNivelacesso());            
            pst.setString(6, "ATIVO");
            pst.setString(7, pUsuario.getObs());
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
    
    public boolean AtualizarUsuarioDAO(Usuario pUsuario)
    {
        conexao.conectar();
        try
        {
            //rf não será alterado por conta de duplicidade de rf
            sql = "UPDATE tblusuarios SET nome=?, secaoid=?, nivelacesso=?, status=?, obs=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pUsuario.getNome());
            pst.setInt(2, pUsuario.getSecaoid());
            pst.setInt(3, pUsuario.getNivelacesso());            
            pst.setString(4, pUsuario.getStatus());
            pst.setString(5, pUsuario.getObs());
            pst.setInt(6, pUsuario.getCodigo());            
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
   
    public Usuario pesquisarUsuarioDAO(String rf, int codigo) 
    {  
        Usuario objUsuario = new Usuario();    
        conexao.conectar();
        try
        { 
            sql = "SELECT * FROM tblusuarios WHERE status='ATIVO' AND (rf = '"+rf+"' OR codigo = '"+codigo+"')";            
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next())
            {
                objUsuario.setCodigo(conexao.rs.getInt("codigo"));
                objUsuario.setNome(conexao.rs.getString("nome"));
                objUsuario.setRf(conexao.rs.getString("rf"));
                objUsuario.setSenha(conexao.rs.getString("senha"));
                objUsuario.setSecaoid(conexao.rs.getInt("secaoid"));
                objUsuario.setNivelacesso(conexao.rs.getInt("nivelacesso"));
                objUsuario.setStatus(conexao.rs.getString("status"));
                objUsuario.setObs(conexao.rs.getString("obs"));
                cadastrado = true;
            }
        } catch (Exception e) {
            cadastrado = false;
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return objUsuario;
    }
    
    public boolean excluirUsuarioDAO(int pCodigo)
    {
        conexao.conectar();      
        try
        {  
            sql = "DELETE FROM tblusuarios WHERE codigo = '" + pCodigo + "'";            
            conexao.ExecutarPesquisaSQL(sql);            
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    }    
           
    public boolean RegistroDuplicado(Usuario pUsuario)
    {
        conexao.conectar();                          
        try 
        {              
            sql = "SELECT * FROM tblusuarios WHERE rf = '"+pUsuario.getRf()+"'";
            conexao.ExecutarPesquisaSQL(sql);            
            if(conexao.rs.next())
            {
                //tipo de msg = 1 é a de informacao msg = 2 é a de exclamação  msg = 3 é a de interrogação
                JOptionPane.showMessageDialog(null,"Atenção este usuario já esta cadastrado/inativado, verifique!","Duplicidade no RF!",2);                
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
        sql = "SELECT codigo FROM tblsecoes WHERE nome = '"+nome+"'";           
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
    
    public boolean reiniciarSenhaUsuarioDAO(Usuario pUsuario)
    {
        conexao.conectar();
        try
        {
            sql = "UPDATE tblusuarios SET senha=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pUsuario.getSenha());
            pst.setInt(2, pUsuario.getCodigo());
            pst.executeUpdate();
            pst.close();  
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível reiniciar a senha do usuário , \n "+e+" , o sql passado foi \n"+sql);  
            return false;
        }finally{
            conexao.desconectar();
        }        
    }
    
    public boolean alterarSenhaUsuarioDAO(Usuario pUsuario)
    {
        conexao.conectar();
        try
        {
            sql = "UPDATE tblusuarios SET senha=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pUsuario.getSenha());
            pst.setInt(2, pUsuario.getCodigo());
            pst.executeUpdate();
            pst.close();  
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível reiniciar a senha do usuário , \n "+e+" , o sql passado foi \n"+sql);  
            return false;
        }finally{
            conexao.desconectar();
        }
        
    }

    public boolean logarUsuarioDAO(Usuario pUsuario)
    {
        //autenticando a senha do usuario
        try
        { 
            conexao.conectar();
            sql = "SELECT * FROM tblusuarios WHERE rf = ? AND senha = ?";   
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pUsuario.getRf());  
            pst.setString(2, pUsuario.getSenha());    
            if(pst.executeQuery().next())
            {
                pst.close(); 
                return true;    
            }else{
                pst.close(); 
                return false;
            }            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
            return false;   
        } finally {
            conexao.desconectar();
        } 
            
    }
    public boolean usuarioCadastradoDAO(Usuario pUsuario)
    {
        //verificando se o usuario existe atraves do rf(login)
        try
        { 
            conexao.conectar();
            sql = "SELECT * FROM tblusuarios WHERE rf=?";   
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pUsuario.getRf());    
            if(pst.executeQuery().next())
            {
                pst.close(); 
                return true;    
            }else{
                pst.close(); 
                return false;
            }            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
            return false;   
        } finally {
            conexao.desconectar();
        } 
    }
    public boolean salvarUsuarioInicialDAO()
    {
        //Se salvar este usuário significa que trata-se de configuração inicial, então deverá gravar também log inicial
        ControleGravarLog objGravarLog   = new ControleGravarLog();
        try
        { 
            conexao.conectar();                
            sql = "INSERT INTO tblusuarios (nome, rf, senha, secaoid, nivelacesso, status, obs) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "EDI AQUINO DE SOUZA");
            pst.setString(2, "D631863");
            pst.setString(3, "129c171d9ac81bfc46ccb98b149a94fb");
            pst.setInt(4, 1);
            pst.setInt(5, 1);            
            pst.setString(6, "ATIVO");
            pst.setString(7, "Usuario inicial criado para abertura do Sistema");
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
    public List<Usuario> RecuperaObjPorNome (String nome)
    {
        conexao.conectar(); 
        List<Usuario> user = new ArrayList<Usuario>();
        try {
              sql="SELECT * FROM tblusuarios where nome like '%"+nome+"%'";  
              conexao.ExecutarPesquisaSQL(sql);
              while ( conexao.rs.next())
              {
                  Usuario u      = new Usuario();
                  int iCodigo    = conexao.rs.getInt("codigo");
                  String sNome   = conexao.rs.getString("nome");
                  String sRf     = conexao.rs.getString("rf");
                  String sSenha  = conexao.rs.getString("senha");
                  String sStatus = conexao.rs.getString("status");
                  String sObs    = conexao.rs.getString("obs");
                  u.setCodigo(iCodigo);
                  u.setNome(sNome);
                  u.setRf(sRf);
                  u.setSenha(sSenha);
                  u.setStatus(sStatus);
                  u.setObs(sObs);
                  //adicionando o usuario á lista
                  user.add(u);
              }                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }  
        return user;
    }
    public List<Usuario> RecuperaObjPorCodigo (Integer codigo)
    {
        conexao.conectar(); 
        List<Usuario> user = new ArrayList<Usuario>();
        try {
              sql="SELECT * FROM tblusuarios where codigo ="+String.valueOf(codigo)+"'";  
              conexao.ExecutarPesquisaSQL(sql);
              while ( conexao.rs.next())
              {
                  Usuario u      = new Usuario();
                  int iCodigo    = conexao.rs.getInt("codigo");
                  String sNome   = conexao.rs.getString("nome");
                  String sRf     = conexao.rs.getString("rf");
                  String sSenha  = conexao.rs.getString("senha");
                  String sStatus = conexao.rs.getString("status");
                  String sObs    = conexao.rs.getString("obs");
                  u.setCodigo(iCodigo);
                  u.setNome(sNome);
                  u.setRf(sRf);
                  u.setSenha(sSenha);
                  u.setStatus(sStatus);
                  u.setObs(sObs);
                  //adicionando o usuario á lista
                  user.add(u);
              }                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }  
        return user;
    }
    
    public ArrayList<Usuario> RecuperaObjetoSQL(String sql) 
    {      
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        
        ArrayList<Usuario> lstUsuario = new ArrayList<>();
        try {
                        
            while (conexao.rs.next()) 
            {
                Integer vCodigo       = conexao.rs.getInt("codigo");
                String  vNome         = conexao.rs.getString("nome");
                String  vRF           = conexao.rs.getString("rf");
                String  vSenha        = conexao.rs.getString("senha");
                Integer vSecaoid      = conexao.rs.getInt("secaoid");
                Integer vNivelAcesso  = conexao.rs.getInt("nivelacesso");
                String  vStatus       = conexao.rs.getString("status");
                String  vObs          = conexao.rs.getString("obs");
                
                Usuario objUsuario = new Usuario();  
                
                objUsuario.setCodigo(vCodigo);
                objUsuario.setNome(vNome);
                objUsuario.setRf(vRF);
                objUsuario.setSenha(vSenha);
                objUsuario.setSecaoid(vSecaoid);
                objUsuario.setNivelacesso(vNivelAcesso);
                objUsuario.setStatus(vStatus);
                objUsuario.setObs(vObs);
                
                lstUsuario.add(objUsuario);
            }
            conexao.desconectar();
        } catch (NumberFormatException | SQLException erro) {
            String erroMsg = "Erro ao recuperar objetos : " + erro.getMessage();
            JOptionPane.showMessageDialog(null, erroMsg, "Mensagem", JOptionPane.ERROR_MESSAGE);
        }

        return lstUsuario;
    }
        
    public ArrayList<Usuario> PesquisaObjeto(String sCampo, String sValor, boolean bTodaParte){
        String sql = "select * from tblusuarios where " + sCampo + " like '";
        //se nao definir em toda parte considerará apenas palavras que começam com...
        if(bTodaParte)
            sql = sql + "%";
            sql = sql + sValor + "%'";
            sql = sql + "Order by nome";
        
        return RecuperaObjetoSQL(sql);
    }
       
}
    
