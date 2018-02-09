package com.morenakingdom.sumek.talkrunners.Views;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.morenakingdom.sumek.talkrunners.R;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_player );
    }

    public void playMusic(View view) {
        MediaPlayer mediaPlayer = MediaPlayer.create( getApplicationContext(), R.raw.simple_music );
        mediaPlayer.start();
    }
}
