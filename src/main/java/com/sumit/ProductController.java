package com.sumit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/models")
public class ProductController {


    @Autowired
    private ProductService modelService;

    @PostMapping("/update")
    public Mono<ResponseEntity<String>> updateModels(@RequestParam String value) {
        return modelService.updateModels( value)
                .thenReturn(ResponseEntity.ok("All updates successful"))
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body("Update failed: " + ex.getMessage())));
    }


    @PostMapping("/update1")
    public Mono<ResponseEntity<String>> updateProd(@RequestParam List<String> value) {
        return modelService.updateProduct( value)
                .thenReturn(ResponseEntity.ok("All updates successful"))
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body("Update failed: " + ex.getMessage())));
    }



}
