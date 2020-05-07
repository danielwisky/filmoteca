package br.com.filmoteca.domains.exception;

public class BusinessLogicException extends RuntimeException {

  public BusinessLogicException(String message) {
    super(message);
  }
}