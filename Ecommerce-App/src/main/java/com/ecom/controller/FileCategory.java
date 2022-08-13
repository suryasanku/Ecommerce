package com.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecom.payload.ApiResponse;
import com.ecom.service.FileService;

public class FileCategory {

	 @Autowired
	    private FileService fileService;

	    @Value("${category.images}")
	    private String categoryPath;

	    @PostMapping("/upload")
	    public ResponseEntity<ApiResponse> uploadFile(
	            @RequestParam("file") MultipartFile file
	    ) throws Exception {
	        String fileName = this.fileService.uploadFile(categoryPath, file);
	        ApiResponse apiResponse = new ApiResponse();
	        apiResponse.setStatus(HttpStatus.OK);
	        apiResponse.setMessage(fileName);
	        apiResponse.setSuccess(true);

	        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	    }
}
