package com.rmob.rocket.services.situacao;

import com.rmob.rocket.entities.situacao.Situacao;
import com.rmob.rocket.mappers.user.UserMapper;
import com.rmob.rocket.repositories.SituacaoCadastroRepository;
import com.rmob.rocket.services.exceptions.DataIntegratyViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;


@Service
@Slf4j
public class SituacaoService {

	@Autowired
	private SituacaoCadastroRepository situacaoCadastroRepository;

	@Autowired
	UserMapper mapper;

	public void createSituacao (Situacao situacao){
		situacaoCadastroRepository.save(situacao);
	}

	@Transactional
	public Situacao aprovarCadastro(Long userId) {
		Situacao situacao = buscaSituacaoPorIdUser(userId);
		verificaQntSolicitacoes(userId);

		if (situacao != null) {
			situacao.setSituacao("Aprovado");
			situacaoCadastroRepository.save(situacao);
		}

		return situacao;
	}


	@Transactional
	public Situacao emAnalise(Long userId) {
		Situacao situacao = buscaSituacaoPorIdUser(userId);
		verificaQntSolicitacoes(userId);

		if (situacao != null) {
			situacao.setSituacao("Em Análise");
			situacao.setDataSolicitacao(LocalDate.now());
			situacaoCadastroRepository.save(situacao);
		}

		return situacao;
	}

	@Transactional
	public Situacao reprovarCadastro(Long userId) {
		Situacao situacao = buscaSituacaoPorIdUser(userId);

		if (situacao != null) {
			situacao.setSituacao("Reprovado");
			situacao.setDataSolicitacao(LocalDate.now());
			situacaoCadastroRepository.save(situacao);
		}

		return situacao;
	}

	public Situacao buscaSituacaoPorIdUser(Long userId){
		Situacao situacao = situacaoCadastroRepository.findByUserId(userId)
				.orElseThrow(() -> new NoSuchElementException("Situação de cadastro não encontrada"));

		log.info("Situação do cadastro {}", situacao);
		return situacao;
	}

	public void atualizaQntdSolicitacao (Long userId){
		Situacao situacao = situacaoCadastroRepository.findByUserId(userId)
				.orElseThrow(() -> new NoSuchElementException("Situação de cadastro não encontrada"));

		situacao.setQntSolicitacoes(situacao.getQntSolicitacoes() + 1);
		situacaoCadastroRepository.save(situacao);
	}

	public void verificaQntSolicitacoes(Long userId){
		Situacao situacao = buscaSituacaoPorIdUser(userId);
		if (situacao.getQntSolicitacoes() >= 2) {
			throw new DataIntegratyViolationException("Quantidade de solicitações excede o permitido");
		}
	}


	public void findById(Long id) {
		Situacao situacao = situacaoCadastroRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Situação não encontrada"));
		mapper.mappingToSituacaoDTO(situacao);
	}

	public void delete(Long id) {
		findById(id);
		situacaoCadastroRepository.deleteById(id);
	}

}
