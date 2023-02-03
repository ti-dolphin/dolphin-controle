/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.apontamento;

import dao.DAOFactory;
import dao.apontamento.StatusApontDAO;
import dao.os.CentroCustoDAO;
import dao.os.PessoaDAO;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import model.apontamento.Apontamento;
import model.apontamento.StatusApont;
import model.os.CentroCusto;
import model.os.Pessoa;
import services.apontamento.ApontamentoService;
import view.Menu;
import view.UIManPessoas;

/**
 *
 * @author guilherme.oliveira
 */
public class UIApontamento extends javax.swing.JDialog {

    UIApontamentos uiApontamentos;
    private DefaultListModel lista = new DefaultListModel();
    private CentroCusto centroCusto;
    private ArrayList<CentroCusto> ca;
    private ApontamentoService apontamentoService;

    public UIApontamento(UIApontamentos uiApontamentos) {
        initComponents();
        this.apontamentoService = new ApontamentoService();
        this.uiApontamentos = uiApontamentos;
        preencherComboSA();
        preencherComboBoxLider();
        jspCentroCusto.setVisible(false);
        jlstCentroCusto.setVisible(false);
        preencherCampos();
        darPermissoes();
    }

    public void darPermissoes() {
        if (Menu.logado.isPermPessoas()) {
            jbAbrirLider.setEnabled(true);
        }
    }

    private void preencherCampos() {
        Apontamento a = uiApontamentos.getApontamento();
        if (a != null) {
            if (uiApontamentos.isFlagApontamentosEmMassa()) {
                uiApontamentos.setFlagApontamentosEmMassa(false);
                jtfCodCentroCusto.setText("-");
                jtfCentroCusto.setText("-");
                jcbStatus.setSelectedIndex(6);
                jcbLider.setSelectedIndex(0);
                jtaAtividade.setText(null);
            } else {
                jtfCentroCusto.setText(a.getCentroCusto().getNome());
                jtfCodCentroCusto.setText(a.getCentroCusto().getCodCusto());
                jcbLider.getModel().setSelectedItem(a.getLider());
                jcbStatus.getModel().setSelectedItem(a.getStatusApont());
                jtaAtividade.setText(a.getAtividade());
            }
        }
    }

    public void preencherCodApont() {
        Apontamento a = uiApontamentos.getApontamento();

        if (a != null) {
            jtfCodCentroCusto.setText(String.valueOf(a.getCodApont()));
        }
    }

    public void preencherComboSA() {
        StatusApontDAO dao = DAOFactory.getSTATUSAPONTDAO();

        try {
            for (StatusApont sa : dao.buscarCombo(false)) {
                jcbStatus.addItem(sa);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this,
                    se.getMessage(),
                    "Erro ao buscar status do apontamento",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método usado para preencher combobox do lider
     */
    public void preencherComboBoxLider() {
        PessoaDAO dao = DAOFactory.getPESSOADAO();

        try {
            for (Pessoa p : dao.buscarPessoasCombo("lider")) {
                jcbLider.addItem(p);
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage());
        }//fecha catch 
    }//fecha preencherCombo

    /**
     * Método usado para preencher lista de centro de custo da OS
     */
    public void preencherCentroCusto() {
        CentroCusto c = jlstCentroCusto.getSelectedValue();

        if (c.getCodCusto() != null) {
            jtfCentroCusto.setText(c.getNome());
            jtfCodCentroCusto.setText(c.getCodCusto());
        }
        jspCentroCusto.setVisible(false);
        jlstCentroCusto.setVisible(false);
    }//fecha preencherCC

    /**
     * Método usado para filtrar Centro de Custo
     */
    public void filtrarCentroCusto() {
        try {
            CentroCustoDAO dao = DAOFactory.getCENTROCUSTODAO();

            String query = "";

            if (!jtfCentroCusto.getText().isEmpty()) {
                query += " and NOME LIKE '%" + jtfCentroCusto.getText() + "%'";
            }

            if (jchAtivos.isSelected()) {
                query += " and ATIVO = TRUE";
            }

            ca = dao.buscar(query);
            for (int i = 0; i < ca.size(); i++) {
                centroCusto = ca.get(i);
                lista.addElement(centroCusto);
            }
            jlstCentroCusto.setModel(lista);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Erro ao filtrar centro de custo",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void salvar() {

        Menu.carregamento(true);

        new Thread(() -> {
            boolean apontamentoSalvo = false;
            List<Apontamento> apontamentos = uiApontamentos.getApontamentosSelecionados();

            for (Apontamento apontamento : apontamentos) {
                try {
                    CentroCusto c = new CentroCusto();

                    if (apontamento != null) {
                        Pessoa lider = (Pessoa) jcbLider.getSelectedItem();
                        StatusApont sa = (StatusApont) jcbStatus.getSelectedItem();

                        //Se nao for vinculado a OS pode editar
                        if (apontamento.getOrdemServico().getCodOs() == 0) {

                            if (apontamento.getFuncionario().getBancoHoras() <= 0
                                    && sa.getCodStatusApont().equals("FO")
                                    && !Menu.getUiLogin().getPessoa().isPermFolga()) {
                                throw new Exception("Não é permitido folga com banco de horas negativo");
                            }

                            //se centro de custo for vazio nao pode editar
                            if (jtfCodCentroCusto.getText().isEmpty()) {
                                throw new Exception("Insira o centro de custo!");
                            }

                            //se centro de custo for "-" e checkboxCentroCusto nao ta selecionada e status for "-" e checkboxStatus for selecionada gera erro
                            if (jtfCodCentroCusto.getText().isEmpty()
                                    && !jchAtualizarCentroCustoNaoPreenchido.isSelected()
                                    && sa.getCodStatusApont().equals("-")
                                    && jchAtualizarStatusNaoPreenchido.isSelected()) {
                                throw new Exception("Insira o centro de custo!");
                            }

                            //se centro de custo for "-" e checkboxCentroCusto nao estiver selecionado e status preenchido gera erro
                            if (jtfCodCentroCusto.getText().equals("-") || jtfCodCentroCusto.getText().isEmpty()
                                    && !jchAtualizarCentroCustoNaoPreenchido.isSelected()
                                    && !sa.getCodStatusApont().equals("-")) {
                                throw new Exception("Insira o centro de custo!");
                            }

                            if (jchAtualizarCentroCustoNaoPreenchido.isSelected()) {
                                if (apontamento.getCentroCusto().getCodCusto().equals("-")) {
                                    c.setCodCusto(jtfCodCentroCusto.getText());
                                    c.setNome(jtfCentroCusto.getText());
                                    apontamento.setCentroCusto(c);
                                }
                            } else {
                                c.setCodCusto(jtfCodCentroCusto.getText());
                                c.setNome(jtfCentroCusto.getText());
                                apontamento.setCentroCusto(c);
                            }

                            if (!jtaAtividade.getText().isEmpty()) {
                                apontamento.setAtividade(jtaAtividade.getText());
                            }

                            if (jchAtualizarStatusNaoPreenchido.isSelected()) {
                                if (apontamento.getStatusApont().getCodStatusApont().equals("-")) {
                                    apontamento.setStatusApont(sa);
                                }
                            } else {
                                if (!jtfCodCentroCusto.getText().isEmpty()) {
                                    apontamento.setStatusApont(sa);
                                }
                            }//else status apont
                        }

                        if (jchAtualizarLiderNaoPreenchido.isSelected()) {

                            if (apontamento.getLider().getCodPessoa() == 1) {
                                apontamento.setLider(lider);
                            }
                        } else {
                            apontamento.setLider(lider);
                        }//else

                        apontamentoService.salvar(apontamento);
                        apontamentoSalvo = true;
                    } else {
                        JOptionPane.showMessageDialog(UIApontamento.this,
                                "Apontamento não encontrado!",
                                "Erro ao salvar apontamento",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception ex) {
                    Logger.getLogger(UIApontamentos.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(UIApontamento.this, ex.getMessage(),
                            "Erro ao salvar apontamento!",
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    Menu.carregamento(false);
                }
            }
            
            if (apontamentoSalvo) {
                JOptionPane.showMessageDialog(UIApontamento.this, "Apontamento salvo!");
                this.dispose();
            }
        }).start();

    }//salvar

    /**
     * Método usado para atualizar combobox de lideres
     */
    private void atualizarCbxLider() {
        jcbLider.removeAllItems();
        preencherComboBoxLider();
    }//atualizarCbxLider

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlAtividade = new javax.swing.JLabel();
        jspAtividade = new javax.swing.JScrollPane();
        jtaAtividade = new javax.swing.JTextArea();
        jbSalvar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jspCentroCusto = new javax.swing.JScrollPane();
        jlstCentroCusto = new javax.swing.JList<>();
        jlLider = new javax.swing.JLabel();
        jtfCodCentroCusto = new javax.swing.JTextField();
        jlCentroCusto = new javax.swing.JLabel();
        jtfCentroCusto = new javax.swing.JTextField();
        jlStatus = new javax.swing.JLabel();
        jcbLider = new javax.swing.JComboBox<>();
        jlCodCentroCusto = new javax.swing.JLabel();
        jcbStatus = new javax.swing.JComboBox<>();
        jbAbrirLider = new javax.swing.JButton();
        jchAtualizarCentroCustoNaoPreenchido = new javax.swing.JCheckBox();
        jchAtualizarLiderNaoPreenchido = new javax.swing.JCheckBox();
        jchAtualizarStatusNaoPreenchido = new javax.swing.JCheckBox();
        jchAtivos = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Apontar Colaborador");
        setModal(true);
        setResizable(false);

        jlAtividade.setText("Atividade");

        jtaAtividade.setColumns(20);
        jtaAtividade.setRows(5);
        jspAtividade.setViewportView(jtaAtividade);

        jbSalvar.setText("Salvar");
        jbSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalvarActionPerformed(evt);
            }
        });

        jbCancelar.setText("Cancelar");
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlstCentroCusto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlstCentroCustoMouseClicked(evt);
            }
        });
        jspCentroCusto.setViewportView(jlstCentroCusto);

        jLayeredPane1.add(jspCentroCusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 500, 90));

        jlLider.setText("Líder");
        jLayeredPane1.add(jlLider, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, -1, -1));

        jtfCodCentroCusto.setEditable(false);
        jtfCodCentroCusto.setEnabled(false);
        jLayeredPane1.add(jtfCodCentroCusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 62, 30));

        jlCentroCusto.setText("Centro de Custo");
        jLayeredPane1.add(jlCentroCusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 11, -1, -1));

        jtfCentroCusto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfCentroCustoKeyPressed(evt);
            }
        });
        jLayeredPane1.add(jtfCentroCusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(78, 31, 500, 30));

        jlStatus.setText("Status");
        jLayeredPane1.add(jlStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 100, -1, -1));

        jcbLider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jcbLiderMouseClicked(evt);
            }
        });
        jLayeredPane1.add(jcbLider, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 226, 30));

        jlCodCentroCusto.setText("Código");
        jLayeredPane1.add(jlCodCentroCusto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, -1, -1));

        jLayeredPane1.add(jcbStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, 280, 30));

        jbAbrirLider.setText("...");
        jbAbrirLider.setEnabled(false);
        jbAbrirLider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAbrirLiderActionPerformed(evt);
            }
        });
        jLayeredPane1.add(jbAbrirLider, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 120, -1, 30));

        jchAtualizarCentroCustoNaoPreenchido.setSelected(true);
        jchAtualizarCentroCustoNaoPreenchido.setText("Atualizar somente centro de custo não preenchido");
        jLayeredPane1.add(jchAtualizarCentroCustoNaoPreenchido, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, -1, -1));

        jchAtualizarLiderNaoPreenchido.setSelected(true);
        jchAtualizarLiderNaoPreenchido.setText("Atualizar somente líder não preenchido");
        jLayeredPane1.add(jchAtualizarLiderNaoPreenchido, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, -1, -1));

        jchAtualizarStatusNaoPreenchido.setSelected(true);
        jchAtualizarStatusNaoPreenchido.setText("Atualizar somente status não preenchido");
        jLayeredPane1.add(jchAtualizarStatusNaoPreenchido, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 280, -1));

        jchAtivos.setSelected(true);
        jchAtivos.setText("Centros de custo ativos");
        jLayeredPane1.add(jchAtivos, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 160, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspAtividade, javax.swing.GroupLayout.DEFAULT_SIZE, 585, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jlAtividade))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlAtividade)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jspAtividade, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbSalvar)
                    .addComponent(jbCancelar))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalvarActionPerformed
        salvar();
    }//GEN-LAST:event_jbSalvarActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void jlstCentroCustoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlstCentroCustoMouseClicked
        preencherCentroCusto();
    }//GEN-LAST:event_jlstCentroCustoMouseClicked

    private void jtfCentroCustoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCentroCustoKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            lista.removeAllElements();
            filtrarCentroCusto();
            if (jtfCentroCusto.getText().isEmpty() || ca.isEmpty()) {
                jspCentroCusto.setVisible(false);
                jlstCentroCusto.setVisible(false);
            } else {
                jspCentroCusto.setVisible(true);
                jlstCentroCusto.setVisible(true);
            }
        }
    }//GEN-LAST:event_jtfCentroCustoKeyPressed

    private void jcbLiderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbLiderMouseClicked
        atualizarCbxLider();
    }//GEN-LAST:event_jcbLiderMouseClicked

    private void jbAbrirLiderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAbrirLiderActionPerformed
        UIManPessoas uiManPessoas = new UIManPessoas();
        if (!uiManPessoas.isVisible()) {
            uiManPessoas.setVisible(true);
        }
    }//GEN-LAST:event_jbAbrirLiderActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JButton jbAbrirLider;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbSalvar;
    private javax.swing.JComboBox<Pessoa> jcbLider;
    private javax.swing.JComboBox<StatusApont> jcbStatus;
    private javax.swing.JCheckBox jchAtivos;
    private javax.swing.JCheckBox jchAtualizarCentroCustoNaoPreenchido;
    private javax.swing.JCheckBox jchAtualizarLiderNaoPreenchido;
    private javax.swing.JCheckBox jchAtualizarStatusNaoPreenchido;
    private javax.swing.JLabel jlAtividade;
    private javax.swing.JLabel jlCentroCusto;
    private javax.swing.JLabel jlCodCentroCusto;
    private javax.swing.JLabel jlLider;
    private javax.swing.JLabel jlStatus;
    private javax.swing.JList<CentroCusto> jlstCentroCusto;
    private javax.swing.JScrollPane jspAtividade;
    private javax.swing.JScrollPane jspCentroCusto;
    private javax.swing.JTextArea jtaAtividade;
    private javax.swing.JTextField jtfCentroCusto;
    private javax.swing.JTextField jtfCodCentroCusto;
    // End of variables declaration//GEN-END:variables
}
