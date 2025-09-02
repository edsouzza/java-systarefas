package controle;

import java.util.ArrayList;

//Criada inicialmente para setar o registro na tabela quando fosse selecionado em uma consulta
public class ControleListaTarefas 
{   
    public ArrayList<String> lstTarefas = new ArrayList();
    static int  ind = 0;
    
    public void addTarefas(String tarefa)
    {
        lstTarefas.add(tarefa);
    }
    public int retornaIndice(String tarefa)
    {    
        for(String c : lstTarefas)
        {           
            ind = lstTarefas.indexOf(tarefa);   
        }  
        return ind;
    }
}
   

