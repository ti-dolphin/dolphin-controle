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
public class AdicionaisTableModel extends AbstractTableModel {
    
    private List<Adicional> adicionais = new ArrayList<>();
    private String[] colunas = {"Projeto", "Adicional"};
    
    private final int COLUNA_PROJETO = 0;
    private final int COLUNA_ADICIONAL = 1;

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return adicionais.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        Adicional adicional = adicionais.get(rowIndex);
        
        switch (columnIndex) {
            case COLUNA_PROJETO:
                return adicional.getProjeto().getId();
            case COLUNA_ADICIONAL:
                return adicional.getNumero();
            default:
                return null;
        }
    }
    
    public List<Adicional> getAdicionais() {
        return Collections.unmodifiableList(adicionais);
    }
    
    public Adicional getRowValue(int rowIndex) {
        return  adicionais.get(rowIndex);
    }
    
    public void addRow(Adicional adicional) {
        this.adicionais.add(adicional);
        this.fireTableDataChanged();
    }
    
    public void removeRow(Adicional adicional) {
        this.adicionais.remove(adicional);
        this.fireTableDataChanged();
    }
    
    public void clear() {
        this.adicionais.clear();
        this.fireTableDataChanged();
    }
    
}
