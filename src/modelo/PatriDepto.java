package modelo;

import java.sql.Date;

public class PatriDepto {
     private int codigo;
     private int tipoid;  
     private int modeloid;   
     private String serie;
     private String chapa;      
     private String origem;      
     private String destino; 
     private String dtentrada;
     private String dtenvio;
     private String memoenvio;
     private String dtdevolucao;
     private String memodevolucao;
     private String status;  
     private String obs;       

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getTipoid() {
        return tipoid;
    }

    public void setTipoid(int tipoid) {
        this.tipoid = tipoid;
    }
        
    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getChapa() {
        return chapa;
    }

    public void setChapa(String chapa) {
        this.chapa = chapa;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
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

    public String getDtenvio() {
        return dtenvio;
    }

    public void setDtenvio(String dtenvio) {
        this.dtenvio = dtenvio;
    }

    public String getDtdevolucao() {
        return dtdevolucao;
    }

    public void setDtdevolucao(String dtdevolucao) {
        this.dtdevolucao = dtdevolucao;
    }

    public String getMemodevolucao() {
        return memodevolucao;
    }

    public void setMemodevolucao(String memodevolucao) {
        this.memodevolucao = memodevolucao;
    }

    public String getDtentrada() {
        return dtentrada;
    }

    public void setDtentrada(String dtentrada) {
        this.dtentrada = dtentrada;
    }

    public String getMemoenvio() {
        return memoenvio;
    }

    public void setMemoenvio(String memoenvio) {
        this.memoenvio = memoenvio;
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