package com.nelioalves.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Pedido;

//aula 63
public abstract class AbstractEmailService implements EmailService {

	// aula 66
	@Autowired
	private TemplateEngine templateEngine;

	// aula 66 - instancia MimeMessage
	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${default.sender}")
	private String sender;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);

	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		// pega valor da chave descrita no arquivo application.properties
		sm.setFrom(sender);
		sm.setSubject("Pedido Confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}

	// aula 66
	protected String htmlFromTemplatePedido(Pedido obj) {

		Context context = new Context();

		// apelido "pedido" tem ligação com o template HTML
		// onde povoa o objeto context para ser processado e enviado para o template
		context.setVariable("pedido", obj);

		// processa o objeto no context envia para o template/pagina html
		// e retorna o objeto HTML em forma de string
		return templateEngine.process("email/confirmacaoPedido", context);
	}

	// aula 66 - envia e-mail de confirmação em HTML
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {

		// o bloco try tenta enviar um e-mail HTML
		// caso ocorra exceção, envia um
		// e-mail comum em texto plano
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
		} catch (MessagingException e) {
			sendOrderConfirmationEmail(obj);

		}

	}

	// aula 66
	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		// atribui valores a mensagem com MimeMessageHelper
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);

		// seta o e-mail do destinatario
		mmh.setTo(obj.getCliente().getEmail());

		// seta o remetente
		mmh.setFrom(sender);

		// seta assunto do email
		mmh.setSubject("Pedido confirmado! Código: " + obj.getId());

		// seta instante do e-mail
		mmh.setSentDate(new Date(System.currentTimeMillis()));

		// seta corpo do email que sera o email HTML processado
		// a partir do metodo que criamos
		// o booleano indica que o conteudo é um HTML
		mmh.setText(htmlFromTemplatePedido(obj), true);

		return mimeMessage;
	}

	// aula 78
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
		sendEmail(sm);
	}

	// aula 78
	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {

		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(cliente.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;

	}

}
