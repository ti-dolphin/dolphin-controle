/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.tables;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import model.Notificacao;
import services.NotificacaoService;

/**
 *
 * @author ti
 */
public class NotificacaoTableModel extends AbstractTableModel {

    private List<Notificacao> notificacoes;
    private NotificacaoService notificacaoService;
    private String[] colunas = {"Data", "Descrição", "Lido"};

    public final int COLUNA_DATA = 0;
    public final int COLUNA_DESCRICAO = 1;
    public final int COLUNA_LIDO = 2;

    public NotificacaoTableModel(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
        this.notificacaoService = new NotificacaoService();
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return notificacoes.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Class<?> getColumnClass(int coluna) {
        switch (coluna) {
            case COLUNA_LIDO:
                return Boolean.class;
            case COLUNA_DATA:
                return LocalDateTime.class;
            default:
                return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return coluna == COLUNA_LIDO;
    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {
        Notificacao notificacao = notificacoes.get(linha);
        if (coluna == COLUNA_LIDO) {
            try {
                notificacao.setLido((boolean) valor);
                notificacaoService.editar(notificacao);
                notificacaoService.atualizarNotificacoes();
            } catch (SQLException ex) {
                Logger.getLogger(NotificacaoTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        fireTableRowsUpdated(linha, linha);
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Notificacao notificacao = notificacoes.get(linha);
        switch (coluna) {
            case COLUNA_DATA:
                return notificacao.getDataHora();
            case COLUNA_DESCRICAO:
                return notificacao.getDescricao();
            case COLUNA_LIDO:
                return notificacao.isLido();
        }
        return null;
    }

    public List<Notificacao> getNotificacoes() {
        return Collections.unmodifiableList(notificacoes);
    }

    public Notificacao getNotificacao(int indiceLinha) {
        if (indiceLinha > -1) {
            Notificacao notificacao = notificacoes.get(indiceLinha);
            return notificacao;
        }
        return null;
    }

    public void addRow(Notificacao notificacao) {
        this.notificacoes.add(notificacao);
        this.fireTableDataChanged();
    }

    public void removeRow(Notificacao notificacao) {
        this.notificacoes.remove(notificacao);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.notificacoes.clear();
        this.fireTableDataChanged();
    }
}
