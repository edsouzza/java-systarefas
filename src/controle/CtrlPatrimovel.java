package controle;
import Dao.DAOPatriMovel;
import Dao.DAOSecao;
import javax.swing.JOptionPane;
import modelo.Patrimovel;

public class CtrlPatrimovel 
{     
    private final Patrimovel objPatrimovel;
    
    public CtrlPatrimovel() {
        this.objPatrimovel = new Patrimovel();
    }
        
    public boolean salvarPatrimovel(Patrimovel pPatrimovel) 
    { 
        JOptionPane.showMessageDialog(null,"Patrimovel cadastrado com sucesso!"); 
        return new DAOPatriMovel().salvarPatrimovelDAO(pPatrimovel);
    }    
    
    public boolean atualizarPatrimovel(Patrimovel pPatrimovel) 
    {
        JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOPatriMovel().atualizarPatrimovelDAO(pPatrimovel);
    }    
       
    public boolean reativarPatrimovel(int codigo, String motivo) 
    { 
        return new DAOPatriMovel().reativarPatrimovelDAO(codigo, motivo);
    }  
    
}
