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
import model.os.Adicional;
import model.os.Projeto;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class ProjetoDAO {

    public int inserir() throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {

            String sql = "insert into PROJETOS(ID) values(null)";

            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.execute();

            int id = 0;
            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            return id;

        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir projeto! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }
    }

    public ArrayList<Projeto> buscar(boolean isComercial) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        Statement stmt = con.createStatement();

        try {

            String sql;

            sql = "select \n"
                    + "ORDEMSERVICO.ID_PROJETO, ADICIONAIS.NUMERO, ORDEMSERVICO.NOME, ORDEMSERVICO.ID_ADICIONAL\n"
                    + "from ORDEMSERVICO\n"
                    + "left JOIN ADICIONAIS ON \n"
                    + "    ADICIONAIS.ID = ORDEMSERVICO.ID_ADICIONAL\n"
                    + "    and ADICIONAIS.NUMERO <> 0 \n"
                    + "where ORDEMSERVICO.CODTIPOOS = 21\n";
            
            if (isComercial) {
                
                sql = sql + "and ADICIONAIS.NUMERO IS NULL";
            }

            ResultSet rs = stmt.executeQuery(sql);

            ArrayList<Projeto> projetos = new ArrayList<>();

            while (rs.next()) {

                Projeto projeto = new Projeto();
                Adicional adicional = new Adicional();

                projeto.setId(rs.getInt("ORDEMSERVICO.ID_PROJETO"));
                projeto.setDescricao(rs.getString("ORDEMSERVICO.NOME"));

                adicional.setId(rs.getInt("ORDEMSERVICO.ID_ADICIONAL"));
                adicional.setNumero(rs.getInt("ADICIONAIS.NUMERO"));

                projeto.setAdicional(adicional);

                projetos.add(projeto);
            }

            return projetos;

        } catch (Exception e) {
            throw new SQLException("Erro ao buscar! " + e.getMessage());
        } finally {
            con.close();
            stmt.close();
        }
    }
}
