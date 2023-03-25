package com.coding.challenge.bankapp.controller;

import com.coding.challenge.bankapp.dto.CustomerDetails;
import com.coding.challenge.bankapp.dto.request.CreateCustomer;
import com.coding.challenge.bankapp.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("customers")
@Api(tags = {"Customer REST endpoints"})
public class CustomerController {

  @Autowired private CustomerService customerService;

  @GetMapping(path = "/all")
  @ApiOperation(value = "Find all customers", notes = "Gets details of all the customers")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal Server Error")
      })
  public List<CustomerDetails> getAllCustomers() {

    return customerService.findAll();
  }

  @PostMapping(path = "/add")
  @ApiOperation(value = "Add a Customer", notes = "Add customer and create an account")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal Server Error")
      })
  public ResponseEntity<Object> addCustomer(@RequestBody CreateCustomer customer) {

    return this.customerService.addCustomer(customer);
  }

  @GetMapping(path = "/{customerId}")
  @ApiOperation(value = "Get customer details", notes = "Get Customer details by customer Id.")
  @ApiResponses(
      value = {
        @ApiResponse(
            code = 200,
            message = "Success",
            response = CustomerDetails.class,
            responseContainer = "Object"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal Server Error")
      })
  public CustomerDetails getCustomer(@PathVariable UUID customerId) {

    return customerService.findByCustomerId(customerId);
  }
}
