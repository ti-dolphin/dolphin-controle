/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services.epi;

import services.EmailService;
import dao.epi.EpiFuncionarioDAO;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.Funcionario;
import model.epi.EpiFuncionario;
import org.apache.commons.mail.EmailException;

/**
 *
 * @author ti
 */
public class EmailEpiFuncionarioService extends EmailService {

    private final EpiFuncionarioDAO epiFuncionarioDAO;

    public EmailEpiFuncionarioService() {
        this.epiFuncionarioDAO = new EpiFuncionarioDAO();
    }

    public void enviarComprovanteDeEntregaDeEpiPorEmail(ArrayList<EpiFuncionario> episFuncionario) throws EmailException, SQLException {
        if (!episFuncionario.isEmpty()) {
            super.enviarEmail("Comprovante de recebimento e devolução de EPI",
                    episFuncionario.get(0).getFuncionario().getEmail(), 
                    construirMensagemDoEmail(episFuncionario));
        }
    }

    private String construirMensagemDoEmail(ArrayList<EpiFuncionario> episFuncionario) throws SQLException {

        String corpoTabela = "";
        for (EpiFuncionario epiFuncionario : episFuncionario) {

            String dataRetirada = "";
            String dataDevolucao = "";
            if (epiFuncionario.getDataRetirada() != null) {
                dataRetirada = epiFuncionario.getDataRetirada().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            }
            if (epiFuncionario.getDataDevolucao() != null) {
                dataDevolucao = epiFuncionario.getDataDevolucao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            }

            corpoTabela += "<tr>\n"
                    + "  <td>" + epiFuncionario.getFuncionario().getChapa() + "</td>\n"
                    + "  <td>" + epiFuncionario.getFuncionario().getNome() + "</td>\n"
                    + "  <td>" + epiFuncionario.getEpi().getNome() + "</td>\n"
                    + "  <td>" + dataRetirada + "</td>\n"
                    + "  <td>" + dataDevolucao + "</td>\n"
                    + "  <td>" + epiFuncionario.getCa() + "</td>\n"
                    + "</tr>\n";
        }

        String corpoMensagem = "<html>\n"
                            + "    <head>\n"
                            + "        <title>Comprovante de recebimento e devolução de EPI</title>\n"
                            + "        <meta charset=\"UTF-8\">\n"
                            + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                            + "        <style type=\"text/css\">\n"
                            + "            th, td {\n"
                            + "                padding: 15px;\n"
                            + "                text-align: left;\n"
                            + "            }\n"
                            + "            tr:nth-child(even) {\n"
                            + "                background-color: #f2f2f2\n"
                            + "            }\n"
                            + "        </style>\n"
                            + "    </head>\n"
                            + "    <body>\n"
                            + "            <table>\n"
                            + "                <tr>\n"
                            + "                    <th>Chapa</th>\n"
                            + "                    <th>Colaborador</th>\n"
                            + "                    <th>EPI</th>\n"
                            + "                    <th>Data de retirada</th>\n"
                            + "                    <th>Data de devolução</th>\n"
                            + "                    <th>CA</th>\n"
                            + "                </tr>"
                            + corpoTabela
                            + "            </table>\n"
                            + "    </body>\n"
                            + "</html>";

        return corpoMensagem;
    }
}
