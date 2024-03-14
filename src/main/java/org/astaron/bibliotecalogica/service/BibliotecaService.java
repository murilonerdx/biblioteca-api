package org.astaron.bibliotecalogica.service;

import org.astaron.bibliotecalogica.handler.BusinessException;
import org.astaron.bibliotecalogica.model.Biblioteca;
import org.astaron.bibliotecalogica.model.Livro;
import org.astaron.bibliotecalogica.model.Permissao;
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
	private final PermissaoService permissaoService;

	public BibliotecaService(BibliotecaRepository bibliotecaRepository, LivroService livroService, PermissaoService permissaoService) {
		this.bibliotecaRepository = bibliotecaRepository;
		this.livroService = livroService;
		this.permissaoService = permissaoService;
	}

	public List<BibliotecaResponse> findAll(String codigoUso) throws Exception {
		if (permissaoService.acessar(codigoUso)) {
			return bibliotecaRepository.findAll().stream().map(Biblioteca::toResponse).collect(Collectors.toList());
		}
		throw new BusinessException("Erro de permissão");
	}

	public BibliotecaResponse findByName(String nome, String codigoUso) throws Exception {
		if (permissaoService.acessar(codigoUso)) {
			return bibliotecaRepository.findByNome(nome).toResponse();
		}
		throw new BusinessException("Erro de permissão");
	}

	public List<BibliotecaResponse> findByLivrosNome(String nomeLivro, String codigoUso) throws Exception {
		if (permissaoService.acessar(codigoUso)) {
			return bibliotecaRepository.findByLivrosNome(nomeLivro).stream().map(Biblioteca::toResponse).collect(Collectors.toList());
		}
		throw new BusinessException("Erro de permissão");
	}

	public BibliotecaResponse criarBiblioteca(BibliotecaRequest bibliotecaRequest, String codigoUso) throws Exception {
		if (permissaoService.acessar(codigoUso)) {
			Biblioteca model = bibliotecaRequest.toModel(null);
			Permissao byCodigoUso = permissaoService.findByCodigoUso(codigoUso);

			if(model.getLdap() == null && byCodigoUso != null){
				model.setLdap(byCodigoUso.getLdap());
			}
			return bibliotecaRepository.save(model).toResponse();
		}
		throw new BusinessException("Erro de permissão");
	}

	public BibliotecaResponse adicionarLivro(String idLivro, String idBiblioteca, String codigoUso) throws Exception {
		if (permissaoService.acessar(codigoUso)) {
			Biblioteca biblioteca =
					bibliotecaRepository.findById(idBiblioteca).orElseThrow(() -> new BusinessException("Não foi possivel encontrar a biblioteca com id " + idBiblioteca));
			Livro livro = livroService.findById(idLivro).orElseThrow(() -> new BusinessException("ID livro não encontrado "));

			Permissao byCodigoUso = permissaoService.findByCodigoUso(codigoUso);
			if(byCodigoUso != null && biblioteca.getLdap().equals(byCodigoUso.getLdap())){
				if (biblioteca.getLivros().stream().noneMatch(it -> Objects.equals(it, livro))) {
					biblioteca.getLivros().add(livro);
				} else {
					throw new BusinessException("O livro já está dentro da biblioteca");
				}

				return bibliotecaRepository.save(biblioteca).toResponse();
			}else{
				throw new BusinessException("Erro você não tem permissão para essa ação!");
			}

		}
		throw new BusinessException("Erro de permissão");
	}

	public BibliotecaResponse retirarLivro(String idLivro, String idBiblioteca, String codigoUso) throws Exception {
		if (permissaoService.acessar(codigoUso)) {
			Biblioteca biblioteca =
					bibliotecaRepository.findById(idBiblioteca).orElseThrow(() -> new BusinessException("Não foi possivel encontrar a biblioteca com id " + idBiblioteca));
			Livro livro = livroService.findById(idLivro).orElseThrow(() -> new BusinessException("ID livro não encontrado "));

			Permissao byCodigoUso = permissaoService.findByCodigoUso(codigoUso);
			if(byCodigoUso != null && biblioteca.getLdap().equals(byCodigoUso.getLdap())){
				biblioteca.getLivros().remove(livro);
			}else{
				throw new BusinessException("Erro você não tem permissão para essa ação!");
			}

			return bibliotecaRepository.save(biblioteca).toResponse();
		}
		throw new BusinessException("Erro de permissão");
	}

	public BibliotecaResponse atualizarBiblioteca(String idBiblioteca, BibliotecaRequest bibliotecaRequest, String codigoUso) throws Exception {
		if (permissaoService.acessar(codigoUso)) {
			Biblioteca biblioteca =
					bibliotecaRepository.findById(idBiblioteca).orElseThrow(() -> new BusinessException("Não foi possivel encontrar a biblioteca com id " + idBiblioteca));

			Permissao byCodigoUso = permissaoService.findByCodigoUso(codigoUso);
			if(byCodigoUso != null && biblioteca.getLdap().equals(byCodigoUso.getLdap())){
				biblioteca.setDescricao(bibliotecaRequest.getDescricao());
				biblioteca.setNome(bibliotecaRequest.getNome());
				biblioteca.setUpdatedAt(LocalDateTime.now());

				return bibliotecaRepository.save(biblioteca).toResponse();
			}else{
				throw new BusinessException("Erro você não tem permissão para essa ação!");
			}
		}
		throw new BusinessException("Erro de permissão");
	}


}
