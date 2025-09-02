package biblioteca;
import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class CampoTxtLimitadoPorQdeCaracteresLowerCase extends PlainDocument {
    //Limita a digitação de caracteres em um JTextArea também como JTextField em (Maiusculas/Minusculas)
    /*Limita a digitação dentro de jTextField com a qde de caracteres determinada pelo programador informada atraves de parametro 
      USO : txtDESTINO.setDocument(new CampoLimitadoParaDigitacao(40)) */
    private int iMaxLength;
    public CampoTxtLimitadoPorQdeCaracteresLowerCase(int maxlen) {
        super();
        iMaxLength = maxlen;
    }
    @Override
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {
        if (str == null) {
            return;
        }
        if (iMaxLength <= 0) // aceitara qualquer no. de caracteres
        {
            //super.insertString(offset, str, attr);
            //super.insertString(offset, str.toString().replaceAll("[^a-z|^A-Z|^0-9|^.|^ |^:|^/|^-]", ""), attr); PARA MAIUSCULAS / MINUSCULAS
            super.insertString(offset, str.toLowerCase().replaceAll("[^a-z|^A-Z|^0-9|^.|^ |^:|^/|^-]", ""), attr); 
            return;
        }
        int ilen = (getLength() + str.length());
        if (ilen <= iMaxLength) // se o comprimento final for menor...
        {
            //super.insertString(offset, str, attr); // ...aceita str
            //super.insertString(offset, str.toString().replaceAll("[^a-z|^A-Z|^0-9|^.|^ |^:|^/|^-]", ""), attr); PARA MAIUSCULAS / MINUSCULAS
            super.insertString(offset, str.toLowerCase().replaceAll("[^a-z|^A-Z|^0-9|^.|^ |^:|^/|^-]", ""), attr); 
        } else {
            if (getLength() == iMaxLength) {
                JOptionPane.showMessageDialog(null, "Atenção foi definido um limite máximo de "+iMaxLength+" caracteres para este campo.");
                return; // nada a fazer
            }
            String newStr = str.substring(0, (iMaxLength - getLength()));
            //super.insertString(offset, str.toString().replaceAll("[^a-z|^A-Z|^0-9|^.|^ |^:|^/|^-]", ""), attr); PARA MAIUSCULAS / MINUSCULAS
            super.insertString(offset, str.toLowerCase().replaceAll("[^a-z|^A-Z|^0-9|^.|^ |^:|^/|^-]", ""), attr); 
            //super.insertString(offset, newStr, attr);
        }
    }
}
