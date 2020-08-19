package com.nelioalves.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

//aula 64
public class SmtpEmailService extends AbstractEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	
	
	//automaticamente o framework instancia um objeto com todos os dados
	//do e-mail inserido no application.properties
	@Autowired
	private MailSender mailSender;
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		
		LOG.info("Enviando de email...");
		mailSender.send(msg);
		LOG.info("Email enviado");
		
		
	}

}
