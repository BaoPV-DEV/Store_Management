package com.example.backend.exceptions;

import com.example.backend.configurations.UploadConfig;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private UploadConfig uploadConfig;

    @ExceptionHandler({EntityNotFoundException.class, DataNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<?> handleAlreadyExistsException(Exception ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        String message = String.format(
                "The file you attempted to upload exceeds the allowed size limits.\n\n" +
                        "🔹 Maximum file size allowed: %s\n" +
                        "🔹 Maximum total request size: %s\n\n" +
                        "Please check your files and try again. If the issue persists, contact support.",
                uploadConfig.getMaxFileSize(),
                uploadConfig.getMaxRequestSize()
        );
        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + ex.getMessage());
    }
}
