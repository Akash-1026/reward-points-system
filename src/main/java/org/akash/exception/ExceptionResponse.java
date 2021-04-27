package org.akash.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private String errorMessage;
    private String errorCode;
    private LocalDateTime timestamp;

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setTimestamp(LocalDateTime  timestamp) {
        this.timestamp = timestamp;
    }
}
