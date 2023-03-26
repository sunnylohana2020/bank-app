package com.coding.challenge.bankapp.service;

import com.coding.challenge.bankapp.constant.AccountType;
import com.coding.challenge.bankapp.constant.Status;
import com.coding.challenge.bankapp.entity.Account;
import com.coding.challenge.bankapp.entity.Customer;
import com.coding.challenge.bankapp.exception.RecordNotFoundException;
import com.coding.challenge.bankapp.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

  @Mock private AccountRepository accountRepository;
  @Mock private CustomerService customerService;
  @Mock private TransactionService transactionService;

  @InjectMocks private AccountService accountService;

  @Test
  public void testAddAccountWithInitialCredit() {
    UUID customerId = UUID.randomUUID();
    Double initialCredit = 100.0;
    AccountType accountType = AccountType.CURRENT;

    Customer customer = Customer.builder().customerId(customerId).build();
    when(customerService.findIfExists(customerId)).thenReturn(customer);

    Account account =
        Account.builder()
            .accountNumber(UUID.randomUUID())
            .accountType(accountType)
            .customer(customer)
            .accountStatus(Status.ACTIVE)
            .build();
    when(accountRepository.save(account)).thenReturn(account);

    accountService.addAccount(customerId, initialCredit, accountType);

    verify(accountRepository, times(1)).save(any());
  }

  @Test
  public void testAddAccountWithoutInitialCredit() {
    UUID customerId = UUID.randomUUID();
    AccountType accountType = AccountType.CURRENT;

    Customer customer = Customer.builder().customerId(customerId).build();
    when(customerService.findIfExists(customerId)).thenReturn(customer);

    Account account =
        Account.builder()
            .accountNumber(UUID.randomUUID())
            .accountType(accountType)
            .customer(customer)
            .accountStatus(Status.ACTIVE)
            .build();
    when(accountRepository.save(account)).thenReturn(account);

    accountService.addAccount(customerId, null, accountType);

    verify(transactionService, times(0)).addTransaction(any(), any(), any());
  }

  @Test
  public void testFindIfExists() {
    UUID accountNumber = UUID.randomUUID();
    Account account =
        Account.builder()
            .accountNumber(accountNumber)
            .accountType(AccountType.CURRENT)
            .customer(Customer.builder().customerId(UUID.randomUUID()).build())
            .accountStatus(Status.ACTIVE)
            .build();
    when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));

    Account result = accountService.findIfExists(accountNumber);

    assertThat(result).isEqualTo(account);
  }

  @Test(expected = RecordNotFoundException.class)
  public void testFindIfExistsNotFound() {
    UUID accountNumber = UUID.randomUUID();
    when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

    accountService.findIfExists(accountNumber);
  }

  @Test
  public void testSave() {
    Account account =
        Account.builder()
            .accountNumber(UUID.randomUUID())
            .accountType(AccountType.CURRENT)
            .customer(Customer.builder().customerId(UUID.randomUUID()).build())
            .accountStatus(Status.ACTIVE)
            .build();

    accountService.save(account);

    verify(accountRepository, times(1)).save(account);
  }
}
