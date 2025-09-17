package com.example.backend.controller;

import com.example.backend.dtos.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @GetMapping("")
    public ResponseEntity<String> getAllProducts(
            @RequestParam int page,
            @RequestParam int limit) {
        return ResponseEntity.ok(String.format("Get product with page = %d, limit = %d", page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(String.format("Get product with id = %d", id));
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProductCategoryDTOs(
            @ModelAttribute @Valid ProductDTO productDTO,
            BindingResult result) {
        try{
            if (result.hasErrors()) {
                List<String> resultErrorList = result.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(resultErrorList);
            }
            // Check size file
            List<MultipartFile> files = productDTO.getFiles();
            files = files == null ? new ArrayList<>() : files;
            for(MultipartFile file : files){
                if(file.getSize() == 0) continue;
                if(file.getSize() > 10 * 1024 * 1024) { // > 10MB
                    // throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "File is too large.");
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                // Save file and update thumbnail to DTO
                String fileName = storeFile(file);
                // Save to product object to insert DB
                // Save to BD table: product_images
                //productDTO.setThumbnail(fileName);
            }

            return ResponseEntity.ok("Successfully created products!" + productDTO);
        } catch (Exception e){
            return ResponseEntity.internalServerError().body("Error from server" + e.getMessage());
        }
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
