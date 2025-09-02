package Dao;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import conexao.ConnConexao;
import modelo.Impressora;

public class DAOImpressora {
    
    ConnConexao conexao = new ConnConexao();       
    
    public ArrayList<Impressora> listaDeImpressorasContratoFinalizado(int pCodEmpresa) 
    {
        ArrayList<Impressora> listaImpressoras = new ArrayList<>();
        
        conexao.conectar();
        String sqlPesquisa = "SELECT empresaid, modeloid, serie, chapa FROM tblpatrimonios WHERE tipoid = 3 AND contrato = 'S' AND status = 'ATIVO' AND empresaid = ?";

        try {
            PreparedStatement stmt = conexao.getConnection().prepareStatement(sqlPesquisa);
            stmt.setInt(1, pCodEmpresa);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Impressora impressora = new Impressora(
                    rs.getInt("empresaid"),
                    rs.getInt("modeloid"),
                    rs.getString("serie"),
                    rs.getString("chapa")
                );
                listaImpressoras.add(impressora);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar impressoras: \n" + e.getMessage());
        } finally {
            conexao.desconectar();
        }

        return listaImpressoras;
    }
}
