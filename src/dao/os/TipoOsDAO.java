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
import model.os.Pessoa;
import model.os.TipoOs;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class TipoOsDAO {

    public int inserir(TipoOs t) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "insert into TIPOOS(CODTIPOOS, DESCRICAO) values (null, ?)";

            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, t.getDescricao());

            pstmt.execute();
            
            int lastId = 0;
            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                lastId = rs.getInt(1);
            }

            return lastId;
        } catch (SQLException se) {
            throw new SQLException("Erro ao cadastrar tipo de ordem de serviço! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha cadastrar

    public ArrayList<TipoOs> buscarTipoOs() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "select * from TIPOOS where CODTIPOOS > 1 ";
            ResultSet rs = stat.executeQuery(sql);

            ArrayList<TipoOs> ta = new ArrayList<>();

            while (rs.next()) {
                TipoOs t = new TipoOs();

                t.setCodTipoOs(rs.getInt("CODTIPOOS"));
                t.setDescricao(rs.getString("DESCRICAO"));

                ta.add(t);
            }

            return ta;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar Tipo de OS! " + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }//fecha finally
    }//fecha buscarTipoOS

    public ArrayList<TipoOs> buscarTiposCombo(Pessoa pessoa) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "SELECT PESSOA_TIPO.CODTIPOOS, TIPOOS.DESCRICAO, PESSOA_TIPO.CODPESSOA FROM TIPOOS\n"
                    + "	INNER JOIN PESSOA_TIPO ON TIPOOS.CODTIPOOS = PESSOA_TIPO.CODTIPOOS\n"
                    + "	WHERE PESSOA_TIPO.CODPESSOA = " + pessoa.getCodPessoa();
            ResultSet rs = stat.executeQuery(sql);

            ArrayList<TipoOs> ta = new ArrayList<>();

            while (rs.next()) {
                TipoOs t = new TipoOs();

                t.setCodTipoOs(rs.getInt("CODTIPOOS"));
                t.setDescricao(rs.getString("DESCRICAO"));

                ta.add(t);
            }

            return ta;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar Tipo de OS (comboBox)! " + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }//fecha finally
    }//fecha buscarTipoCombo

    public ArrayList<TipoOs> filtrar(String query) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "select * from TIPOOS " + query;
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<TipoOs> tipos = new ArrayList<>();

            while (rs.next()) {
                TipoOs tipo = new TipoOs();

                tipo.setCodTipoOs(rs.getInt("CODTIPOOS"));
                tipo.setDescricao(rs.getString("DESCRICAO"));
                tipos.add(tipo);
            }//fecha while

            return tipos;

        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar tipo de OS! " + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }//fecha finally
    }//fecha filtrar

    public void alterar(TipoOs t) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql;
            sql = "update TIPOOS set"
                    + " DESCRICAO = ? where CODTIPOOS = ?";

            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, t.getDescricao());
            pstmt.setInt(2, t.getCodTipoOs());

            pstmt.executeUpdate();

        } catch (SQLException se) {
            throw new SQLException("Erro ao editar tipo da OS! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha alterar

    public void excluir(TipoOs tipo) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql;
            sql = "delete from TIPOOS where CODTIPOOS = " + tipo.getCodTipoOs();

            stat.execute(sql);
        } catch (SQLException se) {
            if (se.getErrorCode() == 1451) {
                throw new SQLException("Não é possível excluir tipo vinculado à OS/Tarefa!");
            }
            throw new SQLException("Erro ao excluir tipo de OS/Tarefa! " + se.getMessage());
        } finally {
            stat.close();
            con.close();
        }//finally
    }//fecha excluir
}
