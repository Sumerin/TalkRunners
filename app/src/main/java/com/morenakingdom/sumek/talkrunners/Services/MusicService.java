package com.morenakingdom.sumek.talkrunners.Services;

import android.app.Service;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.morenakingdom.sumek.talkrunners.Models.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton that is responsible for playing music.
 * Created by sumek on 2/17/18.
 */
public class MusicService implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {
    /**
     * Singleton.
     */
    private static MusicService instance = null;

    /**
     * instance of android player.
     */
    private MediaPlayer player;

    /**
     * list of song loaded to the service.
     */
    private List <Song> songs;

    /**
     * position of current playing song in collection.
     */
    private int songPosition;

    /**
     * ApplicationContext.
     */
    private Context context;

    /**
     * Setting the application context. Part of preparation.
     *
     * @param context Application context.
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Setting the player song list. Part of preparation.
     *
     * @param songs songs list to be managed by player service.
     */
    public void setList(List <Song> songs) {
        this.songs = songs;
    }

    /**
     * @return State of Media player.
     */
    public boolean isPlaying() {
        return player.isPlaying();
    }

    /**
     * @return Indicates if everything is ready to start playing.
     */
    public boolean isReady() {
        return songs != null && context != null && songs.size() != 0;
    }

    /**
     * @return Singleton getter.
     */
    public static MusicService getInstance() {
        if (instance == null) {
            instance = new MusicService();
        }
        return instance;
    }

    private MusicService() {
        songPosition = 0;
        songs = new ArrayList <>();
        player = new MediaPlayer();
        initMusicPlayer();
    }

    /**
     * Implementation of MediaPlayer.OnCompletion.
     * Indicates media player behaviour after finishing playing.
     *
     * @param mp mediaplayer to which is assigned.
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        songPosition++;
        startSong();
    }

    /**
     * Implementation of MediaPlayer.onError.
     * Indicates media player behaviour after error.
     *
     * @param mp    mediaplayer to which is assigned.
     * @param what  What happen mate?
     * @param extra Some extra stuff.
     * @return always false, the problem is unsolvable.
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    /**
     * Implementation of MediaPlayer.OnPrepared.
     * Indicates media player behaviour after finishing preparation.
     * Useful when asynchronous prepare is called.
     *
     * @param mp mediaplayer to which is assigned.
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    /**
     * Release all the resource.
     *
     * @throws Throwable who knows.
     */
    @Override
    protected void finalize() throws Throwable {
        player.stop();
        player.release();
        player = null;
    }

    /**
     * Highly dangerous for further application work. Not recommended unless it is necessary to GC the singleton.
     */
    public void releaseSingleton() {
        instance = null;
    }

    /**
     * Resets player and start playing music on selected position.
     */
    public void startSong() {
        player.reset();

        Song playSong = songs.get( songPosition );
        long currSong = playSong.getId();
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong );

        try {
            player.setDataSource( this.context, trackUri );
        } catch (Exception e) {
            Log.e( "MUSIC SERVICE", "Error setting data source", e );
        }

        player.prepareAsync();
    }

    /**
     * Select position of song.
     *
     * @param songIndex song index in service manged song list.
     */
    public void setSong(int songIndex) {
        this.songPosition = songIndex;
    }

    /**
     * Stop playing song.
     */
    public void stopSong() {
        player.stop();
    }

    /**
     * Pause the song.
     */
    public void pauseSong() {
        player.pause();
    }

    /**
     * Resume the play of song.
     */
    public void playSong() {
        player.start();
    }

    /**
     * Initialize Music player and assign listeners called and service constructor.
     */
    private void initMusicPlayer() {
        //player.setWakeMode(this.context, PowerManager.PARTIAL_WAKE_LOCK); // no difference in working, actually without it work like with it xD
        player.setAudioStreamType( AudioManager.STREAM_MUSIC );

        player.setOnPreparedListener( this );
        player.setOnCompletionListener( this );
        player.setOnErrorListener( this );
    }
}
