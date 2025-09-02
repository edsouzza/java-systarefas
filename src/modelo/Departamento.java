package modelo;

public class Departamento {
     private int codigo;
     private String nome;
     private String range;
     private String status;

    public int getCodigo() {
        return codigo;
    }

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
     * @return the departamento
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the range
     */
    public String getRange() {
        return range;
    }

    /**
     * @param range the range to set
     */
    public void setRange(String range) {
        this.range = range;
    }
}
