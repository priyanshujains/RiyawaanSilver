package com.riyawansilver.dtos;

import lombok.Getter;
import lombok.Setter;

import java.security.PrivateKey;

@Getter
@Setter
public class SignupDto {
    private String  name;
    private String  email;
    private String  password;
    private String  mobile;
    private String  address;
}
