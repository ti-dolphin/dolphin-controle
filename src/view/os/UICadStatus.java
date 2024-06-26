/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.os;

import dao.DAOFactory;
import dao.os.StatusDAO;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import model.os.Status;

/**
 *
 * @author guilherme.oliveira
 */
public class UICadStatus extends javax.swing.JDialog {

    private UIManStatus telaStatus;
    private Status status;
    private StatusDAO dao;

    /**
     * Creates new form UICadStatuss
     */
    public UICadStatus() {
    }

    public UICadStatus(Status status, UIManStatus telaStatus) {
        initComponents();
        this.telaStatus = telaStatus;
        this.status = status;
        this.dao = DAOFactory.getSTATUSDAO();
        preencherCampos();
    }

    public void preencherCampos() {
        if (status != null) {
            jtfNome.setText(status.getNome());
            jcbAcao.setSelectedIndex(status.getAcao());
            chAtivo.setSelected(status.isAtivo());
        }
    }//preecharCampos

    private void setStatus() throws NumberFormatException {
        if (jtfNome.getText().isEmpty()) {
            throw new NumberFormatException("Insira o nome do status!");
        }

        status.setNome(jtfNome.getText());
        status.setAcao(jcbAcao.getSelectedIndex());
        status.setAtivo(chAtivo.isSelected());
    }//setStatus

    private void cadastrar() {
        try {
            int id;

            status = new Status();

            setStatus();

            id = dao.inserir(status);

            status.setCodStatus(id);
            telaStatus.getStatusModel().addRow(status);
            
            JOptionPane.showMessageDialog(this,
                    "Status cadastrado!",
                    "",
                    JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
        } catch (Exception e) {
            status = null;
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro ao cadastrar status!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//inserir

    private void editar() {
        try {
            setStatus();

            dao.alterar(status);

            JOptionPane.showMessageDialog(this,
                    "Status editado!",
                    "",
                    JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro ao editar status!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//editar

    private void salvar() {
        if (status == null) {
            cadastrar();
        } else {
            editar();
        }
    }//salvar

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtfNome = new javax.swing.JTextField();
        jlNome = new javax.swing.JLabel();
        jcbAcao = new javax.swing.JComboBox<>();
        jlAcao = new javax.swing.JLabel();
        jbSalvar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        chAtivo = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de status");
        setModal(true);

        jtfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfNomeKeyPressed(evt);
            }
        });

        jlNome.setText("Nome");

        jcbAcao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Executar", "Finalizar", "Aguardar" }));

        jlAcao.setText("Ação");

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

        chAtivo.setText("Ativo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlNome))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlAcao)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jcbAcao, 0, 152, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chAtivo))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlNome)
                    .addComponent(jlAcao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbAcao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chAtivo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbCancelar)
                    .addComponent(jbSalvar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtfNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            salvar();
        }
    }//GEN-LAST:event_jtfNomeKeyPressed

    private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed
        salvar();
    }//GEN-LAST:event_jbSalvarActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(UICadStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UICadStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UICadStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UICadStatus.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UICadStatus dialog = new UICadStatus();
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
    private javax.swing.JCheckBox chAtivo;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbSalvar;
    private javax.swing.JComboBox<String> jcbAcao;
    private javax.swing.JLabel jlAcao;
    private javax.swing.JLabel jlNome;
    private javax.swing.JTextField jtfNome;
    // End of variables declaration//GEN-END:variables
}
