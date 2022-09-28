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
import model.Funcionario;
import model.HistoricoFeriasPJ;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class HistoricoFeriasPJDAO {

    public int inserir(HistoricoFeriasPJ historico) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            String sql = "INSERT INTO HISTORICO_FERIAS_PJ (DATAINICIO, DATATERMINO,"
                    + " QUANTIDADE, CHAPA, CODCOLIGADA, RECCREATEDBY)"
                    + " VALUES (?, ?, ?, ?, ?, ?)";
            
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setObject(1, historico.getDataInicio());
            pstmt.setObject(2, historico.getDataTermino());
            pstmt.setInt(3, historico.getQuantidade());
            pstmt.setString(4, historico.getFuncionario().getChapa());
            pstmt.setInt(5, historico.getFuncionario().getCodColigada());
            pstmt.setString(6, historico.getCreatedBy());
            
            pstmt.execute();
            
            int id = 0;
            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            return id;
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir férias do funcionário no banco de dados! " + e.getMessage());
        }
    }
    
    public void alterar(HistoricoFeriasPJ historico) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            String sql = "update HISTORICO_FERIAS_PJ set DATAINICIO = ?,"
                    + " DATATERMINO = ?, QUANTIDADE = ?, CHAPA = ?, CODCOLIGADA = ?,"
                    + " RECMODIFIEDBY = ?, RECMODIFIEDON = NOW() where CODHISTORICO = ?";
            
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setObject(1, historico.getDataInicio());
            pstmt.setObject(2, historico.getDataTermino());
            pstmt.setInt(3, historico.getQuantidade());
            pstmt.setString(4, historico.getFuncionario().getChapa());
            pstmt.setInt(5, historico.getFuncionario().getCodColigada());
            pstmt.setString(6, historico.getModifiedBy());
            pstmt.setInt(7, historico.getCodHistorico());
            
            pstmt.execute();
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao alterar férias do funcionário no banco de dados! " + e.getMessage());
        }
    }
    
    public ArrayList<HistoricoFeriasPJ> buscar() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        
        try {
            String sql = "SELECT * FROM HISTORICO_FERIAS_PJ"
                    + " INNER JOIN PFUNC ON HISTORICO_FERIAS_PJ.CHAPA = PFUNC.CHAPA"
                    + " AND HISTORICO_FERIAS_PJ.CODCOLIGADA = PFUNC.CODCOLIGADA";
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<HistoricoFeriasPJ> historicos = new ArrayList<>();
            
            while(rs.next()) {
                HistoricoFeriasPJ historico = new HistoricoFeriasPJ();
                Funcionario funcionario = new Funcionario();
                
                historico.setCodHistorico(rs.getInt("CODHISTORICO"));
                historico.setDataInicio(rs.getDate("DATAINICIO").toLocalDate());
                historico.setDataTermino(rs.getDate("DATATERMINO").toLocalDate());
                historico.setQuantidade(rs.getInt("QUANTIDADE"));
                
                funcionario.setChapa(rs.getString("PFUNC.CHAPA"));
                funcionario.setCodColigada(rs.getShort("PFUNC.CODCOLIGADA"));
                funcionario.setNome(rs.getString("PFUNC.NOME"));
                
                historico.setFuncionario(funcionario);
                
                historicos.add(historico);
            }
            
            return historicos;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar historico de férias! " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }//finally

    }//buscar
    
    public ArrayList<HistoricoFeriasPJ> buscarPorNome(String nome) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            String sql = "SELECT * FROM HISTORICO_FERIAS_PJ"
                    + " INNER JOIN PFUNC ON HISTORICO_FERIAS_PJ.CHAPA = PFUNC.CHAPA"
                    + " AND HISTORICO_FERIAS_PJ.CODCOLIGADA = PFUNC.CODCOLIGADA WHERE PFUNC.NOME LIKE ?";
            pstmt = con.prepareStatement(sql);
            
            pstmt.setString(1, nome);
            
            ResultSet rs = pstmt.executeQuery();
            ArrayList<HistoricoFeriasPJ> historicos = new ArrayList<>();
            
            while(rs.next()) {
                HistoricoFeriasPJ historico = new HistoricoFeriasPJ();
                Funcionario funcionario = new Funcionario();
                
                historico.setCodHistorico(rs.getInt("CODHISTORICO"));
                historico.setDataInicio(rs.getDate("DATAINICIO").toLocalDate());
                historico.setDataTermino(rs.getDate("DATATERMINO").toLocalDate());
                historico.setQuantidade(rs.getInt("QUANTIDADE"));
                
                funcionario.setChapa(rs.getString("PFUNC.CHAPA"));
                funcionario.setCodColigada(rs.getShort("PFUNC.CODCOLIGADA"));
                funcionario.setNome(rs.getString("PFUNC.NOME"));
                
                historico.setFuncionario(funcionario);
                
                historicos.add(historico);
            }
            
            return historicos;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar historico de férias! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//finally

    }//buscar
}
