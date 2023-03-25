package com.coding.challenge.bankapp.dto;

import com.coding.challenge.bankapp.constant.AccountType;
import com.coding.challenge.bankapp.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountDetails {

  private UUID accountNumber;

  private Status accountStatus;

  private AccountType accountType;

  private Double accountBalance;

  private Date accountCreated;

  private List<TransactionDetails> transactions;
}
