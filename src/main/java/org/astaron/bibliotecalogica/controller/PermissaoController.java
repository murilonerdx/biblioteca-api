package org.astaron.bibliotecalogica.controller;


import org.astaron.bibliotecalogica.model.enums.TipoPermissaoEnum;
import org.astaron.bibliotecalogica.response.NovoAcessoResponse;
import org.astaron.bibliotecalogica.response.StatusPermissao;
import org.astaron.bibliotecalogica.service.PermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/permissao")
public class PermissaoController {
	@Autowired
	private final PermissaoService permissaoService;

	public PermissaoController(PermissaoService permissaoService) {
		this.permissaoService = permissaoService;
	}

	@PostMapping
	public String gerarPermissao(@RequestParam String codigoGerador,
								 @RequestParam String ldap, @RequestParam String email) throws Exception {
		return permissaoService.gerarPermissao(codigoGerador, ldap, email);
	}

	@PostMapping("/renovar/acesso")
	public NovoAcessoResponse renovarAcesso(@RequestParam String codigoGerador) throws Exception {
		return permissaoService.renovarAcess(codigoGerador);
	}

	@GetMapping("verificar/acesso-restante")
	public int verificarQuantidadeDeAcesso(@RequestParam String codigoGerador) throws Exception {
		return permissaoService.verificarQuantidadeDeAcessoRestante(codigoGerador);
	}

	@PostMapping("codigo-uso/solicitar")
	public StatusPermissao gerarPermissao(
								 @RequestParam String ldap, @RequestParam String email) throws Exception {
		return permissaoService.solicitarPermissao(ldap, email);
	}

	@PostMapping("adm/dar-permissao")
	public StatusPermissao gerarPermissao(
			@RequestParam String ldap, @RequestParam String email,@RequestParam String codigoValidador, @RequestParam TipoPermissaoEnum tipoPermissaoEnum) throws Exception {
		return permissaoService.darPermissao(ldap, email, codigoValidador, tipoPermissaoEnum.name());
	}

	@GetMapping("verificar-permissao")
	public StatusPermissao verificarStatusPermissao(
			@RequestParam String ldap, @RequestParam String email) throws Exception {
		return permissaoService.verificarStatusPermissao(ldap, email);
	}
}
