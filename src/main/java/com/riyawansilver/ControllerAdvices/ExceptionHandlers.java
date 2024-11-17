package com.riyawansilver.ControllerAdvices;


import com.riyawansilver.Exceptions.EmailExistException;
import com.riyawansilver.Exceptions.InvalidTokenException;
import com.riyawansilver.Exceptions.ProductNotFoundException;
import com.riyawansilver.Exceptions.UserNotExistException;
import com.riyawansilver.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;


@ControllerAdvice
public class ExceptionHandlers {


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException ex) {
        ExceptionDto dto=new ExceptionDto();
        dto.setMessage(ex.getMessage());
        dto.setMessage(ex.toString());
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }


    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException ex) {
        ExceptionDto dto=new ExceptionDto();
        dto.setMessage(ex.getMessage());
        dto.setMessage(ex.toString());
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }



    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxUploadSizeException(MaxUploadSizeExceededException ex) {
        return new ResponseEntity<>("File size exceeds the maximum limit!", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<?> handleEmailFoundException(EmailExistException ex) {
        ExceptionDto dto=new ExceptionDto();
        dto.setMessage(ex.getMessage());
        dto.setMessage(ex.toString());
        return new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> runtimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotExistException ex) {
        ExceptionDto dto=new ExceptionDto();
        dto.setMessage(ex.getMessage());
        dto.setMessage(ex.toString());
        return new ResponseEntity<>(dto,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> handleTokenNotFoundException(InvalidTokenException ex) {
        ExceptionDto dto=new ExceptionDto();
        dto.setMessage(ex.getMessage());
        dto.setMessage(ex.toString());
        return new ResponseEntity<>(dto,HttpStatus.NOT_FOUND);
    }



}
