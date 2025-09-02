/*
 Copia um texto para área de transferência para ser colado em qq. lugar
 */
package biblioteca;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class CopiarParaClipboard implements ClipboardOwner{

    public void setClipboard(String texto){
        StringSelection txt =  new StringSelection(texto);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(txt, this);
    }
        
    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        //classe a ser implementada
    }    
}