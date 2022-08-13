package com.ecom.controller;

import com.ecom.payload.ApiResponse;
import com.ecom.payload.ProductDto;
import com.ecom.service.FileService;
import com.ecom.service.ProductService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;

    @Value("${product.images}")
    private String productPath;

    //    createProduct
    @PostMapping("/category/{catId}/products")
    public ResponseEntity<ProductDto> create(
            @RequestBody ProductDto productDto,
            @PathVariable Integer catId
    ) {
        ProductDto productDto1 = this.productService.create(productDto, catId);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }


    //get all products

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return new ResponseEntity<>(this.productService.getAllProducts(), HttpStatus.OK);
    }
   
    //get byId
    
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDto> getById(@PathVariable Integer productId) {
    	ProductDto product=this.productService.getById(productId);
        return new ResponseEntity<ProductDto>( product,HttpStatus.OK);
    }

    @GetMapping("/products/not-live")
    public ResponseEntity<List<ProductDto>> getAllProductsNotLive() {
        return new ResponseEntity<>(this.productService.getProductNotLive(), HttpStatus.OK);
    }
    
    @GetMapping("/products/out-of-stock")
    public ResponseEntity<List<ProductDto>> getAllProductsoutOfStock() {
        return new ResponseEntity<>(this.productService.getProductOutOfStock(), HttpStatus.OK);
    }
    //update
    //TODO

    @PutMapping("/category/{catId}/product/{productId}")
    public ResponseEntity<ProductDto> update(
            @RequestBody ProductDto product,
            @PathVariable Integer catId)
    {
    	ProductDto update=this.productService.update(product, catId);
    	return new ResponseEntity<ProductDto>(update,HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/product/{productId}")
    public ApiResponse delete(@PathVariable Integer productId)
    {
    	this.productService.delete(productId);
    	return new ApiResponse("product successfully deleted",true, HttpStatus.OK);
    }

    //TODO

    @PostMapping("/products/image/{productId}")
    public ResponseEntity<ApiResponse> uploadFile(
            @RequestParam("image") MultipartFile file,
            @PathVariable Integer productId
    ) throws Exception {
        ProductDto product = this.productService.getById(productId);
        String fileName = this.fileService.uploadFile(productPath, file);
        product.setImageName(fileName);
        this.productService.update(product, productId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage(fileName);
        apiResponse.setSuccess(true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    //serve product images

    @GetMapping(value = "/products/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void serveProductImage(
            @PathVariable String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream data = this.fileService.getData(productPath, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(data, response.getOutputStream());
        data.close();

    }
    
    @DeleteMapping(value = "/products/{productId}")
    public ResponseEntity<ApiResponse>deleteImage(@PathVariable Integer productId) throws IOException{
    	this.productService.deleteProductImage(productId, productPath);
    	ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("image is deleted successfully");
        apiResponse.setSuccess(true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/products/search/{keywords}")
    public ResponseEntity<List<ProductDto> > searchProducts(
            @PathVariable String keywords

    ) {
        List<ProductDto> productDtos = this.productService.searchProduct(keywords);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

}

