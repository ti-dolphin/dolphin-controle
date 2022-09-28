/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.CheckListItem;
import model.Notificacao;
import model.apontamento.Apontamento;
import persistencia.ConexaoBanco;

/**
 *
 * @author ti
 */
public class NotificacaoDAO {

    public List<Notificacao> listarPorPessoa(int pessoaId) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt;

        try {
            String sql = "SELECT"
                    + " NOTIFICACOES.ID, NOTIFICACOES.DATAHORA, NOTIFICACOES.DESCRICAO,"
                    + " NOTIFICACOES.LIDO"
                    + " FROM NOTIFICACOES"
                    + " INNER JOIN PESSOA_GRUPOS ON NOTIFICACOES.GRUPO_ID = PESSOA_GRUPOS.GRUPO_ID"
                    + " WHERE PESSOA_GRUPOS.PESSOA_ID = ?";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, pessoaId);

            List<Notificacao> notificacoes = new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Notificacao notificacao = new Notificacao();

                notificacao.setId(rs.getInt("NOTIFICACOES.ID"));
                notificacao.setDataHora(rs.getTimestamp("NOTIFICACOES.DATAHORA").toLocalDateTime());
                notificacao.setDescricao(rs.getString("NOTIFICACOES.DESCRICAO"));
                notificacao.setLido(rs.getBoolean("NOTIFICACOES.LIDO"));
                notificacao.setDataHora(rs.getTimestamp("NOTIFICACOES.DATAHORA").toLocalDateTime());

                notificacoes.add(notificacao);

            }

            return notificacoes;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar notificações! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public List<Notificacao> listarNaoLidasPorPessoa(int pessoaId) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt;

        try {
            String sql = "SELECT"
                    + " NOTIFICACOES.ID, NOTIFICACOES.DATAHORA, NOTIFICACOES.DESCRICAO,"
                    + " NOTIFICACOES.LIDO, NOTIFICACOES.DATAHORA"
                    + " FROM NOTIFICACOES"
                    + " INNER JOIN PESSOA_GRUPOS ON NOTIFICACOES.GRUPO_ID = PESSOA_GRUPOS.GRUPO_ID"
                    + " WHERE PESSOA_GRUPOS.PESSOA_ID = ? AND NOTIFICACOES.LIDO = FALSE";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, pessoaId);

            List<Notificacao> notificacoes = new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Notificacao notificacao = new Notificacao();

                notificacao.setId(rs.getInt("NOTIFICACOES.ID"));
                notificacao.setDataHora(rs.getTimestamp("NOTIFICACOES.DATAHORA").toLocalDateTime());
                notificacao.setDescricao(rs.getString("NOTIFICACOES.DESCRICAO"));
                notificacao.setLido(rs.getBoolean("NOTIFICACOES.LIDO"));
                notificacao.setDataHora(rs.getTimestamp("NOTIFICACOES.DATAHORA").toLocalDateTime());

                notificacoes.add(notificacao);

            }

            return notificacoes;

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            con.close();
        }
    }

    public void editar(Notificacao notificacao) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        try {
            PreparedStatement preparedStatement;

            String sql = "UPDATE NOTIFICACOES SET LIDO = ? WHERE ID = ?";

            preparedStatement = con.prepareStatement(sql);

            preparedStatement.setBoolean(1, notificacao.isLido());
            preparedStatement.setInt(2, notificacao.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Erro ao marcar como lido! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public int listarTotalNotificoesPorPessoa(int pessoaId) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt;

        try {
            String sql = "SELECT COUNT(*) AS TOTAL"
                    + " FROM NOTIFICACOES"
                    + " INNER JOIN GRUPOS ON GRUPO_ID = GRUPOS.ID"
                    + " INNER JOIN PESSOA_GRUPOS ON PESSOA_GRUPOS.GRUPO_ID = GRUPOS.ID"
                    + " WHERE PESSOA_ID = ? AND LIDO = FALSE";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, pessoaId);

            ResultSet rs = pstmt.executeQuery();
            int total = 0;
            if (rs.next()) {
                total = rs.getInt("TOTAL");
            }

            return total;

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            con.close();
        }
    }

    public void inserir(Notificacao notificacao) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt;

        try {
            String sql = "INSERT INTO NOTIFICACOES(ID, DESCRICAO, GRUPO_ID)"
                    + " VALUE (NULL, ?, ?)";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, notificacao.getDescricao());
            pstmt.setInt(2, 1);

            pstmt.execute();

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        } finally {
            con.close();
        }
    }

}
