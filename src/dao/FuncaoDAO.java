package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Funcao;
import persistencia.ConexaoBanco;

public class FuncaoDAO {

    public void cadastrarFuncao(Funcao f) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql;
            sql = "insert into PFUNCAO(CODCOLIGADA, CODIGO, NOME)"
                    + "values(?, ?, ?)";
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, String.valueOf(f.getCodColigada()));
            pstmt.setString(2, f.getCodigo());
            pstmt.setString(3, f.getNome());

            pstmt.execute();

        } catch (SQLException e) {
            throw new SQLException("Erro ao cadastrar função! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha cadastrar

    public ArrayList<Funcao> buscarFuncao() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "select * from PFUNCAO";
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Funcao> fa = new ArrayList<>();

            while (rs.next()) {
                Funcao f = new Funcao();

                f.setCodColigada(rs.getShort("CODCOLIGADA"));
                f.setCodigo(rs.getString("CODIGO"));
                f.setNome(rs.getString("NOME"));

                fa.add(f);
            }

            return fa;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar função! " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }//fecha finally
    }//fecha buscar

    public void deletarFuncao(String codigo) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql;
            sql = "delete from PFUNCAO where CODIGO = " + codigo;

            stat.execute(sql);

        } catch (SQLException e) {
            throw new SQLException("Erro ao deletar função! " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }//fecha finally
    }//fecha deletarFuncao

    public void alterarFuncao(Funcao f) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql;
            sql = "update PFUNCAO set "
                    + "CODCOLIGADA=?, CODFUNCAO=?, NOME=? "
                    + "where CODFUNCAO=?";

            pstmt = con.prepareStatement(sql);

            pstmt.setShort(1, f.getCodColigada());
            pstmt.setString(2, f.getCodigo());
            pstmt.setString(3, f.getNome());
            pstmt.setString(4, f.getCodigo());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Erro ao alterar função! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha alterarFuncao

}
