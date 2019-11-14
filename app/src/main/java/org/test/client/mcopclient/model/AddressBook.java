package org.test.client.mcopclient.model;

import java.util.ArrayList;
import java.util.List;

public class AddressBook {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Group> groups = new ArrayList<>();

    public static void addUser(User user) {
        users.add(user);
    }

    public static void removeUser(User user) {
        users.remove(user);
    }

    public static User getUser(final String mcpttID) {
        User userData = null;
        for (User user : users) {
            if (user.getMcpttID().equals(mcpttID))
                userData = user;
        }
        return userData;
    }

    public static List<User> getAllUsers() {
        return users;
    }

    public static void addGroup(Group group) {
        groups.add(group);
    }

    public static void removeGroup(Group group) {
        groups.remove(group);
    }

    public static Group getGroup(final String mcpttID) {
        Group groupData = null;
        for (Group group : groups) {
            if (group.getMcpttID().equals(mcpttID))
                groupData = group;
        }
        return groupData;
    }

    public static List<Group> getAllGroups() {
        return groups;
    }
}