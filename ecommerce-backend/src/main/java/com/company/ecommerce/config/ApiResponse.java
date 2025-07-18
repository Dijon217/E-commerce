package com.company.ecommerce.config;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ApiResponse {
    private final boolean success;
    private String message;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getTimeStamp() {
        return LocalDateTime.now().toString();
    }
}
