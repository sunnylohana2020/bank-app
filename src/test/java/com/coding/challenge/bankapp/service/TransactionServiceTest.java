package com.coding.challenge.bankapp.service;

import com.coding.challenge.bankapp.constant.TransactionType;
import com.coding.challenge.bankapp.entity.Account;
import com.coding.challenge.bankapp.entity.Transaction;
import com.coding.challenge.bankapp.exception.BankingException;
import com.coding.challenge.bankapp.repository.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

  @Mock private TransactionRepository transactionRepository;
  @Mock private AccountService accountService;

  @InjectMocks private TransactionService transactionService;

  @Test
  public void testAddTransaction_deposit() {
    UUID accountNumber = UUID.randomUUID();
    Double amount = 100.0;
    TransactionType transactionType = TransactionType.DEPOSIT;

    Account account = Account.builder().availableBalance(0.0).build();
    when(accountService.findIfExists(accountNumber)).thenReturn(account);

    transactionService.addTransaction(accountNumber, amount, transactionType);

    verify(transactionRepository).save(any(Transaction.class));
    verify(accountService).save(account);
    assertEquals(amount, account.getAvailableBalance());
  }

  @Test
  public void testAddTransaction_withdraw() {
    UUID accountNumber = UUID.randomUUID();
    Double amount = 100.0;
    TransactionType transactionType = TransactionType.WITHDRAWAL;

    Account account = Account.builder().availableBalance(200.0).build();
    when(accountService.findIfExists(accountNumber)).thenReturn(account);

    transactionService.addTransaction(accountNumber, amount, transactionType);

    verify(transactionRepository).save(any(Transaction.class));
    verify(accountService).save(account);
    assertEquals(100.0, account.getAvailableBalance(), 0.001);
  }

  @Test(expected = BankingException.class)
  public void testAddTransaction_withdraw_insufficient_balance() {
    UUID accountNumber = UUID.randomUUID();
    Double amount = 100.0;
    TransactionType transactionType = TransactionType.WITHDRAWAL;

    Account account = Account.builder().availableBalance(50.0).build();
    when(accountService.findIfExists(accountNumber)).thenReturn(account);

    transactionService.addTransaction(accountNumber, amount, transactionType);
  }
}
