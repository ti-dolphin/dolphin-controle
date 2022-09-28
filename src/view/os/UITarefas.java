/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.os;

import controllers.OrdemServicoContatoController;
import dao.CriterioDAO;
import dao.DAOFactory;
import dao.OrdemServicoCriterioDAO;
import dao.apontamento.ApontamentoDAO;
import dao.apontamento.ComentarioDAO;
import dao.os.AdicionalDAO;
import dao.os.AlteracoesOsDAO;
import dao.os.CentroCustoDAO;
import dao.os.CidadeDAO;
import dao.os.ClienteDAO;
import dao.os.EstadoDAO;
import dao.os.HistoricoStatusDAO;
import dao.os.MotivoPerdaDAO;
import dao.os.OrdemServicoDAO;
import dao.os.PessoaDAO;
import dao.os.ProjetoDAO;
import dao.os.SegmentoDAO;
import dao.os.SeguidoresDAO;
import dao.os.StatusDAO;
import dao.os.TipoOsDAO;
import exceptions.CampoEmBrancoException;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.Contato;
import model.ContatoTableModel;
import model.os.Adicional;
import model.Seguidor;
import model.apontamento.ApontaTableModel;
import model.apontamento.Apontamento;
import model.apontamento.Comentario;
import model.apontamento.StatusApont;
import model.os.AlteracoesOs;
import model.os.CentroCusto;
import model.os.Cidade;
import model.os.Cliente;
import model.os.ComentarioOSTableModel;
import model.os.Estado;
import model.os.HistoricoStatus;
import model.os.OrdemServico;
import model.os.Pessoa;
import model.os.Projeto;
import model.os.Segmento;
import model.os.SeguidoresTableModel;
import model.os.Status;
import model.os.TipoOs;
import model.os.Venda;
import utilitarios.EmailTotvs;
import utilitarios.os.CurrencyTableCellRenderer;
import view.Menu;
import model.Criterio;
import model.os.MotivoPerda;
import org.apache.commons.mail.EmailException;
import utilitarios.FormatarData;
import view.FrmBuscarContatos;

/**
 *
 * @author guilherme.oliveira
 */
public class UITarefas extends javax.swing.JDialog {

    private boolean flagUIAdicionar = false;
    private DefaultListModel custosModel = new DefaultListModel();
    private DefaultListModel clientesModel = new DefaultListModel();
    private CentroCusto centroCusto;
    private ArrayList<Cliente> clientes;
    private ArrayList<CentroCusto> custos;
    private UIControleOS uiControleOs;
    private ApontaTableModel aTableModel = new ApontaTableModel();
    private SeguidoresTableModel seguidoresTableModel = new SeguidoresTableModel();
    private ComentarioOSTableModel cOSTableModel = new ComentarioOSTableModel();
    private boolean campoEditado = false;
    private boolean flagUIAdicionarSeguidor;
    private OrdemServico ordemServico;
    private Comentario comentario;
    private OrdemServico ordemServicoAntiga;
    private boolean salvarEFechar;
    private OrdemServicoDAO osdao;
    private ProjetoDAO pdao;
    private SeguidoresDAO sdao;
    private Adicional adicional;
    private Projeto projeto;
    private ContatoTableModel contatoTableModel;

    public UITarefas() {
    }

    /**
     * Creates new form UICadOs
     */
    public UITarefas(OrdemServico ordemServico, UIControleOS uiControleOs) {
        long tempoInicial = System.currentTimeMillis();
        this.ordemServico = ordemServico;
        this.uiControleOs = uiControleOs;
        this.osdao = DAOFactory.getORDEMSERVICODAO();
        this.sdao = DAOFactory.getSEGUIDORESDAO();
        initComponents();
        jcbTipo.setEnabled(ordemServico == null);
        jbPesquisarProjeto.setEnabled(ordemServico == null);
        jtfNome.requestFocus();
        preencherComboBoxEstado();
        preencherCampos();
        jspCustos.setVisible(false);
        jlstCustos.setVisible(false);
        jspClientes.setVisible(false);
        jlstClientes.setVisible(false);
        preencherComboBoxTipo();
        preencherComboBoxSegmento();
        preencherComboBoxSolicitante();
        preencherComboBoxResponsavel();
        preencherComboBoxStatus();
        preencherDataSolicitacao();
        preencherComboBoxCriterios();
        preencherComboBoxMotivoPerda();
        darPermissoes();
        jtaApontamentos.setModel(aTableModel);
        buscarComentarios();
        jtComentarios.setModel(cOSTableModel);
        preencherApontamentos();
        buscarSeguidores();
        this.contatoTableModel = new ContatoTableModel();
        buscarContatos();
        redimensionarColunasApontOS();
        redimensionarColunasComentarios();
        Menu.carregamento(false);
        carregarOrdemServicoAntiga();
        onChangeStatus();
        long tempoFinal = System.currentTimeMillis();
        System.out.printf("%.3f s%n", (tempoFinal - tempoInicial) / 1000d);
    }

    public UIControleOS getUiControleOs() {
        return uiControleOs;
    }

    public JTable getJtaApontamentos() {
        return jtaApontamentos;
    }

    public boolean isFlagUIAdicionar() {
        return flagUIAdicionar;
    }

    public void setFlagUIAdicionar(boolean flagUIAdicionar) {
        this.flagUIAdicionar = flagUIAdicionar;
    }

    public ApontaTableModel getApontamentoTableModel() {
        return aTableModel;
    }

    public JTextField getJtfCodOs() {
        return jtfCodOs;
    }

    public boolean isFlagUIAdicionarSeguidor() {
        return flagUIAdicionarSeguidor;
    }

    public void setFlagUIAdicionarSeguidor(boolean flagUIAdicionarSeguidor) {
        this.flagUIAdicionarSeguidor = flagUIAdicionarSeguidor;
    }

    public SeguidoresTableModel getSeguidoresTableModel() {
        return seguidoresTableModel;
    }

    public JTextField getJtfCodProjeto() {
        return jtfCodProjeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public Adicional getAdicional() {
        return adicional;
    }

    public void setAdicional(Adicional adicional) {
        this.adicional = adicional;
    }

    public JComboBox<Object> getJcbTipo() {
        return jcbTipo;
    }

    public JTextField getJtfAdicional() {
        return jtfAdicional;
    }

    public JTable getTblOrdemServicoContatos() {
        return tblOrdemServicoContatos;
    }

    public ContatoTableModel getContatoTableModel() {
        return contatoTableModel;
    }

    public JTextArea getJtaComentariosOS() {
        return jtaComentariosOS;
    }

    public JComboBox<Segmento> getJcbSegmento() {
        return jcbSegmento;
    }

    public JTextField getJtfObra() {
        return jtfObra;
    }

    private void redimensionarColunasApontOS() {

        jtaApontamentos.setAutoResizeMode(jtaApontamentos.AUTO_RESIZE_OFF);
        jtaApontamentos.getColumnModel().getColumn(0).setPreferredWidth(50);//id
        jtaApontamentos.getColumnModel().getColumn(1).setPreferredWidth(50);//chapa
        jtaApontamentos.getColumnModel().getColumn(2).setPreferredWidth(300);//nome
        jtaApontamentos.getColumnModel().getColumn(3).setPreferredWidth(100);//data
        jtaApontamentos.getColumnModel().getColumn(4).setPreferredWidth(50);//status   
        jtaApontamentos.getColumnModel().getColumn(5).setPreferredWidth(200);//centro de custo
        jtaApontamentos.getColumnModel().getColumn(6).setPreferredWidth(200);//lider
        jtaApontamentos.getColumnModel().getColumn(7).setPreferredWidth(100);//situacao

        if (Menu.logado.isPermCustoMO()) {
            CurrencyTableCellRenderer cRenderer = new CurrencyTableCellRenderer();
            jtaApontamentos.getColumnModel().getColumn(8).setCellRenderer(cRenderer);

            jtaApontamentos.getColumnModel().getColumn(8).setPreferredWidth(100);//custo
        } else {
            jtaApontamentos.getColumnModel().getColumn(8).setMinWidth(0);//custo
            jtaApontamentos.getColumnModel().getColumn(8).setPreferredWidth(0);//custo
        }
    }

    private void redimensionarColunasComentarios() {
        jtComentarios.getColumnModel().getColumn(0).setPreferredWidth(40);//id
        jtComentarios.getColumnModel().getColumn(1).setPreferredWidth(700);//descricao
        jtComentarios.getColumnModel().getColumn(2).setPreferredWidth(140);//data
        jtComentarios.getColumnModel().getColumn(3).setPreferredWidth(100);//usuario
    }

    /**
     * Método usado para preencher tabela de apontamentos da OS
     */
    public void preencherApontamentos() {

        if (ordemServico != null) {
            double custo = 0.0;
            if (ordemServico.getApontamentos() != null) {
                for (Apontamento apontamento : ordemServico.getApontamentos()) {
                    aTableModel.addRow(apontamento);
                    custo = custo + apontamento.getCustoMaoDeObra();
                    jlCusto.setText("Custo Total: " + NumberFormat.getCurrencyInstance().format(custo));
                }
            }

            jtaApontamentos.setModel(aTableModel);
        } else {
            jTabbedPane1.setEnabledAt(2, false);
        }
    }

    public void buscarSeguidores() {
        try {

            if (ordemServico != null) {

                if (ordemServico.getSeguidores() != null) {

                    for (Seguidor seguidor : ordemServico.getSeguidores()) {
                        seguidoresTableModel.addRow(seguidor);
                    }
                }

                jtSeguidores.setModel(seguidoresTableModel);
            } else {
                jTabbedPane1.setEnabledAt(4, false);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro ao buscar seguidores", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscarContatos() {
        try {

            if (ordemServico != null) {

                if (ordemServico.getContatos() != null) {

                    for (Contato contato : ordemServico.getContatos()) {
                        contatoTableModel.addRow(contato);
                    }
                }

                tblOrdemServicoContatos.setModel(contatoTableModel);
            } else {
                jTabbedPane1.setEnabledAt(6, false);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro ao buscar contatos", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método usado para filtrar Centro de Custo
     */
    public void filtrarCentroCusto() {

        try {
            CentroCustoDAO dao = DAOFactory.getCENTROCUSTODAO();
            custos = dao.filtrarPorNome(jtfCentroCusto.getText(), true);

            for (int i = 0; i < custos.size(); i++) {
                centroCusto = custos.get(i);
                custosModel.addElement(centroCusto);
            }
            jlstCustos.setModel(custosModel);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Erro ao filtrar centro de custo",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método usado para buscar clientes e adicionar na lista
     */
    public void pesquisarClientes() {
        try {

            ClienteDAO dao = DAOFactory.getCLIENTEDAO();
            clientes = dao.buscarPorNomeFantasia(jtfCliente.getText());

            for (Cliente c : clientes) {
                clientesModel.addElement(c);
            }
            jlstClientes.setModel(clientesModel);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Erro ao buscar clientes",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherComboBoxCriterios() {
        CriterioDAO dao = new CriterioDAO();
        try {
            for (int i = 1; i < 6; i++) {
                for (Criterio criterio : dao.buscar(i)) {
                    switch (i) {
                        case 1:
                            jcbCriterio1.addItem(criterio);
                            break;
                        case 2:
                            jcbCriterio2.addItem(criterio);
                            break;
                        case 3:
                            jcbCriterio3.addItem(criterio);
                            break;
                        case 4:
                            jcbCriterio4.addItem(criterio);
                            break;
                        case 5:
                            jcbCriterio5.addItem(criterio);
                            break;
                    }
                }
            }
        } catch (SQLException se) {
            String msgErro = se.getMessage();
            System.out.println("Erro: " + msgErro);
            JOptionPane.showMessageDialog(this, msgErro,
                    "Erro ao carregar combobox de critérios",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método usado para preencher combobox do tipo de OS
     */
    private void preencherComboBoxTipo() {
        TipoOsDAO dao = DAOFactory.getTIPOOSDAO();

        try {
            for (TipoOs t : dao.buscarTiposCombo(Menu.logado)) {
                jcbTipo.addItem(t);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao carregar combobox tipo",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método usado para preencher combobox de estados
     */
    private void preencherComboBoxEstado() {
        EstadoDAO dao = DAOFactory.getESTADODAO();

        try {
            for (Estado estado : dao.buscar()) {
                jcbEstado.addItem(estado);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro ao carregar estados",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método usado para preencher combobox de cidades
     */
    private void preencherComboBoxCidade() {
        CidadeDAO dao = DAOFactory.getCIDADEDAO();
        Estado estado = (Estado) jcbEstado.getSelectedItem();

        jcbCidade.removeAllItems();
        try {
            for (Cidade cidade : dao.buscarPorEstado(estado.getId())) {
                jcbCidade.addItem(cidade);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Erro ao carregar as cidades",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método usado para preencher combobox de segmentos
     */
    private void preencherComboBoxSegmento() {
        SegmentoDAO dao = DAOFactory.getSEGMENTODAO();

        try {
            for (Segmento segmento : dao.buscar("")) {
                jcbSegmento.addItem(segmento);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro ao carregar combobox segmento",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método usado para preencher combobox do soicitante e responsavel
     */
    private void preencherComboBoxSolicitante() {
        PessoaDAO dao = DAOFactory.getPESSOADAO();

        try {
            for (Pessoa p : dao.buscarPessoasCombo("solicitante")) {
                jcbSolicitante.addItem(p);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao carregar combobox solicitante",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método usado para preencher combobox de responsaveis da OS
     */
    private void preencherComboBoxResponsavel() {
        PessoaDAO dao = DAOFactory.getPESSOADAO();

        try {
            for (Pessoa p : dao.buscarPessoasCombo("responsavel")) {
                jcbResponsavel.addItem(p);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao carregar combobox responsável",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método usado para preencher combobox do status da OS
     */
    private void preencherComboBoxStatus() {
        StatusDAO dao = DAOFactory.getSTATUSDAO();

        try {
            for (Status status : dao.buscarComboCadOs()) {
                jcbStatus.addItem(status);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro ao carregar combobox status da OS",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherComboBoxMotivoPerda() {
        MotivoPerdaDAO dao = DAOFactory.getMOTIVOPERDADAO();

        try {
            for (MotivoPerda motivo : dao.buscarTodos()) {
                cbxMotivoDaPerda.addItem(motivo);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro ao carregar combobox motivo da perda",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método usado para preencher lista de centros de custo da OS
     */
    public void preencherCentrosCusto() {
        CentroCusto c = (CentroCusto) jlstCustos.getSelectedValue();

        jtfCentroCusto.setText(c.getNome());
        jtfCodCusto.setText(c.getCodCusto());
        custosModel.removeAllElements();
        jspCustos.setVisible(false);
        jlstCustos.setVisible(false);
    }

    /**
     * Método usado para preencher lista de clientes da OS
     */
    public void preencherClientes() {
        Cliente c = (Cliente) jlstClientes.getSelectedValue();

        jtfCodCliente.setText(c.getCodCliente());
        jtfCodColigada.setText(String.valueOf(c.getCodColigada()));
        jtfCliente.setText(c.getNomeFantasia());
        clientesModel.removeAllElements();
        jspClientes.setVisible(false);
        jlstClientes.setVisible(false);
    }

    /**
     * Método usado para data atual e setar no campo data da solicitação
     */
    public void preencherDataSolicitacao() {
        if (jftfDataSolicitacao.getText().equals("  /  /    ")) {
            LocalDateTime dataAtual = LocalDateTime.now();
            jftfDataSolicitacao.setText(String.valueOf(dataAtual
                    .format(DateTimeFormatter
                            .ofPattern("dd/MM/yyyy HH:mm"))));
        }
    }

    private void preencherCentroDeCustoPadrao() {

        TipoOs tipo = (TipoOs) jcbTipo.getSelectedItem();
        String codigoCentroCusto = "";

        final int tipoComercial = 21;
        final int tipoOrcamento = 16;
        final int tipoProjetos = 17;

        if (tipo.getCodTipoOs() == tipoComercial) {
            codigoCentroCusto = "2.02.0002";
        } else if (tipo.getCodTipoOs() == tipoOrcamento) {
            codigoCentroCusto = "2.03.0001";
        } else if (tipo.getCodTipoOs() == tipoProjetos) {
            codigoCentroCusto = "4.01";
        }

        try {
            centroCusto = new CentroCustoDAO().buscarPorId(codigoCentroCusto);
        } catch (SQLException ex) {
            Logger.getLogger(UITarefas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }
        if (centroCusto != null) {

            jtfCentroCusto.setText(centroCusto.getNome());
            jtfCodCusto.setText(centroCusto.getCodCusto());
        }
    }

    /**
     * Método usado para preencher campos
     */
    private void preencherCampos() {

        if (ordemServico != null) {
            jbPesquisarProjeto.setEnabled(false);
            jtfCodOs.setText(String.valueOf(ordemServico.getCodOs()));
            jtfNome.setText(ordemServico.getNome());
            jtfCodProjeto.setText(String.valueOf(ordemServico.getProjeto().getId()));

            if (ordemServico.getAdicional().getNumero() != 0) {
                jtfAdicional.setText(String.valueOf(ordemServico.getAdicional().getNumero()));
            }

            jcbTipo.getModel().setSelectedItem(ordemServico.getTipoOs());
            jcbSegmento.getModel().setSelectedItem(ordemServico.getSegmento());
            jcbEstado.getModel().setSelectedItem(ordemServico.getCidade().getEstado());
            jcbCidade.getModel().setSelectedItem(ordemServico.getCidade());
            jcbResponsavel.getModel().setSelectedItem(ordemServico.getResponsavel());

            jcbSolicitante.getModel().setSelectedItem(ordemServico.getSolicitante());
            jtfCodCusto.setText(ordemServico.getCentroCusto().getCodCusto());
            jtfCentroCusto.setText(ordemServico.getCentroCusto().getNome());
            jtfCodColigada.setText(String.valueOf(ordemServico.getCliente().getCodColigada()));
            jtfCodCliente.setText(ordemServico.getCliente().getCodCliente());
            jtfCliente.setText(ordemServico.getCliente().getNomeFantasia());
            jtfObra.setText(ordemServico.getObra());
            if (ordemServico.getDataSolicitacao() != null) {
                jftfDataSolicitacao.setText(String.valueOf(ordemServico.getDataSolicitacao()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            }
            if (ordemServico.getDataInicio() != null) {
                jftfDataInicio.setText(String.valueOf(ordemServico.getDataInicio()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            }
            if (ordemServico.getDataPrevEntrega() != null) {
                jftfDataPrevEntrega.setText(String.valueOf(ordemServico.getDataPrevEntrega()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            }
            if (ordemServico.getDataEntrega() != null) {
                jftfDataEntrega.setText(String.valueOf(ordemServico.getDataEntrega()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            }
            if (ordemServico.getDataNecessidade() != null) {
                jftfDataNecessidade.setText(String.valueOf(ordemServico.getDataNecessidade()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            }
            if (ordemServico.getDataInteracao() != null) {
                jftfDataInteracao.setText(String.valueOf(ordemServico.getDataInteracao()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            }
            Status status = ordemServico.getStatus();
            jcbStatus.getModel().setSelectedItem(status);
            jtfValorComissao.setEnabled(status.getNome().equalsIgnoreCase("Ganho"));

            MotivoPerda motivo = ordemServico.getMotivo();
            System.out.println(motivo);
            cbxMotivoDaPerda.getModel().setSelectedItem(motivo);
            cbxMotivoDaPerda.setEnabled(status.getNome().equalsIgnoreCase("perdido"));
            txtaObservacoes.setText(ordemServico.getObservacoes());
            txtaObservacoes.setEnabled(status.getNome().equalsIgnoreCase("perdido"));

            DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
            formatoDois.setMinimumFractionDigits(2);
            formatoDois.setParseBigDecimal(true);

            try {
                ordemServico.setSeguidores(new SeguidoresDAO().buscarSeguidoresDaOs(ordemServico.getCodOs()));
                ordemServico.setApontamentos(new ApontamentoDAO().buscarApontamentosDaOs(ordemServico.getCodOs()));
                ordemServico.setComentarios(new ComentarioDAO().buscarComentariosDaOs(ordemServico.getCodOs()));
                ordemServico.setContatos(new OrdemServicoContatoController().buscarPorOrdemServico(ordemServico.getCodOs()));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (CampoEmBrancoException ex) {
                Logger.getLogger(UITarefas.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (ordemServico.getVenda() != null) {
                jtfValorFaturamentoDireto.setText(String.valueOf(formatoDois.format(ordemServico.getVenda().getValorFaturamentoDireto())));
                jtfValorMaoDeObra.setText(String.valueOf(formatoDois.format(ordemServico.getVenda().getValorFaturamentoDolphin())));
                jtfValorComissao.setText(String.valueOf(formatoDois.format(ordemServico.getVenda().getValorComissao())));
                jtfValorTotal.setText(String.valueOf(
                        formatoDois.format(
                                ordemServico.getVenda().calcularValorTotal()
                        )
                )
                );
            }

            ArrayList<Criterio> criterios;
            try {
                criterios = new OrdemServicoCriterioDAO().buscar(ordemServico.getCodOs());
                if (!criterios.isEmpty()) {

                    for (int i = 0; i < 5; i++) {
                        ordemServico.getCriterios().add(criterios.get(i));
                    }

                    jcbCriterio1.getModel().setSelectedItem(ordemServico.getCriterios().get(0));
                    jcbCriterio2.getModel().setSelectedItem(ordemServico.getCriterios().get(1));
                    jcbCriterio3.getModel().setSelectedItem(ordemServico.getCriterios().get(2));
                    jcbCriterio4.getModel().setSelectedItem(ordemServico.getCriterios().get(3));
                    jcbCriterio5.getModel().setSelectedItem(ordemServico.getCriterios().get(4));
                }

            } catch (SQLException ex) {
                Logger.getLogger(UITarefas.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null,
                        ex.getMessage(),
                        "Erro ao preencher combobox critérios!",
                        JOptionPane.ERROR_MESSAGE);
            }

            chPrincipal.setSelected(ordemServico.isPrincipal());

            habilitarIconesCliente(ordemServico.getCliente());

            campoEditado = false;
        }
    }

    private void carregarOrdemServicoAntiga() {

        ordemServicoAntiga = new OrdemServico();
        Status statusAntigo = (Status) jcbStatus.getSelectedItem();
        ordemServicoAntiga.setStatus(statusAntigo);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (!jftfDataNecessidade.getText().equals("  /  /    ")) {
            LocalDate dataNecessidade = LocalDate.parse(jftfDataNecessidade.getText(), formatter);
            ordemServicoAntiga.setDataNecessidade(dataNecessidade);
        }

        if (!jftfDataPrevEntrega.getText().equals("  /  /    ")) {
            LocalDate dataPrevEntrega = LocalDate.parse(jftfDataPrevEntrega.getText(), formatter);
            ordemServicoAntiga.setDataPrevEntrega(dataPrevEntrega);
        }
        if (!jftfDataEntrega.getText().equals("  /  /    ")) {
            LocalDate dataEntrega = LocalDate.parse(jftfDataEntrega.getText(), formatter);
            ordemServicoAntiga.setDataEntrega(dataEntrega);
        }
        if (ordemServico != null) {
            ordemServicoAntiga.setCodOs(Integer.parseInt(jtfCodOs.getText()));
        }

    }

    private void setTarefa() throws Exception {
        try {
            CentroCusto c = new CentroCusto();
            Cliente cliente = new Cliente();
            Venda venda = new Venda();
            DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            if (jtfNome.getText().isEmpty()) {
                throw new NumberFormatException("Insira o nome da OS/Tarefa!");
            }
            if (jtfNome.getText().length() > 33) {
                throw new NumberFormatException("Nome da OS deve ter menos de 33 caracteres!");
            }
            if (jcbTipo.getSelectedIndex() == 0) {
                throw new NumberFormatException("Selecione o tipo da OS/Tarefa!");
            }
            if (jtfCodCusto.getText().isEmpty() || jtfCodCusto.getText().equals("-")) {
                throw new NumberFormatException("Selecione o centro de custo!");
            }
            if (jtfObra.getText().isEmpty()) {
                throw new NumberFormatException("Insira ao nome da obra!");
            }
            if (jcbSolicitante.getSelectedIndex() == 0) {
                throw new NumberFormatException("Selecione o solicitante da OS/Tarefa!");
            }
            if (jftfDataInicio.getText().equals("  /  /    ")) {
                throw new NumberFormatException("Insira a data de início!");
            }
            if (jcbResponsavel.getSelectedIndex() == 0) {
                throw new NumberFormatException("Selecione o responsável da OS/Tarefa!");
            }
            if (jftfDataInteracao.getText().equals("  /  /    ")) {
                throw new NumberFormatException("Insira a data de interação com o cliente!");
            } else {
                LocalDate hoje = LocalDate.now();
                LocalDate dataInteracao = FormatarData.converterTextoEmData(jftfDataInteracao.getText(), "dd/MM/yyyy");
                boolean venceu = hoje.isAfter(dataInteracao);
                
                if (venceu) {
                    throw new Exception("Data de interação vencida! Insira uma nova data de interação!");
                }
            }

            Status statusSelecionado = (Status) jcbStatus.getSelectedItem();
            if (statusSelecionado.getNome().equals("Ganho") && (jtfValorComissao.getText().isEmpty() || jtfValorComissao.getText().equals("0,00"))) {
                throw new NumberFormatException("O campo \"valor de comissão\" é obrigatório quando o status é \"Ganho\"");
            }
            MotivoPerda motivo = (MotivoPerda) cbxMotivoDaPerda.getSelectedItem();
            if (statusSelecionado.getNome().equalsIgnoreCase("Perdido")) {
                if (motivo == null || motivo.getId() == 1) {
                    throw new NumberFormatException("O campo \"Motivo da Perda\" é obrigatório quando o status é \"Perdido\"");
                }
                if (txtaObservacoes.getText().isEmpty()) {
                    throw new NumberFormatException("O campo \"Observações\" é obrigatório quando o status é \"Perdido\"");
                }
            }
            ordemServico.setMotivo(motivo);
            ordemServico.setObservacoes(txtaObservacoes.getText());
            ordemServico.setNome(jtfNome.getText());
            TipoOs t = (TipoOs) jcbTipo.getSelectedItem();
            ordemServico.setTipoOs(t);

            c.setNome(jtfCentroCusto.getText());
            c.setCodCusto(jtfCodCusto.getText());
            ordemServico.setCentroCusto(c);

            if (!jtfCodCliente.getText().isEmpty()) {
                if (!jtfCliente.getText().isEmpty()) {
                    cliente.setNomeFantasia(jtfCliente.getText());
                    cliente.setCodCliente(jtfCodCliente.getText());
                    cliente.setCodColigada(Integer.parseInt(jtfCodColigada.getText()));
                }
            } else {
                cliente.setNomeFantasia("-");
                cliente.setCodCliente("-");
                cliente.setCodColigada(0);
            }
            ordemServico.setCliente(cliente);

            habilitarIconesCliente(ordemServico.getCliente());

            ordemServico.setObra(jtfObra.getText());
            Pessoa ps = (Pessoa) jcbSolicitante.getSelectedItem();
            ordemServico.setSolicitante(ps);

            if (!jftfDataSolicitacao.getText().equals("  /  /    ")) {
                ordemServico.setDataSolicitacao(LocalDate.parse(jftfDataSolicitacao.getText(), formatterDate));
            }

            if (!jftfDataNecessidade.getText().equals("  /  /    ")) {
                ordemServico.setDataNecessidade(LocalDate.parse(jftfDataNecessidade.getText(), formatterDate));
            }

            String dataInteracao = jftfDataInteracao.getText();
            if (!dataInteracao.equals("  /  /    ")) {
                ordemServico.setDataInteracao(LocalDate.parse(dataInteracao, formatterDate));
            }

            ordemServico.setDataInicio(LocalDate.parse(jftfDataInicio.getText(), formatterDate));

            String dataPrevEntrega = jftfDataPrevEntrega.getText();
            if (!dataPrevEntrega.equals("  /  /    ")) {
                ordemServico.setDataPrevEntrega(LocalDate.parse(dataPrevEntrega, formatterDate));
            }

            Status status = (Status) jcbStatus.getSelectedItem();

            String dataEntrega = jftfDataEntrega.getText();
            if (!dataEntrega.equals("  /  /    ") && status.getAcao() == 1) {
                ordemServico.setDataEntrega(LocalDate.parse(dataEntrega, formatterDate));
            } else if (!dataEntrega.equals("  /  /    ") && status.getAcao() != 1) {
                throw new NumberFormatException("Selecione um status válido ou insira data de entrega vazia!");
            } else if (dataEntrega.equals("  /  /    ") && status.getAcao() == 1) {
                throw new NumberFormatException("Insira a data de entrega ou mude o status!");
            } else {
                ordemServico.setDataEntrega(null);
            }

            ordemServico.setStatus(status);
            Pessoa pessoaResponsavel = (Pessoa) jcbResponsavel.getSelectedItem();
            ordemServico.setResponsavel(pessoaResponsavel);
            Segmento segmento = (Segmento) jcbSegmento.getSelectedItem();
            ordemServico.setSegmento(segmento);
            Cidade cidade = (Cidade) jcbCidade.getSelectedItem();
            Estado estado = (Estado) jcbEstado.getSelectedItem();
            cidade.setEstado(estado);
            ordemServico.setCidade(cidade);
            ordemServico.setPrioridade(Integer.parseInt(jtfTotal.getText()));
            venda.setValorFaturamentoDireto(Double.parseDouble(jtfValorFaturamentoDireto.getText().replaceAll("\\.", "").replace(",", ".")));
            venda.setValorFaturamentoDolphin(Double.parseDouble(jtfValorMaoDeObra.getText().replaceAll("\\.", "").replace(",", ".")));
            venda.setValorComissao(Double.parseDouble(jtfValorComissao.getText().replaceAll("\\.", "").replace(",", ".")));
            ordemServico.setVenda(venda);

            ordemServico.setPrincipal(chPrincipal.isSelected());

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void cadastrarAdicional() throws Exception {

        AdicionalDAO adicionalDAO = new AdicionalDAO();

        try {
            if (ordemServico.getProjeto() == null) {
                throw new Exception("Selecione o projeto!");
            } else {

                adicional = new Adicional();

                adicional.setProjeto(ordemServico.getProjeto());
                int numeroAdicional;
                if (jchAdicional.isSelected()) {
                    numeroAdicional = adicionalDAO.buscarMaiorNumero(adicional.getProjeto().getId()) + 1;
                } else {
                    numeroAdicional = 0;
                }
                adicional.setNumero(numeroAdicional);

                jtfAdicional.setText(String.valueOf(numeroAdicional));

                adicional.setId(new AdicionalDAO().inserir(adicional));

            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void cadastrarProjeto() throws SQLException {
        Projeto proj = new Projeto();
        proj.setId(new ProjetoDAO().inserir());
        ordemServico.setProjeto(proj);
        jtfCodProjeto.setText(String.valueOf(proj.getId()));
    }

    private void cadastrar() {
        try {
            ordemServico = new OrdemServico();
            setTarefa();

            TipoOs tipo = (TipoOs) jcbTipo.getSelectedItem();
            if (tipo.getCodTipoOs() == 16) {
                if (jtfCodProjeto.getText().isEmpty()) {
                    throw new NumberFormatException("Selecione o projeto!");
                }
            }

            if (projeto == null) {
                projeto = new Projeto();
                projeto.setId(0);
            }

            ordemServico.setProjeto(projeto);

            //Verifica se o tipo é comercial
            if (ordemServico.getTipoOs().getCodTipoOs() == 21) {
                if (!jchAdicional.isSelected()) {
                    cadastrarProjeto();
                }

                cadastrarAdicional();

            }

            ordemServico.setAdicional(adicional);

            ordemServico.setCodOs(osdao.cadastrarOrdemServico(ordemServico));

            jtfCodOs.setText(String.valueOf(ordemServico.getCodOs()));
            ordemServicoAntiga.setCodOs(ordemServico.getCodOs());

            jTabbedPane1.setEnabledAt(2, true);
            jtaComentariosOS.setEnabled(true);
            jTabbedPane1.setEnabledAt(4, true);

            sdao.inserirSeguidoresGerente(ordemServico);
            sdao.inserirSeguidoresResponsavel(ordemServico);
            sdao.inserirSeguidoresSolicitante(ordemServico);

            buscarSeguidores();

            buscarComentarios();

            buscarContatos();
            jTabbedPane1.setEnabledAt(6, true);
            uiControleOs.getOsTableModel().addRow(ordemServico);

            jchAdicional.setEnabled(false);
            jchAdicional.setSelected(false);

            jcbTipo.setEnabled(false);

            jbPesquisarProjeto.setEnabled(false);

            OrdemServicoCriterioDAO ordemServicoCriterioDAO = new OrdemServicoCriterioDAO();
            ArrayList<Criterio> criterios = ordemServico.getCriterios();
            criterios.add((Criterio) jcbCriterio1.getSelectedItem());
            criterios.add((Criterio) jcbCriterio2.getSelectedItem());
            criterios.add((Criterio) jcbCriterio3.getSelectedItem());
            criterios.add((Criterio) jcbCriterio4.getSelectedItem());
            criterios.add((Criterio) jcbCriterio5.getSelectedItem());

            for (Criterio criterio : criterios) {
                ordemServicoCriterioDAO.inserir(ordemServico.getCodOs(), criterio.getId());
            }

            JOptionPane.showMessageDialog(this,
                    "Tarefa cadastrada!",
                    "",
                    JOptionPane.INFORMATION_MESSAGE);

            if (salvarEFechar) {
                this.dispose();
            }

        } catch (Exception e) {
            ordemServico = null;
            Logger.getLogger(UITarefas.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro ao cadastrar tarefa!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editar() {
        try {
            setTarefa();

            editarCriterios();

            osdao.alterar(ordemServico);

            registrarHistoricoDeStatus();
            JOptionPane.showMessageDialog(this,
                    "Tarefa editada!",
                    "",
                    JOptionPane.INFORMATION_MESSAGE);
            if (salvarEFechar) {
                this.dispose();
            }
        } catch (Exception e) {

            Logger.getLogger(UITarefas.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null,
                    e.getMessage(),
                    "Erro ao editar tarefa!",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    private void editarCriterios() {
        try {
            OrdemServicoCriterioDAO ordemServicoCriterioDAO = new OrdemServicoCriterioDAO();
            ArrayList<Criterio> criteriosAntigos = ordemServico.getCriterios();
            ArrayList<Criterio> criteriosNovos = new ArrayList<>();

            criteriosNovos.add((Criterio) jcbCriterio1.getModel().getSelectedItem());
            criteriosNovos.add((Criterio) jcbCriterio2.getModel().getSelectedItem());
            criteriosNovos.add((Criterio) jcbCriterio3.getModel().getSelectedItem());
            criteriosNovos.add((Criterio) jcbCriterio4.getModel().getSelectedItem());
            criteriosNovos.add((Criterio) jcbCriterio5.getModel().getSelectedItem());

            for (int i = 0; i < 5; i++) {
                Criterio criterioNovo1 = criteriosNovos.get(i);
                Criterio criterioAntigo1 = criteriosAntigos.get(i);

                ordemServicoCriterioDAO.alterar(ordemServico.getCodOs(),
                        criterioAntigo1.getId(),
                        criterioNovo1.getId());

            }

        } catch (SQLException ex) {
            Logger.getLogger(UITarefas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,
                    ex.getMessage(),
                    "Erro ao editar criterios da tarefa!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvar() {
        if (ordemServico == null) {
            cadastrar();
        } else {
            editar();
        }
        uiControleOs.filtrarOS();
        campoEditado = false;
    }

    public Apontamento getApontamentoSelecionado() {
        if (jtaApontamentos.getSelectedRow() == -1) {
            return null;
        }

        return aTableModel.getApontamentos().get(jtaApontamentos.getSelectedRow());
    }

    private Seguidor getSeguidorSelecionado() {
        if (jtSeguidores.getSelectedRow() == -1) {
            return null;
        }

        return seguidoresTableModel.getSeguidor().get(jtSeguidores.getSelectedRow());
    }

    private Contato getContatoSelecionado() {
        if (tblOrdemServicoContatos.getSelectedRow() == -1) {
            return null;
        }

        return contatoTableModel.getContatos().get(tblOrdemServicoContatos.getSelectedRow());
    }

    public JTextField getJtfCliente() {
        return jtfCliente;
    }

    public JTextField getJtfCodCliente() {
        return jtfCodCliente;
    }

    public JTextField getJtfCodColigada() {
        return jtfCodColigada;
    }

    public JComboBox<Cidade> getJcbCidade() {
        return jcbCidade;
    }

    public JComboBox<Estado> getJcbEstado() {
        return jcbEstado;
    }

    public void removerApontOS() {
        try {
            ApontamentoDAO dao = DAOFactory.getAPONTAMENTODAO();
            Apontamento a = getApontamentoSelecionado();
            Pessoa p = new Pessoa();
            StatusApont sa = new StatusApont();
            CentroCusto c = new CentroCusto();
            OrdemServico os = new OrdemServico();

            c.setCodCusto("-");
            sa.setCodStatusApont("-");
            a.setStatusApont(sa);
            p.setCodPessoa(1);

            a.setLider(p);
            a.setCentroCusto(c);
            a.setOrdemServico(os);
            a.setAtividade(null);
            dao.alterar(a);
            JOptionPane.showMessageDialog(this, "Apontamento removido");
            aTableModel.removeRow(a);
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao remover apontamento da OS",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerSeguidor() {
        try {

            Seguidor seguidor = getSeguidorSelecionado();

            if (seguidor != null) {
                SeguidoresDAO dao = DAOFactory.getSEGUIDORESDAO();

                dao.excluir(seguidor);

                seguidoresTableModel.removeRow(seguidor);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Selecione o seguidor para remover",
                        "Erro ao remover seguidor",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao remover apontamento da OS",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerContato() {
        try {

            Contato contato = getContatoSelecionado();

            if (contato != null) {
                OrdemServicoContatoController controller = new OrdemServicoContatoController();

                controller.excluir(ordemServico.getCodOs(), contato.getId());

                contatoTableModel.removeRow(contato);

            } else {
                JOptionPane.showMessageDialog(this,
                        "Selecione o contato para remover",
                        "Remover contato",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao remover contato",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarComentario() {
        try {
            ComentarioDAO dao = DAOFactory.getCOMENTARIODAO();

            comentario = new Comentario();

            if (jtaComentariosOS.getText().isEmpty()) {
                throw new NumberFormatException("Comentário em branco!");
            }

            comentario.setOrdemServico(ordemServico);
            comentario.setDescricao(jtaComentariosOS.getText());
            comentario.setCreatedBy(Menu.logado.getLogin());
            comentario.setCreatedOn(LocalDateTime.now());

            comentario.setCodComentario(dao.salvar(comentario));

            ordemServico.setComentarios(
                    dao.buscarComentariosDaOs(ordemServico.getCodOs()));

            cOSTableModel.clear();
            buscarComentarios();

            JOptionPane.showMessageDialog(this, "Comentário salvo!", "", JOptionPane.INFORMATION_MESSAGE);

            atualizarTblComentarios();

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao salvar comentário", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ne) {
            JOptionPane.showMessageDialog(this, ne.getMessage(), "",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void atualizarTblComentarios() {
        limparComentario();
        cOSTableModel.clear();
        buscarComentarios();
    }

    private void buscarComentarios() {

        try {
            if (ordemServico != null) {

                if (ordemServico.getComentarios() != null) {
                    for (Comentario coment : ordemServico.getComentarios()) {
                        cOSTableModel.addRow(coment);
                    }//for
                }
                jtComentarios.setModel(cOSTableModel);
            } else {
                jtaComentariosOS.setEnabled(false);
            }

        } catch (Exception se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao buscar comentários", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparComentario() {
        jtaComentariosOS.setText("");
    }

    private void excluirComentario() {
        try {
            Comentario c = getComentario();

            if (c != null) {

                ComentarioDAO dao = DAOFactory.getCOMENTARIODAO();

                int codComentario = c.getCodComentario();

                dao.deletar(codComentario);

                cOSTableModel.removeRow(c);

            } else {
                JOptionPane.showMessageDialog(this, "Selecione o comentário que deseja excluir!");
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao exlcuir", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Comentario getComentario() {
        if (jtComentarios.getSelectedRow() == -1) {
            return null;
        }

        return cOSTableModel.getComentarios().get(jtComentarios.getSelectedRow());
    }

    private void preencherCampoComentario() {
        jtaComentariosOS.setText(getComentario().getDescricao());
    }

    private void transacao() {
        if (campoEditado == true) {
            Object[] options = {"Sim", "Não"};
            int i = JOptionPane.showOptionDialog(null,
                    "Deseja salvar os dados antes de fechar?",
                    "Fechando janela", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (i == JOptionPane.YES_OPTION) {
                salvar();
            }
        }
    }

    private void darPermissoes() {
        TipoOs tipo = (TipoOs) jcbTipo.getSelectedItem();
        if (tipo.getDescricao().equalsIgnoreCase("Orçamento") || tipo.getDescricao().equalsIgnoreCase("Comercial")) {

            if (Menu.logado.isPermVenda()) {
                jTabbedPane1.setEnabledAt(5, true);
            }

        } else {
            jTabbedPane1.setEnabledAt(5, false);
        }

        if (Menu.logado.isPermCustoMO()) {
            jlCusto.setVisible(true);
        } else {
            jlCusto.setVisible(false);
        }
    }

    private void enviarEmail() {
        Menu.carregamento(true);
        new Thread() {
            @Override
            public void run() {

                if (ordemServico == null) {
                    Menu.carregamento(false);
                    return;
                }
                if (ordemServico.getSeguidores() == null) {
                    Menu.carregamento(false);
                    return;
                }
                AlteracoesOsDAO dao = DAOFactory.getALTERACOESOSDAO();
                ComentarioDAO comentarioDAO = DAOFactory.getCOMENTARIODAO();
                ArrayList<Comentario> comentariosNaoEnviadosPorEmail;
                AlteracoesOs alteracao = new AlteracoesOs();

                DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                boolean controle;
                boolean alterado = false;
                for (Seguidor seguidor : ordemServico.getSeguidores()) {
                    controle = true;

                    if (seguidor == null) {
                        Menu.carregamento(false);
                        return;
                    }

                    if (seguidor.getPessoa().getEmail() == null) {
                        Menu.carregamento(false);
                        return;
                    }

                    if (ordemServicoAntiga == null) {
                        Menu.carregamento(false);
                        return;
                    }

                    if (ordemServico == ordemServicoAntiga) {
                        Menu.carregamento(false);
                        return;
                    }

                    if (ordemServicoAntiga.getStatus() == null) {
                        Menu.carregamento(false);
                        return;
                    }

                    if (ordemServico.getStatus() == null) {
                        Menu.carregamento(false);
                        return;
                    }

                    EmailTotvs email = new EmailTotvs();
                    String msgStatus = "";
                    String msgComentario = "";
                    String msgDataNecessidade = "";
                    String msgDataPrevEntrega = "";
                    String msgDataEntrega = "";
                    String msgDataLiberacao = "";
                    String descricao = "";

                    if (ordemServicoAntiga.getStatus().getCodStatus() != ordemServico.getStatus().getCodStatus()) {
                        alterado = true;
                        msgStatus = "<p style=\"font-family: Arial, Helvetica, sans-serif;font-size: 1em;color: lightslategray;margin-bottom: 10px;\">Status alterado de " + ordemServicoAntiga.getStatus().getNome() + " --> "
                                + ordemServico.getStatus().getNome() + ".</p>\n";
                        descricao = "Status alterado de " + ordemServicoAntiga.getStatus().getNome() + " --> "
                                + ordemServico.getStatus().getNome() + ". \n";
                    }

                    //se as duas data nao sao vazias
                    if (ordemServicoAntiga.getDataNecessidade() != null && ordemServico.getDataNecessidade() != null) {
                        //se nao é igual
                        if (!ordemServicoAntiga.getDataNecessidade().isEqual(ordemServico.getDataNecessidade())) {
                            alterado = true;
                            msgDataNecessidade = "<p style=\"font-family: Arial, Helvetica, sans-serif;font-size: 1em;color: lightslategray;margin-bottom: 10px;\">Data da necessidade alterada de " + ordemServicoAntiga.getDataNecessidade().format(formatoData) + " --> " + ordemServico.getDataNecessidade().format(formatoData) + ".</p>";
                            descricao += "Data da necessidade alterada de " + ordemServicoAntiga.getDataNecessidade() + " --> " + ordemServico.getDataNecessidade() + ". \n";
                        }
                        //se data antiga nao é vazio
                    } else if (ordemServicoAntiga.getDataNecessidade() != null) {
                        alterado = true;
                        msgDataNecessidade = "<p style=\"font-family: Arial, Helvetica, sans-serif;font-size: 1e;color: lightslategray;margin-bottom: 10px;\">Data da necessidade alterada de " + ordemServicoAntiga.getDataNecessidade().format(formatoData) + " --> vazio.</p>";
                        descricao += "Data da necessidade alterada de " + ordemServicoAntiga.getDataNecessidade() + " --> vazio. \n";
                    } else if (ordemServico.getDataNecessidade() != null) {
                        alterado = true;
                        msgDataNecessidade = "<p style=\"font-family: Arial, Helvetica, sans-serif;font-size: 1em;color: lightslategray;margin-bottom: 10px;\">Data da necessidade alterada de vazio --> " + ordemServico.getDataNecessidade().format(formatoData) + ".</p>";
                        descricao += "Data da necessidade alterada de vazio --> " + ordemServico.getDataNecessidade() + ". \n";
                    }//else

                    if (ordemServicoAntiga.getDataPrevEntrega() != null && ordemServico.getDataPrevEntrega() != null) {
                        if (!ordemServicoAntiga.getDataPrevEntrega().isEqual(ordemServico.getDataPrevEntrega())) {
                            alterado = true;
                            msgDataPrevEntrega = "<p style=\"font-family: Arial, Helvetica, sans-serif;font-size: 1em;color: lightslategray;margin-bottom: 10px;\">Data da previsão de entrega alterada de "
                                    + ordemServicoAntiga.getDataPrevEntrega().format(formatoData)
                                    + " --> " + ordemServico.getDataPrevEntrega().format(formatoData) + ".</p>";
                            descricao += "Data da previsão de entrega alterada de " + ordemServicoAntiga.getDataPrevEntrega() + " --> " + ordemServico.getDataPrevEntrega() + ". \n";
                        }
                    } else if (ordemServicoAntiga.getDataPrevEntrega() != null) {
                        alterado = true;
                        msgDataPrevEntrega = "<p style=\"font-family: Arial, Helvetica, sans-serif;font-size: 1em;color: lightslategray;margin-bottom: 10px;\">Data da previsão de entrega alterada de " + ordemServicoAntiga.getDataPrevEntrega().format(formatoData) + " --> vazio.</p>";
                        descricao += "Data da previsão de entrega alterada de " + ordemServicoAntiga.getDataPrevEntrega() + " --> vazio. \n";
                    } else if (ordemServico.getDataPrevEntrega() != null) {
                        alterado = true;
                        msgDataPrevEntrega = "<p style=\"font-family: Arial, Helvetica, sans-serif;font-size: 1em;color: lightslategray;margin-bottom: 10px;\">Data da previsão de entrega alterada de vazio --> " + ordemServico.getDataPrevEntrega().format(formatoData) + ".</p>";
                        descricao += "Data da previsão de entrega alterada de vazio --> " + ordemServico.getDataPrevEntrega() + ". \n";
                    }

                    if (ordemServicoAntiga.getDataEntrega() != null && ordemServico.getDataEntrega() != null) {
                        if (!ordemServicoAntiga.getDataEntrega().isEqual(ordemServico.getDataEntrega())) {
                            alterado = true;
                            msgDataEntrega = "<p style=\"font-family: Arial, Helvetica, sans-serif;font-size: 1em;color: lightslategray;margin-bottom: 10px;\">Data da entrega alterada de " + ordemServicoAntiga.getDataEntrega().format(formatoData) + " --> " + ordemServico.getDataEntrega().format(formatoData) + ".</p>";
                            descricao += "Data da entrega alterada de " + ordemServicoAntiga.getDataEntrega() + " --> " + ordemServico.getDataEntrega() + ". \n";
                        }
                    } else if (ordemServicoAntiga.getDataEntrega() != null) {
                        alterado = true;
                        msgDataEntrega = "<p style=\"font-family: Arial, Helvetica, sans-serif;font-size: 1em;color: lightslategray;margin-bottom: 10px;\">Data da entrega alterada de " + ordemServicoAntiga.getDataEntrega().format(formatoData) + " --> vazio.</p>";
                        descricao += "Data da entrega alterada de " + ordemServicoAntiga.getDataEntrega() + " --> vazio. \n";
                    } else if (ordemServico.getDataEntrega() != null) {
                        alterado = true;
                        msgDataEntrega = "<p style=\"font-family: Arial, Helvetica, sans-serif;font-size: 1em;color: lightslategray;margin-bottom: 10px;\">Data da entrega alterada de vazio --> " + ordemServico.getDataEntrega().format(formatoData) + ".</p>";
                        descricao += "Data da entrega alterada de vazio --> " + ordemServico.getDataEntrega() + ". \n";
                    }

                    try {
                        comentariosNaoEnviadosPorEmail = comentarioDAO.buscarComentariosNaoEnviadosPorEmail(ordemServico.getCodOs());
                        for (Comentario comentarioNaoEnviado : comentariosNaoEnviadosPorEmail) {
                            alterado = true;
                            if (controle) {
                                msgComentario = "<p style=\"font-family: Arial, Helvetica, sans-serif;font-size: 1em;text-align: justify;color: lightslategray;\">@" + comentarioNaoEnviado.getCreatedBy() + " fez um comentário</p>"
                                        + "<p style=\"font-family: Arial, Helvetica, sans-serif;font-size: 1em;text-align: justify;color: lightslategray;\">Comentário: </p>";
                                controle = false;
                            }
                            msgComentario += "<div style=\"background-color: cornflowerblue;margin: 10px 15% 5% 5%;padding: 0.5% 5%;border-radius: 0px 5px 5px 5px;\">\n"
                                    + "<p style=\"text-align: right;color: white;\">" + comentarioNaoEnviado.getCreatedOn().format(formatoDataHora) + "</p>"
                                    + "<p style=\"font-family: Arial, Helvetica, sans-serif;font-size: 1em;text-align: justify;color: white;\">" + comentarioNaoEnviado.getDescricao() + "</p>\n"
                                    + "</div>\n";
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null,
                                ex.getMessage(),
                                "Erro ao buscar emails não enviados",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    String msg = "<!DOCTYPE html>\n"
                            + "<html>\n"
                            + "    <head>\n"
                            + "         <meta charset=\"UTF-8\"/>\n"
                            + "         <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                            + "    </head>\n"
                            + "    <body>\n"
                            + "            <h1 style=\"font-size:1.25em;font-family: Arial, Helvetica, sans-serif;text-align: center;margin-bottom: 15px;color: #4484ce;\">Dolphin Controle</h1>"
                            + "            <h2 style=\"font-size: 1.875em;font-family: Arial, Helvetica, sans-serif;text-align: center;margin-bottom: 10px;color: lightslategray;\">Olá " + seguidor.getPessoa().getNome() + "!</h2>\n"
                            + "            <p style=\"font-family: Arial, Helvetica, sans-serif;font-size: 1em;text-align: center;color: lightslategray;margin-bottom: 10px;\">" + Menu.getUiLogin().getPessoa().getNome() + " fez alterações na OS/Tarefa #" + ordemServico.getCodOs() + " - " + ordemServico.getNome() + ".</p>\n"
                            + msgStatus
                            + msgDataNecessidade
                            + msgDataPrevEntrega
                            + msgDataEntrega
                            + msgDataLiberacao
                            + msgComentario
                            + "<p style=\"font-family: Arial, Helvetica, sans-serif;font-size: 1em;text-align: center; color: lightslategrey;\">Tenha um ótimo dia de trabalho!</p>"
                            + "    </body>\n"
                            + "</html>";

                    try {
                        if (alterado) {
                            email.enviarEmail("guilherme.oliveira@dse.com.br",
                                    "Alterações na OS/Tarefa #" + ordemServico.getCodOs()
                                    + " - " + ordemServico.getNome() + ".",
                                    msg, seguidor.getPessoa().getEmail());

                            comentarioDAO.alterarEmailEnviado(ordemServico.getCodOs());

                            alteracao.setDescricao(descricao);
                            alteracao.setOrdemServico(ordemServico);
                            alteracao.setUsuario(Menu.getUiLogin().getPessoa().getLogin());

                            dao.inserir(alteracao);

                        }
                    } catch (EmailException ex) {
                        JOptionPane.showMessageDialog(null,
                                ex.getMessage(),
                                "Erro ao enviar email",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException se) {
                        JOptionPane.showMessageDialog(null,
                                se.getMessage(),
                                "Erro ao registrar alterações",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                try {
                    if (alterado) {
                        comentarioDAO.alterarEmailEnviado(ordemServico.getCodOs());
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(),
                            "",
                            JOptionPane.ERROR_MESSAGE);
                }

                Menu.carregamento(false);

            }
        }.start();
    }

    private void registrarHistoricoDeStatus() throws SQLException {
        HistoricoStatus historico = new HistoricoStatus();
        HistoricoStatusDAO historicoStatusDAO = DAOFactory.getHISTORICOSTATUSDAO();

        historico.setOrdemServico(ordemServico);
        historico.setStatusNew(ordemServico.getStatus());
        historico.setStatusOld(ordemServicoAntiga.getStatus());
        historico.setData(LocalDateTime.now());
        historico.setUsuario(Menu.getUiLogin().getPessoa());

        historicoStatusDAO.inserir(historico);
    }

    private void habilitarIconesCliente(Cliente cliente) {
        btnInteracaoFone.setEnabled(cliente.isInteracaoFone());
        btnInteracaoMsg.setEnabled(cliente.isInteracaoMsg());
        btnInteracaoReuniao.setEnabled(cliente.isInteracaoReuniao());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jpDados = new javax.swing.JPanel();
        jcbTipo = new javax.swing.JComboBox<>();
        jlTipo = new javax.swing.JLabel();
        jlNome = new javax.swing.JLabel();
        jtfNome = new javax.swing.JTextField();
        jtfCodOs = new javax.swing.JTextField();
        jlCodOs = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jspClientes = new javax.swing.JScrollPane();
        jlstClientes = new javax.swing.JList<>();
        jspCustos = new javax.swing.JScrollPane();
        jlstCustos = new javax.swing.JList<>();
        jtfCodCusto = new javax.swing.JTextField();
        jlCodCusto = new javax.swing.JLabel();
        jlCentroCusto = new javax.swing.JLabel();
        jtfCentroCusto = new javax.swing.JTextField();
        jftfDataSolicitacao = new javax.swing.JFormattedTextField();
        jlDataSolicitacao = new javax.swing.JLabel();
        jftfDataNecessidade = new javax.swing.JFormattedTextField();
        jlDataNecessidade = new javax.swing.JLabel();
        jtfObra = new javax.swing.JTextField();
        jlObra = new javax.swing.JLabel();
        jcbSolicitante = new javax.swing.JComboBox<>();
        jlSolicitante = new javax.swing.JLabel();
        jlCodCliente = new javax.swing.JLabel();
        jtfCodCliente = new javax.swing.JTextField();
        jtfCliente = new javax.swing.JTextField();
        jlCliente = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jtfCodColigada = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        chPrincipal = new javax.swing.JCheckBox();
        jlSegmento = new javax.swing.JLabel();
        jcbSegmento = new javax.swing.JComboBox<>();
        jlCidade = new javax.swing.JLabel();
        jcbCidade = new javax.swing.JComboBox<>();
        jcbEstado = new javax.swing.JComboBox<>();
        jlEstado = new javax.swing.JLabel();
        jchAdicional = new javax.swing.JCheckBox();
        jtfCodProjeto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jbPesquisarProjeto = new javax.swing.JButton();
        jtfAdicional = new javax.swing.JTextField();
        jpExecucao = new javax.swing.JPanel();
        jlDataInicio = new javax.swing.JLabel();
        jftfDataInicio = new javax.swing.JFormattedTextField();
        jlDataPrevEntrega = new javax.swing.JLabel();
        jftfDataPrevEntrega = new javax.swing.JFormattedTextField();
        jlDataEntrega = new javax.swing.JLabel();
        jftfDataEntrega = new javax.swing.JFormattedTextField();
        jlStatus = new javax.swing.JLabel();
        jcbStatus = new javax.swing.JComboBox<>();
        jlResponsavel = new javax.swing.JLabel();
        jcbResponsavel = new javax.swing.JComboBox<>();
        jcbCriterio1 = new javax.swing.JComboBox<>();
        jlCriterio1 = new javax.swing.JLabel();
        jcbCriterio2 = new javax.swing.JComboBox<>();
        jlCriterio2 = new javax.swing.JLabel();
        jcbCriterio3 = new javax.swing.JComboBox<>();
        jlCriterio3 = new javax.swing.JLabel();
        jcbCriterio4 = new javax.swing.JComboBox<>();
        jlCriterio4 = new javax.swing.JLabel();
        jcbCriterio5 = new javax.swing.JComboBox<>();
        jlCriterio5 = new javax.swing.JLabel();
        jtfTotal = new javax.swing.JTextField();
        jlTotal = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtaObservacoes = new javax.swing.JTextArea();
        lblObservacoes = new javax.swing.JLabel();
        cbxMotivoDaPerda = new javax.swing.JComboBox<>();
        lblMotivoDaPerda = new javax.swing.JLabel();
        jpApontamentos = new javax.swing.JPanel();
        jspScrollApont = new javax.swing.JScrollPane();
        jtaApontamentos = new javax.swing.JTable();
        jbAdicionar = new javax.swing.JButton();
        jbRemover = new javax.swing.JButton();
        jlCusto = new javax.swing.JLabel();
        jbComentarios = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaComentariosOS = new javax.swing.JTextArea();
        jlComentario = new javax.swing.JLabel();
        jbExcluir = new javax.swing.JButton();
        jbComentar = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtComentarios = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jftfDataInteracao = new javax.swing.JFormattedTextField();
        btnComentariosSugeridos = new javax.swing.JButton();
        btnInteracaoFone = new javax.swing.JToggleButton();
        btnInteracaoMsg = new javax.swing.JToggleButton();
        btnInteracaoReuniao = new javax.swing.JToggleButton();
        jpSeguidores = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jtSeguidores = new javax.swing.JTable();
        jbAdicionarSeguidores = new javax.swing.JButton();
        jbRemoverSeguidores = new javax.swing.JButton();
        jpVenda = new javax.swing.JPanel();
        jlpValores = new javax.swing.JLayeredPane();
        jlValorFaturamentoDireto = new javax.swing.JLabel();
        jlValorFaturamentoDolphin = new javax.swing.JLabel();
        jlValorTotal = new javax.swing.JLabel();
        jtfValorFaturamentoDireto = new utilitarios.JMoneyFieldValor();
        jtfValorMaoDeObra = new utilitarios.JMoneyFieldValor();
        jtfValorTotal = new utilitarios.JMoneyFieldValor();
        jlValorComissao = new javax.swing.JLabel();
        jtfValorComissao = new utilitarios.JMoneyFieldValor();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrdemServicoContatos = new javax.swing.JTable();
        btnRemoverContato = new javax.swing.JButton();
        btnAdicionarContato = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jbSalvarEFechar = new javax.swing.JButton();
        jbSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tarefas");
        setModal(true);
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jcbTipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbTipoItemStateChanged(evt);
            }
        });

        jlTipo.setText("Tipo*");

        jlNome.setText("Nome*");

        jtfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfNomeKeyPressed(evt);
            }
        });

        jtfCodOs.setEditable(false);

        jlCodOs.setText("Código");

        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlstClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlstClientesMouseClicked(evt);
            }
        });
        jspClientes.setViewportView(jlstClientes);

        jPanel1.add(jspClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, 510, 90));

        jlstCustos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlstCustosMouseClicked(evt);
            }
        });
        jspCustos.setViewportView(jlstCustos);

        jPanel1.add(jspCustos, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 610, 90));

        jtfCodCusto.setEditable(false);
        jPanel1.add(jtfCodCusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 90, 30));

        jlCodCusto.setText("Cód. Custo");
        jPanel1.add(jlCodCusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, -1, -1));

        jlCentroCusto.setText("Centro de Custo *");
        jPanel1.add(jlCentroCusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, -1, -1));

        jtfCentroCusto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfCentroCustoFocusGained(evt);
            }
        });
        jtfCentroCusto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfCentroCustoKeyPressed(evt);
            }
        });
        jPanel1.add(jtfCentroCusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 610, 30));

        try {
            jftfDataSolicitacao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfDataSolicitacao.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        jftfDataSolicitacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfDataSolicitacaoKeyPressed(evt);
            }
        });
        jPanel1.add(jftfDataSolicitacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 80, 200, 30));

        jlDataSolicitacao.setText("Data da Solicitação*");
        jPanel1.add(jlDataSolicitacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 60, -1, -1));

        try {
            jftfDataNecessidade.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfDataNecessidade.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        jftfDataNecessidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfDataNecessidadeKeyPressed(evt);
            }
        });
        jPanel1.add(jftfDataNecessidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 20, 200, 30));

        jlDataNecessidade.setText("Data da Necessidade");
        jPanel1.add(jlDataNecessidade, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 0, -1, -1));

        jtfObra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfObraKeyPressed(evt);
            }
        });
        jPanel1.add(jtfObra, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 560, 30));

        jlObra.setText("Obra *");
        jPanel1.add(jlObra, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, -1, -1));

        jPanel1.add(jcbSolicitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 140, 440, 30));

        jlSolicitante.setText("Solicitante *");
        jPanel1.add(jlSolicitante, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 120, -1, -1));

        jlCodCliente.setText("Cód. Cliente");
        jPanel1.add(jlCodCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jtfCodCliente.setEditable(false);
        jPanel1.add(jtfCodCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 90, 30));

        jtfCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtfClienteFocusGained(evt);
            }
        });
        jtfCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfClienteKeyPressed(evt);
            }
        });
        jPanel1.add(jtfCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 510, 30));

        jlCliente.setText("Cliente");
        jPanel1.add(jlCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, -1, -1));

        jLabel1.setText("Coligada");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, -1, -1));

        jtfCodColigada.setEditable(false);
        jPanel1.add(jtfCodColigada, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 90, 30));

        jButton1.setText("Pesquisar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, -1, 30));

        jButton2.setText("Pesquisar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 80, -1, 30));

        chPrincipal.setSelected(true);
        chPrincipal.setText("Principal");
        jPanel1.add(chPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, -1, -1));

        jlSegmento.setText("Segmento");

        jlCidade.setText("Cidade");

        jcbEstado.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbEstadoItemStateChanged(evt);
            }
        });

        jlEstado.setText("Estado");

        jchAdicional.setText("Adicional");
        jchAdicional.setEnabled(false);
        jchAdicional.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jchAdicionalItemStateChanged(evt);
            }
        });

        jtfCodProjeto.setEnabled(false);

        jLabel3.setText("Código do Projeto");

        jbPesquisarProjeto.setText("Pesquisar");
        jbPesquisarProjeto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPesquisarProjetoActionPerformed(evt);
            }
        });

        jtfAdicional.setEnabled(false);

        javax.swing.GroupLayout jpDadosLayout = new javax.swing.GroupLayout(jpDados);
        jpDados.setLayout(jpDadosLayout);
        jpDadosLayout.setHorizontalGroup(
            jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpDadosLayout.createSequentialGroup()
                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpDadosLayout.createSequentialGroup()
                                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfCodProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jchAdicional)
                                    .addGroup(jpDadosLayout.createSequentialGroup()
                                        .addComponent(jtfAdicional, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jbPesquisarProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcbSegmento, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlSegmento))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jpDadosLayout.createSequentialGroup()
                                .addComponent(jtfCodOs, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfNome)
                                .addGap(10, 10, 10))
                            .addGroup(jpDadosLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jlCodOs)
                                .addGap(72, 72, 72)
                                .addComponent(jlNome)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlTipo)
                            .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jcbTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jpDadosLayout.createSequentialGroup()
                                    .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jcbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jlEstado))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jlCidade)
                                        .addComponent(jcbCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(18, 18, 18))
        );
        jpDadosLayout.setVerticalGroup(
            jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCodOs)
                    .addComponent(jlNome)
                    .addComponent(jlTipo))
                .addGap(6, 6, 6)
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfCodOs, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlSegmento)
                    .addComponent(jlEstado)
                    .addComponent(jlCidade)
                    .addComponent(jLabel3)
                    .addComponent(jchAdicional))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfAdicional, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jcbSegmento, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcbCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtfCodProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbPesquisarProjeto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addGap(54, 54, 54))
        );

        jTabbedPane1.addTab("Dados", jpDados);

        jlDataInicio.setText("Início*");

        try {
            jftfDataInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfDataInicio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfDataInicioKeyPressed(evt);
            }
        });

        jlDataPrevEntrega.setText("Prev. Fechamento*");

        try {
            jftfDataPrevEntrega.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfDataPrevEntrega.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfDataPrevEntregaKeyPressed(evt);
            }
        });

        jlDataEntrega.setText("Data de Fechamento");

        try {
            jftfDataEntrega.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfDataEntrega.setFocusLostBehavior(javax.swing.JFormattedTextField.PERSIST);
        jftfDataEntrega.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfDataEntregaKeyPressed(evt);
            }
        });

        jlStatus.setText("Status*");

        jlResponsavel.setText("Responsável*");

        jcbCriterio1.setPreferredSize(new java.awt.Dimension(170, 20));
        jcbCriterio1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbCriterio1ItemStateChanged(evt);
            }
        });
        jcbCriterio1.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                jcbCriterio1VetoableChange(evt);
            }
        });

        jlCriterio1.setText("Tipo de Cliente");

        jcbCriterio2.setPreferredSize(new java.awt.Dimension(170, 20));
        jcbCriterio2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbCriterio2ItemStateChanged(evt);
            }
        });

        jlCriterio2.setText("Fase de Oportunidade");

        jcbCriterio3.setPreferredSize(new java.awt.Dimension(170, 20));
        jcbCriterio3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbCriterio3ItemStateChanged(evt);
            }
        });

        jlCriterio3.setText("Tipo de Escopo");

        jcbCriterio4.setPreferredSize(new java.awt.Dimension(170, 20));
        jcbCriterio4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbCriterio4ItemStateChanged(evt);
            }
        });

        jlCriterio4.setText("Prazo de Pagamento");

        jcbCriterio5.setPreferredSize(new java.awt.Dimension(170, 20));
        jcbCriterio5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcbCriterio5ItemStateChanged(evt);
            }
        });

        jlCriterio5.setText("Localização da Obra");

        jtfTotal.setEnabled(false);

        jlTotal.setText("Total");

        txtaObservacoes.setColumns(20);
        txtaObservacoes.setRows(5);
        txtaObservacoes.setEnabled(false);
        jScrollPane3.setViewportView(txtaObservacoes);

        lblObservacoes.setText("Observações");

        cbxMotivoDaPerda.setEnabled(false);

        lblMotivoDaPerda.setText("Motivo da Perda");

        javax.swing.GroupLayout jpExecucaoLayout = new javax.swing.GroupLayout(jpExecucao);
        jpExecucao.setLayout(jpExecucaoLayout);
        jpExecucaoLayout.setHorizontalGroup(
            jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpExecucaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jcbCriterio5, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jcbCriterio4, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jcbCriterio3, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jcbCriterio2, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jcbCriterio1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpExecucaoLayout.createSequentialGroup()
                            .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jlDataInicio)
                                .addComponent(jftfDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jlDataPrevEntrega)
                                .addComponent(jftfDataPrevEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jlDataEntrega)
                                .addComponent(jftfDataEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jcbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jlStatus))))
                    .addComponent(jlCriterio1)
                    .addComponent(jlCriterio2)
                    .addComponent(jlCriterio3)
                    .addComponent(jlCriterio4)
                    .addComponent(jlCriterio5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbResponsavel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                    .addComponent(cbxMotivoDaPerda, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jpExecucaoLayout.createSequentialGroup()
                        .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlResponsavel)
                            .addComponent(jlTotal)
                            .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblObservacoes)
                            .addComponent(lblMotivoDaPerda))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpExecucaoLayout.setVerticalGroup(
            jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpExecucaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpExecucaoLayout.createSequentialGroup()
                        .addComponent(jlDataEntrega)
                        .addGap(6, 6, 6)
                        .addComponent(jftfDataEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpExecucaoLayout.createSequentialGroup()
                        .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlStatus)
                            .addComponent(jlResponsavel))
                        .addGap(6, 6, 6)
                        .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jftfDataPrevEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jftfDataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpExecucaoLayout.createSequentialGroup()
                        .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlDataInicio)
                            .addComponent(jlDataPrevEntrega))
                        .addGap(36, 36, 36)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlCriterio1)
                    .addComponent(jlTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbCriterio1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCriterio2)
                    .addComponent(lblMotivoDaPerda))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxMotivoDaPerda)
                    .addComponent(jcbCriterio2, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlCriterio3)
                    .addComponent(lblObservacoes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpExecucaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpExecucaoLayout.createSequentialGroup()
                        .addComponent(jcbCriterio3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jlCriterio4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbCriterio4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jlCriterio5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jcbCriterio5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(94, 94, 94))
        );

        jTabbedPane1.addTab("Execução", jpExecucao);

        jtaApontamentos.setModel(new javax.swing.table.DefaultTableModel(
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
        jspScrollApont.setViewportView(jtaApontamentos);

        jbAdicionar.setText("Adicionar");
        jbAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarActionPerformed(evt);
            }
        });

        jbRemover.setText("Remover");
        jbRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverActionPerformed(evt);
            }
        });

        jlCusto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jlCusto.setText("Custo Total: ");

        javax.swing.GroupLayout jpApontamentosLayout = new javax.swing.GroupLayout(jpApontamentos);
        jpApontamentos.setLayout(jpApontamentosLayout);
        jpApontamentosLayout.setHorizontalGroup(
            jpApontamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpApontamentosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpApontamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspScrollApont, javax.swing.GroupLayout.DEFAULT_SIZE, 1008, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpApontamentosLayout.createSequentialGroup()
                        .addComponent(jlCusto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbAdicionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRemover)))
                .addContainerGap())
        );
        jpApontamentosLayout.setVerticalGroup(
            jpApontamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpApontamentosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspScrollApont, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpApontamentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlCusto))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Apontamentos", jpApontamentos);

        jtaComentariosOS.setColumns(20);
        jtaComentariosOS.setLineWrap(true);
        jtaComentariosOS.setRows(5);
        jScrollPane2.setViewportView(jtaComentariosOS);

        jlComentario.setText("Comentário");

        jbExcluir.setText("Excluir");
        jbExcluir.setToolTipText("Comentários");
        jbExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExcluirActionPerformed(evt);
            }
        });

        jbComentar.setText("Comentar");
        jbComentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbComentarActionPerformed(evt);
            }
        });

        jtComentarios.setModel(new javax.swing.table.DefaultTableModel(
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
        jtComentarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtComentariosMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jtComentarios);

        jLabel4.setText("Data de interação com o cliente");

        try {
            jftfDataInteracao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jftfDataInteracao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jftfDataInteracaoKeyPressed(evt);
            }
        });

        btnComentariosSugeridos.setText("Comentários Sugeridos");
        btnComentariosSugeridos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComentariosSugeridosActionPerformed(evt);
            }
        });

        btnInteracaoFone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fone32.png"))); // NOI18N
        btnInteracaoFone.setEnabled(false);

        btnInteracaoMsg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/msg32.png"))); // NOI18N
        btnInteracaoMsg.setEnabled(false);

        btnInteracaoReuniao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reuniao32.png"))); // NOI18N
        btnInteracaoReuniao.setEnabled(false);

        javax.swing.GroupLayout jbComentariosLayout = new javax.swing.GroupLayout(jbComentarios);
        jbComentarios.setLayout(jbComentariosLayout);
        jbComentariosLayout.setHorizontalGroup(
            jbComentariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jbComentariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jbComentariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1008, Short.MAX_VALUE)
                    .addGroup(jbComentariosLayout.createSequentialGroup()
                        .addGroup(jbComentariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlComentario)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addGroup(jbComentariosLayout.createSequentialGroup()
                        .addComponent(jftfDataInteracao, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnInteracaoFone, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInteracaoMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInteracaoReuniao, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnComentariosSugeridos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbComentar)))
                .addContainerGap())
        );
        jbComentariosLayout.setVerticalGroup(
            jbComentariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jbComentariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jbComentariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jbComentariosLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jbComentariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jftfDataInteracao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbComentar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnComentariosSugeridos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnInteracaoMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInteracaoReuniao, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInteracaoFone, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlComentario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Comentários", jbComentarios);

        jtSeguidores.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(jtSeguidores);

        jbAdicionarSeguidores.setText("Adicionar");
        jbAdicionarSeguidores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarSeguidoresActionPerformed(evt);
            }
        });

        jbRemoverSeguidores.setText("Remover");
        jbRemoverSeguidores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverSeguidoresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpSeguidoresLayout = new javax.swing.GroupLayout(jpSeguidores);
        jpSeguidores.setLayout(jpSeguidoresLayout);
        jpSeguidoresLayout.setHorizontalGroup(
            jpSeguidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSeguidoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpSeguidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 1008, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpSeguidoresLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbAdicionarSeguidores)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRemoverSeguidores)))
                .addContainerGap())
        );
        jpSeguidoresLayout.setVerticalGroup(
            jpSeguidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSeguidoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpSeguidoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAdicionarSeguidores, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbRemoverSeguidores, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Seguidores", jpSeguidores);

        jlpValores.setBorder(javax.swing.BorderFactory.createTitledBorder("Composição de valores gerais"));

        jlValorFaturamentoDireto.setText("Valor de faturamento direto:");

        jlValorFaturamentoDolphin.setText("Valor de faturamento Dolphin:");

        jlValorTotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jlValorTotal.setText("Valor Total:");

        jtfValorFaturamentoDireto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfValorFaturamentoDiretoKeyReleased(evt);
            }
        });

        jtfValorMaoDeObra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfValorMaoDeObraKeyReleased(evt);
            }
        });

        jtfValorTotal.setEditable(false);
        jtfValorTotal.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        jlValorComissao.setText("Valor de Comissão:");

        jtfValorComissao.setText("");
        jtfValorComissao.setToolTipText("");
        jtfValorComissao.setEnabled(false);

        jlpValores.setLayer(jlValorFaturamentoDireto, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpValores.setLayer(jlValorFaturamentoDolphin, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpValores.setLayer(jlValorTotal, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpValores.setLayer(jtfValorFaturamentoDireto, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpValores.setLayer(jtfValorMaoDeObra, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpValores.setLayer(jtfValorTotal, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpValores.setLayer(jlValorComissao, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jlpValores.setLayer(jtfValorComissao, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jlpValoresLayout = new javax.swing.GroupLayout(jlpValores);
        jlpValores.setLayout(jlpValoresLayout);
        jlpValoresLayout.setHorizontalGroup(
            jlpValoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jlpValoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jlpValoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlValorFaturamentoDolphin)
                    .addComponent(jlValorTotal)
                    .addComponent(jlValorFaturamentoDireto)
                    .addComponent(jlValorComissao))
                .addGap(78, 78, 78)
                .addGroup(jlpValoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtfValorTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtfValorMaoDeObra, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(jtfValorFaturamentoDireto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtfValorComissao, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                .addContainerGap())
        );
        jlpValoresLayout.setVerticalGroup(
            jlpValoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jlpValoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jlpValoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlValorFaturamentoDireto)
                    .addComponent(jtfValorFaturamentoDireto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jlpValoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlValorFaturamentoDolphin)
                    .addComponent(jtfValorMaoDeObra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(147, 147, 147)
                .addGroup(jlpValoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlValorTotal)
                    .addComponent(jtfValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jlpValoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlValorComissao)
                    .addComponent(jtfValorComissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpVendaLayout = new javax.swing.GroupLayout(jpVenda);
        jpVenda.setLayout(jpVendaLayout);
        jpVendaLayout.setHorizontalGroup(
            jpVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpVendaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlpValores)
                .addContainerGap())
        );
        jpVendaLayout.setVerticalGroup(
            jpVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpVendaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlpValores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Venda", jpVenda);

        tblOrdemServicoContatos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblOrdemServicoContatos);

        btnRemoverContato.setText("Remover");
        btnRemoverContato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverContatoActionPerformed(evt);
            }
        });

        btnAdicionarContato.setText("Adicionar");
        btnAdicionarContato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarContatoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1008, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAdicionarContato)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRemoverContato)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemoverContato)
                    .addComponent(btnAdicionarContato))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Contatos", jPanel2);

        jbCancelar.setText("Cancelar");
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        jbSalvarEFechar.setText("Salvar e Fechar");
        jbSalvarEFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalvarEFecharActionPerformed(evt);
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
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 772, Short.MAX_VALUE)
                        .addComponent(jbSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbSalvarEFechar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbSalvarEFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtfNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNomeKeyPressed
        campoEditado = true;
    }//GEN-LAST:event_jtfNomeKeyPressed

    private void jftfDataInicioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfDataInicioKeyPressed
        campoEditado = true;
    }//GEN-LAST:event_jftfDataInicioKeyPressed

    private void jftfDataPrevEntregaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfDataPrevEntregaKeyPressed
        campoEditado = true;
    }//GEN-LAST:event_jftfDataPrevEntregaKeyPressed

    private void jftfDataEntregaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfDataEntregaKeyPressed
        campoEditado = true;
    }//GEN-LAST:event_jftfDataEntregaKeyPressed

    private void jbAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarActionPerformed
        new UIApontamentosOs(ordemServico, this).setVisible(true);
    }//GEN-LAST:event_jbAdicionarActionPerformed

    private void jbRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverActionPerformed
        Apontamento apontamento = getApontamentoSelecionado();
        if (apontamento == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecione o apontamento que deseja remover!");
        } else {
            Object[] options = {"Sim", "Não"};
            int i = JOptionPane.showOptionDialog(null,
                    "Tem certeza que deseja remover o apontamento?",
                    "Remover Apontamento", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (i == JOptionPane.YES_OPTION) {
                removerApontOS();
            }
        }
    }//GEN-LAST:event_jbRemoverActionPerformed

    private void jbExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExcluirActionPerformed
        Comentario coment = getComentario();
        if (coment.getCreatedBy().equals(Menu.uiLogin.getPessoa().getLogin()) || Menu.uiLogin.getPessoa().getLogin().equals("adm")) {
            Object[] options = {"Sim", "Não"};
            int i = JOptionPane.showOptionDialog(null,
                    "Tem certeza que deseja excluir?", "Excluir",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (i == JOptionPane.YES_OPTION) {
                excluirComentario();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Você não tem autorização para excluir esse comentário",
                    "Aviso ao tentar excluir comentário", JOptionPane.WARNING_MESSAGE);
        }//else
    }//GEN-LAST:event_jbExcluirActionPerformed

    private void jbComentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbComentarActionPerformed
        salvarComentario();
    }//GEN-LAST:event_jbComentarActionPerformed

    private void jtComentariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtComentariosMouseClicked
        if (evt.getClickCount() == 2) {
            preencherCampoComentario();
        }
    }//GEN-LAST:event_jtComentariosMouseClicked

    private void jbAdicionarSeguidoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarSeguidoresActionPerformed
        new UISeguidores(ordemServico, this).setVisible(true);
    }//GEN-LAST:event_jbAdicionarSeguidoresActionPerformed

    private void jbRemoverSeguidoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverSeguidoresActionPerformed
        removerSeguidor();
    }//GEN-LAST:event_jbRemoverSeguidoresActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void jbSalvarEFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarEFecharActionPerformed
        salvarEFechar = true;
        salvar();
    }//GEN-LAST:event_jbSalvarEFecharActionPerformed

    private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed
        salvar();
    }//GEN-LAST:event_jbSalvarActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        transacao();
        enviarEmail();
    }//GEN-LAST:event_formWindowClosed

    private void jtfValorFaturamentoDiretoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfValorFaturamentoDiretoKeyReleased
        calculaValorTotal();
    }//GEN-LAST:event_jtfValorFaturamentoDiretoKeyReleased

    private void jtfValorMaoDeObraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfValorMaoDeObraKeyReleased
        calculaValorTotal();
    }//GEN-LAST:event_jtfValorMaoDeObraKeyReleased

    private void jlstClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlstClientesMouseClicked
        preencherClientes();
    }//GEN-LAST:event_jlstClientesMouseClicked

    private void jlstCustosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlstCustosMouseClicked
        preencherCentrosCusto();
    }//GEN-LAST:event_jlstCustosMouseClicked

    private void jtfCentroCustoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfCentroCustoFocusGained
        jtfCentroCusto.selectAll();
    }//GEN-LAST:event_jtfCentroCustoFocusGained

    private void jtfCentroCustoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCentroCustoKeyPressed
        campoEditado = true;
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            custosModel.removeAllElements();
            filtrarCentroCusto();
            if (jtfCentroCusto.getText().isEmpty() || custos.isEmpty()) {
                jspCustos.setVisible(false);
                jlstCustos.setVisible(false);
            } else {
                jspCustos.setVisible(true);
                jlstCustos.setVisible(true);
            }
        }
    }//GEN-LAST:event_jtfCentroCustoKeyPressed

    private void jftfDataSolicitacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfDataSolicitacaoKeyPressed
        campoEditado = true;
    }//GEN-LAST:event_jftfDataSolicitacaoKeyPressed

    private void jftfDataNecessidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfDataNecessidadeKeyPressed
        campoEditado = true;
    }//GEN-LAST:event_jftfDataNecessidadeKeyPressed

    private void jtfObraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfObraKeyPressed
        campoEditado = true;
    }//GEN-LAST:event_jtfObraKeyPressed

    private void jtfClienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfClienteFocusGained
        jtfCliente.selectAll();
    }//GEN-LAST:event_jtfClienteFocusGained

    private void jtfClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfClienteKeyPressed
        campoEditado = true;
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            clientesModel.removeAllElements();
            pesquisarClientes();
            if (jtfCliente.getText().isEmpty() || clientes.isEmpty()) {
                jspClientes.setVisible(false);
                jlstClientes.setVisible(false);
            } else {
                jspClientes.setVisible(true);
                jlstClientes.setVisible(true);
            }
        }
    }//GEN-LAST:event_jtfClienteKeyPressed

    private void jcbEstadoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbEstadoItemStateChanged
        preencherComboBoxCidade();
    }//GEN-LAST:event_jcbEstadoItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        clientesModel.removeAllElements();
        pesquisarClientes();
        if (jtfCliente.getText().isEmpty() || clientes.isEmpty()) {
            jspClientes.setVisible(false);
            jlstClientes.setVisible(false);
        } else {
            jspClientes.setVisible(true);
            jlstClientes.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        custosModel.removeAllElements();
        filtrarCentroCusto();
        if (jtfCentroCusto.getText().isEmpty() || custos.isEmpty()) {
            jspCustos.setVisible(false);
            jlstCustos.setVisible(false);
        } else {
            jspCustos.setVisible(true);
            jlstCustos.setVisible(true);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jcbTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbTipoItemStateChanged
        darPermissoes();
        if (ordemServico == null) {
            TipoOs tipo = (TipoOs) jcbTipo.getSelectedItem();
            jchAdicional.setEnabled(tipo.getDescricao().equalsIgnoreCase("Comercial"));
        }

        habilitarBotaoPesquisarProjeto();

        preencherCentroDeCustoPadrao();

    }//GEN-LAST:event_jcbTipoItemStateChanged

    private void habilitarBotaoPesquisarProjeto() {
        TipoOs tipo = (TipoOs) jcbTipo.getSelectedItem();

        if (ordemServico == null
                && !jchAdicional.isSelected()
                && tipo.getCodTipoOs() == 21) {

            jbPesquisarProjeto.setEnabled(false);

        } else {
            if (ordemServico == null) {
                jbPesquisarProjeto.setEnabled(true);
            }
        }
    }

    private void jbPesquisarProjetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPesquisarProjetoActionPerformed
        new UIBuscarProjetos(this).setVisible(true);
    }//GEN-LAST:event_jbPesquisarProjetoActionPerformed

    private void jchAdicionalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jchAdicionalItemStateChanged
        habilitarBotaoPesquisarProjeto();
    }//GEN-LAST:event_jchAdicionalItemStateChanged

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        esconderLista();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        esconderLista();
    }//GEN-LAST:event_formMouseClicked

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        esconderLista();
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jftfDataInteracaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jftfDataInteracaoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jftfDataInteracaoKeyPressed

    private void jcbCriterio1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbCriterio1ItemStateChanged
        jtfTotal.setText(String.valueOf(calcularCriterios()));
    }//GEN-LAST:event_jcbCriterio1ItemStateChanged

    private int calcularCriterios() {
        Criterio criterio1 = (Criterio) jcbCriterio1.getSelectedItem();
        Criterio criterio2 = (Criterio) jcbCriterio2.getSelectedItem();
        Criterio criterio3 = (Criterio) jcbCriterio3.getSelectedItem();
        Criterio criterio4 = (Criterio) jcbCriterio4.getSelectedItem();
        Criterio criterio5 = (Criterio) jcbCriterio5.getSelectedItem();
        int total = 0;
        if (criterio1 != null
                && criterio2 != null
                && criterio3 != null
                && criterio4 != null
                && criterio5 != null) {
            total = (criterio1.getPeso()
                    + criterio2.getPeso()
                    + criterio3.getPeso()
                    + criterio4.getPeso()
                    + criterio5.getPeso());
        }
        return total;
    }

    private void jcbCriterio1VetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_jcbCriterio1VetoableChange
        System.out.println("Vetoable changed!!");
    }//GEN-LAST:event_jcbCriterio1VetoableChange

    private void jcbCriterio2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbCriterio2ItemStateChanged
        jtfTotal.setText(String.valueOf(calcularCriterios()));
    }//GEN-LAST:event_jcbCriterio2ItemStateChanged

    private void jcbCriterio3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbCriterio3ItemStateChanged
        jtfTotal.setText(String.valueOf(calcularCriterios()));
    }//GEN-LAST:event_jcbCriterio3ItemStateChanged

    private void jcbCriterio4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbCriterio4ItemStateChanged
        jtfTotal.setText(String.valueOf(calcularCriterios()));
    }//GEN-LAST:event_jcbCriterio4ItemStateChanged

    private void jcbCriterio5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcbCriterio5ItemStateChanged
        jtfTotal.setText(String.valueOf(calcularCriterios()));
    }//GEN-LAST:event_jcbCriterio5ItemStateChanged

    private void btnAdicionarContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarContatoActionPerformed
        if (ordemServico != null) {
            new FrmBuscarContatos(this, ordemServico.getCodOs()).setVisible(true);
        }
    }//GEN-LAST:event_btnAdicionarContatoActionPerformed

    private void btnRemoverContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverContatoActionPerformed
        Object[] options = {"Sim", "Não"};
        int i = JOptionPane.showOptionDialog(null,
                "Você deseja remover o contato?",
                "Remover contato", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (i == JOptionPane.YES_OPTION) {
            removerContato();
        }
    }//GEN-LAST:event_btnRemoverContatoActionPerformed

    private void btnComentariosSugeridosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComentariosSugeridosActionPerformed
        new FrmComentariosPadraoTarefa(this).setVisible(true);
    }//GEN-LAST:event_btnComentariosSugeridosActionPerformed

    private void onChangeStatus() {
        jcbStatus.addActionListener((ae) -> {
            Status status = (Status) jcbStatus.getSelectedItem();
            if (status == null) {
                System.out.println("Status Nulo!!!!");
            } else {
                jtfValorComissao.setEnabled(status.getNome().equalsIgnoreCase("Ganho"));
                if (status.getNome().equalsIgnoreCase("acompanhamento")) {
                    String hoje = FormatarData.formatarDataEmTexto(LocalDate.now(), "dd/MM/yyyy");
                    jftfDataInicio.setText(hoje);
                }
                if (status.getNome().equalsIgnoreCase("Perdido")) {
                    cbxMotivoDaPerda.setEnabled(true);
                    txtaObservacoes.setEnabled(true);
                } else {
                    txtaObservacoes.setText("");
                    cbxMotivoDaPerda.setEnabled(false);
                    txtaObservacoes.setEnabled(false);
                    cbxMotivoDaPerda.setSelectedIndex(0);
                    MotivoPerda motivo = (MotivoPerda) cbxMotivoDaPerda.getModel().getSelectedItem();
                    ordemServico.setMotivo(motivo);
                    ordemServico.setObservacoes(null);
                }
            }
        });
    }

    private void esconderLista() {
        jspClientes.setVisible(false);
        jlstClientes.setVisible(false);
        jspCustos.setVisible(false);
        jlstCustos.setVisible(false);
    }

    private void calculaValorTotal() {
        jtfValorTotal.setText("");
        double valorFatDir = 0.00;
        double valorFatDolphin = 0.00;

        try {

            valorFatDir = Double.parseDouble(jtfValorFaturamentoDireto.getText().replaceAll("\\.", "").replace(",", "."));
            valorFatDolphin = Double.parseDouble(jtfValorMaoDeObra.getText().replaceAll("\\.", "").replace(",", "."));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inserido é inválido! " + e.getMessage(),
                    "Erro ao calcular valor total", JOptionPane.ERROR_MESSAGE);
        }
        Venda venda = new Venda(valorFatDir, valorFatDolphin);

        DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        formatoDois.setMinimumFractionDigits(2);
        formatoDois.setParseBigDecimal(true);

        jtfValorTotal.setText(String.valueOf(formatoDois.format(venda.calcularValorTotal())));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionarContato;
    private javax.swing.JButton btnComentariosSugeridos;
    private javax.swing.JToggleButton btnInteracaoFone;
    private javax.swing.JToggleButton btnInteracaoMsg;
    private javax.swing.JToggleButton btnInteracaoReuniao;
    private javax.swing.JButton btnRemoverContato;
    private javax.swing.JComboBox<MotivoPerda> cbxMotivoDaPerda;
    private javax.swing.JCheckBox chPrincipal;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbAdicionar;
    private javax.swing.JButton jbAdicionarSeguidores;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbComentar;
    private javax.swing.JPanel jbComentarios;
    private javax.swing.JButton jbExcluir;
    private javax.swing.JButton jbPesquisarProjeto;
    private javax.swing.JButton jbRemover;
    private javax.swing.JButton jbRemoverSeguidores;
    private javax.swing.JButton jbSalvar;
    private javax.swing.JButton jbSalvarEFechar;
    private javax.swing.JComboBox<Cidade> jcbCidade;
    private javax.swing.JComboBox<Object> jcbCriterio1;
    private javax.swing.JComboBox<Criterio> jcbCriterio2;
    private javax.swing.JComboBox<Criterio> jcbCriterio3;
    private javax.swing.JComboBox<Criterio> jcbCriterio4;
    private javax.swing.JComboBox<Criterio> jcbCriterio5;
    private javax.swing.JComboBox<Estado> jcbEstado;
    private javax.swing.JComboBox<Object> jcbResponsavel;
    private javax.swing.JComboBox<Segmento> jcbSegmento;
    private javax.swing.JComboBox<Object> jcbSolicitante;
    private javax.swing.JComboBox<Object> jcbStatus;
    private javax.swing.JComboBox<Object> jcbTipo;
    private javax.swing.JCheckBox jchAdicional;
    private javax.swing.JFormattedTextField jftfDataEntrega;
    private javax.swing.JFormattedTextField jftfDataInicio;
    private javax.swing.JFormattedTextField jftfDataInteracao;
    private javax.swing.JFormattedTextField jftfDataNecessidade;
    private javax.swing.JFormattedTextField jftfDataPrevEntrega;
    private javax.swing.JFormattedTextField jftfDataSolicitacao;
    private javax.swing.JLabel jlCentroCusto;
    private javax.swing.JLabel jlCidade;
    private javax.swing.JLabel jlCliente;
    private javax.swing.JLabel jlCodCliente;
    private javax.swing.JLabel jlCodCusto;
    private javax.swing.JLabel jlCodOs;
    private javax.swing.JLabel jlComentario;
    private javax.swing.JLabel jlCriterio1;
    private javax.swing.JLabel jlCriterio2;
    private javax.swing.JLabel jlCriterio3;
    private javax.swing.JLabel jlCriterio4;
    private javax.swing.JLabel jlCriterio5;
    private javax.swing.JLabel jlCusto;
    private javax.swing.JLabel jlDataEntrega;
    private javax.swing.JLabel jlDataInicio;
    private javax.swing.JLabel jlDataNecessidade;
    private javax.swing.JLabel jlDataPrevEntrega;
    private javax.swing.JLabel jlDataSolicitacao;
    private javax.swing.JLabel jlEstado;
    private javax.swing.JLabel jlNome;
    private javax.swing.JLabel jlObra;
    private javax.swing.JLabel jlResponsavel;
    private javax.swing.JLabel jlSegmento;
    private javax.swing.JLabel jlSolicitante;
    private javax.swing.JLabel jlStatus;
    private javax.swing.JLabel jlTipo;
    private javax.swing.JLabel jlTotal;
    private javax.swing.JLabel jlValorComissao;
    private javax.swing.JLabel jlValorFaturamentoDireto;
    private javax.swing.JLabel jlValorFaturamentoDolphin;
    private javax.swing.JLabel jlValorTotal;
    private javax.swing.JLayeredPane jlpValores;
    private javax.swing.JList<Cliente> jlstClientes;
    private javax.swing.JList<Object> jlstCustos;
    private javax.swing.JPanel jpApontamentos;
    private javax.swing.JPanel jpDados;
    private javax.swing.JPanel jpExecucao;
    private javax.swing.JPanel jpSeguidores;
    private javax.swing.JPanel jpVenda;
    private javax.swing.JScrollPane jspClientes;
    private javax.swing.JScrollPane jspCustos;
    private javax.swing.JScrollPane jspScrollApont;
    private javax.swing.JTable jtComentarios;
    private javax.swing.JTable jtSeguidores;
    private javax.swing.JTable jtaApontamentos;
    private javax.swing.JTextArea jtaComentariosOS;
    private javax.swing.JTextField jtfAdicional;
    private javax.swing.JTextField jtfCentroCusto;
    private javax.swing.JTextField jtfCliente;
    private javax.swing.JTextField jtfCodCliente;
    private javax.swing.JTextField jtfCodColigada;
    private javax.swing.JTextField jtfCodCusto;
    private javax.swing.JTextField jtfCodOs;
    private javax.swing.JTextField jtfCodProjeto;
    private javax.swing.JTextField jtfNome;
    private javax.swing.JTextField jtfObra;
    private javax.swing.JTextField jtfTotal;
    private utilitarios.JMoneyFieldValor jtfValorComissao;
    private utilitarios.JMoneyFieldValor jtfValorFaturamentoDireto;
    private utilitarios.JMoneyFieldValor jtfValorMaoDeObra;
    private utilitarios.JMoneyFieldValor jtfValorTotal;
    private javax.swing.JLabel lblMotivoDaPerda;
    private javax.swing.JLabel lblObservacoes;
    private javax.swing.JTable tblOrdemServicoContatos;
    private javax.swing.JTextArea txtaObservacoes;
    // End of variables declaration//GEN-END:variables

}
