package wagnrd.bookagerserver.account;

public class InvalidSessionException extends RuntimeException {
    public InvalidSessionException() {
        super("Invalid session authentication key!");
    }
}
