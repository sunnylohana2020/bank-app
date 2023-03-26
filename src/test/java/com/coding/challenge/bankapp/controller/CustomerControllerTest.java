package com.coding.challenge.bankapp.controller;

import com.coding.challenge.bankapp.constant.Status;
import com.coding.challenge.bankapp.dto.CustomerDetails;
import com.coding.challenge.bankapp.dto.request.AddressDetails;
import com.coding.challenge.bankapp.dto.request.CreateCustomer;
import com.coding.challenge.bankapp.entity.Customer;
import com.coding.challenge.bankapp.helper.ServiceMapper;
import com.coding.challenge.bankapp.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

  @Mock private CustomerService customerService;

  @InjectMocks private CustomerController customerController;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
  }

  @Test
  public void testGetAllCustomers() throws Exception {
    List<CustomerDetails> customerDetailsList = new ArrayList<>();
    CustomerDetails customerDetails = new CustomerDetails();
    customerDetails.setFirstName("John");
    customerDetails.setSurname("Doe");
    customerDetails.setAccounts(new ArrayList<>());
    customerDetailsList.add(customerDetails);

    when(customerService.findAll()).thenReturn(customerDetailsList);

    mockMvc
        .perform(get("/customers/all"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].firstName", is("John")))
        .andExpect(jsonPath("$[0].surname", is("Doe")))
        .andExpect(jsonPath("$[0].accounts", hasSize(0)));

    verify(customerService, times(1)).findAll();
    verifyNoMoreInteractions(customerService);
  }

  @Test
  public void testGetCustomer() throws Exception {
    UUID customerId = UUID.randomUUID();
    Customer customer = new Customer();

    customer.setCustomerId(customerId);
    customer.setFirstName("John");
    customer.setSurname("Doe");
    customer.setAccounts(new ArrayList<>());
    CustomerDetails expectedCustomerDetails = ServiceMapper.convertToCustomerDto(customer);

    Mockito.when(customerService.findByCustomerId(customerId)).thenReturn(expectedCustomerDetails);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/customers/" + customerId.toString()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Doe"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.accounts").isEmpty());

    Mockito.verify(customerService, Mockito.times(1)).findByCustomerId(customerId);
  }

  @Test
  public void testAddCustomer() throws Exception {
    CreateCustomer createCustomer =
        new CreateCustomer(
            "John",
            "Doe",
            null,
            "john.doe@example.com",
            "123-456-7890",
            Status.ACTIVE,
            new AddressDetails("123 Main St", null, "Anytown", "CA", "12345"));
    Customer customer =
        Customer.builder()
            .firstName(createCustomer.getFirstName())
            .surname(createCustomer.getSurname())
            .customerId(UUID.randomUUID())
            .emailId(createCustomer.getEmailId())
            .phone(createCustomer.getPhone())
            .status(createCustomer.getStatus())
            .customerAddress(
                ServiceMapper.convertToAddressEntity(createCustomer.getCustomerAddress()))
            .build();

    when(customerService.addCustomer(any(CreateCustomer.class)))
        .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(customer));

    mockMvc
        .perform(
            post("/customers/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(createCustomer)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.firstName", is(customer.getFirstName())))
        .andExpect(jsonPath("$.surname", is(customer.getSurname())))
        .andExpect(jsonPath("$.emailId", is(customer.getEmailId())))
        .andExpect(jsonPath("$.phone", is(customer.getPhone())))
        .andExpect(jsonPath("$.status", is(customer.getStatus().toString())))
        .andExpect(
            jsonPath("$.customerAddress.address", is(customer.getCustomerAddress().getAddress())))
        .andExpect(jsonPath("$.customerAddress.city", is(customer.getCustomerAddress().getCity())))
        .andExpect(
            jsonPath("$.customerAddress.state", is(customer.getCustomerAddress().getState())))
        .andExpect(jsonPath("$.customerAddress.zip", is(customer.getCustomerAddress().getZip())));
  }
}
