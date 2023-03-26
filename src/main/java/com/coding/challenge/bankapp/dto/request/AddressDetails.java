package com.coding.challenge.bankapp.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressDetails {
  @NotNull(message = "Address is required.")
  private String address;

  @NotNull(message = "City is required.")
  private String city;

  @NotNull(message = "State is required.")
  private String state;

  @NotNull(message = "Zip is required.")
  private String zip;

  @NotNull(message = "Country is required.")
  private String country;
}
