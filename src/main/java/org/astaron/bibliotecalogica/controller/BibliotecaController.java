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
	public BibliotecaResponse criarBiblioteca(@RequestBody BibliotecaRequest request, @RequestParam String codigoUso) throws Exception {
		return bibliotecaService.criarBiblioteca(request, codigoUso);
	}

	@PostMapping("adicionar-livro/{livroIdm}/{bibliotecaId}")
	public BibliotecaResponse criarBiblioteca(@PathVariable("livroIdm") String idLivro, @PathVariable("bibliotecaId") String bibliotecaId, @RequestParam String codigoUso) throws Exception {
		return bibliotecaService.adicionarLivro(idLivro, bibliotecaId, codigoUso);
	}

	@PutMapping("remover-livro/{livroIdm}/{bibliotecaId}")
	public BibliotecaResponse retirarLivroBiblioteca(@PathVariable("livroIdm") String idLivro, @PathVariable("bibliotecaId") String bibliotecaId, @RequestParam String codigoUso) throws Exception {
		return bibliotecaService.retirarLivro(idLivro, bibliotecaId, codigoUso);
	}

	@PutMapping("atualizar/{bibliotecaId}")
	public BibliotecaResponse atualizarBiblioteca(@PathVariable("bibliotecaId") String bibliotecaId, @RequestBody BibliotecaRequest request, @RequestParam String ldap,@RequestParam String email, @RequestParam String codigoUso) throws Exception {
		return bibliotecaService.atualizarBiblioteca(bibliotecaId, request, codigoUso);
	}

	@GetMapping("/{nome}")
	public BibliotecaResponse buscarPorNome(@PathVariable("nome") String nome, @RequestParam String ldap,@RequestParam String email, @RequestParam String codigoUso) throws Exception {
		return bibliotecaService.findByName(nome, codigoUso);
	}

	@GetMapping("buscar-livro/{nome}")
	public List<BibliotecaResponse> buscarBibliotecaPorLivro(@PathVariable("nome") String nomeDoLivro, @RequestParam String codigoUso) throws Exception {
		return bibliotecaService.findByLivrosNome(nomeDoLivro, codigoUso);
	}


	@GetMapping()
	public List<BibliotecaResponse> buscarTodos(@RequestParam String codigoUso) throws Exception {
		return bibliotecaService.findAll(codigoUso);
	}

}
