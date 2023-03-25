package com.coding.challenge.bankapp.service;

import com.coding.challenge.bankapp.constant.AccountType;
import com.coding.challenge.bankapp.constant.Status;
import com.coding.challenge.bankapp.constant.TransactionType;
import com.coding.challenge.bankapp.entity.Account;
import com.coding.challenge.bankapp.entity.Customer;
import com.coding.challenge.bankapp.exception.RecordNotFoundException;
import com.coding.challenge.bankapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {
  @Autowired private AccountRepository accountRepository;

  @Autowired private CustomerService customerService;

  @Autowired private TransactionService transactionService;

  public ResponseEntity<Object> addAccount(
      UUID customerId, Double initialCredit, AccountType accountType) {
    Customer customer = customerService.findIfExists(customerId);
    Account account =
        Account.builder()
            .accountNumber(UUID.randomUUID())
            .accountType(accountType)
            .customer(customer)
            .accountStatus(Status.ACTIVE)
            .build();
    accountRepository.save(account);
    if (initialCredit != null && initialCredit > 0) {
      transactionService.addTransaction(
          account.getAccountNumber(), initialCredit, TransactionType.DEPOSIT);
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(account);
  }

  public Account findIfExists(UUID accountNumber) {
    return accountRepository
        .findByAccountNumber(accountNumber)
        .orElseThrow(
            () -> new RecordNotFoundException("Could not find Account with provided number "));
  }

  public void save(Account account) {
    accountRepository.save(account);
  }
}
