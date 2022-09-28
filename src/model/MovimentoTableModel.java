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
public class MovimentoTableModel extends AbstractTableModel {

    private List<Movimento> movimentos = new ArrayList<>();
    private String[] colunas = {"ID", "Data", "Local de Estoque de Origem",
        "Local de Estoque de Destino", "Responsável"};
    
    public final int COLUNA_ID = 0;
    public final int COLUNA_DATA = 1;
    public final int COLUNA_ESTOQUE_ORIGEM =  2;
    public final int COLUNA_ESTOQUE_DESTINO = 3;
    public final int COLUNA_RESPONSAVEL = 4;
    
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return movimentos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Class<?> getColumnClass(int coluna) {
        switch (coluna) {
            case COLUNA_DATA:
                return LocalDate.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case COLUNA_ID:
                return movimentos.get(rowIndex).getId();
            case COLUNA_DATA:
                return movimentos.get(rowIndex).getDataEntrega();
            case COLUNA_ESTOQUE_ORIGEM:
                return movimentos.get(rowIndex).getLocalDeEstoqueOrigem().getNome();
            case COLUNA_ESTOQUE_DESTINO:
                return movimentos.get(rowIndex).getLocalDeEstoqueDestino().getNome();
            case COLUNA_RESPONSAVEL:
                return movimentos.get(rowIndex).getResponsavel().getNome();
            default:
                return null;
        }
    }

    public List<Movimento> getMovimentos() {
        return Collections.unmodifiableList(movimentos);
    }

    public void addRow(Movimento movimento) {
        this.movimentos.add(movimento);
        this.fireTableDataChanged();
    }

    public void removeRow(Movimento movimento) {
        this.movimentos.remove(movimento);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.movimentos.clear();
        this.fireTableDataChanged();
    }

    public Movimento getRowValue(int rowIndex) {
        return movimentos.get(rowIndex);
    }
}
