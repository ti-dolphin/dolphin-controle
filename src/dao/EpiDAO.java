package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import persistencia.ConexaoBanco;
import model.Epi;

public class EpiDAO {

    public ArrayList<Epi> buscarEpi() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {

            String sql = "select * from VCATALOGEPI";
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Epi> ea = new ArrayList<>();

            while (rs.next()) {
                Epi e = new Epi();

                e.setCodEpi(rs.getString("CODEPI"));
                e.setNome(rs.getString("NOME"));
                e.setDescricao(rs.getString("DESCRICAO"));
                e.setPreco(rs.getDouble("PRECO"));
                e.setInativa(rs.getBoolean("INATIVA"));
                e.setPeriodicidade(rs.getInt("PERIODICIDADE"));

                ea.add(e);
            }

            return ea;
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar EPIs! " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }//fecha finally
    }//fecha buscarEpi

    public ArrayList<Epi> filtrarEpi(String query) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql;
            sql = "select * from VCATALOGEPI" + query;

            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Epi> ea = new ArrayList<>();

            while (rs.next()) {
                Epi e = new Epi();

                e.setCodEpi(rs.getString("CODEPI"));
                e.setNome(rs.getString("NOME"));
                e.setPreco(rs.getDouble("PRECO"));
                e.setDescricao(rs.getString("DESCRICAO"));
                e.setInativa(rs.getBoolean("INATIVA"));
                e.setPeriodicidade(rs.getInt("PERIODICIDADE"));

                ea.add(e);
            }
            return ea;
        } catch (SQLException e) {
            throw new SQLException("Erro ao filtrar EPIs! " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }//fecha finally
    }//fecha buscarEpi
    
    public void alterar(Epi epi) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "update VCATALOGEPI set"
                    + " PERIODICIDADE = ?, PRECO = ?"
                    + " WHERE CODEPI = ?";

            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, epi.getPeriodicidade());
            pstmt.setDouble(2, epi.getPreco());
            pstmt.setString(3, epi.getCodEpi());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Erro ao editar EPI! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//finally
    }//altearPeriodicidade
}//fecha classe
