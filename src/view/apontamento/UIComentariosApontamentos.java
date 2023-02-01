/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.apontamento;


import dao.DAOFactory;
import dao.apontamento.ApontamentoDAO;
import dao.apontamento.ComentarioDAO;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import model.apontamento.Apontamento;
import model.apontamento.Comentario;
import model.apontamento.ComentarioApontTableModel;
import model.apontamento.StatusApont;
import view.Menu;

/**
 *
 * @author guilherme.oliveira
 */
public class UIComentariosApontamentos extends javax.swing.JDialog {

    UIApontamentos uiApontamentos;
    private boolean flagUIComentar;
    private ComentarioApontTableModel cAptTableModel = new ComentarioApontTableModel();
    private Apontamento apontamentoSelecionado;

    public UIComentariosApontamentos() {
    }
    
    public UIComentariosApontamentos(UIApontamentos uiApontamentos, Apontamento apontamentoSelecionado) {
        initComponents();
        this.uiApontamentos = uiApontamentos;
        this.apontamentoSelecionado = apontamentoSelecionado;
        gerarAvisoDeFolgaNaoPermitida();
        preencherComentarios();
        redimensionarColunas();
    }
    
    public ComentarioApontTableModel getcAptTableModel() {
        return cAptTableModel;
    }

    public UIApontamentos getUiApontamentos() {
        return uiApontamentos;
    }

    public void setUiApontamentos(UIApontamentos uiApontamentos) {
        this.uiApontamentos = uiApontamentos;
    }

    public boolean isFlagUIComentar() {
        return flagUIComentar;
    }

    public void setFlagUIComentar(boolean flagUIComentar) {
        this.flagUIComentar = flagUIComentar;
    }

    public JTextArea getJtaComentarios() {
        return jtaComentarios;
    }

    private void redimensionarColunas() {
        jtComentarios.setAutoResizeMode(jtComentarios.AUTO_RESIZE_OFF);
        jtComentarios.getColumnModel().getColumn(0).setPreferredWidth(50);//id
        jtComentarios.getColumnModel().getColumn(1).setPreferredWidth(300);//descricao
        jtComentarios.getColumnModel().getColumn(2).setPreferredWidth(150);//data
        jtComentarios.getColumnModel().getColumn(3).setPreferredWidth(300);//usuario
    }

    private void preencherCampos() {
        jtaComentarios.setText(getComentario().getDescricao());
    }//preencherCampos

    public void preencherComentarios() {
        try {
            ComentarioDAO dao = DAOFactory.getCOMENTARIODAO();
            ArrayList<Comentario> comentarios = dao.buscarComentApont(apontamentoSelecionado.getCodApont());

            for (int i = 0; i < comentarios.size(); i++) {
                cAptTableModel.addRow(comentarios.get(i));
            }//for

            jtComentarios.setModel(cAptTableModel);

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao buscar comentários", JOptionPane.ERROR_MESSAGE);
        }//catch
    }//preencherComentarios

    public Comentario getComentario() {
        if (jtComentarios.getSelectedRow() == -1) {
            return null;
        }

        return cAptTableModel.getComentarios().get(jtComentarios.getSelectedRow());
    }//getComentario

    private void limpar() {
        jtaComentarios.setText("");
    }//limpar
    
    private void gerarAvisoDeFolgaNaoPermitida() {
        
        StatusApont status = apontamentoSelecionado.getStatusApont();
        double saldoBancoHoras = apontamentoSelecionado.getFuncionario().getBancoHoras();
        
        System.out.println(saldoBancoHoras);
        if (saldoBancoHoras < 0) {
            lblAvisoFolgaNaoPerdida.setForeground(Color.red);
            lblAvisoFolgaNaoPerdida.setText("Aviso! Comentário de folga não é permitido se saldo do banco de horas for negativo.");
        }
    }

    private void salvar() {
        try {
            ComentarioDAO dao = DAOFactory.getCOMENTARIODAO();
            Comentario comentario = new Comentario();

            comentario.setApontamento(apontamentoSelecionado);
            if (!jtaComentarios.getText().isEmpty()) {
                comentario.setDescricao(jtaComentarios.getText());
            } else {
                throw new NumberFormatException("Comentário em branco!");
            }
            comentario.setCreatedBy(Menu.logado.getLogin());

            comentario.setLider(apontamentoSelecionado.getLider().getNome());
            
            dao.salvar(comentario);

            atualizaTblComentarios();
            limpar();

            atualizaColunaComentado(true);

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao salvar comentário", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ne) {
            JOptionPane.showMessageDialog(this, ne.getMessage());
        }
    }//salvar

    /**
     * Método usado para remover comentário do apontamento
     */
    public void excluir() {
        try {
            Comentario comentario = getComentario();
            ComentarioDAO dao = DAOFactory.getCOMENTARIODAO();

            int codComentario = comentario.getCodComentario();

            dao.deletar(codComentario);

            cAptTableModel.removeRow(comentario);

            if (cAptTableModel.getComentarios().isEmpty()) {
                atualizaColunaComentado(false);
            }

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao excluir", JOptionPane.ERROR_MESSAGE);
        }//catch
    }//remover

    private void atualizaTblComentarios() {
        cAptTableModel.clear();
        preencherComentarios();
    }

    private void atualizaColunaComentado(boolean comentado) {
        ApontamentoDAO dao = DAOFactory.getAPONTAMENTODAO();

        try {
            dao.updateComentado(apontamentoSelecionado, comentado);

        } catch (SQLException ex) {
            Logger.getLogger(UIComentariosApontamentos.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jtaComentarios = new javax.swing.JTextArea();
        jlComentario = new javax.swing.JLabel();
        jbComentariosPadrao = new javax.swing.JButton();
        jbExcluir = new javax.swing.JButton();
        jbComentar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtComentarios = new javax.swing.JTable();
        lblAvisoFolgaNaoPerdida = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Comentários do Apontamento");
        setModal(true);
        setResizable(false);

        jtaComentarios.setColumns(20);
        jtaComentarios.setLineWrap(true);
        jtaComentarios.setRows(5);
        jScrollPane2.setViewportView(jtaComentarios);

        jlComentario.setText("Comentário");

        jbComentariosPadrao.setText("Comentários Sugeridos");
        jbComentariosPadrao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbComentariosPadraoActionPerformed(evt);
            }
        });

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
        jScrollPane1.setViewportView(jtComentarios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlComentario)
                        .addGap(18, 18, 18)
                        .addComponent(lblAvisoFolgaNaoPerdida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbComentariosPadrao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbComentar))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlComentario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAvisoFolgaNaoPerdida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbComentariosPadrao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbComentar)
                        .addComponent(jbExcluir)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbComentariosPadraoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbComentariosPadraoActionPerformed
        new UIComentariosPadrao(this).setVisible(true);
    }//GEN-LAST:event_jbComentariosPadraoActionPerformed

    private void jbExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExcluirActionPerformed
        int linha = jtComentarios.getSelectedRow();
        Comentario comentario = getComentario();
        String logado = Menu.logado.getLogin();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione o tipo que deseja excluir!");
        } else {
            if (comentario.getCreatedBy().equals(logado) || logado.equals("adm")) {
                Object[] options = {"Sim", "Não"};
                int i = JOptionPane.showOptionDialog(null,
                    "Tem certeza que deseja excluir?", "Excluir",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
                if (i == JOptionPane.YES_OPTION) {
                    excluir();

                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Você não tem autorização para excluir esse comentário",
                    "Aviso ao tentar excluir comentário", JOptionPane.WARNING_MESSAGE);
            }//else
        }
    }//GEN-LAST:event_jbExcluirActionPerformed

    private void jbComentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbComentarActionPerformed
        salvar();
    }//GEN-LAST:event_jbComentarActionPerformed

    private void jtComentariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtComentariosMouseClicked
        if (evt.getClickCount() == 2) {
            preencherCampos();
        }
    }//GEN-LAST:event_jtComentariosMouseClicked

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
            java.util.logging.Logger.getLogger(UIComentariosApontamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UIComentariosApontamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UIComentariosApontamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIComentariosApontamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UIComentariosApontamentos dialog = new UIComentariosApontamentos();
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbComentar;
    private javax.swing.JButton jbComentariosPadrao;
    private javax.swing.JButton jbExcluir;
    private javax.swing.JLabel jlComentario;
    private javax.swing.JTable jtComentarios;
    private javax.swing.JTextArea jtaComentarios;
    private javax.swing.JLabel lblAvisoFolgaNaoPerdida;
    // End of variables declaration//GEN-END:variables
}
