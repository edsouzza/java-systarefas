package controle;
import Dao.DAOTarefa;
import javax.swing.JOptionPane;
import modelo.Tarefa;

public class CtrlTarefa 
{ 
    public boolean salvarTarefa(Tarefa pTarefa) 
    { 
        JOptionPane.showMessageDialog(null,"Tarefa cadastrada com sucesso!"); 
        return new DAOTarefa().salvarTarefaDAO(pTarefa);
    }    
    
    public boolean atualizarTarefa(Tarefa pTarefa) 
    {
        JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOTarefa().AtualizarTarefaDAO(pTarefa);
    }
    
    public Tarefa pesquisarTarefa(String pTarefa) 
    {
        return new DAOTarefa().pesquisarTarefaDAO(pTarefa);
    }       
       
    public boolean excluirTarefa(int pCodigo)
    {
        return new DAOTarefa().excluirTarefaDAO(pCodigo);
    }   
    
    public boolean fecharTarefa(Tarefa pTarefa) 
    {
        return new DAOTarefa().FecharTarefaDAO(pTarefa);
    }   
    
}
