/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view.epi;

import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import model.epi.Epi;
import model.epi.EpiFuncionario;
import model.epi.tables.EpiFuncionarioTableModel;
import model.epi.tables.EpiEntregaTableModel;
import model.Funcionario;
import model.epi.tables.FuncionarioTableModel;
import model.HistoricoTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import persistencia.ConexaoBanco;
import services.epi.EpiFuncionarioService;
import services.epi.EpiService;
import view.UICarregando;
import view.UIMotivo;

/**
 *
 * @author ti
 */
public class UIFuncionarioEPI extends javax.swing.JDialog {

    private EpiService epiService;
    private EpiFuncionarioService epiFuncionarioService;
    private UIControleEpi uiControleEpi;
    private Funcionario funcionario;
    private EpiEntregaTableModel epiTableModel;
    private final HistoricoTableModel hTableModel = new HistoricoTableModel();
    private final FuncionarioTableModel fTableModel = new FuncionarioTableModel();
    private final EpiFuncionarioTableModel efTableModel = new EpiFuncionarioTableModel();
    private boolean flagRelatorio;
    private int tkt = 0;

    public UIFuncionarioEPI(UIControleEpi telaControleEPI) {
        initComponents();
        this.epiTableModel = new EpiEntregaTableModel();
        this.jtblEpi.setModel(epiTableModel);
        this.epiService = new EpiService();
        this.epiFuncionarioService = new EpiFuncionarioService();
        this.uiControleEpi = telaControleEPI;
        this.funcionario = uiControleEpi.getFuncionarioDaLinhaSelecionada();
        carregarFuncionario();
        filtrarHistoricoDoFunc();
        filtrarEpis();
        configTabelaEpi();
    }

    public UIControleEpi getUiControleEpi() {
        return uiControleEpi;
    }

    private void configTabelaEpi() {

        jtblEpi.setAutoResizeMode(jtblEpi.AUTO_RESIZE_OFF);

        jtblEpi.getColumnModel().getColumn(epiTableModel.COLUNA_CODIGO).setPreferredWidth(200);
        jtblEpi.getColumnModel().getColumn(epiTableModel.COLUNA_NOME).setPreferredWidth(300);
        jtblEpi.getColumnModel().getColumn(epiTableModel.COLUNA_DESCRICAO).setPreferredWidth(300);

    }

    public void carregarFuncionario() {
        jtfColigada.setText(String.valueOf(funcionario.getCodColigada()));
        jtfNome.setText(funcionario.getNome());
    }//fecha carregarFuncionario

    public void filtrarHistoricoDoFunc() {
        try {

            Funcionario funcionarioLinha = uiControleEpi.getFuncionarioDaLinhaSelecionada();
            String chapa = funcionarioLinha.getChapa();
            short coligada = funcionarioLinha.getCodColigada();

            String query = " WHERE EF.CODCOLIGADA = " + coligada + " AND EF.CHAPA = '" + chapa + "'";

            if (!jtfLNomeEpiHistFiltro.getText().isEmpty()) {
                query = query
                        + " and e.NOME like '%"
                        + jtfLNomeEpiHistFiltro.getText()
                        + "%'";
            }

            if (!jtfLCodEpiHistFiltro.getText().isEmpty()) {
                query = query
                        + " and ef.CODEPI like '%"
                        + jtfLCodEpiHistFiltro.getText()
                        + "%'";
            }

            ArrayList<EpiFuncionario> episDoFuncionario = epiFuncionarioService.filtrarEpiFuncionario(query);

            hTableModel.clearTable();

            for (int i = 0; i < episDoFuncionario.size(); i++) {
                hTableModel.addRow(episDoFuncionario.get(i));
            }

            jtEpisDoFunc.setModel(hTableModel);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Erro ao buscar dados do histórico", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void filtrarEpis() {
        try {

            String query = "";
            String codigoEpi = txtCodigoEpi.getText();
            String nomeEpi = txtNomeEpi.getText();

            if (!txtCodigoEpi.getText().isEmpty()) {
                query += " AND CODEPI like '%" + codigoEpi + "%'";
            }

            if (!txtNomeEpi.getText().isEmpty()) {
                query += " AND NOME like '%" + nomeEpi + "%'";
            }

            epiTableModel.clear();

            ArrayList<Epi> epis = epiService.filtrarEpi(query);

            for (int i = 0; i < epis.size(); i++) {
                epiTableModel.addRow(epis.get(i));
            }
            jtblEpi.setModel(epiTableModel);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Erro buscar EPI's",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public EpiFuncionario getLinhaHistorico() {
        if (jtEpisDoFunc.getSelectedRow() == -1) {
            return null;
        }

        return hTableModel.getRegistros().get(jtEpisDoFunc.getSelectedRow());
    }

    public Epi getLinhaEpi() {
        if (jtblEpi.getSelectedRow() == -1) {
            return null;
        }

        return epiTableModel.getEpis().get(jtblEpi.getSelectedRow());
    }

    private void atualizarTblEpis() {
        txtNomeEpi.setText("");
        txtCodigoEpi.setText("");
        filtrarEpis();
    }

    private void atualizarTblHis() {
        hTableModel.clearTable();
        jtfLNomeEpiHistFiltro.setText("");
        jtfLCodEpiHistFiltro.setText("");
        filtrarHistoricoDoFunc();
    }

    private void abreTelaMotivo(EpiFuncionario epiFuncionario) {

        LocalDateTime hoje = LocalDateTime.now();
        LocalDateTime dataRetirada = epiFuncionario.getDataRetirada();

        int periodicidade = epiFuncionario.getEpi().getPeriodicidade();

        LocalDateTime dataDaProximaRetirada = dataRetirada.plusDays(periodicidade);

        //Testa se o periodo (em dias) é menor que a periodicidade do epi
        if (dataDaProximaRetirada.isAfter(hoje)) {

            new UIMotivo(epiFuncionario).setVisible(true);

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlpFuncionario = new javax.swing.JLayeredPane();
        jtfNome = new javax.swing.JTextField();
        jlNome = new javax.swing.JLabel();
        jtfColigada = new javax.swing.JTextField();
        jlColigada = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jlLCodEpiFiltro = new javax.swing.JLabel();
        jlLNomeEpiFiltro = new javax.swing.JLabel();
        jbEPesquisar = new javax.swing.JButton();
        jbAtualizarEpisDoFun = new javax.swing.JButton();
        txtCodigoEpi = new javax.swing.JTextField();
        txtNomeEpi = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblEpi = new javax.swing.JTable();
        jlpEpi = new javax.swing.JLayeredPane();
        jlLCodEpiHistFiltro = new javax.swing.JLabel();
        jtfLCodEpiHistFiltro = new javax.swing.JTextField();
        jlLNomeEpiHistFiltro = new javax.swing.JLabel();
        jtfLNomeEpiHistFiltro = new javax.swing.JTextField();
        jbHPesquisar = new javax.swing.JButton();
        jbAtualizarHistoricoDoFun = new javax.swing.JButton();
        jbRelatorio = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtEpisDoFunc = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);

        jlpFuncionario.setBorder(javax.swing.BorderFactory.createTitledBorder("Funcionário"));

        jtfNome.setEditable(false);
        jtfNome.setEnabled(false);

        jlNome.setText("Nome");

        jtfColigada.setEditable(false);
        jtfColigada.setEnabled(false);

        jlColigada.setText("Coligada");

        jlpFuncionario.setLayer(jtfNome, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpFuncionario.setLayer(jlNome, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpFuncionario.setLayer(jtfColigada, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpFuncionario.setLayer(jlColigada, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jlpFuncionarioLayout = new javax.swing.GroupLayout(jlpFuncionario);
        jlpFuncionario.setLayout(jlpFuncionarioLayout);
        jlpFuncionarioLayout.setHorizontalGroup(
            jlpFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jlpFuncionarioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlColigada)
                .addGap(18, 18, 18)
                .addComponent(jtfColigada, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlNome)
                .addGap(18, 18, 18)
                .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jlpFuncionarioLayout.setVerticalGroup(
            jlpFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jlpFuncionarioLayout.createSequentialGroup()
                .addGroup(jlpFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlColigada)
                    .addComponent(jtfColigada, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlNome)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtrar EPI"));

        jlLCodEpiFiltro.setText("Código do EPI");

        jlLNomeEpiFiltro.setText("EPI");

        jbEPesquisar.setText("Pesquisar");
        jbEPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEPesquisarActionPerformed(evt);
            }
        });

        jbAtualizarEpisDoFun.setText("Atualizar");
        jbAtualizarEpisDoFun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAtualizarEpisDoFunActionPerformed(evt);
            }
        });

        txtCodigoEpi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoEpiKeyPressed(evt);
            }
        });

        txtNomeEpi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeEpiKeyPressed(evt);
            }
        });

        jLayeredPane1.setLayer(jlLCodEpiFiltro, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jlLNomeEpiFiltro, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jbEPesquisar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jbAtualizarEpisDoFun, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtCodigoEpi, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(txtNomeEpi, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlLCodEpiFiltro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigoEpi, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlLNomeEpiFiltro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomeEpi, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbAtualizarEpisDoFun)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbEPesquisar)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbEPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbAtualizarEpisDoFun, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtNomeEpi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jlLCodEpiFiltro)
                        .addComponent(jlLNomeEpiFiltro)
                        .addComponent(txtCodigoEpi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        jtblEpi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nome", "Preço", "Descrição"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtblEpi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblEpiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtblEpi);
        if (jtblEpi.getColumnModel().getColumnCount() > 0) {
            jtblEpi.getColumnModel().getColumn(0).setResizable(false);
            jtblEpi.getColumnModel().getColumn(1).setResizable(false);
            jtblEpi.getColumnModel().getColumn(2).setResizable(false);
            jtblEpi.getColumnModel().getColumn(3).setResizable(false);
        }

        jlpEpi.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtrar Histórico"));

        jlLCodEpiHistFiltro.setText("Código do EPI");

        jtfLCodEpiHistFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfLCodEpiHistFiltroKeyPressed(evt);
            }
        });

        jlLNomeEpiHistFiltro.setText("EPI");

        jtfLNomeEpiHistFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfLNomeEpiHistFiltroKeyPressed(evt);
            }
        });

        jbHPesquisar.setText("Pesquisar");
        jbHPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbHPesquisarActionPerformed(evt);
            }
        });

        jbAtualizarHistoricoDoFun.setText("Atualizar");
        jbAtualizarHistoricoDoFun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAtualizarHistoricoDoFunActionPerformed(evt);
            }
        });

        jbRelatorio.setText("Relatório");
        jbRelatorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRelatorioActionPerformed(evt);
            }
        });

        jlpEpi.setLayer(jlLCodEpiHistFiltro, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpEpi.setLayer(jtfLCodEpiHistFiltro, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpEpi.setLayer(jlLNomeEpiHistFiltro, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpEpi.setLayer(jtfLNomeEpiHistFiltro, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpEpi.setLayer(jbHPesquisar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpEpi.setLayer(jbAtualizarHistoricoDoFun, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpEpi.setLayer(jbRelatorio, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jlpEpiLayout = new javax.swing.GroupLayout(jlpEpi);
        jlpEpi.setLayout(jlpEpiLayout);
        jlpEpiLayout.setHorizontalGroup(
            jlpEpiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jlpEpiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlLCodEpiHistFiltro)
                .addGap(18, 18, 18)
                .addComponent(jtfLCodEpiHistFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlLNomeEpiHistFiltro)
                .addGap(18, 18, 18)
                .addComponent(jtfLNomeEpiHistFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbRelatorio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbAtualizarHistoricoDoFun)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbHPesquisar)
                .addContainerGap())
        );
        jlpEpiLayout.setVerticalGroup(
            jlpEpiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jlpEpiLayout.createSequentialGroup()
                .addGroup(jlpEpiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlLCodEpiHistFiltro)
                    .addComponent(jtfLCodEpiHistFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlLNomeEpiHistFiltro)
                    .addComponent(jtfLNomeEpiHistFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbHPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAtualizarHistoricoDoFun, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jtEpisDoFunc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Coligada", "Funcionário", "EPI", "Data Retirada", "Data Devolução", "CA"
            }
        ));
        jtEpisDoFunc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtEpisDoFuncMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jtEpisDoFunc);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlpFuncionario, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlpEpi)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlpFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlpEpi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbEPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEPesquisarActionPerformed
        filtrarEpis();
    }//GEN-LAST:event_jbEPesquisarActionPerformed

    private void jbAtualizarEpisDoFunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAtualizarEpisDoFunActionPerformed
        atualizarTblEpis();
    }//GEN-LAST:event_jbAtualizarEpisDoFunActionPerformed

    private void jtblEpiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblEpiMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                EpiFuncionario epiFuncionario = new EpiFuncionario();
                epiFuncionario.setFuncionario(funcionario);
                Epi epi = getLinhaEpi();
                epiFuncionario.setEpi(epi);
                if (epi != null) {

                    ArrayList<EpiFuncionario> episPendentes;
                    episPendentes = epiFuncionarioService.buscarEpisPendentes(epiFuncionario);

                    if (!episPendentes.isEmpty()) {
                        JOptionPane.showMessageDialog(null,
                                "O colaborador já tem o EPI "
                                + epi.getNome()
                                + ", devolva-o para pegar outro.",
                                "Aviso",
                                JOptionPane.WARNING_MESSAGE
                        );
                    } else {

                        atualizarTblHis();

                        new UICa(this, epiFuncionario).setVisible(true);

                    }
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "EPI não selecionado",
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE
                    );
                }//teste epi vazio
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_jtblEpiMouseClicked

    private void jtfLCodEpiHistFiltroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfLCodEpiHistFiltroKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            hTableModel.clearTable();
            filtrarHistoricoDoFunc();
        }
    }//GEN-LAST:event_jtfLCodEpiHistFiltroKeyPressed

    private void jtfLNomeEpiHistFiltroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfLNomeEpiHistFiltroKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            hTableModel.clearTable();
            filtrarHistoricoDoFunc();
        }
    }//GEN-LAST:event_jtfLNomeEpiHistFiltroKeyPressed

    private void jbHPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbHPesquisarActionPerformed
        hTableModel.clearTable();
        filtrarHistoricoDoFunc();
    }//GEN-LAST:event_jbHPesquisarActionPerformed

    private void jbAtualizarHistoricoDoFunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAtualizarHistoricoDoFunActionPerformed
        atualizarTblHis();
    }//GEN-LAST:event_jbAtualizarHistoricoDoFunActionPerformed

    private void jbRelatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRelatorioActionPerformed
        if (flagRelatorio == false) {
            flagRelatorio = true;
            final UICarregando carregando = new UICarregando(null, false);
            carregando.setVisible(true);
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        Connection con = ConexaoBanco.getConexao();

                        String caminhoCorrente = new File("").getAbsolutePath();

                        String caminhoDoRelatorio = caminhoCorrente + "/relatorios/rel-historico-epis.jasper";

                        JasperPrint jasperPrint;

                        String caminhoDaImagem = caminhoCorrente + "/img/dse-logo-relatorio.png";

                        HashMap filtro = new HashMap();
                        filtro.put("coligada", funcionario.getCodColigada());
                        filtro.put("chapa", funcionario.getChapa());
                        filtro.put("imagem", caminhoDaImagem);

                        jasperPrint = JasperFillManager.fillReport(caminhoDoRelatorio, filtro, con);

                        JasperViewer view = new JasperViewer(jasperPrint, false);

                        view.setVisible(true);

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "" + ex.getMessage(),
                                "Erro ao carregar dados", JOptionPane.ERROR_MESSAGE);
                    } catch (JRException ex) {
                        JOptionPane.showMessageDialog(null, "" + ex.getMessage(),
                                "Erro ao gerar relatório", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        carregando.dispose();
                    }
                }
            };

            t.start();
        }
    }//GEN-LAST:event_jbRelatorioActionPerformed

    private void jtEpisDoFuncMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtEpisDoFuncMouseClicked
        if (evt.getClickCount() == 2) {
            EpiFuncionario epiFuncionario = getLinhaHistorico();
            if (epiFuncionario != null) {
                if (epiFuncionario.getDataDevolucao() == null) {
                    abreTelaMotivo(epiFuncionario);
                    new UIDescontar(epiFuncionario).setVisible(true);
                    new UIEntregarEPI(this, epiFuncionario, false).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "EPI já foi entregue!",
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione registro!");
            }
        }
    }//GEN-LAST:event_jtEpisDoFuncMouseClicked

    private void txtNomeEpiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeEpiKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            filtrarEpis();
        }
    }//GEN-LAST:event_txtNomeEpiKeyPressed

    private void txtCodigoEpiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoEpiKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            filtrarEpis();
        }
    }//GEN-LAST:event_txtCodigoEpiKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbAtualizarEpisDoFun;
    private javax.swing.JButton jbAtualizarHistoricoDoFun;
    private javax.swing.JButton jbEPesquisar;
    private javax.swing.JButton jbHPesquisar;
    private javax.swing.JButton jbRelatorio;
    private javax.swing.JLabel jlColigada;
    private javax.swing.JLabel jlLCodEpiFiltro;
    private javax.swing.JLabel jlLCodEpiHistFiltro;
    private javax.swing.JLabel jlLNomeEpiFiltro;
    private javax.swing.JLabel jlLNomeEpiHistFiltro;
    private javax.swing.JLabel jlNome;
    private javax.swing.JLayeredPane jlpEpi;
    private javax.swing.JLayeredPane jlpFuncionario;
    private javax.swing.JTable jtEpisDoFunc;
    private javax.swing.JTable jtblEpi;
    private javax.swing.JTextField jtfColigada;
    private javax.swing.JTextField jtfLCodEpiHistFiltro;
    private javax.swing.JTextField jtfLNomeEpiHistFiltro;
    private javax.swing.JTextField jtfNome;
    private javax.swing.JTextField txtCodigoEpi;
    private javax.swing.JTextField txtNomeEpi;
    // End of variables declaration//GEN-END:variables
}
