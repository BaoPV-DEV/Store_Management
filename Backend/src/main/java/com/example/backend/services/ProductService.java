package com.example.backend.services;

import com.example.backend.dtos.ProductDTO;
import com.example.backend.dtos.ProductImageDTO;
import com.example.backend.exceptions.DataNotFoundException;
import com.example.backend.exceptions.InvalidParamException;
import com.example.backend.models.Category;
import com.example.backend.models.Product;
import com.example.backend.models.ProductImage;
import com.example.backend.repositories.ICategoryRepository;
import com.example.backend.repositories.IProductImageRepository;
import com.example.backend.repositories.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final IProductRepository productRepository;
    private final IProductImageRepository productImageRepository;
    private final ICategoryRepository categoryRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) {
        // Check exist category
        Category existingCategory = _checkExistCategory(productDTO.getCategoryId());

        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .category(existingCategory)
                .build();

        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(String.format("Product can not found with id = %s", productId)));
    }

    @Override
    public Page<Product> getAllCategories(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product updateProduct(Long productId, ProductDTO productDTO) {
        Product product = getProductById(productId);
        if (product == null) {
            throw new DataNotFoundException("Product not found with id: " + productId);
        }

        Category existingCategory = _checkExistCategory(productDTO.getCategoryId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setThumbnail(productDTO.getThumbnail());
        product.setCategory(existingCategory);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new DataNotFoundException("Category not found with id: " + productId);
        }
        productRepository.deleteById(productId);
    }

    @Override
    public boolean existsProductByName(String name) {
        return productRepository.existsByName(name);
    }

    private Category _checkExistCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new DataNotFoundException("Category is not found"));
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) {
        Product product = getProductById(productImageDTO.getProductId());

        ProductImage newProductImage = ProductImage.builder()
                .product(product)
                .imageUrl(productImageDTO.getImageUrl())
                .build();

        // Cant not insert 5 image to DB
        int size = productImageRepository.findByProductId(productId).size();
        if (size >= 5) {
            throw new InvalidParamException("Number of image must be less than or equal 5");
        }
        return productImageRepository.save(newProductImage);
    }
}
