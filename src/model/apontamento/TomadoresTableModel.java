/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.apontamento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author guilherme.oliveira
 */
public class TomadoresTableModel extends AbstractTableModel {

    private List<Apontamento> apontamentos = new ArrayList<>();
    private String[] colunas = {"Chapa", "Colaborador", "Centro de Custo", "CNO",
        "Qtd Dias", "%"};

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return apontamentos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return apontamentos.get(linha).getFuncionario().getChapa();
            case 1:
                return apontamentos.get(linha).getFuncionario().getNome();
            case 2:
                return apontamentos.get(linha).getCentroCusto().getNome();
            case 3:
                return apontamentos.get(linha).getCentroCusto().getCodReduzido();
            case 4:
                return apontamentos.get(linha).getQuantidadeDias();
            case 5:
                return apontamentos.get(linha).getPorcentagemDeDiasTrabalhados();
        }
        return null;
    }

    public List<Apontamento> getApontamentos() {
        return Collections.unmodifiableList(apontamentos);
    }

    public void addRow(Apontamento a) {
        this.apontamentos.add(a);
        this.fireTableDataChanged();
    }

    public void removeRow(Apontamento a) {
        this.apontamentos.remove(a);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.apontamentos.clear();
        this.fireTableDataChanged();
    }

}
