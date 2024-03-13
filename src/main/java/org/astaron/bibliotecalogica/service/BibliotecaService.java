package org.astaron.bibliotecalogica.service;

import org.astaron.bibliotecalogica.model.Biblioteca;
import org.astaron.bibliotecalogica.model.Livro;
import org.astaron.bibliotecalogica.repository.BibliotecaRepository;
import org.astaron.bibliotecalogica.request.BibliotecaRequest;
import org.astaron.bibliotecalogica.response.BibliotecaResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BibliotecaService {

	private final BibliotecaRepository bibliotecaRepository;
	private final LivroService livroService;

	public BibliotecaService(BibliotecaRepository bibliotecaRepository, LivroService livroService) {
		this.bibliotecaRepository = bibliotecaRepository;
		this.livroService = livroService;
	}

	public List<BibliotecaResponse> findAll() {
		return bibliotecaRepository.findAll().stream().map(Biblioteca::toResponse).collect(Collectors.toList());
	}

	public BibliotecaResponse findByName(String nome) {
		return bibliotecaRepository.findByNome(nome).toResponse();
	}

	public List<BibliotecaResponse> findByLivrosNome(String nomeLivro) {
		return bibliotecaRepository.findByLivrosNome(nomeLivro).stream().map(Biblioteca::toResponse).collect(Collectors.toList());
	}

	public BibliotecaResponse criarBiblioteca(BibliotecaRequest bibliotecaRequest) throws Exception {
		Biblioteca model = bibliotecaRequest.toModel(null);
		return bibliotecaRepository.save(model).toResponse();
	}

	public BibliotecaResponse adicionarLivro(String idLivro, String idBiblioteca) throws Exception {
		Biblioteca biblioteca =
				bibliotecaRepository.findById(idBiblioteca).orElseThrow(() -> new Exception("Não foi possivel encontrar a biblioteca com id " + idBiblioteca));
		Livro livro = livroService.findById(idLivro).orElseThrow(() -> new Exception("ID livro não encontrado "));


		if(biblioteca.getLivros().stream().noneMatch(it -> Objects.equals(it, livro))){
			biblioteca.getLivros().add(livro);
		}else{
			throw new Exception("O livro já está dentro da biblioteca");
		}

		return bibliotecaRepository.save(biblioteca).toResponse();
	}

	public BibliotecaResponse retirarLivro(String idLivro, String idBiblioteca) throws Exception {
		Biblioteca biblioteca =
				bibliotecaRepository.findById(idBiblioteca).orElseThrow(() -> new Exception("Não foi possivel encontrar a biblioteca com id " + idBiblioteca));
		Livro livro = livroService.findById(idLivro).orElseThrow(() -> new Exception("ID livro não encontrado "));
		biblioteca.getLivros().remove(livro);

		return bibliotecaRepository.save(biblioteca).toResponse();
	}

	public BibliotecaResponse atualizarBiblioteca(String idBiblioteca, BibliotecaRequest bibliotecaRequest) throws Exception {
		Biblioteca biblioteca =
				bibliotecaRepository.findById(idBiblioteca).orElseThrow(() -> new Exception("Não foi possivel encontrar a biblioteca com id " + idBiblioteca));
		biblioteca.setDescricao(bibliotecaRequest.getDescricao());
		biblioteca.setNome(bibliotecaRequest.getNome());
		biblioteca.setUpdatedAt(LocalDateTime.now());

		return bibliotecaRepository.save(biblioteca).toResponse();
	}


}
