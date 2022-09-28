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
public class CheckListItemTableModel extends AbstractTableModel {

    List<CheckListItem> itens = new ArrayList<>();
    private String[] colunas = {"Itens"};
    
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
    
    @Override
    public int getRowCount() {
        return itens.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch(coluna) {
            case 0:
                return itens.get(linha).getNome();
        }
        
        return null;
    }
    
    public List<CheckListItem> getItens() {
        return Collections.unmodifiableList(itens);
    }
    
    public void addRow(CheckListItem item) {
        if (!itens.contains(item)) {
            this.itens.add(item);
            this.fireTableDataChanged();
        }
    }
    
    public void removeRow(CheckListItem item) {
        this.itens.remove(item);
        this.fireTableDataChanged();
    }
    
    public void clear() {
        this.itens.clear();
        this.fireTableDataChanged();
    }
    
}
