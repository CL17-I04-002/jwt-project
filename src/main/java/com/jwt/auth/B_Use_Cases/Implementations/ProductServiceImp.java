package com.jwt.auth.B_Use_Cases.Implementations;

import com.jwt.auth.A_Domain.Product;
import com.jwt.auth.B_Use_Cases.Exception.ObjectNotFoundException;
import com.jwt.auth.B_Use_Cases.Interfaces.ProductService;
import com.jwt.auth.D_Infraestructure.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    private final ProductRepository productRepository;
    private final String productNotFound;

    public ProductServiceImp(ProductRepository productRepository, @Value("${product.not.found}") String productNotFound) {
        this.productRepository = productRepository;
        this.productNotFound = productNotFound;
    }

    @Override
    public Product crateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProductoById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(productNotFound));
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product productFound = productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(productNotFound));
        productFound.setName(product.getName());
        productFound.setPrice(product.getPrice());
        productFound.setDescription(product.getDescription());
        return productRepository.save(productFound);
    }

    @Override
    public void deleteProduct(Long id) {
        Product productFound = productRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(productNotFound));
        productRepository.delete(productFound);
    }
}
