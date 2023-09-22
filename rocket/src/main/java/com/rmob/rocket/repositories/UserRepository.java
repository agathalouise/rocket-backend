package com.rmob.rocket.repositories;

import com.rmob.rocket.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

//	@Query("SELECT u FROM User u INNER JOIN u.situacaoCadastro s WHERE s.status = :status")
//	List<User> findByStatus(@Param("status") String status);
}