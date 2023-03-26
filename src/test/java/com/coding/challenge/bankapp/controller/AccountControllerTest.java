package com.coding.challenge.bankapp.controller;

import com.coding.challenge.bankapp.constant.AccountType;
import com.coding.challenge.bankapp.constant.Status;
import com.coding.challenge.bankapp.entity.Account;
import com.coding.challenge.bankapp.entity.Customer;
import com.coding.challenge.bankapp.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

  private MockMvc mockMvc;

  @Mock private AccountService accountService;

  @InjectMocks private AccountController accountController;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
  }

  @Test
  public void testAddCurrentAccount() throws Exception {
    UUID customerId = UUID.randomUUID();
    Double initialCredit = 1000.0;

    Customer customer = new Customer();
    customer.setCustomerId(customerId);

    Account account = new Account();
    account.setId(1L);
    account.setAccountNumber(UUID.randomUUID());
    account.setAccountType(AccountType.CURRENT);
    account.setCustomer(customer);
    account.setAccountStatus(Status.ACTIVE);

    when(accountService.addAccount(customerId, initialCredit, AccountType.CURRENT))
        .thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(account));

    mockMvc
        .perform(
            post("/accounts/add/" + customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .param("initialCredit", initialCredit.toString()))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.accountNumber", notNullValue()))
        .andExpect(jsonPath("$.accountType", is("CURRENT")))
        .andExpect(jsonPath("$.customer.customerId", is(customerId.toString())))
        .andExpect(jsonPath("$.accountStatus", is("ACTIVE")));
  }
}
