package org.astaron.bibliotecalogica.repository;

import org.astaron.bibliotecalogica.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends MongoRepository<Livro, String> {
	Livro findByNome(String nome);
}
