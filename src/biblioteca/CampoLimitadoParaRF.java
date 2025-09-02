package biblioteca;
//Limita a digitação dentro de jTextField somente com 10 caracteres

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


public class CampoLimitadoParaRF extends PlainDocument{
    private int tamanhoMax = 10;
         
    public CampoLimitadoParaRF(int tamanhoMax){
        this.tamanhoMax = tamanhoMax;
    }
    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
 
            if (str == null) return;  
                    
             String stringAntiga = getText (0, getLength() );  
             
             int tamanhoNovo = stringAntiga.length() + str.length(); 
                        
             if (tamanhoNovo <= tamanhoMax) {  
                 super.insertString(offset, str.toUpperCase().replaceAll("[^D|^E|^X|^0-9|^ ]", ""), attr);  
             } else {    
                 super.insertString(offset, "", attr); 
             } 
             
    }
}

