package com.academy.store.serviceproduct.services;

import com.academy.store.serviceproduct.entity.Category;
import com.academy.store.serviceproduct.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> listAllProduct();
    Product getProduct(Long id);
    Product createProduct(Product product);
    Product updateProduct(Product product);
    Product deleteProduct(Long id);
    List<Product> findByCategory(Category category);
    Product updateStock(Long id, Double quantity);
}
