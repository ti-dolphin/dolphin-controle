/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controllers.ClassificacaoController;
import controllers.ClienteComentarioController;
import controllers.ClienteController;
import dao.DAOFactory;
import dao.os.PessoaDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import model.ClienteComentario;
import model.ClienteComentarioTableModel;
import model.os.Classificacao;
import model.os.Cliente;
import model.os.Pessoa;
import utilitarios.FormatarData;

/**
 *
 * @author ti
 */
public class FrmCadastroCliente extends javax.swing.JDialog {

    private Cliente cliente;
    private ClienteController clienteController;
    private ClienteComentarioController clienteComentarioController;
    private ClassificacaoController classificacaoController;
    private ClienteComentarioTableModel ccTableModel;

    public FrmCadastroCliente(JDialog parent, Cliente cliente) {
        super(parent, true);
        initComponents();
        this.cliente = cliente;
        this.clienteController = new ClienteController();
        this.classificacaoController = new ClassificacaoController();
        this.clienteComentarioController = new ClienteComentarioController();
        this.ccTableModel = new ClienteComentarioTableModel();
        preencherComboBoxClassificacao();
        preencherComboBoxResponsavel();
        preencherCampos();
        popularTabelaComentarios();
    }

    public JTextArea getTxaComentario() {
        return txaComentario;
    }

    private void preencherComboBoxClassificacao() {

        try {
            ArrayList<Classificacao> classificacoes = classificacaoController.buscar();

            for (Classificacao classificacao : classificacoes) {
                cbxClassificacao.addItem(classificacao);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao preencher combobox do classficação",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherComboBoxResponsavel() {
        PessoaDAO dao = DAOFactory.getPESSOADAO();

        try {
            for (Pessoa p : dao.buscarPessoasCombo("responsavel")) {
                cbxResponsavel.addItem(p);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao carregar combobox responsável",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherCampos() {
        if (cliente != null) {
            txtNome.setText(cliente.getNomeFantasia());
            txtDataInteracao.setText(FormatarData.formatarDataEmTexto(cliente.getDataInteracao(), "dd/MM/yyyy"));
            cbxClassificacao.getModel().setSelectedItem(cliente.getClassificacao());
            cbxResponsavel.getModel().setSelectedItem(cliente.getResponsavel());
            chProspectar.setSelected(cliente.isProspectar());
            btnInteracaoFone.setSelected(!cliente.isInteracaoFone());
            btnInteracaoMsg.setSelected(!cliente.isInteracaoMsg());
            btnInteracaoReuniao.setSelected(!cliente.isInteracaoReuniao());
        }
    }

    private void popularTabelaComentarios() {
        try {
            if (cliente != null) {

                ArrayList<ClienteComentario> comentarios
                        = (ArrayList<ClienteComentario>) clienteComentarioController
                                .buscarComentariosDoCliente(cliente.getCodColigada(), cliente.getCodCliente());

                for (ClienteComentario comentario : comentarios) {
                    ccTableModel.addRow(comentario);
                }

                tblComentarios.setModel(ccTableModel);

            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao buscar comentarios do cliente",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public ClienteComentario getComentario() {
        if (tblComentarios.getSelectedRow() == -1) {
            return null;
        }

        return ccTableModel.getRowValue(tblComentarios.getSelectedRow());
    }//getComentario

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        chProspectar = new javax.swing.JCheckBox();
        cbxClassificacao = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtDataInteracao = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnInteracaoFone = new javax.swing.JToggleButton();
        btnInteracaoMsg = new javax.swing.JToggleButton();
        btnInteracaoReuniao = new javax.swing.JToggleButton();
        jLabel5 = new javax.swing.JLabel();
        cbxResponsavel = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblComentarios = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaComentario = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        btnComentar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editar Prospecção");

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        chProspectar.setText("Prospectar");

        jLabel2.setText("Classificação");

        try {
            txtDataInteracao.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtDataInteracao.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDataInteracaoFocusGained(evt);
            }
        });

        jLabel1.setText("Data de interação");

        txtNome.setEditable(false);
        txtNome.setEnabled(false);

        jLabel3.setText("Cliente");

        btnInteracaoFone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fone64.png"))); // NOI18N

        btnInteracaoMsg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/msg64.png"))); // NOI18N
        btnInteracaoMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInteracaoMsgActionPerformed(evt);
            }
        });

        btnInteracaoReuniao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/reuniao64.png"))); // NOI18N

        jLabel5.setText("Selecione o tipo de interação com o cliente:");

        jLabel6.setText("Responsável");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDataInteracao)
                    .addComponent(cbxClassificacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNome)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chProspectar)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnInteracaoFone, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnInteracaoMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnInteracaoReuniao, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(0, 342, Short.MAX_VALUE))
                    .addComponent(cbxResponsavel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDataInteracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxClassificacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chProspectar)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnInteracaoMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnInteracaoReuniao, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnInteracaoFone, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dados", jPanel1);

        tblComentarios.setModel(new javax.swing.table.DefaultTableModel(
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
        tblComentarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblComentarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblComentariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblComentarios);

        txaComentario.setColumns(20);
        txaComentario.setRows(5);
        jScrollPane2.setViewportView(txaComentario);

        jLabel4.setText("Comentário");

        btnComentar.setText("Comentar");
        btnComentar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComentarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnComentar)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(btnComentar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Comentários", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnSalvar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        try {

            if (txtDataInteracao.getText().equals("  /  /    ")) {
                throw new NumberFormatException("Insira a data de interação!");
            }

            cliente.setDataInteracao(FormatarData.converterTextoEmData(txtDataInteracao.getText(), "dd/MM/yyyy"));
            cliente.setClassificacao((Classificacao) cbxClassificacao.getSelectedItem());
            cliente.setProspectar(chProspectar.isSelected());
            cliente.setInteracaoFone(!btnInteracaoFone.isSelected());
            cliente.setInteracaoMsg(!btnInteracaoMsg.isSelected());
            cliente.setInteracaoReuniao(!btnInteracaoReuniao.isSelected());
            
            Pessoa responsavel = (Pessoa) cbxResponsavel.getSelectedItem();
            
            cliente.setResponsavel(responsavel);
                
            clienteController.editar(cliente);

            JOptionPane.showMessageDialog(this, "Cliente editado!");

            this.dispose();

        } catch (SQLException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
            JOptionPane.showMessageDialog(this, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnComentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComentarActionPerformed
        try {
            ClienteComentario comentario = new ClienteComentario();
            comentario.setDescricao(txaComentario.getText());
            clienteComentarioController.inserir(comentario, cliente.getCodColigada(), cliente.getCodCliente());

            ccTableModel.clear();
            popularTabelaComentarios();
        } catch (SQLException ex) {
            Logger.getLogger(FrmCadastroCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnComentarActionPerformed

    private void tblComentariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblComentariosMouseClicked
        if (evt.getClickCount() == 2) {
            txaComentario.setText(getComentario().getDescricao());
        }
    }//GEN-LAST:event_tblComentariosMouseClicked

    private void txtDataInteracaoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDataInteracaoFocusGained
        txtDataInteracao.selectAll();
    }//GEN-LAST:event_txtDataInteracaoFocusGained

    private void btnInteracaoMsgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInteracaoMsgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInteracaoMsgActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnComentar;
    private javax.swing.JToggleButton btnInteracaoFone;
    private javax.swing.JToggleButton btnInteracaoMsg;
    private javax.swing.JToggleButton btnInteracaoReuniao;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<Object> cbxClassificacao;
    private javax.swing.JComboBox<Pessoa> cbxResponsavel;
    private javax.swing.JCheckBox chProspectar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblComentarios;
    private javax.swing.JTextArea txaComentario;
    private javax.swing.JFormattedTextField txtDataInteracao;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
