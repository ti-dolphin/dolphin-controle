/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.apontamento;

import dao.apontamento.ApontamentoDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.apontamento.Apontamento;
import view.Menu;
import view.apontamento.UIApontamentos;
import services.NotificacaoService;

/**
 *
 * @author ti
 */
public class ApontamentoService {

    private ApontamentoDAO dao;
    private NotificacaoService notificacaoService;

    public ApontamentoService() {
        this.dao = new ApontamentoDAO();
        this.notificacaoService = new NotificacaoService();
    }

    public void salvar(Apontamento apontamento) throws SQLException, Exception {
        dao.alterar(apontamento);
    }

    public List<Apontamento> filtrarApontamentos(String query) throws SQLException {
        return dao.filtrarApontamentos(query);
    }

    public List<Apontamento> filtrarApontamentoPonto(String query) throws SQLException {
        return dao.filtrarApontamentoPonto(query);
    }

    public List<Apontamento> filtrarApontamentoProblema(String query) throws SQLException {
        return dao.filtrarProblemasDeApontamento(query);
    }

    public List<Apontamento> buscarPontosPorNotificacoesNaoLidas() throws SQLException {
        return dao.buscarPontosPorNotificacoesNaoLidas();
    }

    public void verificar(Apontamento apontamento) {
        Menu.carregamento(true);

        new Thread() {
            @Override
            public void run() {
                try {
                    dao.verificar(apontamento);
                    List<Apontamento> apontamentosComProblema = dao.buscarApontamentosComProblema(apontamento);
                    if (apontamentosComProblema.isEmpty()) {
                        dao.verificarAssiduidade(apontamento, true);
                    } else {
                        dao.verificarAssiduidade(apontamento, false);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro ao verificar!", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(UIApontamentos.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    Menu.carregamento(false);
                }
            }
        }.start();
    }
}
