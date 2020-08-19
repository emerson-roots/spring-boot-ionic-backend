package com.nelioalves.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.nelioalves.cursomc.domain.Pedido;

//aula 63
public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	//responsavel por enviar e-mail em formato de texto plano
	void sendEmail(SimpleMailMessage msg);
	
	//aula 66
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	//responsavel por enviar e-mail em formato html
	void sendHtmlEmail(MimeMessage msg);
	
}
