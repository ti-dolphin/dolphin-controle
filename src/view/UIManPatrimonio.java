/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PatrimonioDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import model.Patrimonio;
import model.PatrimonioCheckListTableModel;

/**
 *
 * @author guilherme.oliveira
 */
public class UIManPatrimonio extends javax.swing.JDialog {

    private PatrimonioDAO patrimonioDAO;
    private PatrimonioCheckListTableModel patrimonioTableModel;
    private TableRowSorter<PatrimonioCheckListTableModel> sorter;

    public UIManPatrimonio() {
        initComponents();
        this.patrimonioDAO = new PatrimonioDAO();
        patrimonioTableModel = new PatrimonioCheckListTableModel();
        sorter = new TableRowSorter<>(patrimonioTableModel);
        jtPatrimonios.setRowSorter(sorter);
        buscar();
    }

    private void buscar() {
        try {
            for (Patrimonio patrimonio : patrimonioDAO.buscar()) {
                patrimonioTableModel.addRow(patrimonio);
            }

            jtPatrimonios.setModel(patrimonioTableModel);
            
        } catch (Exception ex) {
            Logger.getLogger(UIManPatrimonio.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Erro ao buscar patrimonios", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    private void pesquisar() {
        try {
            
            String codigo = jtfCodPatrimonio.getText().trim();
            String descricao = jtfDescricao.getText().trim();
            List<RowFilter<Object, Object>> filters = new ArrayList<>();
            filters.add(RowFilter.regexFilter("(?i)" + codigo, patrimonioTableModel.COLUNA_CODIGO));
            filters.add(RowFilter.regexFilter("(?i)" + descricao, patrimonioTableModel.COLUNA_DESCRICAO));
            sorter.setRowFilter(RowFilter.andFilter(filters));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), 
                    "Erro ao pesquisar patrimonios", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparFiltros() {
        jtfCodPatrimonio.setText("");
        jtfDescricao.setText("");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlCodPatrimonio = new javax.swing.JLabel();
        jtfCodPatrimonio = new javax.swing.JTextField();
        jtfDescricao = new javax.swing.JTextField();
        jlDescricao = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtPatrimonios = new javax.swing.JTable();
        jbPesquisar = new javax.swing.JButton();
        jbLimparFiltros = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);

        jlCodPatrimonio.setText("Código");

        jtfCodPatrimonio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfCodPatrimonioKeyReleased(evt);
            }
        });

        jtfDescricao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfDescricaoKeyReleased(evt);
            }
        });

        jlDescricao.setText("Descrição");

        jtPatrimonios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtPatrimonios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtPatrimoniosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtPatrimonios);

        jbPesquisar.setText("Pesquisar");
        jbPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPesquisarActionPerformed(evt);
            }
        });

        jbLimparFiltros.setText("Limpar Filtros");
        jbLimparFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimparFiltrosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfCodPatrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlCodPatrimonio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlDescricao)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtfDescricao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbLimparFiltros)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbPesquisar)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCodPatrimonio)
                    .addComponent(jlDescricao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfCodPatrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbPesquisar)
                    .addComponent(jbLimparFiltros))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtfCodPatrimonioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCodPatrimonioKeyReleased
        pesquisar();
    }//GEN-LAST:event_jtfCodPatrimonioKeyReleased

    private void jtfDescricaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfDescricaoKeyReleased
        pesquisar();
    }//GEN-LAST:event_jtfDescricaoKeyReleased

    private void jbLimparFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparFiltrosActionPerformed
        limparFiltros();
        Menu.carregamento(true);
        new Thread(()->{
            buscar();
            Menu.carregamento(false);
        }).start();
    }//GEN-LAST:event_jbLimparFiltrosActionPerformed

    private void jbPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPesquisarActionPerformed
        limparFiltros();
        pesquisar();
    }//GEN-LAST:event_jbPesquisarActionPerformed

    private void jtPatrimoniosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtPatrimoniosMouseClicked
        if (evt.getClickCount() == 2) {
            Patrimonio patrimonio = patrimonioTableModel.getRowValue(jtPatrimonios.getSelectedRow());
            new UICadPatrimonio(patrimonio).setVisible(true);
        }
    }//GEN-LAST:event_jtPatrimoniosMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UIManPatrimonio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UIManPatrimonio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UIManPatrimonio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIManPatrimonio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UIManPatrimonio dialog = new UIManPatrimonio();
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbLimparFiltros;
    private javax.swing.JButton jbPesquisar;
    private javax.swing.JLabel jlCodPatrimonio;
    private javax.swing.JLabel jlDescricao;
    private javax.swing.JTable jtPatrimonios;
    private javax.swing.JTextField jtfCodPatrimonio;
    private javax.swing.JTextField jtfDescricao;
    // End of variables declaration//GEN-END:variables
}
