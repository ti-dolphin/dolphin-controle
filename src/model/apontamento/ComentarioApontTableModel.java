/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.apontamento;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author guilherme.oliveira
 */
public class ComentarioApontTableModel extends AbstractTableModel {

    private List<Comentario> comentarios = new ArrayList<>();
    private String[] colunas = {"Id", "Descrição", "Data",
        "Usuário"};

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return comentarios.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return comentarios.get(linha).getCodComentario();
            case 1:
                return comentarios.get(linha).getDescricao();
            case 2:
                return comentarios.get(linha).getCreatedOn()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            case 3:
                return comentarios.get(linha).getCreatedBy();
        }

        return null;
    }

    public List<Comentario> getComentarios() {
        return Collections.unmodifiableList(comentarios);
    }

    public void addRow(Comentario comentario) {
        this.comentarios.add(comentario);
        this.fireTableDataChanged();
    }

    public void removeRow(Comentario comentario) {
        this.comentarios.remove(comentario);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.comentarios.clear();
        this.fireTableDataChanged();
    }
}