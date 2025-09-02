package biblioteca;
//Limita a digitação dentro de jTextField somente com numeros

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


public class CampoLimitadoParaCHAPA extends PlainDocument{
    @Override
    public void insertString(int Offset, String str, javax.swing.text.AttributeSet attr) throws BadLocationException{
        super.insertString(Offset, str.toUpperCase().replaceAll("[^-|^.|^0-9^ ]", ""), attr);
    }
    public void replace(int Offset, String str, javax.swing.text.AttributeSet attr) throws BadLocationException{
        super.insertString(Offset, str.toUpperCase().replaceAll("[^-|^.|^0-9^ ]", ""), attr);
    }
}

