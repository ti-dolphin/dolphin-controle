/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Sistema;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class SistemaDAO {

    public String buscarVersao() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "SELECT MAX(VERSAO) FROM SISTEMA";
            stat = con.prepareStatement(sql);
            ResultSet rs = stat.executeQuery(sql);

            String versao = "0.1";
            while (rs.next()) {
                versao = rs.getString("MAX(VERSAO)");
            }

            return versao;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar versão" + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }//finally
    }//buscar

    public Sistema buscar() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "SELECT * FROM SISTEMA WHERE CODSISTEMA = 1";
            stat = con.prepareStatement(sql);
            ResultSet rs = stat.executeQuery(sql);

            Sistema sistema = new Sistema();
            
            while (rs.next()) {

                sistema.setCodSistema(rs.getInt("CODSISTEMA"));
                sistema.setVersao(rs.getString("VERSAO"));
                sistema.setDataSincronizacao(rs.getTimestamp("DATASINCRONIZACAO").toLocalDateTime());
            }
            return sistema;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar dados do sistema! " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }//finally
    }//buscar
}
