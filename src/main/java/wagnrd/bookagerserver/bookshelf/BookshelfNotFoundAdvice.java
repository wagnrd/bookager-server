package wagnrd.bookagerserver.bookshelf;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookshelfNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(BookshelfNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String bookshelfNotFoundHandler(BookshelfNotFoundException ex) {
        return ex.getMessage();
    }
}
