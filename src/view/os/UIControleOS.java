/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.os;

import dao.DAOFactory;
import dao.os.OrdemServicoDAO;
import dao.os.PessoaDAO;
import dao.os.PessoaResponsavelDAO;
import dao.os.PessoaTipoDAO;
import dao.os.StatusDAO;
import dao.os.TipoOsDAO;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.TableRowSorter;
import model.os.OrdemServico;
import model.os.OrdemServicoTableModel;
import model.os.Pessoa;
import model.os.PessoaResponsavel;
import model.os.PessoaTipo;
import model.os.Status;
import model.os.TipoOs;
import utilitarios.ExportaExcel;
import utilitarios.FormatarData;
import utilitarios.IconRender;
import utilitarios.os.CurrencyTableCellRenderer;
import utilitarios.os.DateCellRenderer;
import view.Menu;

/**
 *
 * @author guilherme.oliveira
 */
public class UIControleOS extends javax.swing.JInternalFrame {

    private OrdemServicoTableModel osTableModel = new OrdemServicoTableModel();
    private Menu menu;
    private boolean flagUICadOS = false;
    private boolean flagUIManStatus = false;
    private boolean flagUIManTipo = false;
    private boolean flagUIComentarios;
    private ArrayList<OrdemServico> tarefas;
    private OrdemServicoDAO osdao;
    private String listaStatus;

    /**
     * Creates new form UIControleOS
     *
     * @param menu
     */
    public UIControleOS(Menu menu) {
        initComponents();
        this.menu = menu;
        this.osdao = new OrdemServicoDAO();
        darPermissoes();
        jtOrdensServico.setModel(osTableModel);
        preencherComboBoxTipo();
        preencherComboBoxGerentes();
        preencherComboBoxSolicitante();
        preencherComboBoxResponsaveis();
        preencherComboBoxStatus();
        filtrarOS();
        configuraTabela();
        clickCabecalho();
    }

    public void setListaStatus(String listaStatus) {
        this.listaStatus = listaStatus;
    }

    public ArrayList<OrdemServico> getTarefas() {
        return tarefas;
    }

    public boolean isFlagUIComentarios() {
        return flagUIComentarios;
    }

    public void setFlagUIComentarios(boolean flagUIComentarios) {
        this.flagUIComentarios = flagUIComentarios;
    }

    public JComboBox<Object> getJcbTipoFiltro() {
        return jcbTipoFiltro;
    }

    public OrdemServicoTableModel getOsTableModel() {
        return osTableModel;
    }

    public Menu getMenu() {
        return menu;
    }

    public boolean isFlagUICadOS() {
        return flagUICadOS;
    }

    public void setFlagUICadOS(boolean flagUICadOS) {
        this.flagUICadOS = flagUICadOS;
    }

    public boolean isFlagUIManTipo() {
        return flagUIManTipo;
    }

    public void setFlagUIManTipo(boolean flagUIManTipo) {
        this.flagUIManTipo = flagUIManTipo;
    }

    public boolean isFlagUIManStatus() {
        return flagUIManStatus;
    }

    public void setFlagUIManStatus(boolean flagUIManStatus) {
        this.flagUIManStatus = flagUIManStatus;
    }

    public JLabel getJlTotalFaturamentoDireto() {
        return jlTotalFaturamentoDireto;
    }

    public JLabel getJlValorTotal() {
        return jlValorTotal;
    }

    public JLabel getJlValorTotalFaturamentoDolphin() {
        return jlValorTotalFaturamentoDolphin;
    }

    public JLabel getJlNumeroRegistros() {
        return jlNumeroRegistros;
    }

    public void darPermissoes() {
        if (Menu.logado.isPermTipo()) {
            jmiManterTipo.setEnabled(true);
        }
        if (Menu.logado.isPermStatus()) {
            jmiManterStatus.setEnabled(true);
        }
        if (Menu.logado.isPermComentOs()) {
            jbComentarios.setEnabled(true);
        }
        if (!Menu.logado.isPermVenda()) {
            jlTotalFaturamentoDireto.setVisible(false);
            jlValorTotal.setVisible(false);
            jlValorTotalFaturamentoDolphin.setVisible(false);
            jmiRelatorioVendas.setEnabled(false);
        }
    }

    private void configuraTabela() {
        jtOrdensServico.setRowSorter(new TableRowSorter<>(osTableModel));

        DateCellRenderer dRenderer = new DateCellRenderer();
        CurrencyTableCellRenderer cRenderer = new CurrencyTableCellRenderer();

        IconRender iconRender = new IconRender(jtOrdensServico, osTableModel.COLUNA_STATUS_INTERACAO);

        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_DATA_ENTREGA).setCellRenderer(dRenderer);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_DATA_INTERACAO).setCellRenderer(dRenderer);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_DATA_INICIO).setCellRenderer(dRenderer);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_DATA_NECESSIDADE).setCellRenderer(dRenderer);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_DATA_PREVISAO_ENTREGA).setCellRenderer(dRenderer);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_DATA_SOLICITACAO).setCellRenderer(dRenderer);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_VALOR_TOTAL).setCellRenderer(cRenderer);

        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_STATUS_INTERACAO).setCellRenderer(iconRender);

        jtOrdensServico.setAutoResizeMode(jtOrdensServico.AUTO_RESIZE_OFF);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_NUMERO_OS).setPreferredWidth(40);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_STATUS_INTERACAO).setPreferredWidth(40);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_DATA_INTERACAO).setPreferredWidth(110);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_PROJETO).setPreferredWidth(60);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_ADICIONAL).setPreferredWidth(60);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_STATUS).setPreferredWidth(120);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_TAREFA).setPreferredWidth(200);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_CLIENTE).setPreferredWidth(300);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_CENTRO_CUSTO).setPreferredWidth(300);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_VALOR_TOTAL).setPreferredWidth(150);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_DATA_SOLICITACAO).setPreferredWidth(85);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_DATA_NECESSIDADE).setPreferredWidth(85);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_DATA_INICIO).setPreferredWidth(85);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_DATA_PREVISAO_ENTREGA).setPreferredWidth(130);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_DATA_ENTREGA).setPreferredWidth(130);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_RESPONSAVEL).setPreferredWidth(300);
        jtOrdensServico.getColumnModel().getColumn(osTableModel.COLUNA_CLASSIFICACAO_ABC).setPreferredWidth(60);
    }

    private void preencherComboBoxStatus() {
        StatusDAO dao = DAOFactory.getSTATUSDAO();

        try {
            for (Status status : dao.buscarComboCadOs()) {
                jcbStatus.addItem(status);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao carregar combobox status da OS",
                    JOptionPane.ERROR_MESSAGE);
        }//fecha catch
    }//fecha preencherCombo

    /**
     * Método usado para preencher combobox do tipo de OS
     */
    public void preencherComboBoxTipo() {
        TipoOsDAO dao = DAOFactory.getTIPOOSDAO();

        try {
            for (TipoOs t : dao.buscarTiposCombo(Menu.logado)) {
                jcbTipoFiltro.addItem(t);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao preencher combobox do tipo",
                    JOptionPane.ERROR_MESSAGE);
        }//fecha catch
    }//fecha preencherCombo

    /**
     * Método usado para preencher combobox do responsaveis da OS
     */
    public void preencherComboBoxResponsaveis() {
        PessoaDAO dao = DAOFactory.getPESSOADAO();

        try {
            for (Pessoa pessoa : dao.buscarPessoasCombo("responsavel")) {
                jcbResponsavel.addItem(pessoa);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao preencher combobox",
                    JOptionPane.ERROR_MESSAGE);
        }//fecha catch
    }//fecha preencherCombo

    public void preencherComboBoxGerentes() {
        PessoaDAO dao = DAOFactory.getPESSOADAO();

        try {
            for (Pessoa pessoa : dao.buscarPessoasCombo("gerente")) {
                jcbGerente.addItem(pessoa);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao preencher combobox",
                    JOptionPane.ERROR_MESSAGE);
        }//fecha catch
    }//fecha preencherCombo

    /**
     * Método usado para preencher combobox de solicitante da OS
     */
    public void preencherComboBoxSolicitante() {
        PessoaDAO dao = DAOFactory.getPESSOADAO();

        try {
            for (Pessoa pessoa : dao.buscarPessoasCombo("solicitante")) {
                jcbSolicitante.addItem(pessoa);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao preencher combobox",
                    JOptionPane.ERROR_MESSAGE);
        }//fecha catch
    }//fecha preencherCombo

    /**
     * Método usado para retornar objeto ordem de servico da linha selecionada
     *
     * @return OrdemServico
     */
    public OrdemServico getOrdemServicoSelecionada() {
        if (jtOrdensServico.getSelectedRow() > -1) {
            int linhaSelecionada = jtOrdensServico.getRowSorter().convertRowIndexToModel(jtOrdensServico.getSelectedRow());
            return osTableModel.getRowValue(linhaSelecionada);
        } else {
            return null;
        }
    }//fecha getOrdemServicoSelecionada

    /**
     * Método para filtrar OSs
     */
    public void filtrarOS() {

        Menu.carregamento(true);

        new Thread() {
            @Override
            public void run() {
                try {
                    OrdemServicoDAO dao = DAOFactory.getORDEMSERVICODAO();
                    PessoaTipoDAO pessoaTipoDAO = DAOFactory.getPESSOATIPODAO();
                    PessoaResponsavelDAO pessoaResponsavelDAO = DAOFactory.getPESSOARESPONSAVELDAO();

                    ArrayList<PessoaTipo> pTipos = pessoaTipoDAO.buscarTiposPorPessoa(Menu.logado);
                    ArrayList<PessoaResponsavel> pResponsaveis = pessoaResponsavelDAO.buscar(Menu.logado);

                    TipoOs tipoos = (TipoOs) jcbTipoFiltro.getSelectedItem();
                    Pessoa solicitante = (Pessoa) jcbSolicitante.getSelectedItem();
                    Pessoa responsavel = (Pessoa) jcbResponsavel.getSelectedItem();
                    Pessoa gerente = (Pessoa) jcbGerente.getSelectedItem();
                    Status status = (Status) jcbStatus.getSelectedItem();

                    String codigosDosTipos = "0";
                    for (PessoaTipo pessoaTipo : pTipos) {
                        codigosDosTipos += "," + pessoaTipo.getTipo().getCodTipoOs();
                    }

                    String codigosDosResponsaveis = "0";
                    for (PessoaResponsavel pessoaResponsavel : pResponsaveis) {
                        codigosDosResponsaveis += "," + pessoaResponsavel.getResponsavel().getCodPessoa();
                    }

                    String query = " and OS.CODTIPOOS IN (" + codigosDosTipos + ")"
                            + " and OS.RESPONSAVEL IN (" + codigosDosResponsaveis + ")";

                    if (!jtfNumOsFiltro.getText().isEmpty()) {
                        query = query + " and OS.CODOS = " + jtfNumOsFiltro.getText();
                    }

                    if (!jtfNome.getText().isEmpty()) {
                        query = query + " and OS.NOME LIKE '%" + jtfNome.getText() + "%'";
                    }

                    if (jcbTipoFiltro.getSelectedIndex() != 0) {
                        query = query + " and OS.CODTIPOOS = " + tipoos.getCodTipoOs();
                    }

                    if (jcbSolicitante.getSelectedIndex() != 0) {
                        query = query + " and OS.SOLICITANTE = " + solicitante.getCodPessoa();
                    }

                    if (jcbResponsavel.getSelectedIndex() != 0) {
                        query = query + " and PE.CODPESSOA = " + responsavel.getCodPessoa();
                    }

                    if (jcbGerente.getSelectedIndex() != 0) {
                        query = query + " and PG.CODPESSOA = " + gerente.getCodPessoa();
                    }

                    if (!jtfCentroCusto.getText().isEmpty()) {
                        query = query + " and C.NOME like '%" + jtfCentroCusto.getText() + "%'";
                    }

                    if (!jtfCliente.getText().isEmpty()) {
                        query = query + " and CLI.NOMEFANTASIA like '%" + jtfCliente.getText() + "%'";
                    }

                    if (!jchAguardando.isSelected() && !jchFinalizados.isSelected()) {
                        query = query + " and S.ACAO = 0";
                    }

                    if (jchListarAdicionais.isSelected()) {
                        query = query + " and A.NUMERO > 0";
                    }

                    if (jchAguardando.isSelected() && jchFinalizados.isSelected()) {
                        query = query + " and S.ACAO in (0, 1, 2)";
                    } else {
                        if (jchFinalizados.isSelected()) {
                            query = query + " and S.ACAO in (1, 0)";
                        }

                        if (jchAguardando.isSelected()) {
                            query = query + " and S.ACAO in (2, 0)";
                        }
                    }

                    if (jcbStatus.getSelectedIndex() != 0) {
                        query = query + " and S.CODSTATUS = " + status.getCodStatus();
                    }

                    if (listaStatus != null) {
                        query = query + " and S.CODSTATUS in (" + listaStatus + ")";
                    }

                    if (!jtfProjeto.getText().isEmpty()) {
                        query = query + " and OS.ID_PROJETO = " + jtfProjeto.getText();
                    }

                    if (chSomentePrincipais.isSelected()) {
                        query = query + " and OS.PRINCIPAL = true";
                    }

                    if (!jftfDataFechamentoDe.getText().equals("  /  /    ") && !jftfDataFechamentoAte.getText().equals("  /  /    ")) {

                        String dataFechamentoDe = FormatarData.formatarData(jftfDataFechamentoDe.getText());
                        String dataFechamentoAte = FormatarData.formatarData(jftfDataFechamentoAte.getText());

                        query = query + " and ((OS.DATAENTREGA BETWEEN ('" + dataFechamentoDe + "')"
                                + " and ('" + dataFechamentoAte + "'))"
                                + " OR (DATAENTREGA IS NULL and OS.DATAPREVENTREGA BETWEEN ('" + dataFechamentoDe + "')"
                                + " and ('" + dataFechamentoAte + "')))";
                    }

                    if (!jftfInicioDe.getText().equals("  /  /    ") && !jftfInicioAte.getText().equals("  /  /    ")) {

                        String dataInicioDe = FormatarData.formatarData(jftfInicioDe.getText());
                        String dataInicioAte = FormatarData.formatarData(jftfInicioAte.getText());

                        query = query + " AND OS.DATAINICIO BETWEEN ('" + dataInicioDe + "') and ('" + dataInicioAte + "')";
                    }
                    
                    if (!jftfInteracaoDe.getText().equals("  /  /    ") && !jftfInteracaoAte.getText().equals("  /  /    ")) {

                        String dataInteracaoDe = FormatarData.formatarData(jftfInteracaoDe.getText());
                        String dataInteracaoAte = FormatarData.formatarData(jftfInteracaoAte.getText());

                        query = query + " AND OS.DATAINTERACAO BETWEEN ('" + dataInteracaoDe + "') and ('" + dataInteracaoAte + "')";
                    }

                    UIControleOS.this.tarefas = dao.filtrarOrdemServico(query);

                    osTableModel.clear();

                    int numeroRegistros = 0;
                    double totalFaturamentoDireto = 0.0;
                    double totalFaturamentoDolphin = 0.0;
                    double total = 0;

                    for (OrdemServico ordemServico : tarefas) {
                        osTableModel.addRow(ordemServico);
                        numeroRegistros += 1;
                        totalFaturamentoDireto += ordemServico.getVenda().getValorFaturamentoDireto();
                        totalFaturamentoDolphin += ordemServico.getVenda().getValorFaturamentoDolphin();
                        total += ordemServico.getVenda().calcularValorTotal();
                    }//fecha for

                    getJlNumeroRegistros().setText("N° de Registros: " + numeroRegistros);
                    getJlTotalFaturamentoDireto().setText("Faturamento Direto: " + NumberFormat.getCurrencyInstance().format(totalFaturamentoDireto));
                    getJlValorTotal().setText("Total: " + NumberFormat.getCurrencyInstance().format(total));
                    getJlValorTotalFaturamentoDolphin().setText("Faturamento Dolphin: "
                            + NumberFormat
                                    .getCurrencyInstance()
                                    .format(totalFaturamentoDolphin));
                    
                    jtOrdensServico.setModel(osTableModel);

                    if (tarefas.isEmpty()) {
                        JOptionPane.showMessageDialog(UIControleOS.this, "Nenhum registro encontrado");
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(UIControleOS.this, e.getMessage(),
                            "Erro ao filtrar OS", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(UIControleOS.this, ex.getMessage(),
                            "Erro ao filtrar OS", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(UIControleOS.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    Menu.carregamento(false);
                }//finally
            }//run
        }.start();
    }//fecha filtrarOS

    private void limpar() {
        jtfNumOsFiltro.setText("");
        jtfNome.setText("");
        jcbStatus.setSelectedIndex(0);
        jcbSolicitante.setSelectedIndex(0);
        jcbTipoFiltro.setSelectedIndex(0);
        jcbResponsavel.setSelectedIndex(0);
        jcbGerente.setSelectedIndex(0);
        jtfCentroCusto.setText("");
        jchFinalizados.setSelected(false);
        jchAguardando.setSelected(false);
        jtfCliente.setText("");
        jftfDataFechamentoDe.setText("");
        jftfDataFechamentoAte.setText("");
        jftfInicioDe.setText("");
        jftfInicioAte.setText("");
        jftfInteracaoDe.setText("");
        jftfInteracaoAte.setText("");
        listaStatus = null;
        jchListarAdicionais.setSelected(false);
        jtfProjeto.setText("");
    }//limpar

    /**
     * Método usado para excluir os
     *
     * @param ordemServico
     */
    public void excluir(OrdemServico ordemServico) {
        try {

            OrdemServicoDAO dao = DAOFactory.getORDEMSERVICODAO();
            dao.excluir(ordemServico);
            osTableModel.removeRow(ordemServico);
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao excluir ordem de serviço",
                    JOptionPane.ERROR_MESSAGE);
        }//fecha catch
    }//fecha excluir

    private void clickCabecalho() {
        // listener
        jtOrdensServico.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == e.BUTTON3) {

                    try {
                        int col = jtOrdensServico.columnAtPoint(e.getPoint());
                        String nomeDaColuna = jtOrdensServico.getColumnName(col);

                        if (nomeDaColuna.equals("Status")) {
                            StatusFiltro statusFiltro = new StatusFiltro(menu, true, UIControleOS.this);
                            statusFiltro.setLocation(e.getX(), e.getY() + 260);
                            statusFiltro.setVisible(true);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(UIControleOS.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtOrdensServico = new javax.swing.JTable();
        jlNumOs = new javax.swing.JLabel();
        jtfNumOsFiltro = new javax.swing.JTextField();
        jlTipo = new javax.swing.JLabel();
        jcbTipoFiltro = new javax.swing.JComboBox<>();
        jlSolicitante = new javax.swing.JLabel();
        jlResponsavel = new javax.swing.JLabel();
        jlCentroCusto = new javax.swing.JLabel();
        jtfCentroCusto = new javax.swing.JTextField();
        jbPesquisar = new javax.swing.JButton();
        jbLimparFiltros = new javax.swing.JButton();
        jbNovaOs = new javax.swing.JButton();
        jbExcluir = new javax.swing.JButton();
        jbComentarios = new javax.swing.JButton();
        jcbSolicitante = new javax.swing.JComboBox<>();
        jcbResponsavel = new javax.swing.JComboBox<>();
        jchFinalizados = new javax.swing.JCheckBox();
        jcbStatus = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jcbGerente = new javax.swing.JComboBox<>();
        jlGerente = new javax.swing.JLabel();
        jlNome = new javax.swing.JLabel();
        jtfNome = new javax.swing.JTextField();
        jtfCliente = new javax.swing.JTextField();
        jlCliente = new javax.swing.JLabel();
        jlNumeroRegistros = new javax.swing.JLabel();
        jlTotalFaturamentoDireto = new javax.swing.JLabel();
        jlValorTotal = new javax.swing.JLabel();
        jlDe = new javax.swing.JLabel();
        jlAte = new javax.swing.JLabel();
        jchAguardando = new javax.swing.JCheckBox();
        jlValorTotalFaturamentoDolphin = new javax.swing.JLabel();
        jftfDataFechamentoDe = new javax.swing.JFormattedTextField();
        jftfDataFechamentoAte = new javax.swing.JFormattedTextField();
        jtfProjeto = new javax.swing.JTextField();
        jlProjeto = new javax.swing.JLabel();
        jchListarAdicionais = new javax.swing.JCheckBox();
        chSomentePrincipais = new javax.swing.JCheckBox();
        jftfInicioDe = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        jftfInicioAte = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jftfInteracaoDe = new javax.swing.JFormattedTextField();
        jftfInteracaoAte = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jmbMenuOS = new javax.swing.JMenuBar();
        jmManutencao = new javax.swing.JMenu();
        jmiManterTipo = new javax.swing.JMenuItem();
        jmiManterStatus = new javax.swing.JMenuItem();
        jmRelatorios = new javax.swing.JMenu();
        jmiRelatorioVendas = new javax.swing.JMenuItem();
        miRelatorioFollowUp = new javax.swing.JMenuItem();

        setClosable(true);
        setIconifiable(true);
        setTitle("Controle de OS/Tarefa");
        setPreferredSize(new java.awt.Dimension(1190, 836));

        jtOrdensServico.setAutoCreateRowSorter(true);
        jtOrdensServico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtOrdensServico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtOrdensServicoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtOrdensServico);

        jlNumOs.setText("Nº OS");

        jtfNumOsFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfNumOsFiltroKeyPressed(evt);
            }
        });

        jlTipo.setText("Tipo");

        jlSolicitante.setText("Solicitante");

        jlResponsavel.setText("Responsável");

        jlCentroCusto.setText("Centro de Custo");

        jtfCentroCusto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfCentroCustoKeyPressed(evt);
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

        jbNovaOs.setText("Novo");
        jbNovaOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNovaOsActionPerformed(evt);
            }
        });

        jbExcluir.setText("Excluir");
        jbExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExcluirActionPerformed(evt);
            }
        });

        jbComentarios.setText("Comentarios");
        jbComentarios.setEnabled(false);
        jbComentarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbComentariosActionPerformed(evt);
            }
        });

        jchFinalizados.setText("Listar Finalizados");

        jLabel1.setText("Status");

        jlGerente.setText("Gerente");

        jlNome.setText("Nome");

        jtfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfNomeKeyPressed(evt);
            }
        });

        jtfCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfClienteKeyPressed(evt);
            }
        });

        jlCliente.setText("Cliente");

        jlNumeroRegistros.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jlNumeroRegistros.setText("Nº de registros: 0");

        jlTotalFaturamentoDireto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jlTotalFaturamentoDireto.setText("Faturamento direto: ");

        jlValorTotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jlValorTotal.setText("Valor Total: ");

        jlDe.setText("De Fechamento");

        jlAte.setText("Até Fechamento");

        jchAguardando.setText("Listar Aguardando");

        jlValorTotalFaturamentoDolphin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jlValorTotalFaturamentoDolphin.setText("Faturamento Dolphin: ");

        try {
            jftfDataFechamentoDe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfDataFechamentoDe.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        jftfDataFechamentoDe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfDataFechamentoDeKeyPressed(evt);
            }
        });

        try {
            jftfDataFechamentoAte.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfDataFechamentoAte.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        jftfDataFechamentoAte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfDataFechamentoAteKeyPressed(evt);
            }
        });

        jtfProjeto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfProjetoKeyPressed(evt);
            }
        });

        jlProjeto.setText("Projeto");

        jchListarAdicionais.setText("Listar Somente Adicionais");

        chSomentePrincipais.setText("Somente Principais");

        try {
            jftfInicioDe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfInicioDe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfInicioDeKeyPressed(evt);
            }
        });

        jLabel2.setText("De Início");

        try {
            jftfInicioAte.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfInicioAte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfInicioAteKeyPressed(evt);
            }
        });

        jLabel3.setText("Até Início");

        try {
            jftfInteracaoDe.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfInteracaoDe.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfInteracaoDeKeyPressed(evt);
            }
        });

        try {
            jftfInteracaoAte.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfInteracaoAte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfInteracaoAteKeyPressed(evt);
            }
        });

        jLabel4.setText("De Interação");

        jLabel5.setText("Até Interação");

        jmManutencao.setText("Cadastro");

        jmiManterTipo.setText("Tipo");
        jmiManterTipo.setEnabled(false);
        jmiManterTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiManterTipoActionPerformed(evt);
            }
        });
        jmManutencao.add(jmiManterTipo);

        jmiManterStatus.setText("Status");
        jmiManterStatus.setEnabled(false);
        jmiManterStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiManterStatusActionPerformed(evt);
            }
        });
        jmManutencao.add(jmiManterStatus);

        jmbMenuOS.add(jmManutencao);

        jmRelatorios.setText("Relatórios");

        jmiRelatorioVendas.setText("Relatório de vendas");
        jmiRelatorioVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiRelatorioVendasActionPerformed(evt);
            }
        });
        jmRelatorios.add(jmiRelatorioVendas);

        miRelatorioFollowUp.setText("Relatório Follow Up");
        miRelatorioFollowUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miRelatorioFollowUpActionPerformed(evt);
            }
        });
        jmRelatorios.add(miRelatorioFollowUp);

        jmbMenuOS.add(jmRelatorios);

        setJMenuBar(jmbMenuOS);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(11, 11, 11))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlTotalFaturamentoDireto)
                        .addGap(18, 18, 18)
                        .addComponent(jlValorTotalFaturamentoDolphin)
                        .addGap(18, 18, 18)
                        .addComponent(jlValorTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jlNumeroRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfNumOsFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlNumOs))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlNome)
                                .addGap(0, 366, Short.MAX_VALUE))
                            .addComponent(jtfNome))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jcbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbSolicitante, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlSolicitante))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlResponsavel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlGerente)
                            .addComponent(jcbGerente, 0, 252, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jbNovaOs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbComentarios)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chSomentePrincipais)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jchListarAdicionais)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jchAguardando)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jchFinalizados)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbLimparFiltros)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbPesquisar)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbTipoFiltro, 0, 126, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlTipo)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfCentroCusto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlCentroCusto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlCliente)
                                .addGap(0, 64, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlProjeto)
                                .addGap(0, 36, Short.MAX_VALUE))
                            .addComponent(jtfProjeto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jftfInteracaoDe, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jftfInteracaoAte, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jftfInicioDe, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jftfInicioAte, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlDe)
                            .addComponent(jftfDataFechamentoDe, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlAte, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                            .addComponent(jftfDataFechamentoAte))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlNumOs)
                    .addComponent(jlSolicitante)
                    .addComponent(jlResponsavel)
                    .addComponent(jlGerente)
                    .addComponent(jlNome)
                    .addComponent(jLabel1))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfNumOsFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbSolicitante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbGerente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCentroCusto)
                    .addComponent(jlTipo)
                    .addComponent(jlCliente)
                    .addComponent(jlDe)
                    .addComponent(jlAte)
                    .addComponent(jlProjeto)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbTipoFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfCentroCusto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftfDataFechamentoDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftfDataFechamentoAte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftfInicioDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftfInicioAte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftfInteracaoDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jftfInteracaoAte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbNovaOs)
                    .addComponent(jbExcluir)
                    .addComponent(jbComentarios)
                    .addComponent(jbPesquisar)
                    .addComponent(jbLimparFiltros)
                    .addComponent(jchFinalizados)
                    .addComponent(jchAguardando)
                    .addComponent(jchListarAdicionais)
                    .addComponent(chSomentePrincipais))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlTotalFaturamentoDireto)
                    .addComponent(jlValorTotal)
                    .addComponent(jlNumeroRegistros)
                    .addComponent(jlValorTotalFaturamentoDolphin))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiManterTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiManterTipoActionPerformed
        new UIManTipo(this).setVisible(true);
    }//GEN-LAST:event_jmiManterTipoActionPerformed

    private void jmiManterStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiManterStatusActionPerformed
        new UIManStatus(this).setVisible(true);
    }//GEN-LAST:event_jmiManterStatusActionPerformed

    private void jbComentariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbComentariosActionPerformed
        if (flagUIComentarios == false) {
            if (getOrdemServicoSelecionada() != null) {
                UIComentariosOS uiComentariosOS = new UIComentariosOS(this);

                menu.getJdpAreaTrabalho().add(uiComentariosOS);
                uiComentariosOS.setLocation(
                        menu.getJdpAreaTrabalho()
                                .getWidth() / 2 - uiComentariosOS
                                .getWidth() / 2,
                        menu.getJdpAreaTrabalho()
                                .getHeight() / 2 - uiComentariosOS
                                .getHeight() / 2);
                flagUIComentarios = true;
                uiComentariosOS.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Selecione a OS para comentar", "",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_jbComentariosActionPerformed

    private void jbExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExcluirActionPerformed
        OrdemServico objOrdemServico = getOrdemServicoSelecionada();
        if (objOrdemServico == null) {
            JOptionPane.showMessageDialog(this, "Selecione a OS que deseja excluir!", "", JOptionPane.WARNING_MESSAGE);
        } else {
            Object[] options = {"Sim", "Não"};
            int i = JOptionPane.showOptionDialog(null,
                    "Tem certeza que deseja excluir?", "Excluir",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (i == JOptionPane.YES_OPTION) {
                excluir(objOrdemServico);
            }
        }
    }//GEN-LAST:event_jbExcluirActionPerformed

    private void jbNovaOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNovaOsActionPerformed
        new UITarefas(null, this).setVisible(true);
    }//GEN-LAST:event_jbNovaOsActionPerformed

    /**
     * Método usado para atualizar OS
     *
     * @param evt
     */
    private void jbLimparFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparFiltrosActionPerformed
        limpar();
        filtrarOS();
    }//GEN-LAST:event_jbLimparFiltrosActionPerformed

    /**
     * Método usado para pesquisar OS
     *
     * @param evt
     */
    private void jbPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPesquisarActionPerformed
        filtrarOS();
    }//GEN-LAST:event_jbPesquisarActionPerformed

    private void jtfCentroCustoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCentroCustoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            filtrarOS();
        }
    }//GEN-LAST:event_jtfCentroCustoKeyPressed

    private void jtfNumOsFiltroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNumOsFiltroKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            filtrarOS();
        }
    }//GEN-LAST:event_jtfNumOsFiltroKeyPressed

    private void jtOrdensServicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtOrdensServicoMouseClicked

        if (evt.getButton() == evt.BUTTON1 && evt.getClickCount() == 2) {
            Menu.carregamento(true);

            new Thread() {
                @Override
                public void run() {

                    new UITarefas(getOrdemServicoSelecionada(), UIControleOS.this).setVisible(true);

                }
            }.start();

        }
    }//GEN-LAST:event_jtOrdensServicoMouseClicked

    private void jtfNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            filtrarOS();
        }
    }//GEN-LAST:event_jtfNomeKeyPressed

    private void jtfClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClienteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            filtrarOS();
        }
    }//GEN-LAST:event_jtfClienteKeyPressed

    private void jmiRelatorioVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiRelatorioVendasActionPerformed
        Menu.carregamento(true);
        new Thread() {
            @Override
            public void run() {
                try {
                    String path = System.getProperty("java.io.tmpdir") + "\\rel-vendas.xls";
                    ExportaExcel excel = new ExportaExcel(path);
                    String deInicio = FormatarData.formatarData(jftfInicioDe.getText());
                    String ateInicio = FormatarData.formatarData(jftfInicioAte.getText());
                    String deFechamento = FormatarData.formatarData(jftfDataFechamentoDe.getText());
                    String ateFechamento = FormatarData.formatarData(jftfDataFechamentoAte.getText());
                    
                    
                    if (deInicio.equals("    -  -  ")) {
                        deInicio = "";
                    }
                    if (ateInicio.equals("    -  -  ")) {
                        ateInicio = "";
                    }
                    if (deFechamento.equals("    -  -  ")) {
                        deFechamento = "";
                    }
                    if (ateFechamento.equals("    -  -  ")) {
                        ateFechamento = "";
                    }
                    
                    System.out.println("deInicio = " + deInicio + " " + deInicio.isEmpty());
                    System.out.println("ateInicio = " + ateInicio + " " + ateInicio.isEmpty());
                    System.out.println("deFechamento = " + ateFechamento + " " + deFechamento.isEmpty());
                    System.out.println("ateFechamento = " + deFechamento + " " + ateFechamento.isEmpty());
                    
                    ArrayList<OrdemServico> tarefas = osdao.relatorioDeVendas(deInicio, ateInicio, deFechamento, ateFechamento);
                    excel.exportarVendas(tarefas);

                    File file = new File(path);
                    Desktop.getDesktop().open(file);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(),
                            "Erro ao exportar vendas",
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    Menu.carregamento(false);
                }
            }
        }.start();
    }//GEN-LAST:event_jmiRelatorioVendasActionPerformed

    private void jtfProjetoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfProjetoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            filtrarOS();
        }
    }//GEN-LAST:event_jtfProjetoKeyPressed

    private void miRelatorioFollowUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miRelatorioFollowUpActionPerformed
        Menu.carregamento(true);

        new Thread() {
            @Override
            public void run() {
                try {
                    String path = System.getProperty("java.io.tmpdir") + "\\rel-follow-up.xls";
                    ExportaExcel excel = new ExportaExcel(path);

                    excel.exportarRelatorioFollowUp(tarefas);

                    File file = new File(path);
                    Desktop.getDesktop().open(file);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(),
                            "Erro ao exportar relatório",
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    Menu.carregamento(false);
                }
            }
        }.start();
    }//GEN-LAST:event_miRelatorioFollowUpActionPerformed

    private void jftfInteracaoDeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfInteracaoDeKeyPressed
       if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            filtrarOS();
        }
    }//GEN-LAST:event_jftfInteracaoDeKeyPressed

    private void jftfInteracaoAteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfInteracaoAteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            filtrarOS();
        }
    }//GEN-LAST:event_jftfInteracaoAteKeyPressed

    private void jftfInicioDeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfInicioDeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            filtrarOS();
        }
    }//GEN-LAST:event_jftfInicioDeKeyPressed

    private void jftfInicioAteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfInicioAteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            filtrarOS();
        }
    }//GEN-LAST:event_jftfInicioAteKeyPressed

    private void jftfDataFechamentoDeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfDataFechamentoDeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            filtrarOS();
        }
    }//GEN-LAST:event_jftfDataFechamentoDeKeyPressed

    private void jftfDataFechamentoAteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfDataFechamentoAteKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            filtrarOS();
        }
    }//GEN-LAST:event_jftfDataFechamentoAteKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chSomentePrincipais;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbComentarios;
    private javax.swing.JButton jbExcluir;
    private javax.swing.JButton jbLimparFiltros;
    private javax.swing.JButton jbNovaOs;
    private javax.swing.JButton jbPesquisar;
    private javax.swing.JComboBox<Pessoa> jcbGerente;
    private javax.swing.JComboBox<Pessoa> jcbResponsavel;
    private javax.swing.JComboBox<Pessoa> jcbSolicitante;
    private javax.swing.JComboBox<Object> jcbStatus;
    private javax.swing.JComboBox<Object> jcbTipoFiltro;
    private javax.swing.JCheckBox jchAguardando;
    private javax.swing.JCheckBox jchFinalizados;
    private javax.swing.JCheckBox jchListarAdicionais;
    private javax.swing.JFormattedTextField jftfDataFechamentoAte;
    private javax.swing.JFormattedTextField jftfDataFechamentoDe;
    private javax.swing.JFormattedTextField jftfInicioAte;
    private javax.swing.JFormattedTextField jftfInicioDe;
    private javax.swing.JFormattedTextField jftfInteracaoAte;
    private javax.swing.JFormattedTextField jftfInteracaoDe;
    private javax.swing.JLabel jlAte;
    private javax.swing.JLabel jlCentroCusto;
    private javax.swing.JLabel jlCliente;
    private javax.swing.JLabel jlDe;
    private javax.swing.JLabel jlGerente;
    private javax.swing.JLabel jlNome;
    private javax.swing.JLabel jlNumOs;
    private javax.swing.JLabel jlNumeroRegistros;
    private javax.swing.JLabel jlProjeto;
    private javax.swing.JLabel jlResponsavel;
    private javax.swing.JLabel jlSolicitante;
    private javax.swing.JLabel jlTipo;
    private javax.swing.JLabel jlTotalFaturamentoDireto;
    private javax.swing.JLabel jlValorTotal;
    private javax.swing.JLabel jlValorTotalFaturamentoDolphin;
    private javax.swing.JMenu jmManutencao;
    private javax.swing.JMenu jmRelatorios;
    private javax.swing.JMenuBar jmbMenuOS;
    private javax.swing.JMenuItem jmiManterStatus;
    private javax.swing.JMenuItem jmiManterTipo;
    private javax.swing.JMenuItem jmiRelatorioVendas;
    private javax.swing.JTable jtOrdensServico;
    private javax.swing.JTextField jtfCentroCusto;
    private javax.swing.JTextField jtfCliente;
    private javax.swing.JTextField jtfNome;
    private javax.swing.JTextField jtfNumOsFiltro;
    private javax.swing.JTextField jtfProjeto;
    private javax.swing.JMenuItem miRelatorioFollowUp;
    // End of variables declaration//GEN-END:variables
}
