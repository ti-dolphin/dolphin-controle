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
import model.MovimentoItemCheckList;
import persistencia.ConexaoBanco;
import view.Menu;

/**
 *
 * @author guilherme.oliveira
 */
public class MovimentoItemCheckListDAO {
    public void inserirProduto(MovimentoItem movimentoItem, CheckListModelo checkListModelo) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt;

        try {

            String sql = "insert into MOVIMENTO_ITEM_CHECKLIST (ID_MOVIMENTO_ITEM,"
                    + " ID_CHECKLIST_MODELO, ID_CHECKLIST_ITEM_MODELO, RECCREATEDBY) \n"
                    
                    + "	select ?, CHECKLIST_MODELO.ID, CHECKLIST_ITEM_MODELO.ID, ?\n"
                    + "	from CHECKLIST_MODELO \n"
                    
                    + " inner join CHECKLIST_ITEM_MODELO\n"
                    + " on CHECKLIST_MODELO.ID = CHECKLIST_ITEM_MODELO.ID_CHECKLIST_MODELO\n"
                    
                    + " where CHECKLIST_MODELO.ID = ?";

            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, movimentoItem.getId());
            pstmt.setString(2, Menu.logado.getLogin());
            pstmt.setInt(3, checkListModelo.getId());
            pstmt.execute();

        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir checklist dos itens do movimento! " + e.getMessage());
        } finally {
            con.close();
        }
    }
    
    public void inserirPatrimonio(MovimentoItem movimentoItem) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt;

        try {

            String sql = "insert into MOVIMENTO_ITEM_CHECKLIST (ID_MOVIMENTO_ITEM, ID_CHECKLIST_MODELO, ID_CHECKLIST_ITEM_MODELO, RECCREATEDBY) \n"
                    + "	select ?, PATRIMONIOS.ID_CHECKLIST_MODELO, CHECKLIST_ITEM_MODELO.ID, ?\n"
                    + "		from CHECKLIST_MODELO \n"
                    + "        inner join PATRIMONIOS\n"
                    + "        on CHECKLIST_MODELO.ID = PATRIMONIOS.ID_CHECKLIST_MODELO\n"
                    + "        inner join CHECKLIST_ITEM_MODELO\n"
                    + "        on CHECKLIST_MODELO.ID = CHECKLIST_ITEM_MODELO.ID_CHECKLIST_MODELO\n"
                    + "        where PATRIMONIOS.ID = ? and PATRIMONIOS.CODCOLIGADA = ?";

            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, movimentoItem.getId());
            pstmt.setString(2, Menu.logado.getLogin());
            pstmt.setInt(3, movimentoItem.getPatrimonio().getId());
            pstmt.setInt(4, movimentoItem.getPatrimonio().getCodColigada());
            pstmt.execute();

        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir checklist dos itens do movimento! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public ArrayList<MovimentoItemCheckList> buscar(MovimentoItem item) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        Statement stmt = con.createStatement();

        try {

            String sql = "select CHECKLIST_ITEM_MODELO.NOME,"
                    + " MOVIMENTO_ITEM_CHECKLIST.ID_CHECKLIST_MODELO,"
                    + " MOVIMENTO_ITEM_CHECKLIST.ID_MOVIMENTO_ITEM,"
                    + " MOVIMENTO_ITEM_CHECKLIST.ID_CHECKLIST_ITEM_MODELO,"
                    + " MOVIMENTO_ITEM_CHECKLIST.CHECADO,"
                    + " MOVIMENTO_ITEM_CHECKLIST.PROBLEMA"
                    + " from MOVIMENTO_ITEM_CHECKLIST"
                    + " inner join CHECKLIST_ITEM_MODELO"
                    + " on MOVIMENTO_ITEM_CHECKLIST.ID_CHECKLIST_MODELO = CHECKLIST_ITEM_MODELO.ID_CHECKLIST_MODELO\n"
                    + " and MOVIMENTO_ITEM_CHECKLIST.ID_CHECKLIST_ITEM_MODELO = CHECKLIST_ITEM_MODELO.ID"
                    + " where MOVIMENTO_ITEM_CHECKLIST.ID_MOVIMENTO_ITEM = " + item.getId();

            ArrayList<MovimentoItemCheckList> itens = new ArrayList<>();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                CheckListModelo checkList = new CheckListModelo();
                MovimentoItemCheckList movimentoItemCheckList = new MovimentoItemCheckList();
                MovimentoItem movimentoItem = new MovimentoItem();

                CheckListItem checkListItem = new CheckListItem();

                checkList.setId(rs.getInt("MOVIMENTO_ITEM_CHECKLIST.ID_CHECKLIST_MODELO"));
                movimentoItem.setId(rs.getInt("MOVIMENTO_ITEM_CHECKLIST.ID_MOVIMENTO_ITEM"));
                checkListItem.setId(rs.getInt("MOVIMENTO_ITEM_CHECKLIST.ID_CHECKLIST_ITEM_MODELO"));
                checkListItem.setNome(rs.getString("CHECKLIST_ITEM_MODELO.NOME"));

                movimentoItemCheckList.setCheckListModelo(checkList);
                movimentoItemCheckList.setMovimentoItem(movimentoItem);
                movimentoItemCheckList.setCheckListItem(checkListItem);

                movimentoItemCheckList.setChecado(rs.getBoolean("MOVIMENTO_ITEM_CHECKLIST.CHECADO"));
                movimentoItemCheckList.setProblema(rs.getString("MOVIMENTO_ITEM_CHECKLIST.PROBLEMA"));

                itens.add(movimentoItemCheckList);
            }
            return itens;
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar checklist do movimento! " + e.getMessage());
        } finally {
            con.close();
            stmt.close();
        }
    }

    public boolean existeItem(MovimentoItem movimentoItem) throws SQLException {
        
        Connection con = ConexaoBanco.getConexao();
        Statement stmt = con.createStatement();
        
        try {
            String sql = "select ID_MOVIMENTO_ITEM from MOVIMENTO_ITEM_CHECKLIST where ID_MOVIMENTO_ITEM = " + movimentoItem.getId() + " LIMIT 1";
            ResultSet rs = stmt.executeQuery(sql);
            
            return rs.next();
        } catch (SQLException e) {
            throw new SQLException("Erro ao verificar se já existe item! " + e.getMessage());
        } finally {
            con.close();
            stmt.close();
        }
    }
    
    public void alterar(Connection con, MovimentoItemCheckList itemCheckList) throws SQLException {

        PreparedStatement pstmt;

        try {

            String sql = "update MOVIMENTO_ITEM_CHECKLIST"
                    + " set CHECADO = ?, PROBLEMA = ?, RECMODIFIEDBY = ?, RECMODIFIEDON = NOW()"
                    + " where ID_MOVIMENTO_ITEM = ?"
                    + " and ID_CHECKLIST_MODELO = ?"
                    + " and ID_CHECKLIST_ITEM_MODELO = ?";

            pstmt = con.prepareStatement(sql);

            pstmt.setBoolean(1, itemCheckList.isChecado());
            pstmt.setString(2, itemCheckList.getProblema());
            pstmt.setString(3, Menu.logado.getLogin());

            pstmt.setInt(4, itemCheckList.getMovimentoItem().getId());
            pstmt.setInt(5, itemCheckList.getCheckListModelo().getId());
            pstmt.setInt(6, itemCheckList.getCheckListItem().getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Erro ao alterar dados do checklist! " + e.getMessage());
        }
    }

}
