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
import model.Ticket;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class TicketDAO {

    public int buscarTicket() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt;
        Statement stat = con.createStatement();
        
        try {
            String sql = "INSERT INTO TKT (CODTKT, DATAENVIO) VALUES (NULL, NOW())";
            
            pstmt = con.prepareStatement(sql);
            pstmt.execute();
            
            String sqlBusca = "SELECT MAX(CODTKT) FROM TKT";
            
            ResultSet rs = stat.executeQuery(sqlBusca);

            int codTkt = 0;
            if (rs != null && rs.next()) {
                codTkt = rs.getInt("MAX(CODTKT)");
            }
            
            return codTkt;
            
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar ticket! " + se.getMessage());
        } finally {
            con.close();
        }//finally
    }//fecha cadastrarTicket

    public ArrayList<Ticket> buscarTickets() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "SELECT * FROM TKT";
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Ticket> ta = new ArrayList<>();
            while (rs.next()) {
                Ticket t = new Ticket();
                t.setTkt(rs.getInt("CODTKT"));
                t.setDataEnvio(rs.getTimestamp("DATAENVIO").toLocalDateTime());
                ta.add(t);
            }
            return ta;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar tickets!" + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }//fecha finally
    }//fecha buscarTicket    

    public int maiorTicket() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "SELECT MAX(CODTKT) as TKT FROM TKT";
            ResultSet rs = stat.executeQuery(sql);

            Ticket t = new Ticket();
            if (rs != null && rs.next()) {
                t.setTkt(rs.getInt("TKT"));
            }

            return t.getTkt();
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar maior ticket!" + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }//fecha finally
    }
}
