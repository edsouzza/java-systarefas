package controle;

import java.util.ArrayList;

//Criada inicialmente para setar o registro na tabela quando fosse selecionado em uma consulta
public class ControleListaMemorandos 
{   
    public ArrayList<String> lstMemorandos = new ArrayList();
    static int  ind = 0;
    
    public void addMemorandos(String memorando)
    {
        lstMemorandos.add(memorando);
    }
    public int retornaIndice(String memorando)
    {    
        for(String c : lstMemorandos)
        {           
            ind = lstMemorandos.indexOf(memorando);   
        }  
        return ind;
    }
}
   

