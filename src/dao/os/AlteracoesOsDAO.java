/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.os;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.os.AlteracoesOs;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class AlteracoesOsDAO {
    
    public void inserir(AlteracoesOs alteracoesOs) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            String sql = "insert into ALTERACOESOS (CODALTERACOESOS, CODOS, DESCRICAO, DATA, USUARIO)"
                    + " VALUES (NULL, ?, ?, NOW(), ?)";
            
            pstmt = con.prepareStatement(sql);
            
            pstmt.setInt(1, alteracoesOs.getOrdemServico().getCodOs());
            pstmt.setString(2, alteracoesOs.getDescricao());
            pstmt.setString(3, alteracoesOs.getUsuario());
            
            pstmt.execute();
            
        } catch (SQLException se) {
            throw new SQLException("Erro ao registrar alterações da OS/Tarefa! "+se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//finally
    }//inserir
}
