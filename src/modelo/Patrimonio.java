
package modelo;

import java.util.Date;

public class Patrimonio {
    private int codigo;
    private int tipoid;
    private String serie;
    private String chapa;
    private int secaoid;
    private int clienteid;
    private int modeloid;
    private int deptoid;
    private int empresaid;
    private String status;
    private String motivo;
    private Date datacad;
    private Date datainativacao;
    private String estacao;
    private String contrato;
    private String ip;
    private String observacoes;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    public int getSecaoid() {
        return secaoid;
    }

    public void setSecaoid(int secaoid) {
        this.secaoid = secaoid;
    }

    public int getClienteid() {
        return clienteid;
    }

    public void setClienteid(int clienteid) {
        this.clienteid = clienteid;
    }
   
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstacao() {
        return estacao;
    }

    public void setEstacao(String estacao) {
        this.estacao = estacao;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public Date getDatacad() {
        return datacad;
    }

    /**
     * @param datacad the datacad to set
     */
    public void setDatacad(Date datacad) {
        this.datacad = datacad;
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

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
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
    
    public int getDeptoid() {
        return deptoid;
    }

    public void setDeptoid(int deptoid) {
        this.deptoid = deptoid;
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

    /**
     * @return the datainativacao
     */
    public Date getDatainativacao() {
        return datainativacao;
    }

    /**
     * @param datainativacao the datainativacao to set
     */
    public void setDatainativacao(Date datainativacao) {
        this.datainativacao = datainativacao;
    }

    /**
     * @return the empresaid
     */
    public int getEmpresaid() {
        return empresaid;
    }

    /**
     * @param empresaid the empresaid to set
     */
    public void setEmpresaid(int empresaid) {
        this.empresaid = empresaid;
    }
       
}
