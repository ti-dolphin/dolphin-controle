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
public class PessoaResponsavelTableModel extends AbstractTableModel {

    private List<PessoaResponsavel> pessoasResponsaveis = new ArrayList<>();
    private String[] colunas = {"Código", "Responsável"};
    
    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }
    
    @Override
    public int getRowCount() {
        return pessoasResponsaveis.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        switch(coluna) {
            case 0:
                return pessoasResponsaveis.get(linha).getResponsavel().getCodPessoa();
            case 1:
                return pessoasResponsaveis.get(linha).getResponsavel().getNome();
        }
        return null;
    }
    
    public List<PessoaResponsavel> getPessoasResponsaveis(){
        return Collections.unmodifiableList(pessoasResponsaveis);
    }
    
    public void addRow(PessoaResponsavel pessoaResponsavel){
        this.pessoasResponsaveis.add(pessoaResponsavel);
        this.fireTableDataChanged();
    }
    
    public void removeRow(PessoaResponsavel pessoaResponsavel){
        this.pessoasResponsaveis.remove(pessoaResponsavel);
        this.fireTableDataChanged();
    }
    
    public void clear(){
        this.pessoasResponsaveis.clear();
        this.fireTableDataChanged();
    }
    
}
