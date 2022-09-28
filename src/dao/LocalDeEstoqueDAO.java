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
import model.LocalDeEstoque;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class LocalDeEstoqueDAO {
    
    public ArrayList<LocalDeEstoque> buscar() throws SQLException {
        
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        
        try {
            String sql = "select * from LOCALDEESTOQUE where INATIVO = false";
            
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<LocalDeEstoque> estoques = new ArrayList<>();
            
            while(rs.next()) {
                
                LocalDeEstoque estoque = new LocalDeEstoque();
                
                estoque.setCodColigada(rs.getShort("CODCOLIGADA"));
                estoque.setCodFilial(rs.getShort("CODFILIAL"));
                estoque.setCodLoc(rs.getString("CODLOC"));
                estoque.setNome(rs.getString("NOME"));
                estoque.setInativo(rs.getBoolean("INATIVO"));
                
                estoques.add(estoque);
            }
            
            return estoques;
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar locais de estoque! " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }
    }
}
