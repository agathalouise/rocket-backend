package com.rmob.rocket.controllers.funcionario;

import com.rmob.rocket.entities.funcionario.FuncionarioDto;
import com.rmob.rocket.entities.funcionario.FuncionarioUpdate;
import com.rmob.rocket.entities.user.model.UserResponse;
import com.rmob.rocket.services.exceptions.UserNotAuthenticate;
import com.rmob.rocket.services.funcionario.FuncionarioService;
import com.rmob.rocket.services.security.JwtService;
import com.rmob.rocket.services.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("/v1/rocket/adm")
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	UserService userService;

	@Autowired
	JwtService jwtService;


	@GetMapping(value = "/myprofile")
	@ApiOperation("Buscar funcionario logado pelo id")
	public ResponseEntity<FuncionarioDto> findFuncionarioById(@RequestHeader("Authorization") String token) {
		Long id = validarFuncionario(token);

		log.info("Buscando funcionario por id {} ", id);
		return ResponseEntity.ok().body(funcionarioService.findById(id));
	}

	@GetMapping(value = "/{email}")
	@ApiOperation("Buscar funcionario pelo email")
	public ResponseEntity<FuncionarioDto> findFuncionarioByEmail(@RequestHeader("Authorization") String token, @PathVariable String email) {
		Long id = validarFuncionario(token);

		log.info("Buscando funcionario por email {} ", id);
		return ResponseEntity.ok().body(funcionarioService.findFuncionarioByEmailDto(email));
	}

	@PutMapping(value = "")
	@ApiOperation("Atualiza o funcionario logado")
	public ResponseEntity<FuncionarioDto> updateFuncionario(@RequestHeader("Authorization") String token, @RequestBody FuncionarioUpdate request) {
		log.info("FuncionarioController - Atualizando funcionario -> {} ", request);
		Long id = validarFuncionario(token);

		return ResponseEntity.ok().body(funcionarioService.updateFuncionario(request, id));
	}

	@DeleteMapping(value = "")
	@ApiOperation("Deleta o funcionario logado")
	public ResponseEntity<Void> delete(@RequestHeader("Authorization") String token) {
		Long id = validarFuncionario(token);
		log.info("FuncionarioController - Deletando o funcionario com o id{} ", id);

		funcionarioService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/usuario", params = "id")
	@ApiOperation("Buscar usuário pelo id")
	public ResponseEntity<UserResponse> findUserById(@RequestHeader("Authorization") String token, @RequestParam Long id) {
		validarFuncionario(token);

		log.info("Buscando user por id {} ", id);
		return ResponseEntity.ok().body(userService.findById(id));
	}
	@GetMapping(value = "/usuario", params = "email")
	@ApiOperation("Buscar usuário por email")
	public ResponseEntity<UserResponse> findUserByEmail(@RequestHeader("Authorization") String token, @RequestParam String email) {
		validarFuncionario(token);

		log.info("Buscando user por email {} ", email);
		return ResponseEntity.ok().body(userService.findByEmail(email));
	}

	@GetMapping(value = "/usuario")
	@ApiOperation("Buscar todos os usuarios")
	public ResponseEntity<List<UserResponse>> findAll(@RequestHeader("Authorization") String token) {
		validarFuncionario(token);

		log.info("Iniciando a listagem de todos os users");
		return ResponseEntity.ok().body(userService.findAll());
	}

	@PostMapping("/usuario/aprovar")
	@ApiOperation("Aprovar cadastro de um usuario por id")
	public ResponseEntity<String> aprovarCadastro(@RequestHeader("Authorization") String token, @RequestBody Map<String, Long> id) {
		validarFuncionario(token);

		Long idUser = funcionarioService.aprovarSituacao(id.get("id"));
		return ResponseEntity.status(HttpStatus.CREATED).body(idUser.toString());
	}

	@PostMapping("/usuario/reprovar")
	@ApiOperation("Reprovar cadastro de um usuario por id")
	public ResponseEntity<String> reprovarCadastro(@RequestHeader("Authorization") String token, @RequestBody Map<String, Long> id) {
		validarFuncionario(token);

		Long idUser = funcionarioService.reprovarSituacao(id.get("id"));
		return ResponseEntity.status(HttpStatus.CREATED).body(idUser.toString());
	}

	@DeleteMapping(value = "/usuario/{id}")
	@ApiOperation("Deletar um usuario por id")
	public ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") String token, @PathVariable Long id){

		validarFuncionario(token);
		log.info("Deletando o usuario com o id: {} ", id);

		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	public Long validarFuncionario(String token) {
		String email = jwtService.getEmailFromToken(token);
		String regex = ".*@(rocket\\.com|rocket\\.com\\.br)$";

		if (!Pattern.matches(regex, email)) {
			throw new UserNotAuthenticate("Funcão disponivel apenas para funcionarios");
		}

		Long idHeaders = jwtService.getUserIdFromToken(token);
		jwtService.isValidToken(token, idHeaders);

		return idHeaders;
	}


}
