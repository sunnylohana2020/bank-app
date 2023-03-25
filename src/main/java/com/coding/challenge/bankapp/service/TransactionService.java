package com.coding.challenge.bankapp.service;

import com.coding.challenge.bankapp.constant.TransactionType;
import com.coding.challenge.bankapp.entity.Account;
import com.coding.challenge.bankapp.entity.Transaction;
import com.coding.challenge.bankapp.exception.BankingException;
import com.coding.challenge.bankapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TransactionService {
  @Autowired private TransactionRepository transactionRepository;

  @Autowired private AccountService accountService;

  @Transactional(rollbackFor = {Exception.class})
  public void addTransaction(UUID accountNumber, Double amount, TransactionType transactionType) {
    Account account = accountService.findIfExists(accountNumber);
    transactionRepository.save(
        Transaction.builder().account(account).txnAmount(amount).txnType(transactionType).build());
    if (TransactionType.DEPOSIT.equals(transactionType)) {
      account.setAvailableBalance(
          account.getAvailableBalance() == null ? amount : account.getAvailableBalance() + amount);
    } else {
      if (amount > account.getAvailableBalance())
        throw new BankingException(HttpStatus.BAD_REQUEST, "insufficient balance for transaction");
      account.setAvailableBalance(account.getAvailableBalance() - amount);
    }
    accountService.save(account);
  }
}
