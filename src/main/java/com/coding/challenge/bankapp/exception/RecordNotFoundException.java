package com.coding.challenge.bankapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends BankingException {
  private static final long serialVersionUID = -5218143265247846948L;

  public RecordNotFoundException(String message) {
    super(HttpStatus.NOT_FOUND, message);
  }
}
