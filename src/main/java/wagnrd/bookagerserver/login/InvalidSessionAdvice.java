package wagnrd.bookagerserver.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidSessionAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidSessionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String invalidSessionHandler(InvalidSessionException ex) {
        return ex.getMessage();
    }
}
