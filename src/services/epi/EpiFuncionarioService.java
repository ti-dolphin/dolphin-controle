package services.epi;

import java.sql.SQLException;
import java.util.ArrayList;

import model.epi.EpiFuncionario;
import dao.DAOFactory;
import dao.EpiFuncionarioDAO;
import model.Funcionario;
import model.epi.Epi;

public class EpiFuncionarioService {

    EpiFuncionarioDAO dao = new EpiFuncionarioDAO();

    public void cadastrarEpiFuncionario(EpiFuncionario ef) throws SQLException {
        dao.cadastrarEpiFuncionario(ef);
    }//fecha cadastrarEpiFuncionario

    public void alterarEpiFuncionario(EpiFuncionario ef) throws SQLException {
        dao.alterarEpiFuncionario(ef);
    }//fecha alterarEpiFuncionario

    public ArrayList<EpiFuncionario> filtrarEpiFuncionario(String query) throws SQLException {
        return dao.filtrarEpiFuncionario(query);
    }//fecha filtarFuncionario

    public ArrayList<EpiFuncionario> buscarHistoricoEpisNaoEnviados(short coligada, String chapa) throws SQLException {
        return dao.buscarHistoricoEpisNaoEnviados(coligada, chapa);
    }//fecha buscarEpisNaoEntregue

    public ArrayList<EpiFuncionario> buscarEpisPendentes(EpiFuncionario epiFuncionario) throws SQLException {
        return dao.buscarEpisPendentes(epiFuncionario);
    }

    public void alterarDescontar(EpiFuncionario epiFuncionario) throws SQLException {
        dao.alterarDescontar(epiFuncionario);
    }
}
