package biblioteca;

import Dao.DAONumDespacho;
import java.util.ArrayList;
import static biblioteca.VariaveisPublicas.lstListaNumsDespachos;
import modelo.NumsDespacho;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.Connection;
import javax.swing.JOptionPane;

public class GerarNumDespachos {
    
    MetodosPublicos     umMetodo    = new MetodosPublicos();  
    ConnConexao conexao  = new ConnConexao();
    Connection  conn     = null;   
    
     //Aqui voce determina quantos registros serão adicionados na tabela inicialmente e tambem nas adicoes posteriores
    int numADD = 10;
    
    //este metodo sera usado quando virar o ano e o mes for = 1
    private ArrayList<String> numsDespachosAnual()
    {              
        //instanciando array para receber os valores mes/ano
        ArrayList<String> numsDespachos = new ArrayList<>();
        
        //deletando os registros que sobraram no ultimo ano com status DISPONIVEL        
        umMetodo.deletarRegistrosConformeString("TBLNUMDESPACHOS", "STATUS", "DISPONIVEL"); 
        
        //captura o ano vigente
        String ano = String.valueOf(umMetodo.retornaAnoVigente());       
               
        int numInicio = 1;
        int numFinal  = numInicio + numADD;
        
        //atentar para o numero i<que ou seja se for cadastrar 200 coloque 201 e assim por diante, abaixo estamos inserindo nova
        //lista adicional de 10 registros
        for(int i=numInicio; i < numFinal; i++){
            numsDespachos.add(i+"/"+ano);
        }        
        return numsDespachos;  
    }
    
    private ArrayList<String> numsDespachosAdicionais()
    {              
        ArrayList<String> numsDespachosADD = new ArrayList<>();        
        String ano = String.valueOf(umMetodo.retornaAnoVigente());  
        
        //identificar qual o numero do último despacho cadastrado pesquisando no BD gerando valor para variavel valorCampo abaixo ex: 10/2018
        String valorCampo = umMetodo.getValorCampoUltimoCodigo("TBLNUMDESPACHOS", "numdespacho");
        
        //agora temos que pegar apenas o numero 10 acima e convetê-lo para int ou seja precisamos de qualquer valor antes da barra        
        //Posição do caracter ( / ) barra na string
        int pos = valorCampo.indexOf("/");
        
        //variavel svalorUltimoDesp recebe o numero que vem antes da barra no exemplo acima 10
        String svalorUltimoDespacho = valorCampo.substring(0, pos);
        
        //variavel int ultimoDespacho recebe o numero 10 que vem antes da barra como inteiro para prosseguimento
        int ultimoDespacho   = Integer.valueOf(svalorUltimoDespacho);
        
        int numInicio = ultimoDespacho + 1;
        int numFinal  = numInicio + numADD;
        
        //atentar para o numero i<que ou seja se for cadastrar 200 coloque 201 e assim por diante, abaixo estamos inserindo nova
        //lista adicional de 10 registros
        for(int i=numInicio; i < numFinal; i++){
            numsDespachosADD.add(i+"/"+ano);
        }        
        return numsDespachosADD;  
    }
    
    public void gerarNumsDespachosAnual(){
        //instanciando os objetos de classe
        GerarNumDespachos objNum    = new GerarNumDespachos();
        DAONumDespacho objDesp      = new DAONumDespacho();         
        NumsDespacho modDesp        = new NumsDespacho();
        
        //recebendo a lista de mesano gerada no metodo acima
        lstListaNumsDespachos = objNum.numsDespachosAnual();
               
        //recebendo todos os valores do array e gravando no banco de dados
        for(String mesano:lstListaNumsDespachos){
            modDesp.setNumdespacho(mesano); 
            System.out.println(mesano);
            objDesp.salvarNumsDespachoDAO(modDesp);
        }  
        
    }
    
    public void gerarNumsDespachosAdicionais(){
        //instanciando os objetos de classe
        GerarNumDespachos n    = new GerarNumDespachos();
        DAONumDespacho objDesp = new DAONumDespacho();         
        NumsDespacho modDesp   = new NumsDespacho();
        
        //recebendo a lista de mesano gerada no metodo acima
        lstListaNumsDespachos = n.numsDespachosAdicionais();
               
        //recebendo todos os valores do array e gravando no banco de dados
        for(String mesano:lstListaNumsDespachos){
            modDesp.setNumdespacho(mesano);  
            objDesp.salvarNumsDespachoDAO(modDesp);
              
        }        
    }
    
    public boolean temNumeroDisponivel(){
        conn = conexao.conectar();      
        try
        {  
            sql = "SELECT status from TBLNUMDESPACHOS WHERE status = 'DISPONIVEL' AND codigo > 0";            
            conexao.ExecutarPesquisaSQL(sql); 
            if(conexao.rs.next()){
               return true; 
            }else{
                return false;
            }                        
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro a o pesquisar a tabela, erro gerado : \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
       }        
        
    }
    
}