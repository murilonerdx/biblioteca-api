package org.astaron.bibliotecalogica.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.astaron.bibliotecalogica.model.Biblioteca;
import org.astaron.bibliotecalogica.model.Livro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BibliotecaRequest {
	private String nome;
	private String descricao;

	public Biblioteca toModel(String id){
		return new Biblioteca(id, this.nome, this.descricao);
	}
}
