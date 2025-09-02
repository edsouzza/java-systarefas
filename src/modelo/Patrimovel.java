package modelo;

/**
 *
 * @author Edi
 */
public class Patrimovel {
     private int codigo;
     private String serial;
     private String chapa;
     private String descricao;      
     private int modeloid;    
     private int secaoid;    
     private String status;
     private String obs;

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
     * @return the serial
     */
    public String getSerial() {
        return serial;
    }

    /**
     * @param serial the serial to set
     */
    public void setSerial(String serial) {
        this.serial = serial;
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
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    /**
     * @return the secaoid
     */
    public int getSecaoid() {
        return secaoid;
    }

    /**
     * @param secaoid the secaoid to set
     */
    public void setSecaoid(int secaoid) {
        this.secaoid = secaoid;
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
     * @return the obs
     */
    public String getObs() {
        return obs;
    }

    /**
     * @param obs the obs to set
     */
    public void setObs(String obs) {
        this.obs = obs;
    }

    /**
     * @return the modeloid
     */
    public int getModeloid() {
        return modeloid;
    }

    /**
     * @param modeloid the modeloid to set
     */
    public void setModeloid(int modeloid) {
        this.modeloid = modeloid;
    }

 
}