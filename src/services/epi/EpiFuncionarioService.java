package services.epi;

import java.sql.SQLException;
import java.util.ArrayList;

import model.epi.EpiFuncionario;
import dao.EpiFuncionarioDAO;
import org.apache.commons.mail.EmailException;
import services.email.EmailService;
import view.Menu;

public class EpiFuncionarioService {

    private final EpiFuncionarioDAO dao;
    private final EmailService emailService;

    public EpiFuncionarioService() {
        this.dao = new EpiFuncionarioDAO();
        this.emailService = new EmailService();
    }

    public EpiFuncionario entregarEpi(EpiFuncionario epiFuncionario) throws SQLException, EmailException {
        epiFuncionario.setCreatedBy(Menu.logado.getLogin());
        dao.cadastrarEpiFuncionario(epiFuncionario);
        EpiFuncionario ef = dao.buscarEpiFuncionario(epiFuncionario.getFuncionario().getChapa(), 
                epiFuncionario.getFuncionario().getCodColigada(), 
                epiFuncionario.getEpi().getCodEpi());
        return ef;
    }

    public EpiFuncionario devolverEpi(EpiFuncionario epiFuncionario) throws SQLException, EmailException {
        epiFuncionario.setModifiedBy(Menu.logado.getLogin());
        dao.alterarEpiFuncionario(epiFuncionario);
        return dao.buscarEpiFuncionario(epiFuncionario.getFuncionario().getChapa(), 
                epiFuncionario.getFuncionario().getCodColigada(), 
                epiFuncionario.getEpi().getCodEpi());
    }

    public ArrayList<EpiFuncionario> filtrarEpiFuncionario(String query) throws SQLException {
        return dao.filtrarEpiFuncionario(query);
    }

    public ArrayList<EpiFuncionario> buscarHistoricoEpisNaoEnviados(short coligada, String chapa) throws SQLException {
        return dao.buscarHistoricoEpisNaoEnviados(coligada, chapa);
    }

    public ArrayList<EpiFuncionario> buscarEpisPendentes(EpiFuncionario epiFuncionario) throws SQLException {
        return dao.buscarEpisPendentes(epiFuncionario);
    }

    public void alterarDescontar(EpiFuncionario epiFuncionario) throws SQLException {
        dao.alterarDescontar(epiFuncionario);
    }

    
}
