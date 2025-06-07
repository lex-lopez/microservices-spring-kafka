package com.demo.stock.repository;

import org.springframework.data.repository.CrudRepository;
import com.demo.stock.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
