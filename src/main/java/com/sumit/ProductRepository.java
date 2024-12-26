package com.sumit;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {

    @Query("UPDATE product SET productName = :newValue WHERE productName = :currentValue")
    Mono<Integer> updateProductName(String newValue, String currentValue);

}
