/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.apontamento;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author guilherme.oliveira
 */
public class ApontOSTableModel extends AbstractTableModel {

    private List<Apontamento> apontamentos = new ArrayList<>();
    private String[] colunas = {"Id", "Chapa", "Nome", "Data", "Status"};

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
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return apontamentos.get(linha).getCodApont();
            case 1:
                return apontamentos.get(linha).getFuncionario().getChapa();
            case 2:
                return apontamentos.get(linha).getFuncionario().getNome();
            case 3:
                if (apontamentos.get(linha).getData() != null) {
                    return apontamentos.get(linha).getData()
                            .format(DateTimeFormatter
                                    .ofPattern("dd/MM/yyyy HH:mm"));
                } else {
                    return apontamentos.get(linha).getData();
                }
            case 4:
                if (apontamentos.get(linha).getStatusApont() != null) {
                    return apontamentos.get(linha).getStatusApont().getCodStatusApont();
                } else {
                    return "";
                }
        }
        return null;
    }

    public List<Apontamento> getApontamentos() {
        return Collections.unmodifiableList(apontamentos);
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
