package org.astaron.bibliotecalogica.controller;

import org.astaron.bibliotecalogica.request.BibliotecaRequest;
import org.astaron.bibliotecalogica.response.BibliotecaResponse;
import org.astaron.bibliotecalogica.service.BibliotecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/biblioteca")
public class BibliotecaController {

	@Autowired
	final BibliotecaService bibliotecaService;

	public BibliotecaController(BibliotecaService bibliotecaService) {
		this.bibliotecaService = bibliotecaService;
	}

	@PostMapping()
	public BibliotecaResponse criarBiblioteca(@RequestBody BibliotecaRequest request) throws Exception {
		return bibliotecaService.criarBiblioteca(request);
	}

	@PostMapping("add/{livroIdm}/livro/{bibliotecaId}")
	public BibliotecaResponse criarBiblioteca(@PathVariable("livroIdm") String idLivro, @PathVariable("bibliotecaId") String bibliotecaId) throws Exception {
		return bibliotecaService.adicionarLivro(idLivro, bibliotecaId);
	}

	@PutMapping("rm/{livroIdm}/livro/{bibliotecaId}")
	public BibliotecaResponse retirarLivroBiblioteca(@PathVariable("livroIdm") String idLivro, @PathVariable("bibliotecaId") String bibliotecaId) throws Exception {
		return bibliotecaService.retirarLivro(idLivro, bibliotecaId);
	}

	@PutMapping("att/{bibliotecaId}")
	public BibliotecaResponse atualizarBiblioteca(@PathVariable("bibliotecaId") String bibliotecaId, @RequestBody BibliotecaRequest request) throws Exception {
		return bibliotecaService.atualizarBiblioteca(bibliotecaId, request);
	}

	@GetMapping("/{nome}")
	public BibliotecaResponse buscarPorNome(@PathVariable("nome") String nome) {
		return bibliotecaService.findByName(nome);
	}

	@GetMapping("livro/{nome}")
	public List<BibliotecaResponse> buscarBibliotecaPorLivro(@PathVariable("nome") String nomeDoLivro) {
		return bibliotecaService.findByLivrosNome(nomeDoLivro);
	}


	@GetMapping()
	public List<BibliotecaResponse> buscarTodos() {
		return bibliotecaService.findAll();
	}

}
