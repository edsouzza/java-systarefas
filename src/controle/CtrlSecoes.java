package controle;

import javax.swing.JOptionPane;
import Dao.DAOSecao;
import java.util.ArrayList;
import modelo.Secao;

public class CtrlSecoes 
{ 
    private final Secao objSecao;
    
    public CtrlSecoes() {
        this.objSecao = new Secao();
    }
        
    public boolean salvarSecao(Secao pSecao) 
    { 
        JOptionPane.showMessageDialog(null,"Seção cadastrada com sucesso!"); 
        return new DAOSecao().salvarSecaoDAO(pSecao);
    }    
    
    public boolean atualizarSecao(Secao pSecao) 
    {
        JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOSecao().atualizarSecaoDAO(pSecao);
    }
    
    public Secao pesquisarSecao(Secao pSecao)
    {
        return new DAOSecao().pesquisarSecaoDAO(pSecao);
    }       
       
    public boolean excluirSecao(int pCodigo)
    {
        return new DAOSecao().excluirSecaoDAO(pCodigo);
    }
    public boolean salvarSecaoInicial() 
    { 
        return new DAOSecao().salvarSecaoInicialDAO();
    }    
       
    public boolean inativarPatrimonioUsuarioClientes(int idSecao, String motivo) 
    { 
        return new DAOSecao().inativarPatrimonioUsuarioClientesDAO(idSecao, motivo);
    }   
    
    public boolean reativarSecaoComPatrimonioEClientes(int idSecao, String motivo) 
    { 
        return new DAOSecao().reativarSecaoComPatrimoniosEClientesDAO(idSecao, motivo);
    } 
    
    public boolean reativarSecaoComPatrimonios(int idSecao, String motivo) 
    { 
        return new DAOSecao().reativarSecaoComPatrimoniosDAO(idSecao, motivo);
    }  
    
    public boolean reativarSecaoComClientes(int idSecao, String motivo) 
    { 
        return new DAOSecao().reativarSecaoComClientesDAO(idSecao, motivo);
    }  
    
    public boolean reativarSecao(int idSecao, String motivo, int codigoDepto) 
    {
        return new DAOSecao().reativarSecaoDAO(idSecao, motivo, codigoDepto);
    }
    
    public boolean reativarClienteSemUsuario(int codigoCliente) 
    {
        return new DAOSecao().reativarClienteSemUsuarioDAO(codigoCliente);
    }
    
    public boolean reativarClientesSecao(int idSecao, String motivo, int codigoDepto) 
    {
        return new DAOSecao().reativarSecaoDAO(idSecao, motivo, codigoDepto);
    }
    
    public ArrayList<Secao> RecuperaObjetoSQL(String sql) {
        
        return new DAOSecao().RecuperaObjetoSQL(sql);
    }
    
}
