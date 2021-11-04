package com.academy.store.serviceproduct.controller;

import com.academy.store.serviceproduct.entity.Category;
import com.academy.store.serviceproduct.entity.Product;
import com.academy.store.serviceproduct.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<List<Product>> listProduct(@RequestParam(name = "categoryId", required = false) Long categoryId){
        List<Product> result;
        if (null == categoryId){
            result = productService.listAllProduct();
            if(result.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            result = productService.findByCategory(Category.builder().id(categoryId).build());

            if(result.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id){
        Product product = productService.getProduct(id);
        if (null == product){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, formatMessage(bindingResult));
        }
        Product result = productService.createProduct(product);

        return  ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@RequestBody Product product){
        product.setId(id);
        Product productDb = productService.updateProduct(product);

        if (productDb == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productDb);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id){
        Product result = productService.deleteProduct(id);

        if (result == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long id,@RequestParam(name = "quantity") Double quantity){
        Product product = productService.updateStock(id, quantity);
        if (product == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(product);
    }

    private String formatMessage(BindingResult bindingResult) {
        List<Map<String, String>> errors = bindingResult.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String> error = new HashMap<>();
                    error.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try{
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

}
