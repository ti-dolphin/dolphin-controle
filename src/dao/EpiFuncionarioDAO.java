package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import model.Epi;

import persistencia.ConexaoBanco;
import model.EpiFuncionario;
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
                    + " EMAILENVIADO, CODTKT, LATITUDE, LONGITUDE, TKTENTREGA,"
                    + " TKTDEVOLUCAO, RECCREATEDBY, CODFICHAEPI)"
                    + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = con.prepareStatement(sql);
            
            pstmt.setInt(1, ef.getCodRegistro());
            pstmt.setShort(2, ef.getFuncionario().getCodColigada());
            pstmt.setString(3, ef.getFuncionario().getChapa());
            pstmt.setString(4, ef.getEpi().getCodEpi());
            pstmt.setObject(5, ef.getDataRetirada());
            pstmt.setObject(6, ef.getDataDevolucao());
            pstmt.setString(7, ef.getCa());
            pstmt.setBoolean(8, ef.isEmailEnviado());
            pstmt.setInt(9, ef.getCodTkt());
            pstmt.setDouble(10, ef.getLatitude());
            pstmt.setDouble(11, ef.getLongitude());
            pstmt.setInt(12, ef.getTktEntrega());
            pstmt.setInt(13, ef.getTktDevolucao());
            pstmt.setString(14, ef.getCreatedBy());
            pstmt.setInt(15, ef.getFichaEpi().getCodFicha());

            pstmt.execute();

        } catch (SQLException e) {
            throw new SQLException("Erro ao cadastrar registro! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha cadastrarEpi

    public ArrayList<EpiFuncionario> buscarEpiFuncionario() throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "select \n"
                    + "	ef.CODREGISTRO, ef.CODCOLIGADA, ef.CHAPA, f.NOME, e.NOME,"
                    + " ef.DATARETIRADA, ef.DATADEVOLUCAO, ef.CA, ef.RECCREATEDBY,"
                    + " ef.RECMODIFIEDBY\n"
                    + "from \n"
                    + "	PFUNC f \n"
                    + "inner join \n"
                    + "	VCATALOGEPI_PFUNC ef \n"
                    + "on \n"
                    + "	f.CHAPA = ef.CHAPA and f.CODCOLIGADA = ef.CODCOLIGADA \n"
                    + "inner join \n"
                    + "	VCATALOGEPI e \n"
                    + "on \n"
                    + "	ef.CODEPI = e.CODEPI\n"
                    + " order by ef.DATARETIRADA desc";
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<EpiFuncionario> efa = new ArrayList<>();
            while (rs.next()) {
                EpiFuncionario ef = new EpiFuncionario();
                Funcionario f = new Funcionario();
                Epi e = new Epi();
                ef.setCodRegistro(rs.getInt("ef.CODREGISTRO"));
                ef.setCodColigada(rs.getShort("ef.CODCOLIGADA"));
                ef.setChapa(rs.getString("ef.CHAPA"));
                f.setNome(rs.getString("f.NOME"));
                e.setNome(rs.getString("e.NOME"));
                ef.setDataRetirada(rs.getTimestamp("ef.DATARETIRADA").toLocalDateTime());
                Timestamp dataSQL = rs.getTimestamp("ef.DATADEVOLUCAO");
                if (dataSQL != null) {
                    ef.setDataDevolucao(rs.getTimestamp("ef.DATADEVOLUCAO").toLocalDateTime());
                } else {
                    ef.setDataDevolucao(null);
                }
                ef.setCa(rs.getString("ef.CA"));
                ef.setCreatedBy(rs.getString("ef.RECCREATEDBY"));
                ef.setModifiedBy(rs.getString("ef.RECMODIFIEDBY"));
                ef.setFuncionario(f);
                ef.setEpi(e);
                efa.add(ef);
            }

            return efa;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar dados do histórico! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha buscarEpiFuncionario

    public ArrayList<EpiFuncionario> buscarEpisDoFuncionario(short coligada, String chapa) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "select\n"
                    + "	ef.CODREGISTRO, ef.CODCOLIGADA, f.NOME,"
                    + " e.NOME, e.CODEPI, e.PERIODICIDADE, ef.DATARETIRADA,"
                    + " ef.DATADEVOLUCAO, ef.CA, ef.LATITUDE, ef.LONGITUDE\n"
                    + "from \n"
                    + "	VCATALOGEPI_PFUNC ef \n"
                    + "inner join\n"
                    + "	PFUNC f\n"
                    + "on\n"
                    + "	ef.CODCOLIGADA = f.CODCOLIGADA and ef.CHAPA = f.CHAPA\n"
                    + "inner join \n"
                    + "	VCATALOGEPI e\n"
                    + "on\n"
                    + "	ef.CODEPI = e.CODEPI\n"
                    + "where ef.CODCOLIGADA = " + coligada + " and  ef.CHAPA = '" + chapa + "'"
                    + "order by ef.CODREGISTRO desc LIMIT 100";
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<EpiFuncionario> efa = new ArrayList<>();
            while (rs.next()) {
                EpiFuncionario ef = new EpiFuncionario();
                Funcionario f = new Funcionario();
                Epi e = new Epi();
                ef.setCodRegistro(rs.getInt("ef.CODREGISTRO"));
                ef.setCodColigada(rs.getShort("ef.CODCOLIGADA"));
                f.setNome(rs.getString("f.NOME"));
                e.setNome(rs.getString("e.NOME"));
                e.setCodEpi(rs.getString("e.CODEPI"));
                e.setPeriodicidade(rs.getInt("e.PERIODICIDADE"));
                ef.setDataRetirada(rs.getTimestamp("ef.DATARETIRADA").toLocalDateTime());
                Timestamp dataSQL = rs.getTimestamp("ef.DATADEVOLUCAO");
                if (dataSQL != null) {
                    ef.setDataDevolucao(rs.getTimestamp("ef.DATADEVOLUCAO").toLocalDateTime());
                } else {
                    ef.setDataDevolucao(null);
                }
                ef.setCa(rs.getString("ef.CA"));
                ef.setLatitude(rs.getDouble("ef.LATITUDE"));
                ef.setLongitude(rs.getDouble("ef.LONGITUDE"));
                ef.setFuncionario(f);
                ef.setEpi(e);
                efa.add(ef);
            }
            return efa;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar dados do histórico! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha buscarEpiFuncionario
    
    
    public ArrayList<EpiFuncionario> buscarEpisPendentes(Funcionario funcionario, Epi epi) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        try {
            String sql = "SELECT * from VCATALOGEPI_PFUNC"
                    + " WHERE CHAPA = " + funcionario.getChapa()
                    + " AND CODCOLIGADA = " + funcionario.getCodColigada()
                    + " AND CODEPI = '" + epi.getCodEpi()
                    + "' AND DATADEVOLUCAO IS NULL";
            
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<EpiFuncionario> efa = new ArrayList<>();
            while (rs.next()) {
                EpiFuncionario ef = new EpiFuncionario();
                ef.setCodRegistro(rs.getInt("CODREGISTRO"));
                ef.setCodColigada(rs.getShort("CODCOLIGADA"));
                ef.setDataRetirada(rs.getTimestamp("DATARETIRADA").toLocalDateTime());
                Timestamp dataSQL = rs.getTimestamp("DATADEVOLUCAO");
                if (dataSQL != null) {
                    ef.setDataDevolucao(rs.getTimestamp("DATADEVOLUCAO").toLocalDateTime());
                } else {
                    ef.setDataDevolucao(null);
                }
                ef.setCa(rs.getString("CA"));
                ef.setLatitude(rs.getDouble("LATITUDE"));
                ef.setLongitude(rs.getDouble("LONGITUDE"));
                efa.add(ef);
            }
            return efa;
            
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
                    + "	ef.CODREGISTRO, ef.CODCOLIGADA, ef.CHAPA, f.NOME, e.NOME, e.CODEPI, e.PRECO, e.PERIODICIDADE,"
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
            ArrayList<EpiFuncionario> efa = new ArrayList<>();

            while (rs.next()) {
                EpiFuncionario ef = new EpiFuncionario();
                Funcionario f = new Funcionario();
                Epi e = new Epi();
                ef.setCodRegistro(rs.getInt("ef.CODREGISTRO"));
                ef.setCodColigada(rs.getShort("ef.CODCOLIGADA"));
                ef.setChapa(rs.getString("ef.CHAPA"));
                ef.setLatitude(rs.getDouble("ef.LATITUDE"));
                ef.setLongitude(rs.getDouble("ef.LONGITUDE"));
                f.setNome(rs.getString("f.NOME"));
                e.setCodEpi(rs.getString("e.CODEPI"));
                e.setPreco(rs.getDouble("e.PRECO"));
                e.setNome(rs.getString("e.NOME"));
                e.setPeriodicidade(rs.getInt("e.PERIODICIDADE"));
                ef.setDataRetirada(rs.getTimestamp("ef.DATARETIRADA").toLocalDateTime());
                ef.setFuncionario(f);
                ef.setEpi(e);
                
                if (rs.getTimestamp("ef.DATADEVOLUCAO") != null) {
                    ef.setDataDevolucao(rs.getTimestamp("ef.DATADEVOLUCAO").toLocalDateTime());
                }
                
                ef.setCa(rs.getString("ef.CA"));
                ef.setCreatedBy(rs.getString("ef.RECCREATEDBY"));
                ef.setModifiedBy(rs.getString("ef.RECMODIFIEDBY"));
                ef.setPreco(rs.getDouble("ef.PRECOADESCONTAR"));
                ef.setDescontado(rs.getBoolean("ef.DESCONTADO"));

                efa.add(ef);
            }
            return efa;
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
                    + " DATADEVOLUCAO = ?, RECMODIFIEDBY = ?"
                    + " where CODREGISTRO = ?";
            pstmt = con.prepareStatement(sql);

            pstmt.setObject(1, ef.getDataDevolucao());
            pstmt.setString(2, ef.getModifiedBy());
            pstmt.setInt(3, ef.getCodRegistro());

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
                ef.setLatitude(rs.getDouble("LATITUDE"));
                ef.setLongitude(rs.getDouble("LONGITUDE"));
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
