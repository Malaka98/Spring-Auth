package com.example.demo.security.model;

import org.hibernate.usertype.UserType;

public class AuthenticationResponse {

    private String username;
    private long id;
    private UserType userType;
    private String token;

    public AuthenticationResponse() {
        super();
    }

    public AuthenticationResponse(String username, long id, String token) {
        this.username = username;
        this.id = id;
        this.token = token;
    }

    public AuthenticationResponse(String username, long id, UserType userType, String token) {
        this.username = username;
        this.id = id;
        this.userType = userType;
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
