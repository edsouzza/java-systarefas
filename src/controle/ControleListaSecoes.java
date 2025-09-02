package controle;

import java.util.ArrayList;

//Criada inicialmente para setar o registro na tabela quando fosse selecionado em uma consulta

public class ControleListaSecoes 
{   
    public ArrayList<String> lstSecoes = new ArrayList();
    static int  ind = 0;
    
    public void addSecoes(String nome)
    {
        lstSecoes.add(nome);
    }
    public int retornaIndice(String nome)
    {    
        for(String c : lstSecoes)
        {           
            ind = lstSecoes.indexOf(nome);   
        }  
        return ind;
    }
}
   

