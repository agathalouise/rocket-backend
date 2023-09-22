package com.rmob.rocket.entities.situacao;

import com.rmob.rocket.entities.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class SituacaoDto {

	private Long id;
	private Long userid;
	private String situacao;
	private LocalDate dataSolicitacao;
	private int qntSolicitacoes;

}
