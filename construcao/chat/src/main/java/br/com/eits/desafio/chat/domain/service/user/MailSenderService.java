//package br.com.eits.desafio.chat.domain.service.user;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.MailSender;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import br.com.eits.desafio.chat.domain.entity.user.User;
//
//@Service
//public class MailSenderService {
//	
//	@Autowired
//	private JavaMailSenderImpl mailSender;
//	
//	@Autowired
//	private SimpleMailMessage alertMailMessage;
//	
//	public void sendMail(User user){
//		SimpleMailMessage message = new SimpleMailMessage();
//		
//		StringBuilder body = new StringBuilder();
//		body.append("Hello " + user.getName() + "!\n\n");
//		body.append("Your account has been created at Desafio-Chat.\n");
//		body.append("Login with your E-mail and start chatting now!");
//		
//		message.setFrom("desafio.chat.suporte@gmail.com");
//		message.setTo(user.getEmail());
//		message.setSubject("Your New Desafio-Chat Account!");
//		message.setText(body.toString());
//		
//		mailSender.send(message);
//	}
//	
//    public void sendAlertMail(String alert) {
//        
//        SimpleMailMessage mailMessage = new SimpleMailMessage(alertMailMessage);
//        mailMessage.setText(alert);
//        mailSender.send(mailMessage);
//        
//    }
//
//}
