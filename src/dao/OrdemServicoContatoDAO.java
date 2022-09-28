/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exceptions.CampoEmBrancoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Contato;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilh
 */
public class OrdemServicoContatoDAO {

    public void inserir(int ordemServicoId, int contatoId) throws SQLException {
        Connection connection = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "insert into ORDEMSERVICO_CONTATOS (ORDEMSERVICO_ID, CONTATO_ID) values(?, ?)";

            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, ordemServicoId);
            pstmt.setInt(2, contatoId);

            pstmt.execute();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                throw new SQLException("Contato já adicionado");
            }
            throw new SQLException(ex.getMessage());
        } finally {
            connection.close();
        }
    }

    public ArrayList<Contato> buscarPorOrdemServicoId(int OrdemServicoId) throws SQLException, CampoEmBrancoException {
        Connection connection = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "select CONTATO_ID, ORDEMSERVICO_ID, CONTATOS.NOME, CONTATOS.EMAIL, CONTATOS.TELEFONE"
                    + " from ORDEMSERVICO_CONTATOS \n"
                    + " inner join CONTATOS on CONTATO_ID = CONTATOS.ID and ORDEMSERVICO_ID = ?;";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, OrdemServicoId);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Contato> contatos = new ArrayList<>();
            while (rs.next()) {
                Contato contato = new Contato();
                contato.setId(rs.getInt("CONTATO_ID"));
                contato.setNome(rs.getString("CONTATOS.NOME"));
                contato.setEmail(rs.getString("CONTATOS.EMAIL"));
                contato.setTelefone(rs.getString("CONTATOS.TELEFONE"));

                contatos.add(contato);
            }

            return contatos;

        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        } finally {
            connection.close();
        }
    }

    public void deletar(int ordemServicoId, int contatoId) throws SQLException {
        Connection connection = ConexaoBanco.getConexao();
        Statement stat = connection.createStatement();

        try {
            String sql = "delete from ORDEMSERVICO_CONTATOS where ORDEMSERVICO_ID = " + ordemServicoId + " and CONTATO_ID = " + contatoId;

            stat.execute(sql);
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        } finally {
            connection.close();
            stat.close();
        }
    }
}
