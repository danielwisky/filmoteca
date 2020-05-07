package br.com.filmoteca.domains;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("filmes")
public class Filme {

  @Id
  private String id;
  @Indexed
  private String nome;
  private LocalDate dataLancamento;
  @Indexed
  private NivelCensura nivelCensura;
  private String direcao;
  private List<String> elenco;

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

  public NivelCensura getNivelCensura() {
    return nivelCensura;
  }

  public void setNivelCensura(NivelCensura nivelCensura) {
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