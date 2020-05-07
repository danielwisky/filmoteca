package br.com.filmoteca.repositories;

import br.com.filmoteca.domains.Filme;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository extends MongoRepository<Filme, String> {

}