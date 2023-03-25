package com.coding.challenge.bankapp.entity;

import com.coding.challenge.bankapp.constant.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Temporal(TemporalType.TIME)
  private Date txnDateTime;

  @Enumerated(EnumType.STRING)
  private TransactionType txnType;

  private Double txnAmount;

  @ManyToOne(fetch = FetchType.LAZY)
  private Account account;

  @PrePersist
  public void onCreate() {
    if (this.txnDateTime == null) {
      setTxnDateTime(new Date());
    }
  }
}
