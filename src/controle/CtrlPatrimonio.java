package controle;
import Dao.DAOPatrimonio;
import javax.swing.JOptionPane;
import modelo.Patrimonio;
import static biblioteca.VariaveisPublicas.salvandoLote;

public class CtrlPatrimonio 
{ 
    private final Patrimonio objPatrimonio;
    
    public CtrlPatrimonio() {
        this.objPatrimonio = new Patrimonio();
    }
    
    public boolean salvarPatrimonio(Patrimonio umPatrimonio) 
    { 
        if(!salvandoLote)
        {
           JOptionPane.showMessageDialog(null,"Patrimonio cadastrado com sucesso!");           
        }
        return new DAOPatrimonio().salvarPatrimonioDAO(umPatrimonio);
        
    }    
    
    public boolean atualizarPatrimonio(Patrimonio umPatrimonio) 
    {
        JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOPatrimonio().AtualizarPatrimonioDAO(umPatrimonio);
    }
    
    public boolean AtualizarPatrimoniosEncaminhados(Patrimonio umPatrimonio) 
    {        
        return new DAOPatrimonio().AtualizarPatrimoniosEncaminhadosDAO(umPatrimonio);
    }
    
    public boolean reativarInativadoPelaSerie(Patrimonio umPatrimonio) 
    {        
        return new DAOPatrimonio().reativarInativadoPelaSerieDAO(umPatrimonio);
    }
    
    public boolean ExcluirPatrimonio(int pCodigo) 
    {
        JOptionPane.showMessageDialog(null,"O patrimônio com cógido "+pCodigo+" foi excluído definitivamento da base de dados com sucesso!"); 
        return new DAOPatrimonio().ExcluirPatrimonioDAO(pCodigo);
    }
    
    public boolean TransferirPatrimonio(int pCodigo) 
    {
        JOptionPane.showMessageDialog(null,"O patrimônio com cógido "+pCodigo+" foi transferido para CGGM com sucesso!"); 
        return new DAOPatrimonio().TransferirPatrimonioDAO(pCodigo);
    }
    
    public boolean getPatrimonioPeloCodico(int pCodigo) 
    {
      //  JOptionPane.showMessageDialog(null,"O patrimônio com cógido "+pCodigo+" existe na tabela de Patrimônios!"); 
        return new DAOPatrimonio().getPatrimonioPeloCodicoDAO(pCodigo);
    }
            
    public boolean atualizarModeloPatrimonio(Patrimonio umPatrimonio) 
    {
        JOptionPane.showMessageDialog(null,"Os dados foram atualizados com sucesso!"); 
        return new DAOPatrimonio().AtualizarModeloPatrimonioDAO(umPatrimonio);
    }
    
    public boolean ImpressoraDeContrato(int paramCodigo) 
    {        
        return new DAOPatrimonio().ImpressoraDeContrato(paramCodigo);
    }
    
    public boolean reativarPatrimonio(Patrimonio umPatrimonio) 
    {
        JOptionPane.showMessageDialog(null,"Patrimonio reativado com sucesso!"); 
        return new DAOPatrimonio().ReativarPatrimonioDAO(umPatrimonio);
    }
    
    public Patrimonio pesquisarPatrimonio(Patrimonio umPatrimonio) 
    {
        return new DAOPatrimonio().pesquisarPatrimonioDAO(umPatrimonio);
    }     
   
    public boolean patrimonioDuplicado(String paramCHAPA, String paramSERIE, String paramESTACAO) 
    {
        return new DAOPatrimonio().verificarDuplicidadeCadMicrosDAO(paramCHAPA, paramSERIE, paramESTACAO);
    }
    
    public boolean patrimonioDuplicadoMonitor(int paramCodigo, String paramSERIE) 
    {
        return new DAOPatrimonio().duplicidadeMonitorDAO(paramCodigo, paramSERIE);
    }
    
    public boolean patrimonioDuplicadoSwitch(int paramCodigo, String paramSERIE) 
    {
        return new DAOPatrimonio().duplicidadeSwitchDAO(paramCodigo, paramSERIE);
    }
    
    public boolean patrimonioDuplicadoScanner(int paramCodigo, String paramSERIE) 
    {
        return new DAOPatrimonio().duplicidadeScannerDAO(paramCodigo, paramSERIE);
    }
    
    public boolean patrimonioDuplicadoGBit(int paramCodigo, String paramSERIE) 
    {
        return new DAOPatrimonio().duplicidadeGBitDAO(paramCodigo, paramSERIE);
    }
    
    public boolean atualizarNomeDaEstacao(Patrimonio umPatrimonio) 
    {
        return new DAOPatrimonio().atualizarNomeEstacaoDAO(umPatrimonio);
    }
    
    public boolean atualizarDataRegistroInativado(Patrimonio umPatrimonio) 
    {
        return new DAOPatrimonio().atualizarDataRegistroInativadoDAO(umPatrimonio);
    }
    
    public boolean patrimonioDuplicadoImpressora(int paramCodigo, String paramSERIE, String paramIP) 
    {
        return new DAOPatrimonio().duplicidadeImpressoraDAO(paramCodigo, paramSERIE, paramIP);
    }
    
}