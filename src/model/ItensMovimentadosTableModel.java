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
public class ItensMovimentadosTableModel extends AbstractTableModel {

    private List<MovimentoItem> itens = new ArrayList<>();
    private final String[] colunas = {"ID", "Data", "Produto", "Cód. Patrimônio", "Patrimônio",
        "Estoque de Origem", "Estoque de Destino",
        "Responsável"};
    
    public final int COLUNA_ID = 0;
    public final int COLUNA_DATA = 1;
    public final int COLUNA_PRODUTO = 2;
    public final int COLUNA_CODPATRIMONIO = 3;
    public final int COLUNA_PATRIMONIO = 4;
    public final int COLUNA_ESTOQUE_ORIGEM = 5;
    public final int COLUNA_ESTOQUE_DESTINO = 6;
    public final int COLUNA_RESPONSAVEL = 7;

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
            case COLUNA_ID:
                return itens.get(rowIndex).getId();
            case COLUNA_DATA:
                return itens.get(rowIndex).getMovimento().getDataEntrega();
            case COLUNA_PRODUTO:
                return itens.get(rowIndex).getProduto().getNomeFantasia();
            case COLUNA_CODPATRIMONIO:
                return itens.get(rowIndex).getPatrimonio().getCodPatrimonio();
            case COLUNA_PATRIMONIO:
                return itens.get(rowIndex).getPatrimonio().getDescricao();
            case COLUNA_ESTOQUE_ORIGEM:
                return itens.get(rowIndex).getMovimento().getLocalDeEstoqueOrigem().getNome();
            case COLUNA_ESTOQUE_DESTINO:
                return itens.get(rowIndex).getMovimento().getLocalDeEstoqueDestino().getNome();
            case COLUNA_RESPONSAVEL:
                return itens.get(rowIndex).getMovimento().getResponsavel().getNome();
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
