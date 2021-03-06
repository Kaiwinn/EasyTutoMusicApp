package com.example.easytutomusicapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder>{

    ArrayList<AudioModel> songList ;
    Context context;

    public MusicListAdapter(ArrayList<AudioModel> songList, Context context) {
        this.songList = songList;
        this.context = context;
    }

    @Override
    // credit HOLDER
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent, false);
        return new MusicListAdapter.ViewHolder(view);
        // have assigned our recyclee item to this view Holder.
    }

    @Override
    // online HOLDER
    public void onBindViewHolder(MusicListAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AudioModel songdata = songList.get(position);
        holder.titleTextView.setText(songdata.getTitle());

        if(MyMediaPlayer.currentIndex== position){
            holder.titleTextView.setTextColor(Color.parseColor("#db0ba0"));
        }else{
            holder.titleTextView.setTextColor(Color.parseColor("#000000"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigate to another activity
//when i click on anyitem
// recycler view it will first reset the media player.
                MyMediaPlayer.getInstance().reset();
// set the current index at that position.
                MyMediaPlayer.currentIndex = position;
// it will navigate to new activity passing the songs list.
                Intent intent = new Intent(context,MusicPlayerActivity.class);
//get this songsList and position in our new acticity.
                intent.putExtra("LIST",songList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    // GET this list song
    public int getItemCount() {
        return songList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        ImageView iconImageView;

        public ViewHolder( View itemView) {
            super(itemView);
            mapping();


        }

        private void mapping() {
            titleTextView = itemView.findViewById(R.id.txv_MUSIC_Title);
            iconImageView = itemView.findViewById(R.id.icon_view);

        }
    }


}
