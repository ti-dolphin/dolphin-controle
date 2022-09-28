package utilitarios;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author guilherme.oliveira
 */
public class EmailJava {

    HtmlEmail email;

    public EmailJava() {
        email = new HtmlEmail ();
        configurar();
    }

    public void configurar() {
        email.setHostName("email-ssl.com.br");
        email.setSmtpPort(465);
        email.setDebug(true);
        email.setAuthentication("epi@dse.com.br", "Ed@Dse1707");
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
    
    public static boolean validar(String email) {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }
}
