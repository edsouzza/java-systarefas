package controle;
import Dao.DAOLog;
import modelo.Log;

public class CtrlLog 
{ 
    public boolean salvarLog(Log pLog)
    { 
        return new DAOLog().salvarLogDAO(pLog);        
    } 
    public boolean salvarLogInicial()
    { 
        return new DAOLog().salvarLogInicialDAO();
    } 
    
}
