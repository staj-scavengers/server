package dev.staj.scavengr.exception;

import java.util.NoSuchElementException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
  public void notFound() {}

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid request content or parameters")
  public void badRequest() {}

  // TODO add an exception handler for a duplicate value.
  @ExceptionHandler(DuplicateKeyException.class)
  @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Duplicate key value")
  public void duplicateValue(){}

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not processed due to null value")
  public void nullValue() {}


  @ExceptionHandler(MissingPathVariableException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "a URI element is missing or invalid")
  public void missingPath() {}

  @ExceptionHandler(NullPointerException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "a required field is missing")
  public void nullPointer() {}

}
