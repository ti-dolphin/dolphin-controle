/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.Critica;
import dao.DAOFactory;
import dao.os.PessoaDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import model.os.Pessoa;
import model.os.PessoaTableModel;

/**
 *
 * @author guilherme.oliveira
 */
public class UIManPessoas extends javax.swing.JDialog {

    private PessoaDAO dao;
    private PessoaTableModel pTableModel;
    private TableRowSorter<PessoaTableModel> sorter;
    private ArrayList<Pessoa> pessoas;

    public UIManPessoas() {
        initComponents();
        this.dao = DAOFactory.getPESSOADAO();
        this.pTableModel = new PessoaTableModel();
        sorter = new TableRowSorter<>(pTableModel);
        jtaPessoa.setRowSorter(sorter);
        buscar();
        pesquisar();
        configurarTabela();
    }

    private void configurarTabela() {
        jtaPessoa.getTableHeader().setResizingAllowed(false);
        jtaPessoa.setAutoResizeMode(jtaPessoa.AUTO_RESIZE_OFF);
        jtaPessoa.getColumnModel().getColumn(0).setPreferredWidth(50);
        jtaPessoa.getColumnModel().getColumn(1).setPreferredWidth(684);
        jtaPessoa.getColumnModel().getColumn(2).setPreferredWidth(50);
    }

    public PessoaTableModel getpTableModel() {
        return pTableModel;
    }

    /**
     * Método usado para buscar pessoas no banco de dados
     */
    public void buscar() {
        try {

            long tempoInicial = System.currentTimeMillis();

            pessoas = dao.buscar();

            for (Pessoa pessoa : pessoas) {
                pTableModel.addRow(pessoa);
            }

            jtaPessoa.setModel(pTableModel);

            if (pessoas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum registro encontrado");
            }

            long tempoFinal = System.currentTimeMillis();
            System.out.printf("%.3f s%n", (tempoFinal - tempoInicial) / 1000d);

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao buscar pessoa",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método usado para pesquisar pessoas na tabela sem consultar o banco de
     * dados
     */
    public void pesquisar() {
        String nome = jtfNome.getText().trim();
        boolean ativos = jchAtivos.isSelected();
        List<RowFilter<Object, Object>> filters = new ArrayList<>();
        filters.add(RowFilter.regexFilter("(?i)" + nome, 1));
        filters.add(RowFilter.regexFilter("(?i)" + ativos, 2));
        sorter.setRowFilter(RowFilter.andFilter(filters));
    }

    /**
     * Método usado para excluir pessoa
     */
    public void excluir() {
        try {
            Pessoa pessoa = getPessoaSelecionada();

            dao.excluir(pessoa.getCodPessoa());
            
            pTableModel.removeRow(pessoa);
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao excluir pessoa", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método usado para limpar filtro
     */
    public void limparFiltro() {
        jtfNome.setText("");
        jchAtivos.setSelected(true);
    }

    /**
     * Método usado para pegar pessoa da linha selecionada
     *
     * @return Pessoa
     */
    public Pessoa getPessoaSelecionada() {
        if (jtaPessoa.getSelectedRow() > -1) {
            int linhaSelecionada = jtaPessoa.getRowSorter().convertRowIndexToModel(jtaPessoa.getSelectedRow());
            return pTableModel.getRowValue(linhaSelecionada);
        } else {
            return null;
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

        jtfNome = new javax.swing.JTextField();
        jlNome = new javax.swing.JLabel();
        jchAtivos = new javax.swing.JCheckBox();
        jbCadPessoa = new javax.swing.JButton();
        jbExcluir = new javax.swing.JButton();
        jbLimparFiltros = new javax.swing.JButton();
        jbPesquisar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaPessoa = new javax.swing.JTable();
        jbEditar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Manutenção de Pessoas");
        setModal(true);
        setResizable(false);

        jtfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfNomeKeyReleased(evt);
            }
        });

        jlNome.setText("Nome");

        jchAtivos.setSelected(true);
        jchAtivos.setText("Ativos");
        jchAtivos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jchAtivosItemStateChanged(evt);
            }
        });

        jbCadPessoa.setText("Novo");
        jbCadPessoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCadPessoaActionPerformed(evt);
            }
        });

        jbExcluir.setText("Excluir");
        jbExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExcluirActionPerformed(evt);
            }
        });

        jbLimparFiltros.setText("Limpar Filtros");
        jbLimparFiltros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimparFiltrosActionPerformed(evt);
            }
        });

        jbPesquisar.setText("Pesquisar");
        jbPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPesquisarActionPerformed(evt);
            }
        });

        jtaPessoa.setModel(new javax.swing.table.DefaultTableModel(
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
        jtaPessoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtaPessoaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtaPessoa);

        jbEditar.setText("Editar");
        jbEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEditarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtfNome, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jchAtivos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbLimparFiltros)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbPesquisar)
                        .addGap(100, 100, 100)
                        .addComponent(jbExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCadPessoa))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlNome)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbPesquisar)
                    .addComponent(jbLimparFiltros)
                    .addComponent(jbCadPessoa)
                    .addComponent(jbExcluir)
                    .addComponent(jchAtivos)
                    .addComponent(jbEditar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbCadPessoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCadPessoaActionPerformed
        new UICadPessoa(null, this).setVisible(true);
    }//GEN-LAST:event_jbCadPessoaActionPerformed

    private void jbExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExcluirActionPerformed
        Pessoa pessoa = getPessoaSelecionada();
        if (pessoa == null) {
            JOptionPane.showMessageDialog(this, "Selecione a pessoa que deseja excluir!");
        } else {
            Object[] options = {"Sim", "Não"};
            int i = JOptionPane.showOptionDialog(null,
                    "Tem certeza que deseja excluir?", "Excluir",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (i == JOptionPane.YES_OPTION) {
                try {
                    boolean podeExcluirApontamento = Critica.podeExcluir(
                            "APONTAMENTOS", "CODLIDER", getPessoaSelecionada().getCodPessoa(), "cadastro de apontamentos");
                    boolean podeExcluirSolicitante = Critica.podeExcluir("ORDEMSERVICO", "SOLICITANTE", getPessoaSelecionada().getCodPessoa(), "cadastro de OS/Tarefa (Solicitante)");
                    boolean podeExcluirResponsavel = Critica.podeExcluir("ORDEMSERVICO", "RESPONSAVEL", getPessoaSelecionada().getCodPessoa(), "cadastro de OS/Tarefa (Responsável)");
                    if (podeExcluirApontamento == true && podeExcluirResponsavel == true && podeExcluirSolicitante == true) {
                        excluir();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro ao tentar criticar", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_jbExcluirActionPerformed

    private void jbLimparFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimparFiltrosActionPerformed
        limparFiltro();
        pesquisar();
        jtfNome.requestFocus();
    }//GEN-LAST:event_jbLimparFiltrosActionPerformed

    private void jbPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPesquisarActionPerformed
        pesquisar();
        jtfNome.requestFocus();
    }//GEN-LAST:event_jbPesquisarActionPerformed

    private void jtaPessoaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtaPessoaMouseClicked
        if (evt.getClickCount() == 2) {
            new UICadPessoa(getPessoaSelecionada(), this).setVisible(true);
        }
    }//GEN-LAST:event_jtaPessoaMouseClicked

    private void jbEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEditarActionPerformed
        if (getPessoaSelecionada() != null) {
            new UICadPessoa(getPessoaSelecionada(), this).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Selecione a pessoa para edição",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jbEditarActionPerformed

    private void jchAtivosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jchAtivosItemStateChanged
        pesquisar();
    }//GEN-LAST:event_jchAtivosItemStateChanged

    private void jtfNomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNomeKeyReleased
        pesquisar();
    }//GEN-LAST:event_jtfNomeKeyReleased

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
            java.util.logging.Logger.getLogger(UIManPessoas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UIManPessoas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UIManPessoas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIManPessoas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UIManPessoas dialog = new UIManPessoas();
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbCadPessoa;
    private javax.swing.JButton jbEditar;
    private javax.swing.JButton jbExcluir;
    private javax.swing.JButton jbLimparFiltros;
    private javax.swing.JButton jbPesquisar;
    private javax.swing.JCheckBox jchAtivos;
    private javax.swing.JLabel jlNome;
    private javax.swing.JTable jtaPessoa;
    private javax.swing.JTextField jtfNome;
    // End of variables declaration//GEN-END:variables
}
