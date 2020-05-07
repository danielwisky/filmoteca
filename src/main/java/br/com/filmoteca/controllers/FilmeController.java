package br.com.filmoteca.controllers;

import static org.springframework.http.HttpStatus.OK;

import br.com.filmoteca.controllers.resources.request.FilmeRequest;
import br.com.filmoteca.controllers.resources.response.FilmeResponse;
import br.com.filmoteca.domains.Filme;
import br.com.filmoteca.services.FilmeService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    final Filme filme = filmeService.salvar(filmeRequest.toDomain());
    return ResponseEntity.ok(new FilmeResponse(filme));
  }

  @GetMapping
  @ResponseStatus(OK)
  @ApiOperation(value = "Buscar lista de filmes")
  public ResponseEntity buscarFilmes() {
    return ResponseEntity.ok().build();
  }
}