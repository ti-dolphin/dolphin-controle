package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Situacao;
import persistencia.ConexaoBanco;

/*
 *@author Guilherme Borges 
 */
public class SituacaoDAO {
	public void cadastrarPSituacao(Situacao psm) throws SQLException{
		Connection con = ConexaoBanco.getConexao();
		PreparedStatement pstmt = null;
		try{
			String sql;
			sql = "insert into PSITUACAO(CODSITUACAO, DESCRICAO) values(?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, String.valueOf(psm.getCodSituacao()));
			pstmt.setString(2, psm.getDescricao());
			
			pstmt.execute();
		}catch(SQLException e){
			throw new SQLException("Erro ao cadastrar dados!" + e.getMessage());
		}finally{
			con.close();
			pstmt.close();
		}//fecha finally
	}//fecha cadastrarPSituacao
	
	public ArrayList<Situacao> buscarPSituacao() throws SQLException{
		Connection con = ConexaoBanco.getConexao();
		Statement stat = con.createStatement();
		
		try{
			String sql = "select * from PSITUACAO";
			ResultSet rs = stat.executeQuery(sql);
			ArrayList<Situacao> psa = new ArrayList<>();
			while(rs.next()){
				Situacao psm = new Situacao();
				
				psm.setCodSituacao(rs.getString("CODSITUACAO").charAt(0));
				psm.setDescricao(rs.getString("DESCRICAO"));
				
				psa.add(psm);
			}
			
			return psa;
		}catch(SQLException e){
			 throw new SQLException("Erro ao buscar dados! "+e.getMessage());
		}finally {
			con.close();
			stat.close();
		}//fecha finally
		
	}//fecha buscarPSituacao
	
	public void deletarPSituacao(char codSituacao) throws SQLException{
		Connection con = ConexaoBanco.getConexao();
		Statement stat = con.createStatement();
		
		try{
			String sql = "";
			sql = "delete from PSITUACAO where CODSITUACAO = " + codSituacao;
			stat.execute(sql);
			
		}catch(SQLException e){
			throw new SQLException("Erro ao deletar dado!" + e.getMessage());
		}
	}//fecha deletarPSituacao
	
	public void alterarPSituacao(Situacao psm) throws SQLException{
		Connection con = ConexaoBanco.getConexao();
        PreparedStatement pstmt = null;
        
        try{
        	String sql;
        	sql = "update PSITUACAO set CODSITUACAO=?, DESCRICAO=?";
        	
        	pstmt = con.prepareStatement(sql);
        	
        	pstmt.setString(1, String.valueOf(psm.getCodSituacao()));
        	pstmt.setString(2, psm.getDescricao());
        	
        	pstmt.executeUpdate();
        	
        }catch(SQLException e){
        	throw new SQLException("Erro ao alterar dados!" + e.getMessage());
        }finally{
        	con.close();
        	pstmt.close();
        }//fecha finally
	}//fecha alterarPSituacao
}//fecha PSituacaoDAO
