/*
 * Conta a quantidade de linhas de um arquivo txt e retorna um interio
 */
package biblioteca;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RetornarQdeLinhasDoTxt {
    
    public int retornaNumLinhasDoTxt(String caminhoTxt)
    {
        int numLinhas = 0;
        File arquivoLeitura = new File(caminhoTxt);
        long tamanhoArquivo = arquivoLeitura .length();
        FileInputStream fs  = null;
        try {
            fs = new FileInputStream(arquivoLeitura);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RetornarQdeLinhasDoTxt.class.getName()).log(Level.SEVERE, null, ex);
        }
        DataInputStream in = new DataInputStream(fs);
        LineNumberReader lineRead = new LineNumberReader(new InputStreamReader(in));
        try {
            lineRead.skip(tamanhoArquivo);
        } catch (IOException ex) {
            Logger.getLogger(RetornarQdeLinhasDoTxt.class.getName()).log(Level.SEVERE, null, ex);
        }
        numLinhas = lineRead.getLineNumber();
        //System.out.println("O ARQUIVO CONTEM " + numLinhas + " LINHAS!!!!!!!");
        return numLinhas;
    }
}