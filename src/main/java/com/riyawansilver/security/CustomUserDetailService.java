package com.riyawansilver.security;

import com.riyawansilver.models.User;
import com.riyawansilver.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepo userRepo;
    @Autowired
    public CustomUserDetailService(UserRepo userRepo){
        this .userRepo=userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser=userRepo.findByEmail(username);

        if(optionalUser.isEmpty()){
            throw new UsernameNotFoundException("user with mail doesnot present");
        }

        return new CustomUserDetail(optionalUser.get());
    }
}
