package com.fishbook.registration.dto;

public class RegistrationResponse {
    private String response;
    private Boolean approved;

    public RegistrationResponse(String response, Boolean approved) {
        this.response = response;
        this.approved = approved;
    }

    public String getResponse() {
        return response;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
}
