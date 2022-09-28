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
public class ProdutoTableModel extends AbstractTableModel {
    
    private List<Produto> produtos = new ArrayList<>();
    private String[] colunas = {"Coligada", "Nome", "Descrição"};

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return produtos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return produtos.get(rowIndex).getCodColigada();
            case 1:
                return produtos.get(rowIndex).getNomeFantasia();
            case 2:
                return produtos.get(rowIndex).getDescricao();
            default:
                return null;
        }
    }

    public List<Produto> getProdutos() {
        return Collections.unmodifiableList(produtos);
    }

    public Produto getRowValue(int rowIndex) {
        return produtos.get(rowIndex);
    }

    public void addRow(Produto produto) {
        this.produtos.add(produto);
        this.fireTableDataChanged();
    }

    public void removeRow(Produto produto) {
        this.produtos.remove(produto);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.produtos.clear();
        this.fireTableDataChanged();
    }
}
