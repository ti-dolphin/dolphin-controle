/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.epi.tables;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.epi.EpiFuncionario;

/**
 *
 * @author guilherme.oliveira
 */
public class EpiFuncionarioTableModel extends AbstractTableModel {

    private List<EpiFuncionario> lista = new ArrayList<>();
    private String[] colunas = {
        "Id", "Coligada", "Chapa", "Funcionario", "EPI",
        "Data da Retirada", "Data da Devolução", "CA",
        "Entregue Por", "Devolvido Por", "Preço", "Descontado"
    };

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return coluna == 11;
    }

    @Override
    public Class<?> getColumnClass(int coluna) {
        switch (coluna) {
            case 10:
                return Double.class;
            case 11:
                return Boolean.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return lista.get(linha).getCodRegistro();
            case 1:
                return lista.get(linha).getFuncionario().getCodColigada();
            case 2:
                return lista.get(linha).getFuncionario().getChapa();
            case 3:
                return lista.get(linha).getFuncionario().getNome();
            case 4:
                return lista.get(linha).getEpi().getNome();
            case 5:
                if (lista.get(linha).getDataRetirada() != null) {
                    return lista.get(linha).getDataRetirada()
                            .format(DateTimeFormatter
                                    .ofPattern("dd/MM/yyyy HH:mm"));
                } else {
                    return lista.get(linha).getDataRetirada();
                }
            case 6:

                if (lista.get(linha).getDataDevolucao() != null) {
                    return lista.get(linha).getDataDevolucao()
                            .format(DateTimeFormatter
                                    .ofPattern("dd/MM/yyyy HH:mm"));
                } else {
                    return lista.get(linha).getDataDevolucao();
                }
            case 7:
                return lista.get(linha).getCa();
            case 8:
                return lista.get(linha).getCreatedBy();
            case 9:
                return lista.get(linha).getModifiedBy();
            case 10:
                return lista.get(linha).getPreco();
            case 11:
                return lista.get(linha).isDescontado();
        }
        return null;
    }

    @Override
    public void setValueAt(Object valor, int linha, int coluna) {
        if (valor == null) {
            return;
        }

        switch (coluna) {
            case 10:
                lista.get(linha).setPreco((Double) valor);
                break;
            case 11:
                lista.get(linha).setDescontado((Boolean) valor);
                break;
        }
        this.fireTableRowsUpdated(linha, linha);
    }

    public List<EpiFuncionario> getEpiFuncionarios() {
        return Collections.unmodifiableList(lista);
    }
    
    public void addRow(EpiFuncionario e) {
        this.lista.add(e);
        this.fireTableDataChanged();
    }

    public void clear() {
        lista.clear();
        fireTableDataChanged();
    }
}
