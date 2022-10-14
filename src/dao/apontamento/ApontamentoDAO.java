/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.apontamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Funcao;
import model.Funcionario;
import model.Situacao;
import model.apontamento.Apontamento;
import model.apontamento.StatusApont;
import model.os.CentroCusto;
import model.os.OrdemServico;
import model.os.Pessoa;
import persistencia.ConexaoBanco;
import view.Menu;

/**
 *
 * @author guilherme.oliveira
 */
public class ApontamentoDAO {

    public ArrayList<Apontamento> filtrarApontamento(String query) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "SELECT A.CODOS, A.INTEGRA, A.CODAPONT, A.CHAPA, F.NOME, FUN.CODFUNCAO,"
                    + " FUN.NOME, A.DATA, C.CODCUSTO, C.NOME, C.ATIVO, C.CODREDUZIDO, A.CODSTATUSAPONT, SA.DESCRICAO,"
                    + " P.CODPESSOA, P.NOME, PG.CODPESSOA, PG.NOME, A.ATIVIDADE, A.CODSITUACAO, A.COMENTADO, A.MODIFICADOPOR,"
                    + " A.COMPETENCIA, A.ASSIDUIDADE, A.PONTO"
                    + " FROM\n"
                    + "	APONTAMENTOS A\n"
                    + "INNER JOIN\n"
                    + "	PFUNC F\n"
                    + "ON\n"
                    + "	A.CHAPA = F.CHAPA AND F.CODCOLIGADA = 1\n"
                    + "INNER JOIN\n"
                    + "	PFUNCAO FUN\n"
                    + "ON\n"
                    + "	F.CODFUNCAO = FUN.CODFUNCAO\n"
                    + "INNER JOIN\n"
                    + "	GCCUSTO C\n"
                    + "ON\n"
                    + "	A.CODCCUSTO = C.CODCUSTO\n"
                    + "LEFT JOIN\n"
                    + "	PESSOA PG\n"
                    + "ON\n"
                    + "	PG.CODGERENTE = C.RESPONSAVEL\n"
                    + "INNER JOIN\n"
                    + "	PESSOA P\n"
                    + "ON\n"
                    + "	A.CODLIDER = P.CODPESSOA "
                    + "INNER JOIN\n"
                    + "	STATUSAPONT SA\n"
                    + "ON\n"
                    + "	SA.CODSTATUSAPONT = A.CODSTATUSAPONT\n"
                    + " where A.CODAPONT > 0 \n"
                    + query + " ORDER BY A.DATA, F.NOME";

            ResultSet rs = stat.executeQuery(sql);

            ArrayList<Apontamento> apontamentos = new ArrayList<>();

            while (rs.next()) {
                Apontamento apontamento = new Apontamento();
                Funcionario funcionario = new Funcionario();
                Funcao funcao = new Funcao();
                CentroCusto centroCusto = new CentroCusto();
                Pessoa pessoa = new Pessoa();
                Pessoa pg = new Pessoa();
                StatusApont s = new StatusApont();
                OrdemServico os = new OrdemServico();
                Situacao si = new Situacao();

                os.setCodOs(rs.getInt("A.CODOS"));
                apontamento.setCodApont(rs.getInt("A.CODAPONT"));
                apontamento.setIntegra(rs.getBoolean("A.INTEGRA"));
                funcionario.setChapa(rs.getString("A.CHAPA"));
                funcionario.setNome(rs.getString("F.NOME"));
                funcao.setCodigo(rs.getString("FUN.CODFUNCAO"));
                funcao.setNome(rs.getString("FUN.NOME"));
                apontamento.setData(rs.getTimestamp("A.DATA").toLocalDateTime());
                apontamento.setComentado(rs.getBoolean("A.COMENTADO"));
                centroCusto.setCodCusto(rs.getString("C.CODCUSTO"));
                centroCusto.setNome(rs.getString("C.NOME"));
                centroCusto.setAtivo(rs.getBoolean("C.ATIVO"));
                centroCusto.setCodReduzido(rs.getString("C.CODREDUZIDO"));
                s.setCodStatusApont(rs.getString("A.CODSTATUSAPONT"));
                s.setDescricao(rs.getString("SA.DESCRICAO"));
                pessoa.setCodPessoa(rs.getInt("P.CODPESSOA"));
                pessoa.setNome(rs.getString("P.NOME"));
                pg.setCodPessoa(rs.getInt("PG.CODPESSOA"));
                pg.setNome(rs.getString("PG.NOME"));
                apontamento.setAtividade(rs.getString("A.ATIVIDADE"));
                si.setCodSituacao(rs.getString("A.CODSITUACAO").charAt(0));
                apontamento.setModificadoPor(rs.getString("A.MODIFICADOPOR"));
                apontamento.setAssiduidade(rs.getBoolean("A.ASSIDUIDADE"));
                apontamento.setPontoAviso(rs.getBoolean("A.PONTO"));
                apontamento.setCompetencia(rs.getInt("A.COMPETENCIA"));
                apontamento.setOrdemServico(os);
                apontamento.setStatusApont(s);
                funcionario.setFuncao(funcao);
                apontamento.setFuncionario(funcionario);
                apontamento.setCentroCusto(centroCusto);
                apontamento.setSituacao(si);
                apontamento.setLider(pessoa);
                apontamento.setGerente(pg);
                apontamentos.add(apontamento);
            }

            return apontamentos;

        } catch (SQLException se) {
            throw new SQLException("Erro ao filtrar apontamentos! " + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }
    }//fecha buscar

    public ArrayList<Apontamento> filtrarApontOS(String query) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "select\n"
                    + "	A.CODAPONT, A.INTEGRA, A.CHAPA, F.NOME, A.DATA, A.CODSTATUSAPONT,\n"
                    + "    A.CODCCUSTO, C.NOME, A.CODOS, P.CODPESSOA, P.NOME, A.CODLIDER, A.ATIVIDADE, A.CODSITUACAO\n"
                    + "from \n"
                    + "	APONTAMENTOS A\n"
                    + "INNER JOIN\n"
                    + "	PFUNC F\n"
                    + "ON\n"
                    + "	A.CHAPA = F.CHAPA\n"
                    + "INNER JOIN\n"
                    + "	PESSOA P\n"
                    + "ON\n"
                    + "	P.CODPESSOA = A.CODLIDER\n"
                    + "INNER JOIN GCCUSTO C\n"
                    + "ON\n"
                    + " C.CODCUSTO = A.CODCCUSTO " + query;
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Apontamento> aa = new ArrayList<>();
            while (rs.next()) {
                Apontamento a = new Apontamento();
                Funcionario f = new Funcionario();
                CentroCusto c = new CentroCusto();
                OrdemServico os = new OrdemServico();
                Pessoa p = new Pessoa();
                Situacao s = new Situacao();
                StatusApont sa = new StatusApont();

                a.setCodApont(rs.getInt("A.CODAPONT"));
                a.setIntegra(rs.getBoolean("A.INTEGRA"));
                f.setChapa(rs.getString("A.CHAPA"));
                f.setNome(rs.getString("F.NOME"));
                a.setData(rs.getTimestamp("A.DATA").toLocalDateTime());
                sa.setCodStatusApont(rs.getString("A.CODSTATUSAPONT"));
                c.setCodCusto(rs.getString("A.CODCCUSTO"));
                c.setNome(rs.getString("C.NOME"));
                os.setCodOs(rs.getInt("A.CODOS"));
                p.setCodPessoa(rs.getInt("P.CODPESSOA"));
                p.setNome(rs.getString("P.NOME"));
                s.setCodSituacao(rs.getString("A.CODSITUACAO").charAt(0));

                a.setStatusApont(sa);
                a.setSituacao(s);
                a.setLider(p);
                a.setOrdemServico(os);
                a.setCentroCusto(c);
                a.setFuncionario(f);

                aa.add(a);
            }

            return aa;
        } catch (SQLException se) {
            throw new SQLException("Erro ao filtrar apontamentos! " + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }
    }//fecha buscarPorChapa

    //Carrega apontamentos da OS
    public ArrayList<Apontamento> buscarPorOS(int codOs) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<Apontamento> aa = new ArrayList<>();

        try {
            String sql = "select\n"
                    + "	A.CODAPONT, A.INTEGRA, A.CHAPA, F.NOME, A.DATA, A.CODSTATUSAPONT,\n"
                    + "    A.CODCCUSTO, C.NOME, A.CODOS, P.CODPESSOA, P.NOME, A.CODLIDER, A.ATIVIDADE, A.CODSITUACAO\n"
                    + "from \n"
                    + "	APONTAMENTOS A\n"
                    + "INNER JOIN\n"
                    + "	PFUNC F\n"
                    + "ON\n"
                    + "	A.CHAPA = F.CHAPA\n"
                    + "INNER JOIN\n"
                    + "	PESSOA P\n"
                    + "ON\n"
                    + "	P.CODPESSOA = A.CODLIDER\n"
                    + "INNER JOIN\n"
                    + " GCCUSTO C\n"
                    + "ON\n"
                    + " C.CODCUSTO = A.CODCCUSTO\n"
                    + "INNER JOIN\n"
                    + "	ORDEMSERVICO OS\n"
                    + "ON\n"
                    + "	OS.CODOS = A.CODOS\n"
                    + "WHERE\n"
                    + "	OS.CODOS = ? ORDER BY DATA DESC;";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, codOs);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Apontamento a = new Apontamento();
                Funcionario f = new Funcionario();
                CentroCusto c = new CentroCusto();
                OrdemServico os = new OrdemServico();
                Pessoa p = new Pessoa();
                Situacao s = new Situacao();
                StatusApont sa = new StatusApont();

                a.setCodApont(rs.getInt("A.CODAPONT"));
                a.setIntegra(rs.getBoolean("A.INTEGRA"));
                f.setChapa(rs.getString("A.CHAPA"));
                f.setNome(rs.getString("F.NOME"));
                a.setData(rs.getTimestamp("A.DATA").toLocalDateTime());
                sa.setCodStatusApont(rs.getString("A.CODSTATUSAPONT"));
                c.setCodCusto(rs.getString("A.CODCCUSTO"));
                c.setNome(rs.getString("C.NOME"));
                os.setCodOs(rs.getInt("A.CODOS"));
                p.setCodPessoa(rs.getInt("P.CODPESSOA"));
                p.setNome(rs.getString("P.NOME"));
                s.setCodSituacao(rs.getString("A.CODSITUACAO").charAt(0));

                a.setStatusApont(sa);
                a.setSituacao(s);
                a.setLider(p);
                a.setOrdemServico(os);
                a.setCentroCusto(c);
                a.setFuncionario(f);

                aa.add(a);
            }
            return aa;
        } catch (SQLException se) {
            throw new SQLException("Erro ao carregar apontamentos da OS! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }
    }

    public Apontamento alterar(Apontamento a) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql;
            sql = "update APONTAMENTOS set"
                    + " CODSTATUSAPONT = ?, CODCCUSTO = ?, CODOS = ?,"
                    + " CODLIDER = ?, ATIVIDADE = ?, INTEGRA = 0, MODIFICADOPOR = ?"
                    + " where CODAPONT = ?";

            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, String.valueOf(a.getStatusApont().getCodStatusApont()));
            pstmt.setString(2, a.getCentroCusto().getCodCusto());
            if (a.getOrdemServico().getCodOs() == 0) {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(3, a.getOrdemServico().getCodOs());
            }
            pstmt.setInt(4, a.getLider().getCodPessoa());
            pstmt.setString(5, a.getAtividade());
            pstmt.setString(6, Menu.logado.getLogin());
            pstmt.setInt(7, a.getCodApont());

            pstmt.executeUpdate();

            return a;
        } catch (SQLException se) {
            throw new SQLException("Erro ao alterar apontamento! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }

    }

    public void atualizar(Apontamento a) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql;
            sql = "update APONTAMENTOS set"
                    + " CODSTATUSAPONT = ?, CODCCUSTO = ?, CODLIDER = ?,"
                    + " ATIVIDADE = ?, INTEGRA = 0, MODIFICADOPOR = ?"
                    + " where CODAPONT = ?";

            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, String.valueOf(a.getStatusApont().getCodStatusApont()));
            pstmt.setString(2, a.getCentroCusto().getCodCusto());
            pstmt.setInt(3, a.getLider().getCodPessoa());
            pstmt.setString(4, a.getAtividade());
            pstmt.setString(5, Menu.logado.getLogin());
            pstmt.setInt(6, a.getCodApont());

            pstmt.executeUpdate();
        } catch (SQLException se) {
            throw new SQLException("Erro ao atualizar apontamento! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }
    }

    public void updateComentado(Apontamento apontamento, boolean seComentado) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql;
            sql = "update APONTAMENTOS set"
                    + " COMENTADO = ?"
                    + " where CODAPONT = ?";

            pstmt = con.prepareStatement(sql);

            pstmt.setBoolean(1, seComentado);
            pstmt.setInt(2, apontamento.getCodApont());

            pstmt.executeUpdate();
        } catch (SQLException se) {
            throw new SQLException("Erro ao atualizar coluna comentado! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }
    }//updateComentado

    public ArrayList<Apontamento> buscarApontamentosDaOs(int codOs) throws SQLException {
        Connection con = ConexaoBanco.getConexao();

        String sql = "select\n"
                + "	A.CODAPONT, A.INTEGRA, A.CHAPA, F.NOME, A.DATA, A.CODSTATUSAPONT,\n"
                + "    A.CODCCUSTO, C.NOME, A.CODOS, P.CODPESSOA, P.NOME, A.CODLIDER, A.ATIVIDADE, A.CODSITUACAO, A.CUSTOMO\n"
                + "from \n"
                + "	APONTAMENTOS A\n"
                + "INNER JOIN\n"
                + "	PFUNC F\n"
                + "ON\n"
                + "	A.CHAPA = F.CHAPA\n"
                + "INNER JOIN\n"
                + "	PESSOA P\n"
                + "ON\n"
                + "	P.CODPESSOA = A.CODLIDER\n"
                + "INNER JOIN\n"
                + " GCCUSTO C\n"
                + "ON\n"
                + " C.CODCUSTO = A.CODCCUSTO\n"
                + "INNER JOIN\n"
                + "	ORDEMSERVICO OS\n"
                + "ON\n"
                + "	OS.CODOS = A.CODOS\n"
                + "WHERE\n"
                + "	OS.CODOS = ? ORDER BY DATA DESC;";

        PreparedStatement pstmt = con.prepareStatement(sql);
        try {
            pstmt.setInt(1, codOs);

            ResultSet rs = pstmt.executeQuery();
            ArrayList<Apontamento> apontamentosDaOs = new ArrayList<>();

            while (rs.next()) {
                Apontamento a = new Apontamento();
                Funcionario f = new Funcionario();
                CentroCusto c = new CentroCusto();
                OrdemServico os = new OrdemServico();
                Pessoa p = new Pessoa();
                Situacao s = new Situacao();
                StatusApont sa = new StatusApont();

                a.setCodApont(rs.getInt("A.CODAPONT"));
                a.setIntegra(rs.getBoolean("A.INTEGRA"));
                f.setChapa(rs.getString("A.CHAPA"));
                f.setNome(rs.getString("F.NOME"));
                a.setData(rs.getTimestamp("A.DATA").toLocalDateTime());
                a.setCustoMaoDeObra(rs.getDouble("A.CUSTOMO"));
                sa.setCodStatusApont(rs.getString("A.CODSTATUSAPONT"));
                c.setCodCusto(rs.getString("A.CODCCUSTO"));
                c.setNome(rs.getString("C.NOME"));
                os.setCodOs(rs.getInt("A.CODOS"));
                p.setCodPessoa(rs.getInt("P.CODPESSOA"));
                p.setNome(rs.getString("P.NOME"));
                s.setCodSituacao(rs.getString("A.CODSITUACAO").charAt(0));

                a.setStatusApont(sa);
                a.setSituacao(s);
                a.setLider(p);
                a.setOrdemServico(os);
                a.setCentroCusto(c);
                a.setFuncionario(f);

                apontamentosDaOs.add(a);
            }
            return apontamentosDaOs;
        } catch (SQLException se) {
            throw new SQLException("Erro ao carregar apontamentos da OS! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }
    }//fecha buscarApontamentosDaOs

    public ArrayList<Apontamento> buscarDadosRelatorioTomadores(String query) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "SELECT \n"
                    + "	DADOS.CHAPA, DADOS.NOME, DADOS.CC, DADOS.CNO, DADOS.DIAS, (DADOS.DIAS / DIAS_TOTAL.DIAS_TOTAL) * 100 AS PERC\n"
                    + "FROM (SELECT \n"
                    + "	PFUNC.CHAPA, PFUNC.NOME, GCCUSTO.NOME AS CC, GCCUSTO.CODREDUZIDO AS CNO, COUNT(APONTAMENTOS.CODSTATUSAPONT) AS DIAS\n"
                    + "FROM \n"
                    + "	APONTAMENTOS, PFUNC, GCCUSTO\n"
                    + "WHERE\n"
                    + "	APONTAMENTOS.CHAPA = PFUNC.CHAPA\n"
                    + "    AND PFUNC.CODCOLIGADA = 1\n"
                    + "    AND APONTAMENTOS.CODCCUSTO = GCCUSTO.CODCUSTO\n"
                    + "    AND APONTAMENTOS.CODSTATUSAPONT <> '-'\n"
                    + query
                    + " GROUP BY \n"
                    + "	PFUNC.CHAPA, PFUNC.NOME, GCCUSTO.NOME, GCCUSTO.CODREDUZIDO) AS DADOS,\n"
                    + "(SELECT \n"
                    + "	PFUNC.CHAPA, COUNT(APONTAMENTOS.CODSTATUSAPONT) AS DIAS_TOTAL\n"
                    + "FROM \n"
                    + "	APONTAMENTOS, PFUNC\n"
                    + "WHERE\n"
                    + "	APONTAMENTOS.CHAPA = PFUNC.CHAPA\n"
                    + "    AND PFUNC.CODCOLIGADA = 1\n"
                    + "    AND APONTAMENTOS.CODSTATUSAPONT <> '-'\n"
                    + query
                    + " GROUP BY \n"
                    + "	PFUNC.CHAPA) AS DIAS_TOTAL\n"
                    + "WHERE\n"
                    + "	DADOS.CHAPA = DIAS_TOTAL.CHAPA\n"
                    + "ORDER BY CHAPA";

            ResultSet rs = stat.executeQuery(sql);

            ArrayList<Apontamento> apontamentos = new ArrayList<>();

            while (rs.next()) {

                Apontamento apontamento = new Apontamento();
                Funcionario funcionario = new Funcionario();
                CentroCusto custo = new CentroCusto();

                funcionario.setChapa(rs.getString("DADOS.CHAPA"));
                funcionario.setNome(rs.getString("DADOS.NOME"));

                custo.setNome(rs.getString("DADOS.CC"));
                custo.setCodReduzido(rs.getString("DADOS.CNO"));

                apontamento.setQuantidadeDias(rs.getInt("DADOS.DIAS"));
                apontamento.setPorcentagemDeDiasTrabalhados(rs.getDouble("PERC"));
                apontamento.setFuncionario(funcionario);
                apontamento.setCentroCusto(custo);

                apontamentos.add(apontamento);

            }

            return apontamentos;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar dados do relatório de tomadores! " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }
    }

    public List<Apontamento> buscarPontos(String query) throws SQLException {
        Connection con = ConexaoBanco.getConexao();

        try {
            String sql = "SELECT APONTAMENTOS.CODAPONT, PFUNC.CHAPA, PFUNC.NOME, APONTAMENTOS.DATA,"
                    + " APONTAMENTOS.VERIFICADO, APONTAMENTOS.PROBLEMA, APONTAMENTOS.MOTIVO_PROBLEMA,"
                    + " APONTAMENTOS.JUSTIFICATIVA, APONTAMENTOS.COMPETENCIA, APONTAMENTOS.CODSTATUSAPONT, STATUSAPONT.DESCRICAO,"
                    + " APONTAMENTOS.CODCCUSTO, GCCUSTO.NOME, APONTAMENTOS.CODLIDER, PESSOA.NOME, APONTAMENTOS.DATA_HORA_MOTIVO,"
                    + " APONTAMENTOS.DATA_HORA_JUSTIFICATIVA, APONTAMENTOS.AJUSTADO"
                    + " FROM APONTAMENTOS"
                    + " INNER JOIN PFUNC ON APONTAMENTOS.CHAPA = PFUNC.CHAPA"
                    + " INNER JOIN GCCUSTO ON APONTAMENTOS.CODCCUSTO = GCCUSTO.CODCUSTO"
                    + " INNER JOIN PESSOA ON APONTAMENTOS.CODLIDER = PESSOA.CODPESSOA"
                    + " INNER JOIN STATUSAPONT ON APONTAMENTOS.CODSTATUSAPONT = STATUSAPONT.CODSTATUSAPONT"
                    + " WHERE APONTAMENTOS.CODAPONT > 0 " + query + " ORDER BY APONTAMENTOS.DATA, PFUNC.NOME";

            PreparedStatement prepareStatement = con.prepareStatement(sql);
            ResultSet rs = prepareStatement.executeQuery();

            ArrayList<Apontamento> apontamentos = new ArrayList<>();

            while (rs.next()) {

                Apontamento apontamento = new Apontamento();
                Funcionario funcionario = new Funcionario();
                CentroCusto centroCusto = new CentroCusto();
                Pessoa lider = new Pessoa();
                StatusApont status = new StatusApont();

                funcionario.setChapa(rs.getString("PFUNC.CHAPA"));
                funcionario.setNome(rs.getString("PFUNC.NOME"));
                apontamento.setCodApont(rs.getInt("APONTAMENTOS.CODAPONT"));
                apontamento.setData(rs.getTimestamp("APONTAMENTOS.DATA").toLocalDateTime());
                apontamento.setVerificado(rs.getBoolean("APONTAMENTOS.VERIFICADO"));
                apontamento.setProblema(rs.getBoolean("APONTAMENTOS.PROBLEMA"));
                apontamento.setAjustado(rs.getBoolean("APONTAMENTOS.AJUSTADO"));
                apontamento.setDataHoraMotivo((LocalDateTime) rs.getObject("APONTAMENTOS.DATA_HORA_MOTIVO"));
                apontamento.setMotivo(rs.getString("APONTAMENTOS.MOTIVO_PROBLEMA"));
                apontamento.setDataHoraJustificativa((LocalDateTime) rs.getObject("APONTAMENTOS.DATA_HORA_JUSTIFICATIVA"));
                apontamento.setJustificativa(rs.getString("APONTAMENTOS.JUSTIFICATIVA"));
                apontamento.setCompetencia(rs.getInt("APONTAMENTOS.COMPETENCIA"));
                centroCusto.setCodCusto(rs.getString("APONTAMENTOS.CODCCUSTO"));
                centroCusto.setNome(rs.getString("GCCUSTO.NOME"));
                lider.setCodPessoa(rs.getInt("APONTAMENTOS.CODLIDER"));
                lider.setNome(rs.getString("PESSOA.NOME"));
                status.setCodStatusApont(rs.getString("APONTAMENTOS.CODSTATUSAPONT"));
                status.setDescricao(rs.getString("STATUSAPONT.DESCRICAO"));
                apontamento.setCentroCusto(centroCusto);
                apontamento.setFuncionario(funcionario);
                apontamento.setLider(lider);
                apontamento.setStatusApont(status);

                apontamentos.add(apontamento);

            }
            
            return apontamentos;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar dados do apontamento! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public List<Apontamento> buscarPontosPorId(int id) throws SQLException {
        Connection con = ConexaoBanco.getConexao();

        try {
            String sql = "SELECT APONTAMENTOS.CODAPONT, PFUNC.CHAPA, PFUNC.NOME, APONTAMENTOS.DATA,"
                    + " APONTAMENTOS.VERIFICADO, APONTAMENTOS.PROBLEMA, APONTAMENTOS.MOTIVO_PROBLEMA,"
                    + " APONTAMENTOS.JUSTIFICATIVA, APONTAMENTOS.COMPETENCIA,"
                    + " APONTAMENTOS.CODCCUSTO, GCCUSTO.NOME, APONTAMENTOS.CODLIDER, PESSOA.NOME"
                    + " FROM APONTAMENTOS"
                    + " INNER JOIN PFUNC ON APONTAMENTOS.CHAPA = PFUNC.CHAPA"
                    + " INNER JOIN GCCUSTO ON APONTAMENTOS.CODCCUSTO = GCCUSTO.CODCUSTO"
                    + " INNER JOIN PESSOA ON APONTAMENTOS.CODLIDER = PESSOA.CODPESSOA"
                    + " WHERE APONTAMENTOS.CODAPONT = " + id + " ORDER BY APONTAMENTOS.DATA, PFUNC.NOME";

            PreparedStatement prepareStatement = con.prepareStatement(sql);
            ResultSet rs = prepareStatement.executeQuery();

            ArrayList<Apontamento> apontamentos = new ArrayList<>();

            while (rs.next()) {

                Apontamento apontamento = new Apontamento();
                Funcionario funcionario = new Funcionario();
                CentroCusto centroCusto = new CentroCusto();
                Pessoa lider = new Pessoa();

                funcionario.setChapa(rs.getString("PFUNC.CHAPA"));
                funcionario.setNome(rs.getString("PFUNC.NOME"));
                apontamento.setCodApont(rs.getInt("APONTAMENTOS.CODAPONT"));
                apontamento.setData(rs.getTimestamp("APONTAMENTOS.DATA").toLocalDateTime());
                apontamento.setVerificado(rs.getBoolean("APONTAMENTOS.VERIFICADO"));
                apontamento.setProblema(rs.getBoolean("APONTAMENTOS.PROBLEMA"));
                apontamento.setMotivo(rs.getString("APONTAMENTOS.MOTIVO_PROBLEMA"));
                apontamento.setJustificativa(rs.getString("APONTAMENTOS.JUSTIFICATIVA"));
                apontamento.setCompetencia(rs.getInt("APONTAMENTOS.COMPETENCIA"));
                centroCusto.setCodCusto(rs.getString("APONTAMENTOS.CODCCUSTO"));
                centroCusto.setNome(rs.getString("GCCUSTO.NOME"));
                lider.setCodPessoa(rs.getInt("APONTAMENTOS.CODLIDER"));
                lider.setNome(rs.getString("PESSOA.NOME"));
                apontamento.setCentroCusto(centroCusto);
                apontamento.setFuncionario(funcionario);
                apontamento.setLider(lider);

                apontamentos.add(apontamento);

            }

            return apontamentos;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar dados do apontamento! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public List<Apontamento> buscarPontosPorNotificacoesNaoLidas() throws SQLException {
        Connection con = ConexaoBanco.getConexao();

        try {
            String sql = "SELECT APONTAMENTOS.CODAPONT, PFUNC.CHAPA, PFUNC.NOME, APONTAMENTOS.DATA,"
                    + " APONTAMENTOS.VERIFICADO, APONTAMENTOS.PROBLEMA, APONTAMENTOS.MOTIVO_PROBLEMA,"
                    + " APONTAMENTOS.JUSTIFICATIVA, APONTAMENTOS.COMPETENCIA,"
                    + " APONTAMENTOS.CODCCUSTO, GCCUSTO.NOME, APONTAMENTOS.CODLIDER, PESSOA.NOME"
                    + " FROM APONTAMENTOS"
                    + " INNER JOIN PFUNC ON APONTAMENTOS.CHAPA = PFUNC.CHAPA"
                    + " INNER JOIN GCCUSTO ON APONTAMENTOS.CODCCUSTO = GCCUSTO.CODCUSTO"
                    + " INNER JOIN PESSOA ON APONTAMENTOS.CODLIDER = PESSOA.CODPESSOA"
                    + " LEFT JOIN NOTIFICACOES ON APONTAMENTOS.CODAPONT = NOTIFICACOES.APONTAMENTO_ID"
                    + " WHERE NOTIFICACOES.LIDO = FALSE ORDER BY APONTAMENTOS.DATA, PFUNC.NOME";

            PreparedStatement prepareStatement = con.prepareStatement(sql);
            ResultSet rs = prepareStatement.executeQuery();

            ArrayList<Apontamento> apontamentos = new ArrayList<>();

            while (rs.next()) {

                Apontamento apontamento = new Apontamento();
                Funcionario funcionario = new Funcionario();
                CentroCusto centroCusto = new CentroCusto();
                Pessoa lider = new Pessoa();

                funcionario.setChapa(rs.getString("PFUNC.CHAPA"));
                funcionario.setNome(rs.getString("PFUNC.NOME"));
                apontamento.setCodApont(rs.getInt("APONTAMENTOS.CODAPONT"));
                apontamento.setData(rs.getTimestamp("APONTAMENTOS.DATA").toLocalDateTime());
                apontamento.setVerificado(rs.getBoolean("APONTAMENTOS.VERIFICADO"));
                apontamento.setProblema(rs.getBoolean("APONTAMENTOS.PROBLEMA"));
                apontamento.setMotivo(rs.getString("APONTAMENTOS.MOTIVO_PROBLEMA"));
                apontamento.setJustificativa(rs.getString("APONTAMENTOS.JUSTIFICATIVA"));
                apontamento.setCompetencia(rs.getInt("APONTAMENTOS.COMPETENCIA"));
                centroCusto.setCodCusto(rs.getString("APONTAMENTOS.CODCCUSTO"));
                centroCusto.setNome(rs.getString("GCCUSTO.NOME"));
                lider.setCodPessoa(rs.getInt("APONTAMENTOS.CODLIDER"));
                lider.setNome(rs.getString("PESSOA.NOME"));
                apontamento.setCentroCusto(centroCusto);
                apontamento.setFuncionario(funcionario);
                apontamento.setLider(lider);

                apontamentos.add(apontamento);

            }

            return apontamentos;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar dados do apontamento! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public List<Apontamento> buscarApontamentosComProblema(Apontamento apont) throws SQLException {
        Connection con = ConexaoBanco.getConexao();

        try {
            String sql = "SELECT CODAPONT FROM APONTAMENTOS"
                    + " WHERE CHAPA = ?"
                    + " AND COMPETENCIA = ?"
                    + " AND PROBLEMA = TRUE";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, apont.getFuncionario().getChapa());
            preparedStatement.setInt(2, apont.getCompetencia());
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Apontamento> apontamentos = new ArrayList<>();

            while (rs.next()) {

                Apontamento apontamento = new Apontamento();

                apontamento.setCodApont(rs.getInt("CODAPONT"));

                apontamentos.add(apontamento);

            }

            return apontamentos;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar dados do apontamento! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public List<Apontamento> buscarApontamentosComProblemaESemJustificativa(Apontamento apont) throws SQLException {
        Connection con = ConexaoBanco.getConexao();

        try {
            String sql = "SELECT CODAPONT FROM APONTAMENTOS"
                    + " WHERE CHAPA = ?"
                    + " AND COMPETENCIA = ?"
                    + " AND PROBLEMA = TRUE"
                    + " AND JUSTIFICATIVA IS NULL";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, apont.getFuncionario().getChapa());
            preparedStatement.setInt(2, apont.getCompetencia());
            ResultSet rs = preparedStatement.executeQuery();

            ArrayList<Apontamento> apontamentos = new ArrayList<>();

            while (rs.next()) {

                Apontamento apontamento = new Apontamento();

                apontamento.setCodApont(rs.getInt("CODAPONT"));

                apontamentos.add(apontamento);

            }

            return apontamentos;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar dados do apontamento! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public void verificar(Apontamento apontamento) throws SQLException {
        Connection con = ConexaoBanco.getConexao();

        try {
            String sql = "UPDATE APONTAMENTOS SET VERIFICADO = ?, PROBLEMA = ?,"
                    + " MOTIVO_PROBLEMA = ?, JUSTIFICATIVA = ?"
                    + " WHERE CODAPONT = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setBoolean(1, apontamento.isVerificado());
            preparedStatement.setBoolean(2, apontamento.isProblema());
            preparedStatement.setString(3, apontamento.getMotivo());
            preparedStatement.setString(4, apontamento.getJustificativa());
            preparedStatement.setInt(5, apontamento.getCodApont());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException se) {
            throw new SQLException("Erro ao verificar! " + se.getMessage());
        } finally {
            con.close();
        }
    }

    public void verificarAssiduidade(Apontamento apontamento, boolean assiduidade) throws SQLException {
        Connection con = ConexaoBanco.getConexao();

        try {
            String sql = "UPDATE APONTAMENTOS SET ASSIDUIDADE = ?"
                    + " WHERE CHAPA = ?"
                    + " AND COMPETENCIA = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setBoolean(1, assiduidade);
            preparedStatement.setString(2, apontamento.getFuncionario().getChapa());
            preparedStatement.setInt(3, apontamento.getCompetencia());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException se) {
            throw new SQLException("Erro ao verificar assiduidade! " + se.getMessage());
        } finally {
            con.close();
        }
    }

    public void verificarPontoAviso(Apontamento apontamento, boolean ponto) throws SQLException {
        Connection con = ConexaoBanco.getConexao();

        try {
            String sql = "UPDATE APONTAMENTOS SET PONTO = ?"
                    + " WHERE CHAPA = ?"
                    + " AND COMPETENCIA = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setBoolean(1, ponto);
            preparedStatement.setString(2, apontamento.getFuncionario().getChapa());
            preparedStatement.setInt(3, apontamento.getCompetencia());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException se) {
            throw new SQLException("Erro ao verificar ponto! " + se.getMessage());
        } finally {
            con.close();
        }
    }

    public void registrarDataEHoraMotivo(int codApont) throws SQLException {
        Connection con = ConexaoBanco.getConexao();

        try {
            String sql = "UPDATE APONTAMENTOS SET DATA_HORA_MOTIVO = NOW() WHERE CODAPONT = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, codApont);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException se) {
            throw new SQLException("Erro ao registrar data e hora do motivo! " + se.getMessage());
        } finally {
            con.close();
        }
    }

    public void registrarDataEHoraJustificativa(int codApont) throws SQLException {
        Connection con = ConexaoBanco.getConexao();

        try {
            String sql = "UPDATE APONTAMENTOS SET DATA_HORA_JUSTIFICATIVA = NOW() WHERE CODAPONT = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, codApont);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException se) {
            throw new SQLException("Erro ao registrar data e hora da justificativa! " + se.getMessage());
        } finally {
            con.close();
        }
    }

    public Apontamento buscarPontoPorId(int id) throws SQLException {
        Connection con = ConexaoBanco.getConexao();

        try {
            String sql = "SELECT APONTAMENTOS.CODAPONT, PFUNC.CHAPA, PFUNC.NOME, APONTAMENTOS.DATA,"
                    + " APONTAMENTOS.VERIFICADO, APONTAMENTOS.PROBLEMA, APONTAMENTOS.MOTIVO_PROBLEMA,"
                    + " APONTAMENTOS.JUSTIFICATIVA, APONTAMENTOS.COMPETENCIA,"
                    + " APONTAMENTOS.DATA_HORA_MOTIVO, APONTAMENTOS.DATA_HORA_JUSTIFICATIVA,"
                    + " APONTAMENTOS.CODCCUSTO, GCCUSTO.NOME, APONTAMENTOS.CODLIDER, PESSOA.NOME"
                    + " FROM APONTAMENTOS"
                    + " INNER JOIN PFUNC ON APONTAMENTOS.CHAPA = PFUNC.CHAPA"
                    + " INNER JOIN GCCUSTO ON APONTAMENTOS.CODCCUSTO = GCCUSTO.CODCUSTO"
                    + " INNER JOIN PESSOA ON APONTAMENTOS.CODLIDER = PESSOA.CODPESSOA"
                    + " WHERE APONTAMENTOS.CODAPONT = " + id + " ORDER BY APONTAMENTOS.DATA, PFUNC.NOME";

            PreparedStatement prepareStatement = con.prepareStatement(sql);
            ResultSet rs = prepareStatement.executeQuery();

            if (rs.next()) {

                Apontamento apontamento = new Apontamento();
                Funcionario funcionario = new Funcionario();
                CentroCusto centroCusto = new CentroCusto();
                Pessoa lider = new Pessoa();

                funcionario.setChapa(rs.getString("PFUNC.CHAPA"));
                funcionario.setNome(rs.getString("PFUNC.NOME"));
                apontamento.setCodApont(rs.getInt("APONTAMENTOS.CODAPONT"));
                apontamento.setData(rs.getTimestamp("APONTAMENTOS.DATA").toLocalDateTime());
                apontamento.setVerificado(rs.getBoolean("APONTAMENTOS.VERIFICADO"));
                apontamento.setProblema(rs.getBoolean("APONTAMENTOS.PROBLEMA"));
                apontamento.setMotivo(rs.getString("APONTAMENTOS.MOTIVO_PROBLEMA"));
                apontamento.setJustificativa(rs.getString("APONTAMENTOS.JUSTIFICATIVA"));
                apontamento.setCompetencia(rs.getInt("APONTAMENTOS.COMPETENCIA"));
                apontamento.setDataHoraMotivo((LocalDateTime) rs.getObject("APONTAMENTOS.DATA_HORA_MOTIVO"));
                apontamento.setDataHoraJustificativa((LocalDateTime) rs.getObject("APONTAMENTOS.DATA_HORA_JUSTIFICATIVA"));
                centroCusto.setCodCusto(rs.getString("APONTAMENTOS.CODCCUSTO"));
                centroCusto.setNome(rs.getString("GCCUSTO.NOME"));
                lider.setCodPessoa(rs.getInt("APONTAMENTOS.CODLIDER"));
                lider.setNome(rs.getString("PESSOA.NOME"));
                apontamento.setCentroCusto(centroCusto);
                apontamento.setFuncionario(funcionario);
                apontamento.setLider(lider);
                return apontamento;
            }
            
            return null;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar dados do apontamento! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public void editarApontamentoPonto(Apontamento apontamento) throws SQLException {
        Connection con = ConexaoBanco.getConexao();

        try {
            String sql = "UPDATE APONTAMENTOS SET AJUSTADO = ?"
                    + " WHERE CODAPONT = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setBoolean(1, apontamento.isAjustado());
            System.out.println(apontamento.isAjustado());
            preparedStatement.setInt(2, apontamento.getCodApont());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException se) {
            throw new SQLException("Erro ao editar apontamento ponto! " + se.getMessage());
        } finally {
            con.close();
        }
    }
}
