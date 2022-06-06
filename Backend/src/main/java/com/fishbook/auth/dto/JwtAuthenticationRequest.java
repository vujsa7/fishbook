package com.fishbook.auth.dto;

import javax.validation.constraints.NotBlank;

public class JwtAuthenticationRequest {

    public JwtAuthenticationRequest(){
        super();
    }

    public JwtAuthenticationRequest(String username, String password){
        this.username = username;
        this.password = password;
    }

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
