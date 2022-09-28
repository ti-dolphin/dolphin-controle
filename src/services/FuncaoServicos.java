package services;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.DAOFactory;
import dao.FuncaoDAO;
import model.Funcao;

public class FuncaoServicos {
	public void cadastrarFuncao(Funcao f) throws SQLException{
		FuncaoDAO fDAO = DAOFactory.getFuncao();
		fDAO.cadastrarFuncao(f);
	}//fecha cadastrarFuncao
	
	public ArrayList<Funcao> buscarFuncao() throws SQLException{ 
		FuncaoDAO fDAO = DAOFactory.getFuncao();
		return fDAO.buscarFuncao();
	}//fecha buscarFuncao
	
	public void deletarFuncao(String codigo) throws SQLException{
		FuncaoDAO fDAO  = DAOFactory.getFuncao();
		fDAO.deletarFuncao(codigo);
	}//fecha deletarFuncao
	
	public void alterarFuncao(Funcao f) throws SQLException{
		FuncaoDAO fDAO = DAOFactory.getFuncao();
		fDAO.alterarFuncao(f);
	}//fecha alterarFuncao
}
