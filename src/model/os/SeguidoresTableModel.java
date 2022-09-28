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
import model.Seguidor;

/**
 *
 * @author guilherme.oliveira
 */
public class SeguidoresTableModel extends AbstractTableModel {

    private List<Seguidor> seguidores = new ArrayList<>();
    private String[] colunas = {"Nome"};

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return seguidores.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return seguidores.get(linha).getPessoa().getNome();
        }//switch
        return null;
    }

    public List<Seguidor> getSeguidor() {
        return Collections.unmodifiableList(seguidores);
    }

    public void addRow(Seguidor seguidor) {
        if (!seguidores.contains(seguidor)) {
            this.seguidores.add(seguidor);
            this.fireTableDataChanged();
        }
    }

    public void removeRow(Seguidor seguidor) {
        this.seguidores.remove(seguidor);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.seguidores.clear();
        this.fireTableDataChanged();
    }

}
