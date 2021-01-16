package com.nana.imagerepository.Model;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;


    public JwtResponse(){

    }

    public JwtResponse(String token, String username) {
        this.token = token;
        this.username = username;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "JwtResponse{" +
                "token='" + token + '\'' +
                ", type='" + type + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

