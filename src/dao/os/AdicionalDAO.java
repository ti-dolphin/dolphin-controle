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
public class AdicionalDAO {

    public int buscarMaiorNumero(int idProjeto) throws SQLException {
        
        Connection con = ConexaoBanco.getConexao();
        Statement stmt = con.createStatement();
        
        try {
            
            String sql = "select max(numero) as NUMERO from ADICIONAIS where ID_PROJETO = " + idProjeto;
            
            ResultSet rs = stmt.executeQuery(sql);

            int numeroAdicional = 0;
            if (rs != null && rs.next()) {
                numeroAdicional = rs.getInt("NUMERO");
            }

            return numeroAdicional;
            
        } catch (Exception e) {
            throw new SQLException("Erro ao buscar número adicional! " + e.getMessage());
        } finally {
            con.close();
            stmt.close();
        }
    }

    public int inserir(Adicional adicional) throws SQLException {
        
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            
            String sql = "insert into ADICIONAIS (ID, NUMERO, ID_PROJETO) values (null, ?, ?)";
            
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setInt(1, adicional.getNumero());
            pstmt.setInt(2, adicional.getProjeto().getId());
            
            pstmt.execute();
            
            int id = 0;
            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            return id;
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir adicional! " + e.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
        
    }

    public ArrayList<Adicional> buscar() throws SQLException {
        
        Connection con = ConexaoBanco.getConexao();
        Statement stmt = con.createStatement();
        
        try {
            
            String sql = "select * from ADICIONAIS where NUMERO = 0";
            
            ResultSet rs = stmt.executeQuery(sql);
            ArrayList<Adicional> adicionais = new ArrayList<>();
            
            while (rs.next()) {
                
                Adicional adicional = new Adicional();
                Projeto projeto = new Projeto();
                
                adicional.setId(rs.getInt("ID"));
                adicional.setNumero(rs.getInt("NUMERO"));
                projeto.setId(rs.getInt("ADICIONAIS.ID_PROJETO"));
                adicional.setProjeto(projeto);
                
                adicionais.add(adicional);
                
            }
            
            return adicionais;
            
        } catch (SQLException e) {
            
            throw new SQLException("Erro ao buscar adicionais! " + e.getMessage());
            
        } finally {
            con.close();
            stmt.close();
        }
        
    }

    public ArrayList<Adicional> buscarPorProjeto(Projeto projeto) throws SQLException {
        
        Connection con = ConexaoBanco.getConexao();
        Statement stmt = con.createStatement();
        
        try {
            
            String sql = "select * from ADICIONAIS where ID_PROJETO = " + projeto.getId();
            
            ResultSet rs = stmt.executeQuery(sql);
            ArrayList<Adicional> adicionais = new ArrayList<>();
            
            
            while(rs.next()) {
                
                Adicional adicional = new Adicional();
                Projeto prj = new Projeto();
                
                adicional.setId(rs.getInt("ID"));
                adicional.setNumero(rs.getInt("NUMERO"));
                prj.setId(rs.getInt("ID_PROJETO"));
                adicional.setProjeto(prj);
                
                adicionais.add(adicional);
                
            }
            
            return adicionais;
            
        } catch (SQLException e) {
            
            throw new SQLException("Erro ao buscar adicionais do projeto! " + e.getMessage());
        } finally {
            con.close();
            stmt.close();
        }
        
    }
}
