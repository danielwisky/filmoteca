package br.com.filmoteca.services.impl;

import static java.util.Objects.isNull;
import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

import br.com.filmoteca.domains.Filme;
import br.com.filmoteca.domains.NivelCensura;
import br.com.filmoteca.domains.exception.BusinessLogicException;
import br.com.filmoteca.repositories.FilmeRepository;
import br.com.filmoteca.services.FilmeService;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FilmeServiceImpl implements FilmeService {

  private FilmeRepository filmeRepository;
  private MessageSource messageSource;

  public FilmeServiceImpl(
      final FilmeRepository filmeRepository, final MessageSource messageSource) {
    this.filmeRepository = filmeRepository;
    this.messageSource = messageSource;
  }

  @Override
  public Filme salvar(final Filme filme) {
    validarCadastro(filme);
    return filmeRepository.save(filme);
  }

  @Override
  public Page<Filme> buscar(final NivelCensura nivelCensura, final Pageable pageable) {
    return isNull(nivelCensura)
        ? filmeRepository.findAll(pageable)
        : filmeRepository.findByNivelCensura(nivelCensura, pageable);
  }

  private void validarCadastro(final Filme filme) {
    if (filmeRepository.existsByNome(filme.getNome())) {
      throw new BusinessLogicException(
          messageSource.getMessage("nome.duplicado", new Object[]{filme.getNome()}, getLocale()));
    }
  }
}