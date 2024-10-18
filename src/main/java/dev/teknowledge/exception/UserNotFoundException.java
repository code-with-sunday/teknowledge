package dev.teknowledge.exception;

import lombok.Data;

@Data
public class UserNotFoundException extends RuntimeException{
    private  String message;
    private String status;
    public UserNotFoundException(String message){
        this.message = message;
        this.status = String.valueOf(status);
    }
}
