package modelo;

import Dao.DAOCliente;
import java.util.ArrayList;

/**
 *
 * @author Edi
 */
public class Cliente {
     private int codigo;
     private String nome;
     private String rf;    
     private int secaoid;
     private int deptoid;
     private int tipovirtualid;
     private String tipo;
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
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the rf
     */
    public String getRf() {
        return rf;
    }

    /**
     * @param rf the rf to set
     */
    public void setRf(String rf) {
        this.rf = rf;
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
   
    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
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
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the tipovirtualid
     */
    public int getTipovirtualid() {
        return tipovirtualid;
    }

    /**
     * @param tipovirtualid the tipovirtualid to set
     */
    public void setTipovirtualid(int tipovirtualid) {
        this.tipovirtualid = tipovirtualid;
    }
         
}
