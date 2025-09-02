package controle;
import Dao.DAOIpsDisponiveis;
import modelo.IPDisponivel;

public class CtrlIPsDisponiveis
{ 
    public boolean salvarIPDisponivel(IPDisponivel pIP) 
    { 
        return new DAOIpsDisponiveis().salvarIPDisponivelDAO(pIP);
    }    
    
    public boolean atualizarIPDisponivel(IPDisponivel pIP) 
    {
        return new DAOIpsDisponiveis().atualizarIPDisponivelDAO(pIP);
    }
    
    public IPDisponivel pesquisarIPDisponivel(IPDisponivel pIP) 
    {
        return new DAOIpsDisponiveis().pesquisarIPDisponivelDAO(pIP);
    }       
       
    public boolean excluirIPDisponivel(IPDisponivel pIP) 
    {
        return new DAOIpsDisponiveis().excluirIPDisponivelDAO(pIP);
    }   
    
}