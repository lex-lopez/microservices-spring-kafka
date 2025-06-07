package com.demo.payment.repository;

import org.springframework.data.repository.CrudRepository;
import com.demo.payment.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
