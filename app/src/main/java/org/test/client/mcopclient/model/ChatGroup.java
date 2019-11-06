package org.test.client.mcopclient.model;

public class ChatGroup extends Group {

    public void invite(User invitedUser) {
        users.add(invitedUser);
    }

    public void remove(User removingUser) {
        users.remove(removingUser);
    }
}
