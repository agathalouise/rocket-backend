package com.rmob.rocket.mappers.user;

import com.rmob.rocket.entities.situacao.Situacao;
import com.rmob.rocket.entities.situacao.SituacaoDto;
import com.rmob.rocket.entities.user.User;
import com.rmob.rocket.entities.user.model.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

	UserResponse mappingToUserDTO(User user);

	List<UserResponse> mappingListUser (List<User> user);

	SituacaoDto mappingToSituacaoDTO(Situacao situacao);
}
