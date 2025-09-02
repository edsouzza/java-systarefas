package controle;
import Dao.DAOAnotacoes;
import modelo.Anotacoes;

public class CtrlAnotacoes 
{ 
    public boolean salvarAnotacoes(Anotacoes pAnotacoes)
    { 
        return new DAOAnotacoes().salvarAnotacoesDAO(pAnotacoes);        
    } 
    public boolean atualizarAnotacoes(Anotacoes pAnotacoes)
    { 
        return new DAOAnotacoes().atualizarAnotacoesDAO(pAnotacoes);
    } 
    
}