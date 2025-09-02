/*
 Gravando os Logs dos formularios
 */
package controle;

import static biblioteca.VariaveisPublicas.nomeUsuario;
import static biblioteca.VariaveisPublicas.hora;
import java.util.Date;
import modelo.Log;

public class ControleGravarLog
{
    Log umModeloLog       = new Log();
    CtrlLog umControleLog = new CtrlLog();
    Date dataBanco         = new Date();
    
    public void gravarLog(String pOcorrencia){
        String ocorrencia = "O usuario "+nomeUsuario+" registrou "+pOcorrencia;
        umModeloLog.setOcorrencia(ocorrencia);
        umModeloLog.setData(new java.sql.Date(dataBanco.getTime()));
        umModeloLog.setHora(hora);
        umControleLog.salvarLog(umModeloLog);
    }
    
}
