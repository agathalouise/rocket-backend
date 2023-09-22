package com.rmob.rocket.repositories;

import com.rmob.rocket.entities.address.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Cidade, Long> {
	Optional<Cidade> findByCidade(String cidade);
}
