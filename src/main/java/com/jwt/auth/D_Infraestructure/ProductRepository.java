package com.jwt.auth.D_Infraestructure;

import com.jwt.auth.A_Domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
