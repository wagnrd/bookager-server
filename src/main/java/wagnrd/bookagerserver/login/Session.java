package wagnrd.bookagerserver.login;

import lombok.extern.slf4j.Slf4j;
import wagnrd.bookagerserver.data.User;

@Slf4j
public class Session extends Thread {
    private final long keyDuration = 30_000_000; // 30 mins
    private String authKey;
    private User user;
    private long expirationTime;
    private SessionManager manager;

    public Session(String authKey, User user, SessionManager manager) {
        this.authKey = authKey;
        this.user = user;
        this.manager = manager;
        this.expirationTime = System.currentTimeMillis() + keyDuration;

        start();
    }

    public void updateExpirationTime() {
        setExpirationTime(System.currentTimeMillis() + keyDuration);
    }

    public void run() {
        while (getTimeDifference() > 0) {
            try {
                sleep(getTimeDifference());
            } catch (InterruptedException ignored) {
            }
        }

        manager.delete(authKey);
    }

    private long getTimeDifference() {
        return getExpirationTime() - System.currentTimeMillis();
    }

    // Getters
    public String getAuthKey() {
        return authKey;
    }

    public User getUser() {
        return user;
    }

    public synchronized long getExpirationTime() {
        return expirationTime;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public synchronized void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
