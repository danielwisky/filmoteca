package br.com.filmoteca.controllers;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import br.com.filmoteca.controllers.resources.response.ErrorResponse;
import br.com.filmoteca.domains.exception.BusinessLogicException;
import br.com.filmoteca.domains.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

  private static final String CONTENT_TYPE = "Content-Type";
  private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";

  @ExceptionHandler(NotFoundException.class)
  public HttpEntity<ErrorResponse> handleNotFoundException(final NotFoundException ex) {
    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
    return new ResponseEntity<>(createMessage(ex), responseHeaders, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({IllegalArgumentException.class, BusinessLogicException.class})
  public HttpEntity<ErrorResponse> handleIllegalArgumentException(
      final RuntimeException ex) {
    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
    return new ResponseEntity<>(createMessage(ex), responseHeaders, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public HttpEntity<ErrorResponse> handlerValidationException(
      final MethodArgumentNotValidException ex) {

    final BindingResult bindingResult = ex.getBindingResult();
    final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    final ErrorResponse message = this.processFieldErrors(fieldErrors);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);

    return new ResponseEntity<>(message, responseHeaders, BAD_REQUEST);
  }

  @ExceptionHandler(Throwable.class)
  public HttpEntity<ErrorResponse> handleThrowable(final Throwable ex) {
    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);

    return new ResponseEntity<>(createMessage(ex), responseHeaders, INTERNAL_SERVER_ERROR);
  }

  private ErrorResponse createMessage(final Throwable ex) {
    ErrorResponse message = null;
    if (StringUtils.isNotBlank(ex.getMessage())) {
      message = new ErrorResponse(List.of(ex.getMessage()));
    }
    return message;
  }

  private ErrorResponse processFieldErrors(final List<FieldError> fieldErrors) {
    final List<String> errors = fieldErrors.stream()
        .map(a -> String.format("%s: %s", a.getField(), a.getDefaultMessage()))
        .collect(Collectors.toList());
    return new ErrorResponse(errors);
  }
}