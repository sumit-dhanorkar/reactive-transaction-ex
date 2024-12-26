package com.sumit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public class CustomProductRepository {

    private final R2dbcEntityTemplate template;

    @Autowired
    public CustomProductRepository(R2dbcEntityTemplate template) {
        this.template = template;
    }

    public Mono<Long> updateProductName(String newValue, String currentValue) {
        String query = "UPDATE product SET productName = $1 WHERE productName = $2";

        return template.getDatabaseClient()
                .sql(query)
                .bind(0, newValue)
                .bind(1, currentValue)
                .fetch()
                .rowsUpdated();
    }
}
