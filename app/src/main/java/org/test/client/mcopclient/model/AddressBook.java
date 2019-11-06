package org.test.client.mcopclient.model;

import java.util.ArrayList;

public class AddressBook {
    private static ArrayList<User> users = new ArrayList<>();

    public static void addUser(User user) {
        users.add(user);
    }

    public static void removeUser(User user) {
        users.remove(user);
    }

    public static User getUser(final String mcpttID) {
        User userData = null;
        for (User user: users) {
            if(user.getMcpttID().equals(mcpttID))
                userData = user;
        }
        return userData;
    }
}