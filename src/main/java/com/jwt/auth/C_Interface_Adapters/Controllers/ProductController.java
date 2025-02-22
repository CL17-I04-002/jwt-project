package com.jwt.auth.C_Interface_Adapters.Controllers;

import com.jwt.auth.A_Domain.Product;
import com.jwt.auth.B_Use_Cases.Interfaces.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable(required = false) Long id){
        if(Objects.isNull(id)) return ResponseEntity.ok(productService.findAllProducts());
        else return ResponseEntity.ok(productService.findProductoById(id));
    }
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product){
        productService.crateProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product was created");
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable(required = false) Long id, @RequestBody Product product){
        productService.updateProduct(id, product);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product was updated");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(required = false) Long id){
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product was removed");
    }
}
