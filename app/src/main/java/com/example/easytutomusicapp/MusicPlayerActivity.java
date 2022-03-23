package com.example.easytutomusicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {
    TextView titleTv, currentTimeTv, totalTimeTv;
    SeekBar seekBar;
    ImageView btn_play, btn_revious, btn_next, musicIcon;
    ArrayList<AudioModel> songList;
    AudioModel currentSong;

    MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
    // we can return the media player from there.

    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        mapping();

        titleTv.setSelected(true);
        // we have got the song list.
        songList = (ArrayList<AudioModel>) getIntent().getSerializableExtra("LIST");
        setResourcesWithMusic();

        MusicPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTimeTv.setText(convertToMMSS(mediaPlayer.getCurrentPosition()+ ""));

                    if(mediaPlayer.isPlaying()){
                        btn_play.setImageResource(R.drawable.ic_pause);
                        musicIcon.setRotation(x++);
                    }else {
                        btn_play.setImageResource(R.drawable.ic_play);
                        musicIcon.setRotation(0);
                    }
                }
                new Handler().postDelayed(this, 50);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // when user change the seekbar
                if(mediaPlayer != null && fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();

            // the first,
            // i will set seekbar to 0
            // ,when the music play starts
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void playNextSong(){
        if(MyMediaPlayer.currentIndex== songList.size()-1){
            return;
        }
        MyMediaPlayer.currentIndex += 1;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }

    private void playPreviousSong(){
        if(MyMediaPlayer.currentIndex== 0){
            return;
        }
        MyMediaPlayer.currentIndex -= 1;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }

    private void pausePlay(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }else{
            mediaPlayer.start();
        }
    }

    public static String convertToMMSS(String duration){
        Long millis = Long.parseLong(duration);

        // we will convert to minute and seconds.
        return String.format("%02d:%02d",
                // convert it to minute.
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                // convert it to seconds
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }

}