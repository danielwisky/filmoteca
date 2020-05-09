package br.com.filmoteca.testcontainer;

import static br.com.filmoteca.templates.FixtureCoreTemplates.VALIDO;
import static br.com.filmoteca.templates.FixtureCoreTemplates.VALIDO_THOR;
import static br.com.six2six.fixturefactory.Fixture.from;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import br.com.filmoteca.testcontainer.support.TestContainerSupport;
import br.com.filmoteca.controllers.FilmeController;
import br.com.filmoteca.controllers.resources.request.FilmeRequest;
import br.com.filmoteca.controllers.resources.response.FilmeResponse;
import br.com.filmoteca.domains.Filme;
import br.com.filmoteca.domains.exception.BusinessLogicException;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CriarFilmeTest extends TestContainerSupport {

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

    final FilmeRequest filmeRequest = from(FilmeRequest.class).gimme(VALIDO.name());

    final ResponseEntity<FilmeResponse> response =
        filmeController.criarFilme(filmeRequest);

    final FilmeResponse filmeResponse = response.getBody();

    assertNotNull(filmeResponse);
    assertNotNull(filmeResponse.getId());
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }

  @Test(expected = BusinessLogicException.class)
  public void deveValidarFilmeNomeExistente() {

    final Filme filme = from(Filme.class).gimme(VALIDO_THOR.name());
    mongoTemplate.save(filme);

    final FilmeRequest filmeRequest = from(FilmeRequest.class).gimme(VALIDO_THOR.name());

    filmeController.criarFilme(filmeRequest);
  }
}