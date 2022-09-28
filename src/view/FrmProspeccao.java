/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DAOFactory;
import dao.os.ClienteDAO;
import dao.os.PessoaDAO;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import model.os.Cliente;
import model.os.ClienteTableModel;
import model.os.Pessoa;
import utilitarios.FormatarData;
import utilitarios.IconRender;
import utilitarios.os.DateCellRenderer;

/**
 *
 * @author ti
 */
public class FrmProspeccao extends javax.swing.JDialog {

    private ClienteDAO dao;
    private ClienteTableModel cTableModel;
    private TableRowSorter<ClienteTableModel> sorter;
    int qtdPorPagina = 100;
    int pagina = 0;
    int offset = 0;

    public FrmProspeccao(java.awt.Frame parent) {
        super(parent, true);
        initComponents();

        this.dao = DAOFactory.getCLIENTEDAO();
        this.cTableModel = new ClienteTableModel();
        sorter = new TableRowSorter<>(cTableModel);
        tblClientes.setRowSorter(sorter);
        preencherComboBoxResponsaveis();
        buscar();
        habilitarBotaoEditar();
        configurarTabela();
    }

    private void habilitarBotaoEditar() {
        tblClientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (getClienteSelecionado() != null) {
                    btnEditar.setEnabled(true);
                } else {
                    btnEditar.setEnabled(false);
                }
            }
        });
    }

    private void configurarTabela() {

        IconRender iconRender = new IconRender(tblClientes, cTableModel.COLUNA_STATUS);
        DateCellRenderer dRenderer = new DateCellRenderer();

        tblClientes.setAutoResizeMode(tblClientes.AUTO_RESIZE_OFF);
        tblClientes.getColumnModel().getColumn(cTableModel.COLUNA_STATUS).setPreferredWidth(50);
        tblClientes.getColumnModel().getColumn(cTableModel.COLUNA_NOME_FANTASIA).setPreferredWidth(600);
        tblClientes.getColumnModel().getColumn(cTableModel.COLUNA_RAZAO_SOCIAL).setPreferredWidth(600);
        tblClientes.getColumnModel().getColumn(cTableModel.COLUNA_RESPONSAVEL).setPreferredWidth(400);
        tblClientes.getColumnModel().getColumn(cTableModel.COLUNA_DATA_INTERACAO).setPreferredWidth(120);
        tblClientes.getColumnModel().getColumn(cTableModel.COLUNA_CNPJ).setPreferredWidth(120);
        tblClientes.getColumnModel().getColumn(cTableModel.COLUNA_CLASSIFICACAO).setPreferredWidth(90);

        tblClientes.getColumnModel().getColumn(cTableModel.COLUNA_DATA_INTERACAO).setCellRenderer(dRenderer);
        tblClientes.getColumnModel().getColumn(cTableModel.COLUNA_STATUS).setCellRenderer(iconRender);
    }

    public ClienteTableModel getpTableModel() {
        return cTableModel;
    }

    public void buscar() {
        try {

            String query = "";
            Pessoa responsavel = (Pessoa) cbxResponsavel.getSelectedItem();
            
            if (!txtNome.getText().isEmpty()) {
                query += " AND CLI.NOMEFANTASIA LIKE '%" + txtNome.getText() + "%' ";
            }

            if (chEmProspeccao.isSelected()) {
                query += " AND CLI.PROSPECTAR = TRUE";
            }

            if (cbxResponsavel.getSelectedIndex() != 0) {
                query = query + " AND P.CODPESSOA = " + responsavel.getCodPessoa();
            }

            if (!txtDataInteracaoInicio.getText().equals("  /  /    ") || !txtDataInteracaoFim.getText().equals("  /  /    ")) {

                String dataInicio = FormatarData.formatarData(txtDataInteracaoInicio.getText());
                String dataTermino = FormatarData.formatarData(txtDataInteracaoFim.getText());

                query = query + " AND CLI.DATA_INTERACAO BETWEEN ('" + dataInicio + "') AND ('" + dataTermino + "') ";
            }

            ArrayList<Cliente> clientes = dao.buscar(query, offset, qtdPorPagina);

            for (Cliente cliente : clientes) {
                cTableModel.addRow(cliente);
            }

            tblClientes.setModel(cTableModel);

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao buscar clientes",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Erro ao buscar clientes",
                    JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(FrmProspeccao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Cliente getClienteSelecionado() {
        if (tblClientes.getSelectedRow() > -1) {
            int linhaSelecionada = tblClientes.getRowSorter().convertRowIndexToModel(tblClientes.getSelectedRow());
            return cTableModel.getRowValue(linhaSelecionada);
        } else {
            return null;
        }
    }

    private void habilitarIcones() {

        Cliente cliente = getClienteSelecionado();

        btnInteracaoFone.setEnabled(cliente.isInteracaoFone());
        btnInteracaoMsg.setEnabled(cliente.isInteracaoMsg());
        btnInteracaoReuniao.setEnabled(cliente.isInteracaoReuniao());
    }

    public void preencherComboBoxResponsaveis() {
        PessoaDAO dao = DAOFactory.getPESSOADAO();

        try {
            for (Pessoa pessoa : dao.buscarPessoasCombo("responsavel")) {
                cbxResponsavel.addItem(pessoa);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao preencher combobox",
                    JOptionPane.ERROR_MESSAGE);
        }//fecha catch
    }//fecha preencherCombo

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        txtNome = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        chEmProspeccao = new javax.swing.JCheckBox();
        btnInteracaoFone = new javax.swing.JToggleButton();
        btnInteracaoMsg = new javax.swing.JToggleButton();
        btnInteracaoReuniao = new javax.swing.JToggleButton();
        btnProxPag = new javax.swing.JButton();
        btnAntPag = new javax.swing.JButton();
        btnAPrimeiraPag = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnPesquisar = new javax.swing.JButton();
        btnLimparFiltros = new javax.swing.JButton();
        txtDataInteracaoInicio = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDataInteracaoFim = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        cbxResponsavel = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Prospecção de Clientes");

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
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
        tblClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblClientesMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        btnEditar.setText("Editar");
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomeKeyReleased(evt);
            }
        });

        jLabel1.setText("Nome");

        chEmProspeccao.setSelected(true);
        chEmProspeccao.setText("Somente em prospecção");
        chEmProspeccao.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chEmProspeccaoItemStateChanged(evt);
            }
        });

        btnInteracaoFone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fone32.png"))); // NOI18N
        btnInteracaoFone.setEnabled(false);

        btnInteracaoMsg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/msg32.png"))); // NOI18N
        btnInteracaoMsg.setEnabled(false);

        btnInteracaoReuniao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reuniao32.png"))); // NOI18N
        btnInteracaoReuniao.setEnabled(false);

        btnProxPag.setText(">");
        btnProxPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProxPagActionPerformed(evt);
            }
        });

        btnAntPag.setText("<");
        btnAntPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAntPagActionPerformed(evt);
            }
        });

        btnAPrimeiraPag.setText("<<");
        btnAPrimeiraPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAPrimeiraPagActionPerformed(evt);
            }
        });

        jLabel2.setText("Exibindo 100 registros por página");

        btnPesquisar.setText("Pesquisar");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        btnLimparFiltros.setText("Limpar Filtros");
        btnLimparFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparFiltrosActionPerformed(evt);
            }
        });

        try {
            txtDataInteracaoInicio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel3.setText("De");

        try {
            txtDataInteracaoFim.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jLabel4.setText("Até");

        jLabel5.setText("Responsável");

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
                            .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 107, Short.MAX_VALUE))
                            .addComponent(cbxResponsavel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtDataInteracaoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtDataInteracaoFim, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chEmProspeccao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPesquisar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLimparFiltros))
                            .addComponent(jLabel4)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(btnAPrimeiraPag)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAntPag)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnProxPag))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnInteracaoFone, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnInteracaoMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnInteracaoReuniao, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditar)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataInteracaoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataInteracaoFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chEmProspeccao)
                    .addComponent(btnPesquisar)
                    .addComponent(btnLimparFiltros)
                    .addComponent(cbxResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnEditar)
                    .addComponent(btnInteracaoFone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInteracaoMsg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInteracaoReuniao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProxPag)
                    .addComponent(btnAntPag)
                    .addComponent(btnAPrimeiraPag)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        new FrmCadastroCliente(this, getClienteSelecionado()).setVisible(true);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void txtNomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyReleased
    }//GEN-LAST:event_txtNomeKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        if (evt.getClickCount() == 2) {
            new FrmCadastroCliente(this, getClienteSelecionado()).setVisible(true);
        }
    }//GEN-LAST:event_tblClientesMouseClicked

    private void chEmProspeccaoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chEmProspeccaoItemStateChanged
        cTableModel.clear();
        buscar();
    }//GEN-LAST:event_chEmProspeccaoItemStateChanged

    private void tblClientesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseReleased
        habilitarIcones();
    }//GEN-LAST:event_tblClientesMouseReleased

    private void btnProxPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProxPagActionPerformed
        offset += qtdPorPagina;
        pagina++;
        cTableModel.clear();
        buscar();
    }//GEN-LAST:event_btnProxPagActionPerformed

    private void btnAntPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAntPagActionPerformed
        if (offset >= qtdPorPagina) {
            offset -= qtdPorPagina;
            pagina--;
            cTableModel.clear();
            buscar();
        }
    }//GEN-LAST:event_btnAntPagActionPerformed

    private void btnAPrimeiraPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAPrimeiraPagActionPerformed
        pagina = 0;
        offset = 0;
        qtdPorPagina = 100;
        cTableModel.clear();
        buscar();
    }//GEN-LAST:event_btnAPrimeiraPagActionPerformed

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            cTableModel.clear();
            buscar();
        }
    }//GEN-LAST:event_txtNomeKeyPressed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        cTableModel.clear();
        buscar();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnLimparFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparFiltrosActionPerformed
        txtNome.setText("");
        chEmProspeccao.setSelected(true);
        cbxResponsavel.setSelectedIndex(0);
        cTableModel.clear();
        buscar();
    }//GEN-LAST:event_btnLimparFiltrosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAPrimeiraPag;
    private javax.swing.JButton btnAntPag;
    private javax.swing.JButton btnEditar;
    private javax.swing.JToggleButton btnInteracaoFone;
    private javax.swing.JToggleButton btnInteracaoMsg;
    private javax.swing.JToggleButton btnInteracaoReuniao;
    private javax.swing.JButton btnLimparFiltros;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnProxPag;
    private javax.swing.JComboBox<Pessoa> cbxResponsavel;
    private javax.swing.JCheckBox chEmProspeccao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JFormattedTextField txtDataInteracaoFim;
    private javax.swing.JFormattedTextField txtDataInteracaoInicio;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
