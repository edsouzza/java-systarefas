package controle;

import java.util.ArrayList;

//Criada inicialmente para setar o registro na tabela quando fosse selecionado em uma consulta
public class ControleListaDocumentacao 
{   
    public ArrayList<String> lstDocumentacao = new ArrayList();
    static int  ind = 0;
    
    public void addDocumentacao(String doc)
    {
        lstDocumentacao.add(doc);
    }
    public int retornaIndice(String doc)
    {    
        for(String c : lstDocumentacao)
        {           
            ind = lstDocumentacao.indexOf(doc);   
        }  
        return ind;
    }
}  