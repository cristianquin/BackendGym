package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getCustomerByDni(String dni) {
        return customerRepository.findByDniContaining(dni);
    }

    public List<Customer> getCustomerByLastName(String lastName) {
        return customerRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public long getRemainingDays(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        long diff = customer.getEndedDate().getTime() - new Date().getTime();
        return TimeUnit.MILLISECONDS.toDays(diff);
    }

    public Customer renewCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        if (customer.getEndedDate().before(today)){
            customer.setStartedDate(today);
            calendar.setTime(today);
            calendar.add(Calendar.MONTH, 1);
        }else{
            calendar.setTime(customer.getEndedDate());
            calendar.add(Calendar.MONTH, 1);
        }
        customer.setEndedDate(calendar.getTime());

        return customerRepository.save(customer);
    }
}
