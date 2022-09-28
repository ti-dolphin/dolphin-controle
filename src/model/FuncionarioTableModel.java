/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author guilherme.oliveira
 */
public class FuncionarioTableModel extends AbstractTableModel {

    private List<Funcionario> funcionarios = new ArrayList<Funcionario>();
    private String[] colunas = {"Coligada", "Chapa", "Nome", "Situação",
        "Função", "Data de Admissão", "Filial", "CPF","Digital", "Senha", 
        "Email"};

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
    public Object getValueAt(int linha, int coluna) {

        switch (coluna) {
            case 0:
                return funcionarios.get(linha).getCodColigada();
            case 1:
                return funcionarios.get(linha).getChapa();
            case 2:
                return funcionarios.get(linha).getNome();
            case 3:
                return funcionarios.get(linha).getSituacao().getCodSituacao();
            case 4:
                return funcionarios.get(linha).getFuncao().getNome();
            case 5:
                return funcionarios.get(linha).getDataAdmissao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            case 6:
                return funcionarios.get(linha).getCodFilial();
            case 7:
                return funcionarios.get(linha).getCpf();
            case 8:
                if (funcionarios.get(linha).getFinger1() != null || 
                        funcionarios.get(linha).getFinger2() != null ||
                        funcionarios.get(linha).getFinger3() != null ||
                        funcionarios.get(linha).getFinger4() != null ||
                        funcionarios.get(linha).getFinger5() != null ||
                        funcionarios.get(linha).getFinger6() != null) {
                    return "Cadastrado";
                } else {
                    return "";
                }
            case 9:
                if (funcionarios.get(linha).getSenha() != 0) {
                    return "Cadastrado";
                } else {
                    return "";
                }
            case 10:
                return funcionarios.get(linha).getEmail();
        }
        return null;
    }

    public List<Funcionario> getFuncionarios() {
        return Collections.unmodifiableList(funcionarios);
    }

    public void addRow(Funcionario f) {
        this.funcionarios.add(f);
        this.fireTableDataChanged();
    }

    public void clear() {
        funcionarios.clear();
        fireTableDataChanged();
    }

}
