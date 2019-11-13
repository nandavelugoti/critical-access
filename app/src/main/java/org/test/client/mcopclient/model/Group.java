package org.test.client.mcopclient.model;

import org.test.client.mcopclient.R;

public class Group {
    private String mcpttID;
    private String displayName;
    private int photo;

    public Group(String mcpttID, String displayName) {
        this(mcpttID, displayName, R.drawable.group_photo);
    }

    public Group(String mcpttID, String displayName, int photo) {
        this.mcpttID = mcpttID;
        this.displayName = displayName;
        this.photo = photo;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPhoto() {
        return photo;
    }

    public String getMcpttID(){
        return mcpttID;
    }
}