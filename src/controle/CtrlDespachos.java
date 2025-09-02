package controle;
import Dao.DAODespacho;
import javax.swing.JOptionPane;
import modelo.Despacho;

public class CtrlDespachos 
{ 
    private final Despacho objDespacho;
    
    public CtrlDespachos() {
        this.objDespacho = new Despacho();
    }
    
    public boolean salvarDespacho(Despacho pDespacho) 
    {  
        return new DAODespacho().salvarDespachoDAO(pDespacho);
    }    
    
    public boolean atualizarDespacho(Despacho pDespacho) 
    {
        JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAODespacho().AtualizarDespachoDAO(pDespacho);
    }
             
    public boolean excluirDespacho(int pCodigo)
    {
        return new DAODespacho().excluirDespachoDAO(pCodigo);
    }  
       
}