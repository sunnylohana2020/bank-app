package com.coding.challenge.bankapp.service;

import com.coding.challenge.bankapp.dto.CustomerDetails;
import com.coding.challenge.bankapp.dto.request.CreateCustomer;
import com.coding.challenge.bankapp.entity.Customer;
import com.coding.challenge.bankapp.exception.RecordNotFoundException;
import com.coding.challenge.bankapp.helper.ServiceMapper;
import com.coding.challenge.bankapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {
  @Autowired private CustomerRepository customerRepository;

  /*
   * findAll Customers
   *
   * @param customerDetails
   * @return
   */
  public List<CustomerDetails> findAll() {
    return customerRepository.findAll().stream()
        .map(ServiceMapper::convertToCustomerDto)
        .collect(Collectors.toList());
  }

  /*
   * CREATE Customer
   *
   * @param customerDetails
   * @return
   */
  public ResponseEntity<Object> addCustomer(CreateCustomer customer) {
    Customer persistedObject =
        customerRepository.save(ServiceMapper.convertToCustomerEntity(customer));
    return ResponseEntity.status(HttpStatus.CREATED).body(persistedObject);
  }

  /*
   * FIND and MAP Customer details by customerId
   *
   * @param customerId
   * @return
   */
  public CustomerDetails findByCustomerId(UUID customerId) {
    return ServiceMapper.convertToCustomerDto(findIfExists(customerId));
  }

  /*
   * FIND Customer by customerId
   *
   * @param customerId
   * @return
   */
  public Customer findIfExists(UUID customerId) {
    return customerRepository
        .findByCustomerId(customerId)
        .orElseThrow(
            () -> new RecordNotFoundException("Could not find customer with provided ID "));
  }
}
