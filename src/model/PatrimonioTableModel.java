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
public class PatrimonioTableModel extends AbstractTableModel {
    
    private List<Patrimonio> patrimonios = new ArrayList<>();
    private String[] colunas = {"Código do Patrimônio", "Descrição"};
    
    
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return patrimonios.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return patrimonios.get(rowIndex).getCodColigada();
            case 1:
                return patrimonios.get(rowIndex).getDescricao();
            default:
                return null;
        }
    }
    
     public List<Patrimonio> getPatrimonios() {
        return Collections.unmodifiableList(patrimonios);
    }

    public Patrimonio getRowValue(int rowIndex) {
        return patrimonios.get(rowIndex);
    }

    public void addRow(Patrimonio patrimonio) {
        this.patrimonios.add(patrimonio);
        this.fireTableDataChanged();
    }

    public void removeRow(Patrimonio patrimonio) {
        this.patrimonios.remove(patrimonio);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.patrimonios.clear();
        this.fireTableDataChanged();
    }
}
