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
import java.util.List;
import model.ClienteComentario;
import model.os.Cliente;
import persistencia.ConexaoBanco;
import view.Menu;

/**
 *
 * @author ti
 */
public class ClienteComentarioDAO {
    
    public int inserir(ClienteComentario comentario, int codColigada, String codCliente) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        
        try {
            String sql = "INSERT INTO CLIENTE_COMENTARIOS (DESCRICAO, CODCOLIGADA, CODCLIENTE, RECCREATEDBY) VALUES (?, ?, ?, ?)";
            
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, comentario.getDescricao());
            pstmt.setInt(2, codColigada);
            pstmt.setString(3, codCliente);
            pstmt.setString(4, Menu.logado.getLogin());

            pstmt.execute();

            int id = 0;
            ResultSet rs = pstmt.getGeneratedKeys();
            
            if (rs.next()) {
                id = rs.getInt(1);
            }

            return id;
            
        } catch (SQLException se) {
            throw new SQLException("Erro ao inserir comentario! " + se.getMessage());
        }
        
    }
    
    public List<ClienteComentario> buscarComentariosDoCliente(int codColigada, String codCliente) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        List<ClienteComentario> comentarios = new ArrayList<>();
        
        try {
            pstmt = con.prepareStatement("SELECT * FROM CLIENTE_COMENTARIOS WHERE CODCOLIGADA = ? AND CODCLIENTE = ?");
            pstmt.setInt(1, codColigada);
            pstmt.setString(2, codCliente);
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                ClienteComentario comentario = new ClienteComentario();
                comentario.setId(rs.getInt("ID"));
                comentario.setDescricao(rs.getString("DESCRICAO"));
                comentario.setCriadoPor(rs.getString("RECCREATEDBY"));
                comentario.setCriadoEm(rs.getTimestamp("RECCREATEDON").toLocalDateTime());
                
                comentarios.add(comentario);
            }//fecha while
            return comentarios;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar comentarios! " + se.getMessage());
        } finally {
            pstmt.close();
            con.close();
        }//fecha finally
    }

    
}
