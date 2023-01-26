/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.apontamento.tables;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.apontamento.Apontamento;

/**
 *
 * @author ti
 */
public class ApontamentoProblemaTableModel extends AbstractTableModel {

    private List<Apontamento> apontamentos = new ArrayList<>();
    private final String[] colunas = {"Comentado", 
        "Chapa", 
        "Colaborador", 
        "Obra", 
        "Data", 
        "Status", 
        "Gerente", 
        "Líder", 
        "Motivo", 
        "Justificativa"};
    public final int colunaComentado = 0;
    public final int colunaChapa = 1;
    public final int colunaColaborador = 2;
    public final int colunaObra = 3;
    public final int colunaData = 4;
    public final int colunaStatus = 5;
    public final int colunaGerente = 6;
    public final int colunaLider = 7;
    public final int colunaMotivo = 8;
    public final int colunaJustificativa = 9;
    
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return apontamentos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Class<?> getColumnClass(int coluna) {
        switch (coluna) {
            case colunaComentado:
                return Boolean.class;
            case colunaData:
                return LocalDate.class;
            default:
                return String.class;
        }
    }
    
    @Override
    public Object getValueAt(int linha, int coluna) {
        Apontamento apontamento = apontamentos.get(linha);
        switch (coluna) {
            case colunaComentado:
                return apontamento.isComentado();
            case colunaChapa:
                return apontamento.getFuncionario().getChapa();
            case colunaColaborador:
                return apontamento.getFuncionario().getNome();
            case colunaObra:
                return apontamento.getCentroCusto().getNome();
            case colunaData:
                return apontamento.getData();
            case colunaGerente:
                return apontamento.getGerente().getNome();
            case colunaLider:
                return apontamento.getLider().getNome();
            case colunaStatus:
                return apontamento.getStatusApont().getDescricao();
            case colunaMotivo:
                return apontamento.getMotivo();
            case colunaJustificativa:
                return apontamento.getJustificativa();
        }
        return null;
    }

    public List<Apontamento> getApontamentos() {
        return Collections.unmodifiableList(apontamentos);
    }

    public Apontamento getApontamento(int indiceLinha) {
        if (indiceLinha > -1) {
            Apontamento apontamento = apontamentos.get(indiceLinha);
            return apontamento;
        }
        return null;
    }

    public void addRow(Apontamento a) {
        this.apontamentos.add(a);
        this.fireTableDataChanged();
    }

    public void removeRow(Apontamento a) {
        this.apontamentos.remove(a);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.apontamentos.clear();
        this.fireTableDataChanged();
    }
}
