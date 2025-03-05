package br.com.ecosystem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@SpringBootApplication
public class EcoSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(EcoSystemApplication.class, args);

//		try {
//			Thread.sleep(5000);  // Aguarda 5 segundos
//		} catch (InterruptedException e) {
//			Thread.currentThread().interrupt();
//		}
//
//		enviarArquivo();
	}
//
//	private static void enviarArquivo() {
//		RestTemplate restTemplate = new RestTemplate();
//		String filePath = "C:\\Users\\joser\\Desktop\\Cópia de Cópia de Acervo Paleontologia PB - Bibliografia (1).csv";
//		File file = new File(filePath);
//
//		if (!file.exists()) {
//			System.err.println("Arquivo não encontrado: " + filePath);
//			return;
//		}
//
//		FileSystemResource fileResource = new FileSystemResource(file);
//
//		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//		body.add("file", fileResource);
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//		String url = "http://localhost:8080/biblioteca/upload";
//
//		try {
//			ResponseEntity<String> response = restTemplate.exchange(
//					url, HttpMethod.POST, requestEntity, String.class
//			);
//
//			System.out.println("Resposta do servidor: " + response.getBody());
//		} catch (Exception e) {
//			System.err.println("Erro ao enviar arquivo: " + e.getMessage());
//		}
//	}
}
