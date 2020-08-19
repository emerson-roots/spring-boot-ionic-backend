package com.nelioalves.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

//aula 64
public class SmtpEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

	// automaticamente o framework instancia um objeto com todos os dados
	// do e-mail inserido no application.properties
	@Autowired
	private MailSender mailSender;

	// capaz de enviar uma MimeMessage - email com html
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendEmail(SimpleMailMessage msg) {

		LOG.info("Enviando de email...");
		mailSender.send(msg);
		LOG.info("Email enviado");

	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {

		LOG.info("Enviando de email...");
		javaMailSender.send(msg);
		LOG.info("Email enviado");

	}

}
