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
import java.sql.Statement;
import java.util.ArrayList;
import model.Seguidor;
import model.os.OrdemServico;
import model.os.Pessoa;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class SeguidoresDAO {

    public Seguidor inserir(Seguidor seguidor) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "INSERT INTO \n"
                    + "	SEGUIDORES (CODOS, CODPESSOA) \n"
                    + "SELECT \n"
                    + "	ORDEMSERVICO.CODOS, ?\n"
                    + "FROM ORDEMSERVICO\n"
                    + "LEFT JOIN \n"
                    + "	SEGUIDORES \n"
                    + "ON ORDEMSERVICO.CODOS = SEGUIDORES.CODOS \n"
                    + "AND ? = SEGUIDORES.CODPESSOA\n"
                    + "WHERE\n"
                    + "	ORDEMSERVICO.CODOS = ?\n"
                    + "    AND SEGUIDORES.CODPESSOA IS NULL";

            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, seguidor.getPessoa().getCodPessoa());
            pstmt.setInt(2, seguidor.getPessoa().getCodPessoa());
            pstmt.setInt(3, seguidor.getOrdemServico().getCodOs());

            pstmt.execute();

            return seguidor;
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir seguidor! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//finally
    }//inserir

    public ArrayList<Seguidor> filtrar(String query) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "SELECT SEGUIDORES.CODOS, SEGUIDORES.CODPESSOA, PESSOA.NOME"
                    + " FROM SEGUIDORES INNER JOIN ORDEMSERVICO"
                    + " ON ORDEMSERVICO.CODOS = SEGUIDORES.CODOS"
                    + " INNER JOIN PESSOA"
                    + " ON PESSOA.CODPESSOA = SEGUIDORES.CODPESSOA WHERE SEGUIDORES.CODOS > 0 "
                    + query + " ORDER BY PESSOA.NOME";

            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Seguidor> seguidores = new ArrayList<>();

            while (rs.next()) {
                Seguidor seguidor = new Seguidor();
                OrdemServico ordemServico = new OrdemServico();
                Pessoa pessoa = new Pessoa();

                ordemServico.setCodOs(rs.getInt("SEGUIDORES.CODOS"));
                pessoa.setCodPessoa(rs.getInt("SEGUIDORES.CODPESSOA"));
                pessoa.setNome(rs.getString("PESSOA.NOME"));

                seguidor.setOrdemServico(ordemServico);
                seguidor.setPessoa(pessoa);

                seguidores.add(seguidor);
            }//while

            return seguidores;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar seguidor: " + e.getMessage());
        }//catch
    }//filtrar

    public ArrayList<Seguidor> buscarPorOs(int codOs) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "SELECT SEGUIDORES.CODOS, SEGUIDORES.CODPESSOA, PESSOA.NOME, PESSOA.EMAIL"
                    + " FROM SEGUIDORES INNER JOIN ORDEMSERVICO"
                    + " ON ORDEMSERVICO.CODOS = SEGUIDORES.CODOS"
                    + " INNER JOIN PESSOA"
                    + " ON PESSOA.CODPESSOA = SEGUIDORES.CODPESSOA WHERE SEGUIDORES.CODOS = "
                    + codOs + " ORDER BY PESSOA.NOME";

            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Seguidor> seguidores = new ArrayList<>();

            while (rs.next()) {
                Seguidor seguidor = new Seguidor();
                OrdemServico ordemServico = new OrdemServico();
                Pessoa pessoa = new Pessoa();

                ordemServico.setCodOs(rs.getInt("SEGUIDORES.CODOS"));
                pessoa.setCodPessoa(rs.getInt("SEGUIDORES.CODPESSOA"));
                pessoa.setNome(rs.getString("PESSOA.NOME"));
                pessoa.setEmail(rs.getString("PESSOA.EMAIL"));

                seguidor.setOrdemServico(ordemServico);
                seguidor.setPessoa(pessoa);

                seguidores.add(seguidor);
            }//while

            return seguidores;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar seguidor: " + e.getMessage());
        }//catch
    }//buscar

    public ArrayList<Seguidor> buscarSeguidoresDaOs(int codOs) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        
        String sql = "SELECT SEGUIDORES.CODOS, SEGUIDORES.CODPESSOA, PESSOA.NOME, PESSOA.EMAIL"
                + " FROM SEGUIDORES INNER JOIN ORDEMSERVICO"
                + " ON ORDEMSERVICO.CODOS = SEGUIDORES.CODOS"
                + " INNER JOIN PESSOA"
                + " ON PESSOA.CODPESSOA = SEGUIDORES.CODPESSOA WHERE SEGUIDORES.CODOS = ?"
                + " ORDER BY PESSOA.NOME";

        PreparedStatement pstmt = con.prepareStatement(sql);

        try {
            pstmt.setInt(1, codOs);

            ResultSet rs = pstmt.executeQuery();
            ArrayList<Seguidor> seguidores = new ArrayList<>();

            while (rs.next()) {
                Seguidor seguidor = new Seguidor();
                OrdemServico ordemServico = new OrdemServico();
                Pessoa pessoa = new Pessoa();

                ordemServico.setCodOs(rs.getInt("SEGUIDORES.CODOS"));
                pessoa.setCodPessoa(rs.getInt("SEGUIDORES.CODPESSOA"));
                pessoa.setNome(rs.getString("PESSOA.NOME"));
                pessoa.setEmail(rs.getString("PESSOA.EMAIL"));

                seguidor.setOrdemServico(ordemServico);
                seguidor.setPessoa(pessoa);

                seguidores.add(seguidor);
            }//while

            return seguidores;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar seguidor: " + e.getMessage());
        } finally {
            pstmt.close();
        }
    }//buscar

    public void alterar() throws SQLException {
        throw new UnsupportedOperationException("Não suportado ainda.");
    }//update  

    public void excluir(Seguidor seguidor) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql;
            sql = "delete from SEGUIDORES where CODOS = "
                    + seguidor.getOrdemServico().getCodOs()
                    + " AND CODPESSOA = " + seguidor.getPessoa().getCodPessoa();

            stat.execute(sql);
        } catch (SQLException se) {
            throw new SQLException("Erro ao excluir seguidor! " + se.getMessage());
        } finally {
            stat.close();
            con.close();
        }//finally
    }//excluir

    public void inserirSeguidoresGerente(OrdemServico ordemServico) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "INSERT INTO SEGUIDORES (CODOS, CODPESSOA)\n"
                    + "	SELECT\n"
                    + "		OS.CODOS, P.CODPESSOA\n"
                    + "	FROM\n"
                    + "		ORDEMSERVICO OS\n"
                    + "	INNER JOIN	GCCUSTO C\n"
                    + "		ON OS.CODCCUSTO = C.CODCUSTO\n"
                    + "	INNER JOIN	PESSOA P\n"
                    + "		ON	P.CODGERENTE = C.RESPONSAVEL\n"
                    + "	LEFT JOIN	SEGUIDORES S\n"
                    + "		ON	OS.CODOS = S.CODOS\n"
                    + "		AND	P.CODPESSOA = S.CODPESSOA\n"
                    + "	WHERE\n"
                    + "		OS.CODOS = ?\n"
                    + "		AND S.CODPESSOA IS NULL;\n";

            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, ordemServico.getCodOs());

            pstmt.execute();
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir seguidores obrigatórios! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//finally
    }//inserirSeguidoresObrigatorios

    public void inserirSeguidoresResponsavel(OrdemServico ordemServico) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "INSERT INTO SEGUIDORES (CODOS, CODPESSOA)\n"
                    + "	   SELECT \n"
                    + "		ORDEMSERVICO.CODOS, ORDEMSERVICO.RESPONSAVEL \n"
                    + "       FROM \n"
                    + "		ORDEMSERVICO\n"
                    + "	   LEFT JOIN SEGUIDORES \n"
                    + "		ON ORDEMSERVICO.CODOS = SEGUIDORES.CODOS\n"
                    + "		AND ORDEMSERVICO.RESPONSAVEL = SEGUIDORES.CODPESSOA\n"
                    + "       WHERE \n"
                    + "		ORDEMSERVICO.CODOS = ?\n"
                    + "        AND SEGUIDORES.CODPESSOA IS NULL;";

            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, ordemServico.getCodOs());

            pstmt.execute();
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir seguidores obrigatórios! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//finally
    }//inserirSeguidoresObrigatorios

    public void inserirSeguidoresSolicitante(OrdemServico ordemServico) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "INSERT INTO SEGUIDORES (CODOS, CODPESSOA)\n"
                    + "	   SELECT \n"
                    + "		ORDEMSERVICO.CODOS, ORDEMSERVICO.SOLICITANTE \n"
                    + "       FROM \n"
                    + "		ORDEMSERVICO\n"
                    + "	   LEFT JOIN SEGUIDORES \n"
                    + "		ON ORDEMSERVICO.CODOS = SEGUIDORES.CODOS\n"
                    + "		AND ORDEMSERVICO.SOLICITANTE = SEGUIDORES.CODPESSOA\n"
                    + "       WHERE \n"
                    + "		ORDEMSERVICO.CODOS = ?\n"
                    + "        AND SEGUIDORES.CODPESSOA IS NULL;\n";

            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, ordemServico.getCodOs());

            pstmt.execute();
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir seguidores obrigatórios! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//finally
    }//inserirSeguidoresObrigatorios
}//classe
