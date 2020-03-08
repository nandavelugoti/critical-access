package org.test.client.mcopclient.model;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    private User currentUser;

//    public static String readInternal(String filePathName) throws IOException {
//        StringBuffer stringBuffer = new StringBuffer();
//
//        FileInputStream fileInputStream = new FileInputStream(filePathName);
//
//        byte[] buffer = new byte[1024];
//        int len = fileInputStream.read(buffer);
//        while (len > 0) {
//            stringBuffer.append(new String(buffer, 0, len));
//            len = fileInputStream.read(buffer);
//        }
//        fileInputStream.close();
//        return stringBuffer.toString();
//    }
//
//    public static void writeInternal(String filePathName, String[] content) throws IOException {
//
//        FileOutputStream fileOutputStream = new FileOutputStream(filePathName);
//        for (String contactFile :content) {
//            fileOutputStream.write(contactFile.getBytes());
//        }
//        fileOutputStream.close();
//    }

    // load contact data
//    public void load_contacts() throws IOException {
//        Context context = getContext();
//        String pathFile = context.getFilesDir().getAbsolutePath();
//        String contactFileNames = readInternal(pathFile);
//        this.contactFiles = contactFileNames.split(",");
//
//        for (String fileNameString :this.contactFiles) {
//            SharedPreferences sharedPreferences = context.getSharedPreferences(fileNameString, MODE_PRIVATE);
//            String mcptt_id = sharedPreferences.getString("McpttID", "NULL");
//            String displayName = sharedPreferences.getString("Username", "NULL");
//
//            User user = new User(mcptt_id, displayName);
//            users.add(user);
//        }
//    }

    public void load_contacts(){
        String EnvType = Environment.DIRECTORY_DOCUMENTS;

        File targetFolder = Environment.getExternalStoragePublicDirectory(EnvType);

        File file = new File(targetFolder, "contacts.txt");

        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] contactinfo = line.split(",");
                User user = new User(contactinfo[1],contactinfo[0]);
                users.add(user);
            }
            br.close();
        }
        catch (IOException e) {
        }
    }

    public void load_groups(){
        String EnvType = Environment.DIRECTORY_DOCUMENTS;

        File targetFolder = Environment.getExternalStoragePublicDirectory(EnvType);

        File file = new File(targetFolder, "groups.txt");

        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] contactinfo = line.split(",");
                Group group = new Group(contactinfo[1],contactinfo[0]);
                groups.add(group);
            }
            br.close();
        }
        catch (IOException e) {
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static void writeExternal(File filePath, User user) throws IOException {
        if(filePath.exists()){
            FileOutputStream fileOutputStream = new FileOutputStream(filePath,true);
            fileOutputStream.write(user.getDisplayName().getBytes());
            fileOutputStream.write(",".getBytes());
            fileOutputStream.write(user.getMcpttID().getBytes());
            fileOutputStream.write("\n".getBytes());
            fileOutputStream.close();
        }else {
            filePath.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.write(user.getDisplayName().getBytes());
            fileOutputStream.write(",".getBytes());
            fileOutputStream.write(user.getMcpttID().getBytes());
            fileOutputStream.write("\n".getBytes());
            fileOutputStream.close();
        }
    }

    public static void writeExternalGroup(File filePath, Group group) throws IOException {
        if(filePath.exists()){
            FileOutputStream fileOutputStream = new FileOutputStream(filePath,true);
            fileOutputStream.write(group.getDisplayName().getBytes());
            fileOutputStream.write(",".getBytes());
            fileOutputStream.write(group.getMcpttID().getBytes());
            fileOutputStream.write("\n".getBytes());
            fileOutputStream.close();
        }else {
            filePath.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.write(group.getDisplayName().getBytes());
            fileOutputStream.write(",".getBytes());
            fileOutputStream.write(group.getMcpttID().getBytes());
            fileOutputStream.write("\n".getBytes());
            fileOutputStream.close();
        }
    }

    public void addUser(User user) throws IOException {
        String contactName = user.getDisplayName();

        if (users.contains(contactName)){

        } else {
            if (isExternalStorageWritable()){
                String EnvType = Environment.DIRECTORY_DOCUMENTS;

                File targetFolder = Environment.getExternalStoragePublicDirectory(EnvType);
                targetFolder.mkdirs();

                File file = new File(targetFolder, "contacts.txt");

                writeExternal(file,user);

                users.add(user);
            }
        }
    }

    public void removeUser(User user) throws IOException {
        // remove user from local file
        String contactName = user.getDisplayName();

        if (users.contains(user)){

            String EnvType = Environment.DIRECTORY_DOCUMENTS;

            File targetFolder = Environment.getExternalStoragePublicDirectory(EnvType);

            File file = new File(targetFolder, "contacts.txt");

            StringBuilder text = new StringBuilder();

            try {
                ArrayList<String> tempContactList = new ArrayList<>();
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    String[] contactinfo = line.split(",");
                    if (user.getDisplayName().equals(contactinfo[1])){

                    }else{
                        tempContactList.add(line);
                    }
                }
                br.close();

                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                for (String newline:tempContactList) {
                    String[] contactinfo = newline.split(",");
                    fileOutputStream.write(contactinfo[1].getBytes());
                    fileOutputStream.write(",".getBytes());
                    fileOutputStream.write(contactinfo[0].getBytes());
                    fileOutputStream.write("\n".getBytes());
                }
                fileOutputStream.close();
            }
            catch (IOException e) {
            }

            users.remove(user);

        } else {

        }
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

    public void addGroup(Group group) throws IOException {
        String groupName = group.getDisplayName();

        if (groups.contains(groupName)){

        } else {
            groups.add(group);
            if (isExternalStorageWritable()){
                String EnvType = Environment.DIRECTORY_DOCUMENTS;

                File targetFolder = Environment.getExternalStoragePublicDirectory(EnvType);
                targetFolder.mkdirs();

                File file = new File(targetFolder, "groups.txt");

                writeExternalGroup(file,group);

            }
        }

    }

    public void removeGroup(Group group) throws IOException {
        // remove user from local file
        String groupName = group.getDisplayName();

        if (groups.contains(group)){

            String EnvType = Environment.DIRECTORY_DOCUMENTS;

            File targetFolder = Environment.getExternalStoragePublicDirectory(EnvType);

            File file = new File(targetFolder, "groups.txt");

            StringBuilder text = new StringBuilder();

            try {
                ArrayList<String> tempGroupList = new ArrayList<>();
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    String[] contactinfo = line.split(",");
                    if (group.getDisplayName().equals(contactinfo[1])){

                    }else{
                        tempGroupList.add(line);
                    }
                }
                br.close();

                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                for (String newline:tempGroupList) {
                    String[] contactinfo = newline.split(",");
                    fileOutputStream.write(contactinfo[1].getBytes());
                    fileOutputStream.write(",".getBytes());
                    fileOutputStream.write(contactinfo[0].getBytes());
                    fileOutputStream.write("\n".getBytes());
                }
                fileOutputStream.close();
            }
            catch (IOException e) {
            }
            groups.remove(group);

        } else {

        }

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

    public void clearAll() throws IOException {
        groups.clear();
        users.clear();

        if (isExternalStorageWritable()){
            String EnvType = Environment.DIRECTORY_DOCUMENTS;

            File targetFolder = Environment.getExternalStoragePublicDirectory(EnvType);
            targetFolder.mkdirs();

            File contactfile = new File(targetFolder, "contacts.txt");
            contactfile.createNewFile();
            FileOutputStream fileClearContact = new FileOutputStream(contactfile);
            fileClearContact.write("".getBytes());
            fileClearContact.close();

            File groupfile = new File(targetFolder, "groups.txt");
            groupfile.createNewFile();
            FileOutputStream fileClearGroup = new FileOutputStream(groupfile);
            fileClearGroup.write("".getBytes());
            fileClearGroup.close();
        }
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