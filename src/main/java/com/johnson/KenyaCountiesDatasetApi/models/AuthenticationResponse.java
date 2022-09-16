package com.johnson.KenyaCountiesDatasetApi.models;

public class AuthenticationResponse {

    private String jwt;

    public AuthenticationResponse() {
        super();
    }

    public AuthenticationResponse(String jwt) {
        super();
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

}
