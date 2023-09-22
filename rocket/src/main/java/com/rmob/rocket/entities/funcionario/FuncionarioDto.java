package com.rmob.rocket.entities.funcionario;


import lombok.*;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class FuncionarioDto {

	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private static final String cargo = "Funcionario";
}
