package com.example.easytutomusicapp;

import android.media.MediaPlayer;

public class MyMediaPlayer {
    // we need MediaPlayer to play the Music.
    static MediaPlayer instance;

    public static MediaPlayer getInstance(){
        // if there is no instance,
        // is the first time
        // we will create instance.
        if(instance == null){
            instance = new MediaPlayer();
        }
        return instance;
    }
    public static int currentIndex = -1;
        // it means the song is not clicked it.
}
