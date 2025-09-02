package controle;
import Dao.DAOUsuario;
import javax.swing.JOptionPane;
import static biblioteca.VariaveisPublicas.acessoInicial;
import modelo.Usuario;

public class CtrlUsuario 
{ 
    private final Usuario objUsuario;
    
    public CtrlUsuario() {
        this.objUsuario = new Usuario();
    }
    
    public boolean salvarUsuario(Usuario pUsuario) 
    { 
        if(!acessoInicial)
        {
           JOptionPane.showMessageDialog(null,"Usuário cadastrado com sucesso!");  
        }        
        return new DAOUsuario().salvarUsuarioDAO(pUsuario);
    }    
    
    public boolean atualizarUsuario(Usuario pUsuario) 
    {
        JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOUsuario().AtualizarUsuarioDAO(pUsuario);
    }
    
    public Usuario pesquisarUsuario(String rf, int codigo) 
    {
        return new DAOUsuario().pesquisarUsuarioDAO(rf,codigo);
    }       
       
    public boolean excluirUsuario(int pCodigo)
    {
        return new DAOUsuario().excluirUsuarioDAO(pCodigo);
    }
    
    public boolean reiniciarSenhaUsuario(Usuario pUsuario) 
    {
        //JOptionPane.showMessageDialog(null,"A senha do usuário foi reiniciada com sucesso!"); 
        return new DAOUsuario().reiniciarSenhaUsuarioDAO(pUsuario);
    }
    
    public boolean alterarSenhaUsuario(Usuario pUsuario) 
    {
        JOptionPane.showMessageDialog(null,"Senha cadastrada com sucesso!"); 
        return new DAOUsuario().alterarSenhaUsuarioDAO(pUsuario);
    }
    
    public boolean logarUsuario(Usuario pUsuario)
    {
        return new DAOUsuario().logarUsuarioDAO(pUsuario);
    }
    
    public boolean usuarioCadastrado(Usuario pUsuario)
    {
        return new DAOUsuario().usuarioCadastradoDAO(pUsuario); 
    }
     
    public boolean salvarUsuarioInicial()
    {
        return new DAOUsuario().salvarUsuarioInicialDAO(); 
    }
        
}