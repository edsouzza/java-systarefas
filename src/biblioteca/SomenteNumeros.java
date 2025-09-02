package biblioteca;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

//O CAMPO TXT ACEITARA A DIGITAÇÃO APENAS DE NÚMEROS SEGUE EXEMPLO DE USO
//txtNUMEMO.setDocument(soNumeros);

public class SomenteNumeros extends PlainDocument{
    @Override
    public void insertString(int Offset, String str, javax.swing.text.AttributeSet attr) throws BadLocationException{
        super.insertString(Offset, str.toUpperCase().replaceAll("[^0-9^ ]", ""), attr);
    }
    public void replace(int Offset, String str, javax.swing.text.AttributeSet attr) throws BadLocationException{
        super.insertString(Offset, str.toUpperCase().replaceAll("[^0-9^ ]", ""), attr);
    }
    
}
