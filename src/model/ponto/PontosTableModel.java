/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ponto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author guilherme.oliveira
 */
public class PontosTableModel extends AbstractTableModel {

    private List<Ponto> pontos = new ArrayList<>();
    private String[] colunas = {"Colaborador", "Data", "Hora", "Relógio Ponto", "PIS", "Nº de Série"};
    
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
    
    @Override
    public int getRowCount() {
        return pontos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return pontos.get(linha).getFuncionario().getNome();
            case 1:
                if (pontos.get(linha).getData() != null) {
                    return pontos
                            .get(linha)
                            .getData()
                            .format(DateTimeFormatter
                                    .ofPattern("dd/MM/yyyy"));
                } else {
                    return pontos.get(linha).getData();
                }
            case 2:
                return pontos.get(linha).getHora();
            case 3:
                return pontos.get(linha).getRelogioPonto().getNome();
            case 4:
                return pontos.get(linha).getPis();
            case 5:
                return pontos.get(linha).getNsr();
        }
        
        return null;
    }
    
    public List<Ponto> getPontos() {
        return Collections.unmodifiableList(pontos);
    }

    public void addRow(Ponto ponto) {
        this.pontos.add(ponto);
        this.fireTableDataChanged();
    }

    public void removeRow(Ponto ponto) {
        this.pontos.remove(ponto);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.pontos.clear();
        this.fireTableDataChanged();
    }
    
}
