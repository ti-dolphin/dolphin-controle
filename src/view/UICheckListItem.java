/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.CheckListItemDAO;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.CheckListModelo;
import model.CheckListItem;

/**
 *
 * @author guilherme.oliveira
 */
public class UICheckListItem extends javax.swing.JDialog {
    
    private UICadCheckList uiCadCheckList;
    private CheckListModelo checkListModelo;
    private CheckListItem checkListItem;
    private CheckListItemDAO checkListItemDAO;
    
    public UICheckListItem() {
        initComponents();
    }
    
    public UICheckListItem(UICadCheckList uiCadCheckList, CheckListItem checkListItem, CheckListModelo checkListModelo) {
        initComponents();
        this.checkListModelo = checkListModelo;
        this.checkListItem = checkListItem;
        this.checkListItemDAO = new CheckListItemDAO();
        this.uiCadCheckList = uiCadCheckList;
        preencherCampo();
    }
    
    private void preencherCampo() {
        if (checkListItem != null) {
            jtfNome.setText(checkListItem.getNome());
        }
    }
    
    private void setCheckListItem() throws NumberFormatException {
        try {

            if (jtfNome.getText().isEmpty()) {
                throw new NumberFormatException("Insira o nome do item");
            }
            checkListItem.setNome(jtfNome.getText());
            checkListItem.setCheckListModelo(checkListModelo);
            
        } catch (NumberFormatException e) {
           throw new NumberFormatException(e.getMessage());
        }
    }
    
    private void cadastrar() {
        
        try {
            checkListItem = new CheckListItem();
            
            setCheckListItem();
            
            checkListItem.setId(checkListItemDAO.inserir(checkListItem));
            
            uiCadCheckList.getCheckListItemTableModel().addRow(checkListItem);
            this.dispose();
            
        } catch (Exception ex) {
            Logger.getLogger(UICheckListItem.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), 
                    "Erro ao cadastrar item do checklist", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    private void editar() {
        try {
            
            setCheckListItem();
            
            checkListItemDAO.alterar(checkListItem);
            
            this.dispose();
            
        } catch (Exception e) {
            Logger.getLogger(UICheckListItem.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, e.getMessage(), 
                    "Erro ao editar item do checklist", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void salvar() {
        if (checkListItem == null) {
            cadastrar();
        } else {
            editar();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlNome = new javax.swing.JLabel();
        jtfNome = new javax.swing.JTextField();
        jbSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Adicionar Item");
        setModal(true);
        setResizable(false);

        jlNome.setText("Nome");

        jtfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfNomeKeyPressed(evt);
            }
        });

        jbSalvar.setText("Salvar");
        jbSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalvarActionPerformed(evt);
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
                        .addComponent(jlNome)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtfNome, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbSalvar)))
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
                    .addComponent(jbSalvar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed
        salvar();
    }//GEN-LAST:event_jbSalvarActionPerformed

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
            java.util.logging.Logger.getLogger(UICheckListItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UICheckListItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UICheckListItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UICheckListItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UICheckListItem dialog = new UICheckListItem();
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
    private javax.swing.JButton jbSalvar;
    private javax.swing.JLabel jlNome;
    private javax.swing.JTextField jtfNome;
    // End of variables declaration//GEN-END:variables
}
