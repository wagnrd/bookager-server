package wagnrd.bookagerserver.login;

public class UnauthorizedLoginException extends RuntimeException {
    public UnauthorizedLoginException() {
        super("Unauthorized Login!");
    }
}
