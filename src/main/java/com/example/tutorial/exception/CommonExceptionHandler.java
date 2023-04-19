package com.example.tutorial.exception;

import com.example.tutorial.models.ResponseObject;
import com.example.tutorial.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

// this handler will automatically run when the exception is throwing by system

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class CommonExceptionHandler {
    private final JsonUtils jsonUtils;

    // handle http error 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseObject<String>> handleInternalServerError(HttpServletRequest request, Exception ex) {
        // add log to server
        log.error(jsonUtils.convertObjToString(LogDetails.show(ex)));

        // return response
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject<>(
                        String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                        "Internal server error",
                        null
                ));
    }
}
