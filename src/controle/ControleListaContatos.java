package controle;

import java.util.ArrayList;

//Criada inicialmente para setar o registro na tabela quando fosse selecionado em uma consulta

public class ControleListaContatos 
{   
    public ArrayList<String> lstContatos = new ArrayList();
    static int  ind = 0;
    
    public void addContatos(String nome)
    {
        lstContatos.add(nome);
    }
    public int retornaIndice(String nome)
    {    
        for(String c : lstContatos)
        {           
            ind = lstContatos.indexOf(nome);   
        }  
        return ind;
    }
}
   

