package com.rmob.rocket.repositories;

import com.rmob.rocket.entities.funcionario.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	Optional<Funcionario> findByEmail(String email);
}