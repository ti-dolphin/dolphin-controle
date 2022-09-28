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
import java.sql.Statement;
import java.util.ArrayList;
import model.CheckListModelo;
import persistencia.ConexaoBanco;
import view.Menu;

/**
 *
 * @author guilherme.oliveira
 */
public class CheckListModeloDAO {

    public int inserir(CheckListModelo checkList) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt;

        try {
            String sql = "insert into CHECKLIST_MODELO (NOME, RECCREATEDBY) value(?, ?)";

            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, checkList.getNome());
            pstmt.setString(2, Menu.logado.getLogin());

            pstmt.execute();

            int id = 0;
            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            return id;

        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir checklist! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public ArrayList<CheckListModelo> buscar() throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        Statement stmt = con.createStatement();

        try {
            String sql = "select * from CHECKLIST_MODELO";

            ArrayList<CheckListModelo> checks = new ArrayList<>();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                CheckListModelo checkList = new CheckListModelo();

                checkList.setId(rs.getInt("ID"));
                checkList.setNome(rs.getString("NOME"));

                checks.add(checkList);
            }

            return checks;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar checklist! " + e.getMessage());
        } finally {
            con.close();
            stmt.close();
        }
    }

    public void alterar(CheckListModelo checkList) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt;

        try {
            String sql = "update CHECKLIST_MODELO set NOME = ?,"
                    + " RECMODIFIEDBY = ?, RECMODIFIEDON = now() where ID = ?";

            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, checkList.getNome());
            pstmt.setString(2, Menu.logado.getLogin());
            pstmt.setInt(3, checkList.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Erro ao alterar checklist! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public void excluir(int id) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql;
            sql = "delete from CHECKLIST_MODELO where ID = " + id;

            stat.execute(sql);
        } catch (SQLException se) {
            switch (se.getErrorCode()) {
                case 1451:
                    throw new SQLException("Não é possível excluir checklist em uso!");
                default:
                    throw new SQLException(se.getMessage());
            }
        } finally {
            con.close();
            stat.close();
        }//finally
    }

}
