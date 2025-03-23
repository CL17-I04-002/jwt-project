package com.jwt.auth.A_Domain.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "module")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // PRODUCT, CATEGORY, AUTH, CUSTOMER, SO ON
    private String basePath; // /products
}
