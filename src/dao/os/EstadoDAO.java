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
import model.os.Estado;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class EstadoDAO {
    
    public ArrayList<Estado> buscar() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        
        try {
            String sql = "SELECT * FROM ESTADO ORDER BY ID";
            
            ResultSet rs = stat.executeQuery(sql);
            
            ArrayList<Estado> estados = new ArrayList<>();
            
            while(rs.next()) {
                Estado estado = new Estado();
                
                estado.setId(rs.getInt("ID"));
                estado.setNome(rs.getString("NOME"));
                estado.setUf(rs.getString("UF"));
                
                estados.add(estado);
            }
            
            return estados;
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar estados");
        } finally {
            con.close();
            stat.close();
        }//finally
    }//buscar
}
