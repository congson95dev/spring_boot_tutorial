package com.example.tutorial.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Getter
@Setter
@Builder
public class LogDetails {
    private String message;
    private String methodName;
    private String fileName;
    private int lineNumber;

    // add log to the server
    public static LogDetails show(Exception ex) {
        return LogDetails.builder()
                .message(ExceptionUtils.getRootCause(ex).getMessage())
                .methodName(ExceptionUtils.getRootCause(ex).getStackTrace()[0].getMethodName())
                .fileName(ExceptionUtils.getRootCause(ex).getStackTrace()[0].getFileName())
                .lineNumber(ExceptionUtils.getRootCause(ex).getStackTrace()[0].getLineNumber())
                .build();
    }
}
