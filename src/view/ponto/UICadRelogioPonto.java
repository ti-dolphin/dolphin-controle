/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.ponto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import model.ponto.RelogioPonto;
import services.RelogioPontoServicos;
import services.ServicosFactory;
import utilitarios.FormatarData;

/**
 *
 * @author guilherme.oliveira
 */
public class UICadRelogioPonto extends javax.swing.JDialog {

    private UIManRelogioPonto uiManRelogioPonto;
    private RelogioPonto relogioPonto;
    private RelogioPontoServicos rps;
    /**
     * Creates new form UICadRelogioPonto
     *
     */
    public UICadRelogioPonto() {
    }

    public UICadRelogioPonto(RelogioPonto relogioPonto, UIManRelogioPonto uiManRelogioPonto) {
        initComponents();
        this.uiManRelogioPonto = uiManRelogioPonto;
        this.relogioPonto = relogioPonto;
        this.rps = ServicosFactory.getRELOGIOPONTOSERVICOS();
        preencherCampos();
    }

    private void preencherCampos() {
        if (relogioPonto != null) {
            jtfNome.setText(relogioPonto.getNome());
            jftfPatrimonio.setText(relogioPonto.getPatrimonio());
            jtfEnderecoIP.setText(relogioPonto.getIp());
            jftfNumSerie.setText(relogioPonto.getNumeroSerie());
            jchAtivo.setSelected(relogioPonto.isAtivo());
            jftfDataSinc.setText(relogioPonto.getDataSinc().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }//if
    }//preencherCampo
    
    private void inserir() {
        try {
            
            relogioPonto = new RelogioPonto();
            
            if (jtfNome.getText().isEmpty()) {
                throw new Exception("Campo nome obrigatório!");
            }
            if (jftfPatrimonio.getText().isEmpty()) {
                throw new Exception("Campo patrimônio obrigatório!");
            }
            if (jtfEnderecoIP.getText().isEmpty()) {
                throw new Exception("Campo IP obrigatório!");
            }
            if (jftfNumSerie.getText().isEmpty()) {
                throw new Exception("Campo número de série obrigatório!");
            }
            if (!jftfNumSerie.getText().matches("[0-9]*")) {
                throw new Exception("Campo número de série inválido!");
            }
            if (!jftfPatrimonio.getText().matches("[0-9]*")) {
                throw new Exception("Campo número de série inválido!");
            }
            
            relogioPonto.setNome(jtfNome.getText());
            relogioPonto.setPatrimonio(jftfPatrimonio.getText());
            relogioPonto.setIp(jtfEnderecoIP.getText());
            relogioPonto.setNumeroSerie(jftfNumSerie.getText());
            relogioPonto.setAtivo(jchAtivo.isSelected());
            String dataFormatada = FormatarData.formatarData(jftfDataSinc.getText());
            relogioPonto.setDataSinc(LocalDate.parse(dataFormatada));
            
            rps.inserir(relogioPonto);
            uiManRelogioPonto.getRelogiosTableModel().addRow(relogioPonto);
            JOptionPane.showMessageDialog(this, "Relógio ponto cadastrado!");
            this.dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    e.getMessage(), 
                    "Erro ao cadastrar", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }//inserir
    
    private void editar() {
        try {
            if (jtfNome.getText().isEmpty()) {
                throw new Exception("Nome obrigatório!");
            }
            if (jftfPatrimonio.getText().isEmpty()) {
                throw new Exception("Patrimônio obrigatório!");
            }
            if (jtfEnderecoIP.getText().isEmpty()) {
                throw new Exception("IP obrigatório!");
            }
            if (jftfNumSerie.getText().isEmpty()) {
                throw new Exception("Número de série obrigatório!");
            }
            if (!jftfNumSerie.getText().matches("[0-9]*")) {
                throw new Exception("Número de série inválido!");
            }
            if (!jftfPatrimonio.getText().matches("[0-9]*")) {
                throw new Exception("Número de série inválido!");
            }
            
            relogioPonto.setNome(jtfNome.getText());
            relogioPonto.setPatrimonio(jftfPatrimonio.getText());
            relogioPonto.setIp(jtfEnderecoIP.getText());
            relogioPonto.setNumeroSerie(jftfNumSerie.getText());
            relogioPonto.setAtivo(jchAtivo.isSelected());
            String dataFormatada = FormatarData.formatarData(jftfDataSinc.getText());
            relogioPonto.setDataSinc(LocalDate.parse(dataFormatada));
            
            rps.alterar(relogioPonto);
            
            JOptionPane.showMessageDialog(this, "Relógio ponto editado!");
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    e.getMessage(), 
                    "Erro ao editar", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }//editar
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jchAtivo = new javax.swing.JCheckBox();
        jlEnderecoIP = new javax.swing.JLabel();
        jtfNome = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jlPatrimonio = new javax.swing.JLabel();
        jlNumeroDeSerie = new javax.swing.JLabel();
        jftfNumSerie = new javax.swing.JFormattedTextField();
        jftfPatrimonio = new javax.swing.JFormattedTextField();
        jtfEnderecoIP = new javax.swing.JTextField();
        jftfDataSinc = new javax.swing.JFormattedTextField();
        jlDataSinc = new javax.swing.JLabel();
        jbCancelar = new javax.swing.JButton();
        jbSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Relógio Ponto");
        setModal(true);
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));

        jchAtivo.setText("Ativo");

        jlEnderecoIP.setText("Endereço IP");

        jLabel1.setText("Nome");

        jlPatrimonio.setText("Patrimônio");

        jlNumeroDeSerie.setText("Número de Série");

        try {
            jftfNumSerie.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#################")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            jftfPatrimonio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("######")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        try {
            jftfDataSinc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jlDataSinc.setText("Data da última sincronização");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jchAtivo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jtfNome, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jftfNumSerie, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                                .addComponent(jftfPatrimonio))
                            .addComponent(jLabel1)
                            .addComponent(jlPatrimonio)
                            .addComponent(jlNumeroDeSerie))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfEnderecoIP)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlEnderecoIP)
                                    .addComponent(jlDataSinc)
                                    .addComponent(jftfDataSinc, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlPatrimonio)
                    .addComponent(jlEnderecoIP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftfPatrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfEnderecoIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlNumeroDeSerie)
                    .addComponent(jlDataSinc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jftfNumSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftfDataSinc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jchAtivo)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jbCancelar.setText("Cancelar");
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
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
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbCancelar)
                    .addComponent(jbSalvar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed
        if (relogioPonto == null) {
            inserir();
        } else {
            editar();
        }
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
            java.util.logging.Logger.getLogger(UICadRelogioPonto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UICadRelogioPonto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UICadRelogioPonto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UICadRelogioPonto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UICadRelogioPonto dialog = new UICadRelogioPonto();
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbSalvar;
    private javax.swing.JCheckBox jchAtivo;
    private javax.swing.JFormattedTextField jftfDataSinc;
    private javax.swing.JFormattedTextField jftfNumSerie;
    private javax.swing.JFormattedTextField jftfPatrimonio;
    private javax.swing.JLabel jlDataSinc;
    private javax.swing.JLabel jlEnderecoIP;
    private javax.swing.JLabel jlNumeroDeSerie;
    private javax.swing.JLabel jlPatrimonio;
    private javax.swing.JTextField jtfEnderecoIP;
    private javax.swing.JTextField jtfNome;
    // End of variables declaration//GEN-END:variables

}
