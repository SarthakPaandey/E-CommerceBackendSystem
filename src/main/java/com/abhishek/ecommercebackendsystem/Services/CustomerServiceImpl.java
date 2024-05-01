package com.abhishek.ecommercebackendsystem.Services;

import com.abhishek.ecommercebackendsystem.Dtos.CustomerRequestDto;
import com.abhishek.ecommercebackendsystem.Dtos.CustomerResponseDto;
import com.abhishek.ecommercebackendsystem.Exceptions.InvalidCustomerIdException;
import com.abhishek.ecommercebackendsystem.Models.Customer;
import com.abhishek.ecommercebackendsystem.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository CustomerRepository;
    public CustomerServiceImpl(CustomerRepository CustomerRepository) {
        this.CustomerRepository = CustomerRepository;
    }

    @Override
    public Customer createCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = new Customer();
        customer.setName(customerRequestDto.getName());
        customer.setEmail(customerRequestDto.getEmail());

        return CustomerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> optionalCustomer = CustomerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new InvalidCustomerIdException("Invalid Customer Id Found", id);
        }
        return optionalCustomer.get();
    }

    @Override
    public Customer updateCustomer(CustomerRequestDto customerRequestDto, Long id) {
        Optional<Customer> optionalCustomer = CustomerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new InvalidCustomerIdException("Invalid Customer Id Found", id);
        }
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(customerRequestDto.getName());
        customer.setEmail(customerRequestDto.getEmail());

        return CustomerRepository.save(customer);
    }
}
