/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.os;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author guilherme.oliveira
 */
public class StatusTableModel extends AbstractTableModel {
    
    private List<Status> status = new ArrayList<>();
    private String[] colunas = {"Código", "Nome", "Acao", "Ativo"};

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }    
    
    @Override
    public int getRowCount() {
        return status.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }
    
    @Override
    public Class<?> getColumnClass(int coluna) {
        switch (coluna) {
            case 3:
                return Boolean.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch(coluna){
            case 0:
                return status.get(linha).getCodStatus();
            case 1:
                return status.get(linha).getNome();
            case 2:
                return status.get(linha).getAcao();
            case 3:
                return status.get(linha).isAtivo();
        }
        return null;
    }
    
    public List<Status> getStatus(){
        return Collections.unmodifiableList(status);
    }
    
    public void addRow(Status s){
        this.status.add(s);
        this.fireTableDataChanged();
    }
    
    public void removeRow(Status s){
        this.status.remove(s);
        this.fireTableDataChanged();
    }
    
    public void clear(){
        this.status.clear();
        this.fireTableDataChanged();
    }
}
