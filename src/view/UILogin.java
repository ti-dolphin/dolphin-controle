/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DAOFactory;
import dao.SistemaDAO;
import dao.os.PessoaDAO;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.os.Pessoa;
import utilitarios.Criptografia;

/**
 *
 * @author guilherme.oliveira
 */
public class UILogin extends javax.swing.JFrame {

    private Pessoa pessoa;
    
    private static final String CAMINHO = System.getProperty("user.home") + File.separator + "dsecontrole.txt";

    /**
     * Creates new form UILogin
     */
    public UILogin() {
        initComponents();
        verificarVersao();
        carregarCampoLogin();
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    /**
     * Método usado para validar login
     *
     * @param login
     * @param senha
     * @return false/true
     */
    private boolean checkLogin(String login, String senha) {
        try {
            PessoaDAO dao = DAOFactory.getPESSOADAO();
            ArrayList<Pessoa> pessoas;
            pessoas = dao.buscarAtivos();

            String chave = Criptografia.criptografar(senha);//criptografa senha

            for (int i = 0; i < pessoas.size(); i++) {
                pessoa = pessoas.get(i);
                if (login.equals(pessoa.getLogin()) && chave.equals(pessoa.getSenha())) {
                    return true;
                }
            }
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao buscar pessoa", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }//checkLogin

    /**
     * Método usado para verificar a versão usada no sistema. Compara versão do
     * sistema com versão do banco de dados
     *
     * @param versao
     * @return
     */
    private void verificarVersao() {
        try {
            SistemaDAO dao = DAOFactory.getSISTEMADAO();

            String ultimaVersao = dao.buscarVersao();
            System.out.println(ultimaVersao);
            if (Principal.VERSAO != null && !Principal.VERSAO.equals(ultimaVersao)) {
                Object[] options = {"Sim", "Não"};
                int i = JOptionPane.showOptionDialog(null,
                        "Você precisa atualizar sua versão! Deseja atualizar agora?",
                        "Versão desatualizada", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (i == JOptionPane.YES_OPTION) {
                    abrirNavegador();
                    System.exit(0);
                } else {
                    System.exit(0);
                }
            }

        } catch (SQLException se) {
            JOptionPane.showMessageDialog(this, se.getMessage(),
                    "Erro ao buscar versão", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public void abrirNavegador() {
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("https://drive.google.com/drive/folders/1vrOZhZptOnSV3AUgWX_TsV4CxS9JSjI1?usp=sharing"));
        } catch (IOException | URISyntaxException e) {
            System.err.println(e);
        }
    }

    private void carregarCampoLogin() {

        try {

            File file = new File(CAMINHO);

            if (file.exists()) {

                jchLembrarLogin.setSelected(true);
                jpfSenha.grabFocus();

                BufferedReader br = new BufferedReader(new FileReader(file));

                while (br.ready()) {
                    String linha = br.readLine().trim();
                    jtfLogin.setText(linha);
                }
                br.close();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void criarArquivo() throws IOException {

        FileWriter arq = new FileWriter(CAMINHO);
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.printf(pessoa.getLogin());
        arq.close();
    }

    private void entrar() {
        if (checkLogin(jtfLogin.getText(), String.valueOf(jpfSenha.getPassword()))) {

            if (jchLembrarLogin.isSelected()) {
                try {
                    criarArquivo();
                } catch (IOException ex) {
                    Logger.getLogger(UILogin.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                File file = new File(CAMINHO); 
                file.delete();
            }

            new Menu(this).setVisible(true);
            this.dispose();
        } else {
            jpfSenha.setText("");
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválido!",
                    "Erro ao fazer login", JOptionPane.ERROR_MESSAGE);
        }
    }//entrar

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jlImagemDolphin = new javax.swing.JLabel();
        jtfLogin = new javax.swing.JTextField();
        jlLogin = new javax.swing.JLabel();
        jlSenha = new javax.swing.JLabel();
        jpfSenha = new javax.swing.JPasswordField();
        jbEntrar = new javax.swing.JButton();
        jchLembrarLogin = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setBackground(new java.awt.Color(255, 255, 255));
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/img/dse-icone.png")).getImage());
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jlImagemDolphin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dolphin.png"))); // NOI18N

        jlLogin.setText("Login");

        jlSenha.setText("Senha");

        jpfSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jpfSenhaKeyPressed(evt);
            }
        });

        jbEntrar.setText("Entrar");
        jbEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEntrarActionPerformed(evt);
            }
        });
        jbEntrar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbEntrarKeyPressed(evt);
            }
        });

        jchLembrarLogin.setText("Lembrar login");
        jchLembrarLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jchLembrarLoginKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jchLembrarLogin)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jlSenha)
                        .addComponent(jpfSenha)
                        .addComponent(jlLogin)
                        .addComponent(jtfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(jlImagemDolphin))))
                .addContainerGap(160, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbEntrar)
                .addGap(227, 227, 227))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jlImagemDolphin)
                .addGap(39, 39, 39)
                .addComponent(jlLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlSenha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jchLembrarLogin)
                .addGap(18, 18, 18)
                .addComponent(jbEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEntrarActionPerformed
        entrar();
    }//GEN-LAST:event_jbEntrarActionPerformed

    private void jpfSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpfSenhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            entrar();
        }
    }//GEN-LAST:event_jpfSenhaKeyPressed

    private void jbEntrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbEntrarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            entrar();
        }
    }//GEN-LAST:event_jbEntrarKeyPressed

    private void jchLembrarLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jchLembrarLoginKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            entrar();
        }
    }//GEN-LAST:event_jchLembrarLoginKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbEntrar;
    private javax.swing.JCheckBox jchLembrarLogin;
    private javax.swing.JLabel jlImagemDolphin;
    private javax.swing.JLabel jlLogin;
    private javax.swing.JLabel jlSenha;
    private javax.swing.JPasswordField jpfSenha;
    private javax.swing.JTextField jtfLogin;
    // End of variables declaration//GEN-END:variables
}
