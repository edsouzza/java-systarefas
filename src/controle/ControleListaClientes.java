package controle;

import java.util.ArrayList;

//Criada inicialmente para setar o registro na tabela quando fosse selecionado em uma consulta
public class ControleListaClientes 
{   
    public ArrayList<String> lstClientes = new ArrayList();
    static int  ind = 0;
    
    public void addClientes(String nome)
    {
        lstClientes.add(nome);
    }
    public int retornaIndice(String nome)
    {    
        for(String c : lstClientes)
        {           
            ind = lstClientes.indexOf(nome);   
        }  
        return ind;
    }
}
   

