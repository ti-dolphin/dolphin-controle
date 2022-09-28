/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.ponto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.ponto.RelogioPonto;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class RelogioPontoDAO {
    
    public void inserir(RelogioPonto relogioPonto) throws SQLException{
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            String sql = "insert into DOLPHIN_REP (CODREP, NOME, IP, PATRIMONIO, NUMSERIE, ATIVO, DATASINC)"
                    + " values (null, ?, ?, ?, ?, ?, ?)";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, relogioPonto.getNome());
            pstmt.setString(2, relogioPonto.getIp());
            pstmt.setString(3, relogioPonto.getPatrimonio());
            pstmt.setString(4, relogioPonto.getNumeroSerie());
            pstmt.setBoolean(5, relogioPonto.isAtivo());
            pstmt.setObject(6, relogioPonto.getDataSinc());
            
            pstmt.execute();
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir relógio ponto! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//finally
    }//inserir
    
    public void alterar(RelogioPonto relogio) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            String sql = "update DOLPHIN_REP set NOME = ?, IP = ?, PATRIMONIO = ?,"
                    + " NUMSERIE = ?, ATIVO = ?, DATASINC = ? where CODREP = ?";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, relogio.getNome());
            pstmt.setString(2, relogio.getIp());
            pstmt.setString(3, relogio.getPatrimonio());
            pstmt.setString(4, relogio.getNumeroSerie());
            pstmt.setBoolean(5, relogio.isAtivo());
            pstmt.setObject(6, relogio.getDataSinc());
            pstmt.setInt(7, relogio.getCodRep());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao editar dados do relógio ponto! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//finally
        
    }//alterar
    
    public ArrayList<RelogioPonto> buscar(String query) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        
        try {
            String sql = "select * from DOLPHIN_REP where CODREP > 0 " + query;
            
            ResultSet rs = stat.executeQuery(sql);
            
            ArrayList<RelogioPonto> relogios = new ArrayList<>();
            
            while (rs.next()) {
                RelogioPonto relogio = new RelogioPonto();
                
                relogio.setCodRep(rs.getInt("CODREP"));
                relogio.setNome(rs.getString("NOME"));
                relogio.setIp(rs.getString("IP"));
                relogio.setNumeroSerie(rs.getString("NUMSERIE"));
                relogio.setPatrimonio(rs.getString("PATRIMONIO"));
                relogio.setAtivo(rs.getBoolean("ATIVO"));
                if (rs.getDate("DATASINC") != null) relogio.setDataSinc(rs.getDate("DATASINC").toLocalDate());
                
                relogios.add(relogio);
            }//while
            
            return relogios;
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar relógios ponto."+e.getMessage());
        } finally {
            con.close();
            stat.close();
        }//finally
    }//buscar
    
}
