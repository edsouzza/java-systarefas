package controle;
import Dao.DAOPatriTransferido;
import modelo.PatriTransferido;

public class CtrlPatriTransferido 
{     
    private final PatriTransferido objPatritransferido;
    
    public CtrlPatriTransferido() {
        this.objPatritransferido = new PatriTransferido();
    }
        
    public boolean salvarPatriTransferido(PatriTransferido pPatritransferido) 
    {          
        return new DAOPatriTransferido().salvarPatriTransferidoDAO(pPatritransferido);
    }   
    
    public boolean editarPatriTransferido(PatriTransferido pPatritransferido) 
    {          
        return new DAOPatriTransferido().editarPatriTransferidoDAO(pPatritransferido);
    }        
    
    public PatriTransferido pesquisarPatriTransferido(PatriTransferido pPatriTransferido)
    {
        return new DAOPatriTransferido().pesquisarPatriTransferidoDAO(pPatriTransferido);
    }    
      
    public boolean excluirMemoTransferido(String pNumemo)
    {
        return new DAOPatriTransferido().excluirMemoTransferidoDAO(pNumemo);
    }
}