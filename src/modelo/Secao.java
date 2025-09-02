package modelo;

import Dao.DAOSecao;
import java.util.ArrayList;

/**
 *
 * @author Edi
 */
public class Secao {
     private int codigo;
     private String nome;
     private String pr;
     private String email;
     private String status;
     private String simproc;
     private String sei;
     private int deptoid;
     private String obs;
     private String ramal;
         
     public int getCodigo(){
         return codigo;
     }
     public String getNome(){
         return nome;
     }     
     public void setCodigo(Integer codigo){
         this.codigo = codigo;
     }   
     public void setNome(String secao){
         this.nome = secao;
     }   

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPr() {
        return pr;
    }

    public void setPr(String pr) {
        this.pr = pr;
    }
    
    public String getSimproc() {
        return simproc;
    }

    public void setSimproc(String simproc) {
        this.simproc = simproc;
    }          

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
    public String toString(){
        return nome;
    }

    public String getRamal() {
        return ramal;
    }

    public void setRamal(String ramal) {
        this.ramal = ramal;
    }

    /**
     * @return the deptoid
     */
    public int getDeptoid() {
        return deptoid;
    }

    /**
     * @param deptoid the deptoid to set
     */
    public void setDeptoid(int deptoid) {
        this.deptoid = deptoid;
    }

    /**
     * @return the sei
     */
    public String getSei() {
        return sei;
    }

    /**
     * @param sei the sei to set
     */
    public void setSei(String sei) {
        this.sei = sei;
    }
         
}
