/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ponto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author guilherme.oliveira
 */
public class RelogioPontoTableModel extends AbstractTableModel {

    private List<RelogioPonto> relogios = new ArrayList<>();
    private String[] colunas = {"Nome", "Endereço IP", "Patrimônio", 
        "Número de Série", "Ativo", "Última Sinc."};

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return relogios.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return relogios.get(linha).getNome();
            case 1:
                return relogios.get(linha).getIp();
            case 2:
                return relogios.get(linha).getPatrimonio();
            case 3:
                return relogios.get(linha).getNumeroSerie();
            case 4:
                if (relogios.get(linha).isAtivo()) return "Sim";
                return "Não";
            case 5:
                if (relogios.get(linha).getDataSinc() != null) {
                    return relogios.get(linha)
                            .getDataSinc()
                            .format(DateTimeFormatter
                                    .ofPattern("dd/MM/yyyy"));
                }
                return relogios.get(linha).getDataSinc();
        }
        
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                relogios.get(rowIndex).setNome((String) aValue);
                break;
            case 1:
                relogios.get(rowIndex).setIp((String) aValue);
                break;
            case 2:
                relogios.get(rowIndex).setPatrimonio((String) aValue);
                break;
            case 3:
                relogios.get(rowIndex).setNumeroSerie((String) aValue);
                break;
            case 4:
                relogios.get(rowIndex).setAtivo((boolean) aValue);
                break;
            case 5:
                relogios.get(rowIndex).setDataSinc((LocalDate) aValue);
                break;
        }
        
        this.fireTableRowsUpdated(rowIndex, rowIndex);
    }
    
    public List<RelogioPonto> getRelogios() {
        return Collections.unmodifiableList(relogios);
    }

    public void addRow(RelogioPonto relogio) {
        this.relogios.add(relogio);
        this.fireTableDataChanged();
    }

    public void removeRow(RelogioPonto relogio) {
        this.relogios.remove(relogio);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.relogios.clear();
        this.fireTableDataChanged();
    }
}
