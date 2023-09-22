package com.rmob.rocket.entities.address;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ViaCepResponse {
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String localidade;
	private String uf;


	public String getCep() {
		return cep.replaceAll("\\D", "");
	}
}
