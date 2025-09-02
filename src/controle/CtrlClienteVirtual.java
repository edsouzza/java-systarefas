package controle;
import Dao.DAOClienteVirtual;
import modelo.ClienteVirtual;

public class CtrlClienteVirtual 
{     
    private final ClienteVirtual objCliente;
    
    public CtrlClienteVirtual() {
        this.objCliente = new ClienteVirtual();
    }
        
    public boolean salvarClienteVirtual(ClienteVirtual pCliente) 
    { 
        //JOptionPane.showMessageDialog(null,"Cliente Virtual cadastrado com sucesso!"); 
        return new DAOClienteVirtual().salvarClienteVirtualDAO(pCliente);
    }    
    
    public boolean atualizarClienteVirtual(ClienteVirtual pCliente) 
    {
        //JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOClienteVirtual().atualizarClienteVirtualDAO(pCliente);
    }
    
    public boolean atualizarStatusClienteVirtual(int pCod, String pStatus) 
    {
        //JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOClienteVirtual().atualizarStatusClienteVirtualDAO(pCod, pStatus);
    }
    
    public boolean atualizarNomeClienteVirtual(int pCod, String pNome) 
    {
        //JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOClienteVirtual().atualizarNomeClienteVirtualDAO(pCod, pNome);
    }
    
    public ClienteVirtual pesquisarClienteVirtual(ClienteVirtual pCliente) 
    {
        return new DAOClienteVirtual().pesquisarClienteVirtualDAO(pCliente);
    }   
          
    public boolean excluirClienteVirtual(int pCodigo)
    {
        return new DAOClienteVirtual().excluirClienteVirtualDAO(pCodigo);
    }
    
}
