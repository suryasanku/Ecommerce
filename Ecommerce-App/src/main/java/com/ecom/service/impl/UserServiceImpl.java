package com.ecom.service.impl;

import com.ecom.entity.Product;
import com.ecom.entity.Role;
import com.ecom.entity.User;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.payload.ProductDto;
import com.ecom.payload.RoleDto;
import com.ecom.payload.UserDto;
import com.ecom.repository.RoleRepo;
import com.ecom.repository.UserRepository;
import com.ecom.service.FileService;
import com.ecom.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;
    
    @Autowired
    private FileService fileService;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        //ROLE_NORMAL
        Role role = this.roleRepo.findByName("ROLE_ADMIN");
        user.getRoles().add(role);
        User saved = this.userRepository.save(user);
        return this.modelMapper.map(saved, UserDto.class);
    }

    @Override
    public UserDto updateRole(Integer userId, List<RoleDto> roles) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User ", "user id ", userId + ""));
        Set<Role> roles1 = user.getRoles();
        roles1.clear();
        roles1.addAll(roles.stream().map(roleDto -> this.modelMapper.map(roleDto, Role.class)).collect(Collectors.toSet()));
        User save = this.userRepository.save(user);
        return this.modelMapper.map(save, UserDto.class);


    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "user id", userId + ""));

//        user.getRoles().clear();
//
//        this.userRepository.save(user);

        this.userRepository.delete(user);
    }

	@Override
	public UserDto update(UserDto c, Integer userid) {
		// TODO Auto-generated method stub
		User user  = this.userRepository.findById(userid).orElseThrow(() -> new ResourceNotFoundException("user", " user id ", userid + ""));
		user.setName(c.getName());
		user.setEmail(c.getEmail());
		user.setPassword(this.passwordEncoder.encode(c.getPassword()));
		user.setImageName(c.getImageName());
		user.setAddress(c.getAddress());
		user.setAbout(c.getAbout());
		user.setDate(c.getDate());
		
		User updateduser = this.userRepository.save(user);
        return this.modelMapper.map(updateduser, UserDto.class);
	}

	@Override
	public UserDto getById(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "user id ", userId + ""));

        return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public void deleteUserImage(Integer userId, String userPath) throws IOException {
		// TODO Auto-generated method stub
		User user=this.userRepository.findById(userId).orElseThrow(ResourceNotFoundException::new);
		String imageName=user.getImageName();
		 String fullPath=userPath+File.separator+imageName;
		 this.fileService.deleteFileIfExists(fullPath);
		 user.setImageName(null);
		 this.userRepository.save(user);
	}
}
