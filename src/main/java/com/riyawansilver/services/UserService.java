package com.riyawansilver.services;

import com.riyawansilver.Exceptions.EmailExistException;
import com.riyawansilver.Exceptions.ProductNotFoundException;
import com.riyawansilver.Exceptions.UserNotExistException;
import com.riyawansilver.dtos.SigninDto;
import com.riyawansilver.dtos.SignupDto;
import com.riyawansilver.models.Role;
import com.riyawansilver.models.User;
import com.riyawansilver.repositories.RoleRepo;
import com.riyawansilver.repositories.UserRepo;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collections;

import java.util.Optional;

@Service
public class UserService {

    private UserRepo userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepo roleRepo;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Autowired
    public UserService(UserRepo userRepo,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       RoleRepo roleRepo,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService){
        this.userRepo=userRepo;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.roleRepo=roleRepo;
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
    }


    public String register(SignupDto signupDto) throws EmailExistException{

        Optional<User> userOptional =userRepo.findByEmail(signupDto.getEmail());

        if(!userOptional.isEmpty()) {
            throw new EmailExistException("user with the email"+signupDto.getEmail()+"exist");
        }

            User user = new User();
            user.setName(signupDto.getName());
            user.setEmail(signupDto.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(signupDto.getPassword()));
            user.setMobile(signupDto.getMobile());
            user.setAddress(signupDto.getAddress());

            // Retrieve the "USER" role from the database
            Role userRole = roleRepo.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("USER role not set in database"));

            // Assign role to user
            user.setRoles(Collections.singletonList(userRole));

            User user1 = userRepo.save(user);

            return "User registered successfully!";

    }

    public String verify(SigninDto signinDto) throws UserNotExistException {
        // Authenticate the user using the email and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinDto.getEmail(), signinDto.getPassword())
        );

        // Check if the authentication was successful
        if (authentication.isAuthenticated()) {
            // If authentication is successful, you can proceed with generating a token
            System.out.println("User authenticated successfully.");
            String jwt=jwtService.generateToken(signinDto.getEmail());
            return jwt;
            // Additional logic here, such as generating JWT token or returning user details
        } else {
            throw new UserNotExistException("Authentication failed: User does not exist or credentials are incorrect.");
        }
    }

}
