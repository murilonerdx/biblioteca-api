package org.astaron.bibliotecalogica.model.enums;

import lombok.Getter;

@Getter
public enum TipoPermissaoEnum {
	ACEITA("Solicitação aprovada"), NEGADA("Você não tem permissão para acessar, consulte o ADM"), EM_ANDAMENTO("Sua solicitação ainda está em andamento");

	String descricao;

	TipoPermissaoEnum(String descricao) {
		this.descricao = descricao;
	}
}
