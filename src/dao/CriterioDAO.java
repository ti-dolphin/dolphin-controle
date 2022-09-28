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
import java.util.ArrayList;
import model.Criterio;
import model.os.OrdemServico;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilh
 */
public class CriterioDAO {
    
    public ArrayList<Criterio> buscar(int ordem) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        
        try {
            String sql = "select * from CRITERIOS where ORDEM = " + ordem;
            
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Criterio> criterios = new ArrayList<>();

            while(rs.next()) {
                Criterio criterio = new Criterio();
                
                criterio.setId(rs.getInt("ID"));
                criterio.setDescricao(rs.getString("DESCRICAO"));
                criterio.setPeso(rs.getInt("PESO"));
                criterio.setOrdem(rs.getInt("ORDEM"));
                
                criterios.add(criterio);
            }
            
            return criterios;
            
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar criterios! " + se.getMessage());
        }
    }
    
}
