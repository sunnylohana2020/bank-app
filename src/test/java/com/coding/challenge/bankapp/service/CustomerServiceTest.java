package com.coding.challenge.bankapp.service;

import com.coding.challenge.bankapp.constant.Status;
import com.coding.challenge.bankapp.dto.CustomerDetails;
import com.coding.challenge.bankapp.dto.request.AddressDetails;
import com.coding.challenge.bankapp.dto.request.CreateCustomer;
import com.coding.challenge.bankapp.entity.Customer;
import com.coding.challenge.bankapp.exception.RecordNotFoundException;
import com.coding.challenge.bankapp.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

  @Mock private CustomerRepository customerRepository;

  @InjectMocks private CustomerService customerService;

  @Test
  public void testFindAll() {
    // Given
    List<Customer> customers =
        List.of(
            Customer.builder()
                .customerId(UUID.randomUUID())
                .firstName("John")
                .surname("Doe")
                .accounts(new ArrayList<>())
                .build(),
            Customer.builder()
                .customerId(UUID.randomUUID())
                .firstName("Jane")
                .surname("Doe")
                .accounts(new ArrayList<>())
                .build());
    when(customerRepository.findAll()).thenReturn(customers);

    // When
    List<CustomerDetails> result = customerService.findAll();

    // Then
    assertThat(result.get(0).getFirstName()).isEqualTo("John");
    assertThat(result.get(1).getFirstName()).isEqualTo("Jane");
  }

  @Test
  public void testAddCustomer() {
    // Given
    CreateCustomer createCustomer = new CreateCustomer();
    createCustomer.setFirstName("John");
    createCustomer.setSurname("Doe");
    createCustomer.setEmailId("john.doe@example.com");
    createCustomer.setPhone("+1234567890");
    createCustomer.setStatus(Status.ACTIVE);
    createCustomer.setCustomerAddress(new AddressDetails());

    Customer customer =
        Customer.builder()
            .customerId(UUID.randomUUID())
            .firstName("John")
            .surname("Doe")
            .emailId("john.doe@example.com")
            .phone("+1234567890")
            .status(Status.ACTIVE)
            .customerAddress(new com.coding.challenge.bankapp.entity.Address())
            .build();
    when(customerRepository.save(any(Customer.class))).thenReturn(customer);

    // When
    ResponseEntity<Object> result = customerService.addCustomer(createCustomer);

    // Then
    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(result.getBody()).isEqualTo(customer);
  }

  @Test
  public void testFindByCustomerId() {
    // Given
    UUID customerId = UUID.randomUUID();
    Customer customer =
        Customer.builder()
            .customerId(customerId)
            .firstName("John")
            .surname("Doe")
            .accounts(new ArrayList<>())
            .build();
    when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(customer));

    // When
    CustomerDetails result = customerService.findByCustomerId(customerId);

    // Then
    assertThat(result.getFirstName()).isEqualTo("John");
    assertThat(result.getSurname()).isEqualTo("Doe");
  }

  @Test(expected = RecordNotFoundException.class)
  public void testFindIfExistsWithInvalidId() {
    // Given
    UUID customerId = UUID.randomUUID();
    when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());
    // When
    customerService.findIfExists(customerId);
  }

  @Test
  public void testFindIfExistsWithValidId() {
    // Given
    UUID customerId = UUID.randomUUID();
    Customer customer =
        Customer.builder().customerId(customerId).firstName("John").surname("Doe").build();
    when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(customer));

    // When
    Customer result = customerService.findIfExists(customerId);

    // Then
    assertThat(result.getCustomerId()).isEqualTo(customerId);
    assertThat(result.getFirstName()).isEqualTo("John");
    assertThat(result.getSurname()).isEqualTo("Doe");
  }
}
