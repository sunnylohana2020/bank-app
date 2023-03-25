package com.coding.challenge.bankapp.dto.request;

import com.coding.challenge.bankapp.constant.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateCustomer {
  @NotNull(message = "First name is required.")
  private String firstName;

  @NotNull(message = "Surname is required.")
  private String surname;

  @ApiModelProperty(hidden = true)
  private UUID customerId;

  @NotNull(message = "EmailId is required.")
  private String emailId;

  @NotNull(message = "Phone is required.")
  private String phone;

  @NotNull(message = "Status is required.")
  private Status status;

  @Valid private AddressDetails customerAddress;
}
