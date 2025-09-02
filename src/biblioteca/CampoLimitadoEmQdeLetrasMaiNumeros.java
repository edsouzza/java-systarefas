package biblioteca;
//Limita a digitação dentro de jTextField somente com 10 caracteres ou o que o programador informar com parametro

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


public class CampoLimitadoEmQdeLetrasMaiNumeros extends PlainDocument{
    private int tamanhoMax = 10;
         
    public CampoLimitadoEmQdeLetrasMaiNumeros(int tamanhoMax){
        this.tamanhoMax = tamanhoMax;
    }
    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
 
            if (str == null) return;  
                    
             String stringAntiga = getText (0, getLength() );  
             
             int tamanhoNovo = stringAntiga.length() + str.length(); 
                        
             if (tamanhoNovo <= tamanhoMax) {  
                 super.insertString(offset, str.toUpperCase().replaceAll("[^a-z|^A-Z|^0-9|^.|^ |^/]", ""), attr); //minusculas e maiusculas
             } else {    
                 super.insertString(offset, "", attr); 
             } 
             
    }
}

