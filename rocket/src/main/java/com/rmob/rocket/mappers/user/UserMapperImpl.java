package com.rmob.rocket.mappers.user;

import com.rmob.rocket.entities.situacao.Situacao;
import com.rmob.rocket.entities.situacao.SituacaoDto;
import com.rmob.rocket.entities.user.User;
import com.rmob.rocket.entities.user.model.UserResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapperImpl implements UserMapper{

	@Override
	public UserResponse mappingToUserDTO(User user) {

		UserResponse userResponse = new UserResponse();
		userResponse.setId(user.getId());
		userResponse.setNome(user.getNome());
		userResponse.setCpf(user.getCpf());
		userResponse.setEmail(user.getEmail());
		userResponse.setDtNasc(user.getDtNasc());
		userResponse.setNomeMae(user.getNomeMae());
		userResponse.setCep(user.getCep());
		userResponse.setLogradouro(user.getLogradouro());
		userResponse.setComplemento(user.getComplemento());
		userResponse.setBairro(user.getBairro());
		userResponse.setCidade(user.getCidade());
		userResponse.setUf(user.getUf());
		userResponse.setFotoPerfil(user.getFotoPerfil());
		userResponse.setIdentificacaoFrente(user.getIdentificacaoFrente());
		userResponse.setIdentificacaoVerso(user.getIdentificacaoVerso());
		userResponse.setComprovanteEndereco(user.getComprovanteEndereco());
		userResponse.setSituacao(mappingToSituacaoDTO(user.getSituacao()));

		return userResponse;
	}


	@Override
	public SituacaoDto mappingToSituacaoDTO(Situacao situacao) {

		SituacaoDto situacaoDto = new SituacaoDto();
		situacaoDto.setId(situacao.getId());
		situacaoDto.setUserid(situacao.getUser().getId());
		situacaoDto.setSituacao(situacao.getSituacao());
		situacaoDto.setDataSolicitacao(situacao.getDataSolicitacao());
		situacaoDto.setQntSolicitacoes(situacao.getQntSolicitacoes());

		return situacaoDto;
	}
	@Override
	public List<UserResponse> mappingListUser(List<User> user) {
		return user.stream()
				.map(u -> {
					UserResponse userResponse = new UserResponse();
					userResponse.setId(u.getId());
					userResponse.setNome(u.getNome());
					userResponse.setCpf(u.getCpf());
					userResponse.setEmail(u.getEmail());
					userResponse.setDtNasc(u.getDtNasc());
					userResponse.setNomeMae(u.getNomeMae());
					userResponse.setCep(u.getCep());
					userResponse.setLogradouro(u.getLogradouro());
					userResponse.setComplemento(u.getComplemento());
					userResponse.setBairro(u.getBairro());
					userResponse.setCidade(u.getCidade());
					userResponse.setUf(u.getUf());
					userResponse.setFotoPerfil(u.getFotoPerfil());
					userResponse.setIdentificacaoFrente(u.getIdentificacaoFrente());
					userResponse.setIdentificacaoVerso(u.getIdentificacaoVerso());
					userResponse.setComprovanteEndereco(u.getComprovanteEndereco());
					userResponse.setSituacao(mappingToSituacaoDTO(u.getSituacao()));
					return userResponse;
				})
				.collect(Collectors.toList());
	}
}
