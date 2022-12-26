/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import view.epi.UIControleEpi;
import dao.SistemaDAO;
import javax.swing.ImageIcon;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import model.apontamento.Apontamento;
import model.os.Pessoa;
import services.NotificacaoService;
import view.apontamento.UIApontamentos;
import view.os.UIControleOS;
import view.ponto.UIAutomacaoPonto;

/**
 *
 * @author guilherme.oliveira
 */
public class Menu extends javax.swing.JFrame implements InternalFrameListener {

    public static UILogin uiLogin;
    private boolean flagUIControleEpi = false;
    private boolean flagUIControleOS = false;
    private boolean flagUIApontamentos = false;
    private boolean flagUIManPessoa = false;
    private boolean flagUIControleProdutos = false;
    private NotificacaoService notificacaoService;
    private UIApontamentos uiApontamentos;

    public static Pessoa logado;

    /**
     * Creates new form jMenu
     *
     * @param uiLogin
     */
    public Menu(UILogin uiLogin) {
        initComponents();
        this.uiLogin = uiLogin;
        this.notificacaoService = new NotificacaoService();
        logado = Menu.uiLogin.getPessoa();
        setExtendedState(MAXIMIZED_BOTH);
        jlUsuario.setText("Usuário: " + logado.getNome());
        jlVersao.setText("Versão: " + Principal.VERSAO);
        timer();
        carregamento(false);
        darPermissoes();
    }

    private void buscaDataSincronizacao() {
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            LocalDateTime data = new SistemaDAO().buscar().getDataSincronizacao();
            String dataFormatada = data.format(formatter);

            jlDataSincronizacao.setText("Data da última sincronização: " + dataFormatada);
        } catch (SQLException ex) {
            jlDataSincronizacao.setText(ex.getMessage());
        }
    }

    private void timer() {

        int delay = 2000;   // delay de 2 seg.
        int interval = 60000;  // intervalo de 30 min.
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                try {
                    notificacaoService.atualizarNotificacoes();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,
                            ex.getMessage(),
                            "Erro ao atualizar número de notificações",
                            JOptionPane.ERROR_MESSAGE
                    );
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                buscaDataSincronizacao();
            }
        }, delay, interval);
    }

    public boolean isFlagUIApontamentos() {
        return flagUIApontamentos;
    }

    public void setFlagUIApontamentos(boolean flagUIApontamentos) {
        this.flagUIApontamentos = flagUIApontamentos;
    }

    public static UILogin getUiLogin() {
        return uiLogin;
    }

    public boolean isFlagUIManPessoa() {
        return flagUIManPessoa;
    }

    public void setFlagUIManPessoa(boolean flagUIManPessoa) {
        this.flagUIManPessoa = flagUIManPessoa;
    }

    public JDesktopPane getJdpAreaTrabalho() {
        return jdpAreaTrabalho;
    }

    public static void carregamento(boolean carregando) {
        Menu.jpbBarraProgresso.setIndeterminate(carregando);
        Menu.jpbBarraProgresso.setVisible(carregando);
    }

    public void darPermissoes() {
        if (logado.isPermPessoas()) {
            jmiCadPessoa.setEnabled(true);
        }
        if (logado.isPermEpi()) {
            jmiEpi.setEnabled(true);
        }
        if (logado.isPermOS()) {
            jmiOs.setEnabled(true);
        }
        if (logado.isPermApont()) {
            jmiApontamento.setEnabled(true);
        }
        if (logado.isPermPonto()) {
            jmiAutomacaoPonto.setEnabled(true);
        }
        if (logado.isPermGestaoPessoas()) {
            jmGestaoDePessoas.setEnabled(true);
        }
        if (logado.isPermControleRecesso()) {
            jmiControleRecesso.setEnabled(true);
        }
        if (logado.isPermFerramentas()) {
            jmiFerramentas.setEnabled(true);
        }
        if (logado.isPermCadCheckList()) {
            jmiCheckList.setEnabled(true);
        }
        if (logado.isPermProspeccao()) {
            mComercial.setEnabled(true);
            miProspeccao.setEnabled(true);
        }
    }
    
    public void abrirTelaApontamentos(Apontamento apontamento) throws HeadlessException {
        try {
            if (!flagUIApontamentos) {
                final UICarregando carregando = new UICarregando(this, false);
                carregando.setVisible(true);
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        if (apontamento != null) {
                            uiApontamentos = new UIApontamentos(apontamento);
                        } else {
                            uiApontamentos = new UIApontamentos();
                        }
                        jdpAreaTrabalho.add(uiApontamentos);
                        try {
                            uiApontamentos.setMaximum(true);
                        } catch (PropertyVetoException e) {
                            JOptionPane.showMessageDialog(null, e.getMessage(),
                                    "Erro ao maximizar tela!",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        uiApontamentos.setVisible(true);
                        flagUIApontamentos = true;
                        uiApontamentos.addInternalFrameListener(Menu.this);
                        uiApontamentos.setLocation(
                                jdpAreaTrabalho.getWidth() / 2 - uiApontamentos.getWidth() / 2,
                                jdpAreaTrabalho.getHeight() / 2 - uiApontamentos.getHeight() / 2);
                        carregando.dispose();
                    }
                };
                t.start();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/dse-bg.jpg")); Image image = icon.getImage();
        jdpAreaTrabalho = new javax.swing.JDesktopPane(){     public void paintComponent(Graphics g){         g.drawImage(image, 0, 0, getWidth(), getHeight(), this);     } };
        jpBarraInferior = new javax.swing.JPanel();
        jlUsuario = new javax.swing.JLabel();
        jlVersao = new javax.swing.JLabel();
        jlEmpresa = new javax.swing.JLabel();
        jpbBarraProgresso = new javax.swing.JProgressBar();
        jlDataSincronizacao = new javax.swing.JLabel();
        jmbMenu = new javax.swing.JMenuBar();
        jmSistema = new javax.swing.JMenu();
        jmiSair = new javax.swing.JMenuItem();
        jmiAlterarSenha = new javax.swing.JMenuItem();
        jmCadastro = new javax.swing.JMenu();
        jmiCadPessoa = new javax.swing.JMenuItem();
        jmiCheckList = new javax.swing.JMenuItem();
        jmModulo = new javax.swing.JMenu();
        jmiEpi = new javax.swing.JMenuItem();
        jmiFerramentas = new javax.swing.JMenuItem();
        jmiOs = new javax.swing.JMenuItem();
        jmiApontamento = new javax.swing.JMenuItem();
        jmiAutomacaoPonto = new javax.swing.JMenuItem();
        jmGestaoDePessoas = new javax.swing.JMenu();
        jmiControleRecesso = new javax.swing.JMenuItem();
        mComercial = new javax.swing.JMenu();
        miProspeccao = new javax.swing.JMenuItem();
        jmNotificacoes = new javax.swing.JMenu();
        jmAjuda = new javax.swing.JMenu();
        jmiSobre = new javax.swing.JMenuItem();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dolphin Controle");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/img/dse-icone.png")).getImage());

        javax.swing.GroupLayout jdpAreaTrabalhoLayout = new javax.swing.GroupLayout(jdpAreaTrabalho);
        jdpAreaTrabalho.setLayout(jdpAreaTrabalhoLayout);
        jdpAreaTrabalhoLayout.setHorizontalGroup(
            jdpAreaTrabalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1363, Short.MAX_VALUE)
        );
        jdpAreaTrabalhoLayout.setVerticalGroup(
            jdpAreaTrabalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 734, Short.MAX_VALUE)
        );

        jlUsuario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jlUsuario.setText("Usuário: Erro ao buscar nome de usuário");

        jlVersao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jlVersao.setText("Versão: 0.0.0.0");

        jlEmpresa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jlEmpresa.setText("Dolphin Soluções em Engenharia");

        jpbBarraProgresso.setToolTipText("");
        jpbBarraProgresso.setIndeterminate(true);
        jpbBarraProgresso.setString("Aguarde, carregando dados...");

        jlDataSincronizacao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

        javax.swing.GroupLayout jpBarraInferiorLayout = new javax.swing.GroupLayout(jpBarraInferior);
        jpBarraInferior.setLayout(jpBarraInferiorLayout);
        jpBarraInferiorLayout.setHorizontalGroup(
            jpBarraInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBarraInferiorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlVersao, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlDataSincronizacao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jpbBarraProgresso, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlEmpresa)
                .addContainerGap())
        );
        jpBarraInferiorLayout.setVerticalGroup(
            jpBarraInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBarraInferiorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpBarraInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlEmpresa)
                    .addComponent(jpbBarraProgresso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpBarraInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlUsuario)
                        .addComponent(jlVersao)
                        .addComponent(jlDataSincronizacao)))
                .addContainerGap())
        );

        jmSistema.setText("Sistema");

        jmiSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        jmiSair.setText("Sair");
        jmiSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSairActionPerformed(evt);
            }
        });
        jmSistema.add(jmiSair);

        jmiAlterarSenha.setText("Alterar Senha");
        jmiAlterarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAlterarSenhaActionPerformed(evt);
            }
        });
        jmSistema.add(jmiAlterarSenha);

        jmbMenu.add(jmSistema);

        jmCadastro.setText("Cadastro");

        jmiCadPessoa.setText("Pessoas");
        jmiCadPessoa.setEnabled(false);
        jmiCadPessoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiCadPessoaActionPerformed(evt);
            }
        });
        jmCadastro.add(jmiCadPessoa);

        jmiCheckList.setText("CheckList");
        jmiCheckList.setEnabled(false);
        jmiCheckList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiCheckListActionPerformed(evt);
            }
        });
        jmCadastro.add(jmiCheckList);

        jmbMenu.add(jmCadastro);

        jmModulo.setText("Módulo");

        jmiEpi.setText("EPI's");
        jmiEpi.setEnabled(false);
        jmiEpi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiEpiActionPerformed(evt);
            }
        });
        jmModulo.add(jmiEpi);

        jmiFerramentas.setText("Ferramentas");
        jmiFerramentas.setEnabled(false);
        jmiFerramentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiFerramentasActionPerformed(evt);
            }
        });
        jmModulo.add(jmiFerramentas);

        jmiOs.setText("OS/Tarefa");
        jmiOs.setEnabled(false);
        jmiOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiOsActionPerformed(evt);
            }
        });
        jmModulo.add(jmiOs);

        jmiApontamento.setText("Apontamento");
        jmiApontamento.setEnabled(false);
        jmiApontamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiApontamentoActionPerformed(evt);
            }
        });
        jmModulo.add(jmiApontamento);

        jmiAutomacaoPonto.setText("Automação de Ponto");
        jmiAutomacaoPonto.setEnabled(false);
        jmiAutomacaoPonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAutomacaoPontoActionPerformed(evt);
            }
        });
        jmModulo.add(jmiAutomacaoPonto);

        jmGestaoDePessoas.setText("Gestão de Pessoas");
        jmGestaoDePessoas.setEnabled(false);

        jmiControleRecesso.setText("Controle Recesso");
        jmiControleRecesso.setEnabled(false);
        jmiControleRecesso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiControleRecessoActionPerformed(evt);
            }
        });
        jmGestaoDePessoas.add(jmiControleRecesso);

        jmModulo.add(jmGestaoDePessoas);

        mComercial.setText("Comercial");
        mComercial.setEnabled(false);

        miProspeccao.setText("Prospecção");
        miProspeccao.setEnabled(false);
        miProspeccao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miProspeccaoActionPerformed(evt);
            }
        });
        mComercial.add(miProspeccao);

        jmModulo.add(mComercial);

        jmbMenu.add(jmModulo);

        jmNotificacoes.setText("Notificações");
        jmNotificacoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jmNotificacoesMouseClicked(evt);
            }
        });
        jmbMenu.add(jmNotificacoes);

        jmAjuda.setText("Ajuda");

        jmiSobre.setText("Sobre");
        jmiSobre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jmiSobreMousePressed(evt);
            }
        });
        jmAjuda.add(jmiSobre);

        jmbMenu.add(jmAjuda);

        setJMenuBar(jmbMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jdpAreaTrabalho)
            .addComponent(jpBarraInferior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jdpAreaTrabalho)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpBarraInferior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jmiSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSairActionPerformed
        Object[] options = {"Sim", "Não"};
        int i = JOptionPane.showOptionDialog(null,
                "Tem certeza que deseja sair?", "Saída",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);
        if (i == JOptionPane.YES_OPTION) {
            this.dispose();
            UILogin login = new UILogin();
            login.setVisible(true);
        }
    }//GEN-LAST:event_jmiSairActionPerformed

    private void jmiEpiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiEpiActionPerformed
        try {
            if (!flagUIControleEpi) {
                UICarregando carregando = new UICarregando(this, false);
                carregando.setVisible(true);
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        UIControleEpi uiControleEpi = new UIControleEpi(Menu.this);
                        jdpAreaTrabalho.add(uiControleEpi);
                        try {
                            uiControleEpi.setMaximum(true);
                        } catch (PropertyVetoException e) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    e.getMessage(),
                                    "Erro ao maximizar tela!",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                        uiControleEpi.setVisible(true);
                        flagUIControleEpi = true;
                        uiControleEpi.addInternalFrameListener(Menu.this);
                        uiControleEpi.setLocation(
                                jdpAreaTrabalho.getWidth() / 2 - uiControleEpi.getWidth() / 2,
                                jdpAreaTrabalho.getHeight() / 2 - uiControleEpi.getHeight() / 2
                        );
                        carregando.dispose();
                    }
                };
                t.start();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jmiEpiActionPerformed

    private void jmiOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiOsActionPerformed
        try {
            if (!flagUIControleOS) {
                final UICarregando carregando = new UICarregando(this, false);
                carregando.setVisible(true);
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        UIControleOS uiControleOS = new UIControleOS(Menu.this);
                        jdpAreaTrabalho.add(uiControleOS);
                        try {
                            uiControleOS.setMaximum(true);
                        } catch (PropertyVetoException e) {
                            JOptionPane.showMessageDialog(null, e.getMessage(),
                                    "Erro ao maximizar tela!",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        uiControleOS.setVisible(true);
                        flagUIControleOS = true;
                        uiControleOS.addInternalFrameListener(Menu.this);
                        uiControleOS.setLocation(
                                jdpAreaTrabalho.getWidth() / 2 - uiControleOS.getWidth() / 2,
                                jdpAreaTrabalho.getHeight() / 2 - uiControleOS.getHeight() / 2
                        );
                        carregando.dispose();
                    }
                };
                t.start();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e.getMessage());
        }
    }//GEN-LAST:event_jmiOsActionPerformed

    private void jmiCadPessoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiCadPessoaActionPerformed
        UIManPessoas telaManPessoas = new UIManPessoas();
        telaManPessoas.setVisible(true);
    }//GEN-LAST:event_jmiCadPessoaActionPerformed

    private void jmiSobreMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmiSobreMousePressed
        new UISobre(this, false).setVisible(true);
    }//GEN-LAST:event_jmiSobreMousePressed

    private void jmiApontamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiApontamentoActionPerformed
        abrirTelaApontamentos(null);
    }//GEN-LAST:event_jmiApontamentoActionPerformed


    private void jmiAlterarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAlterarSenhaActionPerformed
        new UIAlteraSenha().setVisible(true);
    }//GEN-LAST:event_jmiAlterarSenhaActionPerformed

    private void jmiAutomacaoPontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAutomacaoPontoActionPerformed
        UIAutomacaoPonto uiAutomacaoPonto = new UIAutomacaoPonto();
        jdpAreaTrabalho.add(uiAutomacaoPonto);
        try {
            uiAutomacaoPonto.setMaximum(true);
        } catch (PropertyVetoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Erro ao maximizar tela!",
                    JOptionPane.ERROR_MESSAGE);
        }
        uiAutomacaoPonto.setVisible(true);
    }//GEN-LAST:event_jmiAutomacaoPontoActionPerformed

    private void jmiControleRecessoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiControleRecessoActionPerformed
        new UIHistoricoFeriasPJ().setVisible(true);
    }//GEN-LAST:event_jmiControleRecessoActionPerformed

    private void jmiFerramentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiFerramentasActionPerformed
        UIControleMovimentos uiControleMovimentos = new UIControleMovimentos();
        jdpAreaTrabalho.add(uiControleMovimentos);
        try {
            uiControleMovimentos.setMaximum(true);
        } catch (PropertyVetoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Erro ao maximizar tela!",
                    JOptionPane.ERROR_MESSAGE);
        }
        uiControleMovimentos.setVisible(true);
    }//GEN-LAST:event_jmiFerramentasActionPerformed

    private void jmiCheckListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiCheckListActionPerformed
        new UIManCheckList().setVisible(true);
    }//GEN-LAST:event_jmiCheckListActionPerformed

    private void miProspeccaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miProspeccaoActionPerformed
        new FrmProspeccao(this).setVisible(true);
    }//GEN-LAST:event_miProspeccaoActionPerformed

    private void jmNotificacoesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jmNotificacoesMouseClicked
        new UINoficacao(this, true).setVisible(true);
    }//GEN-LAST:event_jmNotificacoesMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JDesktopPane jdpAreaTrabalho;
    private javax.swing.JLabel jlDataSincronizacao;
    private javax.swing.JLabel jlEmpresa;
    private javax.swing.JLabel jlUsuario;
    private javax.swing.JLabel jlVersao;
    private javax.swing.JMenu jmAjuda;
    private javax.swing.JMenu jmCadastro;
    private javax.swing.JMenu jmGestaoDePessoas;
    private javax.swing.JMenu jmModulo;
    public static javax.swing.JMenu jmNotificacoes;
    private javax.swing.JMenu jmSistema;
    private javax.swing.JMenuBar jmbMenu;
    private javax.swing.JMenuItem jmiAlterarSenha;
    private javax.swing.JMenuItem jmiApontamento;
    private javax.swing.JMenuItem jmiAutomacaoPonto;
    private javax.swing.JMenuItem jmiCadPessoa;
    private javax.swing.JMenuItem jmiCheckList;
    private javax.swing.JMenuItem jmiControleRecesso;
    private javax.swing.JMenuItem jmiEpi;
    private javax.swing.JMenuItem jmiFerramentas;
    private javax.swing.JMenuItem jmiOs;
    private javax.swing.JMenuItem jmiSair;
    private javax.swing.JMenuItem jmiSobre;
    private javax.swing.JPanel jpBarraInferior;
    public static javax.swing.JProgressBar jpbBarraProgresso;
    private javax.swing.JMenu mComercial;
    private javax.swing.JMenuItem miProspeccao;
    // End of variables declaration//GEN-END:variables

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent ife) {
        if (ife.getInternalFrame() instanceof UIControleEpi) {
            flagUIControleEpi = false;
        }
        if (ife.getInternalFrame() instanceof UIControleOS) {
            flagUIControleOS = false;
        }
        if (ife.getInternalFrame() instanceof UIApontamentos) {
            flagUIApontamentos = false;
        }
        if (ife.getInternalFrame() instanceof UIControleMovimentos) {
            flagUIControleProdutos = false;
        }
    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {
    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {
    }
}
