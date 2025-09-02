/*Esta classe limita a digitação de caracteres em um JTextArea / JTextField dentro da Qde 
  estipulada pelo programador atraves de parametro aceitando todos os tipos de caracteres 
  EX de uso : txtDESTINO.setDocument(new CampoTxtLimitadoPorQdeCaracteres(40)) 

APLICAÇÕES DIVERSAS : FAÇA UMA COPIA DESTA CLASSE COM OUTRO NOME, ESCOLHA UMA APLICAÇÃO 
ABAIXO DELETANDO AS DEMAIS E REPLIQUE ATÉ O FIM COMO FOI FEITO COM 
super.insertString(offset, str, attr); NESTA CLASSE
            
    ->Controlo os caracteres que poderão ser digitados ainda em caixa alta e baixa
    super.insertString(offset, str.toString().replaceAll("[^a-z|^A-Z|^0-9|^.|^,|^ |^:|^/|^-]", ""), attr); 

    ->Controlo os caracteres que poderão ser digitados em caixa alta 
    super.insertString(offset, str.toUpperCase().replaceAll("[^a-z|^A-Z|^0-9|^.|^ |^:|^/|^-]", ""), attr); 

    ->Controlo os caracteres que poderão ser digitados em caixa baixa 
    super.insertString(offset, str.toLowerCase().replaceAll("[^a-z|^A-Z|^0-9|^.|^ |^:|^/|^-]", ""), attr); 

*/


package biblioteca;
import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class CampoTxtLimitadoPorQdeCaracteres extends PlainDocument {
    
    private int iMaxLength;
    public CampoTxtLimitadoPorQdeCaracteres(int maxlen) {
        super();
        iMaxLength = maxlen;
    }
    @Override
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {
        if (str == null) {
            return;
        }
        if (iMaxLength <= 0) // Digitação livre dentro da qd estipulada
        {
            /*PARA MAIUSCULAS / MINUSCULAS / Digitação liberada para qq tipo de caracter*/
            super.insertString(offset, str, attr);             
                        
            return;
        }
        int ilen = (getLength() + str.length());
        if (ilen <= iMaxLength) // se o comprimento final for menor...
        {
             /*PARA MAIUSCULAS / MINUSCULAS / Digitação liberada para qq tipo de caracter*/
            super.insertString(offset, str, attr); 
            
        } else {
            if (getLength() == iMaxLength) {
                JOptionPane.showMessageDialog(null, "Atenção foi definido um limite máximo de "+iMaxLength+" caracteres para este campo.","Limite de caracteres excedido!",2);
                return; // nada a fazer
            }
            String newStr = str.substring(0, (iMaxLength - getLength()));
            
             /*PARA MAIUSCULAS / MINUSCULAS / Digitação liberada para qq tipo de caracter*/
            super.insertString(offset, str, attr); 
                        
        }
    }
}
