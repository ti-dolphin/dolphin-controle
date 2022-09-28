/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.HistoricoFeriasPJDAO;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.Funcionario;
import model.HistoricoFeriasPJ;
import utilitarios.FormatarData;

/**
 *
 * @author guilherme.oliveira
 */
public class UICadFeriasFuncionarioPJ extends javax.swing.JDialog {

    private UIHistoricoFeriasPJ uiHistoricoFeriasPJ;
    private HistoricoFeriasPJ historico;
    private Funcionario funcionario;
    private HistoricoFeriasPJDAO dao;

    public UICadFeriasFuncionarioPJ() {
    }

    public UICadFeriasFuncionarioPJ(HistoricoFeriasPJ historico, UIHistoricoFeriasPJ uiHistoricoFeriasPJ) {
        initComponents();
        this.uiHistoricoFeriasPJ = uiHistoricoFeriasPJ;
        this.historico = historico;
        this.dao = new HistoricoFeriasPJDAO();
        preencherCampos();
    }

    public JTextField getJtfChapa() {
        return jtfChapa;
    }

    public void setJtfChapa(JTextField jtfChapa) {
        this.jtfChapa = jtfChapa;
    }

    public JTextField getJtfNome() {
        return jtfNome;
    }

    public void setJtfNome(JTextField jtfNome) {
        this.jtfNome = jtfNome;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    
    private void preencherCampos() {
        if (historico != null) {
            jtfChapa.setText(historico.getFuncionario().getChapa());
            jtfNome.setText(historico.getFuncionario().getNome());
            jftfDataInicio.setText(String.valueOf(historico.getDataInicio()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            
            jftfDataTermino.setText(String.valueOf(historico.getDataTermino()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        }
    }

    private void setHistorico() throws Exception {
        try {
            if (!jftfDataInicio.getText().equals("  /  /    ") && !jftfDataTermino.getText().equals("  /  /    ")) {
                historico.setDataInicio(FormatarData.converterTextoEmData(jftfDataInicio.getText(), "dd/MM/yyyy"));
                historico.setDataTermino(FormatarData.converterTextoEmData(jftfDataTermino.getText(), "dd/MM/yyyy"));
                historico.setQuantidade((int) ChronoUnit.DAYS.between(historico.getDataInicio(), historico.getDataTermino()));
            } else {
                throw new NumberFormatException("Datas inválidas");
            }
            
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }//setHistorico

    private void cadastrar() {
        try {
            historico = new HistoricoFeriasPJ();
            
            if (funcionario != null) {
                historico.setFuncionario(funcionario);
            } else {
                throw new NumberFormatException("Selecione o funcionário!");
            }
            
            setHistorico();
            
            historico.setCreatedBy(Menu.logado.getLogin());
            
            historico.setCodHistorico(dao.inserir(historico));
            
            uiHistoricoFeriasPJ.getHistoricoTableModel().addRow(historico);
            
            JOptionPane.showMessageDialog(null, 
                    "Férias cadastrada com sucesso!", 
                    "", 
                    JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            
        } catch (Exception e) {
            historico = null;
            Logger.getLogger(UICadFeriasFuncionarioPJ.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, 
                    e.getMessage(), 
                    "Erro ao cadastrar recesso", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }//cadastrar
    
    private void editar() {
        try {
            
            if (funcionario != null) {
                historico.setFuncionario(funcionario);
            }
            
            setHistorico();
            
            historico.setModifiedBy(Menu.logado.getLogin());
            
            dao.alterar(historico);
            
            JOptionPane.showMessageDialog(null, 
                    "Recesso editado com sucesso!", 
                    "", 
                    JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            
        } catch (Exception e) {
            Logger.getLogger(UICadFeriasFuncionarioPJ.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, 
                    e.getMessage(), 
                    "Erro ao editar recesso", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }//editar
    
    private void salvar() {
        if (historico == null) {
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

        jPanel1 = new javax.swing.JPanel();
        jtfChapa = new javax.swing.JTextField();
        jlChapa = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jftfDataInicio = new javax.swing.JFormattedTextField();
        jftfDataTermino = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jtfNome = new javax.swing.JTextField();
        jlNome = new javax.swing.JLabel();
        jbBuscarFuncionarioPJ = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Recesso");
        setModal(true);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));

        jtfChapa.setEditable(false);

        jlChapa.setText("Chapa");

        jLabel3.setText("Data Início");

        try {
            jftfDataInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            jftfDataTermino.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel4.setText("Data Término");

        jtfNome.setEditable(false);

        jlNome.setText("Nome");

        jbBuscarFuncionarioPJ.setText("...");
        jbBuscarFuncionarioPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarFuncionarioPJActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jtfChapa, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jlNome)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jtfNome))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbBuscarFuncionarioPJ))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jftfDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlChapa)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jftfDataTermino, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlChapa)
                    .addComponent(jlNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfChapa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscarFuncionarioPJ))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftfDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftfDataTermino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 16, Short.MAX_VALUE))
        );

        jbCancelar.setText("Cancelar");
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        jButton3.setText("Salvar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 277, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbCancelar)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        jPanel1.getAccessibleContext().setAccessibleName("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void jbBuscarFuncionarioPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarFuncionarioPJActionPerformed
        new UIBuscarFuncionariosPJ(this).setVisible(true);
    }//GEN-LAST:event_jbBuscarFuncionarioPJActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       salvar();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(UICadFeriasFuncionarioPJ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UICadFeriasFuncionarioPJ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UICadFeriasFuncionarioPJ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UICadFeriasFuncionarioPJ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UICadFeriasFuncionarioPJ dialog = new UICadFeriasFuncionarioPJ();
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
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbBuscarFuncionarioPJ;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JFormattedTextField jftfDataInicio;
    private javax.swing.JFormattedTextField jftfDataTermino;
    private javax.swing.JLabel jlChapa;
    private javax.swing.JLabel jlNome;
    private javax.swing.JTextField jtfChapa;
    private javax.swing.JTextField jtfNome;
    // End of variables declaration//GEN-END:variables
}
