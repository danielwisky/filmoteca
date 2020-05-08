package br.com.filmoteca.container;

import static br.com.filmoteca.templates.FixtureCoreTemplates.VALID;
import static br.com.filmoteca.templates.FixtureCoreTemplates.VALID_CENSURADO;
import static br.com.filmoteca.templates.FixtureCoreTemplates.VALID_SEM_CENSURA;
import static br.com.filmoteca.templates.FixtureCoreTemplates.VALID_THOR;
import static br.com.six2six.fixturefactory.Fixture.from;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import br.com.filmoteca.container.support.TestContainerSupport;
import br.com.filmoteca.controllers.FilmeController;
import br.com.filmoteca.controllers.resources.request.FilmeRequest;
import br.com.filmoteca.controllers.resources.response.FilmeResponse;
import br.com.filmoteca.controllers.resources.response.PageFilmeResponse;
import br.com.filmoteca.domains.Filme;
import br.com.filmoteca.domains.NivelCensura;
import br.com.filmoteca.domains.exception.BusinessLogicException;
import br.com.filmoteca.domains.exception.NotFoundException;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FilmeControllerTest extends TestContainerSupport {

  @Autowired
  private FilmeController filmeController;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Before
  public void limparBase() {
    mongoTemplate.remove(new Query(), Filme.class);
  }

  @BeforeClass
  public static synchronized void setup() {
    FixtureFactoryLoader.loadTemplates("br.com.filmoteca.templates");
  }

  @Test
  public void deveCriarUmFilme() {

    final FilmeRequest filmeRequest = from(FilmeRequest.class).gimme(VALID.name());

    final ResponseEntity<FilmeResponse> response =
        filmeController.criarFilme(filmeRequest);

    final FilmeResponse filmeResponse = response.getBody();

    assertNotNull(filmeResponse);
    assertNotNull(filmeResponse.getId());
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test(expected = BusinessLogicException.class)
  public void deveValidarFilmeNomeExistente() {

    final Filme filme = from(Filme.class).gimme(VALID_THOR.name());
    mongoTemplate.save(filme);

    final FilmeRequest filmeRequest = from(FilmeRequest.class).gimme(VALID_THOR.name());

    filmeController.criarFilme(filmeRequest);
  }

  public void deveBuscarPorNivelCensura() {

    final Filme filmeCensurado = from(Filme.class).gimme(VALID_CENSURADO.name());
    final Filme filmeSemCensura = from(Filme.class).gimme(VALID_SEM_CENSURA.name());
    mongoTemplate.save(filmeCensurado);
    mongoTemplate.save(filmeSemCensura);

    final ResponseEntity<PageFilmeResponse> response =
        filmeController.buscarFilmes(NivelCensura.CENSURADO, 0, 10);

    assertNotNull(response);
    assertNotNull(response.getBody().getFilmes());
    assertEquals(response.getBody().getTotalElements().longValue(), 1L);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test
  public void deveBuscarSemNivelCensura() {

    final Filme filme = from(Filme.class).gimme(VALID_THOR.name());
    mongoTemplate.save(filme);

    final ResponseEntity<PageFilmeResponse> response =
        filmeController.buscarFilmes(null, 0, 10);

    assertNotNull(response);
    assertNotNull(response.getBody().getFilmes());
    assertEquals(response.getBody().getTotalElements().longValue(), 1L);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test(expected = NotFoundException.class)
  public void naoDeveRetornarQuandoFilmeNaoEncontrado() {
    filmeController.buscarFilmes(NivelCensura.CENSURADO, 0, 10);
  }
}