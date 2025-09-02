package modelo;

public class TipoDocumento {
     private int codigo;
     private String tipo;
     private String status;

    public int getCodigo() {
        return codigo;
    }

    public String getStatus() {
        return status;
    }

    public String getTipo() {
        return tipo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
  
}
