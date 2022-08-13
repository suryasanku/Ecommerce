package com.ecom.controller;

import com.ecom.entity.Role;
import com.ecom.payload.ApiResponse;
import com.ecom.payload.ProductDto;
import com.ecom.payload.RoleDto;
import com.ecom.payload.UserDto;
import com.ecom.service.FileService;
import com.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
public class UserController {


    @Autowired
    private UserService userService;
    
    @Value("${user.profiles}")
    private String userPath;
    
    @Autowired
    private FileService fileService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(
            @RequestBody UserDto userDto
    ) {
        UserDto user = this.userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }

    //update role
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/role/{userId}")
    public ResponseEntity<UserDto> updateRoles(
            @PathVariable Integer userId,
            @RequestBody List<RoleDto> roles
    ) {
        UserDto userDto = this.userService.updateRole(userId, roles);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    //update user
    @PutMapping("/{userid}")
    public ResponseEntity<UserDto> update(
            @RequestBody UserDto userDto,
            @PathVariable Integer userid
    ) {
	 UserDto update = this.userService.update(userDto, userid);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }
    
    //get by id
    @GetMapping("/users/{userId}/")
    public ResponseEntity<UserDto> getById(@PathVariable Integer userId) {
    	UserDto user=this.userService.getById(userId);
        return new ResponseEntity<UserDto>( user,HttpStatus.OK);
    }
    
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ApiResponse> deleteUsers(@PathVariable Integer userId) {
        this.userService.deleteUser(userId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setSuccess(true);
        apiResponse.setMessage("User deleted successfully !!");
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    
    //user upload of image
    
    @PostMapping("/users/image/{userId}")
    public ResponseEntity<ApiResponse> uploadFile(
            @RequestParam("image") MultipartFile file,
            @PathVariable Integer userId
    ) throws Exception {
    	UserDto user = this.userService.getById(userId);
        String fileName = this.fileService.uploadFile(userPath, file);
        user.setImageName(fileName);
        this.userService.update(user, userId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage(fileName);
        apiResponse.setSuccess(true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    
    //user get image
    
    @GetMapping(value = "/users/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void serveUserImage(
            @PathVariable String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream data = this.fileService.getData(userPath, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(data, response.getOutputStream());
        data.close();

    }
    
    //delete image of user
    @DeleteMapping(value = "/image/{userId}/")
    public ResponseEntity<ApiResponse>deleteImage(@PathVariable Integer userId) throws IOException{
    	this.userService.deleteUserImage(userId, userPath);
    	ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setMessage("image is deleted successfully");
        apiResponse.setSuccess(true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
