/* Essa classe foi implementada para uso em um jTextField por exemplo, onde será permitido a digitação apenas do que esta aqui dentro :
  replaceAll("[^a-z|^A-Z|^0-9|^.|^ |^/|^-]", "") por aqui vc contrala o que deseja que seja digitado. Aqui vc determina se serão caracteres em caixa alta 
  str.toUpperCase() ou baixa toLowerCase. Se quiser somente números replaceAll("[^0-9|^.|^ |^/|^-]", "")
  EX de uso : txtPESQUISA.setDocument(new TudoMinusculas()); */

package biblioteca;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class TudoMinusculas extends PlainDocument{
    @Override
    public void insertString(int Offset, String str, javax.swing.text.AttributeSet attr) throws BadLocationException{
        super.insertString(Offset, str.toLowerCase().replaceAll("[^A-Z|^a-z|^@|^.|^,|^/|^0-9|^.|^_|^ ]", ""), attr); //trata-se do que estará habilitado a ser digitado
    }
    public void replace(int Offset, String str, javax.swing.text.AttributeSet attr) throws BadLocationException{
        super.insertString(Offset, str.toLowerCase().replaceAll("[^A-Z|^a-z|^@|^.|^,|^/|^0-9|^.|^_|^ ]", ""), attr); //trata-se do que estará habilitado a ser digitado
    }
    
}
