package com.morenakingdom.sumek.talkrunners.Views;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.morenakingdom.sumek.talkrunners.Controllers.ClientController;
import com.morenakingdom.sumek.talkrunners.R;
import com.morenakingdom.sumek.talkrunners.Services.Client.AudioModule;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * Prototype till functionality will be completed.
 */
public class PlayerActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_player );
    }

    public void playMusic(View view) {
        AudioModule md = ClientController.getInstance().getAudioModule();
        md.setContext( getApplicationContext() );
        new Thread( md ).start();
    }
}
