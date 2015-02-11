/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;


import Entity.Person;
import java.io.IOException;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;


/**
 *
 * @author thiagovulcao
 */
public class EmailService {

    private static final String ACCOUNT = "eventos@ebi.com.br";
    private static final String AUTH = "eventos=ebi.com.br";
    private static final String SENDER = "Isncrição - Computação Amostra";
    private static final String PASS = "ryRYkLCXuJ1Nbs";
    private static final String SMTPSERVER = "smtp.ebi.com.br";
    private static final int PORT = 587;

    public static void sendEmail(String subject, Person person,
        String addresseeEmail, String emailBody) throws EmailException, IOException {
        HtmlEmail email;
        emailBody="";
        if (subject != null) {

            email = new HtmlEmail();
            email.setSubject(subject);
            if (addresseeEmail == null || addresseeEmail.equals("")) {
                email.addTo("tracker@ebi.com.br", "INSCRIÇÃO");
            } else {
                email.addTo(addresseeEmail, person.getIndividual().getName());
                email.addBcc("tracker@ebi.com.br", person.getIndividual().getName());
            }
            email.setAuthentication(AUTH, PASS);
            email.setFrom(ACCOUNT, SENDER);
            email.setHostName(SMTPSERVER);
            email.setSmtpPort(PORT);
            email.setTLS(false);
            email.setSSL(false);
            emailBody += EmailBody.getConfirmationFomenta(person);
            email.setHtmlMsg(emailBody);
            try {
                email.send();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
