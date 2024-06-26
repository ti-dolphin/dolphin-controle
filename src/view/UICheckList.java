/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.MovimentoItemCheckListDAO;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.CheckListModelo;
import model.MovimentoItem;
import model.MovimentoItemCheckList;
import persistencia.ConexaoBanco;

/**
 *
 * @author guilherme.oliveira
 */
public class UICheckList extends javax.swing.JDialog {

    private MovimentoItem item;
    private MovimentoItemCheckListDAO movimentoItemCheckListDAO;
    private ArrayList<MovimentoItemCheckList> movimentoItensCheckList;
    
    private ArrayList<JCheckBox> checks = new ArrayList<>();
    private ArrayList<JTextField> jtfProblemas = new ArrayList<>();
    private ArrayList<JLabel> jlProblemas = new ArrayList<>();
    
    /**
     * Creates new form UICheckList
     *
     * @param item
     */
    public UICheckList(MovimentoItem item) {
        initComponents();
        this.item = item;
        this.movimentoItemCheckListDAO = new MovimentoItemCheckListDAO();
        montarCheckList();
    }

    private UICheckList() {
        initComponents();
    }
    
    private void criarCheckLists() {
//        try {
//            if (item.getProduto() == null || item.getProduto().getId() == 0) {
//                movimentoItemCheckListDAO.inserirPatrimonio(item);
//            } else {
//                movimentoItemCheckListDAO.inserirProduto(item, checkListModelo);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(UICheckList.class.getName()).log(Level.FINE, null, ex);
//        }
    }

    private void montarCheckList() {
        try {
            movimentoItensCheckList = movimentoItemCheckListDAO.buscar(item);

            for (int i = 0; i < movimentoItensCheckList.size(); i++) {

                MovimentoItemCheckList movimentoItemCheckList = movimentoItensCheckList.get(i);
                JCheckBox jcItem = new JCheckBox(movimentoItemCheckList.getCheckListItem().getNome());
                JTextField jtfProblema = new JTextField();

                checks.add(jcItem);
                jPanel1.add(jcItem);

                jlProblemas.add(new JLabel("Problema"));
                jPanel1.add(jlProblemas.get(i));

                jtfProblemas.add(jtfProblema);
                jtfProblema.setPreferredSize(new Dimension(100, 25));
                jPanel1.add(jtfProblema);

                checks.get(i).setSelected(movimentoItemCheckList.isChecado());
                jtfProblemas.get(i).setText(movimentoItemCheckList.getProblema());

            }
            jPanel1.updateUI();
        } catch (Exception ex) {
            Logger.getLogger(UICheckList.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(),
                    "Erro ao criar checklist", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editar() {
        Connection conexao = null;
        try {

            conexao = ConexaoBanco.getConexao();
            for (int i = 0; i < movimentoItensCheckList.size(); i++) {

                MovimentoItemCheckList movimentoItemCheckList = movimentoItensCheckList.get(i);
                JCheckBox jcItem = checks.get(i);
                JTextField jtfProblema = jtfProblemas.get(i);

                movimentoItemCheckList.setChecado(jcItem.isSelected());
                movimentoItemCheckList.setProblema(jtfProblema.getText());
                movimentoItemCheckListDAO.alterar(conexao, movimentoItemCheckList);

            }
            conexao.close();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso!");

            this.dispose();

        } catch (Exception e) {
            Logger.getLogger(UICheckList.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Erro ao editar checklist! ", JOptionPane.ERROR_MESSAGE);
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
        jPanel1 = new javax.swing.JPanel();
        jbSalvar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jlNomeCheckList = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);

        jPanel1.setLayout(new java.awt.GridLayout(0, 1));
        jScrollPane1.setViewportView(jPanel1);

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

        jlNomeCheckList.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlNomeCheckList.setText("Checklist");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlNomeCheckList)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlNomeCheckList)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbSalvar)
                    .addComponent(jbCancelar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed
        Menu.carregamento(true);
        new Thread(() -> {
            long tempoInicial = System.currentTimeMillis();
            editar();
            long tempoFinal = System.currentTimeMillis();
            System.out.printf("%.3f s%n", (tempoFinal - tempoInicial) / 1000d);
            Menu.carregamento(false);
        }).start();
    }//GEN-LAST:event_jbSalvarActionPerformed

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
            java.util.logging.Logger.getLogger(UICheckList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UICheckList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UICheckList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UICheckList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UICheckList dialog = new UICheckList();
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbSalvar;
    private javax.swing.JLabel jlNomeCheckList;
    // End of variables declaration//GEN-END:variables
}
