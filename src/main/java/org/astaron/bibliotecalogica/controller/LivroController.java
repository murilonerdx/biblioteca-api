package org.astaron.bibliotecalogica.controller;

import org.astaron.bibliotecalogica.response.LivroResponse;
import org.astaron.bibliotecalogica.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/livro")
public class LivroController {

	@Autowired
	final LivroService livroService;

	public LivroController(LivroService livroService) {
		this.livroService = livroService;
	}

	@GetMapping()
	public List<LivroResponse> findall() {
		return livroService.findAll();
	}

	@GetMapping("/{nome}")
	public LivroResponse findByName(@PathVariable("nome") String nome) {
		return livroService.findByName(nome);
	}

}
