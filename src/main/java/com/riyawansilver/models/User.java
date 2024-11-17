package com.riyawansilver.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseModel{

    private String name;
    private String email;
    private String password;
    @ManyToMany(cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
    private List<Role> roles;
    private String mobile;
    private String address;
}
