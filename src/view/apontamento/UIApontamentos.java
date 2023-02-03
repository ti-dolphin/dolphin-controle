/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.apontamento;

import dao.DAOFactory;
import dao.apontamento.ApontamentoDAO;
import dao.apontamento.StatusApontDAO;
import dao.os.PessoaDAO;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import model.apontamento.Apontamento;
import model.apontamento.tables.ApontamentoPontoTableModel;
import model.apontamento.tables.ApontamentosPontoTableCellRender;
import model.apontamento.tables.ApontamentosTableCellRender;
import model.apontamento.tables.ApontamentoTableModel;
import model.apontamento.StatusApont;
import model.apontamento.tables.ApontamentoProblemaTableModel;
import model.apontamento.tables.ApontamentoProblemasTableCellRender;
import model.apontamento.tables.SinalizarColunaTabelaApontamentos;
import model.apontamento.tables.IconeApontamentoTableCellRederer;
import model.os.Pessoa;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import persistencia.ConexaoBanco;
import services.apontamento.ApontamentoService;
import utilitarios.ExportaExcel;
import utilitarios.FormatarData;
import view.Menu;

/**
 *
 * @author guilherme.oliveira
 */
public class UIApontamentos extends javax.swing.JInternalFrame {

    private final ApontamentoService apontamentoService;
    private ApontamentoTableModel apontamentoTableModel;
    private ApontamentoProblemaTableModel apontamentoProblemaTableModel;
    private ApontamentosPontoTableCellRender apontamentoPontoTableCellRender;
    private ApontamentosTableCellRender apontamentoTableCellRender;
    private ApontamentoPontoTableModel apontamentoPontoTableModel;
    private boolean flagUIApontar;
    private boolean flagUIComentarios;
    private boolean flagApontamentosEmMassa;
    private boolean flagPrimeiroCarregamentoPonto;
    private boolean flagPrimeiroCarregamentoProblema;

    public UIApontamentos() {
        this.apontamentoService = new ApontamentoService();
        flagPrimeiroCarregamentoPonto = true;
        flagPrimeiroCarregamentoProblema = true;

        initComponents();

        this.apontamentoTableModel = new ApontamentoTableModel();
        this.tblApontamentos.setModel(apontamentoTableModel);
        this.tblApontamentos.setAutoCreateRowSorter(true);
        configurarTabelaApontamentos();

        this.apontamentoPontoTableModel = new ApontamentoPontoTableModel();
        this.tblApontamentoPonto.setModel(apontamentoPontoTableModel);
        this.tblApontamentoPonto.setAutoCreateRowSorter(true);
        configurarTabelaApontamentosPonto();

        this.apontamentoProblemaTableModel = new ApontamentoProblemaTableModel();
        this.tblApontamentoProblema.setModel(apontamentoProblemaTableModel);
        this.tblApontamentoProblema.setAutoCreateRowSorter(true);
        configurarTabelaApontamentoProblema();

        darPermissoes();
        preencherComboBoxLider();
        preencherComboBoxStatusApont();
        preencherComboBoxGerente();
        preencherData();

        pesquisar();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jspCentroCusto = new javax.swing.JScrollPane();
        jlstCentroCusto = new javax.swing.JList<>();
        jlChapa = new javax.swing.JLabel();
        jtfChapa = new javax.swing.JTextField();
        jtfNome = new javax.swing.JTextField();
        jlNome = new javax.swing.JLabel();
        jlCentroCusto = new javax.swing.JLabel();
        jcbLider = new javax.swing.JComboBox<>();
        jlLider = new javax.swing.JLabel();
        jftfDataInicio = new javax.swing.JFormattedTextField();
        jlDataInicio = new javax.swing.JLabel();
        jftfDataTermino = new javax.swing.JFormattedTextField();
        jlDataTermino = new javax.swing.JLabel();
        jbHoje = new javax.swing.JButton();
        jbLimparFiltros = new javax.swing.JButton();
        jbPesquisar = new javax.swing.JButton();
        jbComentarios = new javax.swing.JButton();
        jbApontar = new javax.swing.JButton();
        jchAtivos = new javax.swing.JCheckBox();
        jlContador = new javax.swing.JLabel();
        jtfCentroCusto = new javax.swing.JTextField();
        jcbStatusApont = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jcbGerente = new javax.swing.JComboBox<>();
        jlGerente = new javax.swing.JLabel();
        jbGerarRelatoriosComentarios = new javax.swing.JButton();
        jchComentados = new javax.swing.JCheckBox();
        jbExportarExcel = new javax.swing.JButton();
        jtpPainelApontamentos = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblApontamentos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblApontamentoPonto = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblApontamentoProblema = new javax.swing.JTable();
        btnAvisos = new javax.swing.JButton();
        cbxAssiduidade = new javax.swing.JCheckBox();
        cbxNaoApontados = new javax.swing.JCheckBox();
        btnPeriodoAtual = new javax.swing.JButton();
        btnPeriodoAnterior = new javax.swing.JButton();
        jcbSemJustificativa = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmRelatorios = new javax.swing.JMenu();
        jmiTomadores = new javax.swing.JMenuItem();

        setClosable(true);
        setIconifiable(true);
        setTitle("Apontamentos");
        setPreferredSize(new java.awt.Dimension(1217, 700));

        jspCentroCusto.setViewportView(jlstCentroCusto);

        jlChapa.setText("Chapa");

        jtfChapa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfChapaKeyPressed(evt);
            }
        });

        jtfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfNomeKeyPressed(evt);
            }
        });

        jlNome.setText("Nome");

        jlCentroCusto.setText("Centro de Custo");

        jlLider.setText("Líder");

        try {
            jftfDataInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfDataInicio.setText("16/12/2021");
        jftfDataInicio.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        jftfDataInicio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jftfDataInicioFocusGained(evt);
            }
        });
        jftfDataInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfDataInicioKeyPressed(evt);
            }
        });

        jlDataInicio.setText("De");

        try {
            jftfDataTermino.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfDataTermino.setText("15/01/2022");
        jftfDataTermino.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        jftfDataTermino.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jftfDataTerminoFocusGained(evt);
            }
        });
        jftfDataTermino.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfDataTerminoKeyPressed(evt);
            }
        });

        jlDataTermino.setText("Até");

        jbHoje.setText("Hoje");
        jbHoje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbHojeActionPerformed(evt);
            }
        });

        jbLimparFiltros.setText("Limpar filtros");
        jbLimparFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimparFiltrosActionPerformed(evt);
            }
        });

        jbPesquisar.setText("Pesquisar");
        jbPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jbPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPesquisarActionPerformed(evt);
            }
        });

        jbComentarios.setText("Comentários");
        jbComentarios.setEnabled(false);
        jbComentarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbComentariosActionPerformed(evt);
            }
        });

        jbApontar.setText("Apontar");
        jbApontar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbApontarActionPerformed(evt);
            }
        });

        jchAtivos.setSelected(true);
        jchAtivos.setText("Ativos");

        jlContador.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jlContador.setText("Não foi possível retornar número de registros");

        jtfCentroCusto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfCentroCustoKeyPressed(evt);
            }
        });

        jLabel1.setText("Status");

        jlGerente.setText("Gerente");

        jbGerarRelatoriosComentarios.setText("Relatório dos comentários");
        jbGerarRelatoriosComentarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbGerarRelatoriosComentariosActionPerformed(evt);
            }
        });

        jchComentados.setText("Comentados");

        jbExportarExcel.setText("Exportar Excel");
        jbExportarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExportarExcelActionPerformed(evt);
            }
        });

        jtpPainelApontamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtpPainelApontamentosMouseClicked(evt);
            }
        });

        tblApontamentos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblApontamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblApontamentosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblApontamentos);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1181, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtpPainelApontamentos.addTab("Apontamento", jPanel1);

        tblApontamentoPonto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblApontamentoPonto.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(tblApontamentoPonto);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1181, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtpPainelApontamentos.addTab("Ponto", jPanel2);

        tblApontamentoProblema.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblApontamentoProblema.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblApontamentoProblema.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblApontamentoPontoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblApontamentoProblema);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1181, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE))
        );

        jtpPainelApontamentos.addTab("Problemas", jPanel3);

        btnAvisos.setText("Avisos");
        btnAvisos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAvisosActionPerformed(evt);
            }
        });

        cbxAssiduidade.setText("Sem Assiduidade");

        cbxNaoApontados.setText("Não apontados");

        btnPeriodoAtual.setText("Periodo atual");
        btnPeriodoAtual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeriodoAtualActionPerformed(evt);
            }
        });

        btnPeriodoAnterior.setText("Periodo anterior");
        btnPeriodoAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeriodoAnteriorActionPerformed(evt);
            }
        });

        jcbSemJustificativa.setText("Sem Justificativa");

        jmRelatorios.setText("Relatórios");

        jmiTomadores.setText("Tomadores");
        jmiTomadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiTomadoresActionPerformed(evt);
            }
        });
        jmRelatorios.add(jmiTomadores);

        jMenuBar1.add(jmRelatorios);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbApontar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jspCentroCusto, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jbComentarios)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbGerarRelatoriosComentarios)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbExportarExcel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAvisos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbxNaoApontados)
                                .addGap(0, 0, 0)
                                .addComponent(jcbSemJustificativa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxAssiduidade)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jchComentados)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jchAtivos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbLimparFiltros)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbPesquisar)
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtpPainelApontamentos)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jlChapa)
                                            .addComponent(jtfChapa, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jlNome)
                                            .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jtfCentroCusto)
                                            .addComponent(jlCentroCusto))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jcbStatusApont, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jlGerente)
                                            .addComponent(jcbGerente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jlLider)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jcbLider, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jftfDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jlDataInicio))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jftfDataTermino, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jbHoje))
                                            .addComponent(jlDataTermino)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jlContador)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addComponent(btnPeriodoAtual)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPeriodoAnterior)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlChapa)
                    .addComponent(jlNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlCentroCusto)
                    .addComponent(jlLider)
                    .addComponent(jlDataInicio)
                    .addComponent(jlDataTermino)
                    .addComponent(jLabel1)
                    .addComponent(jlGerente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfChapa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbLider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftfDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftfDataTermino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbHoje)
                    .addComponent(jtfCentroCusto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbStatusApont, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbGerente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPeriodoAtual)
                    .addComponent(btnPeriodoAnterior))
                .addComponent(jspCentroCusto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbApontar)
                    .addComponent(jbComentarios)
                    .addComponent(jchAtivos)
                    .addComponent(jbLimparFiltros)
                    .addComponent(jbPesquisar)
                    .addComponent(jbGerarRelatoriosComentarios)
                    .addComponent(jchComentados)
                    .addComponent(jbExportarExcel)
                    .addComponent(btnAvisos)
                    .addComponent(cbxAssiduidade)
                    .addComponent(cbxNaoApontados)
                    .addComponent(jcbSemJustificativa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtpPainelApontamentos, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlContador)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public boolean isFlagUIApontar() {
        return flagUIApontar;
    }

    public void setFlagUIApontar(boolean flagUIApontar) {
        this.flagUIApontar = flagUIApontar;
    }

    public boolean isFlagUIComentarios() {
        return flagUIComentarios;
    }

    public void setFlagUIComentarios(boolean flagUIComentarios) {
        this.flagUIComentarios = flagUIComentarios;
    }

    private void darPermissoes() {
        if (Menu.logado.isPermComentApont()) {
            jbComentarios.setEnabled(true);
        }
    }

    public ApontamentoTableModel getaTableModel() {
        return apontamentoTableModel;
    }

    public boolean isFlagApontamentosEmMassa() {
        return flagApontamentosEmMassa;
    }

    public void setFlagApontamentosEmMassa(boolean flagApontamentosEmMassa) {
        this.flagApontamentosEmMassa = flagApontamentosEmMassa;
    }

    public Apontamento getApontamento() {
        return apontamentoTableModel.getApontamento(tblApontamentos.getSelectedRow());
    }

    private void configurarTabelaApontamentos() {
        tblApontamentos.getColumnModel().getColumn(ApontamentoTableModel.COLUNA_ASSIDUIDADE).setPreferredWidth(80);
        tblApontamentos.getColumnModel().getColumn(ApontamentoTableModel.COLUNA_COMENTADO).setPreferredWidth(80);
        tblApontamentos.getColumnModel().getColumn(ApontamentoTableModel.COLUNA_BANCO_HORAS).setPreferredWidth(80);
        tblApontamentos.getColumnModel().getColumn(ApontamentoTableModel.COLUNA_CHAPA).setPreferredWidth(70);
        tblApontamentos.getColumnModel().getColumn(ApontamentoTableModel.COLUNA_FUNCIONARIO).setPreferredWidth(250);
        tblApontamentos.getColumnModel().getColumn(ApontamentoTableModel.COLUNA_FUNCAO).setPreferredWidth(350);
        tblApontamentos.getColumnModel().getColumn(ApontamentoTableModel.COLUNA_DATA).setPreferredWidth(80);
        tblApontamentos.getColumnModel().getColumn(ApontamentoTableModel.COLUNA_DIA_DA_SEMANA).setPreferredWidth(100);
        tblApontamentos.getColumnModel().getColumn(ApontamentoTableModel.COLUNA_GERENTE).setPreferredWidth(300);
        tblApontamentos.getColumnModel().getColumn(ApontamentoTableModel.COLUNA_CENTRO_CUSTO).setPreferredWidth(350);
        tblApontamentos.getColumnModel().getColumn(ApontamentoTableModel.COLUNA_STATUS).setPreferredWidth(100);
        tblApontamentos.getColumnModel().getColumn(ApontamentoTableModel.COLUNA_LIDER).setPreferredWidth(350);
        tblApontamentos.getColumnModel().getColumn(ApontamentoTableModel.COLUNA_ATIVIDADE).setPreferredWidth(350);
        tblApontamentos.getColumnModel().getColumn(ApontamentoTableModel.COLUNA_SITUACAO).setPreferredWidth(100);
        apontamentoTableCellRender = new ApontamentosTableCellRender(apontamentoTableModel);
        tblApontamentos.getColumnModel()
                .getColumn(ApontamentoTableModel.COLUNA_DATA)
                .setCellRenderer(apontamentoTableCellRender);
        tblApontamentos.getColumnModel()
                .getColumn(ApontamentoTableModel.COLUNA_DIA_DA_SEMANA)
                .setCellRenderer(apontamentoTableCellRender);
        tblApontamentos.getColumnModel()
                .getColumn(ApontamentoTableModel.COLUNA_ASSIDUIDADE)
                .setCellRenderer(new SinalizarColunaTabelaApontamentos(apontamentoTableModel));
        tblApontamentos.getColumnModel()
                .getColumn(ApontamentoTableModel.COLUNA_BANCO_HORAS)
                .setCellRenderer(new IconeApontamentoTableCellRederer());

    }

    private void configurarTabelaApontamentosPonto() {
        tblApontamentoPonto.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblApontamentoPonto.getColumnModel().getColumn(apontamentoPontoTableModel.COLUNA_CHAPA).setPreferredWidth(50);
        tblApontamentoPonto.getColumnModel().getColumn(apontamentoPontoTableModel.COLUNA_NOME).setPreferredWidth(300);
        tblApontamentoPonto.getColumnModel().getColumn(apontamentoPontoTableModel.COLUNA_DATA).setPreferredWidth(100);
        tblApontamentoPonto.getColumnModel().getColumn(apontamentoPontoTableModel.COLUNA_STATUS).setPreferredWidth(100);
        tblApontamentoPonto.getColumnModel().getColumn(apontamentoPontoTableModel.COLUNA_VERIFICADO).setPreferredWidth(100);
        tblApontamentoPonto.getColumnModel().getColumn(apontamentoPontoTableModel.COLUNA_PROBLEMA).setPreferredWidth(100);
        tblApontamentoPonto.getColumnModel().getColumn(apontamentoPontoTableModel.COLUNA_AJUSTADO).setPreferredWidth(100);
        tblApontamentoPonto.getColumnModel().getColumn(apontamentoPontoTableModel.COLUNA_DATA_HORA_MOTIVO).setPreferredWidth(130);
        tblApontamentoPonto.getColumnModel().getColumn(apontamentoPontoTableModel.COLUNA_MOTIVO).setPreferredWidth(350);
        tblApontamentoPonto.getColumnModel().getColumn(apontamentoPontoTableModel.COLUNA_DATA_HORA_JUSTIFICATIVA).setPreferredWidth(130);
        tblApontamentoPonto.getColumnModel().getColumn(apontamentoPontoTableModel.COLUNA_JUSTIFICADO_POR).setPreferredWidth(100);
        tblApontamentoPonto.getColumnModel().getColumn(apontamentoPontoTableModel.COLUNA_JUSTIFICATIVA).setPreferredWidth(350);
        tblApontamentoPonto.getColumnModel().getColumn(apontamentoPontoTableModel.COLUNA_CENTRO_CUSTO).setPreferredWidth(350);
        tblApontamentoPonto.getColumnModel().getColumn(apontamentoPontoTableModel.COLUNA_LIDER).setPreferredWidth(150);
        apontamentoPontoTableCellRender = new ApontamentosPontoTableCellRender(apontamentoPontoTableModel);
        tblApontamentoPonto.setDefaultRenderer(Object.class, apontamentoPontoTableCellRender);
        tblApontamentoPonto.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
    }

    private void configurarTabelaApontamentoProblema() {
        tblApontamentoProblema.getColumnModel().getColumn(apontamentoProblemaTableModel.colunaComentado).setPreferredWidth(80);
        tblApontamentoProblema.getColumnModel().getColumn(apontamentoProblemaTableModel.colunaChapa).setPreferredWidth(80);
        tblApontamentoProblema.getColumnModel().getColumn(apontamentoProblemaTableModel.colunaColaborador).setPreferredWidth(250);
        tblApontamentoProblema.getColumnModel().getColumn(apontamentoProblemaTableModel.colunaObra).setPreferredWidth(300);
        tblApontamentoProblema.getColumnModel().getColumn(apontamentoProblemaTableModel.colunaData).setPreferredWidth(150);
        tblApontamentoProblema.getColumnModel().getColumn(apontamentoProblemaTableModel.colunaGerente).setPreferredWidth(250);
        tblApontamentoProblema.getColumnModel().getColumn(apontamentoProblemaTableModel.colunaLider).setPreferredWidth(250);
        tblApontamentoProblema.getColumnModel().getColumn(apontamentoProblemaTableModel.colunaStatus).setPreferredWidth(80);
        tblApontamentoProblema.getColumnModel().getColumn(apontamentoProblemaTableModel.colunaMotivo).setPreferredWidth(350);
        tblApontamentoProblema.getColumnModel().getColumn(apontamentoProblemaTableModel.colunaJustificativa).setPreferredWidth(350);
        tblApontamentoProblema.getColumnModel()
                .getColumn(apontamentoProblemaTableModel.colunaData)
                .setCellRenderer(new ApontamentoProblemasTableCellRender());
    }

    private List<Apontamento> carregarTabelaApontamentos() {
        ArrayList<Apontamento> apontamentos = new ArrayList<>();
        
        try {

            apontamentos = (ArrayList) apontamentoService.filtrarApontamentos(gerarFiltros());

            apontamentoTableModel.clear();

            for (Apontamento apontamento : apontamentos) {
                apontamentoTableModel.addRow(apontamento);
            }

            jlContador.setText("Número de registros retornados: " + String.valueOf(apontamentos.size()));

        } catch (SQLException se) {
            Logger.getLogger(UIApontamentos.class.getName()).log(Level.SEVERE, null, se);
            JOptionPane.showMessageDialog(UIApontamentos.this, se.getMessage(),
                    "Erro ao filtrar apontamentos! ",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(UIApontamentos.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(UIApontamentos.this, ex.getMessage(),
                    "Erro ao filtrar apontamentos! ",
                    JOptionPane.ERROR_MESSAGE);
        }

        return apontamentos;
    }

    private List<Apontamento> carregarTabelaApontamentoPonto() {

        List<Apontamento> apontamentosPonto = new ArrayList<>();

        try {
            String query = "";
            String txtChapa = jtfChapa.getText();
            String txtNome = jtfNome.getText();
            String txtCentroCusto = jtfCentroCusto.getText();
            String txtDataDe = FormatarData.formatarData(jftfDataInicio.getText());
            String txtDataAte = FormatarData.formatarData(jftfDataTermino.getText());
            Pessoa lider = (Pessoa) jcbLider.getSelectedItem();

            if (!txtChapa.isBlank()) {
                query += " AND APONTAMENTOS.CHAPA = '" + txtChapa + "'";
            }
            if (!txtNome.isBlank()) {
                query += " AND PFUNC.NOME LIKE '%" + txtNome + "%'";
            }
            if (!txtCentroCusto.isBlank()) {
                query = query + " and GCCUSTO.NOME LIKE '%" + jtfCentroCusto.getText() + "%'";
            }
            if (!txtDataDe.equals("  /  /    ") && !txtDataAte.equals("  /  /    ")) {
                query += " AND APONTAMENTOS.DATA BETWEEN '" + txtDataDe + "' AND '" + txtDataAte + "'";
            }
            if (jcbLider.getSelectedIndex() != 0) {
                query = query + " and APONTAMENTOS.CODLIDER = " + lider.getCodPessoa();
            }

            apontamentosPonto = (ArrayList<Apontamento>) apontamentoService.filtrarApontamentoPonto(query);

            apontamentoPontoTableModel.clear();

            for (Apontamento apontamentoPonto : apontamentosPonto) {
                apontamentoPontoTableModel.addRow(apontamentoPonto);
            }

            jlContador.setText("Número de registros retornados: " + String.valueOf(apontamentosPonto.size()));
        } catch (Exception ex) {
            Logger.getLogger(UIApontamentos.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,
                    "Erro ao carregar dados!",
                    ex.getMessage(),
                    JOptionPane.ERROR_MESSAGE);
        }

        return apontamentosPonto;
    }

    private List<Apontamento> carregarTabelaApontamentoProblema() {

        List<Apontamento> apontamentosProblema = new ArrayList<>();

        try {

            String query = " ";
            Pessoa lider = (Pessoa) jcbLider.getSelectedItem();
            Pessoa gerente = (Pessoa) jcbGerente.getSelectedItem();
            StatusApont status = (StatusApont) jcbStatusApont.getSelectedItem();

            if (!jtfChapa.getText().isEmpty()) {
                query = query + " and APONTAMENTOS.CHAPA LIKE '%" + jtfChapa.getText() + "%'";
            }

            if (!jtfNome.getText().isEmpty()) {
                query = query + " and PFUNC.NOME LIKE '%" + jtfNome.getText() + "%'";
            }

            if (!jtfCentroCusto.getText().isEmpty()) {
                query = query + " and GCCUSTO.NOME LIKE '%" + jtfCentroCusto.getText() + "%'";
            }

            if (jcbLider.getSelectedIndex() != 0) {
                query = query + " and APONTAMENTOS.CODLIDER = " + lider.getCodPessoa();
            }

            if (jcbGerente.getSelectedIndex() != 0) {
                query = query + " and GERENTE.CODPESSOA = " + gerente.getCodPessoa();
            }

            if (jcbStatusApont.getSelectedIndex() != 0) {
                query = query + " AND APONTAMENTOS.CODSTATUSAPONT = '" + status.getCodStatusApont() + "'";
            }

            if (cbxNaoApontados.isSelected()) {
                query = query + " AND APONTAMENTOS.CODSTATUSAPONT = '-'";
            }

            if (jchAtivos.isSelected()) {
                query = query + " and PFUNC.CODSITUACAO <> 'D'";
            }

            apontamentosProblema = (ArrayList<Apontamento>) this.apontamentoService.filtrarApontamentoProblema(query);

            apontamentoProblemaTableModel.clear();

            for (Apontamento apontamento : apontamentosProblema) {
                apontamentoProblemaTableModel.addRow(apontamento);
            }

            jlContador.setText("Número de registros retornados: " + String.valueOf(apontamentosProblema.size()));
        } catch (SQLException ex) {
            Logger.getLogger(UIApontamentos.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(UIApontamentos.this, ex.getMessage(),
                    "Erro ao filtrar apontamentos com problema! ",
                    JOptionPane.ERROR_MESSAGE);
        }

        return apontamentosProblema;
    }

    private void carregarTabelaApontamentosPontoPorNotificacoesNaoLidas() {
        Menu.carregamento(true);

        new Thread() {
            @Override
            public void run() {
                try {
                    apontamentoPontoTableModel.clear();
                    ArrayList<Apontamento> apontamentosPonto = (ArrayList<Apontamento>) apontamentoService.buscarPontosPorNotificacoesNaoLidas();

                    for (Apontamento apontamentoPonto : apontamentosPonto) {
                        apontamentoPontoTableModel.addRow(apontamentoPonto);
                    }
                    tblApontamentoPonto.setModel(apontamentoPontoTableModel);
                    configurarTabelaApontamentosPonto();
                    jlContador.setText("Número de registros retornados: " + String.valueOf(apontamentosPonto.size()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao carregar dados!", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(UIApontamentos.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    Menu.carregamento(false);
                }
            }
        }.start();
    }

    private void calcularNumeroDeRegistrosRetornados(int tamanhoDaLista) {
        int contador = 0;
        for (int i = 0; i < tamanhoDaLista; i++) {
            contador++;
        }

        jlContador.setText("Número de registros retornados: " + String.valueOf(contador));
    }

    private void limpar() {
        jtfNome.setText("");
        jtfChapa.setText("");
        jtfCentroCusto.setText("");
        jcbLider.setSelectedIndex(0);
        jcbStatusApont.setSelectedIndex(0);
        jcbGerente.setSelectedIndex(0);
        jchComentados.setSelected(false);
        jchAtivos.setSelected(true);
        jcbSemJustificativa.setSelected(false);
        preencherData();
    }

    private void preencherData() {
        LocalDate dataAtual = LocalDate.now();
        jftfDataInicio.setText(String.valueOf(dataAtual
                .format(DateTimeFormatter
                        .ofPattern("dd/MM/yyyy"))));
        jftfDataTermino.setText(String.valueOf(dataAtual
                .format(DateTimeFormatter
                        .ofPattern("dd/MM/yyyy"))));
    }

    private void preencherComboBoxLider() {
        PessoaDAO dao = DAOFactory.getPESSOADAO();

        try {
            for (Pessoa p : dao.buscarPessoasCombo("lider")) {
                jcbLider.addItem(p);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage());
        }
    }

    private void preencherComboBoxGerente() {
        PessoaDAO dao = DAOFactory.getPESSOADAO();

        try {
            for (Pessoa p : dao.buscarPessoasCombo("gerente")) {
                jcbGerente.addItem(p);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage());
        }
    }

    private void preencherComboBoxStatusApont() {
        StatusApontDAO dao = DAOFactory.getSTATUSAPONTDAO();

        try {
            for (StatusApont status : dao.buscarCombo(true)) {
                jcbStatusApont.addItem(status);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage());
        }
    }

    public List<Apontamento> getApontamentosSelecionados() {
        List<Apontamento> apont = apontamentoTableModel.getApontamentos();
        List<Apontamento> selecionados = new ArrayList<>();
        if (tblApontamentos.getSelectedRow() != -1) {
            for (int selecao : tblApontamentos.getSelectedRows()) {
                Apontamento selecionado = apont.get(selecao);
                selecionados.add(selecionado);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Selecione um funcionário para apontar",
                    "Aviso ao tentar apontar funcionários", JOptionPane.WARNING_MESSAGE);
        }
        return selecionados;
    }

    public void abrirTelaApontamento() {
        if (apontamentoTableModel.getApontamento(tblApontamentos.getSelectedRow()) != null) {
            UIApontamento uiApontamento = new UIApontamento(this);
            if (!uiApontamento.isVisible()) {
                uiApontamento.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione o funcionário para apontar!",
                    "", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void gerarRelatorio() {
        Menu.carregamento(true);
        new Thread() {
            @Override
            public void run() {
                try {
                    Connection con = ConexaoBanco.getConexao();
                    String caminhoCorrente = new File("").getAbsolutePath();
                    String caminhoDaImagem = caminhoCorrente + "/img/dse-logo-relatorio.png";

                    HashMap filtro = new HashMap();
                    filtro.put("USUARIO_PARAM", Menu.getUiLogin().getPessoa().getLogin());
                    filtro.put("IMG_PARAM", caminhoDaImagem);

                    JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoCorrente + "/relatorios/rel-apontamento-comentario.jasper", filtro, con);
                    JasperViewer view = new JasperViewer(jasperPrint, false);
                    view.setVisible(true);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "" + ex.getMessage(),
                            "Erro ao carregar dados", JOptionPane.ERROR_MESSAGE);
                } catch (JRException ex) {
                    JOptionPane.showMessageDialog(null, "" + ex.getMessage(),
                            "Erro ao gerar relatório", JOptionPane.ERROR_MESSAGE);
                } finally {
                    Menu.carregamento(false);
                }
            }
        }.start();
    }

    private String gerarFiltros() throws Exception {
        String query = " ";
        String dataInicio = FormatarData.formatarData(jftfDataInicio.getText());
        String dataTermino = FormatarData.formatarData(jftfDataTermino.getText());
        Pessoa lider = (Pessoa) jcbLider.getSelectedItem();
        Pessoa gerente = (Pessoa) jcbGerente.getSelectedItem();
        StatusApont statusApont = (StatusApont) jcbStatusApont.getSelectedItem();

        if (!jtfChapa.getText().isEmpty()) {
            query = query + " and A.CHAPA LIKE '%" + jtfChapa.getText() + "%'";
        }

        if (!jtfNome.getText().isEmpty()) {
            query = query + " and F.NOME LIKE '%" + jtfNome.getText() + "%'";
        }

        if (!jtfCentroCusto.getText().isEmpty()) {
            query = query + " and C.NOME LIKE '%" + jtfCentroCusto.getText() + "%'";
        }

        if (jcbLider.getSelectedIndex() != 0) {
            query = query + " and A.CODLIDER = " + lider.getCodPessoa();
        }

        if (jcbGerente.getSelectedIndex() != 0) {
            query = query + " and PG.CODPESSOA = " + gerente.getCodPessoa();
        }

        if (!"SE".equals(statusApont.getCodStatusApont())) {
            query = query + " and A.CODSTATUSAPONT = '" + statusApont.getCodStatusApont() + "'";
        }

        if (!jftfDataInicio.getText().equals("  /  /    ") || !jftfDataTermino.getText().equals("  /  /    ")) {
            query = query + " and A.DATA BETWEEN ('" + dataInicio + "')"
                    + " and ('" + dataTermino + "')";
        }

        if (jchAtivos.isSelected()) {
            query = query + " and F.CODSITUACAO <> 'D'";
        }
        jbGerarRelatoriosComentarios.setEnabled(false);

        if (jchComentados.isSelected()) {
            query = query + " and A.COMENTADO = TRUE";
            jbGerarRelatoriosComentarios.setEnabled(true);
        }

        if (cbxAssiduidade.isSelected()) {
            query = query + " and A.ASSIDUIDADE = FALSE";
        }

        return query;
    }

    private ArrayList<Apontamento> pesquisar() {

        ArrayList<Apontamento> apontamentos;

        switch (jtpPainelApontamentos.getSelectedIndex()) {
            case 0:
                apontamentos = (ArrayList<Apontamento>) carregarTabelaApontamentos();
                break;
            case 1:
                apontamentos = (ArrayList<Apontamento>) carregarTabelaApontamentoPonto();
                break;
            case 2:
                apontamentos = (ArrayList<Apontamento>) carregarTabelaApontamentoProblema();
                break;
            default:
                apontamentos = (ArrayList<Apontamento>) carregarTabelaApontamentos();
        }

        return apontamentos;
    }

    private void jbPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPesquisarActionPerformed
        pesquisar();
    }//GEN-LAST:event_jbPesquisarActionPerformed

    private void jbHojeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbHojeActionPerformed
        preencherData();
    }//GEN-LAST:event_jbHojeActionPerformed

    private void jbLimparFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparFiltrosActionPerformed
        limpar();
        pesquisar();
    }//GEN-LAST:event_jbLimparFiltrosActionPerformed

    private void tblApontamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblApontamentosMouseClicked
        if (evt.getClickCount() == 2) {
            UIApontamento uiApontamento = new UIApontamento(this);
            if (!uiApontamento.isVisible()) {
                uiApontamento.setVisible(true);
            }
        }
    }//GEN-LAST:event_tblApontamentosMouseClicked

    private void jtfChapaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfChapaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisar();
        }
    }//GEN-LAST:event_jtfChapaKeyPressed

    private void jftfDataInicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfDataInicioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisar();
        }
    }//GEN-LAST:event_jftfDataInicioKeyPressed

    private void jftfDataTerminoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfDataTerminoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisar();
        }
    }//GEN-LAST:event_jftfDataTerminoKeyPressed

    private void jbComentariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbComentariosActionPerformed
        Apontamento apontamento = apontamentoTableModel.getApontamento(tblApontamentos.getSelectedRow());
        if (apontamento != null) {
            new UIComentariosApontamentos(this, apontamento).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione o apontamento!",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jbComentariosActionPerformed

    private void jbApontarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbApontarActionPerformed
        if (tblApontamentos.getSelectedRowCount() > 1) {
            flagApontamentosEmMassa = true;
        }
        abrirTelaApontamento();
    }//GEN-LAST:event_jbApontarActionPerformed

    private void jtfCentroCustoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCentroCustoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisar();
        }
    }//GEN-LAST:event_jtfCentroCustoKeyPressed

    private void jbGerarRelatoriosComentariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbGerarRelatoriosComentariosActionPerformed
        try {
            gerarRelatorio();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro ao gerar relatório", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(UIApontamentos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jbGerarRelatoriosComentariosActionPerformed

    private void jbExportarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExportarExcelActionPerformed
        Menu.carregamento(true);
        new Thread(() -> {
            try {
                ApontamentoDAO dao = DAOFactory.getAPONTAMENTODAO();
                ExportaExcel excel = new ExportaExcel(System.getProperty("user.home") + "\\Documents\\rel-apontamentos.xls");

                String query = " ";
                String dataInicio = FormatarData.formatarData(jftfDataInicio.getText());
                String dataTermino = FormatarData.formatarData(jftfDataTermino.getText());
                Pessoa lider = (Pessoa) jcbLider.getSelectedItem();
                Pessoa gerente = (Pessoa) jcbGerente.getSelectedItem();
                StatusApont statusApont = (StatusApont) jcbStatusApont.getSelectedItem();

                if (!jtfChapa.getText().isEmpty()) {
                    query = query + " and A.CHAPA LIKE '%" + jtfChapa.getText() + "%'";
                }

                if (!jtfNome.getText().isEmpty()) {//se tiver alguma coisa no campo 
                    query = query + " and F.NOME LIKE '%" + jtfNome.getText() + "%'";
                }

                if (!jtfCentroCusto.getText().isEmpty()) {
                    query = query + " and C.NOME LIKE '%" + jtfCentroCusto.getText() + "%'";
                }

                if (jcbLider.getSelectedIndex() != 0) {
                    query = query + " and A.CODLIDER = " + lider.getCodPessoa();
                }

                if (jcbGerente.getSelectedIndex() != 0) {
                    query = query + " and PG.CODPESSOA = " + gerente.getCodPessoa();
                }

                if (!"SE".equals(statusApont.getCodStatusApont())) {
                    query = query + " and A.CODSTATUSAPONT = '" + statusApont.getCodStatusApont() + "'";
                }

                if (!jftfDataInicio.getText().equals("  /  /    ") || !jftfDataTermino.getText().equals("  /  /    ")) {
                    query = query + " and A.DATA BETWEEN ('" + dataInicio + "')"
                            + " and ('" + dataTermino + "')";
                }

                if (jchAtivos.isSelected()) {
                    query = query + " and F.CODSITUACAO <> 'D'";
                }
                jbGerarRelatoriosComentarios.setEnabled(false);

                if (jchComentados.isSelected()) {
                    query = query + " and A.COMENTADO = TRUE";
                    jbGerarRelatoriosComentarios.setEnabled(true);
                }

                ArrayList<Apontamento> aponts = dao.filtrarApontamentos(query);

                excel.exportarApontamentos(aponts);

                JOptionPane.showMessageDialog(UIApontamentos.this, "Apontamentos exportados em " + excel.getFileName());

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(UIApontamentos.this,
                        ex.getMessage(),
                        "Erro ao exportar apontamentos",
                        JOptionPane.ERROR_MESSAGE);
            } finally {
                Menu.carregamento(false);
            }
        }).start();

    }//GEN-LAST:event_jbExportarExcelActionPerformed

    private void jftfDataInicioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jftfDataInicioFocusGained
        jftfDataInicio.selectAll();
    }//GEN-LAST:event_jftfDataInicioFocusGained

    private void jftfDataTerminoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jftfDataTerminoFocusGained
        jftfDataTermino.selectAll();
    }//GEN-LAST:event_jftfDataTerminoFocusGained

    private void jmiTomadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiTomadoresActionPerformed
        new UITomadores().setVisible(true);
    }//GEN-LAST:event_jmiTomadoresActionPerformed

    private void jtfNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNomeKeyPressed
        if (evt.getKeyCode() == evt.VK_ENTER) {
            pesquisar();
        }
    }//GEN-LAST:event_jtfNomeKeyPressed

    private void jtpPainelApontamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtpPainelApontamentosMouseClicked
        if (flagPrimeiroCarregamentoPonto) {
            flagPrimeiroCarregamentoPonto = false;
            pesquisar();
        } else if (flagPrimeiroCarregamentoProblema) {
            flagPrimeiroCarregamentoProblema = false;
            pesquisar();
        }
    }//GEN-LAST:event_jtpPainelApontamentosMouseClicked

    private void btnAvisosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAvisosActionPerformed
        carregarTabelaApontamentosPontoPorNotificacoesNaoLidas();
    }//GEN-LAST:event_btnAvisosActionPerformed

    private void btnPeriodoAtualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeriodoAtualActionPerformed
        LocalDate hoje = LocalDate.now();
        int mesAtual = hoje.getMonthValue();
        int proximoMes = mesAtual + 1;
        int anoAtual = hoje.getYear();
        int proximoAno = hoje.getYear() + 1;

        String de = LocalDate.of(anoAtual, mesAtual, 16).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String ate;

        if (proximoMes > 12) {
            ate = LocalDate.of(proximoAno, 1, 15).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            ate = LocalDate.of(anoAtual, proximoMes, 15).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        jftfDataInicio.setText(de);
        jftfDataTermino.setText(ate);
    }//GEN-LAST:event_btnPeriodoAtualActionPerformed

    private void btnPeriodoAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeriodoAnteriorActionPerformed
        LocalDate hoje = LocalDate.now();
        int mesAtual = hoje.getMonthValue();
        int mesAnterior = mesAtual - 1;
        int anoAtual = hoje.getYear();
        int anoAnterior = anoAtual - 1;

        String de;
        String ate;

        if (mesAnterior < 1) {
            de = LocalDate.of(anoAnterior, 12, 16).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } else {
            de = LocalDate.of(anoAtual, mesAnterior, 16).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        ate = LocalDate.of(anoAtual, mesAtual, 15).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        jftfDataInicio.setText(de);
        jftfDataTermino.setText(ate);
    }//GEN-LAST:event_btnPeriodoAnteriorActionPerformed

    private void tblApontamentoPontoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblApontamentoPontoMouseClicked
        int linhaSelecionada = tblApontamentoPonto.getSelectedRow();

        Apontamento apontamento = apontamentoPontoTableModel.getApontamento(linhaSelecionada);
        String motivo = (String) tblApontamentoPonto.getValueAt(linhaSelecionada, apontamentoPontoTableModel.COLUNA_MOTIVO);

        if (apontamento.isProblema() && motivo == null) {
            tblApontamentoPonto.editCellAt(linhaSelecionada, apontamentoPontoTableModel.COLUNA_MOTIVO);
            JOptionPane.showMessageDialog(null,
                    "Campo motivo deve ser preenchido",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_tblApontamentoPontoMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAvisos;
    private javax.swing.JButton btnPeriodoAnterior;
    private javax.swing.JButton btnPeriodoAtual;
    private javax.swing.JCheckBox cbxAssiduidade;
    private javax.swing.JCheckBox cbxNaoApontados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbApontar;
    private javax.swing.JButton jbComentarios;
    private javax.swing.JButton jbExportarExcel;
    private javax.swing.JButton jbGerarRelatoriosComentarios;
    private javax.swing.JButton jbHoje;
    private javax.swing.JButton jbLimparFiltros;
    private javax.swing.JButton jbPesquisar;
    private javax.swing.JComboBox<Pessoa> jcbGerente;
    private javax.swing.JComboBox<Object> jcbLider;
    private javax.swing.JCheckBox jcbSemJustificativa;
    private javax.swing.JComboBox<StatusApont> jcbStatusApont;
    private javax.swing.JCheckBox jchAtivos;
    private javax.swing.JCheckBox jchComentados;
    private javax.swing.JFormattedTextField jftfDataInicio;
    private javax.swing.JFormattedTextField jftfDataTermino;
    private javax.swing.JLabel jlCentroCusto;
    private javax.swing.JLabel jlChapa;
    private javax.swing.JLabel jlContador;
    private javax.swing.JLabel jlDataInicio;
    private javax.swing.JLabel jlDataTermino;
    private javax.swing.JLabel jlGerente;
    private javax.swing.JLabel jlLider;
    private javax.swing.JLabel jlNome;
    private javax.swing.JList<Object> jlstCentroCusto;
    private javax.swing.JMenu jmRelatorios;
    private javax.swing.JMenuItem jmiTomadores;
    private javax.swing.JScrollPane jspCentroCusto;
    private javax.swing.JTextField jtfCentroCusto;
    private javax.swing.JTextField jtfChapa;
    private javax.swing.JTextField jtfNome;
    private javax.swing.JTabbedPane jtpPainelApontamentos;
    private javax.swing.JTable tblApontamentoPonto;
    private javax.swing.JTable tblApontamentoProblema;
    public javax.swing.JTable tblApontamentos;
    // End of variables declaration//GEN-END:variables

}
