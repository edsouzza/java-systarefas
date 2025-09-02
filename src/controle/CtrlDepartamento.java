package controle;
import Dao.DAODepartamento;
import javax.swing.JOptionPane;
import modelo.Departamento;

public class CtrlDepartamento
{ 
    public boolean salvarDepartamento(Departamento pDepartamento) 
    { 
        JOptionPane.showMessageDialog(null,"Departamento cadastrado com sucesso!"); 
        return new DAODepartamento().salvarDepartamentoDAO(pDepartamento);
    }    
    
    public boolean salvarDepartamentoInicial() 
    {         
        return new DAODepartamento().salvarDepartamentoInicialDAO();
    }    
    
    public boolean atualizarDepartamento(Departamento pDepartamento) 
    {
        JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAODepartamento().atualizarDepartamentoDAO(pDepartamento);
    }
    
    public Departamento pesquisarDepartamento(Departamento pDepartamento) 
    {
        return new DAODepartamento().pesquisarDepartamentoDAO(pDepartamento);
    }       
       
    public boolean excluirDepartamento(int pCodigo)
    {
        return new DAODepartamento().excluirDepartamentoDAO(pCodigo);
    }   
    
}
