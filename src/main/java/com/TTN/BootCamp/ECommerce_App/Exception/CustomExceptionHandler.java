package com.TTN.BootCamp.ECommerce_App.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.security.auth.login.AccountLockedException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorFormat> handleAllException(Exception ex, WebRequest request) throws Exception{
        ErrorFormat errorFormat = new ErrorFormat(LocalDateTime.now(), ex.getMessage(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorFormat, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArguments(MethodArgumentNotValidException ex){
        Map<String,String> errorMap = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors() // returns a list of errors
                .forEach(fieldError -> {
                    // populating map with, field with error as key, and relevant message as value
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());

                });
        return errorMap;
    }


    @ExceptionHandler(PasswordDoNotMatchException.class)
    public ResponseEntity<ErrorFormat> handlePasswordDoNotMatch(PasswordDoNotMatchException ex, WebRequest request) throws  PasswordDoNotMatchException{
        ErrorFormat errorFormat = new ErrorFormat(LocalDateTime.now(), ex.getMessage(), request.getDescription(false), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorFormat, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorFormat> handleUserAlreadyExists(UserAlreadyExistsException ex, WebRequest request) throws  UserAlreadyExistsException{
        ErrorFormat errorFormat = new ErrorFormat(LocalDateTime.now(), ex.getMessage(), request.getDescription(false), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorFormat, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorFormat> handleBadCredentials(BadCredentialsException ex, WebRequest request) throws  BadCredentialsException{
        ErrorFormat errorFormat = new ErrorFormat(LocalDateTime.now(), ex.getMessage(), request.getDescription(false), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorFormat, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<ErrorFormat> handleAccountLocked(AccountLockedException ex, WebRequest request){
        ErrorFormat errorFormat = new ErrorFormat(LocalDateTime.now(), ex.getMessage(), request.getDescription(false), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorFormat, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorFormat> handleUserNotFound(UsernameNotFoundException ex, WebRequest request){
        ErrorFormat errorFormat = new ErrorFormat(LocalDateTime.now(), ex.getMessage(), request.getDescription(false), HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorFormat, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidSecureTokenException.class)
    public final ResponseEntity<ErrorFormat> handleInvalidToken(InvalidSecureTokenException ex, WebRequest request){
        ErrorFormat errorFormat = new ErrorFormat(LocalDateTime.now(), ex.getMessage(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorFormat, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LinkExpiredException.class)
    public final ResponseEntity<ErrorFormat> handleLinkExpired(LinkExpiredException ex, WebRequest request){
        ErrorFormat errorFormat = new ErrorFormat(LocalDateTime.now(), ex.getMessage(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorFormat, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
