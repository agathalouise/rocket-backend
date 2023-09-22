package com.rmob.rocket.services.imgur;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rmob.rocket.entities.address.Cidade;
import com.rmob.rocket.entities.address.ViaCepResponse;
import com.rmob.rocket.entities.imgur.ImgurResponse;
import com.rmob.rocket.repositories.AddressRepository;
import com.rmob.rocket.services.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.NoSuchElementException;


@Service
@Slf4j
public class ImgurService {

	private final String IMGUR_CLIENT_ID = "18eb8bab84c9db6";
	private final String IMGUR_CLIENT_SECRET = "7521289bae7c957144f2570bdb7a95478f6291bd";
	private final String IMGUR_BEARER_TOKEN = "b7a80486dc7b9f55a2d7f903d380e1baf54e72b3";


	public String uploadImage(MultipartFile imageFile) {
		log.info("ImgurService iniciando o upload do arquivo");
		try {
			byte[] imageBytes = imageFile.getBytes();

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Client-ID " + IMGUR_CLIENT_ID);
			headers.set("Authorization", "Bearer " + IMGUR_BEARER_TOKEN);

			log.info("headers {}", headers);

			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			body.add("image", new HttpEntity<>(imageBytes, headers));

			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

			ResponseEntity<String> response = restTemplate.exchange(
					"https://api.imgur.com/3/image",
					HttpMethod.POST,
					requestEntity,
					String.class
			);

			if (response.getStatusCode() == HttpStatus.OK) {
				String responseBody = response.getBody();

				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

				ImgurResponse imgurResponse = objectMapper.readValue(responseBody, ImgurResponse.class);

				String imageUrl = imgurResponse.getData().getLink();
				log.info("ImgurService - sucesso no upload, url {}", imageUrl);

				return imageUrl;
			} else {
				throw new RuntimeException("Erro ao fazer o upload da imagem para o Imgur");
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao processar a imagem: " + e.getMessage(), e);
		}
	}
}
