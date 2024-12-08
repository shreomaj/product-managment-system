package com.rest.product_manag_system.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.product_manag_system.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
