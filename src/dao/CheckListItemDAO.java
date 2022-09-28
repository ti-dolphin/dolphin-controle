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
import model.CheckListItem;
import model.MovimentoItem;
import persistencia.ConexaoBanco;
import view.Menu;

/**
 *
 * @author guilherme.oliveira
 */
public class CheckListItemDAO {

    public int inserir(CheckListItem item) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt;

        try {
            String sql = "insert into CHECKLIST_ITEM_MODELO (NOME, ID_CHECKLIST_MODELO, RECCREATEDBY) value(?, ?, ?)";

            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, item.getNome());
            pstmt.setInt(2, item.getCheckListModelo().getId());
            pstmt.setString(3, Menu.logado.getLogin());

            pstmt.execute();

            int id = 0;
            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            return id;

        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir item do checklist! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public void alterar(CheckListItem item) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt;

        try {

            String sql = "update CHECKLIST_ITEM_MODELO set NOME = ?,"
                    + " RECMODIFIEDBY = ?, RECMODIFIEDON = now() where ID = ?";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, item.getNome());
            pstmt.setString(2, Menu.logado.getLogin());
            pstmt.setInt(3, item.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Erro ao alterar item do checklist! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public ArrayList<CheckListItem> buscarItensDoCheckList(int idCheckList) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        Statement stmt = con.createStatement();

        try {
            String sql = "select * from CHECKLIST_ITEM_MODELO where ID_CHECKLIST_MODELO = " + idCheckList;

            ArrayList<CheckListItem> itens = new ArrayList<>();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                CheckListItem item = new CheckListItem();

                item.setId(rs.getInt("ID"));
                item.setNome(rs.getString("NOME"));

                itens.add(item);

            }

            return itens;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar itens do checklist! " + e.getMessage());
        } finally {
            con.close();
            stmt.close();
        }
    }

    public ArrayList<CheckListItem> buscarItensDoCheckListDoItemDoMovimento(MovimentoItem item) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        Statement stmt = con.createStatement();

        try {

            String sql = "select CHECKLIST_ITEM_MODELO.ID from CHECKLIST_ITEM_MODELO \n"
                    + "inner join PATRIMONIOS\n"
                    + " on CHECKLIST_ITEM_MODELO.ID_CHECKLIST_MODELO = PATRIMONIOS.ID_CHECKLIST_MODELO\n"
                    + " where PATRIMONIOS.ID = " + item.getPatrimonio().getId() 
                    + " and PATRIMONIOS.CODCOLIGADA = " + item.getPatrimonio().getCodColigada();

            ArrayList<CheckListItem> itens = new ArrayList<>();
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
                CheckListItem check = new CheckListItem();
                
                check.setId(rs.getInt("CHECKLIST_ITEM_MODELO.ID"));
                
                itens.add(check);
            }
            
            return itens;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar checklist modelo do item do movimento");
        } finally {
            con.close();
            stmt.close();
        }
    }

    public void excluir(int id) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql;
            sql = "delete from CHECKLIST_ITEM_MODELO where ID = " + id;

            stat.execute(sql);
        } catch (SQLException se) {
            switch (se.getErrorCode()) {
                case 1451:
                    throw new SQLException("Não é possível excluir item em uso!");
                default:
                    throw new SQLException(se.getMessage());
            }
        } finally {
            con.close();
            stat.close();
        }
    }

}
