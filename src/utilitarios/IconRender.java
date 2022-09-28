/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author guilherme.oliveira
 */
public class IconRender extends DefaultTableCellRenderer {

    private final JTable table; // tabela que sofrerá a ação
    private final int columnAlter; // coluna que sofrerá as ações junto com as linhas (pintando uma unica célula)
    private final int VERDE = 1;
    private final int AMARELO = 2;
    private final int VERMELHO = 3;

    ImageIcon icone_verde;
    ImageIcon icone_amarelo;
    ImageIcon icone_vermelho;

    public IconRender(JTable table, int columnAlter) {
        this.table = table;
        this.columnAlter = columnAlter;
        try {
            String caminho = new File(".").getCanonicalPath() + File.separator + "img";

            this.icone_verde = new ImageIcon(caminho + File.separator + "verde.png");
            this.icone_amarelo = new ImageIcon(caminho + File.separator + "amarelo.png");
            this.icone_vermelho = new ImageIcon(caminho + File.separator + "vermelho.png");

        } catch (IOException ex) {
            Logger.getLogger(IconRender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {

        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (isSelected) {
            // define cor e fonte caso a linha esteja selecionada      
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionBackground());
        } else {// caso não esteja selecionado...
            // define cor zebrada da tabela  
            if (row % 2 == 0) { // pinta a linha de branco quando a linha for par
                setBackground(Color.WHITE);
                setForeground(Color.white);
            } else { // pinta a  linha de cinza quando for impar
                Color corCinza = new Color(235, 235, 235);
                setBackground(corCinza);
                setForeground(corCinza);
            }
        }
        setIcone(table, row, column);
        return this;
    }

    private void setIcone(JTable table1, int row, int column) {
        try {
            if (table1.getValueAt(row, columnAlter).equals(VERDE)) {
                if (column == columnAlter) {
                    this.setIcon(icone_verde);
                }
            } else if (table1.getValueAt(row, columnAlter).equals(AMARELO)) {
                if (column == columnAlter) {
                    this.setIcon(icone_amarelo);
                }
            } else if (table1.getValueAt(row, columnAlter).equals(VERMELHO)) {
                if (column == columnAlter) {
                    this.setIcon(icone_vermelho);
                }
            } else {
                if (column == columnAlter) {
                    this.setIcon(null);
                }
            }

        } catch (java.lang.NullPointerException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }
}
