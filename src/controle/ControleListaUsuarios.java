package controle;

import java.util.ArrayList;

//Criada inicialmente para setar o registro na tabela quando fosse selecionado em uma consulta

public class ControleListaUsuarios 
{   
    public ArrayList<String> lstUsuarios = new ArrayList();
    static int  ind = 0;
    
    public void addUsuarios(String nome)
    {
        lstUsuarios.add(nome);
    }
    public int retornaIndice(String nome)
    {    
        for(String c : lstUsuarios)
        {           
            ind = lstUsuarios.indexOf(nome);   
        }  
        return ind;
    }
}
   

