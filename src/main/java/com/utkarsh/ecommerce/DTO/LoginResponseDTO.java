package com.utkarsh.ecommerce.DTO;

public class LoginResponseDTO {

    private String token;
    private String tokenType;
    private UserResponseDTO user;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, UserResponseDTO user) {
        this.token = token;
        this.tokenType = "Bearer";
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }
}