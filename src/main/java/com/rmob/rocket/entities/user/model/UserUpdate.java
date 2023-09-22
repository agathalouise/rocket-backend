package com.rmob.rocket.entities.user.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdate {

	@Size(min = 5, max = 60, message = "Informe o nome completo")
	private String nome;

	@CPF(message = "CPF inválido")
	private String cpf;

	@Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Data de nascimento inválida. Use o formato dd/MM/yyyy")
	private String dataNascimento;

	@Size(min = 5, max = 60, message = "Informe o nome completo da mãe")
	private String nomeMae;

	@Email
	private String email;

	@Size(min = 8, max = 20, message = "A senha deve conter de 8 a 20 caracteres")
	@Pattern(regexp = ".*[A-Z].*", message = "A senha deve conter pelo menos uma letra maiúscula")
	private String senha;

	@Size(min = 8, max = 9, message = "O CEP deve conter de 8 digitos")
	private String cep;

	private String logradouro;

	private String complemento;

	private String bairro;

	private String cidade;

	@Size(min = 2, max = 2, message = "A UF deve conter de 2 caracteres - ex: GO")
	private String uf;

}
