package com.rmob.rocket.entities.user.model;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequest {

	@NotBlank(message = "Nome completo é obrigatório")
	@Size(min = 5, max = 100, message = "Informe o nome completo")
	private String nome;

	@NotBlank(message = "CPF é obrigatório")
	@CPF(message = "CPF inválido")
	private String cpf;

	@NotBlank(message = "Data de nascimento é obrigatória")
	@Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Data de nascimento inválida. Use o formato dd/MM/yyyy")
	private String dataNascimento;

	@NotBlank(message = "Nome completo da mãe é obrigatório")
	@Size(min = 5, max = 100, message = "Informe o nome completo da mãe")
	private String nomeMae;

	@Email
	@NotBlank(message = "{user.email.not.blank}")
	private String email;

	@Size(min = 8, max = 20, message = "A senha deve conter de 8 a 20 caracteres")
	@Pattern(regexp = ".*[A-Z].*", message = "A senha deve conter pelo menos uma letra maiúscula")
	private String senha;

	@NotBlank(message = "CEP é obrigatório")
	@Size(min = 8, max = 9, message = "O CEP deve conter de 8 digitos")
	private String cep;

	@NotBlank(message = "CEP é obrigatório")
	private String logradouro;

	private String complemento;

	@NotBlank(message = "bairro é obrigatório")
	private String bairro;

	@NotBlank(message = "cidade é obrigatório")
	private String cidade;

	@NotBlank(message = "uf é obrigatória")
	@Size(min = 2, max = 2, message = "A UF deve conter de 2 caracteres - ex: GO")
	private String uf;

}
