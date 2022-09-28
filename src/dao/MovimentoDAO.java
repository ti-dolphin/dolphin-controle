/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.LocalDeEstoque;
import model.Movimento;
import model.MovimentoItem;
import model.Patrimonio;
import model.Produto;
import model.os.Pessoa;
import persistencia.ConexaoBanco;
import view.Menu;

/**
 *
 * @author guilherme.oliveira
 */
public class MovimentoDAO {

    public int inserir(Movimento movimento) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "insert into MOVIMENTO_PRODUTOS (CODPESSOA,"
                    + " CODFILIALESTOQUE_ORIGEM, CODLOC_ORIGEM,"
                    + " CODCOLESTOQUE_ORIGEM, CODFILIALESTOQUE_DESTINO,"
                    + " CODLOC_DESTINO, CODCOLESTOQUE_DESTINO,"
                    + " DATAENTREGA, RECCREATEDBY)"
                    + " values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, movimento.getResponsavel().getCodPessoa());

            pstmt.setInt(2, movimento.getLocalDeEstoqueOrigem().getCodFilial());
            pstmt.setString(3, movimento.getLocalDeEstoqueOrigem().getCodLoc());
            pstmt.setShort(4, movimento.getLocalDeEstoqueOrigem().getCodColigada());
            pstmt.setInt(5, movimento.getLocalDeEstoqueDestino().getCodFilial());
            pstmt.setString(6, movimento.getLocalDeEstoqueDestino().getCodLoc());
            pstmt.setShort(7, movimento.getLocalDeEstoqueDestino().getCodColigada());
            pstmt.setObject(8, movimento.getDataEntrega());
            pstmt.setString(9, Menu.logado.getLogin());

            pstmt.execute();

            int id = 0;
            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            return id;

        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir movimento de produto! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public void alterar(Movimento movimento) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "update MOVIMENTO_PRODUTOS set"
                    + " CODPESSOA = ?, CODFILIALESTOQUE_ORIGEM = ?,"
                    + " CODLOC_ORIGEM = ?, CODCOLESTOQUE_ORIGEM = ?,"
                    + " CODFILIALESTOQUE_DESTINO = ?, CODLOC_DESTINO = ?,"
                    + " CODCOLESTOQUE_DESTINO = ?, DATAENTREGA = ?,"
                    + " RECMODIFIEDBY = ?, RECMODIFIEDON = now()"
                    + " where ID = ?";

            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, movimento.getResponsavel().getCodPessoa());
            pstmt.setInt(2, movimento.getLocalDeEstoqueOrigem().getCodFilial());
            pstmt.setString(3, movimento.getLocalDeEstoqueOrigem().getCodLoc());
            pstmt.setShort(4, movimento.getLocalDeEstoqueOrigem().getCodColigada());
            pstmt.setInt(5, movimento.getLocalDeEstoqueDestino().getCodFilial());
            pstmt.setString(6, movimento.getLocalDeEstoqueDestino().getCodLoc());
            pstmt.setShort(7, movimento.getLocalDeEstoqueDestino().getCodColigada());
            pstmt.setObject(8, movimento.getDataEntrega());
            pstmt.setString(9, Menu.logado.getLogin());
            pstmt.setInt(10, movimento.getId());
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao alterar movimento! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public ArrayList<Movimento> buscar() throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {

            String sql = "select * from MOVIMENTO_PRODUTOS"
                    + " inner join PESSOA"
                    + " on MOVIMENTO_PRODUTOS.CODPESSOA = PESSOA.CODPESSOA"
                    + " inner join LOCALDEESTOQUE ESTOQUE_ORIGEM"
                    + " on MOVIMENTO_PRODUTOS.CODLOC_ORIGEM = ESTOQUE_ORIGEM.CODLOC"
                    + " and MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_ORIGEM = ESTOQUE_ORIGEM.CODFILIAL"
                    + " and MOVIMENTO_PRODUTOS.CODCOLESTOQUE_ORIGEM = ESTOQUE_ORIGEM.CODCOLIGADA"
                    + " inner join LOCALDEESTOQUE ESTOQUE_DESTINO"
                    + " on MOVIMENTO_PRODUTOS.CODLOC_DESTINO = ESTOQUE_DESTINO.CODLOC"
                    + " and MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_DESTINO = ESTOQUE_DESTINO.CODFILIAL"
                    + " and MOVIMENTO_PRODUTOS.CODCOLESTOQUE_DESTINO = ESTOQUE_DESTINO.CODCOLIGADA";

            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<Movimento> movimentos = new ArrayList<>();

            while (rs.next()) {
                Movimento movimento = new Movimento();
                Pessoa responsavel = new Pessoa();
                LocalDeEstoque estoqueOrigem = new LocalDeEstoque();
                LocalDeEstoque estoqueDestino = new LocalDeEstoque();

                responsavel.setCodPessoa(rs.getInt("MOVIMENTO_PRODUTOS.CODPESSOA"));
                responsavel.setNome(rs.getString("PESSOA.NOME"));

                estoqueOrigem.setCodFilial(rs.getShort("MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_ORIGEM"));
                estoqueOrigem.setCodLoc(rs.getString("MOVIMENTO_PRODUTOS.CODLOC_ORIGEM"));
                estoqueOrigem.setCodColigada(rs.getShort("MOVIMENTO_PRODUTOS.CODCOLESTOQUE_ORIGEM"));
                estoqueOrigem.setNome(rs.getString("ESTOQUE_ORIGEM.NOME"));

                estoqueDestino.setCodFilial(rs.getShort("MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_DESTINO"));
                estoqueDestino.setCodLoc(rs.getString("MOVIMENTO_PRODUTOS.CODLOC_DESTINO"));
                estoqueDestino.setCodColigada(rs.getShort("MOVIMENTO_PRODUTOS.CODCOLESTOQUE_DESTINO"));
                estoqueDestino.setNome(rs.getString("ESTOQUE_DESTINO.NOME"));

                movimento.setId(rs.getInt("MOVIMENTO_PRODUTOS.ID"));

                movimento.setResponsavel(responsavel);
                movimento.setDataEntrega(rs.getDate("DATAENTREGA").toLocalDate());
                movimento.setLocalDeEstoqueOrigem(estoqueOrigem);
                movimento.setLocalDeEstoqueDestino(estoqueDestino);

                movimentos.add(movimento);

            }

            return movimentos;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar movimentos de produtos! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public ArrayList<Movimento> filtrar(String query) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stmt = con.createStatement();

        try {
            String sql = "select MOVIMENTO_PRODUTOS.ID, MOVIMENTO_PRODUTOS.DATAENTREGA,"
                    + " MOVIMENTO_PRODUTOS.CODPESSOA, PESSOA.NOME,"
                    + " MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_ORIGEM,"
                    + " MOVIMENTO_PRODUTOS.CODLOC_ORIGEM,"
                    + " MOVIMENTO_PRODUTOS.CODCOLESTOQUE_ORIGEM,"
                    + " ESTOQUE_ORIGEM.NOME,"
                    + " MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_DESTINO,"
                    + " MOVIMENTO_PRODUTOS.CODLOC_DESTINO,"
                    + " MOVIMENTO_PRODUTOS.CODCOLESTOQUE_DESTINO,"
                    + " ESTOQUE_DESTINO.NOME"
                    + " from MOVIMENTO_PRODUTOS"
                    + " inner join PESSOA"
                    + " on MOVIMENTO_PRODUTOS.CODPESSOA = PESSOA.CODPESSOA"
                    + " inner join LOCALDEESTOQUE ESTOQUE_ORIGEM"
                    + " on MOVIMENTO_PRODUTOS.CODLOC_ORIGEM = ESTOQUE_ORIGEM.CODLOC"
                    + " and MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_ORIGEM = ESTOQUE_ORIGEM.CODFILIAL"
                    + " and MOVIMENTO_PRODUTOS.CODCOLESTOQUE_ORIGEM = ESTOQUE_ORIGEM.CODCOLIGADA"
                    + " inner join LOCALDEESTOQUE ESTOQUE_DESTINO"
                    + " on MOVIMENTO_PRODUTOS.CODLOC_DESTINO = ESTOQUE_DESTINO.CODLOC"
                    + " and MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_DESTINO = ESTOQUE_DESTINO.CODFILIAL"
                    + " and MOVIMENTO_PRODUTOS.CODCOLESTOQUE_DESTINO = ESTOQUE_DESTINO.CODCOLIGADA "
                    + query
                    + " order by MOVIMENTO_PRODUTOS.DATAENTREGA desc";

            ArrayList<Movimento> movimentos = new ArrayList<>();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                Movimento movimento = new Movimento();
                Pessoa responsavel = new Pessoa();
                LocalDeEstoque estoqueOrigem = new LocalDeEstoque();
                LocalDeEstoque estoqueDestino = new LocalDeEstoque();

                responsavel.setCodPessoa(rs.getInt("MOVIMENTO_PRODUTOS.CODPESSOA"));
                responsavel.setNome(rs.getString("PESSOA.NOME"));

                estoqueOrigem.setCodFilial(rs.getShort("MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_ORIGEM"));
                estoqueOrigem.setCodLoc(rs.getString("MOVIMENTO_PRODUTOS.CODLOC_ORIGEM"));
                estoqueOrigem.setCodColigada(rs.getShort("MOVIMENTO_PRODUTOS.CODCOLESTOQUE_ORIGEM"));
                estoqueOrigem.setNome(rs.getString("ESTOQUE_ORIGEM.NOME"));

                estoqueDestino.setCodFilial(rs.getShort("MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_DESTINO"));
                estoqueDestino.setCodLoc(rs.getString("MOVIMENTO_PRODUTOS.CODLOC_DESTINO"));
                estoqueDestino.setCodColigada(rs.getShort("MOVIMENTO_PRODUTOS.CODCOLESTOQUE_DESTINO"));
                estoqueDestino.setNome(rs.getString("ESTOQUE_DESTINO.NOME"));

                movimento.setId(rs.getInt("MOVIMENTO_PRODUTOS.ID"));

                movimento.setResponsavel(responsavel);
                movimento.setDataEntrega(rs.getDate("DATAENTREGA").toLocalDate());
                movimento.setLocalDeEstoqueOrigem(estoqueOrigem);
                movimento.setLocalDeEstoqueDestino(estoqueDestino);

                movimentos.add(movimento);
            }

            return movimentos;
        } catch (SQLException e) {
            throw new SQLException("Erro ao filtrar movimentos! " + e.getMessage());
        } finally {
            con.close();
            stmt.close();
        }
    }
}
