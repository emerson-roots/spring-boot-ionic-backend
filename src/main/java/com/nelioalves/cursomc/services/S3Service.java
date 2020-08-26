package com.nelioalves.cursomc.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.nelioalves.cursomc.services.exceptions.FileExceptionEmerson;

//aula 84
@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	private String bucketName;

	// metodo responsável por fazer o upload de um arquivo passado no diretorio
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			// armazena o nome do arquivo
			String fileName = multipartFile.getOriginalFilename();

			// InputStream - encapsula o processamento de leitura a partir de uma origem, no
			// caso o arquivo
			InputStream inputStream = multipartFile.getInputStream();

			// string correspondente ao tipo de arquivo enviado. Ex.: image, texto, etc
			String contentType = multipartFile.getContentType();

			// retorna a sobrecarga de metodo criado abaixo
			return uploadFile(inputStream, fileName, contentType);
		} catch (IOException e) {
			throw new FileExceptionEmerson("Erro de IO: " + e.getMessage());
		}

	}

	// aula 86 - criação de sobrecarga do metodo
	public URI uploadFile(InputStream inputStream, String fileName, String contentType) {

		try {
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);
			LOG.info("Iniciando upload...");
			s3client.putObject(bucketName, fileName, inputStream, meta);
			LOG.info("Upload finalizado...");

			return s3client.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new FileExceptionEmerson("Erro ao converter a URL para URI");
		}

	}
}
