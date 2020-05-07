package br.com.filmoteca.services;

import br.com.filmoteca.domains.Filme;
import br.com.filmoteca.domains.NivelCensura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilmeService {

  Filme salvar(Filme filme);

  Page<Filme> buscar(NivelCensura nivelCensura, Pageable pageable);
}