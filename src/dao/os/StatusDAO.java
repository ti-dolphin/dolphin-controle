/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.os;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.os.Status;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class StatusDAO {

    public int inserir(Status status) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "insert into STATUS (CODSTATUS, NOME, ACAO) values(NULL, ?, ?);";

            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, status.getNome());
            pstmt.setInt(2, status.getAcao());

            pstmt.execute();

            int lastId = 0;
            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                lastId = rs.getInt(1);
            }

            return lastId;
        } catch (SQLException se) {
            throw new SQLException("Erro ao cadastrar status! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha cadastrar

    public ArrayList<Status> buscar() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "select * from STATUS where CODSTATUS ";

            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<Status> sa = new ArrayList<>();
            while (rs.next()) {
                Status s = new Status();

                s.setCodStatus(rs.getInt("CODSTATUS"));
                s.setNome(rs.getString("NOME"));
                s.setAcao(rs.getInt("ACAO"));

                sa.add(s);
            }

            return sa;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar status! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha buscar

    public ArrayList<Status> buscarCombo() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "select * from STATUS where ATIVO = TRUE";

            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<Status> sa = new ArrayList<>();
            while (rs.next()) {
                Status s = new Status();

                s.setCodStatus(rs.getInt("CODSTATUS"));
                s.setNome(rs.getString("NOME"));
                s.setAcao(rs.getInt("ACAO"));
                s.setAtivo(rs.getBoolean("ATIVO"));

                sa.add(s);
            }

            return sa;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar status (comboBox)! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha buscar

    public ArrayList<Status> buscarComboCadOs() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "select * from STATUS where CODSTATUS > 1 and ATIVO = TRUE";

            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<Status> sa = new ArrayList<>();
            while (rs.next()) {
                Status s = new Status();

                s.setCodStatus(rs.getInt("CODSTATUS"));
                s.setNome(rs.getString("NOME"));
                s.setAcao(rs.getInt("ACAO"));
                s.setAtivo(rs.getBoolean("ATIVO"));

                sa.add(s);
            }

            return sa;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar status (comboBox do cadastro de os)! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha buscar

    public ArrayList<Status> filtrar(String query) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "select * from STATUS " + query;

            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Status> listaStatus = new ArrayList<>();

            while (rs.next()) {
                Status status = new Status();

                status.setCodStatus(rs.getInt("CODSTATUS"));
                status.setNome(rs.getString("NOME"));
                status.setAcao(rs.getInt("ACAO"));
                status.setAtivo(rs.getBoolean("ATIVO"));
                listaStatus.add(status);
            }//fecha while

            return listaStatus;

        } catch (SQLException se) {
            throw new SQLException("Erro ao filtrar status! " + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }//fecha finally
    }//fecha filtrar

    public void alterar(Status s) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql;
            sql = "update STATUS set"
                    + " NOME = ?, ACAO = ?, ATIVO = ? where CODSTATUS = ?";

            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, s.getNome());
            pstmt.setInt(2, s.getAcao());
            pstmt.setBoolean(3, s.isAtivo());
            pstmt.setInt(4, s.getCodStatus());

            pstmt.executeUpdate();

        } catch (SQLException se) {
            throw new SQLException("Erro ao editar status da OS! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha alterar

    public void excluir(int codStatus) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql;
            sql = "delete from STATUS where CODSTATUS = " + codStatus;

            stat.execute(sql);
        } catch (SQLException se) {
            if (se.getErrorCode() == 1451) {
                throw new SQLException("Erro ao excluir status! Não é possível excluir status vinculado a outra tabela");
            }
            throw new SQLException("Erro ao excluir status! " + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }//finally
    }//fecha excluir
}
