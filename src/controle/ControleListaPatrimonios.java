package controle;

import java.util.ArrayList;

//Criada inicialmente para setar o registro na tabela quando fosse selecionado através de uma consulta
public class ControleListaPatrimonios 
{   
    public ArrayList<String> lstPatrimonios = new ArrayList();
    static int  ind = 0;
    
    public void addPatrimonios(String coluna)
    {
        //metodo que adiciona os registros da coluna escolhida
        lstPatrimonios.add(coluna);
    }
    public int retornaIndice(String coluna)
    {    
        //retorna o indice de cada registro
        for(String c : lstPatrimonios)
        {           
            ind = lstPatrimonios.indexOf(coluna);   
        }  
        return ind;
    }
      
}
   

