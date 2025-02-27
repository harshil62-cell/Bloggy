package com.blogging.bloggingApp.payloads;

import jakarta.validation.constraints.*;
import java.util.List;

public class UserDto {
    private int id;

    @NotEmpty(message = "Name should not be blank")
    @Size(min = 3, message = "Name should be at least 3 characters long")
    private String name;

    @Email(message = "Email address is not valid")
    private String email;

    @NotEmpty
    @Size(min = 5, max = 14, message = "Password must be between 5 and 14 characters")
    private String password;

    private String about;

    // Removed posts here to avoid cyclic serialization
    // private List<PostDto> posts;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    // Constructor

    public UserDto(int id, String name, String email, String password, String about) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.about = about;
    }

    public UserDto() {
        super();
    }
}
