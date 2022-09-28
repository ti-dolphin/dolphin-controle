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
import java.util.ArrayList;
import model.LocalDeEstoque;
import model.MovimentoItem;
import model.Produto;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class ProdutoDAO {
    
    public ArrayList<Produto> buscar() throws SQLException {
        
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        
        try {
            String sql = "select * from PRODUTOS";
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Produto> produtos = new ArrayList<>();

            while (rs.next()) {
                Produto produto = new Produto();

                produto.setId(rs.getInt("ID"));
                produto.setCodColigada(rs.getShort("CODCOLPRD"));
                produto.setCodProduto(rs.getString("CODPRODUTO"));
                produto.setNomeFantasia(rs.getString("NOMEFANTASIA"));
                produto.setDescricao(rs.getString("DESCRICAO"));

                produtos.add(produto);
            }
            
            return produtos;
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar produtos! " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }
    }
}
