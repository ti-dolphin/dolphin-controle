/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.os;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author guilherme.oliveira
 */
public class PessoaTableModel extends AbstractTableModel {

    private List<Pessoa> pessoas = new ArrayList<>();
    private String[] colunas = {"Código", "Nome", "Ativos"};

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return pessoas.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 2:
                return Boolean.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return pessoas.get(linha).getCodPessoa();
            case 1:
                return pessoas.get(linha).getNome();
            case 2:
                return pessoas.get(linha).isAtivo();
            default:
                return null;
        }
    }

    public List<Pessoa> getPessoas() {
        return Collections.unmodifiableList(pessoas);
    }

    public Pessoa getRowValue(int l) {
        return pessoas.get(l);
    }

    public void addRow(Pessoa p) {
        this.pessoas.add(p);
        this.fireTableDataChanged();
    }

    public void removeRow(Pessoa p) {
        this.pessoas.remove(p);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.pessoas.clear();
        this.fireTableDataChanged();
    }
}
