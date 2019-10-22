package wagnrd.bookagerserver.account;

public class UnauthorizedLoginException extends RuntimeException {
    public UnauthorizedLoginException() {
        super("Unauthorized Login!");
    }
}
