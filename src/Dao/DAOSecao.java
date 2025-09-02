package Dao;

import biblioteca.Biblioteca;
import static biblioteca.Biblioteca.lstListaDeStrings;
import biblioteca.MetodosPublicos;
import static biblioteca.VariaveisPublicas.codigoDepartamento;
import static biblioteca.VariaveisPublicas.dataDoDia;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.Date;
import java.sql.PreparedStatement;
import modelo.Secao;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.NomeEstacao;
import controle.CtrlNomeEstacao;

public class DAOSecao {

    ConnConexao         conexao      = new ConnConexao();    
    Biblioteca          umaBiblio    = new Biblioteca();
    MetodosPublicos     umMetodo     = new MetodosPublicos();
    NomeEstacao         umaEstacao   = new NomeEstacao();
    CtrlNomeEstacao     crtlEstacao  = new CtrlNomeEstacao();
        
    public boolean salvarSecaoDAO(Secao pSecao) 
    {
        conexao.conectar();
        try 
        {
            sql = "INSERT INTO tblsecoes (nome, email, pr, simproc, sei, deptoid, status, ramal, obs) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pSecao.getNome());
            pst.setString(2, pSecao.getEmail());
            pst.setString(3, pSecao.getPr());
            pst.setString(4, pSecao.getSimproc());
            pst.setString(5, pSecao.getSei());
            pst.setInt(6, pSecao.getDeptoid());
            pst.setString(7, "ATIVO");
            pst.setString(8, pSecao.getRamal());
            pst.setString(9, pSecao.getObs());
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public boolean atualizarSecaoDAO(Secao pSecao)
    {
        conexao.conectar();
        try
        {
            sql = "UPDATE tblsecoes SET nome=?, email=?, pr=?, simproc=?, sei=?, deptoid=?, status=?, ramal=?, obs=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pSecao.getNome());
            pst.setString(2, pSecao.getEmail());
            pst.setString(3, pSecao.getPr());
            pst.setString(4, pSecao.getSimproc());
            pst.setString(5, pSecao.getSei());
            pst.setInt(6, pSecao.getDeptoid());
            pst.setString(7, pSecao.getStatus()); 
            pst.setString(8, pSecao.getRamal());
            pst.setString(9, pSecao.getObs());
            pst.setInt(10, pSecao.getCodigo());
            pst.executeUpdate();
            pst.close();  
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível atualizar o registro, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        }finally{
            conexao.desconectar();
        }
        
    }
    
    public Secao pesquisarSecaoDAO(Secao pSecao)
    {       
        try
        {
            conexao.conectar();
            sql = "SELECT * FROM tblsecoes WHERE (codigo = '"+pSecao.getCodigo()+"') OR (nome = '"+pSecao.getNome()+"') OR (simproc = '"+pSecao.getSimproc()+"') OR (sei = '"+pSecao.getSei()+"') OR (pr = '"+pSecao.getPr()+"')";            
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                pSecao.setCodigo(conexao.rs.getInt("codigo"));
                pSecao.setNome(conexao.rs.getString("nome"));
                pSecao.setEmail(conexao.rs.getString("email"));
                pSecao.setPr(conexao.rs.getString("pr"));
                pSecao.setSimproc(conexao.rs.getString("simproc"));
                pSecao.setSei(conexao.rs.getString("sei"));
                pSecao.setDeptoid(conexao.rs.getInt("deptoid"));
                pSecao.setStatus(conexao.rs.getString("status"));
                pSecao.setStatus(conexao.rs.getString("ramal"));
                pSecao.setObs(conexao.rs.getString("obs"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql); 
        } finally {
            conexao.desconectar();
        }
        return pSecao;
    }
    
    public boolean excluirSecaoDAO(int pCodigo)
    {
        try
        {            
            conexao.conectar();            
            sql = "DELETE FROM tblsecoes WHERE codigo = '" + pCodigo + "'";            
            conexao.ExecutarPesquisaSQL(sql);            
            return true;
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Não foi possível excluir o registro, \n"+e+", o sql passado foi \n"+sql);
            return false;
       } finally {
            conexao.desconectar();
        }        
    }    
           
    public boolean RegistroDuplicado(Secao pSecao)
    {
        conexao.conectar();                          
        try 
        {              
            sql = "SELECT * FROM tblsecoes WHERE nome = '"+pSecao.getNome()+"'";
            conexao.ExecutarPesquisaSQL(sql);            
            if(conexao.rs.next())
            {
                //tipo de msg = 1 é a de informacao msg = 2 é a de exclamação  msg = 3 é a de interrogação
                JOptionPane.showMessageDialog(null,"Atenção esta seção já esta cadastrada, verifique!","Duplicidade no nome da seção!",2);                
                return true;
                          
            }else{ return false; }            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa sobre duplicidade! "+ex);
             return false;
        }finally{
            conexao.desconectar();
        }
               
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
    public boolean salvarSecaoInicialDAO() 
    {
        conexao.conectar();
        try 
        {
            sql = "INSERT INTO tblsecoes (nome,email,pr,deptoid,status,ramal,obs) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, "INFORMATICA");
            pst.setString(2, "pgminfo@prefeitura.sp.gov.br");
            pst.setString(3, "PR1L059");
            pst.setInt(4, 1);
            pst.setString(5, "ATIVO");
            pst.setString(6, "1768");
            pst.setString(7, "Cadastro inicial da Informatica PGM");
            pst.executeUpdate(); 
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Não foi possível executar o comando sql, \n"+e+", o sql passado foi \n"+sql);  
            return false;
        } finally {
            conexao.desconectar();
        }
    }
    
    public ArrayList<Secao> RecuperaObjetoSQL(String sql) 
    {      
        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);
        
        ArrayList<Secao> lstSecao = new ArrayList<>();
        try {
                        
            while (conexao.rs.next()) 
            {
                Integer vCodigo = conexao.rs.getInt("codigo");
                String vNome    = conexao.rs.getString("nome");
                String vPR      = conexao.rs.getString("pr");
                String vSimproc = conexao.rs.getString("simproc");
                String vSei     = conexao.rs.getString("sei");
                Integer vDepto  = conexao.rs.getInt("deptoid");
                String vStatus  = conexao.rs.getString("status");
                String vRamal   = conexao.rs.getString("ramal");
                String vObs     = conexao.rs.getString("obs");
                
                Secao objSecao = new Secao();    
                
                objSecao.setCodigo(vCodigo);
                objSecao.setNome(vNome);
                objSecao.setPr(vPR);
                objSecao.setSimproc(vSimproc);
                objSecao.setSei(vSei);
                objSecao.setDeptoid(vDepto);
                objSecao.setStatus(vStatus);
                objSecao.setRamal(vRamal);
                objSecao.setObs(vObs);
                
                lstSecao.add(objSecao);
            }
            conexao.desconectar();
        } catch (NumberFormatException | SQLException erro) {
            String erroMsg = "Erro ao recuperar objetos : " + erro.getMessage();
            JOptionPane.showMessageDialog(null, erroMsg, "Mensagem", JOptionPane.ERROR_MESSAGE);
        }

        return lstSecao;
    }
        
    public ArrayList<Secao> PesquisaObjeto(String sCampo, String sValor, boolean bTodaParte){
        sql = "select * from tblsecoes where " + sCampo + " like '";
        //se nao definir em toda parte considerará apenas palavras que começam com...
        if(bTodaParte)
            sql = sql + "%";
            sql = sql + sValor + "%'";
            sql = sql + "Order by nome";
            
        return RecuperaObjetoSQL(sql);
    }
    public void inativarClientesComSecaoDAO(int idSecao)
    {
        //inativando todos os clientes da seção inativada
        conexao.conectar();
        sql = "UPDATE tblclientes SET status='INATIVO' WHERE secaoid = "+idSecao;      
        conexao.ExecutarAtualizacaoSQL(sql);
        
    }
     public void inativarUsuariosComSecaoDAO(int idSecao)
    {
        //inativando todos os usuários da seção inativada
        conexao.conectar();
        sql = "UPDATE tblusuarios SET status='INATIVO' WHERE secaoid = "+idSecao;      
        conexao.ExecutarAtualizacaoSQL(sql);
        
    }
    
     public void alteraStatusDoPatrimonioParaInativo(String novoMotivo, int codigoPatrimonio, int idSecao)
    {
        //inativando os patrimonios tem que manter os motivos e obs que ja existem e acrescentar esta linha em motivo e obs
        Date dataInativacao = new java.sql.Date(dataDoDia.getTime());
        
        String motivoAtual = umMetodo.getStringPassandoCodigo("tblpatrimonios", "motivo", codigoPatrimonio);
        String obsAtual    = umMetodo.getStringPassandoCodigo("tblpatrimonios", "observacoes", codigoPatrimonio);
        
        motivoAtual = motivoAtual.isEmpty() ? novoMotivo : motivoAtual+" "+novoMotivo;
        obsAtual    = obsAtual.isEmpty() ? novoMotivo : obsAtual+" "+novoMotivo;                
                
        conexao.conectar();
        sql = "UPDATE tblpatrimonios SET status='INATIVO', deptoid=0, motivo='"+motivoAtual+"', datainativacao='"+dataInativacao+"', observacoes='"+obsAtual+"' WHERE "
             +"codigo = "+codigoPatrimonio+" and secaoid = "+idSecao;      
        conexao.ExecutarAtualizacaoSQL(sql);                 
    } 
     
    private void disponibilizarNomeEstacaoPeloNome(String nomeStacao)
    {   
        umaEstacao.setStatus("DISPONIVEL");
        umaEstacao.setNomestacao(nomeStacao);
        crtlEstacao.atualizarStatusPeloNomeDaEstacao(umaEstacao);      

    }
     
    public void inativarPatrimoniosComSecaoDAO(int idSecao, String motivo)
    {       
        int codigoPatrimonio; 
        String nomeEstacao;
                     
        try
        {     
            conexao.conectar();                   
            sql = "SELECT codigo, estacao FROM tblPatrimonios WHERE secaoid = "+idSecao; 
            conexao.ExecutarPesquisaSQL(sql); 
            while(conexao.rs.next())               
            {
                codigoPatrimonio    = conexao.rs.getInt("codigo"); 
                nomeEstacao         = conexao.rs.getString("estacao"); 
                alteraStatusDoPatrimonioParaInativo(motivo,codigoPatrimonio,idSecao);
                //colocar o nome da estacao a disposicao : descobrir o codigo do nome da estacao e TBLNOMESTACAO / mudar o status para DISPONIVEL 
                disponibilizarNomeEstacaoPeloNome(nomeEstacao);
            }
            } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Rotina não conseguiu pesquisar os resgistros!\n"+e);
        } finally {
            conexao.desconectar();
        }        
    }      
    
    public ArrayList<String> pegarListaDeEstacoes(int idSecao)
    {
        //metodo criado para pegar todos os nomes de estacoes da secao identificada pelo seu id e jogar dentro do arrayList(lstListaDeStrings) do tipo global
        String sEstacao = null;  
        try
        {       
            conexao.conectar();                   
            sql = "SELECT ESTACAO FROM tblpatrimonios WHERE DEPTOID=1 AND ESTACAO<>'MONITOR' AND ESTACAO<>'IMPRESSORA' AND STATUS='ATIVO' AND SECAOID = '"+idSecao+"'";             
            conexao.ExecutarPesquisaSQL(sql);             
            while(conexao.rs.next())            
            {
                sEstacao = conexao.rs.getString("estacao"); 
                lstListaDeStrings.add(sEstacao);                                  
            }
         
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Rotina não conseguiu pesquisar os resgistros!\n"+e);
        } finally {
            conexao.desconectar();
        }   
        return lstListaDeStrings;
    }
    
    public ArrayList<String> pegarListaDeIPSDisponibilizados(int idSecao)
    {
        //metodo criado para pegar todos os nomes de estacoes da secao identificada pelo seu id e jogar dentro do arrayList(lstListaDeStrings) do tipo global
        String sIP = null;  
        try
        {       
            conexao.conectar();                   
            sql = "SELECT IP FROM tblpatrimonios WHERE DEPTOID=1 AND ESTACAO='IMPRESSORA' AND STATUS='ATIVO' AND SECAOID = '"+idSecao+"'";             
            conexao.ExecutarPesquisaSQL(sql);             
            while(conexao.rs.next())            
            {
                sIP = conexao.rs.getString("ip"); 
                lstListaDeStrings.add(sIP);                                  
            }
         
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Rotina não conseguiu pesquisar os resgistros!\n"+e);
        } finally {
            conexao.desconectar();
        }   
        return lstListaDeStrings;
    }    
    
    private void alteraStatusDoIPParaDisponivelDAO(String ip)
    {
        //alterando o status do IP para DISPONIVEL
        conexao.conectar();
        sql = "UPDATE TBLIPSDISPONIVEIS SET status='DISPONIVEL' WHERE ip = '"+ip+"'";        
        conexao.ExecutarAtualizacaoSQL(sql);        
    }
    
    public void disponibilizarIPsImpressoras(int idSecao){
       //alterar o status para DISPONIVEL de cada estacao contida na lista lstListaDeStrings
        pegarListaDeIPSDisponibilizados(idSecao);        
       
        for(int i=0; i<lstListaDeStrings.size(); i++)
        {
            //executando o metodo de disponibilizacao acima passando todos os ips de impressoras contidos na lstListaDeStrings
            alteraStatusDoIPParaDisponivelDAO(lstListaDeStrings.get(i).toString());
        }            
    }
       
    public boolean inativarPatrimonioUsuarioClientesDAO(int idSecao, String motivo)
    {
        //unificando as inativacoes todas em um só metodo, sendo que a inativacao da secao é feita no botao gravar do formulario
        inativarClientesComSecaoDAO(idSecao);
        inativarUsuariosComSecaoDAO(idSecao);
        inativarPatrimoniosComSecaoDAO(idSecao, motivo);        
        return true;
    }   
    
    public void reativarClientesComSecaoDAO(int idSecao)
    {
        //inativando todos os clientes da seção inativada
        conexao.conectar();
        sql = "UPDATE tblclientes SET status='ATIVO' WHERE secaoid = "+idSecao;      
        conexao.ExecutarAtualizacaoSQL(sql);
        
    }
    
    public boolean reativarClienteSemUsuarioDAO(int codigoCliente)
    {        
        conexao.conectar();
        sql = "UPDATE tblclientes SET status='ATIVO' WHERE codigo = "+codigoCliente;     
        conexao.ExecutarAtualizacaoSQL(sql);
        return true;
        
    }
    
    public boolean reativarSecaoDAO(int idSecao, String motivo, int codigoDepto)
    {                
        conexao.conectar();                
        sql = "UPDATE tblsecoes SET status='ATIVO', deptoid='"+codigoDepto+"', obs = '"+motivo+"'  WHERE codigo='"+idSecao+"'";   
        conexao.ExecutarAtualizacaoSQL(sql);
        return true;
        
    }
    
     public void reativarUsuariosComSecaoDAO(int idSecao)
    {
        //inativando todos os usuários da seção inativada
        conexao.conectar();
        sql = "UPDATE tblusuarios SET status='ATIVO' WHERE secaoid = "+idSecao;      
        conexao.ExecutarAtualizacaoSQL(sql);
        
    }
    public void reativarPatrimoniosComSecaoDAO(int idSecao, String motivo)
    {       
        //este metodo atualiza os registro definidos pelo idSecao, nao fiz direto porque preciso inserir na descricao 
        //o motivo sem alterar a descricao atual, apenas adicionar
        try
        {               
            int codigoPatrimonio;
            String descricaoPatrimonio;
                     
            conexao.conectar();                   
            sql = "SELECT p.*, m.* FROM tblPatrimonios p, tblmodelos m WHERE m.codigo=p.modeloid and p.secaoid = "+idSecao; 
            conexao.ExecutarPesquisaSQL(sql); 
            conexao.rs.first();                
            do
            {
                if(conexao.rs != null)
                {
                    descricaoPatrimonio = conexao.rs.getString("descricao"); 
                    codigoPatrimonio    = conexao.rs.getInt("codigo"); 
                    reativarPatrimonio(motivo,codigoPatrimonio,idSecao);
                    
                }else
                    JOptionPane.showMessageDialog(null,"Não foi possivel atualizar os registros....");
                                  
            }while(conexao.rs.next()); 
         
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Rotina não conseguiu percorrer resgistros e atualizar tabela!\n"+e);
        } finally {
            conexao.desconectar();
        }
       
    }    
        
    public void reativarPatrimonio(String motivo, int codigoPatrimonio, int idSecao)
    {
        String secao   = umMetodo.retornarNomeSecao(idSecao);
        //JOptionPane.showMessageDialog(null,"nome da secao :"+secao);
        //JOptionPane.showMessageDialog(null,"ID da secao : "+idSecao);
        
        conexao.conectar();
        sql = "UPDATE tblpatrimonios SET status='ATIVO', motivo = '"+motivo+"' WHERE "
             +"codigo = "+codigoPatrimonio+" and secaoid = "+idSecao+"";      
        conexao.ExecutarAtualizacaoSQL(sql); 
        umMetodo.atualizarStatusPatrimoniosReativados(idSecao,codigoDepartamento);
    }
    
    public boolean reativarSecaoComPatrimoniosEClientesDAO(int idSecao, String motivo)
    {
        //unificando as reativaçoes todas em um só metodo
        //reativarSecaoDAO(idSecao);
        reativarClientesComSecaoDAO(idSecao);
        reativarPatrimoniosComSecaoDAO(idSecao, motivo);
        return true;
    }   
    
    public boolean reativarSecaoComPatrimoniosDAO(int idSecao, String motivo){
        //reativa a secao juntamente com seus micros
        //reativarSecaoDAO(idSecao);
        reativarPatrimoniosComSecaoDAO(idSecao, motivo);
        return true;
    }
    
    public boolean reativarSecaoComClientesDAO(int idSecao, String motivo){
        //reativa a secao juntamente com seus clientes(usuarios)
        //reativarSecaoDAO(idSecao);
        reativarClientesComSecaoDAO(idSecao);
        return true;
    }
    

}