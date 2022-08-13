package com.ecom.controller;

import com.ecom.entity.Category;
import com.ecom.payload.ApiResponse;
import com.ecom.payload.CategoryDto;
import com.ecom.payload.OrderDto;
import com.ecom.payload.ProductDto;
import com.ecom.service.CategoryService;
import com.ecom.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private FileService fileService;

    @Value("${category.images}")
    private String categoryPath;



    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestBody CategoryDto categoryDto
    ) {
        CategoryDto category1 = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(category1, HttpStatus.CREATED);
    }


    @PreAuthorize("hasRole('NORMAL')")
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories(

    ) {
        List<CategoryDto> categories =
                this.categoryService.getCategories();
        return new ResponseEntity<List<CategoryDto>>(categories, HttpStatus.OK);
    }
    
    //getbyId
    @GetMapping("/{categoryId}/")
    public ResponseEntity<CategoryDto> getById(@PathVariable Integer categoryId) {
    	CategoryDto category=this.categoryService.getById(categoryId);
        return new ResponseEntity<CategoryDto>( category,HttpStatus.OK);
    }


    //update category

    @PutMapping("/{cid}")
    public ResponseEntity<CategoryDto> update(
            @RequestBody CategoryDto categoryDto,
            @PathVariable Integer cid
    ) {
        CategoryDto update = this.categoryService.update(categoryDto, cid);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }


    //delete funtionality
    //TODO
    @DeleteMapping("/{cid}")
    public ApiResponse delete(@PathVariable Integer cid)
    {
    	this.categoryService.delete(cid);
    	return new ApiResponse("category successfully deleted",true, HttpStatus.OK);
    }
    
    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ApiResponse> uploadFile(
            @RequestParam("image") MultipartFile file,
            @PathVariable Integer categoryId
    ) throws Exception { 
    	CategoryDto category= this.categoryService.getById(categoryId);
        String fileName = this.fileService.uploadFile(categoryPath, file);
        category.setBannerName(fileName);
        this.categoryService.update(category, categoryId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage(fileName);
        apiResponse.setSuccess(true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    
    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void serveProductImage(
            @PathVariable String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream data = this.fileService.getData(categoryPath, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(data, response.getOutputStream());

    }
    
    @DeleteMapping(value = "/image/{categoryId}/")
    public ResponseEntity<ApiResponse>deleteImage(@PathVariable Integer categoryId) throws IOException{
    	this.categoryService.deleteCategoryImage(categoryId, categoryPath);
    	ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("image is deleted successfully");
        apiResponse.setSuccess(true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
