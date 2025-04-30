package by.artur.testtask.exceptions;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = EmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleEntityEmailAlreadyExistsException(EmailException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(value = PhoneException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleEntityPhoneAlreadyExistsException(PhoneException ex) {
        return ex.getMessage();
    }

}
