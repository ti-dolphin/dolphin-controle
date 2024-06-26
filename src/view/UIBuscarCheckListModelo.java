/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.CheckListModeloDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import model.CheckListModelo;
import model.CheckListModeloTableModel;
import model.MovimentoItem;

/**
 *
 * @author guilherme.oliveira
 */
public class UIBuscarCheckListModelo extends javax.swing.JDialog {

    private UICadMovimento uiCadMovimento;
    private CheckListModeloDAO checkListDAO;
    private CheckListModeloTableModel checkListModeloTableModel;
    private TableRowSorter<CheckListModeloTableModel> sorter;
    private MovimentoItem item;

    public UIBuscarCheckListModelo() {
        initComponents();
    }

    public UIBuscarCheckListModelo(UICadMovimento uiCadMovimento, MovimentoItem item) {
        initComponents();
        this.uiCadMovimento = uiCadMovimento;
        this.item = item;
        this.checkListDAO = new CheckListModeloDAO();
        this.checkListModeloTableModel = new CheckListModeloTableModel();
        sorter = new TableRowSorter<>(checkListModeloTableModel);
        jtChecklists.setRowSorter(sorter);
        buscar();
        pesquisar();
    }

    private void buscar() {
        try {
            for (CheckListModelo check : checkListDAO.buscar()) {
                checkListModeloTableModel.addRow(check);
            }

            jtChecklists.setModel(checkListModeloTableModel);
        } catch (Exception e) {
            Logger.getLogger(UIBuscarCheckListModelo.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Erro ao buscar checklists",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pesquisar() {
        String nome = jtfNome.getText().trim();
        List<RowFilter<Object, Object>> filters = new ArrayList<>();
        filters.add(RowFilter.regexFilter("(?i)" + nome, 0));
        sorter.setRowFilter(RowFilter.andFilter(filters));
    }

    private CheckListModelo pegarCheckListSelecionado() {

        if (jtChecklists.getSelectedRow() > -1) {
            int linhaSelecionada = jtChecklists.getRowSorter().convertRowIndexToModel(jtChecklists.getSelectedRow());
            return checkListModeloTableModel.getRowValue(linhaSelecionada);
        }

        return checkListModeloTableModel.getCheckLists().get(jtChecklists.getSelectedRow());
    }

    private void adicionar() {

        CheckListModelo checkListModeloSelecionado = pegarCheckListSelecionado();

        if (checkListModeloSelecionado != null) {
            uiCadMovimento.setCheckListModelo(checkListModeloSelecionado);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione o checklist! ", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtChecklists = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jtfNome = new javax.swing.JTextField();
        jbAdicionar = new javax.swing.JButton();
        jbPesquisar = new javax.swing.JButton();
        jbLimparFiltros = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);

        jtChecklists.setModel(new javax.swing.table.DefaultTableModel(
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
        jtChecklists.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtChecklistsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtChecklists);

        jLabel1.setText("Nome");

        jbAdicionar.setText("Adicionar");
        jbAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarActionPerformed(evt);
            }
        });

        jbPesquisar.setText("Pesquisar");

        jbLimparFiltros.setText("Limpar Filtros");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtfNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbLimparFiltros)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbPesquisar)
                        .addGap(18, 18, 18)
                        .addComponent(jbAdicionar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAdicionar)
                    .addComponent(jbPesquisar)
                    .addComponent(jbLimparFiltros))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtChecklistsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtChecklistsMouseClicked
        if (evt.getClickCount() == 2) {
            adicionar();
        }
    }//GEN-LAST:event_jtChecklistsMouseClicked

    private void jbAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarActionPerformed
        adicionar();
    }//GEN-LAST:event_jbAdicionarActionPerformed

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
            java.util.logging.Logger.getLogger(UIBuscarCheckListModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UIBuscarCheckListModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UIBuscarCheckListModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIBuscarCheckListModelo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UIBuscarCheckListModelo dialog = new UIBuscarCheckListModelo();
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAdicionar;
    private javax.swing.JButton jbLimparFiltros;
    private javax.swing.JButton jbPesquisar;
    private javax.swing.JTable jtChecklists;
    private javax.swing.JTextField jtfNome;
    // End of variables declaration//GEN-END:variables
}
