package com.jwt.auth.B_Use_Cases.Interfaces;

import com.jwt.auth.A_Domain.Product;

import java.util.List;

public interface ProductService {
    Product crateProduct(Product product);
    List<Product> findAllProducts();
    Product findProductoById(Long id);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}
