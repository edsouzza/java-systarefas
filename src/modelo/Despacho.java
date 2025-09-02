package modelo;

import java.util.Date;

public class Despacho {
     private int codigo;
     private int numdespachoid;
     private String mesano;
     private String assunto;
     private String destino;
     private int cadastranteid;
     private int tipodocumentoid;
     private Date   data; 
     private String obs;
     private String status;

    public String getAssunto() {
        return assunto;
    }

    public int getCadastranteid() {
        return cadastranteid;
    }

    public int getCodigo() {
        return codigo;
    }

    public Date getData() {
        return data;
    }

    public String getDestino() {
        return destino;
    }

    public String getMesano() {
        return mesano;
    }

    public int getNumdespachoid() {
        return numdespachoid;
    }

    public String getObs() {
        return obs;
    }

    public String getStatus() {
        return status;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public void setCadastranteid(int cadastranteid) {
        this.cadastranteid = cadastranteid;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setMesano(String mesano) {
        this.mesano = mesano;
    }

    public void setNumdespachoid(int numdespachoid) {
        this.numdespachoid = numdespachoid;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTipodocumentoid() {
        return tipodocumentoid;
    }

    public void setTipodocumentoid(int tipodocumentoid) {
        this.tipodocumentoid = tipodocumentoid;
    }
   
  
}
