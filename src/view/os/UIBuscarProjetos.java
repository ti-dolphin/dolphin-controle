/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.os;

import dao.os.AdicionalDAO;
import dao.os.OrdemServicoDAO;
import dao.os.ProjetoDAO;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import model.ProjetoTableModel;
import model.os.Cidade;
import model.os.Cliente;
import model.os.Estado;
import model.os.OrdemServico;
import model.os.Projeto;
import model.os.Segmento;
import model.os.TipoOs;
import view.Menu;

/**
 *
 * @author guilherme.oliveira
 */
public class UIBuscarProjetos extends javax.swing.JDialog {

    private UITarefas uiTarefas;
    private ProjetoDAO projetoDAO;
    private OrdemServicoDAO osDAO;
    private ProjetoTableModel projetoTableModel;
    private TableRowSorter<ProjetoTableModel> sorter;

    public UIBuscarProjetos() {
    }

    public UIBuscarProjetos(UITarefas uiTarefas) {
        initComponents();
        this.uiTarefas = uiTarefas;
        this.projetoDAO = new ProjetoDAO();
        this.osDAO = new OrdemServicoDAO();
        projetoTableModel = new ProjetoTableModel();
        sorter = new TableRowSorter<>(projetoTableModel);
        jtProjetos.setRowSorter(sorter);
        jtProjetos.setModel(projetoTableModel);
        buscar();
    }

    private void buscar() {
        Menu.carregamento(true);

        new Thread(() -> {
            try {
                TipoOs tipo = (TipoOs) uiTarefas.getJcbTipo().getSelectedItem();

                ArrayList<Projeto> projetos = projetoDAO.buscar(tipo.getCodTipoOs() == 21);

                for (Projeto projeto : projetos) {
                    projetoTableModel.addRow(projeto);
                }

            } catch (Exception e) {
                Logger.getLogger(UIBuscarProjetos.class.getName()).log(Level.SEVERE, null, e);
                JOptionPane.showMessageDialog(null, e.getMessage(),
                        "Erro ao buscar locais de estoques", JOptionPane.ERROR_MESSAGE);

            }
            Menu.carregamento(false);
        }).start();
    }

    private void pesquisar() {
        int codigo = Integer.parseInt(jtfCodigo.getText().trim());
        List<RowFilter<Object, Object>> filters = new ArrayList<>();
        filters.add(RowFilter.regexFilter("(?i)" + codigo, 0));

        sorter.setRowFilter(RowFilter.andFilter(filters));
    }

    private Projeto pegarProjetoSelecionado() throws IndexOutOfBoundsException {
        try {
            int linhaSelecionada = jtProjetos.getRowSorter().convertRowIndexToModel(jtProjetos.getSelectedRow());
            return projetoTableModel.getRowValue(linhaSelecionada);
        } catch (IndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Selecione o projeto!");
        }
    }

    private void carregarDados() {
        try {
            Projeto projeto = pegarProjetoSelecionado();
            if (projeto != null) {
                uiTarefas.setProjeto(projeto);
                uiTarefas.setAdicional(projeto.getAdicional());

                uiTarefas.getJtfCodProjeto().setText(String.valueOf(projeto.getId()));

                OrdemServico ordemServico = osDAO.buscarPorProjeto(projeto.getId());

                if (ordemServico.getCliente() != null) {
                    Cliente cliente = ordemServico.getCliente();
                    uiTarefas.getJtfCodCliente().setText(cliente.getCodCliente());
                    uiTarefas.getJtfCodColigada().setText(String.valueOf(cliente.getCodColigada()));
                    uiTarefas.getJtfCliente().setText(cliente.getNomeFantasia());
                }
                
                if (ordemServico.getSegmento() != null) {
                    Segmento segmento = ordemServico.getSegmento();
                    uiTarefas.getJcbSegmento().getModel().setSelectedItem(segmento);
                }

                Cidade cidade = ordemServico.getCidade();

                if (cidade != null) {

                    Estado estado = cidade.getEstado();

                    uiTarefas.getJcbCidade().getModel().setSelectedItem(cidade);

                    if (estado != null) {
                        estado.getNome();
                        uiTarefas.getJcbEstado().getModel().setSelectedItem(estado);
                    }
                }
                
                if (ordemServico.getObra() != null) {
                    if (!ordemServico.getObra().isEmpty()) {
                        System.out.println(ordemServico.getObra());
                        uiTarefas.getJtfObra().setText(ordemServico.getObra());
                    }
                }

                if (projeto.getAdicional().getNumero() == 0) {
                    uiTarefas.getJtfAdicional().setText(null);
                } else {
                    uiTarefas.getJtfAdicional()
                            .setText(String.valueOf(
                                        projeto.getAdicional().getNumero()));
                }

                this.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Selecione o projeto!", "", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IndexOutOfBoundsException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), 
                    "", JOptionPane.ERROR_MESSAGE);
        }
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
        jtProjetos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jtfCodigo = new javax.swing.JTextField();
        jbPesquisar = new javax.swing.JButton();
        jbCarregar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);

        jtProjetos.setModel(new javax.swing.table.DefaultTableModel(
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
        jtProjetos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtProjetosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtProjetos);

        jLabel1.setText("Código do Projeto");

        jtfCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfCodigoKeyPressed(evt);
            }
        });

        jbPesquisar.setText("Pesquisar");
        jbPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPesquisarActionPerformed(evt);
            }
        });

        jbCarregar.setText("Carregar");
        jbCarregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCarregarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtfCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbPesquisar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCarregar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbPesquisar)
                    .addComponent(jbCarregar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPesquisarActionPerformed
        pesquisar();
    }//GEN-LAST:event_jbPesquisarActionPerformed

    private void jbCarregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCarregarActionPerformed
        carregarDados();
    }//GEN-LAST:event_jbCarregarActionPerformed

    private void jtfCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCodigoKeyPressed
        pesquisar();
    }//GEN-LAST:event_jtfCodigoKeyPressed

    private void jtProjetosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtProjetosMouseClicked
        if (evt.getClickCount() == 2) {
            carregarDados();
        }
    }//GEN-LAST:event_jtProjetosMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbCarregar;
    private javax.swing.JButton jbPesquisar;
    private javax.swing.JTable jtProjetos;
    private javax.swing.JTextField jtfCodigo;
    // End of variables declaration//GEN-END:variables
}
