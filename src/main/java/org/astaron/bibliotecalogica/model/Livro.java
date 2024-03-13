package org.astaron.bibliotecalogica.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.astaron.bibliotecalogica.response.LivroResponse;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.ZoneId;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Livro {
	@Id
	private String id;
	private String nome;
	private String descricao;
	private String linkDownload;
	private CategoriaEnum categoria;
	private Long size;

	public Livro(String id, String nome, String descricao, CategoriaEnum categoria) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.categoria = categoria;
	}

	private LocalDate updatedAt = LocalDate.now(ZoneId.of("America/Sao_Paulo"));

	public Livro(String id, String nome, String descricao, CategoriaEnum categoria, String linkDownload, Long size) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.categoria = categoria;
		this.linkDownload = linkDownload;
		this.size = size;
	}


	public LivroResponse toDTO() {
		return new LivroResponse(
				this.id,
				this.nome, this.descricao, this.categoria
		);
	}
}
