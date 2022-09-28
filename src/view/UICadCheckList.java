/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
 
import dao.CheckListModeloDAO;
import dao.CheckListItemDAO;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.CheckListModelo;
import model.CheckListItem;
import model.CheckListItemTableModel;

/**
 *
 * @author guilherme.oliveira
 */
public class UICadCheckList extends javax.swing.JDialog {

    private UIManCheckList uiManCheckList;
    private CheckListModelo checkList;
    private CheckListModeloDAO checkListDAO;
    private CheckListItemDAO checkListItemDAO;
    private CheckListItemTableModel checkListItemTableModel;

    public UICadCheckList() {
        initComponents();
    }

    public UICadCheckList(UIManCheckList uiManCheckList, CheckListModelo checklist) {
        initComponents();
        this.uiManCheckList = uiManCheckList;
        this.checkList = checklist;
        checkListDAO = new CheckListModeloDAO();
        checkListItemDAO = new CheckListItemDAO();
        checkListItemTableModel = new CheckListItemTableModel();
        preencherCampos();
        buscarItens();
        habilitarAdicionarItem();
    }

    public CheckListItemTableModel getCheckListItemTableModel() {
        return checkListItemTableModel;
    }

    private void habilitarAdicionarItem() {
        if (checkList != null) {
            jbAdicionarItem.setEnabled(true);
        }
    }

    private void setCheckList() throws NumberFormatException {
        try {

            if (jtfNome.getText().isEmpty()) {
                throw new NumberFormatException("Insira o nome do checklist");
            }
            checkList.setNome(jtfNome.getText());
        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.getMessage());
        }
    }

    private void cadastrar() {
        try {
            checkList = new CheckListModelo();

            setCheckList();

            checkList.setId(checkListDAO.inserir(checkList));

            uiManCheckList.getCheckListModeloTableModel().addRow(checkList);

            JOptionPane.showMessageDialog(null, "Checklist cadastrado!");

            jbAdicionarItem.setEnabled(true);

        } catch (Exception ex) {
            Logger.getLogger(UICadCheckList.class.getName()).log(Level.SEVERE, null, ex);
            checkList = null;
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Erro ao cadastrar!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editar() {
        try {
            setCheckList();

            checkListDAO.alterar(checkList);

            JOptionPane.showMessageDialog(null, "Checklist editado!");

            jbAdicionarItem.setEnabled(true);

        } catch (SQLException ex) {
            Logger.getLogger(UICadCheckList.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Erro ao editar checklist!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvar() {
        if (checkList == null) {
            cadastrar();
        } else {
            editar();
        }
    }

    private void preencherCampos() {
        if (checkList != null) {
            jtfNome.setText(checkList.getNome());
        }
    }

    private CheckListItem pegarCheckListItemSelecionado() {
        if (jtCheckListItens.getSelectedRow() == -1) {
            return null;
        }

        return checkListItemTableModel.getItens().get(jtCheckListItens.getSelectedRow());
    }

    private void buscarItens() {
        try {
            if (checkList != null) {
                for (CheckListItem item : checkListItemDAO.buscarItensDoCheckList(checkList.getId())) {
                    checkListItemTableModel.addRow(item);
                }
            }
            jtCheckListItens.setModel(checkListItemTableModel);
        } catch (Exception ex) {
            Logger.getLogger(UICadCheckList.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Erro ao buscar itens!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerItem(CheckListItem item) {
        try {

            checkListItemDAO.excluir(item.getId());

            checkListItemTableModel.removeRow(item);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro ao excluir checklist", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtfNome = new javax.swing.JTextField();
        jlNome = new javax.swing.JLabel();
        jbSalvar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtCheckListItens = new javax.swing.JTable();
        jbAdicionarItem = new javax.swing.JButton();
        jbRemoverItem = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastrar CheckList");
        setModal(true);
        setResizable(false);

        jtfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfNomeKeyPressed(evt);
            }
        });

        jlNome.setText("Nome");

        jbSalvar.setText("Salvar");
        jbSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalvarActionPerformed(evt);
            }
        });

        jbCancelar.setText("Cancelar");
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        jtCheckListItens.setModel(new javax.swing.table.DefaultTableModel(
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
        jtCheckListItens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtCheckListItensMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtCheckListItens);

        jbAdicionarItem.setText("Adicionar Item");
        jbAdicionarItem.setEnabled(false);
        jbAdicionarItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarItemActionPerformed(evt);
            }
        });

        jbRemoverItem.setText("Remover Item");
        jbRemoverItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlNome)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbRemoverItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAdicionarItem))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jtfNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCancelar)))
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
                    .addComponent(jbCancelar)
                    .addComponent(jbSalvar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAdicionarItem)
                    .addComponent(jbRemoverItem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed
        salvar();
    }//GEN-LAST:event_jbSalvarActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void jbAdicionarItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarItemActionPerformed
        new UICheckListItem(this, null, checkList).setVisible(true);
    }//GEN-LAST:event_jbAdicionarItemActionPerformed

    private void jtCheckListItensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtCheckListItensMouseClicked
        CheckListItem item = pegarCheckListItemSelecionado();
        if (evt.getClickCount() == 2) {
            new UICheckListItem(this, item, checkList).setVisible(true);
        }
    }//GEN-LAST:event_jtCheckListItensMouseClicked

    private void jbRemoverItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverItemActionPerformed
        CheckListItem item = pegarCheckListItemSelecionado();
        if (item == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecione o item que deseja excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
        } else {
            Object[] options = {"Sim", "Não"};
            int i = JOptionPane.showOptionDialog(null,
                    "Tem certeza que deseja excluir?", "Excluir",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (i == JOptionPane.YES_OPTION) {
                removerItem(item);
            }
        }
    }//GEN-LAST:event_jbRemoverItemActionPerformed

    private void jtfNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            salvar();
        }
    }//GEN-LAST:event_jtfNomeKeyPressed

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
            java.util.logging.Logger.getLogger(UICadCheckList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UICadCheckList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UICadCheckList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UICadCheckList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UICadCheckList dialog = new UICadCheckList();
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
    private javax.swing.JButton jbAdicionarItem;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbRemoverItem;
    private javax.swing.JButton jbSalvar;
    private javax.swing.JLabel jlNome;
    private javax.swing.JTable jtCheckListItens;
    private javax.swing.JTextField jtfNome;
    // End of variables declaration//GEN-END:variables

}
