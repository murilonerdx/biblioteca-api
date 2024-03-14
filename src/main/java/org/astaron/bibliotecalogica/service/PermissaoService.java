package org.astaron.bibliotecalogica.service;

import org.astaron.bibliotecalogica.handler.BusinessException;
import org.astaron.bibliotecalogica.model.Permissao;
import org.astaron.bibliotecalogica.model.enums.TipoPermissaoEnum;
import org.astaron.bibliotecalogica.repository.PermissaoRepository;
import org.astaron.bibliotecalogica.response.StatusPermissao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;


@Service
public class PermissaoService {

	@Value(value = "${codigos.codigo-gerador}")
	private String cdGr;

	@Value(value = "${codigos.codigo-validador}")
	private String cdVl;

	@Autowired
	private final PermissaoRepository permissaoRepository;

	public PermissaoService(PermissaoRepository permissaoRepository) {
		this.permissaoRepository = permissaoRepository;
	}

	public boolean acessar(String codigoUso) throws Exception {
		Permissao byCodigoUso = findByCodigoUso(codigoUso);

		if (byCodigoUso != null) {
			int qtdAcesso = byCodigoUso.getQtdAcesso();
			byCodigoUso.setQtdAcesso(qtdAcesso + 1);
			permissaoRepository.save(byCodigoUso);

			return true;
		} else {
			return false;
		}
	}

	public Permissao findByCodigoUso(String codigoUso) {
		return permissaoRepository.findByCodigoUso(codigoUso);
	}

	public String gerarPermissao(String codigoGerador, String ldap, String email) throws Exception {
		if (Objects.equals(cdGr, codigoGerador)) {
			Permissao byLdapAndEmail = permissaoRepository.findByLdapAndEmail(ldap, email);

			if (byLdapAndEmail != null && byLdapAndEmail.getTipoPermissao().equals(TipoPermissaoEnum.ACEITA)) {
				int qtdAcesso = byLdapAndEmail.getQtdAcesso();
				byLdapAndEmail.setQtdAcesso(qtdAcesso + 1);

				if (byLdapAndEmail.getCodigoUso() == null) {
					String codigoUser = UUID.randomUUID().toString().concat("-" + LocalDate.now() + "-" + ldap);
					byLdapAndEmail.setCodigoUso(codigoUser);
					permissaoRepository.save(byLdapAndEmail);
					return codigoUser;
				}
				if (byLdapAndEmail.getQtdAcesso() >= 10) {
					byLdapAndEmail.setTipoPermissao(TipoPermissaoEnum.EM_ANDAMENTO);
					permissaoRepository.save(byLdapAndEmail);
					throw new BusinessException("Você passou dos limites de acesso, por favor solicite novament o acesso");
				}

				permissaoRepository.save(byLdapAndEmail);
				return byLdapAndEmail.getCodigoUso();
			} else {
				throw new BusinessException("Você não tem permissão ACEITA");
			}
		} else {
			throw new BusinessException("Codigo invalido");
		}
	}

	public StatusPermissao solicitarPermissao(String ldap, String email) throws Exception {
		return getStatusPermissao(ldap, email);
	}

	public StatusPermissao darPermissao(String ldap, String email, String codigoValidador, String tipoPermissaoEnum) throws Exception {
		if (Objects.equals(cdVl, codigoValidador)) {
			Permissao byLdapAndEmail = permissaoRepository.findByLdapAndEmail(ldap, email);
			if (byLdapAndEmail != null) {
				byLdapAndEmail.setTipoPermissao(TipoPermissaoEnum.valueOf(tipoPermissaoEnum));

				permissaoRepository.save(byLdapAndEmail);

				return new StatusPermissao(TipoPermissaoEnum.valueOf(tipoPermissaoEnum), TipoPermissaoEnum.valueOf(tipoPermissaoEnum).getDescricao());
			} else {
				throw new BusinessException("Permissao não encontrada");
			}

		} else {
			throw new BusinessException("Codigo validador incorreto");
		}
	}

	private StatusPermissao getStatusPermissao(String ldap, String email) throws Exception {
		Permissao byLdapAndEmail = permissaoRepository.findByLdapAndEmail(ldap, email);

		if (byLdapAndEmail != null && byLdapAndEmail.getTipoPermissao() != null) {
			throw new BusinessException("Você já tem uma solicitação por favor verifique o status");
		} else {
			permissaoRepository.save(new Permissao(null, ldap, null, email, TipoPermissaoEnum.EM_ANDAMENTO));
			return new StatusPermissao(TipoPermissaoEnum.EM_ANDAMENTO, TipoPermissaoEnum.EM_ANDAMENTO.getDescricao());
		}
	}

	public StatusPermissao verificarStatusPermissao(String ldap, String email) throws Exception {
		Permissao byLdapAndEmail = permissaoRepository.findByLdapAndEmail(ldap, email);

		if (byLdapAndEmail != null) {
			if (byLdapAndEmail.getTipoPermissao().equals(TipoPermissaoEnum.EM_ANDAMENTO)) {
				return new StatusPermissao(TipoPermissaoEnum.EM_ANDAMENTO, TipoPermissaoEnum.EM_ANDAMENTO.getDescricao());
			} else if (byLdapAndEmail.getTipoPermissao().equals(TipoPermissaoEnum.NEGADA)) {
				return new StatusPermissao(TipoPermissaoEnum.NEGADA, TipoPermissaoEnum.NEGADA.getDescricao());
			}

			if (byLdapAndEmail.getTipoPermissao().equals(TipoPermissaoEnum.ACEITA)) {
				return new StatusPermissao(TipoPermissaoEnum.ACEITA, TipoPermissaoEnum.ACEITA.getDescricao());
			}
		} else {
			throw new BusinessException("Usuario não encontrado");
		}

		return null;
	}


}
