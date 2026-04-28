package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<List<Customer>> getCustomerByDni(@PathVariable String dni) {
        List<Customer> customer = customerService.getCustomerByDni(dni);
        if (customer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/lastName/{lastName}")
    public ResponseEntity<List<Customer>> getCustomerByLastName(@PathVariable String lastName) {
        List<Customer> customer = customerService.getCustomerByLastName(lastName);
        if (customer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity <Customer> CreateCustomer(@RequestBody Customer customer) {
        Customer newCustomer = customerService.saveCustomer(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/remaining-days")
    public ResponseEntity<?> getRemainingDays(@PathVariable Long id) {
        long days = customerService.getRemainingDays(id);
        return ResponseEntity.ok(days);
    }

    @PutMapping("/{id}/renew")
    public ResponseEntity<Customer> renewCustomer(@PathVariable Long id) {
        Customer renewed = customerService.renewCustomer(id);
        return ResponseEntity.ok(renewed);
    }
}
