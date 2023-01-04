package services.epi;

import java.sql.SQLException;
import java.util.ArrayList;

import model.epi.EpiFuncionario;
import dao.epi.EpiFuncionarioDAO;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import model.Funcionario;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.mail.EmailException;
import persistencia.ConexaoBanco;
import view.Menu;

public class EpiFuncionarioService {

    private final EpiFuncionarioDAO dao;

    public EpiFuncionarioService() {
        this.dao = new EpiFuncionarioDAO();
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

    public void gerarRelatorio(Funcionario funcionario) throws JRException, SQLException {
        Connection con = ConexaoBanco.getConexao();
        String caminhoCorrente = new File("").getAbsolutePath();
        String caminhoDaImagem = caminhoCorrente + "/img/dse-logo-relatorio.png";

        HashMap filtro = new HashMap();
        filtro.put("COLIGADA_PARAM", funcionario.getCodColigada());
        filtro.put("CHAPA_PARAM", funcionario.getChapa());
        filtro.put("IMG_PARAM", caminhoDaImagem);

        JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoCorrente + "/relatorios/rel-epis-entregues.jasper", filtro, con);
        JasperViewer view = new JasperViewer(jasperPrint, false);
        view.setVisible(true);
    }

}
