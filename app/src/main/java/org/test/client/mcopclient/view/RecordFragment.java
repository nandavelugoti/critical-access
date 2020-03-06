package org.test.client.mcopclient.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.test.client.mcopclient.R;
import org.test.client.mcopclient.model.RecordList;


/**
 * Created by User on 2/28/2017.
 */

public class RecordFragment extends Fragment {

    public static RecyclerView myRecyclerView;
    private RecordList listRecordings;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listRecordings = HomePage.recordedFiles;
    }

    @Override
    public void onStart() {
        super.onStart();
        listRecordings = HomePage.recordedFiles;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recordings, container, false);

        myRecyclerView = (RecyclerView) view.findViewById(R.id.record_recyclerview);
        RecordRecyclerViewAdapter recordRecyclerViewAdapter = new RecordRecyclerViewAdapter(getContext(), listRecordings);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recordRecyclerViewAdapter);

        return view;
    }

    public static void updateRecordList() {
        if (myRecyclerView != null) {
            Log.v("Recycler: ", myRecyclerView.toString());
            RecordRecyclerViewAdapter adapter = (RecordRecyclerViewAdapter) myRecyclerView.getAdapter();
            adapter.notifyDataSetChanged();
        }
    }
}