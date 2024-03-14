package org.astaron.bibliotecalogica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.astaron.bibliotecalogica.response.BibliotecaResponse;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Biblioteca {
	@Id
	private String id;
	private String nome;
	private String descricao;
	private LocalDateTime updatedAt = LocalDateTime.now();
	private List<Livro> livros = new LinkedList<>();
	private String ldap;

	public Biblioteca(String id, String nome, String descricao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}

	public BibliotecaResponse toResponse() {
		return new BibliotecaResponse(
				this.id, this.nome, this.descricao, this.updatedAt, this.livros, this.ldap
		);
	}
}
