package controle;
import Dao.DAONumDespacho;
import biblioteca.MetodosPublicos;
import modelo.NumsDespacho;

public class CtrlNumDespachos 
{ 
    private final NumsDespacho objDespacho;
    MetodosPublicos  umMetodo = new MetodosPublicos();
    
    public CtrlNumDespachos() {
        this.objDespacho = new NumsDespacho();
    }
    
    public boolean salvarNumsDespachoInicial() 
    {  
        return new DAONumDespacho().salvarNumsDespachoInicialDAO();
    }    
    public boolean salvarNumsDespacho(NumsDespacho pDespacho) 
    {  
        return new DAONumDespacho().salvarNumsDespachoDAO(pDespacho);
    }    
    
    public boolean disponibilizarStatusNumDespacho(int pCodigo) 
    {
        return new DAONumDespacho().disponibilizarStatusNumDespachoDAO(pCodigo);
    }
    
    public boolean indisponibilizarStatusNumDespacho(int pCodigo) 
    {
        return new DAONumDespacho().indisponibilizarStatusNumDespachoDAO(pCodigo);
    }
    
    public String getNumDespachoDisponivel()
    {
        return new DAONumDespacho().getNumDespachoDisponivelDAO();
    }
    
    public boolean salvarNumDespachoInicialTabelaVazia()
    {
        return new DAONumDespacho().salvarNumDespachoInicialTabelaVaziaDAO();
    }
    
    public boolean temDespachoDisponivel()
    {
        return new DAONumDespacho().temDespachoDisponivelDAO();
    }
    
    public String gerarNumDespacho() 
    {
        return new DAONumDespacho().getNumDespachoDisponivelDAO();
    }
    
    public String gerarMesAno()  
    {
        //retorna o mes e ano vigente ex: NOV/2018
        return umMetodo.retornaMesAnoVigente() ;
    }
       
           
}