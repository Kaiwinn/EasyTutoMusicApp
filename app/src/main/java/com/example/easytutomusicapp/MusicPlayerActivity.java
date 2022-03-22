package com.example.easytutomusicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {
    TextView titleTv, currentTimeTv, totalTimeTv;
    SeekBar seekBar;
    ImageView btn_play, btn_revious, btn_next, musicIcon;
    ArrayList<AudioModel> songList;
    AudioModel currentSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        mapping();
        // we have got the song list.
        songList = (ArrayList<AudioModel>) getIntent().getSerializableExtra("LIST");
        setResourcesWithMusic();
    }

    private void mapping() {
        titleTv = findViewById(R.id.song_title);
        currentTimeTv = findViewById(R.id.txv_current_time);
        totalTimeTv = findViewById(R.id.txv_total_time);
        seekBar = findViewById(R.id.seekbar);
        btn_play = findViewById(R.id.img_play);
        btn_next = findViewById(R.id.img_next);
        btn_revious = findViewById(R.id.img_revious);
        musicIcon = findViewById(R.id.music_icon_big);
    }
    void setResourcesWithMusic(){
        // set everything to this method
        currentSong = songList.get(MyMediaPlayer.currentIndex);

        titleTv.setText(currentSong.getTitle());

        totalTimeTv.setText(convertToMMSS(currentSong.getDuration()));

        btn_play.setOnClickListener(v -> pausePlay());
        btn_next.setOnClickListener(v -> playNextSong());
        btn_revious.setOnClickListener(v -> playPreviousSong());

        playMusic();
    }
    private void playMusic(){

    }

    private void playNextSong(){

    }

    private void playPreviousSong(){

    }

    private void pausePlay(){

    }

    public static String convertToMMSS(String duration){
        Long millis = Long.parseLong(duration);

        // we will convert to minute and seconds.
        return String.format("%02d:%02d",
                // convert it to minute.
                TimeUnit.MICROSECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                // convert it to seconds
                TimeUnit.MICROSECONDS.toSeconds(millis) % TimeUnit.HOURS.toSeconds(1));
    }

}