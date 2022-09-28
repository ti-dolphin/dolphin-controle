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
import model.Local;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class LocalDAO {

    public Local buscarLocal() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt;
        Local local = null;
        try {
            String sql = "SELECT * FROM LOCAL";
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);

            while(rs.next()) {
                local = new Local();
                local.setId(rs.getInt("ID"));
                local.setNome_maquina(rs.getString("NOME_MAQUINA"));
                local.setData(rs.getTimestamp("DATA").toLocalDateTime());
                local.setLatitude(rs.getDouble("LAT"));
                local.setLongitude(rs.getDouble("LNG"));
            }
            
            return local;
            
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar Local! " + se.getMessage());
        }
    }
}
