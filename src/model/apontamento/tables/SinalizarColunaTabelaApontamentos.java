/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.apontamento.tables;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author ti
 */
public final class SinalizarColunaTabelaApontamentos extends DefaultTableCellRenderer {

    private Color corEscuraDaLinha = new Color(243, 243, 243);

    private ApontamentoTableModel apTableModel;
    private Icon iconeVerde;
    private Icon iconeVermelho;
    private String caminho;

    public SinalizarColunaTabelaApontamentos(ApontamentoTableModel apTableModel) {
        this.apTableModel = apTableModel;
        try {
            caminho = new File(".").getCanonicalPath() + File.separator + "img";
            iconeVerde = new ImageIcon(caminho + File.separator + "verde.png");
            iconeVermelho = new ImageIcon(caminho + File.separator + "vermelho.png");
        } catch (IOException ex) {
            Logger.getLogger(SinalizarColunaTabelaApontamentos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (component instanceof JLabel) {
            final JLabel label = (JLabel) component;
            setHorizontalAlignment(CENTER);
            if (isSelected) {
                label.setBackground(table.getSelectionBackground());
                label.setForeground(table.getSelectionBackground());
            } else {
                if (row % 2 == 0) {
                    label.setBackground(Color.white);
                    label.setForeground(Color.white);
                } else {
                    label.setBackground(corEscuraDaLinha);
                    label.setForeground(corEscuraDaLinha);
                }
            }
            if (column == apTableModel.COLUNA_ASSIDUIDADE) {

                if ((boolean) value == false) {
                    label.setIcon(iconeVermelho);
                } else {
                    label.setIcon(iconeVerde);
                }
            }

        }

        return component;
    }
}
