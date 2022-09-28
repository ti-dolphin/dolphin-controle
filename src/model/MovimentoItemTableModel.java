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
public class MovimentoItemTableModel extends AbstractTableModel {

    private List<MovimentoItem> itens = new ArrayList<>();
    private final String[] colunas = {"Produto", "Cód. Patrimônio", "Patrimônio"};
    private final int COLUNA_PRODUTO = 0;
    private final int COLUNA_CODIGO_PATRIMONIO = 1;
    private final int COLUNA_PATRIMONIO = 2;

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return itens.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case COLUNA_PRODUTO:
                if (itens.get(rowIndex).getProduto() != null) {
                    return itens.get(rowIndex).getProduto().getNomeFantasia();
                }
                return "";
            case COLUNA_CODIGO_PATRIMONIO:
                if (itens.get(rowIndex).getPatrimonio() != null) {
                    return itens.get(rowIndex).getPatrimonio().getCodPatrimonio();
                }
                return "";
            case COLUNA_PATRIMONIO:
                if (itens.get(rowIndex).getPatrimonio() != null) {
                    return itens.get(rowIndex).getPatrimonio().getDescricao();
                }
                return "";
            default:
                return null;
        }
    }

    public List<MovimentoItem> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public void addRow(MovimentoItem item) {
        this.itens.add(item);
        this.fireTableDataChanged();
    }

    public void removeRow(MovimentoItem item) {
        this.itens.remove(item);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.itens.clear();
        this.fireTableDataChanged();
    }

    public MovimentoItem getRowValue(int rowIndex) {
        return itens.get(rowIndex);
    }
}
