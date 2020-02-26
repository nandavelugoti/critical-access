package org.test.client.mcopclient.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.test.client.mcopclient.R;
import org.test.client.mcopclient.model.RecordList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class RecordRecyclerViewAdapter extends RecyclerView.Adapter<RecordRecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    RecordList mData;
    Integer clickedPosition;

    public RecordRecyclerViewAdapter(Context mContext, RecordList mData) {
        this.mContext = mContext;
        this.mData = mData;
        clickedPosition = null;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recording, parent, false);
        MyViewHolder viewHolder = new MyViewHolder((view));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtDisplayName.setText(mData.getRecordNameByIndex(position));
        if (clickedPosition != null && clickedPosition != position){
            holder.btnPlayPause.setEnabled(false);
            holder.btnPlayPause.setImageResource(R.drawable.ic_play_arrow_disabled);

        } else {
            holder.btnPlayPause.setEnabled(true);
            holder.btnPlayPause.setImageResource(R.drawable.ic_play_arrow);
        }
    }

    @Override
    public int getItemCount() {
        return mData.getSize();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private boolean isPlaying = false;
        private TextView txtDisplayName;
        private ImageView btnPlayPause;
        private ImageView btnStop;
        private ImageView btnDelete;
        private MediaPlayer mediaPlayer;
        private String record;
        private FileInputStream fis;
        private int position;

        public MyViewHolder(final View itemView) {
            super(itemView);
            txtDisplayName = (TextView) itemView.findViewById(R.id.recording_title);
            btnPlayPause = itemView.findViewById(R.id.button_play_pause);
            btnStop = itemView.findViewById(R.id.button_stop_record);
            btnDelete = itemView.findViewById(R.id.button_delete_record);

            btnPlayPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isPlaying = !isPlaying;
                    btnPlayPause.setImageResource(!isPlaying ? R.drawable.ic_play_arrow : R.drawable.ic_pause);
                    position = getAdapterPosition();

                    if (isPlaying) {
                        btnStop.setVisibility(View.VISIBLE);
                        //stopPlaying();
                        mediaPlayer = new MediaPlayer();
                        try {
                            clickedPosition = position;
                            notifyDataSetChanged();
                            record = mData.getFullRecordNameByIndex(position);
                            File directory = new File(record);
                            fis = new FileInputStream(directory);
                            mediaPlayer.setDataSource(fis.getFD());
                            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.start();

                        // Return to original state once file is ended
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            public void onCompletion(MediaPlayer mp) {
                                stopPlaying();
                            }
                        });
                    } else {
                        //TODO pause instead of stop
                        //mediaPlayer.pause();
                        //keep btnStop
                        //how to know which one to re-play:
                        //will work if disable other record buttons
                        stopPlaying();
                    }
                }
            });

            btnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stopPlaying();
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    position = getAdapterPosition();
                    File file = new File(mData.getFullRecordNameByIndex(position));
                    boolean exists = file.exists();
                    if (exists) {
                        file.delete();
                        mData.removeRecord(position);
                    }
                    notifyDataSetChanged();
                }
            });
        }

        private void stopPlaying() {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            isPlaying = false;
            btnPlayPause.setImageResource(R.drawable.ic_play_arrow);
            btnStop.setVisibility(View.GONE);
            clickedPosition = null;
            notifyDataSetChanged();

        }
    }
}
