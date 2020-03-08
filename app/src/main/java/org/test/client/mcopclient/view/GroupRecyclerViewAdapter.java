package org.test.client.mcopclient.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.test.client.mcopclient.CriticalAccess;
import org.test.client.mcopclient.R;
import org.test.client.mcopclient.controller.MCOPCallManager;
import org.test.client.mcopclient.controller.MCOPServiceManager;
import org.test.client.mcopclient.model.AddressBook;
import org.test.client.mcopclient.model.Group;
import org.test.client.mcopclient.model.User;
import org.test.client.mcopclient.model.calls.CallConfig;

import java.io.IOException;
import java.util.List;

public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<GroupRecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<Group> mData;

    public GroupRecyclerViewAdapter(Context mContext, List<Group> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_group, parent, false);
        MyViewHolder viewHolder = new MyViewHolder((view));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtDisplayName.setText(mData.get(position).getDisplayName());
        holder.imgPhoto.setImageResource(mData.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private boolean isCallInProgress = false;
        private TextView txtDisplayName;
        private ImageView imgPhoto, imgCall;

        private ImageView btnRemove;

        public MyViewHolder(final View itemView) {
            super(itemView);
            txtDisplayName = (TextView) itemView.findViewById(R.id.name_group);
            imgPhoto = (ImageView) itemView.findViewById(R.id.image_group);
            imgCall = itemView.findViewById(R.id.image_call);

            btnRemove = itemView.findViewById(R.id.button_remove_contact);

            imgCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isCallInProgress) {
                        CallConfig config = MCOPCallManager.getCallConfig();
                        MCOPCallManager.makeGroupCall(MCOPServiceManager.AddressBook.getGroupByName(txtDisplayName.getText().toString()), config);
                    } else {
                        MCOPCallManager.hangup(MCOPServiceManager.AddressBook.getGroupByName(txtDisplayName.getText().toString()).getMcpttID());
                    }
                    isCallInProgress = !isCallInProgress;
                    imgCall.setImageResource(!isCallInProgress ? R.drawable.baseline_call_black_18dp: R.drawable.baseline_call_end_black_18dp);
                    HomePage.updateCallerId();
                    HomePage.updateCaller();
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        Group group = MCOPServiceManager.AddressBook.getGroupByName(txtDisplayName.getText().toString());
                        MCOPServiceManager.AddressBook.removeGroup(group);
                        mData.remove(group);
                        notifyDataSetChanged();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
