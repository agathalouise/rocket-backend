package com.rmob.rocket.entities.situacao;

import com.rmob.rocket.entities.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@ToString
@Entity
@Getter
@Setter
public class Situacao {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	@JoinColumn(name = "id_user")
	private User user;
	private String situacao;
	private LocalDate dataSolicitacao;
	private int qntSolicitacoes;

	public Situacao(User user, String situacao, LocalDate dataSolicitacao, int qntSolicitacoes) {
		this.user = user;
		this.situacao = situacao;
		this.dataSolicitacao = dataSolicitacao;
		this.qntSolicitacoes = qntSolicitacoes;
	}

}
