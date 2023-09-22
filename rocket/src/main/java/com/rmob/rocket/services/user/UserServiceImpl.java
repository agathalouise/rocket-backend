package com.rmob.rocket.services.user;


import com.rmob.rocket.entities.address.ViaCepResponse;
import com.rmob.rocket.entities.situacao.Situacao;
import com.rmob.rocket.entities.user.User;
import com.rmob.rocket.entities.user.model.UserRequest;
import com.rmob.rocket.entities.user.model.UserResponse;
import com.rmob.rocket.entities.user.model.UserUpdate;
import com.rmob.rocket.mappers.user.UserMapper;
import com.rmob.rocket.repositories.UserRepository;
import com.rmob.rocket.services.exceptions.DataIntegratyViolationException;
import com.rmob.rocket.services.situacao.SituacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
	public static final String PENDENTE = "Envio pendente";

	@Autowired
	UserRepository repository;

	@Autowired
	UserMapper mapper;

	@Autowired
	SituacaoService situacaoService;

	@Override
	public UserResponse findById(Long id) {
		User user = repository.findById(id)
				.orElseThrow(() -> new NoSuchElementException(USUARIO_NAO_ENCONTRADO));
		return mapper.mappingToUserDTO(user);
	}

	@Override
	@Transactional
	public Long createUser(UserRequest request, ViaCepResponse viaCepResponse) {
		log.info("UserServiceImpl iniciando metódo createUser");

		if (repository.findByEmail(request.getEmail()).isPresent()) {
			throw new DataIntegratyViolationException("Email já cadastrado");
		}

		User user = new User();
		user.setSenha(request.getSenha());
		user.setCpf(request.getCpf());
		user.setNome(request.getNome());
		user.setEmail(request.getEmail());
		user.setDtNasc(request.getDataNascimento());
		user.setNomeMae(request.getNomeMae());
		user.setCep(viaCepResponse.getCep());
		user.setLogradouro(viaCepResponse.getLogradouro());
		user.setComplemento(request.getComplemento());
		user.setBairro(viaCepResponse.getBairro());
		user.setCidade(viaCepResponse.getLocalidade());
		user.setUf(viaCepResponse.getUf());
		user.setFotoPerfil(PENDENTE);
		user.setIdentificacaoFrente(PENDENTE);
		user.setIdentificacaoVerso(PENDENTE);
		user.setComprovanteEndereco(PENDENTE);

		log.info("Salvando user no db: {}", user);
		repository.save(user);


		log.info("Salvando situacao no db");
		Situacao situacao = new Situacao(user, "Em Análise", LocalDate.now(), 1);
		situacaoService.createSituacao(situacao);

		return user.getId();
	}

	@Override
	public UserResponse updateUser(UserUpdate request, Long id) {
		Optional<User> userOptional = repository.findById(id);

		if (userOptional.isEmpty()) {
			throw new DataIntegratyViolationException(USUARIO_NAO_ENCONTRADO);
		}

		if (repository.findByEmail(request.getEmail()).isPresent()) {
			throw new DataIntegratyViolationException("Email já cadastrado");
		}

		User user = userOptional.get();

		if (request.getNome() != null) {
			user.setNome(request.getNome());
		}
		if (request.getEmail() != null) {
			user.setEmail(request.getEmail());
		}
		if (request.getSenha() != null) {
			user.setSenha(request.getSenha());
		}
		if (request.getCpf() != null) {
			user.setCpf(request.getCpf());
		}
		if (request.getDataNascimento() != null) {
			user.setDtNasc(request.getDataNascimento());
		}
		if (request.getNomeMae() != null) {
			user.setNomeMae(request.getNomeMae());
		}
		if (request.getCep() != null) {
			user.setCep(request.getCep());
		}
		if (request.getLogradouro() != null) {
			user.setLogradouro(request.getLogradouro());
		}
		if (request.getComplemento() != null) {
			user.setComplemento(request.getComplemento());
		}
		if (request.getBairro() != null) {
			user.setBairro(request.getBairro());
		}
		if (request.getCidade() != null) {
			user.setCidade(request.getCidade());
		}
		if (request.getUf() != null) {
			user.setUf(request.getUf());
		}

		return mapper.mappingToUserDTO(repository.save(user));
	}

	@Override
	public void delete(Long id) {
		UserResponse us = findById(id);
		situacaoService.delete(us.getSituacao().getId());
		repository.deleteById(id);
	}

	@Override
	public List<UserResponse> findAll() {
		return mapper.mappingListUser(repository.findAll());
	}

//	@Override
//	public List<UserResponse> findByStatus(String status) {
//		return mapper.mappingListUser(repository.findByStatus(status));
//	}

	@Override
	public UserResponse findByEmail(String email) {
		User user = repository.findByEmail(email)
				.orElseThrow(() -> new NoSuchElementException(USUARIO_NAO_ENCONTRADO));

		return mapper.mappingToUserDTO(user);
	}

	@Override
	public User getUser(String email) {
		return repository.findByEmail(email)
				.orElseThrow(() -> new NoSuchElementException(USUARIO_NAO_ENCONTRADO));

	}

	@Override
	public void uploadDocs(List<MultipartFile> photos, Long idUser) {
		User user = repository.findById(idUser)
				.orElseThrow(() -> new NoSuchElementException(USUARIO_NAO_ENCONTRADO));

		List<String> photoUri = new ArrayList<>();

		photos.forEach(photo -> {
			try {
				var fileName = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1);
				photoUri.add(""); //TODO fileUploadService.upload(photo, fileName));

			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		});

		user.setFotoPerfil(photoUri.get(1));
		user.setIdentificacaoFrente(photoUri.get(2));
		user.setIdentificacaoVerso(photoUri.get(3));
		user.setComprovanteEndereco(photoUri.get(4));

		repository.save(user);
	}
}
