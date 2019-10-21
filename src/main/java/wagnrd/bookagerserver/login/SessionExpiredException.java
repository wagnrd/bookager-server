package wagnrd.bookagerserver.login;

public class SessionExpiredException extends RuntimeException {
    public SessionExpiredException() {
        super("The session is expired!");
    }
}
