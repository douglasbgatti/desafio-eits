package br.com.eits.desafio.chat.domain.service.user;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;


@Service("mailSenderService")
public class MailSender{


	public void sendMail(String subject, String message, String emailAddress) {
		try {
			SimpleEmail email = new SimpleEmail();

			try {
				email.setHostName("smtp.googlemail.com");
				email.setSmtpPort(465);
				email.setAuthenticator(new DefaultAuthenticator("desafio.chat.suporte@gmail.com", "desafio123"));
				email.setSSLOnConnect(true);
				email.setFrom("desafio.chat.suporte@gmail.com");
				email.setSubject(subject);
				email.setMsg(message);
				email.addTo(emailAddress);
				email.send();
			} catch (EmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
