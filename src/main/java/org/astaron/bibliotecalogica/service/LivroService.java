package org.astaron.bibliotecalogica.service;

import org.astaron.bibliotecalogica.model.Livro;
import org.astaron.bibliotecalogica.repository.LivroRepository;
import org.astaron.bibliotecalogica.request.LivroUploadRequest;
import org.astaron.bibliotecalogica.response.LivroResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {

	private final LivroRepository livroRepository;

	public LivroService(LivroRepository livroRepository) {
		this.livroRepository = livroRepository;
	}

	public Livro saveBook(LivroUploadRequest livroUploadRequest) {
		return livroRepository.save(livroUploadRequest.toModel(null));
	}

	public List<LivroResponse> findAll(){
		return livroRepository.findAll().stream().map(Livro::toDTO).collect(Collectors.toList());
	}

	public Optional<Livro> findById(String id){
		return livroRepository.findById(id);
	}

	public LivroResponse findByName(String nome) {
		return livroRepository.findByNome(nome).toDTO();
	}
}
