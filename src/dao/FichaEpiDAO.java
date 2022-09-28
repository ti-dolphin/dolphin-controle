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
import model.FichaEpi;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class FichaEpiDAO {
    
    public int cadastrarFicha() throws SQLException {
        
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt;
        Statement stat = con.createStatement();
        
        try {
            String sql = "INSERT INTO FICHAEPI (CODFICHAEPI, ARQUIVO) VALUES (NULL, NULL)";
            
            pstmt = con.prepareStatement(sql);
            pstmt.execute();
            
            String sqlBusca = "SELECT MAX(CODFICHAEPI) FROM FICHAEPI";
            
            ResultSet rs = stat.executeQuery(sqlBusca);

            int codFichaEpi = 0;
            if (rs != null && rs.next()) {
                codFichaEpi = rs.getInt("MAX(CODFICHAEPI)");
            }
            
            return codFichaEpi;
            
        } catch (SQLException se) {
            throw new SQLException("Erro ao cadastrar ficha de epi! " + se.getMessage());
        } finally {
            con.close();
        }//finally
    }//cadastrarFicha
}
