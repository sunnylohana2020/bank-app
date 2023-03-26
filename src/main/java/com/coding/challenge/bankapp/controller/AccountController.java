package com.coding.challenge.bankapp.controller;

import com.coding.challenge.bankapp.constant.AccountType;
import com.coding.challenge.bankapp.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("accounts")
@Api(tags = {"Accounts and Transactions REST endpoints"})
public class AccountController {

  @Autowired private AccountService accountService;

  @PostMapping(path = "/add/{customerId}")
  @ApiOperation(
      value = "Add a new current account",
      notes = "Create an new current account for existing customer.")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal Server Error")
      })
  public ResponseEntity<Object> addCurrentAccount(
      @PathVariable UUID customerId, @RequestParam("initialCredit") Double initialCredit) {

    return accountService.addAccount(customerId, initialCredit, AccountType.CURRENT);
  }
}
