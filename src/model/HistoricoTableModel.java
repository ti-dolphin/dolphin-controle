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
    private String[] colunas = {"Id", "Coligada", "Funcionário", "EPI", "Data Retirada", "Data Devolução", "CA"};

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
            case 0:
                return registros.get(linha).getCodRegistro();
            case 1:
                return registros.get(linha).getFuncionario().getCodColigada();
            case 2:
                return registros.get(linha).getFuncionario().getNome();
            case 3:
                return registros.get(linha).getEpi().getNome();    
            case 4:
                if (registros.get(linha).getDataRetirada() != null) {
                    return registros.get(linha).getDataRetirada().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                } else {
                    return registros.get(linha).getDataRetirada();
                }
            case 5:
                if (registros.get(linha).getDataDevolucao() != null) {
                    return registros.get(linha).getDataDevolucao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                } else {
                    return registros.get(linha).getDataDevolucao();
                }
            case 6:
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
