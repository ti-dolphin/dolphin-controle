/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.os;

/**
 *
 * @author ti
 */
public class FrmComentariosPadraoTarefa extends javax.swing.JDialog {

    UITarefas uiTarefas;
    
    public FrmComentariosPadraoTarefa(UITarefas uiTarefas) {
        super(uiTarefas, true);
        initComponents();
        this.uiTarefas = uiTarefas;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblComentarios = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblComentarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Perdido: Projeto Cancelado"},
                {"Perdido: Preço Acima de 20%"},
                {"Perdido: Preço Acima de 10%"},
                {"Perdido: Dolphin não possui experiência com escopo"},
                {"Perdido: A Dolphin não atende 100% do escopo"},
                {"Declinado: Projeto não faz parte dos serviços ofertados Dolphin"},
                {"Declinado: Não é cliente ou Dolphin não possui nenhum relacionamento"},
                {"Declinado: Pequenas empresas como concorrentes"},
                {"Declinado: Projeto apenas para estimativa"}
            },
            new String [] {
                "Comentários"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblComentarios.setColumnSelectionAllowed(true);
        tblComentarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblComentarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblComentariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblComentarios);
        tblComentarios.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblComentariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblComentariosMouseClicked
        if (evt.getClickCount() == 2) {
            String comentario = (String) tblComentarios.getValueAt(tblComentarios.getSelectedRow(), tblComentarios.getSelectedColumn());
            uiTarefas.getJtaComentariosOS().append(comentario + "\n");
            this.dispose();
        }
    }//GEN-LAST:event_tblComentariosMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblComentarios;
    // End of variables declaration//GEN-END:variables
}
