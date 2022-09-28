/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author guilherme.oliveira
 */
public class PatrimonioCheckListTableModel extends AbstractTableModel {

    private List<Patrimonio> patrimonios = new ArrayList<>();
    private String[] colunas = {"Código", "Descrição", "Checklist"};
    public final int COLUNA_CODIGO = 0;
    public final int COLUNA_DESCRICAO = 1;
    public final int COLUNA_CHECKLIST = 2;
    
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
            case COLUNA_CODIGO:
                return patrimonios.get(rowIndex).getCodPatrimonio();
            case COLUNA_DESCRICAO:
                return patrimonios.get(rowIndex).getDescricao();
            case COLUNA_CHECKLIST:
                return patrimonios.get(rowIndex).getCheckListModelo().getNome();
            default:
                return null;
        }
    }
    
    public List<Patrimonio> getPatrimonios() {
        return Collections.unmodifiableList(patrimonios);
    } 
    
    public Patrimonio getRowValue(int linhaSelecionada) {
        return patrimonios.get(linhaSelecionada);
    }
    
    public void addRow(Patrimonio patrimonio) {
        patrimonios.add(patrimonio);
        fireTableDataChanged();
    }
    
    public void removeRow(Patrimonio patrimonio) {
        patrimonios.remove(patrimonio);
        fireTableDataChanged();
    }
    
    public void clear() {
        patrimonios.clear();
        fireTableDataChanged();
    }
    
}
