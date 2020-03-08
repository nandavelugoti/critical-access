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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import org.test.client.mcopclient.R;
import org.test.client.mcopclient.controller.MCOPServiceManager;
import org.test.client.mcopclient.model.AddressBook;
import org.test.client.mcopclient.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.test.client.mcopclient.CriticalAccess.getContext;


public class ContactFragment extends Fragment {
    private RecyclerView myRecyclerView;
    UserRecyclerViewAdapter recyclerViewAdapter;
    private List<User> listUsers;
    EditText filter;
    Button btnAddContact, btnRemoveContact;
    EditText edtName, edtMcpttId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get real list of groups
        listUsers = MCOPServiceManager.AddressBook.getAllUsers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_contact,container,false);

        filter = (EditText) view.findViewById(R.id.filter);

        myRecyclerView = (RecyclerView) view.findViewById(R.id.contact_recyclerview);
        recyclerViewAdapter = new UserRecyclerViewAdapter(getContext(), listUsers);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);
        btnAddContact = (Button) view.findViewById(R.id.btn_add_contact);
//        btnRemoveContact = (Button) view.findViewById(R.id.btn_remove_contact);
        final Context context = getContext();

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // Add Contact
                LayoutInflater li = LayoutInflater.from(context);
                final View promptsView = li.inflate(R.layout.add_contact, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                alertDialogBuilder.setView(promptsView);

                final EditText userNameInput = (EditText) promptsView
                        .findViewById(R.id.dialogUserNameInput);
                final EditText mcpttIdInput = (EditText) promptsView.findViewById(R.id.dialogMcpttIdInput);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String userName = userNameInput.getText().toString();
                                        String userMcptt = mcpttIdInput.getText().toString();
                                        try {
                                            MCOPServiceManager.AddressBook.addUser(new User(userMcptt, userName));
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
//        btnRemoveContact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // remove contact
//                LayoutInflater li = LayoutInflater.from(context);
//                View promptsView = li.inflate(R.layout.add_contact, null);
//
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                        context);
//
//                alertDialogBuilder.setView(promptsView);
//
//                final EditText userNameInput = (EditText) promptsView
//                        .findViewById(R.id.dialogUserNameInput);
//                final EditText mcpttIdInput = (EditText) promptsView.findViewById(R.id.dialogMcpttIdInput);
//                alertDialogBuilder
//                        .setCancelable(false)
//                        .setPositiveButton("OK",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        // get user input and set it to result
//                                        // edit text
//                                        String userName = userNameInput.getText().toString();
//                                        String userMcptt = mcpttIdInput.getText().toString();
//                                        try {
//                                            MCOPServiceManager.AddressBook.removeUser(new User(userMcptt, userName));
//                                        } catch (IOException e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                })
//                        .setNegativeButton("Cancel",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,int id) {
//                                        dialog.cancel();
//                                    }
//                                });
//
//                // create alert dialog
//                AlertDialog alertDialog = alertDialogBuilder.create();
//
//                // show it
//                alertDialog.show();
//
//            }
//        });

        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<User> filtered = new ArrayList<>();
                for (User user: listUsers) {
                    if (user.getDisplayName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        filtered.add(user);
                    }
                }
                //recyclerViewAdapter.setData(filtered);
                UserRecyclerViewAdapter filteredrecyclerViewAdapter = new UserRecyclerViewAdapter(getContext(), filtered);
                myRecyclerView.setAdapter(filteredrecyclerViewAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

}