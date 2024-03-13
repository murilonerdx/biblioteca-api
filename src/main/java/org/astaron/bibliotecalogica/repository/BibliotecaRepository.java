package org.astaron.bibliotecalogica.repository;

import org.astaron.bibliotecalogica.model.Biblioteca;
import org.astaron.bibliotecalogica.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BibliotecaRepository extends MongoRepository<Biblioteca, String> {
	Biblioteca findByNome(String nome);
	List<Biblioteca> findByLivrosNome(String nome);
}
