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
import model.os.Classificacao;
import model.os.Cliente;
import model.os.Pessoa;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class ClienteDAO {
    
    public ArrayList<Cliente> buscar(String query) throws SQLException{
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        
        try {
            String sql = "select * from CLIENTE inner join CLASSIFICACAO ON CLIENTE.CLASSIFICACAO_ID = CLASSIFICACAO.ID " + query;
            
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Cliente> clientes = new ArrayList<>();
            
            while(rs.next()) {
                Cliente cliente = new Cliente();
                Classificacao classificacao = new Classificacao();
                
                cliente.setCodCliente(rs.getString("CODCLIENTE"));
                cliente.setCodColigada(rs.getInt("CODCOLIGADA"));
                cliente.setNome(rs.getString("NOME"));
                cliente.setNomeFantasia(rs.getString("NOMEFANTASIA"));
                cliente.setDataInteracao(rs.getDate("DATA_INTERACAO").toLocalDate());
                cliente.setProspectar(rs.getBoolean("PROSPECTAR"));
                cliente.setInteracaoFone(rs.getBoolean("INTERACAO_FONE"));
                cliente.setInteracaoMsg(rs.getBoolean("INTERACAO_MSG"));
                cliente.setInteracaoReuniao(rs.getBoolean("INTERACAO_REUNIAO"));
                cliente.setCnpj(rs.getString("CNPJ"));
                
                classificacao.setId(rs.getInt("CLASSIFICACAO_ID"));
                classificacao.setAbreviacao(rs.getString("CLASSIFICACAO.ABREVIACAO").charAt(0));
                cliente.setClassificacao(classificacao);
                
                clientes.add(cliente);
            }
            
            return clientes;
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar clientes: " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }//finally
    }//buscar
    
    public ArrayList<Cliente> buscar(String query, int offset, int qtdPorPagina) throws SQLException{
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "select CLI.CODCOLIGADA, CLI.NOME, CLI.NOMEFANTASIA,"
                    + " CLI.CODCLIENTE, CLI.DATA_INTERACAO, CLI.PROSPECTAR,"
                    + " CLI.INTERACAO_MSG, CLI.INTERACAO_FONE, CLI.INTERACAO_REUNIAO, CLI.CNPJ,"
                    + " CLA.ID, CLA.ABREVIACAO,"
                    + " P.CODPESSOA, P.NOME"
                    + " FROM CLIENTE CLI"
                    + " INNER JOIN CLASSIFICACAO CLA"
                    + " ON CLI.CLASSIFICACAO_ID = CLA.ID"
                    + " INNER JOIN PESSOA P"
                    + " ON CLI.PESSOA_CODPESSOA = P.CODPESSOA"
                    + " WHERE CLI.PESSOAFISOUJUR = 'J' AND CLI.PAGREC <> 2"
                    + query
                    + " ORDER BY CLA.ABREVIACAO ASC LIMIT " + offset + "," + qtdPorPagina;

            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Cliente> clientes = new ArrayList<>();
            
            while(rs.next()) {
                Cliente cliente = new Cliente();
                Classificacao classificacao = new Classificacao();
                Pessoa responsavel = new Pessoa();
                
                cliente.setCodCliente(rs.getString("CLI.CODCLIENTE"));
                cliente.setCodColigada(rs.getInt("CLI.CODCOLIGADA"));
                cliente.setNome(rs.getString("CLI.NOME"));
                cliente.setNomeFantasia(rs.getString("CLI.NOMEFANTASIA"));
                cliente.setDataInteracao(rs.getDate("CLI.DATA_INTERACAO").toLocalDate());
                cliente.setProspectar(rs.getBoolean("CLI.PROSPECTAR"));
                cliente.setInteracaoFone(rs.getBoolean("CLI.INTERACAO_FONE"));
                cliente.setInteracaoMsg(rs.getBoolean("CLI.INTERACAO_MSG"));
                cliente.setInteracaoReuniao(rs.getBoolean("CLI.INTERACAO_REUNIAO"));
                cliente.setCnpj(rs.getString("CLI.CNPJ"));
                
                classificacao.setId(rs.getInt("CLA.ID"));
                classificacao.setAbreviacao(rs.getString("CLA.ABREVIACAO").charAt(0));
                
                responsavel.setCodPessoa(rs.getInt("P.CODPESSOA"));
                responsavel.setNome(rs.getString("P.NOME"));
                
                cliente.setClassificacao(classificacao);
                cliente.setResponsavel(responsavel);

                clientes.add(cliente);
            }
            
            return clientes;
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar clientes: " + e.getMessage());
        } finally {
            con.close();
            stat.close();
        }//finally
    }//buscar
    
    public ArrayList<Cliente> buscarPorNomeFantasia(String nomeFantasia) throws SQLException{
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        ArrayList<Cliente> clientes = new ArrayList<>();
        
        try {
            pstmt = con.prepareStatement("SELECT * FROM CLIENTE WHERE NOMEFANTASIA like ?");
            pstmt.setString(1, "%"+nomeFantasia+"%");
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                Cliente cliente = new Cliente();
                cliente.setCodColigada(rs.getInt("CODCOLIGADA"));
                cliente.setCodCliente(rs.getString("CODCLIENTE"));
                cliente.setNome(rs.getString("NOME"));
                cliente.setNomeFantasia(rs.getString("NOMEFANTASIA"));
                cliente.setInteracaoFone(rs.getBoolean("INTERACAO_FONE"));
                cliente.setInteracaoMsg(rs.getBoolean("INTERACAO_MSG"));
                cliente.setInteracaoReuniao(rs.getBoolean("INTERACAO_REUNIAO"));
                cliente.setCnpj(rs.getString("CNPJ"));
                
                clientes.add(cliente);
            }//fecha while
            return clientes;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar clientes! " + se.getMessage());
        } finally {
            pstmt.close();
            con.close();
        }//fecha finally
    }//fecha método filtrar

    public void editar(Cliente cliente) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            String sql = "UPDATE CLIENTE SET DATA_INTERACAO = ?, CLASSIFICACAO_ID = ?, PROSPECTAR = ?,"
                    + " INTERACAO_FONE =?, INTERACAO_MSG = ?, INTERACAO_REUNIAO = ?, PESSOA_CODPESSOA = ?"
                    + " WHERE CODCOLIGADA = ? AND CODCLIENTE = ?";
            
            pstmt = con.prepareStatement(sql);
            
            pstmt.setObject(1, cliente.getDataInteracao());
            pstmt.setInt(2, cliente.getClassificacao().getId());
            pstmt.setBoolean(3, cliente.isProspectar());
            pstmt.setBoolean(4, cliente.isInteracaoFone());
            pstmt.setBoolean(5, cliente.isInteracaoMsg());
            pstmt.setBoolean(6, cliente.isInteracaoReuniao());
            pstmt.setInt(7, cliente.getResponsavel().getCodPessoa());
            
            
            pstmt.setInt(8, cliente.getCodColigada());
            pstmt.setString(9, cliente.getCodCliente());
            
            pstmt.executeUpdate();
            
        } catch (SQLException se) {
            throw new SQLException(se.getMessage());
        } finally {
            con.close();
        }
    }

    
    
}
