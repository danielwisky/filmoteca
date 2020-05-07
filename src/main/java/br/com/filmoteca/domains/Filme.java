package br.com.filmoteca.domains;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("filmes")
public class Filme {

  @Id
  private String id;
  private String nome;
  private LocalDate dataLancamento;
  private NivelCensura nivelCensura;
  private String direcao;
  private List<String> elenco;
}