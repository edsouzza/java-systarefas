package biblioteca;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VariaveisPublicas {
    public static ArrayList<String> lstListaStrings         = new ArrayList<>();
    public static ArrayList<String> lstAuxiliar             = new ArrayList<>();
    public static ArrayList<String> lstListaStringsAuxiliar = new ArrayList<>();
    public static ArrayList<String> lstListaCampos          = new ArrayList<>();
    public static ArrayList<String> lstListaNumsDespachos   = new ArrayList<>();    
    public static ArrayList<String> lstListaGenerica        = new ArrayList<>();   
    public static ArrayList<Integer> lstListaInteiros       = new ArrayList<>();   
    public static String[]          sValoresVigentes        = new String[8];
    public static String[]          sNovosValores           = new String[8];
    public static Date              dataDoDia               = new Date();
    public static Date              dataInicio              = new Date();
    public static Date              dataFim                 = new Date();
    public static String            data                    = new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis()));
    public static String            dataAtual               = new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis()));
    public static String            hora                    = new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis())); //HH grava:ex:14hs se hh grava:02hs     
    public static String            diaVigente              = new SimpleDateFormat("dd").format(new Date(System.currentTimeMillis())); 
    public static String            mesVigente              = new SimpleDateFormat("MM").format(new Date(System.currentTimeMillis())); 
    public static String            anoVigente              = new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis()));
     
    public static boolean cadastrado,consultandoInativos,editando,clicouNaTabela,consultando,cadastrando,consultou,editandoRegistro,isDeContrato,alterouBanco,editandoNomeEstacaoReativada,alterouNomeRede,visualizandoInativos,enviando,acessoInicial,valorPesquisaTrue,gerarTxt,relPorDestino,temimpressorasAtivas,imprimindoDaLista,
                          deConLicencaConsecutivas,result, selecionouCliente,regEncontrado,isUsuario,isImpressora,isMicro,isMonitor,isSwitch,isScanner,isNotebook,isGbit,reativando,alterouStatus,salvandoreativado,editandoNomeRede,patriDeptos,salvandoLote,cadPatriDeptos,imprimirPorModelo,relPorAssunto,temContratoImpressoraAtivo,
                          reativou, alterandonomestacao, editandoDisponiveis, cadastrandonomestacao,cadPatrimovel,cadastrandoEquipamento, reativandoEquipamento, cadastrandoEstacaoForaRede,confIni,naoTemModelo,userInformatica,naoTemEncerrado,gerouNumo,patriDevolucao, editandoMemorando, tipoCadastroEntrada = false;
    
    public static int qdeUnidades, codigoTipoDocumento, codigoTipoModelo, codigoUsuario, codigoSecao, nivelAcessoUsuario, codigoCliente, codigoSelecionado, codigoUsuarioLogado, codigoPatrimonio,idUsuarioReativado,valorItem, indiceItemSelecionado,controlenaveg, controlaNumemo,
                      codigoRegSelecionado ,codigoDeptoSelecionado, totalRegs, contador, qdeColunas, codParaHistorico, codigoDepartamento, numeroEstacao, idDepto, novoNumeroEstacao, idUsuarioInativado, codigoNumemo, codTipoVirtual, codTipoSelecionado, codModeloRetornado = 0;
    
    public static String cejur_ou_pgm,tipoEquipamento,nomeBancoSetado,url,nomeBancoRelatorios,rfUsuario,nomeUsuario,tabela,oldRF,tabela_da_lista,nomestacaosubstituida,rangeEstacao,itemSelecionadoCadastro,statusLista,assuntoTransferido,numemoSelecionado,numemoParaEditar,
                         TipoDocumento, TipoModelo, nomeUsuarioLogado, ipServidor, nomeDoBanco,sql,sqlCamposSelecionados,nomeEstacao,novaSenha,novonomestacao,nomestacaoinicial,origemTransferidos,origemMemorando,destinoMemorando,assuntoMemorando,obsMemorando,relDestinoMemo,
                         nomeBanco,nomeCliente,nomeDepartamento,nomePadrao,nomeEstacaoSelecionada,nomeSecao,nomeRelatorio,nomePDF,rfCliente,numemoParaImprimir,numemoParaEditarObs,nomeEstacaoTransferida,anoDoUltimoMemo,serieRetornada,chapaRetornada,relAssuntoMemo,
                         status,paramPesquisa,serieEquipamento,numMemoTransferido,destinoTransferidos,entidadeInativa,origemPatrTransferido,deptobiblio,origemDevolucao,motivoInicial,nomeCampoPesq,assuntoSelecionado, caminhoArqTXT, sNumemoTXT = null; 
    
}