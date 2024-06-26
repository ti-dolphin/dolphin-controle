/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.HistoricoFeriasPJDAO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import model.HistoricoFeriasPJ;
import model.HistoricoFeriasPJTableModel;
import utilitarios.os.DateCellRenderer;

/**
 *
 * @author guilherme.oliveira
 */
public class UIHistoricoFeriasPJ extends javax.swing.JDialog {

    private HistoricoFeriasPJDAO dao;
    private HistoricoFeriasPJTableModel historicoTableModel;
    private TableRowSorter<HistoricoFeriasPJTableModel> sorter;

    public UIHistoricoFeriasPJ() {
        initComponents();
        dao = new HistoricoFeriasPJDAO();
        historicoTableModel = new HistoricoFeriasPJTableModel();
        sorter = new TableRowSorter<>(historicoTableModel);
        jtHistorico.setRowSorter(sorter);
        buscar();
        configurarTabela();
    }
    
    public void configurarTabela() {
        DateCellRenderer dRenderer = new DateCellRenderer();

        jtHistorico.getColumnModel().getColumn(3).setCellRenderer(dRenderer);
        jtHistorico.getColumnModel().getColumn(4).setCellRenderer(dRenderer);
        
        jtHistorico.getTableHeader().setResizingAllowed(false);
        jtHistorico.setAutoResizeMode(jtHistorico.AUTO_RESIZE_OFF);
        jtHistorico.getColumnModel().getColumn(0).setPreferredWidth(50);//codigo
        jtHistorico.getColumnModel().getColumn(1).setPreferredWidth(50);//chapa
        jtHistorico.getColumnModel().getColumn(2).setPreferredWidth(355);//nome
        jtHistorico.getColumnModel().getColumn(3).setPreferredWidth(100);//data inicio
        jtHistorico.getColumnModel().getColumn(4).setPreferredWidth(100);//data termino
        jtHistorico.getColumnModel().getColumn(5).setPreferredWidth(100);//quantidade    
    }

    public HistoricoFeriasPJTableModel getHistoricoTableModel() {
        return historicoTableModel;
    }

    public void buscar() {
        try {
            ArrayList<HistoricoFeriasPJ> historicos = dao.buscar();

            for (HistoricoFeriasPJ historico : historicos) {
                historicoTableModel.addRow(historico);
            }

            jtHistorico.setModel(historicoTableModel);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Erro ao buscar histórico",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//buscar

    private void pesquisar() {

        String nome = jtfNome.getText().trim();

        //cria uma lista para guardar os filtros de cada coluna
        List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();
        filters.add(RowFilter.regexFilter("(?i)" + nome, 2));
        //aplica os filtros no RowSorter que foi criado no construtor
        //utilizando o andFilter
        sorter.setRowFilter(RowFilter.andFilter(filters));
    }

    private HistoricoFeriasPJ pegarHistoricoSelecionado() {
        if (jtHistorico.getSelectedRow() > -1) {
            int linhaSelecionada = jtHistorico.getRowSorter().convertRowIndexToModel(jtHistorico.getSelectedRow());
            return historicoTableModel.getRowValue(linhaSelecionada);
        } else {
            return null;
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
        jtHistorico = new javax.swing.JTable();
        jlNome = new javax.swing.JLabel();
        jtfNome = new javax.swing.JTextField();
        jbLimparFiltros = new javax.swing.JButton();
        jbNovo = new javax.swing.JButton();
        jbEditar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Histórico de Recesso de Colaboradores");
        setModal(true);
        setResizable(false);

        jtHistorico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtHistorico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtHistoricoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtHistorico);

        jlNome.setText("Nome");

        jtfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfNomeKeyPressed(evt);
            }
        });

        jbLimparFiltros.setText("Limpar Filtros");
        jbLimparFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimparFiltrosActionPerformed(evt);
            }
        });

        jbNovo.setText("Novo");
        jbNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNovoActionPerformed(evt);
            }
        });

        jbEditar.setText("Editar");
        jbEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlNome)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jtfNome))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbLimparFiltros)
                        .addGap(103, 103, 103)
                        .addComponent(jbNovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbEditar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 761, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbLimparFiltros)
                    .addComponent(jbNovo)
                    .addComponent(jbEditar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbLimparFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparFiltrosActionPerformed
        jtfNome.setText("");
        pesquisar();
    }//GEN-LAST:event_jbLimparFiltrosActionPerformed

    private void jtfNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNomeKeyPressed
        pesquisar();
    }//GEN-LAST:event_jtfNomeKeyPressed

    private void jbNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNovoActionPerformed
        new UICadFeriasFuncionarioPJ(null, this).setVisible(true);
    }//GEN-LAST:event_jbNovoActionPerformed

    private void jbEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEditarActionPerformed
        HistoricoFeriasPJ historico = pegarHistoricoSelecionado();
        if (historico != null) {
            new UICadFeriasFuncionarioPJ(historico, this).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Selecione um registro para editar!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jbEditarActionPerformed

    private void jtHistoricoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtHistoricoMouseClicked
        if (evt.getClickCount() == 2) {
            HistoricoFeriasPJ historico = pegarHistoricoSelecionado();
            if (historico != null) {
                new UICadFeriasFuncionarioPJ(historico, this).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Selecione um registro para editar!",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_jtHistoricoMouseClicked

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
            java.util.logging.Logger.getLogger(UIHistoricoFeriasPJ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UIHistoricoFeriasPJ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UIHistoricoFeriasPJ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIHistoricoFeriasPJ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UIHistoricoFeriasPJ dialog = new UIHistoricoFeriasPJ();
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
    private javax.swing.JButton jbEditar;
    private javax.swing.JButton jbLimparFiltros;
    private javax.swing.JButton jbNovo;
    private javax.swing.JLabel jlNome;
    private javax.swing.JTable jtHistorico;
    private javax.swing.JTextField jtfNome;
    // End of variables declaration//GEN-END:variables
}
