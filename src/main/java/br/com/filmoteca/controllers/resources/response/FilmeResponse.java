package br.com.filmoteca.controllers.resources.response;

import static java.util.Optional.ofNullable;

import br.com.filmoteca.domains.Filme;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(Include.NON_EMPTY)
public class FilmeResponse {

  private String id;
  private String nome;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataLancamento;
  private String nivelCensura;
  private String direcao;
  private List<String> elenco;

  public FilmeResponse(final Filme filme) {
    this.id = filme.getId();
    this.nome = filme.getNome();
    this.dataLancamento = filme.getDataLancamento();
    this.nivelCensura = ofNullable(filme.getNivelCensura())
        .map(Enum::name)
        .orElse(null);
    this.direcao = filme.getDirecao();
    this.elenco = filme.getElenco();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public LocalDate getDataLancamento() {
    return dataLancamento;
  }

  public void setDataLancamento(LocalDate dataLancamento) {
    this.dataLancamento = dataLancamento;
  }

  public String getNivelCensura() {
    return nivelCensura;
  }

  public void setNivelCensura(String nivelCensura) {
    this.nivelCensura = nivelCensura;
  }

  public String getDirecao() {
    return direcao;
  }

  public void setDirecao(String direcao) {
    this.direcao = direcao;
  }

  public List<String> getElenco() {
    return elenco;
  }

  public void setElenco(List<String> elenco) {
    this.elenco = elenco;
  }
}