package br.com.filmoteca.services.impl;

import br.com.filmoteca.domains.Filme;
import br.com.filmoteca.domains.NivelCensura;
import br.com.filmoteca.services.FilmeService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FilmeServiceImpl implements FilmeService {

  @Override
  public Filme salvar(Filme filme) {
    return null;
  }

  @Override
  public List<Filme> buscar(NivelCensura nivelCensura) {
    return null;
  }
}