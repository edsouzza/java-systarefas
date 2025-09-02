package controle;
import Dao.DAONomeEstacao;
import modelo.NomeEstacao;

public class CtrlNomeEstacao
{ 
    public boolean salvarNomeEstacao(NomeEstacao pEstacao) 
    { 
        return new DAONomeEstacao().salvarNomeEstacaoDAO(pEstacao);
    }    
    
    public boolean salvarNomeEstacaoInicial() 
    { 
        return new DAONomeEstacao().salvarNomeEstacaoInicialDAO();
    }    
    
    public boolean atualizarStatusNomeEstacao(NomeEstacao pEstacao) 
    {
        return new DAONomeEstacao().atualizarStatusDoNomeEstacaoDAO(pEstacao);
    }    
    
    public boolean atualizarStatusPeloNomeDaEstacao(NomeEstacao pEstacao) 
    {
        return new DAONomeEstacao().atualizarStatusPeloNomeDaEstacaoDAO(pEstacao);
    }    
   
    public boolean excluirNomeEstacao(int pCodigo) 
    {
        return new DAONomeEstacao().excluirNomeEstacaoIndividualDao(pCodigo);
    }   
        
    public NomeEstacao pesquisarNomeEstacao(NomeEstacao pNomeEstacao) 
    {
        return new DAONomeEstacao().pesquisarNomeEstacaoDAO(pNomeEstacao);
    }  
    
    public boolean excluirNomeEstacaoPorIntervalo(int pCodigo) 
    {
        return new DAONomeEstacao().excluirNomeEstacaoPorIntervaloDao(pCodigo);
    }  
    
    public boolean excluirNomeEstacaoIndividual(int pCodigo) 
    {
        return new DAONomeEstacao().excluirNomeEstacaoIndividualDao(pCodigo);
    }   
    
}