package controle;
import Dao.DAOFlag;

public class CtrlFlags 
{     
    public boolean salvarFlagInicial()
    { 
        return new DAOFlag().salvarFlagInicialDAO();
    } 
    
}
