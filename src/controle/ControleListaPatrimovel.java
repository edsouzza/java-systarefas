package controle;

import java.util.ArrayList;

//Criada inicialmente para setar o registro na tabela quando fosse selecionado em uma consulta
public class ControleListaPatrimovel 
{   
    public ArrayList<String> lstPatrimovel = new ArrayList();
    static int  ind = 0;
    
    public void addPatrimovel(String nome)
    {
        lstPatrimovel.add(nome);
    }
    public int retornaIndice(String nome)
    {    
        for(String c : lstPatrimovel)
        {           
            ind = lstPatrimovel.indexOf(nome);   
        }  
        return ind;
    }
}
   

