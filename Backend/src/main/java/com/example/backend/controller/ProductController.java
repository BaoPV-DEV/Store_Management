package com.example.backend.controller;

import com.example.backend.dtos.request.products.ProductRequestDto;
import com.example.backend.dtos.request.products.ProductImageRequestDto;
import com.example.backend.dtos.response.pagination.PaginationResponse;
import com.example.backend.dtos.response.product.ProductResponseDto;
import com.example.backend.models.Product;
import com.example.backend.models.ProductImage;
import com.example.backend.services.IProductService;
import jakarta.validation.Valid;
import org.apache.tika.Tika;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    private static final List<String> allowedImageTypes = List.of(
            "image/jpeg", "image/png", "image/gif", "image/webp"
    );

    private final Tika tika = new Tika();

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "")
    public ResponseEntity<?> createProductCategoryDTOs(
            @Valid @RequestBody ProductRequestDto productRequestDto,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> resultErrorList = result.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(resultErrorList);
            }
            Product newProduct = productService.createProduct(productRequestDto);

            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error from server" + e.getMessage());
        }
    }

    @PostMapping(value = "/{id}/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFileToProduct(
            @PathVariable Long id,
            @RequestParam("files") List<MultipartFile> files) {
        try {

            Product product = productService.getProductById(id);
            // Check size file
            files = files == null ? new ArrayList<>() : files;
            if(files.size() > 5){
                return ResponseEntity.badRequest()
                        .body("Maximum upload 5 files");
            }

            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file.getSize() == 0) continue;
                String mimeType = tika.detect(file.getInputStream());

                if (!allowedImageTypes.contains(mimeType)) {
                    return ResponseEntity
                            .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("Invalid image format. Only JPEG, PNG, GIF, and WEBP are supported.");
                }
                if (file.getSize() > 10 * 1024 * 1024) { // > 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                // Save file and update thumbnail to DTO
                String fileName = storeFile(file);
                // Save to product object to insert DB
                // Save to BD table: product_images
                productImages.add(productService.createProductImage(
                        product.getId(),
                        ProductImageRequestDto.builder()
                                .imageUrl(fileName)
                                .build()));
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error from server" + e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<PaginationResponse<ProductResponseDto>> getAllProducts(
            @RequestParam int page,
            @RequestParam int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        Page<ProductResponseDto> productList = productService.getAllProducts(pageRequest);

        // Get total page
        List<ProductResponseDto> products = productList.getContent();

        PaginationResponse<ProductResponseDto> response = new PaginationResponse<>(
                products,
                page,
                limit,
                productList.getTotalElements(),
                productList.getTotalPages()
        );
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(String.format("Get product with id = %d", id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateProducts(@PathVariable Long id) {
        return ResponseEntity.ok("Update successfully with id = " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProducts(@PathVariable Long id) {
        return ResponseEntity.ok("Delete successfully with id = " + id);
    }

    // Function private
    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Add UUID is prefix the name file -> unique
        String uniqueFileName = UUID.randomUUID() + "_" + fileName;
        // Directory to save file
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Full path to file
        Path destinationFile = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }
}
