/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.MovimentoItemDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import model.Movimento;
import model.MovimentoItem;
import model.MovimentoItemTableModel;
import model.Patrimonio;
import model.Produto;

/**
 *
 * @author guilherme.oliveira
 */
public class UIBuscarItensDoMovimento extends javax.swing.JDialog {

    private UICadMovimento uiCadMovimento;
    private MovimentoItemDAO movimentoItemDAO;
    private MovimentoItemTableModel movimentoItemTableModel;
    private TableRowSorter<MovimentoItemTableModel> sorter;
    private Movimento movimento;
    private boolean isProduto;

    public UIBuscarItensDoMovimento() {
    }

    public UIBuscarItensDoMovimento(UICadMovimento uiCadMovimento, Movimento movimento, boolean isProduto) {
        initComponents();
        this.uiCadMovimento = uiCadMovimento;
        this.movimento = movimento;
        movimentoItemDAO = new MovimentoItemDAO();
        movimentoItemTableModel = new MovimentoItemTableModel();
        this.isProduto = isProduto;
        sorter = new TableRowSorter<>(movimentoItemTableModel);
        jtItens.setRowSorter(sorter);
        buscar();
    }

    public void pesquisar() {
        String nome = jtfNome.getText().trim();

        List<RowFilter<Object, Object>> filters = new ArrayList<>();
        if (isProduto) {
            filters.add(RowFilter.regexFilter("(?i)" + nome, 0));
        } else {
            filters.add(RowFilter.regexFilter("(?i)" + nome, 1));
        }
        sorter.setRowFilter(RowFilter.andFilter(filters));
    }

    private void buscar() {

        try {
            ArrayList<MovimentoItem> itens;

            if (uiCadMovimento.getJchComprando().isSelected()) {
                if (isProduto) {
                    itens = movimentoItemDAO.buscarProdutosDoMovimento();
                } else {
                    itens = movimentoItemDAO.buscarPatrimoniosDoMovimento();
                }
            } else {
                if (isProduto) {
                    itens = movimentoItemDAO.buscarProdutosPorLocalDeEstoqueOrigem(movimento.getLocalDeEstoqueOrigem());
                } else {
                    itens = movimentoItemDAO.buscarPatrimoniosPorLocalDeEstoqueOrigem(movimento.getLocalDeEstoqueOrigem());
                }
            }

            for (MovimentoItem item : itens) {
                movimentoItemTableModel.addRow(item);
            }

            jtItens.setModel(movimentoItemTableModel);
            
            uiCadMovimento.getUiControleMovimentos().atualizarNumeroDeRegistros();

        } catch (SQLException ex) {
            Logger.getLogger(UIBuscarItensDoMovimento.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Erro ao buscar itens", JOptionPane.ERROR_MESSAGE);
        }
    }

    private MovimentoItem pegarMovimentoItemSelecionado() throws IndexOutOfBoundsException {
        try {
            int linhaSelecionada = jtItens.getRowSorter().convertRowIndexToModel(jtItens.getSelectedRow());
            return movimentoItemTableModel.getRowValue(linhaSelecionada);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Selecione o item!");
        }
    }

    private void adicionar() {
        try {
            MovimentoItem item = pegarMovimentoItemSelecionado();

            if (item != null) {

                Patrimonio patrimonio = new Patrimonio();
                MovimentoItem itemSelecionado = pegarMovimentoItemSelecionado();
                Produto produto = new Produto();

                if (isProduto) {

                    patrimonio.setCodColigada((short) 0);
                    patrimonio.setId(0);
                    patrimonio.setDescricao("-");
                    patrimonio.setCodPatrimonio("-");

                    produto.setId(itemSelecionado.getProduto().getId());
                    produto.setCodColigada(itemSelecionado.getProduto().getCodColigada());
                    produto.setNomeFantasia(itemSelecionado.getProduto().getNomeFantasia());

                } else {

                    produto.setCodColigada((short) 0);
                    produto.setId(0);
                    produto.setDescricao("-");
                    produto.setNomeFantasia("-");

                    patrimonio.setId(itemSelecionado.getPatrimonio().getId());
                    patrimonio.setCodColigada(itemSelecionado.getPatrimonio().getCodColigada());
                    patrimonio.setCodPatrimonio(itemSelecionado.getPatrimonio().getCodPatrimonio());
                    patrimonio.setDescricao(itemSelecionado.getPatrimonio().getDescricao());

                }

                item.setProduto(produto);
                item.setPatrimonio(patrimonio);

                item.setMovimento(movimento);

                item.setId(movimentoItemDAO.inserir(item));

                movimentoItemDAO.alterarItemMovimentado(true, item.getIdOrigem());

                uiCadMovimento.getMovimentoItemTableModel().addRow(item);
                uiCadMovimento.getUiControleMovimentos().getItensMovimentadosTableModel().addRow(item);
                uiCadMovimento.getUiControleMovimentos().atualizarNumeroDeRegistros();
                
                JOptionPane.showMessageDialog(null, "Item adicionado");
                
                this.dispose();

            } else {
                JOptionPane.showMessageDialog(null, "Selecione o item!", 
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            Logger.getLogger(UIBuscarItensDoMovimento.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Erro ao adicionar item!", JOptionPane.ERROR_MESSAGE);
        } catch (IndexOutOfBoundsException ie) {
            Logger.getLogger(UIBuscarItensDoMovimento.class.getName()).log(Level.WARNING, null, ie);
            JOptionPane.showMessageDialog(null, ie.getMessage(),
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limparFiltros() {
        jtfNome.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlNome = new javax.swing.JLabel();
        jtfNome = new javax.swing.JTextField();
        jbLimparFiltros = new javax.swing.JButton();
        jbAdicionar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtItens = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);

        jlNome.setText("Nome");

        jtfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfNomeKeyReleased(evt);
            }
        });

        jbLimparFiltros.setText("Limpar Filtros");
        jbLimparFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimparFiltrosActionPerformed(evt);
            }
        });

        jbAdicionar.setText("Adicionar");
        jbAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarActionPerformed(evt);
            }
        });

        jtItens.setModel(new javax.swing.table.DefaultTableModel(
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
        jtItens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtItensMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtItens);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtfNome, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbLimparFiltros)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAdicionar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlNome)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbLimparFiltros)
                    .addComponent(jbAdicionar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbLimparFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparFiltrosActionPerformed
        movimentoItemTableModel.clear();
        limparFiltros();
        buscar();
    }//GEN-LAST:event_jbLimparFiltrosActionPerformed

    private void jbAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarActionPerformed
        adicionar();
    }//GEN-LAST:event_jbAdicionarActionPerformed

    private void jtfNomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNomeKeyReleased
        pesquisar();
    }//GEN-LAST:event_jtfNomeKeyReleased

    private void jtItensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtItensMouseClicked
        if (evt.getClickCount() == 2) {
            adicionar();
        }
    }//GEN-LAST:event_jtItensMouseClicked

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
            java.util.logging.Logger.getLogger(UIBuscarItensDoMovimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UIBuscarItensDoMovimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UIBuscarItensDoMovimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIBuscarItensDoMovimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UIBuscarItensDoMovimento dialog = new UIBuscarItensDoMovimento();
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbAdicionar;
    private javax.swing.JButton jbLimparFiltros;
    private javax.swing.JLabel jlNome;
    private javax.swing.JTable jtItens;
    private javax.swing.JTextField jtfNome;
    // End of variables declaration//GEN-END:variables
}
