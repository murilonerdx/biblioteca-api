package org.astaron.bibliotecalogica.repository;

import org.astaron.bibliotecalogica.model.Biblioteca;
import org.astaron.bibliotecalogica.model.Permissao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissaoRepository extends MongoRepository<Permissao, String> {
	Permissao findByLdapAndEmail(String ldap, String email);
	Boolean existsByLdapAndEmailAndCodigoUso(String ldap, String email, String codigoUso);
	Boolean existsByCodigoUso(String codigoUso);
	Permissao findByCodigoUso(String codigoUso);
}
