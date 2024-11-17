package com.riyawansilver.controllers;


import com.riyawansilver.Exceptions.EmailExistException;
import com.riyawansilver.Exceptions.UserNotExistException;
import com.riyawansilver.dtos.SigninDto;
import com.riyawansilver.dtos.SignupDto;
import com.riyawansilver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
public class UserController {
        private UserService userService;


        @Autowired
        public UserController(UserService userService){
            this.userService=userService;
        }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignupDto signupDto)
                                            throws EmailExistException {

        return new ResponseEntity<>(userService.register(signupDto), HttpStatus.OK) ;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SigninDto signinDto) throws UserNotExistException {
          return new ResponseEntity<>(userService.verify(signinDto),HttpStatus.OK) ;
    }
}
