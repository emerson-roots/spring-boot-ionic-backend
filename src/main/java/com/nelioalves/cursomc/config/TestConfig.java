package com.nelioalves.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.nelioalves.cursomc.services.DBService;
import com.nelioalves.cursomc.services.EmailService;
import com.nelioalves.cursomc.services.MockMailService;

//auça 56
@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;

	@Bean //metodo retorna true apenas pq o metodo nao pode ser void
	public boolean instantiateDatabase() throws ParseException {

		dbService.instantiateTestDatabase();
		return true;
	}
	
	//aula 63
	@Bean
	public EmailService emailService() {
		return new MockMailService();
	}

}
