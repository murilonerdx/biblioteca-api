package org.astaron.bibliotecalogica.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.astaron.bibliotecalogica.model.Livro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BibliotecaResponse {
	private String id;
	private String nome;
	private String descricao;
	private LocalDateTime updatedAt = LocalDateTime.now();
	private List<Livro> livros = new ArrayList<>();
}
