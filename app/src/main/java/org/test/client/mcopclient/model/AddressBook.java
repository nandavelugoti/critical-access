package org.test.client.mcopclient.model;

import java.util.ArrayList;
import java.util.List;

public class AddressBook {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Group> groups = new ArrayList<>();

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    private User currentUser = new User("Current User ID", "Current User Name");

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public User getUser(final String mcpttID) {
        User userData = null;
        for (User user : users) {
            if (user.getMcpttID().equals(mcpttID))
                userData = user;
        }
        return userData;
    }

    public User getUserByName(final String name) {
        User userData = null;
        for (User user : users) {
            if (user.getDisplayName().equals(name))
                userData = user;
        }
        return userData;
    }

    public List<User> getAllUsers() {
        return users;
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public Group getGroup(final String mcpttID) {
        Group groupData = null;
        for (Group group : groups) {
            if (group.getMcpttID().equals(mcpttID))
                groupData = group;
        }
        return groupData;
    }

    public List<Group> getAllGroups() {
        return groups;
    }

    public void clearAll() {
        groups.clear();
        users.clear();
    }

    public Group getGroupByName(String name) {
        Group groupData = null;
        for (Group group : groups) {
            if (group.getDisplayName().equals(name))
                groupData = group;
        }
        return groupData;
    }
}