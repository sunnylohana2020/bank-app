package com.coding.challenge.bankapp.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Getter
@Service
public class BankingException extends RuntimeException {

  private static final long serialVersionUID = 1786795678656845L;

  private HttpStatus httpStatus;
  private String message;

  protected BankingException() {
    super();
  }

  public BankingException(HttpStatus httpStatus, String message) {
    super(message);
    this.httpStatus = httpStatus;
    this.message = message;
  }

  public BankingException(HttpStatus httpStatus, String message, Exception e) {
    super(message, e);
    this.httpStatus = httpStatus;
    this.message = message;
  }

  public BankingException(HttpStatus httpStatus, String message, Throwable t) {
    super(message, t);
    this.httpStatus = httpStatus;
    this.message = message;
  }

  public static BankingException of(HttpStatus code, String message) {
    return new BankingException(code, message);
  }
}
