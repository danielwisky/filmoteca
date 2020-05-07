package br.com.filmoteca.services.impl;

import static br.com.filmoteca.templates.FixtureCoreTemplates.VALID;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.filmoteca.domains.Filme;
import br.com.filmoteca.domains.exception.BusinessLogicException;
import br.com.filmoteca.repositories.FilmeRepository;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

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
    Filme filme = Fixture.from(Filme.class).gimme(VALID.name());

    filmeService.salvar(filme);

    verify(filmeRepository).save(eq(filme));
  }

  @Test(expected = BusinessLogicException.class)
  public void deveValidarNomeExistente() {
    Filme filme = Fixture.from(Filme.class).gimme(VALID.name());

    when(filmeRepository.existsByNome(filme.getNome())).thenReturn(true);

    filmeService.salvar(filme);

    verify(filmeRepository, never()).save(eq(filme));
  }

  public void deveBuscarPorNivelCensura() {

  }

  public void deveBuscarSemNivelCensura() {

  }
}