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
import java.util.ArrayList;
import model.apontamento.StatusApont;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class StatusApontamentoDAO {
    public ArrayList<StatusApont> filtrarStatusApontamento(String query) throws SQLException{
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            String sql = "select * from STATUSAPONT " + query;
            
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<StatusApont> sa = new ArrayList<>();
            
            while (rs.next()) {
                StatusApont s = new StatusApont();
                
                s.setCodStatusApont(rs.getString("CODSTATUSAPONT"));
                s.setDescricao(rs.getString("DESCRICAO"));
                
                sa.add(s);
            }
            
            return sa;
            
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar status do apontamento! "+se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }
    }//buscarCombo
}
