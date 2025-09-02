package controle;
import Dao.DAOInformacao;
import javax.swing.JOptionPane;
import modelo.Informacao;

public class CtrlInformacao 
{ 
    public boolean salvarInformacao(Informacao pInfo) 
    { 
        JOptionPane.showMessageDialog(null,"Informacao cadastrada com sucesso!"); 
        return new DAOInformacao().salvarInformacaoDAO(pInfo);
    }    
    
    public boolean atualizarInformacao(Informacao pInfo) 
    {
        JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOInformacao().atualizarInformacaoDAO(pInfo);
    }
    
    public Informacao pesquisarInformacao(Informacao pMemo) 
    {
        return new DAOInformacao().pesquisarInformacaoDAO(pMemo);
    }      
             
}
