package br.com.filmoteca.controllers;

import static br.com.filmoteca.templates.FixtureCoreTemplates.INVALIDO_TAMANHO_ELENCO;
import static br.com.filmoteca.templates.FixtureCoreTemplates.VALIDO;
import static br.com.filmoteca.templates.FixtureCoreTemplates.VALIDO_THOR;
import static br.com.six2six.fixturefactory.Fixture.from;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import br.com.filmoteca.controllers.resources.request.FilmeRequest;
import br.com.filmoteca.domains.Filme;
import br.com.filmoteca.domains.NivelCensura;
import br.com.filmoteca.support.IntegrationTestSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

public class FilmeControllerTest extends IntegrationTestSupport {

  @Autowired
  private WebApplicationContext webAppContext;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  private MockMvc mockMVC;

  @Before
  public void setUp() {
    mongoTemplate.remove(new Query(), Filme.class);
    mockMVC = webAppContextSetup(webAppContext).build();
  }

  @Test
  public void deveCriarUmFilme() throws Exception {
    final FilmeRequest filmeRequest = from(FilmeRequest.class).gimme(VALIDO.name());
    mockMVC
        .perform(post("/api/v1/filmes")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(filmeRequest)))
        .andExpect(status().isOk())
        .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.nome", is(filmeRequest.getNome())))
        .andExpect(jsonPath("$.direcao", is(filmeRequest.getDirecao())))
        .andExpect(jsonPath("$.nivelCensura", is(filmeRequest.getNivelCensura())));
  }

  @Test
  public void deveValidarNomeDuplicado() throws Exception {

    final Filme filme = from(Filme.class).gimme(VALIDO_THOR.name());
    mongoTemplate.save(filme);

    final FilmeRequest filmeRequest = from(FilmeRequest.class).gimme(VALIDO_THOR.name());
    mockMVC
        .perform(post("/api/v1/filmes")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(filmeRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors", hasSize(1)))
        .andExpect(jsonPath("$.errors",
            hasItems("O nome de filme `Thor: Ragnarok` já está sendo utilizado")));
  }

  @Test
  public void deveValidarCamposObrigatorios() throws Exception {
    mockMVC
        .perform(post("/api/v1/filmes")
            .contentType(APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors", hasSize(2)))
        .andExpect(jsonPath("$.errors", hasItems("elenco: must not be null")))
        .andExpect(jsonPath("$.errors", hasItems("nome: must not be blank")));
  }

  @Test
  public void deveValidarTamanhoMaximoElenco() throws Exception {

    final FilmeRequest filmeRequest =
        from(FilmeRequest.class).gimme(INVALIDO_TAMANHO_ELENCO.name());

    mockMVC
        .perform(post("/api/v1/filmes")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(filmeRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errors", hasSize(1)))
        .andExpect(jsonPath("$.errors", hasItems("elenco: size must be between 1 and 10")));
  }

  @Test
  public void deveBuscarPorNivelCensura() throws Exception {

    final List<Filme> filmes = from(Filme.class).gimme(10, VALIDO.name());
    filmes.forEach(mongoTemplate::save);

    final long totalFilmesCensurado = filmes
        .stream()
        .filter(filme -> NivelCensura.CENSURADO.equals(filme.getNivelCensura()))
        .count();

    mockMVC
        .perform(get("/api/v1/filmes?nivelCensura=CENSURADO"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.filmes", hasSize((int) totalFilmesCensurado)))
        .andExpect(jsonPath("$.totalElements", is((int) totalFilmesCensurado)))
        .andExpect(jsonPath("$.totalPages", is(1)))
        .andExpect(jsonPath("$.pageSize", is(20)));
  }

  @Test
  public void deveBuscarSemNivelCensura() throws Exception {

    final List<Filme> filmes = from(Filme.class).gimme(10, VALIDO.name());
    filmes.forEach(mongoTemplate::save);

    mockMVC
        .perform(get("/api/v1/filmes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.filmes", hasSize(10)))
        .andExpect(jsonPath("$.totalElements", is(filmes.size())))
        .andExpect(jsonPath("$.totalPages", is(1)))
        .andExpect(jsonPath("$.pageSize", is(20)));
  }

  @Test
  public void naoDeveRetornarQuandoFilmeNaoEncontrado() throws Exception {
    mockMVC
        .perform(get("/api/v1/filmes?nivelCensura=CENSURADO"))
        .andExpect(status().isNotFound());
  }
}