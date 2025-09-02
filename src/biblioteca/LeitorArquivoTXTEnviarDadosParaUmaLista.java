package biblioteca;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class LeitorArquivoTXTEnviarDadosParaUmaLista {

    public static void LeitorArquivoTXTEnviarDadosParaUmaLista(String caminhoArquivo, List<String> listaDestino) {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                listaDestino.add(linha);
            }
            //System.out.println("Arquivo lido e dados adicionados à lista com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
