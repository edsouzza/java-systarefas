package biblioteca;

import java.awt.Desktop;
import java.net.URI;
import javax.swing.JOptionPane;

public class AbrirURL {
    public void abrirURL(String url){
        try {
            URI link = new URI(url);
            Desktop.getDesktop().browse(link);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "[ERRO] Não foi possível abrir a URL passada!");
        }
    }
}
