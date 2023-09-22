package com.rmob.rocket.controllers.user;

import com.rmob.rocket.entities.user.model.UserResponse;
import com.rmob.rocket.entities.user.model.UserUpdate;
import com.rmob.rocket.services.exceptions.DataIntegratyViolationException;
import com.rmob.rocket.services.situacao.SituacaoService;
import com.rmob.rocket.services.user.UserService;
import com.rmob.rocket.services.security.JwtService;
import com.rmob.rocket.services.address.AddressService;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/rocket")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	JwtService jwtService;

	@PostMapping("/upload/docs")
	public ResponseEntity uploadDocs(@RequestHeader("Authorization") String token,
									 @RequestParam(value = "fotoPerfil", required = true) @NotNull MultipartFile fotoPerfil,
									 @RequestParam(value = "identificacaoFrente", required = true )@NotNull MultipartFile identificacaoFrente,
									 @RequestParam(value = "identificacaoVerso", required = true) @NotNull MultipartFile identificacaoVerso,
									 @RequestParam(value = "comprovanteEndereco", required = true) @NotNull MultipartFile comprovanteEndereco) {

		Long idHeaders = jwtService.getUserIdFromToken(token);
		jwtService.isValidToken(token, idHeaders);

		try {
			List<MultipartFile> docs = new ArrayList<>();
				docs.add(fotoPerfil);
				docs.add(identificacaoFrente);
				docs.add(identificacaoVerso);
				docs.add(comprovanteEndereco);

			areValidFileFormats(docs);
			userService.uploadDocs(docs, idHeaders);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/myprofile")
	@ApiOperation("Exibe as infos do user logado")
	public ResponseEntity<UserResponse> findById(@RequestHeader("Authorization") String token){

		Long idHeaders = jwtService.getUserIdFromToken(token);
		jwtService.isValidToken(token, idHeaders);

		log.info("Buscando user por id {} ", idHeaders);
		return ResponseEntity.ok().body(userService.findById(idHeaders));
	}


	@PutMapping(value = "")
	@ApiOperation("Atualiza o user logado")
	public ResponseEntity<UserResponse> updateUser(@RequestHeader("Authorization") String token, @RequestBody UserUpdate request){

		Long idHeaders = jwtService.getUserIdFromToken(token);
		jwtService.isValidToken(token, idHeaders);

		log.info("Atualizando um user");
		log.info("UserResponse request {} ", request);

		return ResponseEntity.ok().body(userService.updateUser(request, idHeaders));
	}

	@DeleteMapping(value = "")
	@ApiOperation("Exclui o user logado")
	public ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") String token){

		Long idHeaders = jwtService.getUserIdFromToken(token);
		jwtService.isValidToken(token, idHeaders);
		log.info("Deletando o usuario com o id: {} ", idHeaders);


		userService.delete(idHeaders);
		return ResponseEntity.noContent().build();
	}

	public void areValidFileFormats(List<MultipartFile> files) {
		log.info("Vefificando se os formatos das imagens são validas");

		for (MultipartFile file : files) {
			if (!isValidFileFormat(file)) {
				throw new DataIntegratyViolationException("Apenas formatos em JPG, PDF ou PNG são aceitos para imagens");
			}
		}
	}

	public boolean isValidFileFormat(MultipartFile file) {
		String originalFilename = file.getOriginalFilename();

		if (originalFilename != null) {
			String fileExtension = originalFilename.toLowerCase();

			return fileExtension.endsWith(".jpg") ||
					fileExtension.endsWith(".pdf") ||
					fileExtension.endsWith(".png");
		}

		return false;
	}

}
