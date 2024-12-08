package com.rest.product_manag_system.dto;
import java.util.List;

import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Data;

@Data
//@Builder
public class ErrorResponse {

	private Integer status;
	private String message;
	
	 // Private constructor for the builder
    private ErrorResponse(Builder builder) {
        this.status = builder.status;
        this.message = builder.message;
       
    }

    // Static Builder class
    public static class Builder {
    	private Integer status;
    	private String message;

        public Builder status(Integer status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }
    // Getters
    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
    
	
}
