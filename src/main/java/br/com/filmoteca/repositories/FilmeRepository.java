package br.com.filmoteca.repositories;

import br.com.filmoteca.domains.Filme;
import br.com.filmoteca.domains.NivelCensura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository extends MongoRepository<Filme, String> {

  boolean existsByNome(String nome);

  Page<Filme> findByNivelCensura(NivelCensura nivelCensura, Pageable pageable);
}