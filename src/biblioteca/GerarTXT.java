/*
 * Esta classe destina-se a geração e leitura de um arquivo TXT
 */
package biblioteca;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GerarTXT {
    
    int contador=0;
    
    public static boolean gravarNoArquivo(String Caminho, String Texto)
    {
        try{
            FileWriter        arq = new FileWriter(Caminho,true);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.println(Texto);
            gravarArq.close();
            return true;
        }catch(IOException e){
            System.out.println(e.getMessage());
            return false;
        }
    }    
        
    public void gerarTXTDELISTA(ArrayList<String> lista)
    {
        //abrindo opção para selecionar o local do TXT para salvar o arquivo que conterá os ítens da lista passada por parametro
        JFileChooser chooser            = new JFileChooser();
        FileNameExtensionFilter filtro  = new FileNameExtensionFilter("Arquivos txt","txt");
        chooser.setFileFilter(filtro);
        chooser.setDialogTitle("Salvar arquivo");
        chooser.setMultiSelectionEnabled(false);
        chooser.setAcceptAllFileFilterUsed(false);

        //se escolheu um local aceitavel continua
        if(chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
        {
            String caminhoArq = chooser.getSelectedFile().toString().concat(".txt");
            //JOptionPane.showMessageDialog(rootPane, "Qde de itens na lista = "+lstAuxiliar.size()); 
            
            for(String itens : lista){
                if(gravarNoArquivo(caminhoArq, itens))
                {
                    contador = 1;
                }
            }   
            if(contador == 1){
                JOptionPane.showMessageDialog(null, "O arquivo TXT foi gerado com sucesso!","Gerando arquivo TXT",2); 
            }
        }
    }    
    
    public void gerarTXTDETEXTO(String texto)
    {
        //abrindo opção para selecionar o local do TXT para salvar o arquivo que conterá o texto passado por parametro
        JFileChooser chooser            = new JFileChooser();
        FileNameExtensionFilter filtro  = new FileNameExtensionFilter("Arquivos txt","txt");
        chooser.setFileFilter(filtro);
        chooser.setDialogTitle("Salvar arquivo");
        chooser.setMultiSelectionEnabled(false);
        chooser.setAcceptAllFileFilterUsed(false);

        //se escolheu um local aceitavel continua
        if(chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
        {
            String caminhoArq = chooser.getSelectedFile().toString().concat(".txt");
                        
            if(gravarNoArquivo(caminhoArq,texto))
            {
                contador =1;
            }
               
            if(contador == 1){
                JOptionPane.showMessageDialog(null, "O arquivo TXT foi gerado com sucesso!","Gerando arquivo TXT",2); 
            }
        }
    }    
    
}