package br.com.filmoteca.controllers;

import static br.com.filmoteca.templates.FixtureCoreTemplates.INVALIDO_TAMANHO_ELENCO;
import static br.com.filmoteca.templates.FixtureCoreTemplates.VALIDO;
import static br.com.six2six.fixturefactory.Fixture.from;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.filmoteca.controllers.resources.request.FilmeRequest;
import br.com.filmoteca.domains.Filme;
import br.com.filmoteca.domains.NivelCensura;
import br.com.filmoteca.services.FilmeService;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(FilmeController.class)
public class FilmeControllerTest {

  @MockBean
  private FilmeService filmeService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMVC;

  @BeforeClass
  public static synchronized void setup() {
    FixtureFactoryLoader.loadTemplates("br.com.filmoteca.templates");
  }

  @Test
  public void deveCriarUmFilme() throws Exception {

    final FilmeRequest filmeRequest = from(FilmeRequest.class).gimme(VALIDO.name());
    when(filmeService.salvar(any(Filme.class))).thenReturn(filmeRequest.toDomain());

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
  public void deveValidarCamposObrigatorios() throws Exception {
    mockMVC
        .perform(post("/api/v1/filmes")
            .contentType(APPLICATION_JSON)
            .content("{}"))
        .andExpect(status().isBadRequest())
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
        .andExpect(jsonPath("$.errors", hasItems("elenco: size must be between 1 and 10")));
  }

  @Test
  public void deveBuscarPorNivelCensura() throws Exception {

    final List<Filme> filmes = from(Filme.class).gimme(10, VALIDO.name());
    when(filmeService.buscar(eq(NivelCensura.CENSURADO), any(Pageable.class)))
        .thenReturn(new PageImpl<>(filmes));

    mockMVC
        .perform(get("/api/v1/filmes?nivelCensura=CENSURADO"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalElements", is(filmes.size())))
        .andExpect(jsonPath("$.totalPages", is(1)))
        .andExpect(jsonPath("$.pageSize", is(filmes.size())));
  }

  @Test
  public void deveBuscarSemNivelCensura() throws Exception {

    final List<Filme> filmes = from(Filme.class).gimme(10, VALIDO.name());
    when(filmeService.buscar(eq(null), any(Pageable.class)))
        .thenReturn(new PageImpl<>(filmes));

    mockMVC
        .perform(get("/api/v1/filmes"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.totalElements", is(filmes.size())))
        .andExpect(jsonPath("$.totalPages", is(1)))
        .andExpect(jsonPath("$.pageSize", is(filmes.size())));
  }

  @Test
  public void naoDeveRetornarQuandoFilmeNaoEncontrado() throws Exception {

    when(filmeService.buscar(eq(NivelCensura.CENSURADO), any(Pageable.class)))
        .thenReturn(new PageImpl<>(new ArrayList<>()));

    mockMVC
        .perform(get("/api/v1/filmes?nivelCensura=CENSURADO"))
        .andExpect(status().isNotFound());
  }
}