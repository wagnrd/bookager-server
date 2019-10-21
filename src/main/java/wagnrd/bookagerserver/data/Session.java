package wagnrd.bookagerserver.data;

import wagnrd.bookagerserver.login.SessionManager;

public class Session extends Thread {
    private final long keyDuration = 30_000_000L; // 30 mins
    private SessionManager manager;

    private String authKey;
    private User user;
    private long expirationTime;

    public Session(String authKey, User user, SessionManager manager) {
        this.authKey = authKey;
        this.user = user;
        this.manager = manager;
        this.expirationTime = System.currentTimeMillis() + keyDuration;

        start();
    }

    public void run() {
        try {
            while (!isInterrupted() && getTimeDifference() > 0) {
                sleep(getTimeDifference());
            }

            manager.delete(authKey);
        } catch (InterruptedException ignored) {
        }
    }

    public void updateExpirationTime() {
        setExpirationTime(System.currentTimeMillis() + keyDuration);
    }

    private long getTimeDifference() {
        return getExpirationTime() - System.currentTimeMillis();
    }

    // Getters and setters
    private synchronized void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public synchronized long getExpirationTime() {
        return expirationTime;
    }

    public String getAuthKey() {
        return authKey;
    }

    public User getUser() {
        return user;
    }
}
