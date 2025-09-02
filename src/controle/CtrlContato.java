package controle;

import javax.swing.JOptionPane;
import Dao.DAOContato;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import modelo.Contato;

public class CtrlContato 
{ 
    private final Contato objContato;
    
    public CtrlContato() {
        this.objContato = new Contato();
    }
    
    public boolean salvarContato(Contato pContato) 
    { 
        JOptionPane.showMessageDialog(null,"Contato cadastrado com sucesso!"); 
        return new DAOContato().salvarContatoDAO(pContato);
    }    
    
    public boolean atualizarContato(Contato pContato) 
    {
        JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOContato().atualizarContatoDAO(pContato);
    }
    
    public Contato pesquisarContato(int pCodigo) 
    {
        return new DAOContato().pesquisarContatoDAO(pCodigo);
    }       
       
    public boolean excluirContato(int pCodigo)
    {
        return new DAOContato().excluirContatoDAO(pCodigo);
    }
   
}
