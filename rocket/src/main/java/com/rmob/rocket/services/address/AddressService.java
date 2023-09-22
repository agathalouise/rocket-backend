package com.rmob.rocket.services.address;

import com.rmob.rocket.entities.address.Cidade;
import com.rmob.rocket.entities.address.ViaCepResponse;
import com.rmob.rocket.repositories.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class AddressService {

	@Autowired
	AddressRepository addressRepository;

	private final Gson gson = new Gson();

	public ViaCepResponse consultarApiViaCep(String cep) {
		log.info("Iniciando a consulta do endereço pela api do Via CEP");
		try {
			String url = "https://viacep.com.br/ws/" + cep + "/json/";
			log.info("url via cep request > {}", url);

			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("GET");

			if (conn.getResponseCode() == 200) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
					StringBuilder response = new StringBuilder();
					String line;

					while ((line = reader.readLine()) != null) {
						response.append(line);
					}

					return gson.fromJson(response.toString(), ViaCepResponse.class);
				}
			} else {
				log.error("Erro ao consultar o CEP: Código de resposta {}", conn.getResponseCode());
			}

			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void consultarRegiaoMetropolitana(String cidade){
		log.info("Verificando se a cidade pertence a região metropolitana de Goiania: {}", cidade);

		//retirando acentos e caracteres especiais
		cidade = Normalizer.normalize(cidade, Normalizer.Form.NFD);
		cidade = cidade.replaceAll("[^\\p{ASCII}]", "");

		Cidade cidadedb = addressRepository.findByCidade(cidade)
				.orElseThrow(() -> new NoSuchElementException("A cidade informada não pertence a região metropolitana de Goiania"));

		log.info("cidade db: {}", cidadedb.getCidade());
	}
}
