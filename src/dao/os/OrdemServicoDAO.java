/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.os;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import model.os.Adicional;
import model.os.CentroCusto;
import model.os.Cidade;
import model.os.Cliente;
import model.os.Estado;
import model.os.MotivoPerda;
import model.os.OrdemServico;
import model.os.Pessoa;
import model.os.Projeto;
import model.os.Segmento;
import model.os.Status;
import model.os.TipoOs;
import model.os.Venda;
import persistencia.ConexaoBanco;
import utilitarios.FormatarData;

/**
 *
 * @author guilherme.oliveira
 */
public class OrdemServicoDAO {

    public int cadastrarOrdemServico(OrdemServico os) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "insert into ORDEMSERVICO(CODOS, CODTIPOOS, CODCCUSTO, OBRA, DATASOLICITACAO,"
                    + " DATANECESSIDADE, DATAINICIO, DATAPREVENTREGA,"
                    + " DATAENTREGA, CODSTATUS, NOME,"
                    + " PRIORIDADE, SOLICITANTE, RESPONSAVEL,"
                    + " FK_CODCLIENTE, FK_CODCOLIGADA, VALORFATDIRETO, VALORFATDOLPHIN, VALOR_COMISSAO, CODSEGMENTO,"
                    + " CODCIDADE, ID_PROJETO, ID_ADICIONAL, DATAINTERACAO, PRINCIPAL, id_motivo_perdido, observacoes)"
                    + " values"
                    + "(null, ?, ?, ?, ?,"
                    + " ?, ?, ?,"
                    + " ?, ?, ?,"
                    + " ?, ?, ?,"
                    + " ?, ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?, ?, ?)";

            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, os.getTipoOs().getCodTipoOs());
            pstmt.setString(2, os.getCentroCusto().getCodCusto());
            pstmt.setString(3, os.getObra());
            pstmt.setObject(4, os.getDataSolicitacao());

            pstmt.setObject(5, os.getDataNecessidade());
            pstmt.setObject(6, os.getDataInicio());
            pstmt.setObject(7, os.getDataPrevEntrega());

            pstmt.setObject(8, os.getDataEntrega());
            pstmt.setInt(9, os.getStatus().getCodStatus());
            pstmt.setString(10, os.getNome());

            pstmt.setInt(11, os.getPrioridade());
            pstmt.setInt(12, os.getSolicitante().getCodPessoa());
            pstmt.setInt(13, os.getResponsavel().getCodPessoa());

            if (os.getCliente() != null) {
                pstmt.setString(14, os.getCliente().getCodCliente());
            } else {
                pstmt.setString(14, "-");
            }
            if (os.getCliente() != null) {
                pstmt.setInt(15, os.getCliente().getCodColigada());
            } else {
                pstmt.setInt(15, 0);
            }
            pstmt.setDouble(16, os.getVenda().getValorFaturamentoDireto());
            pstmt.setDouble(17, os.getVenda().getValorFaturamentoDolphin());
            pstmt.setDouble(18, os.getVenda().getValorComissao());

            pstmt.setInt(19, os.getSegmento().getCodSegmento());
            pstmt.setInt(20, os.getCidade().getId());
            pstmt.setInt(21, os.getProjeto().getId());
            if (os.getAdicional() != null) {
                pstmt.setInt(22, os.getAdicional().getId());
            } else {
                pstmt.setObject(22, null);
            }
            pstmt.setObject(23, os.getDataInteracao());
            pstmt.setBoolean(24, os.isPrincipal());

            if (os.getMotivo() != null) {
                pstmt.setInt(25, os.getMotivo().getId());
            } else {
                pstmt.setInt(25, 1);
            }
            pstmt.setString(26, os.getObservacoes());

            pstmt.execute();

            int id = 0;
            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            return id;

        } catch (SQLException se) {
            throw new SQLException("Erro ao cadastrar Ordem de servico! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha cadastrar

    public ArrayList<OrdemServico> filtrarOrdemServico(String query) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        try {
            String sql = "select OS.CODOS, OS.NOME, C.CODCUSTO, C.NOME, OS.OBRA,"
                    + " OS.DATASOLICITACAO, OS.DATANECESSIDADE, OS.DOCREFERENCIA,"
                    + " OS.DATAINICIO, OS.DATAPREVENTREGA,"
                    + " OS.DATAENTREGA, OS.PRIORIDADE,"
                    + " S.CODSTATUS, S.NOME, S.ACAO,"
                    + " T.CODTIPOOS, T.DESCRICAO, P.CODPESSOA, P.NOME, PE.CODPESSOA,"
                    + " PE.NOME, PG.CODPESSOA, PG.NOME,"
                    + " CLI.CODCLIENTE, CLI.CODCOLIGADA, CLI.NOMEFANTASIA, CLI.INTERACAO_FONE, CLI.INTERACAO_MSG, CLI.INTERACAO_REUNIAO,"
                    + " OS.VALORFATDIRETO, OS.VALORFATDOLPHIN, OS.VALOR_COMISSAO, OS.CODSEGMENTO, SEGMENTO.CODSEGMENTO,"
                    + " SEGMENTO.DESCRICAO, OS.CODCIDADE, CIDADE.NOME, CIDADE.ESTADO, ESTADO.NOME,"
                    + " ESTADO.UF, OS.ID_PROJETO, OS.ID_ADICIONAL, A.NUMERO, OS.DATAINTERACAO, OS.PRINCIPAL, OS.id_motivo_perdido, MP.nome, OS.observacoes \n"
                    + "from \n"
                    + "ORDEMSERVICO OS \n"
                    + "INNER JOIN \n"
                    + "	TIPOOS T \n"
                    + "ON \n"
                    + "	OS.CODTIPOOS = T.CODTIPOOS\n"
                    + "INNER JOIN \n"
                    + "	PESSOA P\n"
                    + "ON\n"
                    + "	P.CODPESSOA = OS.SOLICITANTE\n"
                    + "INNER JOIN\n"
                    + "	PESSOA PE\n"
                    + "ON\n"
                    + "	PE.CODPESSOA = OS.RESPONSAVEL\n"
                    + "INNER JOIN \n"
                    + "	GCCUSTO C\n"
                    + "ON\n"
                    + "	OS.CODCCUSTO = C.CODCUSTO\n"
                    + "LEFT JOIN\n"
                    + "	PESSOA PG\n"
                    + "ON\n"
                    + "	PG.CODGERENTE = C.RESPONSAVEL\n"
                    + "INNER JOIN\n"
                    + "	STATUS S\n"
                    + "ON\n"
                    + "	OS.CODSTATUS = S.CODSTATUS "
                    + "INNER JOIN\n"
                    + "	CLIENTE CLI\n"
                    + "ON\n"
                    + "	CLI.CODCOLIGADA = OS.FK_CODCOLIGADA AND"
                    + "	CLI.CODCLIENTE = OS.FK_CODCLIENTE\n"
                    + "INNER JOIN \n"
                    + "	SEGMENTO \n"
                    + "ON\n"
                    + "	SEGMENTO.CODSEGMENTO = OS.CODSEGMENTO \n"
                    + "INNER JOIN \n"
                    + "	CIDADE \n"
                    + "ON\n"
                    + "	CIDADE.ID = OS.CODCIDADE \n"
                    + "INNER JOIN \n"
                    + "	ESTADO \n"
                    + "ON\n"
                    + "	ESTADO.ID = CIDADE.ESTADO \n"
                    + "LEFT JOIN ADICIONAIS A"
                    + " ON OS.ID_ADICIONAL = A.ID \n"
                    + "INNER JOIN \n"
                    + " motivo_perdido MP \n"
                    + " ON OS.id_motivo_perdido = MP.id \n"
                    + "where OS.CODOS > 0 "
                    + query
                    + " ORDER BY OS.PRIORIDADE DESC";

            ResultSet rs = stat.executeQuery(sql);
            ArrayList<OrdemServico> oa = new ArrayList<>();

            while (rs.next()) {
                OrdemServico os = new OrdemServico();
                CentroCusto c = new CentroCusto();
                Pessoa ps = new Pessoa();
                Pessoa pr = new Pessoa();
                Pessoa pg = new Pessoa();
                Status s = new Status();
                TipoOs t = new TipoOs();
                Cliente cliente = new Cliente();
                Venda venda = new Venda();
                Segmento segmento = new Segmento();
                Estado estado = new Estado();
                Cidade cidade = new Cidade();
                Projeto projeto = new Projeto();
                Adicional adicional = new Adicional();
                MotivoPerda motivo = new MotivoPerda();

                t.setCodTipoOs(rs.getInt("T.CODTIPOOS"));
                t.setDescricao(rs.getString("T.DESCRICAO"));
                segmento.setCodSegmento(rs.getInt("SEGMENTO.CODSEGMENTO"));
                segmento.setDescricao(rs.getString("SEGMENTO.DESCRICAO"));
                estado.setId(rs.getInt("CIDADE.ESTADO"));
                estado.setNome(rs.getString("ESTADO.NOME"));
                estado.setUf(rs.getString("ESTADO.UF"));

                cidade.setId(rs.getInt("OS.CODCIDADE"));
                cidade.setNome(rs.getString("CIDADE.NOME"));
                cidade.setEstado(estado);

                os.setCodOs(rs.getInt("OS.CODOS"));
                os.setNome(rs.getString("OS.NOME"));
                c.setCodCusto(rs.getString("C.CODCUSTO"));
                c.setNome(rs.getString("C.NOME"));
                os.setObra(rs.getString("OS.OBRA"));

                if (rs.getTimestamp("OS.DATASOLICITACAO") != null) {
                    os.setDataSolicitacao(rs.getDate("OS.DATASOLICITACAO").toLocalDate());
                }
                if (rs.getTimestamp("OS.DATANECESSIDADE") != null) {
                    os.setDataNecessidade(rs.getDate("OS.DATANECESSIDADE").toLocalDate());
                }
                if (rs.getTimestamp("OS.DATAINICIO") != null) {
                    os.setDataInicio(rs.getDate("OS.DATAINICIO").toLocalDate());
                }
                if (rs.getTimestamp("OS.DATAPREVENTREGA") != null) {
                    os.setDataPrevEntrega(rs.getDate("OS.DATAPREVENTREGA").toLocalDate());
                }
                if (rs.getTimestamp("OS.DATAENTREGA") != null) {
                    os.setDataEntrega(rs.getDate("OS.DATAENTREGA").toLocalDate());
                }
                if (rs.getTimestamp("OS.DATAINTERACAO") != null) {
                    os.setDataInteracao(rs.getDate("OS.DATAINTERACAO").toLocalDate());
                }
                s.setCodStatus(rs.getInt("S.CODSTATUS"));
                s.setNome(rs.getString("S.NOME"));
                s.setAcao(rs.getInt("S.ACAO"));
                os.setPrioridade(rs.getInt("OS.PRIORIDADE"));
                ps.setCodPessoa(rs.getInt("P.CODPESSOA"));
                ps.setNome(rs.getString("P.NOME"));
                pr.setCodPessoa(rs.getInt("PE.CODPESSOA"));
                pr.setNome(rs.getString("PE.NOME"));
                pg.setCodPessoa(rs.getInt("PG.CODPESSOA"));
                pg.setNome(rs.getString("PG.NOME"));

                cliente.setCodColigada(rs.getInt("CLI.CODCOLIGADA"));
                cliente.setCodCliente(rs.getString("CLI.CODCLIENTE"));
                cliente.setNomeFantasia(rs.getString("CLI.NOMEFANTASIA"));
                cliente.setInteracaoFone(rs.getBoolean("CLI.INTERACAO_FONE"));
                cliente.setInteracaoMsg(rs.getBoolean("CLI.INTERACAO_MSG"));
                cliente.setInteracaoReuniao(rs.getBoolean("CLI.INTERACAO_REUNIAO"));

                venda.setValorFaturamentoDireto(rs.getDouble("OS.VALORFATDIRETO"));
                venda.setValorFaturamentoDolphin(rs.getDouble("OS.VALORFATDOLPHIN"));
                venda.setValorComissao(rs.getDouble("OS.VALOR_COMISSAO"));
                projeto.setId(rs.getInt("OS.ID_PROJETO"));

                if (rs.getObject("OS.ID_ADICIONAL") != null) {
                    adicional.setId(rs.getInt("OS.ID_ADICIONAL"));
                    adicional.setNumero(rs.getInt("A.NUMERO"));
                }

                motivo.setId(rs.getInt("OS.id_motivo_perdido"));
                motivo.setNome(rs.getString("MP.nome"));
                os.setObservacoes(rs.getString("OS.observacoes"));

                os.setAdicional(adicional);
                os.setProjeto(projeto);
                os.setTipoOs(t);
                os.setSegmento(segmento);
                os.setCidade(cidade);
                os.setSolicitante(ps);
                os.setResponsavel(pr);
                os.setGerente(pg);
                os.setCentroCusto(c);
                os.setStatus(s);
                os.setCliente(cliente);
                os.setVenda(venda);
                os.setPrincipal(rs.getBoolean("OS.PRINCIPAL"));
                os.setMotivo(motivo);

                oa.add(os);
            }
            return oa;
        } catch (SQLException se) {
            throw new SQLException("Erro ao filtrar OS/Tarefa! " + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }//fecha finally
    }//fecha filtrar

    public OrdemServico buscarPorProjeto(int idProjeto) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stmt = con.createStatement();
        try {

            String sql = "SELECT ORDEMSERVICO.CODOS, ORDEMSERVICO.OBRA,"
                    + " ORDEMSERVICO.FK_CODCLIENTE, ORDEMSERVICO.FK_CODCOLIGADA, CLIENTE.NOMEFANTASIA,"
                    + " SEGMENTO.CODSEGMENTO, SEGMENTO.DESCRICAO, CIDADE.ID, CIDADE.NOME, CIDADE.ESTADO, ESTADO.NOME, ESTADO.UF \n"
                    + "FROM ORDEMSERVICO \n"
                    + "INNER JOIN SEGMENTO ON ORDEMSERVICO.CODSEGMENTO = SEGMENTO.CODSEGMENTO\n"
                    + "INNER JOIN CLIENTE ON ORDEMSERVICO.FK_CODCLIENTE = CLIENTE.CODCLIENTE AND ORDEMSERVICO.FK_CODCOLIGADA = CLIENTE.CODCOLIGADA\n"
                    + "INNER JOIN CIDADE ON ORDEMSERVICO.CODCIDADE = CIDADE.ID\n"
                    + "INNER JOIN ESTADO ON CIDADE.ESTADO = ESTADO.ID\n"
                    + "WHERE ID_PROJETO = " + idProjeto + " LIMIT 1";

            ResultSet rs = stmt.executeQuery(sql);
            OrdemServico ordemServico = new OrdemServico();

            if (rs != null && rs.next()) {

                Cliente cliente = new Cliente();
                Cidade cidade = new Cidade();
                Estado estado = new Estado();
                Segmento segmento = new Segmento();

                cliente.setCodCliente(rs.getString("ORDEMSERVICO.FK_CODCLIENTE"));
                cliente.setCodColigada(rs.getInt("ORDEMSERVICO.FK_CODCOLIGADA"));
                cliente.setNomeFantasia(rs.getString("CLIENTE.NOMEFANTASIA"));

                cidade.setId(rs.getInt("CIDADE.ID"));
                cidade.setNome(rs.getString("CIDADE.NOME"));

                estado.setId(rs.getInt("CIDADE.ESTADO"));
                estado.setNome(rs.getString("ESTADO.NOME"));
                estado.setUf(rs.getString("ESTADO.UF"));

                cidade.setEstado(estado);

                segmento.setCodSegmento(rs.getInt("SEGMENTO.CODSEGMENTO"));
                segmento.setDescricao(rs.getString("SEGMENTO.DESCRICAO"));

                ordemServico.setSegmento(segmento);
                ordemServico.setCidade(cidade);
                ordemServico.setCliente(cliente);
                ordemServico.setObra(rs.getString("ORDEMSERVICO.OBRA"));
            }

            return ordemServico;

        } catch (SQLException se) {
            throw new SQLException(se.getMessage());
        } finally {
            con.close();
            stmt.close();
        }
    }

    public void alterar(OrdemServico os) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql;

            sql = "update ORDEMSERVICO set"
                    + " CODTIPOOS = ?, CODCCUSTO = ?, OBRA = ?, DATASOLICITACAO = ?,"
                    + " DATANECESSIDADE = ?,"
                    + " DATAINICIO = ?, DATAPREVENTREGA = ?, DATAENTREGA = ?, CODSTATUS = ?,"
                    + " NOME = ?, PRIORIDADE = ?,"
                    + " SOLICITANTE = ?, RESPONSAVEL = ?, FK_CODCLIENTE = ?, FK_CODCOLIGADA = ?,"
                    + " VALORFATDIRETO = ?, VALORFATDOLPHIN = ?, VALOR_COMISSAO = ?, CODSEGMENTO = ?,"
                    + " CODCIDADE = ?, DATAINTERACAO = ?, PRINCIPAL = ?, id_motivo_perdido = ?, observacoes = ? where CODOS = ?";

            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, os.getTipoOs().getCodTipoOs());
            pstmt.setString(2, os.getCentroCusto().getCodCusto());
            pstmt.setString(3, os.getObra());
            pstmt.setObject(4, os.getDataSolicitacao());
            pstmt.setObject(5, os.getDataNecessidade());
            pstmt.setObject(6, os.getDataInicio());
            pstmt.setObject(7, os.getDataPrevEntrega());
            pstmt.setObject(8, os.getDataEntrega());
            pstmt.setInt(9, os.getStatus().getCodStatus());
            pstmt.setString(10, os.getNome());
            pstmt.setInt(11, os.getPrioridade());
            pstmt.setInt(12, os.getSolicitante().getCodPessoa());
            pstmt.setInt(13, os.getResponsavel().getCodPessoa());
            pstmt.setString(14, os.getCliente().getCodCliente());
            pstmt.setInt(15, os.getCliente().getCodColigada());
            pstmt.setDouble(16, os.getVenda().getValorFaturamentoDireto());
            pstmt.setDouble(17, os.getVenda().getValorFaturamentoDolphin());
            pstmt.setDouble(18, os.getVenda().getValorComissao());
            pstmt.setInt(19, os.getSegmento().getCodSegmento());
            pstmt.setInt(20, os.getCidade().getId());
            pstmt.setObject(21, os.getDataInteracao());
            pstmt.setBoolean(22, os.isPrincipal());
            if (os.getMotivo() == null) {
                pstmt.setInt(23, os.getMotivo().getId());
            } else {
                pstmt.setInt(23, 1);
            }
            pstmt.setString(24, os.getObservacoes());

            pstmt.setInt(25, os.getCodOs());

            pstmt.executeUpdate();

        } catch (SQLException se) {
            throw new SQLException("Erro ao editar Ordem de Serviço! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//finally
    }//fecha alterar

    public void excluir(OrdemServico ordemServico) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql;
            sql = "delete from ORDEMSERVICO where CODOS = " + ordemServico.getCodOs();

            stat.execute(sql);
        } catch (SQLException se) {
            throw new SQLException("Erro ao excluir OS! " + se.getMessage());
        } finally {
            stat.close();
            con.close();
        }//finally
    }//fecha excluir

    public ArrayList<OrdemServico> relatorioDeVendas(String deInicio, String ateInicio, String deFechamento, String ateFechamento) throws SQLException {
        Connection connection = ConexaoBanco.getConexao();
        boolean dataInicioPreenchida = !deInicio.isEmpty() && !ateInicio.isEmpty();
        boolean dataFechamentoPreenchida = !deFechamento.isEmpty() && !ateFechamento.isEmpty();
        try {
            String sql = "";
            if (dataInicioPreenchida) {
                sql = "SELECT 'I' AS SIGLA, "
                    + "	ORDEMSERVICO.PRINCIPAL AS PRINCIPAL, "
                    + "	ORDEMSERVICO.ID_PROJETO AS PROJETO, "
                    + " ADICIONAIS.NUMERO AS ADICIONAL, "
                    + " CLIENTE.NOMEFANTASIA AS CLIENTE, "
                    + " ORDEMSERVICO.NOME AS TAREFA, "
                    + " ORDEMSERVICO.VALORFATDIRETO AS VALORFATDIRETO, "
                    + " ORDEMSERVICO.VALORFATDOLPHIN AS VALORFATDOLPHIN, "
                    + " ORDEMSERVICO.VALOR_COMISSAO AS VALOR_COMISSAO, "
                    + " ORDEMSERVICO.PRIORIDADE AS PRIORIDADE, "
                    + " STATUS.NOME AS STATUS, "
                    + " ORDEMSERVICO.DATASOLICITACAO AS DATASOLICITACAO, "
                    + " ORDEMSERVICO.DATAINICIO AS DATAINICIO, "
                    + " ORDEMSERVICO.DATAPREVENTREGA AS DATAPREVENTREGA, "
                    + " ORDEMSERVICO.DATAENTREGA AS DATAENTREGA, "
                    + " PESSOA.NOME AS RESPONSAVEL "
                    + "FROM ORDEMSERVICO "
                    + "INNER JOIN PROJETOS ON ORDEMSERVICO.ID_PROJETO = PROJETOS.ID "
                    + "INNER JOIN ADICIONAIS ON ORDEMSERVICO.ID_ADICIONAL = ADICIONAIS.ID "
                    + "INNER JOIN CLIENTE ON ORDEMSERVICO.FK_CODCLIENTE = CLIENTE.CODCLIENTE "
                    + "INNER JOIN STATUS ON ORDEMSERVICO.CODSTATUS = STATUS.CODSTATUS "
                    + "INNER JOIN PESSOA ON ORDEMSERVICO.RESPONSAVEL = PESSOA.CODPESSOA "
                    + "WHERE ORDEMSERVICO.DATAINICIO BETWEEN (?) AND (?) "
                    + "AND PRINCIPAL = TRUE AND CODTIPOOS = 21";
            } else if (dataFechamentoPreenchida) {
                sql = " SELECT 'F' AS SIGLA, "
                    + "	ORDEMSERVICO.PRINCIPAL AS PRINCIPAL, "
                    + "	ORDEMSERVICO.ID_PROJETO AS PROJETO, "
                    + " ADICIONAIS.NUMERO AS ADICIONAL, "
                    + " CLIENTE.NOMEFANTASIA AS CLIENTE, "
                    + " ORDEMSERVICO.NOME AS TAREFA, "
                    + " ORDEMSERVICO.VALORFATDIRETO AS VALORFATDIRETO, "
                    + " ORDEMSERVICO.VALORFATDOLPHIN AS VALORFATDOLPHIN, "
                    + " ORDEMSERVICO.VALOR_COMISSAO AS VALOR_COMISSAO, "
                    + " ORDEMSERVICO.PRIORIDADE AS PRIORIDADE, "
                    + " STATUS.NOME AS STATUS, "
                    + " ORDEMSERVICO.DATASOLICITACAO AS DATASOLICITACAO, "
                    + " ORDEMSERVICO.DATAINICIO AS DATAINICIO, "
                    + " ORDEMSERVICO.DATAPREVENTREGA AS DATAPREVENTREGA, "
                    + " ORDEMSERVICO.DATAENTREGA AS DATAENTREGA, "
                    + " PESSOA.NOME AS RESPONSAVEL "
                    + "FROM ORDEMSERVICO "
                    + "INNER JOIN PROJETOS ON ORDEMSERVICO.ID_PROJETO = PROJETOS.ID "
                    + "INNER JOIN ADICIONAIS ON ORDEMSERVICO.ID_ADICIONAL = ADICIONAIS.ID "
                    + "INNER JOIN CLIENTE ON ORDEMSERVICO.FK_CODCLIENTE = CLIENTE.CODCLIENTE "
                    + "INNER JOIN STATUS ON ORDEMSERVICO.CODSTATUS = STATUS.CODSTATUS "
                    + "INNER JOIN PESSOA ON ORDEMSERVICO.RESPONSAVEL = PESSOA.CODPESSOA "
                    + "WHERE ORDEMSERVICO.DATAENTREGA BETWEEN (?) AND (?) "
                    + "AND PRINCIPAL = TRUE "
                    + " AND ORDEMSERVICO.CODTIPOOS = 21 "
                    + "AND STATUS.ACAO = 1";
            } else {
                sql = "SELECT 'I' AS SIGLA, "
                    + "	ORDEMSERVICO.PRINCIPAL AS PRINCIPAL, "
                    + "	ORDEMSERVICO.ID_PROJETO AS PROJETO, "
                    + " ADICIONAIS.NUMERO AS ADICIONAL, "
                    + " CLIENTE.NOMEFANTASIA AS CLIENTE, "
                    + " ORDEMSERVICO.NOME AS TAREFA, "
                    + " ORDEMSERVICO.VALORFATDIRETO AS VALORFATDIRETO, "
                    + " ORDEMSERVICO.VALORFATDOLPHIN AS VALORFATDOLPHIN, "
                    + " ORDEMSERVICO.VALOR_COMISSAO AS VALOR_COMISSAO, "
                    + " ORDEMSERVICO.PRIORIDADE AS PRIORIDADE, "
                    + " STATUS.NOME AS STATUS, "
                    + " ORDEMSERVICO.DATASOLICITACAO AS DATASOLICITACAO, "
                    + " ORDEMSERVICO.DATAINICIO AS DATAINICIO, "
                    + " ORDEMSERVICO.DATAPREVENTREGA AS DATAPREVENTREGA, "
                    + " ORDEMSERVICO.DATAENTREGA AS DATAENTREGA, "
                    + " PESSOA.NOME AS RESPONSAVEL "
                    + "FROM ORDEMSERVICO "
                    + "INNER JOIN PROJETOS ON ORDEMSERVICO.ID_PROJETO = PROJETOS.ID "
                    + "INNER JOIN ADICIONAIS ON ORDEMSERVICO.ID_ADICIONAL = ADICIONAIS.ID "
                    + "INNER JOIN CLIENTE ON ORDEMSERVICO.FK_CODCLIENTE = CLIENTE.CODCLIENTE "
                    + "INNER JOIN STATUS ON ORDEMSERVICO.CODSTATUS = STATUS.CODSTATUS "
                    + "INNER JOIN PESSOA ON ORDEMSERVICO.RESPONSAVEL = PESSOA.CODPESSOA "
                    + "WHERE ORDEMSERVICO.DATAINICIO BETWEEN (?) AND (?) "
                    + "AND PRINCIPAL = TRUE AND CODTIPOOS = 21"
                    + " UNION"
                    + " SELECT 'F' AS SIGLA, "
                    + "	ORDEMSERVICO.PRINCIPAL AS PRINCIPAL, "
                    + "	ORDEMSERVICO.ID_PROJETO AS PROJETO, "
                    + " ADICIONAIS.NUMERO AS ADICIONAL, "
                    + " CLIENTE.NOMEFANTASIA AS CLIENTE, "
                    + " ORDEMSERVICO.NOME AS TAREFA, "
                    + " ORDEMSERVICO.VALORFATDIRETO AS VALORFATDIRETO, "
                    + " ORDEMSERVICO.VALORFATDOLPHIN AS VALORFATDOLPHIN, "
                    + " ORDEMSERVICO.VALOR_COMISSAO AS VALOR_COMISSAO, "
                    + " ORDEMSERVICO.PRIORIDADE AS PRIORIDADE, "
                    + " STATUS.NOME AS STATUS, "
                    + " ORDEMSERVICO.DATASOLICITACAO AS DATASOLICITACAO, "
                    + " ORDEMSERVICO.DATAINICIO AS DATAINICIO, "
                    + " ORDEMSERVICO.DATAPREVENTREGA AS DATAPREVENTREGA, "
                    + " ORDEMSERVICO.DATAENTREGA AS DATAENTREGA, "
                    + " PESSOA.NOME AS RESPONSAVEL "
                    + "FROM ORDEMSERVICO "
                    + "INNER JOIN PROJETOS ON ORDEMSERVICO.ID_PROJETO = PROJETOS.ID "
                    + "INNER JOIN ADICIONAIS ON ORDEMSERVICO.ID_ADICIONAL = ADICIONAIS.ID "
                    + "INNER JOIN CLIENTE ON ORDEMSERVICO.FK_CODCLIENTE = CLIENTE.CODCLIENTE "
                    + "INNER JOIN STATUS ON ORDEMSERVICO.CODSTATUS = STATUS.CODSTATUS "
                    + "INNER JOIN PESSOA ON ORDEMSERVICO.RESPONSAVEL = PESSOA.CODPESSOA "
                    + "WHERE ORDEMSERVICO.DATAENTREGA BETWEEN (?) AND (?) "
                    + "AND PRINCIPAL = TRUE "
                    + " AND ORDEMSERVICO.CODTIPOOS = 21 "
                    + "AND STATUS.ACAO = 1";
            }
            

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            if (dataInicioPreenchida) {
                
                preparedStatement.setString(1, deInicio);
                preparedStatement.setString(2, ateInicio);
            } else if (dataFechamentoPreenchida) {
                preparedStatement.setString(3, deFechamento);
                preparedStatement.setString(4, ateFechamento);
            } else {
                preparedStatement.setString(1, deInicio);
                preparedStatement.setString(2, ateInicio);
                preparedStatement.setString(3, deFechamento);
                preparedStatement.setString(4, ateFechamento);
            }

            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<OrdemServico> tarefas = new ArrayList<>();

            while (rs.next()) {
                OrdemServico ordemServico = new OrdemServico();
                Projeto projeto = new Projeto();
                Adicional adicional = new Adicional();
                Cliente cliente = new Cliente();
                Venda venda = new Venda();
                Status status = new Status();
                Pessoa responsavel = new Pessoa();
                ordemServico.setPrincipal(rs.getBoolean("PRINCIPAL"));
                projeto.setId(rs.getInt("PROJETO"));
                ordemServico.setProjeto(projeto);
                adicional.setNumero(rs.getInt("ADICIONAL"));
                ordemServico.setAdicional(adicional);
                cliente.setNomeFantasia(rs.getString("CLIENTE"));
                ordemServico.setCliente(cliente);
                ordemServico.setNome(rs.getString("TAREFA"));
                venda.setValorFaturamentoDireto(rs.getDouble("VALORFATDIRETO"));
                venda.setValorFaturamentoDolphin(rs.getDouble("VALORFATDOLPHIN"));
                venda.setValorComissao(rs.getDouble("VALOR_COMISSAO"));
                ordemServico.setVenda(venda);
                ordemServico.setPrioridade(rs.getInt("PRIORIDADE"));
                ordemServico.setSiglaInicioFechamento(rs.getString("SIGLA"));
                status.setNome(rs.getString("STATUS"));
                ordemServico.setStatus(status);
                if (rs.getTimestamp("DATASOLICITACAO") != null) {
                    ordemServico.setDataSolicitacao(rs.getDate("DATASOLICITACAO").toLocalDate());
                }
                if (rs.getTimestamp("DATAINICIO") != null) {
                    ordemServico.setDataInicio(rs.getDate("DATAINICIO").toLocalDate());
                }
                if (rs.getTimestamp("DATAPREVENTREGA") != null) {
                    ordemServico.setDataPrevEntrega(rs.getDate("DATAPREVENTREGA").toLocalDate());
                }
                if (rs.getTimestamp("DATAENTREGA") != null) {
                    ordemServico.setDataEntrega(rs.getDate("DATAENTREGA").toLocalDate());
                }
                responsavel.setNome(rs.getString("RESPONSAVEL"));
                ordemServico.setResponsavel(responsavel);

                tarefas.add(ordemServico);
            }
            return tarefas;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar registros do relatório! " + se.getMessage());
        } finally {
            connection.close();
        }
    }
}
