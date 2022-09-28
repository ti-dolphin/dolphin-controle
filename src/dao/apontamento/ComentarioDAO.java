/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.apontamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.apontamento.Apontamento;
import model.apontamento.Comentario;
import model.os.OrdemServico;
import persistencia.ConexaoBanco;
import view.Menu;

/**
 *
 * @author guilherme.oliveira
 */
public class ComentarioDAO {

    public int salvar(Comentario comentario) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "INSERT INTO COMENTARIOS (CODCOMENTARIO, CODAPONT,"
                    + " CODOS, DESCRICAO, RECCREATEDBY)"
                    + " VALUES (NULL, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            if (comentario.getApontamento() != null) {
                pstmt.setInt(1, comentario.getApontamento().getCodApont());
            } else {
                pstmt.setInt(1, 0);
            }
            if (comentario.getOrdemServico() != null) {
                pstmt.setInt(2, comentario.getOrdemServico().getCodOs());
            } else {
                pstmt.setInt(2, 0);
            }
            pstmt.setString(3, comentario.getDescricao());
            pstmt.setString(4, comentario.getCreatedBy());

            pstmt.execute();
            
            int id = 0;
            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            return id;
        } catch (SQLException se) {
            throw new SQLException("Erro ao cadastrar comentario! " + se.getMessage());
        }
    }//salvar

    public ArrayList<Comentario> buscarComentApont(int codApont) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "SELECT CODCOMENTARIO, CODAPONT, DESCRICAO,"
                    + " RECCREATEDBY, RECCREATEDON FROM COMENTARIOS"
                    + " WHERE CODAPONT = " + codApont;

            ResultSet rs = stat.executeQuery(sql);

            ArrayList<Comentario> ca = new ArrayList<>();

            while (rs.next()) {
                Comentario comentario = new Comentario();
                Apontamento apontamento = new Apontamento();

                comentario.setCodComentario(rs.getInt("CODCOMENTARIO"));
                apontamento.setCodApont(rs.getInt("CODAPONT"));
                comentario.setDescricao(rs.getString("DESCRICAO"));
                comentario.setCreatedBy(rs.getString("RECCREATEDBY"));
                comentario.setCreatedOn(rs.getTimestamp("RECCREATEDON").toLocalDateTime());

                comentario.setApontamento(apontamento);

                ca.add(comentario);
            }

            return ca;

        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar comentarios! " + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }//finally
    }//buscar

    public ArrayList<Comentario> buscarComentOS(int codOS) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "SELECT * FROM COMENTARIOS C INNER JOIN ORDEMSERVICO OS ON OS.CODOS = C.CODOS WHERE C.CODOS = " + codOS + " ORDER BY C.RECCREATEDON DESC";

            ResultSet rs = stat.executeQuery(sql);

            ArrayList<Comentario> ca = new ArrayList<>();

            while (rs.next()) {
                Comentario comentario = new Comentario();
                OrdemServico ordemServico = new OrdemServico();

                comentario.setCodComentario(rs.getInt("CODCOMENTARIO"));
                ordemServico.setCodOs(rs.getInt("CODOS"));
                ordemServico.setNome(rs.getString("NOME"));
                comentario.setDescricao(rs.getString("DESCRICAO"));
                comentario.setCreatedBy(rs.getString("RECCREATEDBY"));
                comentario.setCreatedOn(rs.getTimestamp("RECCREATEDON").toLocalDateTime());

                comentario.setOrdemServico(ordemServico);

                ca.add(comentario);
            }

            return ca;

        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar comentarios! " + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }//finally
    }//buscar

    public void deletar(int codComentario) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "delete from COMENTARIOS where CODCOMENTARIO = "
                    + codComentario;

            stat.execute(sql);
        } catch (SQLException se) {
            throw new SQLException("Erro ao excluir comentário! " + se.getMessage());
        } finally {
            stat.close();
            con.close();
        }//finally
    }//deletar

    public ArrayList<Comentario> buscarComentariosNaoEnviadosPorEmail(int codOs) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "SELECT * FROM COMENTARIOS WHERE CODOS = " + codOs + " AND EMAIL = FALSE AND RECCREATEDBY LIKE '" + Menu.getUiLogin().getPessoa().getLogin() + "' ORDER BY RECCREATEDON DESC";

            ResultSet rs = stat.executeQuery(sql);

            ArrayList<Comentario> ca = new ArrayList<>();

            while (rs.next()) {
                Comentario comentario = new Comentario();
                OrdemServico ordemServico = new OrdemServico();

                comentario.setCodComentario(rs.getInt("CODCOMENTARIO"));
                ordemServico.setCodOs(rs.getInt("CODOS"));
                comentario.setDescricao(rs.getString("DESCRICAO"));
                comentario.setCreatedBy(rs.getString("RECCREATEDBY"));
                comentario.setCreatedOn(rs.getTimestamp("RECCREATEDON").toLocalDateTime());

                comentario.setOrdemServico(ordemServico);

                ca.add(comentario);
            }

            return ca;

        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar comentarios! " + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }//finally

    }//buscarComentariosNaoEnviadosPorEmail

    public void alterarEmailEnviado(int codOs) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "UPDATE COMENTARIOS SET"
                    + " EMAIL = TRUE WHERE CODOS = ?";
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, codOs);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao alterar email! " + e.getMessage());
        }

    }//alterarEmailEnviado

    public ArrayList<Comentario> buscarComentariosDaOs(int codOs) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "SELECT * FROM COMENTARIOS C"
                    + " INNER JOIN ORDEMSERVICO OS ON OS.CODOS = C.CODOS"
                    + " WHERE C.CODOS = " + codOs + " ORDER BY C.RECCREATEDON DESC";

            ResultSet rs = stat.executeQuery(sql);

            ArrayList<Comentario> ca = new ArrayList<>();

            while (rs.next()) {
                Comentario comentario = new Comentario();
                OrdemServico ordemServico = new OrdemServico();

                comentario.setCodComentario(rs.getInt("CODCOMENTARIO"));
                ordemServico.setCodOs(rs.getInt("CODOS"));
                ordemServico.setNome(rs.getString("NOME"));
                comentario.setDescricao(rs.getString("DESCRICAO"));
                comentario.setCreatedBy(rs.getString("RECCREATEDBY"));
                comentario.setCreatedOn(rs.getTimestamp("RECCREATEDON").toLocalDateTime());

                comentario.setOrdemServico(ordemServico);

                ca.add(comentario);
            }

            return ca;

        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar comentarios! " + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }//finally
    }//buscar
}

