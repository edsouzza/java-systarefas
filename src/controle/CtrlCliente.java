package controle;
import Dao.DAOCliente;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;

public class CtrlCliente 
{     
    private final Cliente objCliente;
    
    public CtrlCliente() {
        this.objCliente = new Cliente();
    }
        
    public boolean salvarCliente(Cliente pCliente) 
    {         
        return new DAOCliente().salvarClienteDAO(pCliente);
    }   
    
    public boolean salvarClienteVirtual(Cliente pCliente) 
    {         
        return new DAOCliente().salvarClienteVirtualDAO(pCliente);
    }   
    
    public boolean atualizarNomeSemUsuario(int codigo, String nome) 
    {         
        return new DAOCliente().atualizarNomeSemUsuarioDAO(codigo, nome);
    }    
    
    public boolean atualizarNomesClientesVirtuais(int pCod, String pNome) 
    {
        //JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOCliente().atualizarNomesClientesVirtuaisDAO(pCod, pNome);
    }    
    
    public boolean atualizarStatusClientesVirtuaisColetivo(int pCod, String pStatus) 
    {
        //JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOCliente().atualizarStatusClientesVirtuaisColetivoDAO(pCod, pStatus);
    }        
    
    public boolean atualizarCliente(Cliente pCliente) 
    {
        JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOCliente().atualizarClienteDAO(pCliente);
    }
    
    public Cliente pesquisarCliente(Cliente pCliente) 
    {
        return new DAOCliente().pesquisarClienteDAO(pCliente);
    }   
    public int pesquisarCodigoClientePeloNome(String nome) 
    {
        return new DAOCliente().pesquisarCodigoClientePeloNome(nome);
    }   
       
    public boolean excluirCliente(int pCodigo)
    {
        return new DAOCliente().excluirClienteDAO(pCodigo);
    }  
      
}