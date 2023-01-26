/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.apontamento.tables;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.apontamento.Apontamento;

/**
 *
 * @author guilherme.oliveira
 */
public class ApontamentoTableModel extends AbstractTableModel {

    private List<Apontamento> apontamentos = new ArrayList<>();
    private String[] colunas = {"Assiduidade", "Comentado", "Chapa", "Funcionário", "Função",
        "Data", "Dia da Semana", "Gerente", "Centro de Custo", "CNO (CEI)", 
        
        "Status", "Líder", "Atividade", "Situação", "Modificado Por", 
        
        "N° OS/Tarefa"};

    public static final int COLUNA_ASSIDUIDADE = 0;
    public static final int COLUNA_COMENTADO = 1;
    public static final int COLUNA_CHAPA = 2;
    public static final int COLUNA_FUNCIONARIO = 3;
    public static final int COLUNA_FUNCAO = 4;
    
    public static final int COLUNA_DATA = 5;
    public static final int COLUNA_DIA_DA_SEMANA = 6;
    public static final int COLUNA_GERENTE = 7;
    public static final int COLUNA_CENTRO_CUSTO = 8;
    public static final int COLUNA_CNO = 9;
    
    public static final int COLUNA_STATUS = 10;
    public static final int COLUNA_LIDER = 11;
    public static final int COLUNA_ATIVIDADE = 12;
    public static final int COLUNA_SITUACAO = 13;
    public static final int COLUNA_MODIFICADO_POR = 14;
    
    public static final int COLUNA_NUMERO_OS = 15;

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
            case COLUNA_ASSIDUIDADE:
                return Boolean.class;
            case COLUNA_CHAPA:
                return Integer.class;
            case COLUNA_DATA:
                return LocalDate.class;
            case COLUNA_DIA_DA_SEMANA:
                return DayOfWeek.class;
            case COLUNA_NUMERO_OS:
                return Integer.class;
            case COLUNA_SITUACAO:
                return Character.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Apontamento apontamento = apontamentos.get(linha);
        switch (coluna) {
            case COLUNA_ASSIDUIDADE:
                return apontamento.isAssiduidade();
            case COLUNA_COMENTADO:
                if (apontamento.isComentado()) {
                    return "Sim";
                }
                return "";
            case COLUNA_CHAPA:
                return apontamento.getFuncionario().getChapa();
            case COLUNA_FUNCIONARIO:
                return apontamento.getFuncionario().getNome();
            case COLUNA_FUNCAO:
                return apontamento.getFuncionario().getFuncao().getNome();
            case COLUNA_DATA:
                return apontamento.getData();
            case COLUNA_DIA_DA_SEMANA:
                return apontamento.getData().getDayOfWeek();
            case COLUNA_GERENTE:
                return apontamento.getGerente().getNome();
            case COLUNA_CENTRO_CUSTO:
                return apontamento.getCentroCusto().getNome();
            case COLUNA_CNO:
                return apontamento.getCentroCusto().getCodReduzido();
            case COLUNA_STATUS:
                return apontamento.getStatusApont().getDescricao();
            case COLUNA_LIDER:
                return apontamento.getLider().getNome();
            case COLUNA_ATIVIDADE:
                return apontamento.getAtividade();
            case COLUNA_SITUACAO:
                return apontamento.getSituacao().getCodSituacao();
            case COLUNA_MODIFICADO_POR:
                return apontamento.getModificadoPor();
            case COLUNA_NUMERO_OS:
                return apontamento.getOrdemServico().getCodOs();
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
