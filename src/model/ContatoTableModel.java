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
 * @author guilh
 */
public class ContatoTableModel extends AbstractTableModel {
    private List<Contato> contatos = new ArrayList<>();
    private String[] colunas = {"Nome", "Telefone", "E-mail"};
    
    public static final int COLUNA_NOME = 0;
    public static final int COLUNA_TELEFONE = 1;
    public static final int COLUNA_EMAIL = 2;
    
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return contatos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case COLUNA_NOME:
                return contatos.get(linha).getNome();
            case COLUNA_TELEFONE:
                return contatos.get(linha).getTelefone();
            case COLUNA_EMAIL:
                return contatos.get(linha).getEmail();
            default:
                return null;
        }
    }

    public List<Contato> getContatos() {
        return Collections.unmodifiableList(contatos);
    }

    public Contato getRowValue(int l) {
        return contatos.get(l);
    }

    public void addRow(Contato contato) {
        this.contatos.add(contato);
        this.fireTableDataChanged();
    }

    public void removeRow(Contato contato) {
        this.contatos.remove(contato);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.contatos.clear();
        this.fireTableDataChanged();
    }
}
