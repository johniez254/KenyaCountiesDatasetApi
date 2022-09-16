package com.johnson.KenyaCountiesDatasetApi.models;

public class AuthenticationRequest {
    private String username;
    private String password;

    //    constructors
    public AuthenticationRequest() {
        super();
    }

    public AuthenticationRequest(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    //    setters and getters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
