package controle;
import Dao.DAOEmpresa;
import modelo.Empresa;

public class CtrlEmpresa 
{     
    private final Empresa objCliente;
    
    public CtrlEmpresa() {
        this.objCliente = new Empresa();
    }
        
    public boolean salvarEmpresa(Empresa pCliente) 
    { 
        //JOptionPane.showMessageDialog(null,"Cliente Virtual cadastrado com sucesso!"); 
        return new DAOEmpresa().salvarEmpresaDAO(pCliente);
    }    
    
    public boolean atualizarEmpresa(Empresa pCliente) 
    {
        //JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOEmpresa().atualizarEmpresaDAO(pCliente);
    }
    
    public boolean atualizarStatusEmpresa(int pCod, String pStatus) 
    {
        //JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOEmpresa().atualizarStatusEmpresaDAO(pCod, pStatus);
    }
    
    public boolean atualizarNomeEmpresa(int pCod, String pNome) 
    {
        //JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOEmpresa().atualizarNomeEmpresaDAO(pCod, pNome);
    }       
          
    public boolean excluirEmpresa(int pCodigo)
    {
        return new DAOEmpresa().excluirEmpresaDAO(pCodigo);
    }
    
    public boolean inativarEmpresaDoContratoImpressoras()
    {
        return new DAOEmpresa().inativarEmpresaDoContratoImpressorasDAO();
    }
    
}
