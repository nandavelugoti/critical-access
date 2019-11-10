package org.test.client.mcopclient.model;

public class Group {
    String name;
    int photo;

    public Group(String name, int photo) {
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public int getPhoto() {
        return photo;
    }
}
