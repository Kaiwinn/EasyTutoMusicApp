package com.example.easytutomusicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView txt_NoMusic;
    ArrayList<AudioModel> songsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        if(checkPermisson() == false){
            requestPermission();
            return;
        }
        String[] projection = {
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";


        Cursor cursor = getContentResolver().query(MediaStore
                .Audio.Media.EXTERNAL_CONTENT_URI,projection, selection, null ,null );
        while(cursor.moveToNext()){
            //initialization SongData
            AudioModel songdata = new AudioModel(cursor.getString(1), cursor.getString(0), cursor.getString(2));

            //Check song exist or not
            if(new File(songdata.getPath()).exists()){
                //add songdata in listSong
                songsList.add(songdata);
            }
        }
        if(songsList.size()== 0){
            txt_NoMusic.setVisibility(View.VISIBLE);

        }else{
            txt_NoMusic.setVisibility(View.INVISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MusicListAdapter(songsList, getApplicationContext()));
        }
    }

    private void mapping() {
        recyclerView = findViewById(R.id.recycler_view);
        txt_NoMusic = findViewById(R.id.txt_noSongsFound);

    }

    boolean checkPermisson(){
        int result= ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        }else{
            return  false;
        }

    }

    void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this, "READ IS REQUIRED, PLEASE ALLOW FROM SETTINGS.", Toast.LENGTH_LONG).show();
        }else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
        }


    }
}