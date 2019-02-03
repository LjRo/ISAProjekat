package isa.projekat.Projekat.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AdminEnabledException.class)
    public ResponseEntity<Object> passwordNotChanged(AdminEnabledException e) {
        return new ResponseEntity<Object>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
