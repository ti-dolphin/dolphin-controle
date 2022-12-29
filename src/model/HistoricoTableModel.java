/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.epi.EpiFuncionario;

/**
 *
 * @author guilherme.oliveira
 */
public class HistoricoTableModel extends AbstractTableModel {

    private List<EpiFuncionario> registros = new ArrayList<>();
    private String[] colunas = {"Funcionário", "EPI", "Data Retirada", "Data Devolução", "CA"};
    
    public final int COLUNA_FUNCIONARIO = 0;
    public final int COLUNA_EPI = 1;
    public final int COLUNA_DATA_RETIRADA = 2;
    public final int COLUNA_DATA_DEVOLUCAO = 3;
    public final int COLUNA_CA = 4;

    @Override
    public String getColumnName(int column) {
        return colunas[column]; 
    }

    @Override
    public int getRowCount() {
        return registros.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case COLUNA_FUNCIONARIO:
                return registros.get(linha).getFuncionario().getNome();
            case COLUNA_EPI:
                return registros.get(linha).getEpi().getNome();    
            case COLUNA_DATA_RETIRADA:
                if (registros.get(linha).getDataRetirada() != null) {
                    return registros.get(linha).getDataRetirada().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                } else {
                    return registros.get(linha).getDataRetirada();
                }
            case COLUNA_DATA_DEVOLUCAO:
                if (registros.get(linha).getDataDevolucao() != null) {
                    return registros.get(linha).getDataDevolucao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                } else {
                    return registros.get(linha).getDataDevolucao();
                }
            case COLUNA_CA:
                return registros.get(linha).getCa();
        }
        return null;
    }

    public List<EpiFuncionario> getRegistros() {
        return Collections.unmodifiableList(registros);
    }
    
    public void addRow(EpiFuncionario ef){
        this.registros.add(ef);
        this.fireTableDataChanged();
    }
    
    public void clearTable(){
        registros.clear();
        fireTableDataChanged();
    }
    
}
