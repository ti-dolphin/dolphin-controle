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
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 *
 * @author ti
 */
public class IconeApontamentoTableCellRederer extends JLabel implements TableCellRenderer {

    private String caminho;
    private final Icon iconeVerde;
    private final Icon iconeVermelho;

    public IconeApontamentoTableCellRederer() {
        setOpaque(true);
        try {
            caminho = new File(".").getCanonicalPath() + File.separator + "img";
        } catch (IOException ex) {
            Logger.getLogger(IconeApontamentoTableCellRederer.class.getName()).log(Level.SEVERE, null, ex);
        }
        iconeVerde = new ImageIcon(caminho + File.separator + "verde.png");
        iconeVermelho = new ImageIcon(caminho + File.separator + "vermelho.png");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row,
            int column) {

        setHorizontalAlignment(CENTER);
        Color corEscuraDaLinha = new Color(243, 243, 243);

        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(Color.WHITE);
        } else {
            if (row % 2 == 0) {
                setBackground(Color.WHITE);
            } else {
                setBackground(corEscuraDaLinha);
            }
            setForeground(Color.BLACK);
        }
        
        if (value instanceof Boolean) {
            if ((boolean) value) {
                setIcon(iconeVerde);
            } else {
                setIcon(iconeVermelho);
            }
        }
        
        if (value instanceof Double) {
            setText(value.toString());
        }

        return this;
    }

    @Override
    public void validate() {
    }

    @Override
    public void revalidate() {
    }

    @Override
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    }

    @Override
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {
    }
}
