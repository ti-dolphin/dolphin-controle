/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DAOFactory;
import dao.MovimentoDAO;
import dao.MovimentoItemDAO;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.ItensMovimentadosTableModel;
import model.Movimento;
import model.MovimentoItem;
import model.MovimentoTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import persistencia.ConexaoBanco;
import utilitarios.ExportaExcel;
import utilitarios.FormatarData;
import utilitarios.os.DateCellRenderer;

/**
 *
 * @author guilherme.oliveira
 */
public class UIControleMovimentos extends javax.swing.JInternalFrame {

    private MovimentoDAO movimentoDAO;
    private MovimentoTableModel movimentoTableModel;
    private ItensMovimentadosTableModel itensMovimentadosTableModel;
    private MovimentoItemDAO movimentoItemDAO;
    private ArrayList<MovimentoItem> itensMovimentados;

    public UIControleMovimentos() {
        initComponents();
        movimentoDAO = DAOFactory.getMOVIMENTODAO();
        movimentoItemDAO = DAOFactory.getMOVIMENTOITEMDAO();
        movimentoTableModel = new MovimentoTableModel();
        itensMovimentadosTableModel = new ItensMovimentadosTableModel();
        jtMovimentos.setModel(movimentoTableModel);
        jtItensMovimentados.setModel(itensMovimentadosTableModel);
        itensMovimentados = new ArrayList<>();
        pesquisarMovimentos();
        pesquisarItensMovimentados();
        configurarTabelaMovimentos();
        configurarTabelaItensMovimentados();
    }

    public MovimentoTableModel getMovimentoTableModel() {
        return movimentoTableModel;
    }

    public ItensMovimentadosTableModel getItensMovimentadosTableModel() {
        return itensMovimentadosTableModel;
    }

    private void configurarTabelaMovimentos() {
        DateCellRenderer dRenderer = new DateCellRenderer();
        
        jtMovimentos.getColumnModel().getColumn(movimentoTableModel.COLUNA_DATA).setCellRenderer(dRenderer);

    }

    private void configurarTabelaItensMovimentados() {
        DateCellRenderer dRenderer = new DateCellRenderer();

        jtItensMovimentados.getColumnModel().getColumn(itensMovimentadosTableModel.COLUNA_DATA).setCellRenderer(dRenderer);
        
        jtItensMovimentados.getColumnModel().getColumn(itensMovimentadosTableModel.COLUNA_ID).setPreferredWidth(50);
        jtItensMovimentados.getColumnModel().getColumn(itensMovimentadosTableModel.COLUNA_DATA).setPreferredWidth(100);
        jtItensMovimentados.getColumnModel().getColumn(itensMovimentadosTableModel.COLUNA_PRODUTO).setPreferredWidth(300);
        jtItensMovimentados.getColumnModel().getColumn(itensMovimentadosTableModel.COLUNA_CODPATRIMONIO).setPreferredWidth(100);
        jtItensMovimentados.getColumnModel().getColumn(itensMovimentadosTableModel.COLUNA_PATRIMONIO).setPreferredWidth(300);
        jtItensMovimentados.getColumnModel().getColumn(itensMovimentadosTableModel.COLUNA_ESTOQUE_ORIGEM).setPreferredWidth(300);
        jtItensMovimentados.getColumnModel().getColumn(itensMovimentadosTableModel.COLUNA_ESTOQUE_DESTINO).setPreferredWidth(300);
        jtItensMovimentados.getColumnModel().getColumn(itensMovimentadosTableModel.COLUNA_RESPONSAVEL).setPreferredWidth(300);
    }

    private Movimento pegarMovimentoSelecionado() {
        if (jtMovimentos.getSelectedRow() == -1) {
            return null;
        }

        return movimentoTableModel.getMovimentos().get(jtMovimentos.getSelectedRow());
    }
    
    private void abrirTelaEditar() {
        Movimento movimento = pegarMovimentoSelecionado();
        if (movimento != null) {
            new UICadMovimento(this, movimento).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione o movimento para editar!",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public void atualizarNumeroDeRegistros() {
        jlNumeroRegistros.setText("N° de Registros: " + itensMovimentadosTableModel.getItens().size());
    }

    private void filtrarItensMovimentados() {
        try {
            String query = " where MOVIMENTO_ITEM.ID > 0";

            if (!jtfProduto.getText().isEmpty()) {
                query = query + " and PRODUTOS.NOMEFANTASIA like '%" + jtfProduto.getText() + "%'";
            }
            if (!jtfPatrimonio.getText().isEmpty()) {
                query = query + " and PATRIMONIOS.DESCRICAO like '%" + jtfPatrimonio.getText() + "%'"
                        + " or PATRIMONIOS.PATRIMONIO like '%" + jtfPatrimonio.getText() + "%'";
            }
            if (!jtfEstoqueOrigem2.getText().isEmpty()) {
                query = query + " and ESTOQUE_ORIGEM.NOME like '%" + jtfEstoqueOrigem2.getText() + "%'";
            }
            if (!jtfEstoqueDestino2.getText().isEmpty()) {
                query = query + " and ESTOQUE_DESTINO.NOME like '%" + jtfEstoqueDestino2.getText() + "%'";
            }
            if (!jtfResponsavel2.getText().isEmpty()) {
                query = query + " and PESSOA.NOME like '%" + jtfResponsavel2.getText() + "%'";
            }
            if (!jftfDataInicio2.getText().equals("  /  /    ") && !jftfDataTermino2.getText().equals("  /  /    ")) {
                String dataInicio = FormatarData.formatarData(jftfDataInicio2.getText());
                String dataTermino = FormatarData.formatarData(jftfDataTermino2.getText());
                query = query + " and MOVIMENTO_PRODUTOS.DATAENTREGA BETWEEN ('" + dataInicio + "')"
                        + " and ('" + dataTermino + "')";
            }

            itensMovimentados = movimentoItemDAO.filtrar(query);
            for (MovimentoItem item : itensMovimentados) {
                itensMovimentadosTableModel.addRow(item);
            }
            
            atualizarNumeroDeRegistros();

        } catch (Exception e) {
            Logger.getLogger(UIControleMovimentos.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtrarMovimentos() {
        try {
            String query = " where MOVIMENTO_PRODUTOS.ID > 0";

            if (!jtfEstoqueOrigem.getText().isEmpty()) {
                query = query + " and ESTOQUE_ORIGEM.NOME like '%" + jtfEstoqueOrigem.getText() + "%'";
            }
            if (!jtfEstoqueDestino.getText().isEmpty()) {
                query = query + " and ESTOQUE_DESTINO.NOME like '%" + jtfEstoqueDestino.getText() + "%'";
            }
            if (!jtfResponsavel.getText().isEmpty()) {
                query = query + " and PESSOA.NOME like '%" + jtfResponsavel.getText() + "%'";
            }
            if (!jftfDataInicio.getText().equals("  /  /    ") && !jftfDataTermino.getText().equals("  /  /    ")) {
                String dataInicio = FormatarData.formatarData(jftfDataInicio.getText());
                String dataTermino = FormatarData.formatarData(jftfDataTermino.getText());
                query = query + " and MOVIMENTO_PRODUTOS.DATAENTREGA BETWEEN ('" + dataInicio + "')"
                        + " and ('" + dataTermino + "')";
            }

            for (Movimento movimento : movimentoDAO.filtrar(query)) {
                movimentoTableModel.addRow(movimento);
            }

        } catch (Exception e) {
            Logger.getLogger(UIControleMovimentos.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void pesquisarMovimentos() {
        Menu.carregamento(true);
        new Thread(() -> {
            movimentoTableModel.clear();
            filtrarMovimentos();
            Menu.carregamento(false);
        }).start();
    }

    public void pesquisarItensMovimentados() {
        Menu.carregamento(true);
        new Thread(() -> {
            itensMovimentadosTableModel.clear();
            filtrarItensMovimentados();
            Menu.carregamento(false);
        }).start();
    }

    private void limparCamposMovimentos() {
        jtfEstoqueOrigem.setText("");
        jtfEstoqueDestino.setText("");
        jtfResponsavel.setText("");
        jftfDataInicio.setText("");
        jftfDataTermino.setText("");
    }

    private void limparCamposItens() {
        jtfProduto.setText("");
        jtfPatrimonio.setText("");
        jtfEstoqueOrigem2.setText("");
        jtfEstoqueDestino2.setText("");
        jtfResponsavel2.setText("");
        jftfDataInicio2.setText("");
        jftfDataTermino2.setText("");
    }

    private void gerarRelatorioItensMovimentados() {

        Menu.carregamento(true);

        new Thread(() -> {

            try {
                String path = System.getProperty("java.io.tmpdir") + "\\rel-itens-movimentados.xls";
                ExportaExcel excel = new ExportaExcel(path);

                excel.exportarItensMovimentados(itensMovimentados);

                File file = new File(path);
                Desktop.getDesktop().open(file);

            } catch (IOException ex) {
                Logger.getLogger(UIControleMovimentos.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                Logger.getLogger(UIControleMovimentos.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
            }

            Menu.carregamento(false);

        }).start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtMovimentos = new javax.swing.JTable();
        jbNovo = new javax.swing.JButton();
        jbEditar = new javax.swing.JButton();
        jbPesquisar = new javax.swing.JButton();
        jbLimparFiltros = new javax.swing.JButton();
        jlLocalDeEstoqueOrigem = new javax.swing.JLabel();
        jlLocalDeEstoqueDestino = new javax.swing.JLabel();
        jlResponsavel = new javax.swing.JLabel();
        jlDataInicio = new javax.swing.JLabel();
        jlDataTermino = new javax.swing.JLabel();
        jftfDataInicio = new javax.swing.JFormattedTextField();
        jftfDataTermino = new javax.swing.JFormattedTextField();
        jtfResponsavel = new javax.swing.JTextField();
        jtfEstoqueDestino = new javax.swing.JTextField();
        jtfEstoqueOrigem = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtItensMovimentados = new javax.swing.JTable();
        jlLocalDeEstoqueOrigem2 = new javax.swing.JLabel();
        jtfEstoqueOrigem2 = new javax.swing.JTextField();
        jbPesquisarItens = new javax.swing.JButton();
        jbLimparFiltrosItens = new javax.swing.JButton();
        jtfEstoqueDestino2 = new javax.swing.JTextField();
        jtfResponsavel2 = new javax.swing.JTextField();
        jlLocalDeEstoqueDestino2 = new javax.swing.JLabel();
        jlResponsavel2 = new javax.swing.JLabel();
        jlDataInicio2 = new javax.swing.JLabel();
        jlDataTermino2 = new javax.swing.JLabel();
        jftfDataInicio2 = new javax.swing.JFormattedTextField();
        jftfDataTermino2 = new javax.swing.JFormattedTextField();
        jtfProduto = new javax.swing.JTextField();
        jtfPatrimonio = new javax.swing.JTextField();
        jlProduto = new javax.swing.JLabel();
        jlPatrimonio = new javax.swing.JLabel();
        jlNumeroRegistros = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jmiRomaneio = new javax.swing.JMenuItem();
        jmCadastro = new javax.swing.JMenu();
        jmiPatrimonio = new javax.swing.JMenuItem();

        setClosable(true);
        setIconifiable(true);
        setTitle("Controle de Produtos");

        jtMovimentos.setModel(new javax.swing.table.DefaultTableModel(
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
        jtMovimentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtMovimentosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtMovimentos);

        jbNovo.setText("Novo");
        jbNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNovoActionPerformed(evt);
            }
        });

        jbEditar.setText("Editar");
        jbEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEditarActionPerformed(evt);
            }
        });

        jbPesquisar.setText("Pesquisar");
        jbPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPesquisarActionPerformed(evt);
            }
        });

        jbLimparFiltros.setText("Limpar Filtros");
        jbLimparFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimparFiltrosActionPerformed(evt);
            }
        });

        jlLocalDeEstoqueOrigem.setText("Estoque Origem");

        jlLocalDeEstoqueDestino.setText("Estoque Destino");

        jlResponsavel.setText("Responsável");

        jlDataInicio.setText("De");

        jlDataTermino.setText("Até");

        try {
            jftfDataInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfDataInicio.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        jftfDataInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfDataInicioKeyPressed(evt);
            }
        });

        try {
            jftfDataTermino.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfDataTermino.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        jftfDataTermino.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfDataTerminoKeyPressed(evt);
            }
        });

        jtfResponsavel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfResponsavelKeyPressed(evt);
            }
        });

        jtfEstoqueDestino.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfEstoqueDestinoKeyPressed(evt);
            }
        });

        jtfEstoqueOrigem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfEstoqueOrigemKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlResponsavel)
                            .addComponent(jtfResponsavel, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfEstoqueOrigem, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addComponent(jlLocalDeEstoqueOrigem))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfEstoqueDestino, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                            .addComponent(jlLocalDeEstoqueDestino))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jftfDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlDataInicio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlDataTermino)
                            .addComponent(jftfDataTermino, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addComponent(jbLimparFiltros)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbPesquisar)
                        .addGap(18, 18, 18)
                        .addComponent(jbEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlLocalDeEstoqueOrigem)
                    .addComponent(jlLocalDeEstoqueDestino)
                    .addComponent(jlDataInicio)
                    .addComponent(jlResponsavel)
                    .addComponent(jlDataTermino))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbPesquisar)
                    .addComponent(jtfEstoqueDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfEstoqueOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftfDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbEditar)
                    .addComponent(jbLimparFiltros)
                    .addComponent(jbNovo)
                    .addComponent(jftfDataTermino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Movimentos", jPanel2);

        jtItensMovimentados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jtItensMovimentados);

        jlLocalDeEstoqueOrigem2.setText("Estoque Origem");

        jtfEstoqueOrigem2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfEstoqueOrigem2KeyPressed(evt);
            }
        });

        jbPesquisarItens.setText("Pesquisar");
        jbPesquisarItens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPesquisarItensActionPerformed(evt);
            }
        });

        jbLimparFiltrosItens.setText("Limpar Filtros");
        jbLimparFiltrosItens.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimparFiltrosItensActionPerformed(evt);
            }
        });

        jtfEstoqueDestino2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfEstoqueDestino2KeyPressed(evt);
            }
        });

        jtfResponsavel2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfResponsavel2KeyPressed(evt);
            }
        });

        jlLocalDeEstoqueDestino2.setText("Estoque Destino");

        jlResponsavel2.setText("Responsável");

        jlDataInicio2.setText("De");

        jlDataTermino2.setText("Até");

        try {
            jftfDataInicio2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfDataInicio2.setFocusLostBehavior(javax.swing.JFormattedTextField.COMMIT);
        jftfDataInicio2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfDataInicio2KeyPressed(evt);
            }
        });

        try {
            jftfDataTermino2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfDataTermino2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfDataTermino2KeyPressed(evt);
            }
        });

        jtfProduto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfProdutoKeyPressed(evt);
            }
        });

        jtfPatrimonio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfPatrimonioKeyPressed(evt);
            }
        });

        jlProduto.setText("Produto");

        jlPatrimonio.setText("Patrimônio");

        jlNumeroRegistros.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jlNumeroRegistros.setText("N° Registros: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlProduto)
                            .addComponent(jtfProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfPatrimonio, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jlPatrimonio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfEstoqueOrigem2, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jlLocalDeEstoqueOrigem2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlLocalDeEstoqueDestino2)
                            .addComponent(jtfEstoqueDestino2, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfResponsavel2, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                            .addComponent(jlResponsavel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jftfDataInicio2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlDataInicio2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlDataTermino2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jftfDataTermino2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(jbLimparFiltrosItens, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbPesquisarItens, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlNumeroRegistros)
                        .addGap(0, 990, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlProduto)
                    .addComponent(jlPatrimonio)
                    .addComponent(jlDataInicio2)
                    .addComponent(jlDataTermino2)
                    .addComponent(jlLocalDeEstoqueOrigem2)
                    .addComponent(jlLocalDeEstoqueDestino2)
                    .addComponent(jlResponsavel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfPatrimonio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftfDataInicio2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftfDataTermino2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfEstoqueOrigem2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfEstoqueDestino2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfResponsavel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbPesquisarItens)
                    .addComponent(jbLimparFiltrosItens))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlNumeroRegistros)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Itens Movimentados", jPanel1);

        jMenu1.setText("Relatórios");

        jMenuItem1.setText("Itens Movimentados");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jmiRomaneio.setText("Romaneio");
        jmiRomaneio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiRomaneioActionPerformed(evt);
            }
        });
        jMenu1.add(jmiRomaneio);

        jMenuBar1.add(jMenu1);

        jmCadastro.setText("Cadastro");

        jmiPatrimonio.setText("Patrimonio");
        jmiPatrimonio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiPatrimonioActionPerformed(evt);
            }
        });
        jmCadastro.add(jmiPatrimonio);

        jMenuBar1.add(jmCadastro);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNovoActionPerformed
        new UICadMovimento(this, null).setVisible(true);
    }//GEN-LAST:event_jbNovoActionPerformed

    private void jbEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEditarActionPerformed
        abrirTelaEditar();
    }//GEN-LAST:event_jbEditarActionPerformed

    private void jtMovimentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtMovimentosMouseClicked
        if (evt.getClickCount() == 2) {
            abrirTelaEditar();
        }
    }//GEN-LAST:event_jtMovimentosMouseClicked

    private void jbPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPesquisarActionPerformed
        pesquisarMovimentos();
    }//GEN-LAST:event_jbPesquisarActionPerformed

    private void jtfEstoqueOrigemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfEstoqueOrigemKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarMovimentos();
        }
    }//GEN-LAST:event_jtfEstoqueOrigemKeyPressed

    private void jtfEstoqueDestinoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfEstoqueDestinoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarMovimentos();
        }
    }//GEN-LAST:event_jtfEstoqueDestinoKeyPressed

    private void jtfResponsavelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfResponsavelKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarMovimentos();
        }
    }//GEN-LAST:event_jtfResponsavelKeyPressed

    private void jftfDataTerminoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfDataTerminoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarMovimentos();
        }
    }//GEN-LAST:event_jftfDataTerminoKeyPressed

    private void jbLimparFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparFiltrosActionPerformed
        limparCamposMovimentos();
        pesquisarMovimentos();
    }//GEN-LAST:event_jbLimparFiltrosActionPerformed

    private void jbPesquisarItensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPesquisarItensActionPerformed
        pesquisarItensMovimentados();
    }//GEN-LAST:event_jbPesquisarItensActionPerformed

    private void jtfProdutoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfProdutoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarItensMovimentados();
        }
    }//GEN-LAST:event_jtfProdutoKeyPressed

    private void jtfPatrimonioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPatrimonioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarItensMovimentados();
        }
    }//GEN-LAST:event_jtfPatrimonioKeyPressed

    private void jtfResponsavel2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfResponsavel2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarItensMovimentados();
        }
    }//GEN-LAST:event_jtfResponsavel2KeyPressed

    private void jtfEstoqueOrigem2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfEstoqueOrigem2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarItensMovimentados();
        }
    }//GEN-LAST:event_jtfEstoqueOrigem2KeyPressed

    private void jtfEstoqueDestino2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfEstoqueDestino2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarItensMovimentados();
        }
    }//GEN-LAST:event_jtfEstoqueDestino2KeyPressed

    private void jftfDataInicio2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfDataInicio2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarItensMovimentados();
        }
    }//GEN-LAST:event_jftfDataInicio2KeyPressed

    private void jftfDataTermino2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfDataTermino2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarItensMovimentados();
        }
    }//GEN-LAST:event_jftfDataTermino2KeyPressed

    private void jbLimparFiltrosItensActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparFiltrosItensActionPerformed
        limparCamposItens();
        pesquisarItensMovimentados();
    }//GEN-LAST:event_jbLimparFiltrosItensActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        gerarRelatorioItensMovimentados();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jftfDataInicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfDataInicioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pesquisarMovimentos();
        }
    }//GEN-LAST:event_jftfDataInicioKeyPressed

    private void jmiRomaneioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiRomaneioActionPerformed
        Movimento movimento = pegarMovimentoSelecionado();

        if (movimento != null) {

            Menu.carregamento(true);
            new Thread(() -> {

                try {
                    Connection con = ConexaoBanco.getConexao();

                    String caminhoCorrente = new File("").getAbsolutePath();

                    String caminhoDoRelatorio = caminhoCorrente + "/relatorios/rel-romaneio.jasper";

                    JasperPrint jasperPrint;

                    String caminhoDaImagem = caminhoCorrente + "/img/dse-logo-relatorio.png";

                    HashMap filtro = new HashMap();
                    filtro.put("imagem", caminhoDaImagem);
                    filtro.put("id_movimento", movimento.getId());

                    jasperPrint = JasperFillManager.fillReport(caminhoDoRelatorio, filtro, con);

                    JasperViewer view = new JasperViewer(jasperPrint, false);
                    view.setVisible(true);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "" + ex.getMessage(),
                            "Erro ao carregar dados", JOptionPane.ERROR_MESSAGE);
                } catch (JRException ex) {
                    JOptionPane.showMessageDialog(null, "" + ex.getMessage(),
                            "Erro ao gerar relatório", JOptionPane.ERROR_MESSAGE);
                }
                Menu.carregamento(false);

            }).start();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um movimento! ",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jmiRomaneioActionPerformed

    private void jmiPatrimonioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiPatrimonioActionPerformed
        new UIManPatrimonio().setVisible(true);
    }//GEN-LAST:event_jmiPatrimonioActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbEditar;
    private javax.swing.JButton jbLimparFiltros;
    private javax.swing.JButton jbLimparFiltrosItens;
    private javax.swing.JButton jbNovo;
    private javax.swing.JButton jbPesquisar;
    private javax.swing.JButton jbPesquisarItens;
    private javax.swing.JFormattedTextField jftfDataInicio;
    private javax.swing.JFormattedTextField jftfDataInicio2;
    private javax.swing.JFormattedTextField jftfDataTermino;
    private javax.swing.JFormattedTextField jftfDataTermino2;
    private javax.swing.JLabel jlDataInicio;
    private javax.swing.JLabel jlDataInicio2;
    private javax.swing.JLabel jlDataTermino;
    private javax.swing.JLabel jlDataTermino2;
    private javax.swing.JLabel jlLocalDeEstoqueDestino;
    private javax.swing.JLabel jlLocalDeEstoqueDestino2;
    private javax.swing.JLabel jlLocalDeEstoqueOrigem;
    private javax.swing.JLabel jlLocalDeEstoqueOrigem2;
    private javax.swing.JLabel jlNumeroRegistros;
    private javax.swing.JLabel jlPatrimonio;
    private javax.swing.JLabel jlProduto;
    private javax.swing.JLabel jlResponsavel;
    private javax.swing.JLabel jlResponsavel2;
    private javax.swing.JMenu jmCadastro;
    private javax.swing.JMenuItem jmiPatrimonio;
    private javax.swing.JMenuItem jmiRomaneio;
    private javax.swing.JTable jtItensMovimentados;
    private javax.swing.JTable jtMovimentos;
    private javax.swing.JTextField jtfEstoqueDestino;
    private javax.swing.JTextField jtfEstoqueDestino2;
    private javax.swing.JTextField jtfEstoqueOrigem;
    private javax.swing.JTextField jtfEstoqueOrigem2;
    private javax.swing.JTextField jtfPatrimonio;
    private javax.swing.JTextField jtfProduto;
    private javax.swing.JTextField jtfResponsavel;
    private javax.swing.JTextField jtfResponsavel2;
    // End of variables declaration//GEN-END:variables

}
