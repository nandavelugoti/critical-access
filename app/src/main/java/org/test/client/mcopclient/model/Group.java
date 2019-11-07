package org.test.client.mcopclient.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String id;
    private String groupName;
    private String displayName;
    private boolean isBroadcast;
    public List<User> users = new ArrayList<>();

    public String getID(){
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isBroadcast() {
        return isBroadcast;
    }

    public List<User> getUsers() {
        return users;
    }
}
