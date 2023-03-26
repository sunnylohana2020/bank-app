package com.coding.challenge.bankapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class BankAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(BankAppApplication.class, args);
  }
}
