package org.test.client.mcopclient.model;

import android.os.Environment;

import org.test.client.mcopclient.R;

import java.util.ArrayList;
import java.util.List;

public class RecordList {
    private String storageLocation;
    private List<String> listRecordings;
    private String extension;

    public RecordList(){
        this.listRecordings = new ArrayList<>();
        this.storageLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        this.extension = ".3gp";
    }

    public RecordList(List<String> listRecordings){
        this.listRecordings = listRecordings;
        this.storageLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        this.extension = ".3gp";
    }

    public List<String> getListRecordings() {
        return this.listRecordings;
    }

    public String getRecordNameByIndex(int index){
        return listRecordings.get(index);
    }

    public String getFullRecordNameByIndex(int index){
        return storageLocation + listRecordings.get(index) + extension;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public String getExtension() {
        return extension;
    }

    public void addRecord(String record){
        this.listRecordings.add(record);
    }

    public int getSize(){
        return this.listRecordings.size();
    }

    public void removeRecord(int position) {
        listRecordings.remove(position);
    }
}