package utils;

import entities.User;

public final class SessionManager {

    private static SessionManager instance;
    private static User currentUser;

    private SessionManager(User user) {
        currentUser = user;
    }

    public static SessionManager getInstance(User user) {
        if (instance == null) {
            instance = new SessionManager(user);
        }
        return instance;
    }

    public static SessionManager getInstance() {
        return instance;
    }

    public static void setInstance(SessionManager instance) {
        SessionManager.instance = instance;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void cleanUserSession() {
        currentUser = null;
    }

    @Override
    public String toString() {
        return "SessionManager{" +
                "currentUser=" + currentUser +
                '}';
    }

    public static void setUser(int id, User user) {
        if (currentUser != null && currentUser.getId() == id) {
            currentUser = user;
        }
    }
}
