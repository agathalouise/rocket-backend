package com.rmob.rocket.entities.funcionario;

import jakarta.persistence.*;

import lombok.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Funcionario {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String cpf;
	@Column(name = "email", unique = true)
	private String email;
	private String senha;
	private static final String cargo = "Funcionario";

	public Funcionario(String nome, String cpf, String email, String senha) {
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		setSenha(senha);
	}

	public void setSenha(String senha) {
		this.senha = BCrypt.hashpw(senha, BCrypt.gensalt());;
	}
}
