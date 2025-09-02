package controle;
import Dao.DAOMemorando;
import javax.swing.JOptionPane;
import modelo.Memorando;

public class CtrlMemorando 
{ 
    public boolean salvarMemorando(Memorando pMemorando) 
    { 
        JOptionPane.showMessageDialog(null,"Memorando cadastrado com sucesso!"); 
        return new DAOMemorando().salvarMemorandoDAO(pMemorando);
    }    
    
    public boolean atualizarMemorando(Memorando pMemorando) 
    {
        JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOMemorando().atualizarMemorandoDAO(pMemorando);
    }
    
    public Memorando pesquisarMemorando(Memorando pMemo) 
    {
        return new DAOMemorando().pesquisarMemorandoDAO(pMemo);
    }       
       
    public boolean excluirMemorando(int pCodigo)
    {
        return new DAOMemorando().excluirMemorandoDAO(pCodigo);
    }   
       
    
}
