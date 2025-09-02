
package biblioteca;
/*Classe retorna o ip da maquina local, faz a comparação com o IP do servidor retornando o IP a ser utilizado pelo Sistema : servidor ou localhost*/

import static biblioteca.VariaveisPublicas.ipServidor;
import static biblioteca.VariaveisPublicas.nomeEstacao;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetIPServidor {
    String host           = ""; 
    String ipLocal        = ""; 
    String comecoIPLocal  = "";
    public static String nomeBancoRelatorios  = ""; 
    
    public void setNomeBanco(String sNome){
        this.nomeBancoRelatorios = sNome;       
    }
    
    public String getNomeBanco(){
        return nomeBancoRelatorios;
    }  
    
    public String getNomeEstacao(){        
        try {
            host = Inet4Address.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            Logger.getLogger(GetIPServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return host;
    }        
      
    public String getIPLocal(){

        nomeEstacao = getNomeEstacao(); 
        //JOptionPane.showMessageDialog(null, nomeEstacao);
        try {

                InetAddress net = Inet4Address.getByName(nomeEstacao);

                //System.out.println(net.getHostAddress());
                ipLocal = net.getHostAddress();
                //System.out.println("IPLocal : "+ipLocal);

        } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }

        return ipLocal;    
       
    }
    
    public String getComecoIP(){
        String ipComputador = getIPLocal();
        comecoIPLocal = ipComputador.substring(0, 3);        
        //System.out.println("Comeco IPLocal : "+comecoIPLocal);
        
        return comecoIPLocal;
    }
    
     public String retornaIPServidor(){
        
          getIPLocal();
          getComecoIP();
          //JOptionPane.showMessageDialog(null, getComecoIP());
          
          //Em CONFIGURAÇÕES sempre que estiver conectado a um servidor diferente do servidor PGMCGGMC53 será identificado com LOCALHOST      
          if(!comecoIPLocal.equals("10.")){
              ipServidor = "LOCALHOST";
          }else{
              //SERVIDOR
              ipServidor  = "10.71.32.55";
              
              //MINHA ESTACAO
              //ipServidor  = "10.71.32.82";      
          }
            
        return ipServidor;        
    }        
    
    public String retornarNomeServidor(){
          getComecoIP();
          //JOptionPane.showMessageDialog(null, getComecoIP());
          
          //Em CONFIGURAÇÕES sempre que estiver conectado a um servidor diferente do servidor PGMCGGMC53 será identificado com LOCALHOST      
          if(!ipServidor.equals("10.71.32.55") || !comecoIPLocal.equals("10.")){
              nomeEstacao = "LOCALHOST";
          }else{
              nomeEstacao = "PGMCGGMC53";
          }

        return nomeEstacao;   
    }    
}