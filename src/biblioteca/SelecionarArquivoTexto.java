/*
 * Abre opção para o usuário selecionar uma arquivo texto retornando o caminho completo do arquivo
 */
package biblioteca;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SelecionarArquivoTexto {
    JFileChooser fileChooser = new JFileChooser();
    private long retornaQdRegs = 0;  
     
    public String ImportarTXT() {
        fileChooser.setDialogTitle("Escolha o arquivo de texto");

        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.txt", "txt");
        fileChooser.setFileFilter(filtro);

        int resposta = fileChooser.showOpenDialog(new JDialog());

        if (resposta == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            // Conta o número de linhas do arquivo
            try (LineNumberReader lineRead = new LineNumberReader(new FileReader(file))) {
                while (lineRead.readLine() != null) {
                    // apenas lê para contar
                }
                int numLinhas = lineRead.getLineNumber();
                setRetornaQdRegs(numLinhas);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao tentar contar as linhas do arquivo!");
            }

            return file.getAbsolutePath();

        } else {
            JOptionPane.showMessageDialog(null, "Operação cancelada pelo usuário!", "Arquivo não selecionado", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    
    
    /**
     * @return the retornaQdRegs
     */
    
    public int getRetornaQdRegs() {
        return (int) retornaQdRegs;
    }

    /**
     * @param retornaQdRegs the retornaQdRegs to set
     */
    public void setRetornaQdRegs(long retornaQdRegs) {
        this.retornaQdRegs = retornaQdRegs;
    }
    
}
