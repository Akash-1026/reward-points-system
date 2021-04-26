package org.akash.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private String errorMessage;
    private String errorCode;
    private LocalDateTime timestamp;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public LocalDateTime  getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime  timestamp) {
        this.timestamp = timestamp;
    }
}
