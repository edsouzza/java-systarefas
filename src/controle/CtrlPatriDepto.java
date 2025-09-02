package controle;
import Dao.DAOPatriDeptos;
import javax.swing.JOptionPane;
import modelo.PatriDepto;

public class CtrlPatriDepto 
{ 
    private final PatriDepto objPatrimonio;
    
    public CtrlPatriDepto() {
        this.objPatrimonio = new PatriDepto();
    }
    
    public boolean salvarPatrimonio(PatriDepto umPatrimonio) 
    { 
        JOptionPane.showMessageDialog(null,"Patrimonio cadastrado com sucesso!"); 
        return new DAOPatriDeptos().salvarPatriDeptosDAO(umPatrimonio);
    }    
    
    public boolean atualizarPatrimonio(PatriDepto umPatrimonio) 
    {
        JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOPatriDeptos().AtualizarPatriDeptosDAO(umPatrimonio);
    }        
    
    public PatriDepto pesquisarPatriDepto(PatriDepto umPatriDepto) 
    {
        return new DAOPatriDeptos().pesquisarPatriDeptoDAO(umPatriDepto);
    }    
}