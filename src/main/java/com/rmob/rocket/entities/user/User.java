package com.rmob.rocket.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rmob.rocket.entities.situacao.Situacao;
import com.rmob.rocket.services.exceptions.DataIntegratyViolationException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
@Entity
@ToString
public class User {

	@Id
	@Column(name = "id_user", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String senha;
	private String cpf;
	@Column(name = "email", unique = true)
	private String email;
	@Column(name = "data_nascimento")
	private LocalDate dtNasc;
	@Column(name = "nome_mae")
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
	@OneToOne(mappedBy = "user")
	@JsonIgnore
	private Situacao situacao;

	public User(Long id, String nome, String senha, String cpf, String email, String dtNasc, String nomeMae,
				String cep, String logradouro, String complemento, String bairro, String cidade, String uf,
				String fotoPerfil, String identificacaoFrente, String identificacaoVerso, String comprovanteEndereco,
				Situacao situacao) {
		this.nome = nome;
		setSenha(senha);
		this.cpf = cpf;
		this.email = email;
		setDtNasc(dtNasc);
		this.nomeMae = nomeMae;
		this.cep = cep;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.fotoPerfil = fotoPerfil;
		this.identificacaoFrente = identificacaoFrente;
		this.identificacaoVerso = identificacaoVerso;
		this.comprovanteEndereco = comprovanteEndereco;
		this.situacao = situacao;
	}

	public void setSenha(String senha) {
		this.senha = BCrypt.hashpw(senha, BCrypt.gensalt());;
	}

	public void setDtNasc(String data) {

		if (data == null || data.isEmpty()){
			throw new DataIntegratyViolationException("data de nascimento invalida");
		}

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			this.dtNasc = LocalDate.parse(data, formatter);

			if(dtNasc.isAfter(LocalDate.now())) {
				throw new DataIntegratyViolationException("data de nascimento deve ser anterior a data de hoje");
			}

		} catch (Exception e) {
				log.info("Erro ao converter data {}", e.getMessage());
				throw new DataIntegratyViolationException(e.getMessage());
		}
	}


}

