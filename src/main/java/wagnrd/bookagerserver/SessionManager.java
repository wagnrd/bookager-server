package wagnrd.bookagerserver;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.HexUtils;
import wagnrd.bookagerserver.account.InvalidSessionException;
import wagnrd.bookagerserver.data.User;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SessionManager {
    private static SessionManager sessionManager;

    private final Map<String, Session> sessions = new HashMap<>();
    private final SecureRandom random = new SecureRandom();
    //private byte[] randomBytes = new byte[16];
    private byte[] randomBytes = new byte[1];

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (sessionManager == null)
            sessionManager = new SessionManager();

        return sessionManager;
    }

    public String createSession(User user) {
        String authKey;

        do {
            //random.nextBytes(randomBytes);        // !!!!!! DISABLED FOR TESTING ONLY !!!!!!
            authKey = HexUtils.toHexString(randomBytes);
        } while (sessions.containsKey(authKey));

        sessions.put(authKey, new Session(authKey, user, this));
        log.info("Session \"" + authKey + "\": CREATED");

        return authKey;
    }

    public void delete(String authKey) throws InvalidSessionException {
        if (sessions.containsKey(authKey)) {
            log.info("Session \"" + authKey + "\": DELETED");
            sessions.get(authKey).interrupt();
            sessions.remove(authKey);
        } else {
            throw new InvalidSessionException();
        }
    }

    public void validate(String authKey) {
        if (!sessions.containsKey(authKey))
            throw new InvalidSessionException();
    }

    public void touch(String authKey) throws InvalidSessionException {
        if (sessions.containsKey(authKey))
            sessions.get(authKey).updateExpirationTime();
        else
            throw new InvalidSessionException();
    }

    public User getSessionUser(String authKey) throws InvalidSessionException {
        if (sessions.containsKey(authKey))
            return sessions.get(authKey).getUser();
        else
            throw new InvalidSessionException();
    }
}
