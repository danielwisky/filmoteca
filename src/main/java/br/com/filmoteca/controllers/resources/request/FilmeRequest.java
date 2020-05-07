package br.com.filmoteca.controllers.resources.request;

import static java.util.Optional.ofNullable;

import br.com.filmoteca.domains.Filme;
import br.com.filmoteca.domains.NivelCensura;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class FilmeRequest {

  @NotBlank
  private String nome;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dataLancamento;
  @Pattern(regexp = "CENSURADO|SEM_CENSURA")
  private String nivelCensura;
  private String direcao;
  @NotNull
  @Size(min = 1, max = 10)
  private List<String> elenco;

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

  public Filme toDomain() {
    final Filme filme = new Filme();
    filme.setNome(this.nome);
    filme.setDataLancamento(this.dataLancamento);
    filme.setNivelCensura(ofNullable(this.nivelCensura)
        .map(NivelCensura::valueOf)
        .orElse(null));
    filme.setDirecao(this.direcao);
    filme.setElenco(this.elenco);
    return filme;
  }
}