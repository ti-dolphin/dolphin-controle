/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.tables;

import java.awt.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ti
 */
public class NotificacaoTableCellRender extends DefaultTableCellRenderer {
    private NotificacaoTableModel notificacaoTableModel;

    public NotificacaoTableCellRender(NotificacaoTableModel notificacaoTableModel) {
        this.notificacaoTableModel = notificacaoTableModel;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (c instanceof JLabel) {
            final JLabel label = (JLabel) c;
            if (value instanceof LocalDateTime) {
                LocalDateTime data = (LocalDateTime) value;
                label.setText(data.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
            }
        }
        return c;
    }
}
