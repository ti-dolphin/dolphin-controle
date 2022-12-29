/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view.epi;

import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Funcionario;
import model.epi.EpiFuncionario;
import services.epi.EpiFuncionarioService;
import view.Menu;

/**
 *
 * @author ti
 */
public class UIEntregarEPI extends javax.swing.JDialog {

    private final EpiFuncionarioService epiFuncionarioService;
    private final EpiFuncionario epiFuncionario;
    private final UIFuncionarioEPI uiFuncionarioEPI;
    private final UIControleEpi uiControleEPI;
    private boolean estaEntregando;
    public static byte[] digital;

    public UIEntregarEPI(UIControleEpi uiControleEpi, EpiFuncionario epiFuncionario, boolean estaEntregando) {
        this.epiFuncionarioService = new EpiFuncionarioService();
        this.epiFuncionario = epiFuncionario;
        this.uiControleEPI = uiControleEpi;
        this.uiFuncionarioEPI = uiControleEpi.getUiFuncionarioEpi();
        this.estaEntregando = estaEntregando;
        initComponents();
    }

    public void entregarEpi() {
        try {
            String strSenha = String.valueOf(jpfSenha.getPassword());
            int senha = Integer.parseInt(strSenha);
            Funcionario funcionario = this.epiFuncionario.getFuncionario();
            EpiFuncionario ef;
            if (senha == funcionario.getSenha()) {
                if (estaEntregando) {
                    ef = epiFuncionarioService.entregarEpi(this.epiFuncionario);
                } else {
                    ef = epiFuncionarioService.devolverEpi(this.epiFuncionario);
                }
                uiControleEPI.getEpisFuncionario().add(ef);
                uiFuncionarioEPI.filtrarHistoricoDoFunc();
                this.dispose();
            } else {
                jpfSenha.setText("");
                jpfSenha.requestFocus();
                JOptionPane.showMessageDialog(null, "Senha incorreta!");
            }
        } catch (HeadlessException | NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "" + nfe.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(UIEntregarEPI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro ao registrar movimento de EPI", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(UIEntregarEPI.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro ao registrar movimento de EPI", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlSenha = new javax.swing.JLabel();
        jpfSenha = new javax.swing.JPasswordField();
        jbDigitarSenha = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);

        jlSenha.setText("Senha");

        jpfSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jpfSenhaKeyPressed(evt);
            }
        });

        jbDigitarSenha.setText("Entregar");
        jbDigitarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDigitarSenhaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlSenha)
                    .addComponent(jpfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbDigitarSenha)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jbDigitarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jpfSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpfSenhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            entregarEpi();
        }
    }//GEN-LAST:event_jpfSenhaKeyPressed

    private void jbDigitarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDigitarSenhaActionPerformed
        entregarEpi();
    }//GEN-LAST:event_jbDigitarSenhaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton jbDigitarSenha;
    private javax.swing.JLabel jlSenha;
    private javax.swing.JPasswordField jpfSenha;
    // End of variables declaration//GEN-END:variables
}
