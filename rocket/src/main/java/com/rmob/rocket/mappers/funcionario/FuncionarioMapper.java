package com.rmob.rocket.mappers.funcionario;

import com.rmob.rocket.entities.funcionario.Funcionario;
import com.rmob.rocket.entities.funcionario.FuncionarioDto;
import org.springframework.stereotype.Component;


@Component
public class FuncionarioMapper {

	public FuncionarioDto mappingToFuncionarioDTO(Funcionario funcionario) {
		FuncionarioDto funcionarioDto = new FuncionarioDto();
		funcionarioDto.setId(funcionario.getId());
		funcionarioDto.setCpf(funcionario.getCpf());
		funcionarioDto.setNome(funcionario.getNome());
		funcionarioDto.setEmail(funcionario.getEmail());

		return funcionarioDto;
	}
}
