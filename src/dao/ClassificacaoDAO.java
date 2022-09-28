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
import model.os.Classificacao;
import persistencia.ConexaoBanco;

/**
 *
 * @author ti
 */
public class ClassificacaoDAO {
    
    public ArrayList<Classificacao> buscar() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        
        try {
            String sql = "select * from CLASSIFICACAO";
            
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Classificacao> classificacoes = new ArrayList<>();

            while(rs.next()) {
                Classificacao classificacao = new Classificacao();
                
                classificacao.setId(rs.getInt("ID"));
                classificacao.setAbreviacao(rs.getString("ABREVIACAO").charAt(0));
                
                classificacoes.add(classificacao);
            }
            
            return classificacoes;
            
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar classificacoes! " + se.getMessage());
        }
    }
}
