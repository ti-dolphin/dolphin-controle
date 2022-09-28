/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import utilitarios.FormatarData;

/**
 *
 * @author ti
 */
public class ClienteComentarioTableModel extends AbstractTableModel {
    
     private List<ClienteComentario> comentarios = new ArrayList<>();
    private String[] colunas = {"Descrição", "Data",
        "Usuário"};
    
    public final int COLUNA_DESCRICAO = 0;
    public final int COLUNA_CRIADO_EM = 1;
    public final int COLUNA_CRIADO_POR = 2;

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
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case 0:
                return comentarios.get(linha).getDescricao();
            case 1:
                return FormatarData.formatarDataEHoraEmTexto(comentarios.get(linha).getCriadoEm(), "dd/MM/yyyy");
            case 2:
                return comentarios.get(linha).getCriadoPor();
        }

        return null;
    }
    
    public List<ClienteComentario> getClientes() {
        return Collections.unmodifiableList(comentarios);
    }

    public ClienteComentario getRowValue(int l) {
        return comentarios.get(l);
    }

    public void addRow(ClienteComentario comentario) {
        this.comentarios.add(comentario);
        this.fireTableDataChanged();
    }

    public void removeRow(ClienteComentario comentario) {
        this.comentarios.remove(comentario);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.comentarios.clear();
        this.fireTableDataChanged();
    }
}
