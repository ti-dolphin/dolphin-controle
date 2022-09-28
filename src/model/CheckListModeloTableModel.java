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
public class CheckListModeloTableModel extends AbstractTableModel {

    private List<CheckListModelo> checks = new ArrayList<>();
    private String[] colunas = {"Checklists"};
    
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
    
    @Override
    public int getRowCount() {
        return checks.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return checks.get(linha).getNome();
            default:
                return null;
        }
    }
    
    public List<CheckListModelo> getCheckLists() {
        return Collections.unmodifiableList(checks);
    }
    
    public CheckListModelo getRowValue(int linhaSelecionada) {
        return checks.get(linhaSelecionada);
    }
    
    public void addRow(CheckListModelo checkList) {
        this.checks.add(checkList);
        this.fireTableDataChanged();
    }
    
    public void removeRow(CheckListModelo checkList) {
        this.checks.remove(checkList);
        this.fireTableDataChanged();
    }
    
    public void clear() {
        this.checks.clear();
        this.fireTableDataChanged();
    }
    
}
