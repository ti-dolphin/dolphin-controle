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
import model.CheckListModelo;
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
public class MovimentoItemDAO {

    public int inserir(MovimentoItem movimentoItem) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {

            String sql = "insert into MOVIMENTO_ITEM (IDPRODUTO, IDMOVIMENTO, CODCOLPRODUTO,"
                    + " IDPATRIMONIO, CODCOLPATRIMONIO, ID_ORIGEM, RECCREATEDBY)"
                    + " values(?, ?, ?, ?, ?, ?, ?)";

            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, movimentoItem.getProduto().getId());
            pstmt.setInt(2, movimentoItem.getMovimento().getId());
            pstmt.setInt(3, movimentoItem.getProduto().getCodColigada());
            pstmt.setInt(4, movimentoItem.getPatrimonio().getId());
            pstmt.setInt(5, movimentoItem.getPatrimonio().getCodColigada());
            pstmt.setInt(6, movimentoItem.getIdOrigem());
            pstmt.setString(7, Menu.logado.getLogin());

            pstmt.execute();

            int id = 0;
            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            return id;

        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir item do movimento! " + e.getMessage());
        } finally {
            con.close();
        }
    }
    
    public void alterarItemMovimentado(boolean movimentado, int idOrigem) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {

            String sql = "update MOVIMENTO_ITEM set MOVIMENTADO = ? where ID = ?";
            
            pstmt = con.prepareStatement(sql);
            
            pstmt.setBoolean(1, movimentado);
            pstmt.setInt(2, idOrigem);
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao alterar item do movimento! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    
    public ArrayList<MovimentoItem> buscarPorMovimento(Movimento movimento) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "select * from MOVIMENTO_ITEM"
                    + " inner join PRODUTOS on MOVIMENTO_ITEM.IDPRODUTO = PRODUTOS.ID"
                    + " inner join PATRIMONIOS on MOVIMENTO_ITEM.IDPATRIMONIO = PATRIMONIOS.ID"
                    + " where IDMOVIMENTO = " + movimento.getId();

            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<MovimentoItem> itens = new ArrayList();

            while (rs.next()) {
                MovimentoItem item = new MovimentoItem();
                Produto produto = new Produto();
                Patrimonio patrimonio = new Patrimonio();

                item.setId(rs.getInt("MOVIMENTO_ITEM.ID"));

                produto.setId(rs.getInt("MOVIMENTO_ITEM.IDPRODUTO"));
                produto.setCodColigada(rs.getShort("MOVIMENTO_ITEM.CODCOLPRODUTO"));
                produto.setNomeFantasia(rs.getString("PRODUTOS.NOMEFANTASIA"));

                patrimonio.setId(rs.getInt("PATRIMONIOS.ID"));
                patrimonio.setCodColigada(rs.getShort("MOVIMENTO_ITEM.CODCOLPATRIMONIO"));
                patrimonio.setCodPatrimonio(rs.getString("PATRIMONIOS.PATRIMONIO"));
                patrimonio.setDescricao(rs.getString("PATRIMONIOS.DESCRICAO"));

                item.setProduto(produto);
                item.setPatrimonio(patrimonio);
                item.setMovimentado(rs.getBoolean("MOVIMENTO_ITEM.MOVIMENTADO"));
                item.setIdOrigem(rs.getInt("MOVIMENTO_ITEM.ID_ORIGEM"));
                
                itens.add(item);
            }

            return itens;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar itens do movimento! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public ArrayList<MovimentoItem> buscarProdutosPorLocalDeEstoqueOrigem(LocalDeEstoque estoqueOrigem) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "select MOVIMENTO_ITEM.ID,"
                    
                    + " MOVIMENTO_ITEM.IDPRODUTO,"
                    + " MOVIMENTO_ITEM.CODCOLPRODUTO, PRODUTOS.NOMEFANTASIA"
                    
                    + " from MOVIMENTO_ITEM\n"
                    
                    + "	inner join MOVIMENTO_PRODUTOS\n"
                    + "		on MOVIMENTO_ITEM.IDMOVIMENTO = MOVIMENTO_PRODUTOS.ID\n"
                    
                    + "	inner join PRODUTOS\n"
                    + "		on MOVIMENTO_ITEM.IDPRODUTO = PRODUTOS.ID"
                    + "              and MOVIMENTO_ITEM.CODCOLPRODUTO = PRODUTOS.CODCOLPRD\n"
                    
                    + "	inner join LOCALDEESTOQUE ESTOQUE_ORIGEM\n"
                    + "		on MOVIMENTO_PRODUTOS.CODLOC_ORIGEM = ESTOQUE_ORIGEM.CODLOC \n"
                    + "			and MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_ORIGEM = ESTOQUE_ORIGEM.CODFILIAL \n"
                    + "            and MOVIMENTO_PRODUTOS.CODCOLESTOQUE_ORIGEM = ESTOQUE_ORIGEM.CODCOLIGADA\n"
                    
                    + "	inner join LOCALDEESTOQUE ESTOQUE_DESTINO\n"
                    + "		on MOVIMENTO_PRODUTOS.CODLOC_DESTINO = ESTOQUE_DESTINO.CODLOC \n"
                    + "			and MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_DESTINO = ESTOQUE_DESTINO.CODFILIAL \n"
                    + "            and MOVIMENTO_PRODUTOS.CODCOLESTOQUE_DESTINO = ESTOQUE_DESTINO.CODCOLIGADA\n"
                    
                    + " where MOVIMENTO_PRODUTOS.CODLOC_DESTINO = ? \n"
                    + "	   and MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_DESTINO = ? \n"
                    + "    and MOVIMENTO_PRODUTOS.CODCOLESTOQUE_DESTINO = ? \n"
                    + "    and MOVIMENTO_ITEM.MOVIMENTADO = FALSE"
                    + "    and MOVIMENTO_ITEM.IDPRODUTO > 0";

            pstmt = con.prepareStatement(sql);
            
            pstmt.setString(1, estoqueOrigem.getCodLoc());
            pstmt.setInt(2, estoqueOrigem.getCodFilial());
            pstmt.setShort(3, estoqueOrigem.getCodColigada());
            
            ResultSet rs = pstmt.executeQuery();

            ArrayList<MovimentoItem> itens = new ArrayList<>();

            while (rs.next()) {

                MovimentoItem item = new MovimentoItem();
                Produto produto = new Produto();

                item.setIdOrigem(rs.getInt("MOVIMENTO_ITEM.ID"));

                produto.setId(rs.getInt("MOVIMENTO_ITEM.IDPRODUTO"));
                produto.setCodColigada(rs.getShort("MOVIMENTO_ITEM.CODCOLPRODUTO"));
                produto.setNomeFantasia(rs.getString("PRODUTOS.NOMEFANTASIA"));

                item.setProduto(produto);

                itens.add(item);

            }

            return itens;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar produtos do movimento de origem! " + e.getMessage());
        } finally {
            con.close();
        }
    }
    public ArrayList<MovimentoItem> buscarPatrimoniosPorLocalDeEstoqueOrigem(LocalDeEstoque estoqueOrigem) throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;

        try {
            String sql = "select MOVIMENTO_ITEM.ID,"
                    
                    + " MOVIMENTO_ITEM.IDPATRIMONIO,"
                    + " MOVIMENTO_ITEM.CODCOLPATRIMONIO,  PATRIMONIOS.PATRIMONIO, PATRIMONIOS.DESCRICAO"
                    
                    + " from MOVIMENTO_ITEM\n"
                    
                    + "	inner join MOVIMENTO_PRODUTOS\n"
                    + "		on MOVIMENTO_ITEM.IDMOVIMENTO = MOVIMENTO_PRODUTOS.ID\n"
                                        
                    + "	inner join PATRIMONIOS\n"
                    + "		on MOVIMENTO_ITEM.IDPATRIMONIO = PATRIMONIOS.ID"
                    + "              and MOVIMENTO_ITEM.CODCOLPATRIMONIO = PATRIMONIOS.CODCOLIGADA\n"
                    
                    + "	inner join LOCALDEESTOQUE ESTOQUE_ORIGEM\n"
                    + "		on MOVIMENTO_PRODUTOS.CODLOC_ORIGEM = ESTOQUE_ORIGEM.CODLOC \n"
                    + "			and MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_ORIGEM = ESTOQUE_ORIGEM.CODFILIAL \n"
                    + "            and MOVIMENTO_PRODUTOS.CODCOLESTOQUE_ORIGEM = ESTOQUE_ORIGEM.CODCOLIGADA\n"
                    
                    + "	inner join LOCALDEESTOQUE ESTOQUE_DESTINO\n"
                    + "		on MOVIMENTO_PRODUTOS.CODLOC_DESTINO = ESTOQUE_DESTINO.CODLOC \n"
                    + "			and MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_DESTINO = ESTOQUE_DESTINO.CODFILIAL \n"
                    + "            and MOVIMENTO_PRODUTOS.CODCOLESTOQUE_DESTINO = ESTOQUE_DESTINO.CODCOLIGADA\n"
                    
                    + " where MOVIMENTO_PRODUTOS.CODLOC_DESTINO = ? \n"
                    + "	   and MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_DESTINO = ? \n"
                    + "    and MOVIMENTO_PRODUTOS.CODCOLESTOQUE_DESTINO = ? \n"
                    + "    and MOVIMENTO_ITEM.MOVIMENTADO = FALSE"
                    + "    and MOVIMENTO_ITEM.IDPATRIMONIO > 0";

            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, estoqueOrigem.getCodLoc());
            pstmt.setInt(2, estoqueOrigem.getCodFilial());
            pstmt.setShort(3, estoqueOrigem.getCodColigada());

            ResultSet rs = pstmt.executeQuery();

            ArrayList<MovimentoItem> itens = new ArrayList<>();

            while (rs.next()) {

                MovimentoItem item = new MovimentoItem();
                Patrimonio patrimonio = new Patrimonio();

                item.setIdOrigem(rs.getInt("MOVIMENTO_ITEM.ID"));

                patrimonio.setId(rs.getInt("MOVIMENTO_ITEM.IDPATRIMONIO"));
                patrimonio.setCodColigada(rs.getShort("MOVIMENTO_ITEM.CODCOLPATRIMONIO"));
                patrimonio.setCodPatrimonio(rs.getString("PATRIMONIOS.PATRIMONIO"));
                patrimonio.setDescricao(rs.getString("PATRIMONIOS.DESCRICAO"));

                item.setPatrimonio(patrimonio);

                itens.add(item);

            }

            return itens;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar patrimônios do movimento de origem! " + e.getMessage());
        } finally {
            con.close();
        }
    }

    public ArrayList<MovimentoItem> buscarProdutosDoMovimento() throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {

            String sql = "select * from PRODUTOS";
            ResultSet rs = stat.executeQuery(sql);

            ArrayList<MovimentoItem> itens = new ArrayList<>();

            while (rs.next()) {

                MovimentoItem item = new MovimentoItem();
                Produto produto = new Produto();

                item.setIdOrigem(0);

                produto.setId(rs.getInt("ID"));
                produto.setCodColigada(rs.getShort("CODCOLPRD"));
                produto.setNomeFantasia(rs.getString("NOMEFANTASIA"));

                item.setProduto(produto);

                itens.add(item);

            }

            return itens;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar produtos do movimento de origem! " + e.getMessage());
        } finally {
            con.close();
        }
    }
    public ArrayList<MovimentoItem> buscarPatrimoniosDoMovimento() throws SQLException {

        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {

            String sql = "select * from PATRIMONIOS";
            ResultSet rs = stat.executeQuery(sql);

            ArrayList<MovimentoItem> itens = new ArrayList<>();

            while (rs.next()) {

                MovimentoItem item = new MovimentoItem();
                Patrimonio patrimonio = new Patrimonio();

                item.setIdOrigem(0);
                
                patrimonio.setId(rs.getInt("ID"));
                patrimonio.setCodColigada(rs.getShort("CODCOLIGADA"));
                patrimonio.setCodPatrimonio(rs.getString("PATRIMONIO"));
                patrimonio.setDescricao(rs.getString("DESCRICAO"));

                item.setPatrimonio(patrimonio);

                itens.add(item);

            }

            return itens;

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar patrimônios do movimento de origem! " + e.getMessage());
        } finally {
            con.close();
        }
    }
    
    public ArrayList<MovimentoItem> filtrar(String query) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stmt = con.createStatement();

        try {
            
            String sql = "select MOVIMENTO_ITEM.ID, MOVIMENTO_ITEM.IDMOVIMENTO, MOVIMENTO_PRODUTOS.DATAENTREGA,"
                    
                    + " MOVIMENTO_PRODUTOS.ID,"
                    
                    + " MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_ORIGEM, MOVIMENTO_PRODUTOS.CODLOC_ORIGEM,"
                    + " MOVIMENTO_PRODUTOS.CODCOLESTOQUE_ORIGEM, ESTOQUE_ORIGEM.NOME,"
                    
                    + " MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_DESTINO, MOVIMENTO_PRODUTOS.CODLOC_DESTINO,"
                    + " MOVIMENTO_PRODUTOS.CODCOLESTOQUE_DESTINO, ESTOQUE_DESTINO.NOME,"
                    
                    + " MOVIMENTO_PRODUTOS.CODPESSOA, PESSOA.NOME,"
                    
                    + " MOVIMENTO_ITEM.IDPRODUTO, MOVIMENTO_ITEM.CODCOLPRODUTO, PRODUTOS.NOMEFANTASIA,"
                    
                    + " MOVIMENTO_ITEM.IDPATRIMONIO, MOVIMENTO_ITEM.CODCOLPATRIMONIO,"
                    + " PATRIMONIOS.DESCRICAO, PATRIMONIOS.PATRIMONIO, PATRIMONIOS.ID_CHECKLIST_MODELO"
                    
                    + " from MOVIMENTO_ITEM"
                    
                    + " inner join MOVIMENTO_PRODUTOS"
                    + " on MOVIMENTO_ITEM.IDMOVIMENTO = MOVIMENTO_PRODUTOS.ID"
                    
                    + " inner join LOCALDEESTOQUE ESTOQUE_ORIGEM"
                    + " on MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_ORIGEM = ESTOQUE_ORIGEM.CODFILIAL"
                    + " and MOVIMENTO_PRODUTOS.CODLOC_ORIGEM = ESTOQUE_ORIGEM.CODLOC"
                    + " and MOVIMENTO_PRODUTOS.CODCOLESTOQUE_ORIGEM = ESTOQUE_ORIGEM.CODCOLIGADA"
                    
                    + " inner join LOCALDEESTOQUE ESTOQUE_DESTINO"
                    + " on MOVIMENTO_PRODUTOS.CODFILIALESTOQUE_DESTINO = ESTOQUE_DESTINO.CODFILIAL"
                    + " and MOVIMENTO_PRODUTOS.CODLOC_DESTINO = ESTOQUE_DESTINO.CODLOC"
                    + " and MOVIMENTO_PRODUTOS.CODCOLESTOQUE_DESTINO = ESTOQUE_DESTINO.CODCOLIGADA"
                    
                    + " inner join PESSOA"
                    + " on MOVIMENTO_PRODUTOS.CODPESSOA = PESSOA.CODPESSOA"
                    
                    + " inner join PRODUTOS"
                    + " on MOVIMENTO_ITEM.IDPRODUTO = PRODUTOS.ID"
                    + " and MOVIMENTO_ITEM.CODCOLPRODUTO = PRODUTOS.CODCOLPRD"
                    
                    + " inner join PATRIMONIOS"
                    + " on MOVIMENTO_ITEM.IDPATRIMONIO = PATRIMONIOS.ID"
                    + " and MOVIMENTO_ITEM.CODCOLPATRIMONIO = PATRIMONIOS.CODCOLIGADA "
                    + query
                    + " order by MOVIMENTO_PRODUTOS.DATAENTREGA desc";

            ArrayList<MovimentoItem> itens = new ArrayList<>();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {

                MovimentoItem item = new MovimentoItem();
                Movimento movimento = new Movimento();
                Pessoa responsavel = new Pessoa();
                LocalDeEstoque estoqueOrigem = new LocalDeEstoque();
                LocalDeEstoque estoqueDestino = new LocalDeEstoque();
                Produto produto = new Produto();
                Patrimonio patrimonio = new Patrimonio();
                CheckListModelo checkListModelo = new CheckListModelo();

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

                produto.setId(rs.getInt("MOVIMENTO_ITEM.IDPRODUTO"));
                produto.setCodColigada(rs.getShort("MOVIMENTO_ITEM.CODCOLPRODUTO"));
                produto.setNomeFantasia(rs.getString("PRODUTOS.NOMEFANTASIA"));
                
                patrimonio.setId(rs.getInt("MOVIMENTO_ITEM.IDPATRIMONIO"));
                patrimonio.setCodColigada(rs.getShort("MOVIMENTO_ITEM.CODCOLPATRIMONIO"));
                patrimonio.setDescricao(rs.getString("PATRIMONIOS.DESCRICAO"));
                patrimonio.setCodPatrimonio(rs.getString("PATRIMONIOS.PATRIMONIO"));
                checkListModelo.setId(rs.getInt(rs.getInt("PATRIMONIOS.ID_CHECKLIST_MODELO")));
                patrimonio.setCheckListModelo(checkListModelo);
                                
                item.setId(rs.getInt("MOVIMENTO_ITEM.ID"));

                movimento.setResponsavel(responsavel);
                movimento.setDataEntrega(rs.getDate("DATAENTREGA").toLocalDate());
                movimento.setLocalDeEstoqueOrigem(estoqueOrigem);
                movimento.setLocalDeEstoqueDestino(estoqueDestino);
                
                item.setProduto(produto);
                item.setPatrimonio(patrimonio);
                item.setMovimento(movimento);
                
                itens.add(item);
            }

            return itens;
            
        } catch (SQLException e) {
            throw new SQLException("Erro ao filtrar itens movimentados! " + e.getMessage());
        } finally {
            con.close();
            stmt.close();
        }
        
    }
    
    public void deletar(MovimentoItem item) throws SQLException {
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();

        try {
            String sql;
            sql = "delete from MOVIMENTO_ITEM where ID = " + item.getId();

            stat.execute(sql);
        } catch (SQLException se) {
            throw new SQLException("Erro ao remover item do movimento! " + se.getMessage());
        } finally {
            stat.close();
            con.close();
        }//finally
    }
    
}
