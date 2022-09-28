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
public class EpiTableModel extends AbstractTableModel{

    private List<Epi> epis = new ArrayList<>();
    private String[] colunas = {"Código", "Nome", "Preço", "Descrição"};

    @Override
    public String getColumnName(int column) {
        return colunas[column]; 
    }
    
    @Override
    public int getRowCount() {
        return epis.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 2:
                return Double.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch(coluna){
            case 0:
                return epis.get(linha).getCodEpi();
            case 1:
                return epis.get(linha).getNome();
            case 2:
                return epis.get(linha).getPreco();
            case 3:
                return epis.get(linha).getDescricao();
            
        }
        return null;
    }
    
    public List<Epi> getEpis(){
        return Collections.unmodifiableList(epis);
    }
    
    public void addRow(Epi e){
        this.epis.add(e);
        this.fireTableDataChanged();
    }
    
    public void removeRow(Epi e){
        this.epis.remove(e);
        this.fireTableDataChanged();
    }
    
    public void clear(){
        epis.clear();
        fireTableDataChanged();
    }
}
