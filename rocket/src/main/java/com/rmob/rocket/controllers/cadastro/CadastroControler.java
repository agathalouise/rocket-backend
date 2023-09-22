package com.rmob.rocket.controllers.cadastro;

import com.rmob.rocket.entities.address.ViaCepResponse;
import com.rmob.rocket.entities.funcionario.FuncionarioRequest;
import com.rmob.rocket.entities.user.model.UserRequest;
import com.rmob.rocket.services.address.AddressService;
import com.rmob.rocket.services.exceptions.DataIntegratyViolationException;
import com.rmob.rocket.services.funcionario.FuncionarioService;
import com.rmob.rocket.services.user.UserService;

import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("v1/rocket/cadastrar")
public class CadastroControler {

	@Autowired
	private UserService userService;

	@Autowired
	AddressService addressService;

	@Autowired
	private FuncionarioService funcionarioService;


	@PostMapping("/passelivre")
	@ApiOperation("Realizar cadastro para disponibilização do cartão de uso irrestrito e sem custos no transporte coletivo - passelivre")
	public ResponseEntity<String> cadastroPasseLivre(@RequestBody @Valid UserRequest request){

		log.info("PasseLivreControler - Criando um novo cadastro para o Passe Livre");
		log.info("Iniciando a validação do request {} ", request);

		this.verificaMaioridade(request);
		ViaCepResponse enderecoViaCep = validaEndereco(request);

		log.info("Request Valido");
		Long idUser = userService.createUser(request, enderecoViaCep);
		return ResponseEntity.status(HttpStatus.CREATED).body(idUser.toString());
	}

	@PostMapping("/funcionario")
	@ApiOperation("Realizar cadastro de funcionario")
	public ResponseEntity<String> cadastroPasseLivre(@RequestBody @Valid FuncionarioRequest request) {

		log.info("FuncionarioController - Criando um novo cadastro de funcionario");
		log.info("Iniciando a validação do request {} ", request);
		Long id = funcionarioService.createUser(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(id.toString());
	}


	public void verificaMaioridade(UserRequest request){
		log.info("Validando idade pela data de nascimento {}", request.getDataNascimento());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		try {
			LocalDate dataNascimento = LocalDate.parse(request.getDataNascimento().toString(), formatter);
			LocalDate dataAtual = LocalDate.now();
			Period periodo = Period.between(dataNascimento, dataAtual);

			if (periodo.getYears() < 18) {
				throw new DataIntegratyViolationException("Necessário ter 18 anos ou mais para realizar o cadastro.");
			}
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Formato de data inválido. Use o formato DD/MM/AAAA.");
		}

	}

	public ViaCepResponse validaEndereco(UserRequest request){
		log.info("Validando endereço CEP: {}", request.getCep());

		String cepFormatted = request.getCep().replaceAll("\\D", "");
		ViaCepResponse endereco  = addressService.consultarApiViaCep(cepFormatted);

		if(endereco == null) {
			throw new DataIntegratyViolationException("Endereço invalido");
		}

		addressService.consultarRegiaoMetropolitana(endereco.getLocalidade());
		return endereco;
	}
}
