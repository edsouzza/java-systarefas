package modelo;

/**
 *
 * @author Edi
 */
public class Informacao {
     private int codigo;
     private String instituicao;
     private String senha;    
     private String status;    
     private String obs;    

    public int getCodigo() {
        return codigo;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public String getSenha() {
        return senha;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

  
   
         
}
