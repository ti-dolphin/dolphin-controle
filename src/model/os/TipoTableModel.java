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
public class TipoTableModel extends AbstractTableModel {
    
    private List<TipoOs> tipos = new ArrayList<>();
    private String[] colunas = {"Código", "Descrição"};

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return tipos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch(coluna){
            case 0:
                return tipos.get(linha).getCodTipoOs();
            case 1:
                return tipos.get(linha).getDescricao();
        }
        return null;
    }   
    
    public List<TipoOs> getTipoOs(){
        return Collections.unmodifiableList(tipos);
    }
    
    public void addRow(TipoOs t){
        this.tipos.add(t);
        this.fireTableDataChanged();
    }
    
    public void removeRow(TipoOs t){
        this.tipos.remove(t);
        this.fireTableDataChanged();
    }
    
    public void clear(){
        this.tipos.clear();
        this.fireTableDataChanged();
    }
}
