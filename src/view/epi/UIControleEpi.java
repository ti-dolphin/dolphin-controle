/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.epi;

import dao.DAOFactory;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.epi.Epi;
import model.epi.EpiFuncionario;
import model.epi.tables.EpiFuncionarioTableModel;
import model.Funcionario;
import model.epi.tables.EpiTableModel;
import model.epi.tables.FuncionarioTableModel;
import org.apache.commons.mail.EmailException;
import services.epi.EpiFuncionarioService;
import services.epi.EpiService;
import services.funcionario.FuncionarioService;
import services.ServicosFactory;
import services.email.EmailEpiFuncionarioService;
import utilitarios.FormatarData;
import utilitarios.os.CurrencyTableCellRenderer;
import view.Menu;

/**
 *
 * @author guilherme.oliveira
 */
public class UIControleEpi extends javax.swing.JInternalFrame {

    private EpiService epiService;
    private EpiFuncionarioService epiFuncionarioService;
    private FuncionarioService funcionarioService;
    private FuncionarioTableModel fTableModel = new FuncionarioTableModel();
    private EpiFuncionarioTableModel efTableModel = new EpiFuncionarioTableModel();
    private EpiTableModel eTableModel = new EpiTableModel();
    private UIFuncionarioEPI uiFuncionarioEpi;
    private ArrayList<EpiFuncionario> episFuncionario;
    private boolean flagNavegador = false;
    private Menu menu;
    private CurrencyTableCellRenderer cRenderer = new CurrencyTableCellRenderer();

    public UIControleEpi(Menu menu) {
        this.menu = menu;
        this.epiService = new EpiService();
        this.epiFuncionarioService = new EpiFuncionarioService();
        this.funcionarioService = new FuncionarioService();
        this.episFuncionario = new ArrayList<>();
        initComponents();
        darPermissoes();
        jchAtivos.setSelected(true);
        fTableModel.clear();
        filtrarFuncionario();
        eTableModel.clear();
        filtrarEpi();
        efTableModel.clear();
        filtrarEpiFuncionario();
        redimensionarColunasFunc();
        configTabelaHist();
        configTabelaEpis();
    }

    public Menu getMenu() {
        return menu;
    }

    public UIFuncionarioEPI getUiFuncionarioEpi() {
        return uiFuncionarioEpi;
    }

    public ArrayList<EpiFuncionario> getEpisFuncionario() {
        return episFuncionario;
    }
    
    public void darPermissoes() {
        if (menu.getUiLogin().getPessoa().isPermAutenticacao()) {
            jbCadAutenticacao.setEnabled(true);
        }

        if (Menu.logado.isPermDescontado()) {
            jchDescontar.setVisible(true);
        } else {
            jchDescontar.setVisible(false);
        }
    }

    private void redimensionarColunasFunc() {
        try {
            jtblFuncionario.setAutoResizeMode(jtblFuncionario.AUTO_RESIZE_OFF);
            jtblFuncionario.getColumnModel().getColumn(0).setPreferredWidth(50);//coligada
            jtblFuncionario.getColumnModel().getColumn(1).setPreferredWidth(50);//chapa
            jtblFuncionario.getColumnModel().getColumn(2).setPreferredWidth(300);//funcionario
            jtblFuncionario.getColumnModel().getColumn(3).setPreferredWidth(60);//situacao
            jtblFuncionario.getColumnModel().getColumn(4).setPreferredWidth(300);//funcao
            jtblFuncionario.getColumnModel().getColumn(5).setPreferredWidth(100);//data admissao
            jtblFuncionario.getColumnModel().getColumn(6).setPreferredWidth(50);//filial
            jtblFuncionario.getColumnModel().getColumn(7).setPreferredWidth(150);//cpf
            jtblFuncionario.getColumnModel().getColumn(8).setPreferredWidth(100);//digital
            jtblFuncionario.getColumnModel().getColumn(9).setPreferredWidth(100);//senha
            jtblFuncionario.getColumnModel().getColumn(10).setPreferredWidth(300);//email
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configTabelaEpis() {
        try {
            jtblEpi.getColumnModel().getColumn(2).setCellRenderer(cRenderer);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configTabelaHist() {
        try {
            jtblEpiFuncionario.getColumnModel().getColumn(10).setCellRenderer(cRenderer);

            jtblEpiFuncionario.setAutoResizeMode(jtblEpiFuncionario.AUTO_RESIZE_OFF);
            jtblEpiFuncionario.getColumnModel().getColumn(0).setPreferredWidth(50);//id
            jtblEpiFuncionario.getColumnModel().getColumn(1).setPreferredWidth(50);//Coligada
            jtblEpiFuncionario.getColumnModel().getColumn(2).setPreferredWidth(100);//Chapa
            jtblEpiFuncionario.getColumnModel().getColumn(3).setPreferredWidth(400);//Funcionario
            jtblEpiFuncionario.getColumnModel().getColumn(4).setPreferredWidth(400);//EPI
            jtblEpiFuncionario.getColumnModel().getColumn(5).setPreferredWidth(150);//Data da Retirada
            jtblEpiFuncionario.getColumnModel().getColumn(6).setPreferredWidth(150);//Data da Devolução
            jtblEpiFuncionario.getColumnModel().getColumn(7).setPreferredWidth(100);//CA
            jtblEpiFuncionario.getColumnModel().getColumn(8).setPreferredWidth(150);//Entregue Por
            jtblEpiFuncionario.getColumnModel().getColumn(9).setPreferredWidth(150);//Devolvido Por
            if (Menu.logado.isPermDescontado()) {
                jtblEpiFuncionario.getColumnModel().getColumn(10).setPreferredWidth(150);//Preco a descontar

                jtblEpiFuncionario.getColumnModel().getColumn(11).setPreferredWidth(80);//Descontado
            } else {
                jtblEpiFuncionario.getColumnModel().getColumn(10).setMinWidth(0);//Preco a descontar
                jtblEpiFuncionario.getColumnModel().getColumn(10).setPreferredWidth(0);//Preco a descontar
                jtblEpiFuncionario.getColumnModel().getColumn(11).setMinWidth(0);//Descontado
                jtblEpiFuncionario.getColumnModel().getColumn(11).setPreferredWidth(0);//Descontado
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtpControle = new javax.swing.JTabbedPane();
        jpFuncionarios = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblFuncionario = new javax.swing.JTable();
        jlColigada = new javax.swing.JLabel();
        jtfColigada = new javax.swing.JTextField();
        jlChapa = new javax.swing.JLabel();
        jtfChapa = new javax.swing.JTextField();
        jlNome = new javax.swing.JLabel();
        jtfNome = new javax.swing.JTextField();
        jbCadAutenticacao = new javax.swing.JButton();
        jbFPesquisar = new javax.swing.JButton();
        jbAtualizarFuncionarios = new javax.swing.JButton();
        jchAtivos = new javax.swing.JCheckBox();
        jpHistorico = new javax.swing.JPanel();
        jlHColigada = new javax.swing.JLabel();
        jtfHColigada = new javax.swing.JTextField();
        jlHFuncionario = new javax.swing.JLabel();
        jtfHNome = new javax.swing.JTextField();
        jlHNomeEpi = new javax.swing.JLabel();
        jtfHNomeEpi = new javax.swing.JTextField();
        jlHCa = new javax.swing.JLabel();
        jtfHCa = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtblEpiFuncionario = new javax.swing.JTable();
        jbHPesquisar = new javax.swing.JButton();
        jbAtualizarHistorico = new javax.swing.JButton();
        jlHChapa = new javax.swing.JLabel();
        jtfHChapa = new javax.swing.JTextField();
        jftfDataInicio = new javax.swing.JFormattedTextField();
        jftfDataTermino = new javax.swing.JFormattedTextField();
        jlAte = new javax.swing.JLabel();
        jlDe = new javax.swing.JLabel();
        jchDescontar = new javax.swing.JCheckBox();
        jpCadEpi = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtblEpi = new javax.swing.JTable();
        jlCodEpiFiltro = new javax.swing.JLabel();
        jtfCodEpiFiltro = new javax.swing.JTextField();
        jlNomeEpiFiltro = new javax.swing.JLabel();
        jtfNomeEpiFiltro = new javax.swing.JTextField();
        jbCPesquisar = new javax.swing.JButton();
        jbAtualizarEpis = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Controle de EPI");
        setPreferredSize(new java.awt.Dimension(1588, 836));

        jtblFuncionario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtblFuncionario.setToolTipText("");
        jtblFuncionario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblFuncionarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtblFuncionario);

        jlColigada.setText("Coligada");

        jtfColigada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfColigadaKeyPressed(evt);
            }
        });

        jlChapa.setText("Chapa");

        jtfChapa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfChapaKeyPressed(evt);
            }
        });

        jlNome.setText("Nome");

        jtfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfNomeKeyPressed(evt);
            }
        });

        jbCadAutenticacao.setText("Cadastrar Autenticação");
        jbCadAutenticacao.setEnabled(false);
        jbCadAutenticacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCadAutenticacaoActionPerformed(evt);
            }
        });

        jbFPesquisar.setText("Pesquisar");
        jbFPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbFPesquisarActionPerformed(evt);
            }
        });

        jbAtualizarFuncionarios.setText("Limpar Filtros");
        jbAtualizarFuncionarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAtualizarFuncionariosActionPerformed(evt);
            }
        });

        jchAtivos.setText("Ativos");

        javax.swing.GroupLayout jpFuncionariosLayout = new javax.swing.GroupLayout(jpFuncionarios);
        jpFuncionarios.setLayout(jpFuncionariosLayout);
        jpFuncionariosLayout.setHorizontalGroup(
            jpFuncionariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpFuncionariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpFuncionariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jpFuncionariosLayout.createSequentialGroup()
                        .addGroup(jpFuncionariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfColigada, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlColigada))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpFuncionariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlChapa)
                            .addComponent(jtfChapa, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpFuncionariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpFuncionariosLayout.createSequentialGroup()
                                .addComponent(jtfNome, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jchAtivos))
                            .addGroup(jpFuncionariosLayout.createSequentialGroup()
                                .addComponent(jlNome)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbCadAutenticacao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAtualizarFuncionarios)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbFPesquisar)))
                .addContainerGap())
        );
        jpFuncionariosLayout.setVerticalGroup(
            jpFuncionariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpFuncionariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpFuncionariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlColigada)
                    .addComponent(jlChapa)
                    .addComponent(jlNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpFuncionariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfColigada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfChapa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbFPesquisar)
                    .addComponent(jbAtualizarFuncionarios)
                    .addComponent(jbCadAutenticacao)
                    .addComponent(jchAtivos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtpControle.addTab("Funcionários", jpFuncionarios);

        jlHColigada.setText("Coligada");

        jtfHColigada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfHColigadaKeyPressed(evt);
            }
        });

        jlHFuncionario.setText("Funcionário");

        jtfHNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfHNomeKeyPressed(evt);
            }
        });

        jlHNomeEpi.setText("EPI");

        jtfHNomeEpi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfHNomeEpiKeyPressed(evt);
            }
        });

        jlHCa.setText("CA");

        jtfHCa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfHCaKeyPressed(evt);
            }
        });

        jtblEpiFuncionario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Título 5", "Título 6", "Título 7", "Título 8", "Título 9", "Título 10"
            }
        ));
        jtblEpiFuncionario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblEpiFuncionarioMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtblEpiFuncionario);

        jbHPesquisar.setText("Pesquisar");
        jbHPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbHPesquisarActionPerformed(evt);
            }
        });

        jbAtualizarHistorico.setText("Limpar Filtros");
        jbAtualizarHistorico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAtualizarHistoricoActionPerformed(evt);
            }
        });

        jlHChapa.setText("Chapa");

        jtfHChapa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfHChapaKeyPressed(evt);
            }
        });

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
        jftfDataTermino.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfDataTerminoKeyPressed(evt);
            }
        });

        jlAte.setText("Até");

        jlDe.setText("De");

        jchDescontar.setText("Descontar");

        javax.swing.GroupLayout jpHistoricoLayout = new javax.swing.GroupLayout(jpHistorico);
        jpHistorico.setLayout(jpHistoricoLayout);
        jpHistoricoLayout.setHorizontalGroup(
            jpHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpHistoricoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpHistoricoLayout.createSequentialGroup()
                        .addGroup(jpHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfHColigada, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlHColigada))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfHChapa, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlHChapa))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpHistoricoLayout.createSequentialGroup()
                                .addComponent(jlHFuncionario)
                                .addGap(0, 186, Short.MAX_VALUE))
                            .addComponent(jtfHNome))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlHNomeEpi)
                            .addComponent(jtfHNomeEpi, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfHCa, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlHCa))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jftfDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlDe))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jftfDataTermino, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlAte))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jchDescontar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbAtualizarHistorico)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbHPesquisar)))
                .addContainerGap())
        );
        jpHistoricoLayout.setVerticalGroup(
            jpHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpHistoricoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlHColigada)
                        .addComponent(jlHChapa)
                        .addComponent(jlHFuncionario))
                    .addGroup(jpHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlDe)
                        .addComponent(jlHCa)
                        .addComponent(jlHNomeEpi)
                        .addComponent(jlAte)))
                .addGroup(jpHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpHistoricoLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jpHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbHPesquisar)
                            .addComponent(jbAtualizarHistorico)
                            .addComponent(jftfDataTermino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jftfDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfHCa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfHNomeEpi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jchDescontar)))
                    .addGroup(jpHistoricoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpHistoricoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtfHColigada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfHChapa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfHNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jtpControle.addTab("Histórico", jpHistorico);

        jtblEpi.setModel(new javax.swing.table.DefaultTableModel(
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
        jtblEpi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtblEpiMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jtblEpi);

        jlCodEpiFiltro.setText("Código");

        jtfCodEpiFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfCodEpiFiltroKeyPressed(evt);
            }
        });

        jlNomeEpiFiltro.setText("Nome");

        jtfNomeEpiFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfNomeEpiFiltroKeyPressed(evt);
            }
        });

        jbCPesquisar.setText("Pesquisar");
        jbCPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCPesquisarActionPerformed(evt);
            }
        });

        jbAtualizarEpis.setText("Limpar Filtros");
        jbAtualizarEpis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAtualizarEpisActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpCadEpiLayout = new javax.swing.GroupLayout(jpCadEpi);
        jpCadEpi.setLayout(jpCadEpiLayout);
        jpCadEpiLayout.setHorizontalGroup(
            jpCadEpiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadEpiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadEpiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpCadEpiLayout.createSequentialGroup()
                        .addGroup(jpCadEpiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlCodEpiFiltro)
                            .addComponent(jtfCodEpiFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpCadEpiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfNomeEpiFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlNomeEpiFiltro))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 552, Short.MAX_VALUE)
                        .addComponent(jbAtualizarEpis)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCPesquisar)))
                .addContainerGap())
        );
        jpCadEpiLayout.setVerticalGroup(
            jpCadEpiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpCadEpiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpCadEpiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCodEpiFiltro)
                    .addComponent(jlNomeEpiFiltro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpCadEpiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfCodEpiFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfNomeEpiFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAtualizarEpis)
                    .addComponent(jbCPesquisar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtpControle.addTab("EPI's", jpCadEpi);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtpControle)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtpControle)
                .addContainerGap())
        );

        setBounds(0, 0, 1144, 584);
    }// </editor-fold>//GEN-END:initComponents

    private void jtblFuncionarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblFuncionarioMouseClicked
        if (evt.getClickCount() == 2) {
            uiFuncionarioEpi = new UIFuncionarioEPI(UIControleEpi.this);
            uiFuncionarioEpi.setVisible(true);
            Menu.carregamento(true);
            new Thread(() -> {
                try {
                    EmailEpiFuncionarioService emailService = new EmailEpiFuncionarioService();
                    emailService.enviarComprovanteDeEntregaDeEpiPorEmail(episFuncionario);
                    episFuncionario.clear();
                } catch (EmailException ex) {
                    Logger.getLogger(UIFuncionarioEPI.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(),
                            "Erro ao enviar e-mail",
                            JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    Logger.getLogger(UIFuncionarioEPI.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(),
                            "Erro ao enviar e-mail",
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    Menu.carregamento(false);
                }
            }).start();
        }
    }//GEN-LAST:event_jtblFuncionarioMouseClicked

    /**
     * Método usado para abrir tela de cadastro de autenticação
     *
     * @param evt
     */
    private void jbCadAutenticacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCadAutenticacaoActionPerformed
        if (getFuncionarioDaLinhaSelecionada() != null) {
            new UICadAutenticacao(this).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "Selecione o registro!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }//GEN-LAST:event_jbCadAutenticacaoActionPerformed

    /**
     * Método usado para pesquisar EPI's
     *
     * @param evt
     */
    private void jbCPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCPesquisarActionPerformed
        pesquisarEpis();
    }//GEN-LAST:event_jbCPesquisarActionPerformed

    /**
     * Método usado para atualizar tabela de EPI's
     *
     * @param evt
     */
    private void jbAtualizarEpisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAtualizarEpisActionPerformed
        eTableModel.clear();
        limparCamposEpis();
        filtrarEpi();
    }//GEN-LAST:event_jbAtualizarEpisActionPerformed

    /**
     * Método usado para filtrar tabela de EPI's pelo código ao pressionar a
     * tecla ENTER do teclado
     *
     * @param evt
     */
    private void jtfCodEpiFiltroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCodEpiFiltroKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarEpis();
        }
    }//GEN-LAST:event_jtfCodEpiFiltroKeyPressed

    /**
     * Método usado para filtrar tabela de EPI's pelo nome ao pressionar a tecla
     * ENTER do teclado
     *
     * @param evt
     */
    private void jtfNomeEpiFiltroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNomeEpiFiltroKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarEpis();
        }
    }//GEN-LAST:event_jtfNomeEpiFiltroKeyPressed

    /**
     * Método usado para pesquisar dados da tabela de histórico
     *
     * @param evt
     */
    private void jbHPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbHPesquisarActionPerformed
        pesquisarHistorico();
    }//GEN-LAST:event_jbHPesquisarActionPerformed

    /**
     * Método usado para atualizar a tabela de histórico
     *
     * @param evt
     */
    private void jbAtualizarHistoricoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAtualizarHistoricoActionPerformed
        atualizarTblHistorico();
    }//GEN-LAST:event_jbAtualizarHistoricoActionPerformed

    /**
     * Método usado para filtrar a tabela de histórico pela coligada ao
     * pressionar tecla ENTER do teclado
     *
     * @param evt
     */
    private void jtfHColigadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfHColigadaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarHistorico();
        }
    }//GEN-LAST:event_jtfHColigadaKeyPressed

    /**
     * Método usado para filtrar a tabela de histórico pelo nome do funcionário
     * ao pressionar tecla ENTER do teclado
     *
     * @param evt
     */
    private void jtfHNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfHNomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarHistorico();
        }
    }//GEN-LAST:event_jtfHNomeKeyPressed

    /**
     * Método usado para filtrar a tabela de histórico pelo nome do EPI ao
     * pressionar tecla ENTER do teclado
     *
     * @param evt
     */
    private void jtfHNomeEpiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfHNomeEpiKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarHistorico();
        }
    }//GEN-LAST:event_jtfHNomeEpiKeyPressed

    /**
     * Método usado para filtrar a tabela de histórico pelo CA ao pressionar
     * tecla ENTER do teclado
     *
     * @param evt
     */
    private void jtfHCaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfHCaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarHistorico();
        }
    }//GEN-LAST:event_jtfHCaKeyPressed

    /**
     * Método usado para pesquisar funcionários
     *
     * @param evt
     */
    private void jbFPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbFPesquisarActionPerformed
        pesquisarFuncionario();
    }//GEN-LAST:event_jbFPesquisarActionPerformed

    /**
     * Método usado para atualizar funcionários
     *
     * @param evt
     */
    private void jbAtualizarFuncionariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAtualizarFuncionariosActionPerformed
        fTableModel.clear();
        limparCamposFuncionarios();
        filtrarFuncionario();
    }//GEN-LAST:event_jbAtualizarFuncionariosActionPerformed

    /**
     * Método usado para filtrar funcionários pela coligada ao pressionar tecla
     * ENTER do teclado
     *
     * @param evt
     */
    private void jtfColigadaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfColigadaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarFuncionario();
        }
    }//GEN-LAST:event_jtfColigadaKeyPressed

    /**
     * Método usado para filtrar funcionários pela chapa ao pressionar tecla
     * ENTER do teclado
     *
     * @param evt
     */
    private void jtfChapaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfChapaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarFuncionario();
        }
    }//GEN-LAST:event_jtfChapaKeyPressed

    /**
     * Método usado para filtrar funcionários pelo nome ao pressionar tecla
     * ENTER do teclado
     *
     * @param evt
     */
    private void jtfNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarFuncionario();
        }
    }//GEN-LAST:event_jtfNomeKeyPressed

    /**
     * Método usado para filtrar dados da tabela de histórico pela chapa ao
     * pressionar tecla ENTER do teclado
     *
     * @param evt
     */
    private void jtfHChapaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfHChapaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            efTableModel.clear();
            filtrarEpiFuncionario();
        }
    }//GEN-LAST:event_jtfHChapaKeyPressed

    private void jftfDataTerminoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfDataTerminoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarHistorico();
        }
    }//GEN-LAST:event_jftfDataTerminoKeyPressed

    private void jtblEpiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblEpiMouseClicked
        if (evt.getClickCount() == 2) {
            if (Menu.logado.isPermCadEpi()) {
                new UICadEpi(getEpiDaLinhaSelecionada()).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Você não tem permissão para alterar o EPI",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_jtblEpiMouseClicked

    private void jtblEpiFuncionarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtblEpiFuncionarioMouseClicked
        try {
            if (Menu.logado.isPermDescontado()) {
                if (jtblEpiFuncionario.getValueAt(jtblEpiFuncionario.getSelectedRow(),
                        jtblEpiFuncionario.getSelectedColumn()).getClass().equals(Boolean.class)) {
                    EpiFuncionario historico = getEpiFuncionarioSelecionado();

                    DAOFactory.getEpifuncionariodao().alterarDescontado(historico);
                    efTableModel.setValueAt(historico.getEpi().getPreco(), jtblEpiFuncionario.getSelectedRow(), 10);

                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    e.getMessage(),
                    "Erro ao salvar desconto no banco de dados",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_jtblEpiFuncionarioMouseClicked

    private void limparCamposFuncionarios() {
        jtfColigada.setText("");
        jtfChapa.setText("");
        jtfNome.setText("");
    }//limparCamposFuncionarios

    private void limparCamposEpis() {
        jtfCodEpiFiltro.setText("");
        jtfNomeEpiFiltro.setText("");
    }//limparCamposEpis

    /**
     * Método usado para filtrar os funcionários
     */
    public void filtrarFuncionario() {
        try {
            String query = " where f.CHAPA > 0";

            if (!jtfColigada.getText().isEmpty()) {

                query += " and f.CODCOLIGADA = " + jtfColigada.getText();
            }

            if (!jtfChapa.getText().isEmpty()) {

                query += " and f.CHAPA like '%" + jtfChapa.getText() + "%'";
            }

            if (!jtfNome.getText().isEmpty()) {

                query += " and f.NOME like '%" + jtfNome.getText() + "%'";
            }

            if (jchAtivos.isSelected()) {

                query += " and f.CODSITUACAO <> 'D'";
            }

            ArrayList<Funcionario> funcionarios = funcionarioService.filtarFuncionarios(query);

            for (Funcionario funcionario : funcionarios) {

                fTableModel.addRow(funcionario);
            } //fecha for

            jtblFuncionario.setModel(fTableModel);

        } catch (SQLException se) {

            JOptionPane.showMessageDialog(rootPane, se.getMessage());
        }
    }//fecha filtarFuncionario

    /**
     * Método usado para filtrar tabela de histórico
     */
    public void filtrarEpiFuncionario() {
        try {
            String query = " where ef.CHAPA > 0";

            if (!jtfHColigada.getText().isEmpty()) {
                query += " and ef.CODCOLIGADA = " + jtfHColigada.getText();
            }

            if (!jtfHNome.getText().isEmpty()) {
                query += " and f.NOME like '%" + jtfHNome.getText() + "%'";
            }

            if (!jtfHChapa.getText().isEmpty()) {
                query += " and ef.CHAPA like '%" + jtfHChapa.getText() + "%'";
            }

            if (!jtfHNomeEpi.getText().isEmpty()) {
                query += " and e.NOME like '%" + jtfHNomeEpi.getText() + "%'";
            }

            if (!jtfHCa.getText().isEmpty()) {
                query += " and ef.CA like '%" + jtfHCa.getText() + "%'";
            }

            if (!jftfDataInicio.getText().equals("  /  /    ") && !jftfDataTermino.getText().equals("  /  /    ")) {

                String dataInicio = FormatarData.formatarData(jftfDataInicio.getText());
                String dataTermino = FormatarData.formatarData(jftfDataTermino.getText());

                query = query + " and ef.DATARETIRADA BETWEEN ('" + dataInicio + "')"
                        + " and ('" + dataTermino + "')";
            }

            if (jchDescontar.isSelected()) {
                query += " and ef.DESCONTAR = TRUE and ef.DESCONTADO = FALSE";
            }

            ArrayList<EpiFuncionario> registrosDeEntrega = epiFuncionarioService.filtrarEpiFuncionario(query);

            for (EpiFuncionario registroDeEntrega : registrosDeEntrega) {
                efTableModel.addRow(registroDeEntrega);
            } //fecha for 

            jtblEpiFuncionario.setModel(efTableModel);

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(UIControleEpi.class.getName()).log(Level.SEVERE, null, ex);
        }//fecha catch
    }//fecha filtrarEpiFuncionario

    /**
     * Método usado para filtrar EPIs
     */
    public void filtrarEpi() {
        try {

            String query = "";

            if (!jtfCodEpiFiltro.getText().isEmpty()) {
                query += " AND CODEPI like '%" + jtfCodEpiFiltro.getText() + "%'";
            }

            if (!jtfNomeEpiFiltro.getText().isEmpty()) {
                query += " AND NOME like '%" + jtfNomeEpiFiltro.getText() + "%'";
            }

            ArrayList<Epi> epis = epiService.filtrarEpi(query);

            for (int i = 0; i < epis.size(); i++) {
                eTableModel.addRow(epis.get(i));
            }

            jtblEpi.setModel(eTableModel);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Erro buscar EPI's",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//fecha filtrarEpi

    /**
     * Método usado para pegar funcionário selecionado na tabela
     *
     * @return Funcionario
     */
    public Funcionario getFuncionarioDaLinhaSelecionada() {
        if (jtblFuncionario.getSelectedRow() == -1) {
            return null;
        }

        return fTableModel.getFuncionarios().get(jtblFuncionario.getSelectedRow());

    }//fecha getFuncionarioDaLinhaSelecionada

    public Epi getEpiDaLinhaSelecionada() {
        if (jtblEpi.getSelectedRow() == -1) {
            return null;
        }

        return eTableModel.getEpis().get(jtblEpi.getSelectedRow());

    }//fecha getFuncionarioDaLinhaSelecionada

    public EpiFuncionario getEpiFuncionarioSelecionado() {
        if (jtblEpiFuncionario.getSelectedRow() == -1) {
            return null;
        }

        return efTableModel.getEpiFuncionarios().get(jtblEpiFuncionario.getSelectedRow());

    }//fecha getEpiFuncionarioSelecionado

    /**
     * Método usado para limpar campos do historico
     */
    public void limparCamposHistorico() {
        jtfHColigada.setText("");
        jtfHNomeEpi.setText("");
        jtfHNome.setText("");
        jtfHCa.setText("");
        jftfDataInicio.setText("");
        jftfDataTermino.setText("");
        jchDescontar.setSelected(false);
    }//fecha limparCamposHistorico

    public void atualizarTblHistorico() {
        efTableModel.clear();
        limparCamposHistorico();
        filtrarEpiFuncionario();
    }//atualizarTblHistorico

    private void pesquisarFuncionario() {
        Menu.carregamento(true);

        new Thread() {
            @Override
            public void run() {
                fTableModel.clear();
                filtrarFuncionario();
                Menu.carregamento(false);
            }
        }.start();
    }

    private void pesquisarHistorico() {
        Menu.carregamento(true);

        new Thread() {
            @Override
            public void run() {
                efTableModel.clear();
                filtrarEpiFuncionario();
                Menu.carregamento(false);
            }
        }.start();
    }

    private void pesquisarEpis() {
        Menu.carregamento(true);

        new Thread() {
            @Override
            public void run() {
                eTableModel.clear();
                filtrarEpi();
                Menu.carregamento(false);
            }
        }.start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbAtualizarEpis;
    private javax.swing.JButton jbAtualizarFuncionarios;
    private javax.swing.JButton jbAtualizarHistorico;
    private javax.swing.JButton jbCPesquisar;
    private javax.swing.JButton jbCadAutenticacao;
    private javax.swing.JButton jbFPesquisar;
    private javax.swing.JButton jbHPesquisar;
    private javax.swing.JCheckBox jchAtivos;
    private javax.swing.JCheckBox jchDescontar;
    private javax.swing.JFormattedTextField jftfDataInicio;
    private javax.swing.JFormattedTextField jftfDataTermino;
    private javax.swing.JLabel jlAte;
    private javax.swing.JLabel jlChapa;
    private javax.swing.JLabel jlCodEpiFiltro;
    private javax.swing.JLabel jlColigada;
    private javax.swing.JLabel jlDe;
    private javax.swing.JLabel jlHCa;
    private javax.swing.JLabel jlHChapa;
    private javax.swing.JLabel jlHColigada;
    private javax.swing.JLabel jlHFuncionario;
    private javax.swing.JLabel jlHNomeEpi;
    private javax.swing.JLabel jlNome;
    private javax.swing.JLabel jlNomeEpiFiltro;
    private javax.swing.JPanel jpCadEpi;
    private javax.swing.JPanel jpFuncionarios;
    private javax.swing.JPanel jpHistorico;
    private javax.swing.JTable jtblEpi;
    public static javax.swing.JTable jtblEpiFuncionario;
    public javax.swing.JTable jtblFuncionario;
    private javax.swing.JTextField jtfChapa;
    private javax.swing.JTextField jtfCodEpiFiltro;
    private javax.swing.JTextField jtfColigada;
    private javax.swing.JTextField jtfHCa;
    private javax.swing.JTextField jtfHChapa;
    private javax.swing.JTextField jtfHColigada;
    private javax.swing.JTextField jtfHNome;
    private javax.swing.JTextField jtfHNomeEpi;
    private javax.swing.JTextField jtfNome;
    private javax.swing.JTextField jtfNomeEpiFiltro;
    private javax.swing.JTabbedPane jtpControle;
    // End of variables declaration//GEN-END:variables
}
