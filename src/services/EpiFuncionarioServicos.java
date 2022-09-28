package services;

import java.sql.SQLException;
import java.util.ArrayList;

import model.EpiFuncionario;
import dao.DAOFactory;
import dao.EpiFuncionarioDAO;

public class EpiFuncionarioServicos {
	public void cadastrarEpiFuncionario(EpiFuncionario ef) throws SQLException{
		EpiFuncionarioDAO dao = DAOFactory.getEpifuncionariodao();
		dao.cadastrarEpiFuncionario(ef);
	}//fecha cadastrarEpiFuncionario
	
	public ArrayList<EpiFuncionario> buscarEpiFuncionario() throws SQLException{
		EpiFuncionarioDAO dao =  DAOFactory.getEpifuncionariodao();
		return dao.buscarEpiFuncionario();
	}//fecha buscarEpiFuncionario
	
	public void alterarEpiFuncionario(EpiFuncionario ef) throws SQLException{
		EpiFuncionarioDAO dao = DAOFactory.getEpifuncionariodao();
		dao.alterarEpiFuncionario(ef);
	}//fecha alterarEpiFuncionario
        
        public ArrayList<EpiFuncionario> filtarEpiFuncionario(String query) throws SQLException{
            EpiFuncionarioDAO dao = DAOFactory.getEpifuncionariodao();
            return dao.filtrarEpiFuncionario(query);
        }//fecha filtarFuncionario
        
        public ArrayList<EpiFuncionario> buscarEpisDoFuncionario(short coligada, String chapa) throws SQLException{
            EpiFuncionarioDAO dao = DAOFactory.getEpifuncionariodao();
            return dao.buscarEpisDoFuncionario(coligada, chapa);
        }//fecha buscarEpisDoFuncionario
        
        public ArrayList<EpiFuncionario> buscarHistoricoEpisNaoEnviados(short coligada, String chapa) throws SQLException{
            EpiFuncionarioDAO dao = DAOFactory.getEpifuncionariodao();
            return dao.buscarHistoricoEpisNaoEnviados(coligada, chapa);
        }//fecha buscarEpisNaoEntregue
}
