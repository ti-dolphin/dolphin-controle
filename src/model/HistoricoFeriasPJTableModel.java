/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author guilherme.oliveira
 */
public class HistoricoFeriasPJTableModel extends AbstractTableModel {

    private List<HistoricoFeriasPJ> historicos = new ArrayList<>();
    private String[] colunas = {"ID", "Chapa", "Nome", "Data Início", "Data Término", "Quantidade"};

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return historicos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Class<?> getColumnClass(int coluna) {
        switch (coluna) {
            case 0:
                return Integer.class;
            case 3:
                return LocalDate.class;
            case 4:
                return LocalDate.class;
            case 5:
                return Integer.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return historicos.get(rowIndex).getCodHistorico();
            case 1:
                return historicos.get(rowIndex).getFuncionario().getChapa();
            case 2:
                return historicos.get(rowIndex).getFuncionario().getNome();
            case 3:
                return historicos.get(rowIndex).getDataInicio();
            case 4:
                return historicos.get(rowIndex).getDataTermino();
            case 5:
                return historicos.get(rowIndex).getQuantidade();
            default:
                return null;
        }
    }

    public List<HistoricoFeriasPJ> getHistoricos() {
        return Collections.unmodifiableList(historicos);
    }

    public HistoricoFeriasPJ getRowValue(int rowIndex) {
        return historicos.get(rowIndex);
    }

    public void addRow(HistoricoFeriasPJ historico) {
        this.historicos.add(historico);
        this.fireTableDataChanged();
    }

    public void removeRow(HistoricoFeriasPJ historico) {
        this.historicos.remove(historico);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.historicos.clear();
        this.fireTableDataChanged();
    }
}
