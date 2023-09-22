package com.rmob.rocket.repositories;

import com.rmob.rocket.entities.situacao.Situacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SituacaoCadastroRepository extends JpaRepository<Situacao, Long> {

	Optional<Situacao> findByUserId(Long userId);
}