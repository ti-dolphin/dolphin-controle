/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.ponto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Funcionario;
import model.ponto.Ponto;
import model.ponto.RelogioPonto;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class PontoDAO {
    
    public ArrayList<Ponto> buscar(String query) throws SQLException{
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        
        try {
            String sql = "select"
                        + " DOLPHIN_REP_PONTO_COLETADO.CODPONTO,"
                        + " DOLPHIN_REP_PONTO_COLETADO.NSR,"
                        + " DOLPHIN_REP_PONTO_COLETADO.TIPO,"
                        + " DOLPHIN_REP_PONTO_COLETADO.DATA,"
                        + " DOLPHIN_REP_PONTO_COLETADO.HORA,"
                        + " DOLPHIN_REP_PONTO_COLETADO.PIS,"
                        + " DOLPHIN_REP_PONTO_COLETADO.CRC,"
                        + " DOLPHIN_REP_PONTO_COLETADO.COMPLETO,"
                        + " DOLPHIN_REP_PONTO_COLETADO.CODREP,"
                        + " DOLPHIN_REP.CODREP,"
                        + " DOLPHIN_REP.NOME,"
                        + " PFUNC.NOME"
                        + " from DOLPHIN_REP_PONTO_COLETADO"
                        + " INNER JOIN DOLPHIN_REP"
                        + " ON DOLPHIN_REP.CODREP = DOLPHIN_REP_PONTO_COLETADO.CODREP"
                        + " INNER JOIN PFUNC"
                        + " ON DOLPHIN_REP_PONTO_COLETADO.PIS = PFUNC.PIS WHERE PFUNC.CODSITUACAO <> 'D' " 
                        + query + " order by DATA desc, PFUNC.NOME, HORA desc";
            System.out.println(sql);
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Ponto> pontos = new ArrayList<>();
            
            while(rs.next()){
                Ponto ponto = new Ponto();
                RelogioPonto relogio = new RelogioPonto();
                Funcionario funcionario  = new Funcionario();
                
                ponto.setCodPonto(rs.getInt("CODPONTO"));
                ponto.setNsr(rs.getString("NSR"));
                ponto.setTipo(rs.getInt("TIPO"));
                ponto.setData(rs.getDate("DATA").toLocalDate());
                ponto.setHora(rs.getTime("HORA").toLocalTime());
                ponto.setPis(rs.getString("DOLPHIN_REP_PONTO_COLETADO.PIS"));
                ponto.setCrc(rs.getString("CRC"));
                ponto.setCompleto(rs.getString("COMPLETO"));
                relogio.setCodRep(rs.getInt("DOLPHIN_REP.CODREP"));
                relogio.setNome(rs.getString("DOLPHIN_REP.NOME"));
                funcionario.setNome(rs.getString("PFUNC.NOME"));
                ponto.setRelogioPonto(relogio);
                ponto.setFuncionario(funcionario);
                
                pontos.add(ponto);
                
            }//while
            
            return pontos;
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar pontos! " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }//finally
    }//buscar
}
