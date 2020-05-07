package br.com.filmoteca.controllers;

import static org.springframework.http.HttpStatus.OK;

import br.com.filmoteca.controllers.resources.request.FilmeRequest;
import br.com.filmoteca.controllers.resources.response.FilmeResponse;
import br.com.filmoteca.controllers.resources.response.PageFilmeResponse;
import br.com.filmoteca.domains.Filme;
import br.com.filmoteca.domains.NivelCensura;
import br.com.filmoteca.domains.exception.NotFoundException;
import br.com.filmoteca.services.FilmeService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/filmes")
public class FilmeController {

  private FilmeService filmeService;

  public FilmeController(final FilmeService filmeService) {
    this.filmeService = filmeService;
  }

  @PostMapping
  @ResponseStatus(OK)
  @ApiOperation(value = "Criar um filme")
  public ResponseEntity<FilmeResponse> criarFilme(
      @RequestBody @Valid final FilmeRequest filmeRequest) {

    return ResponseEntity.ok(new FilmeResponse(
        filmeService.salvar(filmeRequest.toDomain())));
  }

  @GetMapping
  @ResponseStatus(OK)
  @ApiOperation(value = "Buscar lista de filmes")
  public ResponseEntity<PageFilmeResponse> buscarFilmes(
      @RequestParam(required = false) final NivelCensura nivelCensura,
      @RequestParam(defaultValue = "0") final Integer pageNumber,
      @RequestParam(defaultValue = "20") final Integer pageSize) {

    final Page<Filme> page =
        filmeService.buscar(nivelCensura, PageRequest.of(pageNumber, pageSize));

    if (page.isEmpty()) {
      throw new NotFoundException();
    }

    return ResponseEntity.ok(new PageFilmeResponse(page));
  }
}