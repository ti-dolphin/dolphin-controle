/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.os;

import dao.DAOFactory;
import dao.apontamento.ApontamentoDAO;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.apontamento.Apontamento;
import model.apontamento.StatusApont;
import model.os.AdicionarApontamentoTableModel;
import model.os.OrdemServico;

/**
 *
 * @author guilherme.oliveira
 */
public class UIApontamentosOs extends javax.swing.JDialog {

    UITarefas uiTarefas;
    private AdicionarApontamentoTableModel aTableModel = new AdicionarApontamentoTableModel();
    private OrdemServico ordemServico;
    
    /**
     * Creates new form UIApontamentosOs
     */
    public UIApontamentosOs() {
    }

    public UIApontamentosOs(OrdemServico ordemServico, UITarefas uiTarefas) {
        initComponents();
        this.uiTarefas = uiTarefas;
        this.ordemServico = ordemServico;
        jtaApontamentoFiltro.setModel(aTableModel);
        preencherDataFiltro();
        redimensionarColunasApontOS();
    }

    private void redimensionarColunasApontOS() {
        jtaApontamentoFiltro.getColumnModel().getColumn(0).setPreferredWidth(50);//chapa
        jtaApontamentoFiltro.getColumnModel().getColumn(1).setPreferredWidth(495);//nome
        jtaApontamentoFiltro.getColumnModel().getColumn(2).setPreferredWidth(85);//data
        jtaApontamentoFiltro.getColumnModel().getColumn(3).setPreferredWidth(60);//situacao
    }

    public void preencherDataFiltro() {
        LocalDateTime dataAtual = LocalDateTime.now();
        jftfData.setText(String.valueOf(dataAtual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
    }

    public void buscar() {
        try {
            if (jtfChapa.getText().isEmpty() && jtfNome.getText().isEmpty()) {
                aTableModel.clear();
                jtaApontamentoFiltro.removeAll();
            } else {
                ApontamentoDAO dao = DAOFactory.getAPONTAMENTODAO();
                String query = "";

                String data = jftfData.getText();
                String dia = data.substring(0, 2);
                String mes = data.substring(3, 5);
                String ano = data.substring(6, 10);
                String dataFormatada = ano + "-" + mes + "-" + dia;

                if (!jtfChapa.getText().isEmpty()) {
                    query = " where A.CHAPA = " + jtfChapa.getText();
                } else if (!jtfNome.getText().isEmpty()) {
                    query = " where F.NOME like '%" + jtfNome.getText() + "%' and A.DATA = '" + dataFormatada + "'";
                    System.out.println(query);
                }

                ArrayList<Apontamento> aa = dao.filtrarApontOS(query);

                for (int i = 0; i < aa.size(); i++) {
                    aTableModel.addRow(aa.get(i));
                }//fecha for
            }

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this,
                    se.getMessage(),
                    "Erro ao filtrar funcionário",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public Apontamento getApontamentoSelecionado() {
        if (jtaApontamentoFiltro.getSelectedRow() == -1) {
            return null;
        }

        return aTableModel.getApontamentos().get(jtaApontamentoFiltro.getSelectedRow());
    }

    public void adicionar() {
        try {
            ApontamentoDAO dao = DAOFactory.getAPONTAMENTODAO();
            Apontamento apontamento = getApontamentoSelecionado();

            if (apontamento != null) {

                StatusApont sa = new StatusApont();
                sa.setCodStatusApont("PR");

                apontamento.setStatusApont(sa);
                apontamento.setCentroCusto(ordemServico.getCentroCusto());
                apontamento.setOrdemServico(ordemServico);
                
                dao.alterar(apontamento);
                
                ordemServico.setApontamentos(
                        DAOFactory.getAPONTAMENTODAO()
                                .buscarApontamentosDaOs(
                                        ordemServico.getCodOs()));
                
                uiTarefas.getApontamentoTableModel().clear();
                uiTarefas.preencherApontamentos();

                JOptionPane.showMessageDialog(this, "Colaborador apontado!", 
                        "", JOptionPane.INFORMATION_MESSAGE);
                
            } else {
                JOptionPane.showMessageDialog(this, "Selecione o colaborador!",
                        "Aviso ao apontar colaborador",
                        JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this,
                    se.getMessage(),
                    "Erro ao alterar cadastrar apontamento da OS",
                    JOptionPane.ERROR_MESSAGE);
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

        jtfChapa = new javax.swing.JTextField();
        jlChapa = new javax.swing.JLabel();
        jlNome = new javax.swing.JLabel();
        jtfNome = new javax.swing.JTextField();
        jftfData = new javax.swing.JFormattedTextField();
        jlData = new javax.swing.JLabel();
        jbAdicionar = new javax.swing.JButton();
        jbPesquisar = new javax.swing.JButton();
        jspApontamentFiltro = new javax.swing.JScrollPane();
        jtaApontamentoFiltro = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Apontar Colaborador");
        setModal(true);
        setResizable(false);

        jtfChapa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfChapaKeyPressed(evt);
            }
        });

        jlChapa.setText("Chapa");

        jlNome.setText("Nome");

        jtfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfNomeKeyPressed(evt);
            }
        });

        try {
            jftfData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfData.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        jftfData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfDataKeyPressed(evt);
            }
        });

        jlData.setText("Data");

        jbAdicionar.setText("Adicionar");
        jbAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarActionPerformed(evt);
            }
        });

        jbPesquisar.setText("Pesquisar");
        jbPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPesquisarActionPerformed(evt);
            }
        });

        jtaApontamentoFiltro.setModel(new javax.swing.table.DefaultTableModel(
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
        jtaApontamentoFiltro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtaApontamentoFiltroMouseClicked(evt);
            }
        });
        jspApontamentFiltro.setViewportView(jtaApontamentoFiltro);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspApontamentFiltro)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfChapa, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlChapa))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlNome))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlData)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jftfData, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbAdicionar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbPesquisar)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlChapa)
                    .addComponent(jlNome)
                    .addComponent(jlData))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfChapa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftfData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAdicionar)
                    .addComponent(jbPesquisar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jspApontamentFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtfChapaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfChapaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            aTableModel.clear();
            buscar();
        }
    }//GEN-LAST:event_jtfChapaKeyPressed

    private void jtfNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            aTableModel.clear();
            buscar();
        }
    }//GEN-LAST:event_jtfNomeKeyPressed

    private void jftfDataKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfDataKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            aTableModel.clear();
            buscar();
        }
    }//GEN-LAST:event_jftfDataKeyPressed

    private void jbAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarActionPerformed
        adicionar();
    }//GEN-LAST:event_jbAdicionarActionPerformed

    private void jbPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPesquisarActionPerformed
        aTableModel.clear();
        buscar();
    }//GEN-LAST:event_jbPesquisarActionPerformed

    private void jtaApontamentoFiltroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtaApontamentoFiltroMouseClicked
        if (evt.getClickCount() == 2) {
            adicionar();
        }
    }//GEN-LAST:event_jtaApontamentoFiltroMouseClicked

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
            java.util.logging.Logger.getLogger(UIApontamentosOs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UIApontamentosOs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UIApontamentosOs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIApontamentosOs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UIApontamentosOs dialog = new UIApontamentosOs();
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
    private javax.swing.JButton jbAdicionar;
    private javax.swing.JButton jbPesquisar;
    private javax.swing.JFormattedTextField jftfData;
    private javax.swing.JLabel jlChapa;
    private javax.swing.JLabel jlData;
    private javax.swing.JLabel jlNome;
    private javax.swing.JScrollPane jspApontamentFiltro;
    private javax.swing.JTable jtaApontamentoFiltro;
    private javax.swing.JTextField jtfChapa;
    private javax.swing.JTextField jtfNome;
    // End of variables declaration//GEN-END:variables
}
