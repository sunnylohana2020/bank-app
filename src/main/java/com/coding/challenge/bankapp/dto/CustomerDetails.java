package com.coding.challenge.bankapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetails {
  private String firstName;
  private String surname;
  private List<AccountDetails> accounts;
}
