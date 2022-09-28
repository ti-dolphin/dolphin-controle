/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author guilherme.oliveira
 */
public class LocalDeEstoqueTableModel extends AbstractTableModel {

    private List<LocalDeEstoque> estoques = new ArrayList<>();
    private String[] colunas = {"Nome", "Inativo"};

    
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return estoques.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }
    
    @Override
    public Class<?> getColumnClass(int coluna) {
        switch (coluna) {
            case 1:
                return Boolean.class;
            default:
                return String.class;
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return estoques.get(rowIndex).getNome();
            case 1:
                return estoques.get(rowIndex).isInativo();
            default:
                return null;
        }
    }
    
    public List<LocalDeEstoque> getEstoques() {
        return Collections.unmodifiableList(estoques);
    }
    
    public void addRow(LocalDeEstoque estoque) {
        this.estoques.add(estoque);
        this.fireTableDataChanged();
    }

    public void removeRow(LocalDeEstoque estoque) {
        this.estoques.remove(estoque);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.estoques.clear();
        this.fireTableDataChanged();
    }

    public LocalDeEstoque getRowValue(int rowIndex) {
        return estoques.get(rowIndex);
    }
}
