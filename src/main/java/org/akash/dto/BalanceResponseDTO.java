package org.akash.dto;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

public class BalanceResponseDTO {
    String message;
    boolean success;
    String error;
    HashMap response;
    HttpStatus status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public HashMap getResponse() {
        return response;
    }

    public void setResponse(HashMap response) {
        this.response = response;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
