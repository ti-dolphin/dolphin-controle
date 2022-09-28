/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.os;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.os.Segmento;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class SegmentoDAO {

    public ArrayList<Segmento> buscar(String query) throws SQLException {
        
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        
        try {
            String sql = "SELECT * FROM SEGMENTO " + query;
            
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Segmento> segmentos = new ArrayList<>();            
            
            while (rs.next()) {
                Segmento segmento = new Segmento();
                
                segmento.setCodSegmento(rs.getInt("CODSEGMENTO"));
                segmento.setDescricao(rs.getString("DESCRICAO"));
                
                segmentos.add(segmento);
            }//while
            
            return segmentos;
            
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar segmentos! " + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }//finally
    }//buscar
    
    
}
