package com.coding.challenge.bankapp.entity;

import com.coding.challenge.bankapp.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;

  private String surname;
  private String emailId;
  private String phone;
  private UUID customerId;

  @Enumerated(EnumType.STRING)
  private Status status;

  @Temporal(TemporalType.TIME)
  private Date createdAt;

  @OneToOne(cascade = CascadeType.ALL)
  private Address customerAddress;

  @OneToMany(mappedBy = "customer")
  private List<Account> accounts;

  @PrePersist
  public void onCreate() {
    if (this.createdAt == null) {
      setCreatedAt(new Date());
    }
  }
}
