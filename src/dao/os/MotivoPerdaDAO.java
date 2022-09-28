/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.os;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.os.MotivoPerda;
import persistencia.ConexaoBanco;

/**
 *
 * @author ti
 */
public class MotivoPerdaDAO {
    public ArrayList<MotivoPerda> buscarTodos() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "select * from motivo_perdido";

            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<MotivoPerda> motivos = new ArrayList<>();
            while (rs.next()) {
                MotivoPerda motivo = new MotivoPerda();

                motivo.setId(rs.getInt("id"));
                motivo.setNome(rs.getString("nome"));

                motivos.add(motivo);
            }

            return motivos;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar motivos da perda! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha buscar
}
