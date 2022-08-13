package com.ecom.controller;

import com.ecom.payload.ApiResponse;
import com.ecom.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${product.images}")
    private String productPath;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadFile(
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        String fileName = this.fileService.uploadFile(productPath, file);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage(fileName);
        apiResponse.setSuccess(true);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    
    
}
