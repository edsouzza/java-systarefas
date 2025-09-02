package modelo;

public class Tipo {
     private int codigo;
     private String tipo;
     private String status;
     private String tipopatrimonio;
     private String temIp;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
    
    public Object[] allNecessary() {
        return new Object[]{codigo, tipo, status};
    }
    
    public Object[] allGetters() {
        return new Object[]{getCodigo(), getTipo(), getStatus()};
    }

    /**
     * @return the tipopatrimonio
     */
    public String getTipopatrimonio() {
        return tipopatrimonio;
    }

    /**
     * @param tipopatrimonio the tipopatrimonio to set
     */
    public void setTipopatrimonio(String tipopatrimonio) {
        this.tipopatrimonio = tipopatrimonio;
    }

    /**
     * @return the temIp
     */
    public String getTemIp() {
        return temIp;
    }

    /**
     * @param temIp the temIp to set
     */
    public void setTemIp(String temIp) {
        this.temIp = temIp;
    }

}