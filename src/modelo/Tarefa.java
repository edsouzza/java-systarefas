package modelo;

import java.sql.Date;

public class Tarefa {
    private int codigo;
    private int clienteid;
    private int usuarioid;
    private String tarefa;
    private String situacao;
    private String status;
    private Date dtabertura;
    private Date dtfechamento;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getClienteid() {
        return clienteid;
    }

    public void setClienteid(int clienteid) {
        this.clienteid = clienteid;
    }

    public int getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(int usuarioid) {
        this.usuarioid = usuarioid;
    }

    public String getTarefa() {
        return tarefa;
    }

    public void setTarefa(String tarefa) {
        this.tarefa = tarefa;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the dtabertura
     */
    public Date getDtabertura() {
        return dtabertura;
    }

    /**
     * @param dtabertura the dtabertura to set
     */
    public void setDtabertura(Date dtabertura) {
        this.dtabertura = dtabertura;
    }

    /**
     * @return the dtfechamento
     */
    public Date getDtfechamento() {
        return dtfechamento;
    }

    /**
     * @param dtfechamento the dtfechamento to set
     */
    public void setDtfechamento(Date dtfechamento) {
        this.dtfechamento = dtfechamento;
    }

   
}
