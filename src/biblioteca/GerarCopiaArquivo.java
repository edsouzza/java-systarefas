/*
 * Destina-se a fazer copia um arquivo
 */
package biblioteca;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Será usado para gerar a copia do SYSTAREFAS de desenvolvimento denominado SYSDESENV
 * @author edsou
 */


public class GerarCopiaArquivo {
    
    private static void copiar(File fonte, File destino) throws IOException{
        InputStream   in = new FileInputStream(fonte);
        OutputStream out = new FileOutputStream(destino);
     
        byte[] buf = new byte[1024];
        int len;
        while((len = in.read(buf)) > 0){
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
    
   public void CopiarArquivo(String origem, String destino){
        try{
            File arquivo1 = new File(origem);
            File arquivo2 = new File(destino); 
         
            copiar(arquivo1, arquivo2);
            //System.out.println("SysDesenv copiado com sucesso!");
        }
        catch(IOException e){
            // pode ocorrer erros
        }
    }
}