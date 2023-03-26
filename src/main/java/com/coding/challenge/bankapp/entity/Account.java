package com.coding.challenge.bankapp.entity;

import com.coding.challenge.bankapp.constant.AccountType;
import com.coding.challenge.bankapp.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private UUID accountNumber;

  @OneToOne(cascade = CascadeType.ALL)
  private Bank bankDetail;

  @Enumerated(EnumType.STRING)
  private Status accountStatus;

  @Enumerated(EnumType.STRING)
  private AccountType accountType;

  private Double availableBalance;

  @Temporal(TemporalType.TIME)
  private Date createdAt;

  @Temporal(TemporalType.TIME)
  private Date updatedAt;

  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Transaction> transactions = new ArrayList<>();

  @ManyToOne(fetch = FetchType.EAGER)
  private Customer customer;

  @PrePersist
  public void onCreate() {
    if (this.createdAt == null) {
      setCreatedAt(new Date());
    }
  }
}
