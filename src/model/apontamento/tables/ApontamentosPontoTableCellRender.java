/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.apontamento.tables;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDate;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import utilitarios.FormatarData;

/**
 *
 * @author ti
 */
public final class ApontamentosPontoTableCellRender extends DefaultTableCellRenderer {

    private ApontamentoPontoTableModel apTableModel;

    public ApontamentosPontoTableCellRender(ApontamentoPontoTableModel apTableModel) {
        this.apTableModel = apTableModel;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        boolean temProblema = (boolean) table.getValueAt(row, apTableModel.COLUNA_PROBLEMA);

        if (c instanceof JLabel) {
            final JLabel label = (JLabel) c;
            if (temProblema) {
                label.setForeground(Color.RED);
            } else {
                if (isSelected) {
                    label.setForeground(Color.WHITE);
                } else {
                    label.setForeground(Color.BLACK);
                }
            }
            if (value instanceof LocalDate) {
                label.setText(FormatarData.formatarDataEmTexto((LocalDate) value, "dd/MM/yyyy"));
            }
        }
        return c;
    }
}
