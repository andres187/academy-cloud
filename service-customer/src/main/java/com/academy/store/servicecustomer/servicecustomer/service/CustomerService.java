package com.academy.store.servicecustomer.servicecustomer.service;

import com.academy.store.servicecustomer.servicecustomer.repository.entity.Customer;
import com.academy.store.servicecustomer.servicecustomer.repository.entity.Region;

import java.util.List;

public interface CustomerService {
    List<Customer> findCustomerAll();

    List<Customer> findCustomersByRegion(Region region);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    Customer deleteCustomer(Customer customer);

    Customer getCustomer(Long id);
}
