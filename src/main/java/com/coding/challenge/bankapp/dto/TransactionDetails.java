package com.coding.challenge.bankapp.dto;

import com.coding.challenge.bankapp.constant.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionDetails {

  private Date txnDateTime;

  private TransactionType txnType;

  private Double txnAmount;
}
