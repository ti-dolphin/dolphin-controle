    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.epi.tables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.epi.Epi;

/**
 *
 * @author guilherme.oliveira
 */
public class EpiEntregaTableModel extends AbstractTableModel{

    private List<Epi> epis = new ArrayList<>();
    private String[] colunas = {"Código", "Nome", "Descrição"};
    
    public final int COLUNA_CODIGO = 0;
    public final int COLUNA_NOME = 1;
    public final int COLUNA_DESCRICAO = 2;

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
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch(coluna){
            case COLUNA_CODIGO:
                return epis.get(linha).getCodEpi();
            case COLUNA_NOME:
                return epis.get(linha).getNome();
            case COLUNA_DESCRICAO:
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
