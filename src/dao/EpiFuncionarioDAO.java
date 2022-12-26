package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import model.epi.Epi;

import persistencia.ConexaoBanco;
import model.epi.EpiFuncionario;
import model.Funcionario;
import model.Ticket;

public class EpiFuncionarioDAO {

    public void cadastrarEpiFuncionario(EpiFuncionario ef) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql;
            sql = "insert into VCATALOGEPI_PFUNC(CODREGISTRO, CODCOLIGADA, CHAPA,"
                    + " CODEPI, DATARETIRADA, DATADEVOLUCAO, CA,"
                    + " EMAILENVIADO, CODTKT, TKTENTREGA,"
                    + " TKTDEVOLUCAO, RECCREATEDBY)"
                    + " values(?, ?, ?, ?, NOW(),"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?)";

            pstmt = con.prepareStatement(sql);
            
            pstmt.setInt(1, ef.getCodRegistro());
            pstmt.setShort(2, ef.getFuncionario().getCodColigada());
            pstmt.setString(3, ef.getFuncionario().getChapa());
            pstmt.setString(4, ef.getEpi().getCodEpi());
            
            pstmt.setObject(5, ef.getDataDevolucao());
            pstmt.setString(6, ef.getCa());
            pstmt.setBoolean(7, ef.isEmailEnviado());
            pstmt.setInt(8, ef.getCodTkt());
            pstmt.setInt(9, ef.getTktEntrega());
            
            pstmt.setInt(10, ef.getTktDevolucao());
            pstmt.setString(11, ef.getCreatedBy());

            pstmt.execute();

        } catch (SQLException e) {
            throw new SQLException("Erro ao cadastrar registro! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha cadastrarEpi

    public ArrayList<EpiFuncionario> buscarEpisPendentes(EpiFuncionario epiFuncionario) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        try {
            String sql = "SELECT * from VCATALOGEPI_PFUNC"
                    + " WHERE CHAPA = " + epiFuncionario.getFuncionario().getChapa()
                    + " AND CODCOLIGADA = " + epiFuncionario.getFuncionario().getCodColigada()
                    + " AND CODEPI = '" + epiFuncionario.getEpi().getCodEpi()
                    + "' AND DATADEVOLUCAO IS NULL";
            
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<EpiFuncionario> episFuncionario = new ArrayList<>();
            while (rs.next()) {
                epiFuncionario.setCodRegistro(rs.getInt("CODREGISTRO"));
                if (rs.getTimestamp("DATARETIRADA") != null) {
                    epiFuncionario.setDataRetirada(rs.getTimestamp("DATARETIRADA").toLocalDateTime());
                }
                if (rs.getTimestamp("DATADEVOLUCAO") != null) {
                    epiFuncionario.setDataDevolucao(rs.getTimestamp("DATADEVOLUCAO").toLocalDateTime());
                }
                epiFuncionario.setCa(rs.getString("CA"));
                episFuncionario.add(epiFuncionario);
            }
            return episFuncionario;
            
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar epis pendentes! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally    
    }

    public ArrayList<EpiFuncionario> filtrarEpiFuncionario(String query) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        try {
            String sql = "select  \n"
                    + "	ef.CODREGISTRO, ef.CODCOLIGADA, ef.CHAPA, f.NOME, f.SENHA, e.NOME, e.CODEPI, e.PRECO, e.PERIODICIDADE,"
                    + " ef.DATARETIRADA, ef.DATADEVOLUCAO, ef.CA, ef.LATITUDE,"
                    + " ef.LONGITUDE, ef.RECCREATEDBY, ef.RECMODIFIEDBY, ef.PRECOADESCONTAR, ef.DESCONTADO\n"
                    + "from \n"
                    + "	VCATALOGEPI_PFUNC ef \n"
                    + "inner join\n"
                    + "	PFUNC f\n"
                    + "on\n"
                    + "	ef.CODCOLIGADA = f.CODCOLIGADA and ef.CHAPA = f.CHAPA\n"
                    + "inner join \n"
                    + "	VCATALOGEPI e\n"
                    + "on\n"
                    + "	ef.CODEPI = e.CODEPI" 
                    + query
                    + " order by ef.CODREGISTRO desc";

            ResultSet rs = stat.executeQuery(sql);
            ArrayList<EpiFuncionario> episFuncionario = new ArrayList<>();

            while (rs.next()) {
                EpiFuncionario epiFuncionario = new EpiFuncionario();
                Funcionario funcionario = new Funcionario();
                Epi epi = new Epi();
                epiFuncionario.setCodRegistro(rs.getInt("ef.CODREGISTRO"));
                funcionario.setCodColigada(rs.getShort("ef.CODCOLIGADA"));
                funcionario.setChapa(rs.getString("ef.CHAPA"));
                funcionario.setNome(rs.getString("f.NOME"));
                funcionario.setSenha(rs.getInt("f.SENHA"));
                epi.setCodEpi(rs.getString("e.CODEPI"));
                epi.setPreco(rs.getDouble("e.PRECO"));
                epi.setNome(rs.getString("e.NOME"));
                epi.setPeriodicidade(rs.getInt("e.PERIODICIDADE"));
                if (rs.getTimestamp("ef.DATARETIRADA") != null) {
                    epiFuncionario.setDataRetirada(rs.getTimestamp("ef.DATARETIRADA").toLocalDateTime());
                }
                epiFuncionario.setFuncionario(funcionario);
                epiFuncionario.setEpi(epi);
                
                if (rs.getTimestamp("ef.DATADEVOLUCAO") != null) {
                    epiFuncionario.setDataDevolucao(rs.getTimestamp("ef.DATADEVOLUCAO").toLocalDateTime());
                }
                
                epiFuncionario.setCa(rs.getString("ef.CA"));
                epiFuncionario.setCreatedBy(rs.getString("ef.RECCREATEDBY"));
                epiFuncionario.setModifiedBy(rs.getString("ef.RECMODIFIEDBY"));
                epiFuncionario.setPreco(rs.getDouble("ef.PRECOADESCONTAR"));
                epiFuncionario.setDescontado(rs.getBoolean("ef.DESCONTADO"));

                episFuncionario.add(epiFuncionario);
            }
            return episFuncionario;
        } catch (SQLException se) {
            throw new SQLException("Erro ao filtrar dados do histórico! " + se.getMessage());
        } finally {
            con.close();
            stat.close();
        }//fecha finally
    }//fecha filtrarEpiFuncionario

    public void alterarEpiFuncionario(EpiFuncionario ef) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        try {
            String sql;
            sql = "update VCATALOGEPI_PFUNC set"
                    + " DATADEVOLUCAO = NOW(), RECMODIFIEDBY = ?"
                    + " where CODREGISTRO = ?";
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, ef.getModifiedBy());
            pstmt.setInt(2, ef.getCodRegistro());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao alterar dados! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha alterarEpiFuncionario
    
    public void alterarMotivo(EpiFuncionario ef) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        try {
            String sql;
            sql = "update VCATALOGEPI_PFUNC set"
                    + " MOTIVO = ?"
                    + " where CODREGISTRO = ?";
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, ef.getMotivo());
            pstmt.setInt(2, ef.getCodRegistro());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao salvar motivo! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha alterarMotivo
    
    public void alterarDescontar(EpiFuncionario ef) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        try {
            String sql;
            sql = "update VCATALOGEPI_PFUNC set"
                    + " DESCONTAR = ?"
                    + " where CODREGISTRO = ?";
            pstmt = con.prepareStatement(sql);

            pstmt.setBoolean(1, ef.isDescontar());
            pstmt.setInt(2, ef.getCodRegistro());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao salvar dados! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha alterarEpiFuncionario
    
    public void alterarDescontado(EpiFuncionario ef) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        try {
            String sql;
            sql = "update VCATALOGEPI_PFUNC set"
                    + " DESCONTADO = ?, DATADESCONTADO = NOW(), PRECOADESCONTAR = ?"
                    + " where CODREGISTRO = ?";
            pstmt = con.prepareStatement(sql);

            pstmt.setBoolean(1, ef.isDescontado());
            pstmt.setDouble(2, ef.getEpi().getPreco());
            pstmt.setInt(3, ef.getCodRegistro());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao salvar dados! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha alterarEpiFuncionario
    
    public void atualizaEpiEmail(int tkt, int coligada, String chapa) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        try {
            String sqlEntrega;
            String sqlDevolucao;
            sqlEntrega = "update VCATALOGEPI_PFUNC set"
                    + " TKTENTREGA = " + tkt
                    + " where CODCOLIGADA="+coligada
                    + " and CHAPA="+chapa
                    + " and TKTENTREGA = 0";
            pstmt = con.prepareStatement(sqlEntrega);
            pstmt.executeUpdate();
            sqlDevolucao = "update VCATALOGEPI_PFUNC set"
                    + " TKTDEVOLUCAO = " + tkt
                    + " where CODCOLIGADA="+coligada
                    + " and CHAPA="+chapa
                    + " and TKTDEVOLUCAO = 0 AND DATADEVOLUCAO IS NOT NULL";
            pstmt = con.prepareStatement(sqlDevolucao);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao alterar dados! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha alterarEpiFuncionario

    public ArrayList<EpiFuncionario> buscarHistoricoEpisNaoEnviados(short coligada, String chapa) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "select * from (select ef.CODREGISTRO, e.CODEPI, e.NOME, ef.DATARETIRADA,"
                    + " ef.DATADEVOLUCAO, ef.CA, ef.CODTKT, ef.TKTENTREGA,"
                    + " ef.LATITUDE, ef.LONGITUDE, ef.TKTDEVOLUCAO"
                    + " from VCATALOGEPI_PFUNC ef inner join VCATALOGEPI e on ef.CODEPI = e.CODEPI"
                    + " where ef.CODCOLIGADA="+coligada+" and ef.CHAPA="+chapa+" and ef.TKTENTREGA = 0 AND ef.DATARETIRADA IS NOT NULL AND ef.DATADEVOLUCAO IS NULL"
                    + " UNION"
                    + " select ef.CODREGISTRO, e.CODEPI, e.NOME, ef.DATARETIRADA,"
                    + " ef.DATADEVOLUCAO, ef.CA, ef.CODTKT, ef.TKTENTREGA,"
                    + " ef.LATITUDE, ef.LONGITUDE, ef.TKTDEVOLUCAO"
                    + " from VCATALOGEPI_PFUNC ef inner join VCATALOGEPI e on ef.CODEPI = e.CODEPI"
                    + " where ef.CODCOLIGADA="+coligada+" and ef.CHAPA="+chapa+" and ef.TKTDEVOLUCAO = 0 AND ef.DATADEVOLUCAO IS NOT NULL) as tab";
            
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<EpiFuncionario> efa = new ArrayList<>();
            while (rs.next()) {
                EpiFuncionario ef = new EpiFuncionario();
                Epi e = new Epi();
                Ticket t = new Ticket();
                ef.setCodRegistro(rs.getInt("CODREGISTRO"));
                e.setCodEpi(rs.getString("CODEPI"));
                e.setNome(rs.getString("NOME"));
                ef.setCa(rs.getString("CA"));
                ef.setCodTkt(rs.getInt("CODTKT"));
                ef.setDataRetirada(rs.getTimestamp("DATARETIRADA").toLocalDateTime());
                Timestamp dataSQL = rs.getTimestamp("DATADEVOLUCAO");
                if (dataSQL != null) {
                    ef.setDataDevolucao(rs.getTimestamp("DATADEVOLUCAO").toLocalDateTime());
                } else {
                    ef.setDataDevolucao(null);
                }
                t.setTkt(1);
                ef.setTicket(t);
                ef.setEpi(e);
                efa.add(ef);
            }
            return efa;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar historico! "+se.getMessage());
        }
    }
}//fecha classe
