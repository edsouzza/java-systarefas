package controle;
import Dao.DAODocumentacao;
import javax.swing.JOptionPane;
import modelo.Documentacao;

public class CtrlDocumentacao 
{ 
    public boolean salvarDocumentacao(Documentacao pDoc) 
    { 
        JOptionPane.showMessageDialog(null,"Documentacao cadastrado com sucesso!"); 
        return new DAODocumentacao().salvarDocumentacaoDAO(pDoc);
    }    
    
    public boolean atualizarDocumentacao(Documentacao pDoc) 
    {
        JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAODocumentacao().atualizarDocumentacaoDAO(pDoc);
    }
    
    public Documentacao pesquisarDocumentacao(Documentacao pMemo) 
    {
        return new DAODocumentacao().pesquisarDocumentacaoDAO(pMemo);
    }       
       
    public boolean excluirDocumentacao(int pCodigo)
    {
        return new DAODocumentacao().excluirDocumentacaoDAO(pCodigo);
    }   
           
}