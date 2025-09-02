package controle;
import Dao.DAOModelo;
import javax.swing.JOptionPane;
import modelo.Modelo;

public class CtrlModelo 
{ 
    public boolean salvarModelo(Modelo pModelo) 
    { 
        JOptionPane.showMessageDialog(null,"Modelo cadastrado com sucesso!"); 
        return new DAOModelo().salvarModeloDAO(pModelo);
    }    
    
    public boolean atualizarModelo(Modelo pModelo) 
    {
        JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOModelo().atualizarModeloDAO(pModelo);
    }
    
    public Modelo pesquisarModelo(Modelo pModelo) 
    {
        return new DAOModelo().pesquisarModeloDAO(pModelo);
    }       
       
    public boolean excluirModelo(int pCodigo)
    {
        return new DAOModelo().excluirModeloDAO(pCodigo);
    }   
    
}
