package biblioteca;

import static biblioteca.VariaveisPublicas.ipServidor;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 Classe retorna o IP para conexao dos relatorios
 Tive que fazer outra classe porque não consegui fazer conectar na minha classe ModuloConexao
 */
public class GetIPServidorRelatorios {
    private static String host                = ""; 
    public static String nomeBancoRelatorios  = ""; 
    private static String ipLocal             = ""; 
    private static String comecoIP            = ""; 
    
    private static String GetIPLocal(){
          try { 
             InetAddress myself = InetAddress.getLocalHost(); 
             host     = myself.getHostName(); 
             ipLocal = myself.getHostAddress(); 
          } catch (UnknownHostException ex){ 
             ex.printStackTrace(); 
          } 
        //System.out.println("HostName: "+host+" IP: "+ipLocal); 
        return ipLocal;
    }
    
    public void setNomeBancoRelatorios(String sNome){
        this.nomeBancoRelatorios = sNome;       
    }
    
    public String getNomeBancoRelatorios(){
        return nomeBancoRelatorios;
    }
       
    public static String retornaIPServidorRelatorios(){
        //rodando o metodo acima para pegar o ipLocal
        GetIPLocal();
        ipServidor    = "10.71.32.76";
        comecoIP      = ipLocal.substring(0,3);
        //System.out.println("Começo do IP : "+comecoIP); 
        
        //Com o IP Local e IP do servidor definidos agora é só fazer as comparações
        if(ipLocal.equals(ipServidor)){
            //estou trabalhando na minha estação na PGM snjpgmc59
            return ipServidor;
            //estação cliente ips vão começar com 10 => são diferentes do servidor e não começam com 192
        }else if(!ipLocal.equals(ipServidor) && !comecoIP.equals("192")){ 
            return ipServidor;
            //se o ip local começar com 192 deverá usar localhost pois estou trabalhando em casa
        }else if(comecoIP.equals("192")){
            return "localhost";
        }
        return null;
    }
}
