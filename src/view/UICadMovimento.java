/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.MovimentoDAO;
import dao.MovimentoItemCheckListDAO;
import dao.MovimentoItemDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.CheckListModelo;
import model.LocalDeEstoque;
import model.Movimento;
import model.MovimentoItem;
import model.MovimentoItemTableModel;
import model.os.Pessoa;
import utilitarios.FormatarData;

/**
 *
 * @author guilherme.oliveira
 */
public class UICadMovimento extends javax.swing.JDialog {

    private LocalDeEstoque estoqueOrigem;
    private LocalDeEstoque estoqueDestino;
    private Pessoa responsavel;
    private MovimentoDAO movimentoDAO;
    private MovimentoItemTableModel movimentoItemTableModel;
    private Movimento movimento;
    private UIControleMovimentos uiControleMovimentos;
    private MovimentoItemDAO movimentoItemDAO;
    private CheckListModelo checkListModelo;

    public UICadMovimento() {
    }

    public UICadMovimento(UIControleMovimentos uiControleMovimentos, Movimento movimento) {
        initComponents();
        carregarCampoData();
        this.movimento = movimento;
        this.uiControleMovimentos = uiControleMovimentos;
        movimentoDAO = new MovimentoDAO();
        movimentoItemDAO = new MovimentoItemDAO();
        movimentoItemTableModel = new MovimentoItemTableModel();
        jtMovimentosItens.setModel(movimentoItemTableModel);
        carregarCampos();
        buscarItensDoMovimento();
    }

    public void setCheckListModelo(CheckListModelo checkListModelo) {
        this.checkListModelo = checkListModelo;
    }

    public void setEstoqueOrigem(LocalDeEstoque estoqueOrigem) {
        this.estoqueOrigem = estoqueOrigem;
    }

    public void setEstoqueDestino(LocalDeEstoque estoqueDestino) {
        this.estoqueDestino = estoqueDestino;
    }

    public JTextField getJtfEstoqueOrigem() {
        return jtfEstoqueOrigem;
    }

    public JTextField getJtfEstoqueDestino() {
        return jtfEstoqueDestino;
    }

    public JTextField getJtfResponsavel() {
        return jtfResponsavel;
    }

    public void setResponsavel(Pessoa responsavel) {
        this.responsavel = responsavel;
    }

    public JButton getJbAddPatrimonio() {
        return jbAddPatrimonio;
    }

    public JButton getJbAdicionarProduto() {
        return jbAdicionarProduto;
    }

    public MovimentoItemTableModel getMovimentoItemTableModel() {
        return movimentoItemTableModel;
    }

    public JCheckBox getJchComprando() {
        return jchComprando;
    }

    public UIControleMovimentos getUiControleMovimentos() {
        return uiControleMovimentos;
    }

    private void carregarCampoData() {
        try {

            jftfData.setText(FormatarData.formatarDataEmTexto(LocalDate.now(), "dd/MM/yyyy"));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    e.getMessage(), "Erro ao carregar data",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarCampos() {
        if (movimento != null) {
            jtfEstoqueOrigem.setText(movimento.getLocalDeEstoqueOrigem().getNome());
            jtfEstoqueDestino.setText(movimento.getLocalDeEstoqueDestino().getNome());
            jtfResponsavel.setText(movimento.getResponsavel().getNome());
        }
    }

    public void buscarItensDoMovimento() {
        Menu.carregamento(true);
        new Thread(() -> {
            try {
                if (movimento != null) {

                    ArrayList<MovimentoItem> itens = movimentoItemDAO.buscarPorMovimento(movimento);

                    for (MovimentoItem item : itens) {
                        movimentoItemTableModel.addRow(item);
                    }

                } else {
                    jtpMovimento.setEnabledAt(1, false);
                }
            } catch (Exception e) {
                Logger.getLogger(UICadMovimento.class.getName()).log(Level.SEVERE, null, e);
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
            Menu.carregamento(false);
        }).start();
    }

    private void setMovimento() throws NumberFormatException {

        if (jtfEstoqueOrigem.getText().isEmpty()) {
            throw new NumberFormatException("Insira o local de estoque de origem!");
        }
        if (jtfEstoqueDestino.getText().isEmpty()) {
            throw new NumberFormatException("Insira o local de estoque de destino!");
        }
        if (jtfResponsavel.getText().isEmpty()) {
            throw new NumberFormatException("Insira o responsável!");
        }
        if (jftfData.getText().equals("  /  /    ")) {
            throw new NumberFormatException("Insira a data!");
        }
        if (estoqueOrigem != null) {
            movimento.setLocalDeEstoqueOrigem(estoqueOrigem);
        }
        if (estoqueDestino != null) {
            movimento.setLocalDeEstoqueDestino(estoqueDestino);
        }
        if (responsavel != null) {
            movimento.setResponsavel(responsavel);
        }
        movimento.setDataEntrega(FormatarData.converterTextoEmData(jftfData.getText(), "dd/MM/yyyy"));
    }

    private void cadastrar() {
        try {
            movimento = new Movimento();

            setMovimento();

            int id = movimentoDAO.inserir(movimento);

            movimento.setId(id);

            uiControleMovimentos.getMovimentoTableModel().addRow(movimento);

            JOptionPane.showMessageDialog(null, "Movimento cadastrado!");

            jtpMovimento.setEnabledAt(1, true);

        } catch (Exception e) {
            movimento = null;
            Logger.getLogger(UICadMovimento.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Erro ao cadastrar movimento", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editar() {
        try {

            setMovimento();

            movimentoDAO.alterar(movimento);

            uiControleMovimentos.getMovimentoTableModel().fireTableDataChanged();

            JOptionPane.showMessageDialog(null, "Movimento editado!");

        } catch (Exception e) {
            Logger.getLogger(UICadMovimento.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Erro ao editar movimento", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvar() {
        if (movimento == null) {
            cadastrar();
        } else {
            editar();
        }
    }

    private void remover(MovimentoItem item) {
        try {
            movimentoItemDAO.alterarItemMovimentado(false, item.getIdOrigem());
            movimentoItemDAO.deletar(item);
        } catch (SQLException e) {
            Logger.getLogger(UICadMovimento.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private MovimentoItem pegarMovimentoItemSelecionado() {
        if (jtMovimentosItens.getSelectedRow() == -1) {
            return null;
        }

        return movimentoItemTableModel.getItens().get(jtMovimentosItens.getSelectedRow());
    }

    private void abreTelaCheckList() {
        MovimentoItem item = pegarMovimentoItemSelecionado();
        MovimentoItemCheckListDAO movimentoItemCheckListDAO = new MovimentoItemCheckListDAO();
        if (item != null) {
            try {
                if (movimentoItemCheckListDAO.existeItem(item)) {
                    new UICheckList(item).setVisible(true);
                } else {
                    if (item.getPatrimonio().getId() > 0) {
                        movimentoItemCheckListDAO.inserirPatrimonio(item);

                        new UICheckList(item).setVisible(true);

                    } else {

                        new UIBuscarCheckListModelo(this, item).setVisible(true);

                        movimentoItemCheckListDAO.inserirProduto(item, checkListModelo);

                        new UICheckList(item).setVisible(true);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(UICadMovimento.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    "Selecione o item movimentado!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbCancelar = new javax.swing.JButton();
        jbSalvar = new javax.swing.JButton();
        jtpMovimento = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jpDados = new javax.swing.JPanel();
        jlEstoqueOrigem = new javax.swing.JLabel();
        jtfEstoqueOrigem = new javax.swing.JTextField();
        jbBuscarEstoqueOrigem = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jftfData = new javax.swing.JFormattedTextField();
        jtfResponsavel = new javax.swing.JTextField();
        jlResponsavel = new javax.swing.JLabel();
        jbBuscarResponsavel = new javax.swing.JButton();
        jlEstoqueDestino = new javax.swing.JLabel();
        jtfEstoqueDestino = new javax.swing.JTextField();
        jbBuscarEstoqueDestino = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtMovimentosItens = new javax.swing.JTable();
        jbAddPatrimonio = new javax.swing.JButton();
        jbAdicionarProduto = new javax.swing.JButton();
        jbRemover = new javax.swing.JButton();
        jchComprando = new javax.swing.JCheckBox();
        jbChecklist = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Movimento");
        setModal(true);
        setResizable(false);

        jbCancelar.setText("Cancelar");
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        jbSalvar.setText("Salvar");
        jbSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalvarActionPerformed(evt);
            }
        });

        jpDados.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));

        jlEstoqueOrigem.setText("Estoque de Origem");

        jtfEstoqueOrigem.setEditable(false);
        jtfEstoqueOrigem.setEnabled(false);

        jbBuscarEstoqueOrigem.setText("...");
        jbBuscarEstoqueOrigem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarEstoqueOrigemActionPerformed(evt);
            }
        });

        jLabel2.setText("Data");

        try {
            jftfData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        jtfResponsavel.setEditable(false);
        jtfResponsavel.setEnabled(false);

        jlResponsavel.setText("Responsável");

        jbBuscarResponsavel.setText("...");
        jbBuscarResponsavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarResponsavelActionPerformed(evt);
            }
        });

        jlEstoqueDestino.setText("Estoque de Destino");

        jtfEstoqueDestino.setEditable(false);
        jtfEstoqueDestino.setEnabled(false);

        jbBuscarEstoqueDestino.setText("...");
        jbBuscarEstoqueDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarEstoqueDestinoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpDadosLayout = new javax.swing.GroupLayout(jpDados);
        jpDados.setLayout(jpDadosLayout);
        jpDadosLayout.setHorizontalGroup(
            jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDadosLayout.createSequentialGroup()
                        .addComponent(jtfEstoqueDestino)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbBuscarEstoqueDestino))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDadosLayout.createSequentialGroup()
                        .addComponent(jtfEstoqueOrigem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbBuscarEstoqueOrigem))
                    .addGroup(jpDadosLayout.createSequentialGroup()
                        .addComponent(jtfResponsavel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbBuscarResponsavel))
                    .addGroup(jpDadosLayout.createSequentialGroup()
                        .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlEstoqueOrigem)
                            .addComponent(jLabel2)
                            .addComponent(jlResponsavel)
                            .addComponent(jlEstoqueDestino)
                            .addComponent(jftfData, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 501, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpDadosLayout.setVerticalGroup(
            jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlEstoqueOrigem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfEstoqueOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscarEstoqueOrigem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlEstoqueDestino)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfEstoqueDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscarEstoqueDestino))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlResponsavel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscarResponsavel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jftfData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jtpMovimento.addTab("Movimento", jPanel1);

        jtMovimentosItens.setModel(new javax.swing.table.DefaultTableModel(
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
        jtMovimentosItens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtMovimentosItensMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtMovimentosItens);

        jbAddPatrimonio.setText("Adicionar Patrimônio");
        jbAddPatrimonio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddPatrimonioActionPerformed(evt);
            }
        });

        jbAdicionarProduto.setText("Adicionar Produto");
        jbAdicionarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarProdutoActionPerformed(evt);
            }
        });

        jbRemover.setText("Remover");
        jbRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverActionPerformed(evt);
            }
        });

        jchComprando.setText("Comprando");

        jbChecklist.setText("Checklist");
        jbChecklist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbChecklistActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jbChecklist)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbRemover)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAdicionarProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAddPatrimonio)
                        .addGap(18, 18, 18)
                        .addComponent(jchComprando)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jchComprando)
                    .addComponent(jbAddPatrimonio)
                    .addComponent(jbAdicionarProduto)
                    .addComponent(jbRemover)
                    .addComponent(jbChecklist))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jtpMovimento.addTab("Itens do Movimento", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtpMovimento)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtpMovimento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbCancelar)
                    .addComponent(jbSalvar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbBuscarEstoqueOrigemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarEstoqueOrigemActionPerformed
        new UIBuscarEstoque(this, true).setVisible(true);
    }//GEN-LAST:event_jbBuscarEstoqueOrigemActionPerformed

    private void jbBuscarResponsavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarResponsavelActionPerformed
        new UIBuscarResponsavel(this).setVisible(true);
    }//GEN-LAST:event_jbBuscarResponsavelActionPerformed

    private void jbAdicionarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarProdutoActionPerformed
        new UIBuscarItensDoMovimento(this, movimento, true).setVisible(true);
    }//GEN-LAST:event_jbAdicionarProdutoActionPerformed

    private void jbAddPatrimonioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddPatrimonioActionPerformed
        new UIBuscarItensDoMovimento(this, movimento, false).setVisible(true);
    }//GEN-LAST:event_jbAddPatrimonioActionPerformed

    private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed
        salvar();
    }//GEN-LAST:event_jbSalvarActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void jbBuscarEstoqueDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarEstoqueDestinoActionPerformed
        new UIBuscarEstoque(this, false).setVisible(true);
    }//GEN-LAST:event_jbBuscarEstoqueDestinoActionPerformed

    private void jbRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverActionPerformed
        MovimentoItem item = pegarMovimentoItemSelecionado();

        if (item != null) {
            if (!item.isMovimentado()) {

                Object[] options = {"Sim", "Não"};
                int i = JOptionPane.showOptionDialog(null,
                        "Tem certeza que deseja remover?", "Remover",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                        options, options[0]);
                if (i == JOptionPane.YES_OPTION) {
                    remover(item);
                    movimentoItemTableModel.removeRow(item);
                    uiControleMovimentos.pesquisarItensMovimentados();
                }

            } else {
                JOptionPane.showMessageDialog(null, "Não é possível remover itens já movimentados!",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione o item que deseja remover!",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jbRemoverActionPerformed

    private void jbChecklistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbChecklistActionPerformed
        abreTelaCheckList();
    }//GEN-LAST:event_jbChecklistActionPerformed

    private void jtMovimentosItensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtMovimentosItensMouseClicked
        if (evt.getClickCount() == 2) {
            abreTelaCheckList();
        }
    }//GEN-LAST:event_jtMovimentosItensMouseClicked

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
            java.util.logging.Logger.getLogger(UICadMovimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UICadMovimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UICadMovimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UICadMovimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            UICadMovimento dialog = new UICadMovimento();
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAddPatrimonio;
    private javax.swing.JButton jbAdicionarProduto;
    private javax.swing.JButton jbBuscarEstoqueDestino;
    private javax.swing.JButton jbBuscarEstoqueOrigem;
    private javax.swing.JButton jbBuscarResponsavel;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbChecklist;
    private javax.swing.JButton jbRemover;
    private javax.swing.JButton jbSalvar;
    private javax.swing.JCheckBox jchComprando;
    private javax.swing.JFormattedTextField jftfData;
    private javax.swing.JLabel jlEstoqueDestino;
    private javax.swing.JLabel jlEstoqueOrigem;
    private javax.swing.JLabel jlResponsavel;
    private javax.swing.JPanel jpDados;
    private javax.swing.JTable jtMovimentosItens;
    private javax.swing.JTextField jtfEstoqueDestino;
    private javax.swing.JTextField jtfEstoqueOrigem;
    private javax.swing.JTextField jtfResponsavel;
    private javax.swing.JTabbedPane jtpMovimento;
    // End of variables declaration//GEN-END:variables
}
