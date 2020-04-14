package com.example.exception;

import com.example.message.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ResourceNotFoundExceptionHandler
{
    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handler(ResourceNotFoundException e){
        return new ResponseEntity(new ErrorMessage(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
