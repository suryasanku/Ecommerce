package com.ecom.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class JwtRequest {

    @Email(message = "Invalid Email Id !!")
    private String username;

    //    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Invalid Password !!")
    @NotBlank
    private String password;
}
