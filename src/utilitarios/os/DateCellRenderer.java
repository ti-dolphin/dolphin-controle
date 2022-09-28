/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios.os;

import java.awt.Component;
import java.time.LocalDate;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import utilitarios.FormatarData;

/**
 *
 * @author guilherme.oliveira
 */
public class DateCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
            final boolean hasFocus, final int row, final int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if ((value !=null)) {
            
            LocalDate dataNaoFormatada = (LocalDate) value;
            String dataFormatada;
            
            dataFormatada = FormatarData.formatarDataEmTexto(dataNaoFormatada, "dd/MM/yyyy");
            
            setText(dataFormatada);
        } else {
            setText("");
        }
        return c;
    }
}
