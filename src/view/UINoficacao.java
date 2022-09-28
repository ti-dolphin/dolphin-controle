/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import static javax.swing.JTable.AUTO_RESIZE_OFF;
import model.Notificacao;
import model.tables.NotificacaoTableCellRender;
import model.tables.NotificacaoTableModel;
import services.NotificacaoService;

/**
 *
 * @author ti
 */
public class UINoficacao extends JDialog {

    private List<Notificacao> notificacoes;
    private final NotificacaoService notificacaoService;
    private NotificacaoTableModel notificacaoTableModel;
    private NotificacaoTableCellRender notificacaoTableCellRender;

    public UINoficacao(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        notificacaoService = new NotificacaoService();
        carregarTabelaNotificacoes();
    }

    private void carregarTabelaNotificacoes() {
        Menu.carregamento(true);
        new Thread() {
            @Override
            public void run() {
                try {
                    notificacoes = (ArrayList) notificacaoService.listar(cbxLido.isSelected());
                    notificacaoTableModel = new NotificacaoTableModel(notificacoes);
                    tblNotificacao.setModel(notificacaoTableModel);
                    tblNotificacao.setAutoResizeMode(AUTO_RESIZE_OFF);
                    tblNotificacao.getColumnModel().getColumn(notificacaoTableModel.COLUNA_DATA).setPreferredWidth(120);
                    tblNotificacao.getColumnModel().getColumn(notificacaoTableModel.COLUNA_DESCRICAO).setPreferredWidth(550);
                    tblNotificacao.getColumnModel().getColumn(notificacaoTableModel.COLUNA_LIDO).setPreferredWidth(50);
                    notificacaoTableCellRender = new NotificacaoTableCellRender(notificacaoTableModel);
                    tblNotificacao.setDefaultRenderer(Object.class, notificacaoTableCellRender);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Erro ao buscar notificações" + ex.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(UINoficacao.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    Menu.carregamento(false);
                }
            }
        }.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblNotificacao = new javax.swing.JTable();
        cbxLido = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });

        tblNotificacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblNotificacao.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tblNotificacao);

        cbxLido.setText("Lido");
        cbxLido.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxLidoItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbxLido)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbxLido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
    }//GEN-LAST:event_formWindowLostFocus

    private void cbxLidoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxLidoItemStateChanged
        carregarTabelaNotificacoes();
    }//GEN-LAST:event_cbxLidoItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbxLido;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tblNotificacao;
    // End of variables declaration//GEN-END:variables
}
