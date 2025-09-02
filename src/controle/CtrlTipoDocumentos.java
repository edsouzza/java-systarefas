package controle;
import Dao.DAOTipoDocumentos;
import modelo.TipoDocumento;

public class CtrlTipoDocumentos
{ 
    public boolean salvarTipoDocumento(TipoDocumento pTipoDocumento) 
    { 
        return new DAOTipoDocumentos().salvarTipoDocumentoDAO(pTipoDocumento);
    }    
    
    public boolean AtualizarTipoDocumento(TipoDocumento pTipoDocumento) 
    {
        return new DAOTipoDocumentos().AtualizarTipoDocumentoDAO(pTipoDocumento);
    }
       
    public boolean ExcluirTipoDocumento(int pCodigo)
    {
        return new DAOTipoDocumentos().ExcluirTipoDocumentoDAO(pCodigo);
    }   
    
    public boolean salvarTipoDocumentosInicial() 
    {
        return new DAOTipoDocumentos().salvarTipoDocumentosInicialDAO();
    } 
    public TipoDocumento pesquisarTipoDocumento(TipoDocumento pTipoDocumento) 
    {
        return new DAOTipoDocumentos().pesquisarTipoDocumentoDAO(pTipoDocumento);
    } 
    
}
