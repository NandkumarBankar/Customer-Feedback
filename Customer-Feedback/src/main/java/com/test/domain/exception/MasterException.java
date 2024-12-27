package com.test.domain.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MasterException {
    Logger logger = LoggerFactory.getLogger(MasterException.class);

    @ExceptionHandler(MasterNotFoundException.class)
    public ResponseEntity<Object> handleMasterNotFoundException(MasterNotFoundException ex){
        logger.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(RowAlreadyExistsException.class)
    public ResponseEntity<Object> handleMasterDuplicateException(RowAlreadyExistsException ex){
        logger.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
