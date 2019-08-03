package com.ted.eBayDIT.exception;


import com.ted.eBayDIT.ui.model.request.UserDetailsRequestModel;
import com.ted.eBayDIT.ui.model.response.ErrorDetailsModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

//@ControllerAdvice
//@RestController
//
//public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(value = {UserDetailsRequestModel.class})
//    public ResponseEntity<Object> handleUserServiceException(UserDetailsRequestModel ) {
//        return handleUserServiceException(, );
//    }
//
//    @ExceptionHandler(value = {UserServiceException.class})
//    public ResponseEntity<Object> handleUserServiceException(UserServiceException ex, WebRequest request)
//    {
//        ErrorDetailsModel errorMessage = new ErrorDetailsModel(new Date(), ex.getMessage(), request.getDescription(false));
//
//        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
//    }
//
//
//    @ExceptionHandler(value = {Exception.class})
//    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request)
//    {
//        ErrorDetailsModel errorMessage = new ErrorDetailsModel(new Date(), ex.getMessage(),request.getDescription(false));
//
//        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//}





//@ControllerAdvice
//@RestController
//public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//
//
//
//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
//        ErrorDetailsModel errorDetails = new ErrorDetailsModel(new Date(), ex.getMessage(),
//                request.getDescription(false));
//        return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//    @ExceptionHandler(UserServiceException.class)
//    public final ResponseEntity<Object> handleUserNotFoundException(UserServiceException ex, WebRequest request) {
//        ErrorDetailsModel errorDetails = new ErrorDetailsModel(new Date(), ex.getMessage(),
//                request.getDescription(false));
//        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
//    }
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ErrorDetailsModel errorDetails = new ErrorDetailsModel(new Date(), "Validation Failed",
//                ex.getBindingResult().toString());
//        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
//    }
//}


