package wagnrd.bookagerserver.login;

public class InvalidSessionException extends RuntimeException {
    public InvalidSessionException() {
        super("Invalid session authentication key!");
    }
}
