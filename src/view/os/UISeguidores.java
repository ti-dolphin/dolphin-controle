/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.os;

import dao.DAOFactory;
import dao.os.PessoaDAO;
import dao.os.SeguidoresDAO;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Seguidor;
import model.os.OrdemServico;
import model.os.Pessoa;
import model.os.PessoaTableModel;

/**
 *
 * @author guilherme.oliveira
 */
public class UISeguidores extends javax.swing.JDialog {

    private final PessoaTableModel pessoaTableModel = new PessoaTableModel();
    private UITarefas uiTarefas;
    ArrayList<Seguidor> seguidores = new ArrayList<>();
    private OrdemServico ordemServico;

    /**
     * Creates new form UISeguidores
     */
    public UISeguidores() {
    }

    public UISeguidores(OrdemServico ordemServico, UITarefas uiTarefas) {
        this.uiTarefas = uiTarefas;
        this.ordemServico = ordemServico;
        initComponents();
        buscarPessoas();
    }

    private void buscarPessoas() {
        try {

            PessoaDAO dao = DAOFactory.getPESSOADAO();
            String query = " ";

            if (!jtfNome.getText().isEmpty()) {
                query = query + " and NOME LIKE '%" + jtfNome.getText() + "%'";
            }//if

            ArrayList<Pessoa> pessoas;
            pessoas = dao.filtrar(query);

            pessoaTableModel.clear();

            for (Pessoa pessoa : pessoas) {
                pessoaTableModel.addRow(pessoa);
            }//for

            jtPessoas.setModel(pessoaTableModel);

            if (pessoas.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Nenhum registro encontrado para a pesquisa!");
            }//if

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro ao buscar pessoas", JOptionPane.ERROR_MESSAGE);
        }//catch
    }//filtrar

    public Pessoa getPessoaSelecionada() {
        if (jtPessoas.getSelectedRow() == -1) {
            return null;
        }

        return pessoaTableModel.getPessoas().get(jtPessoas.getSelectedRow());
    }//fecha getTipo

    private void adicionar() {
        try {
            SeguidoresDAO dao = DAOFactory.getSEGUIDORESDAO();
            Seguidor seguidor = new Seguidor();
            Pessoa pessoa = getPessoaSelecionada();

            if (pessoa != null) {

                seguidor.setOrdemServico(ordemServico);
                seguidor.setPessoa(pessoa);
                dao.inserir(seguidor);

                ordemServico.setSeguidores(
                        dao.buscarSeguidoresDaOs(
                                ordemServico.getCodOs()));
                
                uiTarefas.getSeguidoresTableModel().clear();
                uiTarefas.buscarSeguidores();
                JOptionPane.showMessageDialog(this, "Seguidor adicionado!");

            } else {
                JOptionPane.showMessageDialog(this, "Selecione o seguidor que deseja adicionar!",
                        "Aviso ao adicionar seguidor",
                        JOptionPane.WARNING_MESSAGE);
            }//else

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro ao adicionar seguidor! ",
                    JOptionPane.ERROR_MESSAGE);
        }//catch
    }//adicionar

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
        jbAdicionarSeguidor = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtPessoas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Adicionar seguidores");
        setModal(true);

        jtfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfNomeKeyPressed(evt);
            }
        });

        jlNome.setText("Nome");

        jbAdicionarSeguidor.setText("Adicionar");
        jbAdicionarSeguidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarSeguidorActionPerformed(evt);
            }
        });

        jButton1.setText("Pesquisar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jtPessoas.setModel(new javax.swing.table.DefaultTableModel(
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
        jtPessoas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtPessoasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtPessoas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfNome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlNome)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAdicionarSeguidor))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbAdicionarSeguidor)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addGap(9, 9, 9))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtfNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buscarPessoas();
        }
    }//GEN-LAST:event_jtfNomeKeyPressed

    private void jbAdicionarSeguidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarSeguidorActionPerformed
        adicionar();
    }//GEN-LAST:event_jbAdicionarSeguidorActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        buscarPessoas();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtPessoasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtPessoasMouseClicked
        if (evt.getClickCount() == 2) {
            buscarPessoas();
        }
    }//GEN-LAST:event_jtPessoasMouseClicked

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
            java.util.logging.Logger.getLogger(UISeguidores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UISeguidores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UISeguidores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UISeguidores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UISeguidores dialog = new UISeguidores();
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
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAdicionarSeguidor;
    private javax.swing.JLabel jlNome;
    private javax.swing.JTable jtPessoas;
    private javax.swing.JTextField jtfNome;
    // End of variables declaration//GEN-END:variables
}
