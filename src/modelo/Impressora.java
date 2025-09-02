/*Esta classe refere-se a Impressoras de Contrato*/
package modelo;

public class Impressora {
    private final int empresaId;
    private final int modeloId;
    private final String serie;
    private final String chapa;

    // Construtor para inicializar os valores
    public Impressora(int empresaId, int modeloId, String serie, String chapa) {
        this.empresaId = empresaId;
        this.modeloId = modeloId;
        this.serie = serie;
        this.chapa = chapa;
    }

    // Métodos "getters" para acessar os valores
    public int getEmpresaId() {
        return empresaId;
    }

    public int getModeloId() {
        return modeloId;
    }

    public String getSerie() {
        return serie;
    }

    public String getChapa() {
        return chapa;
    }
}
