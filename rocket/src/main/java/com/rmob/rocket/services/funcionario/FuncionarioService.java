package com.rmob.rocket.services.funcionario;


import com.rmob.rocket.entities.funcionario.FuncionarioDto;
import com.rmob.rocket.entities.situacao.Situacao;
import com.rmob.rocket.entities.user.model.UserResponse;
import com.rmob.rocket.entities.funcionario.Funcionario;
import com.rmob.rocket.entities.funcionario.FuncionarioRequest;
import com.rmob.rocket.entities.funcionario.FuncionarioUpdate;
import com.rmob.rocket.mappers.funcionario.FuncionarioMapper;
import com.rmob.rocket.repositories.FuncionarioRepository;
import com.rmob.rocket.services.exceptions.DataIntegratyViolationException;
import com.rmob.rocket.services.situacao.SituacaoService;
import com.rmob.rocket.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@Slf4j
public class FuncionarioService {

	@Autowired
	FuncionarioRepository repository;

	@Autowired
	FuncionarioMapper mapper;

	@Autowired
	SituacaoService situacaoService;

	@Autowired
	UserService userService;

	public static final String FUNCIONARIO_NAO_ENCONTRADO = "Funcionario não encontrado";

	public Long createUser(FuncionarioRequest request){
		log.info("FuncionarioService -> Criando novo funcionario");

		if (repository.findByEmail(request.getEmail()).isPresent()) {
			throw new DataIntegratyViolationException("Email já cadastrado");
		}

		Funcionario funcionario = new Funcionario(request.getNome(), request.getCpf(), request.getEmail(), request.getSenha());

		repository.save(funcionario);
		return funcionario.getId();
	}

	public FuncionarioDto findFuncionarioByEmailDto(String email){
		log.info("FuncionarioService -> Buscando funcionario por email");

		Funcionario funcionario = repository.findByEmail(email)
				.orElseThrow(() -> new NoSuchElementException(FUNCIONARIO_NAO_ENCONTRADO));

		return mapper.mappingToFuncionarioDTO(funcionario);
	}

	public Funcionario findFuncionarioByEmail(String email){
		log.info("FuncionarioService -> Buscando funcionario por email");

		return repository.findByEmail(email)
				.orElseThrow(() -> new NoSuchElementException(FUNCIONARIO_NAO_ENCONTRADO));
	}

	public Long aprovarSituacao(Long id){
		log.info("FuncionarioService -> aprovar cadastro");
		UserResponse user = userService.findById(id);

		Situacao situacao = situacaoService.aprovarCadastro(user.getId());
		return situacao.getUser().getId();
	}

	public Long reprovarSituacao(Long id){
		log.info("FuncionarioService -> reprovar cadastro");
		UserResponse user = userService.findById(id);

		Situacao situacao = situacaoService.reprovarCadastro(user.getId());
		return situacao.getUser().getId();
	}

	public FuncionarioDto findById(Long id) {
		Funcionario funcionario = repository.findById(id)
				.orElseThrow(() -> new NoSuchElementException(FUNCIONARIO_NAO_ENCONTRADO));
		return mapper.mappingToFuncionarioDTO(funcionario);
	}

	public FuncionarioDto updateFuncionario(FuncionarioUpdate request, Long id) {
		Optional<Funcionario> funcionarioOp = repository.findById(id);

		if (funcionarioOp.isEmpty()) {
			throw new DataIntegratyViolationException(FUNCIONARIO_NAO_ENCONTRADO);
		}

		if(repository.findByEmail(request.getEmail()).isPresent()){
			throw new DataIntegratyViolationException("Email já cadastrado");
		}

		Funcionario funcionario = funcionarioOp.get();

		if(request.getNome() != null) {funcionario.setNome(request.getNome());}
		if (request.getEmail() != null) {funcionario.setEmail(request.getEmail());}
		if (request.getSenha() != null) {funcionario.setSenha(request.getSenha());}
		if (request.getCpf() != null) {funcionario.setCpf(request.getCpf());}

		return mapper.mappingToFuncionarioDTO(repository.save(funcionario));
	}

	public void delete(Long id) {
		findById(id);
		repository.deleteById(id);
	}

}
