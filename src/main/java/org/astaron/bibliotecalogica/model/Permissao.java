package org.astaron.bibliotecalogica.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.astaron.bibliotecalogica.model.enums.TipoPermissaoEnum;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Permissao {
	private String id;
	private String ldap;
	private String codigoUso;
	private String email;
	private TipoPermissaoEnum tipoPermissao;
	private int qtdAcesso = 0;

	public Permissao(String id, String ldap, String codigoUso, String email, TipoPermissaoEnum tipoPermissao) {
		this.id = id;
		this.ldap = ldap;
		this.codigoUso = codigoUso;
		this.email = email;
		this.tipoPermissao = tipoPermissao;
	}
}
