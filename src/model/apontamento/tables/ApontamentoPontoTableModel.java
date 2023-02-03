/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.apontamento.tables;

import dao.DAOFactory;
import dao.apontamento.ApontamentoDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import model.Notificacao;
import model.apontamento.Apontamento;
import services.apontamento.ApontamentoService;
import services.NotificacaoService;
import utilitarios.FormatarData;
import view.Menu;

/**
 *
 * @author ti
 */
public class ApontamentoPontoTableModel extends AbstractTableModel {

    private List<Apontamento> apontamentos = new ArrayList<>();
    private String[] colunas = {"Chapa", "Nome", "Data", "Status", "Verificado", "Problema", "Ajustado", "Data Motivo",
        "Motivo", "Data Justificativa", "Justificado por", "Justificativa", "Centro de Custo", "Líder"};
    private ApontamentoDAO aDAO;
    private NotificacaoService notificacaoService;

    public final int COLUNA_CHAPA = 0;
    public final int COLUNA_NOME = 1;
    public final int COLUNA_DATA = 2;
    public final int COLUNA_STATUS = 3;
    public final int COLUNA_VERIFICADO = 4;
    public final int COLUNA_PROBLEMA = 5;
    public final int COLUNA_AJUSTADO = 6;
    public final int COLUNA_DATA_HORA_MOTIVO = 7;
    public final int COLUNA_MOTIVO = 8;
    public final int COLUNA_DATA_HORA_JUSTIFICATIVA = 9;
    public final int COLUNA_JUSTIFICADO_POR = 10;
    public final int COLUNA_JUSTIFICATIVA = 11;
    public final int COLUNA_CENTRO_CUSTO = 12;
    public final int COLUNA_LIDER = 13;

    private ApontamentoService apontamentoService;

    public ApontamentoPontoTableModel() {

        this.aDAO = DAOFactory.getAPONTAMENTODAO();
        this.apontamentoService = new ApontamentoService();
        this.notificacaoService = new NotificacaoService();
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return apontamentos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Class<?> getColumnClass(int coluna) {
        switch (coluna) {
            case COLUNA_VERIFICADO:
                return Boolean.class;
            case COLUNA_PROBLEMA:
                return Boolean.class;
            case COLUNA_AJUSTADO:
                return Boolean.class;
            case COLUNA_DATA:
                return LocalDate.class;
            case COLUNA_DATA_HORA_MOTIVO:
                return LocalDateTime.class;
            case COLUNA_DATA_HORA_JUSTIFICATIVA:
                return LocalDateTime.class;
            default:
                return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {

        Apontamento apontamento = getApontamento(linha);
        if (coluna == COLUNA_JUSTIFICATIVA && Menu.logado.isPermApontamentoPontoJustificativa() && getApontamento(linha).isProblema()) {
            return true;
        }
        if (coluna == COLUNA_VERIFICADO && Menu.logado.isPermApontamentoPonto()) {
            if (apontamento.isVerificado() && apontamento.isProblema()) {
                return false;
            }
            return true;
        }
        if (coluna == COLUNA_PROBLEMA && Menu.logado.isPermApontamentoPonto() && apontamento.isVerificado()) {
            return true;
        }
        if (coluna == COLUNA_AJUSTADO && Menu.logado.isPermApontamentoPonto() && apontamento.isVerificado()) {
            return true;
        }
        if (coluna == COLUNA_MOTIVO && Menu.logado.isPermApontamentoPonto() && apontamento.isProblema()) {
            return true;
        }

        return false;
    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {
        Apontamento apontamento = apontamentos.get(linha);
        if (coluna == COLUNA_VERIFICADO) {
            apontamento.setVerificado((boolean) valor);
        }
        if (coluna == COLUNA_PROBLEMA) {
            apontamento.setProblema((boolean) valor);
        }
        if (coluna == COLUNA_AJUSTADO) {
            apontamento.setAjustado((boolean) valor);
            try {
                apontamentoService.editarApontamentoPonto(apontamento);
            } catch (SQLException ex) {
                Logger.getLogger(ApontamentoPontoTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (coluna == COLUNA_MOTIVO) {
            String motivo = (String) valor;
            
            if (!motivo.isBlank()) {
                
                apontamento.setMotivo(motivo);
                
                try {
                    Apontamento apontamentoAtualizado = apontamentoService.registrarDataEHoraMotivo(apontamento.getCodApont());
                    apontamento.setDataHoraMotivo(apontamentoAtualizado.getDataHoraMotivo());
                } catch (SQLException ex) {
                    Logger.getLogger(ApontamentoPontoTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        if (coluna == COLUNA_JUSTIFICATIVA) {
            String justificativa = (String) valor;
            if (justificativa.isBlank()) {
                apontamento.setJustificativa(null);
            } else {
                apontamento.setJustificativa(justificativa);
                String data = FormatarData.formatarDataEmTexto(apontamento.getData(), "dd/MM/yyyy");
                try {
                    notificacaoService.notificar(new Notificacao(
                            Menu.logado.getLogin()
                            + " adicionou uma justificativa no ponto: "
                            + apontamento.getFuncionario().getNome()
                            + " - "
                            + data)
                    );
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            "Erro ao enviar notificação",
                            JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(ApontamentoPontoTableModel.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
            String data = apontamento.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            try {
                Apontamento apontamentoAtualizado = apontamentoService.registrarJustificativa(apontamento);
                apontamento.setDataHoraJustificativa(apontamentoAtualizado.getDataHoraJustificativa());
                apontamento.setJustificadoPor(apontamentoAtualizado.getJustificadoPor());
            } catch (SQLException ex) {
                Logger.getLogger(ApontamentoPontoTableModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                notificacaoService.notificar(new Notificacao(
                        Menu.logado.getLogin()
                        + " adicionou uma justificativa no ponto: "
                        + apontamento.getFuncionario().getNome()
                        + " - "
                        + data)
                );
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "Erro ao enviar notificação",
                        JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(ApontamentoPontoTableModel.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
        apontamentoService.verificar(apontamento);
        fireTableRowsUpdated(linha, linha);
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Apontamento apontamento = apontamentos.get(linha);
        switch (coluna) {
            case COLUNA_CHAPA:
                return apontamento.getFuncionario().getChapa();
            case COLUNA_NOME:
                return apontamento.getFuncionario().getNome();
            case COLUNA_DATA:
                if (apontamento.getData() != null) {
                    return apontamento
                            .getData()
                            .format(DateTimeFormatter
                                    .ofPattern("dd/MM/yyyy"));
                } else {
                    return "";
                }
            case COLUNA_STATUS:
                return apontamento.getStatusApont().getDescricao();
            case COLUNA_VERIFICADO:
                return apontamento.isVerificado();
            case COLUNA_PROBLEMA:
                return apontamento.isProblema();
            case COLUNA_AJUSTADO:
                return apontamento.isAjustado();
            case COLUNA_DATA_HORA_MOTIVO:
                return apontamento.getDataHoraMotivo();
            case COLUNA_MOTIVO:
                return apontamento.getMotivo();
            case COLUNA_DATA_HORA_JUSTIFICATIVA:
                return apontamento.getDataHoraJustificativa();
            case COLUNA_JUSTIFICADO_POR:
                return apontamento.getJustificadoPor();
            case COLUNA_JUSTIFICATIVA:
                return apontamento.getJustificativa();
            case COLUNA_CENTRO_CUSTO:
                return apontamento.getCentroCusto().getNome();
            case COLUNA_LIDER:
                return apontamento.getLider().getNome();
            
        }
        return null;
    }

    public List<Apontamento> getApontamentos() {
        return Collections.unmodifiableList(apontamentos);
    }

    public Apontamento getApontamento(int indiceLinha) {
        if (indiceLinha > -1) {
            Apontamento apontamento = apontamentos.get(indiceLinha);
            return apontamento;
        }
        return null;
    }

    public void addRow(Apontamento a) {
        this.apontamentos.add(a);
        this.fireTableDataChanged();
    }

    public void removeRow(Apontamento a) {
        this.apontamentos.remove(a);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.apontamentos.clear();
        this.fireTableDataChanged();
    }
}
