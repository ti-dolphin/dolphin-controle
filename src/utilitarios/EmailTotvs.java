/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author guilherme.oliveira
 */
public class EmailTotvs {
    HtmlEmail email;

    public EmailTotvs() {
        email = new HtmlEmail ();
        configurar();
    }

    public void configurar() {
        email.setHostName("email-ssl.com.br");
        email.setSmtpPort(465);
        email.setDebug(true);
        email.setAuthentication("totvs@dse.com.br", "Td@Dse1707");
        email.setSSLOnConnect(true);
    }

    public void enviarEmail(String from, String subject, String msg, String to ) throws EmailException {
        try {
            email.setFrom(from);
            email.setSubject(subject);
            email.setHtmlMsg(msg);
            email.addTo(to);
            email.addReplyTo(from);
            email.send();
            System.out.println("Email sent successfully!!!");
        } catch (EmailException e) {
            throw new EmailException("Erro ao enviar email");
        }
    }
}
