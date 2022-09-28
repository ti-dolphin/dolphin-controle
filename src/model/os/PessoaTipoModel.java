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
public class PessoaTipoModel extends AbstractTableModel {

    List<PessoaTipo> pessoasTipos = new ArrayList<>();
    private String[] colunas = {"Tipo"};
    
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return pessoasTipos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch(coluna) {
            case 0:
                return pessoasTipos.get(linha).getTipo().getDescricao();
        }
        
        return null;
    }
    
    
    public List<PessoaTipo> getPessoaTipo() {
        return Collections.unmodifiableList(pessoasTipos);
    }

    public void addRow(PessoaTipo pessoaTipo) {
        if (!pessoasTipos.contains(pessoaTipo)) {
            this.pessoasTipos.add(pessoaTipo);
            this.fireTableDataChanged();
        }
    }

    public void removeRow(PessoaTipo pessoaTipo) {
        this.pessoasTipos.remove(pessoaTipo);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.pessoasTipos.clear();
        this.fireTableDataChanged();
    }
}
