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
import model.os.Pessoa;
import model.os.PessoaTipo;
import model.os.TipoOs;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class PessoaTipoDAO {

    public void cadastrar(PessoaTipo pessoaTipo) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "INSERT INTO PESSOA_TIPO (CODPESSOA, CODTIPOOS)"
                    + " VALUES (?, ?)";

            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, pessoaTipo.getPessoa().getCodPessoa());
            pstmt.setInt(2, pessoaTipo.getTipo().getCodTipoOs());

            pstmt.execute();
        } catch (SQLException e) {
            throw new SQLException("Erro ao cadastrar permissão de tipo " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//finally
    }//cadastrar

    public ArrayList<PessoaTipo> buscarTiposPorPessoa(Pessoa pessoa) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "select\n"
                    + "     PESSOA_TIPO.CODPESSOA, TIPOOS.CODTIPOOS, TIPOOS.DESCRICAO\n"
                    + " from\n"
                    + "     PESSOA_TIPO\n"
                    + " INNER JOIN\n"
                    + "     TIPOOS\n"
                    + " ON\n"
                    + "     PESSOA_TIPO.CODTIPOOS = TIPOOS.CODTIPOOS\n"
                    + " WHERE\n"
                    + "     PESSOA_TIPO.CODPESSOA = " + pessoa.getCodPessoa();

            pstmt = con.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<PessoaTipo> pessoasTipos = new ArrayList<>();
            while (rs.next()) {
                PessoaTipo pessoaTipo = new PessoaTipo();
                TipoOs tipo = new TipoOs();

                tipo.setCodTipoOs(rs.getInt("TIPOOS.CODTIPOOS"));
                tipo.setDescricao(rs.getString("TIPOOS.DESCRICAO"));
                pessoaTipo.setTipo(tipo);
                pessoaTipo.setPessoa(pessoa);

                pessoasTipos.add(pessoaTipo);
            }

            return pessoasTipos;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar permissões de tipos! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//buscar

    public void excluir(PessoaTipo pessoaTipo) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql = "delete from PESSOA_TIPO where CODTIPOOS = " + pessoaTipo.getTipo().getCodTipoOs()
                    + " and CODPESSOA = " + pessoaTipo.getPessoa().getCodPessoa();

            stat.execute(sql);
        } catch (SQLException se) {
            throw new SQLException("Erro ao excluir! " + se.getMessage());
        } finally {
            stat.close();
            con.close();
        }//finally
    }//excluir

}
