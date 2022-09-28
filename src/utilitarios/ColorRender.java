/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author guilherme.oliveira
 */
public class ColorRender extends DefaultTableCellRenderer {

    private final JTable table; // tabela que sofrerá a ação
    private final int columnAlter; // coluna que sofrerá as ações junto com as linhas (pintando uma unica célula)
    private final int VERDE = 1;
    private final int AMARELO = 2;
    private final int VERMELHO = 3;

    public ColorRender(JTable table, int columnAlter) {
        this.table = table;
        this.columnAlter = columnAlter;
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {

        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        
            try {
                if (table.getValueAt(row, columnAlter).equals(VERDE)) {
                    if (column == columnAlter) { // Mais um regra: quero que pinte apenas a coluna selecionada pelo columnAlter
                        setForeground(Color.GREEN); // Volta para cor fonte default
                        setBackground(Color.GREEN);
                    }
                } else if (table.getValueAt(row, columnAlter).equals(AMARELO)) {
                    if (column == columnAlter) { // Mais um regra: quero que pinte apenas a coluna selecionada pelo columnAlter
                        setForeground(Color.YELLOW);
                        setBackground(Color.YELLOW);
                    }
                } else {
                    if (column == columnAlter) { // Mais um regra: quero que pinte apenas a coluna selecionada pelo columnAlter
                        setForeground(Color.RED);
                        setBackground(Color.RED);
                    }
                }

            } catch (java.lang.NullPointerException ex) {
            }
        return this;
    }
}
