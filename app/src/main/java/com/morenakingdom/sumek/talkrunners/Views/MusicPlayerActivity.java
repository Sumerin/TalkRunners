package com.morenakingdom.sumek.talkrunners.Views;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

import com.morenakingdom.sumek.talkrunners.Models.Song;
import com.morenakingdom.sumek.talkrunners.R;
import com.morenakingdom.sumek.talkrunners.Views.Adapters.SongAdapter;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayerActivity extends AppCompatActivity {

    List <Song> songList;
    ListView songView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_music_player );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }
        songList = new ArrayList <Song>();
        songView = findViewById( R.id.songList );

        //SongAdapter adapter = new SongAdapter( this, songList );
        //songView.setAdapter( adapter );
    }

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

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return;
        }
    }

    public void refreshList(View view) {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query( musicUri, null, null, null, null );

        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns

            int idColumn = musicCursor.getColumnIndex( android.provider.MediaStore.Audio.Media._ID );
            int titleColumn = musicCursor.getColumnIndex( android.provider.MediaStore.Audio.Media.TITLE );
            int artistColumn = musicCursor.getColumnIndex( android.provider.MediaStore.Audio.Media.ARTIST );
            //add songs to list
            do {
                long thisId = musicCursor.getLong( idColumn );
                String thisTitle = musicCursor.getString( titleColumn );
                String thisArtist = musicCursor.getString( artistColumn );
                songList.add( new Song( thisId, thisTitle, thisArtist ) );
                //System.out.println(thisArtist + " " + thisTitle);
            }
            while (musicCursor.moveToNext());
        }

    }
}
