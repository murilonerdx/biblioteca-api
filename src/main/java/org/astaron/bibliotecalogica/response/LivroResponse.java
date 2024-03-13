package org.astaron.bibliotecalogica.response;

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
public class LivroResponse {
	private String id;
	private String nome;
	private String descricao;
	private CategoriaEnum categoria;

	public LivroResponse(Livro livro){
		this.id = livro.getId();
		this.nome = livro.getNome();
		this.descricao = livro.getDescricao();
		this.categoria = livro.getCategoria();
	}
}
