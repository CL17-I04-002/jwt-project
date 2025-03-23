package com.jwt.auth.A_Domain.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "operation")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; //ALL_PRODUCTS
    private String path; // /products/{productId}/disabled
    private String httpMethod;
    private boolean permitAll; // is public the endpoint? if yes set true
    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;
}
