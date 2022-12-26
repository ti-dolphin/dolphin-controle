/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view.epi;

import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Funcionario;
import model.epi.EpiFuncionario;
import services.epi.EpiFuncionarioService;
import view.Menu;

/**
 *
 * @author ti
 */
public class UIEntregarEPI extends javax.swing.JDialog {

    private final EpiFuncionarioService epiFuncionarioService;
    private final EpiFuncionario epiFuncionario;
    private final UIFuncionarioEPI uiFuncionario;
    private boolean estaEntregando;
    public static byte[] digital;
    public static int senha = 1;

    public UIEntregarEPI(UIFuncionarioEPI uiFuncionarioEpi, EpiFuncionario epiFuncionario, boolean estaEntregando) {
        this.epiFuncionarioService = new EpiFuncionarioService();
        this.epiFuncionario = epiFuncionario;
        this.uiFuncionario = uiFuncionarioEpi;
        this.estaEntregando = estaEntregando;
        initComponents();
    }
    
    public void devolverEpi() {
        try {
            epiFuncionario.setModifiedBy(Menu.logado.getLogin());
            epiFuncionarioService.alterarEpiFuncionario(epiFuncionario);
            uiFuncionario.filtrarHistoricoDoFunc();
        } catch (SQLException se) {
            Logger.getLogger(UIEntregarEPI.class.getName()).log(Level.SEVERE, null, se);
            JOptionPane.showMessageDialog(null, se.getMessage(),
                    "Erro ao devolver epi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void entregarEpi() {
        try {
            epiFuncionario.setEmailEnviado(false);
            epiFuncionario.setCreatedBy(Menu.logado.getLogin());
            epiFuncionarioService.cadastrarEpiFuncionario(epiFuncionario);
            uiFuncionario.filtrarHistoricoDoFunc();
            JOptionPane.showMessageDialog(null, "EPI entregue!");
        } catch (SQLException se) {
            Logger.getLogger(UIEntregarEPI.class.getName()).log(Level.SEVERE, null, se);
            JOptionPane.showMessageDialog(null, se.getMessage(),
                    "Erro ao inserir histórico", JOptionPane.ERROR_MESSAGE);
        }
    }
    
//    public void lerDigital() {
//        Funcionario funcionario = epiFuncionario.getFuncionario();
//        //testa se funcionario tem alguma digital cadatrada
//        if (funcionario.getFinger1() != null || funcionario.getFinger2() != null || funcionario.getFinger3() != null
//                || funcionario.getFinger4() != null || funcionario.getFinger5() != null || funcionario.getFinger6() != null) {
//            CisBiox biox = new CisBiox();
//            try {
//
//                int iRetorno = biox.iniciar();
//
//                jlOk.setText("Coloque o dedo!");
//                jlOk.setForeground(Color.blue);
//
//                if (iRetorno != 1) {
//                    throw new RetornosException(iRetorno);
//                }
//
//                digital = biox.capturarDigital();
//
//                if (biox.getResultado() != 1) {
//                    biox.finalizar();
//                    jlOk.setText(biox.mensagens(biox.getResultado()));
//                    jlOk.setForeground(Color.red);
//                }
//
//                iRetorno = biox.finalizar();
//
//                if (iRetorno != 1) {
//                    jlOk.setText(biox.mensagens(iRetorno));
//                    jlOk.setForeground(Color.red);
//                    return;
//                }
//
//                iRetorno = biox.iniciar();
//
//                if (iRetorno != 1) {
//                    throw new RetornosException(iRetorno);
//                }
//
//                byte[][] digitais = new byte[6][699];
//                digitais[0] = funcionario.getFinger1();
//                digitais[1] = funcionario.getFinger2();
//                digitais[2] = funcionario.getFinger3();
//                digitais[3] = funcionario.getFinger4();
//                digitais[4] = funcionario.getFinger5();
//                digitais[5] = funcionario.getFinger6();
//
//                for (int i = 0; i < digitais.length; i++) {
//                    iRetorno = biox.compararDigital(digital, digitais[i]);
//                    if (iRetorno == 1) {
//                        break;
//                    }
//                }//fecha for
//                switch (iRetorno) {
//                    case 1:
//                        jlOk.setText("Digital encontrada!");
//                        jlOk.setForeground(Color.green);
//                        FichaEpiDAO dao = DAOFactory.getFICHAEPIDAO();
//                        parent.setCodFichaEpi(dao.cadastrarFicha());
//                        parent.inserirHistorico();
//                        parent.getUiControleEpi().atualizarTblHistorico();
//                        this.dispose();
//                        break;
//                    case -2:
//                        jlOk.setText("Digital não encontrada!");
//                        jlOk.setForeground(Color.red);
//                        break;
//                    default:
//                        jlOk.setText(biox.mensagens(iRetorno));
//                        jlOk.setForeground(Color.red);
//                        break;
//                }
//
//                biox.finalizar();
//            } catch (NullPointerException ne) {
//                biox.finalizar();
//                jlOk.setText("Erro ao ler digital! ");
//                jlOk.setForeground(Color.red);
//            } catch (RetornosException re) {
//                biox.finalizar();
//                jlOk.setText("Erro ao ler digital! " + re.getMessage());
//                jlOk.setForeground(Color.red);
//            } catch (SQLException ex) {
//                JOptionPane.showMessageDialog(this, ex.getMessage(), 
//                        "Erro ao Entrgar EPI", JOptionPane.ERROR_MESSAGE);
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Nenhuma digital cadastrada!",
//                    "Erro ao ler digital", JOptionPane.ERROR_MESSAGE);
//        }
//    }//fecha lerDigital
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlSenha = new javax.swing.JLabel();
        jpfSenha = new javax.swing.JPasswordField();
        jlOk = new javax.swing.JLabel();
        jbLerDigital = new javax.swing.JButton();
        jbDigitarSenha = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);

        jlSenha.setText("Senha");

        jpfSenha.setEnabled(false);
        jpfSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jpfSenhaKeyPressed(evt);
            }
        });

        jlOk.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlOk.setForeground(new java.awt.Color(51, 102, 255));
        jlOk.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jbLerDigital.setText("Ler Digital");
        jbLerDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLerDigitalActionPerformed(evt);
            }
        });

        jbDigitarSenha.setText("Digitar Senha");
        jbDigitarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDigitarSenhaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlSenha)
                    .addComponent(jlOk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbLerDigital, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbDigitarSenha))
                    .addComponent(jpfSenha))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlOk, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbLerDigital, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbDigitarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jpfSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpfSenhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                String strSenha = String.valueOf(jpfSenha.getPassword());
                senha = Integer.parseInt(strSenha);
                Funcionario funcionario = epiFuncionario.getFuncionario();
                if (senha == funcionario.getSenha()) {
                    if (estaEntregando) {
                        entregarEpi();
                    } else {
                        devolverEpi();
                    }
                    this.dispose();
                } else {
                    jpfSenha.setText("");
                    jpfSenha.requestFocus();
                    JOptionPane.showMessageDialog(null, "Senha incorreta!");
                }
            } catch (HeadlessException | NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "" + nfe.getMessage());
            }
        }
    }//GEN-LAST:event_jpfSenhaKeyPressed

    private void jbLerDigitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLerDigitalActionPerformed
//        lerDigital();
    }//GEN-LAST:event_jbLerDigitalActionPerformed

    private void jbDigitarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDigitarSenhaActionPerformed
        // TODO add your handling code here:
        Funcionario funcionario = epiFuncionario.getFuncionario();
        if (funcionario.getSenha() != 0) {
            jpfSenha.setEnabled(true);
            jpfSenha.requestFocus();

        } else {
            JOptionPane.showMessageDialog(null, "Senha não cadastrada!",
                "Erro ao autenticar senha!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jbDigitarSenhaActionPerformed

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton jbDigitarSenha;
    private javax.swing.JButton jbLerDigital;
    private static javax.swing.JLabel jlOk;
    private javax.swing.JLabel jlSenha;
    private javax.swing.JPasswordField jpfSenha;
    // End of variables declaration//GEN-END:variables
}
