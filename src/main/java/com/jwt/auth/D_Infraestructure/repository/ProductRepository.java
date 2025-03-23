package com.jwt.auth.D_Infraestructure.repository;

import com.jwt.auth.A_Domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
