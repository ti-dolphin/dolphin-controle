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

    public ArrayList<Funcionario> buscarFuncionario() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        try {
            String sql = "select \n"
                    + "	f.CODCOLIGADA, f.CHAPA, f.NOME, f.CODSITUACAO, fun.NOME,"
                    + " f.DATAADMISSAO, f.CODFILIAL, f.CPF, f.FINGER1, f.FINGER2,"
                    + " f.FINGER3, f.FINGER4,\n"
                    + "	f.FINGER5, f.FINGER6, f.SENHA, f.EMAIL \n"
                    + "from \n"
                    + "	PFUNC f \n"
                    + "inner join \n"
                    + "	PFUNCAO fun \n"
                    + "on \n"
                    + "	f.CODFUNCAO = fun.CODFUNCAO AND f.CODCOLIGADA = fun.CODCOLIGADA "
                    + "ORDER BY f.NOME LIMIT 1000";
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Funcionario> fa = new ArrayList<>();
            while (rs.next()) {
                Funcionario f = new Funcionario();
                Funcao funcao = new Funcao();
                Situacao situacao = new Situacao();
                f.setCodColigada(rs.getShort("f.CODCOLIGADA"));
                f.setChapa(rs.getString("f.CHAPA"));
                f.setNome(rs.getString("f.NOME"));
                situacao.setCodSituacao(rs.getString("f.CODSITUACAO").charAt(0));

                f.setSituacao(situacao);

                funcao.setNome(rs.getString("fun.NOME"));
                f.setFuncao(funcao);
                f.setDataAdmissao(rs.getDate("f.DATAADMISSAO").toLocalDate());
                f.setCodFilial(rs.getShort("f.CODFILIAL"));
                f.setFinger1(rs.getBytes("f.FINGER1"));
                f.setFinger2(rs.getBytes("f.FINGER2"));
                f.setFinger3(rs.getBytes("f.FINGER3"));
                f.setFinger4(rs.getBytes("f.FINGER4"));
                f.setFinger5(rs.getBytes("f.FINGER5"));
                f.setFinger6(rs.getBytes("f.FINGER6"));
                f.setCpf(rs.getString("f.CPF"));
                f.setSenha(rs.getInt("f.SENHA"));
                f.setEmail(rs.getString("f.EMAIL"));

                fa.add(f);
            }

            return fa;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar funcionários! " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }//fecha finally
    }//fecha buscarFuncionario
    public ArrayList<Funcionario> buscarFuncionariosAtivos() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        try {
            String sql = "select \n"
                    + "	f.CODCOLIGADA, f.CHAPA, f.NOME, f.CODSITUACAO, fun.NOME, f.DATAADMISSAO, f.CODFILIAL, f.CPF, f.FINGER1, f.FINGER2, f.FINGER3, f.FINGER4,\n"
                    + "	f.FINGER5, f.FINGER6, f.SENHA, f.EMAIL \n"
                    + "from \n"
                    + "	PFUNC f \n"
                    + "inner join \n"
                    + "	PFUNCAO fun \n"
                    + "on \n"
                    + "	f.CODFUNCAO = fun.CODFUNCAO AND f.CODCOLIGADA = fun.CODCOLIGADA WHERE f.CODSITUACAO <> 'D'"
                    + "ORDER BY f.NOME LIMIT 1000";
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Funcionario> fa = new ArrayList<>();
            while (rs.next()) {
                Funcionario f = new Funcionario();
                Funcao funcao = new Funcao();
                Situacao situacao = new Situacao();
                f.setCodColigada(rs.getShort("f.CODCOLIGADA"));
                f.setChapa(rs.getString("f.CHAPA"));
                f.setNome(rs.getString("f.NOME"));
                situacao.setCodSituacao(rs.getString("f.CODSITUACAO").charAt(0));

                f.setSituacao(situacao);

                funcao.setNome(rs.getString("fun.NOME"));
                f.setFuncao(funcao);
                f.setDataAdmissao(rs.getDate("f.DATAADMISSAO").toLocalDate());
                f.setCodFilial(rs.getShort("f.CODFILIAL"));
                f.setFinger1(rs.getBytes("f.FINGER1"));
                f.setFinger2(rs.getBytes("f.FINGER2"));
                f.setFinger3(rs.getBytes("f.FINGER3"));
                f.setFinger4(rs.getBytes("f.FINGER4"));
                f.setFinger5(rs.getBytes("f.FINGER5"));
                f.setFinger6(rs.getBytes("f.FINGER6"));
                f.setCpf(rs.getString("f.CPF"));
                f.setSenha(rs.getInt("f.SENHA"));
                f.setEmail(rs.getString("f.EMAIL"));

                fa.add(f);
            }

            return fa;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar funcionários! " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }//fecha finally
    }//fecha buscar Funcionarios ativos

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

    public ArrayList<Funcionario> filtarFuncionario(String query) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql;
            sql = "select \n"
                    + "	f.CODCOLIGADA, f.CHAPA, f.NOME, f.CODSITUACAO, fun.NOME, f.DATAADMISSAO, f.CODFILIAL, f.CPF, f.FINGER1, f.FINGER2, f.FINGER3, f.FINGER4,\n"
                    + "	f.FINGER5, f.FINGER6, f.SENHA, f.EMAIL \n"
                    + "from \n"
                    + "	PFUNC f \n"
                    + "inner join \n"
                    + "	PFUNCAO fun \n"
                    + "on \n"
                    + "	f.CODFUNCAO = fun.CODFUNCAO AND f.CODCOLIGADA = fun.CODCOLIGADA" + query;

            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Funcionario> fun = new ArrayList<>();

            while (rs.next()) {

                Funcionario f = new Funcionario();
                Funcao funcao = new Funcao();
                Situacao situacao = new Situacao();
                f.setCodColigada(rs.getShort("f.CODCOLIGADA"));
                f.setChapa(rs.getString("f.CHAPA"));
                f.setNome(rs.getString("f.NOME"));
                situacao.setCodSituacao(rs.getString("f.CODSITUACAO").charAt(0));

                f.setSituacao(situacao);

                funcao.setNome(rs.getString("fun.NOME"));
                f.setFuncao(funcao);
                f.setDataAdmissao(rs.getDate("f.DATAADMISSAO").toLocalDate());
                f.setCodFilial(rs.getShort("f.CODFILIAL"));
                f.setFinger1(rs.getBytes("f.FINGER1"));
                f.setFinger2(rs.getBytes("f.FINGER2"));
                f.setFinger3(rs.getBytes("f.FINGER3"));
                f.setFinger4(rs.getBytes("f.FINGER4"));
                f.setFinger5(rs.getBytes("f.FINGER5"));
                f.setFinger6(rs.getBytes("f.FINGER6"));
                f.setCpf(rs.getString("f.CPF"));
                f.setSenha(rs.getInt("f.SENHA"));
                f.setEmail(rs.getString("f.EMAIL"));

                fun.add(f);
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
