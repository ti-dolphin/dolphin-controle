package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import persistencia.ConexaoBanco;
import model.Funcao;
import model.Funcionario;
import model.Situacao;

public class FuncionarioDAO {

    public void cadastrarAutenticacao(Funcionario f) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "update PFUNC set FINGER1 = ?, FINGER2 = ?, FINGER3 = ?, FINGER4 = ?, FINGER5 = ?, FINGER6 = ?, SENHA = ?"
                    + " where CODCOLIGADA = ? AND CHAPA = ?";

            pstmt = con.prepareStatement(sql);

            pstmt.setBytes(1, f.getFinger1());
            pstmt.setBytes(2, f.getFinger2());
            pstmt.setBytes(3, f.getFinger3());
            pstmt.setBytes(4, f.getFinger4());
            pstmt.setBytes(5, f.getFinger5());
            pstmt.setBytes(6, f.getFinger6());
            pstmt.setInt(7, f.getSenha());
            pstmt.setShort(8, f.getCodColigada());
            pstmt.setString(9, f.getChapa());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao cadastrar! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha cadastrarAutenticacao

    public ArrayList<Funcionario> filtrarFuncionarios(String query) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql;
            sql = "select \n"
                    + "	f.CODCOLIGADA, f.CHAPA, f.NOME, f.CODSITUACAO, fun.NOME, f.DATAADMISSAO, f.CODFILIAL, f.CPF, f.FINGER1, f.FINGER2, f.FINGER3, f.FINGER4,\n"
                    + "	f.FINGER5, f.FINGER6, f.SENHA, f.EMAIL, f.BANCO_HORAS \n"
                    + "from \n"
                    + "	PFUNC f \n"
                    + "inner join \n"
                    + "	PFUNCAO fun \n"
                    + "on \n"
                    + "	f.CODFUNCAO = fun.CODFUNCAO AND f.CODCOLIGADA = fun.CODCOLIGADA" + query;

            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Funcionario> fun = new ArrayList<>();

            while (rs.next()) {

                Funcionario funcionario = new Funcionario();
                Funcao funcao = new Funcao();
                Situacao situacao = new Situacao();
                funcionario.setCodColigada(rs.getShort("f.CODCOLIGADA"));
                funcionario.setChapa(rs.getString("f.CHAPA"));
                funcionario.setNome(rs.getString("f.NOME"));
                situacao.setCodSituacao(rs.getString("f.CODSITUACAO").charAt(0));

                funcionario.setSituacao(situacao);

                funcao.setNome(rs.getString("fun.NOME"));
                funcionario.setFuncao(funcao);
                funcionario.setDataAdmissao(rs.getDate("f.DATAADMISSAO").toLocalDate());
                funcionario.setCodFilial(rs.getShort("f.CODFILIAL"));
                funcionario.setFinger1(rs.getBytes("f.FINGER1"));
                funcionario.setFinger2(rs.getBytes("f.FINGER2"));
                funcionario.setFinger3(rs.getBytes("f.FINGER3"));
                funcionario.setFinger4(rs.getBytes("f.FINGER4"));
                funcionario.setFinger5(rs.getBytes("f.FINGER5"));
                funcionario.setFinger6(rs.getBytes("f.FINGER6"));
                funcionario.setCpf(rs.getString("f.CPF"));
                funcionario.setSenha(rs.getInt("f.SENHA"));
                funcionario.setEmail(rs.getString("f.EMAIL"));
                funcionario.setBancoHoras(rs.getDouble("f.BANCO_HORAS"));

                fun.add(funcionario);
            }
            return fun;
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar dados! " + e.getMessage());
        } finally {
            stat.close();
            con.close();
        }//fecha finally
    }//fecha filtrar

    public ArrayList<Funcionario> buscarFuncionariosPJ() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        try {
            String sql = "select \n"
                    + "	f.CODCOLIGADA, f.CHAPA, f.NOME, f.CODSITUACAO \n"
                    + "from \n"
                    + "	PFUNC f "
                    + " ORDER BY f.NOME";
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Funcionario> fa = new ArrayList<>();
            while (rs.next()) {
                Funcionario f = new Funcionario();
                Situacao s = new Situacao();
                
                f.setCodColigada(rs.getShort("f.CODCOLIGADA"));
                f.setChapa(rs.getString("f.CHAPA"));
                f.setNome(rs.getString("f.NOME"));
                s.setCodSituacao(rs.getString("f.CODSITUACAO").charAt(0));
                f.setSituacao(s);
                fa.add(f);
            }
            return fa;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar funcionários PJ! " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }//fecha finally
    }
}
