package controle;
import Dao.DAOTipo;
import javax.swing.JOptionPane;
import modelo.Tipo;

public class CtrlTipo
{ 
    public boolean salvarTipo(Tipo pTipo) 
    { 
        JOptionPane.showMessageDialog(null,"Tipo cadastrado com sucesso!"); 
        return new DAOTipo().salvarTipoDAO(pTipo);
    }    
    
    public boolean atualizarTipo(Tipo pTipo) 
    {
        JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOTipo().atualizarTipoDAO(pTipo);
    }
    
    public Tipo pesquisarTipo(Tipo pTipo) 
    {
        return new DAOTipo().pesquisarTipoDAO(pTipo);
    }       
       
    public boolean excluirTipo(int pCodigo)
    {
        return new DAOTipo().excluirTipoDAO(pCodigo);
    }   
    
}
