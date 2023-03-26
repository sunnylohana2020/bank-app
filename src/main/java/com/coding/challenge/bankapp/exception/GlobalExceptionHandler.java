package com.coding.challenge.bankapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BankingException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(BankingException ex) {
    ErrorResponse response = new ErrorResponse(ex.getMessage(), ex.getHttpStatus());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  public static class ErrorResponse {
    private final String message;
    private final HttpStatus httpStatus;

    public ErrorResponse(String message, HttpStatus httpStatus) {
      this.message = message;
      this.httpStatus = httpStatus;
    }

    public String getMessage() {
      return message;
    }

    public HttpStatus getHttpStatus() {
      return httpStatus;
    }
  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ErrorResponse> defaultErrorHandler(
      HttpServletRequest req, HttpServletResponse response, Exception e) {
    return new ResponseEntity<>(
        new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
