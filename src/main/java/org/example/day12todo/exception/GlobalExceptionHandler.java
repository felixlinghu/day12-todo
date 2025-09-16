package org.example.day12todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(InvalidContextException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public InvalidContextException handleInvalidContextException(InvalidContextException e) {
    return new InvalidContextException(e.getMessage());
  }

  @ExceptionHandler(InvalidIdException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public InvalidIdException handleInvalidContextException(InvalidIdException e) {
    return new InvalidIdException(e.getMessage());
  }
}
