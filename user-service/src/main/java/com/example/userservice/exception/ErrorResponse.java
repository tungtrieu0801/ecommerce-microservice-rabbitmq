package com.example.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse implements Serializable {

    private String timestamp;

    private int status;

    private String error;

    private String message;

    private String path;
}
