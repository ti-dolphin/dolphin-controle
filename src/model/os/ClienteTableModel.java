/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.os;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ti
 */
public class ClienteTableModel extends AbstractTableModel {
    private List<Cliente> clientes = new ArrayList<>();
    private String[] colunas = {"Status", "Razão Social", "Nome Fantasia", 
        "CNPJ", "Responsável", "Data de Interação", "Classificação"};
    
    public final int ICONE_VERDE = 1;
    public final int ICONE_AMARELO = 2;
    public final int ICONE_VERMELHO = 3;
    
    public final int COLUNA_STATUS = 0;
    public final int COLUNA_RAZAO_SOCIAL = 1;
    public final int COLUNA_NOME_FANTASIA = 2;
    public final int COLUNA_CNPJ = 3;
    public final int COLUNA_RESPONSAVEL = 4;
    public final int COLUNA_DATA_INTERACAO = 5;
    public final int COLUNA_CLASSIFICACAO = 6;
    
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return clientes.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }
    
    @Override
    public Class<?> getColumnClass(int coluna) {
        switch (coluna) {
            case COLUNA_STATUS:
                return Integer.class;
            case COLUNA_DATA_INTERACAO:
                return LocalDate.class;
            case COLUNA_CLASSIFICACAO:
                return Character.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case COLUNA_STATUS:

                LocalDate dataInteracao = clientes.get(linha).getDataInteracao();

                LocalDate hoje = LocalDate.now();
                
                if (hoje.equals(dataInteracao.minus(1, ChronoUnit.DAYS))
                        || hoje.equals(dataInteracao.minus(2, ChronoUnit.DAYS))
                        || hoje.equals(dataInteracao.minus(3, ChronoUnit.DAYS))
                        ) {
                    return ICONE_AMARELO;
                } else if (hoje.isAfter(dataInteracao)) {
                    return ICONE_VERMELHO;
                } else {
                    return ICONE_VERDE;
                }
            case COLUNA_RAZAO_SOCIAL:
                return clientes.get(linha).getNome();
            case COLUNA_NOME_FANTASIA:
                return clientes.get(linha).getNomeFantasia();
            case COLUNA_CNPJ:
                return clientes.get(linha).getCnpj();
            case COLUNA_RESPONSAVEL:
                return clientes.get(linha).getResponsavel().getNome();
            case COLUNA_DATA_INTERACAO:
                return clientes.get(linha).getDataInteracao();
            case COLUNA_CLASSIFICACAO:
                return clientes.get(linha).getClassificacao().getAbreviacao();
        }

        return null;
    }

    public List<Cliente> getClientes() {
        return Collections.unmodifiableList(clientes);
    }

    public Cliente getRowValue(int l) {
        return clientes.get(l);
    }

    public void addRow(Cliente cliente) {
        this.clientes.add(cliente);
        this.fireTableDataChanged();
    }

    public void removeRow(Cliente cliente) {
        this.clientes.remove(cliente);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.clientes.clear();
        this.fireTableDataChanged();
    }
}
