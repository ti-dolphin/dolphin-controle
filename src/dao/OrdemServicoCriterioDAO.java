/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Criterio;
import persistencia.ConexaoBanco;
import java.sql.Statement;

/**
 *
 * @author guilh
 */
public class OrdemServicoCriterioDAO {

    public int inserir(int idOrdemServico, int idCriterio) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "insert into ORDEMSERVICO_CRITERIOS (ID_ORDEMSERVICO, ID_CRITERIOS)"
                    + " values (?, ?)";

            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, idOrdemServico);
            pstmt.setInt(2, idCriterio);

            pstmt.execute();

            int id = 0;
            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            return id;

        } catch (SQLException se) {
            throw new SQLException("Erro ao inserir critérios da tarefa! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }
    }

    public void alterar(int idOrdemServico, int antigoIdCriterio, int novoIdCriterio) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "UPDATE ORDEMSERVICO_CRITERIOS\n"
                    + "SET ID_CRITERIOS = ? \n"
                    + "WHERE ID_ORDEMSERVICO = ? and ID_CRITERIOS = ?";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, novoIdCriterio);
            pstmt.setInt(2, idOrdemServico);
            pstmt.setInt(3, antigoIdCriterio);

            pstmt.executeUpdate();

        } catch (SQLException se) {
            throw new SQLException("Erro ao alterar critérios da tarefa! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }
    }

    public ArrayList<Criterio> buscar(int idOrdemServico) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "select CRITERIOS.ID, CRITERIOS.DESCRICAO, CRITERIOS.PESO, CRITERIOS.ORDEM \n"
                    + " from ORDEMSERVICO_CRITERIOS \n"
                    + "inner join CRITERIOS \n"
                    + "on ORDEMSERVICO_CRITERIOS.ID_CRITERIOS = CRITERIOS.ID \n"
                    + "where ORDEMSERVICO_CRITERIOS.ID_ORDEMSERVICO = " + idOrdemServico;

            ResultSet rs = stat.executeQuery(sql);

            ArrayList<Criterio> criterios = new ArrayList<>();

            while (rs.next()) {
                Criterio criterio = new Criterio();
                criterio.setId(rs.getInt("CRITERIOS.ID"));
                criterio.setDescricao(rs.getString("CRITERIOS.DESCRICAO"));
                criterio.setPeso(rs.getInt("CRITERIOS.PESO"));
                criterio.setOrdem(rs.getInt("CRITERIOS.ORDEM"));

                criterios.add(criterio);
            }

            return criterios;

        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar criterios da tarefa! " + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }
    }
}
