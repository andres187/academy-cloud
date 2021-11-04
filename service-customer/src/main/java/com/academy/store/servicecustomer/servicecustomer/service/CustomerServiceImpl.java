package com.academy.store.servicecustomer.servicecustomer.service;

import com.academy.store.servicecustomer.servicecustomer.repository.CustomerRepository;
import com.academy.store.servicecustomer.servicecustomer.repository.entity.Customer;
import com.academy.store.servicecustomer.servicecustomer.repository.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> findCustomerAll() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomersByRegion(Region region) {
        return customerRepository.findByRegion(region);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer customerDb = customerRepository.findByNumberID(customer.getNumberID());
        if (null != customerDb){
            return customerDb;
        }

        customer.setState("CREATED");
        customerDb = customerRepository.save(customer);
        return customerDb;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer customerDb = getCustomer(customer.getId());
        if(customerDb == null){
            return null;
        }
        customerDb.setFirstName(customer.getFirstName());
        customerDb.setLastName(customer.getLastName());
        customerDb.setEmail(customer.getEmail());
        customerDb.setPhotoUrl(customer.getPhotoUrl());

        return customerRepository.save(customerDb);
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        Customer customerDb = getCustomer(customer.getId());
        if (null == customerDb){
            return null;
        }
        customer.setState("DELETED");
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}
