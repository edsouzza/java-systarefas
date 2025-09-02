package Dao;

import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.dataDoDia;
import static biblioteca.VariaveisPublicas.cadastrado;
import static biblioteca.VariaveisPublicas.isDeContrato;
import static biblioteca.VariaveisPublicas.sql;
import static biblioteca.VariaveisPublicas.cadastrandoEstacaoForaRede;
import static biblioteca.VariaveisPublicas.destinoTransferidos;
import static biblioteca.VariaveisPublicas.lstListaGenerica;
import static biblioteca.VariaveisPublicas.codigoCliente;
import static biblioteca.VariaveisPublicas.lstListaStrings;
import static biblioteca.VariaveisPublicas.origemPatrTransferido;
import static biblioteca.VariaveisPublicas.origemTransferidos;
import static biblioteca.VariaveisPublicas.valorPesquisaTrue;
import controle.CtrlNomeEstacao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.Patrimonio;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import modelo.NomeEstacao;


public class DAOPatrimonio {

    ConnConexao       conexao                = new ConnConexao(); 
    LocalDate         dataAtual              = LocalDate.now();
    NomeEstacao       umModeloNomeEstacao    = new NomeEstacao();
    CtrlNomeEstacao   umControleNomeEstacao  = new CtrlNomeEstacao();    
    
    public boolean salvarPatrimonioDAO(Patrimonio umPatrimonio) 
    {
        conexao.conectar();
        try 
        {
            sql = "INSERT INTO tblpatrimonios (tipoid,ip,serie,chapa,estacao,secaoid,clienteid,modeloid,deptoid,empresaid,status,motivo,datacad,observacoes,contrato) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1,     umPatrimonio.getTipoid());
            pst.setString(2,  umPatrimonio.getIp());
            pst.setString(3,  umPatrimonio.getSerie());
            pst.setString(4,  umPatrimonio.getChapa());
            pst.setString(5,  umPatrimonio.getEstacao());
            pst.setInt(6,     umPatrimonio.getSecaoid());
            pst.setInt(7,     umPatrimonio.getClienteid());
            pst.setInt(8,     umPatrimonio.getModeloid());
            pst.setInt(9,     umPatrimonio.getDeptoid());
            pst.setInt(10,    umPatrimonio.getEmpresaid());
            pst.setString(11, "ATIVO");            
            pst.setString(12, umPatrimonio.getMotivo());       
            pst.setDate(13, new java.sql.Date(dataDoDia.getTime()));    //grava um Timestamp no banco com data e hora para data de cadastro
            pst.setString(14, umPatrimonio.getObservacoes());
            pst.setString(15, umPatrimonio.getContrato());
            pst.executeUpdate();
            pst.close(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean AtualizarPatrimonioDAO(Patrimonio umPatrimonio) 
    {
        conexao.conectar();
        try
        {
            sql = "UPDATE tblpatrimonios SET tipoid=?, ip=?, serie=?, chapa=?, estacao=?, secaoid=?, clienteid=?, modeloid=?, deptoid=?, status=?, motivo=?, observacoes=?, contrato=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1,       umPatrimonio.getTipoid());
            pst.setString(2,    umPatrimonio.getIp());
            pst.setString(3,    umPatrimonio.getSerie());
            pst.setString(4,    umPatrimonio.getChapa());
            pst.setString(5,    umPatrimonio.getEstacao());
            pst.setInt(6,       umPatrimonio.getSecaoid());
            pst.setInt(7,       umPatrimonio.getClienteid());
            pst.setInt(8,       umPatrimonio.getModeloid());
            pst.setInt(9,       umPatrimonio.getDeptoid());            
            pst.setString(10,   umPatrimonio.getStatus());
            pst.setString(11,   umPatrimonio.getMotivo());
            pst.setString(12,   umPatrimonio.getObservacoes());
            pst.setString(13,   umPatrimonio.getContrato());
            pst.setInt(14,      umPatrimonio.getCodigo());            
            pst.executeUpdate();
            pst.close();  
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n "+e+" , o sql passado foi \n"+sql);  
            return false;
        }finally{
            conexao.desconectar();
        }        
    }    
    
    public boolean TransferirPatrimonioDAO(int pCodigo) 
    {
        /*Transfere automaticamente o patrimônio para CGGM sem o memorando. É um tipo de acerto cadastral no Sistema ex: um monitor encontrado 
        na CGGM mas que no Sistema esta em CEJUR pelas vias normais teriamos que fazer um memorando de transferencia de CEJUR para CGGM mas veio 
        sem nada, para acertar o cadastro transfira para CGGM atraves deste método*/
        
        conexao.conectar();
        try
        {
            sql = "UPDATE tblpatrimonios SET clienteid=?, secaoid=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);    
            pst.setInt(1, 202);
            pst.setInt(2, 30);
            pst.setInt(3, pCodigo);
            pst.executeUpdate();
            pst.close();  
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n "+e+" , o sql passado foi \n"+sql);  
            return false;
        }finally{
            conexao.desconectar();
        }        
    }    
      
    public boolean ExcluirPatrimonioDAO(int pCodigo) 
    {
        conexao.conectar();
        try
        {
            sql = "DELETE FROM tblpatrimonios WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1, pCodigo);
            pst.executeUpdate();
            pst.close();  
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n "+e+" , o sql passado foi \n"+sql);  
            return false;
        }finally{
            conexao.desconectar();
        }        
    }
    
    public boolean getPatrimonioPeloCodicoDAO(int pCodigo) 
    {
        conexao.conectar();
        try
        {            
           sql = "SELECT * FROM tblpatrimonios WHERE codigo = '"+pCodigo+"'";           
           conexao.ExecutarPesquisaSQL(sql);            
       
           if(conexao.rs.next()){
              return true; 
           }else{
               return false;
           }                        
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return false;
        }finally{
            conexao.desconectar();
        }  
    }
    
    public String[]getChapaSerieModeloPeloCodigoDAO(int pCodigo) 
    {
        String[]dados = new String[3];
        conexao.conectar();
        try
        {            
           sql = "SELECT serie,chapa,modeloid FROM tblpatrimonios WHERE codigo = '"+pCodigo+"'";           
           conexao.ExecutarPesquisaSQL(sql);            
       
           if(conexao.rs.next()){
               valorPesquisaTrue = true;
               dados[0] = conexao.rs.getString("serie");               
               dados[1] = conexao.rs.getString("chapa");
               dados[2] = String.valueOf(conexao.rs.getInt("modeloid"));
               return dados;
           }else{               
               valorPesquisaTrue = false;
               return null;               
           }                        
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return null;
        }finally{
            conexao.desconectar();
        }  
    }
    
    public boolean AtualizarModeloPatrimonioDAO(Patrimonio umPatrimonio) 
    {
        conexao.conectar();
        try
        {
            sql = "UPDATE tblpatrimonios SET modeloid=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setInt(1, umPatrimonio.getModeloid());
            pst.setInt(2, umPatrimonio.getCodigo());
            pst.executeUpdate();
            pst.close();  
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n "+e+" , o sql passado foi \n"+sql);  
            return false;
        }finally{
            conexao.desconectar();
        }        
    }
    
    public boolean AtualizarPatrimoniosEncaminhadosDAO(Patrimonio umPatrimonio) 
    {
        conexao.conectar();
        try
        {
            sql = "UPDATE tblpatrimonios SET ip=?, estacao=?, secaoid=?, clienteid=?, status=?, motivo=?, datainativacao=?, observacoes=?  WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, umPatrimonio.getIp());
            pst.setString(2, umPatrimonio.getEstacao());
            pst.setInt(3, umPatrimonio.getSecaoid());
            pst.setInt(4, umPatrimonio.getClienteid());
            pst.setString(5, umPatrimonio.getStatus());
            pst.setString(6, umPatrimonio.getMotivo());
            pst.setDate(7, new java.sql.Date(dataDoDia.getTime())); 
            pst.setString(8, umPatrimonio.getObservacoes());
            pst.setInt(9, umPatrimonio.getCodigo());
            pst.executeUpdate();
            pst.close();  
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n "+e+" , o sql passado foi \n"+sql);  
            return false;
        }finally{
            conexao.desconectar();
        }        
    }
    
   public boolean ReativarPatrimonioDAO(Patrimonio umPatrimonio) 
    {        
        conexao.conectar();
        try
        {
            sql = "UPDATE tblpatrimonios SET ip=?, secaoid=?, clienteid=?, deptoid=?, estacao=?, status=?, motivo=?, datainativacao=?, observacoes=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, umPatrimonio.getIp());
            pst.setInt(2, umPatrimonio.getSecaoid());
            pst.setInt(3, umPatrimonio.getClienteid());
            pst.setInt(4, umPatrimonio.getDeptoid());
            pst.setString(5, umPatrimonio.getEstacao());
            pst.setString(6, umPatrimonio.getStatus());
            pst.setString(7, umPatrimonio.getMotivo());      
            if (umPatrimonio.getDatainativacao() == null){ pst.setNull(8, Types.DATE); }else{ pst.setDate(8, new java.sql.Date(umPatrimonio.getDatainativacao().getTime())); }
            pst.setString(9, umPatrimonio.getObservacoes());
            pst.setInt(10, umPatrimonio.getCodigo());
            pst.executeUpdate();
            pst.close();  
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n "+e+" , o sql passado foi \n"+sql);  
            return false;
        }finally{
            conexao.desconectar();
        }        
    }      
  
   public Patrimonio pesquisarPatrimonioDAO(Patrimonio umPatrimonio) 
    {    
        conexao.conectar();
        try
        { 
            sql = "SELECT * FROM tblpatrimonios WHERE (serie = '"+umPatrimonio.getSerie()+"' OR chapa = '"+umPatrimonio.getChapa()+"' "
                + "OR estacao = '"+umPatrimonio.getEstacao()+"' OR codigo = '"+umPatrimonio.getCodigo()+"')";            
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next())
            {
                umPatrimonio.setCodigo(conexao.rs.getInt("codigo"));
                umPatrimonio.setTipoid(conexao.rs.getInt("tipoid"));
                umPatrimonio.setSerie(conexao.rs.getString("serie"));
                umPatrimonio.setChapa(conexao.rs.getString("chapa"));
                umPatrimonio.setIp(conexao.rs.getString("ip"));
                umPatrimonio.setSecaoid(conexao.rs.getInt("secaoid"));
                umPatrimonio.setClienteid(conexao.rs.getInt("clienteid"));
                umPatrimonio.setModeloid(conexao.rs.getInt("modeloid"));
                umPatrimonio.setDeptoid(conexao.rs.getInt("deptoid"));
                umPatrimonio.setEmpresaid(conexao.rs.getInt("empresaid"));
                umPatrimonio.setEstacao(conexao.rs.getString("estacao"));
                umPatrimonio.setStatus(conexao.rs.getString("status"));
                umPatrimonio.setMotivo(conexao.rs.getString("motivo"));
                umPatrimonio.setDatacad(conexao.rs.getDate("datacad"));
                umPatrimonio.setMotivo(conexao.rs.getString("observacoes"));
                umPatrimonio.setContrato(conexao.rs.getString("contrato"));
                cadastrado = true;
            }
        } catch (Exception e) {
            cadastrado = false;
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return umPatrimonio;
    } 
        
    public int buscarCodigo(String nome)
    {
        //retorna o codigo da seção selecionada
        conexao.conectar();      
        sql = "SELECT codigo FROM tblsecoes WHERE nome = '"+nome+"'";           
        conexao.ExecutarPesquisaSQL(sql);            
        try {
            conexao.rs.first();
            return conexao.rs.getInt("codigo");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa! "+ex);
            return 0;
        }finally{
            conexao.desconectar();
        }  
    }        
    
    //VERIFICANDO DUPLICIDADE DE CADASTRO PELA CHAPA
    public boolean duplicidadePelaChapa(String paramCHAPA) 
    {
        //verificando duplicidade no cadastro de micros atraves da chapa, serie, nome da estacao 
        //somente havera duplicidade se houver um registro identico mas com codigo diferente do passado
        //ou seja vai liberar a edicao se for igual ao codigo passado
        
        conexao.conectar();
        sql = "select p.*, t.* from tblpatrimonios p, tbltipos t where (p.chapa = '"+paramCHAPA+"') "
            + "and p.tipoid=1 and p.tipoid=t.codigo";    
        conexao.ExecutarPesquisaSQL(sql);
        try
        {
            if (conexao.rs.next()) 
            {                
                return true;
            }else{
                return false;
            }             
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Erro ao tentar verificar duplicidade no cadastro de equipamento!\n "+e+" , o sql passado foi \n"+sql);  
        }finally{
            conexao.desconectar();
        } 
        return true;        
    }
    //VERIFICANDO DUPLICIDADE DE CADASTRO PELA SERIE
    public boolean duplicidadePelaSerie(String paramSERIE) 
    {
        //verificando duplicidade no cadastro de micros atraves da chapa, serie, nome da estacao 
        //somente havera duplicidade se houver um registro identico mas com codigo diferente do passado
        //ou seja vai liberar a edicao se for igual ao codigo passado
        
        conexao.conectar();
        sql = "select p.*, t.* from tblpatrimonios p, tbltipos t where (p.serie = '"+paramSERIE+"') "
            + "and p.tipoid=1 and p.tipoid=t.codigo";    
        conexao.ExecutarPesquisaSQL(sql);
        try
        {
            if (conexao.rs.next()) 
            {                
                return true;
            }else{
                return false;
            }             
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Erro ao tentar verificar duplicidade no cadastro de equipamento!\n "+e+" , o sql passado foi \n"+sql);  
        }finally{
            conexao.desconectar();
        } 
        return true;        
    }
    
    //VERIFICANDO DUPLICIDADE DE CADASTRO PELA ESTACAO NA TBLPATRIMONIOS
    public boolean duplicidadePelaEstacao(String paramESTACAO) 
    {
        //verificando duplicidade no cadastro de micros atraves da chapa, serie, nome da estacao 
        //somente havera duplicidade se houver um registro identico mas com codigo diferente do passado
        //ou seja vai liberar a edicao se for igual ao codigo passado
        //So fazer a verificação se não estiver cadastrando patrimonio fora da rede
                  
            conexao.conectar();
//            sql = "select p.*, t.* from tblpatrimonios p, tbltipos t where (p.estacao = '"+paramESTACAO+"') "
//                + "and p.tipoid=1 and p.tipoid=t.codigo";    
            sql = "select estacao from tblpatrimonios where (estacao = '"+paramESTACAO+"') and tipoid=1";    
            conexao.ExecutarPesquisaSQL(sql);
            try
            {
                if (conexao.rs.next()) 
                {                
                    return true;
                }else{
                    return false;
                }             
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null,"Erro ao tentar verificar duplicidade no cadastro de equipamento!\n "+e+" , o sql passado foi \n"+sql);  
            }finally{
                conexao.desconectar();
            }       
            return true;
    }               
       
    //VERIFICANDO TODAS AS DUPLICIDADES DE CADASTRO DE ESTAÇÕES PELA CHAPA/SERIE/ESTACAO EM UM SÓ MÉTODO
    public boolean verificarDuplicidadeCadMicrosDAO(String paramCHAPA, String paramSERIE, String paramESTACAO)
    {    
        if(duplicidadePelaChapa(paramCHAPA)){
            JOptionPane.showMessageDialog(null,"Atenção uma estação com Chapa "+paramCHAPA+" já esta cadastrada, verifique se esta Ativa ou Inativa para reativação!","Duplicidade : Chapa "+paramCHAPA+"",2);
            return true;
        }else
            if(duplicidadePelaSerie(paramSERIE)){
            JOptionPane.showMessageDialog(null,"Atenção uma estação com Série "+paramSERIE+" já esta cadastrada, verifique se esta Ativa ou Inativa para reativação!","Duplicidade : Série "+paramSERIE+"",2);
            return true;
        }else 
            //if(duplicidadePelaEstacao(paramESTACAO)){
            if(!cadastrandoEstacaoForaRede && duplicidadePelaEstacao(paramESTACAO)){
            JOptionPane.showMessageDialog(null,"Atenção uma estação com nome de rede "+paramESTACAO+" já esta cadastrada, verifique!","Duplicidade : Estação "+paramESTACAO+"",2);
            return true;
        }
        return false;
    }
    
    public boolean duplicidadeSwitchDAO(int paramCodigo, String paramSERIE) 
    {
        //verificando duplicidade no cadastro de switch atraves serie, nome da estacao 
        //somente havera duplicidade se houver um registro identico mas com codigo diferente do passado
        //ou seja var liberar a edicao se for igual ao codigo passado
        
        conexao.conectar();
        sql = "select p.*, t.* from tblpatrimonios p, tbltipos t where (p.serie = '"+paramSERIE+"') "
            + "and p.tipoid=4 and p.tipoid=t.codigo and p.status='ATIVO' and p.codigo <> '"+paramCodigo+"' ";    
        conexao.ExecutarPesquisaSQL(sql);
        try
        {
            if (conexao.rs.next()) 
            {
                JOptionPane.showMessageDialog(null,"Atenção um "+conexao.rs.getString("tipo")+" com série "+paramSERIE+" já esta cadastrado e ativo, "
                        + "faça uma consulta e verifique!","Duplicidade no cadastro de "+conexao.rs.getString("tipo")+"",2);
                return true;
            }else{
                return false;
            }             
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Erro ao tentar verificar duplicidade no cadastro de equipamento!\n "+e+" , o sql passado foi \n"+sql);  
        }finally{
            conexao.desconectar();
        } 
        return true;        
    }
    
    public boolean duplicidadeScannerDAO(int paramCodigo, String paramSERIE) 
    {
        //verificando duplicidade no cadastro de switch atraves serie, nome da estacao 
        //somente havera duplicidade se houver um registro identico mas com codigo diferente do passado
        //ou seja var liberar a edicao se for igual ao codigo passado
        
        conexao.conectar();
        sql = "select p.*, t.* from tblpatrimonios p, tbltipos t where (p.serie = '"+paramSERIE+"') "
            + "and p.tipoid=5 and p.tipoid=t.codigo and p.status='ATIVO' and p.codigo <> '"+paramCodigo+"' ";    
        conexao.ExecutarPesquisaSQL(sql);
        try
        {
            if (conexao.rs.next()) 
            {
                JOptionPane.showMessageDialog(null,"Atenção um "+conexao.rs.getString("tipo")+" com série "+paramSERIE+" já esta cadastrado e ativo, "
                        + "faça uma consulta e verifique!","Duplicidade no cadastro de "+conexao.rs.getString("tipo")+"",2);
                return true;
            }else{
                return false;
            }             
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Erro ao tentar verificar duplicidade no cadastro de equipamento!\n "+e+" , o sql passado foi \n"+sql);  
        }finally{
            conexao.desconectar();
        } 
        return true;        
    }
    
    public boolean duplicidadeGBitDAO(int paramCodigo, String paramSERIE) 
    {
        //verificando duplicidade no cadastro de switch atraves serie, nome da estacao 
        //somente havera duplicidade se houver um registro identico mas com codigo diferente do passado
        //ou seja var liberar a edicao se for igual ao codigo passado
        
        conexao.conectar();
        sql = "select p.*, t.* from tblpatrimonios p, tbltipos t where (p.serie = '"+paramSERIE+"') "
            + "and p.tipoid=5 and p.tipoid=t.codigo and p.status='ATIVO' and p.codigo <> '"+paramCodigo+"' ";    
        conexao.ExecutarPesquisaSQL(sql);
        try
        {
            if (conexao.rs.next()) 
            {
                JOptionPane.showMessageDialog(null,"Atenção um "+conexao.rs.getString("tipo")+" com série "+paramSERIE+" já esta cadastrado e ativo, "
                        + "faça uma consulta e verifique!","Duplicidade no cadastro de "+conexao.rs.getString("tipo")+"",2);
                return true;
            }else{
                return false;
            }             
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Erro ao tentar verificar duplicidade no cadastro de equipamento!\n "+e+" , o sql passado foi \n"+sql);  
        }finally{
            conexao.desconectar();
        } 
        return true;        
    }
              
    public boolean duplicidadeMonitorDAO(int paramCodigo, String paramSERIE) 
    {
        /*verificando duplicidade no cadastro de monitores atraves da serie e chapa
        Foi necessario separar dos MICROS e IMPRESSORAS porque se deixar junto vai verificar
        ip e estacao que estarao vazios e vai considerar como duplicidade*/
        
        conexao.conectar();
        sql = "select p.serie, p.chapa from tblpatrimonios p, tbltipos t where (p.serie = '"+paramSERIE+"') "
            + "and p.tipoid=2 and p.tipoid=t.codigo and p.codigo <> '"+paramCodigo+"' and p.status='ATIVO'";        
        conexao.ExecutarPesquisaSQL(sql);
        try
        {
            if (conexao.rs.next()) 
            {
                JOptionPane.showMessageDialog(null,"Atenção um monitor com esta série já esta cadastrado e ativo, "
                        + "faça uma consulta e verifique!","Duplicidade no cadastro do monitor com série: "+paramSERIE,2);
                return true;
            }else{
                return false;
            }             
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Não foi possível pesquisar o registro, \n "+e+" , o sql passado foi \n"+sql);  
        }finally{
            conexao.desconectar();
        } 
        return true;
    }  
    
    public int getCodigoPassandoString(String tabela, String nomeCampo, String sParam) {
        //pesquisar codigo de um campo passando seu nome
        int id = 0;
        conexao.conectar();      
        sql = "SELECT codigo FROM "+tabela+" WHERE "+nomeCampo+" = '" +sParam+ "' ";
        conexao.ExecutarPesquisaSQL(sql);        
        try {
            if(conexao.rs.next()){
                id = conexao.rs.getInt("codigo");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa do código! " + ex);
        }
        return id;
    }
    
    public void atualizarNomesEstacoesPadrao(String nomeEstacao)
    {        
        int codigoEst1 = getCodigoPassandoString("TBLNOMESTACAO", "nomestacao", nomeEstacao);
        umModeloNomeEstacao.setCodigo(codigoEst1);
        umModeloNomeEstacao.setNomestacao(nomeEstacao);
        umModeloNomeEstacao.setStatus("DISPONIVEL");
        umControleNomeEstacao.atualizarStatusNomeEstacao(umModeloNomeEstacao);     
    }
            
    public boolean duplicidadeImpressoraDAO(int paramCodigo, String paramSERIE, String paramIP) 
    {
        /*verificando duplicidade no cadastro de impressoras atraves da serie ou ip*/        
       conexao.conectar();
        sql = "select p.serie, p.ip from tblpatrimonios p, tbltipos t where (p.serie = '"+paramSERIE+"' OR p.ip = '"+paramIP+"') and p.tipoid=3 "
            + "and p.codigo <> '"+paramCodigo+"' and p.tipoid=t.codigo and p.status='ATIVO'";        
        conexao.ExecutarPesquisaSQL(sql);
        try
        {
            if (conexao.rs.next()) 
            {
                JOptionPane.showMessageDialog(null,"Atenção uma impressora com a série "+paramSERIE+ " ou ip "+paramIP+" já esta cadastrada e ativa, "
                        + "faça uma consulta e verifique!","Duplicidade no cadastro da impressora",2);
                return true;
            }else{
                return false;
            }             
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Não foi possível pesquisar o registro, \n "+e+" , o sql passado foi \n"+sql);  
        }finally{
            conexao.desconectar();
        } 
        return true;
    }    
    
    public boolean impressoraDuplicadaPeloIP(String paramIp) 
    {
        /*verificando duplicidade no cadastro de ip de impressoras para fins de atualização de registro*/        
       conexao.conectar();
        sql = "select * from tblpatrimonios where tipoid=3 and ip = '"+paramIp+"' and status = 'ATIVO'";        
        conexao.ExecutarPesquisaSQL(sql);
        try
        {
            if (conexao.rs.next()) 
            {
                return true;
            }else{
                return false;
            }             
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Não foi possível verificar a duplicidade de registro pelo IP, \n "+e+" , o sql passado foi \n"+sql);  
        }finally{
            conexao.desconectar();
        } 
        return true;
    }    
    
    public boolean duplicouIPImpressora(String paramIp, int paramCodigo) 
    {
        /*verificando duplicidade no cadastro de ip de impressoras para fins de atualização de registro*/        
       conexao.conectar();
        sql = "select codigo, ip from tblpatrimonios where tipoid=3 and ip = '"+paramIp+"' and codigo <> '"+paramCodigo+"' and status = 'ATIVO'";        
        conexao.ExecutarPesquisaSQL(sql);
        try
        {
            if (conexao.rs.next()) 
            {
                return true;
            }else{
                return false;
            }             
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Não foi possível pesquisar o registro, \n "+e+" , o sql passado foi \n"+sql);  
        }finally{
            conexao.desconectar();
        } 
        return true;
    }    
    
    public boolean ImpressoraDeContrato(int paramCodigo) 
    {
        /*verificando se o patrimonio é de contrato*/        
       conexao.conectar();
        sql = "select codigo,contrato from tblpatrimonios where contrato='S' and codigo = '"+paramCodigo+"' and status = 'ATIVO'";        
        conexao.ExecutarPesquisaSQL(sql);
        try
        {
            if (conexao.rs.next()) 
            {
                isDeContrato=true;                
                return true;
                
            }else{
                return false;
            }             
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Não foi possível pesquisar o registro, \n "+e+" , o sql passado foi \n"+sql);  
        }finally{
            conexao.desconectar();
        } 
        return true;
    }    
    
    public void atualizarNomeEstacoesInativadasDAO() 
    {               
       conexao.conectar();  
       sql = "UPDATE tblpatrimonios set estacao = 'INATIVA', clienteid=202 WHERE (tipoid = 1 or tipoid=19) and status = 'INATIVO'";  
       conexao.ExecutarAtualizacaoSQL(sql);
        
    } 
            
    public boolean atualizarDataRegistroInativadoDAO(Patrimonio umPatrimonio) 
    {
        conexao.conectar();
        try
        {
            sql = "UPDATE tblpatrimonios SET datainativacao=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setDate(1, new java.sql.Date(dataDoDia.getTime())); 
            pst.setInt(2, umPatrimonio.getCodigo());            
            pst.executeUpdate();
            pst.close();  
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n "+e+" , o sql passado foi \n"+sql);  
            return false;
        }finally{
            conexao.desconectar();
        }
        
    }
    public boolean reativarInativadoPelaSerieDAO(Patrimonio umPatrimonio) 
    {
        conexao.conectar();
        try
        {
            sql = "UPDATE tblpatrimonios SET status=?, datainativacao=?, observacoes=?, motivo=? WHERE serie=?";                           
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, umPatrimonio.getStatus());             
            pst.setNull(2, java.sql.Types.DATE); 
            pst.setString(3, umPatrimonio.getObservacoes()); 
            pst.setString(4, umPatrimonio.getMotivo()); 
            pst.setString(5, umPatrimonio.getSerie());            
            pst.executeUpdate();
            pst.close();  
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n "+e+" , o sql passado foi \n"+sql);  
            return false;
        }finally{
            conexao.desconectar();
        }
        
    }
    
    public boolean atualizarNomeEstacaoDAO(Patrimonio umPatrimonio) 
    {
        conexao.conectar();
        try
        {            
            sql = "UPDATE tblpatrimonios set estacao=? WHERE codigo=?";
            
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, umPatrimonio.getEstacao()); 
            pst.setInt(2, umPatrimonio.getCodigo());            
            pst.executeUpdate();
            pst.close();  
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n "+e+" , o sql passado foi \n"+sql);  
            return false;
        }finally{
            conexao.desconectar();
        }
        
    }
    
    public ArrayList<Patrimonio> RecuperaObjetoSQL(String sql) 
    {      
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        
        ArrayList<Patrimonio> lstPatrimonios = new ArrayList<>();
        try {
                        
            while (conexao.rs.next()) 
            {
                Integer vCodigo        = conexao.rs.getInt("codigo");
                Integer vTipoid        = conexao.rs.getInt("tipoid");
                String vIp             = conexao.rs.getString("ip");
                String vSerie          = conexao.rs.getString("serie");
                String vChapa          = conexao.rs.getString("chapa");
                String vEstacao        = conexao.rs.getString("estacao");
                Integer vSecaoid       = conexao.rs.getInt("secaoid");
                Integer vClienteid     = conexao.rs.getInt("clienteid");
                Integer vModeloid      = conexao.rs.getInt("modeloid");
                Integer vDeptoid       = conexao.rs.getInt("deptoid");
                Integer vEmpresaid     = conexao.rs.getInt("empresaid");
                String vStatus         = conexao.rs.getString("status");
                String vMotivo         = conexao.rs.getString("motivo");
                Date vDatacad          = conexao.rs.getDate("datacad");
                String vObs            = conexao.rs.getString("observacoes");
                String vContrato       = conexao.rs.getString("contrato");
                
                Patrimonio objPatrimonios = new Patrimonio();  
                
                objPatrimonios.setCodigo(vCodigo);
                objPatrimonios.setTipoid(vTipoid);
                objPatrimonios.setIp(vIp);
                objPatrimonios.setSerie(vSerie);
                objPatrimonios.setChapa(vChapa);
                objPatrimonios.setEstacao(vEstacao);
                objPatrimonios.setSecaoid(vSecaoid);
                objPatrimonios.setClienteid(vClienteid);
                objPatrimonios.setModeloid(vModeloid);
                objPatrimonios.setDeptoid(vDeptoid);
                objPatrimonios.setStatus(vStatus);
                objPatrimonios.setMotivo(vMotivo);
                objPatrimonios.setDatacad(vDatacad); 
                objPatrimonios.setObservacoes(vObs);    
                objPatrimonios.setContrato(vContrato);    
                
                lstPatrimonios.add(objPatrimonios);
            }
            conexao.desconectar();
        } catch (NumberFormatException | SQLException erro) {
            String erroMsg = "Erro ao recuperar objetos : " + erro.getMessage();
            JOptionPane.showMessageDialog(null, erroMsg, "Mensagem", JOptionPane.ERROR_MESSAGE);
        }

        return lstPatrimonios;
    }    
    
    public ArrayList<Patrimonio> PesquisaObjeto(String sCampo, String sValor, boolean bTodaParte){
        String sql = "select * from tblpatrimonios where " + sCampo + " like '";
        //se nao definir em toda parte considerará apenas palavras que começam com...
        if(bTodaParte)
            sql = sql + "%";
            sql = sql + sValor + "%'";
            sql = sql + "Order by codigo";
        
        return this.RecuperaObjetoSQL(sql);
    } 
    
    public String getSecaoEquipamento(int pCodigo) {
        //Retornar em que seção esta o equipamento
        conexao.conectar();
        try {
            sql = "select s.nome from tblpatrimonios p, tblsecoes s where p.secaoid = s.codigo and p.status='ATIVO' and p.codigo = '"+ pCodigo +"'";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return conexao.rs.getString("nome");
            }else{
                return "";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar nome de seção pelo código do equipamento, \n" + e + ", o sql passado foi \n" + sql);
            return "";
        } finally {
            conexao.desconectar();
        }
    }
    
    public String getMotivos(int pCodigo) {
        conexao.conectar();
        try {
            sql = "SELECT motivo FROM tblPatrimonios WHERE codigo = '"+ pCodigo +"'";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return conexao.rs.getString("motivo");
            }else{
                return "";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar motivo em tblPatrimonios, \n" + e + ", o sql passado foi \n" + sql);
            return "";
        } finally {
            conexao.desconectar();
        }
    }
    
    public String getObs(int pCodigo) {
        conexao.conectar();
        try {
            sql = "SELECT observacoes FROM tblPatrimonios WHERE codigo = '"+ pCodigo +"'";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return conexao.rs.getString("observacoes");
            }else{
                return "";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar observacoes em tblPatrimonios, \n" + e + ", o sql passado foi \n" + sql);
            return "";
        } finally {
            conexao.desconectar();
        }
    }
           
    public Integer getCodTipo(int pCodigo) {
        //retorna o código do tipo para posterior pesquisa do nome do tipo passando o codigo do equipamento
        conexao.conectar();
        try {
            sql = "SELECT tipoid FROM tblPatrimonios WHERE codigo = '"+ pCodigo +"'";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return conexao.rs.getInt("tipoid");
            }else{
                return 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar observacoes em tblPatrimonios, \n" + e + ", o sql passado foi \n" + sql);
            return 0;
        } finally {
            conexao.desconectar();
        }
    }
    
     public String getNomeTipoEquipto(int pCodigo) {
        //retorna o nome do tipo do equipamento passando seu código 
        conexao.conectar();
        try {
            sql = "SELECT tipo FROM tblTipos WHERE codigo = '"+ pCodigo +"'";
            conexao.ExecutarPesquisaSQL(sql);  
            if (conexao.rs.next()){
                return conexao.rs.getString("tipo");
            }else{
                return "";
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar observacoes em tblPatrimonios, \n" + e + ", o sql passado foi \n" + sql);
            return "";
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean Emicro(int pCodigo) {
        conexao.conectar();
        sql = "SELECT * FROM tblpatrimonios WHERE tipoid=1 AND codigo = '"+ pCodigo +"'";
        conexao.ExecutarPesquisaSQL(sql);
        try {
            if (conexao.rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar se o tipo do equipamento é micro pelo código! " + ex);
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public void disponibilizarStatusNomeEstacao(String pEstacao) 
    {        
        conexao.conectar();
        try 
        {
            sql = "UPDATE tblnomestacao SET status=? WHERE nomestacao=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "DISPONIVEL");           
            pst.setString(2, pEstacao);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    } 
    
    public void disponibilizarNomesEstacoesDoMemorando(){
        
        int totalregs = lstListaStrings.size();
        
        for(int i=0; i<totalregs;i++)
        {
            String nomeEstacao  = lstListaStrings.get(i);        
            //System.out.println("dado : "+pCod);    
            disponibilizarStatusNomeEstacao(nomeEstacao);
        }        
        
    }
    
    public void obsComplementarCadastroSemRede(int pCod, String pObservacao)
    {        
        //Grava atualizacao do equipamento transferido pelo codigo
//        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//        Date dataDia  = dataDoDia;      
//        
//        String novaobs  = "\n"+df.format(dataDia)+" : "+pObservacao;

            try
            {
                conexao.conectar();
                sql = "UPDATE tblpatrimonios SET observacoes=? WHERE codigo=?";

                PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
                pst.setString(1, pObservacao);               
                pst.setInt(2, pCod);               
                pst.executeUpdate();
                pst.close(); 

                //return true;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n "+e+" , o sql passado foi \n"+sql); 
            }finally{
                conexao.desconectar();
                //return false;
            }           
    }             
     
    public ArrayList<String> getSeriesPatrimoniosDoMemorando(String sNumemo) 
    {
        ArrayList<String> lista = new ArrayList<>();
        String sql = "SELECT serie FROM TBLITENSMEMOTRANSFERIDOS WHERE numemo = ?";

        try {
            conexao.conectar();
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, sNumemo);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                lista.add(rs.getString("serie"));
            }
            
            rs.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar patrimônios do memorando:\n" + e);
        } finally {
            conexao.desconectar();
        }

        return lista;
}
    
     public int obterCodigoPatrimonioPelaChapa(String serie) {
        int codigo = -1; // valor inválido padrão
        try {
            conexao.conectar();
            String sql = "SELECT codigo FROM tblpatrimonios WHERE serie = ?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, serie);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                codigo = rs.getInt("codigo");
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            System.out.println("Erro ao buscar código pelo chapa: " + e.getMessage());
        } finally {
            conexao.desconectar();
        }
        return codigo;
    }
    
    public void InativarPatrimonioPeloMemorandoDAO(String pNumemo) {
        //limpa a lista generica para receber as series do memorando
        lstListaGenerica.clear();
        //carregando a lista com as series do memorando
        lstListaGenerica = getSeriesPatrimoniosDoMemorando(pNumemo);
        
        int totalregs = lstListaGenerica.size();

        for (int i = 0; i < totalregs; i++) 
        {
            String serie = lstListaGenerica.get(i);  // ← Para cada serie da lista busca seu respectivo codigo na tblpatrimonios para atualizar cada patrimonio
            int pCod = obterCodigoPatrimonioPelaChapa(serie);
            
            //grava as atualizacos de cada um
            gravarUpdateMemos(pCod, pNumemo);
        }
}
        
    public void disponibilizarIpsDoContratoFinalizado(String pIp) 
    {
        conexao.conectar();
        try 
        {
            sql = "UPDATE TBLIPSDISPONIVEIS SET status=? WHERE ip=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "DISPONIVEL");           
            pst.setString(2, pIp);
            pst.executeUpdate(); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando de inserção sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
    }  
    
    public ArrayList<String> listaDeIpsAdisponibilizar(int pCodEmpresa){
        ArrayList<String> litaIps = new ArrayList<>();
     
        conexao.conectar();
        String sqlPesquisa = "SELECT ip from tblpatrimonios where tipoid=3 and contrato='S' and status='ATIVO' and empresaid = "+pCodEmpresa+"";            
        conexao.ExecutarPesquisaSQL(sqlPesquisa); 
        
        try 
        {
            while(conexao.rs.next()){
                litaIps.add(conexao.rs.getString("ip")); 
            }   
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível pesquisar a lista de codigos de ips a disponibilizar sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
      
        return litaIps;         
    }
    
    public ArrayList<String> listaDeImpressorasContratoFinalizado(int pCodEmpresa){
        ArrayList<String> litaImpressoras = new ArrayList<>();
        
     
        conexao.conectar();
        String sqlPesquisa = "SELECT empresaid, modeloid, serie, chapa from tblpatrimonios where tipoid=3 and contrato='S' and status='ATIVO' and empresaid = "+pCodEmpresa+"";            
        conexao.ExecutarPesquisaSQL(sqlPesquisa); 
        
        try 
        {
            while(conexao.rs.next()){
                
                litaImpressoras.add(conexao.rs.getString("empresaid")); 
                litaImpressoras.add(conexao.rs.getString("modeloid")); 
                litaImpressoras.add(conexao.rs.getString("serie")); 
                litaImpressoras.add(conexao.rs.getString("chapa")); 
            }   
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível pesquisar a lista de codigos de ips a disponibilizar sql, \n"+e+", o sql passado foi \n"+sql);              
        } finally {
            conexao.desconectar();
        }
      
        return litaImpressoras;         
    }
    
    public void ReativarPatrimonioPeloMemorandoDAO(String pNumemo){
        //SE NAO FOR CPU        
        int totalregs = lstListaGenerica.size();
        
        for(int i=0; i<totalregs;i++)
        {
            int pCod       = Integer.valueOf(lstListaGenerica.get(i));       
            gravarUpdateMemosRecebimento(pCod,pNumemo);
        }
        /*DAR ENTRADA DOS PATRIMONIOS NA CGGM/INFO secaoid = 30 clienteid = 202  deptoid = 6 => Se for micro estacao = PGMCGGMC000 => */
    } 
      
    public void gravarUpdateMemosRecebimento(int pCod, String numemo){
        //Grava atualizacao do equipamento transferido pelo codigo
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dataDia  = dataDoDia;
        String status, motivo, obs, ip, destino, estacao ="";
        origemPatrTransferido   = getSecaoEquipamento(pCod);
        int codigoSecao         = 0;   
        int codTipoEquipto      = 0;
        int deptoid             = 6;
        String nomeTipoEquipto  = "";               
        
        if(Emicro(pCod))
        {
            //PARA MICROS  
            destino = destinoTransferidos;            
           
            codigoSecao    = 30;
            codigoCliente  = 202; 
            status         = "ATIVO"; 
            estacao        = "PGMCGGMC000";
                 
            
            disponibilizarNomesEstacoesDoMemorando(); 
                              
            ip                      = "0"; 
            motivo = getMotivos(pCod)+"\n"+df.format(dataDia)+" : Reativado por motivo de Devolucao por "+origemTransferidos+" atraves do memorando "+numemo+".";  
            obs    = getObs(pCod)+"\n"+df.format(dataDia)+" : Devolvido por "+origemTransferidos+" atraves do memorando "+numemo+".";            
                       
            try
            {
                conexao.conectar();
                sql = "UPDATE tblpatrimonios SET estacao=?, ip=?, secaoid=?, clienteid=?, deptoid=?, status=?, motivo=?, observacoes=? WHERE tipoid=1 AND codigo=?";

                PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
                pst.setString(1, estacao);   
                pst.setString(2, ip);   
                pst.setInt(3, codigoSecao);     
                pst.setInt(4, codigoCliente);  
                pst.setInt(5, deptoid);  
                pst.setString(6, status);   
                pst.setString(7, motivo);   
                pst.setString(8, obs);               
                pst.setInt(9, pCod);               
                pst.executeUpdate();
                pst.close(); 

                //return true;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n "+e+" , o sql passado foi \n"+sql); 
            }finally{
                conexao.desconectar();
                //return false;
            }              
        }else{   //PARA NÃO MICROS TIPO SCANNER / MONITOR / IMPRESSORA                     
            
            codTipoEquipto  = getCodTipo(pCod); //retorna o codigo do tipo de equipamento  ex : 1
            nomeTipoEquipto = getNomeTipoEquipto(codTipoEquipto); //retorna o nome do tipo de equipamento ex : MICRO
            
            destino = destinoTransferidos;            
            
            codigoSecao    = 30;
            codigoCliente  = 202; 
            status         = "ATIVO";           
            estacao        = nomeTipoEquipto;
            ip             = "0";    
            motivo = getMotivos(pCod)+"\n"+df.format(dataDia)+" : Reativado por motivo de Devolucao por "+origemTransferidos+" atraves do memorando "+numemo+".";  
            obs    = getObs(pCod)+"\n"+df.format(dataDia)+" : Devolvido por "+origemTransferidos+" atraves do memorando "+numemo+".";                 
                       
            try
            {
                conexao.conectar();
                sql = "UPDATE tblpatrimonios SET estacao=?, ip=?, secaoid=?, clienteid=?, deptoid=?, status=?, motivo=?, observacoes=? WHERE tipoid<>1 AND codigo=?";

                PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
                pst.setString(1, estacao);
                pst.setString(2, ip);  
                pst.setInt(3, codigoSecao); 
                pst.setInt(4, codigoCliente);  
                pst.setInt(5, deptoid);  
                pst.setString(6, status);   
                pst.setString(7, motivo);   
                pst.setString(8, obs);               
                pst.setInt(9, pCod);               
                pst.executeUpdate();
                pst.close(); 

                //return true;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n "+e+" , o sql passado foi \n"+sql); 
            }finally{
                conexao.desconectar();
                //return false;
            }     
        }
    }                         

public void updateStatusPosExclusaoItemDeMemoEnviado(int pCod, String numemo){
    
        //Reativando patrimonio pos exclusao de item em ediçao de memorando de encaminhamento
        //Grava atualizacao do equipamento transferido pelo codigo
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dataDia  = dataDoDia;
        
        String status, estacao, tipo, motivo, obs  ="";

        status                  = "ATIVO";           
        estacao                 = "PGMCGGMC000";  
        motivo                  = getMotivos(pCod)+"\n"+df.format(dataDia)+" : Reativado por motivo de exclusao/estorno do memorando "+numemo+".";  
        obs                     = getObs(pCod)+"\n"+df.format(dataDia)+" : Reativado por motivo de exclusao/estorno do memorando "+numemo+"."; 
        int codTipoEquipto      = 0;
        String nomeTipoEquipto  = "";    
        codTipoEquipto          = getCodTipo(pCod); //retorna o codigo do tipo de equipamento  ex : 1
        nomeTipoEquipto         = getNomeTipoEquipto(codTipoEquipto); //retorna o nome do tipo de equipamento ex : MICRO
        
        if(Emicro(pCod))
        {
            //PARA MICROS  
            try
            {
                conexao.conectar();
                sql = "UPDATE tblpatrimonios SET estacao=?, status=?, motivo=?, datainativacao=?, observacoes=? WHERE codigo=?";

                PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
                pst.setString(1, estacao);           
                pst.setString(2, status);   
                pst.setString(3, motivo);   
                pst.setNull(4, Types.DATE);   
                pst.setString(5, obs);               
                pst.setInt(6, pCod);               
                pst.executeUpdate();
                pst.close(); 

                //return true;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n "+e+" , o sql passado foi \n"+sql); 
            }finally{
                conexao.desconectar();
                //return false;
            }    
        }else{
            //PARA NAO MICROS
            estacao = nomeTipoEquipto;
            try
            {
                conexao.conectar();
                sql = "UPDATE tblpatrimonios SET estacao=?, status=?, motivo=?, datainativacao=?, observacoes=? WHERE codigo=?";

                PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
                pst.setString(1, estacao);           
                pst.setString(2, status);   
                pst.setString(3, motivo);   
                pst.setNull(4, Types.DATE);   
                pst.setString(5, obs);               
                pst.setInt(6, pCod);               
                pst.executeUpdate();
                pst.close(); 

                //return true;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n "+e+" , o sql passado foi \n"+sql); 
            }finally{
                conexao.desconectar();
                //return false;
            }                
        }
}

public boolean verificarSePesquisaRetornouDados(String sql){

    conexao.conectar();
    boolean temDados = conexao.ExecutarPesquisaSQLComRetorno(sql);

    if (temDados) {
        System.out.println("A pesquisa retornou resultados.");
        return true;
    } else {
        System.out.println("Nenhum dado encontrado.");
        return true;
    }
}

public void gravarUpdateMemos(int pCod, String numemo)
{
        //Grava atualizacao do equipamento transferido pelo codigo    
            
        DateFormat df           = new SimpleDateFormat("dd/MM/yyyy");
        Date dataDia            = dataDoDia;
        String status, motivo, obs, ip, destino, estacao ="";
        origemPatrTransferido   = getSecaoEquipamento(pCod);
        int codigoSecao         = 0;   
        int codTipoEquipto      = 0;
        String nomeTipoEquipto  = "";
        
        
        //REFERENTE A TRANSFERENCIA DE UM PATRIMONIO QUE NAO SEJA MICRO POR CONTA DE MICRO AINDA TERMOS DE BAIXAR O NOME 
        //E QUE TAMBEM NAO SEJA PARA CEJUR/CEJUSC/PFM ONDE O CLIENTEID DEVERA SER SEM USUARIO DAQUELE SETOR
        
        if(Emicro(pCod))
        {
            //PARA MICROS             
            destino = destinoTransferidos;
            
            if(destinoTransferidos.equals("PFM"))
            {
                codigoSecao    = 17;
                codigoCliente  = 227;                 
                status         = "ATIVO"; 
                estacao        = "PGMPFMC000";
            }else
            if(destinoTransferidos.equals("CEJUSC"))
            {                
                codigoSecao    = 21;
                codigoCliente  = 197; 
                status         = "ATIVO"; 
                estacao        = "PGMCEJUSCC0";
            }else
            if(destinoTransferidos.equals("CEJUR"))
            {                
                codigoSecao    = 3;
                codigoCliente  = 196; 
                status         = "ATIVO"; 
                estacao        = "PGMCEJURC00";
            }else
            if(destinoTransferidos.equals("BIBLIOTECA"))
            {                
                codigoSecao    = 22;
                codigoCliente  = 195; 
                status         = "ATIVO"; 
                estacao        = "PGMCEJURC00";
            }else{                
                codigoSecao    = 30;
                codigoCliente  = 202; 
                status         = "INATIVO"; 
                estacao        = "PGMCGGMC000";
            }       
            
            disponibilizarNomesEstacoesDoMemorando(); 
                        
            //estacao                 = "INATIVA"; 
            //status                  = "INATIVO"; //04/07/2022 : Inativado por motivo de Transferência para JUD        
            ip                      = "0"; 
            motivo = getMotivos(pCod)+"\n"+df.format(dataDia)+" : Inativado por motivo de Transferencia de "+origemPatrTransferido+" para "+destino+" atraves do memorando "+numemo+".";  
            obs    = getObs(pCod)+"\n"+df.format(dataDia)+" : Transferido de "+origemPatrTransferido+" para "+destino+" atraves do memorando "+numemo+".";            
                       
            try
            {
                conexao.conectar();
                sql = "UPDATE tblpatrimonios SET estacao=?, ip=?, secaoid=?, clienteid=?, status=?, motivo=?, observacoes=?, datainativacao=? WHERE tipoid=1 AND codigo=?";

                PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
                pst.setString(1, estacao);   
                pst.setString(2, ip);   
                pst.setInt(3, codigoSecao);     
                pst.setInt(4, codigoCliente);     
                pst.setString(5, status);   
                pst.setString(6, motivo);   
                pst.setString(7, obs);               
                pst.setDate(8, new java.sql.Date(dataDoDia.getTime())); 
                pst.setInt(9, pCod);          
                pst.executeUpdate();       
                pst.close(); 

                //return true;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n "+e+" , o sql passado foi \n"+sql); 
            }finally{
                conexao.desconectar();
                //return false;
            }              
        }else{   //PARA NÃO MICROS TIPO SCANNER / MONITOR / IMPRESSORA     
            codTipoEquipto  = getCodTipo(pCod); //retorna o codigo do tipo de equipamento  ex : 1
            
            nomeTipoEquipto = getNomeTipoEquipto(codTipoEquipto); //retorna o nome do tipo de equipamento ex : MICRO
            destino = destinoTransferidos;
           
            switch(destinoTransferidos.toUpperCase()) {
                case "PFM":
                    codigoSecao = 17;
                    codigoCliente = 227;
                    status = "ATIVO";
                    break;
                case "CEJUSC":
                    codigoSecao = 21;
                    codigoCliente = 197;
                    status = "ATIVO";
                    break;
                case "CEJUR":
                    codigoSecao = 3;
                    codigoCliente = 196;
                    status = "ATIVO";
                    break;
                case "BIBLIOTECA":
                    codigoSecao = 22;
                    codigoCliente = 195;
                    status = "ATIVO";
                    break;
                case "LINAO":               // Adicione aqui o destino que você mencionou
                    codigoSecao = 30;       // Ajuste conforme o valor correto para LINAO
                    codigoCliente = 202;    // Ajuste conforme necessário
                    status = "INATIVO";     // Ou "ATIVO", conforme sua regra
                    break;
                default:
                    codigoSecao = 30;
                    codigoCliente = 202;
                    status = "INATIVO";
                    break;
            }                       
               
            estacao            = nomeTipoEquipto;
            ip                 = "0";    
            motivo             = getMotivos(pCod)+"\n"+df.format(dataDia)+" : Inativado por motivo de Transferencia de "+origemPatrTransferido+" para "+destino+" atraves do memorando "+numemo+"."; 
            obs                = getObs(pCod)+"\n"+df.format(dataDia)+" : Transferido de "+origemPatrTransferido+" para "+destino+" atraves do memorando "+numemo+".";           

            try
            {
                conexao.conectar();
                sql = "UPDATE tblpatrimonios SET estacao=?, ip=?, secaoid=?, clienteid=?, status=?, motivo=?, observacoes=?, datainativacao=? WHERE tipoid<>1 AND codigo=?";

                PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
                pst.setString(1, estacao);
                pst.setString(2, ip);  
                pst.setInt(3, codigoSecao); 
                pst.setInt(4, codigoCliente);  
                pst.setString(5, status);   
                pst.setString(6, motivo);   
                pst.setString(7, obs);               
                pst.setDate(8, new java.sql.Date(dataDoDia.getTime())); 
                pst.setInt(9, pCod);               
                pst.executeUpdate();
                pst.close(); 

                //return true;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n "+e+" , o sql passado foi \n"+sql); 
            }finally{
                conexao.desconectar();
                //return false;
            }     
        }
    }                         
}

