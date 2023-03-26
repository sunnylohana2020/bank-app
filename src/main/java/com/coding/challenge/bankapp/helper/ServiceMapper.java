package com.coding.challenge.bankapp.helper;

import com.coding.challenge.bankapp.dto.AccountDetails;
import com.coding.challenge.bankapp.dto.CustomerDetails;
import com.coding.challenge.bankapp.dto.TransactionDetails;
import com.coding.challenge.bankapp.dto.request.AddressDetails;
import com.coding.challenge.bankapp.dto.request.CreateCustomer;
import com.coding.challenge.bankapp.entity.Account;
import com.coding.challenge.bankapp.entity.Address;
import com.coding.challenge.bankapp.entity.Customer;
import com.coding.challenge.bankapp.entity.Transaction;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ServiceMapper {

  public static CustomerDetails convertToCustomerDto(Customer customer) {

    return CustomerDetails.builder()
        .firstName(customer.getFirstName())
        .surname(customer.getSurname())
        .accounts(convertToAccountDetails(customer.getAccounts()))
        .build();
  }

  public static Customer convertToCustomerEntity(CreateCustomer createCustomer) {

    return Customer.builder()
        .firstName(createCustomer.getFirstName())
        .surname(createCustomer.getSurname())
        .customerId(UUID.randomUUID())
        .emailId(createCustomer.getEmailId())
        .phone(createCustomer.getPhone())
        .status(createCustomer.getStatus())
        .customerAddress(convertToAddressEntity(createCustomer.getCustomerAddress()))
        .build();
  }

  public static AccountDetails convertToAccountDetails(Account account) {

    return AccountDetails.builder()
        .accountType(account.getAccountType())
        .accountBalance(account.getAvailableBalance())
        .accountNumber(account.getAccountNumber())
        .accountStatus(account.getAccountStatus())
        .transactions(convertToTransactionDto(account.getTransactions()))
        .build();
  }

  public static List<AccountDetails> convertToAccountDetails(List<Account> transactions) {
    return transactions.stream()
        .map(ServiceMapper::convertToAccountDetails)
        .collect(Collectors.toList());
  }

  public static TransactionDetails convertToTransactionDto(Transaction transaction) {
    return TransactionDetails.builder()
        .txnType(transaction.getTxnType())
        .txnAmount(transaction.getTxnAmount())
        .txnDateTime(transaction.getTxnDateTime())
        .build();
  }

  public static List<TransactionDetails> convertToTransactionDto(List<Transaction> transactions) {
    return transactions.stream()
        .map(ServiceMapper::convertToTransactionDto)
        .collect(Collectors.toList());
  }

  public static AddressDetails convertToAddressDomain(Address address) {

    return AddressDetails.builder()
        .address(address.getAddress())
        .city(address.getCity())
        .state(address.getState())
        .zip(address.getZip())
        .country(address.getCountry())
        .build();
  }

  public static Address convertToAddressEntity(AddressDetails addressDetails) {

    return Address.builder()
        .address(addressDetails.getAddress())
        .city(addressDetails.getCity())
        .state(addressDetails.getState())
        .zip(addressDetails.getZip())
        .country(addressDetails.getCountry())
        .build();
  }
}
