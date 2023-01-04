package services;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class EmailService {

    HtmlEmail email;

    public EmailService() {
        this.email = new HtmlEmail();
        configurar();
    }
    
    private void configurar() {
        try {
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("ti.dse01@gmail.com", "zhhveekbsqgpovos"));
            email.setSSLOnConnect(true);
            email.setFrom("ti@dse.com.br");
        } catch (EmailException ex) {
            Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public void enviarEmail(String assunto, String destinatario, String mensagem) throws EmailException {

        email.setSubject(assunto);
        email.setHtmlMsg(mensagem);
        email.addTo(destinatario);
        email.send();
    }
}
