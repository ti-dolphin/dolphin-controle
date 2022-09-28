/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.os;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import model.os.HistoricoStatus;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class HistoricoStatusDAO {
 
    public void inserir(HistoricoStatus historicoStatus) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            String sql = "insert into HISTORICOSTATUS (CODHISTORICO, CODOS, CODSTATUSNEW, CODSTATUSOLD, DATA, CODPESSOA)"
                    + " VALUES (NULL, ?, ?, ?, ?, ?)";
            
            pstmt = con.prepareStatement(sql);
            
            pstmt.setInt(1, historicoStatus.getOrdemServico().getCodOs());
            pstmt.setInt(2, historicoStatus.getStatusNew().getCodStatus());
            pstmt.setInt(3, historicoStatus.getStatusOld().getCodStatus());
            pstmt.setObject(4, historicoStatus.getData());
            pstmt.setInt(5, historicoStatus.getUsuario().getCodPessoa());
            
            pstmt.execute();
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir historico do status! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//finally
    }//inserir
}
