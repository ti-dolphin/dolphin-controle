/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.apontamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class RelComentariosApontDAO {

    public void inserir(int codApont, String usuario) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "insert into RELCOMENTARIOSAPONT value(?, ?)";
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, codApont);
            pstmt.setString(2, usuario);

            pstmt.execute();
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir dados na tabela de relatórios! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//finally
    }//inserir

    public void deletar() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        try {
            String sql = "delete from RELCOMENTARIOSAPONT";

            pstmt = con.prepareStatement(sql);

            pstmt.execute(sql);
        } catch (SQLException e) {
            throw new SQLException("Erro ao deletar registros da tabela RELCOMENTARIOSAPONT" + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//finally
    }//deletar
}
