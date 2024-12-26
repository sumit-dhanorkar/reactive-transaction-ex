package com.sumit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private  ProductRepository1 repository1;


    @Autowired
    CustomProductRepository customProductRepository;

    @Autowired
    private TransactionalOperator transactionalOperator;

    public Mono<Void> updateModels(String value) {
        return productRepository.findAll()
                .flatMap(product -> updateModel(product, value))
                .then()
                .as(transactionalOperator::transactional);
    }

    private Mono<Void> updateModel(Product product, String value) {
        if (product.getProductName().equals(value)) {
            String newProductName = product.getProductName() + " changed";

            return productRepository.updateProductName(newProductName, value)
                    .flatMap(updatedCount -> {
                        if (updatedCount == 0) {
                            return Mono.error(new IllegalArgumentException("No product found with the given value: " + value));
                        }
                        return Mono.empty();
                    });
        } else {
            return Mono.error(new IllegalArgumentException("Product name does not match: " + product.getProductId()));
        }
    }


//    private Mono<Void> updateModel(Product product, String value) {
//        if (product.getProductName().equals(value)) {
//            Mono<Integer> res = productRepository.updateProductName(product.getProductName() + " changed", value);
////            Mono<Long> res = customProductRepository.updateProductName(product.getProductName() + " changed", value);
//            return res.flatMap(r -> {
//                if (r == 0) {
//                    return Mono.error(new IllegalArgumentException("No product found with the given value or the update condition was not met"));
//                }
//                return Mono.empty();
//            }).onErrorResume(e -> {
//                return Mono.error(new RuntimeException("Update failed", e));
//            }).then();
//        }
//        return Mono.error(new IllegalArgumentException("Product name does not match"));
//    }

//    private Mono<Void> updateModel(Product product, String value) {
////        if (product.getProductName().equals(value)) {
////            Mono<Integer> res = productRepository.updateProductName(product.getProductName() + " changed", value);
////            res.flatMap(r -> {
////                if (r == 0) {
////                    return Mono.error(new IllegalArgumentException("No product found with the given value or the update condition was not met"));
////
////                }
////                return Mono.empty();
////            }).onErrorResume(e -> {
////                return Mono.error(new RuntimeException("Update failed", e));
////            });
////
////
////        }
//        if (product.getProductName().equals(value)) {
//            product.setProductName(value + " changed");
//            return productRepository.save(product).then();
//        } else {
//            return Mono.error(new IllegalArgumentException("Value not found in model: " + product.getProductId()));
//        }
//
////        return Mono.empty();
//    }

    @Transactional
    public Mono<Long> updateProduct(List<String> productIds) {
        return repository1.findAllById(productIds)
                .collectList()
                .flatMap(products -> {
                    if (products.size() != productIds.size()) {
                        return Mono.error(new RuntimeException("Some products were not found"));
                    }

                    return Flux.fromIterable(products)
                            .flatMap(this::updateProd)
                            .count();
                });
    }

    private Mono<Product1> updateProd(Product1 product1) {
        String newProductName = "laptop";
        return repository1.updateProductName(newProductName, product1.getProductId())
                .flatMap(updatedCount -> {
                    if (updatedCount > 0) {
                        return Mono.just(product1);
                    }
                    return Mono.error(new IllegalArgumentException(
                            "No product found with ID: " + product1.getProductId()));
                });
    }



}




















