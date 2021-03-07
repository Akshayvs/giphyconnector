package com.sofi.giphyconnector.DataTransferObjects;

import org.springframework.http.HttpStatus;

public class GenericResponseDTO {

    private HttpStatus HttpStatus;
    private String message;

    public GenericResponseDTO(String errorMessage, HttpStatus status) {
        this.message = errorMessage;
        this.HttpStatus = status;
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.HttpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
