package wagnrd.bookagerserver.login;

import org.apache.tomcat.util.buf.HexUtils;
import wagnrd.bookagerserver.data.User;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private Map<String, Session> sessions = new HashMap<>();
    private SecureRandom random = new SecureRandom();

    public String createSession(User user) {
        String authKey;

        do {
            byte[] randomBytes = new byte[128];
            random.nextBytes(randomBytes);
            authKey = HexUtils.toHexString(randomBytes);
        } while (sessions.containsKey(authKey));

        Session session = new Session(authKey, user, this);

        return authKey;
    }

    public void touch(String authKey) throws SessionExpiredException {
        if (sessions.containsKey(authKey))
            sessions.get(authKey).updateExpirationTime();
        else
            throw new SessionExpiredException();
    }

    public void delete(String authKey) {
        sessions.remove(authKey);
    }

}
