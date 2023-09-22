package com.rmob.rocket.entities.user.model;

import com.rmob.rocket.entities.situacao.Situacao;
import com.rmob.rocket.entities.situacao.SituacaoDto;
import lombok.*;


import java.time.LocalDate;
import java.util.UUID;

@Data
@ToString
public class UserResponse {

	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private LocalDate dtNasc;
	private String nomeMae;
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String cidade;
	private String uf;
	private String fotoPerfil;
	private String identificacaoFrente;
	private String identificacaoVerso;
	private String comprovanteEndereco;
	private SituacaoDto situacao;

}
