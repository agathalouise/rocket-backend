package com.rmob.rocket.services.utils;

import org.springframework.stereotype.Component;

import java.text.Normalizer;

@Component
public class Utils {

	public String removerAcentos(String texto) {
		if (texto == null) {
			return null;
		}

		texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
		texto = texto.replaceAll("[^\\p{ASCII}]", "");

		return texto;
	}
}
