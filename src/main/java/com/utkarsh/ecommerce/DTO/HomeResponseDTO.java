package com.utkarsh.ecommerce.DTO;

public class HomeResponseDTO {

    private String message;
    private String application;
    private String status;

    public HomeResponseDTO(String message, String application, String status) {
        this.message = message;
        this.application = application;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getApplication() {
        return application;
    }

    public String getStatus() {
        return status;
    }
}