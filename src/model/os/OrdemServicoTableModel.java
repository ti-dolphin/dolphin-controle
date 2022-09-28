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
 * @author guilherme.oliveira
 */
public class OrdemServicoTableModel extends AbstractTableModel {

    private List<OrdemServico> ordensServicos = new ArrayList<>();
    private String[] colunas = {"Nº", "#", "Data da Interação", "Projeto", "Adicional", 
        "Cliente", "Tarefa", "Status", "Classificação", "Valor Total", 
        "Centro de Custo", "Solicitação", "Necessidade", "Início", "Prev. de Fechamento", 
        "Fechamento", "Responsável"};

    public final int ICONE_VERDE = 1;
    public final int ICONE_AMARELO = 2;
    public final int ICONE_VERMELHO = 3;
    
    public final int COLUNA_NUMERO_OS = 0;
    public final int COLUNA_STATUS_INTERACAO = 1;
    public final int COLUNA_DATA_INTERACAO = 2;
    public final int COLUNA_PROJETO = 3;
    public final int COLUNA_ADICIONAL = 4;
    
    public final int COLUNA_CLIENTE = 5;
    public final int COLUNA_TAREFA = 6;
    public final int COLUNA_STATUS = 7;
    public final int COLUNA_CLASSIFICACAO_ABC = 8;
    public final int COLUNA_VALOR_TOTAL = 9;
    
    public final int COLUNA_CENTRO_CUSTO = 10;
    public final int COLUNA_DATA_SOLICITACAO = 11;
    public final int COLUNA_DATA_NECESSIDADE = 12;
    public final int COLUNA_DATA_INICIO = 13;
    public final int COLUNA_DATA_PREVISAO_ENTREGA = 14; 
    
    public final int COLUNA_DATA_ENTREGA = 15;
    public final int COLUNA_RESPONSAVEL = 16;

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public int getRowCount() {
        return ordensServicos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Class<?> getColumnClass(int coluna) {
        switch (coluna) {
            case COLUNA_NUMERO_OS:
                return Integer.class;
            case COLUNA_ADICIONAL:
                return Integer.class;
            case COLUNA_PROJETO:
                return Integer.class;
            case COLUNA_CLASSIFICACAO_ABC:
                return Integer.class;
            case COLUNA_VALOR_TOTAL:
                return Double.class;
            case COLUNA_DATA_SOLICITACAO:
                return LocalDate.class;
            case COLUNA_DATA_NECESSIDADE:
                return LocalDate.class;
            case COLUNA_DATA_INICIO:
                return LocalDate.class;
            case COLUNA_DATA_PREVISAO_ENTREGA:
                return LocalDate.class;
            case COLUNA_DATA_ENTREGA:
                return LocalDate.class;
            case COLUNA_DATA_INTERACAO:
                return LocalDate.class;
            case COLUNA_STATUS_INTERACAO:
                return Integer.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch (coluna) {
            case COLUNA_NUMERO_OS:
                return ordensServicos.get(linha).getCodOs();
            case COLUNA_STATUS_INTERACAO:

                LocalDate dataInteracao = ordensServicos.get(linha).getDataInteracao();

                LocalDate hoje = LocalDate.now();
                Status status = ordensServicos.get(linha).getStatus();
                boolean vaiVencer = ordensServicos.get(linha).dataInteracaoAVencer();
                
                boolean venceu = hoje.isAfter(dataInteracao);
                
                if (status.getAcao() == 1) { // se o status de acao 1 não precisa ser feito interação com o cliente
                    return 0;
                } else {
                    if (vaiVencer) { // se hoje é 3 dias antes da data de interação com o cliente
                        return ICONE_AMARELO;
                    } else if (venceu) { // se hoje passou da data de interação com o cliente
                        return ICONE_VERMELHO;
                    } else {
                        return ICONE_VERDE;
                    }
                }
                
            case COLUNA_DATA_INTERACAO:
                return ordensServicos.get(linha).getDataInteracao();
            case COLUNA_PROJETO:
                return ordensServicos.get(linha).getProjeto().getId();
            case COLUNA_ADICIONAL:
                if (ordensServicos.get(linha).getAdicional().getNumero() != 0) {
                    return ordensServicos.get(linha).getAdicional().getNumero();
                } else {
                    return 0;
                }
            case COLUNA_CLIENTE:
                if (ordensServicos.get(linha).getCliente() != null) {
                    return ordensServicos.get(linha).getCliente().getNomeFantasia();
                } else {
                    return "";
                }
            case COLUNA_TAREFA:
                return ordensServicos.get(linha).getNome();
            case COLUNA_STATUS:
                return ordensServicos.get(linha).getStatus().getNome();
            case COLUNA_CLASSIFICACAO_ABC:
                return ordensServicos.get(linha).getPrioridade();
            case COLUNA_VALOR_TOTAL:
                return ordensServicos.get(linha).getVenda().calcularValorTotal();
            case COLUNA_CENTRO_CUSTO:
                return ordensServicos.get(linha).getCentroCusto().getNome();
            case COLUNA_DATA_SOLICITACAO:
                return ordensServicos.get(linha).getDataSolicitacao();
            case COLUNA_DATA_NECESSIDADE:
                return ordensServicos.get(linha).getDataNecessidade();
            case COLUNA_DATA_INICIO:
                return ordensServicos.get(linha).getDataInicio();
            case COLUNA_DATA_PREVISAO_ENTREGA:
                return ordensServicos.get(linha).getDataPrevEntrega();
            case COLUNA_DATA_ENTREGA:
                return ordensServicos.get(linha).getDataEntrega();
            case COLUNA_RESPONSAVEL:
                return ordensServicos.get(linha).getResponsavel().getNome();
            default:
                return null;
        }
    }

    public List<OrdemServico> getOrdensServicos() {
        return Collections.unmodifiableList(ordensServicos);
    }

    public OrdemServico getRowValue(int l) {
        return ordensServicos.get(l);
    }

    public void addRow(OrdemServico os) {
        this.ordensServicos.add(os);
        this.fireTableDataChanged();
    }

    public void removeRow(OrdemServico os) {
        this.ordensServicos.remove(os);
        this.fireTableDataChanged();
    }

    public void clear() {
        this.ordensServicos.clear();
        this.fireTableDataChanged();
    }
}
