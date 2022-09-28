/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.os;

import dao.DAOFactory;
import dao.apontamento.ComentarioDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import model.apontamento.Comentario;
import model.os.ComentarioOSTableModel;
import model.os.OrdemServico;

/**
 *
 * @author guilherme.oliveira
 */
public class UIComentariosOS extends javax.swing.JInternalFrame {

    UIControleOS uiControleOS;
    private boolean flagUIComentar;
    private OrdemServico ordemServico;
    private ComentarioOSTableModel cOSTableModel = new ComentarioOSTableModel();

    /**
     * Creates new form ComentariosOS
     *
     * @param uiControleOS
     */
    public UIComentariosOS(UIControleOS uiControleOS) {
        initComponents();
        this.uiControleOS = uiControleOS;
        UIComentariosListener();
        preencherComentarios();
        redimensionarColunas();
    }

    public UIControleOS getUiControleOS() {
        return uiControleOS;
    }

    public boolean isFlagUIComentar() {
        return flagUIComentar;
    }

    public void setFlagUIComentar(boolean flagUIComentar) {
        this.flagUIComentar = flagUIComentar;
    }

    public ComentarioOSTableModel getcOSTableModel() {
        return cOSTableModel;
    }

    public void setcOSTableModel(ComentarioOSTableModel cOSTableModel) {
        this.cOSTableModel = cOSTableModel;
    }

    private void redimensionarColunas() {
        jtComentarios.setAutoResizeMode(jtComentarios.AUTO_RESIZE_OFF);
        jtComentarios.getColumnModel().getColumn(0).setPreferredWidth(50);//id
        jtComentarios.getColumnModel().getColumn(1).setPreferredWidth(500);//descricao
        jtComentarios.getColumnModel().getColumn(2).setPreferredWidth(150);//data
        jtComentarios.getColumnModel().getColumn(3).setPreferredWidth(300);//usuario
    }
    
    private void preencherCampos(){
        jtaComentariosOS.setText(getComentario().getDescricao());
    }//preencherCampos

    public void preencherComentarios() {
        ordemServico = uiControleOS.getOrdemServicoSelecionada();
        try {
            ComentarioDAO dao = DAOFactory.getCOMENTARIODAO();
            ArrayList<Comentario> ca = dao.buscarComentOS(ordemServico.getCodOs());

            for (int i = 0; i < ca.size(); i++) {
                cOSTableModel.addRow(ca.get(i));
            }//for

            jtComentarios.setModel(cOSTableModel);

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao buscar comentários", JOptionPane.ERROR_MESSAGE);
        }//catch
    }//preencherComentarios

    public Comentario getComentario() {
        if (jtComentarios.getSelectedRow() == -1) {
            return null;
        }

        return cOSTableModel.getComentarios().get(jtComentarios.getSelectedRow());
    }//getComentario

    private void limpar() {
        jtaComentariosOS.setText("");
    }//limpar

    public void atualizarTblComentarios() {
        limpar();
        cOSTableModel.clear();
        preencherComentarios();
    }

    private void salvar() {
        try {
            ComentarioDAO dao = DAOFactory.getCOMENTARIODAO();
            OrdemServico ordemServico = getUiControleOS()
                    .getOrdemServicoSelecionada();
            Comentario comentario = new Comentario();

            comentario.setOrdemServico(ordemServico);
            if (!jtaComentariosOS.getText().isEmpty()) {
                comentario.setDescricao(jtaComentariosOS.getText());
            } else {
                throw new NumberFormatException("Comentário em branco!");
            }
            comentario.setCreatedBy(uiControleOS.getMenu()
                    .getUiLogin().getPessoa().getLogin());

            dao.salvar(comentario);

            atualizarTblComentarios();

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao salvar comentário", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ne) {
            JOptionPane.showMessageDialog(this, ne.getMessage(), "",
                    JOptionPane.WARNING_MESSAGE);
        }
    }//salvar

    /**
     * Método usado para remover comentário da OS
     */
    public void excluir() {
        try {
            Comentario comentario = getComentario();

            if (comentario != null) {

                ComentarioDAO dao = DAOFactory.getCOMENTARIODAO();

                int codComentario = comentario.getCodComentario();

                dao.deletar(codComentario);

                cOSTableModel.removeRow(comentario);

            } else {
                JOptionPane.showMessageDialog(this, "Selecione o comentário que deseja excluir!");
            }//else
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao exlcuir", JOptionPane.ERROR_MESSAGE);
        }//catch
    }//remover

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtComentarios = new javax.swing.JTable();
        jbExcluir = new javax.swing.JButton();
        jbComentar = new javax.swing.JButton();
        jlComentario = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaComentariosOS = new javax.swing.JTextArea();

        setClosable(true);
        setIconifiable(true);
        setTitle("Comentarios OS/Tarefa");

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

        jlComentario.setText("Comentário");

        jtaComentariosOS.setColumns(20);
        jtaComentariosOS.setLineWrap(true);
        jtaComentariosOS.setRows(5);
        jScrollPane2.setViewportView(jtaComentariosOS);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbComentar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 906, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlComentario)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlComentario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbComentar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExcluirActionPerformed
        Comentario comentario = getComentario();
        String logado = uiControleOS.getMenu().getUiLogin().getPessoa().getLogin();
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
    }//GEN-LAST:event_jbExcluirActionPerformed

    private void jbComentarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbComentarActionPerformed
        salvar();
    }//GEN-LAST:event_jbComentarActionPerformed

    private void jtComentariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtComentariosMouseClicked
        if (evt.getClickCount() == 2) {
            preencherCampos();
        }
    }//GEN-LAST:event_jtComentariosMouseClicked

    public void UIComentariosListener() {
        this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent ife) {
                if (ife.getInternalFrame() instanceof UIComentariosOS) {
                    uiControleOS.setFlagUIComentarios(false);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbComentar;
    private javax.swing.JButton jbExcluir;
    private javax.swing.JLabel jlComentario;
    private javax.swing.JTable jtComentarios;
    private javax.swing.JTextArea jtaComentariosOS;
    // End of variables declaration//GEN-END:variables

}
