package com.example.demo.security.model;

public class AuthenticationRequest {

    private String username;
    private String password;
    private String userType;

    public AuthenticationRequest() {
        super();
    }

    public AuthenticationRequest(String username, String password, String userType) {
        this.setUsername(username);
        this.setPassword(password);
        this.setUserType(userType);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
