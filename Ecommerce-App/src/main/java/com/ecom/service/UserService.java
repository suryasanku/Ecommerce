package com.ecom.service;

import com.ecom.entity.Role;
import com.ecom.payload.ProductDto;
import com.ecom.payload.RoleDto;
import com.ecom.payload.UserDto;

import java.io.IOException;
import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateRole(Integer userId, List<RoleDto> roles);

    void deleteUser(Integer userId);
    
    UserDto update( UserDto c, Integer userid);
    
    UserDto getById(Integer userId);
    
 //delete user Image
    
    void deleteUserImage(Integer userId,String userPath) throws IOException;

}
