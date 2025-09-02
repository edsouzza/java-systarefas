package modelo;

/**
 *
 * @author Edi
 */
public class Modelo {
     private int codigo;
     private int tipoid;
     private String modelo;
     private String descricao; 
     private String contrato;
     private String status;
     

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the tipoid
     */
    public int getTipoid() {
        return tipoid;
    }

    /**
     * @param tipoid the tipoid to set
     */
    public void setTipoid(int tipoid) {
        this.tipoid = tipoid;
    }

    /**
     * @return the contrato
     */
    public String getContrato() {
        return contrato;
    }

    /**
     * @param contrato the contrato to set
     */
    public void setContrato(String contrato) {
        this.contrato = contrato;
    }
             
}
