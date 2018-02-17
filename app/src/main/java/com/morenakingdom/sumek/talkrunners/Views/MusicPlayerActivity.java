package com.morenakingdom.sumek.talkrunners.Views;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.morenakingdom.sumek.talkrunners.Models.Song;
import com.morenakingdom.sumek.talkrunners.R;
import com.morenakingdom.sumek.talkrunners.Services.MusicService;
import com.morenakingdom.sumek.talkrunners.Views.Adapters.SongAdapter;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayerActivity extends AppCompatActivity {

    private List <Song> songList;
    private ListView songView;
    private SongAdapter adapter;

    private ImageButton playPauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_music_player );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }
        songList = new ArrayList <>();
        songView = findViewById( R.id.songList );
        playPauseButton = findViewById( R.id.playButton );

        MusicService.getInstance().setContext( getApplicationContext() );

        adapter = new SongAdapter( this, songList );
        songView.setAdapter( adapter );

    }

    /**
     * The read permission Acquiring differs with some version (Marshmallow+).
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission() {
        if (checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE )
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE )) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions( new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1 );
        }
    }

    public void refreshList(View view) {
        songList.clear();

        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query( musicUri, null, null, null, null );

        if (musicCursor != null && musicCursor.moveToFirst()) {

            int idColumn = musicCursor.getColumnIndex( android.provider.MediaStore.Audio.Media._ID );
            int titleColumn = musicCursor.getColumnIndex( android.provider.MediaStore.Audio.Media.TITLE );
            int artistColumn = musicCursor.getColumnIndex( android.provider.MediaStore.Audio.Media.ARTIST );
            do {
                long thisId = musicCursor.getLong( idColumn );
                String thisTitle = musicCursor.getString( titleColumn );
                String thisArtist = musicCursor.getString( artistColumn );
                songList.add( new Song( thisId, thisTitle, thisArtist ) );
                //System.out.println(thisArtist + " " + thisTitle);
            }
            while (musicCursor.moveToNext());
        }

        if (musicCursor != null) {
            musicCursor.close();
        }

        MusicService.getInstance().setList( songList );
        adapter.notifyDataSetChanged();
    }

    public void startSong(View view) {
        MusicService.getInstance().setSong( Integer.parseInt( view.getTag().toString() ) );
        MusicService.getInstance().startSong();
        updateView( true );
    }

    public void playPauseSong(View view) {
        if (MusicService.getInstance().isReady()) {
            if (!MusicService.getInstance().isPlaying()) {
                MusicService.getInstance().playSong();

            } else {
                MusicService.getInstance().pauseSong();
            }
            updateView();
        }
    }

    private void updateView() {
        updateView( false );
    }

    private void updateView(boolean forcePlay) {
        if (MusicService.getInstance().isPlaying() || forcePlay) {
            playPauseButton.setImageResource( android.R.drawable.ic_media_pause );
        } else {

            playPauseButton.setImageResource( android.R.drawable.ic_media_play );
        }
    }
}
