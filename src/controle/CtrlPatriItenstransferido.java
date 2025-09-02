package controle;
import Dao.DAOPatriTensTransferido;
import javax.swing.JOptionPane;
import modelo.PatriTensTransferido;

public class CtrlPatriItenstransferido 
{     
    private final PatriTensTransferido objPatriTemtransferido;
    
    public CtrlPatriItenstransferido() {
        this.objPatriTemtransferido = new PatriTensTransferido();
    }
        
    public boolean salvarPatriItensTransferido(PatriTensTransferido pPatriTemtransferido) 
    {          
        return new DAOPatriTensTransferido().salvarPatriItensTransferidoDAO(pPatriTemtransferido);
    }                 
    
    public boolean excluirItensSProcessando()
    {
        return new DAOPatriTensTransferido().excluirItensProcessandoDAO();
    }
    
    public boolean excluirItemDoMemoAtual(int pCodItem)
    {
        return new DAOPatriTensTransferido().excluirItemDoMemoAtualDAO(pCodItem);        
    }
    
    public boolean excluirItemDoMemoAtualPeloNumemo(int pCodItem, String pNumemo)
    {
        return new DAOPatriTensTransferido().excluirItemDoMemoAtualPeloNumemoDAO(pCodItem, pNumemo);        
    }
    
    public boolean excluirItensDoMemoSelecionado(String pNumemo)
    {
        return new DAOPatriTensTransferido().excluirItensDoMemoSelecionadoDAO(pNumemo);
    }
    
    public boolean memoAtualTemItens()
    {
        return new DAOPatriTensTransferido().memoAtualTemItensDAO();
    }
    
    public boolean atualizarValorDosItensAposExclusao(int pItem, String pNumemo)
    {
        return new DAOPatriTensTransferido().atualizarValorDosItensAposExclusaoDAO(pItem, pNumemo);
    }
              
}