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
import model.os.Projeto;

/**
 *
 * @author guilherme.oliveira
 */
public class ProjetoTableModel extends AbstractTableModel {

    private List<Projeto> projetos = new ArrayList<>();
    private String[] colunas = {"Código", "Adicional", "Descrição"};

    private final int COLUNA_CODIGO = 0;
    private final int COLUNA_ADICIONAL = 1;
    private final int COLUNA_NOME = 2;

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return projetos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case COLUNA_CODIGO:
                return projetos.get(rowIndex).getId();
            case COLUNA_ADICIONAL:
                if (projetos.get(rowIndex).getAdicional().getNumero() > 0) {
                    return projetos.get(rowIndex).getAdicional().getNumero();
                } else {
                    return "";
                }
            case COLUNA_NOME:
                return projetos.get(rowIndex).getDescricao();
            default:
                return null;
        }
    }

    public List<Projeto> getProjetos() {
        return Collections.unmodifiableList(projetos);
    }

    public Projeto getRowValue(int rowIndex) {
        return projetos.get(rowIndex);
    }

    public void addRow(Projeto projeto) {
        this.projetos.add(projeto);
        this.fireTableDataChanged();
    }

    public void removeRow(Projeto projeto) {
        this.projetos.remove(projeto);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.projetos.clear();
        this.fireTableDataChanged();
    }

}
