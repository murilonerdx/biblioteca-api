package org.astaron.bibliotecalogica.request;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.astaron.bibliotecalogica.model.CategoriaEnum;
import org.astaron.bibliotecalogica.model.Livro;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class LivroUploadRequest {
	private String nome;
	private String descricao;
	private CategoriaEnum categoria;
	private String linkDownload;
	private Long size;

	public Livro toModel(String id) {
		return new Livro(
				id, this.nome, this.descricao, this.categoria, linkDownload, size
		);
	}
}
