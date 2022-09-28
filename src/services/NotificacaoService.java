/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.NotificacaoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Notificacao;
import view.Menu;

/**
 *
 * @author ti
 */
public class NotificacaoService {

    private NotificacaoDAO dao;

    public NotificacaoService() {
        this.dao = new NotificacaoDAO();
    }

    public List<Notificacao> listar(boolean lido) throws SQLException {
        if (lido) {
            return (ArrayList<Notificacao>) dao.listarPorPessoa(
                    Menu.logado.getCodPessoa()
            );
        } else {
            return (ArrayList<Notificacao>) dao.listarNaoLidasPorPessoa(
                    Menu.logado.getCodPessoa()
            );
        }
    }

    public void editar(Notificacao notificacao) throws SQLException {
        dao.editar(notificacao);
    }

    public int listarTotalNotificoesNaoLidasPorPessoa(int pessoaId) throws SQLException {
        return dao.listarTotalNotificoesPorPessoa(pessoaId);
    }

    public void notificar(Notificacao notificacao) throws SQLException {
        dao.inserir(notificacao);
    }

    public void atualizarNotificacoes() throws SQLException {
        int total = listarTotalNotificoesNaoLidasPorPessoa(Menu.logado.getCodPessoa());
        Menu.jmNotificacoes.setText("Notificações " + total);
    }

}
