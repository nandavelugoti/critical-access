package org.test.client.mcopclient.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.test.client.mcopclient.R;
import org.test.client.mcopclient.controller.MCOPServiceManager;
import org.test.client.mcopclient.model.AddressBook;
import org.test.client.mcopclient.model.Group;

import java.util.List;

/**
 * Created by User on 2/28/2017.
 */

public class GroupFragment extends Fragment {

    private RecyclerView myRecyclerView;
    private List<Group> listGroups;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listGroups = MCOPServiceManager.AddressBook.getAllGroups();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group,container,false);

        myRecyclerView = (RecyclerView) view.findViewById(R.id.group_recyclerview);
        GroupRecyclerViewAdapter groupRecyclerViewAdapter = new GroupRecyclerViewAdapter(getContext(), listGroups);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(groupRecyclerViewAdapter);

        return view;
    }
}