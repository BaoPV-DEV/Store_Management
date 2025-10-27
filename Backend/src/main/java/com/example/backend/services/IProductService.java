package com.example.backend.services;

import com.example.backend.dtos.request.products.ProductRequestDto;
import com.example.backend.dtos.request.products.ProductImageRequestDto;
import com.example.backend.dtos.response.product.ProductResponseDto;
import com.example.backend.models.Product;
import com.example.backend.models.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {

    Product createProduct(ProductRequestDto productRequestDto);

    Product getProductById(Long id);

    Page<ProductResponseDto> getAllProducts(PageRequest pageRequest);

    Product updateProduct(Long productId, ProductRequestDto product);

    void deleteProduct(Long productId);

    boolean existsProductByName(String name);

    ProductImage createProductImage(Long productId, ProductImageRequestDto productImageRequestDto);
}
