/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import biblioteca.Biblioteca;
import conexao.ConnConexao;
import static biblioteca.VariaveisPublicas.sql;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Tipo;

/**
 *
 * @author d631863
 */
public class DAOTipo {

   ConnConexao conexao   = new ConnConexao();
   Biblioteca umaBiblio  = new Biblioteca();

    public boolean salvarTipoDAO(Tipo pTipo) {
        conexao.conectar();
        try {
            String sql = "INSERT INTO tblTipos (tipo,tipopatrimonio,temip,status) VALUES (?,?,?,?)";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pTipo.getTipo());
            pst.setString(2, pTipo.getTipopatrimonio());
            pst.setString(3, pTipo.getTemIp());
            pst.setString(4, pTipo.getStatus());            
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível executar o comando sql, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }

    public boolean atualizarTipoDAO(Tipo pTipo) {
        conexao.conectar();
        try {
            String sql = "UPDATE tblTipos SET tipo=?, tipopatrimonio=?, temip=?, status=? WHERE codigo=?";
            PreparedStatement pst = conexao.getConnection().prepareStatement(sql);
            pst.setString(1, pTipo.getTipo());
            pst.setString(2, pTipo.getTipopatrimonio());
            pst.setString(3, pTipo.getTemIp());
            pst.setString(4, pTipo.getStatus());   
            pst.setInt(5, pTipo.getCodigo());           
            pst.executeUpdate();
            pst.close();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível atualizar o registro, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }

    }

    public Tipo pesquisarTipoDAO(Tipo pTipo) {
        try {
            conexao.conectar();
            sql = "SELECT * FROM tblTipos WHERE codigo = '" + pTipo.getCodigo() + "'";
            conexao.ExecutarPesquisaSQL(sql);
            while (conexao.rs.next()) {
                pTipo.setCodigo(conexao.rs.getInt("codigo"));
                pTipo.setTipo(conexao.rs.getString("tipo"));
                pTipo.setTipopatrimonio(conexao.rs.getString("tipopatrimonio"));
                pTipo.setTemIp(conexao.rs.getString("temip"));
                pTipo.setStatus(conexao.rs.getString("status"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível executar o comando sql, \n" + e + ", o sql passado foi \n" + sql);
        } finally {
            conexao.desconectar();
        }
        return pTipo;
    }

    public boolean excluirTipoDAO(int pCodigo) {
        try {
            conexao.conectar();
            sql = "DELETE FROM tblTipos WHERE codigo = '" + pCodigo + "'";
            conexao.ExecutarPesquisaSQL(sql);
            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível excluir o registro, \n" + e + ", o sql passado foi \n" + sql);
            return false;
        } finally {
            conexao.desconectar();
        }
    }

    public boolean RegistroDuplicado(Tipo pTipo) {
        conexao.conectar();
        try {
            sql = "SELECT * FROM tblTipos WHERE tipo = '" + pTipo.getTipo() + "'";
            conexao.ExecutarPesquisaSQL(sql);
            if (conexao.rs.next()) {
                //tipo de msg = 1 é a de informacao msg = 2 é a de exclamação  msg = 3 é a de interrogação
                JOptionPane.showMessageDialog(null, "Atenção o tipo digitado já esta cadastrado, verifique!", "Operação não concluída por duplicidade no tipo!", 2);
                return true;

            } else {
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao executar a pesquisa sobre duplicidade! " + ex);
            return false;
        } finally {
            conexao.desconectar();
        }

    }

    public List<Tipo> gerarListaTiposSQL(String sql) {
        //gera uma lista de tipos passando sql

        conexao.conectar();
        conexao.ExecutarPesquisaSQL(sql);

        ArrayList<Tipo> lstTipos = new ArrayList<>();
        try {

            while (conexao.rs.next()) {
                Integer vCodigo = conexao.rs.getInt("codigo");
                String vTipo    = conexao.rs.getString("tipo");
                String vTPatr   = conexao.rs.getString("tipopatrimonio");
                String vTIp     = conexao.rs.getString("temip");
                String vStatus  = conexao.rs.getString("status");

                Tipo objTipos = new Tipo();
                objTipos.setCodigo(vCodigo);
                objTipos.setTipo(vTipo);                
                objTipos.setTemIp(vTIp);
                objTipos.setTipopatrimonio(vTPatr);
                objTipos.setStatus(vStatus);

                lstTipos.add(objTipos);
            }
            conexao.desconectar();
        } catch (NumberFormatException | SQLException erro) {
            String erroMsg = "Erro ao recuperar objetos : " + erro.getMessage();
            JOptionPane.showMessageDialog(null, erroMsg, "Mensagem", JOptionPane.ERROR_MESSAGE);
        }

        return lstTipos;
    }

}
