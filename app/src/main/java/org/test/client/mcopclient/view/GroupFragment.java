package org.test.client.mcopclient.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.test.client.mcopclient.R;
import org.test.client.mcopclient.model.Group;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2/28/2017.
 */

public class GroupFragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    private Button btnTEST;

    private RecyclerView myRecyclerView;
    private List<Group> listGroups;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get real list of groups
        listGroups = new ArrayList<>();
        listGroups.add(new Group("Groupe 1", R.drawable.group_photo));
        listGroups.add(new Group("Groupe 2", R.drawable.group_photo));
        listGroups.add(new Group("Groupe 3", R.drawable.group_photo));
        listGroups.add(new Group("Groupe 4", R.drawable.group_photo));
        listGroups.add(new Group("Groupe 5", R.drawable.group_photo));
        listGroups.add(new Group("Groupe 6", R.drawable.group_photo));
        listGroups.add(new Group("Groupe 7", R.drawable.group_photo));
        listGroups.add(new Group("Groupe 8", R.drawable.group_photo));
        listGroups.add(new Group("Groupe 9", R.drawable.group_photo));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group,container,false);

        myRecyclerView = (RecyclerView) view.findViewById(R.id.group_recyclerview);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listGroups);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }
}