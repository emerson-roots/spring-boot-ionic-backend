package com.nelioalves.cursomc.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nelioalves.cursomc.services.exceptions.FileExceptionEmerson;

//aula 89
@Service
public class ImageService {

	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {

		// extrai a extensão do arquivo
		String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());

		// testa se é PNG ou JPG, caso contrariop, recusa a requisição
		if (!"png".equals(ext) && !"jpg".equals(ext)) {
			throw new FileExceptionEmerson("Somente imagens PNG e JPG são permitidas");
		}

		try {

			BufferedImage img = ImageIO.read(uploadedFile.getInputStream());

			// converte imagem caso seja .png
			if ("png".equals(ext)) {
				img = pngToJpg(img);
			}

			return img;

		} catch (IOException e) {
			throw new FileExceptionEmerson("Erro ao ler arquivo");
		}
	}

	// aula 89 - converte png para jpg
	public BufferedImage pngToJpg(BufferedImage img) {

		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);

		return jpgImage;
	}

	/**
	 * aula 89
	 * 
	 * retorna um InputStream a partir de um BufferedImage
	 * 
	 * pq o metodo que faz upload para o S3
	 * 
	 * recebe um inputstream
	 */
	public InputStream getInputStream(BufferedImage img, String extension) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, extension, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			throw new FileExceptionEmerson("Erro ao ler arquivo");
		}
	}

}
