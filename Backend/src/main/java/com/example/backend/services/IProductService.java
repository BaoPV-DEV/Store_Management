package com.example.backend.services;

import com.example.backend.dtos.ProductDTO;
import com.example.backend.dtos.ProductImageDTO;
import com.example.backend.models.Product;
import com.example.backend.models.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {

    Product createProduct(ProductDTO productDTO);

    Product getProductById(Long id);

    Page<Product> getAllCategories(PageRequest pageRequest);

    Product updateProduct(Long productId, ProductDTO product);

    void deleteProduct(Long productId);

    boolean existsProductByName(String name);

    ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO);
}
