package br.com.filmoteca.controllers.resources.response;

import br.com.filmoteca.domains.Filme;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

public class PageFilmeResponse {

  private List<FilmeResponse> filmes;
  private Integer pageNumber;
  private Integer pageSize;
  private Integer totalPages;
  private Long totalElements;

  public PageFilmeResponse(final Page<Filme> pageNumber) {
    this.filmes = pageNumber
        .get()
        .map(FilmeResponse::new)
        .collect(Collectors.toList());
    this.pageNumber = pageNumber.getNumber();
    this.pageSize = pageNumber.getSize();
    this.totalPages = pageNumber.getTotalPages();
    this.totalElements = pageNumber.getTotalElements();
  }

  public List<FilmeResponse> getFilmes() {
    return filmes;
  }

  public void setFilmes(List<FilmeResponse> filmes) {
    this.filmes = filmes;
  }

  public Integer getPageNumber() {
    return pageNumber;
  }

  public void setPageNumber(Integer pageNumber) {
    this.pageNumber = pageNumber;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public Long getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(Long totalElements) {
    this.totalElements = totalElements;
  }
}