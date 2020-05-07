package br.com.filmoteca.services;

import br.com.filmoteca.domains.Filme;
import br.com.filmoteca.domains.NivelCensura;
import java.util.List;

public interface FilmeService {

  Filme salvar(Filme filme);

  List<Filme> buscar(NivelCensura nivelCensura);
}