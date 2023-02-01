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
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class PessoaDAO {
    
    public int inserir(Pessoa p) throws SQLException{
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            String sql = "insert into PESSOA(CODPESSOA,"
                    + " NOME, LOGIN, SENHA, SOLICITANTE, RESPONSAVEL,"
                    + " LIDER, PERM_LOGIN, PERM_EPI, PERM_AUTENTICACAO, PERM_OS,"
                    + " PERM_TIPO, PERM_STATUS, PERM_APONT, PERM_STATUS_APONT, PERM_PESSOAS,"
                    + " PERM_COMENT_OS, PERM_COMENT_APONT, ATIVO, EMAIL, PERM_PONTO,"
                    + " PERM_VENDA, PERM_DESCONTADO, PERM_CADEPI, PERM_CUSTO, PERM_FERRAMENTAS,"
                    + " PERM_CHECKLIST, PERM_PROSPECCAO, PERM_APONTAMENTO_PONTO, PERM_APONTAMENTO_PONTO_JUSTIFICATIVA, PERM_BANCO_HORAS,"
                    + " PERM_FOLGA)"
                    + " values (null,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?)";
            
            pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            pstmt.setString(1, p.getNome());
            pstmt.setString(2, p.getLogin());
            pstmt.setString(3, p.getSenha());            
            pstmt.setBoolean(4, p.isSolicitante());
            pstmt.setBoolean(5, p.isResponsavel());
            
            pstmt.setBoolean(6, p.isLider());
            pstmt.setBoolean(7, p.isPermLogin());
            pstmt.setBoolean(8, p.isPermEpi());
            pstmt.setBoolean(9, p.isPermAutenticacao());
            pstmt.setBoolean(10, p.isPermOS());
            
            pstmt.setBoolean(11, p.isPermTipo());
            pstmt.setBoolean(12, p.isPermStatus());
            pstmt.setBoolean(13, p.isPermApont());
            pstmt.setBoolean(14, p.isPermStatusApont());
            pstmt.setBoolean(15, p.isPermPessoas());
            
            pstmt.setBoolean(16, p.isPermComentOs());
            pstmt.setBoolean(17, p.isPermComentApont());
            pstmt.setBoolean(18, p.isAtivo());
            pstmt.setString(19, p.getEmail());
            pstmt.setBoolean(20, p.isPermPonto());
            
            pstmt.setBoolean(21, p.isPermVenda());
            pstmt.setBoolean(22, p.isPermDescontado());
            pstmt.setBoolean(23, p.isPermCadEpi());
            pstmt.setBoolean(24, p.isPermCustoMO());
            pstmt.setBoolean(25, p.isPermFerramentas());
            
            pstmt.setBoolean(26, p.isPermCadCheckList());
            pstmt.setBoolean(27, p.isPermProspeccao());
            pstmt.setBoolean(28, p.isPermApontamentoPonto());
            pstmt.setBoolean(29, p.isPermApontamentoPontoJustificativa());
            pstmt.setBoolean(30, p.isPermBancoHoras());
            
            pstmt.setBoolean(31, p.isPermFolga());
            
            pstmt.execute();
            
            int id = 0;
            final ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            return id;
            
        } catch (SQLException se) {
            throw new SQLException("Erro ao cadastrar pessoa! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha cadastrar
    
    public ArrayList<Pessoa> buscar() throws SQLException{
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            String sql = "select * from PESSOA where CODPESSOA > 1";
            
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<Pessoa> pa = new ArrayList<>();
            while (rs.next()) {
                Pessoa p = new Pessoa();

                p.setCodPessoa(rs.getInt("CODPESSOA"));
                p.setNome(rs.getString("NOME"));
                p.setLogin(rs.getString("LOGIN"));
                p.setSenha(rs.getString("SENHA"));
                p.setSolicitante(rs.getBoolean("SOLICITANTE"));
                p.setResponsavel(rs.getBoolean("RESPONSAVEL"));
                p.setLider(rs.getBoolean("LIDER"));
                p.setPermLogin(rs.getBoolean("PERM_LOGIN"));
                p.setPermEpi(rs.getBoolean("PERM_EPI"));
                p.setPermAutenticacao(rs.getBoolean("PERM_AUTENTICACAO"));
                p.setPermOS(rs.getBoolean("PERM_OS"));
                p.setPermTipo(rs.getBoolean("PERM_TIPO"));
                p.setPermStatus(rs.getBoolean("PERM_STATUS"));
                p.setPermApont(rs.getBoolean("PERM_APONT"));
                p.setPermStatusApont(rs.getBoolean("PERM_STATUS_APONT"));
                p.setPermPessoas(rs.getBoolean("PERM_PESSOAS"));
                p.setPermPonto(rs.getBoolean("PERM_PONTO"));
                p.setPermComentOs(rs.getBoolean("PERM_COMENT_OS"));
                p.setPermComentApont(rs.getBoolean("PERM_COMENT_APONT"));
                p.setEmail(rs.getString("EMAIL"));
                p.setPermVenda(rs.getBoolean("PERM_VENDA"));
                p.setPermCadEpi(rs.getBoolean("PERM_CADEPI"));
                p.setPermDescontado(rs.getBoolean("PERM_DESCONTADO"));
                p.setAtivo(rs.getBoolean("ATIVO"));
                p.setPermControleRecesso(rs.getBoolean("PERM_CONTROLE_RECESSO"));
                p.setPermGestaoPessoas(rs.getBoolean("PERM_GESTAO_PESSOAS"));
                p.setPermCustoMO(rs.getBoolean("PERM_CUSTO"));
                p.setPermFerramentas(rs.getBoolean("PERM_FERRAMENTAS"));
                p.setPermCadCheckList(rs.getBoolean("PERM_CHECKLIST"));
                p.setPermProspeccao(rs.getBoolean("PERM_PROSPECCAO"));
                p.setPermApontamentoPonto(rs.getBoolean("PERM_APONTAMENTO_PONTO"));
                p.setPermApontamentoPontoJustificativa(rs.getBoolean("PERM_APONTAMENTO_PONTO_JUSTIFICATIVA"));
                p.setPermBancoHoras(rs.getBoolean("PERM_BANCO_HORAS"));
                p.setPermFolga(rs.getBoolean("PERM_FOLGA"));

                pa.add(p);
            }

            return pa;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar pessoa! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha buscar
    
    public ArrayList<Pessoa> buscarAtivos() throws SQLException{
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            String sql = "select * from PESSOA where CODPESSOA > 1 and ATIVO = TRUE";
            
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<Pessoa> pa = new ArrayList<>();
            while (rs.next()) {
                Pessoa p = new Pessoa();

                p.setCodPessoa(rs.getInt("CODPESSOA"));
                p.setNome(rs.getString("NOME"));
                p.setLogin(rs.getString("LOGIN"));
                p.setSenha(rs.getString("SENHA"));
                p.setSolicitante(rs.getBoolean("SOLICITANTE"));
                p.setResponsavel(rs.getBoolean("RESPONSAVEL"));
                p.setLider(rs.getBoolean("LIDER"));
                p.setPermLogin(rs.getBoolean("PERM_LOGIN"));
                p.setPermEpi(rs.getBoolean("PERM_EPI"));
                p.setPermAutenticacao(rs.getBoolean("PERM_AUTENTICACAO"));
                p.setPermOS(rs.getBoolean("PERM_OS"));
                p.setPermTipo(rs.getBoolean("PERM_TIPO"));
                p.setPermStatus(rs.getBoolean("PERM_STATUS"));
                p.setPermApont(rs.getBoolean("PERM_APONT"));
                p.setPermStatusApont(rs.getBoolean("PERM_STATUS_APONT"));
                p.setPermPessoas(rs.getBoolean("PERM_PESSOAS"));
                p.setPermPonto(rs.getBoolean("PERM_PONTO"));
                p.setPermComentOs(rs.getBoolean("PERM_COMENT_OS"));
                p.setPermComentApont(rs.getBoolean("PERM_COMENT_APONT"));
                p.setEmail(rs.getString("EMAIL"));
                p.setPermVenda(rs.getBoolean("PERM_VENDA"));
                p.setPermCadEpi(rs.getBoolean("PERM_CADEPI"));
                p.setPermDescontado(rs.getBoolean("PERM_DESCONTADO"));
                p.setAtivo(rs.getBoolean("ATIVO"));
                p.setPermControleRecesso(rs.getBoolean("PERM_CONTROLE_RECESSO"));
                p.setPermGestaoPessoas(rs.getBoolean("PERM_GESTAO_PESSOAS"));
                p.setPermCustoMO(rs.getBoolean("PERM_CUSTO"));
                p.setPermFerramentas(rs.getBoolean("PERM_FERRAMENTAS"));
                p.setPermCadCheckList(rs.getBoolean("PERM_CHECKLIST"));
                p.setPermProspeccao(rs.getBoolean("PERM_PROSPECCAO"));
                p.setPermApontamentoPonto(rs.getBoolean("PERM_APONTAMENTO_PONTO"));
                p.setPermApontamentoPontoJustificativa(rs.getBoolean("PERM_APONTAMENTO_PONTO_JUSTIFICATIVA"));
                p.setPermBancoHoras(rs.getBoolean("PERM_BANCO_HORAS"));
                p.setPermFolga(rs.getBoolean("PERM_FOLGA"));

                pa.add(p);
            }

            return pa;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar pessoa! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha buscar
    
    public ArrayList<Pessoa> buscarPessoasTbl(boolean ativos) throws SQLException{
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            String sql;
            if(ativos) {
                sql = "select * from PESSOA where CODPESSOA > 2 and ATIVO = true order by NOME";
            } else {
                sql = "select * from PESSOA where CODPESSOA > 2 order by NOME";
            }
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<Pessoa> pa = new ArrayList<>();
            while (rs.next()) {
                Pessoa p = new Pessoa();
                
                p.setCodPessoa(rs.getInt("CODPESSOA"));
                p.setNome(rs.getString("NOME"));
                p.setLogin(rs.getString("LOGIN"));
                p.setSenha(rs.getString("SENHA"));
                p.setSolicitante(rs.getBoolean("SOLICITANTE"));
                p.setResponsavel(rs.getBoolean("RESPONSAVEL"));
                p.setLider(rs.getBoolean("LIDER"));
                p.setPermLogin(rs.getBoolean("PERM_LOGIN"));
                p.setPermEpi(rs.getBoolean("PERM_EPI"));
                p.setPermAutenticacao(rs.getBoolean("PERM_AUTENTICACAO"));
                p.setPermOS(rs.getBoolean("PERM_OS"));
                p.setPermTipo(rs.getBoolean("PERM_TIPO"));
                p.setPermStatus(rs.getBoolean("PERM_STATUS"));
                p.setPermApont(rs.getBoolean("PERM_APONT"));
                p.setPermStatusApont(rs.getBoolean("PERM_STATUS_APONT"));
                p.setPermPessoas(rs.getBoolean("PERM_PESSOAS"));
                p.setPermFerramentas(rs.getBoolean("PERM_FERRAMENTAS"));
                p.setPermCadCheckList(rs.getBoolean("PERM_CHECKLIST"));
                p.setPermPonto(rs.getBoolean("PERM_PONTO"));
                p.setPermComentOs(rs.getBoolean("PERM_COMENT_OS"));
                p.setPermComentApont(rs.getBoolean("PERM_COMENT_APONT"));
                p.setEmail(rs.getString("EMAIL"));
                p.setPermVenda(rs.getBoolean("PERM_VENDA"));
                p.setPermDescontado(rs.getBoolean("PERM_DESCONTADO"));
                p.setPermCustoMO(rs.getBoolean("PERM_CUSTO"));
                p.setPermProspeccao(rs.getBoolean("PERM_PROSPECCAO"));
                p.setPermApontamentoPonto(rs.getBoolean("PERM_APONTAMENTO_PONTO"));
                p.setPermBancoHoras(rs.getBoolean("PERM_BANCO_HORAS"));
                p.setPermFolga(rs.getBoolean("PERM_FOLGA"));
                p.setAtivo(rs.getBoolean("ATIVO"));
                                
                pa.add(p);
            }
            
            return pa;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar pessoa! "+se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha buscar
    
    public ArrayList<Pessoa> buscarPessoasCombo(String tipoPessoa) throws SQLException{
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            String sql;
            switch (tipoPessoa) {
                case "lider":
                    sql = "SELECT * FROM PESSOA WHERE LIDER = TRUE AND ATIVO = TRUE ORDER BY NOME";
                    break;
                case "solicitante":
                    sql = "SELECT * FROM PESSOA WHERE SOLICITANTE = TRUE AND ATIVO = TRUE ORDER BY NOME";
                    break;
                case "responsavel":
                    sql = "SELECT * FROM PESSOA WHERE RESPONSAVEL = TRUE AND ATIVO = TRUE ORDER BY NOME";
                    break;
                case "gerente":
                    sql = "SELECT * FROM PESSOA WHERE CODGERENTE > 0 ORDER BY NOME";
                    break;
                default:
                    sql = "SELECT * FROM PESSOA WHERE CODPESSOA <> 2 AND ATIVO = TRUE ORDER BY NOME";
                    break;
            }
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<Pessoa> pa = new ArrayList<>();
            while (rs.next()) {
                Pessoa p = new Pessoa();
                
                p.setCodPessoa(rs.getInt("CODPESSOA"));
                p.setNome(rs.getString("NOME"));
                p.setLogin(rs.getString("LOGIN"));
                p.setSenha(rs.getString("SENHA"));
                p.setSolicitante(rs.getBoolean("SOLICITANTE"));
                p.setResponsavel(rs.getBoolean("RESPONSAVEL"));
                p.setLider(rs.getBoolean("LIDER"));
                p.setPermLogin(rs.getBoolean("PERM_LOGIN"));
                p.setPermEpi(rs.getBoolean("PERM_EPI"));
                p.setPermAutenticacao(rs.getBoolean("PERM_AUTENTICACAO"));
                p.setPermOS(rs.getBoolean("PERM_OS"));
                p.setPermTipo(rs.getBoolean("PERM_TIPO"));
                p.setPermStatus(rs.getBoolean("PERM_STATUS"));
                p.setPermApont(rs.getBoolean("PERM_APONT"));
                p.setPermStatusApont(rs.getBoolean("PERM_STATUS_APONT"));
                p.setPermPessoas(rs.getBoolean("PERM_PESSOAS"));
                p.setPermFerramentas(rs.getBoolean("PERM_FERRAMENTAS"));
                p.setPermCadCheckList(rs.getBoolean("PERM_CHECKLIST"));
                p.setPermPonto(rs.getBoolean("PERM_PONTO"));
                p.setPermComentOs(rs.getBoolean("PERM_COMENT_OS"));
                p.setPermComentApont(rs.getBoolean("PERM_COMENT_APONT"));
                p.setEmail(rs.getString("EMAIL"));
                p.setPermVenda(rs.getBoolean("PERM_VENDA"));
                p.setPermDescontado(rs.getBoolean("PERM_DESCONTADO"));
                p.setPermCustoMO(rs.getBoolean("PERM_CUSTO"));
                p.setPermProspeccao(rs.getBoolean("PERM_PROSPECCAO"));
                p.setPermBancoHoras(rs.getBoolean("PERM_BANCO_HORAS"));
                p.setPermFolga(rs.getBoolean("PERM_FOLGA"));
                p.setAtivo(rs.getBoolean("ATIVO"));
                
                pa.add(p);
            }
            
            return pa;
        } catch (SQLException se) {
            throw new SQLException("Erro ao buscar pessoas (combobox)! "+se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha buscar
      
    public ArrayList<Pessoa> filtrar(String query) throws SQLException{
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            String sql = "select * from PESSOA where CODPESSOA > 1 " + query;
            
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery(sql);
            ArrayList<Pessoa> pa = new ArrayList<>();
            while (rs.next()) {
                Pessoa p = new Pessoa();
                
                p.setCodPessoa(rs.getInt("CODPESSOA"));
                p.setNome(rs.getString("NOME"));
                p.setLogin(rs.getString("LOGIN"));
                p.setSenha(rs.getString("SENHA"));
                p.setSolicitante(rs.getBoolean("SOLICITANTE"));
                p.setResponsavel(rs.getBoolean("RESPONSAVEL"));
                p.setLider(rs.getBoolean("LIDER"));
                p.setPermLogin(rs.getBoolean("PERM_LOGIN"));
                p.setPermEpi(rs.getBoolean("PERM_EPI"));
                p.setPermAutenticacao(rs.getBoolean("PERM_AUTENTICACAO"));
                p.setPermOS(rs.getBoolean("PERM_OS"));
                p.setPermTipo(rs.getBoolean("PERM_TIPO"));
                p.setPermStatus(rs.getBoolean("PERM_STATUS"));
                p.setPermApont(rs.getBoolean("PERM_APONT"));
                p.setPermStatusApont(rs.getBoolean("PERM_STATUS_APONT"));
                p.setPermPessoas(rs.getBoolean("PERM_PESSOAS"));
                p.setPermFerramentas(rs.getBoolean("PERM_FERRAMENTAS"));
                p.setPermCadCheckList(rs.getBoolean("PERM_CHECKLIST"));
                p.setPermPonto(rs.getBoolean("PERM_PONTO"));
                p.setPermComentOs(rs.getBoolean("PERM_COMENT_OS"));
                p.setPermComentApont(rs.getBoolean("PERM_COMENT_APONT"));
                p.setEmail(rs.getString("EMAIL"));
                p.setPermVenda(rs.getBoolean("PERM_VENDA"));
                p.setPermDescontado(rs.getBoolean("PERM_DESCONTADO"));
                p.setAtivo(rs.getBoolean("ATIVO"));
                p.setPermCustoMO(rs.getBoolean("PERM_CUSTO"));
                p.setPermFerramentas(rs.getBoolean("PERM_FERRAMENTAS"));
                p.setPermProspeccao(rs.getBoolean("PERM_PROSPECCAO"));
                p.setPermApontamentoPonto(rs.getBoolean("PERM_APONTAMENTO_PONTO"));
                p.setPermBancoHoras(rs.getBoolean("PERM_BANCO_HORAS"));
                p.setPermFolga(rs.getBoolean("PERM_FOLGA"));
                
                pa.add(p);
            }
            
            return pa;
        } catch (SQLException se) {
            throw new SQLException("Erro ao filtrar pessoas! " + se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha filtrar
    
    public void alterar(Pessoa p) throws SQLException{
        Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try {
            String sql;
            sql = "update PESSOA set"
                    + " NOME = ?, LOGIN = ?, SENHA = ?, SOLICITANTE = ?, RESPONSAVEL = ?,"
                    + " LIDER = ?, PERM_LOGIN = ?, PERM_EPI = ?, PERM_AUTENTICACAO = ?, PERM_OS = ?,"
                    + " PERM_TIPO = ?, PERM_STATUS = ?, PERM_APONT = ?, PERM_PESSOAS = ?, PERM_STATUS_APONT = ?,"
                    + " PERM_COMENT_OS = ?, PERM_COMENT_APONT = ?, EMAIL = ?, PERM_PONTO = ?, PERM_VENDA = ?,"
                    + " PERM_DESCONTADO = ?, ATIVO = ?, PERM_CUSTO = ?, PERM_FERRAMENTAS = ?, PERM_CHECKLIST = ?,"
                    + " PERM_PROSPECCAO = ?, PERM_APONTAMENTO_PONTO = ?, PERM_APONTAMENTO_PONTO_JUSTIFICATIVA = ?,"
                    + " PERM_BANCO_HORAS = ?, PERM_FOLGA = ?"
                    + " WHERE CODPESSOA = ?";
            
            pstmt = con.prepareStatement(sql);
            
            pstmt.setString(1, p.getNome());
            pstmt.setString(2, p.getLogin());
            pstmt.setString(3, p.getSenha());
            pstmt.setBoolean(4, p.isSolicitante());
            pstmt.setBoolean(5, p.isResponsavel());
            
            pstmt.setBoolean(6, p.isLider());
            pstmt.setBoolean(7, p.isPermLogin());
            pstmt.setBoolean(8, p.isPermEpi());
            pstmt.setBoolean(9, p.isPermAutenticacao());
            pstmt.setBoolean(10, p.isPermOS());
            
            pstmt.setBoolean(11, p.isPermTipo());
            pstmt.setBoolean(12, p.isPermStatus());
            pstmt.setBoolean(13, p.isPermApont());
            pstmt.setBoolean(14, p.isPermPessoas());
            pstmt.setBoolean(15, p.isPermStatusApont());
            
            pstmt.setBoolean(16, p.isPermComentOs());
            pstmt.setBoolean(17, p.isPermComentApont());
            pstmt.setString(18, p.getEmail());
            pstmt.setBoolean(19, p.isPermPonto());
            pstmt.setBoolean(20, p.isPermVenda());
            
            pstmt.setBoolean(21, p.isPermDescontado());
            pstmt.setBoolean(22, p.isAtivo());
            pstmt.setBoolean(23, p.isPermCustoMO());
            pstmt.setBoolean(24, p.isPermFerramentas());
            pstmt.setBoolean(25, p.isPermCadCheckList());
            
            pstmt.setBoolean(26, p.isPermProspeccao());
            pstmt.setBoolean(27, p.isPermApontamentoPonto());
            pstmt.setBoolean(28, p.isPermApontamentoPontoJustificativa());
            pstmt.setBoolean(29, p.isPermBancoHoras());
            pstmt.setBoolean(30, p.isPermFolga());
            
            pstmt.setInt(31, p.getCodPessoa());
            
            
            pstmt.executeUpdate();
            
        } catch (SQLException se) {
            throw new SQLException("Erro ao editar pessoa! "+se.getMessage());
        } finally {
            con.close();
            pstmt.close();
        }//fecha finally
    }//fecha alterar
    
    public void excluir(int codPessoa) throws SQLException{
        Connection con = ConexaoBanco.getConexao();
        Statement stat = con.createStatement();
        
        try {
            String sql;
            sql = "delete from PESSOA where CODPESSOA = " + codPessoa;
            
            stat.execute(sql);
        } catch (SQLException se) {
            if (se.getErrorCode() == 1451) throw new SQLException("Não é possível excluir pessoa vinculada a outra tabela!"); 
            throw new SQLException(se.getMessage());
        } finally {
            con.close();
            stat.close();
        }//finally
    }//fecha excluir
        
}