/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.apontamento;

import dao.DAOFactory;
import dao.apontamento.ApontamentoDAO;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.apontamento.Apontamento;
import model.apontamento.TomadoresTableModel;
import utilitarios.ExportaExcel;
import utilitarios.FormatarData;
import view.Menu;

/**
 *
 * @author guilherme.oliveira
 */
public class UITomadores extends javax.swing.JDialog {

    ApontamentoDAO dao;
    TomadoresTableModel tomadoresTableModel;

    public UITomadores() {
        initComponents();
        this.dao = DAOFactory.getAPONTAMENTODAO();
        this.tomadoresTableModel = new TomadoresTableModel();
        setData();
        buscar();
        configurarTabela();
    }

    private void configurarTabela() {

        final int COLUNA_CHAPA = 0;
        final int COLUNA_COLABORADOR = 1;
        final int COLUNA_CENTRO_DE_CUSTO = 2;
        final int COLUNA_CNO = 3;
        final int COLUNA_QUANTIDADE_DIAS = 4;
        final int COLUNA_PORCENTAGEM_DE_DIAS = 5;

        jtApontamentos.setAutoResizeMode(jtApontamentos.AUTO_RESIZE_OFF);
        jtApontamentos.getColumnModel().getColumn(COLUNA_CHAPA).setPreferredWidth(50);
        jtApontamentos.getColumnModel().getColumn(COLUNA_COLABORADOR).setPreferredWidth(300);
        jtApontamentos.getColumnModel().getColumn(COLUNA_CENTRO_DE_CUSTO).setPreferredWidth(350);
        jtApontamentos.getColumnModel().getColumn(COLUNA_CNO).setPreferredWidth(100);
        jtApontamentos.getColumnModel().getColumn(COLUNA_QUANTIDADE_DIAS).setPreferredWidth(60);
        jtApontamentos.getColumnModel().getColumn(COLUNA_PORCENTAGEM_DE_DIAS).setPreferredWidth(62);
    }

    private void setData() {
        LocalDateTime data = LocalDateTime.now();
        LocalDateTime primeiroDiaDoMesAnterior = data.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime ultimoDiaDoMesAnterior = data.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(ultimoDiaDoMesAnterior.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        System.out.println(primeiroDiaDoMesAnterior.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
        jftfDataInicio.setText(primeiroDiaDoMesAnterior.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        jftfDataTermino.setText(ultimoDiaDoMesAnterior.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    private void buscar() {

        try {
            String query = " ";
            String dataInicio = FormatarData.formatarData(jftfDataInicio.getText());
            String dataTermino = FormatarData.formatarData(jftfDataTermino.getText());

            if (!jtfColaborador.getText().isEmpty()) {
                query = query + "AND PFUNC.NOME LIKE '%" + jtfColaborador.getText() + "%'";
            }

            if (!jftfDataInicio.getText().equals("  /  /    ") || !jftfDataTermino.getText().equals("  /  /    ")) {
                query = query + " and APONTAMENTOS.DATA BETWEEN ('" + dataInicio + "')"
                        + " and ('" + dataTermino + "')";
            }

            if (jchAtivos.isSelected()) {
                query = query + " and PFUNC.CODSITUACAO <> 'D'";
            }

            if (jchSomenteObra.isSelected()) {
                query = query + " AND APONTAMENTOS.CODOS IS NULL";
            }

            if (jchPresentes.isSelected()) {
                query = query + " AND APONTAMENTOS.CODSTATUSAPONT = 'PR'";
            }

            ArrayList<Apontamento> apontamentos = dao.buscarDadosRelatorioTomadores(query);

            for (int i = 0; i < apontamentos.size(); i++) {
                tomadoresTableModel.addRow(apontamentos.get(i));
            }

            if (apontamentos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum retorno para essa consulta!");
            }

            jtApontamentos.setModel(tomadoresTableModel);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Erro ao buscar dados",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pesquisar() {
        Menu.carregamento(true);
        new Thread(() -> {
            tomadoresTableModel.clear();
            buscar();
            Menu.carregamento(false);
        }).start();
    }

    private void limparFiltros() {
        jtfColaborador.setText("");
        jftfDataInicio.setText("");
        jftfDataTermino.setText("");
        jchAtivos.setVisible(true);
        jchPresentes.setSelected(false);
        jchSomenteObra.setSelected(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlColaborador = new javax.swing.JLabel();
        jtfColaborador = new javax.swing.JTextField();
        jftfDataInicio = new javax.swing.JFormattedTextField();
        jlDataInicio = new javax.swing.JLabel();
        jftfDataTermino = new javax.swing.JFormattedTextField();
        jlDataTermino = new javax.swing.JLabel();
        jchAtivos = new javax.swing.JCheckBox();
        jbPesquisar = new javax.swing.JButton();
        jchPresentes = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtApontamentos = new javax.swing.JTable();
        jbLimparFiltros = new javax.swing.JButton();
        jchSomenteObra = new javax.swing.JCheckBox();
        jbExportarExcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Relatório de Tomadores");
        setModal(true);
        setResizable(false);

        jlColaborador.setText("Colaborador");

        jtfColaborador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfColaboradorKeyPressed(evt);
            }
        });

        try {
            jftfDataInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jlDataInicio.setText("De");

        try {
            jftfDataTermino.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jlDataTermino.setText("Até");

        jchAtivos.setSelected(true);
        jchAtivos.setText("Ativos");

        jbPesquisar.setText("Pesquisar");
        jbPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPesquisarActionPerformed(evt);
            }
        });

        jchPresentes.setText("Somente Presentes");

        jtApontamentos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtApontamentos);

        jbLimparFiltros.setText("Limpar Filtros");
        jbLimparFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimparFiltrosActionPerformed(evt);
            }
        });

        jchSomenteObra.setText("Somente Obra");

        jbExportarExcel.setText("Exportar para Excel");
        jbExportarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExportarExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlColaborador)
                                .addGap(0, 182, Short.MAX_VALUE))
                            .addComponent(jtfColaborador))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jftfDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlDataInicio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlDataTermino)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jftfDataTermino, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jchAtivos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jchPresentes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jchSomenteObra)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbLimparFiltros)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbPesquisar))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbExportarExcel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlColaborador)
                    .addComponent(jlDataInicio)
                    .addComponent(jlDataTermino))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftfDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftfDataTermino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jchAtivos)
                    .addComponent(jbPesquisar)
                    .addComponent(jchPresentes)
                    .addComponent(jbLimparFiltros)
                    .addComponent(jchSomenteObra))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbExportarExcel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPesquisarActionPerformed
        pesquisar();
    }//GEN-LAST:event_jbPesquisarActionPerformed

    private void jbLimparFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparFiltrosActionPerformed
        Menu.carregamento(true);
        new Thread(() -> {
            limparFiltros();
            tomadoresTableModel.clear();
            buscar();
            Menu.carregamento(false);
        }).start();
    }//GEN-LAST:event_jbLimparFiltrosActionPerformed

    private void jbExportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExportarExcelActionPerformed
        Menu.carregamento(true);
        new Thread(() -> {
            try {

                String path = System.getProperty("java.io.tmpdir") + "\\rel-tomadores.xls";

                ExportaExcel excel = new ExportaExcel(path);

                String query = " ";
                String dataInicio = FormatarData.formatarData(jftfDataInicio.getText());
                String dataTermino = FormatarData.formatarData(jftfDataTermino.getText());

                if (!jtfColaborador.getText().isEmpty()) {
                    query = query + "AND PFUNC.NOME LIKE '%" + jtfColaborador.getText() + "%'";
                }

                if (!jftfDataInicio.getText().equals("  /  /    ") || !jftfDataTermino.getText().equals("  /  /    ")) {
                    query = query + " and APONTAMENTOS.DATA BETWEEN ('" + dataInicio + "')"
                            + " and ('" + dataTermino + "')";
                }

                if (jchAtivos.isSelected()) {
                    query = query + " and PFUNC.CODSITUACAO <> 'D'";
                }

                if (jchSomenteObra.isSelected()) {
                    query = query + " AND APONTAMENTOS.CODOS IS NULL";
                }

                if (jchPresentes.isSelected()) {
                    query = query + " AND APONTAMENTOS.CODSTATUSAPONT = 'PR'";
                }

                ArrayList<Apontamento> apontamentos = dao.buscarDadosRelatorioTomadores(query);

                excel.exportarTomadores(apontamentos);

                File file = new File(path);
                Desktop.getDesktop().open(file);

            } catch (Exception ex) {

                Logger.getLogger(UITomadores.class.getName()).log(Level.SEVERE, null, ex);

                JOptionPane.showMessageDialog(null,
                        ex.getMessage(),
                        "Erro ao exportar apontamentos",
                        JOptionPane.ERROR_MESSAGE);
            }

            Menu.carregamento(false);
        }).start();
    }//GEN-LAST:event_jbExportarExcelActionPerformed

    private void jtfColaboradorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfColaboradorKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisar();
        }
    }//GEN-LAST:event_jtfColaboradorKeyPressed

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
            java.util.logging.Logger.getLogger(UITomadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UITomadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UITomadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UITomadores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UITomadores dialog = new UITomadores();
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
    private javax.swing.JButton jbExportarExcel;
    private javax.swing.JButton jbLimparFiltros;
    private javax.swing.JButton jbPesquisar;
    private javax.swing.JCheckBox jchAtivos;
    private javax.swing.JCheckBox jchPresentes;
    private javax.swing.JCheckBox jchSomenteObra;
    private javax.swing.JFormattedTextField jftfDataInicio;
    private javax.swing.JFormattedTextField jftfDataTermino;
    private javax.swing.JLabel jlColaborador;
    private javax.swing.JLabel jlDataInicio;
    private javax.swing.JLabel jlDataTermino;
    private javax.swing.JTable jtApontamentos;
    private javax.swing.JTextField jtfColaborador;
    // End of variables declaration//GEN-END:variables
}
