
package view;

import model.os.PessoaResponsavelTableModel;
import dao.DAOFactory;
import dao.os.PessoaDAO;
import dao.os.PessoaResponsavelDAO;
import dao.os.PessoaTipoDAO;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.os.PessoaResponsavel;
import model.os.Pessoa;
import model.os.PessoaTipo;
import model.os.PessoaTipoModel;
import utilitarios.Criptografia;
import view.os.UIPermissaoResponsavel;
import view.os.UIPermissaoTipo;

/**
 *
 * @author guilherme.oliveira
 */
public class UICadPessoa extends javax.swing.JDialog {

    UIManPessoas uiManPessoas;
    PessoaTipoModel pessoaTipoModel = new PessoaTipoModel();
    PessoaResponsavelTableModel pessoaResponsavelModel = new PessoaResponsavelTableModel();
    Pessoa pessoa;
    PessoaDAO pdao;
    PessoaTipoDAO ptdao;
    PessoaResponsavelDAO prdao;
    
    public UICadPessoa(Pessoa pessoa, UIManPessoas uiManPessoas) {
        this.pessoa = pessoa;
        this.uiManPessoas = uiManPessoas;
        pdao = DAOFactory.getPESSOADAO();
        prdao = DAOFactory.getPESSOARESPONSAVELDAO();
        ptdao = DAOFactory.getPESSOATIPODAO();
        initComponents();
        buscarResponsaveisPermitidos();
        buscarTiposPermitidos();
        preencherCampos();
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public UIManPessoas getUiManPessoas() {
        return uiManPessoas;
    }

    private void acessaPermissoes() {
        if (pessoa != null) {
            if (Menu.logado.getLogin().equals("adm")) {
                jTabbedPane1.setEnabledAt(1, true);
                jTabbedPane1.setEnabledAt(2, true);
                jTabbedPane1.setEnabledAt(3, true);
            } else {
                jTabbedPane1.setEnabledAt(1, false);
                jTabbedPane1.setEnabledAt(2, false);
                jTabbedPane1.setEnabledAt(3, false);
            }
        }
    }

    private void preencherCampos() {

        if (pessoa != null) {

            jtfCodPessoa.setText(String.valueOf(pessoa.getCodPessoa()));
            jtfNome.setText(pessoa.getNome());
            jtfEmail.setText(pessoa.getEmail());
            jtfLogin.setText(pessoa.getLogin());
            jchSolicitante.setSelected(pessoa.isSolicitante());
            jchResponsavel.setSelected(pessoa.isResponsavel());
            jchLider.setSelected(pessoa.isLider());
            jchLogin.setSelected(pessoa.isPermLogin());
            jchEpi.setSelected(pessoa.isPermEpi());
            jchAutenticar.setSelected(pessoa.isPermEpi());
            jchOs.setSelected(pessoa.isPermOS());
            jchTipo.setSelected(pessoa.isPermTipo());
            jchCadEPI.setSelected(pessoa.isPermCadEpi());
            jchStatus.setSelected(pessoa.isPermStatus());
            jchApontamentos.setSelected(pessoa.isPermApont());
            jchStatusApont.setSelected(pessoa.isPermStatusApont());
            jchPonto.setSelected(pessoa.isPermPonto());
            jchPessoas.setSelected(pessoa.isPermPessoas());
            jchComentarOs.setSelected(pessoa.isPermComentOs());
            jchComentarApont.setSelected(pessoa.isPermComentApont());
            jchVenda.setSelected(pessoa.isPermVenda());
            jchDescontado.setSelected(pessoa.isPermDescontado());
            jchAtivo.setSelected(pessoa.isAtivo());
            jchGestaoPessoas.setSelected(pessoa.isPermGestaoPessoas());
            jchControleDeRecesso.setSelected(pessoa.isPermControleRecesso());
            jchCustoMO.setSelected(pessoa.isPermCustoMO());
            jchFerramentas.setSelected(pessoa.isPermFerramentas());
            jchCheckList.setSelected(pessoa.isPermCadCheckList());
            chProspeccao.setSelected(pessoa.isPermProspeccao());
            cbxApontamentoPonto.setSelected(pessoa.isPermApontamentoPonto());
            cbxApontamentoPontoJustificativa.setSelected(pessoa.isPermApontamentoPontoJustificativa());
        }
    }

    private void setPessoa() throws NumberFormatException {
        if (jtfNome.getText().isEmpty()) {
            throw new NumberFormatException("Insira o nome!");
        }

        pessoa.setNome(jtfNome.getText().toUpperCase());
        if (!jtfEmail.getText().isEmpty()) {
            pessoa.setEmail(jtfEmail.getText());
        }
        pessoa.setSolicitante(jchSolicitante.isSelected());
        pessoa.setResponsavel(jchResponsavel.isSelected());
        pessoa.setLider(jchLider.isSelected());
        pessoa.setPermLogin(jchLogin.isSelected());
        pessoa.setPermEpi(jchEpi.isSelected());
        pessoa.setPermAutenticacao(jchAutenticar.isSelected());
        pessoa.setPermOS(jchOs.isSelected());
        pessoa.setPermTipo(jchTipo.isSelected());
        pessoa.setPermStatus(jchStatus.isSelected());
        pessoa.setPermApont(jchApontamentos.isSelected());
        pessoa.setPermStatusApont(jchStatusApont.isSelected());
        pessoa.setPermComentOs(jchComentarOs.isSelected());
        pessoa.setPermComentApont(jchComentarApont.isSelected());
        pessoa.setPermPonto(jchPonto.isSelected());
        pessoa.setPermPessoas(jchPessoas.isSelected());
        pessoa.setPermVenda(jchVenda.isSelected());
        pessoa.setPermDescontado(jchDescontado.isSelected());
        pessoa.setPermCadEpi(jchCadEPI.isSelected());
        pessoa.setAtivo(jchAtivo.isSelected());
        pessoa.setPermGestaoPessoas(jchGestaoPessoas.isSelected());
        pessoa.setPermControleRecesso(jchControleDeRecesso.isSelected());
        pessoa.setPermCustoMO(jchCustoMO.isSelected());
        pessoa.setPermFerramentas(jchFerramentas.isSelected());
        pessoa.setPermCadCheckList(jchCheckList.isSelected());
        pessoa.setPermProspeccao(chProspeccao.isSelected());
        pessoa.setPermApontamentoPonto(cbxApontamentoPonto.isSelected());
        pessoa.setPermApontamentoPontoJustificativa(cbxApontamentoPontoJustificativa.isSelected());

        if (jchLogin.isSelected()) {
            if (jtfLogin.getText().isEmpty()) {
                throw new NumberFormatException("Insira o email!");
            } else {
                pessoa.setLogin(jtfLogin.getText());
            }
        }
    }

    private void cadastrar() {
        try {
            int id;

            pessoa = new Pessoa();

            setPessoa();

            if (jchLogin.isSelected()) {
                if (String.valueOf(jpfSenha.getPassword()).isEmpty()) {
                    throw new NumberFormatException("Insira a senha!");
                } else {
                    pessoa.setSenha(Criptografia
                            .criptografar(String
                                    .valueOf(jpfSenha.getPassword())));
                }
            }

            id = pdao.inserir(pessoa);

            pessoa.setCodPessoa(id);
            if (Menu.logado.getLogin().equals("adm")) {
                jTabbedPane1.setEnabledAt(2, true);
                jTabbedPane1.setEnabledAt(3, true);
            }

            uiManPessoas.getpTableModel().addRow(pessoa);
            acessaPermissoes();
            JOptionPane.showMessageDialog(this,
                    "Pessoa cadastrada!",
                    "",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            pessoa = null;
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Cadastrar pessoa!",
                    JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            pessoa = null;
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Cadastrar pessoa!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editar() {
        try {

            setPessoa();
            
            if (!String.valueOf(jpfSenha.getPassword()).isEmpty()) {
                pessoa.setSenha(Criptografia
                            .criptografar(String
                                    .valueOf(jpfSenha.getPassword())));
            }

            pdao.alterar(pessoa);

            uiManPessoas.pesquisar();
            JOptionPane.showMessageDialog(this,
                    "Pessoa editada!",
                    "",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Editar pessoa",
                    JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Editar pessoa",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvar() {
        if (pessoa == null) {
            cadastrar();
        } else {
            editar();
        }
    }

    public void buscarTiposPermitidos() {
        try {
            if (pessoa != null) {

                ArrayList<PessoaTipo> pessoasTipos = ptdao.buscarTiposPorPessoa(pessoa);

                for (PessoaTipo pessoaTipo : pessoasTipos) {
                    pessoaTipoModel.addRow(pessoaTipo);
                }

                jtTiposPermitidos.setModel(pessoaTipoModel);

            } else {
                jTabbedPane1.setEnabledAt(2, false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro ao buscar tipos permitidos", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarResponsaveisPermitidos() {
        try {

            if (pessoa != null) {

                ArrayList<PessoaResponsavel> pessoasResponsaveis = prdao.buscar(pessoa);

                for (PessoaResponsavel pessoaResponsavel : pessoasResponsaveis) {
                    pessoaResponsavelModel.addRow(pessoaResponsavel);
                }

                jtPessoasPermitidas.setModel(pessoaResponsavelModel);
            } else {
                jTabbedPane1.setEnabledAt(3, false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Erro ao buscar responsáveis permitidos",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void atualizarTblPessoasTipos() {
        pessoaTipoModel.clear();
        buscarTiposPermitidos();
    }

    public void atualizarTblResponsaveis() {
        pessoaResponsavelModel.clear();
        buscarResponsaveisPermitidos();
    }

    private PessoaTipo getPessoaTipoSelecionado() {
        if (jtTiposPermitidos.getSelectedRow() == -1) {
            return null;
        }

        return pessoaTipoModel.getPessoaTipo().get(jtTiposPermitidos.getSelectedRow());
    }

    private PessoaResponsavel getPessoaResponsavelSelecionado() {
        if (jtPessoasPermitidas.getSelectedRow() == -1) {
            return null;
        }

        return pessoaResponsavelModel.getPessoasResponsaveis().get(jtPessoasPermitidas.getSelectedRow());
    }

    private void excluirPessoaTipo(PessoaTipo pessoaTipo) {
        try {
            ptdao.excluir(pessoaTipo);
            pessoaTipoModel.removeRow(pessoaTipo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro ao remover tipo permitido",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirPessoaResponsavel(PessoaResponsavel pessoaResponsavel) {
        try {
            prdao.excluir(pessoaResponsavel);
            pessoaResponsavelModel.removeRow(pessoaResponsavel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Erro ao remover responsável permitido",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jchResponsavel = new javax.swing.JCheckBox();
        jchLider = new javax.swing.JCheckBox();
        jlTipoPessoa = new javax.swing.JLabel();
        jtfCodPessoa = new javax.swing.JTextField();
        jtfNome = new javax.swing.JTextField();
        jlCodPessoa = new javax.swing.JLabel();
        jchSolicitante = new javax.swing.JCheckBox();
        jlNome = new javax.swing.JLabel();
        jlEmail = new javax.swing.JLabel();
        jtfEmail = new javax.swing.JTextField();
        jchAtivo = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jchEpi = new javax.swing.JCheckBox();
        jchAutenticar = new javax.swing.JCheckBox();
        jchOs = new javax.swing.JCheckBox();
        jchTipo = new javax.swing.JCheckBox();
        jchStatus = new javax.swing.JCheckBox();
        jchPessoas = new javax.swing.JCheckBox();
        jchStatusApont = new javax.swing.JCheckBox();
        jchApontamentos = new javax.swing.JCheckBox();
        jchComentarOs = new javax.swing.JCheckBox();
        jchComentarApont = new javax.swing.JCheckBox();
        jtfLogin = new javax.swing.JTextField();
        jlSenha = new javax.swing.JLabel();
        jpfSenha = new javax.swing.JPasswordField();
        jlLogin = new javax.swing.JLabel();
        jchLogin = new javax.swing.JCheckBox();
        jchPonto = new javax.swing.JCheckBox();
        jchVenda = new javax.swing.JCheckBox();
        jchDescontado = new javax.swing.JCheckBox();
        jchCadEPI = new javax.swing.JCheckBox();
        jchGestaoPessoas = new javax.swing.JCheckBox();
        jchControleDeRecesso = new javax.swing.JCheckBox();
        jchCustoMO = new javax.swing.JCheckBox();
        jchFerramentas = new javax.swing.JCheckBox();
        jchCheckList = new javax.swing.JCheckBox();
        chProspeccao = new javax.swing.JCheckBox();
        cbxApontamentoPonto = new javax.swing.JCheckBox();
        cbxApontamentoPontoJustificativa = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtTiposPermitidos = new javax.swing.JTable();
        jbRemover = new javax.swing.JButton();
        jbAdicionar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtPessoasPermitidas = new javax.swing.JTable();
        jbRemover1 = new javax.swing.JButton();
        jbAdicionar1 = new javax.swing.JButton();
        jbSalvar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Salvar pessoa");
        setModal(true);
        setResizable(false);

        jchResponsavel.setText("Responsável");

        jchLider.setText("Líder");

        jlTipoPessoa.setText("Tipo de pessoa:");

        jtfCodPessoa.setEditable(false);

        jtfNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfNomeKeyPressed(evt);
            }
        });

        jlCodPessoa.setText("Código");

        jchSolicitante.setText("Solicitante");

        jlNome.setText("Nome");
        jlNome.setToolTipText("Cadastrar Pessoa");

        jlEmail.setText("Email");

        jchAtivo.setSelected(true);
        jchAtivo.setText("Ativo");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfEmail)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jtfCodPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfNome))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jchAtivo)
                            .addComponent(jchLider)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jlCodPessoa)
                                .addGap(35, 35, 35)
                                .addComponent(jlNome))
                            .addComponent(jlTipoPessoa)
                            .addComponent(jchSolicitante)
                            .addComponent(jlEmail)
                            .addComponent(jchResponsavel))
                        .addGap(0, 449, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlCodPessoa)
                    .addComponent(jlNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfCodPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jchAtivo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlTipoPessoa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jchSolicitante)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jchResponsavel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jchLider)
                .addContainerGap(214, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dado Pessoais", jPanel1);

        jchEpi.setText("EPI");

        jchAutenticar.setText("Autenticar");

        jchOs.setText("OS/Tarefa");

        jchTipo.setText("Cadastro de Tipo");

        jchStatus.setText("Cadastro de Status");

        jchPessoas.setText("Pessoas");

        jchStatusApont.setText("Status dos Apontamentos");

        jchApontamentos.setText("Apontamentos");

        jchComentarOs.setText("Comentar OS");

        jchComentarApont.setText("Comentar Apontamento");

        jtfLogin.setEnabled(false);

        jlSenha.setText("Senha");

        jpfSenha.setEnabled(false);
        jpfSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jpfSenhaKeyPressed(evt);
            }
        });

        jlLogin.setText("Login");

        jchLogin.setText("Login");
        jchLogin.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jchLoginStateChanged(evt);
            }
        });

        jchPonto.setText("Automacao Ponto");

        jchVenda.setText(" Venda");

        jchDescontado.setText("Ver descontados");

        jchCadEPI.setText("Cadastro de EPI");

        jchGestaoPessoas.setText("Gestão de pessoas");

        jchControleDeRecesso.setText("Controle de Recesso");

        jchCustoMO.setText("Custo de Mao de Obra");

        jchFerramentas.setText("Ferramentas");

        jchCheckList.setText("Checklist");

        chProspeccao.setText("Prospecção");

        cbxApontamentoPonto.setText("Ponto");

        cbxApontamentoPontoJustificativa.setText("Ponto Justificativa");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlLogin)
                    .addComponent(jtfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jchEpi)
                    .addComponent(jchOs)
                    .addComponent(jchLogin)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jchAutenticar)
                            .addComponent(jchDescontado)
                            .addComponent(jchCadEPI)
                            .addComponent(jchStatus)
                            .addComponent(jchComentarOs)
                            .addComponent(jchVenda)
                            .addComponent(jchCustoMO)
                            .addComponent(jchTipo))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlSenha)
                    .addComponent(jpfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jchApontamentos)
                    .addComponent(jchPonto)
                    .addComponent(jchPessoas)
                    .addComponent(jchGestaoPessoas)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jchComentarApont)
                            .addComponent(jchStatusApont)
                            .addComponent(cbxApontamentoPonto)
                            .addComponent(cbxApontamentoPontoJustificativa)))
                    .addComponent(jchControleDeRecesso)
                    .addComponent(jchFerramentas)
                    .addComponent(jchCheckList)
                    .addComponent(chProspeccao))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jchLogin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlLogin)
                    .addComponent(jlSenha))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpfSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jchEpi)
                    .addComponent(jchApontamentos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jchAutenticar)
                    .addComponent(jchStatusApont))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jchDescontado)
                    .addComponent(jchComentarApont))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxApontamentoPonto)
                    .addComponent(jchCadEPI))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jchOs)
                    .addComponent(cbxApontamentoPontoJustificativa))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jchTipo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jchStatus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jchComentarOs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jchVenda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jchCustoMO))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jchPonto)
                        .addGap(8, 8, 8)
                        .addComponent(jchPessoas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jchGestaoPessoas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jchControleDeRecesso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jchFerramentas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jchCheckList)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chProspeccao)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Permissões", jPanel2);

        jtTiposPermitidos.setModel(new PessoaTipoModel());
        jScrollPane1.setViewportView(jtTiposPermitidos);

        jbRemover.setText("Remover");
        jbRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoverActionPerformed(evt);
            }
        });

        jbAdicionar.setText("Adicionar");
        jbAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbAdicionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRemover)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbRemover)
                    .addComponent(jbAdicionar))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Permissões por tipo", jPanel3);

        jtPessoasPermitidas.setModel(new PessoaResponsavelTableModel());
        jScrollPane2.setViewportView(jtPessoasPermitidas);

        jbRemover1.setText("Remover");
        jbRemover1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemover1ActionPerformed(evt);
            }
        });

        jbAdicionar1.setText("Adicionar");
        jbAdicionar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAdicionar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 386, Short.MAX_VALUE)
                        .addComponent(jbAdicionar1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbRemover1)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbRemover1)
                    .addComponent(jbAdicionar1))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Permissões por responsável", jPanel4);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jbSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCancelar))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbCancelar)
                    .addComponent(jbSalvar))
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

    private void jbAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionarActionPerformed
        new UIPermissaoTipo(pessoa, this).setVisible(true);
    }//GEN-LAST:event_jbAdicionarActionPerformed

    private void jbRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoverActionPerformed
        PessoaTipo pessoaTipo = getPessoaTipoSelecionado();
        if (pessoaTipo == null) {
            JOptionPane.showMessageDialog(this, "Selecione o que deseja remover!", "", JOptionPane.WARNING_MESSAGE);
        } else {
            Object[] options = {"Sim", "Não"};
            int i = JOptionPane.showOptionDialog(null,
                    "Tem certeza que deseja remover?", "Remover",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (i == JOptionPane.YES_OPTION) {
                excluirPessoaTipo(pessoaTipo);
            }
        }
    }//GEN-LAST:event_jbRemoverActionPerformed

    private void jchLoginStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jchLoginStateChanged
        jtfLogin.setEnabled(jchLogin.isSelected());
        jpfSenha.setEnabled(jchLogin.isSelected());
    }//GEN-LAST:event_jchLoginStateChanged

    private void jpfSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpfSenhaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            salvar();
        }
    }//GEN-LAST:event_jpfSenhaKeyPressed

    private void jtfNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNomeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            salvar();
        }
    }//GEN-LAST:event_jtfNomeKeyPressed

    private void jbAdicionar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAdicionar1ActionPerformed
        new UIPermissaoResponsavel(pessoa, this).setVisible(true);
    }//GEN-LAST:event_jbAdicionar1ActionPerformed

    private void jbRemover1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemover1ActionPerformed
        PessoaResponsavel pessoaResponsavel = getPessoaResponsavelSelecionado();
        if (pessoaResponsavel == null) {
            JOptionPane.showMessageDialog(this, "Selecione o responsável que deseja remover!", "", JOptionPane.WARNING_MESSAGE);
        } else {
            Object[] options = {"Sim", "Não"};
            int i = JOptionPane.showOptionDialog(null,
                    "Tem certeza que deseja remover?", "Remover",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (i == JOptionPane.YES_OPTION) {
                excluirPessoaResponsavel(pessoaResponsavel);
            }
        }
    }//GEN-LAST:event_jbRemover1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox cbxApontamentoPonto;
    private javax.swing.JCheckBox cbxApontamentoPontoJustificativa;
    private javax.swing.JCheckBox chProspeccao;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbAdicionar;
    private javax.swing.JButton jbAdicionar1;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JButton jbRemover;
    private javax.swing.JButton jbRemover1;
    private javax.swing.JButton jbSalvar;
    private javax.swing.JCheckBox jchApontamentos;
    private javax.swing.JCheckBox jchAtivo;
    private javax.swing.JCheckBox jchAutenticar;
    private javax.swing.JCheckBox jchCadEPI;
    private javax.swing.JCheckBox jchCheckList;
    private javax.swing.JCheckBox jchComentarApont;
    private javax.swing.JCheckBox jchComentarOs;
    private javax.swing.JCheckBox jchControleDeRecesso;
    private javax.swing.JCheckBox jchCustoMO;
    private javax.swing.JCheckBox jchDescontado;
    private javax.swing.JCheckBox jchEpi;
    private javax.swing.JCheckBox jchFerramentas;
    private javax.swing.JCheckBox jchGestaoPessoas;
    private javax.swing.JCheckBox jchLider;
    private javax.swing.JCheckBox jchLogin;
    private javax.swing.JCheckBox jchOs;
    private javax.swing.JCheckBox jchPessoas;
    private javax.swing.JCheckBox jchPonto;
    private javax.swing.JCheckBox jchResponsavel;
    private javax.swing.JCheckBox jchSolicitante;
    private javax.swing.JCheckBox jchStatus;
    private javax.swing.JCheckBox jchStatusApont;
    private javax.swing.JCheckBox jchTipo;
    private javax.swing.JCheckBox jchVenda;
    private javax.swing.JLabel jlCodPessoa;
    private javax.swing.JLabel jlEmail;
    private javax.swing.JLabel jlLogin;
    private javax.swing.JLabel jlNome;
    private javax.swing.JLabel jlSenha;
    private javax.swing.JLabel jlTipoPessoa;
    private javax.swing.JPasswordField jpfSenha;
    private javax.swing.JTable jtPessoasPermitidas;
    private javax.swing.JTable jtTiposPermitidos;
    private javax.swing.JTextField jtfCodPessoa;
    private javax.swing.JTextField jtfEmail;
    private javax.swing.JTextField jtfLogin;
    private javax.swing.JTextField jtfNome;
    // End of variables declaration//GEN-END:variables
}
