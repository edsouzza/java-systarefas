package modelo;

/**
 *
 * @author Edi
 */
public class PatriTensTransferido {
     private int codigo;
     private int item;
     private String numemo;
     private int modeloid;  
     private String serie;
     private String chapa;
     private String origem;      
     private String destino;      
     private String status;      
     

    /**
     * @return the numemo
     */
    public String getNumemo() {
        return numemo;
    }

    /**
     * @param numemo the numemo to set
     */
    public void setNumemo(String numemo) {
        this.numemo = numemo;
    }

    /**
     * @return the serie
     */
    public String getSerie() {
        return serie;
    }

    public int getModeloid() {
        return modeloid;
    }

    public void setModeloid(int modeloid) {
        this.modeloid = modeloid;
    }

    /**
     * @param serie the serie to set
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * @return the chapa
     */
    public String getChapa() {
        return chapa;
    }

    /**
     * @param chapa the chapa to set
     */
    public void setChapa(String chapa) {
        this.chapa = chapa;
    }

    /**
     * @return the destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * @param destino the destino to set
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }   

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the item
     */
    public int getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(int item) {
        this.item = item;
    }

    /**
     * @return the origem
     */
    public String getOrigem() {
        return origem;
    }

    /**
     * @param origem the origem to set
     */
    public void setOrigem(String origem) {
        this.origem = origem;
    }
      
}