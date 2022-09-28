/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exceptions.CampoEmBrancoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Contato;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilh
 */
public class ContatoDAO {

    public int inserir(Contato contato) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {

            String sql = "insert into CONTATOS (NOME, EMAIL, TELEFONE) values (?, ?, ?)";

            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, contato.getNome());
            pstmt.setString(2, contato.getEmail());
            pstmt.setString(3, contato.getTelefone());

            pstmt.execute();

            int id = 0;
            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            return id;

        } catch (SQLException ex) {
            Logger.getLogger(ContatoDAO.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getErrorCode() == 1062) {
                throw new SQLException("Contato já cadastrado!");
            } else {
                throw new SQLException("Erro ao inserir contato: " + ex.getMessage());
            }
        } finally {
            con.close();
        }
    }

    public ArrayList listar() throws SQLException, CampoEmBrancoException {
        Connection connection = ConexaoBanco.getConexao();
        Statement stat = connection.createStatement();
        try {

            String sql = "select * from CONTATOS";
            ResultSet rs = stat.executeQuery(sql);
            ArrayList<Contato> contatos = new ArrayList<>();

            while (rs.next()) {
                Contato contato = new Contato();
                contato.setId(rs.getInt("ID"));
                contato.setNome(rs.getString("NOME"));
                contato.setEmail(rs.getString("EMAIL"));
                contato.setTelefone(rs.getString("TELEFONE"));

                contatos.add(contato);
            }

            return contatos;
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        } finally {
            connection.close();
            stat.close();
        }
    }

    public Contato buscarPorId(int id) throws SQLException, CampoEmBrancoException {
        Connection connection = ConexaoBanco.getConexao();
        PreparedStatement stat = null;

        try {

            String sql = "select * from CONTATOS where ID = ? LIMIT 1";
            stat = connection.prepareStatement(sql);

            stat.setInt(1, id);

            ResultSet rs = stat.executeQuery(sql);
            Contato contato = new Contato();
            if (rs.next()) {
                contato.setId(rs.getInt("ID"));
                contato.setNome(rs.getString("NOME"));
                contato.setEmail(rs.getString("EMAIL"));
            }
            return contato;
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        } finally {
            connection.close();
        }
    }

    public void editar(Contato contato) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {

            String sql = "update CONTATOS set NOME = ?, EMAIL = ?, TELEFONE = ? where ID = ?";

            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, contato.getNome());
            pstmt.setString(2, contato.getEmail());
            pstmt.setString(3, contato.getTelefone());
            pstmt.setInt(4, contato.getId());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
                throw new SQLException(ex.getMessage());
        } finally {
            con.close();
        }
    }
}
