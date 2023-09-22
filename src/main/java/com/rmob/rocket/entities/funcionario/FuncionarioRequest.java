package com.rmob.rocket.entities.funcionario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class FuncionarioRequest {

	@NotBlank(message = "Nome completo é obrigatório")
	@Size(min = 5, max = 100, message = "Informe o nome completo")
	private String nome;

	@NotBlank(message = "CPF é obrigatório")
	@CPF(message = "CPF inválido")
	private String cpf;

	@Email
	@NotBlank(message = "{user.email.not.blank}")
	@Pattern(regexp = ".*@(rocket\\.com|rocket\\.com\\.br)$", message = "O email deve ser da Rocket: exemplo@rocket.com")
	private String email;

	@Size(min = 8, max = 20, message = "A senha deve conter de 8 a 20 caracteres")
	@Pattern(regexp = ".*[A-Z].*", message = "A senha deve conter pelo menos uma letra maiúscula")
	private String senha;

}
