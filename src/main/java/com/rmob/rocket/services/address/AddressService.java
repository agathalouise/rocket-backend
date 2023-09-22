package com.rmob.rocket.services.address;

import com.rmob.rocket.entities.address.Cidade;
import com.rmob.rocket.entities.address.ViaCepResponse;
import com.rmob.rocket.repositories.AddressRepository;
import com.rmob.rocket.services.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.NoSuchElementException;


@Service
@Slf4j
public class AddressService {

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	Utils utils;

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

					ViaCepResponse responseVia = gson.fromJson(response.toString(), ViaCepResponse.class);

					responseVia.setBairro(utils.removerAcentos(responseVia.getBairro()));
					responseVia.setLogradouro(utils.removerAcentos(responseVia.getLogradouro()));
					responseVia.setLocalidade(utils.removerAcentos(responseVia.getLocalidade()));

					return responseVia;
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

		Cidade cidadedb = addressRepository.findByCidade(utils.removerAcentos(cidade))
				.orElseThrow(() -> new NoSuchElementException("A cidade informada não pertence a região metropolitana de Goiania"));

		log.info("cidade db: {}", cidadedb.getCidade());
	}
}
