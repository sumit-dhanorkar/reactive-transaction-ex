package com.sumit;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository1 extends ReactiveCrudRepository<Product1, String> {
    @Modifying
    @Query("UPDATE product1 SET productName = :newValue WHERE productId = :productId")
    Mono<Integer> updateProductName(String newValue, String productId);
}

