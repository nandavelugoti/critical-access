package org.test.client.mcopclient.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import org.test.client.mcopclient.R;
import org.test.client.mcopclient.controller.MCOPServiceManager;
import org.test.client.mcopclient.model.AddressBook;
import org.test.client.mcopclient.model.Group;
import org.test.client.mcopclient.model.User;

import java.io.IOException;
import java.util.List;

/**
 * Created by User on 2/28/2017.
 */

public class GroupFragment extends Fragment {

    private RecyclerView myRecyclerView;
    private List<Group> listGroups;
    Button btnAddGroup;

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

        btnAddGroup = (Button) view.findViewById(R.id.btn_add_group);
        final Context context = getContext();

        btnAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                 Add Group
                LayoutInflater ligroup = LayoutInflater.from(context);
                final View promptsView = ligroup.inflate(R.layout.add_group, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                alertDialogBuilder.setView(promptsView);

                final EditText groupNameInput = (EditText) promptsView
                        .findViewById(R.id.dialogGroupNameInput);
                final EditText mcpttIdInput = (EditText) promptsView.findViewById(R.id.dialogGroupMcpttIdInput);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        String groupName = groupNameInput.getText().toString();
                                        String groupMcptt = mcpttIdInput.getText().toString();
                                        try {
                                            MCOPServiceManager.AddressBook.addGroup(new Group(groupMcptt, groupName));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                                        inputMethodManager.hideSoftInputFromWindow(promptsView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                                        inputMethodManager.hideSoftInputFromWindow(promptsView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });
        return view;
    }
}