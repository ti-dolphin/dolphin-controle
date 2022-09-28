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
import model.os.PessoaResponsavel;
import model.os.Pessoa;
import model.os.PessoaTipo;
import model.os.TipoOs;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class PessoaResponsavelDAO {
    
    public void cadastrar(PessoaResponsavel responsavel) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            String sql = "INSERT INTO PESSOA_RESPONSAVEL (CODPESSOA, CODRESPONSAVEL)"
                    + " VALUES (?, ?)";
            
            pstmt = con.prepareStatement(sql);
            
            pstmt.setInt(1, responsavel.getPessoa().getCodPessoa());
            pstmt.setInt(2, responsavel.getResponsavel().getCodPessoa());
            
            pstmt.execute();
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao cadastrar permissão para responsável! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//finally
        
    }//cadastrar

    public ArrayList<PessoaResponsavel> buscar(Pessoa pessoa) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "SELECT \n"
                    + "     PESSOA_RESPONSAVEL.CODPESSOA, PESSOA_RESPONSAVEL.CODRESPONSAVEL, PESSOA.NOME\n"
                    + "FROM \n"
                    + "     PESSOA_RESPONSAVEL\n"
                    + "INNER JOIN\n"
                    + "     PESSOA\n"
                    + "ON\n"
                    + "     PESSOA_RESPONSAVEL.CODRESPONSAVEL = PESSOA.CODPESSOA\n"
                    + "WHERE \n"
                    + "     PESSOA_RESPONSAVEL.CODPESSOA = " + pessoa.getCodPessoa();

            pstmt = con.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<PessoaResponsavel> pessoasResponsaveis = new ArrayList<>();
            while (rs.next()) {
                PessoaResponsavel pessoaResponsavel = new PessoaResponsavel();
                Pessoa responsavel = new Pessoa();

                responsavel.setCodPessoa(rs.getInt("PESSOA_RESPONSAVEL.CODRESPONSAVEL"));
                responsavel.setNome(rs.getString("PESSOA.NOME"));

                pessoaResponsavel.setPessoa(pessoa);
                pessoaResponsavel.setResponsavel(responsavel);

                pessoasResponsaveis.add(pessoaResponsavel);
            }

            return pessoasResponsaveis;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar responsáveis permitidos! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//buscar
    
    public void excluir(PessoaResponsavel pessoaResponsavel) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
                String sql = "delete from PESSOA_RESPONSAVEL where CODRESPONSAVEL = " + pessoaResponsavel.getResponsavel().getCodPessoa()
                    + " and CODPESSOA = " + pessoaResponsavel.getPessoa().getCodPessoa();

            stat.execute(sql);
        } catch (SQLException se) {
            throw new SQLException("Erro ao excluir! " + se.getMessage());
        } finally {
            stat.close();
            con.close();
        }//finally
    }//excluir
}
