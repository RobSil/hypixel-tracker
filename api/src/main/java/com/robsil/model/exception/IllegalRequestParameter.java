package com.robsil.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Something wrong with request's parameter's!")
public class IllegalRequestParameter extends RuntimeException {
    public IllegalRequestParameter() {
        super();
    }

    public IllegalRequestParameter(String message) {
        super(message);
    }
}
