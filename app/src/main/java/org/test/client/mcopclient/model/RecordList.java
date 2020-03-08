package org.test.client.mcopclient.model;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecordList {
    private String storageLocation;
    private List<String> listRecordings;
    private String extension;

    public RecordList() throws Exception {
        this.listRecordings = new ArrayList<>();
        this.storageLocation = createFolder("records") + "/";
        this.extension = ".3gp";
    }

    public RecordList(List<String> listRecordings) {
        this.listRecordings = listRecordings;
        this.storageLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        this.extension = ".3gp";
    }

    public List<String> getListRecordings() {
        return this.listRecordings;
    }

    public String getRecordNameByIndex(int index) {
        return listRecordings.get(index);
    }

    public String getFullRecordNameByIndex(int index) {
        return storageLocation + listRecordings.get(index) + extension;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public String getExtension() {
        return extension;
    }

    public void addRecord(String record) {
        this.listRecordings.add(record);
    }

    public int getSize() {
        return this.listRecordings.size();
    }

    public void removeRecord(int position) {
        listRecordings.remove(position);
    }

    private String createFolder(String folderName) throws Exception {
        String folderPath = Environment.getExternalStorageDirectory() + "/" + folderName;
        File folder = new File(folderPath);
        if (!folder.exists())
            if (!folder.mkdir()) {
                Log.v("Record folder:", folderPath + " can't be created.");
                throw new Exception("Record folder:" + folderPath + " can't be created.");
            } else Log.v("Record folder:", folderPath + " can be created.");
        else {
            Log.v("Record folder:", folderPath + " already exists.");
            // get existing records
            File[] files = folder.listFiles();
            List<String> records = new ArrayList<>();
            for (File f : files){
                records.add(f.getName().split("[.]")[0]);
            }
            listRecordings.addAll(records);
        }
        return folderPath;
    }

}