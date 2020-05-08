package br.com.filmoteca.services.impl;

import static br.com.filmoteca.templates.FixtureCoreTemplates.VALID;
import static br.com.six2six.fixturefactory.Fixture.from;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.filmoteca.domains.Filme;
import br.com.filmoteca.domains.NivelCensura;
import br.com.filmoteca.domains.exception.BusinessLogicException;
import br.com.filmoteca.repositories.FilmeRepository;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RunWith(MockitoJUnitRunner.class)
public class FilmeServiceImplTest {

  @InjectMocks
  private FilmeServiceImpl filmeService;

  @Mock
  private FilmeRepository filmeRepository;

  @Mock
  private MessageSource messageSource;

  @BeforeClass
  public static synchronized void setup() {
    FixtureFactoryLoader.loadTemplates("br.com.filmoteca.templates");
  }

  @Test
  public void deveSalvarOFilme() {
    final Filme filme = from(Filme.class).gimme(VALID.name());

    filmeService.salvar(filme);

    verify(filmeRepository).save(eq(filme));
  }

  @Test(expected = BusinessLogicException.class)
  public void deveValidarNomeExistente() {
    final Filme filme = from(Filme.class).gimme(VALID.name());

    when(filmeRepository.existsByNome(filme.getNome())).thenReturn(true);

    filmeService.salvar(filme);

    verify(filmeRepository, never()).save(eq(filme));
  }

  @Test
  public void deveBuscarPorNivelCensura() {

    final List<Filme> filmes = from(Filme.class).gimme(10, VALID.name());
    final NivelCensura censurado = NivelCensura.CENSURADO;
    final PageRequest pageRequest = PageRequest.of(0, 20);

    when(filmeRepository.findByNivelCensura(censurado, pageRequest))
        .thenReturn(new PageImpl<>(filmes));

    final Page<Filme> page = filmeService.buscar(censurado, pageRequest);

    assertNotNull(page);
    assertEquals(page.getTotalElements(), filmes.size());

    verify(filmeRepository, never()).findAll(pageRequest);
  }

  @Test
  public void deveBuscarSemNivelCensura() {

    final List<Filme> filmes = from(Filme.class).gimme(10, VALID.name());
    final PageRequest pageRequest = PageRequest.of(0, 20);

    when(filmeRepository.findAll(pageRequest))
        .thenReturn(new PageImpl<>(filmes));

    final Page<Filme> page = filmeService.buscar(null, pageRequest);

    assertNotNull(page);
    assertEquals(page.getTotalElements(), filmes.size());

    verify(filmeRepository, never())
        .findByNivelCensura(any(NivelCensura.class), any(Pageable.class));
  }
}