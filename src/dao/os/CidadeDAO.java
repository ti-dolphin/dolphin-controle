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
import model.os.Cidade;
import model.os.Estado;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class CidadeDAO {

    public ArrayList<Cidade> buscar(String query) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        
        try {
            String sql = "SELECT\n"
                    + "     CIDADE.ID, CIDADE.NOME, CIDADE.ESTADO, ESTADO.NOME\n"
                    + "FROM\n"
                    + "     CIDADE\n"
                    + "INNER JOIN\n"
                    + "     ESTADO\n"
                    + "ON\n"
                    + "     CIDADE.ESTADO = ESTADO.ID\n" 
                    + query;
            
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Cidade> cidades = new ArrayList<>();
            
            while(rs.next()) {
                Cidade cidade = new Cidade();
                Estado estado = new Estado();
                
                cidade.setId(rs.getInt("CIDADE.ID"));
                cidade.setNome(rs.getString("CIDADE.NOME"));
                estado.setId(rs.getInt("CIDADE.ESTADO"));
                estado.setNome(rs.getString("ESTADO.NOME"));
                cidade.setEstado(estado);
                
                cidades.add(cidade);
            }
            
            return cidades;
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar cidades! " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }//finally
    }//buscar
    
    public ArrayList<Cidade> buscarPorEstado(int id) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        
        try {
            String sql = "SELECT\n"
                    + "     CIDADE.ID, CIDADE.NOME, CIDADE.ESTADO, ESTADO.NOME\n"
                    + "FROM\n"
                    + "     CIDADE\n"
                    + "INNER JOIN\n"
                    + "     ESTADO\n"
                    + "ON\n"
                    + "     CIDADE.ESTADO = ESTADO.ID\n" 
                    + " WHERE CIDADE.ESTADO = " + id;
            
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Cidade> cidades = new ArrayList<>();
            
            while(rs.next()) {
                Cidade cidade = new Cidade();
                Estado estado = new Estado();
                
                cidade.setId(rs.getInt("CIDADE.ID"));
                cidade.setNome(rs.getString("CIDADE.NOME"));
                estado.setId(rs.getInt("CIDADE.ESTADO"));
                estado.setNome(rs.getString("ESTADO.NOME"));
                cidade.setEstado(estado);
                
                cidades.add(cidade);
            }
            
            return cidades;
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar cidades! " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }//finally
    }//buscar
}
