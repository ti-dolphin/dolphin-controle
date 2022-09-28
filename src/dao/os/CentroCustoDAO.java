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
import model.os.CentroCusto;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class CentroCustoDAO {

    public ArrayList<CentroCusto> buscar(String query) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "SELECT * FROM GCCUSTO "
                    + " where Length(CODCUSTO) = 9 "
                    + query
                    + " ORDER BY CODCUSTO";

            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<CentroCusto> ca = new ArrayList<>();

            while (rs.next()) {
                CentroCusto c = new CentroCusto();

                c.setCodCusto(rs.getString("CODCUSTO"));
                c.setNome(rs.getString("NOME"));
                c.setCampoLivre(rs.getString("CAMPOLIVRE"));
                c.setAtivo(rs.getBoolean("ATIVO"));
                c.setResponsavel(rs.getInt("RESPONSAVEL"));

                ca.add(c);
            }

            return ca;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar Centro de Custo! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha buscar

    public ArrayList<CentroCusto> buscarCentroCusto() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "SELECT * FROM GCCUSTO WHERE Length(CODCUSTO) = 9 ORDER BY CODCUSTO";

            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<CentroCusto> ca = new ArrayList<>();

            while (rs.next()) {
                CentroCusto c = new CentroCusto();

                c.setCodCusto(rs.getString("CODCUSTO"));
                c.setNome(rs.getString("NOME"));
                c.setCampoLivre(rs.getString("CAMPOLIVRE"));
                c.setAtivo(rs.getBoolean("ATIVO"));
                c.setResponsavel(rs.getInt("RESPONSAVEL"));

                ca.add(c);
            }

            return ca;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar Centro de Custo! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha buscar

    public ArrayList<CentroCusto> filtrarPorNome(String nome, boolean inativo) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<CentroCusto> ca = new ArrayList<>();

        try {
            pstmt = con.prepareStatement("SELECT * FROM GCCUSTO WHERE Length(CODCUSTO) = 9 AND ATIVO = ? AND NOME like ?");
            pstmt.setBoolean(1, inativo);
            pstmt.setString(2, "%" + nome + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CentroCusto c = new CentroCusto();
                c.setCodCusto(rs.getString("CODCUSTO"));
                c.setNome(rs.getString("NOME"));
                c.setCampoLivre(rs.getString("CAMPOLIVRE"));
                c.setAtivo(rs.getBoolean("ATIVO"));
                c.setResponsavel(rs.getInt("RESPONSAVEL"));
                ca.add(c);
            }//fecha while
            return ca;
        } catch (SQLException se) {
            throw new SQLException("Erro ao filtrar Centro de custo! " + se.getMessage());
        } finally {
            pstmt.close();
            con.close();
        }//fecha finally
    }//fecha método filtrar

    public CentroCusto buscarPorId(String codigoCentroCusto) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "SELECT * FROM GCCUSTO where CODCUSTO = '" + codigoCentroCusto + "'";

            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);

            CentroCusto centroCusto = new CentroCusto();
            
            if (rs.next()) {

                centroCusto.setCodCusto(rs.getString("CODCUSTO"));
                centroCusto.setNome(rs.getString("NOME"));
                centroCusto.setCampoLivre(rs.getString("CAMPOLIVRE"));
                centroCusto.setAtivo(rs.getBoolean("ATIVO"));
                centroCusto.setResponsavel(rs.getInt("RESPONSAVEL"));
            }
            return centroCusto;

        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar Centro de Custo! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }
}//fecha classe
