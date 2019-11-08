package org.test.client.mcopclient.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.test.client.mcopclient.R;
import org.test.client.mcopclient.model.User;

import java.util.ArrayList;
import java.util.List;


public class ContactFragment extends Fragment {
    private RecyclerView myRecyclerView;
    UserRecyclerViewAdapter recyclerViewAdapter;
    private List<User> listUsers;
    SearchView searchView;
    EditText filter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get real list of groups
        listUsers = new ArrayList<>();
        listUsers.add(new User("ID", "Name 1", R.drawable.group_photo));
        listUsers.add(new User("ID", "Name 2", R.drawable.group_photo));
        listUsers.add(new User("ID", "Name 3", R.drawable.group_photo));
        listUsers.add(new User("ID", "Name 4", R.drawable.group_photo));
        listUsers.add(new User("ID", "Name 5", R.drawable.group_photo));
        listUsers.add(new User("ID", "Name 6", R.drawable.group_photo));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact,container,false);

        filter = (EditText) view.findViewById(R.id.filter);

        myRecyclerView = (RecyclerView) view.findViewById(R.id.contact_recyclerview);
        recyclerViewAdapter = new UserRecyclerViewAdapter(getContext(), listUsers);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerViewAdapter);

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
                recyclerViewAdapter.setData(filtered);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

}