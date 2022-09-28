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
import model.CheckListModelo;
import model.Patrimonio;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class PatrimonioDAO {
    
    public ArrayList<Patrimonio> buscar() throws SQLException {
        
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        
        try {
            String sql = "select * from PATRIMONIOS inner join CHECKLIST_MODELO"
                    + " on PATRIMONIOS.ID_CHECKLIST_MODELO = CHECKLIST_MODELO.ID";
            
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Patrimonio> patrimonios = new ArrayList<>();

            while (rs.next()) {
                Patrimonio patrimonio = new Patrimonio();
                CheckListModelo checkListModelo = new CheckListModelo();

                patrimonio.setId(rs.getInt("ID"));
                patrimonio.setCodColigada(rs.getShort("CODCOLIGADA"));
                patrimonio.setCodPatrimonio(rs.getString("PATRIMONIO"));
                patrimonio.setDescricao(rs.getString("DESCRICAO"));
                patrimonio.setObservacao(rs.getString("OBSERVACOES"));
                
                checkListModelo.setId(rs.getInt("PATRIMONIOS.ID_CHECKLIST_MODELO"));
                checkListModelo.setNome(rs.getString("CHECKLIST_MODELO.NOME"));
                
                patrimonio.setCheckListModelo(checkListModelo);
                
                patrimonios.add(patrimonio);
            }
            
            return patrimonios;
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar patrimonios! " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }
    }
    
    public void alterar(Patrimonio patrimonio) throws SQLException {
        
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            
            String sql = "update PATRIMONIOS set ID_CHECKLIST_MODELO = ? where ID = ? and CODCOLIGADA = ?";
            
            pstmt = con.prepareStatement(sql);
            
            pstmt.setInt(1, patrimonio.getCheckListModelo().getId());
            pstmt.setInt(2, patrimonio.getId());
            pstmt.setInt(3, patrimonio.getCodColigada());
            
            pstmt.executeUpdate();
            
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao alterar patrimônio! " + e.getMessage());
        } finally {
            con.close();
        }
    }
}
