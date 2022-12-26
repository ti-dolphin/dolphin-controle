/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.epi.tables;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Funcionario;

/**
 *
 * @author guilherme.oliveira
 */
public class FuncionariosTableModel extends AbstractTableModel {
    
    private List<Funcionario> funcionarios = new ArrayList<>();
    private String[] colunas = {"Chapa", "Nome", "Ativo"};

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return funcionarios.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }
    
    @Override
    public Class<?> getColumnClass(int coluna) {
        switch (coluna) {
            case 2:
                return Boolean.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return funcionarios.get(rowIndex).getChapa();
            case 1:
                return funcionarios.get(rowIndex).getNome();
            case 2:
                return funcionarios.get(rowIndex).getSituacao().getCodSituacao() != 'D';
            default:
                return null;
        }
    }

    public List<Funcionario> getFuncionarios() {
        return Collections.unmodifiableList(funcionarios);
    }
    
    public void addRow(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
        this.fireTableDataChanged();
    }

    public void removeRow(Funcionario funcionario) {
        this.funcionarios.remove(funcionario);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.funcionarios.clear();
        this.fireTableDataChanged();
    }

    public Funcionario getRowValue(int rowIndex) {
        return funcionarios.get(rowIndex);
    }
    
}
