/*
 *
 *  Copyright (C) 2018 Eduardo Zarate Lasurtegui
 *   Copyright (C) 2018, University of the Basque Country (UPV/EHU)
 *
 *  Contact for licensing options: <licensing-mcpttclient(at)mcopenplatform(dot)com>
 *
 *  This file is part of MCOP MCPTT Client
 *
 *  This is free software: you can redistribute it and/or modify it under the terms of
 *  the GNU General Public License as published by the Free Software Foundation, either version 3
 *  of the License, or (at your option) any later version.
 *
 *  This is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.test.client.mcopclient.model;

import org.test.client.mcopclient.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class User {
    private String mcpttID;
    private String displayName;
    private Map<String, String> sessionIDs;
    private ArrayList<String> groups;
    private int photo;

    public User(String mcpttID, String displayName) {
        this(mcpttID, displayName, R.drawable.group_photo);
    }

    public User(String mcpttID, String displayName, int photo) {
        this.mcpttID = mcpttID;
        this.displayName = displayName;
        this.photo = photo;
        sessionIDs=new HashMap<String,String>();
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public User(String mcpttID, String displayName, boolean isRegisted) {
        this.mcpttID = mcpttID;
        this.displayName = displayName;
        sessionIDs = new HashMap<String, String>();
    }


    public User() {
        sessionIDs = new HashMap<String, String>();
    }


    public String getMcpttID() {
        return mcpttID;
    }

    public void setMcpttID(String mcpttID) {
        this.mcpttID = mcpttID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getSessionIDs() {
        if (sessionIDs == null || sessionIDs.keySet() == null) return null;
        Iterator<String> interator = sessionIDs.keySet().iterator();
        ArrayList<String> ids = new ArrayList<>();
        while (interator.hasNext())
            ids.add(interator.next());
        return ids;
    }

    public void removeSessionID(String sessionIDs) {
        this.sessionIDs.remove(sessionIDs);
    }

    public void addSessionID(String sessionIDs) {
        this.sessionIDs.put(sessionIDs, sessionIDs);
    }

    public void addGroup(String group) {
        groups.add(group);
    }
}
