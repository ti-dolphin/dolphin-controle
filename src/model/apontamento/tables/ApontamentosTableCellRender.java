/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.apontamento.tables;

import java.awt.Component;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ti
 */
public final class ApontamentosTableCellRender extends DefaultTableCellRenderer {

    private ApontamentoTableModel aTableModel;

    public ApontamentosTableCellRender(ApontamentoTableModel aTableModel) {
        this.aTableModel = aTableModel;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (c instanceof JLabel) {
            final JLabel label = (JLabel) c;
            if (value instanceof LocalDate) {
                LocalDate data = (LocalDate) value;
                label.setText(data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }

            if (value instanceof DayOfWeek) {
                DayOfWeek dayOfWeek = (DayOfWeek) value;
                String diaDaSemana = dayOfWeek.getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
                label.setText(diaDaSemana);
            }
        }

        return c;
    }
}
